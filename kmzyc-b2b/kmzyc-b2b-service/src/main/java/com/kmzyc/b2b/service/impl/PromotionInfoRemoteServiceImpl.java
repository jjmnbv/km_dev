package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.PromotionRuleDataDao;
import com.kmzyc.b2b.model.PromotionRuleData;
import com.kmzyc.b2b.service.PromotionInfoRemoteService;

@Service("promotionInfoRemoteService")
public class PromotionInfoRemoteServiceImpl implements PromotionInfoRemoteService {
    private static Logger logger= LoggerFactory.getLogger(PromotionInfoRemoteService.class);
    @Resource
    private PromotionRuleDataDao promotionRuleDataDao;

    /** 获取指定金额满足活动的奖励数据 */
    public Long getPrizeDate(Long promotionInfoId, BigDecimal money) {
        Long conpouId = null;
        try {

            Map<String, Object> params = new HashMap<>();
            params.put("promotionInfoId", promotionInfoId);
            params.put("money", money);
            List<PromotionRuleData> ruleDatas =
                    promotionRuleDataDao.queryPromotionRuleDataByPromotionId(params);
            int ruleCount = ruleDatas.size() - 1;
            @SuppressWarnings("unused")
            BigDecimal meetData = null;
            BigDecimal priceData = null;
            while (ruleCount >= 0) {
                PromotionRuleData ruleData = ruleDatas.get(ruleCount);
                BigDecimal meetData1 = ruleData.getMeetData();
                BigDecimal priceData1 = new BigDecimal(ruleData.getPrizeData());
                if (money.compareTo(meetData1) >= 0) {
                    priceData = priceData1;
                    // info.setPrivilegeData(priceData);
                    break;
                }
                ruleCount--;
            }
            if (priceData != null) {
                conpouId = priceData.longValue();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return conpouId;
    }
}
