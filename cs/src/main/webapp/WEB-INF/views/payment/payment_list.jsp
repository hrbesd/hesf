<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	payment = {};
	payment.getParams = function() {
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
		加载账目列表数据
	 */
	payment.loadData = function() {
		var params = payment.getParams();
		esd.common.datagrid("#paymentList_datagrid", "${contextPath}/security/accounts/list", "#paymentList_boolbar", [ [ {
			field : 'companyCode',
			title : '档案编码',
			width : 150
		}, {
			field : 'companyTaxCode',
			title : '税务编码',
			width : 150
		}, {
			field : 'companyId',
			hidden : true
		}, {
			field : 'year',
			hidden : true
		},{
			field : 'companyName',
			title : '企业名称',
			width : 900,
			formatter : function(value, row, index) {
				var c = '<a href="javascript:void(0);" onclick="esd.common.viewCompany(\'' + row.companyId + '\')">' + value + '</a>';
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
				var v = '<a href="javascript:payment.open(' + row.companyId + ','+ row.year +');" >查看</a>';
				var c = '<a href="javascript:void(0);" >待初审</a>';
				var f = '<a href="javascript:void(0);" >待复审</a>';
				var d = '<a href="javascript:void(0);" >已达标</a>';
				var j = '<a href="javascript:payment.open(' + row.companyId + ','+ row.year +');" >缴款</a>';
				if (row.auditProcessStatusId == 1 || row.auditProcessStatusId == 7) {
					return c;
				}
				if (row.auditProcessStatusId == 2) {
					return f;
				}
				if (row.auditProcessStatusId == 3 || row.auditProcessStatusId == 4) {
					return j;
				}
				if (row.auditProcessStatusId == 6) {
					return d;
				}
				return v;
			}
		} ] ], params);
	};
	/*
	打开单位缴款面板
	 */
	payment.open = function(companyId,year) {
		esd.common.defaultOpenWindow("缴款", "${contextPath}/security/payment/edit/" + year+"/"+companyId);
	};
</script>
<!-- 自定义菜单 -->
<div id="paymentList_boolbar" data-options="fit:false,doSize:false" style="white-space: nowrap;height: 70px;">
	<table width="100%" border="0">
		<tr>
			<td width="80" style="text-align: right;">缴款年度:</td>
			<td width="150"><input id="year" class="easyui-combobox" value="${nowYear }"
				data-options="height:30,editable:false"/>
			</td>
			<td width="80" style="text-align: right;">流程状态:</td>
			<td width="150"><input id="process" class="easyui-combobox" data-options="height:30,editable:false,panelHeight:240" />
			</td>
			<td width="80" style="text-align: right;">实缴金额:</td>
			<td width="150"><input type="text" id="money" />
			</td>
			<td width="100%"></td>
		</tr>
		<tr>
			<td style="text-align: right;">档案编码:</td>
			<td><input type="text" style="width: 100%" id="companyCode" />
			</td>
			<td style="text-align: right;">税务编码:</td>
			<td><input type="text" style="width: 100%" id="companyTaxCode" />
			</td>
			<td style="text-align: right;">企业名称 :</td>
			<td colspan="3"><input type="text" style="width: 100%" id="companyName" />
			</td>
			<td><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="payment.loadData()">查找</a>
			</td>
		</tr>
	</table>
</div>
<!-- 数据表格 -->
<table id="paymentList_datagrid"></table>
<script type="text/javascript">
	$.parser.onComplete = function() {
		$('#year').combobox({
			url : 'parameter/getAccountsYears',
			valueField : 'id',
			textField : 'text'
		});
		$('#process').combobox({
			url : 'parameter/getStatus',
			valueField : 'id',
			textField : 'auditProcessStatus',
			value : '${process}'
		});
		payment.loadData();
	};
</script>

