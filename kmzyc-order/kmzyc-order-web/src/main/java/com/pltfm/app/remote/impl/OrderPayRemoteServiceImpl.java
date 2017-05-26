package com.pltfm.app.remote.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderPayRemoteService;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dao.OrderOperateStatementDAO;
import com.pltfm.app.dao.OrderPayStatementDAO;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.PaymentVO;
import com.pltfm.sys.util.ErrorCode;

@Service("orderPayRemoteService")
@SuppressWarnings("unchecked")
public class OrderPayRemoteServiceImpl implements OrderPayRemoteService {
  private static final long serialVersionUID = -400403318478609260L;
  @Resource
  OrderPayStatementService orderPayStatementService;
  @Resource
  OrderPayStatementDAO orderPayStatementDAO;
  @Resource
  OrderMainDAO orderMainDAO;
  @Resource
  OrderOperateStatementDAO orderOperateStatementDAO;

  // @Override
  // public int pay(String operator, Long paymentWay, String account,
  // String orderCode, BigDecimal orderMoney,
  // String outsidePayStatementNo, Long flag, BigDecimal preferentialNo,
  // String bankName, String platFormName,String bankCode, String
  // platFormCode)
  // throws ServiceException {
  // return orderPayStatementService.pay(operator, paymentWay, account,
  // orderCode,
  // orderMoney, outsidePayStatementNo, flag, preferentialNo,
  // bankName, platFormName, bankCode, platFormCode);
  // }

  @Override
  public BigDecimal getOrderPay(Map map) throws ServiceException {
    BigDecimal bd = null;
    try {
      bd = orderPayStatementService.getOrderPay(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_PAYMENT_CALC_ERROR, "获取支付总额发生错误：" + e.getMessage());
    }
    return bd;
  }

  @Override
  public BigDecimal getNotPay(String orderCode) throws ServiceException {
    BigDecimal bd = null;
    try {
      bd = orderPayStatementService.getNotPay(orderCode);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_PAYMENT_CALC_ERROR, "获取未付款金额发生错误："
          + e.getMessage());
    }
    return bd;
  }

  @Override
  public int batchPay(PaymentVO paymentVO) throws ServiceException {
    int result = 0;
    try {
      if (1 == orderPayStatementService.batchPay(paymentVO)) {
        result = orderPayStatementService.lockStock(paymentVO);
      }
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_PAYMENT_CALC_ERROR, "批量处理支付发生错误：" + e.getMessage());
    }
    return result;
  }

  @Override
  public int savePayStatement(List<OrderPayStatement> paymentVOList) throws ServiceException {
    int result = 0;
    try {
      result = orderPayStatementService.savePayStatement(paymentVOList);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_PAYMENT_ASSEMBLY_ERROR, "批量保存订单支付流水发生错误："
          + e.getMessage());
    }
    return result;
  }

  @Override
  public int orderRemotePayForYsDeposit(PaymentVO paymentVO) throws ServiceException {
    List<OrderPayStatement> orderPayStatementList = paymentVO.getOrderPayStatementList();
    OrderPayStatement orderPayStatement = orderPayStatementList.get(0);//预售订单支付定金回调时有且只有一条支付流水
  // 获取订单编码
    String orderCode = paymentVO.getOrderCode();
 // 获取客户账户
    String account = paymentVO.getAccount();
    // 实例化当前时间
    Date now = new Date();
    orderPayStatement.setCreateDate(now);
    try {
      /**1.添加支付流水**/
      orderPayStatementDAO.insertList(orderPayStatementList);
      
      /**2.修改订单状态为待付尾款**/
      OrderMain om = orderMainDAO.selectByOrderCode(orderCode);
      if (null == om) {
        throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "订单不存在");
      }
      long status = OrderDictionaryEnum.Order_Status.Nopay_FinalMoney.getKey();//待支付尾款
      om.setOrderStatus(status);
      orderMainDAO.updateByOrderCode(om);
      
      /**3添加操作流水**/
      OrderOperateStatement oosPrevious = new OrderOperateStatement();
   // 找到该订单号最近的记录操作流水
      oosPrevious = orderOperateStatementDAO.selectNewest(orderCode);
      OrderOperateStatement oosNow = new OrderOperateStatement();
    //如果是预售订单，把操作流水中的订单状态改为待付尾款
      Long nowOrderStatus =0l;
      if(om.getOrderType()==7){
    	  nowOrderStatus = new Long(OrderDictionaryEnum.Order_Status.Nopay_FinalMoney.getKey());
      }else{
    	  nowOrderStatus = new Long(OrderDictionaryEnum.Order_Status.Not_Pay.getKey());
      }
   //如果最近一次操作是支付操作，则只需要修改当前操作时间即可
      if (oosPrevious != null) {
        // comment = "您的订单"+orderCode+"已经支付成功，正在安排配送；";
        if (OrderDictionaryEnum.OrderOperateType.Pay.getKey() == oosPrevious.getNowOperateType()
            .intValue()) {
          BeanUtils.copyProperties(oosPrevious, oosNow);
          oosNow.setNowOperateDate(new Date());
        } else {
          oosNow.setNowOperateDate(now);
          oosNow.setNowOperateType(2L);//支付
          oosNow.setNowOperator(account);
          // 未支付的订单状态
          
          oosNow.setNowOrderStatus(nowOrderStatus);
          //总金额改为支付的金额(已注释)
          //oosNow.setNowOrderSum(paymentVO.getOrderMoney());
          oosNow.setNowOrderSum(oosPrevious.getNowOrderSum());
          oosNow.setOrderCode(orderCode);
          oosNow.setPreviousOperateDate(oosPrevious.getNowOperateDate());
          oosNow.setPreviousOperateType(oosPrevious.getNowOperateType());
          oosNow.setPreviousOperator(oosPrevious.getNowOperator());
          oosNow.setPreviousOrderStatus(oosPrevious.getNowOrderStatus());
          oosNow.setPreviousOrderSum(oosPrevious.getPreviousOrderSum());
        }
      }
      String comment = "您的订单" + orderCode + "已经成功支付定金";
      oosNow.setOperateInfo(comment);
      oosNow.setNowOrderStatus(nowOrderStatus);
      orderOperateStatementDAO.insert(oosNow);
  
      
    } catch (SQLException e) {
      throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR, "预售订单支付定金回调方法中修改订单状态，添加支付，操作流水发生错误：" + e.getMessage());
    }
    return 0;
  }

}
