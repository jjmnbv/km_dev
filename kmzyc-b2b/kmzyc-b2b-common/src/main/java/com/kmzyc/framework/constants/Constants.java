package com.kmzyc.framework.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @title 系统常量类
 * @describtion 存放系统所有常量
 * @author Administrator
 * @date 2013-09-24
 */
public class Constants {
    /**
     * 支付平台
     */
    private static Map<String, String> platFormMap = new HashMap<String, String>();
    public static Map<String, String> REBATE_USER_TYPE = new HashMap<String, String>();// 返利用户类型
    static {
        platFormMap.put("2", "易宝");
        platFormMap.put("3", "支付宝");
        platFormMap.put("4", "财付通");
        platFormMap.put("5", "微信支付");
        platFormMap.put("6", "时代支付");
        platFormMap.put("7", "康美通");
        REBATE_USER_TYPE.put("06", "FANLI");
    }


    public static String getPlatformName(String key) {
        return platFormMap.get(key);
    }

    /**
     * wap支付标识
     */
    public static final int WAP_PAY_FLAG = 1;
    /**
     * 订单数据字典表中的order_dictionary_type:支付方式
     */
    public static final String PAY_METHOD = "Pay_Method";
    /**
     * 订单数据字典表中的order_dictionary_type:配送时段
     */
    public static final String DELIVERY_DATE_TYPE = "Delivery_Date_Type";

    /**
     * session用户ID
     */
    public static final String SESSION_USER_ID = "b2b_seesionUserId";

    /**
     * 微商申请普通用户ID
     */
    public static final String VS_REG_SESSION_USER_ID = "vs_reg_session_user_id";

    /**
     * session用户名
     */
    public static final String SESSION_USER_NAME = "b2b_sessionUserName";

    /**
     * session用户头像
     */
    public static final String SESSION_USER_HEAD_PIC = "b2b_sessionUserHeadPic";

    /**
     * session用户名（直销用户对应的绑定的用户名）
     */
    public static final String SESSION_Zx_USER_NAME = "b2b_sessionZxUserName";

    /**
     * 标识b2b和时代会员："01":会员 ；"02":时代会员
     */
    public static final String SESSION_B2B_OR_ERA = "b2b_login_remark";

    /** 康美会员 */
    /** "02":时代会员 */
    public static final String KM_USER_TYPE = "01";
    public static final String LOGINTYPE = "02";

    /** 商家类型==4 康美中药城商家 */
    public static final short SUPPLIERTYPE = 4;


    /**
     * session昵称
     */
    public static final String SESSION_NICK_NAME = "b2b_sessionNickName";

    /**
     * COOKIE SESSION ID
     */
    public static final String COOKIE_SESSION_ID = "b2b_cookieSessionId";

    /**
     * COOKIE用户名
     */
    public static final String COOKIE_USER_NAME = "b2b_cookieUserName";
    /**
     * COOKIE昵称
     */
    public static final String COOKIE_NICK_NAME = "b2b_cookieNickName";
    /**
     * COOKIE购物车临时用户ID
     */
    public static final String COOKIE_SHOPCART_TEMPID = "b2b_cookie_shopcart_tempid";

    /**
     * 从session中获取临时用户信息的属性名称
     */
    public static final String UNLOGIN_SESSION_USER_KEY = "unloginuser";

    /**
     * 账户余额支付
     */
    public static final Long PAY_METHOD_BALANCE = 1l;
    /**
     * 优惠券支付支付
     */
    public static final Long PAY_METHOD_PREFERENTIAL = 2l;
    /**
     * 网银/信用卡支付支付
     */
    public static final Long PAY_METHOD_ONLINE_PLATFORM = 4l;
    /**
     * 在线支付
     */
    public static final Long PAY_METHOD_ONLINE = 5l;
    /**
     * /** 订单状态 未付款
     */
    public static final Long ORDER_STATUS_NOT_PAY = 1l;
    /**
     * /** 订单状态 待付尾款
     */
    public static final Long ORDER_STATUS_NOT_PAY_RETAINAGE = 23l;
    /**
     * 订单状态 已付款
     */
    public static final Long ORDER_STATUS_PAY = 2l;
    /**
     * 客户类型 游客 KMUSER.Bnes_Customer_Type
     */
    public static final Integer CUSTOMER_TYPE_TOURIST = 6;
    /**
     * 客户类型 普通会员 KMUSER.Bnes_Customer_Type
     */
    public static final Integer CUSTOMER_TYPE_COMMON_MEMBER = 1;
    /**
     * 客户类型 时代会员 KMUSER.Bnes_Customer_Type
     */
    public static final Integer CUSTOMER_TYPE_SD_MEMBER = 2;
    /**
     * 站内消息状态 未阅
     */
    public static final Integer MESSAGE_NOT_READ = 0;

