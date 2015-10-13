var userLoginMessageWarn = $('#userLoginMessageWarn');
var userLoginMessageWarnValue = $('#userLoginMessageWarnValue');
var username_maxLength = 30;
var username_minLength = 5;
var password_maxLength = 12;
var password_minLength = 1;
var RSA_key_info;
var RSA_key_module = '';
var RSA_key_empoent = '';
var usernameObj = null;
var passwordObj = null;

$(function() {
	$('#username').val("");
	$('#password').val("");
	userLoginMessageWarn.hide();
	$('#userLoginBtn').dblclick(function() {
		return false;
	});
	// alert('-2');
	initRSASecurityLogin();
	// var checkInit = false;
	// alert('-1');
	// checkInit = checkInitLoginGetRSAKey();
	// alert('0');
	// if (!checkInit) {
	// return false;
	// }
	// alert('1');
	$('#userLoginBtn').click(function() {
		// alert('2');
		clearMessageWarn();
		var check = false;
		check = checkUserLogin();
		// alert('3');
		if (!check) {
			return false;
		}
		// alert('4');
		doUserLogin();
		// alert('5');
	});
});

function toLoginGetRSAKey() {
	return rssServerApiAddress + 'user//toLogin/getRSAKey';
};

function getUserLoginAddress() {
	return rssServerApiAddress + 'user/login';
};

function initRSASecurityLogin() {
	doAjaxGetRequestWithCrossDomain(toLoginGetRSAKey(), '', false,
			"initRSASecurityLoginCallBack", initRSASecurityLoginCallBack);
};

function doUserLogin() {
	var encryptedUsername = encryptedString(RSA_key_info, usernameObj.val());
	var encryptedPassword = encryptedString(RSA_key_info, passwordObj.val());
	var data = 'username=' + encryptedUsername + '&' + 'password='
			+ encryptedPassword;
	// doAjaxPostRequest(getUserLoginAddress(),$('#userLoginForm').serialize(),false,doUserLoginSuccess);
	doAjaxGetRequestWithCrossDomain(getUserLoginAddress(), data, false,
			"doUserLoginCallBack", doUserLoginCallBack);
};

function doUserLoginCallBack(data) {
	var code = data.code;
	if (code == successProcess) {
		toUserHomePage();// 登陆成功了，前往用户主页吧
	} else {
		showMessageWarn('系统错误:' + data.desc);
		return false;
	}
};

function initRSASecurityLoginCallBack(data) {
	var code = data.code;
	if (code == successProcess) {
		RSA_key_module = data.result.module;
		RSA_key_empoent = data.result.empoent;
		try {
			var message = '系统错误：通讯失败 .';
			if (!isEmptyStr(RSA_key_module)) {
				showMessageWarn(message);
				return false;
			}
			if (!isEmptyStr(RSA_key_empoent)) {
				showMessageWarn(message);
				return false;
			}
			setMaxDigits(130);
			RSA_key_info = new RSAKeyPair(RSA_key_empoent, "", RSA_key_module);
		} catch (err) {
			showMessageWarn('初始化失败：' + err.message);
			return false;
		}
	} else {
		showMessageWarn('系统错误:' + data.desc);
		return false;
	}
};

function toUserHomePage() {
	alert('go.');
};

function checkInitLoginGetRSAKey() {
	// 判断Object对象是否存在：http://www.ruanyifeng.com/blog/2011/05/how_to_judge_the_existence_of_a_global_object_in_javascript.html
	var message = '系统错误：通讯失败 .';
	alert('' + RSA_key_module);
	if (!isEmptyStr(RSA_key_module)) {
		showMessageWarn(message);
		return false;
	}
	if (!isEmptyStr(RSA_key_empoent)) {
		showMessageWarn(message);
		return false;
	}
	return true;
};

function checkUserLogin() {
	usernameObj = $('#username');
	var username = usernameObj.val();
	if (!isEmptyStr(username)) {
		showMessageWarn('用户名不能为空.');
		return false;
	}
	if (username.length > username_maxLength) {
		showMessageWarn('用户名长度不能超过' + username_maxLength + '.');
		return false;
	}
	if (username.length < username_minLength) {
		showMessageWarn('用户名长度不能低于' + username_minLength + '.');
		return false;
	}

	passwordObj = $('#password');
	var password = passwordObj.val();
	if (!isEmptyStr(password)) {
		showMessageWarn('密码不能为空.');
		return false;
	}

	if (password.length > password_maxLength) {
		showMessageWarn('密码长度不能超过' + password_maxLength + '.');
		return false;
	}

	if (password.length < password_minLength) {
		showMessageWarn('密码长度不能低于' + password_minLength + '.');
		return false;
	}
	return true;
};

function showMessageWarn(message) {
	userLoginMessageWarnValue.empty();
	userLoginMessageWarnValue.append('警告：' + message);
	userLoginMessageWarn.show();
};

function clearMessageWarn() {
	userLoginMessageWarnValue.empty();
	userLoginMessageWarn.hide();
};