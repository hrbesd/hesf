<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style>

</style>
<script type="text/javascript">
	$(function() {
		
		
	});
	
	
	var importWorkerFile = {};

	//预定义定时器的返回值
	importWorkerFile.intervalValue;
	//进度条数值刷新函数
	importWorkerFile.freshProgressBar = function(){
		//异步取得进度数值
		$.ajax({
			url:'${contextPath}/security/worker/getDealedProgress',
			type:'GET',
			success:function(data){
				var value = $('#workerImportProgress').progressbar('getValue');
			//	if (value < 100){ 
				//	value += 1;
				$('#workerImportProgress').progressbar('setValue', data);
			//	}else{
				if(value == 100){
					clearInterval(importWorkerFile.intervalValue);
					$('#workerImportProgress').progressbar('setValue',0);
				}
			//	}
			}
			
		});
		
	};
	
	//停止刷新器方法
	importWorkerFile.stopFreshProgressBar = function(){
		clearInterval(importWorkerFile.intervalValue);
	};
	
	/**关闭
	**/
	importWorkerFile.close = function() {
	
	$('#importWorkerWindow').window("close");
	};
	importWorkerFile.submit = function() {
		var str = $("#uploadWorkerFile").val();
		var pos = str.lastIndexOf(".");
		var lastname = str.substring(pos, str.length);

		if (lastname.toLowerCase() == ".xls" || lastname.toLowerCase() == ".xlsx") {
			//定时刷新器, 每0.5秒刷新一次
			importWorkerFile.intervalValue = setInterval('importWorkerFile.freshProgressBar()',1000);
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

<div class="importWorker" id="importWorkerPan">

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
				<span class="red_notice">*</span>导入数据条数(残疾员工数)不超过3000条。
			</p>
			<p>
				<span class="red_notice">*</span> 文件必须是：xls, xlsx类型, 格式如下图.
			</p>
	 		<p>
				<img src="${contextPath }/images/example_excelformat.jpg" />
			</p>
		</div>
	</form>
		<!-- 导入进入条 -->
		<div id="workerImportProgress" class="easyui-progressbar" data-options="value:0" style="width:450px;margin:15px auto;"></div>
		<div style="margin: 65px auto 20px;text-align: center;">
			 <a href="javascript:importWorkerFile.close()" class="easyui-linkbutton" iconCls="icon-undo">返回</a>
			 <a href="javascript:importWorkerFile.stopFreshProgressBar()" class="easyui-linkbutton" iconCls="icon-undo">停止</a>
		</div>
</div>
<iframe name="importWorkerIframe" id="importWorkerIframe" class="importWorkerIframe" frameborder="0"> </iframe>

