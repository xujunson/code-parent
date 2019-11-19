<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var multiOrgTree;
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
                onCheck:orgCheck
            }
        };
        $.ajax({
            url: '${path }/organization/treeSelect?deptids=${deptids}',
            dataType: 'json',
            success: function (data) {
                $.fn.zTree.init($("#multiOrgTree"), setting, data);
                multiOrgTree = $.fn.zTree.getZTreeObj("multiOrgTree");
                // multiOrgTree.expandAll(true);
                treeNode = data;
            }
        })
    });

    function orgCheck(event, treeId, treeNode) {
        var orgid=treeNode.id;
        var orgname=treeNode.text;
       if(treeNode.checked){
           selectOrgBysearch2(orgid,orgname);
       }else{
           delOrgByselect2(orgid)
       }
    }
    function getMultiOrgIds() {
        var checknodes = multiOrgTree.getCheckedNodes(true);
        var ids = [];
        if (checknodes && checknodes.length > 0) {
            for (var i = 0; i < checknodes.length; i++) {
                ids.push(checknodes[i].id);
            }
        }
        return ids.join(',');
    }

    function getMultiOrgNames() {
        var checknodes = multiOrgTree.getCheckedNodes(true);
        var names = [];
        if (checknodes && checknodes.length > 0) {
            for (var i = 0; i < checknodes.length; i++) {
                names.push(checknodes[i].text);
            }
        }
        return names.join(',');
    }

    function searchBtnTree() {
        var name=$('#searchTreeText').val();
        if(!isNull(name)){
            $.ajax({
                url:basePath+'/organization/getNameLike',
                data:{"name":name},
                dataType:'json',
                success:function (data) {
                    if(!isNull(data)){
                        $('#org_search_ul').empty();
                        var html="";
                        for(var i=0;i<data.length;i++){
                            html+="<li onclick='selectOrgBysearch(\""+data[i].id+"\",\""+data[i].name+"\")'>"+data[i].name+"</li>";
                        }
                        $('#org_search_ul').append(html);
                    }
                }
            })
        }

    }

    function resetOrgSearch(){
        $('#searchTreeText').val('');
        $('#org_search_ul').empty();
    }
    function selectOrgBysearch(orgid,orgname) {
        var node=multiOrgTree.getNodeByParam("id",orgid,"");
        if(!node.checked){
            var html="<li onclick='delOrgByselect(\""+orgid+"\",this)' class='orgid_"+orgid+"'>"+orgname+"</li>";
            if(${flag==false}){
                $('#org_search_ul_disp').empty();
            }
            $('#org_search_ul_disp').append(html);
            var node=multiOrgTree.getNodeByParam("id",orgid,"");
            multiOrgTree.checkNode(node,true);
            var val=$('#org_search_id_hidden').val();
            if(val!=''){
                val+=","+orgid;
            }else{
                val=orgid;
            }
            if(${flag==false}){
                $('#org_search_id_hidden').val(orgid);
            }else {
                $('#org_search_id_hidden').val(val);
            }
        }
    }
    function selectOrgBysearch2(orgid,orgname) {
            var html="<li onclick='delOrgByselect(\""+orgid+"\",this)' class='orgid_"+orgid+"'>"+orgname+"</li>";
            if(${flag==false}){
                $('#org_search_ul_disp').empty();
            }
            $('#org_search_ul_disp').append(html);
            var node=multiOrgTree.getNodeByParam("id",orgid,"");
            multiOrgTree.checkNode(node,true);
            var val=$('#org_search_id_hidden').val();
            if(val!=''){
                val+=","+orgid;
            }else{
                val=orgid;
            }
        if(${flag==false}){
            $('#org_search_id_hidden').val(orgid);
        }else {
            $('#org_search_id_hidden').val(val);
        }
    }

    function delOrgByselect(orgid,obj) {
        $(obj).remove();
        var node=multiOrgTree.getNodeByParam("id",orgid,"");
        multiOrgTree.checkNode(node,false);
        strdelId('org_search_id_hidden',orgid);
    }
    function delOrgByselect2(orgid) {
        $('#org_search_ul_disp').find('.orgid_'+orgid).remove();
        strdelId('org_search_id_hidden',orgid);
        var val=$('#org_search_id_hidden').val();
        console.log(val);
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
    <div data-options="region:'center'" title="" style=" padding: 1px;overflow: auto;height: 100%;">
        <form id="org_tree_select">
            <table class="grid">
                <tr>
                    <td>部门名称</td>
                    <td><input type="text" class="easyui-text" name="name" id="searchTreeText">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchBtnTree()">查询</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="resetOrgSearch()">清除</a>
                    </td>
                </tr>
            </table>
            <ul style="list-style: none;height: 180px;padding-top:0px;margin: 0px;overflow-y: auto" id="org_search_ul">
            </ul>
            <div style="border-top:1px solid #95B8E7;">
                <input type="hidden" id="org_search_id_hidden">
                <ul style="list-style: none;margin: 0px;" id="org_search_ul_disp">
                    <c:forEach var="org" items="${orgs}">
                        <li onclick='delOrgByselect("${org.id}",this)' class='orgid_${org.id}'>${org.name}</li>
                    </c:forEach>
                </ul>
            </div>
        </form>
    </div>
</div>