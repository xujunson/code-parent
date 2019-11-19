<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var multiUserTree;
    jQuery(function($) {
    	var setting = {
    			 view: {
    	                selectedMulti: true
    	            },
    	            check: {
    	                chkStyle:"checkbox",
    	                enable: true,
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
    		 url : '${path }/user/findOrgAndUserTree?userids=${userids}',
    		 dataType:'json',
    		 success:function(data){
    			 console.log(data)
    			 $.fn.zTree.init($("#multiUserTree"), setting, data);
    			 multiUserTree = $.fn.zTree.getZTreeObj("multiUserTree");
    			// multiUserTree.expandAll(true);
    		 }
    	})
    })	
  function getMultiUserIds(){
    		  var checknodes = multiUserTree.getCheckedNodes(true);
              var ids = [];
              if (checknodes && checknodes.length > 0) {
                  for ( var i = 0; i < checknodes.length; i++) {
                      ids.push(checknodes[i].id);
                  }
              }
       return ids.join(',');
   }
    
  function checkMultiUserAll(){
    multiUserTree.checkAllNodes(true);
  }
    	
  function checkMultiUserInverse(){
	  multiUserTree.checkAllNodes(false);
  }
</script>
<div id="multiUserGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'west'" title="人员选择" style="width: 300px; padding: 1px;">
        <div class="well well-small">
            <form id="multiUserGrantForm" method="post">
             <input name="id" type="hidden"  value="${id}" readonly="readonly">
                <ul id="multiUserTree" class="ztree"></ul>
                <input id="userids" name="userids" type="hidden" />
            </form>
        </div>
    </div>
    <div data-options="region:'center'" title="" style="overflow: hidden; padding: 10px;">
        <div>
            <button class="btn btn-success" onclick="checkMultiUserAll();">全选</button>
            <br /> <br />
            <button class="btn btn-warning" onclick="checkMultiUserInverse();">反选</button>
            <br /> <br />
        </div>
    </div>
</div>