package com.pltfm.activity.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pltfm.activity.dao.ActivityPromotionCostDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;

@Repository("activityPromotionCostDao")
public class ActivityPromotionCostDAOImpl extends BaseDao<ActivitySupplierEntry> implements ActivityPromotionCostDAO {

    @Override
    public int queryActivityPromotionCostCount(Map<String, String> map) throws SQLException {
        return (Integer) sqlMapClient.queryForObject(
                "ACTIVITY_PROMOTION_COST.queryActivityPromotionCostCount", map);
    }

    @Override
    public List<ActivitySupplierEntry> queryActivityPromotionCost(Map<String, String> map,
            int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("ACTIVITY_PROMOTION_COST.queryActivityPromotionCost", map, skip, max);
    }

    @Override
    public int querySupplierCostDetailCount(ActivitySku activitySku) throws SQLException {
        return (Integer) sqlMapClient.queryForObject(
                "ACTIVITY_PROMOTION_COST.querySupplierCostDetailCount", activitySku);
    }

    @Override
    public List<ActivitySku> querySupplierCostDetail(ActivitySku activitySku, int skip, int max)
            throws SQLException {
        return sqlMapClient.queryForList("ACTIVITY_PROMOTION_COST.querySupplierCostDetail", activitySku, skip, max);
    }


}
