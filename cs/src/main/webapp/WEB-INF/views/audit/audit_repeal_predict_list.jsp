<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	initAuditList = {};
	initAuditList.getParams = function() {
		var params = {};
		params.year = $('#year').val();	//当前审核年份
		params.companyCode = $('#companyCode').val();
		params.companyName = $('#companyName').val();
		return params;
	};

	/**
	 * 加载销补预订人数列表数据
	 */
	initAuditList.loadData = function() {
		var params = initAuditList.getParams();
		esd.common.datagrid("#initAuditList_datagrid", "${contextPath}/security/audits/repealPredictList", "#initAuditList_boolbar", [ [ {
			field : 'year',
			title : '审核年度',
			width : 80
		}, {
			field : 'companyCode',
			title : '档案编码',
			width : 100
		}, {
			field : 'companyId',
			hidden : true
		}, {
			field : 'companyName',
			title : '企业名称',
			width : 400,
			formatter : function(value, row, index) {
				var c = '<a href="javascript:void(0);" onclick="esd.common.viewCompany(\'' + row.companyId + '\')">' + value + '</a>';
				return c;
			}
		}, {
			field : 'initAuditDate',
			align : 'center',
			title : '初审时间',
			width : 120
		}, {
			field : 'initAuditUser',
			align : 'center',
			title : '初审人',
			width : 100
		}, {
			field : 'verifyAuditDate',
			align : 'center',
			title : '复审时间',
			width : 120
		}, {
			field : 'verifyAuditUser',
			align : 'center',
			title : '复审人',
			width : 100
		},{
			field : 'auditProcessStatus',
			align : 'center',
			title : '当前状态',
			width : 120,
			styler :function(value,row,index){
				return "font-size:10px;color:red;";
			}
		}, {
			field : 'auditProcessStatusId',
			hidden : true
		}, {
			field : 'action',
			title : '操作',
			width : 80,
			align : 'center',
			formatter : function(value, row, index) {
				var r = '';
				var c = '<a href="javascript:initAuditList.openRepealPredict(' + row.id +');" >销补</a>';
			//	var v = '<a href="javascript:initAuditList.viewAudit(' + row.id + ');" >查看</a>';
				// 未初审
			/*	if (row.auditProcessStatusId == 1) {
					r = '<c:if test="${process==1}">' + c + '</c:if><c:if test="${process!=1}">' + v + '</c:if>';
					return r;
				}	*/
				return c;
			}
		} ] ], params);
	};
	/**
	 * 打开单位销补预订人数面板
	 */
	initAuditList.openRepealPredict = function(auditId,companyId,year) {
		esd.common.defaultOpenWindowEx("销补预定人数", 920, 600, "${contextPath}/security/audits/repealPredict/"+auditId);
	};
	
	initAuditList.viewAudit = function(index) {
		esd.common.defaultOpenWindowEx("查看", 920, 600, "${contextPath}/security/audits/edit/" + index + "/3");
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

<div id="initAuditList_boolbar" data-options="fit:false,doSize:false" style="white-space: nowrap;">
	<table border="0" width="100%">
		<tr>
			<td width="80" style="text-align: right;">年审时间:</td>
			<td width="80" >
				<input id="year" value="${nowYear }" disabled="disabled" style="width:70px;"/>
			</td>
			<td width="80" style="text-align: right;">档案编码:</td>
			<td width="100"><input type="text" style="width: 100%" id="companyCode" />
			</td>
			<td width="80" style="text-align: right;">企业名称:</td>
			<td width="300"><input type="text" style="width: 100%" id="companyName" />
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
		initAuditList.loadData();
	};
</script>

