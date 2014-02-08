<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Basic DataGrid - jQuery EasyUI Demo</title>
<link rel="stylesheet" type="text/css" href="/cs/js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="/cs/js/themes/gray/easyui.css" />
<link rel="stylesheet" type="text/css" href="/cs/js/demo.css" />
<script type="text/javascript" src="/cs/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="/cs/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/cs/js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/cs/js/full.js"></script>

</head>
<body class="easyui-layout" region="center"   border="false"  doSize ="true"  fit="true" >

	<!-- 上部分 -->
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">
		top
		<div class="tree-node-selected">123</div>

	</div>

	<!-- 左侧菜单 -->
	<div data-options="region:'west',split:true,title:'目录'" style="width:260px;padding:10px;">
		<!-- 主菜单 -->
		<ul id="main_menu"></ul>

	</div>

	<!-- 右侧隐藏栏 -->
	<div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">east region</div>
	<!-- 底部 -->
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>

	<!-- 右侧主体部分 -->
	<div id="mainPanle" >
		<div id="tabs" class="easyui-tabs"  >
			<!--标签页默认页面-->
			<div title="首页">
				<div class="cs-home-remark">
					<h1>黑龙江省残疾人就业保证金管理系统</h1>
					<br />
				</div>
			</div>
		</div>
	</div>
</body>
</html>