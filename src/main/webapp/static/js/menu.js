$(function(){
    layui.use('element', function(){
        var element = layui.element;
        var $ = layui.jquery;
        var activeTab = {
            tabAdd : function(url,id,name){ //新增tab项
                element.tabAdd('tab_content', {
                    title: name
                    ,content: '<iframe  src = '+url +' ></iframe>' //支持传入html
                    ,id: id
                });
                CustomRightClick(id); //给tab绑定右击事件
                FrameWH();//计算框架高度
            },
            tabChange: function(id){ //选中tab项
                element.tabChange('tab_content', id);
            },
            ishasTab : function(){ //判断tab项中是否包含
                var _this = this;
                var dataId,isflag;
                var arrays = $('.layui-tab-title li');
                $.each(arrays,function(idx,ele){
                    dataId = $(ele).attr('lay-id');
                    if(dataId === _this.tabId){
                        isflag = true
                        return false
                    }else{
                        isflag = false
                    }
                })
                return isflag
            },
            init : function(tabtit,taburl,tabid){
                var _this = this;
                _this.tabUrl = taburl;
                _this.tabId = tabid;
                _this.tabTit = tabtit;
                if(taburl){
                    if(!_this.ishasTab()){
                        _this.addTab();
                    }
                    _this.changeTab();
                }else{
                    return false
                }
            },
            tabDelete: function (id) {
                element.tabDelete("tab_content", id);//删除
            }
            , tabDeleteAll: function (ids) {//删除所有
                $.each(ids, function (i,item) {
                    element.tabDelete("tab_content", item); //ids是一个数组，里面存放了多个id，调用tabDelete方法分别删除
                })
            }
        }

        //当点击有site-demo-active属性的标签时，即左侧菜单栏中内容 ，触发点击事件
        $('.site-demo-active').on('click', function() {
            var dataid = $(this);

            //这时会判断右侧.layui-tab-title属性下的有lay-id属性的li的数目，即已经打开的tab项数目
            if ($(".layui-tab-title li[lay-id]").length <= 0) {
                //如果比零小，则直接打开新的tab项
                activeTab.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"),dataid.attr("data-title"));
            } else {
                //否则判断该tab项是否以及存在

                var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
                $.each($(".layui-tab-title li[lay-id]"), function () {
                    //如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
                    if ($(this).attr("lay-id") == dataid.attr("data-id")) {
                        isData = true;
                    }
                })
                if (isData == false) {
                    //标志为false 新增一个tab项
                    activeTab.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"),dataid.attr("data-title"));
                }
            }
            //最后不管是否新增tab，最后都转到要打开的选项页面上
            activeTab.tabChange(dataid.attr("data-id"));
        });

        $(".rightmenu li").click(function () {

            //右键菜单中的选项被点击之后，判断type的类型，决定关闭所有还是关闭当前。
            if ($(this).attr("data-type") == "closethis") {
                //如果关闭当前，即根据显示右键菜单时所绑定的id，执行tabDelete
                activeTab.tabDelete($(this).attr("data-id"))
            } else if ($(this).attr("data-type") == "closeall") {
                var tabtitle = $(".layui-tab-title li");
                var ids = new Array();
                $.each(tabtitle, function (i) {
                    ids[i] = $(this).attr("lay-id");
                })
                //如果关闭所有 ，即将所有的lay-id放进数组，执行tabDeleteAll
                activeTab.tabDeleteAll(ids);
            }

            $('.rightmenu').hide(); //最后再隐藏右键菜单
        })

        function CustomRightClick(id) {
            //取消右键  rightmenu属性开始是隐藏的 ，当右击的时候显示，左击的时候隐藏
            $('.layui-tab-title li').on('contextmenu', function () { return false; })
            $('.layui-tab-title,.layui-tab-title li').click(function () {
                $('.rightmenu').hide();
            });
            //桌面点击右击
            $('.layui-tab-title li').on('contextmenu', function (e) {
                var popupmenu = $(".rightmenu");
                popupmenu.find("li").attr("data-id",id); //在右键菜单中的标签绑定id属性

                //判断右侧菜单的位置
                l = ($(document).width() - e.clientX) < popupmenu.width() ? (e.clientX - popupmenu.width()) : e.clientX;
                t = ($(document).height() - e.clientY) < popupmenu.height() ? (e.clientY - popupmenu.height()) : e.clientY;
                popupmenu.css({ left: l, top: t }).show(); //进行绝对定位
                //alert("右键菜单")
                return false;
            });
        }


        function FrameWH() {
            var h = $(window).height() -41- 10 - 60 -10-44 -10;
            $("iframe").css("height",h+"px").css("width","100%");

        }

        $(window).resize(function () {
            FrameWH();
        })
    });




});