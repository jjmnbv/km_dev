/**
 * 
 */
/**
 * @author KM
 *
 */
package com.kmzyc.promotion.presell.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.promotion.app.enums.AuditStatus;
import com.kmzyc.promotion.app.enums.PresellStatus;
import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellProduct;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.promotion.presell.dao.PresellInfoDao;
import com.kmzyc.promotion.presell.service.PresellInfoService;

@Repository("presellInfoService")
@SuppressWarnings("unchecked")
public class PresellInfoServiceImpl implements PresellInfoService {

    private Logger logger = LoggerFactory.getLogger(PresellInfoServiceImpl.class);
    @Resource
    private PresellInfoDao presellInfoDao;

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Long savePresellRuleProduct(PromotionPresell promotionPresell,
            List<PromotionPresellProduct> listPresellProducts) {
        Long presellId = null;
        if (listPresellProducts == null) {
            logger.info("预售产品为空");
            throw new ServiceException("预售产品为空");
        }
        try {
            // 新增预售规则
            promotionPresell.setPresellStatus(PresellStatus.DRAFT.getStatus());
            promotionPresell.setAuditStatus(Integer.valueOf(AuditStatus.UNAUDIT.getStatus()));
            presellId = presellInfoDao.insertPresellRule(promotionPresell);
            for (PromotionPresellProduct promotionPresellProduct : listPresellProducts) {
                promotionPresellProduct.setPresellId(Long.valueOf(presellId));
                // 设置尾款
                promotionPresellProduct.setFinalPrice(promotionPresellProduct.getPresellPrice()
                        .subtract(promotionPresellProduct.getDepositPrice()));
            }
            // 批量新增预售商品
            presellInfoDao.batchInsertPresellProduct(listPresellProducts);
        } catch (Exception e) {
            logger.error("新增预售规则及商品失败", e);
            throw new ServiceException(e);
        }
        return presellId;
    }

    @Override
    public PromotionPresell findPresellInfoDetailById(Long presellId) throws ServiceException {
        PromotionPresell promotinPresell = null;
        try {
            promotinPresell = presellInfoDao.findPresellInfoDetailById(presellId);
            if (promotinPresell == null) {
                logger.error("通过预售id查询预售详细信息失败");
                throw new ServiceException("通过预售id查询预售详细信息失败");
            }
        } catch (Exception e) {
            logger.error("通过预售id查询预售详细信息失败", e);
            throw new ServiceException(e);
        }
        return promotinPresell;
    }

    @Override
    public void updatePresellRule(PromotionPresell promotionPresell) throws Exception {
        try {
            int rest = presellInfoDao.updatePresellRule(promotionPresell);
            if (rest < 0) {
                logger.error("修改预售规则信息失败");
                throw new ServiceException("修改预售规则信息失败");
            }
        } catch (Exception e) {
            logger.error("修改预售规则信息失败", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public int selectPresellAndActivityProductCount(List<Long> skuIds) throws Exception {
        return presellInfoDao.selectPresellAndActivityProductCount(skuIds);
    }

}
