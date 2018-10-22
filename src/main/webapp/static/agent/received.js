var receivedListColumns = [[
    {field: "id", checkbox: true},
    {field: "callernumber", title: "主叫号码", align: "center", width: 80},
    {field: "accesscode", title: "编号", align: "center", width: 80},
    {field: "username", title: "处理人", align: "center", width: 80},
    {field: "checktime", title: "处理时间", align: "center", width: 80},
    {field: "starttime", title: "开始时间", align: "center", width: 80},
    {field: "createdat", title: "结束时间", align: "center", width: 80},
    {
        field: "filename", title: "试听", align: "center", width: 295, formatter: function (value) {
        // return '<audio preload="auto" src="http://kolber.github.io/audiojs/demos/mp3/juicy.mp3"></audio>';
        return '<audio preload="auto" src="' + value + '"></audio>';
    }
    },
    {
        field: "filetype", title: "下载", align: "center", width: 80, formatter: function (value, rowData) {
        return '<a class="layui-btn layui-btn-xs" href="./received/download?path=' + rowData.filename + '" style="text-decoration: none">点击下载</a>'
    }
    },
    {
        field: "handle", title: "处理", align: "center", width: 80, formatter: function (value, rowData) {
        var flag = rowData.state;
        if (flag=='0') {
            return '<a class="layui-btn layui-btn-xs" onclick="handleRecord('+rowData.id+')" style="text-decoration: none">点击处理</a>'
        }
    }
    },
    {
        field: "state", title: "是否处理", align: "center", width: 80, formatter: function (value) {
        switch (value) {
            case '0':
                return '<span class="layui-badge layui-bg-orange">否</span>';
            case '1':
                return '<span class="layui-badge layui-bg-green">是</span>';
            default:
                return '';
        }
    }
    }
]];

layui.config({
    base: "../static/agent-ui/js/"
});

$.fn.toJSON = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        var name = this.name;
        var value = this.value;
        var paths = this.name.split(".");
        var len = paths.length;
        var obj = o;
        $.each(paths, function (i, e) {
            if (i == len - 1) {
                if (obj[e]) {
                    if (!obj[e].push) {
                        obj[e] = [obj[e]];
                    }
                    obj[e].push(value || '');
                } else {
                    obj[e] = value || '';
                }
            } else {
                if (!obj[e]) {
                    obj[e] = {};
                }
            }
            obj = o[e];
        });
    });
    return o;
};

layui.use(["laypage", "layer", "validator", "tools", "form", "select", "laydate"], function () {
    var date = layui.laydate;
    date.render({
        elem: '#ringTime',
        type: 'datetime',
        range: true
    });

    $(function () {
        $('#receivedList').datagrid({
            columns: receivedListColumns,
            fitColumns: true,
            fit: true,
            singleSelect: true,
            pagination: true,
            pageSize: 30,
            pageList: [30, 50, 100, 1000],
            toolbar: '#receivedListHeader',
            method: 'get',
            url: './findPage',
            onLoadSuccess: function () {
                audiojs.events.ready(function () {
                    audiojs.createAll();
                });
            }
        });
    });

    $('#recevied_submit').on('click', function () {
        $('#receivedList').datagrid('load', $('#recevied_form').toJSON());
    });

    $("#export").on("click", function(){
        var queryParams = $("#recevied_form").toJSON();
        var form = $("<form>");
        form.attr("style", "display:none")
            .attr("method", "get")
            .attr("action", "./export")
            .appendTo($("body"));
        for(var item in queryParams){
            $("<input/>").attr("type", "hidden").attr("name", item).val(queryParams[item]).appendTo(form);
        }

        form.submit().remove();
    });
});

function handleRecord(id){
    $.ajax({
        url:'./handle',
        data:{
            id:id
        },
        success:function () {
            $('#receivedList').datagrid('reload');
        }
    });
}



