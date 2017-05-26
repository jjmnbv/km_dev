package com.pltfm.app.util;

import java.util.HashMap;
import java.util.Map;
// import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.enums.CouponGrantDetailType;
import com.pltfm.app.enums.PromotionTypeEnums;

public class SysConstants {
    private static final Logger log = Logger.getLogger(SysConstants.class);

    // 结转生成的excel文档创建路径
    public static String PATH = null;
    // 结转生成的excel文档访问路径
    public static String VISIT_PATH = null;
    // 产品系统的库存接口
    public static String STOCK_SERVICE = null;
    // 优惠券接口
    public static String COUPON_SERVICE = null;
    // 客户系统的扣取账户余额接口
    public static String TRATIONLIST_SERVICE = null;
    // 客户系统的积分修改接口
    public static String USERGROWING_SERVICE = null;
    // 客户系统的通用查询接口
    public static String CUSTOMER_SERVICE = null;
    // 系统查询第三方支付状态的接口
    public static String QRY_ORDER_ONLINE_SERVICE = null;
    // 系统第三方支付退款接口
    public static String REFUND_ORDER_SERVICE = null;
    // 系统根据订单实付金额查询所发优惠券id的接口
    public static String QRY_COUPONID_SERVICE = null;
    // Pv订单同步接口
    public static String ORDER_PV_SYSNC_SERVICE = null;
    // 系统的短信接口
    public static String MESSAGE_SERVICE = null;
    // 订单系统的系统编码
    public static String ORDER_SYSCODE = null;
    // 产品系统的系统编码
    public static String PRODUCT_SYSCODE = null;
    // 物流中间件系统编码
    public static String EXPRESS_SYSCODE = null;
    // 促销系统的系统编码
    public static String PROMOTION_SYSCODE = null;
    // 客户系统的系统编码
    public static String USER_SYSCODE = null;
    // 内容发布系统的系统编码
    public static String CMS_SYSCODE = null;
    // B2B网站系统的系统编码
    public static String B2B_SYSCODE = null;
    // B2B网站系统的渠道编码
    public static String B2B_CHANNEL = null;
    // B2B网站系统的系统编码,用于短信邮件接口
    public static String B2B_SYSCODE4SMSMAIL = null;
    // B2B网站系统的URL
    public static String B2B_WEBSITE = null;
    // 短信邮件系统的系统编码
    public static String SMSMAIL_SYSCODE = null;
    // 各个系统编码映射到短信邮件系统上的系统类型map
    public static Map<String, Integer> sysCodeTosmsmailMap = null;
    // 各个渠道编码映射到短信邮件系统上的系统类型map
    public static Map<String, Integer> sysChannelTosmsmailMap = null;
    // 各个渠道编码映射到网站地址map
    public static Map<String, String> sysChannelToWebsiteMap = null;
    // 系统操作员
    public static String SYS = null;
    // 订单支付完成时对应的客户积分规则
    public static String RULE_AFTER_PAY = null;
    // 通用加操作类型，用于客户积分增加和等级增加等场景
    public static String COMMON_ADD_TYPE = null;
    // 通用减操作类型，用于客户积分增加和等级增加等场景
    public static String COMMON_MINUS_TYPE = null;
    // 支付金额的标志，用于构造客户等级接口入参的调用
    public static String PAYABLE_AMOUNT_SYMBOL = null;
    // 金额保留小数位，用于退款金额的计算
    public static String DEE_DIV_SCALE = null;
    public static int DEE_DIV_SCALE_INT;
    // 运费区间0kg-2kg
    public static String FARE_SECTION_ONE = null;
    // 运费区间0kg-2kg对应的运费
    public static String FARE_ONE_IN = null;
    public static String FARE_ONE_OUT = null;
    // 运费区间2kg-5kg
    public static String FARE_SECTION_TWO = null;
    // 运费区间2kg-5kg对应的运费
    public static String FARE_TWO_IN = null;
    public static String FARE_TWO_OUT = null;
    // 运费区间5kg以上对应的运费
    public static String FARE_OVER_IN = null;
    public static String FARE_OVER_OUT = null;
    // 缺省免运费最低金额
    public static String DEFAULT_FREE_PRICE = null;
    // 系统是否进行debug模式，主要是针对支付时，跳过支付金额是否和订单金额相等的校验
    public static boolean DEBUG_MODE = false;
    // “付款”备注
    public static final String COMMENT_PAY = "支付成功！";
    // “退款”备注
    public static final String COMMENT_REFUND = "您的订单已经完成退款.";
    // 退换货申请图片路径
    public static String RETURNSHOP_PHOTO_PATH = null;
    // 商品详情页路径
    public static String CMS_PAGE_PATH = null;
    public static final String SYS_KEY = "KMORDER-3C012C45-F9DF-DA36-3582-91193FC4F519";
    // 优惠券类型-满额送券
    public static Long PROMOTION_TYPE_GIFT = new Long(PromotionTypeEnums.COUPON.getValue());
    /**
     * 自动确认收货/款时间 10*24小时过期
     */
    public static final Integer ORDER_AUTO_SURE_TIME = 10 * 24;//

    // 优惠券冻结操作类型码
    public static final Long COUPON_FREEZE =
            new Long(CouponGrantDetailType.ORDER_FREEZORDER.getType());
    // 优惠券解冻操作类型码
    public static final Long COUPON_UNFREEZE =
            new Long(CouponGrantDetailType.ORDER_UNFREEZORDER.getType());
    // 优惠券退货完成后作废操作类型码
    public static final Long COUPON_RETURN_ORDER =
            new Long(CouponGrantDetailType.ORDER_RETURNORDER.getType());
    // 优惠券支付完成时置为已使用
    public static final Long COUPON_USED =
            new Long(CouponGrantDetailType.ORDER_PAYUSEING.getType());
    // 退款中转地址
    public static String REFUND_TRACK_URL = null;

