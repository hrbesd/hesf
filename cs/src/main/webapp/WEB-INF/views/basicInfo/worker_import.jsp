<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style>

.importWorkerForm{
margin: 30px auto auto;
    width: 400px;
}
.importWorkerIframe{
width:100%;
    height: 400px;
    margin-top: 30px;
}



</style>
<script type="text/javascript">
	$(function(){
		$("#currentCompanyId").val($("#companyId").val());
	});
</script>
<form id="importWorkerForm" class="importWorkerForm" action="worker/importworker" method="post" enctype="multipart/form-data" target="importWorkerIframe">
	<input type="file" name="file" />
		<input type="hidden" name="companyId"  id="currentCompanyId"/>
	
	 <input type="submit" value="上传" />
</form>
<iframe name="importWorkerIframe" class="importWorkerIframe" frameborder="0">


</iframe>
