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

		esd.common.datagrid("#workerList_dataGrid", "query/worker/company_worker_list", "#workerListBoolbar", [ [

		{
			field : 'retirementAge',
			title : '休年龄',
			hidden : true,
		}, {
			field : 'workerName',
			title : '姓名',
			width : 300,
			styler : function(value, row, index) {
				//年龄检测
				return workerList.ageDetection(value, row, index);
			}
		}, {
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
			field : 'yanz',
			title : '操作',
			width : 250,
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
		} ] ], params);
	};

	/**
	 职工年龄检测
	 **/
	workerList.ageDetection = function(value, row, index) {

		if (row.workerGender == '男') {
			if (row.workerAge > row.retirementAge) {
				return 'background-color:red;font-weight: bold;';
			}
		}
		if (row.workerGender == '女') {
			if (row.workerAge > row.retirementAge) {
				return 'background-color:red;font-weight: bold;';
			}
		}

	};

	/**
		组装查询参数
	 **/
	workerList.getParams = function() {

		params = {};
		params.companyId = $("#companyId").val();//单位id
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

		esd.common.openWindow("#workerWindow", "增加残疾职工", 960, 550, 'worker/add/' + $("#companyId").val());
	};

	/**
	打开编辑残疾职工页面
	 **/
	workerList.openEditWorker = function(id) {
		esd.common.openWindow("#workerWindow", "编辑残疾职工", 960, 550, 'worker/edit/' + id);
	};
	/**
	打开导入残疾职工页面
	 **/
	workerList.openImportWorker = function() {
		esd.common.openWindowEx("#importWorkerWindow", "导入残疾职工", 960, 550, 'worker/importworker/'+ $("#companyId").val(), function() {
			$("#importWorkerWindow").window("destroy");
			//刷新数据列表
			$('#workerList_dataGrid').datagrid('reload');
		});
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
			var selection = $("#workerList_dataGrid").datagrid('getChecked');
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
		$.messager.confirm('确认', '您确认想要删除' + params.length + '记录吗？', function(r) {
			if (r) {
				// 删除请求
				$.ajax({
					url : 'worker/delete',
					data : {
						"params" : params,
						"companyId" : $("#companyId").val(),
						"year" : $("#year").val()
					},
					type : 'post',
					success : function(data) {
						if (data == true) {
							// 刷新数据列表
							$('#workerList_dataGrid').datagrid('reload');
							$("#workerList_dataGrid").datagrid('clearSelections');
						
							$.messager.alert('消息', '删除成功。', 'ok');
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
	 获取企业信息
	 **/
	workerList.getCompany = function() {

		$.ajax({
			url : 'company/getinformation/' + $("#companyId").val(),
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

	//组件解析完成
	$.parser.onComplete = function() {
		workerList.init();
		workerList.loadData(workerList.getParams());
		workerList.getCompany();
	};

	$(function() {
		//初始化组件

	});
</script>
<!-- 数据表格 -->
<table id="workerList_dataGrid"></table>

<input type="hidden" id="companyId" value="${companyId}" />
<!-- 自定义菜单 -->
<div id="workerListBoolbar">

	<table class="workerListTip" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tipTextEx">企业名称:</td>
			<td id="company_name" style="width: 330px"></td>
			<td class="tipTextEx">档案编码:</td>
			<td id="company_Code"></td>

			<td class="tipTextEx">税务编码:</td>
			<td id="company_TaxCode"></td>
		</tr>
		<tr>
			<td class="tipTextEx">企业总人数:</td>
			<td id="company_EmpTotal"></td>
			<td class="tipTextEx">残疾职工总数:</td>
			<td id="worker_HandicapTotal"></td>

			<td class="tipTextEx">地区:</td>
			<td id="company_Area" style="width:135px"></td>

		</tr>
	</table>
	<div style="text-align: right; margin: 7px;">

		<a href="javascript:workerList.openAddWorker();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">增加</a> <a href="javascript:workerList.deleteWorker('',1);"
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">删除</a> <a href="javascript:workerList.openImportWorker();" class="easyui-linkbutton"
			data-options="iconCls:'icon-ok',plain:true">导入文件</a>
	</div>
	<table>
		<tr>
			<td>
				<!-- 姓名 --> <input id="workerFind_workerName" type="text" style="width: 100px;margin-left: 48px" />
			</td>
			<td>
				<!-- 残疾证号 --> <input id="workerFind_workerHandicapCode" type="text" style="width: 183px" />
			</td>
			<td>
				<!-- 性别 --> <input id="workerFind_workerGender" type="text" />
			</td>
			<td>
				<!-- 年龄 --> <input id="workerFind_workerAge1" type="text" style="width: 32px" data-options="validType:['_number']" class="easyui-validatebox" />- <input id="workerFind_workerAge2" type="text"
				style="width: 32px" data-options="validType:['_number']" class="easyui-validatebox" />
			</td>
			<td>
				<!-- 电话 --> <input id="workerFind_phone" type="text" style="width:105px" data-options="validType:['_number']" class="easyui-validatebox" />
			</td>
			<td>
				<!-- 残疾类型 --> <input style="width:80px" type="text" id="workerFind_workerHandicapType" data-options="height:30,panelHeight:240" class="easyui-combobox" />
			</td>
			<td>
				<!-- 残疾等级 --> <input style="width:80px" type="text" id="workerFind_workerHandicapLevel" data-options="height:30,panelHeight:145" class="easyui-combobox" /> <a
				href="javascript:workerList.findData();" class="easyui-linkbutton" iconCls="icon-search">查找</a>
			</td>
		</tr>
	</table>

</div>



