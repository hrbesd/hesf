<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	var queryPayment = {};
	// 档案号缓存数据
	var tempCompanyCode;
	// 公司名称缓存数据
	var tempCompanyName;
	// 开始时间缓存数据
	var tempStartDate;
	// 结束时间缓存数据
	var tempEndDate;
	
	/**
		初始化数据
	 **/
	queryPayment.init = function() {
		//企业性质
		$('#companyProperty').combobox({
			url : 'parameter/property',
			valueField : 'id',
			textField : 'companyProperty',
			onSelect:function(){
				queryPayment.findData();
			}
		});

		//经济类型
		$('#companyEconomyType').combobox({
			url : 'parameter/economytype',
			valueField : 'id',
			textField : 'companyEconomyType',
			onSelect:function(){
				queryPayment.findData();
			}
		});
		//地区
		$('#area').combobox({
			url : 'parameter/getArea',
			valueField : 'code',
			textField : 'name',
			onSelect:function(){
				queryPayment.findData();
			}
		});

		//年份
		$('#year').combobox({
			url : 'parameter/getyears',
			valueField : 'id',
			textField : 'text',
			onSelect:function(){
				queryPayment.findData();
			}
		});

		//缴款人
		$('#paymentPerson').combobox({
			url : 'parameter/getPayer',
			valueField : 'id',
			textField : 'userRealName',
			onSelect:function(){
				queryPayment.findData();
			}
		});
		
		//初始化输入元素默认值
		var element = $(".inputElement");
		for ( var i = 0; i < element.length; i++) {
			$(element[i]).val("");
		}
		
		//已返票 和 是否作废票据  添加选中事件
		$('#billReturn').click(function(){
			queryPayment.findData();
		});
		$('#billObsolete').click(function(){
			queryPayment.findData();
		});
		
		//档案编码, 企业名称焦点离开事件
		$('#companyCode').blur(function(){
			//如果为空, 不触发查询事件
			var companyCode = $('#companyCode').val();
			if(companyCode == tempCompanyCode){
				return;
			}
			tempCompanyCode = companyCode;
			queryPayment.findData();
		});
		$('#companyName').blur(function(){
			//如果为空, 不触发查询事件
			var companyName = $('#companyName').val();
			if(companyName == tempCompanyName){
				return;
			}
			tempCompanyName = companyName;
			queryPayment.findData();
		});
		
		//档案编码, 企业名称回车事件
		$('#companyCode').keydown(function(event){
			if(event.keyCode == 13){
				//如果为空, 不触发查询事件
				var companyCode = $('#companyCode').val();
				if(companyCode == tempCompanyCode){
					return;
				}
				tempCompanyCode = companyCode;
				queryPayment.findData();
			}
		});
		$('#companyName').keydown(function(event){
			if(event.keyCode == 13){
				//如果为空, 不触发查询事件
				var companyName = $('#companyName').val();
				if(companyName == tempCompanyName){
					return;
				}
				tempCompanyName = companyName;
				queryPayment.findData();
			}
		});
		
		//时间选择框 选择后事件
		$('#startDate').datebox({
			onSelect:function(date){
				queryPayment.findData();
			}
		});
		$('#endDate').datebox({
			onSelect:function(date){
				queryPayment.findData();
			}
		});
		
		//时间选择框清空
		$('#startDate').datebox('setValue','');
		$('#endDate').datebox('setValue','');
		
	};
	queryPayment.openPayment = function(index) {
		esd.common.defaultOpenWindowEx("查看缴款信息", 920, 600, "${contextPath}/security/audits/edit/" + index + "/2");
	};
	/*
	 * 获取缴款基本档案函数
	 */
	queryPayment.loadData = function() {
		var params = queryPayment.getParams();
		esd.common.datagrid("#queryPaymentGrid", "query/payment/list", "#queryPaymentBoolbar", [ [ {
			field : 'paymentDate',
			title : '缴款时间',
			width : 120
		}, {
			field : 'companyCode',
			title : '公司档案号',
			width : 120
		},{
			field : 'companyName',
			title : '缴款公司',
			width : 300
		}, {
			field : 'paymentBill',
			title : '缴款票号',
			width : 180
		},{
			field : 'paymentMoney',
			title : '缴款金额',
			width : 110,
			align : 'right'
		}, {
			field : 'paymentPerson',
			title : '缴款操作人',
			width : 120
		}, {
			field : 'paymentType',
			title : '缴款方式',
			width : 150
		}, {
			field : 'paymentExceptional',
			title : '缴款方式',
			width : 100
		}
	/*	{
			field : 'action',
			title : '操作',
			width : 100,
			align : 'center',
			formatter : function(value, row, index) {
				var v = '<a href="#" onclick="queryPayment.openPayment(' + row.id + ')">查看</a>';
				return v;
			}
		}	*/
	] ], params);

	};

	/**
		组装查询参数
	 **/
	queryPayment.getParams = function() {
		var params = {};
		params.year = $("#year").combobox("getValue"); // 年度
		params.companyCode = $("#companyCode").val(); // 档案号码
		params.companyName = $("#companyName").val(); // 企业名称
		params.companyProperty = $("#companyProperty").combobox("getValue");// 公司性质 _
		params.companyEconomyType = $("#companyEconomyType").combobox("getValue");// 企业经济类型
		params.area = $("#area").combobox("getValue");// 公司所属地区
		params.paymentPerson = $("#paymentPerson").combobox("getValue");// 缴款人
		params.startDate = $('#startDate').combobox("getValue");	//缴款起始时间
		params.endDate = $('#endDate').combobox("getValue");	//缴款结束时间
		params.belongsType = $('input[name="belongsType"]:checked').val();
		var billReturn = $('#billReturn').attr('checked');
		if(billReturn == 'checked'){
			params.billReturn = true;
		}
		var billObsolete = $('#billObsolete').attr('checked');
		if(billObsolete == 'checked'){
			params.billObsolete = true;
		}
		params.minPaymentMoney = $('#minPaymentMoney').val();	//最小缴款额
		params.maxPaymentMoney = $('#maxPaymentMoney').val();	//最大缴款额
		return params;
	};

	/**
	 查询数据 并校验所有输入框
	 **/
	queryPayment.findData = function() {
		if (esd.common.validatebox("#queryPaymentParams")) {
			//重新根据参数加载数据
			$('#queryPaymentGrid').datagrid('load', queryPayment.getParams());
		}
		;
	};
	/**
	查看企业信息框
	 **/
	queryPayment.openViewPayment = function(id) {
		esd.common.defaultOpenWindow("查看缴款信息", 'company/view/' + id);
	};
	
	/**
	 * 导出选中企业
	 **/
	 queryPayment.downloadSelected = function() {
		// 获取所有选中列
		var selection = $("#queryPaymentGrid").datagrid('getChecked');
	//	$.messager.alert(selection.length,selection[0].id);
		// 判断选择数目是否大于0
		if (selection.length == 0) {
			$.messager.alert('消息', '未选择任何数据。', 'error');
		} else {
			// 显示确认删除对话框
			$.messager.confirm('确认', '您确认想要导出' + selection.length + '记录吗？', function(r) {
				if (r) {
					// 组装参数
					var ids = new Array();
					for ( var i = 0; i < selection.length; i++) {
						ids.push(selection[i].id);
					}
					//发送导出请求
					$.ajax({
						url:'query/payment/export',
						type:'post',
						traditional:true,
						data: {
							'idArray' : ids,
							'isDownLoadAll' : 'no'
						},
						success:function(data){
							if(data!="null"){
								window.location.href=data;
							}else{
								$.messager.alert('消息', '缴款信息导出错误。', 'error');
							}
						},error:function(){
							$.messager.alert('消息', '请求缴款信息数据时出现错误。', 'error');
						}
					});
				}
			});
		}
	};
	
	/**
	 * 导出所有数据
	 **/
	 queryPayment.downloadAll = function() {
		$.messager.confirm('确认', '您确认想要导出所有记录吗？', function(r) {
			if (r) {
				var params = queryPayment.getParams();
				params.isDownLoadAll = 'yes';
				//发送导出请求
				$.ajax({
					url:'query/payment/export',
					type:'post',
					data: {
						'year' : params.year,	//审核年度
						'companyCode' : params.companyCode,	//档案号码
						'companyName' : params.companyName, //企业名称
						'companyProperty' : params.companyProperty,	//公司性质
						'companyEconomyType' : params.companyEconomyType,	// 企业经济类型
						'area' : params.area,	//公司所属地区
						'paymentPerson' : params.paymentPerson,	//缴款人
						'startDate' : params.startDate, 	//缴款起始时间
						'endDate' : params.endDate,	//缴款结束时间
						'belongsType' : params.belongsType,	//所属类型
						'billReturn' : params.billReturn,	//是否返票
						'billObsolete' : params.billObsolete,	//是否作废票
						'minPaymentMoney' : params.minPaymentMoney,	//最小金额
						'maxPaymentMoney' : params.maxPaymentMoney,	//最大金额
						'isDownLoadAll' : params.isDownLoadAll 	//是否下载全部
					},
					success:function(data){
						if(data!="null"){
							window.location.href=data;
						}else{
							$.messager.alert('消息', '缴款信息导出错误。', 'error');
						}
					},error:function(){
						$.messager.alert('消息', '请求缴款信息数据时出现错误。', 'error');
					}
				});
			}
		});
	};
	
	//组件解析完成
	$.parser.onComplete = function() {
		//加载缴款档案数据
		queryPayment.loadData();
	};
	$(function() {
		queryPayment.init();

	});
