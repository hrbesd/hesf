$(document).ready(function() {
	view.tabs.addIndexTab();
	view.bindMenu();
});
/**
 * 视图类
 */
var view = {};
/**
 * 绑定菜单事件
 */
view.bindMenu = function() {
	// 1.创建树形菜单
	$('#main_menu').tree({
		// 绑定单击事件
		onSelect : function(e, node) {
			if (e.attributes != undefined) {
				// 页面单击，转到实体页面
				var _url = e.attributes.url;
				var _title = e.text;
				view.tabs.update(_title, _url, false);
			}
		}
	});
};

/**
 * 增加标签页函数
 * 
 * @param title
 * @param url
 */
view.tabs = {};
view.tabs.index = 0;
view.tabs.loading = '<div class="panel-loading">Loading...</div>';

/**
 * 更新tab
 */
view.tabs.update = function(title, url, closable) {
	var tab = $('#main').tabs('getSelected');
	$('#main').tabs('update', {
		tab : tab,
		options : {
			title : title,
			href : url,
			closable : closable
		},
	});

	// $.ajax({
	// url : url,
	// method : "GET",
	// success : function(data) {
	// var tab = $('#main').tabs('getSelected'); // 获取选择的面板
	// if (tab == null) {
	// $('#main').tabs('add', {
	// title : title,
	// content : data,
	// closable : closable
	// });
	// } else {
	// $('#main').tabs('update', {
	// tab : tab,
	// options : {
	// title : title,
	// content : data,
	// closable : closable
	// },
	// });
	// }
	// $('#main').html(data);
	// $.parser.parse('#main');
	// },
	// error : function() {
	// alert("正在维护中");
	// },
	// dataType : "html",
	// async : false
	// });
};

/**
 * 添加首页
 */
view.tabs.addIndexTab = function() {
	// view.tabs.update("首页", "/cs/security/main", false);
	$('#main').tabs('add', {
		title : "首页",
		href : "/cs/security/main",
		closable : false
	});
};
/**
 * 关闭所有tab
 */
view.tabs.removeAllTab = function(_title, _url) {
	var tab = $('#main').tabs('getSelected');
	while (tab != null) {
		var index = $('#main').tabs('getTabIndex', tab);
		$('#main').tabs('close', index);
		var tab = $('#main').tabs('getSelected');
	}
	// 保证删除后添加tab
	view.tabs.addTab(_title, _url, false);
};
