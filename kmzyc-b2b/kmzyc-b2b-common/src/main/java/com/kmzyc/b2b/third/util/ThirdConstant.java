package com.kmzyc.b2b.third.util;

/**
 * 第三方信息需要用到的常量
 * 
 * @author Administrator 2014-03-20
 */
public class ThirdConstant {

    /**
     * 微信开发者账号所对应的常量token
     */
    public static final String WX_TOKEN = "kmb2b";

    /**
     * 第三方账号绑定的需要完善信息的正式会员类型的数字代码(次前是用游客的身份代替)
     */
    public static final Integer THIRD_CUSTOMER_TYPE_NEED_FILLINFO = 1;

    /**
     * 第三方账号类型-QQ
     */
    public static final String THIRD_ACCOUNT_TYPE_QQ = "01";
    /**
     * 第三方账号类型-微信
     */
    public static final String THIRD_ACCOUNT_TYPE_WX = "02";
    /**
     * 第三方账号类型-新浪微博
     */
    public static final String THIRD_ACCOUNT_TYPE_SINA = "03";

    /**
     * 第三方账号类型 - 支付宝
     */
    public static final String THIRD_ACCOUNT_TYPE_ALIPAY = "04";

    /**
     * 第三方账号类型 - 淘宝账号
     */
    public static final String THIRD_ACCOUNT_TYPE_TAOBAO = "05";

    /**
     * 第三方账号类型 - 返利账号 20151214
     */
    public static final String THIRD_ACCOUNT_TYPE_FANLI = "06";

    public static final String THIRD_ACCOUNT_TYPE_KMCLOUD = "07";

    /**
     * 返利网收货地址来源 20151214
     */
    public static final int ADDRESS_SOURCE_FANLI = 1;

    /**
     * 第三方账号绑定类型-和正式会员绑定(相当于用户既可以使用第三方账号登录康美,也可以使用第三方登录账号绑定的康美会员(已有密码和邮箱)账号登录)
     */
    public static final String THIRD_BIND_TYPE_FORMAL = "01";

    /**
     * 第三方账号绑定类型-和需要完善信息的正式会员绑定(使用第三方账号登录时加载出其绑定的康美中药城的(无密码和邮箱)正式会员信息, 享有和正式会员一样儿的折扣只有一种方式登录进康美商城)
     */
    public static final String THIRD_BIND_TYPE_TEMP = "02";

    /**
     * 第三方账号性别统一在我们系统表中的定义 -男
     */
    public static final String THIRD_GENDER_MALE = "1";
    /**
     * 第三方账号性别统一在我们系统表中的定义 -女
     */
    public static final String THIRD_GENDER_FEMALE = "2";

    /**
     * 第三方账号绑定的康美会员的登录用户信息放入到session中
     */
    public static final String THIRD_SESSION_USER = "loginUser";

    /**
     * 第三方账号绑定的是否是需要完善信息的正式会员,如果是 ,则存入session的具体值为Y(供屏蔽菜单使用),如果不是,则为N
     */
    public static final String THIRD_ISTEMP_MEMBER = "isTempMember";

    /**
     * 是否是从绑定管理中心过来的主动绑定,如果是,值为Y
     */
    public static final String IS_COMEFROMBINDMANAGE = "isComeFromBindManage";

    /**
     * 20151221 add 是否是返利网用户常量标识
     */
    public static final String IS_FANLI_USER = "isFanliUser";

    /**
     * 默认关注时或用户输入非识别字符的回复文本
     */
    public static final String WX_MENU_TEXT = "您可以回复以下数字查看内容.\n1.红枣\n2.枸杞";

    /**
     * 微信公众平台开发者的微信号
     */
    public static final String WX_USERNAME = "kangmeicloud";

    /**
     * 消息类型--纯文本类型
     */
    public static final String WX_MSG_TYPE_TEXT = "text";

    /**
     * 消息类型--图文类型
     */
    public static final String WX_MSG_TYPE_NEWS = "news";

    /**
     * 消息类型 --图片
     */
    public static final String WX_MSG_TYPE_IMAGE = "image";

    /**
     * 消息类型 --音乐
     */
    public static final String WX_MSG_TYPE_MUSIC = "music";

    /**
     * 消息类型 --地理位置
     */
    public static final String WX_MSG_TYPE_LOCATION = "location";

    /**
     * 消息类型 --链接
     */
    public static final String WX_MSG_TYPE_LINK = "link";

    /**
     * 消息类型 --语音
     */
    public static final String WX_MSG_TYPE_VOICE = "voice";

    /**
     * 推送事件类型
     */
    public static final String WX_EVENT_TYPE = "event";

    /**
     * 菜单事件推送click类型: 每日热卖
     */
    public static final String WX_KEY_DAYSALEHOT = "menu_dayhot";

    /**
     * 事件推送类型--关注
     */
    public static final String WX_EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件推送类型--取消关注
     */
    public static final String WX_EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件推送类型--点击菜单事所触发的类型
     */
    public static final String WX_EVENT_TYPE_CLICK = "CLICK";

    /**
     * 从天猫授权过来的淘宝用户的openid
     */
    public static final String TMALL_USER_ID = "tmall_user_id";

    /**
     * 普通用户注册默认类型
     */
    public static final Integer CUSTOMER_DEFAULT_TYPE = 1;
}
