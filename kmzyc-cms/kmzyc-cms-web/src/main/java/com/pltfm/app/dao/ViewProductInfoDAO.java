package com.pltfm.app.dao;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewProductInfoExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 产品视图DOA接口
 *
 * @author cjm
 * @since 2013-9-18
 */
public interface ViewProductInfoDAO {

    /**
     * 分页查询产品视图
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    List queryForPage(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 套餐根据产品信息主键或SkuId查询单条产品信息
     *
     * @throws SQLException sql异常
     * @return 返回值
     */
    ViewProductInfo selectSkuId(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 根据SKU查询产品Id和SKUCODE
     *
     * @param productSku 产品Id和SKUCODE
     * @throws SQLException 异常
     * @return 返回值
     */
    List queryByProductSku(Integer productSku) throws SQLException;

    /**
     * 查询视图产品排行
     *
     * @throws SQLException 异常
     * @return 返回值
     */
    List pageRanking() throws SQLException;

    /**
     * 根据产品条件查询产品数量
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    Integer countByViewPromotion(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 根据产品信息主键查询单条产品信息
     *
     * @param productId 产品视图主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    ViewProductInfo selectByPrimaryKey(Integer productId) throws SQLException;


    /**
     * 根据产品信息条件查询产品信息
     *
     * @param example 产品信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    List selectByExample(ViewProductInfoExample example) throws SQLException;

    /**
     * 根据产品信息条件查询产品预览视图信息
     *
     * @param example 产品信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    List preViewProductByExample(ViewProductInfoExample example) throws Exception;

    /**
     * 通过查询实体查询产品信息
     *
     * @param example 查询实体
     * @return 产品信息
     * @throws Exception 异常信息
     */
    List queryViewProductByExample(ViewProductInfo example) throws Exception;


    /**
     * 通过查询实体查询产品信息
     *
     * @param example 查询实体
     * @return 产品信息
     * @throws Exception 异常信息
     */
    List<ViewProductInfo> queryViewpProductDraftInfo(ViewProductInfo example) throws Exception;


    /**
     * 更具活动ID查询该活动所有的商品
     */
    public List selProByPk(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 根据ViewProductInfo对象查询活动的商品
     */
    public List selProByViewProductInfo(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 获取活动产品分类
     */
    public List getCategory(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 获取活动对应类目商品
     */
    public List getCategoryProducts(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 根据产品Id或SkuId查询产品
     */
    public List selProByIdsOrSkuId(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 根据产品信息主键或SkuId查询单条产品信息
     *
     * @param productId 产品视图主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    public List selectByIdOrSkuId(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 分页查询产品视图
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public List queryForPagePro(ViewProductInfo viewProductInfo, Page page) throws SQLException;

    /**
     * 根据产品条件查询产品数量
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    Integer countByViewPromotionPro(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 分页查询产品视图
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    List queryForPage1(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 根据产品条件查询产品数量
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    Integer countByViewPromotion1(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 根据产品条件查询产品数量
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */

    public Integer countByViewPromotionTwo(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 分页查询产品视图
     *
     * @param viewProductInfo 产品视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public List queryForPageTwo(ViewProductInfo viewProductInfo) throws SQLException;

    /**
     * 查询活动商品
     */
    public List queryByViewPromotionId(List<Integer> promotionIds) throws SQLException;
}
