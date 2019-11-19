<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户信息</title>
	<%@ include file="/commons/global.jsp" %>
	<%@ include file="/commons/basejs.jsp" %>
	<style type="text/css">
	</style>
	<script type="text/javascript">
	function back() {
		parent.index_tabs.tabs('close', '个人信息');
	}
	function submit(){
	    var id='${user.id}';
	    var name=$('#userinfo_base [name=name]').val();
	    var sex=$('#userinfo_base [name=sex]:checked').val();
	    var orgId=$('#userinfo_base [name=orgId]').val();
	    var username=$('#userinfo_base [name=username]').val();
	    var phone=$('#userinfo_base [name=phone]').val();
	    var idCard=$('#userinfo_base [name=idCard]').val();
	    var superior=$('#userinfo_base [name=superior]').val();
        var superiorName=$('#userinfo_base [name=superiorName]').val();
        var data={id:id,name:name,sex:sex,orgId:orgId,username:username,phone:phone,idCard:idCard,superior:superior,superiorName:superiorName};
	    $.ajax({
			url:'${ctxPath}/user/updatePersonalInfo',
			data:data,
			dataType:'json',
			type:'post',
			success:function(data){
			    if(data.success){
			        showMsg(data.msg);
                }else{
			        $.messager.alert('提示',data.msg,'error');
                }
            }
		})
	}

    function upload_cover(obj) {
        ajaxFileUpload(obj.id);
    }
    /**
	 * 上传图片
     */
    function ajaxFileUpload(file1) {
        if (image_check(file1)) { //自己添加的文件后缀名的验证
            //如果不理解我写的，可以看看我的前几篇文章
            $.ajax({
                url: '${ctxPath}/user/uploadPhoto?id=${user.id}',
                type: "post",
                dataType: "json",
                cache: false,
                data: new FormData($("#formTag1")[0]),
                processData: false,// 不处理数据
                contentType: false, // 不设置内容类型
                success: function(data) {
                    console.log(data)
                    if (data.success) {
                        if (!isNull(data.msg)) {
                            $("#img1").attr("src", '${ctxPath}/upload/readImg?id='+data.msg);
                        }
                    }
                }
            })
		}
    }

    function image_check(feid) { //自己添加的文件后缀名的验证
        var img = document.getElementById(feid);
        return /.(jpg|png|gif|bmp)$/.test(img.value)?true:(function() {
            alert('图片格式仅支持jpg、png、gif、bmp格式，且区分大小写。');
            return false;
        })();
    }
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div  id="userinfo_base" style="padding: 20px">
			<input type="hidden" name="id" value="${user.id}">
			<table class="grid">
				<tr>
					<td colspan="5" style="background:rgb(224, 236, 255);color: #666;"><b>基本信息</b></td>
				</tr>
				<tr>
				<td>姓名</td>
				<td><input type="text" name="name" class="easyui-text easyui-text-2" value="${user.name}"></td>
					<td>账号</td>
					<td><input type="text" name="username" class="easyui-text easyui-text-2" value="${user.username}"></td>
					<td rowspan="4" style="width: 20%">
						<div style="position: relative;bottom: 5px;top:2px;text-align: center">
							<%--<img src="${staticPath}/static/img/member.png" alt="" width="100" height="100" id="img1">--%>
							<%--<input type="file" id="file1" name="file" />--%>
							<%--<button onclick="uploadImg()">上传照片</button>--%>
								<form id="formTag1" enctype="multipart/form-data">
                                    <c:if test="${user.userInfoExt!=null}">
                                        <img id="img1"   src="${ctxPath}/upload/readImg?id=${user.userInfoExt.attachId}"
                                             width="150" height="150" style="cursor: pointer;" />
                                    </c:if>
                                    <c:if test="${user.userInfoExt==null}">
								<img id="img1"   src="${staticPath}/static/img/upload_show.png"
									 width="150" height="150" style="cursor: pointer;" />
                                    </c:if>
								<input id="file1" name="file" type="file" onchange="upload_cover(this)"
									   style="position: absolute; left: 0px; top: 0px; width: 100%; height: 100%; opacity: 0; cursor: pointer;"/>
								</form>
						</div>
					</td>
				</tr>
				<tr>
					<td>部门</td>
					<td>
						<input type="hidden" name="orgId" id="userAdd_orgId" value="${user.orgId}">
						<input type="text" id="userAdd_orgId_disp" class="easyui-text easyui-text-2"  readonly="readonly"  placeholder="请选择" value="${user.orgName}">
						<a href="javascript:void(0)" onclick="selectOrg(true,'userAdd_orgId','userAdd_orgId_disp')" class="easyui-linkbutton">选择</a>
						<a href="javascript:void(0)" onclick="clearDisp('userAdd_orgId','userAdd_orgId_disp')" class="easyui-linkbutton">清除</a>
					</td>
					<td>手机号</td>
					<td><input type="text" name="phone" class="easyui-text easyui-text-2" value="${user.phone}"></td>
				</tr>
				<tr>
					<td>性别</td>
					<td>
						<input type="radio" name="sex" value="1" <c:if test="${user.sex==1}">checked</c:if>>男
						<input type="radio" name="sex" value="0"  <c:if test="${user.sex==0}">checked</c:if>>女
						<input type="radio" name="sex" value="2"  <c:if test="${user.sex==2}">checked</c:if>>保密
					</td>
					<td>角色</td>
					<td>${user.roleNameList}</td>
				</tr>
				<tr>
					<td>身份证</td>
					<td><input type="text" placeholder="请输入" name="idCard" class="easyui-text easyui-text-2" value="${user.idCard}"></td>
					<td>直接上级</td>
					<td>
						<input type="hidden" name="superior" id="userAdd_superior" value="${user.superior}"/>
						<input type="text" name="superiorName" value="${user.superiorName}" class="easyui-text easyui-text-2" id="userAdd_superior_disp" readonly="readonly"  placeholder="请选择"/>
						<a href="javascript:void(0)" onclick="selectUser(false,'userAdd_superior','userAdd_superior_disp')" class="easyui-linkbutton">选择</a>
						<a href="javascript:void(0)" onclick="clearDisp('userAdd_superior','userAdd_superior_disp')" class="easyui-linkbutton">清除</a>
					</td>
				</tr>
			</table>
		</div>
		<div style="text-align: center">
			<a href="javascript:;" class="easyui-linkbutton" onclick="submit()">提交</a>
			<a href="javascript:;" class="easyui-linkbutton" onclick="back()">返回</a>
		</div>
	</div>
</div>
</body>
</html>