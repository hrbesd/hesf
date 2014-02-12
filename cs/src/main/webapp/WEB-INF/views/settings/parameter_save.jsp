<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var user_detail = {};
	user_detail.submit = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data == true) {
				$.messager.alert('消息', '添加成功', 'info', function() {
					esd.common.defaultOpenWindowClose();
					$("#parameter_list_grid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', '添加失败', 'info');
			}
		});
	};
	$.extend($.fn.validatebox.defaults.rules, {
		equals : {
			validator : function(value, param) {
				return value == $(param[0]).val();
			},
			message : 'Field do not match.'
		}
	});
</script>
<div>
	<div style="margin-top: 20px; margin-left: 20px;">
		<form id="form" action="${contextPath}/security/settings/yearAuditParameter/add" method="post">
			<table class="parameterTab" border="0">
				<tr>
					<td width="100">年度:</td>
					<td><input class="easyui-validatebox" required="true" name="year" type="text" value="${year}" />
					 <input class="easyui-validatebox" name="version" type="hidden" value="1" /></td>
				</tr>
				<tr>
					<td>所属地区:</td>
					<td><input name="area.code" class="easyui-combobox" value="10230000" data-options="height:30,required:true,editable:false,valueField:'code',textField:'name',url:'parameter/getArea'" />
					</td>
					<td>滞纳金开始时间:</td>
					<td><input class="easyui-datebox easyui-validatebox" name="auditDelayDate" data-options="required:true,showSeconds:false" value="2014-09-15" style="width:150px" /></td>
				</tr>
				<tr>
					<td>安置比例(%):</td>
					<td><input class="easyui-validatebox" name="putScale" type="text" required="true" value="0.15" />
					</td>
					<td>滞纳金征收比率(%):</td>
					<td><input class="easyui-validatebox" name="auditDelayRate" type="text" required="true" value="0.005" /></td>
					<td>职工退休年龄(男)：</td>
					<td><input class="easyui-validatebox" name="retireAgeMale" type="text" required="true" value="60" /></td>
				</tr>
				<tr>
					<td>计算基数:</td>
					<td><input class="easyui-validatebox" name="averageSalary" type="text" required="true" />
					</td>
					<td>支付限制天数:</td>
					<td><input class="easyui-validatebox" name="payLimitDays" type="text" required="true" value="15" /></td>
					<td>职工退休年龄(女)：</td>
					<td><input class="easyui-validatebox" name="retireAgeFemale" type="text" required="true" value="50" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>

			</table>
			<div><strong>残疾人等级计算：</strong></div>
			<table style="margin-top: 10px; margin-left: 20px; float: left;" border="0">
				<tr>
					<td width="120">视力1,2,3,4级:</td>
					<td><select class="easyui-combobox" name="eyeOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.eyeOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.eyeOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="eyeTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.eyeTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.eyeTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="eyeThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.eyeThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.eyeThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="eyeFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.eyeFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.eyeFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>听力1,2,3,4级:</td>
					<td><select class="easyui-combobox" name="hearingOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.hearingOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.hearingOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="hearingTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.hearingTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.hearingTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="hearingThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.hearingThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.hearingThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="hearingFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.hearingFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.hearingFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>言语1,2,3,4级:</td>
					<td><select class="easyui-combobox" name="speakOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.speakOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.speakOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="speakTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.speakTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.speakTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="speakThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.speakThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.speakThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="speakFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.speakFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.speakFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>肢体1,2,3,4级:</td>
					<td><select class="easyui-combobox" name="bodyOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.bodyOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.bodyOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="bodyTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.bodyTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.bodyTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="bodyThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.bodyThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.bodyThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="bodyFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.bodyFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.bodyFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>智力1,2,3,4级:</td>
					<td><select class="easyui-combobox" name="intelligenceOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.intelligenceOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.intelligenceOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="intelligenceTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.intelligenceTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.intelligenceTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="intelligenceThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.intelligenceThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.intelligenceThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="intelligenceFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.intelligenceFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.intelligenceFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>精神1,2,3,4级:</td>
					<td><select class="easyui-combobox" name="mentalOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.mentalOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.mentalOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="mentalTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.mentalTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.mentalTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="mentalThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.mentalThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.mentalThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="mentalFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.mentalFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.mentalFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>多重1,2,3,4级:</td>
					<td><select class="easyui-combobox" name="multiOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.multiOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.multiOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="multiTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.multiTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.multiTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="multiThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.multiThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.multiThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="multiFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.multiFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.multiFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3"><a href="javascript:user_detail.submit();" class="easyui-linkbutton" iconCls="icon-ok">保存</a> <a href="javascript:user_detail.submit();" class="easyui-linkbutton"
						iconCls="icon-ok">返回</a></td>
				</tr>
			</table>
		</form>
	</div>
</div>
