package com.pltfm.app.remote.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.model.QueryResult;
import com.kmzyc.b2b.model.RefundResult;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderChangeStatusRemoteService;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.sys.util.ErrorCode;

@Service("orderChangeStatusRemoteService")
public class OrderChangeStatusRemoteServiceImpl implements OrderChangeStatusRemoteService {
  private final static Logger logger = Logger.getLogger(OrderChangeStatusRemoteServiceImpl.class);

  private static final long serialVersionUID = -2795471693424890961L;
  
  @Resource
  private OrderOperateStatementService orderOperateStatementService;

  @Override
  public int changeOrderStatus(String operator, String orderCode, Long status, BigDecimal no,QueryResult qr)
      throws ServiceException {
    int result = 0;
    try {
        if(null != qr){
            orderOperateStatementService.additionalBeforeCancle(orderCode, status,qr);
        }
    } catch (Exception e) {
      logger.error(e);
    }
    try {
      result = orderOperateStatementService.changeOrderStatus(operator, orderCode, status, no);
    } catch (Exception e) {
        logger.error(e);
        throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_ORDER_ERROR, "修改订单" + orderCode
          + "状态发生错误：" + e.getMessage());
    }
    return result;
  }

  @Override
  public int changeOrderDisabled(String operator, String orderCode, Long disabled)
      throws ServiceException {
    int result = 0;
    try {
      result = orderOperateStatementService.changeOrderDisabled(operator, orderCode, disabled);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_ORDER_ERROR, (0 == disabled
          .compareTo(1L) ? "恢复" : "删除")
          + "订单" + orderCode + "发生错误：" + e.getMessage());
    }
    return result;
  }

  @Override
  public int changeOrderAssessStatus(String orderCode, Long asssessStatus) throws ServiceException {
    int result = 0;
    try {
      result = orderOperateStatementService.changeOrderAssessStatus(orderCode, asssessStatus);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_EVALUATE_ERROR, "修改订单评价"
          + orderCode + "发生错误：" + e.getMessage());
    }
    return result;
  }

  @Override
  public int changeOrderInfo(Map<String, String> map) throws ServiceException {
    int result = 0;
    try {
      result = orderOperateStatementService.changeOrderInfo(map);
    } catch (Exception e) {
      throw new ServiceException(0, "修改订单信息出错！");
    }
    return result;
  }

  @Override
  public int additional(String orderCode) throws ServiceException {
    int result = 0;
    try {
      result = orderOperateStatementService.additional(orderCode);
    } catch (Exception e) {
      throw new ServiceException(0, "修改订单信息出错！");
    }
    return result;
  }

  @Override
  public int handle(String orderCode, Short state, String account, Date date)
      throws ServiceException {
    int result = 0;
    try {
      result = orderOperateStatementService.handle(orderCode, state, account, date);
    } catch (Exception e) {
      throw new ServiceException(0, "记录订单处理标记出错！");
    }
    return result;
  }

  /**
   * 退款回调
   * 
   * @param rr
   * @throws ServiceException
   */
  @Override
public void refundCallBack(RefundResult rr) throws ServiceException {

  }
}
