<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var roleSelectDataGrid;
    $(function() {
    	var checkedroleids="${roleids}";
    	roleSelectDataGrid = $('#roleSelectDataGrid').datagrid({
            url : '${path }/role/dataGrid',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : ${!flag},
            idField : 'id',
            sortName : 'id',
            sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '100',
                title : 'id',
                field : 'id',
                hidden : true
            }, {
                width : '80',
                title : '名称',
                field : 'name',
                sortable : true
            } ,{
                width : '100',
                title : '编码',
                field : 'code'
            }, {
                width : '80',
                title : '排序号',
                field : 'seq',
                sortable : true
            }, {
                width : '200',
                title : '描述',
                field : 'description'
            } , {
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
            }] ],
            onLoadSuccess:function(data){ 
            	if(data){
            		 if(!isNull(checkedroleids)){
            			varcheckedroleidsArr=checkedroleids.split(',');
	            		$.each(data.rows, function(index, item){
	            			$.each(varcheckedroleidsArr,function(i,item1){
	            				if(item.id==item1){
	            					roleSelectDataGrid.datagrid('checkRow', index);
	            				}
	            			})
	            		});
            		} 
            	}
            },
            toolbar : '#roleSelectToolbar'
        });
    });


    function searchSelectRoleFun(){
    	roleSelectDataGrid.datagrid('load', $.serializeObject($('#searchSelectRoleForm')));
    }
    function cleanSelectRoleFun(){
    	 $('#searchSelectRoleForm input').val('');
    	 roleSelectDataGrid.datagrid('load', {});
    }
   
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="roleSelectDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="roleSelectToolbar" style="display: none;">
 <form id="searchSelectRoleForm">
            <table>
                <tr>
                    <td>角色名称:</td>
                    <td><input name="name" placeholder="" class="easyui-text" style="width:200px"/>
                          <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchSelectRoleFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanSelectRoleFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
</div>
