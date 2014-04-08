<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	//	var v = '${entity.accountsYear}/${entity.companyId}';
		//单选框选中的年份s
		var rdo_value = $('input[name="rdoAuditYear"]:checked').val();
		$.ajax({
			url : 'payment/getBalance/'+rdo_value+'/${entity.accountsYear}/${entity.companyId}',			
			type : 'GET',
			success : function(data) {
				$('#payAmount').html(data.payAmount);	//应缴
				$('#alreadyPayAmount').html(data.alreadyPayAmount);	//已缴
				$('#lessPayAmount').html(data.lessPayAmount);//未缴
			//	$('#readyPayments').html(data.readyPayments);
			},
			error : function() {
			//	alert("请求错误");
			},
			dataType : "json",
			async : true
		});
	};
/*	payment.open = function(index) {
		esd.common.defaultOpenWindow("新建缴款记录", "${contextPath}/security/payment/edit/" + index);
	};	*/
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
		var lessPayAmount = $('#lessPayAmount').html();
		var auditYear = $("input[name='rdoAuditYear']:checked").val();
		esd.common.openWindow("#add", "新建缴款", 750, 350, "${contextPath}/security/payment/add/"+auditYear+"/${entity.accountsYear}/${entity.companyId}/" + lessPayAmount);
	};
	payment.confirm = function(id) {
		esd.common.openWindow("#add", "确认缴款", 750, 350, "${contextPath}/security/payment/confirm/" + id);
	};
	payment.view = function(id) {
		esd.common.openWindow("#add", "查看缴款", 750, 350, "${contextPath}/security/payment/view/" + id);
	};
	payment.backAudit = function(id) {
		$.ajax({
			url : 'payment/backAudit/${entity.id}',
			type : 'GET',
			success : function(data) {
				if (data == true) {
					$.messager.alert('消息', '重审提交成功', 'info', function() {
						esd.common.defaultOpenWindowClose();
						$("#paymentList_datagrid").datagrid('reload');
					});
				} else {
					$.messager.alert('消息', '失败，有缴款记录不能重审', 'info');
				}
			},
			dataType : "json",
			async : false
		});
	};

	payment.loadPaymentData = function() {
		esd.common.datagridEx("#payment_datagrid", "${contextPath }/security/payment/getPayments/${entity.accountsYear}/${entity.companyId}", [ {
			text : '新建缴款记录',
			iconCls : 'icon-add',
			handler : payment.insert
		}, {
			text : '返回',
			iconCls : 'icon-back',
			handler : payment.back

		} ], [ [ {
			field : 'id',
			hidden : true
		}, {
			field : 'billPrintDate',
			title : '出票日期',
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
			title : '返票日期',
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
			}
		}, {
			field : 'billObsolete',
			title : '废',
			width : 20,
			align : 'center',
			formatter : function(value, row, index) {
				val = '<strong style="color: orange;" >F</strong>';
				if (value == true) {
					var val = '<strong style="color: red;" >T</strong>';
				}
				return val;
			}
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
				if (row.billReturn == true || row.billObsolete==true) {
					var v = '<a href="#" style="font-size: 12px;" onclick="payment.view(' + row.id + ')">查看</a>';
				} else {
					var v = '<a href="#" style="font-size: 12px;" onclick="payment.confirm(' + row.id + ')">确认</a>';
				}
				return v;
			}
		} ] ]);
	};
	$.parser.onComplete = function() {
	//	payment.getBalance();
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
				<td class="td_long bj_belu readonly" colspan="3">${entity.companyCode}</td>
				<td class="td_short">审计年度:</td>
				<td class="td_long bj_belu readonly" colspan="3">
					<c:forEach items="${entity.auditYears}" var="item">
						 ${item }<input type="radio" checked="checked" value="${item }" name="rdoAuditYear" id="rdoAuditYear${item }" onclick="payment.getBalance()" style="width:auto;margin-right:15px;"/>
					</c:forEach>
					<c:if test="${fn:length(entity.auditYears) > 1 }">
						全部 <input type="radio" checked="checked" value="all" name="rdoAuditYear" id="rdoAuditYear" onclick="payment.getBalance()" style="width:auto;margin-right:15px;"/>
					</c:if>
			<!--		<input type="radio" name="rdoAuditYears" style="width:auto;margin-left:15px;margin-right:5px;"/>
					所有审核年(
						<c:forEach items="${entity.auditYears}" var="item">
							${item},
						</c:forEach>)
			 		<input type="radio" name="rdoAuditYears" style="width:auto;margin-left:15px;margin-right:5px;" />
					单选
					<select>
						<c:forEach items="${entity.auditYears}" var="item">
							<option value="${item}" title="${item }">${item}</option>
						</c:forEach>
					</select>	 -->
				</td>
			</tr>
			<tr>
				<td class="td_short">单位名称:</td>
				<td class="bj_belu readonly" style="width: 200px;" colspan="3">${entity.companyName}</td>
				<td class="td_short">税务代码:</td>
				<td class="td_long bj_belu readonly" colspan="3">${entity.companyTaxCode}</td>
			</tr>
			<tr>
				<td class="bj_belu2" style="text-align:center;">缴款年度:</td>
				<td class="td_short readonly">${entity.accountsYear}</td>
				<td class="bj_belu2" style="text-align:center;">应缴金额:</td>
				<td class="td_short readonly" id="payAmount">${entity.payAmount}</td>
				<td class="td_short">已缴金额(总):</td>
				<td class="bj_belu2 readonly" id="alreadyPayAmount">${entity.alreadyPayAmount}</td>
				<td class="td_short">待缴金额:</td>
				<td class="bj_belu readonly" id="lessPayAmount">${entity.lessPayAmount}</td>


			</tr>
		<!-- 	<tr>
				<td class="td_short">预缴金额:</td>
				<td class="bj_belu2 readonly" id="readyPayments"></td>
				<td class="td_short">已缴金额:</td>
				<td class="bj_belu2 readonly" id="payments"></td>
				<td class="td_short">余缴金额:</td>
				<td class="bj_belu2 readonly" id="balance"></td>
				<td class="td_short">实缴总金额:</td>
				<td class="bj_belu2 readonly">${entity.payAmount }</td>

			</tr>	 -->
			<tr>
				<td class="td_short" rowspan="3">备注:</td>
				<td colspan="3" rowspan="3"><textarea class="readonly" style="height: 100%" rows="2" cols="90">${entity.remark}</textarea>
				</td>
				<td class="td_short">职工总人数:</td>
				<td class="td_short readonly" style="text-align: left;">${entity.companyEmpTotal }</td>
				<td class="td_short">年人均工资:</td>
				<td class="td_short readonly" style="text-align: left;">${entity.averageSalary }</td>
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
<table id="payment_datagrid" style="font-size: 12px; height: 100px;"></table>