    /**
     * 站内消息平台(1：b2b；3：供应商；4：微商)
     */
    public static final Integer message_platform_b2b = 1;

    /**
     * 审核状态 审核通过
     */
    public static final Integer CHECK_STATUS_PASS = 1;
    /**
     * 订单自创建时间 24小时过期
     */
    public static final Integer ORDER_DISABLED_TIME = 24;
    /**
     * 预售订单自创建时间 1小时过期
     */
    public static final Integer PRESELL_ORDER_DISABLED_TIME = 1;
    /**
     * 自订单签收到退换货时间 15*24小时过期
     */
    public static final Integer ORDER_APPLY_TIME = 15 * 24;
    /**
     * 自退换货申请通过到未退货致失效时间 7*24小时过期
     */
    public static final Integer ORDER_RETURN_TIME = 7 * 24;


    /**
     * 付/退款标志 付款
     */
    public static final Long ORDER_PAY_FLAG_PAYMENT = 1l;

    /**
     * 充值、还款成功
     */
    public static final Integer RECHARGE_OK = 1;

    /**
     * 充值、还款失败
     */
    public static final Integer RECHARGE_FALSE = 2;
    /**
     * 未付款
     */
    public static final Integer RECHARGE_UN_PAY = 0;


    /**
     * 用户中等大小头像（分辨率100*100）后缀名
     */
    public static final String MIDDLE_IMG_SUFFIX = "_mid";
    /**
     * 用户小头像（分辨率48*58）后缀名
     */
    public static final String SMALL_IMG_SUFFIX = "_sma";

    /**
     * 常见问题faq
     */
    public final static String faq = "常见问题FAQ";

    /**
     * 未使用
     */
    public static final String NOTUSE_COUPONSTATUS = "3";

    /**
     * 订单流水支付状态 未付款
     */
    public static final Long ORDER_PAY_STATE_SUCCESS = 1L;

    /**
     * 订单状态 已付款
     */
    public static final Long ORDER_PAY_STATE_SUCCESS_2 = 2L;

    /**
     * 立即购 标志位
     */
    public static final String EASY_BUY = "easyBuy";
    /**
     * 处方商品购买 标志位
     */
    public static final String PRESCRIPTION_BUY = "prescription";
    /**
     * 预售购买
     */
    public static final String PRESELL_BUY = "presellBuy";


    /**
     * 类目状态 1:有效
     */
    public static final String STATUS = "1";

    /**
     * 订单是否评价完成
     */
    public static final String PROD_APPRIASEDONE = "1";

    /**
     * 移动客户端系统类型
     */
    public static final String APP_TYPE_IOS = "iOS";

    /**
     * 移动客户端系统类型
     */
    public static final String APP_TYPE_ANDROID = "Android";
    /**
     * 交易类型 1 在线充值
     */
    public static final Integer TRANSACTION_TYPE_ONLINE = 1;
    /**
     * 交易类型 2 后台充值
     */
    public static final Integer TRANSACTION_TYPE_STAGEBACK = 2;
    /**
     * 交易类型 3 余额支付
     */
    public static final Integer TRANSACTION_TYPE_PAY = 3;
    /**
     * 交易类型 4 取消订单
     */
    public static final Integer TRANSACTION_TYPE_CANCEL = 4;
    /**
     * 交易类型 5 订单退款
     */
    public static final Integer TRANSACTION_TYPE_BACK = 5;
    /**
     * 交易类型 6 取现
     */
    public static final Integer TRANSACTION_TYPE_REW = 6;
    /**
     * 交易类型 16 邀请返利
     */
    public static final Integer TRANSACTION_TYPE_REWARD = 16;
    /**
     * 交易类型 17 商家结算
     */
    public static final Integer TRANSACTION_TYPE_MERCHANT = 17;
    // 18--商家取现
    public static final Integer TRANSACTION_TYPE_ENCHASHMENT = 18;
    // 19--余额冻结
    public static final Integer TRANSACTION_TYPE_FREEZING = 19;
    // 20--余额解冻
    public static final Integer TRANSACTION_TYPE_THAW = 20;
    // 21--消费返利；22--销售返佣；23--分销返佣
    public static final Integer TRANSACTION_TYPE_CONSUMPTION = 21;
    public static final Integer TRANSACTION_TYPE_SALE = 22;
    public static final Integer TRANSACTION_TYPE_DISTRIBUTION = 23;

