package com.pltfm.activity.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pltfm.activity.dao.ActivitySupplierEntryDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;

@Repository("activitySupplierEntryDao")
public class ActivitySupplierEntryDAOImpl extends BaseDao
        implements ActivitySupplierEntryDAO {
    @Override
    public int countByEntryExample(Map map) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("ACTIVITY_SUPPLIER_ENTRY.countByEntryExample",
                map);
    }

    @Override
    public List selectByEntryExamples(Map map, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("ACTIVITY_SUPPLIER_ENTRY.selectByEntryExamples", map,
                skip, max);
    }

    @Override
    public ActivitySupplierEntry activityEntryDetail(Map map) throws SQLException {
        return (ActivitySupplierEntry) sqlMapClient.queryForObject(
                "ACTIVITY_SUPPLIER_ENTRY.activityEntryDetail", map);
    }

    @Override
    public void activityAuditEntryById(ActivitySupplierEntry ectivitySupplierEntry)
            throws SQLException {
        sqlMapClient.update("ACTIVITY_SUPPLIER_ENTRY.ibatorgenerated_updateByPrimaryKeySelective",
                ectivitySupplierEntry);
    }

    @Override
    public int batchSaveActivitySupplierEntry(List<ActivitySupplierEntry> activitySupplierEntryList)
            throws SQLException {
        return super.batchinsertNt(activitySupplierEntryList,
                "ACTIVITY_SUPPLIER_ENTRY.ibatorgenerated_insert");
    }

    @Override
    public int deleteSupplierEntryByActivityId(Long activityId) throws SQLException {
        return sqlMapClient.delete("ACTIVITY_SUPPLIER_ENTRY.deleteSupplierEntryByActivityId",
                activityId);
    }

    @Override
    public List verifyStatus(Long supplierEntryId) throws SQLException {
        return sqlMapClient.queryForList("ACTIVITY_SUPPLIER_ENTRY.verifyStatus", supplierEntryId);
    }

    @Override
    public int batchSaveActivityInviteSupplierEntry(
            List<ActivitySupplierEntry> activitySupplierEntryList) throws SQLException {
        return super.batchinsertNt(activitySupplierEntryList,
                "ACTIVITY_SUPPLIER_ENTRY.saveActivityInviteSupplierEntry");
    }

    @Override
    public int queryInviteSupplierEntryCount(Map map) throws SQLException {
        return (Integer) sqlMapClient.queryForObject(
                "ACTIVITY_SUPPLIER_ENTRY.queryInviteSupplierEntryCount", map);
    }

    @Override
    public List queryInviteSupplierEntry(Map map, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("ACTIVITY_SUPPLIER_ENTRY.queryInviteSupplierEntry", map,
                skip, max);
    }

    @Override
    public int batchUpdateDatas(String sqlstr, List<ActivitySku> list) throws SQLException {
        return super.batchUpdateData(sqlstr,list);
    }


}
