package per.rss.core.nosql.mongo;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import per.rss.core.nosql.page.Pagination;

/**
 * mongodb 基础类
 * 
 * http://blog.csdn.net/laigood/article/details/7056093
 * 
 * @author cifpay
 *
 */
public interface MongoBaseDao<T> {

	/**
	 * 通过条件查询,查询分页结果 ,去重复：distinct
	 */
	public Pagination<T> getDistinctPage(String collection, String distinct_key, int currentPage, int pageSize,
			Query query);

	/**
	 * 通过条件查询,查询分页结果
	 */
	public Pagination<T> getPage(int currentPage, int pageSize, Query query, Class<T> c);

	/**
	 * 
	 * mongodb，解析 更新操作是否成功
	 * 
	 * 返回更新数据库的结果
	 * 
	 * 小于零：更新出现异常 等于零：成功执行了更新的SQL，但是没有影响到任何数据 大于零：成功执行了更新的SQL，影响到多条数据，条数就是返回值
	 * 
	 * @param result
	 * @return
	 */
	public int getUpdateResult(WriteResult result);

	/**
	 * this.mongoTemplate.insert
	 * 
	 */
	public void insert(T obj);

	/**
	 * this.mongoTemplate.remove
	 * 
	 */

	public int remove(Query query, Class<T> c);

	/**
	 * this.mongoTemplate.find
	 * 
	 */

	public List<T> find(Query query, Class<T> c);

	/**
	 * this.mongoTemplate.findAll
	 * 
	 */

	public List<T> findAll(Class<T> c);

	/**
	 * this.mongoTemplate.findOne
	 * 
	 */

	public T findOne(Query query, Class<T> c);

	/**
	 * this.mongoTemplate.findAndModify
	 * 
	 */

	public T findAndModify(Query query, Update update, Class<T> c);

	/**
	 * this.mongoTemplate.count
	 * 
	 */

	public long count(Query query, Class<T> c);

	/**
	 * this.mongoTemplate.findAndRemove
	 * 
	 */

	public T findAndRemove(Query query, Class<T> c);

	/**
	 * this.mongoTemplate.updateFirst
	 * 
	 */

	public int updateFirst(Query query, Update update, Class<T> c);

	/**
	 * this.mongoTemplate.collectionExists
	 * 
	 */

	public boolean collectionExists(Class<T> c);

	/**
	 * this.mongoTemplate.createCollection
	 * 
	 */

	public void createCollection(Class<T> c);

	/**
	 * this.mongoTemplate.indexOps
	 * 
	 */

	public IndexOperations indexOps(Class<T> c);

	/**
	 * this.mongoTemplate.updateMulti
	 * 
	 */

	public int updateMulti(Query query, Update update, Class<T> c);

	/**
	 * this.mongoTemplate.save
	 * 
	 */

	public void save(Object saveData);

	/**
	 * this.mongoTemplate.exists
	 * 
	 */

	public boolean exists(Query query, Class<T> c);

	/**
	 * this.mongoTemplate.upsert
	 * 
	 */

	public int upsert(Query query, Update update, Class<T> class1);

	/**
	 * 查询指定的多个字段
	 * 
	 * @param queryFiled
	 * @param query
	 * @param c
	 * @return
	 */
	T findOne(Set<String> queryFiled, Query query, Class<T> c);

}
