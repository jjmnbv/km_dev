package com.pltfm.sys.bean;



import org.springframework.beans.BeansException;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.util.SpringUtils;


/**
 * 类 BaseBean 所有bean的基类，用户获取ibatis数据表配置文件
 *
 * @author  
 * @version 2.1
 */
public abstract class  BaseBean {
    protected static SqlMapClient sqlMap;

    public BaseBean()  {
        if(sqlMap == null){
            synchronized (BaseBean.class){
                if(sqlMap==null){
                    try {
                        sqlMap= SpringUtils.getBean("sqlMapClientSys");
                    } catch (BeansException e) {
                       throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
