<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br /><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div style="width: 500px;margin: auto;font-size: 18;font-weight: bold;text-align: center;">
	<c:if test="${fileLoadUpResult==''}">
						${fileLoadUpResult}
		</c:if>
	<c:if test="${fileLoadUpResult!=''}">
		导入数据总量为：${totalLength}条，导入失败：${errorLength}条，导入成功${succesLength}条。
	</c:if>

</div>

