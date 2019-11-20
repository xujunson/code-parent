<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<style>
</style>
<script type="text/javascript">
    $(function() {
        /*CKEDITOR.replace( 'docAdd_content',{
            language : 'zh-cn'
        });
*/
        getDocType('','docAdd_classicId');
        var docAdd_classicId=$('#docAdd_classicId').val();
        if(docAdd_classicId != ''){
            getDocType(docAdd_classicId,'docAdd_typeId');
        }
        $('#docAdd_classicId').change(function(){
            docAdd_classicId=$(this).val();
            if(docAdd_classicId != '') {
                getDocType(docAdd_classicId, 'docAdd_typeId');
            }else{
                $('#docAdd_typeId').empty();
                $('#docAdd_typeId').append('<option value="">请选择</option>');
            }
        })
        $('#docAddForm').form({
            url : '${path}/document/addDoc',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    showMsg(result.msg);
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#docAddForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });

    function getDocType(pid,divId){
        $.ajax({
            url:'${ctxPath}/document/queryDocumentType',
            type:'post',
            data:{pid:pid},
            dataType:'json',
            success:function(data){
                if(!isNull(data)){
                    $('#'+divId).empty();
                    $('#'+divId).append('<option value="">请选择</option>');
                    var html="";
                    if(data.length > 0){
                        for(var i=0; i<data.length; i++){
                            html+='<option value="'+data[i].id+'">'+data[i].typeName+'</option>';
                        }
                        $('#'+divId).append(html);
                    }
                }
            }
        })
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow-x:hidden;overflow-y: auto;padding: 3px;" >
        <form id="docAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>标题</td>
                    <td colspan="3">
                        <input name='title'   type='text' placeholder='请输入标题' class='easyui-text span2 easyui-validatebox' style="width: 90%" data-options="required:true">
                    </td>
                </tr>
                <tr>
                <td>文件类型</td>
                <td>
                    <select name="classicId" id="docAdd_classicId" class="easyui-text easyui-validatebox" data-options="required:true">
                        <option value="">请选择</option>
                        <option value="1">1</option>
                    </select>
                    <select name="typeId" id="docAdd_typeId" class="easyui-text">
                        <option value="">请选择</option>
                        <option value="2">2</option>
                    </select>&nbsp;&nbsp;&nbsp;
                </td>
            </tr>
                <tr>
                    <td>描述/简介</td>
                    <td colspan="3">
                        <textarea name='description' class="easyui-text" id='docAdd_content' style="min-height: 150px;width: 90%"></textarea>
                    </td>
                </tr>

                <tr>
                    <td>附件</td>
                    <td colspan="3">
                        <input name='attachId' id="docAdd_attachfile_input"  type="hidden"/>
                        <div id="docAdd_attachfile_input_disp"></div>
                        <a onclick="attachUpload('docAdd_attachfile_input','docAdd_attachfile_input_disp');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">上传附件</a>
                        <p style="color: red">* 上传附件只能上传一个</p>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>