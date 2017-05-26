package com.kmzyc.express.remote;

import java.io.Serializable;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;

public interface ExpressSubscriptionRemoteService extends Serializable {

  /**
   * 订阅订单的物流信息
   * 
   * 需要传入orderCode(订单编码),logisticsCode(物流公司编码),logisticsNo(物流单号)
   * channel(渠道(枚举B2B,B2C)),fromCity(起始城市),toCity(目的城市)
   * 
   * @param expressSubscription
   * @return 成功返回"sucess"
   * @throws ServiceException
   */
  String sucribeOrderExpressInfo(ExpressSubscription expressSubscription) throws ServiceException;

  /**
   * 根据物流公司编码，物流单号查询物流订阅信息
   * 
   * @param logisticsCode 物流公司编码
   * @param logisticsNo 物流单号
   * @return
   * @throws ServiceException
   */
  ExpressSubscription queryOrderExpressInfo(String logisticsCode, String logisticsNo)
      throws ServiceException;

}
