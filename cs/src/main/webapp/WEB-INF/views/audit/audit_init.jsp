<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style>

/*表格部分*/
.company-examined {
	width: 900px;
	background-color: #E0ECFF;
	text-align: center;
}

#startaudit_tabs td {
	font-size: 12px;
}

#account td {
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
	initAudit.show = function(w, q) {
		if (w.length > 0) {
			var c = "<h3>未审年度计算明细</h3>";
			var t = "<table border='1' width='485'>";
			var s = c + t + "<tr><th>未审年度</th><th>应缴金额</th><th>逾期天数</th><th>滞纳金比例</th><th>滞纳金</th><th>年度总金额</th></tr>";
			for ( var i = 0; i < w.length; i++) {
				s = s + "<tr><td>" + w[i].year + "</td><td>" + w[i].money + "</td><td>" + w[i].days + "</td><td>" + w[i].prop + "</td><td>" + w[i].penalty + "</td><td>" + w[i].total + "</td></tr>";
			}
			var m1 = "<ul><li>应缴金额=审计年度的残疾人保障金(安排残疾人总人数按(零)计算)</li><li>滞纳金=应缴金额×逾期天数×滞纳金比例</li>" + "<li>年度总金额 = 应缴金额+滞纳金</li></ul>";
			s = s + "</table>" + m1;
		}
		if (q.length > 0) {
			var c1 = "<h3>欠缴年度计算明细</h3>";
			s = s + c1 + t + "<tr><th>欠缴年度</th><th>欠缴金额</th><th>逾期天数</th><th>滞纳金比例</th><th>滞纳金</th><th>年度总金额</th></tr>";
			for ( var i = 0; i < q.length; i++) {
				s = s + "<tr><td>" + q[i].year + "</td><td>" + q[i].money + "</td><td>" + q[i].days + "</td><td>" + q[i].prop + "</td><td>" + q[i].penalty + "</td><td>" + q[i].total + "</td></tr>";
			}
			var m3 = "<ul><li>滞纳金=欠缴金额×逾期天数×滞纳金比例</li><li>年度总金额 = 应缴金额+滞纳金</li></ul>";
			//$('#account #q').html(s);
			s = s + "</table>" + m3;
		}
		$('#account').dialog({
			title : '未缴款明细',
			width : 500,
			height : 500,
			closed : false,
			cache : false,
			content : s,
			modal : true
		});
	};
	initAudit.initVerify = function() {
		$('#zaiZhiYuanGongZongShu').attr("onkeyup", "value=value.replace(/\\D/g,'')");
		$('#zaiZhiYuanGongZongShu').attr("onmouseup", "value=value.replace(/\\D/g,'')");
		$('#zaiZhiYuanGongZongShu').attr("maxlength", "9");
		$('#yuDingCanJiRen').attr("onkeyup", "value=value.replace(/\\D/g,'')");
		$('#yuDingCanJiRen').attr("onmouseup", "value=value.replace(/\\D/g,'')");
		$('#yuDingCanJiRen').attr("maxlength", "9");
	};

	//输入金钱校验
	initAudit.checkMoney = function(e) {
		if (e.value == e.value2)
			return;
		if (e.value.search(/^\d*(?:\.\d{0,2})?$/) == -1)
			e.value = (e.value2) ? e.value2 : '';
		else
			e.value2 = e.value;
	};

	initAudit.jisuan = function() {
		var param = {};
		param.zaiZhiYuanGongZongShu = $('#zaiZhiYuanGongZongShu').val();
		param.yingAnPaiCanJiRen = $('#yingAnPaiCanJiRen').val();
		param.yiAnPaiCanJiRen = $('#yiAnPaiCanJiRen').val();
		param.yiLuRuCanJiRen = $('#yiLuRuCanJiRen').val();
		param.yuDingCanJiRen = $('#yuDingCanJiRen').val();

		param.shangNianDuWeiJiaoBaoZhangJin = $('#shangNianDuWeiJiaoBaoZhangJin').val();
		param.yingJiaoJinE = $('#yingJiaoJinE').val();
		param.jianJiaoJinE = $('#jianJiaoJinE').val();
		param.shiJiaoJinE = $('#shiJiaoJinE').val();
		param.zhiNaJin = $('#zhiNaJin').val();
		param.mianZhiNaJin = $('#mianZhiNaJin').combobox('getValue');
		param.mianJiao = $('#mianJiao').combobox('getValue');
		param.shiJiaoZongJinE = $('#shiJiaoZongJinE').val();
		param.year = $('#year').val();
		param.companyCode = $('input[name="company.companyCode"]').val();
		$.ajax({
			url : 'audits/calculate',
			type : 'POST',
			data : param,
			success : function(data) {
				$('#zaiZhiYuanGongZongShu').val(data.s_zaiZhiYuanGongZongShu);
				$('#yingAnPaiCanJiRen').val(data.s_yingAnPaiCanJiRen);
				$('#yiAnPaiCanJiRen').val(data.s_yiAnPaiCanJiRen);
				$('#yiLuRuCanJiRen').val(data.s_yiLuRuCanJiRen);
				$('#yuDingCanJiRen').val(data.s_yuDingCanJiRen);

				$('#shangNianDuWeiJiaoBaoZhangJin').val(data.s_shangNianDuWeiJiaoBaoZhangJin);
				var wl = data.weiShenMingXi.length;
				var ql = data.qianJiaoMingXi.length;
				if (wl != 0 || ql != 0) {
					$('#message').css("display", "block");
					$('#message').bind("click", function() {
						initAudit.show(data.weiShenMingXi, data.qianJiaoMingXi);
					});
				}
				$('#isDelayPay').val(data.isDelayPay);
				
				$('#yingJiaoJinE').val(data.s_yingJiaoJinE);
				$('#jianJiaoJinE').val(data.s_jianJiaoJinE);
				$('#shiJiaoJinE').val(data.s_shiJiaoJinE);
				$('#zhiNaJin').val(data.s_zhiNaJin);
				$('#zhiNaJinTianShu').val(data.s_zhiNaJinTianShu);
				$('#shiJiaoZongJinE').val(data.s_shiJiaoZongJinE);

			},
			error : function() {
				alert("请求错误");
			},
			dataType : "json",
			async : true
		});

	};
	initAudit.save = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data == true) {
				$.messager.alert('消息', '保存成功', 'info',function(data){
					esd.common.defaultOpenWindowClose();
				});
			} else {
				$.messager.alert('消息', '保存失败', 'info');
			}
		});
	};
	initAudit.audit = function() {
		esd.common.syncPostSubmitEx("#form", "${contextPath }/security/audits/audit", function(data) {
			if (data == true) {
				$.messager.alert('消息', '审批成功', 'info', function() {
					esd.common.defaultOpenWindowClose();
					$("#initAuditList_datagrid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', '审批失败', 'info');
			}
		});
	};

	/*
	关闭单位年审窗口
	 */
	initAudit.back = function() {
		esd.common.defaultOpenWindowClose();
	};

	$(function() {
		$(".readonly").each(function() {
			$(this).attr("readonly", "readonly");
			$(this).attr("disabled", "disabled");
		});
		initAudit.initVerify();
		//初始化easyUi完成
		$.parser.onComplete = function(){
			$('#mianJiao').combobox({
				onChange:initAudit.jisuan
			});	
			$('#mianZhiNaJin').combobox({
				onChange:initAudit.jisuan
			});	
		};
		

	});
</script>

<form id="form" action="${contextPath }/security/audits/save" method="post" style="margin-top: 5px; margin-left: 2px;">
	<!-- 年审企业表格  第二部分 -->
	<div style="border: #95B8E7 1px solid; width: 900px;">
		<table class="company-examined" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100">档案号码:</td>
				<td width="300" colspan="2"><input name="company.companyCode" class="bj_belu2 readonly" type="text" value="${entity.company.companyCode}" /> <input type="hidden" id="companyId"
					name="company.id" value="${entity.company.id}" /> <input type="hidden" id="year" value="${entity.year}" /> <input type="hidden" name="company.version" value="${entity.company.version}" /></td>
				<td>年审年度:</td>
				<td><input name="company.year" class="readonly" type="text" value="${entity.company.year}" /></td>
				<td>未审年度:</td>
				<td><input type="text" readonly="readonly" <c:if test="${unAudityearNum==0}">value="无" class="readonly"</c:if> <c:if test="${unAudityearNum>0}">value="${unAudityear}" class="error"</c:if> />
				</td>
			</tr>
			<tr>
				<td>企业名称:</td>
				<td colspan="2"><input name="company.companyName" type="text" value="${entity.company.companyName}" /></td>

				<td style="width:100px;">税务代码:</td>
				<td width="180"><input name="company.companyTaxCode" type="text" value="${entity.company.companyTaxCode}" /></td>
				<td width="100">年龄超标</td>
				<td><input type="text" readonly="readonly" value="${ageEx }" <c:if test="${ageEx>0}">class="error"</c:if> <c:if test="${ageEx==0}">class="readonly"</c:if> /></td>
			</tr>

			<tr>
				<td rowspan="4">单位基本情况</td>
				<td width="100">法人代表:</td>
				<td width="199"><input name="company.companyLegal" class="bj_belu3" type="text" value="${entity.company.companyLegal}" />
				</td>
				<td>组织机关代码证:</td>
				<td><input name="company.companyOrganizationCode" class="bj_belu3" type="text" value="${entity.company.companyOrganizationCode}" /></td>
				<td>企业性质:</td>
				<td>
					<!--
			<input value="${entity.company.companyProperty.id}" method="GET" id="companyProperty" name="company.companyProperty.id" class="easyui-combobox"
				data-options="height:30,editable:false,valueField:'id',textField:'companyProperty',url:'parameter/property'" />
				--> <select style="font-size: 12px;" class="easyui-combobox" name="company.companyProperty.id" data-options="width:106,height:30,editable:false">
						<c:forEach items="${companyPropertys}" var="item">
							<option value="${item.id}" <c:if test="${entity.company.companyProperty.id eq item.id}">selected="selected"</c:if>>${item.companyProperty }</option>
						</c:forEach>
				</select></td>

			</tr>
			<tr>
				<td>联系人 :</td>
				<td><input name="company.companyContactPerson" class="bj_belu3" type="text" value="${entity.company.companyContactPerson}" /></td>
				<td>电话号码:</td>
				<td><input name="company.companyPhone" class="bj_belu3" type="text" value="${entity.company.companyPhone}" /></td>
				<td>经济类型:</td>
				<td>
					<!-- 
			<input value="${entity.company.companyEconomyType.id}" method="GET" id="companyEconomyType" name="company.companyEconomyType.id" class="easyui-combobox" 
			data-options="height:30,editable:false,valueField:'id',textField:'companyEconomyType',url:'parameter/economytype'" />
			 --> <select style="font-size: 12px;" class="easyui-combobox" name="company.companyEconomyType.id" data-options="width:106,height:30,editable:false">
						<c:forEach items="${companyEconomyTypes}" var="item">
							<option value="${item.id}" <c:if test="${entity.company.companyEconomyType.id eq item.id}">selected="selected"</c:if>>${item.companyEconomyType }</option>
						</c:forEach>
				</select></td>

			</tr>
			<tr>
				<td>手机号码:</td>
				<td><input name="company.companyMobile" class="bj_belu3" value="${entity.company.companyMobile}" /></td>
				<td>企业地址:</td>
				<td colspan="6"><input name="company.companyAddress" value=${entity.company.companyAddress } /></td>

			</tr>
			<tr>
				<td>开户银行:</td>
				<td><input name="company.companyBank" class="bj_belu3" value=${entity.company.companyBank } /></td>
				<td>银行账号:</td>
				<td><input name="company.companyBankAccount" class="bj_belu3" value=${entity.company.companyBankAccount } /></td>
				<td>邮政编码:</td>
				<td><input name="company.companyZipCode" value=${entity.company.companyZipCode } /></td>

			</tr>
		</table>
		<!-- 年审企业表格  第三部分(残疾人信息) -->
		<table class="company-examined" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td rowspan="4" width="100">在职员工情况:</td>
				<td width="100">职员工总数:</td>
				<td colspan="4" width="200">职残疾职工情况</td>


				<td width="98">未缴金额:</td>
				<td width="91">未审年数:</td>
				<td>补审年度:</td>
			</tr>
			<tr>
				<td rowspan="3" style="vertical-align: bottom"><input name="company.companyEmpTotal" title="在职员工总数" id="zaiZhiYuanGongZongShu" type="text" value="${entity.company.companyEmpTotal }"
					class="warn" style="border-top: #95B8E7 2px solid;" onblur="initAudit.jisuan()" /></td>
				<td width="97">应按排数:</td>
				<td width="100">已安排数:</td>
				<td width="100">已录入数</td>
				<td width="98">预定人数</td>
				<td><input style="height: 12px; line-height: 12px; display: none;" value="点击查看明细" type="button" id="message" />
				</td>
				<td style="vertical-align: bottom" rowspan="2"><input name="unauditYears" id="weiShenNianShu" title="未审年数" class="readonly" style="border-top: #95B8E7 2px solid;" value="${unAudityearNum}" />
				</td>
				<td style="vertical-align: bottom" rowspan="2"><input name="supplementYear" title="补审年度" class="readonly" style="border-top: #95B8E7 2px solid;" value="${supplementYear}" /></td>

			</tr>
			<tr>

				<td><input type="text" class="text_short readonly" value="${entity.company.companyShouldTotal}" name="company.companyShouldTotal" id="yingAnPaiCanJiRen" title="应按排数" /></td>
				<td><input type="text" class="text_short readonly" value="${entity.company.companyAlreadyTotal}" name="company.companyAlreadyTotal" id="yiAnPaiCanJiRen" title="已按排数" /></td>
				<td><input id="yiLuRuCanJiRen" name="company.companyHandicapTotal" class="readonly" title="已录入残疾职工人数" value="${entity.company.companyHandicapTotal }" /></td>
				<td><input id="yuDingCanJiRen" name="company.companyPredictTotal" value="${entity.company.companyPredictTotal }" title="预定残疾职工人数" class="warn" onblur="initAudit.jisuan()" /></td>
				<td><input name="remainAmount" id="shangNianDuWeiJiaoBaoZhangJin" title="上年度未缴保障金额度" class="readonly" value="${entity.remainAmount}" /></td>
			</tr>

		</table>
		<!-- 年审企业表格  第四部分(保障金额度) -->
		<table class="company-examined" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td rowspan="2" width="100">保障金情况:</td>
				<td width="100">应缴金额:</td>
				<td><input id="yingJiaoJinE" type="text" name="amountPayable" class="readonly" value="${entity.amountPayable}" /></td>
				<td width="100">减缴金额:</td>
				<td width="100"><input id="jianJiaoJinE" type="text" class="readonly" name="reductionAmount" value="${entity.reductionAmount}" onblur="initAudit.jisuan()" /></td>
				<td width="99">免滞纳金:</td>
				<td><select disabled="disabled" id="mianZhiNaJin" style="font-size: 12px;" class="easyui-combobox" name="isDelayPay" data-options="width:100,panelHeight:80,height:30,editable:false">
						<option value="true" <c:if test="${entity.isDelayPay eq 'true'}">selected="selected"</c:if>>是</option>
						<option value="false" <c:if test="${entity.isDelayPay eq 'false'}">selected="selected"</c:if>>否</option>
				</select></td>

				<td width="91">是否免交:</td>
				<td><select disabled="disabled" id="mianJiao" style="font-size: 12px;"  class="easyui-combobox" name="isExempt" data-options="width:100,panelHeight:80,height:30,editable:false">
						<option value="true" <c:if test="${entity.isExempt eq 'true'}">selected="selected"</c:if>>是</option>
						<option value="false" <c:if test="${entity.isExempt eq 'false'}">selected="selected"</c:if>>否</option>
				</select></td>
			</tr>
			<tr>
				<td>实缴金额:</td>
				<td><input id="shiJiaoJinE" type="text" class="readonly" name="actualAmount" value="${entity.actualAmount}" />
				</td>
				<td>滞纳金:</td>
				<td><input id="zhiNaJin" type="text" class="readonly" name="delayPayAmount" value="${entity.delayPayAmount}" />
				</td>
				<td>实缴总金额:</td>
				<td colspan="1"><input id="shiJiaoZongJinE" type="text" class="readonly" name="payAmount" value="${entity.payAmount}" />
				</td>
				<td width="91">滞纳金天数:</td>
				<td><input name="delayDays" class="readonly" id="zhiNaJinTianShu" value="${entity.delayDays}" /></td>
			</tr>
		</table>

		<!-- 年审企业表格  第五部分(残疾人信息备注) -->
		<table class="company-examined" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100">备注:</td>
				<td colspan="6"><textarea name="remark" rows="2" cols="90">${entity.remark }</textarea></td>

			</tr>
		</table>
		<!-- 年审企业表格  第六部分(意见栏) -->
		<table class="company-examined" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100">初审意见:</td>
				<td colspan="3"><textarea name="initAuditComment" rows="3" cols="45">${entity.initAuditComment}</textarea></td>
				<td width="100">复审意见:</td>
				<td colspan="3"><textarea class="readonly" rows="3" cols="45">${entity.verifyAuditComment}</textarea></td>
			</tr>
			<tr>
				<td class="">初审日期:</td>
				<td><c:if test="${entity.initAuditDate==null}">
						<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="date" />
					</c:if> <c:if test="${entity.initAuditDate!=null}">
						<fmt:formatDate value="${entity.initAuditDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="date" />
					</c:if> <input class="readonly" value="${date}" /></td>
				<td width="100">初审人:</td>
				<td><input type="text" class="readonly" value="${entity.initAuditUser.userRealName}" /></td>
				<td class="">复核日期:</td>
				<td><fmt:formatDate value="${entity.verifyAuditDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="verifyAuditDate" /> <input type="text" class="bj_belu4 readonly"
					value="${verifyAuditDate}" /></td>
				<td width="100">复审人:</td>
				<td><input type="text" class="readonly" value="${entity.verifyAuditUser.userRealName}" /></td>
			</tr>
		</table>
	</div>
	<div style="text-align: center;margin-top: 10px;">
		<input name="id" type="hidden" value="${entity.id}" /> <input name="version" type="hidden" value="${entity.version}" /> <a href="javascript:initAudit.save();" class="easyui-linkbutton"
			iconCls="icon-save">保存</a> <a href="javascript:initAudit.audit();" class="easyui-linkbutton" iconCls="icon-ok">确认初审</a> <a href="javascript:initAudit.back();" class="easyui-linkbutton"
			iconCls="icon-back">返回</a>
	</div>

</form>

