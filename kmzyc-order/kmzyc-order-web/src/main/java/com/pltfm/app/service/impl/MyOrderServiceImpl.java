package com.pltfm.app.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pltfm.app.remote.impl.OrderChangeStatusRemoteServiceImpl;
import com.pltfm.app.service.MyOrderService;
import com.pltfm.app.util.OrderDictionaryEnum;

@Service("myOrderService")
public class MyOrderServiceImpl implements MyOrderService {
  private static Logger logger = Logger.getLogger(MyOrderServiceImpl.class);
  
  @Resource
  private OrderChangeStatusRemoteServiceImpl orderChangeStatusRemoteService;
  @Override
  public int cancelOrderMain(String userAccount, String orderMainCode, long orderMainStatus)
      throws Exception {
    int result = -1;
    try {
      // 付款前与付款后取消订单统一调用接口，由订单系统处理付款前后问题
      logger.info("开始调用订单系统订单取消接口,参数userAccount:" + userAccount + ",orderMainCode:" + orderMainCode
          + ",orderMainStatus:" + orderMainStatus);
   
      result = orderChangeStatusRemoteService.changeOrderStatus(userAccount, orderMainCode,
          (long) (OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()), null,null);
      logger.info("调用订单系统订单取消接口结束!");
 

    } catch (Exception e) {
      logger.error("调用订单系统的取消订单接口异常：" + e.getMessage(), e);
      throw e;
    }
    return result;
  }

}
