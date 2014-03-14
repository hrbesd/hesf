<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<script type="text/javascript">
	var queryPayment = {};
	/**
		初始化数据
	 **/
	queryPayment.init = function() {
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

		//缴款人
		$('#paymentPerson').combobox({
			url : 'parameter/getPayer',
			valueField : 'id',
			textField : 'userName'
		});
		//初始化输入元素默认值
		var element = $(".inputElement");
		for ( var i = 0; i < element.length; i++) {
			$(element[i]).val("");
		}

	};
	queryPayment.openPayment = function(index) {
		esd.common.defaultOpenWindowEx("查看缴款信息", 920, 600, "${contextPath}/security/audits/edit/" + index + "/2");
	};
	/*
	 * 获取缴款基本档案函数
	 */
	queryPayment.loadData = function(params) {
		esd.common.datagrid("#queryPaymentGrid", "query/payment/list", "#queryPaymentBoolbar", [ [ {
			field : 'paymentDate',
			title : '缴款时间',
			width : 120
		}, {
			field : 'companyCode',
			title : '公司档案号',
			width : 120
		},{
			field : 'companyName',
			title : '缴款公司',
			width : 300
		}, {
			field : 'paymentBill',
			title : '缴款票号',
			width : 180
		},{
			field : 'paymentMoney',
			title : '缴款金额',
			width : 110
		}, {
			field : 'paymentPerson',
			title : '缴款操作人',
			width : 120
		}, {
			field : 'paymentType',
			title : '缴款方式',
			width : 150
		}, {
			field : 'paymentExceptional',
			title : '缴款方式',
			width : 100
		}
	/*	{
			field : 'action',
			title : '操作',
			width : 100,
			align : 'center',
			formatter : function(value, row, index) {
				var v = '<a href="#" onclick="queryPayment.openPayment(' + row.id + ')">查看</a>';
				return v;
			}
		}	*/
	] ], params);

	};

	/**
		组装查询参数
	 **/
	queryPayment.getParams = function() {
		var params = {};
		params.year = $("#year").combobox("getValue"); // 年度
		params.companyCode = $("#companyCode").val(); // 档案号码
		params.companyName = $("#companyName").val(); // 企业名称
		params.companyProperty = $("#companyProperty").combobox("getValue");// 公司性质 _
		params.companyEconomyType = $("#companyEconomyType").combobox("getValue");// 企业经济类型
		params.area = $("#area").combobox("getValue");// 公司所属地区
		params.paymentPerson = $("#paymentPerson").combobox("getValue");// 缴款人
		params.startDate = $('#startDate').combobox("getValue");	//缴款起始时间
		params.endDate = $('#endDate').combobox("getValue");	//缴款结束时间
		var billReturn = $('#billReturn').attr('checked');
		if(billReturn == 'checked'){
			params.billReturn = true;
		}
		var billObsolete = $('#billObsolete').attr('checked');
		if(billObsolete == 'checked'){
			params.billObsolete = true;
		}
		return params;
	};

	/**
	 查询数据 并校验所有输入框
	 **/
	queryPayment.findData = function() {
		if (esd.common.validatebox("#queryPaymentParams")) {
			//重新根据参数加载数据
			$('#queryPaymentGrid').datagrid('load', queryPayment.getParams());
		}
		;
	};
	/**
	查看企业信息框
	 **/
	queryPayment.openViewPayment = function(id) {

	esd.common.defaultOpenWindow("查看缴款信息", 'company/view/' + id);
	};

	//组件解析完成
	$.parser.onComplete = function() {
		//加载单位档案数据
		queryPayment.loadData(queryPayment.getParams());
	};
	$(function() {
		queryPayment.init();

	});
</script>



<!-- 数据表格 -->
<table id="queryPaymentGrid"></table>

<!-- 自定义菜单 -->
<div id="queryPaymentBoolbar">
	<div class="paramsTab">
		<table id="queryPaymentParams">
			<tr>
				<td class="tipsText">审核年度:</td>
				<td><input id="year" class="easyui-combobox" value="" data-options="height:30,editable:false" /></td>
				<td class="tipsText">档案号码:</td>
				<td><input type="text" id="companyCode" class="inputElement" /></td>
				<td class="tipsText">企业名称:</td>
				<td colspan="3"><input class="longtext inputElement" id="companyName" style="width:426px" /></td>

			</tr>
			<tr>
				<td class="tipsText">企业性质:</td>
				<td><input id="companyProperty" class="easyui-combobox" data-options="height:30,editable:false" /></td>
				<td class="tipsText">经济类型:</td>
				<td><input id="companyEconomyType" class="easyui-combobox" data-options="height:30,editable:false" /></td>
				<td class="tipsText">地区:</td>
				<td><input id="area" class="easyui-combobox" data-options="height:30,editable:false" value="10230000" /></td>
				<td class="tipsText">缴款操作人:</td>
				<td><input type="text" id="paymentPerson" class="easyui-combobox" data-options="height:30,editable:false" />
				</td>
			</tr>
			<tr>
				<td class="tipsText">缴款时间:</td>
				<td colspan="3"><input class="easyui-datebox" name="startDate" id="startDate" data-options="height:30,showSeconds:false" style="width:150px" />至
				<input class="easyui-datebox" name="endDate" id="endDate" data-options="height:30,showSeconds:false" style="width:150px" />
				</td>
				<td class="tipsText" colspan="4" style="text-align:left;padding-left:20px;font-size:13px;">
				已返票 : <input id="billReturn" type="checkbox" style="height:auto;margin-right:20px;"/>
				作废票据号 : <input id="billObsolete" type="checkbox" style="height:auto" />
				</td>
			</tr>
		</table>
		<div class="findBut">
			<a href="javascript:queryPayment.findData()" class="easyui-linkbutton" iconCls="icon-search">查询</a> <a href="javascript:queryPayment.init()" class="easyui-linkbutton" iconCls="icon-redo">重置</a>

		</div>
	</div>
</div>

<div id="viewCompanyPanel"></div>
