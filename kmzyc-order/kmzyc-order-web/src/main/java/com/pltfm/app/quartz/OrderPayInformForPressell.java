package com.pltfm.app.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pltfm.app.service.OrderPayInformForPressellService;

/**
 * 尾款支付通知短信定时任务
 * 
 * @author mkw
 * 
 */
public class OrderPayInformForPressell extends QuartzJobBean {

    private static final Logger logger = Logger.getLogger(OrderPayInformForPressell.class);


    private OrderPayInformForPressellService orderPayInformForPressellService;


    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        try {
            orderPayInformForPressellService.operate();
        } catch (Exception e) {
            logger.error("OrderPayInformForPressell", e);
        }

    }


    public OrderPayInformForPressellService getOrderPayInformForPressellService() {
        return orderPayInformForPressellService;
    }


    public void setOrderPayInformForPressellService(
            OrderPayInformForPressellService orderPayInformForPressellService) {
        this.orderPayInformForPressellService = orderPayInformForPressellService;
    }




}
