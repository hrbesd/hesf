<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	payment.add = {};
	payment.add.save = function() {
		//验证缴款金额必须为大于零的数字
		var paymentMoney = $('#paymentMoney').val();
		//余缴金额
		var prePaymentMoney = $('#prePaymentMoney').val();
		if(paymentMoney <= 0){
			$.messager.alert('消息','缴款金额必须大于零!');
			return;
		}
		if(prePaymentMoney < paymentMoney){
			$.messager.alert('消息','缴款金额不能大于代缴金额!');
			return;
		}
		esd.common.syncPostSubmit("#add_form", function(data) {
			if (data == true) {
				$.messager.alert('消息', '保存成功', 'info', function() {
					payment.add.back();
					$("#payment_datagrid").datagrid('reload');
					payment.getBalance();
				});
			} else {
				$.messager.alert('消息', '保存失败', 'info');
			}
		});
	};
	payment.add.back = function() {
		$('#add').window("close");
	};
	$.parser.onComplete = function(){

	};
</script>
<div id="payment_add" style="padding: 10px 10px 10px 10px;">
	<form id="add_form" action="${contextPath}/security/payment/add" method="post">
		<input type="hidden" value="${entity.version }" name="version" />
		<input type="hidden" value="${entity.paymentCompany.id }" name="paymentCompany.id" />
		<input type="hidden" value="${entity.year }" name="year" />
		<table border="0">
			<tbody>
				<tr>
					<td>收款人:</td>
					<td><input type="text" readonly="readonly" class="readonly" value="${entity.paymentPerson.userRealName}" data-options="required:true" />
					</td>
					<td>缴款方式:</td>
					<td><input name="paymentType.id" class="easyui-combobox" data-options="value:1,height:30,editable:false,valueField:'id',textField:'text',url:'parameter/getPaymentType'" />
					</td>
					<td>状态:</td>
					<td><input name="paymentExceptional.id" class="easyui-combobox" data-options="value:1,height:30,editable:false,valueField:'id',textField:'paymentExceptional',url:'parameter/getExStatic'" /></td>
				</tr>
				<tr>
					<td>出票时间:</td>
					<td><c:if test="${entity.billPrintDate==null}">
							<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="billPrintDate" />
						</c:if> <c:if test="${entity.billPrintDate!=null}">
							<fmt:formatDate value="${entity.billPrintDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="billPrintDate" />
						</c:if> <input class="easyui-datebox" name="billPrintDate" value="${billPrintDate}" data-options="required:true,height:30,showSeconds:false" style="width:150px" />
					</td>
					<td>票据号:</td>
					<td><input name="paymentBill" type="text" class="easyui-validatebox" data-options="required:true" value="${entity.paymentBill }" />
					</td>
					<td>缴费金额:</td>
					<td><input name="paymentMoney" id="paymentMoney" type="text" maxlength="16" class="easyui-numberbox" data-options="required:true,precision:2" value="${entity.paymentMoney }" />
						<input type="hidden" id="prePaymentMoney" value="${entity.paymentMoney }"  />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>


<div style="text-align: center;margin-top: 30px;">
	<a href="javascript:payment.add.save();" class="easyui-linkbutton" iconCls="icon-save">保存</a> <a href="javascript:payment.add.back();" class="easyui-linkbutton" iconCls="icon-back">取消</a>
</div>


