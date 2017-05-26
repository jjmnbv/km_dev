package com.pltfm.activity.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivityInfoExample;


public interface ActivityInfoDAO {

    /**
     * 活动列表(按活动创建时间倒序排)
     * @param example
     * <note>
     *     活动标签，收费类型，活动级别，所属行业,第几页，默认页数
     * </note>
     * @return
     * <note>
     *     活动ID,活动名称，活动简介，LOGO图片，活动关键字，活动有效时间段,活动是否可报名，第几页，总页数,总数
     * </note>
     */
    List<ActivityInfo> getActivityList(ActivityInfoExample example, int skip, int max) throws SQLException;

    /**
     * 活动记录条数
     *
     * @param example
     * @return
     * @throws SQLException
     */
    int countActivityList(ActivityInfoExample example) throws SQLException;

    /**
     * 获取活动详情
     *
     * @param activityId  活动ID
     *
     * @return
     * <note>
     *     活动名称，活动简介，LOGO图片，报名时间段，活动时间段，关键字，活动标签，
     *     所属行业，收费类型，活动级别，活动介绍，活动答疑
     * </note>
     * @throws SQLException
     */
    ActivityInfo getActivityById(Long activityId) throws SQLException;

  /**
   * 活动记录条数
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  int countByExample(ActivityInfoExample example) throws SQLException;

  /**
   * 活动列表（不含CLOB字段）
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  List selectByExampleWithoutCLOBs(ActivityInfoExample example, int skip, int max)
      throws SQLException;

  /**
   * 查找活动基本信息
   * 
   * @param avcivity
   * @return
   * @throws SQLException
   */
  public ActivityInfo findActivityInfo(ActivityInfo activityInfo) throws SQLException;

  /**
   * 根据活动id查询活动详细信息
   * 
   * @param activityId
   * @return
   * @throws SQLException
   */
  public ActivityInfo findActivityInfoById(Long activityId) throws SQLException;

  public int updateActivityInfoForAudit(ActivityInfo activityInfo) throws SQLException;

  /**
   * 根据ID删除活动
   * 
   * @param activityId
   * @return
   * @throws Exception
   */
  public int deleteActivityById(Long activityId) throws SQLException;

  /**
   * 根据ID终止活动
   * 
   * @param activityId
   * @return
   * @throws Exception
   */
  public int stopActivityById(Long activityId) throws SQLException;

  /**
   * 提交审核
   * 
   * @param activityId
   * @return
   * @throws Exception
   */
  public int submitAuditActivityById(Long activityId) throws SQLException;

  /**
   * 撤销提审
   * 
   * @param activityId
   * @return
   * @throws Exception
   */
  public int cancelSubmitAuditActivityById(Long activityId) throws SQLException;

  /**
   * 撤销审核
   * 
   * @param activityId
   * @return
   * @throws Exception
   */
  public int cancelAuditActivityById(Long activityId) throws SQLException;

  /**
   * 保存活动信息
   * 
   * @param activityInfo
   * @return
   * @throws SQLException
   */
  public Long saveActivity(ActivityInfo activityInfo) throws SQLException;


  /**
   * 修改活动信息
   * 
   * @param activityInfo
   * @return
   * @throws SQLException
   */
  int updateActivityInfo(ActivityInfo activityInfo) throws SQLException;

  /*
   * 查找活动已邀请的商家
   */
  List<String> queryInvitedSupplier(Long activityId) throws SQLException;
}
