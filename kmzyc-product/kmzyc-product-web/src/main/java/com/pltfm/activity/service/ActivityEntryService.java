package com.pltfm.activity.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;

public interface ActivityEntryService {

    /**
     * 分页 查找活动报名商家数据
     * 
     * @param page
     * @param activitySupplierEntry
     * @throws ServiceException
     */
    void searchPage(Page page, ActivitySupplierEntry activitySupplierEntry) throws ServiceException;

    /**
     * 查询活动报名商家详情
     * 
     * @param activitySupplierEntry
     * @throws ServiceException
     */
    ActivitySupplierEntry activityEntryDetail(ActivitySupplierEntry activitySupplierEntry)
            throws ServiceException;
    /**
     * 查询促销详情
     * @param promotionId
     * @throws ServiceException
     */
    PromotionInfo selectPromotion(Long promotionId) throws ServiceException;

    /**
     * 审核不通过并标记不通过商品
     * 
     * @param activitySupplierEntry
     * @param activitySkuIds
     * @throws ServiceException
     */
    String activityAuditEntryNoPass(ActivitySupplierEntry activitySupplierEntry,
            String[] activitySkuIds) throws ServiceException;

    /**
     * 审核通过并标创建促销信息
     * 
     * @param activitySupplierEntry activitySkuList
     * @throws ServiceException
     */
    String activityAuditEntryPass(ActivitySupplierEntry activitySupplierEntry,
            List<ActivitySku> activitySkuList) throws ServiceException;

    /**
     * 远程调用促销系统创建特价活动
     * 
     * @param activitySkuList
     * @param activitySupplierEntry
     * @return 其他数字成功:成功 1:失败
     * @throws ServiceException
     */
    Integer createPromotion(List<ActivitySku> activitySkuList,
            ActivitySupplierEntry activitySupplierEntry) throws ServiceException;

    /**
     * 审核通过
     * 
     * @param activitySupplierEntry
     * @throws ServiceException
     */
    void activityPassUpdateAuditEntryById(ActivitySupplierEntry activitySupplierEntry)
            throws ServiceException;

    /**
     * 审核校验
     * 
     * @param activitySupplierEntry
     * @throws ServiceException
     */
    List verifyStatus(ActivitySupplierEntry activitySupplierEntry) throws ServiceException;

    /**
     * 分页 查找活动报名商家数据
     * 
     * @param page
     * @param activitySupplierEntry
     * @throws ServiceException
     */
    void queryInviteSupplierEntry(Page page, ActivitySupplierEntry activitySupplierEntry)
            throws ServiceException;

    /**
     * 保存邀请商家
     * 
     * @param activitySupplierEntryList
     * @throws ServiceException
     */
    void saveInviteSuppliers(List<ActivitySupplierEntry> activitySupplierEntryList)
            throws ServiceException;
}
