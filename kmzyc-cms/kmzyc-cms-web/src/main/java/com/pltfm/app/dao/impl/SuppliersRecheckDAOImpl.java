/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.app.service.impl date:
 * 2016年9月14日下午5:38:59 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.SuppliersRecheckDAO;
import com.pltfm.cms.vobject.SuppliersRecheck;

/**
 * 供应商资质重审信息 DAO实现 <br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 下午5:38:59
 * @version
 * @see
 */
@Repository(value = "suppliersRecheckDAO")
public class SuppliersRecheckDAOImpl implements SuppliersRecheckDAO {

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @Override
    @SuppressWarnings("unchecked")
    public List<SuppliersRecheck> findListBySupplierId(Long supplierId) throws SQLException {
        List<SuppliersRecheck> suppliersRecheckList =
                sqlMapClient.queryForList("SUPPLIERS_RECHECK.findListBySupplierId", supplierId);
        return suppliersRecheckList;
    }

}
