<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var multipositionTree;
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
                onCheck:positionCheck
            }
        };
        $.ajax({
            url: '${path }/position/treeSelect?positionIds=${positionIds}',
            dataType: 'json',
            success: function (data) {
                $.fn.zTree.init($("#multipositionTree"), setting, data);
                multipositionTree = $.fn.zTree.getZTreeObj("multipositionTree");
                // multipositionTree.expandAll(true);
                treeNode = data;
            }
        })
    });

    function positionCheck(event, treeId, treeNode) {
        var positionid=treeNode.id;
        var positionname=treeNode.text;
        if(treeNode.checked){
            selectpositionBysearch2(positionid,positionname);
        }else{
            delpositionByselect2(positionid)
        }
    }
    function getMultiPositionIds() {
        var checknodes = multipositionTree.getCheckedNodes(true);
        var ids = [];
        if (checknodes && checknodes.length > 0) {
            for (var i = 0; i < checknodes.length; i++) {
                ids.push(checknodes[i].id);
            }
        }
        return ids.join(',');
    }

    function getMultiPositionNames() {
        var checknodes = multipositionTree.getCheckedNodes(true);
        var names = [];
        if (checknodes && checknodes.length > 0) {
            for (var i = 0; i < checknodes.length; i++) {
                names.push(checknodes[i].text);
            }
        }
        return names.join(',');
    }

    function searchpositionBtnTree() {
        var name=$('#searchpositionTreeText').val();
        if(!isNull(name)){
            $.ajax({
                url:basePath+'/position/getNameLike',
                data:{"name":name},
                dataType:'json',
                success:function (data) {
                    if(!isNull(data)){
                        $('#position_search_ul').empty();
                        var html="";
                        for(var i=0;i<data.length;i++){
                            html+="<li onclick='selectpositionBysearch(\""+data[i].id+"\",\""+data[i].name+"\")'>"+data[i].name+"</li>";
                        }
                        $('#position_search_ul').append(html);
                    }
                }
            })
        }

    }

    function resetpositionSearch(){
        $('#searchpositionTreeText').val('');
        $('#position_search_ul').empty();
    }
    function selectpositionBysearch(positionid,positionname) {
        var node=multipositionTree.getNodeByParam("id",positionid,"");
        if(!node.checked){
            var html="<li onclick='delpositionByselect(\""+positionid+"\",this)' class='positionid_"+positionid+"'>"+positionname+"</li>";
            if(${flag==false}){
                $('#position_search_ul_disp').empty();
            }
            $('#position_search_ul_disp').append(html);
            var node=multipositionTree.getNodeByParam("id",positionid,"");
            multipositionTree.checkNode(node,true);
            var val=$('#position_search_id_hidden').val();
            if(val!=''){
                val+=","+positionid;
            }else{
                val=positionid;
            }
            if(${flag==false}){
                $('#position_search_id_hidden').val(positionid);
            }else {
                $('#position_search_id_hidden').val(val);
            }
        }
    }
    function selectpositionBysearch2(positionid,positionname) {
        var html="<li onclick='delpositionByselect(\""+positionid+"\",this)' class='positionid_"+positionid+"'>"+positionname+"</li>";
        if(${flag==false}){
            $('#position_search_ul_disp').empty();
        }
        $('#position_search_ul_disp').append(html);
        var node=multipositionTree.getNodeByParam("id",positionid,"");
        multipositionTree.checkNode(node,true);
        var val=$('#position_search_id_hidden').val();
        if(val!=''){
            val+=","+positionid;
        }else{
            val=positionid;
        }
        if(${flag==false}){
            $('#position_search_id_hidden').val(positionid);
        }else {
            $('#position_search_id_hidden').val(val);
        }
    }

    function delpositionByselect(positionid,obj) {
        $(obj).remove();
        var node=multipositionTree.getNodeByParam("id",positionid,"");
        multipositionTree.checkNode(node,false);
        strdelId('position_search_id_hidden',positionid);
    }
    function delpositionByselect2(positionid) {
        $('#position_search_ul_disp').find('.positionid_'+positionid).remove();
        strdelId('position_search_id_hidden',positionid);
        var val=$('#position_search_id_hidden').val();
        console.log(val);
    }
</script>
<div id="multipositionGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'west'" title="" style="width: 300px; padding: 1px;">
        <div class="well well-small">
            <form id="multipositionGrantForm" method="post">
                <ul id="multipositionTree" class="ztree"></ul>
            </form>
        </div>
    </div>
    <div data-options="region:'center'" title="" style=" padding: 1px;overflow: hidden;height: 100%;">
        <form id="position_tree_select">
            <table class="grid">
                <tr>
                    <td>职位名称</td>
                    <td><input type="text" class="easyui-text" name="name" id="searchpositionTreeText">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchpositionBtnTree()">查询</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="resetpositionSearch()">清除</a>
                    </td>
                </tr>
            </table>
            <ul style="list-style: none;height: 180px;padding-top:0px;margin: 0px;overflow-y: auto" id="position_search_ul">
            </ul>
            <div style="border-top:1px solid #95B8E7;">
                <input type="hidden" id="position_search_id_hidden" value="${positionIds}">
                <ul style="list-style: none;height: 160px;margin: 0px;overflow-y: auto;" id="position_search_ul_disp">
                    <c:forEach var="position" items="${positions}">
                        <li onclick='delpositionByselect("${position.id}",this)' class='positionid_${position.id}'>${position.name}</li>
                    </c:forEach>
                </ul>
            </div>
        </form>
    </div>
</div>