
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
String filename = new String(("表格名").getBytes("UTF-8"),"ISO-8859-1");
 response.addHeader("Content-Disposition", "filename=" + filename + ".xls");%>
<html>
<head>
<meta name="Generator" content="Microsoft Excel 11">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<table border="1" align="center" cellpadding="0" cellspacing="1">
	<tr>
		<td colspan="3" style="text-align: center;">
			报表123
		</td>
	</tr>
		<tr>
			<td> 本地区上年度职工年人均工资数: 30000.00元</td>
			<td> 本地区上年度职工年人均工资数: 30000.00元</td>
			<td> 本地区上年度职工年人均工资数: 30000.00元</td>
			<td> 本地区上年度职工年人均工资数: 30000.00元</td>
			<td> 本地区上年度职工年人均工资数: 30000.00元</td>
		</tr>
		<tr>
			<td> 本地区上年度职工年人均工资数: 30000.00元</td>
			<td> 本地区上年度职工年人均工资数: 30000.00元</td>
			<td> 本地区上年度职工年人均工资数: 30000.00元</td>
			<td> 本地区上年度职工年人均工资数: 30000.00元</td>
			<td> 本地区上年度职工年人均工资数: 30000.00元</td>
		</tr>

	</table>
</body>
</html>
