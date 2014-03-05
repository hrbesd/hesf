<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style>

/*表格部分*/
#payment .company-examined {
	width: 886px;
	/*background-color: #6AB7DB;*/
	background-color: #E0ECFF;
	text-align: center;
	/*border: #95B8E7 1px solid;*/
}

#payment .td_short {
	width: 100px;
}

#payment .td_GD_short {
	width: 70px;
}

#payment .td_short_150 {
	width: 150px;
}

#payment .td_zhong {
	width: 200px;
}

#payment .text_short {
	width: 80px;
}

#payment .bj_belu {
	text-align: left;
}

#payment .bj_belu2 {
	text-align: left;
	width: 117px;
}

#payment .readonly {
	background: #d8d8d8;
	height: 30px;
}

#payment .warn {
	background-color: #D8D828;
}

#payment td {
	font-size: 12px;
}

#payment input {
	font-size: 12px;
	/*height: 18px;*/
	line-height: 28px;
	margin: 0px;
	width: 100%;
	border: 0px;
}

#payment textarea {
	font-size: 12px;
	line-height: 28px;
	width: 100%;
	border: 0px;
}

#payment td,th {
	border: #95B8E7 1px solid;
}

#payment .combo {
	border-style: none;
}

#defaultWindow .datagrid-cell {
	font-size: 12px;
}

