<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<div>
	<table title="企业年审信息" class="print_tab">
		<tr>
			<td colspan="4">
				<h2 style="text-align: center;">用人企业安排残疾人就业名单</h2>
			</td>
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
	<table cellspacing="0" cellpadding="0" border="" title="企业年审信息" class="print_tab">
		<tr>
			<td style="width: 90px">姓名</td>
			<td style="width: 40px">性别</td>
			<td style="width: 180px">残疾证号</td>
			<td class="" style="width: 100px">联系电话</td>
			<td class="" style="width: 100px">残疾类别</td>
			<td class=" " style="width: 100px">残疾等级</td>
			<td class="" style="width: 100px">现任岗位</td>
			<td class="" style="width: 100px">月薪</td>

		</tr>
		<tr>
			<td>王占波</td>
			<td>男</td>
			<td>23010319571027093862</td>
			<td>肢体</td>
			<td>精神</td>
			<td>二级</td>
			<td>部门经理</td>
			<td>3000.00</td>
		</tr>
	</table>
	<div class="printBut">
		<button>打印</button>
		<button>打印页面设置</button>
		<button>打印预览</button>
		<button>返回</button>
	</div>
</div>
