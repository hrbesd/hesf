<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var workerList = {};
	/**
		初始化数据
	 **/
	workerList.init = function() {
		//残疾类型
		$('#workerFind_workerHandicapType').combobox({
			url : 'parameter/workerHandicapTypeService',
			valueField : 'id',
			textField : 'handicapType',
			editable : false
		});

		//残疾等级
		$('#workerFind_workerHandicapLevel').combobox({
			url : 'parameter/workerHandicapLevelService',
			valueField : 'id',
			textField : 'handicapLevel',
			editable : false
		});
		//性别
		$('#workerFind_workerGender').combobox({
			data : [ {
				label : '',
				value : '请选择'
			}, {
				label : '1',
				value : '男'
			}, {
				label : '0',
				value : '女'
			} ],
			valueField : 'label',
			textField : 'value',
			editable : false,
			height : 30,
			width : 76,
			panelHeight : 80
		});

	};

	/**	
		加载列表数据
	 **/
	workerList.loadData = function(params) {

		esd.common.datagrid("#workerList_dataGrid", "query/worker/company_temp_worker_list", "#workerListBoolbar", [ [
		{
			field : 'yuangongming',
			title : '姓名',
			width : 300,
			formatter : function(value,row,index){
				var head = row.workerName;
				var cadreMaleHeadImg = '<img src="${contextPath}/images/cadre_male.png" style="width:15px;height:20px;" title="干部"/>';
				var cadreFemaleHeadImg = '<img src="${contextPath}/images/cadre_female.png" style="width:15px;height:20px;" title="干部"/>';
				if(row.isCadre == true  && row.workerGender == '男'){
					head = cadreMaleHeadImg + head;
				}else if(row.isCadre == true  && row.workerGender == '女'){
					head = cadreFemaleHeadImg + head;
				}else{
					head = '&nbsp;&nbsp;&nbsp;' + head;
				}
				return head;
			},
			styler : function(value, row, index) {
				//年龄检测
				return workerList.ageDetection(value, row, index);
			}
		},{
			field : 'workerName',
			hidden : true
		},{
			field : 'workerHandicapCode',
			title : '残疾证号',
			width : 600,
			styler : function(value, row, index) {
				//年龄检测
				return workerList.ageDetection(value, row, index);
			}
		}, {
			field : 'workerGender',
			title : '性别',
			width : 250,
			align : 'center',
			styler : function(value, row, index) {
				//年龄检测
				return workerList.ageDetection(value, row, index);
			}
		}, {
			field : 'workerAge',
			title : '年龄',
			width : 250,
			align : 'center',
			styler : function(value, row, index) {
				//年龄检测
				return workerList.ageDetection(value, row, index);
			}
		}, {
			field : 'phone',
			title : '联系电话',
			width : 350,
			align : 'center',
			styler : function(value, row, index) {
				//年龄检测
				return workerList.ageDetection(value, row, index);
			}
		}, {
			field : 'workerHandicapType',
			title : '残疾类别',
			width : 250,
			align : 'center',
			styler : function(value, row, index) {
				//年龄检测
				return workerList.ageDetection(value, row, index);
			}
		}, {
			field : 'workerHandicapLevel',
			title : '残疾等级',
			width : 250,
			align : 'center',
			styler : function(value, row, index) {
				//年龄检测
				return workerList.ageDetection(value, row, index);
			}
		}, {
			field : 'isCadre',
			hidden : true
		},{
			field : 'yanz',
			title : '操作',
			width : 270,
			align : 'center',
			formatter : function(value, row, index) {
				var e = '<a href="#" onclick="workerList.openEditWorker(' + row.id + ')">编辑</a> ';
				var d = '<a href="#" onclick="workerList.deleteWorker(' + row.id + ',0)">删除</a>';
				return e + d;
			},
			styler : function(value, row, index) {
				//年龄检测
				return workerList.ageDetection(value, row, index);
			}
		} ] ], params,function(){
		//从新获企业信息
	//	workerList.getCompany();
		});
	};

	/**
	 职工年龄检测
	 **/
	workerList.ageDetection = function(value, row, index) {
		if(row.isCadre){
			if (row.workerGender == '男') {
				if (row.workerAge >= $('#retireAgeCadreMale').val()) {
					return 'background-color:red;font-weight: bold;';
				}
			}
			if (row.workerGender == '女') {
				if (row.workerAge >= $('#retireAgeCadreFemale').val()) {
					return 'background-color:red;font-weight: bold;';
				}
			}
		}else{
			if (row.workerGender == '男') {
				if (row.workerAge >= $('#retireAgeMale').val()) {
					return 'background-color:red;font-weight: bold;';
				}
			}
			if (row.workerGender == '女') {
				if (row.workerAge >= $('#retireAgeFemale').val()) {
					return 'background-color:red;font-weight: bold;';
				}
			}
		}
	};

	/**
		组装查询参数
	 **/
	workerList.getParams = function() {

		params = {};
		params.year = $("#year").val();//年份
		params.companyId = $("#companyId").val();//单位id
		params.companyCode = $('#companyCode').val(); //档案号
		params.workerName = $("#workerFind_workerName").val(); // //员工名
		params.workerHandicapCode = $("#workerFind_workerHandicapCode").val();// 残疾证号
		params.workerGender = $("#workerFind_workerGender").combobox("getValue");// 性别
		params.workerAge_1 = $("#workerFind_workerAge1").val();// 年龄
		params.workerAge_2 = $("#workerFind_workerAge2").val();// 年龄
		params.phone = $("#workerFind_phone").val();// 电话号
		params.workerHandicapType = $("#workerFind_workerHandicapType").combobox("getValue");//残疾类型 
		params.workerHandicapLevel = $("#workerFind_workerHandicapLevel").combobox("getValue");// 残疾等级
		//是否显示年龄超标职工
	 	var isExceed=$("input[name='isExceed']:checked").val();
	  	if(isExceed=='on'){
	  		params.isExceed=true;
	  	}else{
	  		params.isExceed=false;
	  		
	  	}
	  	//是否是干部
	  	var isCadre=$("input[name='isCadre']:checked").val();
	  	if(isCadre=='on'){
	  		params.isCadre=true;
	  	}
		return params;
	};

	/**
	 查询数据 并校验所有输入框
	 **/
	workerList.findData = function() {
		if (esd.common.validatebox()) {
			//重新根据参数加载数据
			$('#workerList_dataGrid').datagrid('load', workerList.getParams());
		}
			//刷新已经补充的人数
			workerList.getfillNumber();
	};

	/**
	打开增加残疾职工页面
	 **/
	workerList.openAddWorker = function() {
		var predictNum = $('#companyPredictTotal').html();
		var fillNumber = $('#fillNumber').html();
		if(fillNumber >= predictNum){
			$.messager.alert('提示','补充的人数已经达到预定人数了, 不可以继续添加.','info');
			return;
		}
		esd.common.openWindow("#workerWindow", "增加残疾职工", 860, 450, 'audits/repealPredictAdd/' + $("#companyId").val()+'/'+$('#year').val());
	};

	/**
	打开编辑残疾职工页面
	 **/
	workerList.openEditWorker = function(id) {
		esd.common.openWindow("#workerWindow", "编辑残疾职工", 860, 450, 'worker/editWorkerTemp/' + id);
	};
	
	/**
	删除残疾员工
	 **/
	workerList.deleteWorker = function(id, type) {
		var params = new Array();
		var len = 0;
		//删除单条
		if (type == 0) {
			params.push(id);
			len=1;
		}
		//删除多条
		if (type == 1) {
			// 获取所有选中列
			var selection = $("#workerList_dataGrid").datagrid('getChecked');
			len = selection.length;
			// 判断选择数目是否大于0
			if (selection.length == 0) {
				$.messager.alert('消息', '未选择任何数据。', 'info');
				return;
			} else {
				// 组装参数
				for ( var i = 0; i < selection.length; i++) {
					params.push(selection[i].id);
				}
			}
		}
		// 显示确认删除对话框
		$.messager.confirm('确认', '您确认想要删除' + len + '记录吗？', function(r) {
			if (r) {
				// 删除请求
				$.ajax({
					url : 'worker/deleteWorkerTemp',
					data : {
						"params" : params,
					},
					type : 'post',
					success : function(data) {
						if (data == true) {
							// 刷新数据列表
							$("#workerList_dataGrid").datagrid('clearChecked');
							$('#workerList_dataGrid').datagrid('reload');
							//刷新已经补充的人数
							workerList.getfillNumber();
						} else {
							$.messager.alert('消息', '残疾职工删除失败。', 'error');
						}
					},
					error : function() {
							$.messager.alert('消息', '删除残疾职工信息时发生错误。', 'error');
					}
				});
			}
		});
	};

	/**
	 *	重新获得企业信息
	 */
	workerList.getCompany = function() {

		$.ajax({
			url : 'company/getinformation/' + $("#companyId").val()+'/'+$("#year").val(),
			type : 'post',
			success : function(data) {
				if (data.length > 0) {
					$("#company_name").html(data[0].companyName);//企业名字
					$("#company_EmpTotal").html(data[0].companyEmpTotal);//总人数
					$("#company_Code").html(data[0].companyCode);// 档案编码
					$("#worker_HandicapTotal").html(data[0].workerHandicapTotal);// 已录用残疾职工总人数
					$("#company_TaxCode").html(data[0].companyTaxCode);// 税务编码
					$("#company_Area").html(data[0].companyArea);// 地区
				} else {
					$.messager.alert('消息', '未获得到企业信息数据。', 'error');
				}

			},
			error : function() {
			$.messager.alert('消息', '残疾职工列表，获取企业信息错误。', 'error');
			
			}
		});

	};

	/**
	 * 查询已经补充上了多少个残疾人
	 **/
	workerList.getfillNumber = function(){
		$.ajax({
			url : 'query/worker/getfillNumber/' + $("#companyId").val()+'/'+$('#year').val(),
			type : 'post',
			success : function(data) {
				var fillNumber = data;
				$('#fillNumber').html(fillNumber);
				//校验 补充人数和 预定人数
				workerList.verifyFillNumber(fillNumber);
			},
			error : function() {
				$.messager.alert('消息', '残疾职工列表，获取企业信息错误。', 'error');
			}
		});
	};
	
	/**
	 * 校验 补充人数和预定人数
	 **/
	 workerList.verifyFillNumber = function(fillNumber){
		//预定人数
		var companyPredictTotal = $('#companyPredictTotal').html();
	//	$.messager.alert('companyPredictTotal:'+companyPredictTotal);
		// fillNumber 补充的人数
		if(fillNumber > companyPredictTotal){
			$('#fillNumber').css('background-color','red');
		//	$.messager.alert('提示','');
			return false;
		}else{
			$('#fillNumber').css('background-color','');
			return true;
		}
	};
	
	/**
	 * 保存本页信息, 包括残疾人和新的备注信息
	 **/
	workerList.saveAllInfo = function(){
		//校验 补充人数和预定人数
		
		if(!workerList.verifyFillNumber($('#fillNumber').html())){
			$.messager.alert('提示','补充的残疾职工经换算后的人数不可以 > 预定人数, 请检查添加的残疾职工是否有误.','info');
			return;
		}
		// 显示确认删除对话框
		$.messager.confirm('提示', '确定保存吗?', function(r) {
			if (r) {
				var remark = $('#remark').val(); //审核信息的备注
				var auditId = $('#auditId').val();	//审核id
				var companyId = $('#companyId').val();	//公司id
				var fillNumber = $('#fillNumber').html();	//实际补充的人数(经过审核参数的比例换算后)
				// ajax请求
				$.ajax({
					url : 'worker/repealPredictAdd',
					type : 'post',
					data : {
						'fillNumber':fillNumber,
						'auditId':auditId,
						'companyId':companyId,
						'remark':remark
					},
					success : function(data) {
						if (data.notice == 'success') {
							$.messager.alert('提示','保存成功','info');
							// 刷新数据列表
							$("#initAuditList_datagrid").datagrid('clearChecked');
							$('#initAuditList_datagrid').datagrid('reload');
							//关闭本窗口
							workerList.close();
						} else {
							$.messager.alert('消息', data.notice, 'error');
						}
					},
					error : function() {
							$.messager.alert('消息', '删除残疾职工信息时发生错误。', 'error');
					}
				});
			}
		});
	};
	
	/**
	 * 关闭窗口
	 **/
	workerList.close = function(){
	//	var fillNumber =  $('#fillNumber').html();
	//	$.messager.alert('fillNumber:'+fillNumber);
	//	workerList.verifyFillNumber(fillNumber);
	//	return ;
		esd.common.defaultOpenWindowClose();
	};
	 
	 
	//组件解析完成
	$.parser.onComplete = function() {
		workerList.init();
		workerList.loadData(workerList.getParams());
		
	};
	$(function(){
	    //年龄超标复选框
	/*	$("#isExceed").bind("click",function(){
			//清楚列表复选框
			$("#workerList_dataGrid").datagrid("clearChecked");
			workerList.loadData(workerList.getParams());
		});	*/
		//是否干部复选框
		$("#isCadre").bind("click",function(){
			//清楚列表复选框
			$("#workerList_dataGrid").datagrid("clearChecked");
			workerList.loadData(workerList.getParams());
		});
	});


