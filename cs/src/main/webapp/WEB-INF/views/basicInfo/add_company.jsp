<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<style type="text/css">
	.red_notice{
		color:red;
	}
</style>

<form id="addComapnyForm" action="${contextPath}/security/company/add" method="post">
	<!-- 数据表格 -->
	<table id="company_information"  class="companyTable" >
		<tr>
			<td class="tdRight">档案编码(<span class="red_notice">*</span>):</td>
			<td >
				<input name="companyCode" id="companyCode" class="easyui-validatebox"  data-options="required:true"  validType="remote['company/validate_companyCode','param','企业档案号已存在']" type="text" />
			</td>
		</tr>
		<tr>
			<td class="tdRight">企业名称(<span class="red_notice">*</span>):</td>
			<td colspan="3">
				<input name="companyName" class="longtext easyui-validatebox" type="text" data-options="required:true" validType="remote['company/validate_companyName','param','企业名称已经存在, 请不要重复添加, 在审核或者公司资料 查询页面输入公司名称即可查询到.']" />
			</td>
		</tr>
		<tr>
			<td class="tdRight">组织机关代码:</td>
			<td class="">
			<input name="companyOrganizationCode" class="easyui-validatebox" validType="remote['company/validate_companyOrganizationCode','param','组织机关代码证已存在']" type="text" />
			</td>
			<td class="tdRight">税务编码:</td>
			<td><input name="companyTaxCode" class="inNormal easyui-validatebox" type="text" /></td>
		</tr>
		<tr>
			<td class="tdRight">企业性质:</td>
			<td><input value="${companyProperty}" name="companyProperty.id" class="easyui-combobox"
				data-options="height:30,required:true,editable:false,valueField:'id',textField:'companyProperty',url:'parameter/propertyEx'" />
			</td>
			<td class="tdRight">经济类型:</td>
			<td class="bj_belu"><input name="companyEconomyType.id" class="easyui-combobox" value="1"
				data-options="height:30,required:true,editable:false,valueField:'id',textField:'companyEconomyType',url:'parameter/economytypeEx'" />
			</td>
		</tr>
		<tr>
			<td class="tdRight">单位类型:</td>
			<td class="bj_belu"><input name="companyType.id" class="easyui-combobox" value="1"
				data-options="height:30,required:true,editable:false,valueField:'id',textField:'companyType',url:'parameter/companytypeEx'" />
			</td>
			<td class="tdRight">主管部门:</td>
			<td class="bj_belu">
			<input name="companyManagement"  />
			</td>
		</tr>
		
		<tr>
			<td class="tdRight">法人代表(<span class="red_notice">*</span>):</td>
			<td><input name="companyLegal" class="inNormal easyui-validatebox" type="text" data-options="required:true" /></td>
		<td class="tdRight">联系人(<span class="red_notice">*</span>):</td>
			<td><input name="companyContactPerson" class="inNormal easyui-validatebox" type="text" data-options="required:true" /></td>
		
		</tr>
		<tr>
			<td class="tdRight">电话号码(<span class="red_notice">*</span>):</td>
			<td><input name="companyPhone" class="inNormal easyui-validatebox" type="text" data-options="required:true"  /></td>
			<td class="tdRight">手机号码:</td>
			<td><input name="companyMobile" class="easyui-validatebox" type="text" data-options="validType:['_number']" /></td>
		</tr>
		<tr>
			<td class="tdRight">开户银行:</td>
			<td><input name="companyBank" class="inNormal easyui-validatebox" type="text" /></td>
			<td class="tdRight">银行账户:</td>
			<td><input name="companyBankAccount" class="inNormal easyui-validatebox" type="text" data-options="validType:['_number']" /></td>
		</tr>
		<tr>
			<td class="tdRight">地区</td>
			<td><input name="area.code" class="easyui-combobox" data-options="height:30,required:true,editable:false,valueField:'code',textField:'name',url:'parameter/getArea'" value="10230000" />
			</td>
			<td class="tdRight">邮政编码:</td>
			<td class="bj_belu"><input name="companyZipCode" class="inNormal easyui-validatebox" type="text" data-options="validType:['_length[6]','_number']" /></td>
		</tr>
		<tr>
			<td class="tdRight">企业地址(<span class="red_notice">*</span>):</td>
			<td colspan="3"><input name="companyAddress" class="longtext easyui-validatebox" type="text" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="tdRight">备注：</td>
			<td colspan="4"><textarea name="companyRemark" class="longtext" rows="3" cols="90"></textarea></td>
		</tr>
		<tr>
			<td class="tdRight">&nbsp;</td>
			<td colspan="4">
				<p style="margin: 0px;"><input type="checkbox" value="1" name="ckbCreateAudit" checked="checked" id="ckbCreateAudit" style="width:auto;height:auto;"/><span>创建当年的审核信息, 默认自动创建.</span></p>
					<input type="hidden" name="createAudit" id="createAudit" value="0"/>
				<script type="text/javascript">
					$('#ckbCreateAudit').click(function(){
						var ckbCreateAudit = $('#ckbCreateAudit').attr('checked');
						if(ckbCreateAudit != null && ckbCreateAudit =='checked'){
							$('#createAudit').val('1');
						}else{
							$('#createAudit').val('0');
						}
					});
				</script>
			<!-- 	<p>带" <span style="color:red;">*</span> "项为必填项, 请认真填写.</p>	 -->
			</td>
		</tr>

		<tr>
			<td class="tdRight"></td>
			<td class="tdCenter" colspan="4"><a href="javascript:basicFile.addCompany();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			 <a href="javascript:esd.common.defaultOpenWindowClose()" class="easyui-linkbutton" iconCls="icon-undo">取消</a>
			</td>
		</tr>
	</table>
</form>

<script type="text/javascript">
	//组件解析完成
	$.parser.onComplete = function() {
	
	};
</script>
