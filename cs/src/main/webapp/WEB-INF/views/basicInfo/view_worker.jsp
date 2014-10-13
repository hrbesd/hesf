<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	/**
	 * 残疾职工页面
	 **/
	var addWorker = {};
	
	/**
	 * 关闭增残疾职工信息窗口
	 **/
	addWorker.close = function() {
		$('#workerList_dataGrid').datagrid('reload');
		$("#workerWindow").window("destroy");
	};
	
	
	//组件解析完成
	$.parser.onComplete = function() {
	};
</script>

	<!-- 显示信息-->
	<div>
	 	<form id="viewWorkerForm" action="#" method="post" class="viewWorkerForm">
			<!-- 数据表格 -->
			<table id="company_information" align="center">
				<tr>
					<td width="80" >残疾证号:</td>
					<td colspan="3" width="350">
						<input readonly="readonly" type="text" id="workerHandicapCode" value="${worker.workerHandicapCode }" name="workerHandicapCode" style="width: 180px" /> 
					</td>
					<td rowspan="7" width="170" style="border: 1px solid;text-align: center;color: silver;font-size: 20px;font-weight: bold;vertical-align: middle;">
						<c:if test="${worker.picTitle == null || worker.picTitle == '' }">暂<br/>无<br/>图<br/>片</c:if>
						<c:if test="${worker.picTitle != null && worker.picTitle != '' }"><img style="height:220px;max-width:160px;" title="精神残疾证明文件扫面件" alt="精神残疾证明文件扫面件" src="${contextPath }/security/worker/pic/${worker.id}"/></c:if>
					</td>
				</tr>
				<tr>
					<td>姓名:</td>
					<td >
						<input readonly="readonly" type="text" value="${worker.workerName }" name="workerName" id="workerName" />
					</td>
					<td >
						职务:
					</td>
					<td >
						<c:choose>
							<c:when test="${worker.isCadre == false }">职工</c:when>
							<c:when test="${worker.isCadre == true }">干部</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>	
					<td >性别:</td>
					<td>
						<input readonly="readonly" type="text" value="<c:if test='${worker.workerGender == 1 }'>男</c:if><c:if test='${worker.workerGender == 0 }'>女</c:if>" name="workerName" id="workerName" />
					</td>
					<td >出生日期:</td>
					<td>
						<input readonly ="readonly" value="${worker.workerBirth }" type="text" data-options="disabled:true" id="workerBirth" name="workerBirth" />
					</td>
				</tr>
				<tr>
					<td >残疾类别:</td>
					<td>
						<input readonly="readonly" type="text" value="${worker.workerHandicapType.handicapType }" />
					</td>
					<td >残疾等级:</td>
					<td>
						<input readonly="readonly" type="text" value="${worker.workerHandicapLevel.handicapLevel }" />
					</td>
				</tr>
				<tr>
					<td>就业证号:</td>
					<td>
						<input readonly="readonly" type="text" value="${worker.careerCard }" name="careerCard" id="careerCard" />
					</td>
					<td>现任岗位:</td>
					<td>
						<input readonly="readonly" type="text" value="${worker.currentJob }" name="currentJob" id="currentJob" />
					</td>
				</tr>
				<tr>
					<td >联系电话:</td>
					<td>
						<input readonly="readonly" type="text" value="${worker.phone }" name="phone" id="phone" data-options="" />
					</td>
					<td >工资:</td>
					<td>
						<input readonly="readonly" type="text" value="${worker.salary }" name="salary" id="salary"/>
					</td>
				</tr>
				<tr>
					<td>养老保险:</td>
					<td colspan="3">
						<input readonly="readonly" type="text" value="${worker.pensionInsurance }" name="pensionInsurance" id="pensionInsurance" />
					</td>
				</tr>
			<!-- 	<tr>
					<td>所在公司:</td>
					<td colspan="4">
						<input readonly="readonly" type="text" style="width:100%;"value="${worker.pensionInsurance }" name="pensionInsurance" id="pensionInsurance" />
					</td>
				</tr> -->
				<tr>
					<td >备注:</td>
					<td colspan="5">
						<textarea readonly="readonly" rows="4" cols="80" name="remark" id="remark">${worker.remark }</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center;">
						<a href="javascript:addWorker.close();" class="easyui-linkbutton" iconCls="icon-undo">返回</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
<div id="win"></div>
