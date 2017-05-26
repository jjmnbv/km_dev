package com.kmzyc.promotion.sys.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext _context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		_context = context;
	}

	public static ApplicationContext getApplicationContext() {
		return _context;
	}
}
