<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="userEditForm" method="post">
           <table class="grid">
                <tr>
                    <td><b>登录名</b></td>
                    <td><input name="id" type="hidden"  value="${user.id}">
                    <span>${user.username}</</span></td>
                    <td><b>姓名</b></td>
                    <td><span>${user.name}</span></td>
                </tr>
                <tr>
                    <td><b>密码</b></td>
                    <td>*****</td>
                    <td><b>性别</b></td>
                    <td>${user.sex=='0'?'女':'男'}</td>
                </tr>
                <tr>
                    <td><b>部门</b></td>
                    <td>${user.orgName}</td>
                    <td><b>角色</b></td>
                    <td>${user.roleNameList}</td>
                </tr>
                <tr>
                    <td><b>电话</b></td>
                    <td>
                    ${user.phone}
                    </td>
                    <!-- <td><b>用户类型</b></td> -->
                     <td><b>用户状态</b></td> 
                    <td>${user.status=='1'?'正常':'停用'}</td>
                </tr>
                
            </table>
        </form>
    </div>
</div>