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
		//是否免交
		$('#isExempt').combobox({
			data : [ {
				label : '',
				value : '请选择'
			}, {
				label : 'true',
				value : '是'
			}, {
				label : 'false',
				value : '否'
			} ],
			valueField : 'label',
			textField : 'value',
			editable : false,
			height : 30,
			width : 140,
			panelHeight : 80
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
			width : 150
		}, {
			field : 'companyName',
			title : '缴款公司',
			width : 400
		}, {
			field : 'paymentMoney',
			title : '缴款金额',
			width : 150
		}, {
			field : 'paymentPerson',
			title : '缴款操作人',
			width : 150
		}, {
			field : 'paymentType',
			title : '缴款方式',
			width : 150
		}, {
			field : 'paymentExceptional',
			title : '缴款方式',
			width : 100
		},{
			field : 'action',
			title : '操作',
			width : 100,
			align : 'center',
			formatter : function(value, row, index) {
				var v = '<a href="#" onclick="queryPayment.openPayment(' + row.id + ')">查看</a>';
				return v;
			}
		} ] ], params);

	};

	/**
		组装查询参数
	 **/
	queryPayment.getParams = function() {
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

		params.paymentPerson = $("#paymentPerson").combobox("getValue");// 缴款人

		params.overYear = $("#overYear").val(); // 超过指定年度未初审的企业
		params.isExempt = $("#isExempt").combobox("getValue");// 是否免交
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
				<td class="tipsText">年度:</td>
				<td><input id="year" class="easyui-combobox" value="${nowYear}" data-options="height:30,editable:false" /></td>
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
				<td colspan="3"><input class="easyui-datebox" name="startDate" id="startDate" value="" data-options="height:30,showSeconds:false" style="width:150px" />至
				<input class="easyui-datebox" name="endDate" id="endDate" value="" data-options="height:30,showSeconds:false" style="width:150px" />
				</td>

				<td class="tipsText" colspan="4" >返票:<input type="checkbox" /></td>
		<!-- 		<td><input type="text" id="isExempt" />
				</td>	 -->
			</tr>
		</table>
		<div class="findBut">
			<a href="javascript:queryPayment.findData()" class="easyui-linkbutton" iconCls="icon-search">查询</a> <a href="javascript:queryPayment.init()" class="easyui-linkbutton" iconCls="icon-redo">重置</a>

		</div>
	</div>
</div>

<div id="viewCompanyPanel"></div>
