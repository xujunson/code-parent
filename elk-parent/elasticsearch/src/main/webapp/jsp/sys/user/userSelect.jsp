<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" src="${staticPath }/static/util.js" charset="utf-8"></script>
<script type="text/javascript">
    var userDataGrid1;
    $(function() {
        userDataGrid1 = $('#userDataGrid1').datagrid({
            url : '${path }/user/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'id',
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
                    }
                }
            },{
                width : '120',
                title : '手机号码',
                field : 'phone',
                sortable : true
            }, {
                width : '80',
                title : '所属部门',
                field : 'orgName'
            }
            ,
            {
                width : '200',
                title : '角色',
                field : 'roleNameList'
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
            }] ]
        });

    });
    
    
    function searchUserFun1() {
        userDataGrid1.datagrid('load', $.serializeObject($('#searchUserForm1')));
    }
    function cleanUserFun1() {
        $('#searchUserForm1 input').val('');
        userDataGrid1.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchUserForm1">
            <table>
                <tr>
                    <th>姓名:</th>
                    <td><input name="name" placeholder="请输入用户姓名" class="easyui-textbox"/></td>
                    <th>手机号码:</th>
                    <td>
                        <input name="phone" placeholder="请输入手机号码" class="easyui-textbox"/>
                          <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchUserFun1();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanUserFun1();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'用户列表'" >
        <table id="userDataGrid1" data-options="fit:true,border:false"></table>
    </div>
</div>
