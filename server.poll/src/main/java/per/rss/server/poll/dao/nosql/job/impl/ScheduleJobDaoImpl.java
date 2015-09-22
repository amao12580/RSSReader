package per.rss.server.poll.dao.nosql.job.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import per.rss.server.poll.dao.nosql.IDGenerator.IDGeneratorDao;
import per.rss.server.poll.dao.nosql.base.mongo.MongoBaseDao;
import per.rss.server.poll.dao.nosql.job.ScheduleJobDao;
import per.rss.server.poll.model.job.ScheduleJob;

@Repository(value = "scheduleJobDao")
public class ScheduleJobDaoImpl implements ScheduleJobDao {

	private static final String scheduleJobIDIndexKey = "scheduleJob_id";// 用户ID自增序列对应的key
	private static final int scheduleJobIDDefaultIndex = 100000;// 默认用户id序列起始值

	@Autowired
	private MongoBaseDao<ScheduleJob> mongoBaseDao;

	@Autowired
	private IDGeneratorDao idGeneratorDao;

	@Override
	public int generateUserID() {
		return idGeneratorDao.getID(scheduleJobIDIndexKey, scheduleJobIDDefaultIndex);
	}

	@Override
	public List<ScheduleJob> findByJobInit() {
		if (!this.mongoBaseDao.collectionExists(ScheduleJob.class)) {
			this.mongoBaseDao.createCollection(ScheduleJob.class);
		}
		return this.mongoBaseDao.findAll(ScheduleJob.class);
	}
}
