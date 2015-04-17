<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style type="text/css">
	.div-legelholiday,.div-permissiontypeset{
	}
	.div-legelholiday h3,.div-permissiontypeset h3{
		margin-bottom: 0px;
	}
	.div-legelholiday p,.div-permissiontypeset p{
		color:rgb(144, 144, 144);
		margin: 0px;
		padding: 0 20px;
		font-size: 12px;
	}
	.div-legelholiday ul,.div-permissiontypeset ul{
		margin:0px;
	}
	.div-legelholiday ul li,.div-permissiontypeset ul li{
		list-style:none;
	}
	.div-legelholiday span,.div-permissiontypeset span{
		display:-moz-inline-box;  
		display:inline-block;
	}
	.div-legelholiday input,.div-permissiontypeset input{
		height: 20px;
		line-height: 20px;
		font-size: 12px;
		padding: 0px 5px;
	}
	.div-legelholiday .btnDelete:hover,.div-permissiontypeset  .btnDelete:hover{
		background-color:rgb(167, 167, 226);
		color:white;
		cursor:pointer;
	}
	
</style>
<script type="text/javascript">
	var holidayset = {};
	holidayset.submit = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data == true) {
				$.messager.alert('消息', '修改密码成功', 'info',function(){
					$('#userPassword').val('');
					$('#confirmUserPassword').val('');
				});
			} else {
				$.messager.alert('消息', '修改密码失败', 'info');
			}
		});
	};

	/**
	 * 获取节假日列表
	 */
	holidayset.getholidays = function(){
		$.ajax({
			url:'settings/holidayset/legalholidaylist',
			type:'post',
			dataType:'json',
			data:{},
			success:function(data){
				var content = '';
				//标题
				content += '<li>';
				content += '<span style="width:50px;">序号</span>';
				content += '<span style="width:120px;">假日名称</span>';
				content += '<span style="width:70px;">时间</span>';
				content += '<span style="width:50px;">操作</span>';
				content += '</li>';
				//循环假日列表
				$.each(data.legalHolidayList,function(index,item){
					content += '<li>';
					content += '<span style="width:50px;">' + (index+1) + '</span>';
					content += '<span style="width:120px;" title="'+ item.remark +'">'+ item.name +'</span>';
				//	var date = item.legaldate.substring(0,item.legaldate.indexOf('-')) + '月' + item.legaldate.substring(item.legaldate.indexOf('-')+1) + '日';
					content += '<span style="width:70px;">' + item.legaldate + '</span>';
					content += '<span style="width:50px;"><input class="btnDelete" type="button" onclick="holidayset.deleteholidays(\'' + item.id +'\',\'' + item.name +'\')" value="删 除" /></span>';
					content += '</li>';
				});
				$('.div-legelholiday ul').empty().append(content);
			}
		});
	};
	
	/**
	 * 新增单个节假日
	 */
	holidayset.addholidays = function(holidayname,legaldate){
		$.ajax({
			url:'settings/holidayset/savelegalholiday',
			type:'post',
			data:{'name':holidayname,'legaldate':legaldate},
			dataType:'json',
			success:function(data){
				//删除成功则刷新 列表
				if(data.notice == 'success'){
					holidayset.getholidays();
				}else{
					//失败则进行提示
					alert('删除假期失败, 请联系重新尝试或联系管理员.');
				}
			}
		});
	};
	
	/**
	 * 检查单个 节假日 是否存在
	 */
	holidayset.checkholidays = function(){
		var holidayname = $('#holidayname').val();
		if(holidayname == null || holidayname == ''){
			alert('假期名称不能为空!');
			$('#holidayname').focus();
			return;
		}
		var month = $('#month').val();
		var day = $('#day').val();
		var legaldate = month + day;
		$.ajax({
			url:'settings/holidayset/checkholidays',
			type:'post',
			dataType:'json',
			data:{'legaldate':legaldate},
			success:function(data){
				if(data.notice == 'success'){
					return;
				}else{
					holidayset.addholidays(holidayname,legaldate);
				}
			}
		});
	};
	
	/**
	 * 删除单个节假日
	 */
	holidayset.deleteholidays = function(id,name){
		if(window.confirm('确实要删除' + name + '吗?')){
			$.ajax({
				url:'settings/holidayset/deletelegalholiday/' + id,
				type:'post',
				dataType:'json',
				success:function(data){
					//删除成功则刷新 列表
					if(data.notice == 'success'){
						holidayset.getholidays();
					}else{
						//失败则进行提示
						alert('删除假期失败, 请联系重新尝试或联系管理员.');
					}
				}
			});
		}
	};
	
	/**
	 * 获取 权限允许操作时间列表
	 */
	holidayset.getpermissiontypelist = function(){
		$.ajax({
			url:'settings/holidayset/permissiontypelist',
			type:'post',
			dataType:'json',
			data:{},
			success:function(data){
				var content = '';
				//标题
				content += '<li>';
				content += '<span style="width:50px;">序号</span>';
				content += '<span style="width:90px;">权限操作类别</span>';
				content += '<span style="width:110px;">开始时间</span>';
				content += '<span style="width:100px;">结束时间</span>';
				content += '<span style="width:50px;">操作</span>';
				content += '</li>';
				//循环假日列表
				$.each(data.permissionTypeList,function(index,item){
					content += '<li>';
					content += '<span style="width:50px;">' + (index+1) + '</span>';
					//权限名称
					content += '<span style="width:90px;" title="'+ item.permissionType +'">'+ item.permissionType +'</span>';
					var shour = item.startTime.substring(0,2);	//起始小时
					var sminute = item.startTime.substring(2,4);	//起始分钟
					content += '<span style="width:110px;">';
					//起始小时下拉框
					content +='<select>';
					for(var i=0;i<=23;i++){
						if(i<10){
							i = '0' + i;
						}
						content += '<option value="'+i + '"';
						if(shour == i){
							content += ' selected="selected"';
						}
						content += '>'+i+'</option>';
					}
					content += '</select> : ';
					//起始分钟下拉框
					content +='<select>';
					for(var i=0;i<=59;i++){
						if(i<10){
							i = '0' + i;
						}
						content += '<option value="'+i + '"';
						if(sminute == i){
							content += ' selected="selected"';
						}
						content += '>'+i+'</option>';
					}
					content += '</select>~';
					content += '</span>';
					var ehour = item.endTime.substring(0,2);	//结束小时
					var eminute = item.endTime.substring(2,4);	//结束分钟
					content += '<span style="width:100px;">';
					//结束小时下拉框
					content +='<select>';
					for(var i=0;i<=23;i++){
						if(i<10){
							i = '0' + i;
						}
						content += '<option value="'+i + '"';
						if(ehour == i){
							content += ' selected="selected"';
						}
						content += '>'+i+'</option>';
					}
					content += '</select> : ';
					//结束分钟下拉框
					content +='<select>';
					for(var i=0;i<=59;i++){
						if(i<10){
							i = '0' + i;
						}
						content += '<option value="'+i + '"';
						if(eminute == i){
							content += ' selected="selected"';
						}
						content += '>'+i+'</option>';
					}
					content += '</select>';
					content += '</span>';
				//	var date = item.legaldate.substring(0,item.legaldate.indexOf('-')) + '月' + item.legaldate.substring(item.legaldate.indexOf('-')+1) + '日';
					content += '<span style="width:50px;"><input class="btnDelete" type="button" onclick="holidayset.updatepermissiontype(this,\'' + item.id + '\')" value="更 新" /></span>';
					content += '</li>';
				});
				$('.div-permissiontypeset ul').empty().append(content);
			}
		});
	};
	
	/**
	 * 更新 权限允许操作时间
	 */
	holidayset.updatepermissiontype = function(obj,id){
		//获得开始时间
		var shour = $(obj).parent().parent().children('span').eq(2).find('select').eq(0).val();
		var sminute = $(obj).parent().parent().children('span').eq(2).find('select').eq(1).val();
		//获得结束时间
		var ehour = $(obj).parent().parent().children('span').eq(3).find('select').eq(0).val();
		var eminute = $(obj).parent().parent().children('span').eq(3).find('select').eq(1).val();
		var startTime = shour + sminute;
		var endTime= ehour + eminute;
		//起始时间不能晚于结束时间
	//	alert(startTime + '\t' + endTime);
		if(startTime>=endTime){
			alert('结束时间不能早于开始时间, 请重新设定!');
			return;
		}
		//更新
		$.ajax({
			url:'settings/holidayset/updatepermissiontype',
			type:'post',
			dataType:'json',
			data:{'id':id,'startTime':startTime,'endTime':endTime},
			success:function(data){
				if(data.notice != 'success'){
					alert('更新时间失败, 请联系重新尝试或联系管理员.');
				}
			}
		});
	};
	$(function() {
		//初始法定假日列表
		holidayset.getholidays();
		
		//初始权限类别操作时间列表
		holidayset.getpermissiontypelist();
	});
</script>



<!-- 自定义菜单 -->
<div style="padding: 30px;">
	
	<div class="div-legelholiday">
		<h3>法定假日设定</h3>
		<p>以下列表中的时间为法定休息日, 期间都不可以进行操作.</p>
		<div class="div-legelholiday-add" style="margin-left: 35px;">
			<span style="width:120px;"><input type="text" style="width:110px;" id="holidayname" name="holidayname" /></span>
			<span >
				<select id="month" name="month">
					<c:forEach begin="1" end="12" step="1" var="item">
						<option value="${item }月">${item }月</option>
					</c:forEach>
				</select>
				<select id="day" name="day">
					<c:forEach begin="1" end="31" step="1" var="item">
						<option value="${item }日">${item }日</option>
					</c:forEach>
				</select>
			</span>
			<span style="width:50px;"><input type="button" class="btnDelete" onclick="holidayset.checkholidays();" value="新 增" /></span>
		</div>
		<ul>
		</ul>
	</div>
	
	<div class="div-permissiontypeset">
		<h3>权限操作时间设定</h3>
		<p>以下操作权限, 只有在设定的时间段内才可以进行操作.</p>
		<ul>
		</ul>
	</div>
</div>

