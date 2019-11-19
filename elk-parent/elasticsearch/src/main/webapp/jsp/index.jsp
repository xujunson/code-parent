<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>主页</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="shortcut icon" href="${staticPath }/static/img/logo2.jpg" />
    <%@ include file="/commons/global.jsp" %>
    <%@ include file="/commons/basejs.jsp" %>
<style type="text/css">
#logo_text{
      font-size: 40px;
      text-shadow: 5px 5px 5px black, 0px 0px 2px black;
      color: white;
}

</style>
<script type="text/javascript">
    var index_tabs;
    var indexTabsMenu;
    var indexMenuZTree;
    var homeSelectCount=0;
    $(function() {
        $('#index_layout').layout({fit : true});

        //加载主页提示
        // iframeLoad('homePage');

        index_tabs = $('#index_tabs').tabs({
            fit : true,
            border : false,
            onContextMenu : function(e, title) {
                e.preventDefault();
                indexTabsMenu.menu('show', {
                    left : e.pageX,
                    top : e.pageY
                }).data('tabTitle', title);
            },
             onSelect:function(title){
            	if(title=='首页'){//刷新首页
                    if(homeSelectCount==0){
                        homeSelectCount++;
                    }else{
                        document.getElementById('homePage').contentWindow.location.reload(true);
                        homeSelectCount++;
                    }
            	}
            }, 
            tools : [{
                iconCls : 'fi-home',
                handler : function() {
                    index_tabs.tabs('select', 0);
                    refreshTab();
                }
            }, {
                iconCls : 'fi-loop',
                handler : function() {
                    refreshTab();
                }
            }, /* {
                iconCls : 'fi-x',
                handler : function() {
                    var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                    var tab = index_tabs.tabs('getTab', index);
                    if (tab.panel('options').closable) {
                        index_tabs.tabs('close', index);
                    }
                }
            }  */
            {
            	iconCls : 'fi-x',
                handler:function(){
                	var allTabs = index_tabs.tabs('tabs');
                    var closeTabsTitle = [];
                    $.each(allTabs, function() {
                        var opt = $(this).panel('options');
                        console.log(opt)
                     if(opt.closable) {
                            closeTabsTitle.push(opt.title);
                        }
                    });
                    for ( var i = 0; i < closeTabsTitle.length; i++) {
                        index_tabs.tabs('close', closeTabsTitle[i]);
                    }
                 }
              }

            ]
        });
        // 选项卡菜单
        indexTabsMenu = $('#tabsMenu').menu({
            onClick : function(item) {
                var curTabTitle = $(this).data('tabTitle');
                var type = $(item.target).attr('type');
                if (type === 'refresh') {
                    refreshTab();
                    return;
                }
                if (type === 'close') {
                    var t = index_tabs.tabs('getTab', curTabTitle);
                    if (t.panel('options').closable) {
                        index_tabs.tabs('close', curTabTitle);
                    }
                    return;
                }
                var allTabs = index_tabs.tabs('tabs');
                var closeTabsTitle = [];
                $.each(allTabs, function() {
                    var opt = $(this).panel('options');
                    if (opt.closable && opt.title != curTabTitle
                            && type === 'closeOther') {
                        closeTabsTitle.push(opt.title);
                    } else if (opt.closable && type === 'closeAll') {
                        closeTabsTitle.push(opt.title);
                    }
                });
                for ( var i = 0; i < closeTabsTitle.length; i++) {
                    index_tabs.tabs('close', closeTabsTitle[i]);
                }
            }
        });

        indexMenuZTree = $.fn.zTree.init($("#layout_west_tree"), {
        	view: {
                dblClickExpand: false
            },
            data: {
                key: {
                    name: "text"
                },
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pid",
                    rootPId: 1
                }
            },
            async: {
                enable: true,
                url:"${path}/resource/tree",
                dataFilter: function (treeId, parentNode, responseData) {
                    if (responseData) {
                        for (var i =0; i < responseData.length; i++) {
                            var node = responseData[i];
                            if (node.state == "open") {
                                node.open = true;
                            }
                        }
                    }
                    return responseData;
                }
            },
            callback: {
                onClick: function(event, treeId, node) {
                	var zTree = $.fn.zTree.getZTreeObj(treeId);
                    zTree.expandNode(node);
                    var opts = {
                        title : node.text,
                        border : false,
                        closable : true,
                        fit : true,
                        iconCls : node.iconCls
                    };
                    var url = node.attributes;
                    if (url && url.indexOf("http") == -1) {
                        url = '${path }' + url;
                    }
                    if (node.openMode == 'iframe') {
                         opts.content = '<iframe  src="' + url + '" class="frameLoading" frameborder="0" style="border:0;width:100%;height:99%;overflow:hidden" id="'+node.id+'" ></iframe>';
                         addTab(opts,node.id);

                    } else if (node.openMode == 'newWin') {
                        window.open(url);
                    }else if (url) {
                    	if(url.trim()!=''){
                    		if(node.openMode == 'newWin'){
                    			window.open(url);
                    		}else{
                    			opts.href = url;
                            	addTab(opts);
                    		}
                    	}
                    }
                }
            }
        });
    });

    /*iframe加载提示  */
    function iframeLoad(id){
        var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    	 var iframe=document.getElementById(id);
         progressLoad2();
    	 if (isIE) {
		 iframe.onreadystatechange = function(){
		    if(iframe.readyState == "loaded" || iframe.readyState == "complete"){
               parent.progressClose2();
		    }
		  }
		} else {
		  iframe.onload = function(){
              parent.progressClose2();
		  }
		}
    }
    function addTab(opts) {
        var t = $('#index_tabs');
        if (t.tabs('exists', opts.title)) {
            t.tabs('select', opts.title);
        } else {
            t.tabs('add', opts);
        }
    }
    function addTab(opts,id) {
        var t = $('#index_tabs');
        if (t.tabs('exists', opts.title)) {
            t.tabs('select', opts.title);
        } else {
            t.tabs('add', opts);
            if(!isNull(id)) {
                iframeLoad(id);
            }
        }
    }

    function refreshTab() {
        var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
        var tab = index_tabs.tabs('getTab', index);
        var options = tab.panel('options');
        if(!isNull(options.href)){
	        if (options.content) {
	            index_tabs.tabs('update', {
	                tab: tab,
	                options: {
	                    content: options.content
	                }
	            });
	        } else {
	            tab.panel('refresh', options.href);
	        }
        }else{
            var id=$(options.content).attr('id');
        	 //刷新首页
        	 if(options.title=='首页'){
        		 document.getElementById('homePage').contentWindow.location.reload(true);
        	 }else{
        	     //刷新iframe
                 document.getElementById(id).contentWindow.location.reload(true);
                 iframeLoad(id);
             }
        }
    }

    function logout(){
        $.messager.confirm('提示','确定要退出?',function(r){
            if (r){
                progressLoad();
                $.post('${path }/logout', function(result) {
                    if(result.success){
                        progressClose();
                        window.location.href='${path }/jsp/login.jsp';
                    }
                }, 'json');
            }
        });
    }

    function editUserPwd() {
        parent.$.modalDialog({
            title : '修改密码',
            width : 300,
            height : 250,
            href : '${path }/user/editPwdPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    var f = parent.$.modalDialog.handler.find('#userEditPassForm');
                    f.submit();
                }
            } ]
        });
    }

    function msgRss(){
    	  var dialog=$('<div>').dialog({
              title : '发送消息',
              width : '60%',
              height : '80%',
              modal:true,
              onClose : function() {
                  $(this).dialog('destroy');
              },
              href : '${path}/socketIoServer/manager',
              buttons : [ {
                  text : '关闭',
                  handler : function() {
                      dialog.dialog('close');
                  }
              },{
            	  text : '发送',
                  handler : function() {
                     // dialog.dialog('close');
                     var html='<div style="float:left">'
                      	+'777'
                      	+'</div>'
                      	+'<br />'
                      	+'<br style="clear:both;"/>'
                      	+'<div style="float:right">'
                      	+'666'
                      	+'</div>'
                      	+'<br />'
                      	+'<br style="clear:both;"/>';
                     $('#socket-content').append(html)
                  }
              }]
    	  })
    }
    
    /**
    * 显示个人信息
    */
    function showMyprofile(){
    	 var opts = {
             title : '个人信息',
             border : false,
             closable : true,
             fit : true,
             iconCls : 'fi-torso',
             content:'<iframe src="${ctxPath}/user/toUserInfo?id=<shiro:principal property="id"/>" frameborder="0" style="border:0;width:100%;height:99.5%;" id="user_userinfo"></iframe>'
          };
    	 addTab(opts,'user_userinfo');
    }
