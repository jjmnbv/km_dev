package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewProductInfoExample;
import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.app.vobject.ViewSkuAttr;

/**
 * 产品视图业务逻辑接口
 *
 * @author cjm
 * @since 2013-9-18
 */
public interface ViewProductInfoService {
    /**
     * 分页查询产品
     *
     * @param viewProductInfo 产品实体
     * @param page            分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryForPage(ViewProductInfo viewProductInfo, Page page) throws Exception;

    /**
     * 套餐根据产品主键查询单条产品
     *
     * @throws Exception sql异常
     * @return 返回值
     */
    ViewProductInfo selectSkuId(ViewProductInfo viewProductInfo) throws Exception;

    /**
     * 查询新产品排行
     *
     * @throws Exception sql异常
     * @return 产品集合
     */
    List<ViewProductInfo> pageRanking() throws Exception;

    /**
     * 根据产品主键集合查询产品信息
     *
     * @param dataIds 产品主键集合
     * @throws Exception sql异常
     * @return 返回值
     */
    public List selectByDataIds(List dataIds, List dataSkuIds, List productsSkuIdsSort) throws Exception;

    /**
     * 根据产品主键集合与站点id查询产品信息
     *
     * @param dataIds 产品主键集合
     * @throws Exception sql异常
     * @return 返回值
     */
    public List selectByDataIdsAndSiteId(List dataIds/*,int siteId*/) throws Exception;

    /**
     * 根据产品主键查询单条产品
     *
     * @param productId 产品主键
     * @throws Exception sql异常
     * @return 返回值
     */
    public ViewProductInfo selectByPrimaryKey(Integer productId) throws Exception;


    /**
     * 根据产品信息条件查询产品信息
     *
     * @param example 产品信息条件
     * @throws Exception sql异常
     * @return 返回值
     */
    List selectByExample(ViewProductInfoExample example) throws Exception;

    /**
     * 根据产品信息条件查询产品信息
     *
     * @param shopCode   供应商ID //     * @param channel	站点下的渠道
     * @param startIndex 开始索引值
     * @param endIndex   结束索引值
     * @throws Exception sql异常
     * @return 产品集合
     */
    List<ViewProductInfo> selectByShopCode(String shopCode,/*String channel,*/Integer startIndex, Integer endIndex) throws Exception;


    /**
     * 产品详细页面预览接口
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
     * 通过产品主键查询产品sku信息
     */
    List<CategoryAttr> queryProductSkuInfo(Integer productId) throws Exception;


    /**
     * 通过产品主键产品每组sku的图片信息
     */
    List<ProductSku> queryProductSkuImage(Integer productId) throws Exception;


    /**
     * 通过产品草稿主键查询产品sku信息
     */
    List<CategoryAttr> queryProductDraftSkuInfo(Integer productId) throws Exception;

    /**
     * 通过产品草稿主键产品每组sku的图片信息
     */
    List<ProductSku> queryProductDraftSkuImage(Integer productId) throws Exception;

    /**
     * 根据活动ID查询该活动商品
     */
    List selProByPk(ViewProductInfo viewProductInfo) throws Exception;

    /**
     * 根据ViewProductInfo对象查询活动的商品
     */
    List selProByViewProductInfo(ViewProductInfo viewProductInfo, ViewPromotion promotion) throws Exception;

    /**
     * 通过产品sku主键查询sku信息
     *
     * @param productSkuId 产品sku主键
     * @throws Exception 异常信息
     */
    List<ViewSkuAttr> findBySkuId(Long productSkuId) throws Exception;

    /**
     * 通过产品草稿sku主键查询sku信息
     *
     * @param productSkuId 产品sku主键
     * @throws Exception 异常信息
     */
    List<ViewSkuAttr> findBySkuDraftId(Long productSkuId) throws Exception;


    /**
     * 获取活动产品类别
     */
    List getCategory(ViewProductInfo viewProductInfo) throws Exception;

    /**
     * 跟据产品skuId查询产品优惠套餐信息
     */
    List queryProductPreferInfo(Long skuId) throws Exception;

    /**
     * 根据产品skuId查询产品人气组合信息
     */
    List queryProductPackage(Long skuId) throws Exception;

    /**
     * 根据产品主键或SkuId查询单条产品
     *
     * @param productId 产品主键
     * @throws Exception sql异常
     * @return 返回值
     */

    ViewProductInfo selectByIdOrSkuId(ViewProductInfo viewProductInfo) throws Exception;

    /**
     * 分页查询产品和窗口数据
     *
     * @param viewProductInfo 产品实体
     * @param page            分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    public Page queryForPagePro(ViewProductInfo viewProductInfo, Page page) throws Exception;

    /**
     * 分页查询产品
     *
     * @param viewProductInfo 产品实体
     * @param page            分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryForPage1(ViewProductInfo viewProductInfo, Page page) throws Exception;

    /**
     * 查询活动商品
     */
    public List queryByViewPromotionId(List<Integer> promotionIds) throws ServiceException;
}
