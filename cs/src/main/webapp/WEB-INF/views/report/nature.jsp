<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<div>
	年审企业性质汇总表<select>
		<option>2012</option>
	</select>
</div>
<!-- 年审情况汇总表(单位性质) -->
<table id="table_statistics_properties" class="table_statistics" border="" cellspacing="0" cellpadding="0">

	<tr>
		<th colspan="13">年审企业性质汇总表</th>
	</tr>
	<tr>
		<th colspan="6" style="text-align: left;">制表单位:黑龙江省残疾人联合会</th>
		<th colspan="7" style="text-align:right">制表时间:2013-08-29</th>

	</tr>
	<tr>
		<td>企业性质</td>
		<td>企业总数</td>
		<td>企业总人数</td>

		<td>待初企业位数</td>
		<td>待复审企业数</td>
		<td>已复核达标的企业数</td>
		<td>已复核未达标的企业数</td>
		<td>应安排人数</td>

		<td>少安排人数</td>
		<td>应缴金额</td>

		<td>减免金额</td>

		<td>实缴金额</td>
		<td>已缴金额</td>
	</tr>
</table>