</script>
</head>
<body>
    <div id="loading" style="position: fixed;top: -50%;left: -50%;width: 200%;height: 200%;background: #fff;z-index: 100;overflow: hidden;">
        <img src="${staticPath }/static/style/images/ajax-loader.gif" style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;"/>
    </div>
    <div id="index_layout">
        <div data-options="region:'north',border:false" style="overflow: hidden;background: #E0ECFF">
            <div>
               <div style="float:left;">
            	<img src="${staticPath}/static/img/logo2.jpg" alt="" width="60" height="60" style="margin-left:10px;"/>
            	<span style="position:relative;top:-15px;"><b style="font-size:20px;" id="logo_text">知识库</b></span>
            	</div>
            	 <span style="float: right; padding-right: 10px; margin-top: 10px; color: #333">
            	  <a href="javascript:void(0)" onclick="editUserPwd()" class="easyui-linkbutton" plain="true" icon="fi-page-multiple" >使用说明</a>
            	 </span>
                <span style="clear:both;float: right; padding-right: 10px; margin-top: -20px; color: #333">

                    <!-- <i class="fi-torso" style="position: relative;top:5px"></i> -->
                    <a  href="javascript:void(0)" onclick="showMyprofile()" class="easyui-linkbutton" plain="true" icon="fi-torso"><shiro:principal></shiro:principal></a>
                    <a href="javascript:void(0)" onclick="editUserPwd()" class="easyui-linkbutton" plain="true" icon="fi-unlock" >修改密码</a>
                    <a href="javascript:void(0)" onclick="logout()" class="easyui-linkbutton" plain="true" icon="fi-x">安全退出</a>
                </span>
               <!--  <div style="margin-left:40%;position: relative;top:45px;"><b style="font-size:12px;">当前项目：滇池明珠广场二期</b></div>  -->
                <span class=""></span>
            </div>
        </div>
        <div data-options="region:'west',split:true" title="菜单" style="width: 160px; overflow: hidden;overflow-y:auto; padding:0px">
            <div class="well well-small" style="padding: 5px 5px 5px 5px;">
                <ul id="layout_west_tree" class="ztree"></ul>
            </div>
        </div>
        
        <div data-options="region:'center'" style="overflow: hidden;" title="" id="index_title">
            <div id="index_tabs" style="overflow: hidden;">
                <div title="首页" data-options="iconCls:'fi-home',border:false" style="overflow: hidden;">
                    <script src='#'></script>
                    <style>
                        .pro_name a{color: #4183c4;}
                        .osc_git_title{background-color: #d8e5f1;}
                        .osc_git_box{background-color: #fafafa;}
                        .osc_git_box{border-color: #ddd;}
                        .osc_git_info{color: #666;}
                        .osc_git_main a{color: #4183c4;}
                    </style>
                   <iframe src="${ctxPath}/index/home" frameborder="0" style="border:0;width:100%;height:99.5%;" id="homePage"></iframe>
                </div>
            </div>
        </div>
        <div data-options="region:'south',border:false" style="height: 30px;line-height:30px; overflow: hidden;text-align: center;background-color: #eee" >Copyright © 2018 power by <a href="#" target="_blank"></a>
           <a  href="javascript:void(0);" style="float:right;margin-right:5px;margin-top:3px;" class="easyui-linkbutton" data-options="iconCls:'fi-rss icon-blue'" onclick="msgRss()"></a>
        </div>
    </div>
    <div id="tabsMenu">
        <div data-options="iconCls:'fi-loop'" type="refresh" style="font-size: 12px;">刷新</div>
        <div class="menu-sep"></div>
        <div data-options="iconCls:''" type="close" style="font-size: 12px;">关闭</div>
        <div data-options="iconCls:''" type="closeOther">关闭其他</div>
        <div data-options="iconCls:'fi-x'" type="closeAll">关闭所有</div>
    </div>

    <!--[if lte IE 7]>
    <div id="ie6-warning"><p>您正在使用 低版本浏览器，在本页面可能会导致部分功能无法使用。建议您升级到 <a href="http://www.microsoft.com/china/windows/internet-explorer/" target="_blank">Internet Explorer 8</a> 或以下浏览器：
    <a href="http://www.mozillaonline.com/" target="_blank">Firefox</a> / <a href="http://www.google.com/chrome/?hl=zh-CN" target="_blank">Chrome</a> / <a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> / <a href="http://www.operachina.com/" target="_blank">Opera</a></p></div>
    <![endif]-->
    <style type="text/css">
        /*ie6提示*/
        #ie6-warning{width:100%;position:absolute;top:0;left:0;background:#fae692;padding:5px 0;font-size:12px}
        #ie6-warning p{width:960px;margin:0 auto;}
    </style>
</body>
</html>