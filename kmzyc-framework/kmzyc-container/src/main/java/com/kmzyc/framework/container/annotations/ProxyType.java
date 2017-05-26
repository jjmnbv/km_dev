package com.kmzyc.framework.container.annotations;

import com.kmzyc.framework.container.interceptors.CustomInterceptor;
import com.kmzyc.framework.container.interceptors.LoggerInterceptor;
import com.kmzyc.framework.container.interceptors.SecurityInterceptor;
import com.kmzyc.framework.container.interceptors.TransactionInterceptor;
import com.kmzyc.framework.container.lang.Interceptor;

/**
 * 代理类型，包括事务，安全，日志等.
 * 
 * @author weishanyao
 *
 */
public enum ProxyType {

	TRANSACTION(new TransactionInterceptor()),
	SECURITY(new SecurityInterceptor()),
	LOG(new LoggerInterceptor()),
	CUSTOM(new CustomInterceptor());
	
	public final Interceptor interceptor;
	private ProxyType(Interceptor interceptor) {
	    this.interceptor = interceptor;
	}
}
