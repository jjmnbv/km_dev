package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.OrderItemDAO;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dao.OrderOperateStatementDAO;
import com.pltfm.app.dao.OrderPayStatementDAO;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.service.OrderAlterService;
import com.pltfm.app.util.OrderCodeUtil;
import com.pltfm.app.util.OrderDictionaryEnum;

@Service("orderAlterService")
public class OrderAlterServiceImpl implements OrderAlterService {

  @Resource
  private OrderMainDAO orderMainDAO;

  @Resource
  private OrderItemDAO orderItemDAO;

  @Resource
  private OrderOperateStatementDAO operateStatementDAO;

  @Resource
  private OrderPayStatementDAO orderPayStatementDAO;

  /**
   * 仓库拆单
   * 
   * @param orderList
   * @param orderItemMap
   * @param operator
   * @return
   * @throws ServiceException
   */
  @Override
  public void splitOrderByWarehouse(List<OrderMain> orderList,
      Map<String, List<OrderItem>> orderItemMap,
      Map<String, Map<Long, List<OrderItem>>> splitOrderCodes, String operator)
      throws ServiceException {
    try {
      if (null != orderList && !orderList.isEmpty() && null != orderItemMap
          && !orderItemMap.isEmpty() && null != splitOrderCodes && !splitOrderCodes.isEmpty()) {
        String orderCode = null;
        OrderMain newOrderMain = null;
        List<OrderItem> parentItem = null, wareOrderItem = null;// 父订单明细/订单明细
        Map<Long, List<OrderItem>> splitMapSum = null;// 某订单ordercode的拆单订单明细集合  map<仓库id,订单明细集合>
        Map<OrderMain, List<OrderItem>> splitMap = new HashMap<OrderMain, List<OrderItem>>();// 拆单后订单集合
        Map<String, BigDecimal> itemSum = null;// 子订单明细汇总
        List<OrderMain> newOrderList = new ArrayList<OrderMain>();
        int index = 0, len = 0;
        BigDecimal discount = BigDecimal.ZERO, payable = BigDecimal.ZERO;
        for (OrderMain om : orderList) {//循环订单，对订单分别进行拆单操作
          if (!splitOrderCodes.containsKey(om.getOrderCode())) {
            continue;
          }
          splitMap.clear();
          splitMapSum = splitOrderCodes.get(om.getOrderCode());//某订单ordercode下的集合  map<仓库id,订单明细集合>
          parentItem = orderItemMap.get(om.getOrderCode());//某订单原来的订单明细列表
          index = 0;
          len = splitMapSum.keySet().size();//某订单下商品所处的仓库个数, 一定会大于1
          discount = BigDecimal.ZERO;
          payable = BigDecimal.ZERO;
          BigDecimal fareTotal = BigDecimal.ZERO;//拆单时子订单运费累加
          BigDecimal invoiceMoneyTotal = BigDecimal.ZERO;//拆单时子订单运费累加
          for (Long wareId : splitMapSum.keySet()) {
            index++;
            wareOrderItem = splitMapSum.get(wareId);//取得该orercode下，某仓库id下的订单明细集合
            newOrderMain = (OrderMain) om.clone();
            orderCode = OrderCodeUtil.generateOrderCode();
            newOrderMain.setOrderId(null);
            newOrderMain.setOrderCode(orderCode);
            newOrderMain.setParentOrderCode(om.getOrderCode());
            newOrderMain.setOrderStatus((long) OrderDictionaryEnum.Order_Status.Splited_Not_Settle
                .getKey());// 已拆分未结转
            itemSum = calcOrderItemSum(orderCode, parentItem, wareOrderItem);
            newOrderMain.setCommoditySum(itemSum.get("commoditySum")); // 商品实际支付总额
            newOrderMain.setAmountPayable(itemSum.get("commoditySum"));//应付金额等于实际支付总额,修改者WJ
            newOrderMain.setOrderPv(itemSum.get("orderPV").floatValue()); // pv
            // orderMain.set(this.countOrderMainPvByOrderItems(orderItemListForWareHouse));//设置订单的收益
            if (index != len) {
              BigDecimal percent =
                  newOrderMain.getCommoditySum().divide(itemSum.get("pcommoditySum"), 4,
                      BigDecimal.ROUND_HALF_EVEN);// 子百分比,获取四位减少误差
              newOrderMain.setDiscountAmount(om.getDiscountAmount().multiply(percent).setScale(2,
                  BigDecimal.ROUND_HALF_EVEN));// 子优惠总额=优惠总额/(子商品总额/父商品总额)
              //应付金额等于实际支付总额,修改者WJ,此处不应该这样计算
              //newOrderMain.setAmountPayable(om.getAmountPayable().multiply(percent).setScale(2,
              //    BigDecimal.ROUND_HALF_EVEN));// 子总金额=总金额//(子商品总额/父商品总额) 
              discount=discount.add(newOrderMain.getDiscountAmount());
              payable=payable.add(newOrderMain.getAmountPayable());
              
              //运费拆分
              newOrderMain.setFare(om.getFare().multiply(percent).setScale(2, BigDecimal.ROUND_HALF_EVEN));
              fareTotal = fareTotal.add(newOrderMain.getFare());
              
              //发票金额拆单
              newOrderMain.setInvoiceMoney(om.getInvoiceMoney().multiply(percent).setScale(2, BigDecimal.ROUND_HALF_EVEN));
              invoiceMoneyTotal = invoiceMoneyTotal.add(newOrderMain.getInvoiceMoney());
            } else {
              newOrderMain.setDiscountAmount(om.getDiscountAmount().subtract(discount));
              //应付金额等于实际支付总额,修改者WJ,此处不应该这样计算
              //newOrderMain.setAmountPayable(om.getAmountPayable().subtract(payable));
              newOrderMain.setFare(om.getFare().subtract(fareTotal));
              newOrderMain.setInvoiceMoney(om.getInvoiceMoney().subtract(invoiceMoneyTotal));
            }
            orderItemMap.put(orderCode, wareOrderItem);//更新orderItemMap   wareOrderItem管理了新订单号
            newOrderList.add(newOrderMain);
            splitMap.put(newOrderMain, wareOrderItem);
          }
          om.setOrderStatus((long) OrderDictionaryEnum.Order_Status.Split_Done.getKey());// 已拆分
          saveSplitOrder(om, splitMap, operator);
        }
        orderList.addAll(newOrderList);//装载拆分后的订单的对象
      }
    } catch (Exception e) {
      throw new ServiceException(0, "按仓库id拆分订单时发生异常", e);
    }
  }

