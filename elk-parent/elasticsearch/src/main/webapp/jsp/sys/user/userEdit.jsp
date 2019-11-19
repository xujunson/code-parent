<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<style>
.easyui-text{
	width: 140px; height: 25px;border-radius:5px;border:thin solid #ddd;
	border-color:#95B8E7;
}
.easyui-text:focus{
	outline: none;
	border-color:#95B8E7;
}
</style>
<script type="text/javascript">
    $(function() {
        $('#userEditorganizationId').combotree({
            url : '${path }/organization/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value : '${user.orgId}'
        });
        $('#userEditPosition').combotree({
            url : '${path }/position/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value : '${user.positionId}'
        });

     /*    $('#userEditRoleIds').combotree({
            url : '${path }/role/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            multiple : true,
            required : true,
            cascadeCheck : false,
            value : '${user.roleIdList}'
        }); */

        $('#userEditForm').form({
            url : '${path }/user/edit',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                	 showMsg("编辑成功");
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#userEditForm');
                    parent.$.messager.alert('错误',result.msg, 'error');
                }
            }
        });
        $("#userEditSex").val('${user.sex}');
        $("#userEditStatus").val('${user.status}');
      /*  
        $('#userEditForm [name=superiorName]').focus(function(){
        	chooseEditUser();
        	$(this).blur();
        }) */
    });
   /*  function chooseEditUser() {
        var url="${ctxPath}/user/toSelectUser";
        var title='选人';
        var width=250;
        var dialog= $('<div>').dialog({
            title:title,
            href:url,
            width : width,
            height : 500,
            onClose : function() {
                $(this).dialog('destroy');
            },
            buttons:[{
                text:'确定',
                handler:function () {
                        var treeObj = $.fn.zTree.getZTreeObj("singleUserTree"); //获取全部节点数据
                        var nodes = treeObj.getCheckedNodes(true);
                        var id = nodes[0].id;
                        var name = nodes[0].text;
                        id = id.replace('USER', '');//用户id
                        $('#userEditForm [name=superior]').val(id);
                        $('#userEditForm [name=superiorName]').val(name);
                	dialog.dialog('close');
                }
            },{
                text:'取消',
                handler:function () {
                	dialog.dialog('close');
                }
            }]
        });
    } */
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;overflow-y: auto;padding: 3px;">
    <div id="dd"></div>
        <form id="userEditForm" method="post">
            <div class="light-info" style="overflow: hidden;padding: 3px;">
                <div>密码不修改请留空。</div>
            </div>
           <table class="grid">
                <tr>
                    <td>登录名</td>
                    <td><input name="id" type="hidden"  value="${user.id}">
                    <input name="username" type="text" placeholder="请使用手机号码作为登录密码" class="easyui-text easyui-validatebox" data-options="required:true" value="${user.username}"></td>
                    <td>姓名</td>
                    <td><input name="name" type="text" placeholder="请输入姓名" class="easyui-text easyui-validatebox" data-options="required:true" value="${user.name}"></td>
                </tr>
                <tr>
                    <td>密码</td>
                    <td><input type="text" name="password" class="easyui-text"/></td>
                    <td>性别</td>
                    <td><select id="userEditSex" name="sex" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="1">男</option>
                            <option value="0">女</option>
                            <option value="2">保密</option>
                    </select></td>
                </tr>
                <tr>
                    <td>部门</td>
                    <td><!-- <select id="userEditorganizationId" name="orgId" style="width: 140px; height: 29px;" ></select> -->
                       <input type="hidden" name="orgId" id="userEdit_orgId" value="${user.orgId}">
                        <input type="text" id="userEdit_orgId_disp" class="easyui-text"  readonly="readonly" value="${user.orgName}">
                        <a href="javascript:void(0)" onclick="selectOrg(false,'userEdit_orgId','userEdit_orgId_disp')" class="easyui-linkbutton">选择</a>
                    </td>
<!--                     <td><select id="userEditorganizationId" name="orgId" style="width: 140px; height: 29px;" class="easyui-validatebox" data-options="required:true"></select></td> -->
                    <td>角色</td>
                    <td>
                    <!-- <input  id="userEditRoleIds" name="roleIdList" style="width: 140px; height: 29px;"/> -->
                     <input type="hidden" name="roleIdList" id="userEdit_roleIdList" value="${user.roleIdList}" readonly="readonly" placeholder="请选择"/>
                    <input type="text" class="easyui-text" id="userEdit_roleIdList_disp" name="roleNameList" value="${user.roleNameList}"/>
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectRole(true,'userEdit_roleIdList','userEdit_roleIdList_disp')">选择</a>
                    
                    </td>
                </tr>
                <tr>
                    <td>手机号码</td>
                    <td>
                        <input type="text" name="phone" class="easyui-text easyui-numberbox easyui-validatebox" data-options="required:true" value="${user.phone}" style="height:30px"/>
                    </td>
                    <td>状态</td>
                    <td>
                    <select id="userEditStatus" name="status" value="${user.status}" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="1" >正常</option>
                            <option value="0">停用</option>
                    </select>
                    </td>
                </tr>
                 <tr>
                	<td>直接上级</td>
                	<td>
                		<%-- <input type="hidden" name="superior" value="${user.superior}"/>
                		<input type="text" name="superiorName" class="easyui-text" value="${user.superiorName}"/> --%>
                		<input type="hidden" name="superior" id="userEdit_superior" value="${user.superior}"/>
                		<input type="text" name="superiorName" class="easyui-text" id="userEdit_superior_disp" value="${user.superiorName}" readonly="readonly"/>
                        <a href="javascript:void(0)" onclick="selectUser(false,'userEdit_superior','userEdit_superior_disp')" class="easyui-linkbutton">选择</a>
                	</td>
                		<td>职位</td>
                	<td><!-- <select id="userEditPosition" name="positionId" style="width: 140px; height: 29px;" ></select> -->
                	 <input type="hidden" name="positionId" id="userEdit_positionId" value="${user.positionId}">
                        <input type="text" class="easyui-text" readonly="readonly" id="userEdit_positionId_disp" value="${user.positionName}">
                        <a href="javascript:void(0)" onclick="selectPosition(false,'userEdit_positionId','userEdit_positionId_disp')" class="easyui-linkbutton">选择</a>
                	</td>
                </tr>
               <tr>
                   <td>身份证号码</td>
                   <td colspan="3"><input type="text" class="easyui-text" name="idCard" value="${user.idCard}"></td>
               </tr>
            </table>
        </form>
    </div>
</div>