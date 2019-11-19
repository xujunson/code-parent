<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<style>
</style>
<script type="text/javascript">
    $(function() {
    	$('#userEditPassForm [name=pwdConfirm]').blur(function(){
    		var pwd=$('#userEditPassForm [name=pwd]').val();
    		var pwdConfirm=$(this).val();
    		if(pwd!=pwdConfirm){
    			$.messager.alert("提示","两次输入的新密码不对！请重新输入",'warning');
    		}
    	})
    	
    	 $('#userEditPassForm').form({
             url : '${path }/user/editUserPwd',
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
                     showMsg(result.msg);
                     parent.$.modalDialog.handler.dialog('close');
                 } else {
                     var form = $('#userEditPassForm');
                     parent.$.messager.alert('提示', result.msg, 'warning');
                 }
             }
         });
    })
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="userEditPassForm" method="post">
            <table class="grid">
                <tr>
                    <td>旧密码</td>
                    <td><input name="oldPwd"  type="password" placeholder="请输入旧密码" class="easyui-text easyui-validatebox" data-options="required:true"  value=""></td>
                </tr>
                <tr>
                    <td>新密码</td>
                    <td><input name="pwd"  type="password" placeholder="请输入新密码" class="easyui-text easyui-validatebox" data-options="required:true"></td>
                </tr>
                <tr>
                    <td>确认密码</td>
                    <td><input name="pwdConfirm"  type="password" placeholder="请输入" class="easyui-text easyui-validatebox" data-options="required:true"></td>
                </tr>
            </table>
        </form>
    </div>
</div>