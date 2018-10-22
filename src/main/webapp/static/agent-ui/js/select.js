/**
 * 用于加载select初始选项的js
 * Created by lixia on 2017/6/14.
 */
layui.define(function(exports){
    "use strict";
    var $ = layui.jquery;

    var select = {
        format:function(){
            $("form").find("select").each(function(){
                var url = $(this).attr("data-url");
                var select = $(this);
                if(url){
                    $.ajax({
                        url: url,
                        type: "post",
                        dataType: "json",
                        data: {
                            name: $(this).attr("name") || "",
                            value: $(this).attr("value") || "",
                            alias: $(this).attr("data-alias") || "",
                            tenantId: $(this).attr("data-tenant") || "",
                            className: $(this).attr("data-class") || ""
                        },
                        success:function(data){
                            $(data).each(function(i, d){
                                var opt = $("<option />").val(d.value).append(d.text);
                                if(d.selected){
                                    opt.attr("selected", true);
                                }
                                opt.appendTo(select);
                            });
                            var form = layui.form();
                            form.render();
                        }
                    });
                }
            });
        }
    }

    exports("select", select);
});
