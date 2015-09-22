package per.rss.server.poll.dao.nosql.IDGenerator;


/**
 * ID生成器
 * @author Admin
 * @date 2015年4月7日
 */
public interface IDGeneratorDao {
	/**
	 * 获取ID
	 * @param indexKey   序列对应key
	 * @param defaultIndex   默认序列起始值
	 * @return
	 */
	int getID(String indexKey,int defaultIndex);
}
