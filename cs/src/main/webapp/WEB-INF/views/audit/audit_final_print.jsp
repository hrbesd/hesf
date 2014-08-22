<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<%@page contentType="text/html" pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<script type="text/javascript">
		var finalPrint = {};
		finalPrint.printFinalAuditWindow = function(){
			esd.common.printFinalAuditWindow();
		};
		//初始化打印页面数据
		finalPrint.initFinalPrintInfo = function(){
		//	var auditId = '${entity.id}';
			$.ajax({
				url : '${contextPath}/security/query/audit/getFinalPrintInfo/${entity.id}',
				type : 'get',
				success : function(data) {
					//公司信息
					$('#printCompanyName').html(data.companyName);
					$('#printCompanyProperty').html(data.companyProperty);
					$('#printCompanyManagerment').html(data.companyManagement);
					$('#printCompanyLegal').html(data.companyLegal);
					$('#printCompanyAddress').html(data.companyAddress);
					$('#printCompanyPhone').html(data.companyPhone);
					//审核数据
					$('#printAuditYear').html(data.year);
					$('#printAuditEmpTotal').html(data.empTotal);
					$('#printAuditShouldTotal').html(data.shouldTotal);
					$('#printAuditAlreadyTotal').html(data.alreadyTotal);
					$('#printAuditLessTotal').html(data.lessTotal);
					$('#printAuditPayAmount').html(data.payAmount);
					//员工信息
					var workerStr = '';
					var workerLength = data.workerList.length;
					if(workerLength == 0){
						workerStr += '<tr><td colspan="6" style="height:50px;font-weight:bold;">该单位没有在职的残疾职工</td></tr>';
					}else{
						$(data.workerList).each(function(index,element){
							workerStr += '<tr>';
							workerStr += '<td>'+element.name+'</td>';	//姓名
							if(element.gender == 0){
								element.gender = '女';
							}else{
								element.gender = '男';
							}
							workerStr += '<td>'+element.gender+'</td>';	//性别
							workerStr += '<td>'+element.age+'</td>';	//年龄
							workerStr += '<td colspan="2">'+element.handicapCode+'</td>';	//残疾证号
							if(element.isRetired == true){
								element.isRetired = '是';
							}else{
								element.isRetired = '-';
							}
							workerStr += '<td>'+element.isRetired+'</td>';	//是否内退
							workerStr += '</tr>';
						});
					}
					$('#infoBody tbody').append(workerStr);
					return true;
				},
				error : function() {
					$.messager.alert('消息', '获取数据失败, 请重新尝试或联系管理员.', 'error');
				}
			});
		};
		$(document).ready(function(){
			finalPrint.initFinalPrintInfo();
		});
	</script>
	<!--startfinalauditprint-->
	<style type="text/css">
		#infoBody{
			width:100%;
			color:#333333;
			border-width: 1px;
			border-color: #666666;
			border-collapse: collapse;
			margin-top:10px;
		}
		#infoBody thead tr td {
			height: 30px;
			text-align:center;
			font-size:14px;
		}
		#infoBody tbody tr td {
			text-align:center;
			font-size:14px;
		}
		#infoBody tfoot tr td p{
			width:100%;
		}
		.p_toleft{
			text-align: left;
			margin-top: 5px;
			margin-left: 5px;
			height:80px;
		}
		.p_toright{
			text-align: right;
		}
	</style>
	<div id="printArea" style="width: 800px;  margin: 10px auto auto;font-size: 15px;text-align: center;">
			<div style="text-align:center;font-size:25px;font-weight: bold;">
				<span style="font-size:20px;">黑 龙 江 垦 区</span><br/>
				分散按比例安置残疾人就业认定书
			</div>
			<div>
				<table id="infoBody" border="1px">
					<thead>
						<tr>
							<td width="15%">单位名称</td>
							<td colspan="2" width="35%" id="printCompanyName">${printEntity.companyName }</td>
							<td width="15%">单位性质</td>
							<td colspan="2" width="35%" id="printCompanyProperty">${printEntity.companyProperty }</td>
						</tr>
						<tr>
							<td>主管部门</td>
							<td colspan="2" id="printCompanyManagerment">${printEntity.companyManagerment }</td>
							<td>法人代表</td>
							<td colspan="2" id="printCompanyLegal">${printEntity.companyLegal }</td>
						</tr>
						<tr>
							<td>单位地址</td>
							<td colspan="2" id="printCompanyAddress">${printEntity.companyAddress}</td>
							<td>电&nbsp;&nbsp;&nbsp;话</td>
							<td colspan="2" id="printCompanyPhone">${printEntity.companyPhone }</td>
						</tr>
						<tr>
							<td>年度</td>
							<td>在职职工数<br/>(含临时工)</td>
							<td>应安置残疾人数</td>
							<td>已安置残疾人数</td>
							<td>按1.5%比例<br/>尚缺残疾人数</td>
							<td>应缴纳残疾人<br/>就业保障金金额(元)</td>
						</tr>
						<tr>
							<td id="printAuditYear">${printEntity.year}</td>
							<td id="printAuditEmpTotal">${printEntity.empTotal }</td>
							<td id="printAuditShouldTotal">${printEntity.shouldTotal }</td>
							<td id="printAuditAlreadyTotal">${printEntity.alreadyTotal }</td>
							<td id="printAuditLessTotal">${printEntity.lessTotal }</td>
							<td id="printAuditPayAmount">${printEntity.payAmount }</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="6" style="height:30px;">在职残疾职工名单</td>
						</tr>
						<tr>
							<td width="15%">姓名</td>
							<td width="10%">性别</td>
							<td width="10%">年龄</td>
							<td colspan="2" width="40%">残疾人证号</td>
							<td>是否内退</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="3">
								<p class="p_toleft">单位意见:</p>
								<p class="p_toright">年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日</p>
							</td>
							<td colspan="3">
								<p class="p_toleft">残疾人劳动就业服务管理中心认定意见:</p>
								<p class="p_toright">年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日</p>
							</td>
						</tr>
						<tr>
							<td style="text-align:center;">
								备<br/>注
							</td>
							<td colspan="5" style="height:100px;">
								&nbsp;
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
	</div>
	<!--endfinalauditprint--> 
	<div style="width:100%;text-align:center;margin-top:20px;">
		<c:if test="${userGroupId == 1 || userGroupId == 6 }">
			<input type="button" onclick="finalPrint.printFinalAuditWindow();" value="点击我打印本页" />
		</c:if>
	</div>

