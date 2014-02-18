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
			url : 'report/notice/' + $("#year").val(),
			type : 'post',
			success : function(data) {
		
				
//<td></td><td></td><td></td><td></td><td></td><td></td>
//<td></td><td>已缴金额</td>
				for(var i=0;i<data.length;i++){
	//																					单位性质，                                        单位总数，                                      单位总人数，                         待初单位数，
					$("#printNatureTab").append("<tr><td>"+data[i].reportName+"</td><td>"+data[i].unitNum+"</td><td>"+data[i].empTotal+"</td><td>"+data[i].unAudit+"</td>"
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
	$(function() {
		printNature.load();
	});
</script>
<input type="hidden" value="${year}" id="year" />
<div class="printOverall">
	<div>
		年审单位性质汇总表<select>
			<option>2012</option>
		</select>
	</div>
	<!--年审单位性质汇总表 -->
	<table id="printNatureTab"  border="" cellspacing="0" cellpadding="0" style="margin-top: 30px">
		<tr>
			<th colspan="14">年审单位性质汇总表</th>
		</tr>
		<tr>
			<th colspan="7" style="text-align: left;">制表单位:黑龙江省残疾人联合会</th>
			<th colspan="7" style="text-align:right">制表时间:2013-08-29</th>
		</tr>
		<tr>
			<td>单位性质</td>
			<td>单位总数</td>
			<td>单位总人数</td>
			<td>待初单位数</td>
			<td>待复审单位数</td>
			<td>已复核达标单位数</td>
			<td>已复核未达标单位数</td>
			<td>应安排人数</td>
			<td>已安排人数</td>
			<td>预定人数</td>
			<td>应缴金额</td>
			<td>减免金额</td>
			<td>实缴金额</td>
			<td>已缴金额</td>
		</tr>
		
		
		
		
		<tr>
			<td colspan="14" align="right">制表人：校验</td>
		</tr>
	</table>

	<div align="center" style="margin-top: 15px">

		<a href="" class="easyui-linkbutton" iconCls="icon-undo">导出</a>
	</div>



</div>
