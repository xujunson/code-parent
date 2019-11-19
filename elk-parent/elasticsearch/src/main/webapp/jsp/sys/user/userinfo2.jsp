<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport"
		  content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<title>Title</title>
	<link rel="stylesheet"
		  href="${ctxPath}/static/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet"
		  href="${ctxPath}/static/font-awesome-4.7.0/css/font-awesome.min.css">
	<style type="text/css">
	</style>
</head>
<body>
<div class="container-fluid" style="padding-top: 20px">
	<div class="col-md-4" style="border: 1px solid #337ab7;border-radius: 20px;text-align: center">
		<div style="height: 200px;padding-top: 20px">
			<img src="${ctxPath}/static/img/towercrane.jpg" alt="图片" width="160" height="160">
		</div>
		<button class="btn btn-sm btn-primary">修改头像</button>
		<div style="text-align:left;;border-top: 1px solid #337ab7;margin-top: 10px">
			<b style="font-size: 20px;color: #337ab7;" id="userinfo_name"></b>
			<span style="margin-left: 20px;" id="userinfo_sex"></span>
			<span style="margin-left: 20px;" id="phone">18787080357</span>
			<ul style="list-style: none" id="userinfo_ul">

			</ul>
		</div>
	</div>
</div>
<script type="text/javascript"
		src="${ctxPath}/static/jquery-2.1.4/jquery.min.js"></script>
<script type="text/javascript"
		src="${ctxPath}/static/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript"
		src="${ctxPath}/static/util.js"></script>
<script type="text/javascript">
    $(function() {
        $.ajax({
            url:'${staticPath}/user/getUserInfo',
            type:'post',
            data:{id:'${id}'},
            dataType:'json',
            success:function(data){
                if(!isNull(data)){
                    $('#phone').text(data.phone);
                    $('#userinfo_name').text(data.name);
                    if(data.sex==1){
                        $('#userinfo_sex').text('男');
                    }
                    if(data.sex==0){
                        $('#userinfo_sex').text('女');
                    }
                    if(data.sex==2){
                        $('#userinfo_sex').text('保密');
                    }
                    var html="";
                    if(!isNull(data.orgName)){
                        html+='<li><span style="color:#337ab7; font-size:10px;">●</span> '+data.orgName+'</li>';
                    }
                    if(!isNull(data.positionName)){
                        html+='<li><span style="color:#337ab7; font-size:10px;">●</span> '+data.positionName+'</li>';
                    }
                    if(!isNull(data.superiorName)){
                        html+='<li><span style="color:#337ab7; font-size:10px;">●</span>上级：'+data.superiorName+'</li>';
				    }
				    $('#userinfo_ul').append(html);
                }
			}
        })
    });
</script>
</body>
</html>