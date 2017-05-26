package com.pltfm.activity.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pltfm.activity.dao.ActivitySkuDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.ActivitySku;

@Repository("activitySkuDao")
public class ActivitySkuDAOImpl extends BaseDao<ActivitySku> implements ActivitySkuDAO {
  
  @Override
  public List activityProductList(Long supplierEntryId) throws SQLException {
    return sqlMapClient.queryForList("ACTIVITY_SKU.activityProductList", supplierEntryId);
  }

  @Override
  public int activityEntryProductCount(Map map) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("ACTIVITY_SKU.activityEntryProductCount", map);
  }

  @Override
  public List activityEntryProductList(Map map, int skip, int max) throws SQLException {
    return sqlMapClient.queryForList("ACTIVITY_SKU.activityEntryProductList", map,skip,max);
  }

  @Override
  public List exportActivitySupplierProductList(Map map) throws SQLException {
    return sqlMapClient.queryForList("ACTIVITY_SKU.activityEntryProductList", map);
  }

    @Override
    public int batchUpdateActivitySkuByEntryId(Map map) throws SQLException {
       return sqlMapClient.update("ACTIVITY_SKU.batchUpdateActivitySkuByEntryId", map);
    }

    @Override
    public List<String> getSkuInUnfinishedActivity(Map<String, Object> condition)
            throws SQLException {
        return (List<String>)sqlMapClient.queryForList("ACTIVITY_SKU.getSkuInUnfinishedActivity", condition);
    }
}