/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.app.service date:
 * 2016年9月14日下午5:36:20 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.cms.vobject.SuppliersCertificateInfo;

/**
 * 供应商资质信息 Service <br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 下午5:36:20
 * @version
 * @see
 */
public interface SuppliersCertificateInfoDAO {
    /** 通过供应商ID 获取资质信息列表 */
    public List<SuppliersCertificateInfo> findListBySupplierId(Long supplierId) throws SQLException;
}
  