/**
 * Copyright (c) 2013 北京海达网畅信息技术开发有限公司（www.hadventure.com）. All rights reserved.
 * 
 * HADVENTURE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * @author zhangjianzong
 * @作用：账户相关的所有js
 */
$(document).ready(function() {
	ps.bindRegister();// 绑定注册js
	ps.bindForgot();// 绑定找回密码页面js
	ps.bindModify();// 绑定修改密码页面js
});
// 修改密码页面==============================================================
ps.modify = {};
ps.bindModify = function() {
	if ($("#modify").size() > 0) {
		ps.modify.addAttr();
		ps.modify.submit();
	}
};
ps.modify.addAttr = function() {
	$("#modify input[name=password]").attr("onblur", "ps.register.check_password(value)");
	$("#modify input[name=password]").attr("maxlength", "64");// 限制64

	$("#modify input[name=confirm]").attr("onblur", "ps.register.check_confirm(value)");
	$("#modify input[name=confirm]").attr("maxlength", "64");// 限制64
};
ps.modify.submit = function() {
	$("#modify").submit(function() {
		var password = $("#modify input[name=password]").val();
		if (ps.register.check_password(password) == false) {
			return false;
		}
		var confirm = $("#modify input[name=confirm]").val();
		if (ps.register.check_confirm(confirm) == false) {
			return false;
		}
		return true;
	});
};
// 找回密码页面==============================================================
ps.forgot = {};
ps.bindForgot = function() {
	if ($("#forgot").size() > 0) {
		ps.forgot.addAttr();
		ps.forgot.submit();
	}
};
ps.forgot.addAttr = function() {
	$("#forgot input[name=mobile]").attr("onblur", "ps.register.check_mobile2(value,true)");
	$("#forgot input[name=mobile]").attr("onkeyup", "value=value.replace(/[^\\d]/g,'')");
	$("#forgot input[name=mobile]").attr("onmouseup", "value=value.replace(/[^\\d]/g,'')");
	$("#forgot input[name=mobile]").attr("maxlength", "11");// 限制32

	$("#forgot input[name=sendcode]").attr("onclick", "ps.register.send_mobileCode()");// 发送验证码

	$("#forgot input[name=mobileCode]").attr("onblur", "ps.register.check_mobileCode(value,true)");
	$("#forgot input[name=mobileCode]").attr("onkeyup", "value=value.replace(/[^\\d]/g,'')");
	$("#forgot input[name=mobileCode]").attr("onmouseup", "value=value.replace(/[^\\d]/g,'')");
	$("#forgot input[name=mobileCode]").attr("maxlength", "6");// 限制32

};
ps.forgot.submit = function() {
	$("#forgot").submit(function() {
		var mobile = $("#forgot input[name=mobile]").val();
		if (ps.register.check_mobile2(mobile,false) == false) {
			return false;
		}
		var mobileCode = $("#forgot input[name=mobileCode]").val();
		if (ps.register.check_mobileCode(mobileCode,false) == false) {
			return false;
		}

		return true;
	});
};
// 注册密码页面==============================================================
ps.register = {};// 注册类
ps.register.successMsg = "";
ps.register.errorMsg = "";
ps.bindRegister = function() {
	// 限制输入
	ps.register.addAttr();
	// 提交
	$("#register").submit(function() {
		var params = {};
		params.invitation = $("#register input[name=invitation]").val();
		params.realName = $("#register input[name=realName]").val();
		params.idCardNumber = $("#register input[name=idCardNumber]").val();
		params.email = $("#register input[name=email]").val();
		params.name = $("#register input[name=name]").val();
		params.password = $("#register input[name=password]").val();
		params.confirm = $("#register input[name=confirm]").val();
		params.mobile = $("#register input[name=mobile]").val();
		params.mobileCode = $("#register input[name=mobileCode]").val();
		if (ps.register.verify(params) == true) {
			ps.register.submit(params);
		}
		return false;
	});
};
/**
 * 添加校验属性
 */
