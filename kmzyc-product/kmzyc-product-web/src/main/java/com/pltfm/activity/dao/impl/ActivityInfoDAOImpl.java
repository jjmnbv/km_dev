package com.pltfm.activity.dao.impl;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pltfm.activity.dao.ActivityInfoDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivityInfoExample;

@Repository("activityInfoDao")
public class ActivityInfoDAOImpl extends BaseDao<ActivityInfo> implements ActivityInfoDAO {

    @Override
    public List<ActivityInfo> getActivityList(ActivityInfoExample example, int skip, int max) throws SQLException {
        return  sqlMapClient.queryForList("ACTIVITY_INFO.getActivityList", example, skip, max);
    }

    @Override
    public int countActivityList(ActivityInfoExample example) throws SQLException {
        return  (Integer)sqlMapClient.queryForObject("ACTIVITY_INFO.countActivityList", example);
    }

    @Override
    public ActivityInfo getActivityById(Long activityId) throws SQLException {
        return (ActivityInfo) sqlMapClient.queryForObject("ACTIVITY_INFO.getActivityById",activityId);
    }

    @Override
  public int countByExample(ActivityInfoExample example) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("ACTIVITY_INFO.ibatorgenerated_countByExample",
        example);
  }

  @Override
  public List selectByExampleWithoutCLOBs(ActivityInfoExample example, int skip, int max)
      throws SQLException {
    return sqlMapClient.queryForList("ACTIVITY_INFO.ibatorgenerated_selectByExample", example,
        skip, max);
  }

  @Override
  public ActivityInfo findActivityInfo(ActivityInfo activityInfo) throws SQLException {
    return (ActivityInfo) sqlMapClient.queryForObject("ACTIVITY_INFO.findActivityInfo",
        activityInfo);
  }

  @Override
  public ActivityInfo findActivityInfoById(Long activityId) throws SQLException {
    return (ActivityInfo) sqlMapClient.queryForObject("ACTIVITY_INFO.findActivityInfoById",
        activityId);
  }

  @Override
  public int updateActivityInfoForAudit(ActivityInfo activityInfo) throws SQLException {
    return sqlMapClient.update("ACTIVITY_INFO.updateActivityInfoForAudit", activityInfo);
  }

  @Override
  public int deleteActivityById(Long activityId) throws SQLException {
    return sqlMapClient.delete("ACTIVITY_INFO.ibatorgenerated_deleteByPrimaryKey", activityId);
  }

  @Override
  public int stopActivityById(Long activityId) throws SQLException {
    return sqlMapClient.update("ACTIVITY_INFO.stopActivityById", activityId);
  }

  @Override
  public int submitAuditActivityById(Long activityId) throws SQLException {
    return sqlMapClient.update("ACTIVITY_INFO.submitAuditActivityById", activityId);
  }

  @Override
  public int cancelSubmitAuditActivityById(Long activityId) throws SQLException {
    return sqlMapClient.update("ACTIVITY_INFO.cancelSubmitAuditActivityById", activityId);
  }

  @Override
  public int cancelAuditActivityById(Long activityId) throws SQLException {
    return sqlMapClient.update("ACTIVITY_INFO.cancelAuditActivityById", activityId);
  }


  @Override
  public Long saveActivity(ActivityInfo activityInfo) throws SQLException {
    return (Long) sqlMapClient.insert("ACTIVITY_INFO.ibatorgenerated_insert", activityInfo);
  }

  @Override
  public int updateActivityInfo(ActivityInfo activityInfo) throws SQLException {
    return sqlMapClient.update("ACTIVITY_INFO.ibatorgenerated_updateByPrimaryKeySelective",
        activityInfo);
  }

  @Override
  public List<String> queryInvitedSupplier(Long activityId) throws SQLException {
    List<String> supplierList =
        sqlMapClient.queryForList("ACTIVITY_INFO.queryInvitedSupplier", activityId);
    return supplierList;
  }
}
