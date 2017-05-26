package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.user.remote.service.TrationListRemoteService;
import com.pltfm.app.dao.OrderAlterPayStatementDAO;
import com.pltfm.app.entities.OrderAlterPayStatement;
import com.pltfm.app.entities.OrderAlterPayStatementExample;
import com.pltfm.app.service.OrderAlterOperateStatementService;
import com.pltfm.app.service.OrderAlterPayStatementService;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.OrderPayStatementVo;
import com.pltfm.sys.util.ErrorCode;


@SuppressWarnings("unchecked")
@Service("orderAlterPayStatementService")
@Scope("singleton")
public class OrderAlterPayStatementServiceImpl extends BaseService
    implements
      OrderAlterPayStatementService {
  private static Logger logger = Logger.getLogger(OrderAlterPayStatementServiceImpl.class);

  @Resource
  OrderAlterPayStatementDAO orderAlterPayStatementDAO;
  @Resource
  OrderAlterOperateStatementService orderAlterOperateStatementService;
  // 客户系统的修改账户余额的接口服务类
  @Autowired
  private TrationListRemoteService trationListRemoteService;


  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int pay(String operator, Long paymentWay, String account, String alterCode,
      BigDecimal orderMoney, String outsidePayStatementNo, Long flag, BigDecimal preferentialNo,
      int status, Long state) throws ServiceException {
    Date now = new Date();// 操作时间
    Date endDate = null;// 退款完成时间
    if (flag.intValue() == OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()) {// 退款
      if (status == OrderAlterDictionaryEnum.Propose_Status.F_Backpay.getKey()
          || status == OrderAlterDictionaryEnum.Propose_Status.Stockout.getKey()) {// 前置条件：已通过质检
   
     /*删除预备金业务   if (paymentWay.intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.Reserve.getKey()
            && orderMoney.compareTo(BigDecimal.ZERO) != 0) {
          // 预备金退回
          save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
              outsidePayStatementNo, flag, preferentialNo);
          try {
            int result =
                trationListRemoteService.updateReserver(account, orderMoney.doubleValue(), alterCode, "支付内容:退款到预备金账户",
                    13, 1);
            if (1 != result) {
              return result;
            }
          } catch (Exception e) {
            logger.error("退换货退款到预备金时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "退换货退款到预备金时发生异常："
                + e.getMessage());
          }
        }*/ if (paymentWay.intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.Balance
            .getKey()
            || paymentWay.intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.AlipayToBalance
                .getKey()) {
          // 余额退回
          save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
              outsidePayStatementNo, flag, preferentialNo);
          try {
            int result =
                trationListRemoteService.orderTrationAccout(account, orderMoney.doubleValue(), alterCode, "支付内容", flag
                    .intValue());
            if (1 != result) {
              return result;
            }
          } catch (Exception e) {
            logger.error("退换货退款到余额时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "退换货退款到余额时发生异常："
                + e.getMessage());
          }
        } else if (paymentWay.intValue() == OrderDictionaryEnum.OrderPayMethod.Platform.getKey()) {
          // 支付成功
          save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
              outsidePayStatementNo, flag, preferentialNo);

        } else if (paymentWay.intValue() == OrderDictionaryEnum.OrderPayMethod.Platform.getKey()) {
          // 支付成功
          save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
              outsidePayStatementNo, flag, preferentialNo);

        } else if (paymentWay.intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.TenPay
            .getKey()) {
          // 财付通支付成功
          save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
              outsidePayStatementNo, flag, preferentialNo);
        } else if (paymentWay.intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.AliPay
            .getKey()) {
          // 支付宝支付成功
          save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
              outsidePayStatementNo, flag, preferentialNo);
        } else if (paymentWay.intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.YeePay
            .getKey()) {
          // 支付成功
          save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
              outsidePayStatementNo, flag, preferentialNo);
        } else if (paymentWay.intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.WeiXin
            .getKey()) {
          // 支付成功
          save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
              outsidePayStatementNo, flag, preferentialNo);
        } else if (orderMoney.compareTo(BigDecimal.ZERO) != 0) {
          save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
              outsidePayStatementNo, flag, preferentialNo);
          // 退款方式不支持退换货状态:-2,已驳回;-1:已取消;1:已提交待审核;2:已通过待退货;3:已退货待取件;4:已取件待质检;51:已通过待退款;52:已通过待发货;53:已驳回待原件返回;54:换货转退货:61:已退款待确认;62:已发货待签收;63:已原件返回待签收;7:已完成
          // return -3;
        }
      } else {
        // 退换货单此状态下不能退款
        return -2;
      }
    } else {
      // 未知请求
      return -1;
    }
    return 1;
  }
  
  //预售
  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int pay(String operator, Long paymentWay, String account, String alterCode,
      BigDecimal orderMoney, String outsidePayStatementNo, Long flag, BigDecimal preferentialNo,
      int status, Long state,String ysFlag) throws ServiceException {
    Date now = new Date();// 操作时间
    Date endDate = null;// 退款完成时间
    if (flag.intValue() == OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()) {// 退款
      if (status == OrderAlterDictionaryEnum.Propose_Status.F_Backpay.getKey()) {
        
        if (paymentWay.intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.Balance
            .getKey()) {
          // -------余额退回
          int result=-1;
          try {
            result =
                trationListRemoteService.orderTrationAccout(account, orderMoney.doubleValue(), alterCode, "支付内容", flag
                    .intValue());
            if (1 != result) {
              return result;
            }
          } catch (Exception e) {
            logger.error("退换货退款到余额时发生异常", e);
            /*throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "退换货退款到余额时发生异常："
                + e.getMessage());*/
            result =-1;
            return result;
          }
          if(result ==1)
            save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
                outsidePayStatementNo, flag, preferentialNo,ysFlag);
        } else if (paymentWay.intValue() == 12) {
          // 康美通支付成功
          save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
              outsidePayStatementNo, flag, preferentialNo,ysFlag);

        }else if (paymentWay.intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.AliPay
            .getKey()) {
          // 支付宝支付成功
          save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
              outsidePayStatementNo, flag, preferentialNo,ysFlag);
        } else if (orderMoney.compareTo(BigDecimal.ZERO) != 0) {
          save(paymentWay, state, account, alterCode + "", orderMoney.negate(), now, state==OrderDictionaryEnum.OrderPayState.Success.getKey()?new Date():endDate,
              outsidePayStatementNo, flag, preferentialNo,ysFlag);
          // 退款方式不支持退换货状态:-2,已驳回;-1:已取消;1:已提交待审核;2:已通过待退货;3:已退货待取件;4:已取件待质检;51:已通过待退款;52:已通过待发货;53:已驳回待原件返回;54:换货转退货:61:已退款待确认;62:已发货待签收;63:已原件返回待签收;7:已完成
          // return -3;
        }
      } else {
        // 退换货单此状态下不能退款
        return -2;
      }
    } else {
      // 未知请求
      return -1;
    }
    return 1;
  }
  

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int pay(OrderPayStatementVo vo) throws ServiceException {
    Date now = new Date();// 操作时间
    vo.setCreateDate(now);
    if (vo.getFlag().intValue() == OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()) {// 退款
      if (vo.getStatus() == OrderAlterDictionaryEnum.Propose_Status.F_Backpay.getKey()
          || vo.getStatus() == OrderAlterDictionaryEnum.Propose_Status.Stockout.getKey()) {// 前置条件：已通过质检
     
      /*删除预备金业务  if (vo.getPaymentWay().intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.Reserve
            .getKey()) {
          // 预备金退回
          save(vo);
          try {
            int result =
                trationListRemoteService.updateReserver(vo.getAccount(), vo.getOrderMoney().doubleValue(), vo
                    .getAlterCode(), "支付内容:退款到预备金账户", 13, 1);
            if (1 != result) {
              return result;
            }
          } catch (Exception e) {
            logger.error("退换货退款到预备金时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "退换货退款到预备金时发生异常："
                + e.getMessage());
          }
        } else*/ if (vo.getPaymentWay().intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.Balance
            .getKey()
            || vo.getPaymentWay().intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.AlipayToBalance
                .getKey()) {
          // 余额退回
          save(vo);
          try {
            int result =
                trationListRemoteService.orderTrationAccout(vo.getAccount(), vo.getOrderMoney().doubleValue(), vo
                    .getAlterCode(), "支付内容", vo.getFlag().intValue());
            if (1 != result) {
              return result;
            }
          } catch (Exception e) {
            logger.error("退换货退款到余额时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "退换货退款到余额时发生异常："
                + e.getMessage());
          }
        } else if (vo.getPaymentWay().intValue() == OrderDictionaryEnum.OrderPayMethod.Platform
            .getKey()) {
          // 支付成功
          save(vo);

        } else if (vo.getPaymentWay().intValue() == OrderDictionaryEnum.OrderPayMethod.Platform
            .getKey()) {
          // 支付成功
          save(vo);

        } else if (vo.getPaymentWay().intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.TenPay
            .getKey()) {
          // 财付通支付成功
          save(vo);
        } else if (vo.getPaymentWay().intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.AliPay
            .getKey()) {
          // 支付宝支付成功
          save(vo);
        } else if (vo.getPaymentWay().intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.YeePay
            .getKey()) {
          // 支付成功
          save(vo);
        } else {
          // 退款方式不支持退换货状态:-2,已驳回;-1:已取消;1:已提交待审核;2:已通过待退货;3:已退货待取件;4:已取件待质检;51:已通过待退款;52:已通过待发货;53:已驳回待原件返回;54:换货转退货:61:已退款待确认;62:已发货待签收;63:已原件返回待签收;7:已完成
          return -3;
        }
      } else {
        // 退换货单此状态下不能退款
        return -2;
      }
    } else {
      // 未知请求
      return -1;
    }
    return 1;
  }

  /**
   * 记录退换货单支付流水 参数：支付方式、支付状态、客户帐号、退换货单号、付款金额、生成时间、支付完成时间、第三方支付流水号、付款/退款标识、优惠券编号
   */
  @Override
