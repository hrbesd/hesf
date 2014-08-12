<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var historyList = {};

	/**	
		加载列表数据
	 **/
	historyList.loadData = function(params) {
		esd.common.datagrid("#historyList_dataGrid", "query/audit/history", "#historyListBoolbar", [ [
		{
			field : 'year',
			title : '审核年度',
			width : 70
		},{
			field : 'companyEmpTotal',
			title : '员工总数',
			width : 70
		},{
			field : 'companyHandicapTotal',
			title : '残疾职工',
			width : 70
		},{
			field : 'companyShouldTotal',
			title : '应安排数',
			width : 70
		},{
			field : 'companyAlreadyTotal',
			title : '已安排数',
			width : 70
		},{
			field : 'amountPayable',
			title : '应缴金额',
			width : 80
		},{
			field : 'reductionAmount',
			title : '减缴金额',
			width : 70
		},/*{
			field : 'remainAmount',
			title : '上年未缴',
			width : 80
		},{
			field : 'complementAmount',
			title : '补缴',
			width : 70
		},*/{
			field : 'delayPayAmount',
			title : '滞纳金',
			width : 70
		},/*{
			field : 'isDelayPay',
			title : '补缴金额',
			width : 80
		},*/{
			field : 'isDelayPay',
			title : '免滞纳金?',
			width : 80,
			formatter : function(index,row,value){
				var w = '';
				if(row.isDelayPay == 'true' || row.isDelayPay == true){
					w = '是';
				}else{
					w = '否';
				}
				return w;
			}	
		},{
			field : 'payAmount',
			title : '实缴总额',
			width : 80
		},{
			field : 'auditProcessStatus',
			title : '审核状态',
			width : 100,
			styler : function (value,row,index){
				return "font-size:30px;";
			}
		} ] ], params,function(){
		});
	};

	/**
	 职工年龄检测
	 **/
	historyList.ageDetection = function(value, row, index) {
		return '';
	};

	/**
		组装查询参数
	 **/
	historyList.getParams = function() {

		params = {};
		params.year = $("#currentYear").val();//年份
		params.companyId = $("#companyId").val();//单位id
		return params;
	};

	/**
	 * 读取公司相关信息
	 **/
	historyList.getCompany = function() {
		$.ajax({
			url : 'company/getinformation/' + $("#companyId").val()+'/'+$("#currentYear").val(),
			type : 'post',
			success : function(data) {
				if (data.length > 0) {
					$("#company_name").html(data[0].companyName);//企业名字
					$("#company_code").html(data[0].companyCode);// 档案编码
					$("#company_address").html(data[0].companyAddress);// 公司地址
					$("#company_legal").html(data[0].companyLegal);// 法人/联系人
				} else {
					$.messager.alert('消息', '未获得到企业信息数据。', 'error');
				}

			},
			error : function() {
			$.messager.alert('消息', '残疾职工列表，获取企业信息错误。', 'error');
			
			}
		});

	};

	//组件解析完成
	$.parser.onComplete = function() {
		historyList.loadData(historyList.getParams());
		historyList.getCompany();
	};
	$(function(){

	});


</script>
<!-- 数据表格 -->
<table id="historyList_dataGrid"></table>

<input type="hidden" id="currentYear" value="${year}" />
<input type="hidden" id="companyId" value="${companyId}" />
<!-- 自定义菜单 -->
<div id="historyListBoolbar">

	<table class="historyListTip" cellspacing="0" cellpadding="0" style="width:100%">
		<tr>
			<td class="tipTextEx" width="15%">企业名称:</td>
			<td id="company_name" style="width:40%;" class="readonly"></td>
			<td class="tipTextEx" width="15%">档案编码:</td>
			<td id="company_code" class="readonly"></td>

		</tr>
		<tr>
			<td class="tipTextEx">地址:</td>
			<td id="company_address" class="readonly"></td>
			<td class="tipTextEx">联系人</td>
			<td id="company_legal" class="readonly"></td>
		</tr>
	</table>

	<style type="text/css">
		.historyListTip tr td{
			border: 1px solid #95B8E7;
		}
	</style>
</div>



