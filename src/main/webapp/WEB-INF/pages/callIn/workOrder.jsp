<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../common.jsp" %>
<html>
<head>
    <title>工单明细</title>
    <link href="${ctx}/static/agent-ui/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/easyui/themes/gray/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/easyui/themes/gray/easyui.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/easyui/themes/icon.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/color.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/agent-ui/css/buttons.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/player.css"/>
    <link href="http://cdn.bootcss.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet"/>
    <script>
        var ctx = "${ctx}"
    </script>
</head>
<body>
<div id="receivedListHeader" class="datagrilBtn" style="height: auto">
    <form id="search_form" class="layui-form">
        <div class="layui-form-item">
            <div class="layui-inline">
                <input id="ringTime" name="ringTime" class="layui-input" placeholder="请选择工单时间范围"
                       style="width: 240px">
            </div>
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input name="callerNumber" class="layui-input" lay-verify="phone|number" placeholder="请输入主叫号码">
                </div>
                <div class="layui-input-inline">
                    <select name="status" lay-verify="required">
                        <option value="">请选择工单状态</option>
                        <option value="0">创建</option>
                        <option value="1">下发</option>
                        <option value="2">维修中</option>
                        <option value="3">回单</option>
                        <option value="4">关闭</option>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <div id="search_submit" class="layui-btn layui-btn-normal">查询</div>
            </div>|
            <div class="layui-inline">
                <div id="addData" class="layui-btn layui-btn-normal">新增工单</div>
            </div>
        </div>
    </form>
</div>
<div class="layui-row" id="test" style="display: none;">
    <form id = "add_form" class="layui-form layui-form-pane" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">主叫号码</label>
            <div class="layui-input-inline">
                <input  id="callerNumber"  name="callerNumber" lay-verify="required" placeholder="请输入"  class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">编号</label>
            <div class="layui-input-inline">
                <input id="serialNo" name="serialNo" lay-verify="required" placeholder="请输入"  class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">问题</label>
            <div class="layui-input-block">
                <input  id = "problem" name="problem" lay-verify="required" placeholder="请输入"  class="layui-input">
            </div>
        </div>

        <%--<div class="layui-form-item">
            <div class="layui-input-block">
                <button id = "insert" class="layui-btn" lay-submit="" lay-filter="demo1">提交</button>
                <button id = "insertProcess" class="layui-btn" lay-submit="" lay-filter="demo1">提交并下发</button>
                <button class="layui-btn" lay-submit="" lay-filter="demo1">关闭</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>--%>
    </form>
</div>
<div id="list" class="easyui-datagrid"></div>

<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/agent-ui/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/static/agent/callIn/workOrder.js"></script>
<script type="text/javascript" src="${ctx}/static/audiojs/audio.min.js"></script>
</body>
</html>