public void save(Long paymentWay, Long state, String account, String orderAlterCode,
      BigDecimal orderMoney, Date createDate, Date endDate, String outsidePayStatementNo,
      Long flag, BigDecimal preferentialNo) throws ServiceException {
    try {
      if (orderMoney.compareTo(BigDecimal.ZERO) == 0) {
        return;
      }
      if (state == OrderDictionaryEnum.OrderPayState.Ready.getKey()) {
        OrderAlterPayStatementExample example = new OrderAlterPayStatementExample();
        OrderAlterPayStatementExample.Criteria criteria = example.createCriteria();
        criteria.andOrderAlterCodeEqualTo(orderAlterCode);
        criteria.andStateEqualTo(state);
        criteria.andFlagEqualTo(flag);
        List list = orderAlterPayStatementDAO.selectByExample(example);
        if (null == list || 0 == list.size()) {
          orderAlterPayStatementDAO.insert(newOrderPay(paymentWay, state, account, orderAlterCode,
              orderMoney, createDate, endDate, outsidePayStatementNo, flag, preferentialNo));
        }
      } else {
        orderAlterPayStatementDAO.insert(newOrderPay(paymentWay, state, account, orderAlterCode,
            orderMoney, createDate, endDate, outsidePayStatementNo, flag, preferentialNo));
      }
    } catch (SQLException e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_RETURNS_ERROR, "记录退换货单支付流水时发生异常："
          + e.getMessage());
    }
  }
  
  /**
   * 预售----记录退换货单支付流水 参数：增加预售标识
   */
  public void save(Long paymentWay, Long state, String account, String orderAlterCode,
      BigDecimal orderMoney, Date createDate, Date endDate, String outsidePayStatementNo,
      Long flag, BigDecimal preferentialNo,String ysFlag) throws ServiceException {
    try {
      if (orderMoney.compareTo(BigDecimal.ZERO) == 0) {
        return;
      }
      if (state == OrderDictionaryEnum.OrderPayState.Ready.getKey()) {
        /* OrderAlterPayStatementExample example = new OrderAlterPayStatementExample();
        OrderAlterPayStatementExample.Criteria criteria = example.createCriteria();
        criteria.andOrderAlterCodeEqualTo(orderAlterCode);
        criteria.andStateEqualTo(state);
        criteria.andFlagEqualTo(flag);
        criteria.andYsflagEqualTo(ysFlag);
        List list = orderAlterPayStatementDAO.selectByExample(example); */        
        Map queryMap = new HashMap();
        queryMap.put("orderAlterCode", orderAlterCode);
        queryMap.put("state", state);
        queryMap.put("flag", flag);
        queryMap.put("ysFlag", ysFlag);
        List list = orderAlterPayStatementDAO.selectForYs(queryMap);
        
        if (null == list || 0 == list.size()) {
          orderAlterPayStatementDAO.insert(newOrderPay(paymentWay, state, account, orderAlterCode,
              orderMoney, createDate, endDate, outsidePayStatementNo, flag, preferentialNo,ysFlag));
        }
      } else {
        orderAlterPayStatementDAO.insert(newOrderPay(paymentWay, state, account, orderAlterCode,
            orderMoney, createDate, endDate, outsidePayStatementNo, flag, preferentialNo,ysFlag));
      }
    } catch (SQLException e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_RETURNS_ERROR, "保存退换货支付流水表时发生异常："
          + e.getMessage());
    }
  }

  @Override
  public void save(OrderPayStatementVo vo) throws ServiceException {
    try {
      if (vo.getOrderMoney().compareTo(BigDecimal.ZERO) == 0) {
        return;
      }
      if (vo.getState() == OrderDictionaryEnum.OrderPayState.Ready.getKey()) {
        OrderAlterPayStatementExample example = new OrderAlterPayStatementExample();
        OrderAlterPayStatementExample.Criteria criteria = example.createCriteria();
        criteria.andOrderAlterCodeEqualTo(vo.getAlterCode());
        criteria.andStateEqualTo(vo.getState());
        criteria.andFlagEqualTo(vo.getFlag());
        List list = orderAlterPayStatementDAO.selectByExample(example);
        if (null == list || 0 == list.size()) {
          orderAlterPayStatementDAO.insert(newOrderPay(vo));
        }
      } else {
        orderAlterPayStatementDAO.insert(newOrderPay(vo));
      }
    } catch (SQLException e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_RETURNS_ERROR, "记录退换货单支付流水时发生异常："
          + e.getMessage());
    }
  }

  private OrderAlterPayStatement newOrderPay(OrderPayStatementVo vo) {
    OrderAlterPayStatement newRecord = new OrderAlterPayStatement();
    newRecord.setAccount(vo.getAccount());
    newRecord.setCreateDate(vo.getCreateDate());
    newRecord.setEndDate(vo.getEndDate());
    newRecord.setFlag(vo.getFlag());
    newRecord.setOrderAlterCode(vo.getAlterCode());
    newRecord.setOrderMoney(vo.getOrderMoney().compareTo(BigDecimal.ZERO)==1?vo.getOrderMoney().multiply(new BigDecimal(-1)):vo.getOrderMoney());
    newRecord.setOutsidePayStatementNo(vo.getOutsidePayStatementNo());
    newRecord.setPaymentWay(vo.getPaymentWay());
    newRecord.setPreferentialNo(vo.getPreferentialNo());
    newRecord.setState(vo.getState());
    return newRecord;
  }

  private OrderAlterPayStatement newOrderPay(Long paymentWay, Long state, String account,
      String orderAlterCode, BigDecimal orderMoney, Date createDate, Date endDate,
      String outsidePayStatementNo, Long flag, BigDecimal preferentialNo) {
    OrderAlterPayStatement newRecord = new OrderAlterPayStatement();
    newRecord.setAccount(account);
    newRecord.setCreateDate(createDate);
    newRecord.setEndDate(endDate);
    newRecord.setFlag(flag);
    newRecord.setOrderAlterCode(orderAlterCode);
    newRecord.setOrderMoney(orderMoney.compareTo(BigDecimal.ZERO)==1?orderMoney.multiply(new BigDecimal(-1)):orderMoney);
    newRecord.setOutsidePayStatementNo(outsidePayStatementNo);
    newRecord.setPaymentWay(paymentWay);
    newRecord.setPreferentialNo(preferentialNo);
    newRecord.setState(state);
    return newRecord;
  }

  private OrderAlterPayStatement newOrderPay(Long paymentWay, Long state, String account,
      String orderAlterCode, BigDecimal orderMoney, Date createDate, Date endDate,
      String outsidePayStatementNo, Long flag, BigDecimal preferentialNo,String ysFlag) {
    OrderAlterPayStatement newRecord = new OrderAlterPayStatement();
    newRecord.setAccount(account);
    newRecord.setCreateDate(createDate);
    newRecord.setEndDate(endDate);
    newRecord.setFlag(flag);
    newRecord.setOrderAlterCode(orderAlterCode);
    newRecord.setOrderMoney(orderMoney.compareTo(BigDecimal.ZERO) == 1
        ? orderMoney.multiply(new BigDecimal(-1))
        : orderMoney);
    newRecord.setOutsidePayStatementNo(outsidePayStatementNo);
    newRecord.setPaymentWay(paymentWay);
    newRecord.setPreferentialNo(preferentialNo);
    newRecord.setState(state);
    newRecord.setYsFlag(ysFlag);
    return newRecord;
  }
  
  @Override
public BigDecimal needToRefund(Map<String, Object> map) throws ServiceException {
    try {
      return this.orderAlterPayStatementDAO.needToRefund(map);
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_RETURNS_ERROR, "查询预备金还需要退还金额异常："
          + e.getMessage());
    }
  }

}
