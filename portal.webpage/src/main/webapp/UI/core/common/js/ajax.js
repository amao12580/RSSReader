//常用ajax方法进行封装

//http://www.cnblogs.com/yeer/archive/2009/07/23/1529460.html
//http://my.oschina.net/adamboy/blog/26307

function doAjaxPostRequest(url, data, async, successCallback) {
	var options = {
		type : 'POST',
		url : url,
		cache : false,
		data : data,
		async : async,
		dataType : "json",
		success : function(data, textStatus) {
			successCallback(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('not ok');
			alert(XMLHttpRequest.responseText);
			alert(XMLHttpRequest.readyState);
			alert(XMLHttpRequest.status);
			alert(textStatus);
			alert(XMLHttpRequest.readyState + XMLHttpRequest.status
					+ XMLHttpRequest.responseText);
		}
	};
	$.ajax(options);
};

/**
 * 跨域请求 http://www.iteye.com/topic/1130452
 * 
 * @param url
 * @param data
 * @param async
 * @param successCallbackName
 *            回调函数名称，string类型
 * @param successCallbackFunc
 *            回调函数对象，object类型
 */
function doAjaxGetRequestWithCrossDomain(url, data, async, successCallbackName,
		successCallbackFunc) {
	if (isEmptyStr(data)) {
		url = url + '?' + data;
	}
	var options = {
		type : "GET",
		async : async,
		cache : false,
		url : url,
		dataType : "jsonp",// 数据类型为jsonp
		jsonp : successCallbackName,// 服务端用于接收callback调用的function名的参数
		success : function(data) {
			successCallbackFunc(data);
		},
		error : function() {
			alert('fail');
		}
	};
	$.ajax(options);
};

