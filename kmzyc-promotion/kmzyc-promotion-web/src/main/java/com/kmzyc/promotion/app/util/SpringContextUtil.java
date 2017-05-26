package com.kmzyc.promotion.app.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * <p>
 * 项目名称：product
 * </p>
 * <p>
 * 类名称：SpringContextUtil
 * </p>
 * <p>
 * 类描述：暂无
 * </p>
 * <p>
 * 创建人：
 * </p>
 * <p>
 * 创建时间：2012-9-24 下午05:13:52
 * </p>
 * <p>
 * 修改人：gwr
 * </p>
 * <p>
 * 修改时间：2012-9-24 下午05:13:52
 * </p>
 * <p>
 * 修改备注：
 * </p>
 * <p>
 * 
 * @version </p>
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
}
