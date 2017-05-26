/**
 * project : B2B 康美健康商城
 * module : km-cms-web
 * package : com.pltfm.app.service.impl
 * date: 2016年9月14日下午5:38:24
 * Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.app.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ProductCertificateFileDAO;
import com.pltfm.cms.vobject.ProductCertificateFile;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

/**
 * 产品正式资质文件 Service <br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 下午5:38:24
 * @version
 * @see
 */
@Repository(value = "productCertificateFileDAO")
public class ProductCertificateFileDAOImpl implements ProductCertificateFileDAO {

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @Override
    @SuppressWarnings("unchecked")
    public List<ProductCertificateFile> findListByProductId(Long productId) throws SQLException {
        List<ProductCertificateFile> productCertificateFileList =
                sqlMapClient.queryForList("PRODUCT_CERTIFICATE_FILE.findListByProductId", productId);
        return productCertificateFileList;
    }
}
