<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<table title="企业年审信息" class="print_tab">
		<tr>
			<td colspan="4">
				<h2 style="text-align: center;">用人企业安排残疾人就业名单</h2></td>
		</tr>
		<tr>
			<td style="text-align: left;" class="print_outline">企业法人代码：</td>
			<td class="print_content">123456789</td>

			<td class="print_outline">税务编码：</td>
			<td class="print_content">01010111</td>
		</tr>
		<tr>
			<td style="text-align: left;" class="print_outline">企业名称(盖章):</td>
			<td class="print_content">黑龙江省北龙交通工程有限公司</td>
			<td class="print_outline">年度</td>
			<td class="print_content">2012年</td>
		</tr>
	</table>
<!-- 企业基本情况表 -->
<table cellspacing="0" cellpadding="0" border="1" title="企业年审信息" class="print_tab">
	<tr>
		<td class="print_outline" rowspan="3">企业基本情况</td>
		<td>法人代表</td>
		<td class="print_content">马站村</td>
		<td class="">联系人</td>
		<td class="print_content">赵玉红</td>
		<td class="">联系电话</td>
		<td class="print_content">587845784</td>
	</tr>
	<tr>
		<td class="">企业地址</td>
		<td colspan="5" class="print_content" align="left">哈尔滨市道里区河洛街10号</td>
	</tr>
	<tr>
		<td class="">开户银行</td>
		<td colspan="2" class="print_content">工行河图分理处</td>
		<td class="">账号</td>
		<td colspan="2" class="print_content">20124878181512012</td>
	</tr>
</table>

<!-- 在职职工情况 -->
<table cellspacing="0" cellpadding="0" border="" title="企业年审信息" class="print_tab">
	<tr>
		<td rowspan="3" class="print_outline">在职职工情况</td>
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
<table cellspacing="0" cellpadding="0" border="" title="保障金应缴数" class="print_tab">
	<tr>
		<td class="print_outline">保障金应缴数</td>
		<td colspan="6" class="print_content">￥:0.0元 大写:</td>
	</tr>
</table>
<!--备注-->
<table cellspacing="0" cellpadding="0" border="" title="保障金应缴数" class="print_tab">
	<tr>
		<td class="print_outline">备注</td>
		<td></td>
	</tr>

</table>

<table class="print_tab">
	<tr>
		<td class="print_outline">企业负责人:</td>
		<td class="print_content">李成果</td>
		<td class="print_outline">统计负责人:</td>
		<td class="print_content">李成果</td>
		<td class="print_outline">报出日期:</td>
		<td class="print_content">2013年07月</td>
	</tr>
</table>

<div class="printBut">
	<button>打印</button>
	<button>打印页面设置</button>
	<button>打印预览</button>
	<button>返回</button>
</div>
<div />