package com.pltfm.app.util;

public class OrderDictionaryEnum {

    /**
     * 订单状态的枚举类
     *
     * @author zengming
     * @version 1.0
     * @since 2013-7-31
     */

    public enum Order_Status {
        // Veto(-2, "已驳回", 1, "Veto"),
        Cancel_Done(-1, "已取消", 2, "Already_Cancel"),
//        Cancel_Checking(-2, "取消审核中", 1, "Cancel_Checking"),
        Exception_Order(-3, "异常订单", 16, "Exception_Order"),
        Not_Pay(1, "未付款", 3, "Not_Pay"),
        Pay_Done(2, "已付款", 4, "Pay_Done"),
        // Audit_Done(25, "已审核", 5, "Audit_Done"),
        Settle_Done(3, "已结转", 6, "Settle_Done"),
        Stock_Done(4, "已出库", 7, "Already_Stock"),
        Ship_Done(5, "已配送", 8, "Already_Ship"),
        Order_Done(6, "已完成", 9, "Already_Done"),
//        Ship_Fail(12, "送货失败", 10, "Ship_Fail"),
        Settle_Not_Stock(15, "已结转未出库", 11, "Settle_Not_Stock"),
        Split_Done(16, "已拆分", 12, "Split_Done"),
//        Merge_Done(17, "已合并", 13, "Merge_Done"),
        Splited_Not_Settle(18, "已拆分未结转", 14, "Splited_Not_Settle"),
//        Merge_Not_Settle(19, "已合并未结转", 15, "Merge_Not_Settle"),
        Stock_Lock(20, "已锁库存", 17, "Stock_Lock"),
        Risk_Appraise(21, "待风控评估", 18, "Risk_Appraise"),
        Risk_Pass(22, "风控通过", 19, "Risk_Pass"),
        Nopay_FinalMoney(23, "待付尾款", 20, "Nopay_FinalMoney");


        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }


        public String getValue() {
            return value;
        }


        public int getIndex() {
            return index;
        }


        public String getCode() {
            return code;
        }


