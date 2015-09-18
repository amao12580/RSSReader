package per.rss.core.base.constant;

/**
 * 定义一般的常量，要考虑使用枚举来实现
 * 
 * @author cifpay
 *
 */
public class CommonConstant {
	
	public enum status {
		success,
		failed,
    };
    
    
	public final static int status_success = 1;
	public final static int status_failed = 0;
	
	public final static int default_feed_new_max = 20;//每个订阅源默认最多解析20篇文章
	
	public static void main(String[] args) {
		System.out.println(CommonConstant.status.success);
	}
}
