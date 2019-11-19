<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var resourceTree;
    jQuery(function($) {
    	var setting = {
                view: {
                    dblClickExpand: true,
                    showLine:true
                },
                check: {
                    enable: true,
                    chkStyle:"checkbox",
                    chkboxType: { "Y" : "", "N" : "" }
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
                }
            };
    	$.ajax({
    		 url : '${path }/resource/allTrees?roleId=${id}',
    		 dataType:'json',
    		 success:function(data){
    			 console.log(data)
    			 $.fn.zTree.init($("#resourceTree"), setting, data);
    			 resourceTree = $.fn.zTree.getZTreeObj("resourceTree");
    			 resourceTree.expandAll(true);
    		 }
    	})
    	
    	$('#roleGrantForm').form({
            url : '${path }/role/grant',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                var checknodes = resourceTree.getCheckedNodes(true);
                var ids = [];
                if (checknodes && checknodes.length > 0) {
                    for ( var i = 0; i < checknodes.length; i++) {
                        ids.push(checknodes[i].id);
                    }
                }
                $('#resourceIds').val(ids);
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    showMsg(result.msg);
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });

  function checkAll(){
	  resourceTree.checkAllNodes(true);
  }
  
  function checkInverse(){
	  resourceTree.checkAllNodes(false);
  }
</script>
<div id="roleGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'west'" title="系统资源" style="width: 300px; padding: 1px;">
        <div class="well well-small">
            <form id="roleGrantForm" method="post">
             <input name="id" type="hidden"  value="${id}" readonly="readonly">
                <ul id="resourceTree" class="ztree"></ul>
                <input id="resourceIds" name="resourceIds" type="hidden" />
            </form>
        </div>
    </div>
    <div data-options="region:'center'" title="" style="overflow: hidden; padding: 10px;">
        <div>
            <button class="btn btn-success" onclick="checkAll();">全选</button>
            <br /> <br />
            <button class="btn btn-warning" onclick="checkInverse();">反选</button>
            <br /> <br />
        </div>
    </div>
</div>