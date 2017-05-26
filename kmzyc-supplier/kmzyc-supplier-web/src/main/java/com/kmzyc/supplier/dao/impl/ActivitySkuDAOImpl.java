package com.kmzyc.supplier.dao.impl;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.dao.ActivitySkuDAO;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySkuExample;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/24 14:29
 */
@Repository("activitySkuDAO")
public class ActivitySkuDAOImpl extends BaseDAO implements ActivitySkuDAO {

    @Override
    public Pagination selectProductSkuList4Activity(Pagination page) throws SQLException {
        return findPaginationByPage(sqlMapClient, "ACTIVITY_SKU.selectProductSkuList4Activity",
                "ACTIVITY_SKU.countProductSkuList4Activity", page);
    }

    @Override
    public int batchSaveActivitySku(List<ActivitySku> skuList) throws SQLException {
        return batchInsertData("ACTIVITY_SKU.insertSelective", skuList);
    }

    @Override
    public Long saveActivitySku(ActivitySku activitySku) throws SQLException {
        return (Long) sqlMapClient.insert("ACTIVITY_SKU.insertSelective", activitySku);
    }

    @Override
    public List<String> getSkuInUnfinishedActivity(Map<String, Object> condition) throws SQLException {
        return (List<String>)sqlMapClient.queryForList("ACTIVITY_SKU.getSkuInUnfinishedActivity", condition);
    }

    @Override
    public List<ActivitySkuVo> getActivitySku(Map<String, Object> condition) throws SQLException {
        return (List<ActivitySkuVo>)sqlMapClient.queryForList("ACTIVITY_SKU.getActivitySku", condition);
    }

    @Override
    public int deleteActivitySku(ActivitySkuExample example) throws SQLException {
        return sqlMapClient.delete("ACTIVITY_SKU.deleteByExample", example);
    }

    @Override
    public int batchUpdateActivitySku(List<ActivitySku> skuList) {
        return batchUpdateData("ACTIVITY_SKU.updateByPrimaryKeySelective", skuList);
    }
}
