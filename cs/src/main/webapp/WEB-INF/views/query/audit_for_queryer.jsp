<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<script type="text/javascript">
	var queryAudit = {};
	
	//载入标记
	var firstTime = 'firstTime';
	//缓存数据
	var tempCompanyName = '请输入查找企业的名称';
	var tempCompanyAddress = '请输入查找企业的地址';
	/**
		初始化数据
	 **/
	queryAudit.init = function() {
		
		//禁止继承的onkeydown事件
		window.onkeydown = function(event){
		}
		
		//年份
		$('#year').combobox({
			url : 'parameter/getyears',
			valueField : 'id',
			textField : 'text',
			onSelect : function(){
				queryAudit.findData();
			}
		});
		//名称, 地址
		$('#companyName').val(tempCompanyName);
		$('#companyAddress').val(tempCompanyAddress);
		
		//名称, 地址焦点获得事件
		$('#companyName').focus(function(){
			var companyName = $('#companyName').val();
			// 如果里面为预设的提示文字
			if(companyName == tempCompanyName){
				$('#companyName').val('');
				$('#companyName').css('color','black');
			}
		});
		$('#companyAddress').focus(function(){
			var companyAddress = $('#companyAddress').val();
			// 如果里面为预设的提示文字
			if(companyAddress == tempCompanyAddress){
				$('#companyAddress').val('');
				$('#companyAddress').css('color','black');
			}
		});
		
		//名称, 地址焦点离开事件
		$('#companyName').blur(function(){
			var companyName = $('#companyName').val();
			// 如果里面为预设的提示文字
			if(companyName == '' || companyName == null){
				$('#companyName').val(tempCompanyName);
				$('#companyName').css('color','gray');
			}
		});
		$('#companyAddress').blur(function(){
			var companyAddress = $('#companyAddress').val();
			// 如果里面为预设的提示文字
			if(companyAddress == '' || companyAddress == null){
				$('#companyAddress').val(tempCompanyAddress);
				$('#companyAddress').css('color','gray');
			}
		});
		
		//名称, 地址 回车事件
		$('#companyName').keydown(function(event){
			if(event.keyCode == 13){
				//如果为空, 且公司地址的内容为预设值, 则不触发查询事件
				var companyName = $('#companyName').val();
				var companyAddress = $('#companyAddress').val();
				if(companyName == '' && companyAddress == tempCompanyAddress){
					return;
				}
				queryAudit.findData();
			}
		});
		$('#companyAddress').keyup(function(event){
			if(event.keyCode == 13){
				//如果为空, 且公司名称的内容为预设值, 则不触发查询事件
				var companyName = $('#companyName').val();
				var companyAddress = $('#companyAddress').val();
				if(companyAddress == '' && companyName == tempCompanyName){
					return;
				}
				queryAudit.findData();
			}
		});

	};
	queryAudit.openAudit = function(index) {
		esd.common.defaultOpenWindowEx("年审查看", 920, 600,
				"${contextPath}/security/audits/view/" + index);
	};
	/*
	 * 获取企业基本档案函数
	 */
	queryAudit.loadData = function(params) {
		esd.common
				.datagrid(
						"#queryAuditGrid",
						"query/audit/listforcompany",
						"#queryAuditBoolbar",
						[ [
								{
									field : 'companyCode',
									title : '档案编码',
									width : 80
								},
								{
									field : 'companyTaxCode',
									title : '税务编码',
									width : 150
								},
								{
									field : 'companyId',
									hidden : true
								},
								{
									field : 'companyName',
									title : '企业名称',
									width : 300,
									formatter : function(value, row, index) {
										var c = '<a href="javascript:void(0);" onclick="queryAudit.openViewCompany('
												+ row.companyId
												+ ')">'
												+ value
												+ '</a>';
										return c;
									}
								},{
									field : 'companyLegal',
									title : '企业法人',
									width : 100
								},{
									field : 'companyAddress',
									title : '地址',
									width : 250
								},
								{
									field : 'companyPhone',
									title : '联系电话',
									width : 110
								},
								{
									field : 'auditProcessStatus',
									title : '审核状态',
									width : 100,
									align : 'center'
								} ] ], params);

	};

	/**
		组装查询参数
	 **/
	queryAudit.getParams = function() {
		var params = {};
		params.year = $("#year").combobox("getValue"); // 年度
		var companyName =  $("#companyName").val(); // 企业名称
		if(companyName == tempCompanyName){
			companyName = '';
		}
		params.companyName = companyName;
		var companyAddress =  $("#companyAddress").val(); // 企业名称
		if(companyAddress == tempCompanyAddress){
			companyAddress = '';
		}
		params.companyAddress = companyAddress;
		params.firstTime = firstTime;
		firstTime = '';
		return params;
	};

	/**
	 查询数据 并校验所有输入框
	 **/
	queryAudit.findData = function() {
		var companyName = $('#companyName').val();
		var companyAddress = $('#companyAddress').val();
		if(companyName == tempCompanyName && companyAddress == tempCompanyAddress){
			$('#companyName').focus();
			return;
		}
		if(companyName == '' && companyAddress == ''){
			$('#companyName').focus();
			return;
		}
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
		queryAudit
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
		<table id="queryAuditParams" style="width:100%">
			<tr>
				<td style="width:30%;text-align:right;" >年度:<input id="year" class="easyui-combobox" style="width:100%;" value="${nowYear}"
					data-options="height:30,editable:false" />
				</td>
				<td style="width:25%;text-align:left;"><input class="longtext inputElement"
					id="companyName" style="width:100%;color:gray;"/>
				</td>
				<td style="width:28%;text-align:left;"><input id="companyAddress"
					class="longtext inputElement" type="text" style="margin-left:2px;width:100%;color:gray;"/></td>
				<td colspan="3" style="text-align:left;">
					<div class="findBut">
						<a href="#" onclick="queryAudit.findData()" class="easyui-linkbutton"
							iconCls="icon-search">查询</a>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>

<div id="viewCompanyPanel"></div>
