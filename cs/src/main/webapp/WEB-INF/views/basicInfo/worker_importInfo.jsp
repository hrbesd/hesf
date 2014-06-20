<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br /><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/js/lib/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/js/lib/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/cs.css" />
<script type="text/javascript"
	src="${contextPath}/js/lib/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/lib/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	$.parser.onComplete = function() {
		$('#importWorkerPan', window.parent.document).hide(0, function() {
			$('#importWorkerIframe', window.parent.document).height(500);
		});
	};
	$(function() {
	});

	var importWorkerManager = {};
	importWorkerManager.close = function() {
		$('#importWorkerPan', window.parent.document).fadeIn("slow");
		$('#importWorkerIframe', window.parent.document).height(0);
		$("#importInfoPan").empty();
	};
	importWorkerManager.import = function() {
	$('#divButtons').hide();
	$('#divProcessing').show();
		if ($("#currentYear").val() == undefined) {
			alert("年份获取失败！");
		}
		$.ajax({
			url : '${contextPath}/security/worker/determineimport',
			data : {
				"companyId" : $("#companyId").val(),
				"year" : $("#currentYear").val()
			},
			type : 'post',
			success : function(data) {
				if (data == true) {
					$('#divProcessing').hide();
					$.messager.alert('消息', '导入成功。', 'ok', function() {
						importWorkerManager.close();
					});

				} else {
					$.messager.alert('消息', '导入残疾职工失败。', 'error', function() {
						importWorkerManager.close();
					});
				}
			},
			error : function() {
				$.messager.alert('消息', '导入残疾职工信息时发生错误。', 'error');
			}
		});
	};
</script>

<div id="importInfoPan" style="width: 500px;  margin: 50px auto auto;font-size: 18;font-weight: bold;text-align: center;">
	<!-- 错误提示信息 -->
	<c:if test="${errorInfo!='null'}">
		<div style="  margin: 154px auto auto;">
			<span style="font-size: 19px;" class="red_notice">${errorInfo}</span>
			<div>
				<a style="margin-top: 20px;"
					href="javascript:importWorkerManager.close()"
					class="easyui-linkbutton" iconCls="icon-undo">返回</a>
			</div>
		</div>
	</c:if>

	<c:if test="${errorInfo=='null'}">
		<div class="importWorkerTitle">导入残疾职工确认页面</div>
		<div class="importTip">
			职工信息数据总量为：${totalLength}条，<label class="red_notice">职工信息错误有：${errorLength}条</label>，职工信息正确有:${succesLength}条。
			<input type="hidden" value="${companyId}" id="companyId" /> <input
				type="hidden" value="${year}" id="currentYear" />
			<div>
				<c:if test="${errorLength!=0}">
					<a href="${errorFilePath}">点击此处，下载错误信息列表</a>
				</c:if>
			</div>
		</div>
		<div style="text-align: center;margin-top: 10px;" id="divButtons">
				 <a href="javascript:importWorkerManager.import()" class="easyui-linkbutton" iconCls="icon-ok">导入</a>
				 <a href="javascript:importWorkerManager.close()" class="easyui-linkbutton" iconCls="icon-undo">取消</a>
		</div>
		<div id="divProcessing" style="text-align:center;margin-top:10px;display:none;">
			<img src="${contextPath }/images/processing.gif" style="height:50px;"/><br/>
			<span style="color:rgb(117, 71, 71)">导入中ing...</span>
		</div>
	</c:if>
</div>


