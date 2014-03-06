<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	workerList.close = function() {
		$("#workerWindow").window("destroy");
	};
	/**
	保存编辑残疾职工信息
	**/
	workerList.edit = function() {
		esd.common.syncPostSubmit("#editWorkerForm", function(data) {
			if (data == true) {
			
				$.messager.alert('消息', '残疾职工信息编辑成功。', 'ok');
				workerList.close();
				$('#workerList_dataGrid').datagrid("load");

			} else {
				$.messager.alert('消息', '残疾职工信息编辑失败。', 'error');
				
			}
		});
	};
</script>

<form id="editWorkerForm" action="${contextPath}/security/worker/edit" method="post">
	<input type="hidden" value="${worker.id}" name="id" /> <input type="hidden" value="${worker.version}" name="version" />

	<!-- 数据表格 -->
	<table id="company_information" align="center" style="margin-top: 40px;">
		<tr>
			<td align="center"><span style="font-size: 18px;font-weight: bold;">编辑残疾人信息</span>
			</td>
		</tr>
		<tr>
			<td>残疾证号:</td>
			<td>${worker.workerHandicapCode}</td>
		</tr>
		<tr>
			<td class="">姓名:</td>
			<td><input class="easyui-validatebox" value="${worker.workerName}" type="text" name="workerName" id="workerName" data-options="required:true" />
			</td>
			<td class="">性别:</td>
			<td><select name="workerGender" id="workerGender" class="easyui-combobox" data-options="height:30,disabled:'true'">

					<option value="0" <c:if test="${worker.workerGender eq '0'}">selected="selected"</c:if>>男</option>
					<option value="1" <c:if test="${worker.workerGender eq '1'}">selected="selected"</c:if>>女</option>
			</select>
			</td>
			<td class="">出生日期:</td>
			<td><input readonly="value"  class="easyui-validatebox" value="${worker.workerBirth}" type="text" class="easyui-datebox"  id="workerBirth"></input></td>

		</tr>
		<tr>
			<td class="">就业证号:</td>
			<td><input class="easyui-validatebox" value="${worker.careerCard}" type="text" name="careerCard" id="careerCard" />
			</td>
			<td class="">残疾类别:</td>
			<td><input class="easyui-combobox" type="text" name="workerHandicapType.id" id="workerHandicapType" value="${worker.workerHandicapType.id}"
				data-options="height:30,disabled:'true',required:true,editable:false,valueField:'id',textField:'handicapType',url:'parameter/workerHandicapTypeService'" />
			</td>
			<td class="">残疾等级:</td>
			<td><input class="easyui-combobox" type="text" name="workerHandicapLevel.id" id="workerHandicapLevel" value="${worker.workerHandicapLevel.id}"
				data-options="height:30,disabled:'true',required:true,editable:false,valueField:'id',textField:'handicapLevel',url:'parameter/workerHandicapLevelService'" />
			</td>
		</tr>
		<tr>
			<td class="">联系电话:</td>
			<td><input class="easyui-validatebox" value="${worker.phone}" type="text" name="phone" id="phone" />
			</td>
			<td class="">现任岗位:</td>
			<td><input class="easyui-validatebox" value="${worker.currentJob}" type="text" name="currentJob" id="currentJob" />
			</td>
		</tr>
		<tr>
			<td class="">备注:</td>
			<td colspan="5"><textarea rows="4" cols="100" name="remark">${worker.remark}</textarea>
			</td>
		</tr>

		<tr>
			<td colspan="6"><a href="javascript:workerList.edit();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			 <a href="javascript:workerList.close();" class="easyui-linkbutton"
				iconCls="icon-undo">取消</a>
			</td>
		</tr>
	</table>
</form>

