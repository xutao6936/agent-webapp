<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="common.jsp"%>
<html>
<head>
    <title>登陆</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/agent-ui/plugins/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/agent-ui/css/index.css"/>
</head>
<body style="background-color: #F2F2F2;">
<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login">
    <div class="layadmin-user-login-main">
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2>登陆</h2>
        </div>
        <form action="${ctx}/login" method="post" class="layadmin-user-login-box layadmin-user-login-body layui-form">
            <div class="layui-form-item">
                <label for="LAY-user-login-username"></label>
                <input name="username" id="LAY-user-login-username" lay-verify="required" placeholder="用户名" class="layui-input" type="text">
            </div>
            <div class="layui-form-item">
                <label for="LAY-user-login-password"></label>
                <input name="password" id="LAY-user-login-password" lay-verify="required" placeholder="密码" class="layui-input" type="password">
            </div>
            <%
                String error = (String) request.getAttribute("loginErrorMsg");
                System.out.println("error is "+error);
                if(error != null){
            %>
            <div class="layui-form-item">
                <span style="color: red;"><%=error%></span>
            </div>
            <%
                }
            %>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit="" lay-filter="LAY-user-login-submit">登 入</button>
            </div>
        </form>
    </div>

</div>
<script type="text/javascript" src="${ctx}/static/agent-ui/plugins/layui/layui.js"></script>
<script>
layui.use(['form'],function () {

})
</script>
</body>
</html>
