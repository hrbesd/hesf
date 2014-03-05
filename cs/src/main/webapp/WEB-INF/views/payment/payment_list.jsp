<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	payment = {};
	payment.getParams = function() {
		alert('fff');
		var params = {};
		params.year = $('#year').combobox('getValue');
		params.process = $('#process').combobox('getValue');
		params.money = $('#money').val();
		params.companyCode = $('#companyCode').val();
		params.companyTaxCode = $('#companyTaxCode').val();
		params.companyName = $('#companyName').val();
		return params;
	};
	/**
	加载初审列表数据
	 */
	payment.loadData = function() {
		var params = payment.getParams();
		esd.common.datagrid("#paymentList_datagrid", "${contextPath}/security/audits/list", "#paymentList_boolbar", [ [ {
			field : 'companyCode',
			title : '档案编码',
			width : 150
		}, {
			field : 'companyTaxCode',
			title : '税务编码',
			width : 150
		}, {
			field : 'companyName',
			title : '企业名称',
			width : 900,
			formatter : function(value, row, index) {
				var c = '<a href="#" onclick="payment.openViewCompany(\'' + row.companyId + '\')">' + value + '</a>';
				return c;
			}
		}, {
			field : 'auditProcessStatus',
			title : '流程状态',
			width : 150
		}, {
			field : 'action',
			title : '操作',
			width : 150,
			align : 'center',
			formatter : function(value, row, index) {
				var v = '<a href="#" onclick="payment.open(' + row.id + ')">缴款</a>';
				return v;
			}
		} ] ], params);
	};
	/**
	 * 查看企业信息框
	 */
	payment.openViewCompany = function(id) {
		esd.common.defaultOpenWindow("查看企业信息", 'company/view/' + id);
	};
	/*
	打开单位初审面板
	 */
	payment.open = function(index) {
		esd.common.defaultOpenWindow("缴款", "${contextPath}/security/payment/edit/" + index);
	};
</script>
<!-- 自定义菜单 -->
<div id="paymentList_boolbar" data-options="fit:false,doSize:false" style="white-space: nowrap;height: 70px;margin-top: 5px">
	<table width="100%" border="0">
		<tr>
			<td width="80" style="text-align: right;">年审时间:</td>
			<td width="150"><input id="year" class="easyui-combobox" value="${nowYear}"
				data-options="height:30,editable:false,valueField:'id',textField:'text',url:'${contextPath }/security/parameter/getyears'" /></td>
			<td width="80" style="text-align: right;">流程状态:</td>
			<td width="150"><input id="process" class="easyui-combobox" data-options="height:30,editable:false" /></td>
			<td width="80" style="text-align: right;">实缴金额:</td>
			<td width="150"><input type="text" id="money" /></td>
			<td width="100%" ></td>
		</tr>
		<tr>
			<td style="text-align: right;">档案编码 :</td>
			<td><input type="text" style="width: 100%" id="companyCode" /></td>
			<td style="text-align: right;">税务编码:</td>
			<td><input type="text" style="width: 100%" id="companyTaxCode" /></td>
			<td  style="text-align: right;">企业名称 :</td>
			<td colspan="3"><input type="text" style="width: 100%" id="companyName" /></td>
			<td><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="payment.loadData()">查找</a></td>
		</tr>
	</table>
</div>
<!-- 数据表格 -->
<table id="paymentList_datagrid"></table>
<script type="text/javascript">
	$.parser.onComplete = function(){
		$('#year').combobox({
			url : 'parameter/getyears',
			valueField : 'id',
			textField : 'text'
		});
		$('#process').combobox({
			url : 'parameter/getStatus',
			valueField : 'id',
			textField : 'auditProcessStatus',
			value : '${process}'
		});
		alert("aa");
		payment.loadData();
	};
</script>

