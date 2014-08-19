<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
  <head>
    <title>My JSP 'test_controller.jsp' starting page</title>
    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="${contextPath}/js/lib/jquery-1.8.0.min.js"></script>
	<script type="text/javascript">
	$(function(){
		
	});
	</script>
  </head>
  
  <body>
    <img id="pic" src="${contextPath}/test/83" style="height:400px;width:500px;"title="精神鉴定图片" />
  </body>
</html>









