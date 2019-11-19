<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<style>
.easyui-text{
	width: 140px; height: 25px;border-radius:5px;border:thin solid #ddd;
	border-color:#95B8E7;
}
.easyui-text:focus{
	outline: none;
}
</style>
<script type="text/javascript">
    $(function() {

        $('#positionAddForm').form({
            url : '${path}/position/add',
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
                    parent.$.modalDialog.openner_treeGrid.treegrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                 } else {
                    var form = $('#positionAddForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow-x:hidden;overflow-y: auto;padding: 3px;" >
        <form id="positionAddForm" method="post">
            <table class="grid">
 				<tr>
                     <td>职位</td>
                      <td>
                        <input name='name' id='name'  type='text' placeholder='请输入职位' class='easyui-text  span2' >
                     </td>
                     <td>排序</td>
                      <td>
                        <input name='seq' id='seq'  type='text' placeholder='请输入排序' class='easyui-text  span2' >
                     </td>
                     </tr>
                     <tr>
                      <td>图标</td>
               		   <td colspan="3"><input name="iconCls" value="fi-folder" class="easyui-text"/></td>
                     </tr>
					 <tr>
		  		    <td>上级职位</td>
                         <td colspan="3">
                             <input type="hidden" name="pid" id="positionAdd_positionId">
                             <input type="text" class="easyui-text" readonly="readonly" id="positionAdd_positionId_disp">
                             <a href="javascript:void(0)" onclick="selectPosition(false,'positionAdd_positionId','positionAdd_positionId_disp')" class="easyui-linkbutton">选择</a>
                         </td>
		            </tr>
            </table>
        </form>
    </div>
</div>