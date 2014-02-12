<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	initAuditList = {};

	initAuditList.getParams = function() {
		var params = {};
		params.year = $('#year').combobox('getValue');
		params.process = $('#process').combobox('getValue');
		params.companyCode = $('#companyCode').val();
		params.companyTaxCode = $('#companyTaxCode').val();
		params.companyName = $('#companyName').val();
		return params;
	};

	/**
	 * 加载初审列表数据
	 */
	initAuditList.loadData = function() {
		var params = initAuditList.getParams();
		esd.common.datagrid("#initAuditList_datagrid", "${contextPath}/security/audits/list", "#initAuditList_boolbar", [ [ {
			field : 'companyCode',
			title : '档案编码',
			width : 200
		}, {
			field : 'companyTaxCode',
			title : '税务编码',
			width : 200
		}, {
			field : 'companyId',
			hidden : true
		}, {
			field : 'companyName',
			title : '企业名称',
			width : 800,
			formatter : function(value, row, index) {
				var c = '<a href="#" onclick="initAuditList.openViewCompany(\'' + row.companyId + '\')">' + value + '</a>';
				return c;
			}
		}, {
			field : 'auditProcessStatus',
			title : '流程状态',
			width : 150
		}, {
			field : 'action',
			title : '操作',
			width : 100,
			align : 'center',
			formatter : function(value, row, index) {
				var v = '<a href="#" onclick="initAuditList.openAudit(' + row.id + ')">';
				return v + '<c:if test="${process==1}">初审</c:if><c:if test="${process==2}">复审</c:if></a> ';
			}
		} ] ], params);
	};
	/**
	 * 查看企业信息框
	 */
	initAuditList.openViewCompany = function(id) {
		esd.common.defaultOpenWindow("查看企业信息", 'company/view/' + id);
	};
	/*
	打开单位初审面板
	 */
	initAuditList.openAudit = function(index) {
		<c:if test="${process==1}">
		esd.common.defaultOpenWindowEx("初审", 920, 600, "${contextPath}/security/audits/edit/" + index + "/${process}");
		</c:if>
		<c:if test="${process==2}">
		esd.common.defaultOpenWindowEx("复审", 920, 600, "${contextPath}/security/audits/edit/" + index + "/${process}");
		</c:if>
	};
	/**
	 * 查询数据 并校验所有输入框
	 */
	initAuditList.findData = function() {
		if (esd.common.validatebox()) {
			// 重新根据参数加载数据
			var params = initAuditList.getParams();
			$('#initAuditList_datagrid').datagrid('load',params);
		};
	};
</script>
<!-- 自定义菜单 -->
<div id="initAuditList_boolbar" data-options="fit:false,doSize:false" style="white-space: nowrap;height: 70px;margin-top: 5px">
	<table border="0" width="100%">
		<tr>
			<td width="80" style="text-align: right;" >年审时间:</td>
			<td width="150" ><input id="year" class="easyui-combobox" value="${nowYear}" data-options="height:30,editable:false" /></td>
			<td width="80" style="text-align: right;">流程状态:</td>
			<td width="150" ><input id="process" class="easyui-combobox" data-options="height:30,editable:false" /></td>
		</tr>
		<tr>
			<td style="text-align: right;" >档案编码 :</td>
			<td><input type="text" style="width: 100%" id="companyTaxCode" /></td>
			<td style="text-align: right;" >税务编码:</td>
			<td><input type="text" style="width: 100%" id="companyCode" /></td>
			<td width="80" style="text-align: right;" >企业名称 :</td>
			<td><input type="text" style="width: 100%" id="companyName" /></td>
			<td><a href="#"
			class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="initAuditList.findData()">查找</a></td>
		</tr>
	</table>
</div>
<!-- 数据表格 -->
<table id="initAuditList_datagrid"></table>

<script type="text/javascript">
	$(document).ready(function() {
		$('#year').combobox({
			url : 'parameter/getyears',
			valueField : 'id',
			textField : 'text'
		});
		$('#process').combobox({
			url : 'parameter/getStatus',
			valueField : 'id',
			textField : 'auditProcessStatus',
			value:'${process}' 
		});
	
		initAuditList.loadData();
	});
</script>

