package com.pltfm.cms.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.pltfm.cms.dao.CmsSupplyDao;
import com.pltfm.cms.vobject.CommercialTenantBasicInfo;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component("cmsSupplyDao")
public class CmsSupplyDaoImpl implements CmsSupplyDao {
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /**
     * 查询供应商信息
     */
    public CommercialTenantBasicInfo getUserId(Long Id) throws SQLException {
        return (CommercialTenantBasicInfo) sqlMapClient.queryForObject(
                "CMS_SUPPLIER.ibatorgenerated_selectCommercialTenantId", Id);
    }

    /**
     * 查询供应商资历
     */
    public List getCertificateList(Long Id) throws SQLException {
        List list = sqlMapClient.queryForList(
                "CMS_SUPPLIER.ibatorgenerated_selectCertificate", Id);
        return list;
    }

    /**
     * 查询供应商店铺主表
     *
     * @param 店铺 Id
     */
    public ShopMain getShopMain(Long shopId) throws SQLException {
        ShopMain shopMain = new ShopMain();
        shopMain.setShopId(shopId);
        return (ShopMain) sqlMapClient.queryForObject(
                "CMS_SUPPLIER.ibatorgenerated_selectShopMain", shopMain);

    }

    /**
     * 查询供应商店铺主表
     *
     * @param 店铺 Id
     */
    public List getShopMainList(Long shopId) throws SQLException {
        ShopMain shopMain = new ShopMain();
        shopMain.setShopId(shopId);
        return sqlMapClient.queryForList(
                "CMS_SUPPLIER.ibatorgenerated_selectShopMain", shopMain);

    }

    /**
     * 查询供应商是不是自营
     *
     * @param 供应商 Id
     */
    public SuppliersInfo getSuppliers(Long suppliersId) throws SQLException {
        return (SuppliersInfo) sqlMapClient.queryForObject(
                "CMS_SUPPLIER.ibatorgenerated_selectSuppliersInfo", suppliersId);

    }

    /**
     * 查询供应商店铺
     */
    public List queryShopMainList() throws SQLException {

        return sqlMapClient.queryForList("CMS_SUPPLIER.getSupplierByCloseStatus");

    }

}
