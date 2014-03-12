<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style>
.importWorker {
	margin: 0px auto;
}

/*导入残疾职工*/
.importWorkerForm {
	border: 1px solid #95B8E7;
	margin: 50px auto auto;
	padding: 15px;
	width: 450px;
}

.importWorkerIframe {
	width: 100%;

	margin-top: 0px;
}

.importWorkerTitle {
	text-align: center;
	font-size: 25px;

}
</style>
<script type="text/javascript">
	$(function() {
	});
	var importWorkerFile = {};
	importWorkerFile.submit = function() {

		var str = $("#uploadWorkerFile").val();
		var pos = str.lastIndexOf(".");
		var lastname = str.substring(pos, str.length);

		if (lastname.toLowerCase() == ".xls" || lastname.toLowerCase() == ".xlsx") {
			return true;

		}
		if ($("#uploadWorkerFile").val() == '') {

			$.messager.alert('消息', '请选择文件。', 'info');
			return false;
		}
		$.messager.alert('消息', '文件格式不支持。', 'info');

		return false;
	};
	function f() {
		
	}
	
	
	$("#importWorkerTitle").toggle(function() {

		$("#importWorkerForm").hide();
	}, function() {

		$("#importWorkerForm").show();
	});
</script>

<div class="importWorker">

	<form class="importWorkerForm" id="importWorkerForm" action="worker/importworker" method="post" enctype="multipart/form-data" target="importWorkerIframe" onsubmit="return importWorkerFile.submit()">
	<div class="importWorkerTitle"  id="importWorkerTitle"  >导入残疾职工</div>
		<table>
			<tr>
				<td><input type="file" id="uploadWorkerFile" value="选择文件" name="file" style="width: 400px" accept="application/vnd.ms-excel" />
				</td>
				<td><input type="submit" value="上传" />
				</td>
			</tr>
		</table>
		<input type="hidden" name="companyId" id="currentCompanyId" value="${companyId}" />
		<input type="hidden" name="year" id="currentyear" value="${year}" />
		<div>
			<p>
				<span class="red_notice">*</span>文件大小不能超过2MB。
			</p>
			<p>
				<span class="red_notice">*</span> 文件格式必须是：.xls ,.xlsx格式。
			</p>

		</div>
	</form>
</div>
<iframe name="importWorkerIframe" id="importWorkerIframe" class="importWorkerIframe" frameborder="0"> </iframe>

