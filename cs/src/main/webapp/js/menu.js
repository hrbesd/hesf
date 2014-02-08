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
				view.tabs.removeAllTab();
				view.tabs.addTab(_title, _url, false);
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
 * 新添加tab
 */
// view.tabs.addTab = function(title, url, closable) {
// if ($('#main').tabs('exists', title) == true) {
// $('#main').tabs('select', title);
// view.tabs.update(url);
// return;
// }
// view.index++;
// $('#main').tabs('add', {
// title : title,
// content : view.tabs.loading,
// closable : closable
// });
// view.tabs.update(url);
// };
/**
 * new 新添加tab
 */
view.tabs.addTab = function(title, url, closable) {
	// view.tabs.removeAllTab();
	// view.index++;
	$.ajax({
		url : url,
		method : "GET",
		success : function(data) {
			$('#main').tabs('add', {
				title : title,
				content : data,
				closable : closable
			});

		},
		error : function() {
			alert("请求错误");
		},
		dataType : "html",
		async : true
	});
};
/**
 * 更新tab
 */
view.tabs.update = function(url) {
	var tab = $('#main').tabs('getSelected');
	$.ajax({
		url : url,
		method : "GET",
		success : function(data) {
			$('#main').tabs('update', {
				tab : tab,
				options : {
					content : data
				}
			});
		},
		error : function() {
			alert("请求错误");
		},
		dataType : "html",
		async : false
	});

};
/**
 * 添加首页
 */
view.tabs.addIndexTab = function() {
	view.tabs.addTab("首页", "/cs/security/main", false);

};
/**
 * 关闭所有tab
 */
view.tabs.removeAllTab = function() {
	var tab = $('#main').tabs('getSelected');
	while (tab != null) {
		var index = $('#main').tabs('getTabIndex', tab);
		$('#main').tabs('close', index);
		var tab = $('#main').tabs('getSelected');
	}
	// view.tabs.addIndexTab();
};
