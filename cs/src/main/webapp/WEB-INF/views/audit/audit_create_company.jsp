<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var auditCreateCompany = {};
	
	auditCreateCompany.getRandomCode = function(){
		$.ajax({
			url:'${contextPath}/security/company/getRandomCode',
			dataType:'json',
			success:function(data){
			//	alert($('#companyCode').val());
				//设置companyCode
				$('#txtCompanyCode').combobox('setValue',data.randomCode);
			}
		});
	};	
	auditCreateCompany.add = function(){
		esd.common.syncPostSubmit("#addCompanyForm", function(data) {
			if (data.notice == 'success') {
				$.messager.alert('消息', '单位信息添加成功,请继续添加“残疾职工信息”,如没有残疾职工则直接填写“年审信息”!', 'info');
				$('#companyId').val(data.companyId);
				$('#tempCompanyCode').val($('#txtCompanyCode').combobox('getValue'));
			//	$('#company_grid').datagrid("reload");
			} else {
				$.messager.alert('消息', '单位信息增加失败!', 'error');
			}
		});
	};
	
	//根据公司档案code得到对应的公司对象,然后将值赋予到对应的框框中
	auditCreateCompany.getCompanyByCode = function(companyCode){
		$.ajax({
			url:'${contextPath}/security/company/getCompanyByCode',
			dataType:'json',
			data:{'companyCode':companyCode},
			success:function(data){
				$('#txtCompanyName').val(data.entity.companyName);
				$('#txtCompanyOrganizationCode').val(data.entity.companyOrganizationCode);
				$('#txtCompanyTaxCode').val(data.entity.companyTaxCode);
				$('#comboboxCompanyProperty').combobox('setValue',data.entity.companyProperty.id);
				$('#comboboxCompanyEconomyType').combobox('setValue',data.entity.companyEconomyType.id);
				$('#comboxCompanyType').combobox('setValue',data.entity.companyType.id);
				$('#txtCompanyManagement').val(data.entity.companyManagement);
				$('#txtCompanyLegal').val(data.entity.companyLegal);
				$('#txtCompanyContactPerson').val(data.entity.companyContactPerson);
				$('#txtCompanyPhone').val(data.entity.companyPhone);
				$('#txtCompanyMobile').val(data.entity.companyMobile);
				$('#txtCompanyBank').val(data.entity.companyBank);
				$('#txtCompanyBankAccount').val(data.entity.companyBankAccount);
				$('#comboboxAreaCode').combobox('setValue',data.entity.area.code);
				$('#txtCompanyZipCode').val(data.entity.companyZipCode);
				$('#txtCompanyAddress').val(data.entity.companyAddress);
				$('#txtCompanyRemark').val(data.entity.companyRemark);
				$('#txtVersion').val(data.entity.version);
			}
		});	
	};
	
	//检查组织机关代码证号
	auditCreateCompany.getCompanyByOrganizationCode = function(){
		var companyOrganizationCode = $('#txtCompanyOrganizationCode').val();
		if(companyOrganizationCode == null || companyOrganizationCode =='' || companyOrganizationCode == undefined){
			return;
		}
		$.ajax({
			url:'${contextPath}/security/company/getCompanyByOrganizationCode',
			dataType:'json',
			data:{'companyOrganizationCode':companyOrganizationCode},
			success:function(data){
				//如果存在公司, 则提示是否载入
				if(data.entity != null){
					//弹出框提示, 公司已经存在
					$.messager.confirm('确认对话框','该组织机构代码证对应的公司已经存在,要导入原来的信息吗?',function(r){
						if(r){
							$('#txtCompanyCode').combobox('setValue',data.entity.companyCode);
							$('#txtCompanyName').val(data.entity.companyName);
							$('#txtCompanyOrganizationCode').val(data.entity.companyOrganizationCode);
							$('#txtCompanyTaxCode').val(data.entity.companyTaxCode);
							$('#comboboxCompanyProperty').combobox('setValue',data.entity.companyProperty.id);
							$('#comboboxCompanyEconomyType').combobox('setValue',data.entity.companyEconomyType.id);
							$('#comboxCompanyType').combobox('setValue',data.entity.companyType.id);
							$('#txtCompanyManagement').val(data.entity.companyManagement);
							$('#txtCompanyLegal').val(data.entity.companyLegal);
							$('#txtCompanyContactPerson').val(data.entity.companyContactPerson);
							$('#txtCompanyPhone').val(data.entity.companyPhone);
							$('#txtCompanyMobile').val(data.entity.companyMobile);
							$('#txtCompanyBank').val(data.entity.companyBank);
							$('#txtCompanyBankAccount').val(data.entity.companyBankAccount);
							$('#comboboxAreaCode').combobox('setValue',data.entity.area.code);
							$('#txtCompanyZipCode').val(data.entity.companyZipCode);
							$('#txtCompanyAddress').val(data.entity.companyAddress);
							$('#txtCompanyRemark').val(data.entity.companyRemark);
							$('#txtVersion').val(data.entity.version);
						}
					});
					
				}
			}
		});	
	};
	
	//清空添加的所有信息
	auditCreateCompany.reset = function(){
		$('#companyId').val('');
		$('#txtCompanyCode').combobox('setValue','');
		$('#txtCompanyName').val('');
		$('#txtCompanyOrganizationCode').val('');
		$('#txtCompanyTaxCode').val('');
		$('#comboboxCompanyProperty').combobox('setValue',4);
		$('#comboboxCompanyEconomyType').combobox('setValue',1);
		$('#comboxCompanyType').combobox('setValue',1);
		$('#txtCompanyManagement').val('');
		$('#txtCompanyLegal').val('');
		$('#txtCompanyContactPerson').val('');
		$('#txtCompanyPhone').val('');
		$('#txtCompanyMobile').val('');
		$('#txtCompanyBank').val('');
		$('#txtCompanyBankAccount').val('');
		$('#comboboxAreaCode').combobox('setValue','n001');
		$('#txtCompanyZipCode').val('');
		$('#txtCompanyAddress').val('');
		$('#txtCompanyRemark').val('');
	};
	
	$(function(){
		
	});
	
