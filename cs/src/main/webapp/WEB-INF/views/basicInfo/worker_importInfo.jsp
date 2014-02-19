<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br /><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/cs.css" />
<script type="text/javascript" src="${contextPath}/js/lib/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/lib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/locale/easyui-lang-zh_CN.js"></script>



<div style="width: 500px;margin: auto;font-size: 18;font-weight: bold;text-align: center;">
	<c:if test="${fileLoadUpResult==''}">
						${fileLoadUpResult}
		</c:if>
	<c:if test="${fileLoadUpResult!=''}">
		导入数据总量为：${totalLength}条，导入失败：${errorLength}条，导入成功${succesLength}条。
	</c:if>
	<div>

		<c:if test="${errorLength!=0}">
					
				<a href="${errorFilePath}">点击此处，下载错误信息列表</a>
		</c:if>
	</div>

</div>


