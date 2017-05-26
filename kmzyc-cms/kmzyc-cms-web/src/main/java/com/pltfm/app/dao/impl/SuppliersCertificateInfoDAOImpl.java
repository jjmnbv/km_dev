/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.app.service.impl date:
 * 2016年9月14日下午5:39:23 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.SuppliersCertificateInfoDAO;
import com.pltfm.cms.vobject.SuppliersCertificateInfo;

/**
 * 供应商资质信息 Service <br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 下午5:39:23
 * @version
 * @see
 */
@Repository(value = "suppliersCertificateInfoDAO")
public class SuppliersCertificateInfoDAOImpl implements SuppliersCertificateInfoDAO {

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @Override
    @SuppressWarnings("unchecked")
    public List<SuppliersCertificateInfo> findListBySupplierId(Long supplierId) throws SQLException {
        List<SuppliersCertificateInfo> suppliersCertificateInfoList =
                sqlMapClient.queryForList("SUPPLIERS_CERTIFICATE_INFO.findListBySupplierId", supplierId);
        return suppliersCertificateInfoList;
    }
}
