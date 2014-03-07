<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	payment.add = {};
	payment.add.save = function() {
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
		<input type="hidden" name="audit.id" value="${entity.audit.id}" /> <input type="hidden" value="1" name="version" />
		<table border="0">
			<tbody>
				<tr>
					<td>收款人:</td>
					<td><input type="text" readonly="readonly" class="readonly" value="${entity.paymentPerson.userRealName}" data-options="required:true" />
					</td>
					<td>缴款方式:</td>
					<td><input name="paymentType.id" class="easyui-combobox" data-options="value:1,height:30,editable:false,valueField:'id',textField:'text',url:'parameter/getPaymentType'" />
					</td>
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
					<td><input name="paymentMoney" type="text" maxlength="16" class="easyui-numberbox" data-options="required:true,precision:2" value="${entity.paymentMoney }" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>


<div style="text-align: center;margin-top: 30px;">
	<a href="javascript:payment.add.save();" class="easyui-linkbutton" iconCls="icon-save">保存</a> <a href="javascript:payment.add.back();" class="easyui-linkbutton" iconCls="icon-back">取消</a>
</div>


