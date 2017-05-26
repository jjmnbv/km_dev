package com.pltfm.activity.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;



public interface ActivitySupplierEntryDAO {
    /**
     * 活动报名商家记录条数
     * 
     * @param Map
     * @return
     * @throws SQLException
     */
    int countByEntryExample(Map map) throws SQLException;

    /**
     * 活动报名商家列表
     * 
     * @param Map
     * @param skip
     * @param max
     * @return
     * @throws SQLException
     */
    List selectByEntryExamples(Map map, int skip, int max) throws SQLException;

    /**
     * 查询活动报名商家详情
     * 
     * @param map
     * @throws SQLException
     */
    ActivitySupplierEntry activityEntryDetail(Map map) throws SQLException;

    /**
     * 审核校验
     * 
     * @param activityEntryId
     * @throws SQLException
     */
    List verifyStatus(Long supplierEntryId) throws SQLException;

    /**
     * 审核
     * 
     * @param activityEntryId
     * @throws SQLException
     */
    void activityAuditEntryById(ActivitySupplierEntry activitySupplierEntry) throws SQLException;

    /**
     * 保存商家报名
     * 
     * @param activitySupplierEntry
     * @return
     * @throws SQLException
     */
    int batchSaveActivitySupplierEntry(List<ActivitySupplierEntry> activitySupplierEntryList)
            throws SQLException;
    int batchUpdateDatas (String sqlstr,List<ActivitySku> list)throws SQLException;

    /**
     * 根据活动ID删除商家报名
     * 
     * @param activityId
     * @return
     * @throws SQLException
     */
    int deleteSupplierEntryByActivityId(Long activityId) throws SQLException;

    /**
     * 保存邀请商家
     * 
     * @param activitySupplierEntryList
     * @return
     * @throws SQLException
     */
    int batchSaveActivityInviteSupplierEntry(List<ActivitySupplierEntry> activitySupplierEntryList)
            throws SQLException;

    /**
     * 活动已邀请商家记录条数
     * 
     * @param Map
     * @return
     * @throws SQLException
     */
    int queryInviteSupplierEntryCount(Map map) throws SQLException;

    /**
     * 活动已邀请商家列表
     * 
     * @param Map
     * @param skip
     * @param max
     * @return
     * @throws SQLException
     */
    List queryInviteSupplierEntry(Map map, int skip, int max) throws SQLException;


}
