<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var singleUserTree;
    jQuery(function($) {
    	var setting = {
    			 view: {
    	                selectedMulti: false
    	            },
    	            check: {
    	                chkStyle:"radio",
    	                enable: true,
    	                radioType:'all'
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
    	            callback: {
    	                onCheck: zTreeOnCheck
    	            }
            };
    	$.ajax({
    		 url : '${path }/user/findOrgAndUserTree?userids=${userids}',
    		 dataType:'json',
    		 success:function(data){
    			 console.log(data)
    			 $.fn.zTree.init($("#singleUserTree"), setting, data);
    			 singleUserTree = $.fn.zTree.getZTreeObj("singleUserTree");
    			// singleUserTree.expandAll(true);
    		 }
    	})
    })
    
   function zTreeOnCheck(event, treeId, treeNode) {
            if(treeNode.type=="0"){
                var treeObj = $.fn.zTree.getZTreeObj("singleUserTree");
                treeObj.checkNode(treeNode, false, true);
               alert("【不能选择部门！只能选择用户！】","warning");
            }
     }  
  function getSingleUserIds(){
    		  var checknodes = singleUserTree.getCheckedNodes(true);
              var ids = [];
              if (checknodes && checknodes.length > 0) {
                  for ( var i = 0; i < checknodes.length; i++) {
                      ids.push(checknodes[i].id);
                  }
              }
       return ids.join(',');
   }


</script>
<div id="singleUserGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'west'" >
        <div class="well well-small">
            <form id="singleUserGrantForm" method="post">
             <input name="id" type="hidden"  value="${id}" readonly="readonly">
                <ul id="singleUserTree" class="ztree"></ul>
                <input id="userids" name="userids" type="hidden" />
            </form>
        </div>
    </div>

</div>