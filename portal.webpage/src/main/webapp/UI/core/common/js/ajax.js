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
 * @param successCallbackFunc
 *            响应成功后回调函数对象 object
 */
var activeId = '';
var activeId = '';
var sessionId = '';
var sessionSign = '';

var nacl = '';

function Head(command) {
	this.command = command;
	if (nacl === '') {
		nacl = nacl_factory.instantiate();
	}
	this.messageId = nacl.to_hex(nacl.random_bytes(16)) + '';
	this.activeId = activeId + '';
	this.sessionId = sessionId + '';
	this.sessionSign = sessionSign + '';
};

function Req(command, encryptType, encryptData, digest) {
	this.head = new Head(command);
	this.dataMode = encryptType + '';
	this.body = encryptData + '';
	this.digest = digest + '';
	this.time = new Date().getTime();
};

var encryptTypeLevel1 = '1';

function CrossDomainByCORS(command, data, successCallbackFunc, encryptType) {
	var url = rssServerApiAddress + command;
	var XHR = createXMLHttpRequest();
	if (XHR) {
		// 为数据加密
		var digest = null;
		var encryptData = null;
		if (encryptType === encryptTypeLevel1) {
			if (data === '') {
				encryptData = '';
			} else {
				encryptData = encode64(data);
			}
			digest = CryptoJS.SHA256(data);
		}
		// 组装请求对象Req
		Req
		req = new Req(command, encryptType, encryptData, digest);
		var finalData = JSON.stringify(req);
		doAsyncAjaxGetRequestWithCrossDomain(XHR, url, finalData,
				successCallbackFunc);
		XHR = null;
	} else {
		// XMLHttpRequest 获取失败
	}
};
var timeout = 5 * 1000;
var parameterKey = 'params';

function doAsyncAjaxGetRequestWithCrossDomain(MyXHR, Myurl, MyData,
		MySuccessCallbackFunc) {
	MyData = parameterKey + '=' + MyData;
	MyXHR.open('POST', Myurl, true);
	MyXHR.timeout = timeout;
	var timeoutFunction = setTimeout(function() {
		MyXHR.abort();
	}, timeout);
	MyXHR.withCredentials = true;
	// GET跨域请求时：setRequestHeader会导致浏览器拒绝提交:http://itwap.net/ArticleContent.aspx?id=31
	// 不能修改的 :http://segmentfault.com/a/1190000002497440
	// MyXHR.setRequestHeader('X2-ITBILU', 'itbilu2.com');
	// MyXHR.setRequestHeader(contentTypeKey, contentTypeValue_Default);
	MyXHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	// MyXHR.setRequestHeader("Content-length", MyData.length);
	// MyXHR.setRequestHeader("Connection", "close");
	MyXHR.onreadystatechange = function() {
		if (MyXHR.readyState == 4) {
			clearTimeout(timeoutFunction);
			if (MyXHR.status == 200) {
				if (MyXHR.getResponseHeader(contentTypeKey) === contentTypeValue_Default) {
					var result = '';
					try {
						result = JSON.parse(MyXHR.responseText);
					} catch (e) {
						alert('响应的数据格式有问题，无法使用json进行解析.');
						result = '';
						return false;
					}
					if (!(result === '')) {
						MySuccessCallbackFunc(result);
					}
				} else {
					// 响应的contentType值有误
					alert('响应的content-type有问题:'
							+ MyXHR.getResponseHeader(contentTypeKey));
				}
			} else {
				alert('请求被拒绝:' + MyXHR.status);
				// 响应的http状态码不是200，出现了异常
			}
		} else {
			// 异步请求还没有完全完成
			// XMLHttpRequest().readyState的五种状态详解:http://www.blogjava.net/hulizhong/archive/2009/05/04/268846.html
		}
	};
	MyXHR.send(MyData);
};
