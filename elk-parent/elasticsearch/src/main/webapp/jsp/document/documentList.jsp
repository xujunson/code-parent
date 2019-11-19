<%--
  Created by IntelliJ IDEA.
  User: lenovo-
  Date: 2019/3/26
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--    <script type="text/javascript" src="${staticPath}/static/ckeditor4.10/ckeditor.js"></script>--%>
    <style>
        p img {
            width: auto;
            height: auto;
            max-width: 100%;
        }
    </style>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="documentDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="docToolbar" style="display: none;">
    <form id="searchDocForm">
        <table>
            <tr>
                <td>标题</td>
                <td>
                    <input name="title"   type="text" placeholder="请输入标题" class="easyui-text  span2" >
                </td>
                <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchDoc();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanDoc();">清空</a>
                </td></tr>

        </table>
    </form>
    <a onclick="addDoc();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
</div>
<script type="text/javascript">
    var documentDataGrid;
    $(function() {
        documentDataGrid = $('#documentDataGrid').datagrid({
            url : '${path}/document/dataGrid',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'fileId',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [
                {
                    width : '300',
                    title : '标题',
                    field : 'title'
                },
                {
                    width : '400',
                    title : '描述',
                    field : 'description'
                },
                {
                    width : '200',
                    title : '标签',
                    field : 'tags'
                },
                {
                    width : '200',
                    title : '类型',
                    field : 'typeName'
                },
                {
                    width : '120',
                    title : '创建时间',
                    field : 'agentStarttime',
                    formatter:function (value,row,index) {
                        return datetime2Str(value);
                    }
                }
            ]],
            frozenColumns : [ [{
                field : 'action',
                title : '操作',
                width : 150,
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="doc-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteDoc(\'{0}\');" >删除</a>', row.fileId);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="doc-easyui-linkbutton-view" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="viewDoc(\'{0}\',\'{1}\');" >查看</a>', row.fileId,row.title);
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.doc-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.doc-easyui-linkbutton-view').linkbutton({text:'查看'});
            },
            toolbar : '#docToolbar'
        });
    });

    function addDoc() {
        parent.$.modalDialog({
            title : '添加',
            width : '80%',
            height : '80%',
            href : '${path}/document/toAdd',
            buttons : [ {
                text : '保存',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = documentDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#docAddForm');
                    f.submit();
                }
            } ]
        });
    }


    function deleteDoc(fileId) {
        if (fileId == undefined) {//点击右键菜单才会触发这个
            var rows = documentDataGrid.datagrid('getSelections');
            fileId = rows[0].fileId;
        } else {//点击操作里面的删除图标会触发这个
            documentDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除吗？', function(b) {
            if (b) {
                progressLoad();
                var url='${path}/document/delete?fileId='+fileId;
                $.post(url, {}
                    , function(result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            documentDataGrid.datagrid('reload');
                        }else {
                            $.messager.alert('提示',data.msg,'error');
                        }
                        progressClose();
                    }, 'JSON');
            }
        });
    }
    function viewDoc(fileId,title) {
        if (fileId == undefined) {
            var rows =  documentDataGrid.datagrid('getSelections');
            fileId = rows[0].fileId;
        } else {
            documentDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        var opts = {
            title : '查看'+title,
            border : false,
            closable : true,
            fit : true,
            href : '${path}/document/viewPage/' + fileId,
        };
        addTab(opts);
    }
    function searchDoc() {
        documentDataGrid.datagrid('load', $.serializeObject($('#searchDocForm')));
    }
    function cleanDoc() {
        $('#searchDocForm input').val('');
        documentDataGrid.datagrid('load', {});
    }

</script>
