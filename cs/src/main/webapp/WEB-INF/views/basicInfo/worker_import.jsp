<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	$(function() {
	});
	
	var importWorker = {};
	
	//定义上传的参数
	var params = new Object();
	
	/*
	 * 给参数赋值, 以便提交
	 */
	importWorker.getParams = function(){
		params = new Object();
		var currentCompanyId = $('#currentCompanyId').val();
		//定义需要提交的参数(除照片外)
		if(currentCompanyId == null || currentCompanyId == '' || currentCompanyId == undefined){
			$.messager.alert('提示','上传参数有误, 请刷新页面重新操作.','info');
			return null;
		}
		params.companyId = $('#currentCompanyId').val(); //所属企业id
		var currentyear = $('#currentyear').val();
		if(currentyear == null || currentyear == '' || currentyear == undefined){
			$.messager.alert('提示','上传参数有误, 请刷新页面重新操作.','info');
			return null;
		}
		params.year = $('#currentyear').val();	//年审年份
		return params;
	};
	
	//定义绑定上传按钮事件
	var button = $('#picFileImport'),interval;
	
	//提示文字
	var loadingWords = ['数','据','校','验','中',',','请','稍','后','.','.','.'];
	var msgIndex = 0;
	/**
	 * 校验中文字滚动
	 **/
	importWorker.changeLoadWord = function(){
		var maxLength = loadingWords.length;
		if(msgIndex < maxLength){
			//var text = 
			$('#divLoadingMsg').append(loadingWords[index]);
			msgIndex++;
		}
		$('#divLoadingMsg').html('');
		msgIndex = 0;
	};
	/*
	 * 异步 上传excel文件方法函数
	 */
	var ajaxUploadPic = new AjaxUpload(button, {
		action: '${contextPath}/security/worker/importworker',
		name: 'excel',//更改上传的文件名
		autoSubmit:false,
		type:'POST',
		data: {},
		onSubmit : function(file , ext){
			/**
			 *	①验证上传文件格式
			 **/
			if(!(ext && /^(xls|xlsx)$/.test(ext))){
				$.messager.alert('提示','您上传的文件格式不对, 或者不是excel文件, 请重新选择','info');
				$('#picfileTitle').val('');
				return false;
			}
			/**
			 *	③提交按钮隐藏,进行中提示文字出现
			 **/
			//
			$('#divBtn').hide();
			$('#divLoadingMsg').show();
			/**
			 *	②设置上传参数
			 **/
			this.setData({
				'companyId':params.companyId,
				'year':params.year
			});
			
		},
		onComplete : function(file,response){
			//截取出提示信息
			var noticeMsg = response.indexOf('success:');
			//非成功提示信息
			if(noticeMsg < 0){
				$.messager.alert('提示',response,'info');
				//提交按钮出现,进行中提示文字隐藏
				$('#divBtn').show();
				$('#divLoadingMsg').hide();
				$('#picfileTitle').val('');
				return;
			}
			
			//解析完成, 上传框隐藏, 提示框出现
			$('#importWorkerDiv').hide();
			$('#returnMessageDiv').show();
			
			//成功提示信息
			var numbers = response.substring(8,response.indexOf('successEnd'));
			//提取出总条数, 正确条数, 错误条数数据
			var numberArray = numbers.split(',');
			var totalCount = numberArray[0];	//总
			var rightCount = numberArray[1];	//正
			var wrongCount = numberArray[2];	//误
			$('#totalCount').html(totalCount);
			$('#rightCount').html(rightCount);
			$('#wrongCount').html(wrongCount);
			//正确条数为0, 则不显示导入按钮
			if(rightCount == 0 || rightCount == '0'){
				$('#btnImport').hide();
			}else if(rightCount > 0 ){
				$('#btnImport').show();
			}
			// 错误条数为0, 则不显示下载错误列表按钮
			if(wrongCount == 0 || wrongCount == '0'){
				$('#pWrongPath').hide();
			}else if(wrongCount > 0){
				$('#pWrongPath').show();
			}
			
			//截取错误列表文件下载路径
			var wrongPathStart = response.indexOf('wrongPath');
			if(wrongPathStart > 0){
				var wrongPath = response.substring(wrongPathStart+10);
				$('#wrongPath').attr('href',wrongPath);
			}
			this.enable();
		}
	});
	
	/**
	 * 上传
	 **/
	importWorker.upload = function() {
		//检验是否选择了正确的文件 
		if($('#picfileTitle').val() == null || $('#picfileTitle').val() == ''){
			$.messager.alert('提示','请先选择需要上传的excel文件!','info');
			return;
		}
		//上传
		ajaxUploadPic.submit();
	};

	/**
	 * 导入正确的文件
	 **/
	importWorker.import = function(){
		//导入进度图片出现 
		$('#divImportImg').show();
		//按钮暂时消失
		$('#divImportBtn').hide();
		$.ajax({
			url : '${contextPath}/security/worker/importFromWorkerTemp',
			data : {
				"companyId" : $("#companyId").val(),
				"year" : $("#currentYear").val()
			},
			type : 'post',
			success : function(data) {
				if (data == true) {
					$.messager.alert('消息', '导入成功。', 'ok', function() {
						importWorker.close();
					});

				} else {
					$.messager.alert('消息', '导入残疾职工失败。', 'error', function() {
						importWorkerManager.close();
					});
				}
			},
			error : function() {
				$.messager.alert('消息', '导入残疾职工信息时发生错误。', 'error');
			}
		});
	};
	
	/**
	 * 信息确认div中 取消按钮
	 **/
	importWorker.cancel = function(){
		//上传框出现, 提示框隐藏
		$('#importWorkerDiv').show();
			//上传按钮出现
			$('#divBtn').show();
			//校验进行中提示文字消失
			$('#divLoadingMsg').hide();
		$('#returnMessageDiv').hide();
		//文件框清空
		$('#picfileTitle').val('');
	};
	/**
	 * 关闭
	 **/
	importWorker.close = function() {
		$('#importWorkerWindow').window("close");
	};
	
	
	
