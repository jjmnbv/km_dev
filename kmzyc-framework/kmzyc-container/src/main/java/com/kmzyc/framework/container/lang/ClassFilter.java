package com.kmzyc.framework.container.lang;


/**
 * 类过滤器。.
 * @author weishanyao
 */
public interface ClassFilter {

	/**
	 * 是否认可给定的类.
	 * 
	 * @param clazz 判断此组件(类)是否可以被接受.
	 * @param key 以此键将clazz保存于容器.
	 * 
	 * @return 如果给定的类被接受，则返回<code>true</code>，否则返回<code>false</code>.
	 * 
	 */
	boolean accept(Class<?> clazz, String key);
}
