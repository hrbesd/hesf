<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style>

/*表格部分*/
.company-examined {
	width: 910px;
	/*background-color: #6AB7DB;*/
	background-color: #E0ECFF;
	text-align: center;
	/*border: #95B8E7 1px solid;*/
}

.td_long {
	width: 300px;
}

.td_short {
	width: 100px;
}

.td_short_150 {
	width: 150px;
}

.td_GD_short {
	width: 70px;
}

.txt_ {
	text-align: left;
	padding-left: 5px;
}

.td_zhong {
	width: 200px;
}

.text_short {
	width: 80px;
}

.bj_belu {
	text-align: left;
}

.bj_belu2 {
	text-align: left;
	width: 350px;
}

.bj_belu3 {
	text-align: left;
	width: 160px;
}

.bj_belu4 {
	text-align: left;
	width: 120px;
}

.readonly {
	background: #d8d8d8;
}

.warn {
	background-color: #D8D828;
}

/*输入框背景颜色 特殊*/
.bj_special {
	background-color: #7881B2;
}
/*输入框背景颜色    不可输入样色*/
.bj_prohibit {
	background-color: #E1E9F8;
}

.tab_select {
	width: 90%;
}

#startaudit_tabs td {
	font-size: 12px;
}

#startaudit_tabs input {
	font-size: 12px;
	/*height: 18px;*/
	line-height: 28px;
	margin: 0px;
	width: 100%;
	border: 0px;
}

#startaudit_tabs textarea {
	font-size: 12px;
	line-height: 28px;
	width: 100%;
	border: 0px;
}

#startaudit_tabs td {
	border: #95B8E7 1px solid;
}

#startaudit_tabs .combo {
	border-style: none;
}
</style>

