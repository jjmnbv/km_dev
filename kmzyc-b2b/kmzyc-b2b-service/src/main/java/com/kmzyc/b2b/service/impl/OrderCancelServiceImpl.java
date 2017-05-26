package com.kmzyc.b2b.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tempuri.IMemberInfo;

import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.OrderCancelService;

import javax.annotation.Resource;

@Service
public class OrderCancelServiceImpl implements OrderCancelService {
  //private static Logger logger = Logger.getLogger(OrderCancelServiceImpl.class);
  private static Logger logger = LoggerFactory.getLogger(OrderCancelServiceImpl.class);
  @Resource(name = "loginServiceImp")
  private LoginService loginService;

  /**
   * ws返回值 /// 处理成功返回1 /// 0：系统异常 /// -1：订单不存在 /// -2：订单未支付 /// -3：订单已结算,不能取消 /// -4：订单删除失败
   */
  @Override
  public int cancelOrder(String orderCode, String cancelReason, String ordertype,
      int TLOrderAmount, String TLOrderDatetime) {
    String result = null;
    try {
      if (orderCode == null) return -1;
      IMemberInfo im = loginService.findWebservice();
      result = im.orderCancel(orderCode, cancelReason, ordertype, TLOrderAmount, TLOrderDatetime);
      switch (Integer.parseInt(result)) {
        case 1:
          logger.info("取消订单webservice调用成功！");
          break;
        case 0:
          logger.info("系统异常!");
          break;
        case -1:
          logger.info("订单不存在!");
          break;
        case -2:
          logger.info("订单未支付!");
          break;
        case -3:
          logger.info("订单已结算,不能取消!");
          break;
        case -4:
          logger.info("订单删除失败!");
          break;
        default:
          break;
      }
    } catch (Exception e) {
      logger.error("webservice取消订单调用异常！" + e);
    }
    return Integer.parseInt(result);
  }

}
