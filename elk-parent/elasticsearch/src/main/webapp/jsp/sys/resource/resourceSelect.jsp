<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var multiresourceTree;
    var treeNode;//树节点
    var chkStyle = "checkbox";
    jQuery(function ($) {
        if (!${flag}) {
            chkStyle = "radio";
        }
        var setting = {
            view: {
                selectedMulti: ${flag},
                nameIsHTML: true
            },
            check: {
                chkStyle: chkStyle,
                enable: true,
                chkboxType: {"Y": "", "N": ""},
                <c:if test="${flag==false}">
                radioType: 'all'
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
            },
            callback:{
                onCheck:resourceCheck
            }
        };
        $.ajax({
            url: '${path }/resource/treeSelect?resIds=${resIds}',
            dataType: 'json',
            success: function (data) {
                $.fn.zTree.init($("#multiresourceTree"), setting, data);
                multiresourceTree = $.fn.zTree.getZTreeObj("multiresourceTree");
                // multiresourceTree.expandAll(true);
                treeNode = data;
            }
        })
    });

    function resourceCheck(event, treeId, treeNode) {
        var resourceid=treeNode.id;
        var resourcename=treeNode.text;
        if(treeNode.checked){
            selectresourceBysearch2(resourceid,resourcename);
        }else{
            delresourceByselect2(resourceid)
        }
    }
    function getMultiResourceIds() {
        var checknodes = multiresourceTree.getCheckedNodes(true);
        var ids = [];
        if (checknodes && checknodes.length > 0) {
            for (var i = 0; i < checknodes.length; i++) {
                ids.push(checknodes[i].id);
            }
        }
        return ids.join(',');
    }

    function getMultiResourceNames() {
        var checknodes = multiresourceTree.getCheckedNodes(true);
        var names = [];
        if (checknodes && checknodes.length > 0) {
            for (var i = 0; i < checknodes.length; i++) {
                names.push(checknodes[i].text);
            }
        }
        return names.join(',');
    }

    function searchresourceBtnTree() {
        var name=$('#searchresourceTreeText').val();
        if(!isNull(name)){
            $.ajax({
                url:basePath+'/resource/getNameLike',
                data:{"name":name},
                dataType:'json',
                success:function (data) {
                    if(!isNull(data)){
                        $('#resource_search_ul').empty();
                        var html="";
                        for(var i=0;i<data.length;i++){
                            html+="<li onclick='selectresourceBysearch(\""+data[i].id+"\",\""+data[i].name+"\")'>"+data[i].name+"</li>";
                        }
                        $('#resource_search_ul').append(html);
                    }
                }
            })
        }

    }

    function resetresourceSearch(){
        $('#searchresourceTreeText').val('');
        $('#resource_search_ul').empty();
    }
    function selectresourceBysearch(resourceid,resourcename) {
        var node=multiresourceTree.getNodeByParam("id",resourceid,"");
        if(!node.checked){
            var html="<li onclick='delresourceByselect(\""+resourceid+"\",this)' class='resourceid_"+resourceid+"'>"+resourcename+"</li>";
            if(${flag==false}){
                $('#resource_search_ul_disp').empty();
            }
            $('#resource_search_ul_disp').append(html);
            var node=multiresourceTree.getNodeByParam("id",resourceid,"");
            multiresourceTree.checkNode(node,true);
            var val=$('#resource_search_id_hidden').val();
            if(val!=''){
                val+=","+resourceid;
            }else{
                val=resourceid;
            }
            if(${flag==false}){
                $('#resource_search_id_hidden').val(resourceid);
            }else {
                $('#resource_search_id_hidden').val(val);
            }
        }
    }
    function selectresourceBysearch2(resourceid,resourcename) {
        var html="<li onclick='delresourceByselect(\""+resourceid+"\",this)' class='resourceid_"+resourceid+"'>"+resourcename+"</li>";
        if(${flag==false}){
            $('#resource_search_ul_disp').empty();
        }
        $('#resource_search_ul_disp').append(html);
        var node=multiresourceTree.getNodeByParam("id",resourceid,"");
        multiresourceTree.checkNode(node,true);
        var val=$('#resource_search_id_hidden').val();
        if(val!=''){
            val+=","+resourceid;
        }else{
            val=resourceid;
        }
        if(${flag==false}){
            $('#resource_search_id_hidden').val(resourceid);
        }else {
            $('#resource_search_id_hidden').val(val);
        }
    }

    function delresourceByselect(resourceid,obj) {
        $(obj).remove();
        var node=multiresourceTree.getNodeByParam("id",resourceid,"");
        multiresourceTree.checkNode(node,false);
        strdelId('resource_search_id_hidden',resourceid);
    }
    function delresourceByselect2(resourceid) {
        $('#resource_search_ul_disp').find('.resourceid_'+resourceid).remove();
        strdelId('resource_search_id_hidden',resourceid);
        var val=$('#resource_search_id_hidden').val();
        console.log(val);
    }
</script>
<div id="multiresourceGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'west'" title="" style="width: 300px; padding: 1px;">
        <div class="well well-small">
            <form id="multiresourceGrantForm" method="post">
                <ul id="multiresourceTree" class="ztree"></ul>
            </form>
        </div>
    </div>
    <div data-options="region:'center'" title="" style=" padding: 1px;overflow: hidden;height: 100%;">
        <form id="resource_tree_select">
            <table class="grid">
                <tr>
                    <td>资源名称</td>
                    <td><input type="text" class="easyui-text" name="name" id="searchresourceTreeText">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchresourceBtnTree()">查询</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="resetresourceSearch()">清除</a>
                    </td>
                </tr>
            </table>
            <ul style="list-style: none;height: 180px;padding-top:0px;margin: 0px;overflow-y: auto" id="resource_search_ul">
            </ul>
            <div style="border-top:1px solid #95B8E7;">
                <input type="hidden" id="resource_search_id_hidden">
                <ul style="list-style: none;height: 160px;margin: 0px;overflow-y: auto;" id="resource_search_ul_disp">
                    <c:forEach var="resource" items="${resources}">
                        <li onclick='delresourceByselect("${resource.id}",this)' class='resourceid_${resource.id}'>${resource.name}</li>
                    </c:forEach>
                </ul>
            </div>
        </form>
    </div>
</div>