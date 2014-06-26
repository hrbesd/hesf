<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	/*
		残疾职工页面
	 */
	var addWorker = {};
	var params = new Object();
	/*
	 * 给参数赋值, 以便提交
	 */
	addWorker.getParams = function(){
		params = new Object();
		//定义需要提交的参数(除照片外)
		params.workerHandicapCode = $('#workerHandicapCode').val();	//残疾证号
		if($('#workerName').val() == null || $('#workerName').val() == ''){
			$.messager.alert('提示','请输入员工姓名','info');
			return null;
		}
		params.workerName = $('#workerName').val();//姓名
		var careerCard = $('#careerCard').val();
		if(careerCard == null || careerCard == ''){
			careerCard = esd.common.unknown();
		}
		params.careerCard = careerCard;
		var phone = $('#phone').val();
		if(phone == null || phone == ''){
			phone = esd.common.unknown();
		}
		params.phone = phone;
		var currentJob = $('#currentJob').val();
		if(currentJob == null || currentJob == ''){
			currentJob = esd.common.unknown();
		}
		params.currentJob = currentJob;
		var remark = $('#remark').val();
		if(remark == null || remark == ''){
			remark = esd.common.unknown();
		}
		params.remark = remark;
		params.companyId = $('#companyId').val(); //所属企业id
		params.year = $('#year').val();	//年审年份
		return params;
	};
	
	//定义绑定上传按钮事件
	var button = $('#picfile'),interval;
	
	/*
	 * 异步上传带照片的员工的数据
	 */
	var ajaxUploadPic = new AjaxUpload(button, {
		action: '${contextPath}/security/worker/addWithPic',
		name: 'picfile',//更改上传的文件名
		autoSubmit:false,
		type:'POST',
		data: {},
		onSubmit : function(file , ext){
			//验证上传文件格式
			if(!(ext && /^(bmp|tiff|gif|jpeg|jpg|png)$/.test(ext))){
				$.messager.alert('提示','您上传的文件格式不对, 或者不是图片文件, 请重新选择','info');
				$('#picfileTitle').val('');
				return false;
			}

			//设置上传参数
			this.setData({
				'workerHandicapCode':params.workerHandicapCode, //残疾证号
				'workerName':params.workerName, //姓名
				'careerCard':params.careerCard, //就业证号
				'phone':params.phone, //电话
				'currentJob':params.currentJob, //部门
				'remark':params.remark, //备注
				'companyId':params.companyId,
				'year':params.year
			});
		},
		onComplete : function(file,response){
			if(response == 'success'){
				$.messager.alert('提示','保存成功!','info');
				addWorker.close();
			}else{
				$.messager.alert('提示',response,'error');
			}
			this.enable();
		}
	});
	
	/*
	 * 调用  异步上传带照片的员工的数据
	 */
	var saveWithPic = function(){
		// 先提取参数
		params = addWorker.getParams();
		if( params!= null && params != undefined){
			ajaxUploadPic.submit();
		};
	};
	
	/*
	 * 异步提交    不   带照片的员工信息
	 */
	var saveWithOutPic = function(){
		// 先提取参数
		params = addWorker.getParams();
		if(params != null && params != undefined){
			$.ajax({
				url : '${contextPath}/security/worker/add',
				type : 'post',
				data : {
					'workerHandicapCode':params.workerHandicapCode, //残疾证号
					'workerName':params.workerName, //姓名
					'careerCard':params.careerCard, //就业证号
					'phone':params.phone, //电话
					'currentJob':params.currentJob, //部门
					'remark':params.remark, //备注
					'companyId':params.companyId,
					'year':params.year
				},
				success : function(data) {
					if(data == 'true' || data == true){
						$.messager.alert('提示','保存成功!','info');
						addWorker.close();
					}else{
						$.messager.alert('提示','保存出错!','error');
					}
				},
				error : function() {
					$.messager.alert('消息', '增加残疾职工时发生错误。', 'error');
					return;
				}
			});
		}
	};
	
	
	/**
	关闭增残疾职工信息窗口
	 **/
	addWorker.close = function() {
		$('#workerList_dataGrid').datagrid('reload');
		$("#workerWindow").window("destroy");
	};
	
	/**
		清空控件数据
	 **/
	addWorker.empty = function() {
		$("#addWorkerForm #workerHandicapCode").val("");//残疾证号
		$("#addWorkerForm #workerName").val("");//姓名
		$("#addWorkerForm #workerBirth").val("");//出生日期
		$("#addWorkerForm #careerCard").val("");//就业证号
		$("#addWorkerForm #phone").val("");//电话
		$("#addWorkerForm #currentJob").val("");//部门
		$("#addWorkerForm #remark").val("");//备注
		$("#addWorkerForm #verification").val("");//验证
		$("#workerGender").combobox("setValue", "1");//性别
		$("#workerHandicapType").combobox("setValue", "1");//残疾类别
		$("#workerHandicapLevel").combobox("setValue", "1");//残疾等级
	};
	
	/*
	 *	 保存残疾职工信息
	 */
	addWorker.save = function() {
		//获取残疾证号
		var workerHandicapCode = $("#workerHandicapCode").val();
		
		//根据残疾证号初始化其他组件, 并进行非访问数据库的校验
		var initElement = addWorker.initElement(workerHandicapCode);
		if(!initElement){
			return;
		}
		
		//身份证号
		var workerIdCard = ($("#workerHandicapCode").val()).substring(0, 18);
		var companyId = $('#companyId').val();

		//校验残疾证号是否存在，是否在其他公司
		$.ajax({
			url : '${contextPath}/security/worker/validateWorkerIdentityCode',
			type : 'post',
			data : {
				'workerIdCard' : workerIdCard,
				'year':$("#nowYear").val()
			},
			success : function(data) {
				//第一种情况， 员工存在，并在其他公司内
				if (data.notice == 'inCompany') {
					//如果就是在本公司的话, 则不进行提示,只将信息不全
					if(data.companyId == companyId){
						//将员工相关控件信息补全
						$.messager.alert('提示','该员工已经在此公司里了, 请不要重复添加','info');
						return;
					}
					$('#win').window(
						{
							title : '警告：该员工已被其他公司录用',
							height : 180,
							width : 650,
							content :  "<div class='addtip'><span >证号：“" + $("#workerIdCard").val() + "”已被：“" + data.companyName + "”录用。</span>\n"
									+ "\n<table  border='1' > <tr><th>档案编码</th><th>企业名称</th></tr><tr><td>" + data.companyCode + "</td>"
									+ "<td style='width: 450px;'>" + data.companyName + "</td></tr></table><div>",
							modal : true,
							collapsible : false,
							minimizable : false,
							maximizable : false
						});
					return;
				//第二种情况，员工存在且不在任何公司 或者 员工不存在, 都进行保存操作, 在方法内部有相应的判断
				} else if(data.notice == 'exists' || data.notice == 'notExists') {
					//保存分两种,是否选中了上传照片
					var checked = $('#isUpload').attr('checked');
					//① 有照片时
					if(checked){
						var picfileTitle = $('#picfileTitle').val();
						if(picfileTitle == null || picfileTitle == '' || picfileTitle == undefined){
							$.messager.alert('提示','请选择您要上传的照片.','info');
							return;
						}
						saveWithPic();
					}else{
					//②没有照片时
						saveWithOutPic();
					}
				}
			},
			error : function() {
				alert("增加残疾职工校验时发生错误。");
				return;
			}
		});	
		return;
	};

	/*
	 * 根据残疾证号, 初始化其他控件
	 */
	addWorker.initElement=function(workerHandicapCode){
		//校验表单
		if (esd.common.validatebox("#addWorkerForm") == false) {
			return false;
		}
		//非法字符判断
		var reg = /^[0-9]{14}[0-9a-zA-Z]{4}[1-7]{1}[1-4]{1}[B]?[0-9]?/;
		var checkResult = reg.test(workerHandicapCode);
		if(!checkResult){
			$.messager.alert('消息', '您输入的残疾证号格式有误, 请检查后重新输入.', 'error');
			return false;
		}
		//获取残疾类型 
		var workerType = workerHandicapCode.substring(18, 19);
		$("#workerHandicapType").combobox("setValue", workerType);
		if (workerType < 1 || workerType>7) {
		
			$.messager.alert('消息', '残疾证号内残疾类型错误。', 'error');
			return false;
		}
		//获取残疾等级
		var workerLeven = workerHandicapCode.substring(19, 20);
		$("#workerHandicapLevel").combobox("setValue", workerLeven);
		if (workerLeven < 1 || workerLeven > 4 ) {
			$.messager.alert('消息', '残疾证号内残疾等级错误。', 'error');
			return false;
		}
		//根据残疾证号获取出生日期
		var year = workerHandicapCode.substring(6, 10);//年份
		var month = workerHandicapCode.substring(10, 12);//月
		var day = workerHandicapCode.substring(12, 14);//日
		//根据残疾证号获取性别
		var sex = workerHandicapCode.substring(16, 17);
		//职工去年年龄=审核年份-出生年份+1
		var age=$("#nowYear").val()-year+1;
		//判断年龄
		if(age<=16){
				$.messager.alert('消息', '职工年龄过小，不能录入。', 'error');
				return false;
		}
		if (sex % 2 === 0) {
			//偶数 女性职工
			$("#workerGender").combobox("setValue", "0");
			if(age >= $("#retireAgeFemale").val()){
				$.messager.alert('消息', '职工年龄：'+age+'岁，性别：女性。去年已超过退休年龄', 'error');
				return false;
			}
		} else {
			//奇数 男性
			$("#workerGender").combobox("setValue", "1");
				if(age >= $("#retireAgeMale").val()){
					$.messager.alert('消息', '职工年龄：'+age+'岁，性别：男性。去年已超过退休年龄', 'error');
				return false;
			}
		}
		//出生日期
		$("#workerBirth").val(year + "-" + month + "-" + day);
		//身份证号
		var workerIdCard = ($("#workerHandicapCode").val()).substring(0, 18);
		$("#workerIdCard").val(workerIdCard);
		// 出生年份--供后台查询使用
		$("#workerBirthYear").val($("#nowYear").val());
		return true;
	};
	
	/**
		残疾证号校验
	 **/
	addWorker.handicapCodeValidate = function() {
		//获取残疾证号
		var workerHandicapCode = $("#workerHandicapCode").val();
		//身份证号
		
		//根据残疾证号初始化其他组件, 并进行非访问数据库的校验
		var initElement = addWorker.initElement(workerHandicapCode);
		if(!initElement){
			return;
		}
		var workerIdCard = ($("#workerHandicapCode").val()).substring(0, 18);
		var companyId = $('#companyId').val();
		//远程校验 校验残疾证号是否存在，是否在其他公司
		$.ajax({
			url : 'worker/validateWorkerIdentityCode',
			type : 'post',
			data : {
				'workerIdCard' : workerIdCard,	//身份证号
				'year':$("#nowYear").val(),	//审核年份
			},
			success : function(data) {
				//第一种情况， 员工存在，并在其他公司内
				if (data.notice == 'inCompany') {
					//如果就是在本公司的话, 则不进行提示,只将信息不全
					if(data.companyId == companyId){
						//将员工相关控件信息补全
						$.messager.alert('提示','该员工已经在此公司里了, 请不要重复添加','info');
						return;
					}
					//如果在其他家公司的话, 则进行提示
					$('#win').window(
							{
								title : '警告：该员工已被其他公司录用',
								height : 180,
								width : 650,
								content :  "<div class='addtip'><span >证号：“" + $("#workerIdCard").val() + "”已被：“" + data.companyName + "”录用。</span>\n"
										+ "\n<table  border='1' > <tr><th>档案编码</th><th>企业名称</th></tr><tr><td>" + data.companyCode + "</td>"
										+ "<td style='width: 450px;'>" + data.companyName + "</td></tr></table><div>",
								modal : true,
								collapsible : false,
								minimizable : false,
								maximizable : false
							});
					return;
					//第二种情况，员工存在，不在任何公司
				} else if(data.notice == 'exists' || data.notice == 'notExists') {
					//将员工相关控件信息补全
					$("#addWorkerForm #workerName").val(data.workerName);//姓名
					$("#addWorkerForm #careerCard").val(data.careerCard);//就业证号
					$("#addWorkerForm #phone").val(data.phone);//电话
					$("#addWorkerForm #remark").val(data.remark);//备注
				//	$.messager.alert('消息', '验证通过，可以注册', 'ok');
					return;
				}else{
					$.messager.alert('消息', data.notice, 'info');
					return;
				}
			},
			error : function() {
				$.messager.alert('消息', '增加残疾职工校验时发生错误。', 'error');
				return;
			}
		});
		return;
	};
	
	
	//组件解析完成
	$.parser.onComplete = function() {
		
		//上传组件初始为隐藏状态
		$('#picfile').hide();
		$('#picfileTitle').hide();
		//是否显示上传组件
		$('#isUpload').click(function(){
			var checked = $('#isUpload').attr('checked');
			if(checked){
				$('#picfile').show();
				$('#picfileTitle').show();
			}else{
				$('#picfile').hide();
				$('#picfileTitle').hide();
			}
		});
	};
