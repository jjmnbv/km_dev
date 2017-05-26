
package com.kmzyc.mailmobile.util;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring容器内java bean的工具类
 * @author river
 *
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

	private static ApplicationContext _context;
	
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		_context = context;
	}

	public static ApplicationContext getApplicationContext(){
		return _context;
	}
	
	public static Object getBean(String beanName) {
		return _context.getBean(beanName);
	}
}