ps.register.addAttr = function() {
	$("#register input[name=invitation]").attr("onblur", "ps.register.check_invitation(value,true)");
	$("#register input[name=invitation]").attr("maxlength", "64");
	// 真实姓名
	$("#register input[name=realName]").attr("onblur", "ps.register.check_realName(value)");
	$("#register input[name=realName]").attr("maxlength", "32");// 限制32

	// 身份证号
	$("#register input[name=idCardNumber]").attr("onblur", "ps.register.check_idCardNumber(value)");
	$("#register input[name=idCardNumber]").attr("onkeyup", "value=value.replace(/[^\\d]/g,'')");
	$("#register input[name=idCardNumber]").attr("onmouseup", "value=value.replace(/[^\\d]/g,'')");
	$("#register input[name=idCardNumber]").attr("maxlength", "18");// 限制18
	// 邮箱
	$("#register input[name=email]").attr("onblur", "ps.register.check_email(value,true)");
	$("#register input[name=email]").attr("maxlength", "32");// 限制32
	// 账号
	$("#register input[name=name]").attr("onblur", "ps.register.check_name(value,true)");
	$("#register input[name=name]").attr("maxlength", "15");// 限制32

	$("#register input[name=password]").attr("onblur", "ps.register.check_password(value)");
	$("#register input[name=password]").attr("onkeyup", "isSecurity(value);");
	$("#register input[name=password]").attr("maxlength", "32");// 限制64

	$("#register input[name=confirm]").attr("onblur", "ps.register.check_confirm(value)");
	$("#register input[name=confirm]").attr("maxlength", "32");// 限制64

	$("#register input[name=mobile]").attr("onblur", "ps.register.check_mobile(value,true)");
	$("#register input[name=mobile]").attr("onkeyup", "value=value.replace(/[^\\d]/g,'')");
	$("#register input[name=mobile]").attr("onmouseup", "value=value.replace(/[^\\d]/g,'')");
	$("#register input[name=mobile]").attr("maxlength", "11");// 限制32

	$("#register input[name=sendcode]").attr("onclick", "ps.register.send_mobileCode()");// 发送验证码

	$("#register input[name=mobileCode]").attr("onblur", "ps.register.check_mobileCode(value,true)");
	$("#register input[name=mobileCode]").attr("onkeyup", "value=value.replace(/[^\\d]/g,'')");
	$("#register input[name=mobileCode]").attr("onmouseup", "value=value.replace(/[^\\d]/g,'')");
	$("#register input[name=mobileCode]").attr("maxlength", "6");// 限制32
	$("#register input[name=mobileCode]").attr("disabled", true);
	$("#register input[name=mobileCode]").attr("readonly", true);

};
/**
 * 批量校验
 */
ps.register.verify = function(params) {
	if (ps.register.check_invitation(params.invitation,false) == false) {
		return false;
	}
	if (ps.register.check_realName(params.realName) == false) {
		return false;
	}
	if (ps.register.check_idCardNumber(params.idCardNumber) == false) {
		return false;
	}
	if (ps.register.check_email(params.email,false) == false) {
		return false;
	}
	if (ps.register.check_name(params.name,false) == false) {
		return false;
	}
	if (ps.register.check_password(params.password) == false) {
		return false;
	}
	if (ps.register.check_confirm(params.confirm) == false) {
		return false;
	}
	if (ps.register.check_mobile(params.mobile,false) == false) {
		return false;
	}
	if (ps.register.check_mobileCode(params.mobileCode,false) == false) {
		return false;
	}
	return true;
};
/**
 * 提交注册
 */
ps.register.submit = function() {
	var url = ps.url + "/register";
	$.ajax({
		url : url,
		type : "POST",
		data : params,
		success : function(e) {
			alert("注册状态" + e);
		},
		error : ps.error,
		dataType : "json",
		async : true
	});
};
/**
 * 检测邀请码
 */
