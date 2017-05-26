package com.pltfm.activity.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;

public interface ActivityPromotionCostDAO {
    /**
     * 查询活动的推广费用总记录条数（渠道推广）
     * 
     * @param map
     * @return
     * @throws SQLException
     */
    int queryActivityPromotionCostCount(Map<String, String> map) throws SQLException;

    /**
     * 查询活动的推广费用（渠道推广）
     * 
     * @param map
     * @return
     * @throws SQLException
     */
    List<ActivitySupplierEntry> queryActivityPromotionCost(Map<String, String> map, int skip,
            int max) throws SQLException;

    /**
     * 查询活动商家消费明细总记录条数（渠道推广）
     * 
     * @param activitySku
     * @return
     * @throws SQLException
     */
    int querySupplierCostDetailCount(ActivitySku activitySku) throws SQLException;

    /**
     * 查询活动商家消费明细（渠道推广）
     * 
     * @param activitySku
     * @return
     * @throws SQLException
     */
    List<ActivitySku> querySupplierCostDetail(ActivitySku activitySku, int skip, int max)
            throws SQLException;

}
