<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function() {
		esd.common.datagrid("#parameter_list_grid", "${contextPath}/security/settings/yearAuditParameter/list", "#parameter_list_boolbar", [ [ {
			field : 'year',
			title : '年度',
			width : 200,
			formatter : function(value, row, index) {
				var c = '<a href="#" onclick="viewrow(' + row.id + ')">' + value + '</a>';
				return c;
			}
		}, {
			field : 'areaCode',
			title : '所属区域',
			width : 200
		}, {
			field : 'averageSalary',
			title : '计算基数',
			width : 200
		}, {
			field : 'putScale',
			title : '安置比例(%)',
			width : 200
		}, {
			field : 'payCloseDate',
			title : '支付截至日期',
			width : 200
		}, {
			field : 'action',
			title : '操作',
			width : 250,
			align : 'center',
			formatter : function(value, row, index) {
				var e = '<a href="#" onclick="parameter_list.updateParameter(' + row.id + ')">编辑</a> ';
				var d = '<a href="#" onclick="parameter_list.deleteParameter(' + row.id + ')">删除</a>';
				return e + d;
			}
		} ] ]);
	});

	parameter_list = {};
	/*
		打开参数窗口
	 */
	parameter_list.addParameter = function() {
		esd.common.defaultOpenWindowEx("添加年审参数", 650, 600, "${contextPath}/security/settings/yearAuditParameter/add");
	};
	parameter_list.updateParameter = function(index) {
		esd.common.defaultOpenWindowEx("添加年审参数", 650, 600, "${contextPath}/security/settings/yearAuditParameter/edit/" + index);
	};

	parameter_list.deleteParameter = function(index) {
		var params = new Array();
		params.push(index);
		$.messager.confirm('确认对话框', '是否删除', function(r) {
			if (r == true) {
				$.ajax({
					url : "${contextPath}/security/settings/yearAuditParameter/delete",
					type : 'POST',
					data : {
						params : params
					},
					success : function(data) {
						if (data == true) {
							$.messager.alert('消息', '删除成功', 'info', function() {
								$("#parameter_list_grid").datagrid('reload');
							});
						} else {
							$.messager.alert('消息', '删除失败', 'info');
						}
					},
					dataType : "json",
					async : false
				});
			}
		});

	};
</script>



<!-- 数据表格 -->
<table id="parameter_list_grid"></table>

<!-- 自定义菜单 -->
<div id="parameter_list_boolbar" data-options="fit:false,doSize:false" style="white-space: nowrap;height: 60px;margin-top: 5px">
	<div style="text-align: right;">
		<a href="javascript:parameter_list.addParameter();" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a> <a href="javascript:parameter_list.delParameter();" class="easyui-linkbutton"
			plain="true" iconCls="icon-cancel">批量删除</a>
	</div>
	<div>
		<input type= /> <input type="" style="width: 6%" /> <input type="" style="width: 68%" /> <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search">查找</a>
	</div>
</div>


<div id="parameterPanel"></div>


