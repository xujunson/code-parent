<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<link rel="stylesheet" href="${path}/static/mTips/mTips.css" />
<script type="text/javascript" src="${path}/static/mTips/mTips.js"></script>
<script type="text/javascript">
    var userDataGrid;
    var organizationTree;

    $(function() {
        organizationTree = $('#organizationTree').tree({
            url : '${path }/organization/tree',
            parentField : 'pid',
            lines : true,
            onClick : function(node) {
                userDataGrid.datagrid('load', {
                    orgId: node.id
                });
            }
        });

        userDataGrid = $('#userDataGrid').datagrid({
            url : '${path }/user/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'createTime',
	        sortOrder : 'asc',
            pageSize : 20,
            frozenColumns:[[    
            	 {
                     field : 'action',
                     title : '操作',
                     width : 220,
                     formatter : function(value, row, index) {
                          var str = '';
                           str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editUserFun(\'{0}\');" >编辑</a>', row.id);
                                 str += '&nbsp;&nbsp;&nbsp;&nbsp;'; 
                                str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteUserFun(\'{0}\',\'{1}\');" >删除</a>', row.id,row.status);
                                 str += '&nbsp;&nbsp;&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0);" class="user-easyui-linkbutton-view" data-options="iconCls:\'fi-magnifying-glass\',plain:true"\' onclick="viewUserFun(\'{0}\');" >查看</a>', row.id); 
                 		return str;
                     }
                 }
            ]], 
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '80',
                title : '登录名',
                field : 'username',
                sortable : true
            }, {
                width : '80',
                title : '姓名',
                field : 'name',
                sortable : true
            },{
                width : '80',
                title : '部门ID',
                field : 'orgId',
                hidden : true
            },  {
                width : '40',
                title : '性别',
                field : 'sex',
                sortable : true,
                formatter : function(value, row, index) {
                	 switch (value) {
                     case 1:
                         return '男';
                     case 0:
                         return '女';
                     case 2:
                         return '保密';
                    }
                }
            },{
                width : '120',
                title : '手机号码',
                field : 'phone',
                sortable : true
            },{
                width : '100',
                title : '身份证号码',
                field : 'idCard'
            }, {
                width : '80',
                title : '直接上级',
                field : 'superiorName'
            }
            ,{
                width : '80',
                title : '所属部门',
                field : 'orgName'
            }
            ,{
                width : '80',
                title : '职位',
                field : 'positionName'
            },
            {
                width : '200',
                title : '角色',
                field : 'roleNameList',
                formatter:function(value,row,index){
                	/* if(value.indexOf('admin')>-1){
                		return value.replace('admin','超级管理员');
                	} */
                	return value;
                }
            },{
                width : '60',
                title : '状态',
                field : 'status',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 1:
                        return '正常';
                    case 0:
                        return '停用';
                    }
                }
            },{
                width : '130',
                title : '创建时间',
                field : 'createTime',
                formatter : function(value, row, index) {
                	if(value==null){
                  	   	 return "";
                  	 	}
                     var date=new Date(value);
   	               	 var y = date.getFullYear();
   	               	 var m = date.getMonth() + 1;  
   	               	 if(m < 10) m = "0" + m;
   	               	 var d = date.getDate();  
   	               	 if(d < 10) d = "0" + d;
   	               	 var h = date.getHours();
   	               	 if(h < 10) h = "0" + h;
   	               	 var min = date.getMinutes();
   	               	 if(min < 10) min = "0" + min;
   	               	 var s=date.getSeconds();
   	               	 if(s<10)s="0"+s;
                   return y+"/"+m+"/"+d+" "+h+":"+min+":"+s;
                },
                sortable : true
            }, ] ],
            onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.user-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.user-easyui-linkbutton-view').linkbutton({text:'查看'});
            },
            toolbar : '#userToolbar'
        });
    });
    
    function addUserFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 800,
            height : 550,
            href : '${path }/user/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = userDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function deleteUserFun(id,status) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = userDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            userDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
            if (b) {
                progressLoad();
                var url='${path }/user/cancel';
                if(status==0){
                    url='${path }/user/delete';
                }
                $.post(url, {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        userDataGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    
    function editUserFun(id) {
        if (id == undefined) {
            var rows = userDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            userDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 800,
            height : 560,
            href : '${path }/user/editPage?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = userDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userEditForm');
                    f.submit();
                }
            } ]
        });
    }
    function viewUserFun(id) {
        if (id == undefined) {
            var rows = userDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            userDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '查看',
            width : 800,
            height : 450,
            href : '${path }/user/viewPage?id=' + id,
        });
    }
    
    function searchUserFun() {
        userDataGrid.datagrid('load', $.serializeObject($('#searchUserForm')));
    }
    function cleanUserFun() {
        $('#searchUserForm input').val('');
        $('#userList_status').combobox('setValue','1');
        userDataGrid.datagrid('load', {});
    }
    
    function importUserFun(){
    	 parent.$.modalDialog({
             title : '导入用户',
             width : 400,
             height : 300,
             href : '${path }/user/importPage',
            /*  buttons : [ {
                 text : '确定',
                 handler : function() {
                     parent.$.modalDialog.openner_dataGrid = userDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                     parent.$.modalDialog.handler.dialog('close');
                 }
             } ] */
         });
    }
    
    //批量设置用户部门，仅仅针对导入的数据
    function setUserOrgFun(){
    	parent.$.modalDialog({
            title : '分配用户部门',
            width : 500,
            height : 600,
            href : '${path }/user/tosetUserOrg',
             buttons : [ {
                text : '确定',
                handler : function() {
                	 parent.$.modalDialog.openner_dataGrid = userDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                     var f = parent.$.modalDialog.handler.find('#userOrgForm');
                     f.submit();
                }
            } ] 
        });
    }
    
    function genUserQrcode(){
    	 var rows = userDataGrid.datagrid('getSelections');
         if(rows.length<=0){
        	 $.messager.alert('提示','请选择要生成二维码的记录！','info')
         }
    	 if(rows.length>0){
    		 id=rows[0].qrcodeId;
    		 fId=rows[0].id;
    		 type="user";
    		 var url=basePath+'/qrcode/genQrcode?id='+id+'&fId='+fId+'&type='+type;
    		 if(id==null){
    			url= basePath+'/qrcode/genQrcode?fId='+fId+'&type='+type;
    		 }
    		 $.ajax({
    			 url:url,
    			 dataType:'json',
    			 success:function(result){
    				 if(result.success){
    					 showMsg(result.msg);
    					 searchUserFun();
    				 }else{
    					 $.messager.alert('错误', result.msg, 'error');
    				 }
    			 }
    			 
    		 })
    	 }
    }
    function downloadUserQrcode(){
    	 var rows = userDataGrid.datagrid('getSelections');
         if(rows.length<=0){
        	 $.messager.alert('提示','请选择要下载二维码的记录！','info')
         }
    	 if(rows.length>0){
    		 id=rows[0].qrcodeId;
    		 window.location.href="${ctxPath}/qrcode/downloadQrcode?id="+id;
    	 }
    }
    
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
    
        <form id="searchUserForm">
            <table>
                <tr>
                    <th>姓名:</th>
                    <td><input name="name" placeholder="请输入用户姓名" class="easyui-textbox" /></td>
                    <th>状态:</th>
                    <td><select name="status" id="userList_status" class="easyui-combobox" data-options="width:140,editable:false,panelHeight:'auto'">
                    	<option value="1" selected>有效</option>
                    	<option value="0">无效</option>
                    </select></td>
                    <th>手机号码:</th>
                    <td>
                        <input name="phone" placeholder="请输入手机号码" class="easyui-textbox"/>
                          <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchUserFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanUserFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
     <div data-options="region:'center',border:true,title:'用户列表'" >
        <table id="userDataGrid" data-options="fit:true,border:false"></table>
    </div> 
    <div data-options="region:'west',border:true,split:true,title:'组织机构'"  style="width:200px;overflow: hidden; overflow-y:auto; padding:0px">
        <ul id="organizationTree" style="width:160px;margin: 10px 10px 10px 10px"></ul>
    </div>
</div>
<div id="userToolbar" style="display: none;">
   <%--  <shiro:hasPermission name="/user/add"> --%>
        <a onclick="addUserFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
      <%-- </shiro:hasPermission> --%>
</div>