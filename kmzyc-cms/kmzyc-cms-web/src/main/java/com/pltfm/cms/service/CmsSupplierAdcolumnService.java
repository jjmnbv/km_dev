package com.pltfm.cms.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.kmzyc.supplier.model.ShopMain;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.CmsSupplierAdcolumn;
import com.pltfm.cms.vobject.CmsTemplate;


public interface CmsSupplierAdcolumnService {
    void insert(CmsSupplierAdcolumn supplierAdcolumn) throws SQLException;

    public void insertSupplierAdcolumn(CmsTemplate cmsTemplate, CmsAdcolumn cmsAdcolumn, CmsSupplierAdcolumn supplierAdcolumn) throws Exception;


    public List queryAdcolumnByShopMainId(ShopMain shopMain)
            throws SQLException;

    public List queryAdvByShopMainId(ShopMain shopMain) throws SQLException;

    /***
     * 查询是否供应商下有广告
     * */
    List queryListAdv(Long supplierId) throws SQLException;

    //修改
    void update(CmsSupplierAdcolumn supplierAdcolumn) throws SQLException;

    Page queryList(Page page, CmsSupplierAdcolumn supplierAdcolumn) throws SQLException;

    public CmsSupplierAdcolumn byId(CmsSupplierAdcolumn supplierAdcolumn) throws SQLException;

    public Integer checkAdvIsUpload(CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException;


    public CmsSupplierAdcolumn byAdcolumnId(CmsSupplierAdcolumn cmsSupplierAdcolumn) throws SQLException;

    public List querySupplierIdByAdcolumn(String str) throws SQLException;
}
