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
	payment.open = function(index) {
		esd.common.defaultOpenWindow("新建缴款记录", "${contextPath}/security/payment/edit/" + index);
	};
	payment.pay = function() {
		esd.common.syncPostSubmitEx("#form", "${contextPath }/security/payment/confirm", function(data) {
			if (data == true) {
				$.messager.alert('消息', '审批成功', 'info', function() {
					$("#auditPanel").window("close");
					$("#initAuditList_datagrid").datagrid('reload');
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
		esd.common.openWindow("#add","新建缴款记录",750,350,"${contextPath}/security/payment/add/${entity.id}");
	};

	payment.loadData = function() {
		esd.common.datagridEx("#payment_datagrid", "${contextPath }/security/payment/getPayments/${entity.id}", [ {
			text : '新建缴款记录',
			iconCls : 'icon-add',
			handler : payment.insert
		} ], [ [ {
			field : 'id',
			hidden : true
		}, {
			field : 'companyCode',
			title : '打票日期',
			width : 150
		}, {
			field : 'companyTaxCode',
			title : '缴费金额',
			width : 150
		}, {
			field : 'companyId',
			title : '票据号',
			width : 150
		}, {
			field : 'companyName',
			title : '换票日期',
			width : 150
		}, {
			field : 'auditProcessStatus1',
			title : '返票'
		}, {
			field : 'auditProcessStatus2',
			title : '财政'
		}, {
			field : 'auditProcessStatus3',
			title : '作废票据',
			width : 150
		}, {
			field : 'auditProcessStatus4',
			title : '备注',
			width : 150
		}, {
			field : 'action',
			title : '操作',
			width : 100,
			align : 'center',
			formatter : function(value, row, index) {
				//var v = '<a href="#" onclick="initAuditList.openAudit(' + row.id + ')">';
				//var v = '<a href="#" >确认收款</a>';
				return '';
			}
		} ] ]);
	};
	$(function() {
		payment.loadData();
	});
</script>
<div id="payment">
	<input name="paymentCompany.id" type="hidden" value="${entity.company.id}" /> <input name="auditId" type="hidden" value="${entity.id}" /> <input name="version" type="hidden" value="1" />
	<!-- 年审企业表格  第一部分 -->
	<table cellspacing="0" cellpadding="0" border="0" title="企业年审信息" class="company-examined">
		<tbody>
			<tr>
				<td class="td_short">档案号码:</td>
				<td class="bj_belu readonly" style="width: 200px;" colspan="2">${entity.company.companyCode}</td>
				<td class="td_short">税务代码:</td>
				<td class="td_long bj_belu readonly" colspan="2">${entity.company.companyTaxCode}</td>
			</tr>
			<tr>
				<td class="td_short">单位名称:</td>
				<td class="td_long bj_belu readonly" colspan="2">${entity.company.companyName}</td>
				<td class="td_short">年审年度:</td>
				<td class="td_long bj_belu readonly" colspan="2">${entity.company.year}</td>
			</tr>
			<tr>
				<td class="td_short">应缴金额:</td>
				<td class="bj_belu2 readonly">${entity.amountPayable }</td>
				<td class="td_short">减缴金额:</td>
				<td class="bj_belu2 readonly">${entity.reductionAmount }</td>
				<td class="td_short">实缴金额:</td>
				<td class="bj_belu2 readonly">${entity.actualAmount }</td>

			</tr>
			<tr>
				<td class="td_short">已缴金额:</td>
				<td class="bj_belu2 readonly"></td>
				<td class="td_short">余缴金额:</td>
				<td class="bj_belu2 readonly"></td>
				<td class="td_short">实缴金额大写:</td>
				<td class="bj_belu"><input class="bj_prohibit readonly" type="text" />
				</td>
			</tr>
			<tr>
				<td class="td_short">备注:</td>
				<td colspan="5"><textarea class="readonly" rows="3" cols="90">${entity.remark}</textarea>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<table id="payment_datagrid" style="font-size: 12px;"></table>
<div style="text-align: center;margin-top: 30px;">
	<a href="javascript:payment.save();" class="easyui-linkbutton" iconCls="icon-save">缴款</a> <a href="javascript:payment.back();" class="easyui-linkbutton" iconCls="icon-back">返回</a>
</div>


