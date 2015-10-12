package per.rss.server.api.model.user;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import per.rss.core.base.constant.CommonConstant;

@Document(collection = "account")
public class Account implements Serializable {
	private static final long serialVersionUID = 5302856543700774717L;

	@Id
	private String id = CommonConstant.stringBegining;
	private String uid = CommonConstant.stringBegining;// 系统收录时使用的URL、更新rss源的网址。区别于link字段

	// 固有属性 开始
	private String username = CommonConstant.stringBegining;
	private String password = CommonConstant.stringBegining;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
