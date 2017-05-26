package com.pltfm.app.dao;

import com.pltfm.app.vobject.ViewPromotion;

import java.sql.SQLException;
import java.util.List;

/**
 * 活动信息DAO接口
 *
 * @author cjm
 * @since 2013-9-3
 */
public interface ViewPromotionDAO {
    /**
     * 分页查询活动信息视图
     *
     * @param viewPromotion 活动信息视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public List queryForPage(ViewPromotion viewPromotion) throws SQLException;

    /**
     * 分页查询活动信息视图 (for添加活动商品)
     *
     * @param viewPromotion 活动信息视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public List queryForPagePro(ViewPromotion viewPromotion) throws SQLException;

    /**
     * 根据活动条件查询活动信息总数量
     *
     * @param viewPromotion 活动信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public Integer countByViewPromotion(ViewPromotion viewPromotion) throws SQLException;

    /**
     * 根据活动条件查询活动信息总数量(for添加活动商品)
     *
     * @param viewPromotion 活动信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public Integer countByViewPromotionPro(ViewPromotion viewPromotion) throws SQLException;


    //活动
    public List queryPromotionData(List dataIdsList, List dataIdsSort) throws SQLException;

    /**
     * 根据活动信息主键查询单条活动信息
     *
     * @param viewPromotionId 活动信息主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    ViewPromotion selectByPrimaryKey(Integer viewPromotionId) throws SQLException;
}