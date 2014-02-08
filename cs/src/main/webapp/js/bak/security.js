/**
 * Copyright (c) 2013 北京海达网畅信息技术开发有限公司（www.hadventure.com）. All rights reserved.
 * 
 * HADVENTURE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @author zhangjianzong 密码强度
 */
var pwd = function(v) {
	return document.getElementById(v);
};
function isSecurity(v) {
	if (v.length < 3) {
		iss.reset();
		return;
	}
	var lv = -1;
	if (v.match(/[a-z]/ig)) {
		lv++;
	}
	if (v.match(/[0-9]/ig)) {
		lv++;
	}
	if (v.match(/(.[^a-z0-9])/ig)) {
		lv++;
	}
	if (v.length < 6 && lv > 0) {
		lv--;
	}
	iss.reset();
	switch (lv) {
	case 0:
		iss.level0();
		break;
	case 1:
		iss.level1();
		break;
	case 2:
		iss.level2();
		break;
	default:
		iss.reset();
	}
}
var iss = {
	color : [ "#CC0000", "#FFCC33", "#66CC00", "#CCCCCC" ],
	text : [ "弱", "中", "强" ],
	width : [ "260px", "260px", "260px", "260px" ],
	reset : function() {
		pwd("B").style.backgroundColor = iss.color[3];
		pwd("B").style.width = iss.width[3];
		pwd("A").innerHTML = "密码强度";
	},
	level0 : function() {
		pwd("B").style.backgroundColor = iss.color[0];
		pwd("B").style.width = iss.width[0];
		pwd("A").innerHTML = "较弱";
	},
	level1 : function() {
		pwd("B").style.backgroundColor = iss.color[1];
		pwd("B").style.width = iss.width[1];
		pwd("A").innerHTML = "中";
	},
	level2 : function() {
		pwd("B").style.backgroundColor = iss.color[2];
		pwd("B").style.width = iss.width[2];
		pwd("A").innerHTML = "高强";
	}
};
