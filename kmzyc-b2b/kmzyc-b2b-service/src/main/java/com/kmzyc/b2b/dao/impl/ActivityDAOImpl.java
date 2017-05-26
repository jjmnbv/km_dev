package com.kmzyc.b2b.dao.impl;

import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.ActivityDAO;
import com.pltfm.app.vobject.ActivityInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/4/18 19:53
 */
@Component("activityDAO")
public class ActivityDAOImpl extends DaoImpl implements ActivityDAO {

    @Resource(name = "sqlMapClient")
    private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

    @Override
    public List<ActivityInfo> getActivityList(Map<String, Object> condition, int skip, int max) throws SQLException {
        return  sqlMapClient.queryForList("activity.getActivityList", condition, skip, max);
    }

    @Override
    public int countActivityList(Map<String, Object> condition) throws SQLException {
        return  (Integer)sqlMapClient.queryForObject("activity.countActivityList", condition);
    }

    @Override
    public ActivityInfo getActivityById(Long activityId) throws SQLException {
        return (ActivityInfo) sqlMapClient.queryForObject("activity.getActivityById",activityId);
    }
}
