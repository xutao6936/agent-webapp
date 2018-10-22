<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../common.jsp" %>
<html>
<head>
    <title>坐席通话明细表</title>
    <link href="${ctx}/static/agent-ui/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/easyui/themes/gray/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/easyui/themes/gray/easyui.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/easyui/themes/icon.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/color.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/agent-ui/css/buttons.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/player.css"/>
    <link href="http://cdn.bootcss.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet"/>
</head>
<body>
<div id="receivedListHeader" class="datagrilBtn" style="height: auto">
    <form id="recevied_form" class="layui-form">
        <div class="layui-form-item">
            <div class="layui-inline">
                <div id="recevied_submit" class="layui-btn layui-btn-normal">查询</div>
            </div>
            <div class="layui-inline">
                <input id="ringTime" name="ringTime" class="layui-input" placeholder="请选择来电时间范围"
                       style="width: 240px">
            </div>
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input name="callernumber" class="layui-input" lay-verify="phone|number" placeholder="请输入来电号码">
                </div>
                <div class="layui-input-inline">
                    <select name="state" lay-verify="required">
                        <option value=""></option>
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <div id="export" class="layui-btn layui-btn-normal">导出数据</div>
            </div>
        </div>
    </form>
</div>
<div id="receivedList" class="easyui-datagrid"></div>

<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/agent-ui/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/static/agent/received.js"></script>
<script type="text/javascript" src="${ctx}/static/audiojs/audio.min.js"></script>
</body>
</html>
