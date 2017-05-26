package com.kmzyc.promotion.app.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.PromotionInfoDao;
import com.kmzyc.promotion.app.util.Constants;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.exception.ServiceException;

@Component("promotionInfoDao")
@SuppressWarnings("unchecked")
public class PromotionInfoDaoImpl implements PromotionInfoDao {
    @Resource
    private SqlMapClient sqlMapClient;

    /**
     * 根据查询条件查询有效的（通过审核）活动
     * 
     * @param map
     * @return
     */
    @Override
    public List<PromotionInfo> getPromotionInfoList(Map<String, Object> map) throws SQLException {

        return sqlMapClient.queryForList("PromotionInfo.SQL_QUERY_AUDITED_PROMOTION_INFO", map);
    }

    /**
     * 根据活动ID查询
     * 
     * @param id
     * @return
     */
    @Override
    public PromotionInfo getPromotionInfoById(Long id) throws SQLException {

        return (PromotionInfo) sqlMapClient
                .queryForObject("PromotionInfo.SQL_QUERY_PROMOTION_INFO_BY_ID", id);
    }

    /**
     * 根据商家类型查询有效活动 参数：1 商家类型,2渠道ID
     * 
     * @param shopSort
     * @return
     */
    @Override
    public List<PromotionInfo> getPromtionBySupplierType(Long shopSort) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("shopSort", shopSort);

