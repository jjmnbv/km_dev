package com.kmzyc.promotion.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.app.vobject.PromotionRuleDataExample;
import com.kmzyc.promotion.exception.ServiceException;

public interface PromotionRuleDataService {
    public List<PromotionRuleData> selectPromotionRuleList(Long promotionInfoId)
            throws ServiceException;

    public List selectByExample(PromotionRuleDataExample example) throws ServiceException;

    /**
     * 批量查询规则数据
     * 
     * @param pids
     * @return
     * @throws ServiceException
     */
    public Map<Long, List<PromotionRuleData>> queryBatchPromotionRuleData(List<Long> pids)
            throws ServiceException;

    public List<com.alibaba.fastjson.JSONObject> selectPromotionRuleAndPrizeEntity(
            PromotionInfo info);
}
