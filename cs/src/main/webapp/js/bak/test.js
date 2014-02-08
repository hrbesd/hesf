$(document).ready(function() {
	var url = "/cs/json1";
	$.ajax({
		url : url,
		type : "GET",
		success : function(data) {
			alert(data.name);
		},
		dataType : "json",
		async : true
	});
});