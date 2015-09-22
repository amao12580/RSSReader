package per.rss.server.poll.dao.nosql.base.mongo.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.CommandResult;
import com.mongodb.WriteResult;

import per.rss.server.poll.dao.nosql.base.mongo.MongoBaseDao;
import per.rss.server.poll.page.Pagination;

/**
 * mongodb  基础类
 * 
 * http://blog.csdn.net/laigood/article/details/7056093
 * 
 * @author cifpay
 *
 */
@Repository(value="mongoBaseDao")
public class MongoBaseDaoImpl<T> implements MongoBaseDao<T>{
	
	@Autowired  
	private MongoTemplate mongoTemplate;
	/** 每页默认显示五条   */
    protected static final int PAGE_SIZE = 5; 
    /** 默认页码，第一页  */
    protected static final int DEFAULT_PAGE = 1;
    /** 修改操作失败的返回码 -1 */
    protected static final int FAIL_CODE_ONE = -1;
    /** 修改操作失败的返回码 -2 */
    protected static final int FAIL_CODE_TWO = -2;
    /** 修改操作失败的返回码 -3 */
    protected static final int FAIL_CODE_THREE = -3;
    /** 修改操作失败的返回码 -4 */
    protected static final int FAIL_CODE_FOUR = -4;
    
    /** 
     * 通过条件查询,查询分页结果  
     */  
    @Override
    public Pagination<T> getPage(int page,int pageSize,Query query,Class<T> c) {  
        //总页数  
        if(page < 1){
        	page = DEFAULT_PAGE;
        }
        if(pageSize<=0){
        	pageSize = PAGE_SIZE;
        }
        //获取总条数  
        long totalNumber = this.mongoTemplate.count(query, c);  
        int skip=(page - DEFAULT_PAGE)*pageSize;
        query.skip(skip);// skip相当于从那条记录开始  
        query.limit(pageSize);// 从skip开始,取多少条记录  
        List<T> datas = new ArrayList<T>();
        if(totalNumber > 0){
        	datas=this.mongoTemplate.find(query, c);
        }
        int totalPage = (int) (totalNumber/pageSize);  
        Pagination<T> p = new Pagination<T>(page,pageSize, totalPage, (int)totalNumber);  
        p.build(datas);//获取数据      
        return p;  
    }  
    @SuppressWarnings("unchecked")
	@Override
	public Pagination<T> getDistinctPage(String collection, String distinct_key, int page,int pageSize,Query query) {
    	//总页数  
        if(page < 1){
        	page = DEFAULT_PAGE;
        }
        if(pageSize <= 0){
        	pageSize = PAGE_SIZE;
        }
        //获取总条数  
        long totalNumber = 0;  
        List<T> allData=this.mongoTemplate.getCollection(collection).distinct(distinct_key);
        if(allData!=null){
        	totalNumber=allData.size();
        }
        int skip=(page - DEFAULT_PAGE)*pageSize;
        query.skip(skip);// skip相当于从那条记录开始  
        query.limit(pageSize);// 从skip开始,取多少条记录  
        List<T> datas = new ArrayList<T>();
        if(totalNumber > 0){
        	datas=this.mongoTemplate.getCollection(collection).distinct(distinct_key,query.getQueryObject());
        }
        int totalPage = (int) (totalNumber/pageSize);  
        Pagination<T> p = new Pagination<T>(page,pageSize, totalPage, (int)totalNumber);  
        p.build(datas);//获取数据      
        return p;
	}
    /**
	 * 
	 * mongodb，解析 更新操作是否成功
	 * 
	 * 返回更新数据库的结果
	 * 
	 * 小于零：更新出现异常
	 * 等于零：成功执行了更新的SQL，但是没有影响到任何数据
	 * 大于零：成功执行了更新的SQL，影响到多条数据，条数就是返回值
	 * 
	 * @param result
	 * @return
	 */
    @Override
	public int getUpdateResult(WriteResult result) {
		if(result==null){
			return FAIL_CODE_ONE;
		}
		@SuppressWarnings("deprecation")
		CommandResult cr=result.getLastError();
		if(cr==null){
			return FAIL_CODE_TWO;
		}
		boolean error_flag=false;
		error_flag=cr.ok();
		if(!error_flag){//获取上次操作结果是否有错误.
			return FAIL_CODE_THREE;
		}
		int affect_count=result.getN();//操作影响的对象个数
		
		if(affect_count<0){
			return FAIL_CODE_FOUR;
		}else{
			return affect_count;
		}
	}
    @Override
	public void insert(T obj) {
    	this.mongoTemplate.insert(obj);
	}
    @Override
	public int remove(Query query, Class<T> c) {
    	return this.getUpdateResult(this.mongoTemplate.remove(query,c));
	}
    @Override
    public List<T> find(Query query, Class<T> c) {
    	return this.mongoTemplate.find(query, c);
    }
    @Override
	public List<T> findAll(Class<T> c) {
    	return this.mongoTemplate.findAll(c);
	}
    @Override
    public T findOne(Query query, Class<T> c) {
    	return this.mongoTemplate.findOne(query, c);
    }
    @Override
	public T findOne(Set<String> queryFiled,Query query, Class<T> c) {
    	for (String str : queryFiled) {
    		query.fields().include(str);
    	}
    	return this.mongoTemplate.findOne(query, c);
	}
    @Override
	public T findAndModify(Query query, Update update,Class<T> c) {
		return this.mongoTemplate.findAndModify(query, update, c);
	}
    @Override
	public long count(Query query, Class<T> c) {
		return this.mongoTemplate.count(query, c);
	}
    @Override
	public T findAndRemove(Query query,Class<T> c){
    	return this.mongoTemplate.findAndRemove(query, c);
    }
    @Override
	public int updateFirst(Query query, Update update,Class<T> c){
    	return this.getUpdateResult(this.mongoTemplate.updateFirst(query, update, c));
    }
    @Override
	public boolean collectionExists(Class<T> c){
    	return this.mongoTemplate.collectionExists(c);
    }
    @Override
	public void createCollection(Class<T> c){
    	this.mongoTemplate.createCollection(c);
    }
    @Override
	public IndexOperations indexOps(Class<T> c){
    	return this.mongoTemplate.indexOps(c);
    }
	@Override
	public int updateMulti(Query query, Update update, Class<T> c) {
		return this.getUpdateResult(this.mongoTemplate.updateMulti(query, update, c));
	}
	@Override
	public void save(Object saveData) {
		this.mongoTemplate.save(saveData);
	}
	@Override
	public boolean exists(Query query, Class<T> c) {
		return this.mongoTemplate.exists(query, c);
	}
	@Override
	public int upsert(Query query, Update update, Class<T> c) {
		return this.getUpdateResult(this.mongoTemplate.upsert(query, update, c));
	}
}
