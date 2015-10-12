package per.rss.server.api.dao.user.login;

public interface AccountDao {
	public String findUidByLogin(String username,String password);
}
