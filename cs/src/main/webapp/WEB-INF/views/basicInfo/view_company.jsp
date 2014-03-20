<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- 编辑企业信息 -->

<form id="editComapnyForm" action="${contextPath}/security/company/edit" method="post">
	<!-- 数据表格 -->
	<table id="edit_companyTab" class="companyTable" border="0">
		<tr>
			<td class="tdRight" style="width:150px;">档案编码 :</td>
			<td class="texttd">${company.companyCode}</td>
			<td style="width:85px;">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td class="tdRight">企业名称 :</td>
			<td colspan="3">${company.companyName}</td>
		</tr>
		<tr>
			<td class="tdRight">组织机关代码 :</td>
			<td>${company.companyOrganizationCode}</td>
			<td class="tdRight">税务编码 :</td>
			<td>${company.companyTaxCode}</td>
		</tr>
		<tr>
			<td class="tdRight">企业性质:</td>
			<td>${company.companyProperty.companyProperty}</td>
			<td class="tdRight">经济类型:</td>
			<td class="bj_belu">${company.companyEconomyType.companyEconomyType}</td>
		</tr>
		<tr>
			<td class="tdRight">单位类别:</td>
			<td class="bj_belu">${company.companyType.id}</td>
			<td class="tdRight">主管部门:</td>
			<td class="bj_belu">${company.companyManagement}</td>
		</tr>

		<tr>
			<td class="tdRight">法人代表 :</td>
			<td>${company.companyLegal}</td>
			<td class="tdRight">联系人 :</td>
			<td>${company.companyContactPerson}</td>
		</tr>
		<tr>
			<td class="tdRight">电话号码 :</td>
			<td>${company.companyPhone}</td>
			<td class="tdRight">手机号码:</td>
			<td>${company.companyMobile}</td>
		</tr>
		<tr>
			<td class="tdRight">开户银行 :</td>
			<td>${company.companyBank}</td>
			<td class="tdRight">银行账户 :</td>
			<td>${company.companyBankAccount}</td>
		</tr>
		<tr>
			<td class="tdRight">地区</td>
			<td>${company.area.name}</td>
			<td class="tdRight">邮政编码:</td>
			<td class="bj_belu">${company.companyZipCode}</td>
		</tr>
		<tr>
			<td class="tdRight">企业地址 :</td>
			<td colspan="3">${company.companyAddress}</td>
		</tr>
		<tr>
			<td class="tdRight">备注:</td>
			<td colspan="3">${company.companyRemark}</td>
		</tr>
		<tr>
			<td class="tdCenter" colspan="4" style="text-align:center;"><a href="javascript:esd.common.defaultOpenWindowClose();" class="easyui-linkbutton" iconCls="icon-undo">确定</a></td>
		</tr>
	</table>
</form>

<script type="text/javascript">
	$.parser.onComplete = function() {
	
	};
</script>