</script>
<!-- 数据表格 -->
<table id="workerList_dataGrid"></table>

<!-- 自定义菜单 -->
<div id="workerListBoolbar">

	<table class="workerListTip" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tipTextEx">审核年度:
				<input type="hidden" id="year" value="${year}" />
				<input type="hidden" id="companyId" value="${companyId}" />
				<input type="hidden" id="companyCode" value="${companyCode}" />
				<input type="hidden" id="auditId" value="${auditId}" />
			</td>
			<td class="readonly" style="width:90px;">${year }</td>
			<td class="tipTextEx">公司名称:</td>
			<td class="readonly" colspan="3">${companyName }</td>
			<td class="tipTextEx">档案号码:</td>
			<td class="readonly">${companyCode }</td>
			
		</tr>
		<tr>
			<td class="tipTextEx">企业总人数:</td>
			<td id="companyEmpTotal" class="readonly">${companyEmpTotal }</td>
			<td class="tipTextEx">应安排数:</td>
			<td id="companyShouldTotal" class="readonly">${companyShouldTotal }</td>
			<td class="tipTextEx">预定人数:</td>
			<td id="companyPredictTotal" style="width:135px" class="readonly">${companyPredictTotal }</td>
			<td class="tipTextEx">补充人数:</td>
			<td id="fillNumber" class="readonly">
				0
			</td>
		</tr>
		<tr>
			<td class="tipTextEx">备注:</td>
			<td colspan="6">
				<textarea id="remark" style="margin-left: 0px; margin-right: 0px; width: 630px;">${remark }</textarea>
			</td>
			<td style="text-align:center;">
				<input type="button" onclick="workerList.saveAllInfo()" value="保存" />&nbsp;&nbsp;
				<input type="button" onclick="workerList.close()" value="取消" />
			</td>
		</tr>
	</table>

		<div style="text-align: right; margin: 7px 7px 7px 48px;float: left;">
		<table>
			<tr>
				<td>
				<!-- 	<input type="checkbox" name="isExceed"  id="isExceed" />	 -->
					<!--  女退休年龄 -->
					<input type="hidden" value="${retireAgeFemale}"  id="retireAgeFemale"/>
					<!--  女干部退休年龄 -->
					<input type="hidden" value="${retireAgeCadreFemale }" id="retireAgeCadreFemale" />
					<!--  男退休年龄 -->
					<input type="hidden" value="${retireAgeMale}" id="retireAgeMale"/>
					<!--  女干部退休年龄 -->
					<input type="hidden" value="${retireAgeCadreMale }" id="retireAgeCadreMale" />
				</td>
			<!-- 	<td id="isExceedText">
					<span style="font-size:10px;">年龄超标</span>
				</td>	 -->
				<td>
					<input type="checkbox" name="isCadre"  id="isCadre" />
				</td>
				<td id="isExceedText">
					<span style="font-size:10px;">干部</span>
				</td>
			</tr>
		</table>
			
		</div>
	
	<div style="text-align: right; margin: 7px;">
		<a href="javascript:workerList.openAddWorker();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">增加</a> 
		<a href="javascript:workerList.deleteWorker('',1);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">删除</a> 
	</div>
	<table style="clear: both;">
		<tr>
			<td>
				<!-- 姓名 --> <input id="workerFind_workerName" type="text" style="width: 100px;margin-left: 48px" />
			</td>
			<td>
				<!-- 残疾证号 --> <input id="workerFind_workerHandicapCode" type="text" style="width: 193px" />
			</td>
			<td>
				<!-- 性别 --> <input id="workerFind_workerGender" type="text" />
			</td>
			<td>
				<!-- 年龄 --> <input id="workerFind_workerAge1" type="text" style="width: 34px" data-options="validType:['_number']" class="easyui-validatebox" />- <input id="workerFind_workerAge2" type="text"
				style="width: 34px" data-options="validType:['_number']" class="easyui-validatebox" />
			</td>
			<td>
				<!-- 电话 --> <input id="workerFind_phone" type="text" style="width:109px" data-options="validType:['_number']" class="easyui-validatebox" />
			</td>
			<td>
				<!-- 残疾类型 --> <input style="width:80px" type="text" id="workerFind_workerHandicapType" data-options="height:30,panelHeight:240" class="easyui-combobox" />
			</td>
			<td>
				<!-- 残疾等级 --> 
				<input style="width:80px" type="text" id="workerFind_workerHandicapLevel" data-options="height:30,panelHeight:145" class="easyui-combobox" />
				 <a href="#" onclick="workerList.findData()" class="easyui-linkbutton"  iconCls="icon-search">查询</a>
			
			</td>
		</tr>
	</table>

</div>


