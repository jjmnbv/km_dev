package com.kmzyc.promotion.presell.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellCriteria;
import com.kmzyc.promotion.presell.dao.PresellManagerDao;

@Repository("presellManagerDao")
@SuppressWarnings("unchecked")
public class PresellManagerDaoimpl implements PresellManagerDao {

    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public List<PromotionPresell> queryPresellManagerList(
            PromotionPresellCriteria promotionPresellCriteria) throws Exception {
        return sqlMapClient.queryForList("PROMOTION_PRESELL.queryPresellManagerList",
                promotionPresellCriteria);

    }

    @Override
    public PromotionPresell queryPresellInfoByPresellId(long presellId) throws Exception {
        return (PromotionPresell) sqlMapClient.queryForObject(
                "PROMOTION_PRESELL.queryPresellInfoByPresellId", presellId);
    }

    @Override
    public Integer queryPresellManagerCount(PromotionPresellCriteria promotionPresellCriteria)
            throws Exception {

        return (Integer) sqlMapClient.queryForObject("PROMOTION_PRESELL.queryPresellManagerCount",
                promotionPresellCriteria);
    }

    @Override
    public List<PromotionPresell> queryProductDetailList(List<PromotionPresell> list)
            throws Exception {
        return sqlMapClient.queryForList("PROMOTION_PRESELL.queryProductDetailList", list);
    }

    @Override
    public void submitPresellApp(PromotionPresellCriteria promotionPresellCriteria)
            throws Exception {
        sqlMapClient.update("PROMOTION_PRESELL.updatePresellManager", promotionPresellCriteria);
    }

    @Override
    public void deletePresellInfo(Long presellId) throws Exception {
        sqlMapClient.delete("PROMOTION_PRESELL.deletePresellInfo", presellId);
    }

    @Override
    public void deletePresellProduct(Long presellId) throws Exception {
        sqlMapClient.delete("PROMOTION_PRESELL.deletePresellProduct", presellId);
    }

    @Override
    public int stopPresell(PromotionPresellCriteria promotionPresellCriteria) throws Exception {
        return sqlMapClient.update("PROMOTION_PRESELL.updatePresellManager",
                promotionPresellCriteria);
    }

    @Override
    public List<Long> querySkuidsByPresellId(Long presellId) throws Exception {
        return sqlMapClient.queryForList("PROMOTION_PRESELL.querySkuidsByPresellId", presellId);
    }

    @Override
    public Integer queryPromotionCountBySkuid(Long skuid) throws Exception {
        return (Integer) sqlMapClient.queryForObject(
                "PROMOTION_PRESELL.queryPromotionCountBySkuid", skuid);
    }


    @Override
    public Double queryProductPriceBySkuid(Long skuid) throws Exception {
        return (Double) sqlMapClient.queryForObject("PROMOTION_PRESELL.queryProductPriceBySkuid",
                skuid);
    }

    @Override
    public void addPreselledCount(Long skuid, Long presellId, int count) throws Exception {
        HashMap<String, Number> map = new HashMap<String, Number>();
        map.put("skuid", skuid);
        map.put("count", count);
        map.put("presellId", presellId);
        sqlMapClient.update("PROMOTION_PRESELL.addPreselledCount", map);
    }

    @Override
    public String queryProductStatusBySkuid(Long skuid) throws Exception {
        return (String) sqlMapClient.queryForObject("PROMOTION_PRESELL.queryProductStatusBySkuid",
                skuid);
    }

    @Override
    public void cancelPresellApply(Long presellId) throws Exception {
        sqlMapClient.update("PROMOTION_PRESELL.cancelPresellApply", presellId);
    }

    @Override
    public List<Long> queryPresellIdForAutoStop() throws Exception {
        return sqlMapClient.queryForList("PROMOTION_PRESELL.queryPresellIdForAutoStop");
    }

}
