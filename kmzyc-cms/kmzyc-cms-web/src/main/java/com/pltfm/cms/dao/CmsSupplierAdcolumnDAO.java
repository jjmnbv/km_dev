package com.pltfm.cms.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.ShopMain;
import com.pltfm.cms.vobject.CmsSupplierAdcolumn;

public interface CmsSupplierAdcolumnDAO {
    //添加
    void insert(CmsSupplierAdcolumn supplierAdcolumn) throws SQLException;

    //修改
    void update(CmsSupplierAdcolumn supplierAdcolumn) throws SQLException;
    //列表

    List<CmsSupplierAdcolumn> queryList(CmsSupplierAdcolumn supplierAdcolumn) throws SQLException;

    /***
     * 查询是否供应商下有广告
     * */
    List queryListAdv(Long supplierId) throws SQLException;

    int queryCount(CmsSupplierAdcolumn supplierAdcolumn) throws SQLException;

    public List queryAdcolumnByShopMainId(ShopMain shopMain) throws SQLException;


    public List queryAdvByShopMainId(ShopMain shopMain) throws SQLException;

    public CmsSupplierAdcolumn byId(CmsSupplierAdcolumn supplierAdcolumn) throws SQLException;


    public CmsSupplierAdcolumn byAdcolumnId(CmsSupplierAdcolumn supplierAdcolumn) throws SQLException;

    public Integer checkAdvIsUpload(CmsSupplierAdcolumn supplierAdcolumn) throws SQLException;

    public List querySupplierIdByAdcolumn(String str) throws SQLException;
}
