package com.kmzyc.supplier.dao.impl;

import com.kmzyc.supplier.dao.ActivitySupplierDAO;
import com.pltfm.app.vobject.ActivitySupplierEntry;
import com.pltfm.app.vobject.ActivitySupplierEntryExample;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/18 14:41
 */
@Repository("activitySupplierDAO")
public class ActivitySupplierDAOImpl extends BaseDAO implements ActivitySupplierDAO {

    @Override
    public ActivitySupplierEntry selectByExample(ActivitySupplierEntryExample example) throws SQLException {
        return (ActivitySupplierEntry)sqlMapClient.queryForObject("ACTIVITY_SUPPLIER_ENTRY.selectByExample", example);
    }

    @Override
    public int updateByPrimaryKeySelective(ActivitySupplierEntry activitySupplierEntry) throws SQLException {
        return sqlMapClient.update("ACTIVITY_SUPPLIER_ENTRY.updateByPrimaryKeySelective", activitySupplierEntry);
    }

    @Override
    public Integer getActivitySupplierType(Map<String, Object> paramMap) throws SQLException {
        return (Integer)sqlMapClient.queryForObject("ACTIVITY_SUPPLIER_ENTRY.getActivitySupplierType", paramMap);
    }

    @Override
    public Long getSupplierEntryId(Map<String, Object> paramMap) throws SQLException {
        return (Long)sqlMapClient.queryForObject("ACTIVITY_SUPPLIER_ENTRY.getSupplierEntryId", paramMap);
    }

    @Override
    public BigDecimal getActivityTotalPrice(Map<String, Object> paramMap) throws SQLException {
        return (BigDecimal)sqlMapClient.queryForObject("ACTIVITY_SUPPLIER_ENTRY.getActivityTotalPrice", paramMap);
    }

    @Override

    public Long saveSupplierEntry(ActivitySupplierEntry supplierEntry) throws SQLException {
        return (Long)sqlMapClient.insert("ACTIVITY_SUPPLIER_ENTRY.insertSelective", supplierEntry);
    }

    @Override
    public void updateSupplierEntry(ActivitySupplierEntry supplierEntry) throws SQLException {
        sqlMapClient.insert("ACTIVITY_SUPPLIER_ENTRY.updateByPrimaryKeySelective", supplierEntry);
    }

    @Override
    public Map<String, Long> getActivitySupplierEntryStatus(Map<String, Object> paramMap) throws SQLException {
        return (Map)sqlMapClient.queryForObject("ACTIVITY_SUPPLIER_ENTRY.getActivitySupplierEntryStatus", paramMap);
    }

    @Override
    public int appendPrice(ActivitySupplierEntry supplierEntry) throws SQLException {
        return sqlMapClient.update("ACTIVITY_SUPPLIER_ENTRY.appendPrice", supplierEntry);
    }

    @Override
    public Map<String, BigDecimal> getShopScore(Long supplierId) throws SQLException {
        return (Map)sqlMapClient.queryForObject("ACTIVITY_SUPPLIER_ENTRY.getShopScore", supplierId);
    }
}
