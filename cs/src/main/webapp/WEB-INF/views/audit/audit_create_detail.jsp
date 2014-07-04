<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- 容器面板 -->
<script type="text/javascript">
	/*	$('#auditTabs').tabs({
	 onSelect : function(title,index) {
	 if(index==0){
	 if($('#worker_HandicapTotal').length>0){
	 if($('#yiLuRuCanJiRen').val($('#worker_HandicapTotal').html()));
	 };
	 };
	 }
	 });	*/
	//如果初审选项卡被选中时, 更新里面的内容
	
	
	
	$('#auditTabs').tabs({
		onSelect:function(title,index){
			//公司信息 -- 选项卡
			if(index == 0){
			/*	if($('#tempCompanyCode')){
					var auCompanyCode = $('#tempCompanyCode').val();
					$('#txtCompanyCode').combobox('setValue',auCompanyCode);
				}	*/
				//档案号的自动补全
				$('#txtCompanyCode').combobox({
					valueField : 'id',
					textField : 'text',
					method : 'GET',
					onChange : function(newValue, oldValue) {
						var url = '${contextPath}/security/audits/create/' + newValue;
						$('#txtCompanyCode').combobox('reload', url);
						//如果选中了一个, 则将这个公司对象的值复制到已有的控件
						if(newValue.length == 6){
							auditCreateCompany.getCompanyByCode(newValue);
						}
					}
				});
			}
			//残疾职工信息选项卡
			if(index  == 1 ){
				var companyId = $('#companyId').val();
				if(companyId == null || companyId == ''){
					$.messager.alert('消息','请先填写公司信息, 再来填写残疾职工信息','info');
					return;
				}
				//初始化残疾职工信息选项卡内的参数
				workerList.init();
				var companyId = $('#companyId').val();
				if(companyId != '' && companyId != null){
					//装载残疾职工数据
					workerList.loadData(workerList.getParams());
				}
			}
			//年审信息 -- 选项卡
			if(index == 2){
				//根据公司id, 得到该公司当年的基本审核信息, 显示在下方--关于公司基本信息的.
				var companyId = $('#companyId').val();
				if(companyId == null || companyId == ''){
					$.messager.alert('消息','请先填写公司信息, 再来填写审核信息','info');
					return;
				}
				var bl = initAudit.getAuditInfo(companyId);
				if(bl == true){
					//审核信息--关于人数和金额的
					if($('#worker_HandicapTotal').length>0){
						if($('#yiLuRuCanJiRen').val($('#worker_HandicapTotal').html()));
					};
					initAudit.jisuan();
				}
			}
			
			
		}
	});
</script>

<div class="easyui-panel"
	data-options="closable:true,collapsible:true,minimizable:true,
       					maximizable:true,fit:true,border:false,doSize:true">

	<div id="auditTabs" class="easyui-tabs" data-options="fit:true">
		<!--startprint-->
		<div title="公司信息">
			<jsp:include page="audit_create_company.jsp" />
		</div>
		<!--endprint-->
	<!-- 	<div title="残疾职工信息" data-options="href:'worker/list/auditCreate/${year}'"></div>	 -->
		<div title="残疾职工信息" cache="false">
			<jsp:include page="audit_create_worker_list.jsp" />
		</div>
		<div id="startaudit_tabs" title="年审信息" cache="false">
			<jsp:include page="audit_create_init.jsp" />
		</div>
		<div title="年审参数浏览"><jsp:include page="audit_params.jsp" /></div>
	</div>
</div>


