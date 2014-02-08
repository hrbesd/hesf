<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	/*
		残疾职工页面
	 */
	addWorker = {};
	/**
	关闭增残疾职工信息窗口
	**/
	addWorker.close = function() {
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
		$("#workerGender").combobox("setValue", "0");//性别
		$("#workerHandicapType").combobox("setValue", "1");//残疾类别
		$("#workerHandicapLevel").combobox("setValue", "1");//残疾等级
	};
	/**
		校验信息  并  保存残疾职工信息
	 **/
	addWorker.validate = function() {
		if (esd.common.validatebox("#addWorkerForm") == false) {
			return;
		}
		//获取残疾证号
		var workerHandicapCode = $("#workerHandicapCode").val();
		//校验残疾证号是否存在，是否在其他公司
		$.ajax({
			url : 'worker/validate_workerHandicapCode',
			type : 'post',
			data : {
				'workerIdCard' :($("#workerHandicapCode").val()).substring(0, 18)
			},
			success : function(data) {
				//第一种情况， 员工存在，并在其他公司内
				if (data[0].type == "1") {
					$.messager.alert("警告：该员工已被其他公司录用", "员工残疾证号： “" + $("#workerHandicapCode").val() + "”已被：\n ”" + data[1].companyName + "“\n公司录用。公司档案编码为：" + data[1].companyCode);
					return;
					//第二种情况，员工存在，不再任何公司
				} else if (data[0].type == "2") {
					//更改表单路径为编辑路径
					$("#addWorkerForm").attr('action', "worker/updata");
					addWorker.save();
					return;
					//第三种情况，员工不存在
				} else if (data[0].type == "3") {
					//更改表单路径为增加路径
					$("#addWorkerForm").attr('action', "worker/add");
					addWorker.save();
					return;
				}
			},
			error : function() {
				alet("增加残疾职工校验时发生错误。");
			}
		});
	};

	/**
			保存增加残疾职工信息
	 **/
	addWorker.save = function() {
		var workerHandicapCode = $("#workerHandicapCode").val();
		
		//数据验证可以通过
		esd.common.syncPostSubmit("#addWorkerForm", function(data) {
			if (data == true) {
				alert("残疾职工信息增加成功。");
				addWorker.close();
				$('#workerList_dataGrid').datagrid("load");
			} else {
				alert("单位信息增加失败。");
			}
		});
	};
	/**
		获取残疾职工信息 并校验
	 **/
	addWorker.getData = function() {
		//校验残疾证号长度是否符合
		var b = $("#workerHandicapCode").validatebox('isValid');
		if (b == false) {
			return;
		}
		//获取残疾证号
		var workerHandicapCode = $("#workerHandicapCode").val();
		
		//根据残疾证号获取出生日期
		var year = workerHandicapCode.substring(6, 10);//年份
		var month = workerHandicapCode.substring(10, 12);//月
		var day = workerHandicapCode.substring(12, 14);//日
		//根据残疾证号获取性别
		var sex = workerHandicapCode.substring(16, 17);
		if (sex % 2 === 0) {
			//偶数
			$("#workerGender").combobox("setValue", "1");
		} else {
			//基数
			$("#workerGender").combobox("setValue", "0");

		}
		//出生日期
		$("#workerBirth").val(year + "-" + month + "-" + day);
		//身份证号
		var workerIdCard = ($("#workerHandicapCode").val()).substring(0, 18);
		$("#workerIdCard").val(workerIdCard);

		//远程校验
		//校验残疾证号是否存在，是否在其他公司
		$.ajax({
			url : 'worker/validate_workerHandicapCode',
			type : 'post',
			data : {
				'workerIdCard' : workerIdCard
			},
			success : function(data) {
				//第一种情况， 员工存在，并在其他公司内
				if (data[0].type == "1") {
					$.messager.alert("警告：该员工已被其他公司录用", "残疾证号： “" + $("#workerHandicapCode").val() + "”已被：\n ”" + data[1].companyName + "“\n公司录用。公司档案编码为：" + data[1].companyCode);
					return;
					//第二种情况，员工存在，不再任何公司
				} else {
					alert("验证通过，可以注册");
					return;
				}
			},
			error : function() {
				alet("增加残疾职工校验时发生错误。");
			}
		});
	};

