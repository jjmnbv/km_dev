package com.pltfm.app.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ViewProductInfoDAO;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewProductInfoExample;
import com.pltfm.app.vobject.ViewProductSku;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

/**
 * 产品视图DOA实现类
 *
 * @author cjm
 * @since 2013-9-18
 */
@Component(value = "viewProductInfoDAO")
public class ViewProductInfoDAOImpl implements ViewProductInfoDAO {

    /**
     * 数据库操作类
     */
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /**
     * 根据产品条件查询产品数量
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public Integer countByViewPromotion(ViewProductInfo viewProductInfo)
            throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "VIEW_PRODUCT_INFO.abatorgenerated_countByViewProductInfo", viewProductInfo);
        return count;
    }

    /**
     * 分页查询产品视图
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public List queryForPage(ViewProductInfo viewProductInfo)
            throws SQLException {
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_pageByViewProductInfo", viewProductInfo);
    }

    /**
     * 查询视图产品排行
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public List pageRanking()
            throws SQLException {
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_pageRanking");
    }

    /**
     * 分页查询产品视图
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public List queryForPage1(ViewProductInfo viewProductInfo)
            throws SQLException {
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_pageByViewProductInfo1", viewProductInfo);
    }

    /**
     * 根据产品信息主键查询单条产品信息
     *
     * @param productId 产品视图主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public ViewProductInfo selectByPrimaryKey(Integer productId)
            throws SQLException {
        ViewProductInfo key = new ViewProductInfo();
        key.setProductId(productId);
        List list = null;
        list = sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_selectByPrimaryKey", key);
        if (list != null && list.size() > 0) {
            return (ViewProductInfo) list.get(0);
        }
        return null;
    }


    /**
     * 根据产品信息条件查询产品信息
     *
     * @param example 产品信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public List selectByExample(ViewProductInfoExample example)
            throws SQLException {
        List list = sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * 通过查询实体查询产品信息
     *
     * @param example 查询实体
     * @return 产品信息
     * @throws Exception 异常信息 sql select PRODUCT_ID, PRODUCT_SKU_ID, PRODUCT_SKU_CODE,
     *                   CATEGORY_ATTR_ID, CATEGORY_ATTR_NAME, CATEGORY_ATTR_VALUE_ID,
     *                   CATEGORY_ATTR_VALUE, STATUS,WAREHOUSE_ID,WAREHOUSE_NAME,SKU_INTRODUCE from
     *                   VIEW_SKU_ATTR
     */
    public List queryViewProductByExample(ViewProductInfo example) throws Exception {
        List list = sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_queryViewProductByExample", example);
        return list;
    }

    /**
     * 通过查询实体查询产品信息
     *
     * @param example 查询实体
     * @return 产品信息
     * @throws Exception 异常信息
     */
    @Override
    public List<ViewProductInfo> queryViewpProductDraftInfo(ViewProductInfo example)
            throws Exception {
        List list = sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_queryViewpProductDraftInfoByExample", example);
        return list;
    }


    /**
     * 通过查询实体查询产品信息
     *
     * @param example 查询实体
     * @return 产品信息
     * @throws Exception 异常信息
     */
    public List preViewProductByExample(ViewProductInfoExample example) throws Exception {
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_preViewProductByExample", example);
    }

    /**
     * 根据SKU查询产品Id和SKUCODE
     *
     * @param List 产品Id和SKUCODE
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public List queryByProductSku(Integer productSku) throws SQLException {
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_queryByProductSku", productSku);
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table VIEW_PRODUCT_INFO
     * 产品视图查询条件类
     *
     * @abatorgenerated Wed Sep 18 15:15:58 CST 2013
     */
    private static class UpdateByExampleParms extends ViewProductInfoExample {
        private Object record;

        public UpdateByExampleParms(Object record, ViewProductInfoExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }


    @Override
    public List selProByPk(ViewProductInfo viewProductInfo) throws SQLException {
        // TODO Auto-generated method stub
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_selProByPromotionId", viewProductInfo);
    }

    @Override
    public List selProByViewProductInfo(ViewProductInfo viewProductInfo)
            throws SQLException {
        // TODO Auto-generated method stub
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_selProByViewProductInfo", viewProductInfo);
    }

    @Override
    public List getCategory(ViewProductInfo viewProductInfo)
            throws SQLException {
        // TODO Auto-generated method stub
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_getCategory", viewProductInfo);
    }

    @Override
    public List getCategoryProducts(ViewProductInfo viewProductInfo)
            throws SQLException {
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_getCategoryProducts", viewProductInfo);
    }

    /**
     * 根据产品Id或SkuId查询产品
     */
    @Override
    public List selProByIdsOrSkuId(ViewProductInfo viewProductInfo)
            throws SQLException {
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_selProByIdsOrSkuId", viewProductInfo);
    }

    /**
     * 根据产品信息主键或SkuId查询单条产品信息
     *
     * @param productId 产品视图主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public List selectByIdOrSkuId(ViewProductInfo viewProductInfo) throws SQLException {
        List list = sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_selProByIdsAndSkuId", viewProductInfo);
        return list;
    }

    /**
     * 套餐根据产品信息主键或SkuId查询单条产品信息
     *
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public ViewProductInfo selectSkuId(ViewProductInfo viewProductInfo) throws SQLException {
        ViewProductInfo v = (ViewProductInfo) sqlMapClient.queryForObject("VIEW_PRODUCT_INFO.abatorgenerated_selProBySkuId", viewProductInfo);
        return v;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }


    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }


    /**
     * 分页查询产品视图
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public List queryForPagePro(ViewProductInfo viewProductInfo, Page page)
            throws SQLException {
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.recommend_pageByViewProductInfo", viewProductInfo, (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
    }

    /**
     * 根据产品条件查询产品数量
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public Integer countByViewPromotionPro(ViewProductInfo viewProductInfo)
            throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "VIEW_PRODUCT_INFO.recommend_countByViewProductInfo", viewProductInfo);
        return count;
    }

    /**
     * 根据产品条件查询产品数量
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public Integer countByViewPromotion1(ViewProductInfo viewProductInfo)
            throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "VIEW_PRODUCT_INFO.abatorgenerated_countByViewProductInfo1", viewProductInfo);
        return count;
    }

    /**
     * 根据产品条件查询产品数量
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public Integer countByViewPromotionTwo(ViewProductInfo viewProductInfo)
            throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "VIEW_PRODUCT_INFO.abatorgenerated_countByViewProductInfoTwo", viewProductInfo);
        return count;
    }

    /**
     * 分页查询产品视图
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public List queryForPageTwo(ViewProductInfo viewProductInfo)
            throws SQLException {
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_pageByViewProductInfoTwo", viewProductInfo);
    }

    /**
     * 查询活动商品
     */
    @Override
    public List queryByViewPromotionId(List<Integer> promotionIds) throws SQLException {
        return sqlMapClient.queryForList("VIEW_PRODUCT_INFO.abatorgenerated_queryByViewPromotionId", promotionIds);
    }

}
