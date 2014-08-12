<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />



<script type="text/javascript">
	/*
		残疾职工页面
	 */
	report = {};
	report.load = function() {
		var auditYear = $('#companyYear').combobox("getText");
		if(auditYear == null || auditYear == ''){
			$.messager.alert('提示信息','没有相关审核年份数据, 请审核后再进行查询.','info');
			return;
		}
		$.ajax({
			url : 'report/area/' + auditYear,
			type : 'post',
			async:false,
			success : function(data) {
				var content = '';
				$("#reportTab tbody tr:gt(2)").remove();
				for(var i=0;i<data.length;i++){
					if(i%2==1){
						content += '<tr class="reportTab_tr_light">';
					}else{
						content += '<tr>';																	
					}												//	单位性质，                                        单位总数，                                      单位总人数，                         待初单位数，
					content += '<td  class="reportTab_td_left">'+data[i].reportName+'</td><td>'+esd.common.fmoney(data[i].unitNum,0)+'</td><td>'+esd.common.fmoney(data[i].empTotal,0)+'</td><td>'+esd.common.fmoney(data[i].unAudit,0)+'</td>'
				//								待复审单位数，								已复核达标单位数				已复核未达标单位数								应安排人数						已安排人数
					+'<td>'+esd.common.fmoney(data[i].unReAudit,0)+'</td><td>'+esd.common.fmoney(data[i].auditOk,0)+'</td><td>'+esd.common.fmoney(data[i].unauditOk,0)+'</td><td>'+esd.common.fmoney(data[i].shouldTotal,2)+'</td><td>'+esd.common.fmoney(data[i].alreadyTotal,2)+'</td>'
					//				少安排人数								应缴金额												减免金额												实缴金额											已缴金额
					+'<td>'+esd.common.fmoney(data[i].lessTotal,2)+'</td><td>'+esd.common.fmoney(data[i].amountPayable,2)+'</td><td>'+esd.common.fmoney(data[i].reductionAmount,2)+'</td><td>'+esd.common.fmoney(data[i].actualAmount,2)+'</td><td class="reportTab_td_right">'+esd.common.fmoney(data[i].alreadyAmount,2)+'</td></tr>'
					;
				}
				content += '<tr><td colspan="14" align="right" class="reportTab_td_left reportTab_td_right">制表人：${createPeople}</td></tr>';
				$("#reportTab tbody").append(content);
			},
			error : function() {
				$.messager.alert('消息', '获取地区报表数据时错误。', 'error');
			
			}

		});

	};
	
	/**
	 *	导出
	 **/
	report.exportExport = function() {
		$.ajax({
			url : 'report/export/area/'+$('#companyYear').combobox("getText"),
			type : 'post',
			success : function(data) {
				if(data!="null"){
					window.location.href=data;
				}else{
				
					$.messager.alert('消息', '导出报表时错误。', 'error');
				}
			},
			error : function() {
			$.messager.alert('消息', '导出报表时错误。', 'error');
	
			}
		});
	};
	
	//组件解析完成
	$.parser.onComplete=function(){
		//加载数据
		report.load();
	};
	$(function() {
		$('#companyYear').combobox({
			onSelect:function(param){
				report.load();
			}
	});
	});
</script>
<div class="printOverAll">
	<div class="printOverAllTitle">
		审核年度
		<!-- 年份 -->
		<input id="companyYear" class="easyui-combobox" value="${year}" data-options="panelHeight:120,width:80,height:25,editable:false,valueField:'id',textField:'text',url:'${contextPath }/security/parameter/getyears'" />年
	</div>
	<!--年审地区汇总表 -->
	<table id="reportTab" cellspacing="0" cellpadding="0"  class="reportTab" >
		<tr>
			<td colspan="14" style="font-size:16px;font-weight:bold;">年审汇总表(地区)</td>
		</tr>
		<tr>
			<td colspan="11" class="reportTab_td_left" style="text-align: left;">制表单位:${createTabCompany}</td>
			<td colspan="3" style="text-align:right" class="reportTab_td_right">制表时间:${currentTime}</td>
		</tr>
		<tr class="reportTab_tr_title">
			<td class="reportTitle reportTab_td_left">地区</td>
			<td>单位总数</td>
			<td>单位总人数</td>
			<td>待初审单位数</td>
			<td>待复审单位数</td>
			<td>已复核达标单位数</td>
			<td>已复核未达标单位数</td>
			<td>应安排人数</td>
			<td>已安排人数</td>
			<td>少安排人数</td>
			<td>应缴金额</td>
			<td>减免金额</td>
			<td>实缴金额</td>
			<td class="reportTab_td_right">已缴金额</td>
		</tr>
	</table>

	<div align="center" style="margin-top: 15px">
		<a href="javascript:report.exportExport()" class="easyui-linkbutton" iconCls="icon-ok">导出</a>
	</div>
</div>
