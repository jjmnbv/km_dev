package com.pltfm.app.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 常量类
 * 
 * @author zhl
 * @since 2013-07-22
 */
public class Constants {
    /**
     * 例如 public static final String SYS = "sys"; public static Map<Integer, String> getSex() {
     * Map<Integer, String> sexMap = new LinkedHashMap<Integer, String>(); sexMap.put(1, "男");
     * sexMap.put(2, "女"); return sexMap; }
     **/
    /**
     * 判断是否集合
     */
    public static Map<Integer, String> getJudge() {
        Map<Integer, String> judgeMap = new LinkedHashMap<Integer, String>();
        judgeMap.put(0, "是");
        judgeMap.put(1, "否");
        return judgeMap;
    }

    /*
     * //时代会员请求地址 public static final String
     * SOAPBINDINGADDRESS="http://192.168.120.101:8088/Service.svc?wsdl"; //远程时代接口请求命名空间 public
     * static final String QNAMESPACE="http://tempuri.org/"; //获取远程时代会员信息请求action public static
     * final String AOAPACTION="http://tempuri.org/IMemberInfo/MemberGetInfo"; //获取远程时代会员请求方法 public
     * static final String AOAPElEMENT="MemberGetInfo";
     */


    // 抽奖活动状态 草稿
    public static final String LUCKDRAWDRAFT = "0";
    // 抽奖活动状态 审核通过
    public static final String LUCKDRAWSUBMIT = "2";
    // 抽奖活动状态 进行中
    public static final String LUCKDRAWING = "4";
    // 抽奖活动状态 暂停
    public static final String LUCKDRAWTIMEOUT = "5";
    // 抽奖活动状态 结束
    public static final String LUCKDRAWOVER = "6";


    // 预备金
    public static final Integer USERTYPERESERVER = 1;
    // 账单是否还清 是
    public static final String RESERVERBILLTRUE = "1";
    // 账单是否还清 否
    public static final String RESERVERBILLNO = "2";
    // 账单是否逾期 是
    public static final String RESERVERBILLOVERDUEPAYTRUE = "1";
    // 账单是否逾期 否
    public static final String RESERVERBILLOVERDUEPAYNO = "2";

    // 流水交易状态 未付款
    public static final Integer TRANSACTIONSTATUSNO = 0;
    // 流水交易状态 成功
    public static final Integer TRANSACTIONSTATUSSUCCESS = 1;
    // 流水交易状态 失败
    public static final String TRANSACTIONSTATUSFALSE = "2";
    // 流水交易状态 付款中
    public static final String TRANSACTIONSTATUSING = "3";
    // 流水交易对象
    public static final Integer TRANSACTIONOBJECTRESERVER = 4;
    /**
     * 交易类型 预备金，8~10，分别为，8---预备金开通;9--预备金变更;1 0---预备金关闭;11---预备金支付订单;12--预备金在线还款;
     * 13---预备金订单退款;14--预备金取消订单;15--预备金调整;1--在线充值
     */
    public static final Integer TRANSACTION_TYPE_RECHARGE_ONLINE = 1;
    public static final Integer TRANSACTION_TYPE_RESERVER_START = 8;
    public static final Integer TRANSACTION_TYPE_RESERVER_CHANGE = 9;
    public static final Integer TRANSACTION_TYPE_RESERVER_CLOSE = 10;
    public static final Integer TRANSACTION_TYPE_RESERVER_PAYBILL = 11;
    public static final Integer TRANSACTION_TYPE_RESERVER_ONLINEREPAY = 12;
    public static final Integer TRANSACTION_TYPE_RESERVER_RETURN = 13;
    public static final Integer TRANSACTION_TYPE_RESERVER_CANCELBILL = 14;
    public static final Integer TRANSACTION_TYPE_RESERVER_RESERVERCHANGE = 15;
    // 余额支付流水交易类型5---订单退款 16--邀请奖励
    public static final Integer TRANSACTION_TYPE_RETURN = 5;
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
     * 远程系统编号：产品系统
     */
    public static final String REMOTE_SERVICE_PRODUCT = "02";

    public static final Integer RANSACTION_TYPE_INVITATION_REWARD = 16;

    /**
     * 邀请奖励规则 1：注册 ，2：验证手机，3：首次购物
     */
    public static final String RULECODE_REGIST = "1";
    public static final String RULECODE_VERIFICATION = "2";
    public static final String RULECODE_FIRSTBUY = "3";

    /**
     * 分销邀请机构 0：有效；1：无效
     */
    public static final String ORGANISTATETURE = "0";
    public static final String ORGANISTATEFALSE = "1";

    // 余额取现 取现审核状态;0----待审核;1---提现完成;2---审核拒绝
    public static final int ENCHASHMENT_STATUS_PENDING = 0;
    public static final int ENCHASHMENT_STATUS_SUCCESS = 1;
    public static final int ENCHASHMENT_STATUS_FALSE = 2;

    // 短信提醒 31:提现成功提醒;32:提现拒绝提醒;33微商申请开通通过;34微商申请开通拒绝,41提现失败,35机构申请通过,36机构申请拒绝
    // public static final int MSG_ENCHASHMENTSUCCESS = 31;
    // public static final int MSG_ENCHASHMENTREFUSE = 32;
    // public static final int MSG_ENCHASHMENTFAIL = 41;
    // public static final int VS_OPENAPPLYSUCCESS = 33;
    // public static final int VS_OPENAPPLYFLASE = 34;
    //
    // public static final int CS_INSAPPLYSUCCESS = 43;
    // public static final int CS_INSAPPLYFAILED = 44;

