<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户登录</title>
    <meta charset="utf8">
    <meta name="viewport" content="width=device-width">
    <%@ include file="/commons/basejs.jsp" %>
    <link rel="stylesheet" type="text/css" href="${staticPath }/static/style/css/login.css?v=201612202107" />
    <script type="text/javascript" src="${staticPath }/static/CryptoJS-v3.1.2/rollups/tripledes.js" charset="utf-8"></script>
    <script type="text/javascript" src="${staticPath }/static/CryptoJS-v3.1.2/components/mode-ecb.js" charset="utf-8"></script>
    <script type="text/javascript" src="${staticPath }/static/login.js?v=20170115" charset="utf-8"></script>
    <style type="text/css">
        .order {
            height: 60px;
            line-height: 60px;
            text-align: center;
        }
        .order .line {
            display: inline-block;
            width: 150px;
            border-top: 1px solid #d3d3d3 ;
        }
        .order .txt {
            color: #686868;
            vertical-align: -4px;
            margin-left: 5px;
            margin-right: 5px;
        }
        .other_img{
            margin-bottom: 15px;
            margin-right: 10px;
            cursor: pointer;
        }
    </style>
</head>
<body onkeydown="enterlogin();" >
<div class="top_div" style="background-image:url(/static/img/login_bg.jpg) no-repeat;background-size:100% 100%;height:50%; position:absolute;top:0;left:0;padding: 0;" >
	<p style="font-size: 45px;font-style: normal;color: white; padding-top: 10%;padding-left: 40%">知识库</p>
<div style="background: rgb(255, 255, 255); margin: 1% auto auto; border: 1px solid rgb(231, 231, 231);border-image:none;width:400px;text-align: center;overflow: hidden">
    <form method="post" id="loginform">
       <%--  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
        <!-- <div style="width: 165px; height: 96px; position: absolute;">
            <div class="tou"></div>
            <div class="initial_left_hand" id="left_hand"></div>
            <div class="initial_right_hand" id="right_hand"></div>
        </div> -->
           <input type="hidden" id="key" value="${sessionScope.encryption_key}">
        <P style="padding: 30px 0px 10px; position: relative;">
            <span class="u_logo"></span>
            <input class="ipt" type="text" name="username" placeholder="请输入登录名"/>
        </P>
        <P style="position: relative;">
            <span class="p_logo"></span>
            <input class="ipt" id="password" type="password" name="password" placeholder="请输入密码"/>
        </P>
        <P style="padding: 10px 0px 10px; position: relative;">
            <input class="captcha" type="text" name="captcha" placeholder="请输入验证码"/>
            <img id="captcha" alt="验证码" src="${path }/captcha.jpg" data-src="${path }/captcha.jpg?t=" style="vertical-align:middle;border-radius:4px;width:94.5px;height:35px;cursor:pointer;">
        </P>
        <!-- <P style="position: relative;text-align: left;">
            <input class="rememberMe" type="checkbox" name="rememberMe" value="1" checked style="vertical-align:middle;margin-left:40px;height:20px;"/> 记住密码
        </P> -->
        <div style="height: 50px; line-height: 50px; margin-top: 10px;border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
            <P style="margin: 0px 35px 20px 45px;">
                <span style="float: left;">
                   <a style="color: rgb(204, 204, 204);" href="${path }/zhgd/resetPassword.jsp">忘记密码?</a> 
                </span>
                <span style="float: right;">
                    <!-- <a style="color: rgb(204, 204, 204); margin-right: 10px;" href="javascript:;">注册</a> -->
                    <a style="background: rgb(0, 142, 173); padding: 7px 10px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;"  href="javascript:;" onclick="submitForm()">登录</a>
                </span>
            </P>
        </div>
       <%--<div class="order">--%>
           <%--<span class="line"></span>--%>
           <%--<span class="txt">其他方式登录</span>--%>
           <%--<span class="line"></span>--%>
       <%--</div>--%>
        <%--<div style="text-align: center;">--%>
            <%--<img src="${staticPath}/static/images/qq.png" alt="qq" title="qq登录" id="qq" class="other_img">--%>
            <%--<img src="${staticPath}/static/images/weixin.png" alt="weixin" title="微信登录" id="weixin" class="other_img">--%>
        <%--</div>--%>
    </form>
</div>
</div>
</body>
</html>
