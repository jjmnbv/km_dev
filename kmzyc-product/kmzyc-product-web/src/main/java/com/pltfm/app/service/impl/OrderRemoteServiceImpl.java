package com.pltfm.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.remote.ExpressSubscriptionRemoteService;
import com.kmzyc.order.remote.OrderAlterCallBackRemoteService;
import com.kmzyc.order.remote.OrderAlterChangeStatusRemoteService;
import com.kmzyc.order.remote.OrderCallBackRemoteService;
import com.pltfm.app.service.OrderRemoteService;
import com.pltfm.app.threadHandler.OrderAfterHandler;
import com.pltfm.app.threadHandler.OrderRemoteHandler;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.StockOut;

@Service("orderRemoteService")
public class OrderRemoteServiceImpl implements OrderRemoteService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private TaskExecutor taskExecutor;

    @Resource
    private OrderAlterChangeStatusRemoteService orderAlterChangeStatusRemoteService;

    @Resource
    private ExpressSubscriptionRemoteService expressSubscriptionRemoteService;

    @Resource
    private OrderCallBackRemoteService orderCallBackRemoteService;

    @Resource
    private OrderAlterCallBackRemoteService orderAlterCallBackRemoteService;

    @Override
    public void getLogisticNumber(final LogisticAndDistributionInfoVO distribution, final Short stockOutType)
            throws ServiceException {
        try {
            taskExecutor.execute(new OrderRemoteHandler(stockOutType, expressSubscriptionRemoteService,
                    orderCallBackRemoteService,orderAlterCallBackRemoteService,distribution));
        } catch (TaskRejectedException e) {
            logger.error("调用订单系统的接口失败,", e);
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void changeAlterStatusForProduct(List<StockOut> stockOutList) throws ServiceException {
        try {
            taskExecutor.execute(new OrderAfterHandler(stockOutList, orderAlterChangeStatusRemoteService));
        } catch (TaskRejectedException e) {
            logger.error("订单转退货接口失败,", e);
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}