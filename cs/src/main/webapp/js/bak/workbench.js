/**
 * Copyright (c) 2013 北京海达网畅信息技术开发有限公司（www.hadventure.com）. All rights reserved.
 * 
 * HADVENTURE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * @author zhangjianzong
 */
$(document).ready(function() {
	workbench.pullwork();
});

var workbench = {};
workbench.url = "/ps";
workbench.work = null; // 任务实体
//workbench.imageIp = "http://114.246.157.47:9080/storage/storage/image/";
workbench.SUBMIT_STATUS_SUCCESS = "提交成功";
workbench.SUBMIT_STATUS_ERROR = "提交失败";
workbench.SUBMIT_STATUS_REPEAT = "重复提交";
workbench.SUBMIT_STATUS_ILLEGAL = "非法提交";
workbench.SUBMIT_STATUS_TIMEOUT = "超时提交";
/**
 * 跳转到任务大厅
 */
workbench.forword = function() {
	window.top.location.href = workbench.url + "/worker/category";
};

/**
 * 消息提示
 */
workbench.msg = function(e) {
	$.unblockUI();
	$.blockUI({
		message : "<h3>" + e + "</h3>"
	});
};
workbench.submit = function() {
	$.unblockUI();
	$.blockUI({
		message : "<form onSubmit=\"workbench.pushwork();\" onreset=\"$.unblockUI();\" ><div class=\"zon_work_tcwz\">确定提交当前任务？</div>" + "<div class=\"zon_work_tcwz\">"
				+ "<input value=\"确 定\" type=\"submit\" />" + "<input value=\"取 消\" type=\"reset\" />" + "</div></form>"
	});
};

/**
 * 获取一个任务
 */
workbench.pullwork = function() {
	workbench.msg("正在努力加载任务");
	var url = workbench.url + "/worker/workbench/pullwork";
	$.ajax({
		url : url,
		type : "POST",
		success : workbench.pullCallback,
		error : workbench.error,
		dataType : "json",
		async : false
	});

};
/**
 * 获取任务的回调
 */

workbench.pullCallback = function(result) {
	workbench.work = result;
	var ary = new Array();
	for ( var i = 0; i < result.list.length; i++) {
		$("#scan").append("<td><img id=" + result.list[i].uuid + " src=\"" + result.imageURL + result.list[i].fileName + "\" /></td>");
		$("#input").append("<td abbr=\"" + result.list[i].uuid + "\" ></td>");
	}
	$("#scan img").load(function() {
		$("td[abbr=" + $(this).attr("id") + "]").append("<input onkeyup=\"workbench.sbc(this);\" name=\"" + $(this).attr("id") + "\" style=\"width:" + $(this).width() + "px;\"/>");
		if ($("#input input").size() == result.list.length) {
			$('#input input').each(function(index) {
				ary.push(this);
				if (index == 0) {
					$(this).focus();
				}
				$(this).bind('keypress', function(event) {
					if (event.keyCode == "13") {
						$("#submit").val($(ary[index + 1]).length);
						if ($(ary[index + 1]).length == 0) {
							workbench.submit();
						} else {
							$(ary[index + 1]).focus();
						}
					}
				});
			});
		}
	});
	workbench.timer(180);
	$.unblockUI();
};
/**
 * 获取输入区数据
 */
workbench.getValue = function(e) {
	for ( var i = 0; i < workbench.work.list.length; i++) {
		var res = $("#input input[name=" + workbench.work.list[i].uuid + "]").val();
		if (res != null || res != undefined || res != "") {
			workbench.work.list[i].result = res;
		}
	}
};
/**
 * 上传输入结果
 */
workbench.pushwork = function(data) {
	// workbench.submit();
	if (workbench.work == null) {
		workbench.pullwork();
	}
	workbench.getValue();
	workbench.msg("正在提交任务结果");
	var url = workbench.url + "/worker/workbench/pushwork";
	$.ajax({
		url : url,
		type : "POST",
		data : $.toJSON(workbench.work),
		success : workbench.pushCallback,
		error : workbench.error,
		dataType : "json",
		contentType : 'application/json;charset=utf-8',
		async : false
	});
};
/**
 * 提交结果回调判断是否提交成功，重复提交，提交失败
 */
workbench.pushCallback = function(e) {
	$.unblockUI();
	// 提交成功
	if (e.status == workbench.SUBMIT_STATUS_SUCCESS) {
		workbench.work = null;
		workbench.pullwork();
	}
	// 重复提交
	if (e.status == workbench.SUBMIT_STATUS_REPEAT) {
		alert(workbench.SUBMIT_STATUS_REPEAT);
		workbench.forword();
	}
	// 提交失败
	if (e.status == workbench.SUBMIT_STATUS_ERROR) {
		alert(workbench.SUBMIT_STATUS_ERROR);
		workbench.error();
	}
	// 非法提交
	if (e.status == workbench.SUBMIT_STATUS_ILLEGAL) {
		alert(workbench.SUBMIT_STATUS_ILLEGAL);
	}
	// 超时提交
	if (e.status == workbench.SUBMIT_STATUS_TIMEOUT) {
		alert(workbench.SUBMIT_STATUS_TIMEOUT);
	}
};
/**
 * 通用的错误处理页面
 */
workbench.error = function(e, ex) {
	// alert(e.status + ":" + ex);
	alert("工作台提交出现严重错误,错误状态：" + e.status + ",错误描述：" + ex);
};

/**
 * 模板的检测方法，载入模板完成时加载
 * 
 * @输入校验 对于type=text得input控件 全角禁止输入
 */
workbench.verify = function() {
	$("#template :text").each(function() {
		$(this).bind("keyup", function() {
			workbench.sbc(this);
		});
		$(this).bind("mouseup", function() {
			workbench.sbc(this);
		});
	});

};

/**
 * 删除左右两端的空格
 * 
 * @param str
 * @returns 处理过的字符串
 */
workbench.trim = function(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
};
/**
 * 全角校验
 */
workbench.sbc = function(obj) {
	var str = obj.value;
	if (str.length > 0) {
		for ( var i = str.length - 1; i >= 0; i--) {
			unicode = str.charCodeAt(i);
			if (unicode > 65280 && unicode < 65375) {
				$.blockUI({
					message : "<h3>不能输入全角字符，请输入半角字符</h3>",
					onBlock : function() {
						$.unblockUI();
					}
				});
				obj.value = str.substr(0, i);
			}
		}
	}
};
workbench.intervalId = null;
workbench.timer = function(intDiff) {
	intervalId = window.setInterval(function() {
		var day = 0, hour = 0, minute = 0, second = 0;// 时间默认值
		if (intDiff > 0) {
			day = Math.floor(intDiff / (60 * 60 * 24));
			hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
			minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
			second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
		}
		if (minute <= 9)
			minute = '0' + minute;
		if (second <= 9)
			second = '0' + second;
		$('#day_show').html(day + "days");
		$('#hour_show').html('<s id="h"></s>' + hour + 'h');
		$('#minute_show').html('<s></s>' + minute + '分');
		$('#second_show').html('<s></s>' + second + '秒');
		intDiff--;
		if (intDiff < 0) {
			alert("任务超时，本任务已失效。返回工作大厅");
			workbench.forword();
			window.clearInterval(intervalId);
		}
	}, 1000);
};