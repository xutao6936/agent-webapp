/** tools.js By mavisbao  */
layui.define(function(exports) {
	"use strict";
	var $ = layui.jquery;
	var tools = {
		//表单转换成json
		formToJson:function(input){
			var arr = input.serializeArray();
			var jsonStr = "";
			jsonStr += '{';
			for (var i = 0; i < arr.length; i++) {
				jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",'
			}
			jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
			jsonStr += '}'
			var json = JSON.parse(jsonStr);
			return json;
		},
		getSelectIds:function(input){
			var ids = "";
			var $input = $('.site-table tbody tr td').find('input');
			if(input){
				$input = input;
			}
			$input.each(function(){
				if($(this).is(':checked')){
					if(ids.length==0){
						ids = ids +  $(this).val();
					}else{
						ids = ids +","+ $(this).val();
					}
				}
			});
			if(ids){
				return ids;
			}else{
				return "";
				//layer.msg('您未选中任何数据!');
			}
		},
        getSelectId:function(input){
            var ids = "";
            var $input = $('.site-table tbody tr td').find('input');
            if(input){
                $input = input;
            }
            var i = 0;
            $input.each(function(){
                if($(this).is(':checked')){
                    if(ids.length==0){
                        ids = ids +  $(this).val();
                    }else{
                        ids = ids +","+ $(this).val();
                    }
                    i++;
                }
            });
            if(i==1){
                if(ids){
                    return ids;
                }else{
                    return "";
                    //layer.msg('您未选中任何数据!');
                }
            }else{
                layer.msg('您只能选择一条记录!');
            }
        },
        getSelectColumn:function(field){
            var column = [];
            var $input = $('.site-table tbody tr td').find('input');
            var i = 0;
            $input.each(function(){
                if($(this).is(':checked')){
					if(field){
						$(this).parent().parent().parent().find("td[field='" + field + "']").each(function () {
							column.push($(this).text());
						});
					} else {
						column.push($(this).val());
						$(this).parent().parent().parent().find("td").each(function () {
							column.push($(this).text());
						});
					}
                    i++;
                }
            });
            if(i==1){
                if(column){
                    return column;
                }else{
                    return "";
                    //layer.msg('您未选中任何数据!');
                }
            }else{
                layer.msg('您只能选择一条记录!');
            }
        },
		reviewImage:function(obj, imgPreviewId, divPreviewId){
			var browserVersion = window.navigator.userAgent.toUpperCase();
			if (browserVersion.indexOf("MSIE") > -1) {
				if (browserVersion.indexOf("MSIE 6.0") > -1) {//ie6
					document.getElementById(imgPreviewId).setAttribute("src", obj.value);
				} else {//ie[7-8]、ie9
					obj.select();
					var newPreview = document.getElementById(divPreviewId + "New");
					if (newPreview == null) {
						newPreview = document.createElement("div");
						newPreview.setAttribute("id", divPreviewId + "New");
//					newPreview.style.width = 160;
//					newPreview.style.height = 170;
						newPreview.style.border = "solid 1px #d2e2e2";
					}
					newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";
					var tempDivPreview = document.getElementById(divPreviewId);
					tempDivPreview.parentNode.insertBefore(newPreview, tempDivPreview);
					tempDivPreview.style.display = "none";
				}
			} else if (browserVersion.indexOf("FIREFOX") > -1) {//firefox
				var firefoxVersion = parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);
				if (firefoxVersion < 7) {//firefox7以下版本
					document.getElementById(imgPreviewId).setAttribute("src", obj.files[0].getAsDataURL());

				} else {//firefox7.0+
					document.getElementById(imgPreviewId).setAttribute("src", window.URL.createObjectURL(obj.files[0]));

				}
			} else if (obj.files) {
				//兼容chrome、火狐等，HTML5获取路径
				if (typeof FileReader !== "undefined") {
					var reader = new FileReader();
					reader.onload = function (e) {
						document.getElementById(imgPreviewId).setAttribute("src", e.target.result);
					}
					reader.readAsDataURL(obj.files[0]);
				} else if (browserVersion.indexOf("SAFARI") > -1) {
					alert("暂时不支持Safari浏览器!");
				}
			} else {
				document.getElementById(divPreviewId).setAttribute("src", obj.value);
			}
		},
		saveForm:function(data,url,layerp){
			$.ajax({
				type: "POST",
				url: url,
				data: data,
				async:false,
				dataType: "json",//返回数据类型格式json
				success: function(data){
					if(data == true) {
						layerp.msg('保存成功!', {icon: 6});
						layerp.closeAll("iframe");
					}else{
						layerp.msg('保存失败!', {icon: 5});
					}
				}
			});
		},
		//不关闭所有iframe的保存方法
		saveFormNoCloseIframe:function(data,url,layerp,index){
			$.ajax({
				type: "POST",
				url: url,
				data: data,
				async:false,
				dataType: "json",//返回数据类型格式json
				success: function(data){
					if(data == true) {
						layerp.msg('保存成功!', {icon: 6});
						layerp.close(index);//关闭当前窗口
					}else{
						layerp.msg('保存失败!', {icon: 5});
					}
				}
			});
		}

	};
	exports('tools', tools);
});