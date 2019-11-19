<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow-x: hidden; overflow-y: auto; padding: 3px;">
		<table class="grid">
			<tr>
				<td><b>职位</b></td>
				<td>${sysPosition.name}</td>
				<td><b>排序</b></td>
				<td>${sysPosition.seq}</td>
				</tr><tr>
				<td><b>上级职位</b></td>
				<td colspan="3">${sysPosition.pName}</td>
			</tr>
		</table>
	</div>
</div>