    // 商城联系方式
    public static String MARKET_CONCACTS = null;
    static {
        try {
            log.info("######################加载远程zookeeper配置文件开始");
            // ResourceBundle rb = ResourceBundle.getBundle(SysConstants.PROPERTIES_PATH);
            PATH = ConfigurationUtil.getString("path");
            VISIT_PATH = ConfigurationUtil.getString("visitPath");
            STOCK_SERVICE = ConfigurationUtil.getString("stockService");
            TRATIONLIST_SERVICE = ConfigurationUtil.getString("trationListService");
            USERGROWING_SERVICE = ConfigurationUtil.getString("userGrowingService");
            CUSTOMER_SERVICE = ConfigurationUtil.getString("customerService");

            QRY_ORDER_ONLINE_SERVICE = ConfigurationUtil.getString("qryOrderOnLineService");
            REFUND_ORDER_SERVICE = ConfigurationUtil.getString("refundOrderService");
            QRY_COUPONID_SERVICE = ConfigurationUtil.getString("qryCouponIdService");

            MESSAGE_SERVICE = ConfigurationUtil.getString("messageRemoteService");
            COUPON_SERVICE = ConfigurationUtil.getString("couponService");
            ORDER_PV_SYSNC_SERVICE = ConfigurationUtil.getString("orderPvSysncService");
            ORDER_SYSCODE = ConfigurationUtil.getString("orderSysCode");
            PRODUCT_SYSCODE = ConfigurationUtil.getString("productSysCode");
            PROMOTION_SYSCODE = ConfigurationUtil.getString("promotionSysCode");
            USER_SYSCODE = ConfigurationUtil.getString("userSysCode");
            CMS_SYSCODE = ConfigurationUtil.getString("productSysCode");
            EXPRESS_SYSCODE = ConfigurationUtil.getString("expressSysCode");
            B2B_SYSCODE = ConfigurationUtil.getString("b2bSysCode");

            B2B_SYSCODE4SMSMAIL = ConfigurationUtil.getString("b2bSysCode4smsmail");

            B2B_CHANNEL = ConfigurationUtil.getString("b2bChannel");

            B2B_WEBSITE = ConfigurationUtil.getString("b2bWebsite");

            SMSMAIL_SYSCODE = ConfigurationUtil.getString("smsmailSysCode");
            sysCodeTosmsmailMap = new HashMap<String, Integer>();
            sysCodeTosmsmailMap.put(B2B_SYSCODE, new Integer(B2B_SYSCODE4SMSMAIL));

            sysChannelTosmsmailMap = new HashMap<String, Integer>();
            sysChannelTosmsmailMap.put(B2B_CHANNEL, new Integer(B2B_SYSCODE4SMSMAIL));

            sysChannelToWebsiteMap = new HashMap<String, String>();
            sysChannelToWebsiteMap.put(B2B_CHANNEL, B2B_WEBSITE);

            SYS = ConfigurationUtil.getString("sys");
            RULE_AFTER_PAY = ConfigurationUtil.getString("ruleAfterPay");
            COMMON_ADD_TYPE = ConfigurationUtil.getString("commonAddType");
            COMMON_MINUS_TYPE = ConfigurationUtil.getString("commonMinusType");
            PAYABLE_AMOUNT_SYMBOL = ConfigurationUtil.getString("payableAmountSymbol");
            DEE_DIV_SCALE = ConfigurationUtil.getString("deeDivScale");
            DEE_DIV_SCALE_INT = Integer.parseInt(DEE_DIV_SCALE);
            FARE_SECTION_ONE = ConfigurationUtil.getString("fareSectionOne");
            FARE_ONE_IN = ConfigurationUtil.getString("fareOneIn");
            FARE_ONE_OUT = ConfigurationUtil.getString("fareOneOut");
            FARE_SECTION_TWO = ConfigurationUtil.getString("fareSectionTwo");
            FARE_TWO_IN = ConfigurationUtil.getString("fareTwoIn");
            FARE_TWO_OUT = ConfigurationUtil.getString("fareTwoOut");
            FARE_OVER_IN = ConfigurationUtil.getString("fareOverIn");
            FARE_OVER_OUT = ConfigurationUtil.getString("fareOverOut");
            DEFAULT_FREE_PRICE = ConfigurationUtil.getString("defaultFreePrice");
            DEBUG_MODE = "1".equals(ConfigurationUtil.getString("debugMode")) ? true : false;
            RETURNSHOP_PHOTO_PATH = ConfigurationUtil.getString("RETURNSHOP_PHOTO_PATH");
            CMS_PAGE_PATH = ConfigurationUtil.getString("CMS_PAGE_PATH");
            REFUND_TRACK_URL = ConfigurationUtil.getString("REFUND_TRACK_URL");
            MARKET_CONCACTS = ConfigurationUtil.getString("marketConcacts");
            log.info("######################加载远程zookeeper配置文件结束");
        } catch (Exception e) {
            log.info("######################加载远程zookeeper配置项异常：");
            log.error(e);
            // log.error("加载配置文件 " + PROPERTIES_PATH + " 失败", e);
            log.error("加载远程配置文件失败", e);
        }
    }
    // *********************** 1.订单状态名称 begin
    // *******************************************
    public static final String ORDER_STATUS = "Order_Status";
    public static final String NOT_PAY = "Not_Pay";
    // *********************** 1.订单状态名称 end
    // *******************************************

    // *********************** 8.订单支付状态类型名称 begin
    // *******************************************
    public static final String SUCCESS = "Success";
    // *********************** 8.订单支付状态类型名称 end
    // *******************************************
}
