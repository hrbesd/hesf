<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style type="text/css">
	.addWorkerIframe{
		width: 100%;
		margin-top: 0px;
		height: 30px;
	}
</style>
<script type="text/javascript">
	/*
		残疾职工页面
	 */
	addWorker = {};
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
	/**
		校验信息  并  保存残疾职工信息
	 **/
	addWorker.validate = function() {
	
		//获取残疾证号
		var workerHandicapCode = $("#workerHandicapCode").val();
		//根据残疾证号初始化其他组件
		if(addWorker.initElement(workerHandicapCode)==false){
			return false;
		}
		//校验表单
		if (esd.common.validatebox("#addWorkerForm") == false) {
			return false;
		}
		//校验残疾证号是否存在，是否在其他公司
		$.ajax({
			url : '${contextPath}/security/worker/validate_workerHandicapCode',
			type : 'post',
			data : {
				'workerIdCard' : ($("#workerHandicapCode").val()).substring(0, 18),
				'year':$("#nowYear").val()
			},
			success : function(data) {
				//第一种情况， 员工存在，并在其他公司内
				if (data[0].type == "1") {
					$('#win').window(
							{
								title : '警告：该员工已被其他公司录用',
								height : 180,
								width : 650,
								content : "<div class='addtip'><span >证号：“" + $("#workerIdCard").val() + "”已被：“" + data[1].companyName + "”录用。</span>\n"
										+ "\n<table  border='1' > <tr><th>档案编码</th><th>税务编码</th><th>企业名称</th></tr><tr><td>" + data[1].companyCode + "</td> <td>"
										+ data[1].companyTaxCode + "</td><td style='width: 450px;'>" + data[1].companyName + "</td></tr></table><div>",
								modal : true,
								collapsible : false,
								minimizable : false,
								maximizable : false
							});
					return false;
					//第二种情况，员工存在，不再任何公司
				} else if (data[0].type == "2") {
					//更改表单路径为编辑路径
					$("#addWorkerForm").attr('action', "worker/updata");
				//	addWorker.save();
					return false;
					//第三种情况，员工不存在
				} else if (data[0].type == "3") {
					//更改表单路径为增加路径
					$("#addWorkerForm").attr('action', "worker/add");
				//	addWorker.save();
					return true;
				}
			},
			error : function() {
				alert("增加残疾职工校验时发生错误。");
				return false;
			}
		});	
		return true;
	};

	/**
		保存增加残疾职工信息
	 **/
	addWorker.save = function() {
		$('#addWorkerForm').submit();
	//	addWorker.close();
	//	$('#workerList_dataGrid').datagrid("load");
	};
	
	addWorker.initElement=function(workerHandicapCode){

		//校验表单
		if (esd.common.validatebox("#addWorkerForm") == false) {
			return false;
		}
		//非法字符判断
		var workerVal = workerHandicapCode.substring(0, 20);
		if(isNaN(workerVal)){
		   alert("残疾证号前20未有非法字符。");
		   return false;
		}

		
		
		//获取残疾类型 
		var workerType = workerHandicapCode.substring(18, 19);
		$("#workerHandicapType").combobox("setValue", workerType);
		if (workerType == 0 || workerType>7) {
		
			$.messager.alert('消息', '残疾证号内残疾类型错误。', 'error');
			return false;
		}
		//获取残疾等级
		var workerLeven = workerHandicapCode.substring(19, 20);
		$("#workerHandicapLevel").combobox("setValue", workerLeven);
		if (workerLeven == 0 || workerLeven>4 ) {
			
			$.messager.alert('消息', '残疾证号内残疾等级错误。', 'error');
			return false;
		}
		//根据残疾证号获取出生日期
		var year = workerHandicapCode.substring(6, 10);//年份
		var month = workerHandicapCode.substring(10, 12);//月
		var day = workerHandicapCode.substring(12, 14);//日
		//根据残疾证号获取性别
		var sex = workerHandicapCode.substring(16, 17);
		//职工当前年龄
		var age=$("#nowYear").val()-year+1;

		//判断年龄
		if(age<16){
				$.messager.alert('消息', '职工年龄过小，不能录入。', 'error');
				return false;
		}
		if (sex % 2 === 0) {
			//偶数 女性职工
			$("#workerGender").combobox("setValue", "0");
			if(age > $("#retireAgeFemale").val()){
				$.messager.alert('消息', '职工年龄：'+age+'岁，性别：女性。已超过退休年龄', 'error');
	
				return false;
			}
		} else {
			//基数 男性
			$("#workerGender").combobox("setValue", "1");
				if(age > $("#retireAgeMale").val()){
					$.messager.alert('消息', '职工年龄：'+age+'岁，性别：男性。已超过退休年龄', 'error');
				
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
		残疾证号校验并校验
	 **/
	addWorker.handicapCodeValidate = function() {
		//校验表单
	//	var b = $("#workerHandicapCode").validatebox('isValid');
	//	if (b == false) {
	//		return;
	//	}
		//获取残疾证号
		var workerHandicapCode = $("#workerHandicapCode").val();
		//身份证号
		var workerIdCard = ($("#workerHandicapCode").val()).substring(0, 18);
		//根据残疾证号初始化其他组件
		addWorker.initElement(workerHandicapCode);
	
	

		//远程校验 校验残疾证号是否存在，是否在其他公司
		$.ajax({
			url : 'worker/validate_workerHandicapCode',
			type : 'post',
			data : {
				'workerIdCard' : workerIdCard,
					'year':$("#nowYear").val()
			},
			success : function(data) {
				//第一种情况， 员工存在，并在其他公司内
				if (data[0].type == "1") {
					$('#win').window(
							{
								title : '警告：该员工已被其他公司录用',
								height : 180,
								width : 650,
								content :  "<div class='addtip'><span >证号：“" + $("#workerIdCard").val() + "”已被：“" + data[1].companyName + "”录用。</span>\n"
										+ "\n<table  border='1' > <tr><th>档案编码</th><th>税务编码</th><th>企业名称</th></tr><tr><td>" + data[1].companyCode + "</td> <td>"
										+ data[1].companyTaxCode + "</td><td style='width: 450px;'>" + data[1].companyName + "</td></tr></table><div>",
								modal : true,
								collapsible : false,
								minimizable : false,
								maximizable : false
							});
					return;
					//第二种情况，员工存在，不再任何公司
				} else if(data[0].type == '2') {
					//将员工相关控件信息补全
					$("#addWorkerForm #workerName").val(data[0].workerName);//姓名
					$("#addWorkerForm #careerCard").val(data[0].careerCard);//就业证号
					$("#addWorkerForm #phone").val(data[0].phone);//电话
					$("#addWorkerForm #remark").val(data[0].remark);//备注
					$.messager.alert('消息', '验证通过，可以注册', 'ok');
					return;
				}
			},
			error : function() {
				$.messager.alert('消息', '增加残疾职工校验时发生错误。', 'error');
			
			}
		});
	};
	//组件解析完成
	$.parser.onComplete = function() {
		
		$('#isUpload').click(function(){
			var checked = $('#isUpload').attr('checked');
			if(checked){
				$('#file').show();
			}else{
				$('#file').hide();
			}
		});
	};
</script>

<!-- 导入成功隐藏 -->
<c:if test="${notice != 'success' || notice != 'failure'}">
	<div id="addWorkerDiv">
		<form id="addWorkerForm" action="worker/add" method="post" class="addWorkerForm" target="addWorkerIframe" enctype="multipart/form-data" onsubmit="return addWorker.validate();">
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
					<td class="">姓名(<label class="red_notice"> *</label>):</td>
					<td><input class="easyui-validatebox" type="text" name="workerName" id="workerName" data-options="required:true" />
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
					<td class="">上传照片:</td>
					<td colspan="5">
						<input type="checkbox" id="isUpload" style="height:auto;" />
						<input type="file" name="file" id="file" value="tudou" style="height:19px;line-height:19px;border:none;width:200px;display:none;" />
					</td>
				</tr>
				<tr>
					<td class="">备注:</td>
					<td colspan="5"><textarea rows="4" cols="100" name="remark" id="remark"></textarea>
					</td>
				</tr>
				<!-- 
				<tr>
					<td class="">重复性验证:</td>
					<td><input class="" type="text" id="verification" data-options="validType:['_number']" class="easyui-validatebox" />
						<button>验证</button></td>
				</tr>
			 -->
				<tr>
					<td colspan="6" style="text-align: center;">
						<a href="javascript:addWorker.save();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
						<a href="javascript:addWorker.close();" class="easyui-linkbutton" iconCls="icon-undo">取消</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</c:if>

<!-- 导入结果显示 -->
<iframe name="addWorkerIframe" id="addWorkerIframe" class="addWorkerIframe" frameborder="0"> 5654564</iframe>
