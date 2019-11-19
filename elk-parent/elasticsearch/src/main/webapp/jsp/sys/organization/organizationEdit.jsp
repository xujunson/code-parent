<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<style>
</style>
<script type="text/javascript">
    $(function() {
        <%--$('#organizationEditPid').combotree({--%>
            <%--url : '${path }/organization/tree?flag=false',--%>
            <%--parentField : 'pid',--%>
            <%--lines : true,--%>
            <%--panelHeight : 'auto',--%>
            <%--value :'${organization.pid}',--%>
            <%--onLoadSuccess:function(){--%>
            	<%--$('#organizationEditPid').combotree('tree').tree('collapseAll');--%>
         <%--}--%>
        <%--});--%>
        
        $('#organizationEditForm').form({
            url : '${path }/organization/edit',
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
                    var form = $('#organizationEditForm');
                    parent.$.messager.alert('提示', eval(result.msg), 'warning');
                }
            }
        });
      
    });
</script>
<div style="padding: 3px;">
    <form id="organizationEditForm" method="post">
        <table class="grid">
            <tr>
                <td>编号</td>
                <td><input name="id" type="hidden"  value="${organization.id}"><input name="code" type="text" value="${organization.code}" class="easyui-text easyui-validatebox" data-options="required:true"/></td>
                <td>机构名称</td>
                <td><input name="name" type="text" value="${organization.name}" placeholder="请输入部门名称" class="easyui-validatebox easyui-text" data-options="required:true" ></td>
            </tr>
            <tr>
                <td>排序</td>
                <td><input name="seq"  class="easyui-numberspinner" value="${organization.seq}" style="widtd: 140px; height: 29px;" required="required" data-options="editable:false"></td>
                <td>菜单图标</td>
                <td ><input name="icon" value="${organization.icon}" class="easyui-text"/></td>
            </tr>
            <tr>
                <td>地址</td>
                <td colspan="3"><input class="easyui-text" name="address" style="width: 300px;" value="${organization.address}"/></td>
            </tr>
            <tr>
                <td>上级部门</td>
                <td colspan="3">
                    <input type="hidden" name="pid" id="orgEdit_Id" value="${organization.pid}">
                    <input type="text" id="orgEdit_Id_disp" class="easyui-text"  readonly="readonly" value="${organization.pName}">
                    <a href="javascript:void(0)" onclick="selectOrg(false,'orgEdit_Id','orgEdit_Id_disp')" class="easyui-linkbutton">选择</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dd"></div>
