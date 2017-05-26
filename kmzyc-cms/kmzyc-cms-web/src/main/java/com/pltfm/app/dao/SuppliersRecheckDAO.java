/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.app.service date:
 * 2016年9月14日下午5:36:58 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.cms.vobject.SuppliersRecheck;

/**
 * 供应商资质重审信息 DAO <br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 下午5:36:58
 * @version
 * @see
 */
public interface SuppliersRecheckDAO {
    /** 通过供应商ID 获取供应商资质重审信息 */
    public List<SuppliersRecheck> findListBySupplierId(Long supplierId) throws SQLException;
}
  