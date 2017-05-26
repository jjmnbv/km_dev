package com.kmzyc.promotion.app.enums;

/**
 * 动态数据源各数据库切换标识 DBContextHolder.setDBType(DBType.userDataSource.name());
 * 
 * @author river
 * @date 2015年3月17日 上午10:29:34
 * 
 */
public enum DBType {
	userDataSource, // 用户数据库标识
	productDataSource
	// 产品数据库标识
}
