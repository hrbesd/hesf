<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style>
.importWorkerForm {
	margin: 30px auto auto;
	width: 450px;
}

.importWorkerIframe {
	width: 100%;
	height: 340px;
	margin-top: 30px;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#currentCompanyId").val($("#companyId").val());
	});
	var importWorkerFile = {};
	importWorkerFile.submit = function() {
	
	
		 var str=$("#uploadWorkerFile").val();
		 var pos = str.lastIndexOf(".");
		 var lastname = str.substring(pos,str.length);
		 
 		if(lastname.toLowerCase()==".xls" || lastname.toLowerCase()==".docx"  || lastname.toLowerCase()==".xlsm" || lastname.toLowerCase()==".xlsx" ){
			return true;
			
		}
		alert("文件格式不支持");
		return false;
	};
	
	
	
	
	
</script>
<form id="importWorkerForm" class="importWorkerForm" action="worker/importworker" method="post" enctype="multipart/form-data" target="importWorkerIframe" onsubmit="return importWorkerFile.submit()">
	<input type="file" id="uploadWorkerFile" name="file" style="width: 400px" /> 
	<input type="hidden" name="companyId" id="currentCompanyId" />
	 <input type="submit" value="上传" />
	 <div>
	 	<p><span  class="red_notice">*</span>文件大小不能超过2MB。</p>
		<p><span  class="red_notice">*</span>
		文件格式必须是：.xsl，.xlsm，.xlsx 格式。</p>
	 </div>
</form>
<iframe name="importWorkerIframe" class="importWorkerIframe" frameborder="0"> </iframe>

