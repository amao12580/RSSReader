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