</script>

	<!-- 显示要编辑的基本信息-->
	<div id="addWorkerDiv">																								<!--  onsubmit="return addWorker.validate();" -->
	 	<form id="addWorkerForm" action="#" method="post" class="addWorkerForm">
			<!--  女退休年龄 -->
			<input type="hidden" value="${retireAgeFemale}"  id="retireAgeFemale"/>
			<!--  男退休年龄 -->
			<input type="hidden" value="${retireAgeMale}" id="retireAgeMale"/>
			<input type="hidden" value="${year}" id="nowYear" name="year"/>
			<input type="hidden" value="${companyId}" name="companyId"  />
			
			<!-- 数据表格 -->
			<table id="company_information" align="center">
				
				<tr>
					<td align="center" colspan="6"><span style="font-size: 18px;font-weight: bold;">增加残疾职工</span>
					</td>
				</tr>
				<tr>
					<td>残疾证号(<label class="red_notice"> *</label>):</td>
					<td colspan="5">
		
						<div style="float: left;width: 600px;">
							<input class="easyui-validatebox" type="text" id="workerHandicapCode" value="" name="workerHandicapCode" data-options="required:true,validType:['length[20,22]']"
								style="width: 200px" /> <input type="hidden" name="workerIdCard" id="workerIdCard" /> <a href="javascript:addWorker.handicapCodeValidate()" class="easyui-linkbutton" iconCls="icon-search">调取残疾人信息</a> <a
								href="javascript:addWorker.empty()" class="easyui-linkbutton" iconCls="icon-reload">清空</a>
						</div></td>
				</tr>
				<tr>
					<td class="">姓名:</td>
					<td><input class="easyui-validatebox" type="text" name="workerName" id="workerName" />
					</td>
					<td class="">性别:</td>
					<td><select name="workerGender" id="workerGender" class="easyui-combobox" data-options="height:30,disabled:'true'">
							<option value="1">男</option>
							<option value="0">女</option>
					</select>
					</td>
					<td class="">出生日期:</td>
					<td><input  readonly ="readonly"  type="text" data-options="disabled:true" id="workerBirth" name="workerBirth"></input> <input type="hidden"
						id="workerBirthYear" name="workerBirthYear" /></td>
		
		
				</tr>
				<tr>
					<td class="">就业证号:</td>
					<td><input class="easyui-validatebox" type="text" name="careerCard" id="careerCard" />
					</td>
					<td class="">残疾类别:</td>
					<td><input class="easyui-combobox" type="text" name="workerHandicapType.id" id="workerHandicapType" value="1"
						data-options="height:30,disabled:'true',required:true,editable:false,valueField:'id',textField:'handicapType',url:'parameter/workerHandicapTypeService'" />
					</td>
					<td class="">残疾等级:</td>
					<td><input class="easyui-combobox" type="text" name="workerHandicapLevel.id" id="workerHandicapLevel" value="1"
						data-options="height:30,disabled:'true',required:true,editable:false,valueField:'id',textField:'handicapLevel',url:'parameter/workerHandicapLevelService'" />
					</td>
				</tr>
				<tr>
					<td class="">联系电话:</td>
					<td><input class="easyui-validatebox" type="text" name="phone" id="phone" data-options="" />
					</td>
					<td class="">现任岗位:</td>
					<td><input class="easyui-validatebox" type="text" name="currentJob" id="currentJob" />
					</td>
				</tr>
				<tr>
					<td class="" style="height:31px;">上传照片:</td>
					<td colspan="5">
						<input type="checkbox" id="isUpload" style="height:auto;" />
						<input type="button" name="file" value="上传" id="picfile" display="none"/>
						<input type="text" readonly="readonly" id="picfileTitle" style="border:none;width:400px;" display="none"/>
					</td>
				</tr>
				<tr>
					<td class="">备注:</td>
					<td colspan="5"><textarea rows="4" cols="100" name="remark" id="remark"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center;">
						<a href="javascript:addWorker.save();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
						<a href="javascript:addWorker.close();" class="easyui-linkbutton" iconCls="icon-undo">返回</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
<div id="win"></div>
