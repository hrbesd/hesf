<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	payment.add = {};
	payment.add.save = function() {
		esd.common.syncPostSubmit("#add_form", function(data) {
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
	payment.add.back = function() {
		$('#add').window("close");
	};
	$(function() {

	});
</script>
<div id="payment_add" style="padding: 10px 10px 10px 10px;">
	<form id="add_form" action="${contextPath}/security/payment/add/${entity}" method="post">
		<input type="hidden" value="${entity.auditId}" name="auditId" />
		<table border="0">
			<tbody>
				<tr>
					<td>缴款人:</td>
					<td><input type="text" value="${entity.paymentPerson }" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>出票时间:</td>
					<td><fmt:formatDate value="${entity.billPrintDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="auditDelayDate" /> <input class="easyui-datebox easyui-validatebox"
						name="auditDelayDate" data-options="height:30,showSeconds:false" value="${auditDelayDate}" style="width:150px" />
					</td>
					<td>票据号:</td>
					<td><input type="text" data-options="required:true" value="${entity.paymentBill }" />
					</td>
					<td>缴费金额:</td>
					<td><input type="text" class="easyui-numberbox" data-options="min:0,precision:2" value="${entity.paymentMoney }" />
					</td>
				</tr>
			</tbody>
		</table>
		<hr />
		<table border="0">
			<tbody>
				<tr>
					<td>换票时间:</td>
					<td><fmt:formatDate value="${entity.billExchangeDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="auditDelayDate" /> <input class="easyui-datebox easyui-validatebox"
						name="auditDelayDate" data-options="height:30,showSeconds:false" value="${auditDelayDate}" style="width:150px" />
					</td>
					<td>是否返票:</td>
					<td><select style="font-size: 12px;" class="easyui-combobox" name="billReturn" data-options="width:100,panelHeight:80,height:30,editable:false">
							<option value="true" <c:if test="${entity.billReturn eq 'true'}">selected="selected"</c:if>>是</option>
							<option value="false" <c:if test="${entity.billReturn eq 'false'}">selected="selected"</c:if>>否</option>
					</select>
					</td>
					<td>财政已到:</td>
					<td><select style="font-size: 12px;" class="easyui-combobox" name="isExempt" data-options="width:100,panelHeight:80,height:30,editable:false">
							<option value="true" <c:if test="${entity.billFinance eq 'true'}">selected="selected"</c:if>>是</option>
							<option value="false" <c:if test="${entity.billFinance eq 'false'}">selected="selected"</c:if>>否</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>状态:</td>
					<td><input name="area.code" class="easyui-combobox" data-options="value:1,height:30,editable:false,valueField:'id',textField:'paymentExceptional',url:'parameter/getExStatic'" />
					</td>
					<td>作废票据号:</td>
					<td><input type="text" value="${entity.billObsolete }" />
					</td>

				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="5"><textarea rows="2" cols="20" style="width: 100%">${entity.remark}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>


<div style="text-align: center;margin-top: 30px;">
	<a href="javascript:payment.add.save();" class="easyui-linkbutton" iconCls="icon-save">保存</a> <a href="javascript:payment.add.back();" class="easyui-linkbutton" iconCls="icon-back">取消</a>
</div>


