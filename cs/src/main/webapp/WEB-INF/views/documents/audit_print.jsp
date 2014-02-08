<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<h2 style="text-align: center;">按比例安排残疾人就业情况表</h2>
<table data-options="fit:true,border:false,doSize:true" title="企业年审信息" class="print_table">
	<tr>
		<td style="text-align: left;">企业法人代码：123456789</td>
		<td>税务编码：01010111</td>
	</tr>
	<tr>
		<td style="text-align: left;">企业名称(盖章):黑龙江省北龙交通工程有限公司</td>
		<td>2012年度</td>
	</tr>
</table>
<!-- 企业基本情况表 -->
<table data-options="fit:true,border:false,doSize:true" cellspacing="0" cellpadding="0" border="" title="企业年审信息" class="print_table">
	<tr>
		<td class="td_short" rowspan="3">企业基本情况</td>
		<td>法人代表</td>
		<td class=" txt_">马站村</td>
		<td class="td_short">联系人</td>
		<td class="txt_">赵玉红</td>
		<td class="td_short">联系电话</td>
		<td class=" txt_">587845784</td>
	</tr>
	<tr>
		<td class="td_short">企业地址</td>
		<td colspan="5" class="txt_">哈尔滨市道里区河洛街10号</td>
	</tr>
	<tr>
		<td -g`class="td_short">开户银行</td>
		<td colspan="2" class="txt_">工行河图分理处</td>
		<td class="td_GD_short">账号</td>
		<td colspan="2" class="txt_">20124878181512012</td>
	</tr>
</table>

<!-- 在职职工情况 -->
<table data-options="fit:true,border:false,doSize:true" cellspacing="0" cellpadding="0" border="" title="企业年审信息" class="print_table">
	<tr>
		<td rowspan="3" class="td_short">在职职工情况</td>
		<td rowspan="2">职工总数(人)</td>

		<td colspan="4">在职残疾职工情况(人)</td>
		<td rowspan="2">在职残疾职工占在职职工%</td>

	</tr>
	<tr>
		<td>应安排数</td>
		<td>已安排数</td>
		<td>欠安排数</td>
		<td>超安排数</td>
	</tr>
	<tr>
		<td>10</td>
		<td>10</td>
		<td>10</td>
		<td>10</td>
		<td>10</td>
		<td>10</td>
	</tr>
</table>

<!-- 保障金应缴数 -->
<table data-options="fit:true,border:false,doSize:true" cellspacing="0" cellpadding="0" border="" title="保障金应缴数" class="print_table">
	<tr>
		<td style="text-align: left;" class="td_short">保障金应缴数</td>
		<td>￥:0.0元 大写:</td>
	</tr>
</table>
<!--备注-->
<table data-options="fit:true,border:false,doSize:true" cellspacing="0" cellpadding="0" border="" title="保障金应缴数" class="print_table">
	<tr>
		<td style="text-align: left;" class="td_short">备注</td>
		<td></td>
	</tr>

</table>

<table data-options="fit:true,border:false,doSize:true" title="" class="print_table">
	<tr>
		<td>企业负责人</td>
		<td>sss</td>

		<td>统计负责人</td>
		<td>sss</td>
		<td>报出日期</td>
		<td>报出日期：2013年07月01日</td>
	</tr>
</table>

<div>
	<button>打印</button>
	<button>打印页面设置</button>
	<button>打印预览</button>
	<button>返回</button>
</div>
<div />