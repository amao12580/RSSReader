package per.rss.core.nosql.IDGenerator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import per.rss.core.nosql.IDGenerator.IDGeneratorDao;
import per.rss.core.nosql.model.IDGenerator.IDGenerator;
import per.rss.core.nosql.mongo.MongoBaseDao;

/**
 * ID生成器
 * 
 * @author Admin
 * @date 2015年4月7日
 */
@Repository(value = "idGeneratorDao")
public class IDGeneratorDaoImpl implements IDGeneratorDao {
	@Autowired(required=true)
	private MongoBaseDao<IDGenerator> mongoBaseDao;

	@Override
	public int getID(String indexKey, int defaultIndex) {
		synchronized (IDGeneratorDaoImpl.class) {
			if (!this.mongoBaseDao.collectionExists(IDGenerator.class)) {
				this.mongoBaseDao.createCollection(IDGenerator.class);
			}
			Query query = new Query();
			query.addCriteria(new Criteria("id").is(indexKey));
			Update update = new Update();
			update.inc("index", 1);
			IDGenerator idGenerator = this.mongoBaseDao.findAndModify(query, update, IDGenerator.class);
			if (idGenerator == null) {// 如果不存在，则新增
				idGenerator = new IDGenerator(indexKey, defaultIndex);
				this.mongoBaseDao.save(idGenerator);
			}
			return idGenerator.getIndex();
		}
	}

}
