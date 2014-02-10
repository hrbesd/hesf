<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- 编辑企业信息 -->

<form id="editComapnyForm" action="${contextPath}/security/company/edit" method="post">

	<!-- 数据表格 -->
	<table id="edit_companyTab"class="companyTable" border="0">
		<tr>
			<td class="tdRight">档案编码:</td>
			<td class="texttd"><input name="companyCode" class="inNormal" type="text" value="${company.companyCode}" style="border: 0" readonly="true" />
			</td>

		</tr>
		<tr>
			<td class="tdRight">组织机关代码证:</td>
			<td><input name="companyOrganizationCode" class="inNormal easyui-validatebox" type="text" value="${company.companyOrganizationCode}" data-options="required:true" /> <!-- 年份 --> <input
				name="year" type="hidden" value="${company.year}" /> <!-- 地区码 --> <input name="id" type="hidden" value="${company.id}" /> <!-- 地区码 --> <input name="version" type="hidden"
				value="${company.version}" />
			</td>
			<td class="tdRight">税务编码:</td>
			<td><input name="companyTaxCode" class="inNormal easyui-validatebox" type="text" value="${company.companyTaxCode}" data-options="required:true,validType:['_length[8]']" />
			</td>
		</tr>

		<tr>
			<td class="tdRight">企业名称:</td>
			<td colspan="3"><input name="companyName" class="longtext easyui-validatebox" type="text" value="${company.companyName}" data-options="required:true" />
			</td>

		</tr>
		<tr>
			<td class="tdRight">法人代表:</td>
			<td><input name="companyLegal" class="inNormal easyui-validatebox" type="text" value="${company.companyLegal}" data-options="required:true" />
			</td>
			<td class="tdRight">联系人:</td>
			<td><input name="companyContactPerson" class="inNormal easyui-validatebox" type="text" data-options="required:true" value="${company.companyContactPerson}" />
			</td>
		</tr>
		<tr>
			<td class="tdRight">单位类别:</td>
			<td class="bj_belu"><input value="${company.companyType.id}" id="companyType" name="companyType.id" class="easyui-combobox iniTcompanyType"
				data-options="height:30,ditable:false,valueField:'id',textField:'companyType',url:'parameter/companytypeEx'" />
			</td>
			<td class="tdRight">企业性质:</td>
			<td><input value="${company.companyProperty.id}" name="companyProperty.id" class="easyui-combobox iniTcompanyProperty"
				data-options="height:30,editable:false,valueField:'id',textField:'companyProperty',url:'parameter/propertyEx'" /></td>
		</tr>
		<tr>
			<td class="tdRight">经济类型:</td>
			<td class="bj_belu"><input value="${company.companyEconomyType.id}" name="companyEconomyType.id" class="easyui-combobox "
				data-options="height:30,editable:false,valueField:'id',textField:'companyEconomyType',url:'parameter/economytypeEx'" /></td>
			<td class="tdRight">邮政编码:</td>
			<td class="bj_belu"><input name="companyZipCode" class="inNormal easyui-validatebox" type="text" value="${company.companyZipCode}" data-options="validType:['_length[6]','_number']" />
			</td>
		</tr>

		<tr>
			<td class="tdRight">电话号码:</td>
			<td><input name="companyPhone" class="inNormal easyui-validatebox" type="text" value="${company.companyPhone}" data-options="required:true" />
			</td>
			<td class="tdRight">手机号码:</td>
			<td><input name="companyMobile" class="inNormal easyui-validatebox" type="text" value="${company.companyMobile}" />
			</td>
		</tr>
		<tr>
			<td class="tdRight">开户银行:</td>
			<td><input name="companyBank" class="inNormal easyui-validatebox" type="text" value="${company.companyBank}" data-options="required:true" />
			</td>
			<td class="tdRight">银行账户:</td>
			<td><input name="companyBankAccount" class="inNormal easyui-validatebox" type="text" value="${company.companyBankAccount}" data-options="required:true" />
			</td>
		</tr>
		<tr>
			<td>地区</td>

			<td><input name="area.code" class="easyui-combobox iniTcompanyProperty" value="${company.area.code}"
				data-options="height:30,editable:false,valueField:'code',textField:'name',url:'parameter/getArea'" /></td>
		</tr>
		<tr>
			<td class="tdRight">企业总人数:</td>
			<td><input name="companyEmpTotal" class="easyui-validatebox" type="text" value="${company.companyEmpTotal}" data-options="required:true,validType:['_number']" />
			</td>
			<td class="tdRight">残疾职工人数:</td>
			<td><input name="companyHandicapTotal" class="inNormal easyui-validatebox" type="text" value="${company.companyHandicapTotal}" data-options="required:true,validType:['_number']" />
			</td>
		</tr>
		<tr>
			<td class="tdRight">企业地址:</td>
			<td colspan="3"><input name="companyAddress" class="longtext easyui-validatebox" type="text" value="${company.companyAddress}" data-options="required:true" />
			</td>
		</tr>
		<tr>
			<td class="tdRight">备注:</td>
			<td colspan="4"><textarea name="companyRemark"  class="longtext" rows="3" cols="90">${company.companyRemark}</textarea>
			</td>
		</tr>
		<tr>
			<td class="tdRight"></td>
			<td colspan="4">带" *"项为必填项，请认真填写。带" *"项为重复验项项，请注意</td>
		</tr>
	
		<tr>
			<td class="tdRight"></td>
			<td class="tdCenter" colspan="4"><a href="javascript:basicFile.editCompany();" class="easyui-linkbutton" iconCls="icon-ok">保存</a> <a href="javascript:esd.common.defaultOpenWindowClose();"
				class="easyui-linkbutton" iconCls="icon-undo">取消</a>
			</td>
		</tr>
	</table>
</form>




