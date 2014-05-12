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
	height: 28px;
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
	height: 28px;
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
	initAudit.show = function(w, q, b) {
		var t = "<table border='0' width='500' style='border:1px solid #5591E0;border-collapse:collapse;margin-left:15px;margin-top:5px;' >";
		var s = "";
		if (w.length > 0) {
			var c = "<span style='font-weight:bold;'>未审年度计算明细</span>(始终计算滞纳金)";
			s = c + t + "<tr style='border: 1px solid #6E6ED6;background-color:#E0ECFF'><th>未审年度</th><th>应缴金额</th><th>逾期天数</th><th>滞纳金比例</th><th>滞纳金</th><th>年度总金额</th></tr>";
			for ( var i = 0; i < w.length; i++) {
				var backgroundColor;
				if(i%2 == 0){
					backgroundColor = "style='background-color:#c9c7ff;'";
				}else{
					backgroundColor = "style='background-color:#d9daf0;'";
				}
				s = s + "<tr "+backgroundColor+"><td>" + w[i].year + "</td><td>" + w[i].money + "</td><td>" + w[i].days + "</td><td>" + w[i].prop + "</td><td>" + w[i].penalty + "</td><td>" + w[i].total + "</td></tr>";
			}
			s = s + "</table>";
		}
		if (b.length > 0) {
			var c = "<span style='font-weight:bold;'>未缴年度计算明细</span>(按当年审计状态计算滞纳金,可重审)";
			s = s + c + t + "<tr style='border: 1px solid #6E6ED6;background-color:#E0ECFF'><th>未缴年度</th><th>未缴金额</th><th>逾期天数</th><th>滞纳金比例</th><th>滞纳金</th><th>年度总金额</th></tr>";
			for ( var i = 0; i < b.length; i++) {
				var backgroundColor;
				if(i%2 == 0){
					backgroundColor = "style='background-color:#c9c7ff;'";
				}else{
					backgroundColor = "style='background-color:#d9daf0;'";
				}
				s = s + "<tr "+backgroundColor+"><td>" + b[i].year + "</td><td>" + b[i].money + "</td><td>" + b[i].days + "</td><td>" + b[i].prop + "</td><td>" + b[i].penalty + "</td><td>" + b[i].total + "</td></tr>";
			}
			s = s + "</table>";
		}
		if (q.length > 0) {
			var c = "<span style='font-weight:bold;'>欠缴年度计算明细</span>(始终计算滞纳金)";
			s = s + c + t + "<tr style='border: 1px solid #6E6ED6;background-color:#E0ECFF'><th>欠缴年度</th><th>欠缴金额</th><th>逾期天数</th><th>滞纳金比例</th><th>滞纳金</th><th>年度总金额</th></tr>";
			for ( var i = 0; i < q.length; i++) {
				var backgroundColor;
				if(i%2 == 0){
					backgroundColor = "style='background-color:#c9c7ff;'";
				}else{
					backgroundColor = "style='background-color:#d9daf0;'";
				}
				s = s + "<tr "+backgroundColor+"><td>" + q[i].year + "</td><td>" + q[i].money + "</td><td>" + q[i].days + "</td><td>" + q[i].prop + "</td><td>" + q[i].penalty + "</td><td>" + q[i].total + "</td></tr>";
			}
			s = s + "</table>";
		}
		$('#account').dialog({
			title : '补缴明细',
			width : 600,
			height : 500,
			closed : false,
			cache : false,
			content : s,
			modal : true,
		});
		
	};
	initAudit.initVerify = function() {
		$('#zaiZhiYuanGongZongShu').attr("maxlength", "9");
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
		//param.mianZhiNaJin = $('#mianZhiNaJin').combobox('getValue');
		param.mianZhiNaJin = $('#mianZhiNaJin').val();
		//param.mianJiao = $('#mianJiao').combobox('getValue');
		param.mianJiao = $('#mianJiao').val();
		param.shiJiaoZongJinE = $('#shiJiaoZongJinE').val();
		param.year = $('input[name=year]').val();
		param.companyId = $('#companyId').val();
	//	$.messager.alert('info','jisuan1');
	//	$.messager.alert('info','year: '+param.year+' companyId: '+param.companyId);
		$.ajax({
			url : '${contextPath}/security/audits/calculate',
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
				var wj = data.weiJiaoMingXi.length;
				if (wl != 0 || ql != 0 || wj != 0) {
					$('#message').css("display", "block");
					$('#message').bind("click", function() {
						initAudit.show(data.weiShenMingXi, data.qianJiaoMingXi, data.weiJiaoMingXi);
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
			dataType : "json",
			async : true
		});

	};
	initAudit.save = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data == true) {
				$.messager.alert('消息','审核信息添加成功,请继续添加残疾职工信息!');
			} else {
				esd.common.noCloseButtonDialog('消息','保存失败');
			}
		});
	};
	
	//获得审核信息, 并显示出来
	initAudit.getAuditInfo = function(companyId){
		$.ajax({
			url:'${contextPath}/security/audits/getInitAuditInfo',
			dataType:'json',
			data:{'companyId':companyId},
			success:function(data){
				//公司信息
				$('#auditCompanyId').val(data.company.id);
				$('#auCompanyCode').val(data.company.companyCode);
				$('#auCompanyName').val(data.company.companyName);
				$('#auCompanyTaxCode').val(data.company.companyTaxCode);
				$('#auCompanyLegal').val(data.company.companyLegal);
				$('#auCompanyOrganizationCode').val(data.company.companyOrganizationCode);
				$('#auCompanyProperty').append('<option value="'+data.company.companyProperty.id+'">'+data.company.companyProperty.companyProperty+'</option>');
				$('#auCompanyContactPerson').val(data.company.companyContactPerson);
				$('#auCompanyPhone').val(data.company.companyPhone);
				$('#auCompanyEconomyType').append('<option value="'+data.company.companyEconomyType.id+'">'+data.company.companyEconomyType.companyEconomyType+'</option>');
				$('#auCompanyMobile').val(data.company.companyMobile);
				$('#auCompanyAddress').val(data.company.companyAddress);
				$('#auCompanyBank').val(data.company.companyBank);
				$('#auCompanyBankAccount').val(data.company.companyBankAccount);
				$('#auCompanyZipCode').val(data.company.companyZipCode);
				//年龄超标
				$('#ageEx').val(data.ageEx);
				
				//审核信息
				//在职员工总数
				$('#zaiZhiYuanGongZongShu').val(data.audit.companyEmpTotal);
				//应安排人数
				$('#yingAnPaiCanJiRen').val(data.audit.companyShouldTotal);
				//已安排人数
				$('#yiAnPaiCanJiRen').val(data.audit.companyAlreadyTotal);
				//已录入人数
				$('#yiLuRuCanJiRen').val(data.audit.companyHandicapTotal);
				//预定人数
				$('#yuDingCanJiRen').val(data.audit.companyPredictTotal);
				//补缴金额
				$('#shangNianDuWeiJiaoBaoZhangJin').val(data.audit.remainAmount);
				//未审年度
				$('#unauditYears').val(data.audit.unauditYears);
				var unAuditYearList = '';
				if(data.audit.unauditYears == 0){
					unAuditYearList = '无';
				}else if(data.audit.unauditYears > 0){
					unAuditYearList = data.unAudityear;
				}
				$('#unAuditYearList').val(unAuditYearList);
				//补审年度
				$('#supplementYear').val(data.audit.supplementYear);
				//应缴金额
				$('#yingJiaoJinE').val(data.audit.amountPayable);
				//减缴金额
				$('#jianJiaoJinE').val(data.audit.reductionAmount);
				//实缴金额
				$('#shiJiaoJinE').val(data.audit.actualAmount);
				//滞纳金天数
				$('#zhiNaJinTianShu').val(data.audit.delayDays);
				//滞纳金
				$('#zhiNaJin').val(data.audit.delayPayAmount);
				//实缴总金额
				$('#shiJiaoZongJinE').val(data.audit.payAmount);
				//备注
				$('#auditRemark').val(data.audit.remark);
				$('#auditId').val(data.audit.id);
				$('#auditVersion').val(data.audit.version);
			}
		});
		return true;
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
/*		//初始化easyUi完成
		$.parser.onComplete = function() {
			
		};
		initAudit.jisuan();	*/
	});
