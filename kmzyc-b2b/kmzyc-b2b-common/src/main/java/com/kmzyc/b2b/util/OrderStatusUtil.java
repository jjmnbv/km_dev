package com.kmzyc.b2b.util;

import java.util.Map;

import com.pltfm.app.util.OrderDictionaryEnum;

public class OrderStatusUtil {

    /**
     * 解析并组装订单状态查询条件
     */
    public static void parseOrderStatus(int orderStatusFlag, Map<String, Object> newConditon) {
        String orderStatus = null;
        switch (orderStatusFlag) {
            case -1: // 待收货
                orderStatus =
                        String.valueOf(OrderDictionaryEnum.Order_Status.Settle_Done.getKey()) +
                                "," + OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey() +
                                "," + OrderDictionaryEnum.Order_Status.Splited_Not_Settle.getKey() +
                                "," + OrderDictionaryEnum.Order_Status.Stock_Done.getKey() + "," +
                                String.valueOf(OrderDictionaryEnum.Order_Status.Ship_Done.getKey());
                break;
            case 0: // 全部状态
                break;
            case 1: // 未付款
                orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Not_Pay.getKey());
                break;
            case 2: // 已付款
                orderStatus =
                        String.valueOf(OrderDictionaryEnum.Order_Status.Pay_Done.getKey()) + ","

                                + String.valueOf(
                                OrderDictionaryEnum.Order_Status.Stock_Lock.getKey()) + "," +
                                String.valueOf(
                                        OrderDictionaryEnum.Order_Status.Risk_Appraise.getKey()) +
                                "," +
                                String.valueOf(OrderDictionaryEnum.Order_Status.Risk_Pass.getKey());
                break;
            case 3: // 配货中
                orderStatus =
                        String.valueOf(OrderDictionaryEnum.Order_Status.Settle_Done.getKey()) +
                                "," + OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey() +
                                "," + OrderDictionaryEnum.Order_Status.Splited_Not_Settle.getKey() +
                                "," + OrderDictionaryEnum.Order_Status.Stock_Done.getKey();
                break;
            case 4: // 已发货
                orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Ship_Done.getKey());
                break;
            case 5: // 已完成
                orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Order_Done.getKey());
                break;
            case 7: // 已取消
                orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Cancel_Done.getKey());
                break;
     /* case 8: // 已取消
        orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Cancel_Checking.getKey());
        break;*/
            default:
                break;
        }
        if (null != orderStatus) {
            newConditon.put("orderStatus", orderStatus);
        }
    }


    /**
     * 解析并组装订单状态查询条件
     */
    public static void parseWapOrderStatus(int orderStatusFlag, Map<String, Object> newConditon) {
        String orderStatus = null;
        switch (orderStatusFlag) {
            case -1: // 待收货
                orderStatus =
                        String.valueOf(OrderDictionaryEnum.Order_Status.Settle_Done.getKey()) +
                                "," + OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey() +
                                "," + OrderDictionaryEnum.Order_Status.Splited_Not_Settle.getKey() +
                                "," + OrderDictionaryEnum.Order_Status.Stock_Done.getKey() + "," +
                                String.valueOf(OrderDictionaryEnum.Order_Status.Ship_Done.getKey());
                break;
            case 0: // 全部状态
                break;
            case 1: // 未付款
                orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Not_Pay.getKey());
                break;
            case 2: // 已付款
                orderStatus =
                        String.valueOf(OrderDictionaryEnum.Order_Status.Pay_Done.getKey()) + "," +
                                String.valueOf(
                                        OrderDictionaryEnum.Order_Status.Settle_Done.getKey()) +
                                "," + String.valueOf(
                                OrderDictionaryEnum.Order_Status.Stock_Done.getKey()) + "," +
                                String.valueOf(OrderDictionaryEnum.Order_Status.Settle_Not_Stock
                                        .getKey()) + ","

                                + String.valueOf(
                                OrderDictionaryEnum.Order_Status.Stock_Lock.getKey()) + "," +
                                String.valueOf(
                                        OrderDictionaryEnum.Order_Status.Risk_Appraise.getKey()) +
                                "," +
                                String.valueOf(OrderDictionaryEnum.Order_Status.Risk_Pass.getKey());
                break;
            case 3: // 配货中
                orderStatus =
                        String.valueOf(OrderDictionaryEnum.Order_Status.Settle_Done.getKey()) +
                                "," + OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey() +
                                "," + OrderDictionaryEnum.Order_Status.Splited_Not_Settle.getKey() +
                                "," + OrderDictionaryEnum.Order_Status.Stock_Done.getKey();
                break;
            case 4: // 已发货
                orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Ship_Done.getKey());
                break;
            case 5: // 已完成
                orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Order_Done.getKey());
                break;
            case 7: // 已取消
                orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Cancel_Done.getKey());
                break;
     /* case 8: // 已取消
        orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Cancel_Checking.getKey());
        break;*/
            default:
                break;
        }
        if (null != orderStatus) {
            newConditon.put("orderStatus", orderStatus);
        }
    }

    /**
     * 解析并组装订单状态查询条件APP
     */
    public static void parseAppOrderStatus(int orderStatusFlag, Map<String, Object> newConditon) {
        String orderStatus = null;
        switch (orderStatusFlag) {
            case 1: // 全部状态
                break;
            case 2: // 未付款
                orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Not_Pay.getKey());
                break;
            case 3: // 待发货
                orderStatus = OrderDictionaryEnum.Order_Status.Pay_Done.getKey() + "," +
                        OrderDictionaryEnum.Order_Status.Settle_Done.getKey() + "," +
                        OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey() + "," +
                        OrderDictionaryEnum.Order_Status.Splited_Not_Settle.getKey() + "," +
                        OrderDictionaryEnum.Order_Status.Stock_Done.getKey() + "," +
                        OrderDictionaryEnum.Order_Status.Stock_Lock.getKey() + "," +
                        OrderDictionaryEnum.Order_Status.Risk_Appraise.getKey() + "," +
                        OrderDictionaryEnum.Order_Status.Risk_Pass.getKey();
                break;
            case 4: // 待收货
                orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Ship_Done.getKey());
                break;
            case 5: // 待评价
                orderStatus = String.valueOf(OrderDictionaryEnum.Order_Status.Order_Done.getKey());
                // 评价状态 1未评价2已评价3已追加评价
                newConditon.put("assessStatus", 1);// App添加
                break;
        }
        if (null != orderStatus) {
            newConditon.put("orderStatus", orderStatus);
        }
    }


}
