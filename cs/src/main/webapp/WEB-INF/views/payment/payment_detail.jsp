<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style>

/*表格部分*/
#payment .company-examined {
	width: 886px;
	/*background-color: #6AB7DB;*/
	background-color: #E0ECFF;
	text-align: center;
	/*border: #95B8E7 1px solid;*/
}

#payment .td_short {
	width: 100px;
}

#payment .td_GD_short {
	width: 70px;
}

#payment .td_short_150 {
	width: 150px;
}

#payment .td_zhong {
	width: 200px;
}

#payment .text_short {
	width: 80px;
}

#payment .bj_belu {
	text-align: left;
}

#payment .bj_belu2 {
	text-align: left;
	width: 117px;
}

#payment .readonly {
	background: #d8d8d8;
	height: 30px;
}

#payment .warn {
	background-color: #D8D828;
}

#payment td {
	font-size: 12px;
}

#payment input {
	font-size: 12px;
	/*height: 18px;*/
	line-height: 28px;
	margin: 0px;
	width: 100%;
	border: 0px;
}

#payment textarea {
	font-size: 12px;
	line-height: 28px;
	width: 100%;
	border: 0px;
}

#payment td {
	border: #95B8E7 1px solid;
}

#payment .combo {
	border-style: none;
}
</style>
<script type="text/javascript">
	payment.save = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data == true) {
				$.messager.alert('消息', '保存成功', 'info', function() {
					$("#auditPanel").window("close");
					//$("#initAuditList_datagrid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', '保存失败', 'info');
			}
		});
	};
	payment.pay = function() {
		esd.common.syncPostSubmitEx("#form", "${contextPath }/security/payment/confirm", function(data) {
			if (data == true) {
				$.messager.alert('消息', '审批成功', 'info', function() {
					$("#auditPanel").window("close");
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
	payment.back = function() {
		esd.common.defaultOpenWindowClose();
	};

	$(function() {

	});
</script>
<div id="payment">
	<!-- 年审企业表格  第一部分 -->
	<table cellspacing="0" cellpadding="0" border="0" title="企业年审信息" class="company-examined">
		<tbody>
			<tr>
				<td class="td_short">档案号码:</td>
				<td class="bj_belu readonly" style="width: 372px;">${entity.company.companyCode}</td>
				<td class="td_short">税务代码:</td>
				<td class="td_long bj_belu readonly">${entity.company.companyTaxCode}</td>
			</tr>
			<tr>
				<td class="td_short">单位名称:</td>
				<td class="td_long bj_belu readonly">${entity.company.companyName}</td>
				<td class="td_short">年审年度:</td>
				<td class="td_long bj_belu readonly">${entity.company.year}</td>
			</tr>

		</tbody>
	</table>
	<!-- 年审企业表格  第三部分(残疾人信息) -->
	<table border="0" cellspacing="0" cellpadding="0" class="company-examined">
		<tr>
			<td rowspan="4" class="td_short">在职员工情况:</td>
			<td>在职员工总数:</td>
			<td>应按排数:</td>
			<td>已安排数:</td>
			<td>残疾职工总人数:</td>
		</tr>
		<tr class="bj_belu">
			<td class="bj_belu readonly">${entity.company.companyEmpTotal}</td>
			<td class="bj_belu readonly">${entity.company.companyShouldTotal}</td>
			<td class="bj_belu readonly">${entity.company.companyAlreadyTotal}</td>
			<td class="bj_belu readonly">${entity.company.companyHandicapTotal}</td>
		</tr>
	</table>
	<!-- 年审企业表格  第四部分(保障金额度) -->
	<table border="0" cellspacing="0" cellpadding="0" class="company-examined">
		<tr>
			<td class="td_short">应缴金额:</td>
			<td class="bj_belu2 readonly">${entity.amountPayable }</td>
			<td class="td_short">减缴金额:</td>
			<td class="bj_belu2 readonly">${entity.reductionAmount }</td>
			<td class="td_short">实缴金额:</td>
			<td class="bj_belu2 readonly" style="width: 340px;">${entity.actualAmount }</td>

		</tr>
		<tr>
			<td class="td_short">已缴金额:</td>
			<td class="bj_belu2 readonly"></td>
			<td class="td_short">余缴金额:</td>
			<td class="bj_belu2 readonly"></td>
			<td class="td_short">实缴金额大写:</td>
			<td class="bj_belu"><input class="bj_prohibit readonly" type="text" /></td>
		</tr>
	</table>
	<!-- 年审企业表格  第五部分(残疾人信息备注) -->
	<table border="0" cellspacing="0" cellpadding="0" class="company-examined">
		<tr>
			<td class="td_short">备注:</td>
			<td colspan="6"><textarea class="readonly" rows="3" cols="90" disabled="disabled">${entity.remark}</textarea></td>
		</tr>
	</table>
	<!-- 年审企业表格  第六部分(意见栏) -->
	<form id="form" action="${contextPath }/security/payment/confirm" method="post">
		<input name="paymentCompany.id" type="hidden" value="${entity.company.id}" /> <input name="auditId" type="hidden" value="${entity.id}" /> <input name="version" type="hidden" value="1" />
	</form>
</div>
<a href="#" onclick="find()">fff</a>
<table id="payment_tt"></table>

<div style="text-align: center;margin-top: 30px;">
<!-- 
	<a href="javascript:payment.save();" class="easyui-linkbutton" iconCls="icon-save">缴款</a>
	 <a href="javascript:payment.pay();" class="easyui-linkbutton" iconCls="icon-ok">缴款并返回</a>
	  -->
	  <a href="javascript:payment.back();" class="easyui-linkbutton" iconCls="icon-back">返回</a>
</div>
	<script>
	var products=[ 
		{
			productid : '1',
			name : '残联自收'
		}, {
			productid : '2',
			name : '地税代征'
		} ];
		$(function(){
			$('#payment_tt').datagrid({
			toolbar : [ {
				text : '新建缴款记录',
				iconCls : 'icon-add',
				handler : insert
			}],
				title:'缴款记录',
				iconCls:'icon-edit',
				width:'100%',
				height:250,
				singleSelect:true,
				idField:'id',
				url:'${contextPath }/security/payment/getPayments/${entity.id}',
				columns:[[
					{field:'id',title:'缴款编号',width:10},
					{field:'paymentDate',title:'缴款日期',width:100,editor:{type:'datebox',options:{height:32,required:true}}},
					{field:'paymentBill',title:'票据号',width:150,editor:'text'},
					{field:'paymentMoney',title:'缴款金额',width:120,align:'right',editor:{type:'numberbox',options:{precision:2,height:30,required:true}}},
					{field:'paymentType',title:'缴款方式',width:100,
						formatter:function(value){
							for(var i=0; i<products.length; i++){
								if (products[i].productid == value) return products[i].name;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								panelHeight:80,
								editable:false,
								height:32,
								valueField:'productid',
								textField:'name',
								data:products,
								required:true
								
							}
						}
					},
					{field:'paymentPerson',title:'缴款人',width:100,editor:'text'},
					{field:'remark',title:'备注',width:180,editor:'text'},
					{field:'action',title:'操作',width:100,align:'center',
						formatter:function(value,row,index){
							if (row.editing){
								var s = '<a href="#" onclick="saverow('+index+')">保存</a> ';
								var c = '<a href="#" onclick="cancelrow('+index+')">取消</a>';
								return s+c;
							} else {
								var e = '<a href="#" onclick="editrow('+index+')">编辑</a> ';
								var d = '<a href="#" onclick="deleterow('+index+')">删除</a>';
								return e+d;
							}
						}
					}
				]],
				onBeforeEdit:function(index,row){
					row.editing = true;
					updateActions();
				},
				onAfterEdit:function(index,row){
					row.editing = false;
					updateActions();
				},
				onCancelEdit:function(index,row){
					row.editing = false;
					updateActions();
				}
			});
		});
		function updateActions(){
			var rowcount = $('#payment_tt').datagrid('getRows').length;
			for(var i=0; i<rowcount; i++){
				$('#payment_tt').datagrid('updateRow',{
					index:i,
					row:{action:''}
				});
			}
		}
		function editrow(index){
			$('#payment_tt').datagrid('beginEdit', index);
		}
		function deleterow(index){
			$.messager.confirm('警告','是否要删除该缴款记录?',function(r){
				if (r){
					$('#payment_tt').datagrid('selectRow', index);
					var row = $('#payment_tt').datagrid('getSelected');
					$.ajax({
						url : '${contextPath }/security/payment/delPayment/'+row.id,
						type : 'post',
						success : function(data) {
							if(data==true){
									$('#payment_tt').datagrid('deleteRow', index);
									updateActions();
								}
						},
						error : function() {
							alert("请求错误");
						},
						dataType : "json",
						async : false
					});

				}
			});
		}
		function saverow(index){
			$('#payment_tt').datagrid('endEdit', index);
			$('#payment_tt').datagrid('selectRow', index);
			var row = $('#payment_tt').datagrid('getSelected');
					$.ajax({
						url : '${contextPath }/security/payment/save',
						type : 'post',
						data : row,
						success : function(data) {
							if (data == true) {
								$.messager.alert('消息', '保存成功', 'info');
							} else {
								$.messager.alert('消息', '保存失败,收款人必须是同一个人', 'info');
							}
						},
						error : function() {
							alert("请求错误");
						},
						dataType : "json",
						async : false
					});
			
		}
		function cancelrow(index){
			$('#payment_tt').datagrid('cancelEdit', index);
		}
		function insert(){
			var row = $('#payment_tt').datagrid('getSelected');
			if (row){
				var index = $('#payment_tt').datagrid('getRowIndex', row);
			} else {
				index = 0;
			}
			$('#payment_tt').datagrid('insertRow', {
				index: index,
				row:{
					'companyId':'${entity.company.id}',
					'auditId':'${entity.id}',
					'paymentPerson':'${sessionScope.userRealName}'
				}
			});
			$('#payment_tt').datagrid('selectRow',index);
			$('#payment_tt').datagrid('beginEdit',index);
		}
		function findCallBack(data){

			for(var i=0;i<data.length;i++){
				var row = $('#payment_tt').datagrid('getSelected');
				if (row){
					var index = $('#payment_tt').datagrid('getRowIndex', row);
				} else {
					index = 0;
				}
				$('#payment_tt').datagrid('insertRow', {
					index: index,
					row:{
						'paymentCompany.id':'1',
						'auditId':'2',
						'paymentPerson.id':'1'
					}
				});
				$('#payment_tt').datagrid('selectRow',index);
				$('#payment_tt').datagrid('beginEdit',index);
			}

		
		}
		function find(){
			$.ajax({
			url : '${contextPath }/security/payment/getPayments/${entity.id}',
			type : 'post',
			success : function(data) {
				findCallBack(data);
			},
			error : function() {
				alert("请求错误");
			},
			dataType : "json",
			async : false
		});
		}
	</script>

