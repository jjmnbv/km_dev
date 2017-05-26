package com.pltfm.app.util;

public class QueryFields {

  // 列表页用
  public static final String[] TITLE_LIST =
      new String[] {"订单号", "收货人", "订单金额", "订单生成时间", "订单状态", "配送方式", "支付方式"};
  public static final String[] FIELDS_LIST =
      new String[] {"orderId", "consigneeName", "commoditySum", "createDate", "orderStatus"};
  public static final String ORDER_STATUS = "Order_Status";// 订单状态
  public static final String PAY_METHOD = "Pay_Method";// 支付方式
  public static final String ORDER_PURCHASER_TYPE = "Order_Purchaser_Type";// 下单类型

  // 详情页用
  public static final String[] TITLE_DETAIL =
      new String[] {"商品编号", "商品名称", "健康第一网价格", "赠送积分", "商品数量", "库存状态"};
  public static final String[] FIELDS_DETAIL =
      new String[] {"commodityCode", "commodityName", "commodityUnitPrice", "credits",
          "commodityNumber", "warehouseId"};

}
