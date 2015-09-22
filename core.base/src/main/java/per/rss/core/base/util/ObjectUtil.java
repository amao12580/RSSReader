package per.rss.core.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class ObjectUtil {
	
	private static Logger logger=LoggerFactory.getLogger(ObjectUtil.class);

	public static <T> T copyProperties(Object srcObj, Class<T> toClass) {
		try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);   
			T instance = toClass.newInstance();
			BeanUtils.copyProperties(instance,srcObj);
			return instance;
		} catch (Exception e) {
			throw new RuntimeException("拷贝对象属性值异常", e);
		}
	}
	
	/**
	 * 获得一个对象各个属性的字节流
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> getProperty(Object entityName)throws Exception {
		Class c = entityName.getClass();
		Field field[] = c.getDeclaredFields();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (Field f : field) {
			Object value = invokeMethod(entityName, f.getName(), null);
			if (value != null && !"serialVersionUID".equals(f.getName())) {
				if (!f.getType().isAssignableFrom(Date.class)&& !f.getType().isAssignableFrom(List.class)) {
					String val=value.toString();
					String newval=URLEncoder.encode(val,"UTF-8");
					map.put(f.getName(),newval);
				} else if (f.getType().isAssignableFrom(Date.class)) {
					SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put(f.getName(), dateFm.format(value));
				} else if (f.getType().isAssignableFrom(List.class)) {
					List<Object> vs = (List<Object>)value;
					int i = 0;
					for (Object o : vs) {
						i++;
						map.put(f.getName() + "|@#|" + i, JSON.toJSON(o));
					}
				}
			}
		}
		return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object invokeMethod(Object owner,String methodName,Object[] args) throws Exception {
		Class ownerClass = owner.getClass();
		methodName = methodName.substring(0, 1).toUpperCase()+ methodName.substring(1);
		Method method = null;
		try {
			method = ownerClass.getMethod("get" + methodName);
		} catch (SecurityException e) {
			logger.error( " system throws SecurityException when invoke" + methodName + " method");
			throw e;
		} catch (NoSuchMethodException e) {
			logger.error( " can't find 'get" + methodName + "' method");
			throw e;
		}
		return method.invoke(owner);
	}
	
	 public static boolean isNotEmptyList(Object value) {
		  try {
			  	if(null==value){
			  		return false;
			  	}
		  		Object[] os = (Object[])value;
		  		if(os.length==0){
		  			return false;
		  		}
		  		return true;
		  } catch (Exception e) {
			  return true;
		  }
	 }

	
}