        return sqlMapClient.queryForList("PromotionInfo.SQL_QUERY_PROMTION_INFO_BY_SUPPLIER_TYPE",
                map);
    }

    /**
     * 根据活动种类查询活动
     * 
     * @param promotion
     * @return
     * @throws ServiceException
     */
    @Override
    public List<PromotionInfo> selectPromotionByNature(PromotionInfo promotion)
            throws SQLException {

        return sqlMapClient.queryForList("PromotionInfo.SQL_QUERY_PROMOTION_BY_NATURE", promotion);
    }

    /**
     * 根据活动规则ID查询活动个数
     * 
     * @param ruldId
     * @return
     * @throws SQLException
     */
    @Override
    public Integer queryCoutPromotionByRuleId(Long ruldId) throws SQLException {

        return (Integer) sqlMapClient
                .queryForObject("PromotionInfo.SQL_QUERY_COUNT_PROMOTION_RULE_ID");
    }

    /**
     * 根据SkuId查询限购活动 参数：产品skuId
     * 
     * @param skuId
     * @return
     */
    @Override
    public List<PromotionInfo> getPurchasePromontionBySkuId(Long skuId) throws SQLException {

        return (List<PromotionInfo>) sqlMapClient
                .queryForObject("PromotionInfo.SQL_PURCHASE_PROMONTION_BY_SKU_ID", skuId);
    }

    /**
     * 查询商家有效活动 参数：1渠道ID ,2活动商家ID
     * 
     * @param supplierId
     * @param sellerIds
     * @return
     */
    @Override
    public List<PromotionInfo> getValidPromotionListBySupplierId(Long supplierId,
            List<Long> sellerIds) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sellerId", supplierId);
        map.put("sellerIds", sellerIds);

        return sqlMapClient.queryForList("PromotionInfo.SQL_QUERY_VALID_PROMOTION_BY_SUPPLIER_ID",
                map);
    }

    /**
     * 根据商家类型查询有效活动
     * 
     * @param map
     * @return
     * @throws SQLException
     * @throws SQLException
     */
    @Override
    public List<PromotionInfo> queryAblePromotionsByType(Map<String, Object> params)
            throws SQLException {

        return sqlMapClient.queryForList("PromotionInfo.SQL_QUERY_PROMTION_INFO_BY_SUPPLIER_TYPE",
                params);
    }

    /**
     * 查询有效活动
     * 
     * @param map
     * @return
     * @throws SQLException
     * @throws SQLException
     */
    @Override
    public List<PromotionInfo> queryAblePromotions() throws SQLException {

        return sqlMapClient.queryForList("PromotionInfo.SQL_QUERY_PROMTION_INFO");
    }

    /**
     * 查询商家有效活动
     * 
     * @param map
     * @return
     * @throws SQLException
     */
    @Override
    public List<PromotionInfo> queryAblePromotionsIds(Map<String, Object> params)
            throws SQLException {

        return sqlMapClient.queryForList("PromotionInfo.SQL_QUERY_VALID_PROMOTION_BY_SUPPLIER_ID",
                params);
    }

    /**
     * 分页查询
     * 
     * @param page
     * @param promotion
     * @return
     * @throws SQLException
     */
    @Override
    public Page queryPage(Page page, PromotionInfo promotion) throws SQLException {
        if (promotion == null) {
            promotion = new PromotionInfo();
        }

        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        promotion.setStartIndex((pageIndex - 1) * pageSize + 1);
        promotion.setEndIndex(pageSize * pageIndex);

        int count = (Integer) sqlMapClient
                .queryForObject("PromotionInfo.SQL_QUERY_PROMOTION_BY_PAGE_COUNT", promotion);
        List<PromotionInfo> list =
                sqlMapClient.queryForList("PromotionInfo.SQL_QUERY_PROMOTION_BY_PAGE", promotion);
        page.setRecordCount(count);
        page.setDataList(list);
        return page;
    }

    /**
     * 分页查询
     * 
     * @param page
     * @param promotion
     * @return
     * @throws SQLException
     */
    @Override
    public Page queryPageForRemote(Page page, PromotionInfo promotion) throws SQLException {
        if (promotion == null) {
            promotion = new PromotionInfo();
        }

        int count = (Integer) sqlMapClient
                .queryForObject("PromotionInfo.SQL_QUERY_PROMOTION_BY_PAGE_COUNT", promotion);
        List<PromotionInfo> list =
                sqlMapClient.queryForList("PromotionInfo.SQL_QUERY_PROMOTION_BY_PAGE", promotion);
        page.setRecordCount(count);
        page.setDataList(list);
        return page;
    }

    /**
     * 查询时间存在冲突的促销活动
     * 
     * @param promotion
     * @return
     * @throws SQLException
     */
    @Override
    public List<PromotionInfo> selectMutexPromotion(PromotionInfo promotion) throws SQLException {

        return sqlMapClient.queryForList("PromotionInfo.SQL_QUERY_MUTEX_PROMOTION", promotion);
    }

    /**
     * 查询是否存在相同的优先级
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    @Override
    public Integer selectCountPromotionPriority(PromotionInfo record) throws SQLException {

        Object obj = sqlMapClient.queryForObject("PromotionInfo.SQL_QUERY_COUNT_PROMOTION_PRIORITY",
                record);
        return null == obj ? 0 : (Integer) obj;
    }

    /**
     * 查询互斥活动
     * 
     * @param mutexPromotionId
     * @return
     * @throws SQLException
     */
    @Override
    public Map<Long, String> selectExclusionPromotion(String mutexPromotionId) throws SQLException {
        Map<Long, String> rsMap = null;

        if (!Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG.equals(mutexPromotionId)
                && null != mutexPromotionId && mutexPromotionId.length() > 0) {
            List<Long> pids = new ArrayList<Long>();
            for (String pid : mutexPromotionId.split(",")) {
                if (pid.length() > 0) {
                    pids.add(Long.parseLong(pid));
                }
            }
            if (pids.size() > 0) {
                rsMap = sqlMapClient.queryForMap("PromotionInfo.SQL_QUERY_EXCLUSION_PROMOTION",
                        pids, "PROMOTION_ID", "PROMOTION_NAME");
            }
        }
        return rsMap;
    }

    /**
     * 更新活动
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    @Override
    public int updateByPromotion(PromotionInfo promotion) throws SQLException {
        if (promotion.getMutexPromotionId() == null) {
            promotion.setMutexPromotionId("");
        }

        return sqlMapClient.update("PromotionInfo.SQL_UPDATE_PROMOTION_BY_PID", promotion);
    }

    /**
     * 更新活动上线过期状态
     */
    @Override
    public Integer updatePromotionOnlineStatus() throws SQLException {
        int notOnline = 0, isIng = 0, down = 0;

        try {
            notOnline = sqlMapClient.update("PromotionInfo.SQL_UPDATE_PROMOTION_NOT_ONLINE");// 更新未上线
        } catch (Exception e) {
        }
        try {
            isIng = sqlMapClient.update("PromotionInfo.SQL_UPDATE_PROMOTION_IS_ING");// 更新已上线
        } catch (Exception e) {
        }
        try {
            down = sqlMapClient.update("PromotionInfo.SQL_UPDATE_PROMOTION_DOWN");// 更新已过期
        } catch (Exception e) {
        }
        return notOnline + isIng + down;
    }

    /**
     * 根据活动ID删除活动
     * 
     * @param promotionId
     * @return
     * @throws SQLException
     */
    @Override
    public int deletePromotionByPromotionId(Long promotionId) throws SQLException {

        return sqlMapClient.delete("PromotionInfo.SQL_DELETE_PROMOTION_BY_PID", promotionId);
    }

    /**
     * 新增活动
     * 
     * @param promotion
     * @return
     * @throws SQLException
     */
    @Override
    public Long addPromotion(PromotionInfo promotion) throws SQLException {

        return (Long) sqlMapClient.insert("PromotionInfo.SQL_INSER_PROMOTION_INFO", promotion);
    }

    /**
     * 更新索引状态
     */
    @Override
    public void updateIsSyncIndex(Map<String, Object> map) throws SQLException {
        sqlMapClient.startBatch();
        List<Long> promotionIds = (List<Long>) map.get("pids");
        for (Long promotionId : promotionIds) {
            if (promotionId != null) {
                sqlMapClient.update("PromotionInfo.SQL_UPDATE_IS_SYNC_INDEX",
                        Long.valueOf(promotionId));
            }
        }
        sqlMapClient.executeBatch();

    }

    @Override
    public void updatePromotionIsSycnCacheByPromotionId(PromotionInfo promotionInfo)
            throws SQLException {

        sqlMapClient.update("PromotionInfo.SQL_UPDATE_PROMOTION_ISSYNCCACHE", promotionInfo);
    }

    @Override
    public List<PromotionInfo> queryProductEnterablePromotion(Map<String, Object> map)
            throws SQLException {
        return this.sqlMapClient.queryForList("PromotionInfo.queryProductEnterablePromotion", map);
    }

    @Override
    public Integer promotionIsLock(PromotionInfo promotion) throws SQLException {
        return (Integer) this.sqlMapClient.queryForObject("PromotionInfo.promotionIsLock",
                promotion.getPromotionId());
    }

}
