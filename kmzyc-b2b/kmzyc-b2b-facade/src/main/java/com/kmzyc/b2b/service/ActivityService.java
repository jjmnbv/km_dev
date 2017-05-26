package com.kmzyc.b2b.service;

import java.util.Map;

import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ActivityInfo;

/**
 * 功能：活动远程接口
 *
 * @author Zhoujiwei
 * @since 2016/4/18 11:49
 */
public interface ActivityService {

    /**
     * 活动列表
     *
     * @param activityType  活动类型（1所有活动/2征集中的活动）
     * @param activityLabel 活动标签（多选用英文,隔开）
     * @param industry      所属行业（多选用英文,隔开）
     * @param chargeType    收费类型：1免费/2收费
     * @param activityLevel 活动级别
     * @param pageNumber    第几页
     * @param size          页码
     *
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
    ReturnResult<Map<String, Object>> getActivityList(String activityType, String activityLabel, String industry, String chargeType,
                                                      String activityLevel, String pageNumber, String size) ;


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
    ActivityInfo getActivityById(Long activityId) ;
}
