<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />





<div id="workerPage">

	<!-- 数据表格 -->
	<input type="hidden" id="companyId" value="${companyId}" /> <input type="hidden" id="year" value="${year}" />
	<!-- 自定义菜单 -->
	<div id="workerListBoolbar">
		<div style="text-align: right;">
			<a href="javascript:workerList.openAddWorker();" class="easyui-linkbutton" iconCls="icon-ok">添加</a> <a href="javascript:workerList.deleteWorker('',1);" class="easyui-linkbutton" iconCls="icon-ok">删除</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok">导入文件</a>
		</div>
		<table>
			<tr>
				<td>企业名称:</td>
				<td id="company_name"></td>
				<td>档案编码:</td>
				<td id="company_Code"></td>

				<td>税务编码:</td>
				<td id="company_TaxCode"></td>
			</tr>
			<tr>
				<td>企业总人数:</td>
				<td id="company_EmpTotal"></td>
				<td>已录用残疾职工人数:</td>
				<td id="worker_HandicapTotal"></td>

				<td>地区:</td>
				<td id="company_Area"></td>

			</tr>
		</table>
		<div>
			<!-- 姓名 -->
			<input id="workerFind_workerName" type="text" style="width: 105px;margin-left: 52px" />
			<!-- 残疾证号 -->
			<input id="workerFind_workerHandicapCode" type="text" style="width: 213px" />
			<!-- 性别 -->
			<input id="workerFind_workerGender" type="text" />
			<!-- 年龄 -->
			<input id="workerFind_workerAge1" type="text"  style="width: 34px" data-options="validType:['_number']" class="easyui-validatebox" />- <input id="workerFind_workerAge2" type="text" style="width: 4%"
				data-options="validType:['_number']" class="easyui-validatebox" />
			<!-- 电话 -->
			<input id="workerFind_phone" type="text" style="width:125px" data-options="validType:['_number']" class="easyui-validatebox" />
			<!-- 残疾类型 -->
			<input style="width:85px" type="text" id="workerFind_workerHandicapType" data-options="height:30,panelHeight:240" class="easyui-combobox" />
			<!-- 残疾等级 -->
			<input style="width:85px" type="text" id="workerFind_workerHandicapLevel" data-options="height:30,panelHeight:150" class="easyui-combobox" /> <a href="javascript:workerList.findData();"
				class="easyui-linkbutton" iconCls="icon-search">查找</a>
		</div>
	</div>

<!-- 增加残疾人信息窗口 -->
		<div id="workerPanel"></div>

</div>
<h2>查看参数</h2>
		
		
<table id="workerList_dataGrid"></table>
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
		});

		//残疾等级
		$('#workerFind_workerHandicapLevel').combobox({
			url : 'parameter/workerHandicapLevelService',
			valueField : 'id',
			textField : 'handicapLevel'
		});
		//性别
		$('#workerFind_workerGender').combobox({
			data : [ {
				label : '',
				value : '请选择'
			}, {
				label : '0',
				value : '男'
			}, {
				label : '1',
				value : '女'
			} ],
			valueField : 'label',
			textField : 'value',
			height : 30,
			width : 86
		});

	};

	/**	
		加载列表数据
	 **/
	workerList.loadData = function(params) {

		esd.common.datagrid("#workerList_dataGrid", "query/worker/company_worker_list", "#workerListBoolbar", [ [ {
			field : 'workerName',
			title : '姓名',
			width : 300
		}, {
			field : 'workerHandicapCode',
			title : '残疾证号',
			width : 600
		}, {
			field : 'workerGender',
			title : '性别',
			width : 250,
			align : 'center'
		}, {
			field : 'workerAge',
			title : '年龄',
			width : 250,
			align : 'center'
		}, {
			field : 'phone',
			title : '联系电话',
			width : 350,
			align : 'center'
		}, {
			field : 'workerHandicapType',
			title : '残疾类别',
			width : 250,
			align : 'center'
		}, {
			field : 'workerHandicapLevel',
			title : '残疾等级',
			width : 250,
			align : 'center'
		}, {
			field : 'yanz',
			title : '操作',
			width : 250,
			align : 'center',
			formatter : function(value, row, index) {
				var e = '<a href="#" onclick="workerList.openEditWorker(' + row.id + ')">编辑</a> ';
				var d = '<a href="#" onclick="workerList.deleteWorker(' + row.id + ',0)">删除</a>';
				return e + d;
			}
		} ] ]);

	};

	/**
		组装查询参数
	 **/
	workerList.getParams = function() {

		params = {};
		params.companyId = $("#companyId").val();//单位id
		params.year = $("#year").val();//年份
		params.workerName = $("#workerFind_workerName").val(); // //员工名
		params.workerHandicapCode = $("#workerFind_workerHandicapCode").val();// 残疾证号
		params.workerGender = $("#workerFind_workerGender").combobox("getValue");// 性别
		params.workerAge_1 = $("#workerFind_workerAge1").val();// 年龄
		params.workerAge_2 = $("#workerFind_workerAge2").val();// 年龄
		params.phone = $("#workerFind_phone").val();// 电话号
		params.workerHandicapType = $("#workerFind_workerHandicapType").combobox("getValue");//残疾类型 
		params.workerHandicapLevel = $("#workerFind_workerHandicapLevel").combobox("getValue");// 残疾等级

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
	};

	/**
	打开增加残疾职工页面
	 **/
	workerList.openAddWorker = function() {
		esd.common.openWindow('#workerPanel', "增加残疾职工", 1000, 550, 'worker/add/' + $("#workerPage #companyId").val());
	};

	/**
	打开编辑残疾职工页面
	 **/
	workerList.openEditWorker = function(id) {
		esd.common.openWindow('#workerPanel', "编辑残疾职工", 1000, 550, 'worker/edit/' + id);
	};

	/**
	删除企业
	 **/
	workerList.deleteWorker = function(id, type) {
		var params = new Array();

		//删除单条
		if (type == 0) {
			params.push(id);
		}
		//删除多条
		if (type == 1) {
			// 获取所有选中列
			var selection = $("#workerList_dataGrid").datagrid('getSelections');
			// 判断选择数目是否大于0
			if (selection.length == 0) {
				alert("未选择任何数据。");
				return;
			} else {
				// 组装参数
				for ( var i = 0; i < selection.length; i++) {
					params.push(selection[i].id);
				}
			}
		}
		// 显示确认删除对话框
		$.messager.confirm('确认', '您确认想要删除' + params.length + '记录吗？', function(r) {
			if (r) {
				// 删除请求
				$.ajax({
					url : 'worker/delete',
					data : {
						"params" : params,
						"companyId" : $("#companyId").val(),
						"years" : $("#workerPage #year").val()
					},
					type : 'post',
					success : function(data) {
						if (data == true) {
							// 刷新数据列表
							$('#workerList_dataGrid').datagrid('reload');
							$("#workerList_dataGrid").datagrid('clearSelections');
							alert("删除成功。");
						} else {
							alert("残疾职工删除失败。");
						}
					},
					error : function() {
						alert("删除残疾职工信息时发生错误。");
					}
				});
			}
		});
	};
	/**
	 删除企业
	 **/
	workerList.getCompany = function() {

		$.ajax({
			url : 'worker/get_comapnmy_information/' + $("#companyId").val() + '/' + $("#year").val(),
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
					alert("未获得到企业信息数据。");
				}

			},
			error : function() {

				alert("残疾职工列表，获取企业信息错误。");
			}
		});

	};
	$(function() {

		//加载数据
		workerList.loadData();
		
	});
</script>