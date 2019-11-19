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
    
        <%--$('#userAddOrganizationId').combotree({--%>
            <%--url : '${path }/organization/tree',--%>
            <%--parentField : 'pid',--%>
            <%--lines : true,--%>
            <%--panelHeight : 'auto'--%>
        <%--});--%>
        $('#userAddPosition').combotree({
            url : '${path }/position/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto'
        });



        $('#userAddForm').form({
            url : '${path }/user/add',
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
                    showMsg("添加成功");
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#userAddForm');
                    parent.$.messager.alert('提示', result.msg, 'warning');
                }
            }
        });

    });

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;overflow-y: auto;padding: 3px;">
    	 <div id="dd"></div>
        <form id="userAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>登录名</td>
                    <td><input name="username"  type="text" placeholder="请使用手机号码作为登录名" class="easyui-text easyui-validatebox" data-options="required:true"  value=""></td>
                    <td>姓名</td>
                    <td><input name="name"  type="text" placeholder="请输入姓名" class="easyui-text easyui-validatebox" data-options="required:true" value=""></td>
                </tr>
                <tr>
                    <td>密码</td>
                    <td><input name="password"  type="password" placeholder="请输入密码" class="easyui-text easyui-validatebox" data-options="required:true"></td>
                    <td>性别</td>
                    <td>
                        <select name="sex" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="1" selected="selected">男</option>
                            <option value="0" >女</option>
                            <option value="2" >保密</option>
                        </select>
                    </td>
                </tr>
                
                <tr>
                    <td>部门</td>
                    <td>
                        <input type="hidden" name="orgId" id="userAdd_orgId">
                        <input type="text" id="userAdd_orgId_disp" class="easyui-text easyui-validatebox" data-options="required:true"  readonly="readonly"   placeholder="请选择">
                        <a href="javascript:void(0)" onclick="selectOrg(false,'userAdd_orgId','userAdd_orgId_disp')" class="easyui-linkbutton">选择</a>
                    </td>
                    <td>角色</td>
                    <td>
                    <!-- <select id="userAddRoleIds" name="roleIdList" style="width: 140px; height: 29px;"></select> -->
                    <input type="hidden" name="roleIdList" id="userAdd_roleIdList"/>
                    <input type="text" class="easyui-text easyui-validatebox" data-options="required:true" id="userAdd_roleIdList_disp" name="roleNameList" readonly="readonly" placeholder="请选择" />
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectRole(true,'userAdd_roleIdList','userAdd_roleIdList_disp')">选择</a>
                    </td>
                </tr>
                <tr>
                    <td>手机号码</td>
                    <td>
                        <input type="text" name="phone" class="easyui-text easyui-numberbox easyui-validatebox" data-options="required:true" style="height: 29px;"/>
                    </td>
                    <td>状态</td>
                    <td>
                        <select name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                                <option value="1">正常</option>
                                <option value="0">停用</option>
                        </select>
                    </td>
                </tr>
                <tr>
                	<td>直接上级</td>
                	<td>
                		<input type="hidden" name="superior" id="userAdd_superior"/>
                		<input type="text" name="superiorName" class="easyui-text" id="userAdd_superior_disp" readonly="readonly"  placeholder="请选择"/>
                        <a href="javascript:void(0)" onclick="selectUser(false,'userAdd_superior','userAdd_superior_disp')" class="easyui-linkbutton">选择</a>
                	</td>
                    <td>职位</td>
                    <td>
                        <input type="hidden" name="positionId" id="userAdd_positionId">
                        <input type="text" class="easyui-text" readonly="readonly" id="userAdd_positionId_disp"  placeholder="请选择">
                        <a href="javascript:void(0)" onclick="selectPosition(false,'userAdd_positionId','userAdd_positionId_disp')" class="easyui-linkbutton">选择</a>
                    </td>
                </tr>
                <tr>
                    <td>身份证号码</td>
                    <td colspan="3"><input type="text" class="easyui-text" name="idCard"></td>
                </tr>
                 <tr>
              	<td>附件：</td>
              	<td colspan="3">
                    <input name='attachfile' id="attachfile_input"  type="hidden"/>
                       <div id="attachfile_disp"></div>
                      	<a onclick="attachUpload('attachfile_input','attachfile_disp');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">增加附件</a></div>            	
                    </td>
                  </tr>
            </table>
        </form>
    </div>
</div>