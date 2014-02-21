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
		//流程状态
		$('#auditProcessStatus').combobox({
			url : 'parameter/getStatus',
			valueField : 'id',
			textField : 'auditProcessStatus'
		});

		//缴款人
		$('#paymentPerson').combobox({
			url : 'parameter/getPayer',
			valueField : 'id',
			textField : 'userName'
		});
	
		//初始化输入元素默认值
		var element=$(".inputElement");
		for(var i=0;i<element.length;i++){
			$(element[i]).val("");
		}
	};

	/**
	 * 获取企业基本档案函数
	 */
	queryAudit.loadData = function(params) {
		esd.common.datagrid("#queryAuditGrid", "query/company/list", "#queryAuditBoolbar", [ [ {
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
				var c = '<a href="#" onclick="esd.common.viewCompany(\'' + row.id + '\')">' + value + '</a>';

				return c;
			}
		}, {
			field : 'action',
			title : '操作',
			width : 370,
			align : 'center',
			formatter : function(value, row, index) {
				var e = '<a  href="#" onclick="printList.openAuditTab(\'' + row.id + '\')">年审表</a> ';
				var d = '<a href="#" onclick="printList.openWorkerList(\'' + row.id + '\')">残疾职工表</a> ';
				var v = '<a href="#" onclick="printList.openNotice(\'' + row.id + '\')">缴款通知书</a> ';
				return e + d + v;
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
		params.auditProcessStatus = $("#auditProcessStatus").combobox("getValue");// 流程状态
		params.paymentPerson = $("#paymentPerson").combobox("getValue");// 缴款人
		params.overYear = $("#overYear").val(); // 超过指定年度未初审的企业
		params.isExempt = $("#isExempt").val();// 是否免交
		return params;
	};

	/**
	 校验所有输入框 并 查询数据
	 **/
	queryAudit.findData = function() {
		if (esd.common.validatebox()) {
			//重新根据参数加载数据
			$('#queryAuditGrid').datagrid('load', queryAudit.getParams());
		}
	};

	//打印列表对象
	var printList = {};

	/**
	打开年审表
	 **/
	printList.openAuditTab = function(id) {
		esd.common.defaultOpenWindow("年审表", 'print/audit/' + id + '/' + $("#year").val());

	};
	/**
	打开职工列表
	 **/
	printList.openWorkerList = function(id) {
		esd.common.defaultOpenWindow("残疾职工列表", 'print/workerlist/' + id);

	};
	/**
	打开催缴通知书
	 **/
	printList.openNotice = function(id) {
		esd.common.defaultOpenWindow("催缴通知书", 'print/notice/' + id + '/' + $("#year").val());

	};

	$(function() {
		queryAudit.init();
		//加载单位档案数据
		queryAudit.loadData(queryAudit.getParams());

	});
</script>



<!-- 数据表格 -->
<table id="queryAuditGrid"></table>

<!-- 自定义菜单 -->
<div id="queryAuditBoolbar">
	<div style="">
		<table>
			<tr>
				<td>年度:</td>
				<td><input id="year" class="easyui-combobox" value="${nowYear}" data-options="height:30,editable:false" />
				</td>
				<td>档案号码:</td>
				<td><input type="text" id="companyCode" class="inputElement"/>
				</td>
				<td>税务号码:</td>
				<td><input id="companyTaxCode" type="text"  class="inputElement"/>
				</td>
				<td>组织机构代码证:</td>
				<td><input type="text" id="companyOrganizationCode" class="inputElement" /></td>

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
				<td><input type="text" style="width: 40px" id="companyEmpTotal_1"  data-options="validType:['_number']" class="easyui-validatebox inputElement" />-<input type="text" style="width: 40px" id="companyEmpTotal_2"  data-options="validType:['_number']" class="easyui-validatebox inputElement" />人</td>
			</tr>
			<tr>
				<td>企业名称:</td>
				<td colspan="3"><input class="longInputBox inputElement" id="companyName" />
				</td>
				<td>企业地址:</td>
				<td colspan="3"><input class="longInputBox inputElement" id="companyAddress" type="text" /></td>

			</tr>
			<tr>
				<td>法人代表:</td>
				<td><input id="companyLegal" type="text" class="inputElement" /></td>
			</tr>
			<tr>
				<td colspan="8">
					<hr /></td>
			</tr>
			<tr>
				<td>流程状态:</td>
				<td><input id="auditProcessStatus" class="easyui-combobox" data-options="height:30,editable:false" /> 单位</td>

				<td>缴款人:</td>
				<td><input type="text" id="paymentPerson" class="easyui-combobox" data-options="height:30,editable:false" /></td>
				<td>超过:</td>
				<td><input id="overYear" type="text"  style="width: 60px" data-options="validType:['_number']" class="easyui-validatebox inputElement" /> 年未初审</td>

				<td>是否免交:</td>
				<td><select id="isExempt">
						<option value="" selected="selected">请选择</option>
						<option value="true">是</option>
						<option value="false">否</option>

				</select></td>
			</tr>
		</table>
		<div class="findBut">
			<a href="javascript:queryAudit.findData()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			 <a href="javascript:queryAudit.init()" class="easyui-linkbutton" iconCls="icon-redo">重置</a> 
			<a href=""
				class="easyui-linkbutton" iconCls="icon-ok">生成excel</a>
		</div>
	</div>
</div>
