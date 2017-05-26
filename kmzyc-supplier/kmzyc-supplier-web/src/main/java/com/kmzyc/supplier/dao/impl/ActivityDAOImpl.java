package com.kmzyc.supplier.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.dao.ActivityDAO;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivityInfoExample;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * 功能：活动DAO实现
 *
 * @author Zhoujiwei
 * @since 2016/3/18 14:41
 */
@Repository("activityDAO")
public class ActivityDAOImpl extends BaseDAO implements ActivityDAO {

    @Override
    public boolean isStop(Long activityId) throws SQLException {
        int i = (Integer)sqlMapClient.queryForObject("ACTIVITY_INFO.isStop", activityId);
        return i == 1;
    }

    @Override
    public void getActivityList(Pagination pagination, String listSql, String countSql) throws SQLException {
        findPaginationByPage(sqlMapClient, listSql, countSql, pagination);
    }

    @Override
    public ActivityInfo getActivityById(Long activityId) throws SQLException {
        ActivityInfoExample activityInfoExample = new ActivityInfoExample();
        ActivityInfoExample.Criteria criteria = activityInfoExample.createCriteria();
        criteria.andActivityIdEqualTo(activityId);
        return (ActivityInfo)sqlMapClient.queryForObject("ACTIVITY_INFO.selectByExample", activityInfoExample);
    }

    @Override
    public String bnesAcctTransactionById(Integer accountTransactionId) throws SQLException {
        return (String)sqlMapClient.queryForObject("ACTIVITY_INFO.bnesAcctTransactionById", accountTransactionId);
    }
}
