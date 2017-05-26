package com.pltfm.cms.service.impl;

import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.pltfm.cms.dao.CmsSupplyDao;
import com.pltfm.cms.service.CmsSupplyService;
import com.pltfm.cms.vobject.CommercialTenantBasicInfo;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;


@Component("cmsSupplyService")
public class CmsSupplyServiceImpl implements CmsSupplyService {
    @Resource(name = "cmsSupplyDao")
    private CmsSupplyDao cmsSupplyDao;

    /**
     * 查询供应商信息
     */
    public CommercialTenantBasicInfo getUserId(Long id) throws SQLException {
        return cmsSupplyDao.getUserId(id);
    }

    /**
     * 查询资历
     *
     * @param 供应商 Id
     */
    public List getCertificateList(Long id) throws SQLException {
        return cmsSupplyDao.getCertificateList(id);
    }

    /**
     * 查询供应商店铺主表
     *
     * @param 店铺 Id
     */
    public ShopMain getShopMain(Long id) throws SQLException {
        return cmsSupplyDao.getShopMain(id);
    }

    /**
     * 查询供应商店铺主表
     *
     * @param 店铺 Id
     */
    public List getShopMainList(List dataIds) throws Exception {
        List list = null;
        for (int i = 0; i < dataIds.size(); i++) {
            Object object = dataIds.get(i);
            Long idLong = Long.valueOf(object.hashCode());
            list = cmsSupplyDao.getShopMainList(idLong);
        }
        ;
        return list;
    }

    /**
     * 查询供应商是不是自营
     *
     * @param 供应商 Id
     */
    public SuppliersInfo getSuppliers(Long suppliersId) throws SQLException {
        return cmsSupplyDao.getSuppliers(suppliersId);
    }

    public CmsSupplyDao getCmsSupplyDao() {
        return cmsSupplyDao;
    }

    public void setCmsSupplyDao(CmsSupplyDao cmsSupplyDao) {
        this.cmsSupplyDao = cmsSupplyDao;
    }

    @Override
    public List queryShopMainList() throws SQLException {
        // TODO Auto-generated method stub
        return cmsSupplyDao.queryShopMainList();
    }
}
