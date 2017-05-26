package com.pltfm.activity.service;

import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.ActivityInfo;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

public interface ActivityService {

    /**
     * 更新活动缓存
     *
     * @param activityInfo          活动信息
     * @param activityId            活动id
     * @param needUpdateSupplierMax 是否需要更新活动商家上限
     */
    void updateActivityInfoCache(ActivityInfo activityInfo, Long activityId,
                                 boolean needUpdateSupplierMax) throws ServiceException;
    /**
     * 活动列表(按活动创建时间倒序排)
     *
     * @param param <note>
     *              活动标签，收费类型，活动级别，所属行业,第几页，默认页数
     *              </note>
     * @return <note>
     * 活动ID,活动名称，活动简介，LOGO图片，活动关键字，活动有效时间段,活动是否可报名，第几页，总页数,总数
     * </note>
     */
    Map<String, Object> getActivityList(Map<String, String> param) throws ServiceException;

    /**
     * 获取活动详情
     *
     * @param activityId 活动ID
     * @return <note>
     * 活动名称，活动简介，LOGO图片，报名时间段，活动时间段，关键字，活动标签，
     * 所属行业，收费类型，活动级别，活动介绍，活动答疑
     * </note>
     * @throws ServiceException
     */
    ActivityInfo getActivityById(Long activityId) throws ServiceException;

    /**
     * 分页 查找活动数据
     *
     * @param page
     * @param activityInfo
     */
    void searchPage(Page page, ActivityInfo activityInfo, int activityListType) throws ServiceException;

    /**
     * 新增活动信息
     *
     * @param activityInfo
     * @throws ServiceException
     */
    void saveActivityInfo(ActivityInfo activityInfo, List<Long> brandIdList, List<Long> categoryIdList,
                          List<Long> supplierIdList) throws ServiceException;

    /**
     * 修改活动信息
     *
     * @param activityInfo
     * @throws ServiceException
     */
    void updateActivityInfo(ActivityInfo activityInfo, List<Long> brandIdList, List<Long> categoryIdList,
                            List<Long> supplierIdList) throws ServiceException;

    /**
     * 根据ID删除活动
     *
     * @param activityId
     * @return
     * @throws ServiceException
     */
    void deleteActivityById(Long activityId) throws ServiceException;

    /**
     * 根据ID终止活动
     *
     * @param activityId
     * @return
     * @throws ServiceException
     */
    void stopActivityById(Long activityId) throws ServiceException;

    /**
     * 提交审核
     *
     * @param activityId
     * @return
     * @throws ServiceException
     */
    void submitAuditActivityById(Long activityId) throws ServiceException;

    /**
     * 撤销提审
     *
     * @param activityId
     * @return
     * @throws ServiceException
     */
    void cancelSubmitAuditActivityById(Long activityId) throws ServiceException;

    /**
     * 撤销审核
     *
     * @param activityId
     * @return
     * @throws ServiceException
     */
    void cancelAuditActivityById(Long activityId) throws ServiceException;

    /**
     * 根据活动id查询活动详细信息
     *
     * @param activityId
     * @return
     * @throws ServiceException
     */
    ActivityInfo findActivityInfoById(Long activityId) throws ServiceException;
}
