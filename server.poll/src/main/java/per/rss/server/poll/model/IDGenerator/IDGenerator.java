package per.rss.server.poll.model.IDGenerator;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 *	ID自增生成器
 * @author Admin
 * @date 2015年4月2日
 */
@Document(collection = "idGenerator") 
public class IDGenerator implements Serializable{
	private static final long serialVersionUID = -5718743322799922224L;
	
	@Id
	private String id;//ID生成器序列对应的key
	private int index;//索引值  
	
	public IDGenerator(String idIndexKey,int defaultIDIndex){
		this.id=idIndexKey;
		this.index=defaultIDIndex;
	}
	public IDGenerator(){
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
}
