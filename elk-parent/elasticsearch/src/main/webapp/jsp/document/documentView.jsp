<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<style>
</style>
<script type="text/javascript">
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow-x:hidden;overflow-y: auto;padding: 3px;" >
            <table class="grid">
                <tr>
                    <td>标题</td>
                    <td colspan="3">
                        ${document.title}
                    </td>
                </tr>
                <tr>
                    <td>文件类型</td>
                    <td>
                        ${document.typeName}
                    </td>
                </tr>
                <tr>
                    <td>描述/简介</td>
                    <td colspan="3">
                        ${document.description}
                    </td>
                </tr>
                <tr>
                    <td>内容</td>
                    <td colspan="3">
                        ${document.contentbody}
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>