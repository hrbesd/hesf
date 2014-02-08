<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />




<script type="text/javascript">
	$(function() {
		$('#print_list_grid').datagrid({
			// url:'../../CompanyBasicAction', 
			title : '打印列表',
			iconCls : 'icon-save',
			pageSize : 10,
			pageList : [ 10, 30, 50, 100 ],
			pagination : true, //是否显示分页栏 
			doSize : true,//在面板被创建的时候将重置大小和重新布局。
			fit : true, //面板大小将自适应父容器
			fitColumns : true,//是否显示横向滚动条，未生效
			nowrap : true,//如果为true，则在同一行中 。设置为true可以提高加载性能 
			idField : 'id',
			rownumbers : false,//显示行号
			striped : true,//奇偶行使用不同背景色
			frozenColumns : [ [ {
				field : 'ck',
				checkbox : true
			}, ] ],
			columns : [ [ {
				field : 'sArchivesCode',
				title : '档案编码',
				width : 100
			}, {
				field : 'sTaxCode',
				title : '税务编码',
				width : 100
			}, {
				field : 'sCompanyName',
				title : '企业名称',
				width : 900,
				formatter : function(value, row, index) {
					var c = '<a href="#" onclick="print_list.openViewCompany(' + index + ')">' + value + '</a>';
					return c;
				}

			}, {
				field : 'action',
				title : '操作',
				width : 250,
				align : 'center',
				formatter : function(value, row, index) {
						var e = '<a href="#" onclick="print_list.openAuditPrint(' + index + ')">年审表</a> ';
						var d = '<a href="#" onclick="print_list.openStaffPrint(' + index + ')">残疾职工表</a> ';
						var v = '<a href="#" onclick="print_list.openNoticePrint(' + index + ')">缴款通知书</a> ';
					return e + d + v;
					
				}
			} ] ],
			toolbar : '#print_list_boolbar'//指定自定义菜单
		});
		var print_list_data = {
			total : 3,
			rows : [ {
				"sArchivesCode" : "301006",
				"sCompanyName" : "黑龙江省委办公厅黑龙江省委办公厅",
				"sTaxCode" : "80451001"
			}, {
				"sArchivesCode" : "301007",
				"sCompanyName" : "黑龙江省委老干部局",
				"sTaxCode" : "80451002"
			}, {
				"sArchivesCode" : "301008",
				"sCompanyName" : "中国人寿保险股份有限公司绥化分公司 ",
				"sTaxCode" : "80451003"
			} ]
		};
		//加载前台数据		
		$("#print_list_grid").datagrid("loadData", print_list_data);

	});
	
	
	print_list={};
	
	
	/*
		转向到 “年审表”打印页面
	*/
	print_list.openAuditPrint=function(){
		$("#printPanel").window({
			width : 1000,
			href : 'print/audit_print',
			height : 550,
			title : '年审表 打印页面',
			minimizable : false,
			loadingMessage : '正在加载，请稍后。',
			iconCls : 'icon-save',
			modal : true//模态窗口
		});
	};
	/*
		转向到 “残疾职工表”打印页面
	*/
	print_list.openStaffPrint=function(){
		$("#printPanel").window({
			width : 1000,
			href : 'print/staff_print',
			height : 550,
			title : '残疾职工表 打印页面',
			minimizable : false,
			loadingMessage : '正在加载，请稍后。',
			iconCls : 'icon-save',
			modal : true//模态窗口
		});
	};
	
	/*
		转向到 “缴款通知书”打印页面
	*/
	print_list.openNoticePrint=function(){
		$("#printPanel").window({
			width : 1000,
			href : 'print/notice_print',
			height : 550,
			title : '缴款通知书 打印页面',
			minimizable : false,
			loadingMessage : '正在加载，请稍后。',
			iconCls : 'icon-save',
			modal : true//模态窗口
		});
	};
		
	
	/* 	
		 查看企业信息框
	*/
	print_list.openViewCompany = function(index) {
				$('#viewCompanyPanel').window({
					width : 1000,
					href : 'basicInfo/view_company',
					height : 550,
					title : '查看单位信息',
					minimizable : false,
					loadingMessage : '正在加载，请稍后。',
					iconCls : 'icon-save',
					modal:true//模态窗口
				});
			};
	
</script>


<!-- 数据表格 -->
<table id="print_list_grid"></table>
<!-- 自定义菜单 -->
<div id="print_list_boolbar" data-options="fit:false,doSize:false" style="white-space: nowrap;height: 50px;margin-top: 5px">
	<div>
		<input type="" style="width: 7%" /> <input type="" style="width: 6%" /> <input type="" style="width: 68%" />
		<button>查找</button>
	</div>
</div>


<div id="printPanel"></div>
<!-- 查看企业信息窗口 -->
<div id="viewCompanyPanel"></div>