<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<style>
.easyui-text{
	width: 140px; height: 25px;border-radius:5px;border:thin solid #ddd;
	border-color:#a4e9c1;
}
.easyui-text:focus{
	outline: none;
}
</style>
<script type="text/javascript">
    var positionDataGrid;
    $(function() {
        positionDataGrid = $('#positionDataGrid').datagrid({
            url : '${path}/position/dataGrid',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'id',
            sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [
                {
                width : '100',
                title : '注解id',
                field : 'id'
                ,hidden : true
            },
                {
                width : '100',
                title : '职位',
                field : 'name'
            },
                {
                width : '100',
                title : '父id',
                field : 'pid'
            },
                {
                width : '100',
                title : '排序',
                field : 'seq'
            },
                {
                width : '100',
                title : '上级职位',
                field : 'pName'
            },
            ]],
            frozenColumns : [ [{
                field : 'action',
                title : '操作',
                width : 200,
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="position-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editpositionFun(\'{0}\');" >编辑</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="position-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deletepositionFun(\'{0}\');" >删除</a>', row.id);
                      str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="position-easyui-linkbutton-view" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="viewpositionFun(\'{0}\');" >查看</a>', row.id);
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.position-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.position-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.position-easyui-linkbutton-view').linkbutton({text:'查看'});
            },
            toolbar : '#positionToolbar'
        });
    });

    function addpositionFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 300,
            href : '${path}/position/addPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = positionDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#positionAddForm');
                    f.submit();
                }
            } ]
        });
    }

    function editpositionFun(id) {
        if (id == undefined) {
            var rows = positionDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            positionDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 500,
            height : 300,
            href : '${path}/position/editPage/' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = positionDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#positionEditForm');
                    f.submit();
                }
            } ]
        });
    }

    function deletepositionFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = positionDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            positionDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除吗？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path}/position/delete/'+id, {}
                , function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        positionDataGrid.datagrid('reload');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
     function viewpositionFun(id) {
        if (id == undefined) {
            var rows =  positionDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
             positionDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '查看',
            width : 800,
            height : 450,
            href : '${path}/position/viewPage/' + id,
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
                        <div data-options="region:'center',border:true">
        <table id="positionDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="positionToolbar" style="display: none;">
    <a onclick="addpositionFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
</div>
