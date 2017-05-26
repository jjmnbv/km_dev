package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


import com.pltfm.app.vobject.SupplierForExport;
import org.springframework.stereotype.Repository;

import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.kmzyc.supplier.model.User;
import com.kmzyc.supplier.model.example.MerchantInfoExample;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.SupplierAuditDAO;
import com.kmzyc.commons.page.Page;

@Repository("supplierAuditDao")
public class SupplierAuditDAOImpl extends BaseDao implements SupplierAuditDAO {

    public int countByExample(MerchantInfoOrSuppliers example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("SUPPLIERAUDIT.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(MerchantInfo example) throws SQLException {
        int rows = sqlMapClient.delete("SUPPLIERAUDIT.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long merchantId) throws SQLException {
        MerchantInfo key = new MerchantInfo();
        key.setMerchantId(merchantId);
        int rows = sqlMapClient.delete("SUPPLIERAUDIT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(MerchantInfo record) throws SQLException {
        sqlMapClient.insert("SUPPLIERAUDIT.ibatorgenerated_insert", record);
    }

    public Long insertSelective(MerchantInfo record) throws SQLException {
        return (Long) sqlMapClient.insert("SUPPLIERAUDIT.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(MerchantInfoOrSuppliers record, Page page) throws SQLException {
        List list = sqlMapClient.queryForList("SUPPLIERAUDIT.ibatorgenerated_selectBySuppOrMer", record,
                        (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
        return list;
    }

    public MerchantInfo selectByPrimaryKey(Long merchantId) throws SQLException {
        MerchantInfo key = new MerchantInfo();
        key.setMerchantId(merchantId);
        MerchantInfo record =
                (MerchantInfo) sqlMapClient.queryForObject(
                        "SUPPLIERAUDIT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(MerchantInfo record, MerchantInfoExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("MerchantOrSupplier.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(MerchantInfo record, MerchantInfoExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("MerchantOrSupplier.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MerchantInfo record) throws SQLException {
        int rows = sqlMapClient.update("SUPPLIERAUDIT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MerchantInfo record) throws SQLException {
        int rows = sqlMapClient.update("SUPPLIERAUDIT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends MerchantInfo {
        private Object record;

        public UpdateByExampleParms(Object record, MerchantInfoExample example) {
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public User selectUserByUserName(String userName) throws SQLException {
        User user = new User();
        user.setLoginAccount(userName);
        return (User) sqlMapClient.queryForObject("User.queryUserByUserName", user);
    }

    @Override
    public MerchantInfo selectByUserLoginId(MerchantInfo merchant) throws SQLException {
        return (MerchantInfo) sqlMapClient.queryForObject("SUPPLIERAUDIT.selectByloginId", merchant);
    }

    public List<MerchantInfoOrSuppliers> getSupplierByCloseStatus(MerchantInfoOrSuppliers record)
            throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERAUDIT.getSupplierByCloseStatus", record);
    }

    public List<MerchantInfoOrSuppliers> selectPageSupplierIdAndMerchantName(MerchantInfoOrSuppliers record) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERAUDIT.ibatorgenerated_selectPageBySuppOrMer", record);
    }

    @Override
    public Integer selectCountSupplierIdAndMerchantName(MerchantInfoOrSuppliers record) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("SUPPLIERAUDIT.ibatorgenerated_selectPageCountBySuppOrMer", record);
    }

    public List<MerchantInfo> selectByCompanyName1(MerchantInfo merchant) throws SQLException {
        return (List<MerchantInfo>) sqlMapClient
                .queryForList("SUPPLIERAUDIT.selectBycorName", merchant);

    }

    @Override
    public List<SupplierForExport> getSupplierListForExcel(MerchantInfoOrSuppliers record) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERAUDIT.getSupplierForExcel", record);
    }

    @Override
    public int countItemMer(MerchantInfoOrSuppliers mer) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("SUPPLIERAUDIT.countItemMer", mer);
    }

    @Override
    public List<MerchantInfoOrSuppliers> queryMerList(MerchantInfoOrSuppliers mer, Page page)
            throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERAUDIT.queryMerList", mer, (page.getPageNo() - 1)
                * page.getPageSize(), page.getPageSize());
    }

    @Override
    public List<MerchantInfoOrSuppliers> querySuppliersForInviteList(MerchantInfoOrSuppliers mer,
                                                                     Long activityId, Page page) throws SQLException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("activityId", activityId.toString());
        map.put("corporateName", mer.getCorporateName());
        map.put("supplierType", mer.getSupplierType().toString());
        return sqlMapClient.queryForList("SUPPLIERAUDIT.querySuppliersForInviteList", map,
                (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
    }

    @Override
    public int countSuppliersForInvite(MerchantInfoOrSuppliers mer, Long activityId)
            throws SQLException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("activityId", activityId.toString());
        map.put("corporateName", mer.getCorporateName());
        map.put("supplierType", mer.getSupplierType().toString());
        return (Integer) sqlMapClient.queryForObject("SUPPLIERAUDIT.countSuppliersForInvite", map);
    }

}