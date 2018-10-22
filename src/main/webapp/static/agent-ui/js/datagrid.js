/**
 *
 * Created by lixia on 2017/6/15.
 */
layui.define(function(exports){
    "use strict";
    var $ = layui.jquery,
        validator = layui.validator,
        laypage = layui.laypage,
        layer = parent.layer === undefined ? layui.layer : parent.layer;
    var id,$dg,options,columns,$dgdata;
    var params = {};

    var datagrid = {
        loadGridHeader:function(tId){
            id = tId;
            $dg = $(id + " .site-table");
            options = eval("({" + $dg.attr("data-options") + "})") || {};
            columns = options.columns || [];
            if(columns) {
                loadHeader();
            }

            //处理初始化数据
            if(options.url){
                loadGridData(id);
            }
        },
        loadGridData:function(param){
            loadGridData(param);
        }
    }

    /**
     * 加载数据
     * @param param
     */
    function loadGridData(param){
        if(param){
            params = $.extend({}, params, param);
        }
        $.ajax({
            type: "POST",
            url: options.url,
            dataType: "json",
            data: $.extend({}, params, {
                pageNum: $(id + " #pageNum").html(),
                pageSize: $(id + " #pageSize").val()
            }),
            success:function(data){
                $(id + " #pages").html(data.pages);
                $(id + " #total").html(data.total);
                //加载数据
                loadData(data);
                //处理分页
                var pagination =  options.pagination || false;
                if(pagination){
                    loadJumpPagination(param);
                }
            }
        })
    }

    /**
     * 加载表头
     */
    function loadHeader(){
        var $tr = $("<tr/>").attr("data-type", "header").css("background-color", "#D1D1D1");
        if(options.rownumbers || false){
            $("<th/>").width(5).attr("field", "index").appendTo($tr);
        }
        $(columns).each(function (i, column) {
            var $th = $("<th/>").attr("field", column.field);
            if (column.checkbox) {
                $th.width(10).append($("<input/>").attr("type", "checkbox")
                    .on('ifChanged', function (event) {
                        $(id + " .site-table tr td").find("input")
                            .iCheck(event.currentTarget.checked ? "check" : "uncheck");
                    })).iCheck({
                    checkboxClass: "icheckbox_flat-blue"
                });
            } else if (column.hidden) {
                $th.hide().append(column.title);
            } else {
                $th.append(column.title);
            }
            $th.appendTo($tr);
        });
        $tr.appendTo($dg);

        var pagination =  options.pagination || false;
        if(pagination){
            loadInfoPagination();
        }
    }

    /**
     * 加载后台数据
     * @param dataJson
     */
    function loadData(data){
        if($dgdata){
            $dgdata.empty();
        } else {
            $dgdata = $("<table>").attr("class", "site-table table-hover").css("margin-top", "-21px").appendTo($("<div/>").css("overflow", "scroll")).appendTo($dg.parent());
        }
        $(data.result).each(function (n, Row) {
            if(columns) {
                var $tr = $("<tr/>");
                $tr.attr("data-type", "data").on("click", function() {
                        $(this).children("td").eq(0).find("input")
                        .on("ifChecked", function () {
                            $(this).css("background-color", "#EEEEEE");
                        }).on("ifUnchecked", function () {
                            $(this).removeAttr("style");
                        }).iCheck("toggle");
                    });
                if(options.rownumbers || false){
                    $("<td/>").width(5).attr("field", "index").html((n+1)).appendTo($tr);
                    $dg.find("tr").eq(0).find("th[field='index']").width($dgdata.find("tr").eq(0).find("td[field='index']").width());
                }
                $(columns).each(function (i, column) {
                    var $td = $("<td/>").attr("field", column.field);
                    var tdHtml = validator.NullToString(Row[column.field]);
                    if (column.checkbox) {
                        $td.width(10).append($("<input/>").attr("type", "checkbox").val(tdHtml)
                            .on("ifChecked", function() {
                                $(this).parents("tr").css("background-color", "#EEEEEE");
                            }).on("ifUnchecked", function() {
                                $(this).parents("tr").removeAttr("style");
                            })).iCheck({
                                checkboxClass: "icheckbox_flat-blue"
                            });
                        tdHtml = "";
                    } else if (column.hidden) {
                        $td.hide();
                    } else if (column.formatter) {
                        tdHtml = column.formatter(tdHtml);
                    }
                    $td.append(tdHtml).appendTo($tr);
                });

                $tr.appendTo($dgdata);
            }
        });

        //重新计算表头的宽度
        $(columns).each(function(i, column){
            var width = $dgdata.find("tr").eq(0).find("td[field='" + column.field + "']").width();
            $dg.find("tr").eq(0).find("th[field='" + column.field + "']").width(width);
        });
    }

    /**
     * 加载提示的分页信息
     */
    function loadInfoPagination(){
        var $rowsView = $("<div/>"), $view = $("<div/>"), $refresh = $("<div/>"), $pagination = $("<div/>");
        $rowsView.attr("id", "rowsView").css("float", "left")
            .css("padding-left", "20px").css("padding-top", "10px");
        var $pageSize = $("<select/>").attr("id", "pageSize").attr("name", "pageSize")
            .append($("<option/>").val(15).html(15))
            .append($("<option/>").val(20).html(20))
            .append($("<option/>").val(30).html(30))
            .append($("<option/>").val(50).html(50))
            .append($("<option/>").val(100).html(100));
        $rowsView.append("每页").append($pageSize).append("条数据");

        $view.attr("id", "view").css("float", "left").css("padding-top", "10px")
            .append("，当前第")
            .append($("<span>").attr("id", "pageNum").html(1))
            .append("/")
            .append($("<span>").attr("id", "pages").html(0))
            .append("页，共")
            .append($("<span>").attr("id", "total").html(0))
            .append("条数据");

        $refresh.attr("class", "layui-btn-group").css("float", "left").css("padding-top", "10px").css("margin-left", "10px")
            .append($("<button/>").attr("class", "layui-btn layui-btn-mini  layui-btn-normal")
                .attr("id", "refresh")
                .attr("title", "刷新").css("margin", "0px")
                .append($("<i/>").attr("class", "layui-icon").html("&#x1002;"))
            );

        $pagination.attr("class", "page").attr("id", "pagination");

        $dg.parent().append($("<div/>").attr("class", "admin-table-page").append($rowsView).append($view).append($refresh).append($pagination));

        //更新按钮点击事件绑定
        $(id + " #refresh").on("click", function() {
            loadGridData();
        });

        //每页显示条目更改事件
        $(id + " #pageSize").on("change", function() {
            loadGridData();
        });
    }

    /**
     * 加载跳转的分页信息
     * @param param
     */
    function loadJumpPagination(param){
        laypage({
            cont: "pagination",//容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
            pages: $(id + " #pages").html(),//总页数
            groups: 10, //连续显示分页数
            skip: false, //是否开启跳页
            skin: '#1e9fff',
            curr: $(id + " #pageNum").html() || 1, //当前页,
            jump: function (obj, first) {//触发分页后的回调
                if (!first) {//点击跳页触发函数自身，并传递当前页：obj.curr
                    $(id + " #pageNum").html(obj.curr);
                    loadGridData(param);
                }
            }
        });
        layer.closeAll('loading');
    }

    exports("datagrid", datagrid);
});