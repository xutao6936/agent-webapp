<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<link href="${ctx}/static/agent-ui/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/static/easyui/themes/gray/base.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/static/easyui/themes/gray/easyui.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/static/easyui/themes/icon.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/color.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/agent-ui/css/buttons.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/player.css"/>
<link href="http://cdn.bootcss.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/agent-ui/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/static/agent/received.js"></script>
<script type="text/javascript" src="${ctx}/static/audiojs/audio.min.js"></script>
<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">呼叫中心</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <!--<ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="">控制台</a></li>
            <li class="layui-nav-item"><a href="">商品管理</a></li>
            <li class="layui-nav-item"><a href="">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="">邮件管理</a></dd>
                    <dd><a href="">消息管理</a></dd>
                    <dd><a href="">授权管理</a></dd>
                </dl>
            </li>
        </ul>-->
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img"/>
                    <shiro:principal property="userName"/>
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="${ctx}/logout">安全退出</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;">呼入模块</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a data-url="${ctx}/workOrder" data-id="11" data-title="工单" href="#" class="site-demo-active" data-type="tabAdd">工单</a>
                        </dd>
                        <dd><a href="javascript:;">列表二</a></dd>
                        <dd><a href="javascript:;">列表三</a></dd>
                        <dd><a href="">超链接</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">呼出模块</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">列表一</a></dd>
                        <dd><a href="javascript:;">列表二</a></dd>
                        <dd><a href="">超链接</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item"><a href="">云市场</a></li>
                <li class="layui-nav-item"><a href="">发布商品</a></li>
            </ul>


        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/js/menu.js"></script>
</body>
</html>
