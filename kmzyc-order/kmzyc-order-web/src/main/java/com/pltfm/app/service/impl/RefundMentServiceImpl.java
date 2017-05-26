package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.model.RefundResult;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.service.OrderAlterOperateStatementService;
import com.pltfm.app.service.OrderAlterPayStatementService;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.RefundMentService;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.SysConstants;
import com.pltfm.app.vobject.OrderAlterOperateStatementSaveVo;

@Service("refundMentService")
@Scope("singleton")
public class RefundMentServiceImpl implements RefundMentService {
  private static Logger logger = Logger.getLogger(RefundMentServiceImpl.class);

  @Resource
  OrderPayStatementService orderPayStatementService;
  @Resource
  OrderItemQryService orderItemQryService;
  @Resource
  OrderQryService orderQryService;
  @Resource
  private OrderAlterPayStatementService orderAlterPayStatementService;
  @Resource
  OrderOperateStatementService orderOperateStatementService;
  @Resource
  OrderAlterOperateStatementService orderAlterOperateStatementService;

  @Override
  public void cancelOrderReady(String operator, OrderMain om) throws ServiceException {
  // 作废
  }

  @Override
  public void refundMentReady(OrderMain om, OrderAlter oa) throws ServiceException {
  // 作废
  }

  private void saveOperateStatement(OrderAlter oa, long operateType, String comment)
      throws ServiceException {
    OrderAlterOperateStatementSaveVo vo = new OrderAlterOperateStatementSaveVo();
    vo.setOrderAlterCode(oa.getOrderAlterCode());
    vo.setOrderItemId(oa.getOrderItemId());
    vo.setStatus(oa.getProposeStatus().longValue());
    vo.setOperator(SysConstants.SYS);
    vo.setDate(new Date());
    vo.setType(operateType);
    vo.setOrderSum(oa.getRuturnSum());
    vo.setInfo(comment);
    orderAlterOperateStatementService.save(vo);
  }

  @Override
  public void refundMentDone(RefundResult refundResult) throws ServiceException {
    // 取消订单
    String code =
        refundResult.getRefundBatchNo().substring(8, refundResult.getRefundBatchNo().length() - 1);
    if (refundResult.getRefundBatchNo().indexOf("C") > 0) {
      OrderMain om = orderQryService.getOrderByCode(code);
      try {
        orderPayStatementService.pay(SysConstants.SYS,
            (long) OrderDictionaryEnum.OrderPayMethod.Platform.getKey(), om.getCustomerAccount(),
            om.getOrderCode(), new BigDecimal(refundResult.getR3_Amt()),
            refundResult.getR2_TrxId(),
            (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null, null, om
                .getOrderStatus(), (long) OrderDictionaryEnum.OrderPayState.Success.getKey(), null,
            null);
      } catch (Exception e) {
        logger.error("取消订单发生异常", e);
      }
      long operateType = OrderDictionaryEnum.OrderOperateType.Refund.getKey();
      String comment = "您的订单已经取消，订单中已支付部分金额已经返还到您的余额及银行账户。";
      om.setOrderStatus((long) OrderDictionaryEnum.Order_Status.Cancel_Done.getKey());
      orderOperateStatementService.updateOrderMain(om);
      // 记录操作流水
      orderOperateStatementService.save(om.getOrderCode(), null, (long) om.getOrderStatus(),
          SysConstants.SYS, new Date(), operateType, om.getAmountPayable(), comment);
    }
    // 退货单退款
    if (refundResult.getRefundBatchNo().indexOf("R") > 0) {
      OrderAlter oa = orderAlterOperateStatementService.getOrderAlterByCode(code);
      try {
        orderAlterPayStatementService.pay(SysConstants.SYS,
            (long) OrderDictionaryEnum.OrderPayMethod.Platform.getKey(), null, oa
                .getOrderAlterCode(), new BigDecimal(refundResult.getR3_Amt()), refundResult
                .getR2_TrxId(), (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null,
            oa.getProposeStatus(), (long) OrderDictionaryEnum.OrderPayState.Success.getKey());
      } catch (Exception e) {
        logger.error("退货单退款发生异常", e);
      }
      long operateType = OrderAlterDictionaryEnum.OrderAlterOperateType.PayBack.getKey();
      String comment = "完成退款";
      oa.setProposeStatus((short) OrderAlterDictionaryEnum.Propose_Status.F_Backpay.getKey());
      orderAlterOperateStatementService.updateOrderAlter(oa);
      saveOperateStatement(oa, operateType, comment);
    }
  }

}
