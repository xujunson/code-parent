<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<link rel="stylesheet" href="${path}/static/huploadify/Huploadify.css" />
<script type="text/javascript" src="${path}/static/huploadify/jquery.Huploadify.js"></script>
<style>
#importUser_upload01 .uploadify-button{
	width:50px!important;
}
#importUser_upload01 .uploadify-progress{
	width:350px!important;
}
</style>
<script type="text/javascript">
	
    $(function() {
    	  $('#importUser_upload01').Huploadify({
        	 auto:false,  //是否开启自动上传
        	 fileTypeExts:'*.xls;*.xlsx', //类型限制
             multi:false,  
             fileObjName:'Filedata',  //在后端接受的文件参数
             fileSizeLimit:99999999999,//允许上传大小  
             buttonText:'导入',  
             showUploadedPercent:true,//上传百分比
             showUploadedSize:true,//实时显示上传文件大小
             uploader:basePath+'/user/importData',
                //上传到服务器，服务器返回相应信息到data里
               onUploadSuccess:function(file,data){  //上传成功操作
                	data=$.parseJSON(data);
                   if(data.success){
                	  showMsg("导入成功！请刷新用户列表！");
                	 
                    }else{
                    	showMsg("导入失败！");
                     }
                },
              	//当单个文件上传出错时触发
               onUploadError:function(file,response){ 
                	//alert("上传失败");
                } 
            }); 
    });
    
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
    <div class="" style="text-align: center;">
    	<a href="/user/downloadTemplete" >下载用户导入模板</a><br />
    	<div id="importUser_upload01"></div>
    	</div>
    </div>
</div>