ps.register.check_invitation = function(val,async) {
	if (verify.isNull(val) == true) {
		$("#check_invitation").html(" × 邀请码不能为空");
		return false;
	}
	var s = false;
	var url = ps.url + "/check/check_invitation";
	$.ajax({
		url : url,
		type : "POST",
		data : {
			invitation : val
		},
		success : function(e) {
			if (e == true) {
				$("#check_invitation").html(ps.register.successMsg);
			} else {
				$("#check_invitation").html(" × 邀请码不存在");
			}
			s = e;
		},
		error : ps.error,
		dataType : "json",
		async : async
	});
	return s;
};
/**
 * 检测邮箱
 */
ps.register.check_email = function(val,async) {
	if (verify.isNull(val) == true) {
		$("#check_email").html(" × 邮箱不能为空");
		return false;
	}
	if (verify.isEmail(val) == false) {
		$("#check_email").html(" × 邮箱不合法");
		return false;
	}
	var url = ps.url + "/check/check_email";
	var s = false;
	$.ajax({
		url : url,
		type : "POST",
		data : {
			email : val
		},
		success : function(e) {
			if (e == true) {
				$("#check_email").html(" × 邮箱已被注册");
				s = false;
			} else {
				$("#check_email").html(ps.register.successMsg);
				s = true;
			}
		},
		error : ps.error,
		dataType : "json",
		async : async
	});
	return s;
};
/**
 * q 检测姓名
 */
ps.register.check_name = function(val,async) {
	if (verify.isNull(val) == true) {
		$("#check_name").html(" × 用户名不能为空");
		return false;
	}
	var url = ps.url + "/check/check_name";
	var s = false;
	$.ajax({
		url : url,
		type : "POST",
		data : {
			name : val
		},
		success : function(e) {
			if (e == true) {
				$("#check_name").html(" × 用户名已被注册");
				s = false;
			} else {
				$("#check_name").html(ps.register.successMsg);
				s = true;
			}
		},
		error : ps.error,
		dataType : "json",
		async : async
	});
	return s;
};
/**
 * 检测手机
 */
ps.register.check_mobile = function(val,async) {
	if (verify.isNull(val) == true) {
		$("#check_mobile").html(" × 手机号码不能为空");
		return false;
	} else if (verify.getByteLe(val) != 11) {
		$("#check_mobile").html(" × 手机号码必须等于11位");
		return false;
	}
	var url = ps.url + "/check/check_mobile/" + val;
	var s = false;
	$.ajax({
		url : url,
		type : "GET",
		success : function(e) {
			if (("#register").length > 0) {
				if (e == true) {
					$("#check_mobile").html(" × 手机号码以存在");
					s = false;
				} else {
					$("#check_mobile").html(ps.register.successMsg);
					s = true;
					alert("fff");
				}
			}
		},
		error : ps.error,
		dataType : "json",
		async : false
	});
	if (s == true) {
		$("input[name=sendcode]").attr("disabled", false);
		$("input[name=mobileCode]").attr("disabled", false);
		$("#register input[name=mobileCode]").attr("readonly", false);
	} else {
		$("input[name=sendcode]").attr("disabled", true);
		$("input[name=mobileCode]").attr("disabled", true);
		$("#register input[name=mobileCode]").attr("readonly", true);
	}
	return s;

};
/**
 * 检测手机2
 */
ps.register.check_mobile2 = function(val,async) {
	if (verify.isNull(val) == true) {
		$("#check_mobile").html(" × 手机号码不能为空");
		return false;
	} else if (verify.getByteLe(val) != 11) {
		$("#check_mobile").html(" × 手机号码必须等于11位");
		return false;
	}
	var url = ps.url + "/check/check_mobile/" + val;
	var s = false;
	$.ajax({
		url : url,
		type : "GET",
		success : function(e) {
			if (("#forgot").length > 0) {
				if (e == true) {
					$("#check_mobile").html(ps.register.successMsg);
				} else {
					$("#check_mobile").html(" × 手机号码不存在");
				}
				s = e;
			}
		},
		error : ps.error,
		dataType : "json",
		async : async
	});
	if (s == true) {
		$("input[name=sendcode]").attr("disabled", false);
	}
	return s;

};
/**
 * 发送手机验证码
 */
