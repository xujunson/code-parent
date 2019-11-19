<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var organizationTreeGrid;
    $(function() {
        organizationTreeGrid = $('#organizationTreeGrid').treegrid({
            url : '${path }/organization/treeGrid',
            idField : 'id',
            treeField : 'name',
            parentField : 'pid',
            fit : true,
            fitColumns : false,
            border : false,
            frozenColumns : [ [ {
                title : 'id',
                field : 'id',
                width : 40,
                hidden : true
            } ] ],
            columns : [ [ {
                field : 'code',
                title : '编号',
                width : 40
            },{
                field : 'name',
                title : '部门名称',
                width : 180
            }, {
                field : 'seq',
                title : '排序',
                width : 40
            }, {
                field : 'iconCls',
                title : '图标',
                width : 120
            },  {
                field : 'address',
                title : '地址',
                width : 120
            } , {
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
                }
            },{
                field : 'pid',
                title : '上级资源ID',
                width : 150,
                hidden : true
            },{
                field : 'action',
                title : '操作',
                width : 130,
                formatter : function(value, row, index) {
                    var str = '';
                            str += $.formatString('<a href="javascript:void(0)" class="organization-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editOrganizationFun(\'{0}\');" >编辑</a>', row.id);
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="organization-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteOrganizationFun(\'{0}\');" >删除</a>', row.id);
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
            	$('#organizationTreeGrid').treegrid('collapseAll');
                $('.organization-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.organization-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#orgToolbar'
        });
    });
    
    function editOrganizationFun(id) {
        if (id != undefined) {
            organizationTreeGrid.treegrid('select', id);
        }
        var node = organizationTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.modalDialog({
                title : '编辑',
                width : 500,
                height : 450,
                href : '${path }/organization/editPage?id=' + node.id,
                buttons : [ {
                    text : '编辑',
                    handler : function() {
                        parent.$.modalDialog.openner_treeGrid = organizationTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#organizationEditForm');
                        f.submit();
                    }
                } ]
            });
        }
    }
    
    function deleteOrganizationFun(id) {
        if (id != undefined) {
            organizationTreeGrid.treegrid('select', id);
        }
        var node = organizationTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.messager.confirm('询问', '您是否要删除'+node.name+'部门?', function(b) {
                if (b) {
                    progressLoad();
                    $.post('${path }/organization/delete', {
                        id : node.id
                    }, function(result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            organizationTreeGrid.treegrid('reload');
                        }else{
                            parent.$.messager.alert('提示', result.msg, 'info');
                        }
                        progressClose();
                    }, 'JSON');
                }
            });
        }
    }
    
    function addOrganizationFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 400,
            href : '${path }/organization/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_treeGrid = organizationTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#organizationAddForm');
                    f.submit();
                }
            } ]
        });
    }
    function searchOrgFun(){
    	organizationTreeGrid.treegrid('load', $.serializeObject($('#searchOrgForm')));
    }
    function cleanOrgFun(){
    	 $('#searchOrgForm input').val('');
    	 organizationTreeGrid.treegrid('load', {});
    }
    
    function orgSXFun(){
    	if($('#searchOrgForm').css('display')=='none'){
    		$('#searchOrgForm').css('display','block');
    	}else{
    		$('#searchOrgForm').css('display','none');
    	}
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false"  style="overflow: hidden;">
        <table id="organizationTreeGrid"></table>
    </div>
    <div id="orgToolbar" style="display: none;">
    <form id="searchOrgForm" style="width:100%;height:30px;display:none">
            <table>
                <tr>
                    <td>部门名称:</td>
                    <td><input name="name" placeholder="" class="easyui-text" style="width:200px"/>
                          <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchOrgFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanOrgFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
            <a onclick="addOrganizationFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
             <a onclick="orgSXFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-filter icon-green'">筛选</a>
    </div>
</div>