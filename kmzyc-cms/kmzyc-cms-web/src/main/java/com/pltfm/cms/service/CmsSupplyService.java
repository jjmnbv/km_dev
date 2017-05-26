package com.pltfm.cms.service;

import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.pltfm.cms.vobject.CommercialTenantBasicInfo;

import java.sql.SQLException;
import java.util.List;


public interface CmsSupplyService {
    /**
     * 查询供应商信息
     */
    public CommercialTenantBasicInfo getUserId(Long id) throws SQLException;

    /**
     * 查询供应商店铺主表
     *
     * @param 店铺 Id
     */
    public List getShopMainList(List dataIds) throws Exception;

    /**
     * 查询资历
     *
     * @param 供应商 Id
     */
    public List getCertificateList(Long id) throws SQLException;

    /**
     * 查询供应商是不是自营
     *
     * @param 供应商 Id
     */
    public SuppliersInfo getSuppliers(Long suppliersId) throws SQLException;

    /**
     * 查询供应商店铺主表
     *
     * @param 店铺 Id
     */
    public ShopMain getShopMain(Long id) throws SQLException;

    /**
     * 查询供应商店铺
     */
    public List queryShopMainList() throws SQLException;
}