</script>



<!-- 数据表格 -->
<table id="queryPaymentGrid"></table>

<!-- 自定义菜单 -->
<div id="queryPaymentBoolbar">
	<div class="paramsTab">
		<table id="queryPaymentParams">
			<tr>
				<td class="tipsText">审核年度:</td>
				<td><input id="year" class="easyui-combobox" value="${nowYear }" data-options="height:30,editable:false" /></td>
				<td class="tipsText">档案号码:</td>
				<td><input type="text" id="companyCode" class="inputElement" /></td>
				<td class="tipsText">企业名称:</td>
				<td colspan="3"><input class="longtext inputElement" id="companyName" style="width:426px" /></td>

			</tr>
			<tr>
				<td class="tipsText">企业性质:</td>
				<td><input id="companyProperty" class="easyui-combobox" data-options="height:30,editable:false" /></td>
				<td class="tipsText">经济类型:</td>
				<td><input id="companyEconomyType" class="easyui-combobox" data-options="height:30,editable:false" /></td>
				<td class="tipsText">地区:</td>
				<td><input id="area" class="easyui-combobox" data-options="height:30,editable:false" value="10230000" /></td>
				<td class="tipsText">缴款操作人:</td>
				<td><input type="text" id="paymentPerson" class="easyui-combobox" data-options="height:30,editable:false" />
				</td>
			</tr>
			<tr>
				<td class="tipsText">缴款时间:</td>
				<td colspan="2"><input class="easyui-datebox" name="startDate" id="startDate" data-options="height:30,showSeconds:false" style="width:100px" />-
					<input class="easyui-datebox" name="endDate" id="endDate" data-options="height:30,showSeconds:false" style="width:100px" />
				</td>
				<td colspan="2" style="text-align:left;font-size:13px;">
					<div style="width:70%;border:2px rgb(174, 179, 243) solid;height: 22px;padding: 2px 5px;text-align: center;border-radius: 5px;">
						省残指 : <input type="radio" onclick="queryPayment.loadData()" name="belongsType" checked="checked" value="nonSix" style="height:auto;margin-right:20px;"/>
						地税 : <input type="radio" onclick="queryPayment.loadData()" name="belongsType" value="six" style="height:auto" />
					</div>
				</td>
				<td class="tipsText" style="text-align:left;font-size:13px;">
				已返票 : <input id="billReturn" type="checkbox" style="height:auto; "/>
				作废票 : <input id="billObsolete" type="checkbox" style="height:auto" />
				</td>
				<td class="tipsText">缴款金额:</td>
				<td><input type="text" style="width: 60px"
					id="minPaymentMoney" data-options="validType:['_number']"
					class="easyui-validatebox inputElement" />~<input type="text"
					style="width: 60px" id="maxPaymentMoney"
					data-options="validType:['_number']"
					class="easyui-validatebox inputElement" />元</td>
			</tr>
		</table>
		<div class="findBut">
			<a href="#" onclick="queryPayment.findData()" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			<a href="javascript:queryPayment.init()" class="easyui-linkbutton" iconCls="icon-redo">重置</a>
			<a href="javascript:queryPayment.downloadSelected()" class="easyui-linkbutton" iconCls="icon-ok">下载选中</a>
			<a href="javascript:queryPayment.downloadAll()" class="easyui-linkbutton" iconCls="icon-ok">下载所有数据</a>

		</div>
	</div>
</div>

<div id="viewCompanyPanel"></div>