        /**
         * 构造函数
         */
        Order_Status(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        Order_Status() {
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (Order_Status o : Order_Status.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (Order_Status o : Order_Status.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (Order_Status o : Order_Status.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }

    }

    /**
     * 订单支付类型的枚举类
     *
     * @author Administrator
     * @author zengming
     * @since 2013-8-1
     */
    public enum OrderPayMethod {
        Balance(1, "账户余额支付", 1, "Balance"),
        Coupon(2, "优惠券支付", 0, "Coupon"),
        Bank(3, "网银/信用卡支付", 2, "Bank"),
        Platform(4, "第三方支付平台支付", 3, "Platform"),
        OnLine(5, "在线支付", 4, "Online"),
        WeiXin(6, "微信支付", 5, "WeiXin"),
        /*删除预备金 Reserve(7, "预备金支付", 6, "Reserve"),*/
        Prize(-1, "奖品(实际无支付数据)", 7, "Prize");

        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }


        public String getValue() {
            return value;
        }


        public int getIndex() {
            return index;
        }


        public String getCode() {
            return code;
        }


        /**
         * 构造函数
         */
        OrderPayMethod(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (OrderPayMethod o : OrderPayMethod.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (OrderPayMethod o : OrderPayMethod.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (OrderPayMethod o : OrderPayMethod.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 订单送货日期类型的枚举类
     *
     * @author zengming
     * @since 2013-8-1
     */
    public enum OrderDeliveryDateType {
        WorkDay(1, "工作日送", 0, "WorkDay"),
        Holiday(2, "节假日送", 1, "Holiday"),
        Both(3, "工作日节假日皆可", 2, "Both");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }


        public String getValue() {
            return value;
        }


        public int getIndex() {
            return index;
        }


        public String getCode() {
            return code;
        }


        /**
         * 构造函数
         */
        OrderDeliveryDateType(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (OrderDeliveryDateType o : OrderDeliveryDateType.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (OrderDeliveryDateType o : OrderDeliveryDateType.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (OrderDeliveryDateType o : OrderDeliveryDateType.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 订单下单类型的枚举类
     *
     * @author zengming
     * @since 2013-8-1
     */
    public enum OrderPurchaserType {
        Register(1, "普通会员下单", 0, "Register"),
//        UnRegister(2, "免注册客户下单", 1, "UnRegister"),
//        ServiceStaff(3, "客服代下单", 2, "ServiceStaff"),
        TimeMember(2, "时代会员下单", 1, "TimeMember");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }


        public String getValue() {
            return value;
        }


        public int getIndex() {
            return index;
        }


        public String getCode() {
            return code;
        }


        /**
         * 构造函数
         */
        OrderPurchaserType(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (OrderPurchaserType o : OrderPurchaserType.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (OrderPurchaserType o : OrderPurchaserType.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (OrderPurchaserType o : OrderPurchaserType.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    public enum OrderSaleTypes {
        SelfSupport(1, "自营", 0, "SelfSupport"),
        OtherSupport(2, "第三方", 1, "OtherSupport");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }


        public String getValue() {
            return value;
        }


        public int getIndex() {
            return index;
        }


        public String getCode() {
            return code;
        }


        /**
         * 构造函数
         */
        OrderSaleTypes(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (OrderSaleTypes o : OrderSaleTypes.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (OrderSaleTypes o : OrderSaleTypes.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (OrderSaleTypes o : OrderSaleTypes.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 订单优惠类型的枚举类
     *
     * @author zengming
     * @since 2013-8-1
     */
    public enum OrderPreferentialType {
        COUPON(4, "满额送券", 0, "Discount"),
        Cut(6, "满额减免", 1, "Cut"),
        Discount(8, "打折", 2, "Discount"),
        SPECIAL(10, "特价", 3, "SPECIAL"),
        INCREASE(5, "加价购", 4, "Increase"),
        GIFT(3, "满额赠品", 5, "Gift");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }


        public String getValue() {
            return value;
        }


        public int getIndex() {
            return index;
        }


        public String getCode() {
            return code;
        }


        /**
         * 构造函数
         */
        OrderPreferentialType(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (OrderPreferentialType o : OrderPreferentialType.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (OrderPreferentialType o : OrderPreferentialType.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (OrderPreferentialType o : OrderPreferentialType.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 订单操作类型字典
     *
     * @author zengming
     * @since 2013-8-8
     */
    public enum OrderOperateType {
        PayCancel(-2, "已支付取消", -2, "PayCancel"),
        Create(1, "生成", 0, "Create"),
        Pay(2, "支付", 1, "Pay"),
        Merge(3, "合并", 2, "Merge"),
        Split(4, "拆分", 3, "Split"),
        Settle(5, "结转", 4, "Settle"),
        Stock_Out(6, "出库", 5, "Stock_Out"),
        Ship(7, "配送", 6, "Stock_Out"),
        Cancel(8, "撤单", 7, "Cancel"),
        Refund(89, "退款", 8, "Refund"),
        Finish(9, "完成", 9, "Finish"),
        Delete(10, "删除", 10, "Delete"),
        Recovery(11, "恢复", 10, "Recovery"),
        Drop(12, "永久删除", 11, "Drop"),
        Assess(13, "评价", 12, "Assess"),
        Additional(14, "追加评价", 13, "Additional"),
        Audit(15, "审核", 0, "Audit"),
        ChangeFee(16, "修改运费", 16, "ChangeFee"),
        LockStock(17, "锁库存", 17, "LockStock"),
        MarkException(18, "标记异常", 18, "MarkException"),
        ReleaseException(19, "解除异常", 19, "ReleaseException"),
        ORDERRISK_JUDGE(20, "风控判断", 20, "orderrisk_judge"),
        ORDERRISK_EVALUATE(21, "风控评估", 21, "orderrisk_evaluate");

        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private OrderOperateType(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (OrderOperateType o : OrderOperateType.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (OrderOperateType o : OrderOperateType.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (OrderOperateType o : OrderOperateType.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 订单支付流水支付状态的枚举类
     *
     * @author zengming
     * @since 2013-8-1
     */
    public enum OrderPayState {
        Success(1, "成功", 0, "Success"),
        Fail(2, "失败", 1, "Fail"),
        Freeze(3, "冻结", 2, "Freeze"),
        Ready(4, "准备", 3, "Ready");

        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private OrderPayState(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (OrderPayState o : OrderPayState.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (OrderPayState o : OrderPayState.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (OrderPayState o : OrderPayState.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 付/退款标志的枚举类
     *
     * @author zengming
     * @since 2013-8-1
     */
    public enum OrderPayFlag {
        Payment(1, "付款", 0, "Payment"),
        Refundment(2, "退款", 1, "Refundment");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private OrderPayFlag(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (OrderPayFlag o : OrderPayFlag.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (OrderPayFlag o : OrderPayFlag.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (OrderPayFlag o : OrderPayFlag.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 发票开票类型的枚举类
     *
     * @author zengming
     * @since 2013-8-1
     */
    public enum InvoiceCreateType {
        Self(1, "自用", 0, "Self"),
        Replace(2, "代开", 1, "Replace");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private InvoiceCreateType(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (InvoiceCreateType o : InvoiceCreateType.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (InvoiceCreateType o : InvoiceCreateType.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (InvoiceCreateType o : InvoiceCreateType.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * /订单评价维度明细枚举
     *
     * @author liheping
     * @since 2013-8-26
     */
    public enum OrderAssessDetail {
        goodPackAssess(1, "商品包装满意度", 0, "goodPackAssess"),
        deliverySpeedAssess(2, "送货速度满意度", 1, "deliverySpeedAssess"),
        DistributionSelfAssess(3, "配送或自提人员的服务满意度", 2, "DistributionSelfAssess");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        private OrderAssessDetail(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 构造函数
         *
         * @param key
         * @param value
         * @param index
         * @param code
         */

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (OrderAssessDetail o : OrderAssessDetail.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (OrderAssessDetail o : OrderAssessDetail.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (OrderAssessDetail o : OrderAssessDetail.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 订单是否被删除的枚举类
     *
     * @author zengming
     * @since 2013-8-1
     */
    public enum OrderDisabled {
        Display(1, "显示", 0, "Display"),
        Delete(2, "删除", 1, "Delete"),
        Drop(3, "永久删除", 2, "Drop");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private OrderDisabled(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (OrderDisabled o : OrderDisabled.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (OrderDisabled o : OrderDisabled.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (OrderDisabled o : OrderDisabled.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 订单评价状态枚举类
     *
     * @author zengming
     * @since 2013-8-1
     */
    public enum Assess_Status {
        None(1, "未评价", 0, "None"),
        Assess(2, "已评价", 1, "Assess"),
        Additional(3, "已追加评价", 2, "Additional");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private Assess_Status(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (Assess_Status o : Assess_Status.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (Assess_Status o : Assess_Status.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (Assess_Status o : Assess_Status.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 订单明细中的商品类型枚举类
     *
     * @author zengming
     * @since 2014-3-7
     */
    public enum Commodity_Type {
        Normal(1, "普通商品", 0, "Normal"),
        SuitMain(2, "套餐主产品", 1, "SuitMain"),
        SuitAttachment(3, "套餐附属产品", 2, "SuitAttachment"),
        Gift(4, "赠品", 3, "Gift"),
        Additional(5, "加价购", 4, "Additional");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private Commodity_Type(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (Commodity_Type o : Commodity_Type.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (Commodity_Type o : Commodity_Type.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (Commodity_Type o : Commodity_Type.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 订单类型
     *
     * @author zengming
     * @since 2014-4-9
     */
    public enum Order_Type {
        Normal(1, "普通商品订单", 0, "Normal"),
        Prize(2, "抽奖商品订单", 1, "Prize"),
      /*  Times(3, "时代二次购物", 2, "Times"),
        TimesReg(4, "时代注册购物", 3, "TimesReg"),
        TimesUp(5, "时代升级购物", 4, "TimesUp"),*/
        YsOrder(7, "预售订单", 6, "YsOrder");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private Order_Type(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (Commodity_Type o : Commodity_Type.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (Commodity_Type o : Commodity_Type.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (Commodity_Type o : Commodity_Type.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 支付平台代码
     *
     * @author Administrator
     * @author zengming
     * @since 2013-8-1
     */
    public enum PlatformCode {
        yeepay(2, "易宝", 0, "yeepay"),
        alipay(3, "支付宝", 1, "alipay");

        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private PlatformCode(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 商品额外属性
     *
     * @author Administrator
     * @author zengming
     * @since 2014-7-24
     */
    public enum OrderItemExtAttrType {
        includeMedic(1, "含有药品/医疗器械", 1, "includeMedic");

        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private OrderItemExtAttrType(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 含药品/医疗器械的订单审核标志位
     *
     * @author Administrator
     * @author zengming
     * @since 2014-7-24
     */
    public enum OrderMainMedicCheckFlag {
        fail(0, "审核不通过", 0, "fail"),
        pass(1, "审核通过", 1, "pass");

        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private OrderMainMedicCheckFlag(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 含药品/医疗器械的订单审核标志位
     *
     * @author Administrator
     * @author zengming
     * @since 2014-7-24
     */
    public enum OrderMainMedicFlag {
        no(0, "不含", 0, "no"),
        yes(1, "含", 1, "yes");

        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private OrderMainMedicFlag(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }

    }

    /**
     * 同步标识
     *
     * @author xlg
     */
    public enum OrderSyncFlag {
        failed(0, "同步失败", "failed"),
        success(1, "同步成功", "success"),
        unSync(2, "未同步", "unSync");

        private OrderSyncFlag(int key, String value, String code) {
            this.key = key;
            this.value = value;
            this.code = code;
        }

        private int key;
        private String value;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public static String getValueByKey(int key) {
            for (OrderSyncFlag o : OrderSyncFlag.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        public static String getCodeByKey(int key) {
            for (OrderSyncFlag o : OrderSyncFlag.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }
    }

    public enum OrderCancelHandleStatus {
        // 处理状态 1申请中，2失败，3成功
        apply(1, "申请中", 1, "apply"),
        failure(2, "失败", 2, "failure"),
        success(3, "成功", 3, "success");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 构造函数
         */
        private OrderCancelHandleStatus(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }

        /**
         * 根据索引查找key键
         */
        public static int getKeyByIndex(int index) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getIndex() == index) {
                    return o.getKey();
                }
            }
            return 0;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }

    }

    /**
     * 结算状态
     *
     * @author xlg
     */
    public enum SettlementStatus {
        unSure(1, "未确认", 0, "unSure"),
        sellerSure(2, "商家已确认", 1, "sellerSure"),
        operateSure(3, "运营已确认", 2, "operate_sure"),
        financialAuditApproval(4, "财务审核通过", 3, "financial_audit_approval"),
        financialAuditRefuse(5, "财务审核拒绝", 4, "financial_audit_refuse"),
        setted(6, "已结出", 5, "setted");

        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }

        private SettlementStatus(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }
    }

    /**
     * 结算单操作类型
     *
     * @author xlg
     */
    public enum SettlementOperateType {
        gener(1, "生成", 0, "gener"),
        sellerSure(2, "商家确认", 1, "seller_sure"),
        operateSure(3, "运营确认", 2, "operate_sure"),
        financialAudit(4, "财务审核", 3, "financial_audit"),
        financialRefused(5, "财务拒绝", 4, "financial_refused"),
        financialSett(6, "财务结出", 5, "financial_sett");
        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }

        private SettlementOperateType(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }
    }

    /**
     * 结算单操作人类型
     *
     * @author xlg
     */
    public enum SettlementOperatorType {
        bgUser(1, "后台用户", 0, "bg_user"),
        ftUser(2, "前台用户", 1, "ft_user"),
        sysAuto(3, "系统自动", 2, "sys_auto");

        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }

        private SettlementOperatorType(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }
    }

    /**
     * 退款 服务类型Settlement_refund
     *
     * @author xlg
     */
    public enum SettlementRefundServiceType {
        // ||不退货退款
        refund(1, "退货", 0, "refund"),
        barter(2, "换货", 1, "barter"),
        reimburse(3, "不退货退款", 2, "reimburse"),
        Indemnity(4, "超时未发货赔付", 3, "Indemnity");

        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }

        private SettlementRefundServiceType(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }
    }

    /**
     * 推广效果状态
     *
     * @author xlg
     */
    public enum SpreadEffectStatus {
        orderCancel(0, "订单取消", 0, "orderCancel"),
        ready(1, "准备", 1, "ready"),
        payOrder(2, "订单支付", 2, "payOrder"),
        orderFinish(3, "订单完成", 3, "orderFinish"),
        setted(4, "已结算", 4, "setted"),
        forbid_setted(5, "禁止结算", 5, "forbid_setted");

        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }

        private SpreadEffectStatus(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }
    }

    /**
     * 差异调整单状态
     *
     * @author xlg
     */
    public enum DiffAdjStatus {
        notYet(0, "未结算", 0, "unSure"),
        alReady(1, "差异已结算", 1, "adjStuts");

        private int key;
        private String value;
        private int index;
        private String code;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 根据key键查找value值
         */
        public static String getValueByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getValue();
                }
            }
            return null;
        }

        /**
         * 根据key键查找code代码
         */
        public static String getCodeByKey(int key) {
            for (PlatformCode o : PlatformCode.values()) {
                if (o.getKey() == key) {
                    return o.getCode();
                }
            }
            return null;
        }

        private DiffAdjStatus(int key, String value, int index, String code) {
            this.key = key;
            this.value = value;
            this.index = index;
            this.code = code;
        }
    }
}