</script>

<style type="text/css">
	.red_notice{
		color:red;
	}
</style>
<form id="addCompanyForm" action="${contextPath}/security/company/addCompany" method="post">
	<!-- 数据表格 -->
	<table id="company_information"  class="companyTable" >
		<tr>
			<td class="tdRight">档案编码(<span class="red_notice">*</span>):
			</td>
			<td>
				<input type="text" id="txtCompanyCode" name="companyCode" value="" class="easyui-combobox" data-options="width:400,height:30,editable:true,panelHeight:240" />
				<!-- 保存 上次提交的companyCode  未使用-->
				<input type="hidden" id="tempCompanyCode" />
				<!-- 自动创建审核信息标示符 -->
				<input type="hidden" name="createAudit" id="createAudit" value="1"/>
				<!-- 版本号 , 默认为1-->
		<!-- 		<input type="hidden" name="version" id="txtVersion" value="1"/>	 -->
				<!-- 公司id -->
				<input type="hidden" id="companyId" value=""/>
			</td>
			<td colspan="2">
				<a href="javascript:auditCreateCompany.getRandomCode();" class="easyui-linkbutton" >生成档案编码</a>
			</td>
		</tr>
		<tr>
			<td class="tdRight">企业名称(<span class="red_notice">*</span>):</td>
			<td colspan="3"><input value="" id="txtCompanyName" name="companyName" class="longtext easyui-validatebox" type="text" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="tdRight">组织机关代码(<span class="red_notice">*</span>):</td>
			<td class="">
				<input id="txtCompanyOrganizationCode" name="companyOrganizationCode" class="easyui-validatebox" required="true" type="text" onblur="auditCreateCompany.getCompanyByOrganizationCode();"/>
				<!-- <input id="txtCompanyOrganizationCode" name="companyOrganizationCode" class="easyui-validatebox" required="true" validType="remote['company/validate_companyOrganizationCode','param','组织机关代码证已存在']" type="text" />	 -->
			</td>
			<td class="tdRight">税务编码(<span class="red_notice">*</span>):</td>
			<td><input id="txtCompanyTaxCode" name="companyTaxCode" class="inNormal easyui-validatebox" type="text" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="tdRight">企业性质:</td>
			<td><input id="comboboxCompanyProperty" name="companyProperty.id" class="easyui-combobox" value="4"
				data-options="height:30,required:true,editable:false,valueField:'id',textField:'companyProperty',url:'parameter/propertyEx'" />
				<!-- 经济类型，单位类型由于数据库限制不能为空，都写死为 1 -->
				<input type="hidden" name="companyEconomyType.id" value="1" />
				<input type="hidden" name="companyType.id" value="1" />
			</td>
			<td class="tdRight">主管部门:</td>
			<td class="bj_belu">
				<input name="companyManagement" id="txtCompanyManagement"  />
			</td>
		</tr>
		<tr>
			<td class="tdRight">法人代表(<span class="red_notice">*</span>):</td>
			<td><input id="txtCompanyLegal" name="companyLegal" class="inNormal easyui-validatebox" type="text" data-options="required:true" /></td>
		<td class="tdRight">联系人(<span class="red_notice">*</span>):</td>
			<td><input id="txtCompanyContactPerson" name="companyContactPerson" class="inNormal easyui-validatebox" type="text" data-options="required:true" /></td>
		
		</tr>
		<tr>
			<td class="tdRight">电话号码(<span class="red_notice">*</span>):</td>
			<td><input id="txtCompanyPhone" name="companyPhone" class="inNormal easyui-validatebox" type="text" data-options="required:true"  /></td>
			<td class="tdRight">手机号码:</td>
			<td><input id="txtCompanyMobile" name="companyMobile" class="easyui-validatebox" type="text" data-options="validType:['_number']" /></td>
		</tr>
		<tr>
			<td class="tdRight">开户银行(<span class="red_notice">*</span>):</td>
			<td><input id="txtCompanyBank" name="companyBank" class="inNormal easyui-validatebox" type="text" data-options="required:true" /></td>
			<td class="tdRight">银行账户(<span class="red_notice">*</span>):</td>
			<td><input id="txtCompanyBankAccount" name="companyBankAccount" class="inNormal easyui-validatebox" type="text" data-options="required:true,validType:['_number']" /></td>
		</tr>
		<tr>
			<td class="tdRight">地区</td>
			<td><input id="comboboxAreaCode" name="area.code" class="easyui-combobox" data-options="height:30,required:true,editable:false,valueField:'code',textField:'name',url:'parameter/getArea'" value="n001"/>
			</td>
			<td class="tdRight">邮政编码:</td>
			<td class="bj_belu"><input id="txtCompanyZipCode" name="companyZipCode" class="inNormal easyui-validatebox" type="text" data-options="validType:['_length[6]','_number']" /></td>
		</tr>
		<tr>
			<td class="tdRight">企业地址(<span class="red_notice">*</span>):</td>
			<td colspan="3"><input id="txtCompanyAddress" name="companyAddress" class="longtext easyui-validatebox" type="text" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="tdRight">备注：</td>
			<td colspan="4"><textarea id="txtCompanyRemark" name="companyRemark" class="longtext" rows="3" cols="90"></textarea></td>
		</tr>
<!-- 		<tr>
			<td class="tdRight">&nbsp;</td>
			<td colspan="4">
				<p><input type="checkbox" value="1" name="ckbCreateAudit" id="ckbCreateAudit" style="width:auto;height:auto;"/><span>创建当年的审核信息</span></p>
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
				<p>带" <span style="color:red;">*</span> "项为必填项, 请认真填写.</p>
			</td>
		</tr> -->

		<tr>
			<td class="tdRight"></td>
			<td class="tdCenter" colspan="4">
				<a href="javascript:auditCreateCompany.add();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
				<a href="javascript:esd.common.defaultOpenWindowClose()" class="easyui-linkbutton" iconCls="icon-undo">取消</a>
				<a href="javascript:auditCreateCompany.reset()" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
			</td>
		</tr>
	</table>
</form>