#defaultWindow .datagrid-cell span {
	font-size: 12px;
}
</style>
<script type="text/javascript">
	payment.save = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data == true) {
				$.messager.alert('消息', '保存成功', 'info', function() {
					$("#auditPanel").window("close");
					//$("#initAuditList_datagrid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', '保存失败', 'info');
			}
		});
	};
	payment.getBalance = function() {
		var auditId = $('#auditId').val();
		$.ajax({
			url : 'payment/getBalance/' + auditId,
			type : 'GET',
			success : function(data) {
				$('#payments').html(data.payments);
				$('#balance').html(data.balance);
				$('#readyPayments').html(data.readyPayments);
			},
			error : function() {
				alert("请求错误");
			},
			dataType : "json",
			async : true
		});
	};
	payment.open = function(index) {
		esd.common.defaultOpenWindow("新建缴款记录", "${contextPath}/security/payment/edit/" + index);
	};
	payment.pay = function() {
		esd.common.syncPostSubmitEx("#form", "${contextPath }/security/payment/confirm", function(data) {
			if (data == true) {
				$.messager.alert('消息', '审批成功', 'info', function() {
					$("#auditPanel").window("close");
					$("#payment_datagrid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', '审批失败', 'info');
			}
		});
	};

	/*
	关闭单位年审窗口
	 */
	payment.back = function() {
		esd.common.defaultOpenWindowClose();
	};
	payment.insert = function() {
		esd.common.openWindow("#add", "新建缴款", 750, 350, "${contextPath}/security/payment/add/${entity.id}");
	};
	payment.confirm = function(id) {
		esd.common.openWindow("#add", "确认缴款", 750, 350, "${contextPath}/security/payment/confirm/" + id);
	};
	payment.view = function(id) {
		esd.common.openWindow("#add", "查看缴款", 750, 350, "${contextPath}/security/payment/view/" + id);
	};

	payment.loadPaymentData = function() {
		esd.common.datagridEx("#payment_datagrid", "${contextPath }/security/payment/getPayments/${entity.id}", [ {
			text : '新建缴款记录',
			iconCls : 'icon-add',
			handler : payment.insert
		} ], [ [ {
			field : 'id',
			hidden : true
		}, {
			field : 'billPrintDate',
			title : '打票日期',
			width : 75
		}, {
			field : 'paymentMoney',
			title : '缴费金额',
			align : 'right',
			width : 90,
			formatter : function(value, row, index) {
				if (value.indexOf('-') != -1) {
					var v = '<span style="color: red;">' + value + '</span>';
				} else {
					var v = '<span style="color: green;">' + value + '</span>';
				}
				return v;
			}
		}, {
			field : 'paymentType',
			title : '缴费方式',
			width : 60
		}, {
			field : 'paymentBill',
			title : '票据号',
			width : 80
		}, {
			field : 'billExchangeDate',
			title : '换票日期',
			width : 75
		}, {
			field : 'paymentExceptional',
			title : '状态',
			width : 60
		}, {
			field : 'billReturn',
			title : '返',
			align : 'center',
			width : 20,
			formatter : function(value, row, index) {
				val = '<strong style="color: orange;" >F</strong>';
				if (value == true) {
					var val = '<strong>B</strong>';
				}
				return val;
			},
			style : function(value, row, index) {

			}
		}, {
			field : 'billFinance',
			title : '财',
			align : 'center',
			width : 20,
			formatter : function(value, row, index) {
				val = '<strong style="color: orange;" >F</strong>';
				if (value == true) {
					var val = '<strong>A</strong>';
				}
				return val;
			}
		}, {
			field : 'billObsolete',
			title : '作废票据',
			width : 80
		}, {
			field : 'remark',
			title : '备注',
			width : 150
		}, {
			field : 'userRealName',
			title : '收款人',
			width : 60
		}, {
			field : 'action',
			title : '操作',
			width : 30,
			align : 'center',
			formatter : function(value, row, index) {
				if(row.billReturn==true){
					var v = '<a href="#" style="font-size: 12px;" onclick="payment.view(' + row.id + ')">查看</a>';
				}else{
					var v = '<a href="#" style="font-size: 12px;" onclick="payment.confirm(' + row.id + ')">确认</a>';
				}
				return v;
			}
		} ] ]);
	};
	$.parser.onComplete = function(){
		payment.getBalance();
		payment.loadPaymentData();
	};
</script>
<div id="payment">
	<input id="auditId" type="hidden" value="${entity.id}" />
	<!-- 年审企业表格  第一部分 -->
	<table cellspacing="0" cellpadding="0" border="0" title="企业年审信息" class="company-examined">
		<tbody>
			<tr>
				<td class="td_short">档案号码:</td>
				<td class="bj_belu readonly" style="width: 200px;" colspan="3">${entity.companyCode}</td>
				<td class="td_short">税务代码:</td>
				<td class="td_long bj_belu readonly" colspan="3">${entity.companyTaxCode}</td>
			</tr>
			<tr>
				<td class="td_short">单位名称:</td>
				<td class="td_long bj_belu readonly" colspan="3">${entity.companyName}</td>
				<td class="td_short">年审年度:</td>
				<td class="td_long bj_belu readonly" colspan="3">${entity.year}</td>
			</tr>
			<tr>
				<td class="td_short">应缴金额:</td>
				<td class="bj_belu2 readonly">${entity.amountPayable }</td>
				<td class="td_short">补缴金额:</td>
				<td class="bj_belu2 readonly">${entity.remainAmount }</td>
				<td class="td_short">减缴金额:</td>
				<td class="bj_belu2 readonly">${entity.reductionAmount }</td>
				<td class="td_short">实缴金额:</td>
				<td class="bj_belu2 readonly">${entity.actualAmount }</td>

			</tr>
			<tr>
				<td class="td_short">预缴金额:</td>
				<td class="bj_belu2 readonly" id="readyPayments"></td>
				<td class="td_short">已缴金额:</td>
				<td class="bj_belu2 readonly" id="payments"></td>
				<td class="td_short">余缴金额:</td>
				<td class="bj_belu2 readonly" id="balance"></td>
				<td class="td_short">滞纳金:</td>
				<td class="bj_belu readonly">${entity.delayPayAmount}
				</td>
			</tr>
			<tr>
				<td class="td_short" rowspan="3">备注:</td>
				<td colspan="3" rowspan="3"><textarea class="readonly" style="height: 100%" rows="2" cols="90">${entity.remark}</textarea>
				</td>
				<td class="td_short" >职工总人数:</td>
				<td class="td_short readonly">${entity.companyEmpTotal }</td>
				<td class="td_short" >年人均工资:</td>
				<td class="td_short readonly">${entity.companyEmpTotal }</td>
			</tr>
			<tr>
				<td class="td_short">应按排数:</td>
				<td class="td_short">已安排数:</td>
				<td class="td_short">已录入数:</td>
				<td class="td_short">预定人数:</td>
			</tr>
			<tr>
				<td class="td_short readonly">${entity.companyShouldTotal}</td>
				<td class="td_short readonly">${entity.companyAlreadyTotal}</td>
				<td class="td_short readonly">${entity.companyHandicapTotal }</td>
				<td class="td_short readonly">${entity.companyPredictTotal }</td>
			</tr>
		</tbody>
	</table>
</div>
<table id="payment_datagrid" style="font-size: 12px;"></table>
<div style="text-align: center;margin-top: 30px;">
	<a href="javascript:payment.save();" class="easyui-linkbutton" iconCls="icon-save">缴款</a> <a href="javascript:payment.back();" class="easyui-linkbutton" iconCls="icon-back">返回</a>
</div>


