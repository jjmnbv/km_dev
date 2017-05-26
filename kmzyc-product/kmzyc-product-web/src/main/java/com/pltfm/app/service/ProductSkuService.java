package com.pltfm.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.CarryStockOutDetailVO;
import com.pltfm.app.vobject.CouponProduct;
import com.pltfm.app.vobject.ProductAndSku;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductStock;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

/**
 * 产品SKU业务逻辑接口
 *
 * @author humy
 * @since 2013-7-9
 */
public interface ProductSkuService {

    /**
     * 添加产品SKU
     *
     * @param productSku 产品SKU基本信息
     * @return
     * @throws ServiceException
     */
    void addProductSku(ProductSku productSku) throws ServiceException;

    /**
     * 更新数据
     *
     * @param productSku
     * @return
     * @throws ServiceException
     */
    int updateProductSku(ProductSku productSku) throws ServiceException;

    /**
     * 根据SKU，查找商品信息
     */
    Map<Long, ProductAndSku> findProduct(String productSkuCode);

    ProductSku findProductSkuByCode(String skuCode) throws ServiceException;

    ResultMessage checkSkuRelation(Long skuId) throws ServiceException;

    /**
     * 更新缓存中的价格
     *
     * @param skuIdList
     */
    void updateCachePrice(List<Long> skuIdList) throws ServiceException;

    /**
     * 查询产品SKU
     *
     * @param productSku 产品SKU基本信息
     * @return List<ProductSku>
     * @throws ServiceException
     */
    List<ProductSku> queryProductSkuList(ProductSku productSku) throws ServiceException;

    /**
     * 删除产品SKU
     *
     * @param productSkuId 产品SKU基本信息
     * @return
     * @throws ServiceException
     */
    void deleteProductSku(Long productSkuId) throws ServiceException;

    /**
     * 根据主键查询产品SKU
     *
     * @param productSkuId 产品SKU主键
     * @return ProductSku
     * @throws ServiceException
     */
    ProductSku queryProductSkuList(Long productSkuId) throws ServiceException;

    /**
     * 根据一级类编号找到最大的SkuCode
     *
     * @param cateCode
     * @return
     * @throws ServiceException
     */
    String findMaxSkuCodeByCateCode(String cateCode) throws ServiceException;

    /**
     * 批量更新重量
     *
     * @param skuList
     * @throws ServiceException
     */
    void updateUnitWeight(List<ProductSku> skuList) throws ServiceException;

    /**
     * 根据传递进来的包含categoryId 的list 查询出所有的其类别下所有的skuId集合
     *
     * @param categoryIdList
     * @return
     * @throws ServiceException
     */
    List<Long> selectSkuIdListByCategoryIdList(List<Long> categoryIdList) throws ServiceException;

    /**
     * 根据产品ID集合，查询其所有SKUID集合
     *
     * @param productIdList
     * @return
     * @throws ServiceException
     */
    List<Long> selectSkuIdsByProductIdList(List<Long> productIdList) throws ServiceException;

    /**
     * 查询所有的skuCode
     *
     * @return
     * @throws ServiceException
     */
    List<String> selectAllSkuCodeList() throws ServiceException;

    /**
     * 根据skuId 查询对应的skuCode map 出来
     *
     * @param skuIdList
     * @return
     * @throws ServiceException
     */
    Map<Long, ProductSku> getSkuIdAndCodeMap(List<Long> skuIdList) throws ServiceException;

    /**
     * 根据SKU_CODE集合，检索所有的ProductSku信息
     *
     * @param productSkuCodeList
     * @return
     * @throws ServiceException
     */
    List<ProductSku> findProductSkuBySkuCodes(List<String> productSkuCodeList) throws ServiceException;

    ProductSku findProductSkuBySkuId(Long skuId) throws ServiceException;

    /**
     * 根据skuIds查询sku
     *
     * @param productSkuIds
     * @return
     * @throws ServiceException
     */
    List<ProductSku> findBySkuIds(List<Long> productSkuIds) throws ServiceException;

    /**
     * 批量更新库存
     *
     * @param stockList
     * @throws ServiceException
     */
    void updateStock(List<ProductStock> stockList) throws ServiceException;

    /**
     * 根据条形码查找商品SKU
     *
     * @param map
     * @return
     * @throws ServiceException
     */
    List<ProductSku> findSameSkuBarCodeProductSku(Map<String, Object> map) throws ServiceException;

    /**
     * 根据商品skuid获取商品sku详细信息
     *
     * @param productSkuIds 商品skuid集合
     * @return
     * @throws ServiceException
     */
    List<Map<String, String>> getProductSkuDetailsMap(List<Long> productSkuIds) throws ServiceException;

    /**
     * 更新所有商品sku详细信息的缓存
     *
     * @return
     * @throws ServiceException
     */
    void updateAllProductSkuCache() throws ServiceException;

    /**
     * 更新sku详细的缓存
     *
     * @param productSkuIds 商品skuid集合
     * @throws ServiceException
     */
    void updateProductSkuCache(List<Long> productSkuIds) throws ServiceException;

    /**
     * 根据SKUCODE查询优惠券产品列表
     *
     * @param skuCode
     * @return
     * @throws ServiceException
     */
    List<CouponProduct> selectCouponProductBySkuId(String skuCode) throws ServiceException;

    /**
     * 获取代销商的PV值比例
     *
     * @return
     * @throws ServiceException
     */
    BigDecimal getSupportPvProportion() throws ServiceException;

    /**
     * 获取代销商家当前SKU的最大pv值
     *
     * @param pvProportion 代销商家pv比例
     * @param price        销售价
     * @param costPrice    市场价
     * @return
     */
    Double getSkuMaxPvValue(BigDecimal pvProportion, Double price, Double costPrice) throws ServiceException;

    /**
     * 获取代销商家当前SKU的最大pv值
     *
     * @param price        销售价
     * @param costPrice    市场价
     * @return
     */
    Double getSkuMaxPvValue(Double price, Double costPrice) throws ServiceException;
}
