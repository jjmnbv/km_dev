package com.pltfm.app.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ViewPromotion;

import java.util.Map;

/**
 * 活动信息业务逻辑层接口
 *
 * @author cjm
 * @since 2013-9-3
 */
public interface ViewPromotionService {
    /**
     * 分页查询活动信息
     *
     * @param viewPromotion 活动信息实体
     * @param page          分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryForPage(ViewPromotion viewPromotion, Page page) throws Exception;

    /**
     * 分页查询活动信息(for添加活动商品)
     *
     * @param viewPromotion 活动信息实体
     * @param page          分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryForPagePro(ViewPromotion viewPromotion, Page page) throws Exception;

    /**
     * 根据活动信息主键查询单条活动信息
     *
     * @param viewPromotionId 活动信息主键
     * @throws Exception sql异常
     * @return 返回值
     */
    ViewPromotion selectByPrimaryKey(Integer viewPromotionId) throws Exception;

    /**
     * 获取活动类目
     */
    public Map getPromotionCategory() throws Exception;
}