/**	$('#auditTabs').tabs({
		onSelect : function(title,index) {
			if(index==0){
				if($('#worker_HandicapTotal').length>0){
					if($('#yiLuRuCanJiRen').val($('#worker_HandicapTotal').html()));
				};
			};
			initAudit.jisuan();
		}
	});	**/
</script>

<form id="form" action="${contextPath }/security/audits/saveAudit" method="post" style="margin-top: 5px; margin-left: 2px;">
	<!-- 年审企业表格  第二部分 -->
	<div style="border: #95B8E7 1px solid; width: 900px;">
		<table class="company-examined" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100">档案号码:</td>
				<td width="290" colspan="3"><input id="auCompanyCode" name="companyCode" class="bj_belu2 readonly" type="text" value="" /> 
				</td>
				<td>年审年度:</td>
				<td colspan="2"><input name="year" class="readonly" type="text" value="${year}" /></td>
				<td>未审年度:</td>
				<td colspan="2"><input type="text" id="unAuditYearList" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>企业名称:</td>
				<td colspan="3"><input readonly="readonly" id="auCompanyName" name="company.companyName" type="text" value="" /></td>

				<td style="width:100px;">税务代码:</td>
				<td width="180" colspan="2"><input readonly="readonly" id="auCompanyTaxCode" name="company.companyTaxCode" type="text" value="" /></td>
				<td width="100">年龄超标</td>
				<td colspan="2"><input type="text" id="ageEx" readonly="readonly" value="" /></td>
			</tr>

			<tr>
				<td rowspan="4">单位基本情况</td>
				<td width="100">法人代表:</td>
				<td width="199" colspan="2"><input readonly="readonly" id="auCompanyLegal" name="company.companyLegal" class="bj_belu3" type="text" value="" />
				</td>
				<td>组织机关代码证:</td>
				<td colspan="2"><input readonly="readonly" id="auCompanyOrganizationCode" name="company.companyOrganizationCode" class="bj_belu3" type="text" value="" /></td>
				<td>企业性质:</td>
				<td colspan="2">
					<select disabled="disabled" id="auCompanyProperty" style="font-size: 12px; width: 100px;height: 28px;"  name="company.companyProperty.id" data-options="width:106,height:30,editable:false">
					</select>
				</td>

			</tr>
			<tr>
				<td>联系人 :</td>
				<td colspan="2"><input readonly="readonly" id="auCompanyContactPerson" name="company.companyContactPerson" class="bj_belu3" type="text" value="" /></td>
				<td>电话号码:</td>
				<td colspan="2"><input readonly="readonly" id="auCompanyPhone" name="company.companyPhone" class="bj_belu3" type="text" value="" /></td>
				<td>经济类型:</td>
				<td colspan="2">
					<select disabled="disabled" id="auCompanyEconomyType" style="font-size: 12px;width: 100px;height: 28px;" class="" name="company.companyEconomyType.id" data-options="width:106,height:30,editable:false">
					</select>
				</td>

			</tr>
			<tr>
				<td>手机号码:</td>
				<td colspan="2"><input readonly="readonly" id="auCompanyMobile" name="company.companyMobile" class="bj_belu3" value="" /></td>
				<td>企业地址:</td>
				<td colspan="5"><input readonly="readonly" id="auCompanyAddress" name="company.companyAddress" value="" /></td>

			</tr>
			<tr>
				<td>开户银行:</td>
				<td colspan="2"><input readonly="readonly" id="auCompanyBank" name="company.companyBank" class="bj_belu3" value="" /></td>
				<td>银行账号:</td>
				<td colspan="2"><input readonly="readonly" id="auCompanyBankAccount" name="company.companyBankAccount" class="bj_belu3" value="" /></td>
				<td>邮政编码:</td>
				<td colspan="2"><input readonly="readonly" id="auCompanyZipCode" name="company.companyZipCode" value="" /></td>

			</tr>
			<!-- 年审企业表格  第三部分(残疾人信息) -->
			<tr>
				<td rowspan="3" width="100">在职员工情况:</td>
				<td width="100">职员工总数:</td>
				<td colspan="4" width="200">职残疾职工情况</td>
				<td width="98">补缴金额:</td>
				<td width="91">未审年数:</td>
				<td>补审年度:</td>
			</tr>
			<tr>
				<td rowspan="2" style="vertical-align: bottom"><input name="companyEmpTotal" title="在职员工总数" id="zaiZhiYuanGongZongShu" type="text" onpropertychange="initAudit.propertyChange()" value=""
					class="easyui-numberbox warn" style="border-top: #95B8E7 2px solid;" onblur="initAudit.jisuan()" data-options="min:0" /></td>
				<td width="97">应按排数:</td>
				<td width="100">已安排数:</td>
				<td width="100">已录入数</td>
				<td width="98">预定人数</td>
				<td><input style="display: none;" class="warn" value="点击查看明细" type="button" id="message" />
				</td>
				<td style="vertical-align: bottom" rowspan="2"><input id="unauditYears" name="unauditYears" id="weiShenNianShu" title="未审年数" class="readonly" style="border-top: #95B8E7 2px solid;" value="" />
				</td>
				<td style="vertical-align: bottom" rowspan="2"><input id="supplementYear" name="supplementYear" title="补审年度" class="readonly" style="border-top: #95B8E7 2px solid;" value="" /></td>

			</tr>
			<tr>

				<td><input type="text" class="text_short readonly" value="" name="companyShouldTotal" id="yingAnPaiCanJiRen" title="应按排数" /></td>
				<td><input type="text" class="text_short readonly" value="" name="companyAlreadyTotal" id="yiAnPaiCanJiRen" title="已按排数" /></td>
				<td><input id="yiLuRuCanJiRen" name="companyHandicapTotal" class="readonly" title="已录入残疾职工人数" value="" /></td>
				<td><input id="yuDingCanJiRen" name="companyPredictTotal" value="" title="预定残疾职工人数" class="easyui-numberbox warn" onblur="initAudit.jisuan()"
					data-options="min:0" /></td>
				<td><input name="remainAmount" id="shangNianDuWeiJiaoBaoZhangJin" title="补缴金额" class="readonly" value="" /></td>
			</tr>

			<!-- 年审企业表格  第四部分(保障金额度) -->
			<tr>
				<td width="100" rowspan="2">保障金情况:</td>
				<td width="100">应缴金额:</td>
				<td><input id="yingJiaoJinE" type="text" name="amountPayable" class="readonly" value="" /></td>
				<td width="100">减缴金额:</td>
				<td width="100"><input id="jianJiaoJinE" type="text" class="readonly" name="reductionAmount" value="" onblur="initAudit.jisuan()" /></td>
				<td width="99">免滞纳金:</td>
				<td>
					<select disabled="disabled" id="mianZhiNaJin" style="font-size: 12px; width: 100px;height: 28px;" class="" name="isDelayPay" data-options="width:100,panelHeight:80,height:30,editable:false">
						<option value="true" >是</option>
						<option value="false" selected="selected">否</option>
					</select>
				</td>

				<td width="91">是否免交:</td>
				<td>
					<select disabled="disabled" id="mianJiao" style="font-size: 12px; width: 100px;height: 28px;" class="" name="isExempt" data-options="width:100,panelHeight:80,height:30,editable:false">
						<option value="true" title="是" >是</option>
						<option value="false" title="否" selected="selected" >否</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>实缴金额:</td>
				<td><input id="shiJiaoJinE" type="text" class="readonly" name="actualAmount" value="" />
				</td>
				<td width="91">滞纳金天数:</td>
				<td><input name="delayDays" class="readonly" id="zhiNaJinTianShu" value="" /></td>
				<td>滞纳金:</td>
				<td><input id="zhiNaJin" type="text" class="readonly" name="delayPayAmount" value="" />
				</td>
				<td>实缴总金额:</td>
				<td colspan="1"><input id="shiJiaoZongJinE" type="text" class="readonly" name="payAmount" value="" />
				</td>

			</tr>
			<tr>
				<td width="100">备注:</td>
				<td colspan="10"><textarea id="auditRemark" name="remark" rows="2" style="height: 60px;" readonly="readonly"></textarea></td>
			</tr>
		</table>
	</div>
	<div style="text-align: center;margin-top: 10px;">
			<input id="auditCompanyId" name="company.id"type="hidden" value="" />
			<input id="auditId" name="id" type="hidden" value="" />
			<input id="auditVersion" name="version" type="hidden" value="" />
			<a href="javascript:initAudit.save();" class="easyui-linkbutton" iconCls="icon-save">保存</a>
			<a href="javascript:initAudit.back();" class="easyui-linkbutton" iconCls="icon-back">返回</a> 
	<!-- 		<a href="javascript:esd.common.printWindow();" class="easyui-linkbutton" iconCls="icon-print">打印</a>	 -->
	</div>

</form>

