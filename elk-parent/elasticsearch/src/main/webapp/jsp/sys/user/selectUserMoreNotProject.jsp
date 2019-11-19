<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" src="${staticPath }/static/util.js" charset="utf-8"></script>
<script type="text/javascript">
/* 多选 */
    var userDataGrid122;
    $(function() {
        userDataGrid122 = $('#userDataGrid122').datagrid({
            url : '${path }/user/dataGridNotProject?projectId=${projectId}',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : false,
            idField : 'id',
            sortName : 'createTime',
	        sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
				field : 'ck',
				checkbox : true
			} ] ],
			 columns : [ [ {
	                width : '80',
	                title : '登录名',
	                field : 'username',
	                sortable : true,
	                hidden:true
	            }, {
	                width : '80',
	                title : '姓名',
	                field : 'name',
	                sortable : true
	            },{
	                width : '80',
	                title : '部门ID',
	                field : 'organizationId',
	                hidden : true
	            },{
	                width : '80',
	                title : '所属部门',
	                field : 'organizationName'
	            },{
	                width : '130',
	                title : '创建时间',
	                field : 'createTime',
	                hidden:true,
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
	            },  {
	                width : '40',
	                title : '性别',
	                field : 'sex',
	                sortable : true,
	                formatter : function(value, row, index) {
	                    switch (value) {
	                    case 0:
	                        return '女';
	                    case 1:
	                        return '男';
	                    }
	                }
	            }, {
	                width : '40',
	                title : '年龄',
	                field : 'age',
	                sortable : true,
	                hidden:true
	            },
	            {
	                width : '80',
	                title : '出生日期',
	                field : 'birthday',
	                hidden:true,
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
	                    return y+"/"+m+"/"+d;
	                 },
	                sortable : true,
	                hidden:true
	            },
	            {
	                width : '40',
	                title : '民族',
	                field : 'national',
	                sortable : true,
	                hidden:true
	            },
	            {
	                width : '100',
	                title : '身份证号码',
	                field : 'idNum',
	                sortable : true,
	                hidden:true
	            },
	            {
	                width : '100',
	                title : '进场日期',
	                field : 'enterDay',
	                hidden:true,
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
	                    return y+"/"+m+"/"+d;
	                 },
	                sortable : true,
	                hidden:true
	            },
	            {
	                width : '100',
	                title : '文化程度',
	                field : 'education',
	                sortable : true,
	                hidden:true
	            },
	            {
	                width : '100',
	                title : '直接上级',
	                field : 'leaderName',
	                sortable : true,
	                hidden:true
	            },
	            {
	                width : '100',
	                title : '工号',
	                field : 'workNo',
	                hidden:true,
	            },
	            {
	                width : '100',
	                /* title : '职位/工种', */
	                 title : '岗位', 
	                
	                field : 'workType',
	                sortable : true
	            },
	            {
	                width : '100',
	                title : '所属项目',
	                field : 'projectName',
	                sortable : true
	            }
	            ,{
	                width : '120',
	                title : '电话',
	                field : 'phone',
	                sortable : true
	            }, 
	            {
	                width : '200',
	                title : '角色',
	                field : 'roleNameList',
	                hidden:true
	            }, {
	                width : '60',
	                title : '用户类型',
	                field : 'userTypeName',
	               /*  sortable : true,
	                formatter : function(value, row, index) {
	                    if(value == 0) {
	                        return "管理员";
	                    }else if(value == 1) {
	                        return "用户";
	                    }
	                    return "未知类型";
	                } */
	            },{
	                width : '60',
	                title : '状态',
	                field : 'status',
	                sortable : true,
	                hidden:true,
	                formatter : function(value, row, index) {
	                    switch (value) {
	                    case 1:
	                        return '正常';
	                    case 0:
	                        return '停用';
	                    }
	                }
	            } ] ]
        });

    });
    
    
    function searchUserFun112() {
        userDataGrid122.datagrid('load', $.serializeObject($('#searchUserForm112')));
    }
    function cleanUserFun112() {
        $('#searchUserForm112 input').val('');
        userDataGrid122.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchUserForm112">
            <table>
                <tr>
                    <th>姓名:</th>
                    <td><input name="name" placeholder="请输入用户姓名" class="easyui-textbox"/></td>
                    <th>手机号码:</th>
                    <td>
                        <input name="phone" placeholder="请输入手机号码" class="easyui-textbox"/>
                          <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchUserFun112();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanUserFun112();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'用户列表'" >
        <table id="userDataGrid122" data-options="fit:true,border:false"></table>
    </div>
</div>
