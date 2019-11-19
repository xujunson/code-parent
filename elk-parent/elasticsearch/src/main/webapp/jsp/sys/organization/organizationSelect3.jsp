<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var multiOrgTree;
    var treeNode;//树节点
    var chkStyle="checkbox";
    jQuery(function($) {
    	  if(!${flag}){
    		  chkStyle="radio";
    	  }
    	var setting = {
    			 view: {
    	                selectedMulti: ${flag},
                        nameIsHTML: true,
                        fontCss : setFontCss
    	            },
    	            check: {
    	                chkStyle:chkStyle,
    	                enable: true,
                        chkboxType : { "Y" : "", "N" : "" },
                        <c:if test="${flag==false}">
                        radioType:'all'
                        </c:if>
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
    		 url : '${path }/organization/treeSelect?deptids=${deptids}',
    		 dataType:'json',
    		 success:function(data){
    			 $.fn.zTree.init($("#multiOrgTree"), setting, data);
    			 multiOrgTree = $.fn.zTree.getZTreeObj("multiOrgTree");
    			// multiOrgTree.expandAll(true);
                 treeNode = data;
    		 }
    	})
        var nodeList=[];
        //搜索节点并高亮显示
        $("#searchBtnTree").on("click", function() {
            var key = $("#searchTreeText").val();
            var treeNodes = multiOrgTree.transformToArray(multiOrgTree.getNodes());
            for(var i = 0; i < treeNodes.length; i++) {
                treeNodes[i].icon=basePath+"/static/img/tree.png";
                setFontCss(treeNodes[i].id, treeNodes[i]);
                multiOrgTree.updateNode(treeNodes[i]);
            }
            if (key != null) {
                nodeList = multiOrgTree.getNodesByParamFuzzy("text", key, null);//模糊查询
                if (nodeList && nodeList.length > 0) {
                    for ( var i = 0; i < nodeList.length; i++) {
                        nodeList[i].highlight = true;//设置高亮
                        findParent(multiOrgTree, nodeList[i]);
                        nodeList[i].icon=basePath+"/static/img/tree.png";
                        multiOrgTree.updateNode(nodeList[i]);
                        multiOrgTree.expandNode(nodeList[i], true, true, false);//展开节点
                    }
                }
            }else{
                alert("请输入搜索条件");
            }

        });

        //找到其父亲节点
        function findParent(ztree, node) {
            ztree.expandNode(node, true, false, true);//展开节点
            var pNode = node.getParentNode();
            if (pNode != null) {
                findParent(ztree, pNode);
            }
        }

        //重置
        $("#reset").on("click",function(){
            treeNode.highlight=false;//所有节点取消高亮
            $("#searchTreeText").val('');//搜索框重置
            multiOrgTree.expandNode(treeNode,true,true,true);
            multiOrgTree.refresh();
        });


    });
    //设置字体颜色
    function setFontCss(treeId, treeNode) {
        if (treeNode.id == 0) {//根节点
            return {
                color : "black"
            };
        }
        if (treeNode.highlight) {//如果符合模糊查询条件则高亮显示
            return {
                color : "red"
            };
        }else{
            return {
                color : "black"
            };
        }
    }

    function getMultiOrgIds(){
    		  var checknodes = multiOrgTree.getCheckedNodes(true);
              var ids = [];
              if (checknodes && checknodes.length > 0) {
                  for ( var i = 0; i < checknodes.length; i++) {
                      ids.push(checknodes[i].id);
                  }
              }
       return ids.join(',');
   }
  function getMultiOrgNames(){
    		  var checknodes = multiOrgTree.getCheckedNodes(true);
              var names = [];
              if (checknodes && checknodes.length > 0) {
                  for ( var i = 0; i < checknodes.length; i++) {
                	  names.push(checknodes[i].text);
                  }
              }
       return names.join(',');
   }

</script>
<div id="multiOrgGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'west'" title="" style="width: 300px; padding: 1px;">
        <div class="well well-small">
            <form id="multiOrgGrantForm" method="post">
                <ul id="multiOrgTree" class="ztree"></ul>
            </form>
        </div>
    </div>
    <div data-options="region:'center'" title="" style=" padding: 1px;">
        <form id="org_tree_select">
            <table class="grid">
                <tr>
                    <td>部门名称</td>
                    <td><input type="text" class="easyui-text" name="name" id="searchTreeText">
                        <a href="javascript:void(0)" class="easyui-linkbutton" id="searchBtnTree">查询</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" id="reset">清除</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>