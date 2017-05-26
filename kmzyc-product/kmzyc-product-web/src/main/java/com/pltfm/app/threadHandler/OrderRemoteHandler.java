package com.pltfm.app.threadHandler;

import org.slf4j.LoggerFactory;

import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.remote.ExpressSubscriptionRemoteService;
import com.kmzyc.order.remote.OrderAlterCallBackRemoteService;
import com.kmzyc.order.remote.OrderCallBackRemoteService;
import com.pltfm.app.enums.StockOutTypeStatus;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;

public class OrderRemoteHandler implements Runnable {

    private org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    //出库单类型
    private Short stockOutType;

    private ExpressSubscriptionRemoteService expressSubscriptionRemoteService;

    private OrderCallBackRemoteService orderCallBackRemoteService;

    private OrderAlterCallBackRemoteService orderAlterCallBackRemoteService;

    private LogisticAndDistributionInfoVO logisticAndDistributionInfoVO;

    public OrderRemoteHandler(Short stockOutType, ExpressSubscriptionRemoteService expressSubscriptionRemoteService,
                              OrderCallBackRemoteService orderCallBackRemoteService,
                              OrderAlterCallBackRemoteService orderAlterCallBackRemoteService,
                              LogisticAndDistributionInfoVO logisticAndDistributionInfoVO) {
        this.stockOutType = stockOutType;
        this.expressSubscriptionRemoteService = expressSubscriptionRemoteService;
        this.orderCallBackRemoteService = orderCallBackRemoteService;
        this.orderAlterCallBackRemoteService = orderAlterCallBackRemoteService;
        this.logisticAndDistributionInfoVO = logisticAndDistributionInfoVO;
    }

    @Override
    public void run() {
        ExpressSubscription expressSubscription = new ExpressSubscription();
        expressSubscription.setLogisticsCode(logisticAndDistributionInfoVO.getLogisticsCode());
        expressSubscription.setLogisticsNo(logisticAndDistributionInfoVO.getLogisticsOrderNo());
        expressSubscription.setOrderCode(logisticAndDistributionInfoVO.getOrderCode());

        try {
            log.info("订单{},订阅物流信息准备开始!", logisticAndDistributionInfoVO.getOrderCode());
            String result = expressSubscriptionRemoteService.sucribeOrderExpressInfo(expressSubscription);
            log.info("订单{},订阅物流信息完成!结果={}", new Object[]{logisticAndDistributionInfoVO.getOrderCode(), result});

            if (StockOutTypeStatus.ORDER.getStatus().shortValue() == stockOutType.shortValue()) {
                log.info("订单{},物流单号推送准备开始!", logisticAndDistributionInfoVO.getOrderCode());
                result = orderCallBackRemoteService.getLogisticNumber(logisticAndDistributionInfoVO);
                log.info("订单{},物流单号推送完成!结果=", new Object[]{logisticAndDistributionInfoVO.getOrderCode(), result});
            } else if (StockOutTypeStatus.EXCHANGE.getStatus().shortValue() == stockOutType.shortValue()) {
                log.info("换货单物流单号推送准备开始!");
                result = orderAlterCallBackRemoteService.getLogisticNumber(logisticAndDistributionInfoVO);
                log.info("订单{},换货单物流单号推送完成!结果=",new Object[]{logisticAndDistributionInfoVO.getOrderCode(), result});
            }
        } catch (Exception e) {
            log.error("订阅物流信息失败：", e);
        }
    }

    public Short getStockOutType() {
        return stockOutType;
    }

    public void setStockOutType(Short stockOutType) {
        this.stockOutType = stockOutType;
    }

    public LogisticAndDistributionInfoVO getLogisticAndDistributionInfoVO() {
        return logisticAndDistributionInfoVO;
    }

    public void setLogisticAndDistributionInfoVO(
            LogisticAndDistributionInfoVO logisticAndDistributionInfoVO) {
        this.logisticAndDistributionInfoVO = logisticAndDistributionInfoVO;
    }

    public ExpressSubscriptionRemoteService getExpressSubscriptionRemoteService() {
        return expressSubscriptionRemoteService;
    }

    public void setExpressSubscriptionRemoteService(ExpressSubscriptionRemoteService expressSubscriptionRemoteService) {
        this.expressSubscriptionRemoteService = expressSubscriptionRemoteService;
    }

    public OrderCallBackRemoteService getOrderCallBackRemoteService() {
        return orderCallBackRemoteService;
    }

    public void setOrderCallBackRemoteService(OrderCallBackRemoteService orderCallBackRemoteService) {
        this.orderCallBackRemoteService = orderCallBackRemoteService;
    }

    public OrderAlterCallBackRemoteService getOrderAlterCallBackRemoteService() {
        return orderAlterCallBackRemoteService;
    }

    public void setOrderAlterCallBackRemoteService(OrderAlterCallBackRemoteService orderAlterCallBackRemoteService) {
        this.orderAlterCallBackRemoteService = orderAlterCallBackRemoteService;
    }
}