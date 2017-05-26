package com.pltfm.sys.bean;


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
     static SqlMapClient sqlMap;
//	private static final String resource = "com/pltfm/sys/sqlmap/sql-map-config.xml";

    public BaseBean() {
        if (sqlMap == null) {
            sqlMap = SpringUtils.getBean("sqlMapClient");
        }
    }
}
