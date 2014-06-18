<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/cs.css" />
<script type="text/javascript" src="${contextPath}/js/lib/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/lib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	
	$.parser.onComplete = function() {
		$('#addWorkerForm', window.parent.document).hide(0, function() {
			$('#addWorkerIframe', window.parent.document).height(300);
		});
	};
	var addWorkerNotice = {};
	
	addWorkerNotice.empty = function() {
		$("#addWorkerForm #workerHandicapCode").val("");//残疾证号
		$("#addWorkerForm #workerName").val("");//姓名
		$("#addWorkerForm #workerBirth").val("");//出生日期
		$("#addWorkerForm #careerCard").val("");//就业证号
		$("#addWorkerForm #phone").val("");//电话
		$("#addWorkerForm #currentJob").val("");//部门
		$("#addWorkerForm #remark").val("");//备注
		$("#addWorkerForm #verification").val("");//验证
		$("#workerGender").combobox("setValue", "1");//性别
		$("#workerHandicapType").combobox("setValue", "1");//残疾类别
		$("#workerHandicapLevel").combobox("setValue", "1");//残疾等级
	};
	
	/**
	关闭增残疾职工信息窗口
	 **/
	 addWorkerNotice.close = function() {
		$('#addWorkerForm', window.parent.document).fadeIn("slow");
		$('#addWorkerIframe', window.parent.document).height(0);
		//清空所填的数据
		addWorkerNotice.empty();
		$('#workerWindow').window("close");
		//刷新数据列表--没起作用
		$('#workerList_dataGrid').datagrid("load");
	};
</script>
<div style="width: 500px;  margin: 50px auto auto;font-size: 18;font-weight: bold;text-align: center;">
	
	<span style="font-size: 20px; line-height: 50px;">
		操作
		<c:choose>
			<c:when test="${notice == 'success' }">
				成功
			</c:when>
			<c:otherwise>
				失败,${notice }
			</c:otherwise>
		</c:choose>
	</span>
	<br/>
	<a href="javascript:addWorkerNotice.close()" class="easyui-linkbutton" iconCls="icon-ok">点我返回</a>
</div>
