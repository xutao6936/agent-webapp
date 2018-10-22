var listColumns = [[
    {field: "id", checkbox: true},
    {field: "callerNumber", title: "主叫号码", align: "center", width: 80},
    {field: "serialNo", title: "编号", align: "center", width: 80},
    {field: "problem", title: "问题", align: "center", width: 120},
    {field: "status", title: "当前状态", align: "center", width: 80,formatter:function (value) {
        switch(value){
            case "0": return "创建";
            case "1": return "下发";
            case "2": return "维修中";
            case "3": return "回单";
            case "4": return "关闭";
            default: return "";
        }
    }},
    {field: "createTime", title: "创建时间", align: "center", width: 80},
    {field: "updateTime", title: "更新时间", align: "center", width: 80},
    {
        field: "handle", title: "处理", align: "center", width: 80, formatter: function (value, rowData) {
        var flag = rowData.status;
        if (flag=='0') {
            return '<a class="layui-btn layui-btn-xs" onclick="handleRecord('+rowData.id+','+flag+')" style="text-decoration: none">下发任务</a>'
        }else if (flag=='1') {
            return '<a class="layui-btn layui-btn-xs" onclick="handleRecord('+rowData.id+','+flag+')" style="text-decoration: none">维修</a>'
        }else if (flag=='2') {
            return '<a class="layui-btn layui-btn-xs" onclick="handleRecord('+rowData.id+','+flag+')" style="text-decoration: none">回单</a>'
        }else if (flag=='3') {
            return '<a class="layui-btn layui-btn-xs" onclick="handleRecord('+rowData.id+','+flag+')" style="text-decoration: none">关闭</a>'
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
    var layer=layui.layer;

    $(function () {
        $('#list').datagrid({
            columns: listColumns,
            fitColumns: true,
            fit: true,
            singleSelect: true,
            pagination: true,
            pageSize: 30,
            pageList: [30, 50, 100, 1000],
            toolbar: '#receivedListHeader',
            method: 'get',
            url: ctx +'/workOrder/list',
            onLoadSuccess: function () {
                audiojs.events.ready(function () {
                    audiojs.createAll();
                });
            }
        });
    });

    $('#search_submit').on('click', function () {
        $('#list').datagrid('load', $('#search_form').toJSON());
    });

    $("#addData").on("click", function(){
        /* 再弹出添加界面 */
        layer.open({
            type:1,
            title:"添加工单",
            skin:"myclass",
            area:["50%"],
            content:$("#test").html(),
            btn:['提交','提交并下单','关闭'],
            yes:function (index,layero) {
                add(index,'0',layero);
            },
            btn2:function (index,layero,layero) {
                add(index,'1');
            },
            btn3:function (index) {
                layer.close(index);
            }
        });

    });
});

function add(index,status,layero) {
    var callerNumber = $(layero).find('input[name=callerNumber]').val();
    var serialNo = $(layero).find('input[name=serialNo]').val();
    var problem = $(layero).find('input[name=problem]').val();
    $.ajax({
        url:ctx+'/workOrder/add',
        type : 'POST',
        data:
            {
            callerNumber:callerNumber,
            serialNo:serialNo,
            problem:problem,
            status:status
        },
        success:function () {
            layer.close(index);
            $('#list').datagrid('reload');
        }
    });
}
//  更新操作
function handleRecord(id,flag){
    $.ajax({
        url:ctx+'/workOrder/update',
        type : 'POST',
        data:{
            id:id,
            flag:flag
        },
        success:function () {
            $('#list').datagrid('reload');
        }
    });
}



