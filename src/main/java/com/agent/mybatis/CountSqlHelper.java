package com.agent.mybatis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixia on 2016/5/11.
 */
public final class CountSqlHelper {

    private final static String WHERE = " WHERE ";
    private final static String where = " where ";

    private final static String FROM = " FROM ";
    private final static String from = " from ";

    private final static String LEFTJOIN = " LEFT JOIN ";
    private final static String leftjoin = " left join ";

    public static String format(String sql) {
        String str = removeOrders(sql);
        str = str.replaceAll("\\s+", " ");
        int begin = str.indexOf(FROM);
        if(begin < 0){
            begin = str.indexOf(from);
        }

        return "SELECT COUNT(1) " + str.substring(begin);
    }

    private static String format1(String sql){
        String countSql = "SELECT COUNT(1) FROM ";
        String str = removeOrders(sql);
        str = str.replaceAll("[\\w|\\W|\\s|\\S]+", " ");
        str = str.substring(str.indexOf(FROM) + FROM.length());
        if(str.indexOf(LEFTJOIN) < 0){ //没有left join
            return countSql + str;
        } else {
            String[] tableNames = str.split(LEFTJOIN);
            String where = str.substring(str.indexOf(WHERE) + WHERE.length());
            String[] wheres = where.split("");

            return "";
        }
    }


    public static String countFormat(String sql){
        String countSql = "SELECT COUNT(1) FROM ";
        String str = removeOrders(sql);
        str = str.replaceAll("\\s+", " ");
        int begin = str.indexOf(FROM);
        if(begin < 0){
            begin = str.indexOf(from);
        }
        str = str.substring(begin + FROM.length());
       // str = str.substring(str.indexOf(FROM) + FROM.length());
        if(str.indexOf(LEFTJOIN) < 0 && str.indexOf(leftjoin) < 0){ //没有left join
            return countSql + str;
        } else {
            if(str.indexOf(WHERE) >= 0) {
                String where = str.substring(str.indexOf(WHERE) + WHERE.length());
                String[] tableNames = str.toUpperCase().split(LEFTJOIN);
                StringBuffer leftJoinStr = new StringBuffer();
                for (int i = 1; i < tableNames.length; i++) {
                    String[] items = tableNames[i].split(" ");

                    if (tableNames[i].toLowerCase().indexOf("t_akt_dict") == -1/*&& where.toLowerCase().indexOf(items[1].toLowerCase()+".")!=-1*/) {
                        if (tableNames[i].toLowerCase().indexOf("where") != -1) {
                            tableNames[i] = tableNames[i].substring(0, tableNames[i].toLowerCase().indexOf("where") - 1);
                        }
                        leftJoinStr.append(" LEFT JOIN " + tableNames[i] + " ");
                    }
                }

                countSql += tableNames[0].toLowerCase().replace("from", " ") + leftJoinStr + WHERE + where;
                return countSql;
            } else {
                return countSql + str.toLowerCase();
            }
        }
    }


    public static void main(String[] args) {
        //String str = "(  FROM  )  FROM ";

        //str = str.replaceAll("\\([\\w|\\W|\\s|\\S]*SELECT[\\w|\\W|\\s|\\S]*FROM[\\w|\\W|\\s|\\S]*\\)", "1111");
        String str = "SELECT COUNT(1) FROM \n" +
                "T_AKT_CUSTOMER_CRM A \n" +
                "\t LEFT JOIN T_AKT_VIP V ON V.ID = A.CUSTOMER_SOURCE\n" +
                "\t\t LEFT JOIN SYS_AREA S ON S.ID = A.CUSTOMER_BELONGS\n" +
                "\t\t\t LEFT JOIN SYS_DICT D1 ON D1.VALUE = A.CUSTOMER_SEX AND D1.TYPE = 'sys_sex'\n" +
                "\t\t\t\t LEFT JOIN SYS_DICT D2 ON D2.VALUE = A.HEALTHY_PAY_TYPE AND D2.TYPE = 'healthy_pay_type'\n" +
                "\t\t\t\t\t LEFT JOIN SYS_DICT D3 ON D3.VALUE = A.LIVE_CONDITION AND D3.TYPE = 'liv__condition'\n" +
                "\t\t\t\t\t\t LEFT JOIN T_AKT_PRODUCT_EXPDATE P ON P.CUSTOMER_ID = A.ID \n" +
                "WHERE A.DEL_FLAG = '0' AND P.product_id = 'd6263b0b7822466ba08d0c30b2b4cc72'";
//        String str = "SELECT \n" +
//                "\t\t\t \n" +
//                "\t\ta.id AS \"id\",\n" +
//                "\t\ta.modify_class AS \"modifyClass\",\n" +
//                "\t\ta.modify_column AS \"modifyColumn\",\n" +
//                "\t\ta.modify_user_id AS \"modifyUserId\",\n" +
//                "\t\ta.modify_user_name AS \"modifyUserName\",\n" +
//                "\t\ta.class_name AS \"className\",\n" +
//                "\t\ta.column_name AS \"columnName\",\n" +
//                "\t\ta.old_value AS \"oldValue\",\n" +
//                "\t\ta.new_value AS \"newValue\",\n" +
//                "\t\ta.create_by AS \"createBy.id\",\n" +
//                "\t\ta.create_date AS \"createDate\",\n" +
//                "\t\ta.update_by AS \"updateBy.id\",\n" +
//                "log4j:ERROR Failed to rename [C:\\Users\\mavisbao/logs/error.log] to [C:\\Users\\mavisbao/logs/error.log.2016-09-19].\n" +
//                "\t\ta.update_date AS \"updateDate\",\n" +
//                "\t\ta.remarks AS \"remarks\",\n" +
//                "\t\ta.dict_name AS \"dictName\",\n" +
//                "\t\ta.modify_id AS \"modifyId\",\n" +
//                "\t\ta.del_flag AS \"delFlag\",\n" +
//                "\t\ta.customer_name AS \"customerName\"\n" +
//                "\t \n" +
//                "\t\tFROM t_akt_batch_log a\n" +
//                "\t\t \n" +
//                "\n" +
//                "\t \n" +
//                "\t\t WHERE a.del_flag = '0'";
       // str = str.replaceAll("\\s+", " ");
        System.out.println(countFormat(str));
    }


    private static String removeOrders(String qlString) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(qlString);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