  /**
   * 
   * 拆分订单的订单明细表赋予新订单号;
   * 计算拆分订单明细的pv值，商品实际交易金额，用于赋值给拆分后的订单对象
   * 
   * @param orderCode 子订单号
   * @param parentItem 父订单明细
   * @param wareOrderItem 子订单明细
   * @return
   */
  private Map<String, BigDecimal> calcOrderItemSum(String orderCode, List<OrderItem> parentItem,
      List<OrderItem> wareOrderItem) {
    Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();
    BigDecimal pcommoditySum = BigDecimal.ZERO, commoditySum = BigDecimal.ZERO, orderPV =
        BigDecimal.ZERO, commodityNumber = BigDecimal.ZERO;// 父商品总额/商品总额/PV/数量
    for (OrderItem oi : wareOrderItem) {
      oi.setOrderCode(orderCode);
      commodityNumber = BigDecimal.valueOf(oi.getCommodityNumber());
      commoditySum = commoditySum.add(oi.getCommodityUnitPrice().multiply(commodityNumber));
      orderPV = orderPV.add(commodityNumber.multiply(BigDecimal.valueOf(oi.getCommodityPv())));
    }
    for (OrderItem oi : parentItem) {
      pcommoditySum =
          pcommoditySum.add(oi.getCommodityUnitPrice().multiply(
              BigDecimal.valueOf(oi.getCommodityNumber())));
    }
    result.put("commoditySum", commoditySum);
    result.put("orderPV", orderPV);
    result.put("pcommoditySum", pcommoditySum);
    return result;
  }

