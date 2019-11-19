<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var multiOrgTree;
    var chkStyle="checkbox";
    jQuery(function($) {
    	  if(!${flag}){
    		  chkStyle="radio";
    	  }
    	var setting = {
    			 view: {
    	                selectedMulti: ${flag},
                        nameIsHTML: true
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
    		 }
    	})
    });


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

    /**
     * 展开树
     * @param treeId
     */
    function expand_ztree(treeId) {
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        treeObj.expandAll(true);
    }

    /**
     * 收起树：只展开根节点下的一级节点
     * @param treeId
     */
    function close_ztree(treeId) {
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        var nodes = treeObj.transformToArray(treeObj.getNodes());
        var nodeLength = nodes.length;
        for(var i = 0; i < nodeLength; i++) {
            if(nodes[i].id == '0') {
                //根节点：展开
                treeObj.expandNode(nodes[i], true, true, false);
            } else {
                //非根节点：收起
                treeObj.expandNode(nodes[i], false, true, false);
            }
        }
    }

    /**
     * 搜索树，高亮显示并展示【模糊匹配搜索条件的节点s】
     * @param treeId
     * @param searchConditionId 文本框的id
     */
    function search_ztree(treeId, searchConditionId) {
        searchByFlag_ztree(treeId, searchConditionId, "");
    }

    /**
     * 搜索树，高亮显示并展示【模糊匹配搜索条件的节点s】
     * @param treeId
     * @param searchConditionId     搜索条件Id
     * @param flag                  需要高亮显示的节点标识
     */
    function searchByFlag_ztree(treeId, searchConditionId, flag) {
        //<1>.搜索条件
        var searchCondition = $('#' + searchConditionId).val();
        //<2>.得到模糊匹配搜索条件的节点数组集合
        var highlightNodes = new Array();
        if(searchCondition != "") {
            var treeObj = $.fn.zTree.getZTreeObj(treeId);
            highlightNodes = treeObj.getNodesByParamFuzzy("text", searchCondition, null);
        }
        //<3>.高亮显示并展示【指定节点s】
        highlightAndExpand_ztree(treeId, highlightNodes, searchCondition, flag);
    }

    /**
     * 高亮显示并展示【指定节点s】
     * @param treeId
     * @param highlightNodes 需要高亮显示的节点数组
     * @param flag           需要高亮显示的节点标识
     */
    function highlightAndExpand_ztree(treeId, highlightNodes, tx, flag) {
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        //<1>. 先把全部节点更新为普通样式
        var treeNodes = treeObj.transformToArray(treeObj.getNodes());
        for(var i = 0; i < treeNodes.length; i++) {
            treeNodes[i].icon=basePath+"/static/img/tree.png";
            treeObj.updateNode(treeNodes[i]);
        }
        //<2>.收起树, 只展开根节点下的一级节点
        close_ztree(treeId);
        //<3>.把指定节点的样式更新为高亮显示，并展开
        if(highlightNodes != null) {
            for(var i = 0; i < highlightNodes.length; i++) {
                if(flag != null && flag != "") {
                    if(highlightNodes[i].flag == flag) {
                        //高亮显示节点，并展开
                        highlightNodes[i].highlight = true;
                        highlightNodes[i].icon=basePath+"/static/img/tree.png";
                        treeObj.updateNode(highlightNodes[i]);
                        //高亮显示节点的父节点的父节点....直到根节点，并展示
                        var parentNode = highlightNodes[i].getParentNode();
                        var parentNodes = getParentNodes_ztree(treeId, parentNode);
                        treeObj.expandNode(parentNodes, true, false, true);
                        treeObj.expandNode(parentNode, true, false, true);
                    }
                } else {
                    //高亮显示节点，并展开
                    //highlightNodes[i].checked = true;
                    var t = highlightNodes[i].text;
                    t = t.replace(eval("/" + tx + "/gi"), "<span style='background-color: yellow;color:red'>" + tx + "</span>");
                    highlightNodes[i].text = t;
                    highlightNodes[i].icon=basePath+"/static/img/tree.png";
                    treeObj.updateNode(highlightNodes[i]);
                    //高亮显示节点的父节点的父节点....直到根节点，并展示
                    var parentNode = highlightNodes[i].getParentNode();
                    var parentNodes = getParentNodes_ztree(treeId, parentNode);
                    treeObj.expandNode(parentNodes, true, false, true);
                    treeObj.expandNode(parentNode, true, false, true);
                }
            }
        }
    }

    /**
     * 递归得到指定节点的父节点的父节点....直到根节点
     */
    function getParentNodes_ztree(treeId, node) {
        if(node != null) {
            var treeObj = $.fn.zTree.getZTreeObj(treeId);
            var parentNode = node.getParentNode();
            return getParentNodes_ztree(treeId, parentNode);
        } else {
            return node;
        }
    }

    /**
     * 设置树节点字体样式
     */
    function setFontCss_ztree(treeId, treeNode) {
        if(treeNode.id == 0) {
            //根节点
            return {
                color: "#333",
                "font-weight": "bold"
            };
        } else if(treeNode.isParent == false) {
            //叶子节点
            return(!!treeNode.highlight) ? {
                color: "#ff0000",
                "font-weight": "bold"
            } : {
                color: "#660099",
                "font-weight": "normal"
            };
        } else {
            //父节点
            return(!!treeNode.highlight) ? {
                color: "#ff0000",
                "font-weight": "bold"
            } : {
                color: "#333",
                "font-weight": "normal"
            };
        }
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
                    <td><input type="text" class="easyui-text" name="name" id="org_select_name">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="search_ztree('multiOrgTree', 'org_select_name')">查询</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearOrgSelectTreeFun()">清除</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>