<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	payment = {};
	/**
	加载初审列表数据
	 */
	payment.loadData = function() {
		var params = {};
		params.year = $('#companyYears').val();
		params.process = $('#process').val();
		esd.common.datagrid("#payment_datagrid", "${contextPath}/security/audits/list", "#payment_boolbarTest", [ [ {
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
				var c = '<a href="#" onclick="basicFile.openViewCompany(' + row.id + ')">' + value + '</a>';
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

	/*
	打开单位初审面板
	 */
	payment.open = function(index) {
		esd.common.defaultOpenWindow("缴款", "${contextPath}/security/payment/edit/" + index);
	};
</script>


<!-- 数据表格 -->
<table id="payment_datagrid"></table>
<input type="hidden" id="process" value="${process}" />
<!-- 自定义菜单 -->
<div id="payment_boolbarTest" data-options="fit:false,doSize:false" style="white-space: nowrap;height: 70px;margin-top: 5px">
	<div style="text-align: center;">
		<input id="companyYears" class="easyui-combobox" value="${nowYear}" data-options="height:30,editable:false,valueField:'id',textField:'text',url:'${contextPath }/security/parameter/getyears'" />年
	</div>
	<div style="padding-top: 5px;">
		<input type="text" style="width: 50px" /> <input type="text" style="width: 50px" /> <input type="text" style="width: 68%" /> <a href="#" class="easyui-linkbutton" plain="true"
			iconCls="icon-search">查找</a>
	</div>
</div>
<!-- 审计窗口 -->
<div id="auditPanel"></div>

<script type="text/javascript">
	$(document).ready(function() {
		payment.loadData();
	});
</script>

