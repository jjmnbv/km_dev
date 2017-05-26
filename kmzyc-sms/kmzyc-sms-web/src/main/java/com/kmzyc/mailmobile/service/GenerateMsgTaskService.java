package com.kmzyc.mailmobile.service;

import com.kmzyc.mailmobile.model.MsgSendTask;

public interface GenerateMsgTaskService {


    /**
     * 组装发送验证码的信息
     * 
     * @param mobilePhone
     * @param mobileVerifyCode
     * @param modeType
     * @param systemType
     * @return
     */
    public MsgSendTask generateMobileVerifyMsg(String mobilePhone, String mobileVerifyCode,
            String modeType, Integer systemType);


    /**
     * 组装发送注册信息
     * 
     * @param mobilePhone
     * @param modeType
     * @param systemType
     * @return
     */
    public MsgSendTask generateRegisterMsg(String mobilePhone, String modeType, Integer systemType);


    /**
     * 组装发送发货成功
     * 
     * @param mobilePhone
     * @param orderCode
     * @param logisticsName
     * @param logisticsOrderNo
     * @param modelType
     * @param systemType
     * @return
     */
    public MsgSendTask generateOrderShippedMsg(String mobilePhone, String orderCode,
            String logisticsName, String logisticsOrderNo, String modelType, Integer systemType);



    /**
     * 组装退货
     * 
     * @param mobilePhone
     * @param returnOrderCode
     * @param systemType
     * @return
     */
    public MsgSendTask generateReturnGoodsMsg(String mobilePhone, String companyAddress,
            String companyPhone, String contectUserName, String returnOrderCode, Integer systemType,
            Integer businessType, String businessPhone);


    /**
     * 组装取现成功
     * 
     * @param mobilePhones
     * @param systemType
     * @return
     */
    public MsgSendTask generateEnchashmentSuccessMsg(String mobilePhones, Integer systemType);

    /**
     * 组装系统通知
     * 
     * @param mobilePhones
     * @param systemType
     * @return
     */
    public MsgSendTask generateEnchashmentSuccessMsg(String mobilePhones, Integer systemType,
            String sendCount);


    /**
     * 组装取现拒绝
     * 
     * @param mobilePhones
     * @param systemType
     * @return
     */
    public MsgSendTask generateEnchashmentRefuseMsg(String mobilePhones, Integer systemType);

    /**
     * 组装取现失败
     * 
     * @param mobilePhones
     * @param systemType
     * @return
     */
    public MsgSendTask generateEnchashmentErrorMsg(String mobilePhones, Integer systemType);

    /**
     * 结算通知
     * 
     * @param mobilePhones 手机号 settlementNo结算编号
     * @param systemType
     * @return
     */
    public MsgSendTask generatesettlementMsg(String mobilePhones, String settlementNo,
            Integer systemType);

    /**
     * 组装优惠劵到期信息
     * 
     * @param mobilePhones
     * @param systemType
     * @return
     */
    public MsgSendTask generateCouponExpiringMsg(String mobilePhones, Integer systemType);


    /**
     * 
     * @param mobilePhones
     * @param
     * @return
     */
    public MsgSendTask generateSpreadMsg(String mobilePhones, String msgContent, Integer timeType);

    /**
     * 组装商家资质重审信息
     * 
     * @param mobilePhones,companyName
     * @param systemType
     * @return
     */
    public MsgSendTask generateAptitudeAppMsg(String mobilePhones, String companyName,
            Integer systemType);


    /**
     * 组装预售尾款支付通知信息
     * 
     * @param mobilePhones,orderPayDate,productSkucode,finalpayEndTime
     * @param systemType
     * @return
     */
    public MsgSendTask generatePresellFinalpayMsg(String mobilePhones, String orderPayDate,
            String productSkucode, String finalpayEndTime, Integer systemType);


    public MsgSendTask generatePromotionCouponMsg(String mobilePhones, String count,
            String promotionName, Integer systemType);

}
