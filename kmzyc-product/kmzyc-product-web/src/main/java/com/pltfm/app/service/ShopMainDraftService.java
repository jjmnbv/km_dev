package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.ShopMainDraft;
import com.kmzyc.commons.page.Page;

public interface ShopMainDraftService {

    /**
     * 显示供应商店铺列表
     *
     * @return
     */
    List<ShopMainDraft> supplierShopDraftListShow(Page page, ShopMainDraft searchShopMainDraft) throws ServiceException;

    /**
     * 根据店铺id查询店铺信息
     *
     * @param shopId
     * @return
     * @throws ServiceException
     */
    ShopMainDraft supplierShopDraftView(Long shopId) throws ServiceException;

    /**
     * 更新店铺状态
     *
     * @param list
     * @return
     * @throws ServiceException
     */
    int updateShopMainDraftBatch(List<ShopMainDraft> list, String auditStatus, List<Long> shopIdList) throws ServiceException;

}