var userLoginMessageWarn = $('#userLoginMessageWarn');
var userLoginMessageWarnValue = $('#userLoginMessageWarnValue');
var username_maxLength = 30;
var username_minLength = 5;
var password_maxLength = 30;
var password_minLength = 1;

$(function() {
	userLoginMessageWarn.hide();
	var userLoginForm = $('#userLoginForm');
	$('#userLoginBtn').dblclick(function() {
		return false;
	});
	$('#userLoginBtn').click(function() {
		var check = false;
		check = checkUserLogin(userLoginForm);
		if (!check) {
			return false;
		}
		doUserLogin(userLoginForm);
	});
});

function getUserLoginAddress() {
	return rssServerApiAddress + 'user/login';
};
function doUserLogin(form) {
	// doAjaxPostRequest(getUserLoginAddress(),$('#userLoginForm').serialize(),false,doUserLoginSuccess);
	doAjaxGetRequestWithCrossDomain(getUserLoginAddress(), form.serialize(),
			false, "doUserLoginSuccessCallBack", doUserLoginSuccessCallBack);
}
function doUserLoginSuccessCallBack(data) {
	var code = data.code;
	if (code == successProcess) {
		toUserHomePage();// 登陆成功了，前往用户主页吧
	} else {
		showMessageWarn(data.desc);
		return false;
	}
}

function toUserHomePage() {
	alert('go.');
}

function checkUserLogin(obj) {
	var username = $('#username').val();
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

	var password = $('#password').val();
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