</script>





<form id="addWorkerForm" action="security/worker/add" method="post">
	<input type="hidden" value="${companyId}" name="companyId"  style="margin-top: 40px;"/>
	<!-- 数据表格 -->
	<table id="company_information" align="center">
		<tr>
			<td align="center"><span style="font-size: 18px;font-weight: bold;">新增残疾人</span></td>
		</tr>
		<tr>
			<td>残疾证号:</td>
			<td colspan="5">

				<div style="float: left;width: 600px;">
					<input class="easyui-validatebox" type="text" id="workerHandicapCode" value="93230119880529463700" name="workerHandicapCode" data-options="required:true,validType:['_length[20]']"
						style="width: 200px" /> <input type="hidden" name="workerIdCard" id="workerIdCard" /> <a href="javascript:addWorker.getData()" class="easyui-linkbutton" iconCls="icon-search">调取残疾人信息</a> <a
						href="javascript:addWorker.empty()" class="easyui-linkbutton" iconCls="icon-reload">清空</a>
				</div>
			</td>
		</tr>
		<tr>
			<td class="">姓名:</td>
			<td><input class="easyui-validatebox" type="text" name="workerName" id="workerName" data-options="required:true" /></td>
			<td class="">性别:</td>
			<td><select name="workerGender" id="workerGender" class="easyui-combobox" data-options="disabled:'true'">
					<option value="0">男</option>
					<option value="1">女</option>
			</select></td>
			<td class="">出生日期:</td>
			<td><input class="easyui-validatebox" type="text" class="easyui-datebox" data-options="required:true,disabled:true" id="workerBirth" name="workerBirth"></input>
			</td>


		</tr>
		<tr>
			<td class="">就业证号:</td>
			<td><input class="easyui-validatebox" type="text" name="careerCard" id="careerCard" /></td>
			<td class="">残疾类别:</td>
			<td><input class="easyui-combobox" type="text" name="workerHandicapType.id" id="workerHandicapType" value="1"
				data-options="disabled:'true',required:true,editable:false,valueField:'id',textField:'handicapType',url:'parameter/workerHandicapTypeService'" /></td>
			<td class="">残疾等级:</td>
			<td><input class="easyui-combobox" type="text" name="workerHandicapLevel.id" id="workerHandicapLevel" value="1"
				data-options="disabled:'true',required:true,editable:false,valueField:'id',textField:'handicapLevel',url:'parameter/workerHandicapLevelService'" /></td>
		</tr>
		<tr>
			<td class="">联系电话:</td>
			<td><input class="easyui-validatebox" type="text" name="phone" id="phone" /></td>
			<td class="">现任岗位:</td>
			<td><input class="easyui-validatebox" type="text" name="currentJob" id="currentJob" /></td>
		</tr>
		<tr>
			<td class="">备注:</td>
			<td colspan="5"><textarea rows="4" cols="100" name="remark" id="remark"></textarea></td>
		</tr>
		<!-- 
		<tr>
			<td class="">重复性验证:</td>
			<td><input class="" type="text" id="verification" data-options="validType:['_number']" class="easyui-validatebox" />
				<button>验证</button></td>
		</tr>
	 -->
		<tr>
			<td colspan="6"><a href="javascript:addWorker.validate();" class="easyui-linkbutton" iconCls="icon-ok">保存</a> <a href="javascript:addWorker.close();" class="easyui-linkbutton"
				iconCls="icon-undo">取消</a></td>
		</tr>
	</table>
</form>




