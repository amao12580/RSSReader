//常用工具方法

//http://www.ruanyifeng.com/blog/2014/03/undefined-vs-null.html
function isEmptyStr(str) {
	if (!str) {
		return false;
	}
	if (str == null) {
		return false;
	}
	str = '' + str;
	if (str == '') {
		return false;
	}
	if (str == 'undefined') {
		return false;
	}
	return true;
}

/*
 * http://www.xuanfengge.com/js-random.html
 * 
 * 生成3-32位随机串：randomWord(true, 3, 32)
 * 
 * 生成43位随机串：randomWord(false, 43)
 * 
 * randomWord 产生任意长度随机字母数字组合 * randomFlag-是否任意长度 min-任意长度最小位[固定位数] max-任意长度最大位 *
 * xuanfeng 2014-08-28
 */

function randomWord(randomFlag, min, max) {
	var str = "", range = min, arr = [ '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z' ];

	// 随机产生
	if (randomFlag) {
		range = Math.round(Math.random() * (max - min)) + min;
	}
	for (var i = 0; i < range; i++) {
		pos = Math.round(Math.random() * (arr.length - 1));
		str += arr[pos];
	}
	return str;
}



/**
 * http://lyjilu.iteye.com/blog/1996118
 * 
 * 获取浏览器类型及 版本
 * 
 * @returns 返回对象，两属性，type:表示浏览器类型，version：表示版本 {___anonymous25556_25557}
 */
function getUserAgent(){
	var Sys={};
	var ua=navigator.userAgent.toLowerCase();
	var s;
	(s=ua.match(/msie ([\d.]+)/))?(Sys['type']='ie',Sys['version']=s[1]):
		(s=ua.match(/firefox\/([\d.]+)/))?(Sys['type']='firefox',Sys['version']=s[1]):
			(s=ua.match(/chrome\/([\d.]+)/))?(Sys['type']='chrome',Sys['version']=s[1]):
				(s=ua.match(/opera.([\d.]+)/))?(Sys['type']='opera',Sys['version']=s[1]):
					(s=ua.match(/version\/([\d.]+).*safari/))?(Sys['type']='safari',Sys['version']=s[1]):0;
					return Sys;
}

/**
 * http://dreamoftch.iteye.com/blog/1872108
 * 
 * 获取当前session Id
 * 
 */
function getSessionId(){
	var c_name = 'JSESSIONID';
	if(document.cookie.length>0){
	   c_start=document.cookie.indexOf(c_name + "=")
	   if(c_start!=-1){ 
	     c_start=c_start + c_name.length+1 
	     c_end=document.cookie.indexOf(";",c_start)
	     if(c_end==-1) c_end=document.cookie.length
	     return unescape(document.cookie.substring(c_start,c_end));
	   }
	}
}