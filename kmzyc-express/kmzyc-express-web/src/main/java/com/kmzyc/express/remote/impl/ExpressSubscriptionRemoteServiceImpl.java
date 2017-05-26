package com.kmzyc.express.remote.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.remote.ExpressSubscriptionRemoteService;
import com.kmzyc.express.service.ExpressCompanyService;
import com.kmzyc.express.service.ExpressSubscriptionService;
import com.kmzyc.express.util.ErrorCode;

@SuppressWarnings("unchecked")
@Service("expressSubscriptionRemoteService")
public class ExpressSubscriptionRemoteServiceImpl implements ExpressSubscriptionRemoteService {

    private static final long serialVersionUID = 1709801756084802575L;

    private static Logger logger = LoggerFactory.getLogger(ExpressSubscriptionRemoteServiceImpl.class);

    @Resource
    ExpressSubscriptionService expressSubscriptionService;

    @Resource
    ExpressCompanyService expressCompanyService;

    /**
     * 内部系统订阅订单物流信息, 返回SUCESS:表示订阅成功，FAIL:表示订阅失败,
     */
    @Override
    public String sucribeOrderExpressInfo(ExpressSubscription expressSubscription)
            throws ServiceException {
        String result = "FAIL";
        try {
            // 入参检查
            if (StringUtils.isEmpty(checkSubscriberParameters(expressSubscription))) {
                // 内部系统向中间件订阅
                BigDecimal subId = expressSubscriptionService.executeInnerSubscription(expressSubscription);
                // 如果能返回一个合适的subId,则认为订阅成功
                if (subId != null && subId.compareTo(BigDecimal.ZERO) > 0) {
                    result = "SUCESS";
                }
            }
        } catch (Exception e) {
            logger.error("系统订阅物流信息失败：" + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "系统订阅物流信息失败" + e.getMessage(), e);
        }
        return result;
    }

    /**
     * 传入参数检查
     *
     * @param record
     * @return
     */
    private String checkSubscriberParameters(ExpressSubscription record) throws ServiceException {
        String checkResult = "";
        if (record == null) {
            checkResult = "传入的物流信息不能为空(001)";
        } else if (StringUtils.isEmpty(record.getLogisticsCode())) {
            checkResult = "传入的物流公司编码不能为空(002)";
        } else if (StringUtils.isEmpty(record.getLogisticsNo())) {
            checkResult = "传入的物流单据编码不能为空(003)";
        }
        return checkResult;
    }

    @Override
    public ExpressSubscription queryOrderExpressInfo(String logisticsCode, String logisticsNo)
            throws ServiceException {
        ExpressSubscription result = null;
        try {
            if (StringUtils.isEmpty(logisticsCode) || StringUtils.isEmpty(logisticsNo)) {
                logger.error("系统获取物流信息失败,参数有为空。快递公司编号[{}],物流单号[{}].",
                        new Object[]{logisticsCode, logisticsNo});
                return result;
            }
            List listResult = expressSubscriptionService.queryExpressSubWithTrackDetail(logisticsCode, logisticsNo);
            if (CollectionUtils.isNotEmpty(listResult)) {
                result = (ExpressSubscription) listResult.get(0);
            }
        } catch (Exception e) {
            logger.error("内部系统获取物流信息失败：", e);
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "内部系统获取物流信息失败" + e.getMessage(), e);
        }
        return result;
    }
}
