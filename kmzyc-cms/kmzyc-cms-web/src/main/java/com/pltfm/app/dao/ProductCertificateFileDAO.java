/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.app.service date:
 * 2016年9月14日下午5:37:41 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.cms.vobject.ProductCertificateFile;

/**
 * 产品正式资质文件 Service <br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 下午5:37:41
 * @version
 * @see
 */
public interface ProductCertificateFileDAO {
    public List<ProductCertificateFile> findListByProductId(Long productId) throws SQLException;
}
  