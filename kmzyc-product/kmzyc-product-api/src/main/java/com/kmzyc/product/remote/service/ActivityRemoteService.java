package com.kmzyc.product.remote.service;

import com.kmzyc.commons.exception.ServiceException;

import java.util.Map;

/**
 * 功能：活动远程接口
 *
 * @author Zhoujiwei
 * @since 2016/4/18 11:49
 */
public interface ActivityRemoteService {

    /**
     * 活动列表
     * @param condition
     * <note>
     *     活动标签（多选用英文,隔开）activityLabel：
     *     所属行业（多选用英文,隔开）industry：
     *     收费类型chargeType：1免费/0收费
     *     活动级别activityLevel：
     *     第几页pageIndex：
     *     页码pageSize：
     * </note>
     * @return
     * <note>
     *     活动ID(activityId),
     *     活动名称(activityName)，
     *     活动简介(activityDesc)，
     *     LOGO图片(logoPath)，
     *     活动关键字(activitySeo)，
     *     活动有效时间段{(activityStartTime),(activityEndTime)},
     *     活动是否可报名(canEntry)，
     *     第几页(pageIndex)，
     *     总页数(pageCount),
     *     总数(recordCount)
     * </note>
     */
    String getActivityList(Map<String, String> condition) throws ServiceException;


    /**
     * 获取活动详情
     *
     * @param activityId  活动ID
     *
     * @return
     * <note>
     *     活动名称(activityName)，活动简介(activityDesc)，LOGO图片(logoPath)，报名时间段{(entryStartTime),(entryEndTime)}，
     *     活动时间段{(activityStartTime),(activityEndTime)}，关键字(activitySeo)，活动标签(activityLabel)--字典，
     *     所属行业(industry)--字典，收费类型(chargeType)--字典，活动级别(activityLevel)--字典，
     *     活动介绍(activityIntroduce)，活动答疑(activityQuestions)
     * </note>
     * @throws ServiceException
     */
    String getActivityById(Long activityId) throws ServiceException;
}
