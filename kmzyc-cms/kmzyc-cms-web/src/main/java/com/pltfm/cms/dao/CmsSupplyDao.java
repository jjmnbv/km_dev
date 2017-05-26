package com.pltfm.cms.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.pltfm.cms.vobject.CommercialTenantBasicInfo;


public interface CmsSupplyDao {
    /**
     * 查询供应商信息
     */
    CommercialTenantBasicInfo getUserId(Long Id) throws SQLException;

    /**
     * 查询供应商店铺主表
     *
     * @param 店铺 Id
     */
    public List getShopMainList(Long shopId) throws SQLException;

    /**
     * 查询供应商资历
     */

    List getCertificateList(Long Id) throws SQLException;

    /**
     * 查询供应商店铺主表
     *
     * 店铺 Id
     */
    ShopMain getShopMain(Long shopId) throws SQLException;

    /**
     * 查询供应商是不是自营
     *
     * @param 供应商 Id
     */
    SuppliersInfo getSuppliers(Long suppliersId) throws SQLException;

    /**
     * 查询供应商店铺
     */
    public List queryShopMainList() throws SQLException;
}
