<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	payment.add={};
	payment.add.save = function() {
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
	payment.add.back = function() {
		$('#add').window("close");
	};
	$(function() {

	});
</script>
<div id="payment_add" style="padding: 10px 10px 10px 10px;">
	<input type="hidden" value="${id}" name="auditId"/>
	<table border="0" >
		<tbody>
			<tr>
				<td>出票时间:</td>
				<td><fmt:formatDate value="${entity.auditDelayDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="auditDelayDate" />
					<input class="easyui-datebox easyui-validatebox" name="auditDelayDate" data-options="required:true,height:30,showSeconds:false" value="${auditDelayDate}" style="width:150px" /></td>
				<td>票据号:</td>
				<td><input type="text" class="easyui-validatebox" data-options="required:true" /></td>
				<td>缴费金额:</td>
				<td><input type="text" class="easyui-numberbox" data-options="required:true,min:0,precision:2"/></td>
			</tr>
			<tr>
				<td>换票时间:</td>
				<td><fmt:formatDate value="${entity.auditDelayDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="auditDelayDate" />
					<input class="easyui-datebox easyui-validatebox" name="auditDelayDate" data-options="height:30,showSeconds:false" value="${auditDelayDate}" style="width:150px" /></td>
				<td>是否返票:</td>
				<td><select  id="mianJiao" style="font-size: 12px;"  class="easyui-combobox" name="isExempt" data-options="width:100,panelHeight:80,height:30,editable:false">
						<option value="true" <c:if test="${entity.isExempt eq 'true'}">selected="selected"</c:if>>是</option>
						<option value="false" <c:if test="${entity.isExempt eq 'false'}">selected="selected"</c:if>>否</option>
				</select></td>
				<td>财政已到:</td>
				<td><select id="mianJiao" style="font-size: 12px;"  class="easyui-combobox" name="isExempt" data-options="width:100,panelHeight:80,height:30,editable:false">
						<option value="true" <c:if test="${entity.isExempt eq 'true'}">selected="selected"</c:if>>是</option>
						<option value="false" <c:if test="${entity.isExempt eq 'false'}">selected="selected"</c:if>>否</option>
				</select></td>
			</tr>
			<tr>
				<td>状态:</td>
				<td><input name="area.code" class="easyui-combobox" data-options="height:30,editable:false,valueField:'code',textField:'name',url:'parameter/getArea'" /></td>
			</tr>
		</tbody>
	</table>
</div>
<div style="text-align: center;margin-top: 30px;">
	<a href="javascript:payment.add.save();" class="easyui-linkbutton" iconCls="icon-save">保存</a> <a href="javascript:payment.add.back();" class="easyui-linkbutton" iconCls="icon-back">取消</a>
</div>


