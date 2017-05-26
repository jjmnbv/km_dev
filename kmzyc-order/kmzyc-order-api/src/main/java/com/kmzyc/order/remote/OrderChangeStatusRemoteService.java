package com.kmzyc.order.remote;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.kmzyc.b2b.model.QueryResult;
import com.kmzyc.b2b.model.RefundResult;
import com.kmzyc.commons.exception.ServiceException;

/**
 * 订单更改状态接口
 * 
 * @author chenwei
 * @date 2013.09.10
 */
public interface OrderChangeStatusRemoteService extends Serializable {

  /**
   * 修改订单状态
   * @param QueryResult 第三方支付查询
   * @param operator 操作人
   * @param orderCode 订单号
   * @param status 订单状态
   * @param no 出库单/配送单/物流单/快递单号
   * @throws ServiceException 1 操作成功 -1 未知请求 -2 此状态下不能进行此操作 -3 不支持此退款方式 -4 调用远程接口异常 -5 数据查询异常
   */
  public int changeOrderStatus(String operator, String orderCode, Long status, BigDecimal no,QueryResult qr)
      throws ServiceException;

  /**
   * 删除、恢复订单
   * 
   * @param operator 操作人
   * @param orderCode 订单号
   * @param disabled 可见性（是否被删除） 1可见
   * @throws ServiceException 1 操作成功 -1 未知请求 -2 此状态下不能进行此操作
   */
  public int changeOrderDisabled(String operator, String orderCode, Long disabled)
      throws ServiceException;

  /**
   * 更改订单评价状态
   */
  public int changeOrderAssessStatus(String orderCode, Long asssessStatus) throws ServiceException;

  /**
   * 修改订单信息
   */
  public int changeOrderInfo(Map<String, String> map) throws ServiceException;

  /**
   * 补单
   */
  public int additional(String orderCode) throws ServiceException;

  /**
   * 处理标记
   */
  public int handle(String orderCode, Short state, String account, Date date)
      throws ServiceException;

  /**
   * 退款回调
   * 
   * @param rr
   * @throws ServiceException
   */
  public void refundCallBack(RefundResult rr) throws ServiceException;

}
