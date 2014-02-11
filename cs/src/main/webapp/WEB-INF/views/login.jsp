<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>残疾人就业保障金</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/cs.css" />
<script type="text/javascript" src="${contextPath}/js/lib/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/lib/jquery.easyui.min.js"></script>
<script type="text/javascript">
	function getimgcode() {
		var timestamp = new Date().getTime();
		$("#loginVerifyCode").attr("src", "${contextPath}/captcha/create?" + timestamp);
	}
	function submit() {
		$("#loginform").submit();
	}
	$(function(){
		getimgcode();
		$("body").keypress(function(event){
			if(event.keyCode == 13){
                submit();
            }
		});
	});
</script>
<style type="text/css">
p {
	line-height: 30px;
}

img {
	width: 100px;
	height: 30px;
	float: right;
	cursor: pointer;
}
</style>
</head>
<body>
	<div id="main" style=" display: none;">
	
		<div id="win" class="easyui-window" title="登录" style="width:450px; height:350px;" collapsible="false" minimizable="false" maximizable="false" closable="false">
			<form id="loginform" action="${contextPath}/login" method="post" style="padding-left: 80px; padding-top: 30px;">
				<div style="text-align: left;">
					<div style="color: red;">${message}</div>
					<p>
						用户名: <input name="username" type="text" style="width: 160px;" value="${username}"/>
					</p>
					<p>
						密&nbsp;&nbsp;&nbsp;码: <input name="password" type="password" style="width: 160px;" value="${password}" />
					</p>
					<div>
						<div style="height: 30px; width: 230px;">
							验证码: <input name="checkCode" type="text" style="width: 60px; " /> <img id="loginVerifyCode" onclick="getimgcode();" alt="验证码" />
						</div>
					</div>
				</div>
				<div style="padding-left: 40px;">
					<p>
						<a href="javascript:getimgcode();">看不清验证码</a>
					</p>
					<a id="login" href="javascript:submit();" plain="false" class="easyui-linkbutton" icon="icon-ok">登录</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>