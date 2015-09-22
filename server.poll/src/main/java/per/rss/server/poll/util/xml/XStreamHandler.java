package per.rss.server.poll.util.xml;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import per.rss.server.poll.model.feed.Feed;  
   
/**
 * 
 * 代码还需要完善
 * 
 * @author cifpay
 *
 */
public class XStreamHandler extends XMLHandler{  
   
    private XStream xstream = null;  
    private Student bean = null;  
    
    protected XStreamHandler() {
		super();
	}
   
    @Before  
    public void init() {  
        try {  
            xstream = new XStream(); 
            bean = getTestStudent();  
            // xstream = new XStream(new DomDriver()); // 需要xpp3 jar  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
   
    public static void main(String[] args) {  
//    	XMLHander test = new XMLHander();  
//        test.init();  
//        test.testWriteBean2XML_01();  
    }  
   
    public final void fail(String string) {  
        System.out.println(string);  
    }  
   
    public final void failRed(String string) {  
        System.err.println(string);  
    }  
   
    /** 
     * bean 2 XML 
     * */  
    @Test  
    public void testWriteBean2XML_01() {  
        try {  
            fail("------------Bean->XML------------");  
            fail(xstream.toXML(bean));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
   
    /** 
     * 类重命名后的XML 
     * */  
    @Test  
    public void testWriteBean2XML_02() {  
        try {  
            fail("-----------类重命名后的XML------------");  
            // 类重命名  
            xstream.alias("student", Student.class);  
            xstream.aliasField("生日", Student.class, "birthday");  
            xstream.aliasField("生日日期", Birthday.class, "birthday");  
            fail(xstream.toXML(bean));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
   
    /** 
     * 类重命名后的XML 
     * */  
    @Test  
    public void testWriteBean2XML_03() {  
        try {  
            fail("-----------属性重命名后的XML------------");  
            // 属性重命名  
            xstream.aliasField("邮件", Student.class, "email");  
            fail(xstream.toXML(bean));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
   
    /** 
     * 包重命名后的XML 
     * */  
    @Test  
    public void testWriteBean2XML_04() {  
        try {  
            fail("-----------包重命名后的XML------------");  
            //包重命名  
            xstream.aliasPackage("modile", "com.entity");  
            fail(xstream.toXML(bean));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
   
    /** 
     * 构造数据 
     * */  
    private Student getTestStudent() {  
        Student bean = new Student();  
        bean.setAddress("china");  
        bean.setEmail("email");
        bean.setId(1);  
        bean.setName("jack");  
        Birthday day = new Birthday();  
        day.setBirthday("2010-11-22");  
        bean.setBirthday(day);  
        bean.setRegistDate(new Date());  
        return bean;  
    }  
    
    class Student{
    	private String address;
    	private String email;
    	private int id;
    	private String name;
    	private Birthday birthday;
    	private Date registDate;
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Birthday getBirthday() {
			return birthday;
		}
		public void setBirthday(Birthday birthday) {
			this.birthday = birthday;
		}
		public Date getRegistDate() {
			return registDate;
		}
		public void setRegistDate(Date registDate) {
			this.registDate = registDate;
		}
    }
    class Birthday{
    	private String birthday;

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
    	
    }
	@Override
	protected Feed doParseXML(String xml) throws Exception {
		return null;
	}
}
