<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var user_detail = {};
	user_detail.submit = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data == true) {
				$.messager.alert('消息', '更新成功', 'info', function() {
					esd.common.defaultOpenWindowClose();
					$("#user_list_grid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', '更新失败', 'info');
			}
		});
	};
</script>
<div>
	<div style="padding:15px 15px 15px 15px;">
		<form id="form" action="${contextPath }/security/settings/user/update" method="post">
			<table class="parameter_tab">
				<tr>
					<td style="width: 100px;">用户名:</td>
					<td style="width: 200px;">
					<input class="easyui-validatebox" required="true" name="userName" type="text" value="${entity.userName}" /> 
					<input name="id" type="hidden" value="${entity.id}" />
					<input name="version" type="hidden" value="${entity.version}" />
					</td>
					<td><span id="username_message"></span>
					</td>
				</tr>
<!--  
				<tr>
					<td>登陆密码:</td>
					<td><input class="easyui-validatebox" name="userPassword" type="text" value=""/></td>
					<td><span id="password_message"></span>
					</td>
				</tr>
				<tr>
					<td>确认登陆密码:</td>
					<td><input name="confirm" type="text" /></td>
					<td><span id="confirm_message"></span>
					</td>
				</tr>
				-->
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>真实姓名:</td>
					<td><input name="userRealName" type="text" value="${entity.userRealName}" /></td>
					<td><span id="realname_message"></span>
					</td>
				</tr>
				<tr>
					<td>手机号码:</td>
					<td><input name="userMobile" required="true" type="text" value="${entity.userMobile}" /></td>
					<td><span id="mobile_message"></span>
					</td>
				</tr>
				<tr style="line-height: 20px;">
					<td>状态:</td>
					<td><select class="easyui-combobox" name="userStatus" data-options="panelHeight:70,height:30,editable:false">
							<option value="true" <c:if test="${entity.userStatus eq 'true'}">selected="selected"</c:if>>正常</option>
							<option value="false" <c:if test="${entity.userStatus eq 'false'}">selected="selected"</c:if>>停用</option>
					</select>
					</td>
				</tr>
				<tr style="line-height: 20px;">
					<td>用户组:</td>
					<td><input name="userGroup.id" class="easyui-combobox"
						data-options="panelHeight:120,height:30,
						<c:if test="${entity.userGroup.id!=null}">value:${entity.userGroup.id},</c:if>
						<c:if test="${entity.userGroup.id==null}">value:1,</c:if>
						editable:false,valueField:'id',textField:'userGroupName',url:'${contextPath }/security/settings/user/group'" />
					</td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="3"><textarea name="userRemark" rows="5" cols="70">${entity.userRemark}</textarea></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3"><a href="javascript:user_detail.submit();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
