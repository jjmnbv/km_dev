package com.pltfm.cms.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.ShopMain;
import com.pltfm.cms.dao.CmsSupplierAdcolumnDAO;
import com.pltfm.cms.vobject.CmsSupplierAdcolumn;

@Component("cmsSupplierAdcolumnDAO")
public class CmsSupplierAdcolumnDAOImpl implements CmsSupplierAdcolumnDAO {
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @Override
    public void insert(CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException {
        sqlMapClient.insert("CMS_SUPPLIER_ADCOLUMN.abatorgenerated_insert", supplierAdcolumn);

    }


    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }


    //广告位
    @Override
    public List queryAdcolumnByShopMainId(ShopMain shopMain)
            throws SQLException {
        List list = sqlMapClient.queryForList("CMS_ADCOLUMN.ibatorgenerated_queryAdcolumnListBySupplierId", shopMain);
        return list;
    }

    //广告
    @Override
    public List queryAdvByShopMainId(ShopMain shopMain) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_ADV.ibatorgenerated_queryAdvByShopMianId", shopMain);
        return list;
    }


    @Override
    public void update(CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException {
        sqlMapClient.update("CMS_SUPPLIER_ADCOLUMN.abatorgenerated_update", supplierAdcolumn);

    }


    @Override
    public List queryList(CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException {
        List list = sqlMapClient.queryForList("CMS_SUPPLIER_ADCOLUMN.abatorgenerated_queryAdcolumnList", supplierAdcolumn);
        return list;
    }


    @Override
    public int queryCount(CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException {
        int count = (Integer) sqlMapClient.queryForObject("CMS_SUPPLIER_ADCOLUMN.abatorgenerated_countByAdcolumn", supplierAdcolumn);
        return count;
    }


    @Override
    public CmsSupplierAdcolumn byId(CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException {
        return (CmsSupplierAdcolumn) sqlMapClient.queryForObject("CMS_SUPPLIER_ADCOLUMN.abatorgenerated_byId", supplierAdcolumn);

    }


    @Override
    public CmsSupplierAdcolumn byAdcolumnId(CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException {

        CmsSupplierAdcolumn msSupplierAdcolumn = (CmsSupplierAdcolumn) sqlMapClient.queryForObject("CMS_SUPPLIER_ADCOLUMN.abatorgenerated_byAdcolumnId", supplierAdcolumn);
        return msSupplierAdcolumn;

    }


    @Override
    public Integer checkAdvIsUpload(CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException {
        // TODO Auto-generated method stub
        return (Integer) sqlMapClient.queryForObject("CMS_SUPPLIER_ADCOLUMN.abatorgenerated_checkAdvIsUpload", supplierAdcolumn);
    }

    /***
     * 查询是否供应商下有广告
     * */
    @Override
    public List queryListAdv(Long supplierId)
            throws SQLException {
        List list = sqlMapClient.queryForList("CMS_SUPPLIER_ADCOLUMN.abatorgenerated_adv", supplierId);
        return list;
    }


    @Override
    public List querySupplierIdByAdcolumn(String str) throws SQLException {
        // TODO Auto-generated method stub
        return sqlMapClient.queryForList("CMS_SUPPLIER_ADCOLUMN.abatorgenerated_querySupplierIdByAdcolumn", str);
    }


}
