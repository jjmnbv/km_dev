package com.kmzyc.supplier.service;

import java.util.Map;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.ShopMainDraft;

public interface ShopMainDraftService {

    /**
     * 分页 查找数据
     *
     * @param shopMainDraft
     * @param page
     */
    Pagination searchPage(ShopMainDraft shopMainDraft, Pagination page) throws ServiceException;

    /**
     * 保存店铺草稿
     *
     * @param shopMainDraft
     * @return
     * @throws ServiceException
     */
    Long insertShopMainDraft(ShopMainDraft shopMainDraft) throws ServiceException;

    /**
     * 删除店铺
     *
     * @param shopId
     * @return
     * @throws ServiceException
     */
    Integer deleteByShopId(Long shopId) throws ServiceException;

    /**
     * 根据shopId查询shopMainDraft
     *
     * @param shopId
     * @return
     * @throws ServiceException
     */
    ShopMainDraft findByShopId(Long shopId) throws ServiceException;

    /**
     * 根据主键更新
     *
     * @param shopMainDraft
     * @return
     * @throws ServiceException
     */
    int updateById(ShopMainDraft shopMainDraft) throws ServiceException;

    /**
     * 查找店铺
     *
     * @param supplierId
     * @return
     * @throws ServiceException
     */
    int findShopMainDraft(Long supplierId) throws ServiceException;

    /**
     * 查询是否重复名
     *
     * @param conditionMap
     * @return
     * @throws ServiceException
     */
    Integer selectRepeatName(Map conditionMap) throws ServiceException;

    /**
     * 查询是否重复名（直接修改店铺正式数据时）
     *
     * @param conditionMap
     * @return
     * @throws ServiceException
     */
    Integer selectRepeatNameForUpdateShopMain(Map conditionMap) throws ServiceException;

    /**
     * 根据供应商ID和站点获取店铺草稿记录
     *
     * @param supplierId
     * @return
     * @throws ServiceException
     */
    ShopMainDraft findShopMainDraftByIdWithoutBLOBs(Long supplierId) throws ServiceException;

    /**
     * 直接在店铺正式信息列表上获取数据插入并提审店铺草稿数据
     *
     * @param shopMainDraft
     * @return
     * @throws ServiceException
     */
    Long insertAndUpdateShopMainDraft(ShopMainDraft shopMainDraft) throws ServiceException;
}
