package com.kmzyc.promotion.remote.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.OrderProduct;
import com.kmzyc.promotion.app.vobject.ProductPromotionInfo;
import com.kmzyc.promotion.app.vobject.ProductSkuPriceCache;
import com.kmzyc.promotion.app.vobject.ProductStock;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.exception.ServiceException;

public interface PromotionRemoteService {
    /**
     * 根据订单明细获取促销活动信息 订单系统专用接口<br>
     * 
     * @param orderProductList
     * @param date
     * @return
     * @throws Exception
     */
    public ProductPromotionInfo selectPromotionByOrder(List<OrderProduct> orderProductList,
            Date date) throws Exception;

    /**
     * 批量获取促销价格
     */
    public List<ProductSkuPriceCache> getProductSkuPriceBatch(List<ProductSkuPriceCache> list,
            Date date);

    public ProductSkuPriceCache getProductSkuPrice(ProductSkuPriceCache productSkuPriceCache,
            Date date);

    public ProductSkuPriceCache getCurrentTimeProductSkuPrice(
            ProductSkuPriceCache productSkuPriceCache);

    /**
     * 获取活动类型map
     * 
     * @return
     */
    public Map<Integer, String> getPromotionTypeMap();

    /**
     * 更新一个产品的缓存 产品系统调用
     * 
     * @param productSkuId
     */
    public void updateProductPromotionCache(Long productSkuId);

    /**
     * 获取指定时间全场打折活动
     * 
     * @param date
     * @return
     */
    public PromotionInfo getDiscountPromotionInfo(Date date);

    /**
     * 供应商类型发生变化后更新缓存 supplierId：供应商id beforeSuppliersType： 修改之前的供应商类型 afterSuppliersType：修改之后的供应商类型
     */
    public void updateCacheBySupplier(Long supplierId, String beforeSuppliersType,
            String afterSuppliersType);

    /**
     * 更新活动库存
     * 
     * @param stockList 库存发生变化的产品列表
     * @param type 操作类型 ： "0" 为下单 ，"1" 为取消订单时
     * @return add by songmiao
     */
    public boolean updatePromotionStock(List<ProductStock> stockList, String type);

    /**
     * 根据活动ID获取活动信息 参数：活动ID
     * 
     * @param promotionId
     * @return
     * @throws Exception
     */
    public PromotionInfo getPromotionById(Long promotionId) throws Exception;

    /**
     * 根据SkuId查询限购活动 参数：产品skuId
     * 
     * @param skuId
     * @return
     * @throws Exception
     */
    public PromotionInfo getPurchasePromontionBySkuId(Long skuId) throws Exception;

    /**
     * 根据商家类型查询有效活动 参数：1 商家类型,2渠道ID
     * 
     * @param shopSort
     * @return
     * @throws Exception
     */
    public List<PromotionInfo> getPromtionBySupplierType(Long shopSort) throws Exception;

    /**
     * 查询商家有效活动 参数：1渠道ID ,2活动商家ID
     * 
     * @param supplierId
     * @param sellerIds
     * @return
     * @throws Exception
     */
    public List<PromotionInfo> getValidPromotionListBySupplierId(Long supplierId,
            List<Long> sellerIds) throws Exception;

    /**
     * 获取活动规则数据 参数：活动ID
     * 
     * @param promtionId
     * @return
     * @throws Exception
     */
    public List<PromotionRuleData> getPromotionRuleInfoList(Long promtionId) throws Exception;

    /**
     * 判断商品是否在活动中
     * 
     * @param skuId
     * @param info
     * @return
     * @throws Exception
     */
    public boolean inPromotionInfo(Long skuId, PromotionInfo info) throws Exception;

    /**
     * 获取活动列表
     */
    public Page getPromotionInfoListByPage(Page page, PromotionInfo promotion);

    /**
     * 查询存在时间冲突的活动列表
     */
    public List<PromotionInfo> queryMutexPromotionList(PromotionInfo promotion);

    /**
     * 批量删除活动 promtionIds:多个id用逗号分隔 return ： 0 成功 1失败
     */
    public Integer deletePromotions(String promotionIds);

    /**
     * 批量删除活动产品 return ： 0 成功 1失败
     */
    public Integer deletePromotionProducts(PromotionProduct promotionProduct);

    /**
     * 复制活动 return ： 0 成功 1失败
     */
    public Integer copyPromotion(Long promotionId);

    /**
     * 审核或者撤销审核一个活动
     */
    public int updateIssuePromotion(Long promotionId);

    /**
     * 修改更新活动优先级序号return ： 0 成功 1失败
     */
    public Integer updatePromotionPriority(PromotionInfo p);

    /**
     * 撤销活动 return ： 0 成功 1失败
     */
    public Integer updatePromotionEndTime(PromotionInfo p);

    /**
     * 添加活动 return ： 0失败 其他数字为 promotionId
     */
    public Long addPromotionNew(PromotionInfo p, String ruleData, String meetDataType);

    /**
     * 查询互斥活动
     */
    public Map<Long, String> selectExclusionPromotion(Long promotionId);

    /**
     * 
     */
    public boolean checkPromoitonTimeInCoupon(PromotionInfo p);

    /**
     * 修改促销活动 return ： 0 成功 1失败
     */
    public Integer updatePromotion(PromotionInfo p);

    public Page queryPromotionProductList(Page page, PromotionInfo promotion);

    /**
     * 添加活动商品
     */
    public Integer addPromotionProduct(Long promotion, String productSkuIds);

    /**
     * 更新商品销售数据 返回 ：0 成功 1 最小购买数必须小于最大购买数 2 最小购买数必须小于最大购买数 3操作失败
     */
    public Integer updatePromotionProductForXianGou(PromotionProduct p);

    /**
     * 根据促销活动ID更新缓存数据
     * 
     * @param promotionid
     * @throws ServiceException
     */
    public void synPromotionCom(long promotionid) throws ServiceException;

    /**
     * 修改产品状态
     * 
     * @param promotionProductId
     * @return
     * @throws ServiceException
     */
    public Integer updateIssuePromotionProduct(Long promotionProductId) throws ServiceException;

    /**
     * 根据PromotionProduct列表创建特价活动
     * 
     * @param skuList
     * @return 0：成功 其他数字为新创建的活动id
     */
    public Integer createPromotion(List<PromotionProduct> list, PromotionInfo p);
}
