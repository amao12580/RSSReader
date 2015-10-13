package per.rss.server.api.biz.user.login;

import javax.servlet.http.HttpSession;

import per.rss.server.api.bo.core.Resp;
import per.rss.server.api.bo.user.login.LoginBo;

public interface LoginBiz {
	Resp doUserLogin(LoginBo loginBo);

	Resp getRSAKey(HttpSession session);
}
