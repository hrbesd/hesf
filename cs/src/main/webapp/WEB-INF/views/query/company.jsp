<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />



<script type="text/javascript">
	var queryCompany = {};
	/**
		初始化数据
	 **/
	queryCompany.init = function() {
		//企业性质
		$('#companyProperty').combobox({
			url : 'parameter/property',
			valueField : 'id',
			textField : 'companyProperty',
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

		$('#year').combobox({
			url : 'parameter/getyears',
			valueField : 'id',
			textField : 'text'
		});
	};

	/*
	 * 获取企业基本档案函数
	 */
	queryCompany.loadData = function(params) {
		esd.common.datagrid("#queryCompanyGrid", "query/company/list", "#queryCompany_boolbar", [ [ {
			field : 'companyArea',
			title : '地区',
			width : 230
		}, {
			field : 'companyCode',
			title : '档案编码',
			width : 230
		}, {
			field : 'companyTaxCode',
			title : '税务编码',
			width : 230
		}, {
			field : 'companyName',
			title : '企业名称',
			width : 800,
			formatter : function(value, row, index) {
			
				var c = '<a href="#" onclick="queryCompany.openViewCompany(\'' + row.id + '\')">' + value + '</a>';
				return c;
			}

		} ] ], params);

	};

	/**
		组装查询参数
	 **/
	queryCompany.getParams = function() {
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
		params.companyHandicapTotal_1 = $("#companyHandicapTotal_1").val();// 残疾员工总数
		params.companyHandicapTotal_2 = $("#companyHandicapTotal_2").val();// 残疾员工总数
		return params;
	};

	/**
	 查询数据 并校验所有输入框
	 **/
	queryCompany.findData = function() {
		if (esd.common.validatebox()) {
			//重新根据参数加载数据
			$('#queryCompanyGrid').datagrid('load', queryCompany.getParams());
		};
	};
	/**
	查看企业信息框
	**/
	queryCompany.openViewCompany = function(id) {
		esd.common.defaultOpenWindow("查看企业信息",'company/view/' + id);
		
	};
	$(function() {
		queryCompany.init();
		//加载单位档案数据
		queryCompany.loadData(queryCompany.getParams());

	});
</script>

<table id="queryCompanyGrid"></table>
<!-- 自定义菜单 -->
<div id="queryCompany_boolbar">
	<div style="">
		<table>
			<tr>
				<td>年度:</td>
				<td><input id="year" class="easyui-combobox" value="${nowYear}" data-options="height:30,editable:false" />
				</td>
				<td>档案号码:</td>
				<td><input type="text" id="companyCode" />
				</td>
				<td>税务号码:</td>
				<td><input id="companyTaxCode" type="text" />
				</td>
				<td>组织机构代码证:</td>
				<td><input type="text" id="companyOrganizationCode" /></td>

			</tr>
			<tr>
				<td>企业性质:</td>
				<td><input id="companyProperty" class="easyui-combobox" data-options="height:30,editable:false" />
				</td>
				<td>经济类型:</td>
				<td><input id="companyEconomyType" class="easyui-combobox" data-options="height:30,editable:false" />
				</td>
				<td>地区:</td>
				<td><input id="area" class="easyui-combobox" data-options="height:30,editable:false" value="10230000" />
				</td>
				<td>企业人数:</td>
				<td>
				<input type="text" style="width: 40px" 	id="companyEmpTotal_1"  data-options="validType:['_number']" class="easyui-validatebox" />-
				<input type="text" style="width: 40px" 	id="companyEmpTotal_2"  data-options="validType:['_number']" class="easyui-validatebox" />
		
					</td>
			</tr>
			<tr>
				<td>企业名称:</td>
				<td colspan="3"><input class="longInputBox" id="companyName" />
				</td>
				<td>企业地址:</td>
				<td colspan="3"><input class="longInputBox"  type="text" id="companyAddress" /></td>

			</tr>
			<tr>
				<td>法人代表:</td>
				<td><input id="companyLegal" type="text" /></td>
				<td>残疾职工数:</td>
				<td>
				<input type="text" style="width: 40px" 	id="companyHandicapTotal_1"  data-options="validType:['_number']" class="easyui-validatebox" />
				-
				<input type="text" style="width: 40px" 	id="companyHandicapTotal_2"  data-options="validType:['_number']" class="easyui-validatebox" />人
				</td>
			</tr>
			

		</table>
		<div class="findBut">
			<a href="javascript:queryCompany.findData()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	
			<a href="" class="easyui-linkbutton" iconCls="icon-redo">重置</a>
			<a href="" class="easyui-linkbutton" iconCls="icon-ok">生成excel</a>
		</div>
	</div>
</div>


