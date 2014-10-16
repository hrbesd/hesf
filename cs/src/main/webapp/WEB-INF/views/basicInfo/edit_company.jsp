<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- 编辑企业信息 -->

<form id="editComapnyForm" action="${contextPath}/security/company/edit" method="post">
	<!-- 数据表格 -->
	<table id="edit_companyTab" class="companyTable" border="0">
		<tr>
			<td class="tdRight">档案编码:</td>
			<td class="texttd">
				<input name="" class="inNormal" type="text" value="${company.companyCode}"   data-options="required:true" readonly="readonly" />
				<input name="id" type="hidden" value="${company.id}" /> 
				<input name="version" type="hidden" value="${company.version}" />
			</td>
		</tr>
		<tr>
			<td class="tdRight">企业名称(<span class="red_notice">*</span>):</td>
			<td colspan="3"><input name="companyName" class="longtext easyui-validatebox" type="text" value="${company.companyName}" data-options="required:true" />
			</td>
		</tr>
		<tr>
			<td class="tdRight">企业性质:</td>
			<td><input value="${company.companyProperty.id}" name="companyProperty.id" class="easyui-combobox iniTcompanyProperty"
				data-options="height:30,editable:false,valueField:'id',textField:'companyProperty',url:'parameter/propertyEx'" /></td>
			<td class="tdRight">主管部门:</td>
			<td class="bj_belu"><input name="companyManagement" value="${company.companyManagement}" /></td>
		</tr>
		<tr>
			<td class="tdRight">法人代表:</td>
			<td><input name="companyLegal" class="inNormal easyui-validatebox" type="text" value="${company.companyLegal}" />
			</td>
			<td class="tdRight">联系人:</td>
			<td><input name="companyContactPerson" class="inNormal easyui-validatebox" type="text" value="${company.companyContactPerson}" />
			</td>
		</tr>
		<tr>
			<td class="tdRight">电话号码:</td>
			<td><input name="companyPhone" class="inNormal easyui-validatebox" type="text" value="${company.companyPhone}" />
			</td>
			<td class="tdRight">手机号码:</td>
			<td><input name="companyMobile" class="inNormal easyui-validatebox" type="text" value="${company.companyMobile}"/>
			</td>
		</tr>
		<tr>
			<td class="tdRight">地区</td>
			<td><input name="area.code" class="easyui-combobox iniTcompanyProperty" value="${company.area.code}"
				data-options="height:30,editable:false,valueField:'code',textField:'name',url:'parameter/getArea'" /></td>
			<td class="tdRight">邮政编码:</td>
			<td class="bj_belu"><input name="companyZipCode" class="inNormal easyui-validatebox" type="text" value="${company.companyZipCode}" data-options="validType:['_number']" />
			</td>
		</tr>
		<tr>
			<td class="tdRight">企业地址(<span class="red_notice">*</span>):</td>
			<td colspan="3"><input name="companyAddress" class="longtext easyui-validatebox" type="text" value="${company.companyAddress}" data-options="required:true" />
			</td>
		</tr>
		<tr>
			<td class="tdRight">备注:</td>
			<td colspan="4"><textarea name="companyRemark" class="longtext" rows="3" cols="90">${company.companyRemark}</textarea>
			</td>
		</tr>
		<tr>
			<td class="tdRight"></td>
			<td colspan="4">带" <span style="color:red;">*</span> "项为必填项, 请认真填写.</td>
		</tr>

		<tr>
			<td class="tdRight"></td>
			<td class="tdCenter" colspan="4"><a href="javascript:basicFile.editCompany();" class="easyui-linkbutton" iconCls="icon-ok">保存</a> <a href="javascript:esd.common.defaultOpenWindowClose();"
				class="easyui-linkbutton" iconCls="icon-undo">取消</a>
			</td>
		</tr>
	</table>
</form>

<script type="text/javascript">
	//组件解析完成
	$.parser.onComplete = function() {
	
	};
</script>