<script type="text/javascript">
	initAudit = {};
	//拒绝
	initAudit.refusal = function() {
		var comment = $('textarea[name=verifyAuditComment]').val();
		alert(comment);
		if (comment == "" || comment == null || comment == undefined) {
			alert("拒绝必须填写 复审意见");
			return;
		}
		esd.common.syncPostSubmitEx("#form", "${contextPath }/security/audits/refusal", function(data) {
			if (data == true) {
				$.messager.alert('消息', '拒绝成功', 'info', function() {
					esd.common.defaultOpenWindowClose();
					$("#initAuditList_datagrid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', '拒绝失败', 'info');
			}
		});
	};
	//复审确认
	initAudit.verifyAudit = function() {
		esd.common.syncPostSubmitEx("#form", "${contextPath }/security/audits/verifyAudit", function(data) {
			if (data == true) {
				$.messager.alert('消息', '复审确认成功', 'info', function() {
					$("#auditPanel").window("close");
					$("#initAuditList_datagrid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', '复审确认失败', 'info');
			}
		});
	};
	//返回
	initAudit.back = function() {
		esd.common.defaultOpenWindowClose();
	};

	$(function() {
		$(".readonly").each(function() {
			$(this).attr("readonly", "readonly");
			$(this).attr("disabled", "disabled");
		});
	});
</script>

<form id="form" action="${contextPath }/security/audits/save" method="post">
	<!-- 年审企业表格  第二部分 -->
	<table class="company-examined" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="td_short">档案号码:</td>
			<td class="td_short bj_belu" colspan="2"><input class="bj_belu2 readonly" type="text" value="${entity.company.companyCode}" />
			</td>
			<td class="td_short">税务代码:</td>
			<td class="td_long bj_belu" colspan="3"><input class="bj_belu2 readonly" type="text" value="${entity.company.companyTaxCode}" />
			</td>
		</tr>
		<tr>
			<td class="td_short">企业名称:</td>
			<td class="td_long bj_belu" colspan="2"><input class="bj_belu2 readonly" type="text" value="${entity.company.companyName}" />
			</td>
			<td class="td_short">年审年度:</td>
			<td class="td_long bj_belu" colspan="3"><input class="bj_belu2 readonly " type="text" value="${entity.company.year}" />
			</td>
		</tr>

		<tr>
			<td rowspan="4" class="td_short">单位基本情况</td>
			<td class="td_short">法人代表:</td>
			<td class="bj_belu"><input class="bj_belu3 readonly" type="text" value="${entity.company.companyLegal}" /></td>
			<td class="td_short_150">组织机关代码证:</td>
			<td class="bj_belu"><input class="bj_belu3 readonly" type="text" value="${entity.company.companyOrganizationCode}" />
			</td>
			<td class="td_short">企业性质:</td>
			<td class="bj_belu"><select disabled="disabled" style="font-size: 12px;" class="easyui-combobox" data-options="width:150,height:30,editable:false">
					<c:forEach items="${companyPropertys}" var="item">
						<option value="${item.id}" <c:if test="${entity.company.companyProperty.id eq item.id}">selected="selected"</c:if>>${item.companyProperty }</option>
					</c:forEach>
			</select>
			</td>

		</tr>
		<tr>
			<td class="td_short">联系人 :</td>
			<td class="bj_belu"><input class="bj_belu3 readonly" type="text" value="${entity.company.companyContactPerson}" />
			</td>
			<td class="td_short_150">电话号码:</td>
			<td class="bj_belu"><input class="bj_belu3 readonly" type="text" value="${entity.company.companyPhone}" />
			</td>
			<td class="td_short">经济类型:</td>
			<td class="bj_belu"><select disabled="disabled" style="font-size: 12px;" class="easyui-combobox" data-options="width:150,height:30,editable:false">
					<c:forEach items="${companyEconomyTypes}" var="item">
						<option value="${item.id}" <c:if test="${entity.company.companyEconomyType.id eq item.id}">selected="selected"</c:if>>${item.companyEconomyType }</option>
					</c:forEach>
			</select>
			</td>

		</tr>
		<tr>
			<td class="td_short">手机号码:</td>
			<td class="bj_belu"><input class="bj_belu3 readonly" value="${entity.company.companyMobile}" />
			</td>
			<td class="td_short_150">企业地址:</td>
			<td class="bj_belu" colspan="6"><input class="readonly" name="company.companyAddress" value=${entity.company.companyAddress } />
			</td>

		</tr>
		<tr>
			<td class="td_short">开户银行:</td>
			<td class="bj_belu"><input class="bj_belu3 readonly" value=${entity.company.companyBank } />
			</td>
			<td class="td_short_150">银行账号:</td>
			<td class="bj_belu"><input class="bj_belu3 readonly" value=${entity.company.companyBankAccount } />
			</td>
			<td class="td_short">邮政编码:</td>
			<td class="bj_belu"><input class="readonly" value=${entity.company.companyZipCode } />
			</td>

		</tr>
	</table>
	<!-- 年审企业表格  第三部分(残疾人信息) -->
	<table class="company-examined" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td rowspan="4" class="td_short">在职员工情况:</td>
			<td>在职员工总数(人):</td>
			<td colspan="2">在职残疾职工情况(人)</td>
			<td>已录入残疾职工人数(人):</td>
			<td>预定残疾职工人数(人):</td>

			<td>上年度未缴保障金额度:</td>
		</tr>
		<tr>
			<td rowspan="3" style="vertical-align: bottom"><input name="company.companyEmpTotal" title="在职员工总数" id="zaiZhiYuanGongZongShu" type="text" value="${entity.company.companyEmpTotal }"
				class="readonly" style="border-top: #95B8E7 2px solid;" />
			</td>
			<td>应按排数:</td>
			<td>已安排数:</td>

			<td rowspan="2" style="vertical-align: bottom"><input id="yiLuRuCanJiRen" class="readonly" title="已录入残疾职工人数" value="${entity.company.companyHandicapTotal }"
				style="border-top: #95B8E7 2px solid;" />
			</td>
			<td rowspan="2" style="vertical-align: bottom"><input id="yuDingCanJiRen" value="${entity.company.companyPredictTotal }" title="预定残疾职工人数" class="readonly"
				style="border-top: #95B8E7 2px solid;" />
			</td>
			<td style="vertical-align: bottom" rowspan="2"><input id="shangNianDuWeiJiaoBaoZhangJin" title="上年度未缴保障金额度" class="readonly" style="border-top: #95B8E7 2px solid;"
				value="${entity.complementAmount }" />
			</td>

		</tr>
		<tr>
			<td><input type="text" class="text_short readonly" value="${entity.company.companyShouldTotal}" title="应按排数" />
			</td>
			<td><input type="text" class="text_short readonly" value="${entity.company.companyAlreadyTotal}" title="已按排数" />
			</td>
		</tr>

	</table>
	<!-- 年审企业表格  第四部分(保障金额度) -->
	<table class="company-examined" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td style="width: 60px;">是否免交:</td>
			<td class="bj_belu"><select disabled="disabled" id="cc" style="font-size: 12px;" class="easyui-combobox" data-options="value:1,width:40,panelHeight:80,height:30,editable:false">
					<option value="false" <c:if test="${entity.isExempt eq 'false'}">selected="selected"</c:if>>否</option>
					<option value="true" <c:if test="${entity.isExempt eq 'true'}">selected="selected"</c:if>>是</option>
			</select>
			</td>
			<td class="td_GD_short">应缴金额:</td>
			<td class="bj_belu"><input type="text" name="amountPayable" class="readonly" value="${entity.amountPayable}" />
			</td>
			<td class="td_GD_short">减缴金额:</td>
			<td class="bj_belu"><input type="text" class="readonly" value="${entity.reductionAmount}" />
			</td>
			<td class="td_GD_short">补缴金额:</td>
			<td class="bj_belu"><input type="text" class="readonly" value="${entity.complementAmount }" />
			</td>
			<td class="td_GD_short">实缴金额:</td>
			<td class="bj_belu"><input type="text" class="readonly" value="${entity.actualAmount}" />
			</td>

		</tr>
	</table>

	<!-- 年审企业表格  第五部分(残疾人信息备注) -->
	<table class="company-examined" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="td_short">备注:</td>
			<td class="bj_belu" colspan="6"><textarea name="remark" rows="3" cols="90" class="readonly">${entity.remark }</textarea>
			</td>

		</tr>
	</table>
	<!-- 年审企业表格  第六部分(意见栏) -->
	<table class="company-examined" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="td_short">初审意见:</td>
			<td class="bj_belu" colspan="3"><textarea class="readonly" name="initAuditComment" rows="3" cols="45">${entity.initAuditComment}</textarea>
			</td>
			<td class="td_short">复审意见:</td>
			<td colspan="3"><textarea name="verifyAuditComment" class="warn" rows="3" cols="45">${entity.verifyAuditComment}</textarea>
			</td>
		</tr>
		<tr>
			<td class="td_short">初审日期:</td>
			<td><fmt:formatDate value="${entity.initAuditDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="initAuditDate" /> <input class="bj_belu4 readonly" value="${initAuditDate}" />
			</td>
			<td class="td_short">初审人:</td>
			<td><input type="text" class="bj_belu4 readonly" value="${entity.initAuditUserId}" />
			</td>
			<td class="td_short">复核日期:</td>
			<td><fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="date" /> <input class="bj_belu4 readonly" value="${date}" />
			</td>
			<td class="td_short">复审人:</td>
			<td><input type="text" class="bj_belu4 readonly" value="${sessionScope.userRealName}" />
			</td>
		</tr>
	</table>
	<div style="text-align: center;margin-top: 30px;">
		<input name="id" type="hidden" value="${entity.id}" /> <input name="version" type="hidden" value="${entity.version}" /> <a href="javascript:initAudit.refusal();" class="easyui-linkbutton"
			iconCls="icon-cancel">拒绝</a> <a href="javascript:initAudit.verifyAudit();" class="easyui-linkbutton" iconCls="icon-ok">确认复审</a> <a href="javascript:initAudit.back();" class="easyui-linkbutton"
			iconCls="icon-back">返回</a>
	</div>
</form>


