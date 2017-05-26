package com.kmzyc.promotion.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.promotion.app.dao.PromotionRuleDataDAO;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.service.PromotionRuleDataService;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.app.vobject.PromotionRuleDataExample;
import com.kmzyc.promotion.exception.ServiceException;

@Service("promotionRuleDataService")
public class PromotionRuleDataServiceImpl implements PromotionRuleDataService {
    private static Logger logger = LoggerFactory.getLogger(PromotionRuleDataServiceImpl.class);
    @Resource
    private PromotionRuleDataDAO promotionRuleDataDAO;

    @Override
    public List<PromotionRuleData> selectPromotionRuleList(Long promotionInfoId)
            throws ServiceException {
        try {
            return promotionRuleDataDAO.selectPromotionRuleList(promotionInfoId);
        } catch (Exception e) {
            logger.error("selectPromotionRuleList异常：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List selectByExample(PromotionRuleDataExample example) throws ServiceException {
        try {
            return promotionRuleDataDAO.selectByExample(example);
        } catch (Exception e) {
            logger.error("调用promotionRuleDataDAO.selectByExample异常：", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 批量查询规则数据
     * 
     * @param pids
     * @return
     * @throws ServiceException
     */
    @Override
    public Map<Long, List<PromotionRuleData>> queryBatchPromotionRuleData(List<Long> pids)
            throws ServiceException {
        Map<Long, List<PromotionRuleData>> rsMap = null;
        try {
            List<PromotionRuleData> prdList =
                    promotionRuleDataDAO.queryBatchPromotionRuleData(pids);
            if (null != prdList && !prdList.isEmpty()) {
                rsMap = new HashMap<Long, List<PromotionRuleData>>();
                Long pid = 0L;
                List<PromotionRuleData> innerList = new ArrayList<PromotionRuleData>();
                for (PromotionRuleData prd : prdList) {
                    if (0 != pid.compareTo(prd.getPromotionId())) {
                        if (0 != pid.compareTo(0L)) {
                            rsMap.put(pid, innerList);
                            innerList = new ArrayList<PromotionRuleData>();
                        }
                        pid = prd.getPromotionId();
                    }
                    innerList.add(prd);
                }
                if (null != pid && 0 != pid.compareTo(0L)) {
                    rsMap.put(pid, innerList);
                }
            }
        } catch (Exception e) {
            logger.error("批量查询规则数据异常：", e);
            throw new ServiceException(e);
        }
        return rsMap;
    }

    @Override
    public List<com.alibaba.fastjson.JSONObject> selectPromotionRuleAndPrizeEntity(
            PromotionInfo info) {
        int type = info.getPromotionType().intValue();
        long promotionId = info.getPromotionId().longValue();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("promotionId", String.valueOf(promotionId));

            String sql = "PROMOTION_RULE_DATA";
            if (type == PromotionTypeEnums.COUPON.getValue().intValue()) {
                sql += ".selectPromotionRuleAndCouponEntity";
            } else if (type == PromotionTypeEnums.GIFT.getValue().intValue()) {
                sql += ".selectPromotionRuleAndProductEntity";
            } else if (type == PromotionTypeEnums.INCREASE.getValue().intValue()) {
                sql += ".selectPromotionRuleAndProductEntity";
            } else if (type == PromotionTypeEnums.GANT.getValue().intValue()
                    && info.getPromotionData() != null
                    && info.getPromotionData().compareTo(BigDecimal.valueOf(-1)) == 0) {
                // 附赠为指定全场时
                sql += ".queryGantProduct";
            } else {
                sql += ".selectPromotionRuleData";
            }

            return promotionRuleDataDAO.selectPromotionRuleAndEntity(map, sql);
        } catch (SQLException e) {
            logger.error("查询活动规则数据异常，promotionId={},type={}", promotionId, type, e);
        }
        return null;
    }
}
