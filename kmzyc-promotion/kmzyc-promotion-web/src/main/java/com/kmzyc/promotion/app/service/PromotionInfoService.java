package com.kmzyc.promotion.app.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.BaseProduct;
import com.kmzyc.promotion.app.vobject.Message;
import com.kmzyc.promotion.app.vobject.ProductAndSku;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.ProductSkuPrice;
import com.kmzyc.promotion.app.vobject.ProductStock;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.exception.ServiceException;

/**
 * 促销活动远程调用专用服务 1、提供http形式请求商品促销活动列表 2、提供客户系统remote远程调用获取退货获取订单的促销活动（不包含订单） 3、MQ获取商品促销活动价格
 * 
 * @author hwf
 * 
 */
public interface PromotionInfoService {

    /**
     * 获取指定时间指定渠道所有发布的促销活动
     * 
     * @param date为空 ，取所有时间
     * @return
     */
    public List<PromotionInfo> getAllPromotionListByTime(Date date) throws ServiceException;

    /**
     * 获取结束时间大于指定时间指定渠道所有发布的促销活动，用于创建缓存
     * 
     * @param date为空 ，取所有时间
     * @return
     */
    public List<PromotionInfo> getExpiryPromotionListByTime(Date date) throws ServiceException;

    /**
     * 根据活动ID查询活动
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    public PromotionInfo getPromotionById(Long id) throws ServiceException;

    /**
     * 从指定列表中获取该商品参加的活动
     * 
     * @param sku
     * @param infoList
     * @return
     */
    public List<PromotionInfo> getAllPromotionInfoByProductSku(ProductAndSku sku,
            List<PromotionInfo> infoList) throws ServiceException;

    /**
     * 获取指定时间的活动和价格信息
     * 
     * @param productSkuPrice 需要设置初始价格
     * @param middDate 指定时间
     * @param promotionInfoList 商品可以参与的活动
     * @return
     */
    public ProductSkuPrice getProductSkuPricePromotionInfoByDB(ProductSkuPrice productSkuPrice,
            Date middDate, List<PromotionInfo> promotionInfoList) throws ServiceException;

    /**
     * 获取商品在指定时间内所有的活动列表
     * 
     * @param sku
     * @param date
     * @return
     */
    public List<PromotionInfo> getAllPromotionInfoByProductSku(ProductAndSku sku, Date date)
            throws ServiceException;

    /**
     * 设置限购
     * 
     * @param promotionId
     * @param productSkuId
     * @param productSkuPrice
     * @return
     * @throws ServiceException
     */
    public boolean setRestriction(Long promotionId, Long productSkuId,
            ProductSkuPrice productSkuPrice) throws ServiceException;

    /**
     * 设置活动规则数据
     * 
     * @param info
     * @throws ServiceException
     */
    public void initRuleDatas(PromotionInfo info) throws ServiceException;

    /**
     * 根据供应商id获取活动列表
     * 
     * @param supplierId
     * @return
     */
    public List<PromotionInfo> getAllPromotionInfoBySupplierId(Long supplierId)
            throws ServiceException;

    /**
     * 根据商家类别获取活动列表
     * 
     * @param supplierId
     * @return
     */
    public List<PromotionInfo> getAllPromotionInfoByType(String string) throws ServiceException;

    /**
     * 判断商品是否参加活动
     * 
     * @param sku
     * @param info
     * @return
     */
    public boolean inPromotionInfoByPAS(ProductAndSku pas, PromotionInfo info)
            throws ServiceException;

    /**
     * 判断商品（已有特价、打折活动）是否参加活动
     * 
     * @param bp
     * @param pid
     * @return
     * @throws ServiceException
     */
    public boolean inPromotionInfo(BaseProduct bp, Long pid) throws ServiceException;

    /**
     * 判断商品是否参加活动并设置规则
     * 
     * @param bp
     * @param info
     * @return
     * @throws ServiceException
     */
    public boolean inPromotionInfoAndInitRule(BaseProduct bp, PromotionInfo info)
            throws ServiceException;

    /**
     * 更新
     * 
     * @param p
     */
    public void updatePromotion(PromotionInfo p) throws ServiceException;

    /**
     * 设置更新索引状态为已更新
     * 
     * @param p
     * @return
     */
    public boolean updateIsSyncIndex(Long promotionId) throws ServiceException;

    /**
     * 设置更新索引状态为未更新
     * 
     * @param p
     * @return
     */
    public boolean updateIsNotSyncIndex(Long promotionId) throws ServiceException;

    /**
     * 查询存在时间冲突的活动列表
     * 
     * @param p
     * @return
     */
    public List<PromotionInfo> queryMutexPromotionList(PromotionInfo p) throws ServiceException;

