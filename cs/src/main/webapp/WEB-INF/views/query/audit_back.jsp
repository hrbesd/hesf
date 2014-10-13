<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<script type="text/javascript">
	var queryAudit = {};
	/**
		初始化数据
	 **/
	queryAudit.init = function() {
		//年份
		$('#year').combobox({
			url : 'parameter/getyears',
			valueField : 'id',
			textField : 'text'
		});
		//初始化输入元素默认值
		var element = $(".inputElement");
		for ( var i = 0; i < element.length; i++) {
			$(element[i]).val("");
		}
	};
	queryAudit.openAudit = function(index) {
		esd.common.defaultOpenWindowEx("年审查看", 920, 600,
				"${contextPath}/security/audits/edit/" + index+"/3");
	};
	/*
	 * 获取企业基本档案函数
	 */
	queryAudit.loadData = function(params) {
		esd.common
				.datagrid(
						"#queryAuditGrid",
						"query/audit/list",
						"#queryAuditBoolbar",
						[ [
								{
									field : 'companyCode',
									title : '档案编码',
									width : 75 
								},
								{
									field : 'companyId',
									hidden : true
								},
								{
									field : 'companyName',
									title : '企业名称',
									width : 250,
									formatter : function(value, row, index) {
										var c = '<a href="javascript:void(0);" onclick="queryAudit.openViewCompany('
												+ row.companyId
												+ ')">'
												+ value
												+ '</a>';
										return c;
									}
								},{
									field : 'initAuditDate',
									align : 'center',
									title : '初审时间',
									width : 90
								}, {
									field : 'initAuditUser',
									align : 'center',
									title : '初审人',
									width : 90
								}, {
									field : 'verifyAuditDate',
									align : 'center',
									title : '复审时间',
									width : 90
								}, {
									field : 'verifyAuditUser',
									align : 'center',
									title : '复审人',
									width : 90
								},{
									field : 'auditProcessStatusId',
									hidden : true
								},{
									field : 'auditProcessStatus',
									title : '审核状态',
									width : 80
								},{
									field : 'action',
									title : '操作',
									width : 40,
									align : 'center',
									formatter : function(value, row, index) {
										var v = '<a href="#" onclick="queryAudit.openAudit('
												+ row.id + ')">查看</a>';
										return v;
									}
								} ] ], params);

	};

	/**
		组装查询参数
	 **/
	queryAudit.getParams = function() {
		var params = {};
		params.year = $("#year").combobox("getValue"); // 年度
		params.companyCode = $("#companyCode").val(); // 档案号码
		params.companyName = $("#companyName").val(); // 企业名称
		params.auditProcessStatus = $("#auditProcessStatus").val(); // 流程状态
		return params;
	};

	/**
	 查询数据 并校验所有输入框
	 **/
	queryAudit.findData = function() {
		if (esd.common.validatebox("#queryAuditParams")) {
			//重新根据参数加载数据
			$('#queryAuditGrid').datagrid('load', queryAudit.getParams());
		}
		;
	};
	
	/**
	查看企业信息框
	 **/
	queryAudit.openViewCompany = function(id) {
		esd.common.defaultOpenWindow("查看企业信息", 'company/view/' + id);
	};

	//组件解析完成
	$.parser.onComplete = function() {
		//加载单位档案数据
		queryAudit.loadData(queryAudit.getParams());
	};
	$(function() {
		queryAudit.init();

	});
</script>



<!-- 数据表格 -->
<table id="queryAuditGrid"></table>

<!-- 自定义菜单 -->
<div id="queryAuditBoolbar">
	<div class="paramsTab">
		<table id="queryAuditParams">
			<tr>
				<td class="tipsText" style="width:50px;">年度:</td>
				<td>
					<input id="year" class="easyui-combobox" value="${nowYear}" data-options="width:70,height:30,editable:false" />
			 		<input type="hidden" id="auditProcessStatus" name="auditProcessStatus" value="${process }" />
				</td>
				<td class="tipsText" style="width:80px;">档案号码:</td>
				<td style="width:50px;">
					<input type="text" id="companyCode" class="inputElement" />
				</td>
				<td class="tipsText" style="width:80px;">企业名称:</td>
				<td style="width:300px;">
					<input class="longtext inputElement" id="companyName" />
				</td>
				<td>
					<a href="#" onclick="queryAudit.findData()" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
					<a href="javascript:queryAudit.init()" class="easyui-linkbutton" iconCls="icon-redo">重置</a> 
				</td>
			</tr>
		</table>
	</div>
</div>

<div id="viewCompanyPanel"></div>
