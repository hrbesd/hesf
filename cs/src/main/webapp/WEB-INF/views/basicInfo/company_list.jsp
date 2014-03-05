<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${contextPath}/js/basicFile.js"></script>
<!-- 基本档案   企业信息列表页面 -->

<!-- 企业信息类型 -->
<input type="hidden" id="companyProperty" value="${companyProperty}" />
<!-- 自定义菜单 -->
<div id="company_boolbar">
	<div style="text-align: right;">
		<a onclick="basicFile.openAddCompany()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">增加</a> <a onclick="basicFile.openBatchDeleteCompany()" href="#"
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">删除</a>

	</div>

	<div>
		<!-- 基本档案 搜索框 档案编码 -->
		<input id="companyCode" type="text" style="width: 14%;margin-left: 50px" />
		<!-- 基本档案 搜索框 税务编码 -->
		<input id="companyTaxCode" type="text" style="width: 14%" />
		<!-- 基本档案 搜索框 企业名称 -->
		<input id="companyName" type="text" style="width: 48%" /> <a href="#" onclick="basicFile.findData()" class="easyui-linkbutton" plain="true" iconCls="icon-search">查找</a>
	</div>
</div>

<!-- 数据表格 -->
<table id="company_grid"></table>

<script type="text/javascript">
	//组件解析完成
	$.parser.onComplete = function() {
		//加载数据
		basicFile.loadData(basicFile.getParams());
	};

</script>
