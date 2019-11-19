<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<link rel="stylesheet" href="${path}/static/huploadify/Huploadify.css" />
<script type="text/javascript" src="${path}/static/huploadify/jquery.Huploadify.js"></script>
<style>
.easyui-text{
	width: 140px; height: 25px;border-radius:5px;border:thin solid #ddd;
	border-color:#a4e9c1;
}
.easyui-text:focus{
	outline: none;
	border-color:#a4e9c1;
}

</style>
<script type="text/javascript">
    $(function() {
    	 $('#setUserOrg01').combotree({
             url : '${path }/organization/tree',
             parentField : 'pid',
             lines : true,
             panelHeight : 'auto'
         });

         $('#userSelectAll').bind('click',function(){
        	 var $this=$(this);
     		if($this.prop('checked')){
     			$('#userOrgTable tbody .userSelect').prop("checked",true);
     		}else{
     			$('#userOrgTable tbody .userSelect').prop("checked",false);
     		}

          });

         $('#userOrgForm').form({
             url : '${path }/user/setUserOrg',
             onSubmit : function() {
                 progressLoad();
                 var isValid = $(this).form('validate');
                 if (!isValid) {
                     progressClose();
                 }
                 return isValid;
             },
             queryParams:{userids:function(){
                 var userids=new Array();
            	   $('#userOrgTable .userSelect:checked').each(function(){
                	   userids.push($(this).val());
                  })
                  return userids.join(',');
                 }},
             success : function(result) {
                 progressClose();
                 result = $.parseJSON(result);
                 if (result.success) {
                     showMsg("成功");
                     parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                     parent.$.modalDialog.handler.dialog('close');
                 } else {
                     var form = $('#userOrgForm');
                     parent.$.messager.alert('提示', result.msg, 'warning');
                 }
             }
         });
    });
    function viewUserOrg(id){
    	var dialog=$('#dd').dialog({    
    	    title: '查看',    
             width : 800,
             height : 450,
             href : '${path }/user/viewPage?id=' + id,
         });
     }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
<div id="dd"></div>
<form method="post" id="userOrgForm">
    <div data-options="region:'north',border:false" title="" style="overflow: hidden;padding: 3px;">
     <span style="color:#0E2D5F"><b>部门</b> </span>&nbsp;&nbsp;&nbsp; <select id="setUserOrg01" name="orgid" style="width: 240px; height: 29px;" class="easyui-validatebox" data-options="required:true"></select>
      <br />
           </div>
     <div data-options="region:'center',border:true,collapse:false,title:'人员列表'" style="height:400px;" >
     <table class="grid" id="userOrgTable">
      	<thead>
      		<tr>
      			<th><input type="checkbox" id="userSelectAll"/></th>
      			<th>姓名</th>
      			<th>性别</th>
      			<th>年龄</th>
      			<th>职位/工种</th>
      			<th>操作</th>
      		</tr>
      	</thead>
      	<tbody>
      	<c:if test="${users.size()>0 }">
      		<c:forEach  items="${users}" var="user">
      		  <tr>
	      	  	<td><input type="checkbox" class="userSelect" value="${user.id}"></td>
	      	  	<td>${user.name}</td>
	      	  	<td>${user.sex==1?'男':'女'}</td>
	      	  	<td>${user.age}</td>
	      	  	<td>${user.workType}</td>
	      	  	<td><a href="javascript:void(0)"  class="easyui-linkbutton" onclick="viewUserOrg('${user.id}')">查看</a></td>
	      	  </tr>
      		</c:forEach>
      	</c:if>
      	</tbody>
      </table> 
    </div>
    </form>
</div>