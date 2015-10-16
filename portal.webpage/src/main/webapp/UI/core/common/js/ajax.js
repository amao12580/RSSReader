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
function doAjaxGetRequestWithCrossDomain1(url, data, async, successCallbackFunc) {
	if (isEmptyStr(data)) {
		url = url + '?' + data;
	}
	var options = {
		type : "GET",
		async : async,
		cache : false,
		url : url,
		timeout : 10000,// 设置请求超时时间（毫秒）。此设置将覆盖全局设置。
		dataType : "jsonp",// 数据类型为jsonp
		crossDomain : true,
		jsonp : randomWord(true, 3, 18) + '__CallBack',// 服务端用于接收callback调用的function名的参数
		success : function(data) {
			successCallbackFunc(data);
		},
		error : function() {
			alert('fail');
		},
		xhrFields : {
			withCredentials : true
		}
	// ,
	// beforeSend : function(XMLHttpRequest) {
	// 不能修改的 :http://segmentfault.com/a/1190000002497440
	// 跨域时无法提交自定义的header信息 :http://itwap.net/ArticleContent.aspx?id=31
	// XMLHttpRequest.setRequestHeader('content-type',
	// 'text/html;charset=UTF-8;'+CSRFTOKENNAME+'='+$("#CSRFToken").val());
	// }
	};
	$.ajax(options);
};

function createXMLHttpRequest() {
	var request = false;
	if (window.XMLHttpRequest) {
		request = new XMLHttpRequest();
		// overrideMimeType资料：http://www.ruanyifeng.com/blog/2012/09/xmlhttprequest_level_2.html
		// if (request.overrideMimeType) {
		// request.overrideMimeType('text/xml');
		// }
	} else if (window.ActiveXObject) {
		var versions = [ 'Microsoft.XMLHTTP', 'MSXML.XMLHTTP',
				'Microsoft.XMLHTTP', 'Msxml2.XMLHTTP.7.0',
				'Msxml2.XMLHTTP.6.0', 'Msxml2.XMLHTTP.5.0',
				'Msxml2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP' ];
		for (var i = 0; i < versions.length; i++) {
			try {
				request = new ActiveXObject(versions[i]);
				if (request) {
					return request;
				}
			} catch (e) {

			}
		}
	}
	return request;
};

var contentTypeKey = 'content-type';
var contentTypeValue_Default = 'application/json;charset=UTF-8';

/**
 * @param url
 *            请求地址 string
 * @param isGet
 *            是否为GET请求 true/false
 * @param data
 *            请求的数据
 * @param withCredentials
 *            是否发送认证信息，如cookie true/false
 * @param successCallbackFunc
 *            响应成功后回调函数对象 object
 */
function CrossDomainByCORS(url, isGet, data, successCallbackFunc) {
	var XHR = createXMLHttpRequest();
	if (XHR) {
		doAsyncAjaxGetRequestWithCrossDomain(XHR, isGet, url, data,
				successCallbackFunc);
		XHR = null;
	} else {
		// XMLHttpRequest 获取失败
	}
};
var timeout = 5000;

function doAsyncAjaxGetRequestWithCrossDomain(MyXHR, isGet, Myurl, MyData,
		MySuccessCallbackFunc) {
	var type = 'GET';
	var reqTime = new Date().getTime();
	var MyParams = null;
	if (isEmptyStr(MyData)) {
		MyParams = MyData + '&reqTime=' + reqTime;
	} else {
		MyParams = 'reqTime=' + reqTime;
	}

	if (!isGet) {
		type = 'POST';
		Myurl = Myurl + '?' + 'reqTime=' + reqTime;
		MyParams = MyData;
	} else {

		Myurl = Myurl + '?' + MyParams;
	}
	MyXHR.open(type, Myurl, true);
	MyXHR.timeout = timeout;
	MyXHR.withCredentials = true;
	// GET跨域请求时：setRequestHeader会导致浏览器拒绝提交:http://itwap.net/ArticleContent.aspx?id=31
	// 不能修改的 :http://segmentfault.com/a/1190000002497440
	if (!isGet) {
		MyXHR.setRequestHeader('X-ITBILU', 'itbilu.com');
		// MyXHR.setRequestHeader(contentTypeKey, contentTypeValue_Default);
		MyXHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		MyXHR.setRequestHeader("Content-length", MyParams.length);
		MyXHR.setRequestHeader("Connection", "close");
	}
	MyXHR.onreadystatechange = function() {
		if (MyXHR.readyState == 4) {
			if (MyXHR.status == 200) {
				if (MyXHR.getResponseHeader(contentTypeKey) === contentTypeValue_Default) {
					var result = JSON.parse(MyXHR.responseText);
					MySuccessCallbackFunc(result);
				} else {
					// 响应的contentType值有误
				}
			} else {
				// 响应的http状态码不是200，出现了异常
			}
		} else {
			// 异步请求还没有完全完成
			// XMLHttpRequest().readyState的五种状态详解:http://www.blogjava.net/hulizhong/archive/2009/05/04/268846.html
		}
	};
	if (!isGet) {
		MyXHR.send(MyParams);
	} else {
		MyXHR.send(null);
	}
};
