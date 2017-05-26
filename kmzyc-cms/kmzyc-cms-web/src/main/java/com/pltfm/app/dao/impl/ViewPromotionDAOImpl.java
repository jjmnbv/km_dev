package com.pltfm.app.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ViewPromotionDAO;
import com.pltfm.app.vobject.ViewPromotion;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

/**
 * 活动信息DAO接口实现类
 *
 * @author cjm
 * @since 2013-9-3
 */
@Component(value = "viewPromotionDAO")
public class ViewPromotionDAOImpl implements ViewPromotionDAO {
    /**
     * 数据库操作对象
     */
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /**
     * 根据活动条件查询活动信息总数量
     *
     * @param viewPromotion 活动信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public Integer countByViewPromotion(ViewPromotion viewPromotion)
            throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "VIEW_PROMOTION.abatorgenerated_countByViewPromotion", viewPromotion);
        return count;
    }

    /**
     * 根据活动条件查询活动信息总数量(for添加活动商品)
     *
     * @param viewPromotion 活动信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public Integer countByViewPromotionPro(ViewPromotion viewPromotion)
            throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "VIEW_PROMOTION.abatorgenerated_countByViewPromotionPro", viewPromotion);
        return count;
    }

    /**
     * 分页查询活动信息视图
     *
     * @param viewPromotion 活动信息视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public List queryForPage(ViewPromotion viewPromotion) throws SQLException {
        return sqlMapClient.queryForList("VIEW_PROMOTION.abatorgenerated_pageByViewPromotion", viewPromotion);
    }

    /**
     * 分页查询活动信息视图 (for添加活动商品)
     *
     * @param viewPromotion 活动信息视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public List queryForPagePro(ViewPromotion viewPromotion) throws SQLException {
        return sqlMapClient.queryForList("VIEW_PROMOTION.abatorgenerated_pageByViewPromotionPro", viewPromotion);
    }

    /**
     * 根据活动信息主键查询单条活动信息
     *
     * @param viewPromotionId 活动信息主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public ViewPromotion selectByPrimaryKey(Integer viewPromotionId)
            throws SQLException {
        ViewPromotion key = new ViewPromotion();
        key.setPromotionId(viewPromotionId);
        ViewPromotion record = (ViewPromotion) sqlMapClient.queryForObject("VIEW_PROMOTION.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }


    //活动
    public List queryPromotionData(List dataIdsList, List dataIdsSort) throws SQLException {
        ViewPromotion viewPromotion = new ViewPromotion();
        viewPromotion.setPromotionIds(dataIdsList);
        viewPromotion.setPromotionIdsSort(dataIdsSort);
        return sqlMapClient.queryForList("VIEW_PROMOTION.abatorgenerated_queryPromotionData", viewPromotion);
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }


}
