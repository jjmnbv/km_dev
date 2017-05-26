package com.kmzyc.b2b.dao;

import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivityInfoExample;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/4/18 19:51
 */
public interface ActivityDAO {

    /**
     * 活动列表(按活动创建时间倒序排)
     * @param condition
     * <note>
     *     活动标签，收费类型，活动级别，所属行业,第几页，默认页数
     * </note>
     * @return
     * <note>
     *     活动ID,活动名称，活动简介，LOGO图片，活动关键字，活动有效时间段,活动是否可报名，第几页，总页数,总数
     * </note>
     */
    List<ActivityInfo> getActivityList(Map<String, Object> condition, int skip, int max) throws SQLException;

    /**
     * 活动记录条数
     *
     * @param condition
     * @return
     * @throws SQLException
     */
    int countActivityList(Map<String, Object> condition) throws SQLException;

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

}
