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
		params.id  = $('#workerId').val();	//id
		params.version = $('#version').val();	//悲观锁版本号
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
				'id':params.id, //id
				'version':params.version,	//悲观锁, 版本号
				'workerName':params.workerName, //姓名
				'careerCard':params.careerCard, //就业证号
				'phone':params.phone, //电话
				'currentJob':params.currentJob, //部门
				'remark':params.remark //备注
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
				url : '${contextPath}/security/worker/edit',
				type : 'post',
				data : {
					'id':params.id, //id
					'version':params.version,	//悲观锁, 版本号
					'workerName':params.workerName, //姓名
					'careerCard':params.careerCard, //就业证号
					'phone':params.phone, //电话
					'currentJob':params.currentJob, //部门
					'remark':params.remark //备注
				},
				success : function(data) {
					if(data == 'true' || data == true){
						$.messager.alert('提示','更新成功!','info');
						addWorker.close();
					}else{
						$.messager.alert('提示','更新出错!','error');
					}
				},
				error : function() {
					$.messager.alert('消息', '更新残疾职工时发生错误。', 'error');
					return;
				}
			});
		}
	};
	
	addWorker.openPicView = function(workerId){
		 
	};
	
	
	
	/**
	关闭增残疾职工信息窗口
	 **/
	addWorker.close = function() {
		$("#workerWindow").window("destroy");
		$('#workerList_dataGrid').datagrid('reload');
	};
	
	/**
	 *	 保存残疾职工信息
	 */
	addWorker.save = function() {
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

	<!-- 要编辑的基本信息 -->
	<div id="addWorkerDiv">																								<!--  onsubmit="return addWorker.validate();" -->
	 	<form id="addWorkerForm" action="#" method="post" class="addWorkerForm">
			
			<!-- 数据表格 -->
			<table id="company_information" align="center">
				
				<tr>
					<td align="center" colspan="6"><span style="font-size: 18px;font-weight: bold;">编辑残疾职工</span>
					</td>
				</tr>
				<tr>
					<td>残疾证号(<label class="red_notice"> *</label>):</td>
					<td colspan="5">
						<input type="hidden" name="workerId" id="workerId" value="${worker.id }" />
						<input type="hidden" name="version" id="version" value="${worker.version }" />
						<div style="float: left;width: 600px;">
							<input class="easyui-validatebox" type="text" id="workerHandicapCode" value="${worker.workerHandicapCode }" name="workerHandicapCode" readonly="readonly" data-options="required:true,validType:['length[20,22]']"
								style="width: 200px" />
						</div></td>
				</tr>
				<tr>
					<td class="">姓名:</td>
					<td><input class="easyui-validatebox" type="text" name="workerName" id="workerName" value="${worker.workerName }"/>
					</td>
					<td class="">性别:</td>
					<td><select name="workerGender" id="workerGender" class="easyui-combobox" data-options="height:30,disabled:'true'">
							<option value="1" <c:if test="${worker.workerGender == 1 }">selected = "selected" </c:if> >男</option>
							<option value="0" <c:if test="${worker.workerGender == 0 }">selected = "selected" </c:if> >女</option>
					</select>
					</td>
					<td class="">出生日期:</td>
					<td>
						<input  readonly ="readonly"  type="text" data-options="disabled:true" id="workerBirth" name="workerBirth" value="${worker.workerBirth }" />
					</td>
		
		
				</tr>
				<tr>
					<td class="">就业证号:</td>
					<td><input class="easyui-validatebox" type="text" name="careerCard" id="careerCard" value="${worker.careerCard }"/>
					</td>
					<td class="">残疾类别:</td>
					<td><input class="easyui-combobox" type="text" name="workerHandicapType.id" id="workerHandicapType" value="${worker.workerHandicapType.id }"
						data-options="height:30,disabled:'true',required:true,editable:false,valueField:'id',textField:'handicapType',url:'parameter/workerHandicapTypeService'" />
					</td>
					<td class="">残疾等级:</td>
					<td><input class="easyui-combobox" type="text" name="workerHandicapLevel.id" id="workerHandicapLevel" value="${worker.workerHandicapLevel.id }"
						data-options="height:30,disabled:'true',required:true,editable:false,valueField:'id',textField:'handicapLevel',url:'parameter/workerHandicapLevelService'" />
					</td>
				</tr>
				<tr>
					<td class="">联系电话:</td>
					<td><input class="easyui-validatebox" type="text" name="phone" id="phone" data-options="" value="${worker.phone }" />
					</td>
					<td class="">现任岗位:</td>
					<td><input class="easyui-validatebox" type="text" name="currentJob" id="currentJob" value="${worker.currentJob }"/>
					</td>
				</tr>
				<tr>
					<td class="" style="height:31px;">照片:</td>
					<td colspan="5">
						<c:if test="${worker.picTitle != null && worker.picTitle != '' }">
							<input type="button" id="btnShowPic" onclick="" value="点我查看照片" style="cursor:pointer;" />
						</c:if>
						<input type="checkbox" id="isUpload" style="height:auto;" />
						<input type="button" name="file" value="上传" id="picfile" display="none"/>
						<input type="text" readonly="readonly" id="picfileTitle" style="border:none;width:400px;" display="none"/>
					</td>
				</tr>
				<tr>
					<td class="">备注:</td>
					<td colspan="5"><textarea rows="4" cols="100" name="remark" id="remark">${worker.remark }</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center;">
						<a href="javascript:addWorker.save();" class="easyui-linkbutton" iconCls="icon-ok">更新</a>
						<a href="javascript:addWorker.close();" class="easyui-linkbutton" iconCls="icon-undo">返回</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
