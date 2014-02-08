/**
 * Copyright (c) 2013 北京海达网畅信息技术开发有限公司（www.hadventure.com）. All rights reserved.
 * 
 * HADVENTURE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * @author zhangjianzong
 */
$(document).ready(function() {
	ps.bindRegister();
});
var ps = {};
ps.url = "/ps";
ps.imageIp = "http://192.168.1.100:9080/storage/storage/image/";
ps.error = function() {
	alert("请求错误");
};
ps.bindRegister = function() {
	if ($("#register").size() > 0 || $("#forgot").size() > 0 || $("#modify").size() > 0) {
		// alert("加载ps.register.js");
		$("head").append("<script type=\"text/javascript\" src=\"" + ps.url + "/js/ps.register.js\"></script>");
	}
};
//util class
//倒计时方法util.timer(秒);
var util = {};
util.intervalId = null;
util.timer = function(intDiff,name,callback) {
	intDiff = parseInt(intDiff);
	util.intervalId = window.setInterval(function() {
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
		$("input[name="+name+"]").val("("+minute + ":" + second+")");
		intDiff--;
		if (intDiff < 0) {
			window.clearInterval(util.intervalId);
			//util.callBack();
			//eval(callback);
			callback();
		}
	}, 1000);
};
//fanyie
var pagination={};
pagination.prefixSize = 4; // 本页前缀页数
pagination.suffixSize = 4; // 本页后缀页数
pagination.records = 0;// 总条数
pagination.pageSize=20; //每页显示数
pagination.totalPages = 0;// 总页数
pagination.thisPage = 0;// 当前选中页
/**
 * 总条数,当前页
 */
pagination.init = function(thisPage,records){
	pagination.records = records;//总条数
	pagination.thisPage = thisPage;//当前页码
};

pagination.getPages = function() {
    var pages = records / pagination.pageSize;
    if (records % pagination.pageSize != 0) {
        pages++;
    }
    return pages;
};

pagination.getStart = function() {
    if (thisPage > pagination.prefixSize) {
        return thisPage - pagination.prefixSize;
    }
    return 1;
};

pagination.getEnd = function() {
    if ((pagination.totalPages - pagination.thisPage) > pagination.suffixSize) {
        return pagination.thisPage + pagination.suffixSize;
    } else {
        return pagination.totalPages;
    }
};

pagination.getHandler = function() {
	pagination.totalPages = pagination.getPages();
	pagination.start = pagination.getStart();
	pagination.end = pagination.getEnd();
};