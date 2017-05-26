/**
 * project : B2B 康美健康商城
 * module : km-cms-web
 * package : com.pltfm.app.service.impl
 * date: 2016年9月14日下午5:38:59
 * Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.app.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.SuppliersCertificateFileDAO;
import com.pltfm.cms.vobject.SuppliersCertificateFile;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

/**
 * 供应商资质文件(新) Service <br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 下午5:38:59
 * @version
 * @see
 */
@Repository(value = "suppliersCertificateFileDAO")
public class SuppliersCertificateFileDAOImpl implements SuppliersCertificateFileDAO {

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @Override
    @SuppressWarnings("unchecked")
    public List<SuppliersCertificateFile> findListByInfoId(Long infoId) throws SQLException {
        List<SuppliersCertificateFile> suppliersCertificateFileList =
                sqlMapClient.queryForList("SUPPLIERS_CERTIFICATE_FILE.findListByInfoId", infoId);
        return suppliersCertificateFileList;
    }

}
