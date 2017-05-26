package com.kmzyc.promotion.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.exception.ServiceException;

public interface PromotionInfoDao {

    /**
     * 根据查询条件查询有效的（通过审核）活动
     * 
     * @param map
     * @return
     */
    public List<PromotionInfo> getPromotionInfoList(Map<String, Object> map) throws SQLException;

    /**
     * 根据活动ID查询
     * 
     * @param id
     * @return
     */
    public PromotionInfo getPromotionInfoById(Long id) throws SQLException;

    /**
     * 根据活动种类查询活动
     * 
     * @param promotion
     * @return
     * @throws ServiceException
     */
    public List<PromotionInfo> selectPromotionByNature(PromotionInfo promotion) throws SQLException;

    /**
     * 查询有效活动
     * 
     * @param map
     * @return
     * @throws SQLException
     * @throws SQLException
     */
    public List<PromotionInfo> queryAblePromotions() throws SQLException;

    /**
     * 根据商家类型查询有效活动 参数：1 商家类型,2渠道ID
     * 
     * @param shopSort
     * @return
     */
    public List<PromotionInfo> getPromtionBySupplierType(Long shopSort) throws SQLException;

    /**
     * 根据SkuId查询限购活动 参数：产品skuId
     * 
     * @param skuId
     * @return
     */
    public List<PromotionInfo> getPurchasePromontionBySkuId(Long skuId) throws SQLException;

    /**
     * 查询商家有效活动
     * 
     * @param supplierId
     * @param sellerIds
     * @return
     */
    public List<PromotionInfo> getValidPromotionListBySupplierId(Long supplierId,
            List<Long> sellerIds) throws SQLException;

    /**
     * 根据商家类型查询有效活动
     * 
     * @param map
     * @return
     * @throws SQLException
     * @throws SQLException
     */
    public List<PromotionInfo> queryAblePromotionsByType(Map<String, Object> params)
            throws SQLException;

    /**
     * 查询商家有效活动
     * 
     * @param map
     * @return
     * @throws SQLException
     */
    public List<PromotionInfo> queryAblePromotionsIds(Map<String, Object> params)
            throws SQLException;

    /**
     * 分页查询
     * 
     * @param page
     * @param promotion
     * @return
     * @throws SQLException
     */
    public Page queryPage(Page page, PromotionInfo promotion) throws SQLException;

    /**
     * 查询时间存在冲突的促销活动
     * 
     * @param promotion
     * @return
     * @throws SQLException
     */
    public List<PromotionInfo> selectMutexPromotion(PromotionInfo promotion) throws SQLException;

    /**
     * 查询是否存在相同的优先级
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    public Integer selectCountPromotionPriority(PromotionInfo record) throws SQLException;

    /**
     * 查询互斥活动
     * 
     * @param mutexPromotionId
     * @return
     * @throws SQLException
     */
    public Map<Long, String> selectExclusionPromotion(String mutexPromotionId) throws SQLException;

    /**
     * 更新活动
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    public int updateByPromotion(PromotionInfo record) throws SQLException;

    /**
     * 更新活动上线过期状态
     */
    public Integer updatePromotionOnlineStatus() throws SQLException;

    /**
     * 根据活动ID删除活动
     * 
     * @param promotionId
     * @return
     * @throws SQLException
     */
    public int deletePromotionByPromotionId(Long promotionId) throws SQLException;

    /**
     * 新增活动
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    public Long addPromotion(PromotionInfo record) throws SQLException;

    /**
     * 根据活动规则ID查询活动个数
     * 
     * @param ruldId
     * @return
     * @throws SQLException
     */
    public Integer queryCoutPromotionByRuleId(Long ruldId) throws SQLException;

    Page queryPageForRemote(Page page, PromotionInfo promotion) throws SQLException;

    /**
     * 更新索引状态
     * 
     * @param map
     * @throws SQLException
     */
    public void updateIsSyncIndex(Map<String, Object> map) throws SQLException;

    /**
     * 根据促销活动ID更新 促销活动表 的 是否已同步缓存状态
     * 
     * @param promotionInfo
     * @throws SQLException
     */
    public void updatePromotionIsSycnCacheByPromotionId(PromotionInfo promotionInfo)
            throws SQLException;

    /**
     * 根据SKUID查询商品可参加的促销活动
     * 
     * @param map
     * @return
     */
    public List<PromotionInfo> queryProductEnterablePromotion(Map<String, Object> map)
            throws SQLException;

    public Integer promotionIsLock(PromotionInfo promotion) throws SQLException;
}
