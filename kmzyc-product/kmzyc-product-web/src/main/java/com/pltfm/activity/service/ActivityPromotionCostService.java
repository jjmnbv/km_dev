package com.pltfm.activity.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;
import com.kmzyc.commons.page.Page;


public interface ActivityPromotionCostService {

    /**
     * 分页查找活动推广费用（渠道推广）
     * 
     * @param page
     * @param activitySupplierEntry
     * @throws ServiceException
     */
    void queryActivityPromotionCost(Page page, ActivitySupplierEntry activitySupplierEntry)
            throws ServiceException;

    /**
     * 分页查找活动推广销售明细（渠道推广）
     * 
     * @param page
     * @param activitySku
     * @throws ServiceException
     */
    void queryActivitySupplierCostDetail(Page page, ActivitySku activitySku)
            throws ServiceException;
}
