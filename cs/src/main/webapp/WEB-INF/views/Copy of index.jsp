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
<script type="text/javascript" src="${contextPath}/js/main.js"></script>
</head>
<body class="easyui-layout">

	<!-- 上部分 -->
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px"></div>

	<!-- 左侧菜单 -->
	<div data-options="region:'west',split:true,title:'目录'" style="width:260px;padding:10px;">
		<!-- 主菜单 -->
		<ul id="main_menu" data-options="url:'menu'"></ul>

	</div>


	<!-- 右侧隐藏栏 -->
	<div data-options="region:'east',split:true,collapsed:true,title:'帮助'" style="width:100px;padding:10px;">east region</div>
	<!-- 底部 -->
	<div data-options="region:'south',border:true" style="height:40px;text-align:center; padding:10px;">
		<div>© 2013 hrbesd.com, Inc. 哈尔滨亿时代数码科技开发有限公司, All rights reserved. 黑ICP备10202513号</div>
	</div>

	<!-- 右侧主体部分 -->
	<div data-options="region:'center',title:'残疾人就业保障金'">
		<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools'"></div>
	</div>
	<div id="tab-tools">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="view.tabs.removeAllTab()"></a>
	</div>
</body>
</html>