ps.register.send_mobileCode = function() {
	var mobile = $("input[name=mobile]").val();
	// 为空不发送请求
	if (mobile == "" || mobile == null || mobile == undefined) {
		return;
	}
	var url = ps.url + "/check/sendcheck/" + mobile;
	$.ajax({
		url : url,
		type : "GET",
		success : function(e) {
			if (e == false) {
				alert("每次只能发送3次验证码");
			} else {
				$("input[name=sendcode]").attr("disabled", "disabled");
				util.timer(180, ps.register.callBack);
			}
		},
		error : ps.error,
		dataType : "json",
		async : true
	});
};

/**
 * 检测手机验证码
 */
ps.register.check_mobileCode = function(val,async) {
	if (verify.isNull(val) == true) {
		$("#check_mobileCode").html(" × 手机验证码不能为空");
		return false;
	} else if (verify.getByteLe(val) != 6) {
		$("#check_mobileCode").html(" × 手机验证码必须等于6位");
		return false;
	}
	var mobile = $("input[name=mobile]").val();
	if (mobile == "" || mobile == undefined) {
		$("#check_mobileCode").html(" × 必须填写手机号");
	}
	var url = ps.url + "/check/check_mobileCode/" + mobile + "/" + val;
	var s = false;
	$.ajax({
		url : url,
		type : "GET",
		success : function(e) {
			if (e == true) {
				$("#check_mobileCode").html(ps.register.successMsg);
			} else {
				$("#check_mobileCode").html(" × 验证码过期或以失效");
			}
			s = e;
		},
		error : function() {
			$("#check_mobileCode").html(" × 验证码过期或以失效");
		},
		dataType : "json",
		async : async
	});
	return s;
};
/**
 * 检测输入密码
 */
ps.register.check_password = function(val) {
	if (verify.isNull(val) == true) {
		$("#check_password").html(" × 密码不能为空");
		return false;
	} else if (verify.getByteLe(val) < 6) {
		$("#check_password").html(" × 密码不能小于6位");
		return false;
	} else {
		$("#check_password").html(ps.register.successMsg);
	}
	return true;
};
/**
 * 检测密码确认
 */
ps.register.check_confirm = function(val) {
	var password = $("input[name=password]").val();
	var confirm = $("input[name=confirm]").val();
	if (confirm != password) {
		$("#check_confirm").html(" × 两次输入的密码不一致");
		return false;
	} else {
		$("#check_confirm").html(ps.register.successMsg);
	}
	return true;
};

/**
 * 检测真实姓名
 */
ps.register.check_realName = function(val) {
	if (verify.isNull(val) == true) {
		$("#check_realName").html(" × 真实姓名不能为空");
		return false;
	} else {
		$("#check_realName").html(ps.register.successMsg);
	}
	return true;
};
/**
 * 检测身份证号
 */
ps.register.check_idCardNumber = function(val) {
	if (verify.isNull(val) == true) {
		$("#check_idCardNumber").html(" × 身份证号不能为空");
		return false;
	} else if (verify.getByteLe(val) < 16) {
		$("#check_idCardNumber").html(" × 身份证号不能小于16位");
		return false;
	} else {
		$("#check_idCardNumber").html(ps.register.successMsg);
	}
	return true;
};
/**
 * 发送验证码倒记时回调
 */
ps.register.timeCallBack = function() {
	$("input[name=sendcode]").attr("disabled", false);
	$("input[name=sendcode]").val("发送验证码");
};