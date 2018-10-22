<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>呼叫中心</title>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">

    <!--头-->
    <%@ include file="header.jsp"%>
    <div class="layui-tab" lay-filter="tab_content" lay-allowclose="true" style="margin-left: 200px;">
        <ul class="layui-tab-title">
        </ul>
        <ul class="rightmenu" style="display: none;position: absolute;">
            <li data-type="closethis">关闭当前</li>
            <li data-type="closeall">关闭所有</li>
        </ul>
        <div class="layui-tab-content">
        </div>
    </div>
    <!--<div class="layui-body" style="margin: 1%">
        <table id="demo" lay-filter="test"></table>
    </div>-->
    <!--底部-->
    <%@ include file="footer.jsp"%>
</div>
</body>
</html>
