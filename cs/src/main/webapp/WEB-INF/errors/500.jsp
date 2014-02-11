<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>500</title>

<link href="${contextPath}/css/404.css" rel="stylesheet" type="text/css" />

</head>

<body>

	<div class="top_404">&nbsp;</div>
	<div class="photo_404">
		<img src="${contextPath}/images/error/500.gif" width="500" height="380" />
	</div>

	<div class="wz_404">
		<a href="${contextPath}/">&gt;&gt;返回首页</a> <a href="javascript:history.back();">&gt;返回上一页</a>
	</div>
	<div class="line_404">&nbsp;</div>

</body>
</html>