</script>

<div class="importWorker" id="importWorkerPan">
	
	<!-- 导入div -->
	<div class="importWorkerDiv" id="importWorkerDiv">
		<span style="text-align:center;width:100%;display:-moz-inline-block;display:inline-block;font-size:20px;font-weight:700;">导入残疾职工</span>
		<table>
			<tr>
				<td>
					<input type="button" name="file" value="选择文件" id="picFileImport"/>
					<input type="text" readonly="readonly" id="picfileTitle" style="border:none;width:350px;color:blue;"/>
				</td>
			</tr>
		</table>
		<input type="hidden" name="companyId" id="currentCompanyId" value="${companyId}" />
		<input type="hidden" name="year" id="currentyear" value="${year}" />
		<div>
			<p>
				<span class="red_notice">*</span>女干部 不要使用导入功能, 请单个录入.<br/>
			</p>
			<p>
				<span class="red_notice">*</span>导入数据条数(残疾员工数)不超过7000条。<br/>
				<span style="font-size:9px;color:red;">&nbsp;&nbsp;&nbsp;&nbsp;数据超过2000条时, 解析会比较慢, 请耐心等待1-4分钟.</span>
			</p>
			<p>
				<span class="red_notice">*</span> 文件必须是：xls, xlsx类型, 格式如下图.
			</p>
	 		<p>
				<img src="${contextPath }/images/example_excelformat.jpg" />
			</p>
		</div>
		<!-- 校验中ing 提示文字 -->
		<div id="divLoadingMsg" style="text-align:center;margin-top:10px;display:none;">
			<img src="${contextPath }/images/processing.gif" title="数据校验中... ..." alt="数据校验中... ..." /><br/>
			数据校验中, 请稍后... ...
		</div>
		<!-- 提交按钮 -->
		<div id="divBtn" style="text-align:center;margin-top:10px;">
			<a href="javascript:importWorker.upload()" class="easyui-linkbutton" iconCls="icon-ok" id="btnUpload">上传</a>
			<a href="javascript:importWorker.close()" class="easyui-linkbutton" iconCls="icon-undo">返回</a>	
		</div>	
	</div>
	
	<!-- 提示信息div -->
	<div class="importWorkerDiv" id="returnMessageDiv" style="display:none;">
		<span style="text-align:center;width:100%;display:-moz-inline-block;display:inline-block;font-size:20px;font-weight:700;">导入残疾职工结果页面</span>
		<div>
			<p>
				职工信息数据总量为：<span id="totalCount"></span>条，
					正确可导入职工信息有：<span id="rightCount"></span>条，
					<span class="red_notice">错误职工信息有:<span id="wrongCount"></span>条。</span>
			</p>
	 		<p id="pWrongPath">
				<a href="" id="wrongPath">点我下载错误列表</a>
			</p>
		</div>
		
		<div id="divImportImg" style="text-align:center;margin-top:10px;display:none;">
			<img src="${contextPath }/images/processing.gif" title="数据导入中... ..." alt="数据导入中... ..." /><br/>
			数据导入中, 请稍后... ...
		</div>
		<!-- 导入按钮 -->
		<div id="divImportBtn" style="text-align:center;margin-top:10px;">
			<a href="javascript:importWorker.import()" class="easyui-linkbutton" iconCls="icon-ok" id="btnImport">导入</a>
			<a href="javascript:importWorker.cancel()" class="easyui-linkbutton" iconCls="icon-undo">返回</a>	
		</div>	
	</div>
	
	
		
</div>