    /**
     * 发送短信返回状态,0失败，1成功，2未发送，4发送异常
     */
    public static final String SEND_BACK_STATUS_EXCEPTION = "4";
    /**
     * 发送短信返回状态,0失败，1成功，2未发送，4发送异常
     */
    public static final String SEND_BACK_STATUS_SUCCESS = "1";

    // 商家类型 入驻
    public static final Short SUPPLIER_TYPE_SETTLED = 2;

    // 微商申请类型 1开通|2修改信息|3重新开通
    public static final short APPLY_TYPE_NEW = 1;
    public static final short APPLY_TYPE_UPDATE = 2;
    public static final short APPLY_TYPE_OPEN = 3;

    // 微商审核状态,1待审核|2审核通过|3审核拒绝
    public static final short AUDIT_STATUS_PENDING = 1;
    public static final short AUDIT_STATUS_SUCCESS = 2;
    public static final short AUDIT_STATUS_FALSE = 3;

    // 系统编号 type 1、产品 2、订单 3、B2B 4、促销
    public static final int SYSTEM_PROMOTION_NUMBER = 4;

    // 微商是否有效,0否，1是
    public static final short VS_ISVALID_STATUS_FALSE = 0;
    public static final short VS_ISVALID_STATUS_TRUE = 1;

    // 安全消息类型；1----安全消息；2---产品信息；3----订单信息；4----审核信息
    public static final Integer MESSAGE_TYPE_SAFETY = 1;
    public static final Integer MESSAGE_TYPE_PRODUCT = 2;
    public static final Integer MESSAGE_TYPE_ORDER = 3;
    public static final Integer MESSAGE_TYPE_AUDIT = 4;

    // 安全消息发布状态；0----待发布；1---已经发布
    public static final Integer MESSAGE_STATUS_RELEASEING = 0;
    public static final Integer MESSAGE_STATUS_RELEASED = 1;

    // 安全消息是否定时发布 0是1否
    public static final Integer MESSAGE_ISTIME_TRUE = 0;
    public static final Integer MESSAGE_ISTIME_FALSE = 1;

    // 安全消息发布类型(1:'按客户类别发布',2:'按具体客户发布)
    public static final Integer MESSAGE_OBJECT_CUSTYPE = 1;
    public static final Integer MESSAGE_OBJECT_CUSSPECIFIC = 2;

    // 安全消息发送平台(1：b2b；2：供应商；3：微商)
    public static final Integer MESSAGE_PLATFORM_B2B = 1;
    public static final Integer MESSAGE_PLATFORM_SUPPLIER = 2;
    public static final Integer MESSAGE_PLATFORM_VS = 3;

    // 系统管理员id 23 注：只能接口使用
    public static final Integer SYS_LOGIN_ID = 23;

    // 微商全局设置推广者申请须审核,0否，1是
    public static final Short IS_APPLICATE_AUDIT_FAlSE = 0;
    public static final Short IS_APPLICATE_AUDIT_TRUE = 1;
    // 微商全局设置推广者修改信息须审核,0否，1是
    public static final Short IS_ALTER_AUDIT_FALSE = 0;
    public static final Short IS_ALTER_AUDIT_TRUE = 1;

    // 证件类型：0.身份证1.护照2.回乡证3.选择证件类型
    public static final Integer N_CERTIFICATE_TYPE_IDENTITY = 0;
    public static final Integer N_CERTIFICATE_TYPE_PASSPORT = 1;
    public static final Integer N_CERTIFICATE_TYPE_HOMEBACK = 2;

    // 提现来源（0：供应商；1：微商;2机构）
    public static final Integer ENCHASHMENT_RESOURCE_GYS = 0;
    public static final Integer ENCHASHMENT_RESOURCE_VS = 1;
    public static final Integer ENCHASHMENT_RESOURCE_JG = 2;

    /**
     * 促销系统调用类型
     */
    public static final String PROMOTION_SYSTEM_CODE = "11";

    // 接口调用系统编号
    public static final Integer SYSTEM_TYPE_ORDER = 2;

    /**
     * 全局变量：机构申请自动审1、手动审核0
     */
    public static final String GLOBAL_SETTING_INST_AUDIT_AUTO = "1";
    public static final String GLOBAL_SETTING_INST_AUDIT_HAND = "0";

    /**
     * 机构申请记录状态：待审核0、已审核1、审核拒绝2
     */
    public static final Short INST_APPLY_RECORD_AUDIT_STATE_NOTYET = 0;
    public static final Short INST_APPLY_RECORD_AUDIT_STATE_ALREADY = 1;
    public static final Short INST_APPLY_RECORD_AUDIT_STATE_CANCEL = 2;

    /**
     * 抽奖参与成功标识
     */
    public static final String LOTTERY_PARTICIPATION_SUCCEED_FLAG =
            "lottery_participation_succeed_flag_";
    /**
     * 颁奖超时用户
     */
    public static final String LUCK_YOU_EXCEPTION_TIMEOUT = "luckYouExceptionTimeOut";
}
