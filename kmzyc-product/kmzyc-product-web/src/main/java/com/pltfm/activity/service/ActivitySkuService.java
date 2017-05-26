package com.pltfm.activity.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;
import com.kmzyc.commons.page.Page;

public interface ActivitySkuService {

    /**
     * 活动商品列表详情（暂时不需要分页，查全部）
     *
     * @param page
     * @param supplierEntryId
     * @throws ServiceException
     */
    void activityProductList(Page page, Long supplierEntryId) throws ServiceException;

    /**
     * 分页查询活动报名商品列表
     *
     * @param page
     * @param activitySku
     * @throws ServiceException
     */
    void activityEntryProductList(Page page, ActivitySku activitySku) throws ServiceException;

    /**
     * 导出活动商家报名产品列表
     *
     * @param activitySku
     * @throws ServiceException
     */
    void exportActivitySupplierProductList(ActivitySku activitySku) throws ServiceException;

    /**
     * 修改渠道报名审核通过产品在促销系统生成促销活动（activity_sku表促销id为返回活动促销id）
     *
     * @param promotionId     促销活动id
     * @param supplierEntryId 报名id
     * @return
     * @throws SQLException
     */
    int batchUpdateActivitySkuByEntryId(Long promotionId, Long supplierEntryId) throws ServiceException;

    /**
     * 查询渠道报名产品是否在其他活动中审核通过或进行中
     *
     * @param activitySkuList
     * @param activitySupplierEntry
     * @return
     * @throws ServiceException
     */
    List<String> getSkuInUnfinishedActivity(List<ActivitySku> activitySkuList,
                                            ActivitySupplierEntry activitySupplierEntry)
            throws ServiceException;
}
