package com.kmzyc.order.remote;

import java.io.Serializable;
import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;

/**
 * 各种回调订单系统的服务类接口
 * 
 * @author zengming
 * 
 */
public interface OrderCallBackRemoteService extends Serializable {

  /**
   * 供产品系统调用，在填写完第三方物流的运单号之后触发
   * 
   * @param logisticAndDistributionInfoVO
   * @return
   * @throws ServiceException
   */
  public String getLogisticNumber(LogisticAndDistributionInfoVO logisticAndDistributionInfoVO)
      throws ServiceException;

  /**
   * 供供应商后台调用，在填写完第三方物流的运单号之后触发
   * 
   * @param logisticAndDistributionInfoVO
   * @return
   * @throws ServiceException
   */
  public String getLogisticNumber4Supplier(
      LogisticAndDistributionInfoVO logisticAndDistributionInfoVO) throws ServiceException;

  /**
   * 批量插入支付流水，用于在通过银行/支付平台支付时，先录入一条支付流水 如果发生掉单的情况，可以追溯到该支付流水信息
   * 
   * @param list
   * @return
   * @throws ServiceException
   */
  public int insertPayStatement(List<OrderPayStatement> list) throws ServiceException;

  /**
   * 获取订单物流信息修改次数
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public Integer queryUpdateLogisticCount(String orderCode) throws ServiceException;
}
