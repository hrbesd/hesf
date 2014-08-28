<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	initAuditList = {};
	initAuditList.getParams = function() {
		var params = {};
		params.year = $('#year').val();
		params.companyCode = $('#companyCode').val();
		params.companyName = $('#companyName').val();
		return params;
	};

	/**
	 * 加载初审列表数据
	 */
	initAuditList.loadData = function() {
		var params = initAuditList.getParams();
		esd.common.datagrid("#initAuditList_datagrid", "${contextPath}/security/audits/list", "#initAuditList_boolbar", [ [ {
			field : 'year',
			title : '审核年度',
			width : 100
		}, {
			field : 'companyCode',
			title : '档案编码',
			width : 100
		}, {
			field : 'companyId',
			hidden : true
		}, {
			field : 'companyName',
			title : '企业名称',
			width : 400,
			formatter : function(value, row, index) {
				var c = '<a href="javascript:void(0);" onclick="esd.common.viewCompany(\'' + row.companyId + '\')">' + value + '</a>';
				return c;
			}
		}, {
			field : 'initAuditDate',
			align : 'center',
			title : '初审时间',
			width : 120
		}, {
			field : 'initAuditUser',
			align : 'center',
			title : '初审人',
			width : 100
		}, {
			field : 'verifyAuditDate',
			align : 'center',
			title : '复审时间',
			width : 120
		}, {
			field : 'verifyAuditUser',
			align : 'center',
			title : '复审人',
			width : 100
		},{
			field : 'auditProcessStatus',
			align : 'center',
			title : '流程状态',
			width : 120
		}, {
			field : 'auditProcessStatusId',
			hidden : true
		}, {
			field : 'action',
			title : '操作',
			width : 150,
			align : 'center',
			formatter : function(value, row, index) {
				//只有处于复审状态的数据, 才可以执行撤消操作
				var r ='';
				if(row.auditProcessStatusId > 1){
					r = '<a href="javascript:initAuditList.repeal('+row.companyCode+',' + row.id + ','+row.companyId+');" style="color:red;">撤消</a>';
				}else{
					r = '<a href="javascript:initAuditList.viewAudit(' + row.id + ');" >查看</a>';
				}
				return r;
			}
		} ] ], params);
	};

	/**
	 *  查看审核信息
	 */
	initAuditList.viewAudit = function(index) {
		esd.common.defaultOpenWindowEx("查看", 920, 600, "${contextPath}/security/audits/edit/" + index + "/3");
	};

	/**
	 * 撤消复审操作
	 */
	 initAuditList.repeal = function(companyCode,auditId,companyId){
		var nowYear = $('#year').val();
		$.messager.confirm('警告','确定要撤消档案号为：'+companyCode+'的公司回到初审状态吗?该操作会删除已经开出的缴款票!请慎重操作!',function(r){
			if(!r){
				return;
			}else{
				$.ajax({
					url:'${contextPath}/security/audits/repealAudit/'+auditId+'/'+companyId,
					type:'get',
					success:function(data){
						if(data.notice == 'success'){
							$.messager.alert('信息','撤消成功, 该数据已经返回待复审状态.','info');
							//刷新列表数据
							initAuditList.findData();
						}else{
							$.messager.alert('信息',data.notice,'info');
						}
					},
					error:function(){
						$.messager.alert('信息','撤消复审操作发生错误, 请重新操作, 或联系管理员.','info');
					}
				});
			}
		});
		
	};
	/**
	 * 查询数据 并校验所有输入框
	 */
	initAuditList.findData = function() {
		var params = initAuditList.getParams();
		$('#initAuditList_datagrid').datagrid('reload', params);
	};
</script>
<!-- 自定义菜单 -->

<div id="initAuditList_boolbar" data-options="fit:false,doSize:false" style="white-space: nowrap;">
	<table border="0" width="100%">
	 	<tr>
			<td style="text-align: right;width: 7%;">年审时间:</td>
			<td style="width: 7%;">
				<input type="text" id="year" value="${nowYear }" disabled="disabled" readonly="readonly" />
			</td>
			<td style="text-align: right;width: 7%;">档案编码:</td>
			<td style="width: 10%;">
				<input type="text" style="width: 100%" id="companyCode" />
			</td>
			<td style="text-align: right;width: 7%;">企业名称:</td>
			<td style="text-align: right;width: 20%;">
				<input type="text" style="width: 100%" id="companyName" />
			</td>
			<td style="text-align: left;">
				<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="initAuditList.findData()">查找</a>
			</td>
		</tr>
	</table>
</div>
<!-- 数据表格 -->
<table id="initAuditList_datagrid"></table>
<div id="account" />
<script type="text/javascript">
	$.parser.onComplete = function(v) {
		initAuditList.loadData();
	};
</script>

