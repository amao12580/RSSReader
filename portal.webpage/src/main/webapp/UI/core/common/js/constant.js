var rssServerApiAddress = 'http://127.0.0.1:8088/server.api/';

function getUserLoginAddress() {
	return rssServerApiAddress + 'user/login';
}

$(function() {
	$('#userLoginForm').attr("action", getUserLoginAddress());
});
