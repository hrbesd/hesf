<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="printOverall">
	<div>
		年审地区汇总表<select>
			<option>2012</option>
		</select>
	</div>
	<!-- 年审地区汇总表 -->
	<table id="" border="" cellspacing="0" cellpadding="0" style="margin-top: 30px">
		<tr>
			<th colspan="14">年审地区汇总表</th>
		</tr>
		<tr>
			<th colspan="7" style="text-align: left;">制表单位:黑龙江省残疾人联合会</th>
			<th colspan="7" style="text-align:right">制表时间:2013-08-29</th>
		</tr>
		<tr>
				<td>地区</td>
				<td>单位总数</td>
				<td>单位总人数</td>
				<td>待初单位数</td>
				<td>待复审单位数</td>
				<td>已复核达标单位数</td>
				<td>已复核未达标单位数</td>
				<td>应安排人数</td>
				<td>已安排人数</td>
				<td>预定人数</td>
				<td>应缴金额</td>
				<td>减免金额</td>
				<td>实缴金额</td>
				<td>已缴金额</td>
		</tr>
		<tr>
		
			<td colspan="14" align="right"> 制表人：校验</td>
		</tr>
	
	</table>
	
	<div align="center"  style="margin-top: 15px">
	
		 <a href="" class="easyui-linkbutton" iconCls="icon-undo">导出</a>
	</div>
		
	
		
</div>