    /**
     * 产品标识
     */
    public static final int PRODUCT_FLAG = 1;
    /**
     * 套餐标识
     */
    public static final int SUIT_FLAG = 2;

    // 购物车商品错误编码start //

    /**
     * 商品不存在
     */
    public static final int NON_PRODUCT = -998;
    /**
     * 无库存
     */
    public static final int NON_STOCK = -999;
    /**
     * 下架
     */
    public static final int DROPS = -1000;
    /**
     * 库存不足
     */
    public static final int UNDERSTOCK = -1001;
    /**
     * 系统异常
     */
    public static final int DATAEXCEPTION = -1002;

    /**
     * 未达起售数量
     */
    public static final int BELOWLIMIT = -1003;
    /**
     * 超过限购上限
     */
    public static final int OVERLIMIT = -1004;
    /**
     * 不能享受活动
     */
    public static final int UNENJOYACTIVITY = -1005;
    /**
     * 处方药
     */
    public static final int PRESCRIPTION = -1006;
    /***
     * 只有时代会员才能购买康美中药城产品
     * 
     */
    public static final int UNENBUY = -1007;

    /**
     * 活动库存不足
     */
    public static final int SELLSTOCK = -1008;

    /**
     * 订单来源web
     */
    public static final long ORDER_SOURCE_WEB = 1;
    /**
     * 订单来源APP
     */
    public static final long ORDER_SOURCE_APP = 2;
    /**
     * 订单来源WAP
     */
    public static final long ORDER_SOURCE_WAP = 4;

    /**
     * 自营
     */
    public static final long SELF_SELLER_ID = 221L;

    // 优惠券激活成功
    public static int ACTIVE_SUCCESS = 8;

    // 优惠券激活码无效
    public static int ACTIVE_NOUSE = 3;

    // 优惠券已激活
    public static int ACTIVE_HAVEUSE = 4;

    // 激活码已过期
    public static int ACTIVE_OUTTIME = 6;

    /**
     * 微信openid常量存储
     */
    public static final String SESSION_WX_OPENID = "b2b_session_wx_openid";


    /**
     * 结算页面-5-购物车信息变动
     */
    public static final String SETTLEMENT_SHOPCART_CHANGE = "5";
    /**
     * 渠道号 app
     */
    public static final String CHANNEL_APP = "APP";


    /** 注册的设备，1：pc 2：wap 3：app **/
    public static final Integer REGISTER_DEVICE_PC = 1;
    public static final Integer REGISTER_DEVICE_WAP = 2;
    public static final Integer REGISTER_DEVICE_APP = 3;


    /** 注册的平台，1：机构 2:微商,3：时代，4：总部，5：砍价，6：51返利，7：cps,8:常规(1不在portal项目),9:康美云 **/

    public static final Integer REGISTER_PLATFORM_ERA = 3;
    public static final Integer REGISTER_PLATFORM_CPS = 7;
    public static final Integer REGISTER_PLATFORM_DEFAULT = 8;

    /**
     * session总部用户名
     */
    public static final String SESSION_KM_MOBILE = "b2b_seesionKmMobile";


    /**
     * 活动开始时间,优惠券发放活动
     */
    public static final String COUPON_ACTIVITY_STARTTIME = "coupn_activity_starttime";
    /**
     * 活动结束时间，优惠券发放活动
     */
    public static final String COUPON_ACTIVITY_ENDTIME = "coupn_activity_endtime";
    /**
     * 优惠券发放id
     */
    public static final String COUPON_GRANT_ID = "coupon_grant_id";
    /**
     * 优惠券发放数量限制，针对每个用户
     */
    public static final String COUPON_LIMIT_FOR_USER = "coupon_limit_for_user";
    public static final String COUPON_USER = "coupon_user_";
    /**
     * 优惠券并发
     */
    public static final String GRANT_COUPON_FOR_ACTIVITY = "grantCouponForActivity_";

    /**
     * 自营和代销的key
     */
    public static final Long SELF_AND_PROXY_KEY = 1L;

    /**
     * 登录账户前缀
     */
    public static final String LOGACCOUNT_NAME_PREFIX = "SD_";
    /**
     * 栏目标识 热销
     */
    public static final String SECTION_INDENTIFICATION_REXIAO = "REXIAO";
}
