package com.kmzyc.framework.persistence.util;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import javax.annotation.Resource;


public class InjectSqlMapClient extends SqlSessionDaoSupport {

    @Resource(name = "sqlMapClient")
    protected void inject(SqlSessionTemplate sqlMapClient) {
        super.setSqlSessionTemplate(sqlMapClient);
    }

}
