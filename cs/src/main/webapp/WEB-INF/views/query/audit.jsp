<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style type="text/css">
#queryAuditBoolbar td {
	width: 100px;
}
</style>

<script type="text/javascript">
	var queryAudit = {};
	/**
		初始化数据
	 **/
	queryAudit.init = function() {
		//企业性质
		$('#companyProperty').combobox({
			url : 'parameter/property',
			valueField : 'id',
			textField : 'companyProperty'
		});

		
		//经济类型
		$('#companyEconomyType').combobox({
			url : 'parameter/economytype',
			valueField : 'id',
			textField : 'companyEconomyType',
		});
		//地区
		$('#area').combobox({
			url : 'parameter/getArea',
			valueField : 'code',
			textField : 'name',
		});

		//年份
		$('#year').combobox({
			url : 'parameter/getyears',
			valueField : 'id',
			textField : 'text'
		});
		//流程状态
			$('#auditProcessStatus').combobox({
			url : 'parameter/getStatus',
			valueField : 'id',
			textField : 'auditProcessStatus'
		});
		
		//缴款人
		$('#paymentPerson').combobox({
			url : 'parameter/getPayer',
			valueField : 'id',
			textField : 'userName'
		});
		
		
	};

	/*
	 * 获取企业基本档案函数
	 */
	queryAudit.loadData = function(params) {
		esd.common.datagrid("#queryAuditGrid", "query/audit/audit_list", "#queryAuditBoolbar", [ [ {
			field : 'companyCode',
			title : '档案编码',
			width : 230
		}, {
			field : 'companyTaxCode',
			title : '税务编码',
			width : 230
		}, {
			field : 'companyName',
			title : '企业名称',
			width : 800,
			formatter : function(value, row, index) {
				var c = '<a href="#" onclick="basicFile.openViewCompany(\'' + row.id + '\')">' + value + '</a>';
				return c;
			}
		}] ], params);

	};

	/**
		组装查询参数
	 **/
	queryAudit.getParams = function(){
		var params = {};
		params.year = $("#year").combobox("getValue"); // 年度
		params.companyCode = $("#companyCode").val(); // 档案号码
		params.companyTaxCode = $("#companyTaxCode").val();// 税务编码
		params.companyOrganizationCode = $("#companyOrganizationCode").val();// 组织机构代码证号
		params.companyProperty = $("#companyProperty").combobox("getValue");// 公司性质 _
		params.companyEconomyType = $("#companyEconomyType").combobox("getValue");// 企业经济类型
		params.area = $("#area").combobox("getValue");// 公司所属地区
		params.companyEmpTotal_1 = $("#companyEmpTotal_1").val();// 员工总数
		params.companyEmpTotal_2 = $("#companyEmpTotal_2").val();
		params.companyName = $("#companyName").val(); // 企业名称
		params.companyAddress = $("#companyAddress").val();// 企业地址
		params.companyLegal = $("#companyLegal").val();//	企业法人

		params.auditProcessStatus=$("#auditProcessStatus").combobox("getValue");// 流程状态
		params.paymentPerson=$("#paymentPerson").combobox("getValue");// 缴款人
		
		params.overYear=$("#overYear").val(); // 超过指定年度未初审的企业
		params.isExempt=$("#isExempt").val();// 是否免交
		return params;
	};


	/**
	 查询数据 并校验所有输入框
	 **/
	queryAudit.findData = function() {
		if (esd.common.validatebox()) {
			//重新根据参数加载数据
			$('#queryAuditGrid').datagrid('load', queryAudit.getParams());
		};
	};
	
	$(function() {
		queryAudit.init();
		//加载单位档案数据
		queryAudit.loadData(queryAudit.getParams());

	});
</script>



<!-- 数据表格 -->
<table id="queryAuditGrid"></table>

<!-- 自定义菜单 -->
<div id="queryAuditBoolbar">
	<div style="">
		<table>
			<tr>
				<td>年度:</td>
				<td><input id="year" class="easyui-combobox" value="${nowYear}" data-options="height:30,editable:false" />
				</td>
				<td>档案号码:</td>
				<td><input type="text" id="companyCode" />
				</td>
				<td>税务号码:</td>
				<td><input id="companyTaxCode" type="text" />
				</td>
				<td>组织机构代码证:</td>
				<td><input type="text" id="companyOrganizationCode" /></td>

			</tr>
			<tr>
				<td>企业性质:</td>
				<td><input id="companyProperty" class="easyui-combobox" data-options="height:30,editable:false" />
				</td>
				<td>经济类型:</td>
				<td><input id="companyEconomyType" class="easyui-combobox" data-options="height:30,editable:false" />
				</td>
				<td>地区:</td>
				<td><input id="area" class="easyui-combobox" data-options="height:30,editable:false" value="10230000" />
				</td>
				<td>企业人数:</td>
				<td><input type="text" style="width: 40px" id="companyEmpTotal_1" />-<input type="text" style="width: 40px" id="companyEmpTotal_2" />人</td>
			</tr>
			<tr>
				<td>企业名称:</td>
				<td colspan="3"><input class="longInputBox" id="companyName" />
				</td>
				<td>企业地址:</td>
				<td colspan="3"><input id="companyAddress" type="text" /></td>

			</tr>
			<tr>
				<td>法人代表:</td>
				<td><input id="companyLegal" type="text" /></td>
			</tr>
			<tr>
				<td colspan="8">
					<hr /></td>
			</tr>
			<tr>
				<td>流程状态:</td>
				<td><input id="auditProcessStatus" class="easyui-combobox" data-options="width:75,height:30,editable:false" /> 单位</td>

				<td>缴款人:</td>
				<td><input type="text" id="paymentPerson" class="easyui-combobox" data-options="width:80,height:30,editable:false" /></td>
				<td>超过:</td>
				<td><input id="overYear" type="text" value="0" style="width: 60px" /> 年未初审</td>

				<td>是否免交:</td>
				<td><select id="isExempt">
						<option value="" selected="selected">请选择</option>
						<option value="true">是</option>
						<option value="false">否</option>

				</select></td>
			</tr>
		</table>
		<div>
			<a href="javascript:queryAudit.findData()" class="easyui-linkbutton" iconCls="icon-ok">查询</a>

			<button>重置</button>
			<button>生成excel</button>
		</div>
	</div>
</div>

<div id="viewCompanyPanel"></div>
