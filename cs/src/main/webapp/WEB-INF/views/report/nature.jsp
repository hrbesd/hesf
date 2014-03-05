<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<script type="text/javascript">
	/*
		残疾职工页面
	 */
	printNature = {};
	printNature.load = function() {
		$.ajax({
			url : 'report/notice/' + 	$('#companyYear').combobox("getText"),
			type : 'post',
			async:false,
			success : function(data) {
			$("#printNatureTab tbody").empty();
				for(var i=0;i<data.length;i++){
	//																					单位性质，                                        单位总数，                                      单位总人数，                         待初单位数，
					$("#printNatureTab tbody").append("<tr><td>"+data[i].reportName+"</td><td>"+data[i].unitNum+"</td><td>"+data[i].empTotal+"</td><td>"+data[i].unAudit+"</td>"
				//								待复审单位数，								已复核达标单位数				已复核未达标单位数								应安排人数						已安排人数
					+"<td>"+data[i].unReAudit+"</td><td>"+data[i].unReAudit+"</td><td>"+data[i].unauditOk+"</td><td>"+data[i].shouldTotal+"</td><td>"+data[i].alreadyTotal+"</td>"
					//				少安排人数								应缴金额												减免金额												实缴金额											已缴金额
					+"<td>"+data[i].lessTotal+"</td><td>"+data[i].amountPayable+"</td><td>"+data[i].reductionAmount+"</td><td>"+data[i].actualAmount+"</td><td>"+data[i].alreadyAmount+"</td>"
					);
				}
			},
			error : function() {
				alert("获取单位性质报表数据时错误。");
			}
		});
	};

	/**
		导出
	**/
	printNature.exportExport = function() {
		$.ajax({
			url : 'report/export/nature/'+$('#companyYear').combobox("getText"),
			type : 'post',
			success : function(data) {
				if(data!="null"){
					window.location.href=data;
				}else{
					alert("导出报表时错误。");
				}
			},
			error : function() {
				alert("导出报表时错误。");
			}
		});
	};
	
	//组件解析完成
	$.parser.onComplete=function(){
		//加载数据
		printNature.load();
	};
	$(function() {
		$('#companyYear').combobox({
			onSelect:function(param){
				printNature.load();
			}
		});
	

	});
</script>
<div class="printOverall">
	<div>
		年审单位性质汇总表
		<!-- 年份 -->
		<input id="companyYear" class="easyui-combobox" value="${year}" data-options="height:30,editable:false,valueField:'id',textField:'text',url:'${contextPath }/security/parameter/getyears'" />年

	</div>
	<!--年审单位性质汇总表 -->
	<table id="printNatureTab" border="" cellspacing="0" cellpadding="0" style="margin-top: 30px">
		<thead>
					<tr>
			<th colspan="14">年审单位性质汇总表</th>
		</tr>
		<tr>
			<th colspan="7" style="text-align: left;">制表单位:${createTabCompany}</th>
			<th colspan="7" style="text-align:right">制表时间:${currentTime}</th>
		</tr>
		<tr>
			<td class="reportTitle">单位性质</td>
			<td>单位总数</td>
			<td>单位总人数</td>
			<td>待初单位数</td>
			<td>待复审单位数</td>
			<td>已复核达标单位数</td>
			<td>已复核未达标单位数</td>
			<td>应安排人数</td>
			<td>已安排人数</td>
			<td>少安排人数</td>
			<td>应缴金额</td>
			<td>减免金额</td>
			<td>实缴金额</td>
			<td>已缴金额</td>
		</tr>
		</thead>
		<tbody >
		
		</tbody>
		<tfoot>
			<tr>
			<td colspan="14" align="right">制表人：${createPeople}</td>
		</tr>
		</tfoot>
	</table>

	<div align="center" style="margin-top: 15px">
		<a href="javascript:printNature.exportExport()" class="easyui-linkbutton" iconCls="icon-ok">导出</a>
	</div>



</div>
