<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body class="easyui-layout">
	<div style="padding: 10px;">
		<h2>1.残疾人就业保障金计算公式：</h2>
		<div>保障金计算=(单位在职职工总数×1.5%﹣(减)单位在岗残疾职工总数)×本地区上年度职工年人均工资数</div>
			<h3>例:A公司情况如下</h3>
		<table border="1">
			<tr>
				<td>本地区上年度职工年人均工资数:</td>
				<td>30000.00元</td>
				<td></td>
			</tr>
			<tr>
				<td>单位在职职工总数:</td>
				<td>100人</td>
				<td></td>
			</tr>
			<tr>
				<td>单位在岗残疾职工总数:</td>
				<td>1人</td>
				<td></td>
			</tr>
			<tr>
				<td>应安排残疾人比例：</td>
				<td>1.5%</td>
				<td></td>
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
	<h2>2.年审流程</h2>
	 设置年审参数→添加公司→添加残疾人职工→初审→复审→缴款
	<h2>3.未年审处理方式</h2>
		应缴金额 = 参考（残疾人保障金计算公式）<br/>
		未缴金额 = 以前年审通过后未缴款的金额。<br/>
		实缴金额 = (应缴金额×(未审年数+1))+(未缴金额×(初审当天-未缴年度的支付截至日期)天数×5‰)<br />
		<span style="color: red;font-size: 12px;">注：审核状态包括以下几种：1.未初审 2.已初审 未复审 3.已复审 未缴款 4.部分缴款 5.已缴款 6.达标</span>
	<h2>滞纳金计算方式：</h2>
	
	
	
	</div>
</body>
</html>