  /**
   * 订单拆分后对订单、订单明细、操作流水、支付流水做记录
   * 
   * @param Order2ItemMapGo
   * @param Order2ItemMapStop
   * @param orderMain
   * @return
   * @throws SQLException
   */
  private void saveSplitOrder(OrderMain orderMain, Map<OrderMain, List<OrderItem>> splitMap,
      String operator) throws ServiceException {
    List<OrderOperateStatement> operateList = new ArrayList<OrderOperateStatement>();
    List<OrderPayStatement> payList = new ArrayList<OrderPayStatement>();
    OrderOperateStatement operateStatement = null;
    OrderPayStatement payStatement = null;
    try {
      orderMainDAO.updateByPrimaryKey(orderMain);

      Date now = new Date();
      OrderOperateStatement previousOperateStatement =
          operateStatementDAO.selectLatestByPrimaryKey(orderMain.getOrderCode());
      operateStatement = new OrderOperateStatement();
      operateStatement.setOrderCode(orderMain.getOrderCode());
      operateStatement.setOperateInfo("您的订单已被拆分，后续订单进度请分别跟踪各子订单。");
      operateStatement.setNowOperateDate(now);
      operateStatement.setNowOperator(operator);
      operateStatement.setNowOperateType(new Long(OrderDictionaryEnum.OrderOperateType.Split
          .getKey()));
      operateStatement.setNowOrderStatus(new Long(OrderDictionaryEnum.Order_Status.Split_Done
          .getKey()));
      operateStatement.setNowOrderSum(orderMain.getAmountPayable());
      if (previousOperateStatement != null) {
        operateStatement.setPreviousOperateDate(previousOperateStatement.getNowOperateDate());
        operateStatement.setPreviousOperateType(previousOperateStatement.getNowOperateType());
        operateStatement.setPreviousOperator(previousOperateStatement.getNowOperator());
        operateStatement.setPreviousOrderStatus(previousOperateStatement.getNowOrderStatus());
        operateStatement.setPreviousOrderSum(previousOperateStatement.getNowOrderSum());
      }
      operateList.add(operateStatement);

      List<OrderItem> oiList = null;
      Long operateType = new Long(OrderDictionaryEnum.OrderOperateType.Create.getKey()), orderStatus =
          new Long(OrderDictionaryEnum.Order_Status.Settle_Done.getKey()), flag =
          new Long(OrderDictionaryEnum.OrderPayFlag.Payment.getKey());
      Long orderId = null;
      for (OrderMain om : splitMap.keySet()) {
        oiList = splitMap.get(om);
        orderId = orderMainDAO.insert(om);
        for (OrderItem oi : oiList) {
          oi.setOrderItemId(orderId);
        }
        orderItemDAO.insertList(oiList);

        operateStatement = new OrderOperateStatement();
        operateStatement.setOrderCode(om.getOrderCode());
        operateStatement.setOperateInfo("已从订单" + orderMain.getOrderCode() + "拆分生成子订单"
            + om.getOrderCode());
        operateStatement.setNowOperateDate(now);
        operateStatement.setNowOperator(operator);
        operateStatement.setNowOperateType(operateType);
        operateStatement.setNowOrderStatus(orderStatus);
        operateStatement.setNowOrderSum(om.getAmountPayable());
        operateList.add(operateStatement);

        payStatement = new OrderPayStatement();
        payStatement.setAccount(om.getCustomerAccount());
        payStatement.setCreateDate(now);
        payStatement.setEndDate(now);
        payStatement.setFlag(flag);
        payStatement.setOrderCode(om.getOrderCode());
        payStatement.setPaymentWay(om.getPayMethod());
        payList.add(payStatement);
      }
      operateStatementDAO.insertList(operateList);
      orderPayStatementDAO.insertList(payList);
    } catch (Exception e) {
      throw new ServiceException(0, "订单拆分后对订单、订单明细、操作流水、支付流水做记录时发生异常", e);
    }
  }
}
