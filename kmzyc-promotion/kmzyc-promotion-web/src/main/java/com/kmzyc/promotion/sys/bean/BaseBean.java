package com.kmzyc.promotion.sys.bean;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.util.SpringUtils;

/**
 * 类 BaseBean 所有bean的基类，用户获取ibatis数据表配置文件
 * 
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public abstract class BaseBean {
    Logger log = LoggerFactory.getLogger(this.getClass());
    protected static SqlMapClient sqlMap;

    public BaseBean() {
        if (sqlMap == null) {
            sqlMap = SpringUtils.getBean("sqlMapClient");
        }
    }
}
