package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.PromotionRuleDAO;
import com.kmzyc.promotion.app.dao.PromotionRuleDataDAO;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.service.PromotionInfoService;
import com.kmzyc.promotion.app.service.PromotionRuleService;
import com.kmzyc.promotion.app.util.ListUtils;
import com.kmzyc.promotion.app.vobject.PromotionRule;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.app.vobject.PromotionRuleDataExample;
import com.kmzyc.promotion.exception.ServiceException;

@Service("promotionRuleService")
@SuppressWarnings("unchecked")
public class PromotionRuleServiceImpl implements PromotionRuleService {
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(PromotionRuleServiceImpl.class);

    @Resource
    private PromotionRuleDAO promotionRuleDAO;

    @Resource
    private PromotionInfoService promotionInfoService;

    @Resource
    private PromotionRuleDataDAO promotionRuleDataDAO;

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void saveOrUpdatePromotionRule(PromotionRule pr, PromotionRuleData promotionRuleData) {

        if (pr == null) {
            throw new ServiceException(new NullPointerException("修改或者保存的对象为空！"));
        }
        try {
            if (pr.getPromotionRuleId() != null) {
                updatePromtionRule(pr, promotionRuleData);
            } else {
                addPromtionRule(pr, promotionRuleData);
            }
        } catch (SQLException e) {
            logger.error("saveOrUpdatePromotionRule异常：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void savePromotionRule(PromotionRule pr, List<PromotionRuleData> promotionRuleDataList) {

        this.addPromotionRule(pr);
        if (!ListUtils.isNotEmpty(promotionRuleDataList)) {
            return;
        }
        // if(pr.getPromotionTypeId().equals(PromotionTypeEnums.OTHER.getValue())){
        // return;
        // }
        for (PromotionRuleData promotionRuleData : promotionRuleDataList) {
            // promotionRuleData.setPromotionRuleId(id);
            // 1：金额；2：折扣率；3：优惠券ID
            if (pr.getPromotionTypeId().equals(PromotionTypeEnums.COUPON.getValue())) {
                promotionRuleData.setPrizeDataType(3);// 优惠券ID
            } else if (pr.getPromotionTypeId().equals(PromotionTypeEnums.DISCOUNT.getValue())) {
                // promotionRuleData.setMeetData(null);
                // promotionRuleData.setMeetDataType(null);
                promotionRuleData.setPrizeDataType(2);// 折扣
                // promotionRuleData.setPrizeData(prizeData);
            } else {
                promotionRuleData.setPrizeDataType(1);// 金额
            }
            try {
                if (promotionRuleData.getMeetData() == null
                        && (promotionRuleData.getPrizeData() == null
                                || promotionRuleData.getPrizeData().equals(""))) {
                    return;
                }
                promotionRuleDataDAO.insertSelective(promotionRuleData);
            } catch (SQLException e) {
                logger.error("savePromotionRule异常：", e);
                throw new ServiceException(e);
            }
        }

    }

    private void addPromtionRule(PromotionRule pr, PromotionRuleData promotionRuleData)
            throws SQLException {
        this.addPromotionRule(pr);
        // if(pr.getPromotionTypeId().equals(PromotionTypeEnums.OTHER.getValue())){
        // return;
        // }
        // promotionRuleData.setPromotionRuleId(id);
        // 1：金额；2：折扣率；3：优惠券ID
        if (pr.getPromotionTypeId().equals(PromotionTypeEnums.COUPON.getValue())) {
            promotionRuleData.setPrizeDataType(3);// 优惠券ID
        } else if (pr.getPromotionTypeId().equals(PromotionTypeEnums.DISCOUNT.getValue())) {
            // promotionRuleData.setMeetData(null);
            // promotionRuleData.setMeetDataType(null);
            promotionRuleData.setPrizeDataType(2);// 折扣
            // promotionRuleData.setPrizeData(prizeData);
        } else {
            promotionRuleData.setPrizeDataType(1);// 金额
        }
        if (promotionRuleData.getMeetData() == null && (promotionRuleData.getPrizeData() == null
                || promotionRuleData.getPrizeData().equals(""))) {
            return;
        }
        promotionRuleDataDAO.insertSelective(promotionRuleData);
    }

    private void updatePromtionRule(PromotionRule pr, PromotionRuleData promotionRuleData)
            throws SQLException {
        promotionRuleDAO.updateByPrimaryKeySelective(pr);
    }

    @Override
    public Page selectPageByVo(Page page, PromotionRule record) {

        try {
            return promotionRuleDAO.selectPageByVo(page, record);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkPromotionRule(PromotionRule record) {
        int count = 0;
        try {
            count = promotionRuleDAO.selectCountByVo(record);
        } catch (SQLException e) {
            logger.error("checkPromotionRule异常：", e);
        }
        return count == 0;
    }

    @Override
    public PromotionRule getPromotionRuleById(Long promotionRuleId) {

        try {
            PromotionRule promotionRule = promotionRuleDAO.selectByPrimaryKey(promotionRuleId);
            PromotionRuleDataExample example = new PromotionRuleDataExample();
            example.setOrderByClause("MEET_DATA ASC");
            example.createCriteria().andPromotionRuleIdEqualTo(promotionRuleId);
            List<PromotionRuleData> promotionRuleDataList =
                    promotionRuleDataDAO.selectByExample(example);
            promotionRule.setPromotionRuleDataList(promotionRuleDataList);
            if (ListUtils.isNotEmpty(promotionRuleDataList)) {
                PromotionRuleData data = promotionRuleDataList.get(0);
                promotionRule.setMeetData(data.getMeetData());
                promotionRule.setPrizeData(data.getPrizeData());
            }
            return promotionRule;
        } catch (SQLException e) {
            logger.error("getPromotionRuleById异常：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deletePromotionRule(PromotionRule record) {
        try {

            promotionRuleDAO.deleteByPrimaryKey(record.getPromotionRuleId());
        } catch (SQLException e) {
            logger.error("deletePromotionRule异常：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public String deletePromotionRule(List<PromotionRule> list) {
        if (list.isEmpty()) {
            return "";
        }
        StringBuffer notCanDeleteRuleName = new StringBuffer();
        try {
            for (PromotionRule record : list) {
                record = promotionRuleDAO.selectByPrimaryKey(record.getPromotionRuleId());
                if (promotionInfoService.queryCoutPromotionByRuleId(record.getPromotionRuleId())
                        .compareTo(0) > 0) {
                    notCanDeleteRuleName.append(record.getPromotionRuleRuleName());
                    notCanDeleteRuleName.append(",");
                } else {
                    PromotionRuleDataExample dataExample = new PromotionRuleDataExample();
                    dataExample.createCriteria()
                            .andPromotionRuleIdEqualTo(record.getPromotionRuleId());
                    promotionRuleDataDAO.deleteByExample(dataExample);
                    this.deletePromotionRule(record);
                }
            }
        } catch (SQLException e) {
            logger.error("deletePromotionRule异常：", e);
            throw new ServiceException(e);
        }
        return notCanDeleteRuleName.toString();
    }

    @Override
    public Long addPromotionRule(PromotionRule pr) {

        pr.setPromotionRuleType((short) 1);
        if (pr.getPromotionRulePriority() == null) {
            pr.setPromotionRulePriority(0);
        }
        Object idObj;
        try {
            idObj = promotionRuleDAO.insertSelective(pr);
        } catch (SQLException e) {
            logger.error("addPromotionRule异常：", e);
            throw new ServiceException(e);
        }
        return (Long) idObj;
    }

    @Override
    public Long getIncreasePromotionRuleId() {

        try {
            Long id = promotionRuleDAO.getIncreasePromotionRuleId();
            if (id == null) {
                PromotionRule pr = new PromotionRule();
                pr.setPromotionTypeId(PromotionTypeEnums.SALE.getValue());
                // pr.setPromotionRuleType();
                pr.setPromotionRuleRuleName("加价购专用规则(勿删)");
                id = addPromotionRule(pr);
            }
            return id;
        } catch (Exception e) {
            logger.error("getIncreasePromotionRuleId异常：", e);
            return null;
        }
    }
}
