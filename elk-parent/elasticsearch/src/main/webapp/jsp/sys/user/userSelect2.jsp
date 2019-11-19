<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var multiUserTree;
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
                chkboxType:  { "Y" : "ps", "N" : "ps" },
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
                onCheck:userCheck
            }
        };
        $.ajax({
            url: '${path }/user/findOrgAndUserTree?userids=${userids}',
            dataType: 'json',
            success: function (data) {
                $.fn.zTree.init($("#multiUserTree"), setting, data);
                multiUserTree = $.fn.zTree.getZTreeObj("multiUserTree");
                // multiUserTree.expandAll(true);
                treeNode = data;
            }
        })
    });

    function userCheck(event, treeId, treeNode) {
//        var id=treeNode.id;
//        var name=treeNode.text;
//        if (id.indexOf('USER') > -1) {
//            if(treeNode.checked){
//                selectuserBysearch2(id,name);
//            }else{
//                deluserByselect2(id)
//            }
//        }
        $('#user_search_ul_disp').empty();
        $('#user_search_id_hidden').val('');

        var checknodes = multiUserTree.getCheckedNodes(true);
        if (checknodes && checknodes.length > 0) {
            for (var i = 0; i < checknodes.length; i++) {
                    if (checknodes[i].id.indexOf('USER') > -1) {
                        if(checknodes[i].checked){
                            selectuserBysearch2(checknodes[i].id,checknodes[i].text);
                        }else{
                            deluserByselect2(checknodes[i].id)
                        }
                    }
            }
        }

    }
    function getMultiuserids() {
        var checknodes = multiUserTree.getCheckedNodes(true);
        var ids = [];
        if (checknodes && checknodes.length > 0) {
            for (var i = 0; i < checknodes.length; i++) {
                var id = checknodes[i].id;
                if (id.indexOf('USER') > -1) {
                    id = id.replace('USER', '');//用户id
                    ids.push(id);
                }
            }
        }
        return ids.join(',');
    }

    function getMultiusernames() {
        var checknodes = multiUserTree.getCheckedNodes(true);
        var names = [];
        if (checknodes && checknodes.length > 0) {
            for (var i = 0; i < checknodes.length; i++) {
                var id = checknodes[i].id;
                if (id.indexOf('USER') > -1) {
                    id = id.replace('USER', '');//用户id
                    names.push(checknodes[i].text);
                }
            }
        }
        return names.join(',');
    }

    function searchUserBtnTree() {
        var name=$('#searchTreeText_user').val();
        if(!isNull(name)){
            $.ajax({
                url:basePath+'/user/getNameLike',
                data:{"name":name},
                dataType:'json',
                success:function (data) {
                    if(!isNull(data)){
                        $('#user_search_ul').empty();
                        var html="";
                        for(var i=0;i<data.length;i++){
                            html+="<li onclick='selectuserBysearch(\"USER"+data[i].id+"\",\""+data[i].name+"\")'>"+data[i].name+"</li>";
                        }
                        $('#user_search_ul').append(html);
                    }
                }
            })
        }

    }

    function resetuserSearch(){
        $('#searchTreeText_user').val('');
        $('#user_search_ul').empty();
    }
    function selectuserBysearch(userid,username) {
        var node=multiUserTree.getNodeByParam("id",userid,"");
        if(!node.checked){
            var html="<li onclick='deluserByselect(\""+userid+"\",this)' class='userid_"+userid+"'>"+username+"</li>";
            if(${flag==false}){
                $('#user_search_ul_disp').empty();
            }
            $('#user_search_ul_disp').append(html);
            var node=multiUserTree.getNodeByParam("id",userid,"");
            multiUserTree.checkNode(node,true);
            var val=$('#user_search_id_hidden').val();
            if(val!=''){
                val+=","+userid;
            }else{
                val=userid;
            }
            if(${flag==false}){
                $('#user_search_id_hidden').val(userid);
            }else {
                $('#user_search_id_hidden').val(val);
            }
        }
    }
    function selectuserBysearch2(userid,username) {
        var html="<li onclick='deluserByselect(\""+userid+"\",this)' class='userid_"+userid+"'>"+username+"</li>";
        if(${flag==false}){
            $('#user_search_ul_disp').empty();
        }
        $('#user_search_ul_disp').append(html);
        var node=multiUserTree.getNodeByParam("id",userid,"");
        multiUserTree.checkNode(node,true);
        var val=$('#user_search_id_hidden').val();
        if(val!=''){
            val+=","+userid;
        }else{
            val=userid;
        }
        if(${flag==false}){
            $('#user_search_id_hidden').val(userid);
        }else {
            $('#user_search_id_hidden').val(val);
        }
    }

    function deluserByselect(userid,obj) {
        $(obj).remove();
        var node=multiUserTree.getNodeByParam("id",userid,"");
        console.log(node)
        multiUserTree.checkNode(node,false);
        strdelId('user_search_id_hidden',userid);
    }
    function deluserByselect2(userid) {
        $('#user_search_ul_disp').find('.userid_'+userid).remove();
        strdelId('user_search_id_hidden',userid);
        var val=$('#user_search_id_hidden').val();
        console.log(val);
    }
</script>
<div id="multiuserGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'west'" title="" style="width: 300px; padding: 1px;">
        <div class="well well-small">
            <form id="multiuserGrantForm" method="post">
                <ul id="multiUserTree" class="ztree"></ul>
            </form>
        </div>
    </div>
    <div data-options="region:'center'" title="" style=" padding: 1px;overflow: hidden;height: 100%;">
        <form id="user_tree_select">
            <table class="grid">
                <tr>
                    <td>姓名</td>
                    <td><input type="text" class="easyui-text" name="name" id="searchTreeText_user">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchUserBtnTree()">查询</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="resetuserSearch()">清除</a>
                    </td>
                </tr>
            </table>
            <ul style="list-style: none;height: 180px;padding-top:0px;margin: 0px;overflow-y: auto" id="user_search_ul">
            </ul>
            <div style="border-top:1px solid #95B8E7;">
                <input type="hidden" id="user_search_id_hidden" value="${userids}">
                <ul style="list-style: none;height: 160px;margin: 0px;overflow-y: auto;" id="user_search_ul_disp">
                    <c:forEach items="${users}" var="user">
                        <li onclick='deluserByselect("USER${user.id}",this)' class='userid_USER${user.id}'>${user.name}</li>
                    </c:forEach>
                </ul>
            </div>
        </form>
    </div>
</div>