    /**
     * 分页查询
     * 
     * @param page
     * @param promotion
     * @return
     */
    public Page queryPromotionList(Page page, PromotionInfo promotion) throws ServiceException;

    /**
     * 删除活动
     * 
     * @param Message 结果对象
     * @return
     * @throws Exception
     */
    public Message deletePromotion(PromotionInfo promotion) throws ServiceException;

    /**
     * 检验优先级是否重复
     * 
     * @param p
     * @return
     */
    public boolean checkPromotionPriorityIsVaild(PromotionInfo p) throws ServiceException;

    /**
     * 复制一条新的活动数据
     * 
     * @param promotionId
     * @throws ServiceException
     */
    public void copyPromotion(Long promotionId) throws ServiceException;

    /**
     * 更新撤销活动 0：操作成功 1：操作失败，活动已上线，无法进行该操作，如果活动有问题，你可以撤销活动！ 2：操作失败，活动存在特价价格小于等于0的商品
     * 3：操作失败，该加价购活动组合商品更新失败
     * 
     * @param promotionId
     * @return
     * @throws ServiceException
     */
    public int updataPromotionStatus(Long promotionId) throws ServiceException;

    /**
     * 根据互斥ID查询
     * 
     * @param mutexPromotionId
     * @return
     */
    public Map<Long, String> selectExclusionPromotion(String mutexPromotionId)
            throws ServiceException;

    /**
     * 新增活动数据
     * 
     * @param p
     * @param prizeData
     * @throws Exception
     */
    // public void addPromotionNew(PromotionInfo p, String prizeData) throws ServiceException;

    /**
     * 订单结算后，更新促销活动商品销售数，同时更新部分缓存
     * 
     * @param stockList
     * @return
     */
    public boolean batchUpdatePromotionSell(List<ProductStock> stockList) throws ServiceException;

    /**
     * 订单取消后，更新促销活动商品销售数，同时更新部分缓存
     * 
     * @param stockList
     * @return
     */
    public boolean batchUpdatePromotionSellMin(List<ProductStock> stockList)
            throws ServiceException;

    /**
     * 查询规则个数
     * 
     * @param ruldId
     * @return
     * @throws ServiceException
     */
    public Integer queryCoutPromotionByRuleId(Long ruldId) throws ServiceException;

    /**
     * 加载活动到商品中并设置规则数据
     * 
     * @param <T>
     * @param t
     * @param info
     * @throws ServiceException
     */
    public <T extends BaseProduct> void setPromotionInfoToBaseProduct(T t, PromotionInfo info)
            throws ServiceException;

    /**
     * 查询商家有效活动
     * 
     * @param sellerIds
     * @return
     * @throws ServiceException
     */
    public Map<Long, List<PromotionInfo>> queryAblePromotions(List<Long> sKeys)
            throws ServiceException;

    Page queryPromotionListFroRemote(Page page, PromotionInfo promotion) throws ServiceException;

    Long addPromotionNewForRemote(PromotionInfo p, String prizeData, String meetDataType)
            throws ServiceException;

    public List<PromotionInfo> getAllPromotionInfoByProduct(ProductSku andsku,
            List<PromotionInfo> list);


    /**
     * 更新索引状态
     * 
     * @param p
     * @return
     */
    public void updateIsSyncIndex(List<Long> promotionId, Integer syncIndex)
            throws ServiceException;

    public BigDecimal getPriceByPromotionAndProduct(ProductSku andsku, Date now,
            List<PromotionInfo> list);

    /**
     * 
     * 新增活动数据,增加了订单级活动满足条件类型，满 元/件数
     * 
     * @param promotion
     * @param ruleData
     * @param meetDataType
     * @throws ServiceException
     * @return_type void
     * @author songmiao
     * @date 2015-7-27 下午02:42:30
     * @exception @since 1.0.0
     */
    public void addPromotionNew(PromotionInfo promotion, String ruleData, String meetDataType)
            throws ServiceException;

    /**
     * 根据促销活动ID更新缓存数据
     * 
     * @param promotionid
     * @throws ServiceException
     */
    public void synPromotionCom(long promotionid) throws ServiceException;

    /**
     * 根据SKUID查询商品可参加的促销活动
     * 
     * @param map
     * @return
     */
    public List<PromotionInfo> queryProductEnterablePromotion(Map<String, Object> map)
            throws ServiceException;

    /**
     * 设置promotionFilterProductName属性
     * 
     * @param promotion
     * @return
     */
    public PromotionInfo promotionFilterProductName(PromotionInfo promotion);

    public Integer createPromotion(List<PromotionProduct> list, PromotionInfo p);

    public Integer promotionIsLock(PromotionInfo promotion) throws ServiceException, SQLException;
}
