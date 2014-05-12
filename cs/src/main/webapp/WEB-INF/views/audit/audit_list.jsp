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
			field : 'year',
			title : '审核年度',
			width : 100
		}, {
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
			width : 700,
			formatter : function(value, row, index) {
				var c = '<a href="javascript:void(0);" onclick="esd.common.viewCompany(\'' + row.companyId + '\')">' + value + '</a>';
				return c;
			}
		}, {
			field : 'auditProcessStatus',
			title : '流程状态',
			width : 150
		}, {
			field : 'auditProcessStatusId',
			hidden : true
		}, {
			field : 'action',
			title : '操作',
			width : 100,
			align : 'center',
			formatter : function(value, row, index) {
				var r = '';
				var c = '<a href="javascript:initAuditList.openAudit(' + row.id + ');" >初审</a>';
				var v = '<a href="javascript:initAuditList.viewAudit(' + row.id + ');" >查看</a>';
				var f = '<a href="javascript:initAuditList.openAudit(' + row.id + ');" >复审</a>';
				var ff = '<a href="javascript:initAuditList.openAudit(' + row.id + ');" >终审</a>';
				if (row.auditProcessStatusId == 1) {
					r = '<c:if test="${process==1}">' + c + '</c:if><c:if test="${process!=1}">' + v + '</c:if>';
					return r;
				}
				if (row.auditProcessStatusId == 2) {
					r = '<c:if test="${process==2}">' + f + '</c:if><c:if test="${process!=2}">' + v + '</c:if>';
					return r;
				}
				if (row.auditProcessStatusId == 7) {
					r = '<c:if test="${process==7}">' + c + '</c:if><c:if test="${process!=7}">' + v + '</c:if>';
					return r;
				}
				if (row.auditProcessStatusId == 8) {
					r = '<c:if test="${process==8}">' + ff + '</c:if><c:if test="${process!=8}">' + v + '</c:if>';
					return r;
				}
				if (row.auditProcessStatusId == 9) {
					r = '<c:if test="${process == 9}">' + c + '</c:if><c:if test="${process!=9}">' + v + '</c:if>';
					return r;
				}
				return v;
			}
		} ] ], params);
	};
	/*
	打开单位初审面板
	 */
	initAuditList.openAudit = function(index) {
		<c:if test="${process==1}">
			esd.common.defaultOpenWindowEx("初审", 920, 600, "${contextPath}/security/audits/edit/" + index + "/${process}");
		</c:if>
		<c:if test="${process==7}">
			esd.common.defaultOpenWindowEx("初审", 920, 600, "${contextPath}/security/audits/edit/" + index + "/1");
		</c:if>
		<c:if test="${process==9}">
			esd.common.defaultOpenWindowEx("初审", 920, 600, "${contextPath}/security/audits/edit/" + index + "/1");
		</c:if>
		<c:if test="${process==2}">
			esd.common.defaultOpenWindowEx("复审", 920, 600, "${contextPath}/security/audits/edit/" + index + "/${process}");
		</c:if>
		<c:if test="${process==8}">
			esd.common.defaultOpenWindowEx("终审", 920, 600, "${contextPath}/security/audits/edit/" + index + "/${process}");
		</c:if>
	};
	initAuditList.viewAudit = function(index) {
		esd.common.defaultOpenWindowEx("查看", 920, 600, "${contextPath}/security/audits/edit/" + index + "/10");
	};
	
	/**
	 * 新建审计记录
	 **/
	initAuditList.createAudit = function() {
		esd.common.defaultOpenWindowEx("新建审计记录", 600, 200, "${contextPath}/security/audits/create");
	};

	/**
	 * 查询数据 并校验所有输入框
	 */
	initAuditList.findData = function() {
		var params = initAuditList.getParams();
		$('#initAuditList_datagrid').datagrid('reload', params);
	};
</script>
<!-- 自定义菜单 -->

<div id="initAuditList_boolbar" data-options="fit:false,doSize:false" style="white-space: nowrap;height: 70px;">
	<table border="0" width="100%">
		<tr>
			<td width="80" style="text-align: right;">年审时间:</td>
			<td width="150"><input id="year" class="easyui-combobox" value="${nowYear }" data-options="height:30,editable:false" />
			</td>
			<td width="80" style="text-align: right;">流程状态:</td>
			<td width="150"><input id="process" class="easyui-combobox" data-options="height:30,editable:false,panelHeight:240" />
			</td>
			<c:if test="${process==1}">
				<td colspan="3" style="text-align: right;">
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="initAuditList.createAudit();">添加年审记录</a>
				</td>
			</c:if>
		</tr>
		<tr>
			<td style="text-align: right;">档案编码:</td>
			<td><input type="text" style="width: 100%" id="companyCode" />
			</td>
			<td style="text-align: right;">税务编码:</td>
			<td><input type="text" style="width: 100%" id="companyTaxCode" />
			</td>
			<td width="80" style="text-align: right;">企业名称:</td>
			<td><input type="text" style="width: 100%" id="companyName" />
			</td>
			<td><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="initAuditList.findData()">查找</a>
			</td>
		</tr>
	</table>
</div>
<!-- 数据表格 -->
<table id="initAuditList_datagrid"></table>
<div id="account" />
<script type="text/javascript">
	$.parser.onComplete = function(v) {
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
		initAuditList.loadData();
	};
</script>

