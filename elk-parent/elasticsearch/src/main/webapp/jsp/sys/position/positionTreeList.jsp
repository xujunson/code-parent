<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript">
    var positionTreeGrid;
    $(function() {
        positionTreeGrid = $('#positionTreeGrid').treegrid({
            url : '${path}/position/treeGrid',
            idField : 'id',
            treeField : 'name',
            parentField : 'pid',
            fit : true,
            fitColumns : false,
            border : false,
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
                field : 'pid',
                hidden:true
            },{
                field : 'iconCls',
                title : '图标',
                width : 120
            }, 
                {
                width : '100',
                title : '排序',
                field : 'seq'
            },
                {
                width : '100',
                title : '上级职位',
                field : 'pName',
                hidden:true
            },
		{
                field : 'action',
                title : '操作',
                width : 200,
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="position-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editpositionFun(\'{0}\');" >编辑</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="position-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deletepositionFun(\'{0}\');" >删除</a>', row.id);
                     
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
            	$('#positionTreeGrid').treegrid('collapseAll');
                $('.position-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.position-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#positionToolbar'
        });
    });
    
    function editpositionFun(id) {
        if (id != undefined) {
            positionTreeGrid.treegrid('select', id);
        }
        var node = positionTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.modalDialog({
                title : '编辑',
                width : 500,
                height : 450,
                href : '${path}/position/editPage/' + node.id,
                buttons : [ {
                    text : '编辑',
                    handler : function() {
                        parent.$.modalDialog.openner_treeGrid = positionTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#positionEditForm');
                        f.submit();
                    }
                } ]
            });
        }
    }
    
    function deletepositionFun(id) {
        if (id != undefined) {
            positionTreeGrid.treegrid('select', id);
        }
        var node = positionTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.messager.confirm('询问', '您是否要删除'+node.name+'?', function(b) {
                if (b) {
                    progressLoad();
                    $.post('${path}/position/delete/'+node.id, {
                    }, function(result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            positionTreeGrid.treegrid('reload');
                        }else{
                            parent.$.messager.alert('提示', result.msg, 'info');
                        }
                        progressClose();
                    }, 'JSON');
                }
            });
        }
    }
    
    function addpositionFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 400,
            href : '${path}/position/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_treeGrid = positionTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#positionAddForm');
                    f.submit();
                }
            } ]
        });
    }
    function searchPositionFun(){
    	positionTreeGrid.treegrid('load', $.serializeObject($('#searchPositionForm')));
    }
    function cleanPositionFun(){
    	 $('#searchPositionForm input').val('');
    	 positionTreeGrid.treegrid('load', {});
    }
    
    function positionSXFun(){
    	if($('#searchPositionForm').css('display')=='none'){
    		$('#searchPositionForm').css('display','block');
    	}else{
    		$('#searchPositionForm').css('display','none');
    	}
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false"
		style="overflow: hidden;">
		<table id="positionTreeGrid"></table>
	</div>
	<div id="positionToolbar" style="display: none;">
	<form id="searchPositionForm" style="width:100%;height:30px;display:none">
            <table>
                <tr>
                    <td>职位名称:</td>
                    <td><input name="name" placeholder="" class="easyui-text" style="width:200px"/>
                          <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchPositionFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanPositionFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
		<a onclick="addpositionFun();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
			  <a onclick="positionSXFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-filter icon-green'">筛选</a>
	</div>
</div>