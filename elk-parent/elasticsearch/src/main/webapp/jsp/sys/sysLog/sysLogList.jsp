<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var sysLogDataGrid;
    $(function () {
        sysLogDataGrid=$('#sysLogDataGrid').datagrid({
            url: '${path }/sysLog/dataGrid',
            striped: true,
            pagination: true,
            singleSelect: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                width: '80',
                title: '登录名',
                field: 'username',
                sortable: true
            }, {
                width: '100',
                title: 'IP地址',
                field: 'clientIp',
            }, {
                width: '100',
                title: '操作类型',
                field: 'optType',
                formatter:function (value,row,index) {
                    if(value=='add'){
                        return '添加';
                    }
                    if(value=='delete'){
                        return '删除';
                    }
                    if(value=='add'){
                        return '添加';
                    }
                    if(value=='edit'){
                        return '修改';
                    }
                    if(value=='授权'){
                        return '授权';
                    }
                    if(value=='login'){
                        return '登录';
                    }
                    if(value=='logout'){
                        return '退出';
                    }
                }
            }, {
                width: '200',
                title: '操作内容',
                field: 'desc'
            },  {
                width: '800',
                title: '操作详情',
                field: 'optContent'
            }, {
                width : '130',
                title : '操作时间',
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
            }]],
            toolbar : '#sysLogToolbar'
        });
    });
    function searchSysLogFun() {
        sysLogDataGrid.datagrid('load', $.serializeObject($('#searchSysLogForm')));
    }
    function cleanSysLogFun() {
        $('#searchSysLogForm input,select').val('');
        sysLogDataGrid.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="sysLogDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="sysLogToolbar" style="display: none;">
    <form id="searchSysLogForm">
        <table>
            <tr>
                <td>姓名:</td>
                <td><input name="username" placeholder="请输入" class="easyui-text" />
                </td>
                <td>操作类型</td>
                <td>
                    <select name="optType" class="easyui-text">
                        <option value="">--请选择--</option>
                        <option value="add">添加</option>
                        <option value="delete">删除</option>
                        <option value="edit">修改</option>
                        <option value="grant">授权</option>
                        <option value="login">登录</option>
                        <option value="logout">退出</option>
                    </select>
                </td>
                <td>操作时间</td>
                <td>
                    <input type="text" name="createTimeStart" class="easyui-text" onclick="WdatePicker()"/>~<input type="text" class="easyui-text" name="createTimeEnd" onclick="WdatePicker()"/>
                </td>
                <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchSysLogFun();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanSysLogFun();">清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>