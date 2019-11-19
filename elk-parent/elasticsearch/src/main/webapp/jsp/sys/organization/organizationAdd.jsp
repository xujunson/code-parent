<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<style>
</style>
<script type="text/javascript">
    $(function() {
        $('#organizationAddPid').combotree({
            url : '${path }/organization/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            onLoadSuccess:function(){
            	  $('#organizationAddPid').combotree('tree').tree('collapseAll');
           }
        });
        
        $('#organizationAddForm').form({
            url : '${path }/organization/add',
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
                    parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为organization.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#organizationAddForm');
                    parent.$.messager.alert('提示', eval(result.msg), 'warning');
                }
            }
        });
        
       
        
    });
</script>
<div style="padding: 3px;">
    <form id="organizationAddForm" method="post">
        <table class="grid">
            <tr>
                <td>编号</td>
                <td><input name="code" type="text" placeholder="请输入部门编号" class="easyui-validatebox easyui-text" data-options="required:true" ></td>
                <td>部门名称</td>
                <td><input name="name" type="text" placeholder="请输入部门名称" class="easyui-validatebox easyui-text" data-options="required:true" ></td>
                
            </tr>
            <tr>
                <td>排序</td>
                <td><input name="seq" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false" value="0"></td>
                <td>菜单图标</td>
                <td><input name="icon" value="fi-folder" class="easyui-text"/></td>
            </tr>
            <tr>
                <td>地址</td>
                <td colspan="3"><input name="address" style="width: 300px;" class="easyui-text"/></td>
            </tr>
          
            <tr>
                <td>上级部门</td>
                <td colspan="3">
                    <input type="hidden" name="pid" id="orgAdd_Id">
                    <input type="text" id="orgAdd_Id_disp" class="easyui-text"  readonly="readonly">
                    <a href="javascript:void(0)" onclick="selectOrg(false,'orgAdd_Id','orgAdd_Id_disp')" class="easyui-linkbutton">选择</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dd"></div>