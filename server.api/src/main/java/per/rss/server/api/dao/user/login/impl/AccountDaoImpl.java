package per.rss.server.api.dao.user.login.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import per.rss.core.nosql.constant.DBAccountKeysConstant;
import per.rss.core.nosql.mongo.MongoBaseDao;
import per.rss.server.api.dao.user.login.AccountDao;
import per.rss.server.api.model.user.Account;

@Repository(value = "accountDao")
public class AccountDaoImpl implements AccountDao {
//	private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);
	@Autowired(required = true)
	private MongoBaseDao<Account> mongoBaseDao;

	@Override
	public String findUidByLogin(String username, String password) {
		Set<String> queryFileds = new HashSet<String>(1);
		queryFileds.add(DBAccountKeysConstant.uid);
		Query query = new Query();
		query.addCriteria(new Criteria(DBAccountKeysConstant.username).is(username));
		query.addCriteria(new Criteria(DBAccountKeysConstant.password).is(password));
		Account account = mongoBaseDao.findOne(queryFileds, query, Account.class);
		if (account == null) {
			return null;
		} else {
			return account.getUid();
		}
	}
}
