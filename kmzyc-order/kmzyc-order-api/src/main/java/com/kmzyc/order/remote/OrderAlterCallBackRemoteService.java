package com.kmzyc.order.remote;

import java.io.Serializable;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;

/**
 * 各种回调退换货系统的服务类接口
 * 
 * @author zengming
 * 
 */
public interface OrderAlterCallBackRemoteService extends Serializable {

  /**
   * 供产品系统调用，在填写完第三方物流的运单号之后触发
   * 
   * @param logisticsOrderNo 第三方物流的运单号
   * @param logisticsCode 第三方物流公司代码
   * @param logisticsName 第三方物流公司名称
   * @param orderAlterCode 退换货编码
   * @return 第三方物流的运单号
   * @throws ServiceException
   */
  public String getLogisticNumber(LogisticAndDistributionInfoVO logisticAndDistributionInfoVO)
      throws ServiceException;
}
