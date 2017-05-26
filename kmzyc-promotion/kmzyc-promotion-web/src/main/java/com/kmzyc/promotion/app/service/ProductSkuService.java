package com.kmzyc.promotion.app.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.CarryStockOutDetailVO;
import com.kmzyc.promotion.app.vobject.ProductAndSku;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.exception.ServiceException;

/**
 * 产品SKU业务逻辑接口
 * 
 * @author humy
 * @since 2013-7-9
 */
@SuppressWarnings("unchecked")
public interface ProductSkuService {

    /**
     * 根据SKU，查找商品信息
     */
    public abstract Map<Long, ProductAndSku> findProduct(String productSkuCode);

    ProductSku findProductSkuByCode(String skuCode) throws Exception;

    /**
     * 批量更新价格
     * 
     * @param skuList
     * @throws Exception
     */
    void updatePrice(List<ProductSku> skuList) throws Exception;

    /**
     * 更新缓存中的价格
     * 
     * @param skuIdList
     */
    void updateCachePrice(List<Long> skuIdList);

    /**
     * 查询产品SKU
     * 
     * @param productSku 产品SKU基本信息
     * @return List<ProductSku>
     * @throws Exception
     */
    public List<ProductSku> queryProductSkuList(ProductSku productSku) throws Exception;

    /**
     * 查询产品SKU列表
     * 
     * @param page 分页对象
     * @param productSku 产品SKU基本信息
     * @return Page
     * @throws Exception
     */
    public Page queryProductSkuList(Page pageParam, ProductSku productSku) throws Exception;

    /**
     * 根据主键查询产品SKU
     * 
     * @param productSkuId 产品SKU主键
     * @return ProductSku
     * @throws Exception
     */
    public ProductSku queryProductSkuList(Long productSkuId) throws Exception;

    /**
     * 根据一级类编号找到最大的SkuCode
     * 
     * @param cateCode
     * @return
     * @throws Exception
     */
    public String findMaxSkuCodeByCateCode(String cateCode) throws Exception;

    public void updateSkuByProductSkuIdBatch(List list) throws Exception;

    /**
     * @author ljh
     * @param skuCode 根据一个产品的skuCode 查询出 同一品牌 同一类别下的skuCode 集合出来
     * @return
     * @throws Exception
     */

    public List<String> selectSkuCodeListByCategoryBrandBySkucode(ProductSku sku) throws Exception;

    /**
     * @author ljh
     * @param sku 根据同一个产品的skuCode 查询出同一类别下的skuCode 出来
     * @return
     * @throws Exception
     */

    public List<String> selectSkuCodeListByCategory(ProductSku sku) throws Exception;

    /**
     * @author ljh
     * @param sku 根据同一产品的skuCode 查询出同一价位的 skuCode 出来
     * @return
     * @throws Exception
     */

    public List<String> selectSamePriceStatus(ProductSku sku) throws Exception;

    /**
     * 批量更新重量
     * 
     * @param skuList
     * @throws Exception
     */
    public void updateUnitWeight(List<ProductSku> skuList) throws Exception;

    /**
     * 根据skuCode 查询出productSku 信息
     * 
     * @param sku
     * @return
     * @throws Exception
     */
    public ProductSku selectProductSkuBySkucode(ProductSku sku) throws Exception;

    /**
     * 根据传递进来的包含categoryId 的list 查询出所有的其类别下所有的skuCode集合
     * 
     * @param categoryIdList
     * @return
     * @throws Exception
     */
    public List<String> selectSkuCodeListByCategoryIdList(List<Long> categoryIdList)
            throws Exception;

    /**
     * 根据传递进来的包含categoryId 的list 查询出所有的其类别下所有的skuId集合
     * 
     * @param categoryIdList
     * @return
     * @throws Exception
     */

    public List<Long> selectSkuIdListByCategoryIdList(List<Long> categoryIdList) throws Exception;

    /**
     * 根据产品ID集合，查询其所有SKUID集合
     * 
     * @param productIdList
     * @return
     * @throws Exception
     */
    public List<Long> selectSkuIdsByProductIdList(List<Long> productIdList) throws Exception;

    /**
     * 查询所有的skuCode
     * 
     * @return
     * @throws Exception
     */
    public List<String> selectAllSkuCodeList() throws Exception;

    /**
     * 根据skuId 查询对应的skuCode map 出来
     * 
     * @param skuIdList
     * @return
     * @throws Exception
     */

    public Map<Long, ProductSku> getSkuIdAndCodeMap(List<Long> skuIdList) throws Exception;

    /**
     * 根据SKUCODE的集合，查找所有的ProductSku信息
     * 
     * @param productSkuCodeList
     * @return create by luoyi 2014/04/06
     */
    public List<ProductAndSku> findProductSkuListBySkuCodes(
            List<CarryStockOutDetailVO> productSkuCodeList);

    /**
     * 根据SKU_CODE集合，检索所有的ProductSku信息
     * 
     * @param productSkuCodeList
     * @return
     * @throws Exception
     */
    public List<ProductSku> findProductSkuBySkuCodes(List<String> productSkuCodeList)
            throws Exception;

    public ProductSku findProductSkuBySkuId(Long skuId) throws Exception;

    /**
     * 批量查询产品价格 多用于
     * 
     * @param params
     * @return
     * @throws SQLException
     */
    public Map<Long, BigDecimal> queryBatchPrice(List<Long> skuIds) throws ServiceException;

    /**
     * 查询预售产品SKU列表，审核通过的商品，上下架状态不限，库存不限 ，产品状态4:下架、5:系统下架、3:上架 、 -2违规下架 ;
     * 
     * 每个商品只能同时参加一个预售活动，上一个活动全部结束之后（预售状态为“已结束”），才可参加新的预售活动；
     * 
     * 参加了商家活动中心报名（促销推广、图文推广、渠道推广）的商品（报名成功的产品并活动结束时间大于等于当前时间）,不可参与预售活动
     * 
     * @param page 分页对象
     * @param productSku 产品SKU基本信息
     * @return Page
     * @throws Exception
     */
    public Page queryPresellProductSkuList(Page pageParam, ProductSku productSku) throws Exception;
}
