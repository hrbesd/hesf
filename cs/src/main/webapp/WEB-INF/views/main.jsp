<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body class="easyui-layout">
	<div style="padding: 10px;">
		<strong>残疾人就业保障金计算公式：</strong>
		<div>本地区上年度职工年人均工资数×(单位在职职工总数×1.5%﹣(减)单位在岗残疾职工总数)。</div>
		<div>
			<strong>例:</strong>A公司人情况如下
		</div>
		<table border="1">
			<tr>
				<td>本地区上年度职工年人均工资数:</td>
				<td>30000</td>
			</tr>
			<tr>
				<td>单位在职职工总数:</td>
				<td>100</td>
			</tr>
			<tr>
				<td>单位在岗残疾职工总数:</td>
				<td>1</td>
			</tr>
			<tr>
				<td>应安排残疾人比例：</td>
				<td>1.5%</td>
			</tr>
			<tr>
				<td>A公司应安排残疾人为：</td>
				<td>1.5</td>
				<td>计算公式：100×1.5%=1.5</td>
			</tr>
			<tr>
				<td>A公司应缴保障金为:</td>
				<td>15000</td>
				<td>计算公式：(100×1.5%-1)×30000=15000</td>
			</tr>
		</table>

	</div>
</body>
</html>