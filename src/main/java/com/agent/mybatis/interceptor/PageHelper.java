package com.agent.mybatis.interceptor;

import com.agent.mybatis.CountSqlHelper;
import com.agent.page.Page;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * @author lixia
 * Created by lixia on 2016/5/10.
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class PageHelper implements Interceptor {
    private static final Logger logger = Logger.getLogger(PageHelper.class);

    public static final ThreadLocal<Page> localPage = new ThreadLocal();

    /**
     * 对外公布localPage
     * @return
     */
    public static ThreadLocal<Page> getLocalPage() {
        return localPage;
    }

    /**
     * 开始分页
     * @param pageNum 起始页数
     * @param pageSize 每页条数
     * @param countTotal 是否统计总数
     */
    public static void startPage(int pageNum, int pageSize, boolean countTotal) {
        localPage.set(new Page(pageNum, pageSize, countTotal));
    }

    /**
     *
     * @param pageNum 起始页数
     * @param pageSize 每页条数
     * @param countTotal 是否统计总数
     * @param entity 需要分页的原始对象信息
     * @param <T> 原始对象类型
     */
    public static <T> void startPage(int pageNum, int pageSize, boolean countTotal, T entity) {
        localPage.set(new Page(pageNum, pageSize, countTotal, entity));
    }

    /**
     * 开始分页
     * @param page 分页信息
     */
    public static void startPage(Page page) {
        localPage.set(page);
    }

    /**
     * 结束分页并返回结果，该方法必须被调用，否则localPage会一直保存下去，直到下一次startPage
     * @return
     */
    public static Page endPage() {
        Page page = localPage.get();
        localPage.remove();
        return page;
    }
    /**
     * 设置数据类型
     * @return
     */
    public static void setDataType(String dataType) {
        Page page = localPage.get();
        page.setDataType(dataType);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (localPage.get() == null) {
            return invocation.proceed();
        }

        //数据类型
        String dataTypeFooter = "footer";

        if (invocation.getTarget() instanceof StatementHandler) {

            if (dataTypeFooter.equals(localPage.get().getDataType())) {
                return invocation.proceed();
            }

            Page page = localPage.get();
            //分页信息未完成
            if(!page.isCompleted()) {
                StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
                MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
                // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
                // 可以分离出最原始的的目标类)
                String getter = "h";
                while (metaStatementHandler.hasGetter(getter)) {
                    Object object = metaStatementHandler.getValue(getter);
                    metaStatementHandler = SystemMetaObject.forObject(object);
                }
                // 分离最后一个代理对象的目标类
                String target = "target";
                while (metaStatementHandler.hasGetter(target)) {
                    Object object = metaStatementHandler.getValue(target);
                    metaStatementHandler = SystemMetaObject.forObject(object);
                }
                MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
                BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
                //分页参数作为参数对象parameterObject的一个属性
                String sql = boundSql.getSql();
                //重写sql
                String pageSql = buildPageSql(sql, page);
                //重写分页sql
                metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                Connection connection = (Connection) invocation.getArgs()[0];

                //需要查询分页总数
                if(page.isCountTotal()){
                    //重设分页参数里的总页数等
                    setPageParameter(sql, connection, mappedStatement, boundSql, page);
                } else {
                    page.setTotal(1000000);
                    page.setPages(1000);
                    page.setCompleted(true);
                }
            }
            // 将执行权交给下一个拦截器
            return invocation.proceed();
        } else if (invocation.getTarget() instanceof ResultSetHandler) {
            Object result = invocation.proceed();
            Page page = localPage.get();

            if (dataTypeFooter.equals(page.getDataType())) {
                page.setFooter((List) result);
            } else {
                page.setResult((List) result);
            }

            return result;
        }

        return null;
    }

    /**
     * 只拦截这两种类型的
     * <br>StatementHandler
     * <br>ResultSetHandler
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 修改原SQL为分页SQL
     * @param sql
     * @param page
     * @return
     */
    private String buildPageSql(String sql, Page page) {
        sql = sql.trim();
        StringBuilder pageSql = new StringBuilder(sql.length()+100);
        pageSql.append("select * from(select a.*,row_number() over (order by id desc) rownum from( ");
        pageSql.append(sql);
        pageSql.append(") a )b where rownum> " + (page.getPageNum() - 1) + " and rownum <= " + ((page.getPageNum() - 1) + page.getPageSize()));
        return pageSql.toString();
    }

    /**
     * 获取总记录数
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
                                  BoundSql boundSql, Page page) {
        // 记录总记录数
        String countSql;
        try{
            countSql = CountSqlHelper.countFormat(sql);
        }catch(Exception e){
            countSql = CountSqlHelper.format(sql);
        }
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            setParameters(countStmt, mappedStatement, boundSql);
            rs = countStmt.executeQuery();
            int totalCount = 0;
            int totalCount_group = 0;

            while(rs.next()) {
                totalCount = rs.getInt(1);
                totalCount_group++;
            }
            if (totalCount_group>1
                    || sql.toLowerCase().contains("avg(")
                    || sql.toLowerCase().contains("sum(")
                    ) {
                totalCount = totalCount_group;
            }
            page.setTotal(totalCount);
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
            page.setPages(totalPage);
            page.setCompleted(true);
        } catch (SQLException e) {
            logger.error("Ignore this exception", e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }
    }

    /**
     * 代入参数值
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (boundSql.getParameterObject() == null) {
                        value = null;
                    } else if (mappedStatement.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(boundSql.getParameterObject().getClass())) {
                        value = boundSql.getParameterObject();
                    } else if (boundSql.getAdditionalParameter(propertyName) != null){
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else {
                        MetaObject metaObject = mappedStatement.getConfiguration().newMetaObject(boundSql.getParameterObject());
                        value = metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if (value == null && jdbcType == null) {
                        jdbcType = mappedStatement.getConfiguration().getJdbcTypeForNull();
                    }
                    typeHandler.setParameter(ps, i + 1, value, jdbcType);
                }
            }
        }

    }
}
