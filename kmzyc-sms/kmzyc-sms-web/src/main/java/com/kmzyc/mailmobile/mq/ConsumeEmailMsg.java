package com.kmzyc.mailmobile.mq;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
//import com.kmzyc.zkconfig.ConfigurationUtil;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.MessageConstants;
import com.kmzyc.mailmobile.model.MsgSendTask;
import com.kmzyc.mailmobile.service.GenerateMsgTaskService;
import com.kmzyc.mailmobile.service.HandleMsgSendTaskService;
import com.kmzyc.mailmobile.util.RegexUtils;
import com.kmzyc.mailmobile.util.SpringBeanUtil;
import com.kmzyc.mframework.constants.SystemEnums;
import com.kmzyc.mframework.logger.MessagelLogger;
import com.kmzyc.zkconfig.ConfigurationUtil;

public class ConsumeEmailMsg {

    private static Map<String, Object> map = new HashMap<String, Object>();
    private static Logger logger = Logger.getLogger(ConsumeEmailMsg.class);

    /**
     * 接收到MQ消息后处理方法
     * 
     * @param kmMsg
     * @throws SQLException
     */
    public static void consumeMsg(KmMsg kmMsg) throws Exception {
        String kmMsgType = (String) kmMsg.getMsgData().get("kmMsgType");
        logger.info("消息处理类型。1【短信】，传入的参数为【" + kmMsgType + "】");
        if (StringUtils.equals(MessageConstants.KMMSG_MOBIL, kmMsgType)) {
            consumeMobileMsg(kmMsg);
        } else {
            logger.info("消息处理类型。2【邮件】，传入的参数为【" + kmMsgType + "】邮件发送功能已删除");
        }
    }

    /**
     * 获取需要发送的手机号码
     * 
     * @param uidList
     * @param mobilePhoneList
     * @return
     */
    public static String getSendMoblies(List<Long> uidList, List<String> mobilePhoneList,
            int type) {
        MessagelLogger.info("传入的校验手机号码为：【" + StringUtils.join(mobilePhoneList, ",") + "】");
        boolean isBook = true;
        int unSendNum = 0;
        int unBookPhoneNum = 0;
        int illegalPhoneNum = 0;
        int duplicatePhoneNum = 0;
        StringBuffer phoneBufferTemp = new StringBuffer();
        for (int i = 0; i < mobilePhoneList.size(); i++) {
            if (RegexUtils.isMobileNO(mobilePhoneList.get(i))) {
                if (phoneBufferTemp.indexOf(mobilePhoneList.get(i)) == -1) {// 去重处理，为解决早期直接从数据库中插入的不合法数据,
                    // 这两步判断去除不喝规则的手机号码
                    if (isBook) {
                        phoneBufferTemp.append(mobilePhoneList.get(i)).append(',');
                    } else {
                        unBookPhoneNum = unBookPhoneNum + 1;
                        map.put(mobilePhoneList.get(i), MessageConstants.SEND_BACK_STATUS_UNSEND);
                    }

                } else {
                    duplicatePhoneNum = duplicatePhoneNum + 1;
                    map.put(mobilePhoneList.get(i), MessageConstants.SEND_BACK_STATUS_UNSEND);
                }
            } else {
                illegalPhoneNum = illegalPhoneNum + 1;
                map.put(mobilePhoneList.get(i), MessageConstants.SEND_BACK_STATUS_UNSEND);
            }
        }
        unSendNum = illegalPhoneNum + unBookPhoneNum + duplicatePhoneNum;
        int sendNum = mobilePhoneList.size() - unSendNum;
        MessagelLogger.info(
                "未发送的手机共有【" + unSendNum + "】个,其中有【" + illegalPhoneNum + "】个不合法号码,【" + unBookPhoneNum
                        + "】个未订阅，【" + duplicatePhoneNum + "】个重复" + ",发送的手机号码共有【" + sendNum + "】个");
        if (phoneBufferTemp == null || phoneBufferTemp.length() <= 0) {
            MessagelLogger.info("需要发送的手机都不在符合条件内");
            map.put("result", MessageConstants.SEND_BACK_STATUS_EXCEPTION);
            return null;
        }
        return phoneBufferTemp.toString().substring(0, phoneBufferTemp.toString().length() - 1);

    }

    /**
     * 发送短信
     * 
     * @param kmMsg
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public static void consumeMobileMsg(KmMsg kmMsg) throws Exception {

        MessagelLogger.info("进入短信消息处理:(入参)"+ JSONObject.toJSONString(kmMsg));
        try {
            MsgSendTask msgSendTask = new MsgSendTask();
            GenerateMsgTaskService generateMsgTaskService =
                    (GenerateMsgTaskService) SpringBeanUtil.getBean("generateMsgTaskService");
            HandleMsgSendTaskService handleMsgSendTaskService =
                    (HandleMsgSendTaskService) SpringBeanUtil.getBean("handleMsgSendTaskService");
            // 根据不同的短信或者邮件类型发送短信和邮件
            Integer smsmailType = (Integer) kmMsg.getMsgData().get("smsmailType");// 短信发送类型，验证码，下单成功等
            List<String> mobilePhones = (List<String>) kmMsg.getMsgData().get("mobilePhones");
            List<Long> uidList = (List<Long>) kmMsg.getMsgData().get("uidList");
            Integer systemType = (Integer) kmMsg.getMsgData().get("systemType");// 系统参数
            Integer msgSendType = (Integer) kmMsg.getMsgData().get("msgSendType");// 立即发送和定时发送

            MessagelLogger.info((String) kmMsg.getMsgData().get("microBusinessNo"));
            MessagelLogger.info("传入的短信基本信息参数为，smsmailType【" + smsmailType + "】,mobilePhones"
                    + mobilePhones + "】uidList【" + uidList + "】systemType【" + systemType
                    + "】msgSendType【" + msgSendType + "】");

            if (msgSendType == null) {
                MessagelLogger.error("发送模式，立即发送和定时发送不能为空");
                throw new RuntimeException("发送模式，立即发送和定时发送不能为空");
            }
            if (mobilePhones == null || mobilePhones.size() <= 0) {
                MessagelLogger.error("手机号码不能为空");
                throw new RuntimeException("手机号码不能为空");
            }
            if (systemType == null) {
                MessagelLogger.error("系统类型不能为空");
                throw new RuntimeException("系统类型不能为空");
            }
            if (smsmailType == null) {
                MessagelLogger.error("短信邮件类型不能为空");
                throw new RuntimeException("短信邮件类型不能为空");
            }
            String mobilePhone = getSendMoblies(uidList, mobilePhones, smsmailType.intValue());
            if (mobilePhone == null) {
                MessagelLogger.error("手机号都不符合发送规定");
                throw new RuntimeException("手机号码不能为空");
            }
            // 条数通知
            if (EmailSendType.MSG_SYSTEMSENDCOUNT.getStatus() == smsmailType) {
                String sendCount = (String) kmMsg.getMsgData().get("sendCount");
                msgSendTask = generateMsgTaskService.generateEnchashmentSuccessMsg(mobilePhone,
                        systemType, sendCount);
            }
            // 验证码信息
            if (EmailSendType.MSG_VERIFY_CODE.getStatus() == smsmailType) {
                String mobileVerifyCode = (String) kmMsg.getMsgData().get("mobileVerifyCode");
                String modeType = (String) kmMsg.getMsgData().get("modeType");
                MessagelLogger.info("传入的验证码信息为【" + mobileVerifyCode + "】,验证码类型为:" + modeType + "】");
                if (mobileVerifyCode == null) {
                    MessagelLogger.error("验证码为空");
                    throw new RuntimeException("验证码为空");
                }
                if (modeType == null) {
                    MessagelLogger
                            .error("短信发送类型为空，需要传入类型，如：【orderTrail、noRegisterToMember、resetPwd】");
                    throw new RuntimeException(
                            "短信发送类型为空，需要传入类型，如：【orderTrail、noRegisterToMember、resetPwd】");
                }
                msgSendTask = generateMsgTaskService.generateMobileVerifyMsg(mobilePhone,
                        mobileVerifyCode, modeType, systemType);
            }
            // 注册
            if (EmailSendType.MSG_REGISTER.getStatus() == smsmailType) {
                String loginAccount = (String) kmMsg.getMsgData().get("loginAccount");
                String modelType = (String) kmMsg.getMsgData().get("modelType");
                if (loginAccount == null) {
                    MessagelLogger.info("注册名称不能为空");
                    throw new RuntimeException("注册类型不能为空");
                }
                if (modelType == null) {
                    MessagelLogger.info("注册类型不能为空");
                    throw new RuntimeException("注册类型不能为空");
                }
                msgSendTask = generateMsgTaskService.generateRegisterMsg(mobilePhone, modelType,
                        systemType);
            }
            // 发货
            if (EmailSendType.MSGDELIVERY.getStatus() == smsmailType) {
                String orderCode = (String) kmMsg.getMsgData().get("orderCode");
                String logisticsName = (String) kmMsg.getMsgData().get("logisticsName");
                String logisticsOrderNo = (String) kmMsg.getMsgData().get("logisticsOrderNo");
                String modelType = (String) kmMsg.getMsgData().get("modelType");
                if (StringUtils.isBlank(modelType)) {
                    MessagelLogger.error("modelType不能为空!");
                    throw new RuntimeException("modelType不能为空!");
                } else if (StringUtils.isBlank(orderCode)) {
                    MessagelLogger.error("订单号不能为空!");
                    throw new RuntimeException("订单号不能为空!");
                } else if (StringUtils.isBlank(logisticsName)) {
                    MessagelLogger.error("发货信息中,快递公司不能为空");
                    throw new RuntimeException("发货信息中,快递公司不能为空!");
                } else if (StringUtils.isBlank(logisticsOrderNo)) {
                    MessagelLogger.error("发货信息中,快递单号不能为空");
                    throw new RuntimeException("发货信息中,快递单号不能为空!");
                }
                msgSendTask = generateMsgTaskService.generateOrderShippedMsg(mobilePhone, orderCode,
                        logisticsName, logisticsOrderNo, modelType, systemType);
            }

            // 退换货
            if (EmailSendType.MSGRETURNGOODS.getStatus() == smsmailType) {
                String returnOrderCode = (String) kmMsg.getMsgData().get("returnOrderCode");
                String companyAddress = (String) kmMsg.getMsgData().get("companyAddress");
                String companyPhone = (String) kmMsg.getMsgData().get("companyPhone");
                String contectUserName = (String) kmMsg.getMsgData().get("contectUserName");
                String businessPhone = (String) kmMsg.getMsgData().get("businessPhone");
                Integer businessType = (Integer) kmMsg.getMsgData().get("businessType");

                String systemTitle = SystemEnums.getKeyByIndex(systemType);
                if (StringUtils.isBlank(returnOrderCode)) {
                    MessagelLogger.error("退货短信发送失败,退货单号不能为空");
                    throw new RuntimeException("退货短信发送失败,退货单号不能为空!");
                }
                if (null == businessType) {
                    MessagelLogger.error("退货短信发送失败,商家类型不能为空");
                    throw new RuntimeException("退货短信发送失败,商家类型不能为空!");
                }
                if (StringUtils.isBlank(companyAddress)) {// 退货地址为空
                    companyAddress = ConfigurationUtil.getString("companyAddress");
                }
                if (StringUtils.isBlank(companyPhone)) {// 退货电话为空
                    companyPhone = ConfigurationUtil.getString("companyPhone");
                }
                if (StringUtils.isBlank(contectUserName)) {// 康美收货人为空
                    contectUserName =
                            (ConfigurationUtil.getString("contectUserName_" + systemTitle));
                }
                msgSendTask = generateMsgTaskService.generateReturnGoodsMsg(mobilePhone,
                        companyAddress, companyPhone, contectUserName, returnOrderCode, systemType,
                        businessType, businessPhone);
            }
            // /取现成功
            if (EmailSendType.MSG_ENCHASHMENTSUCCESS.getStatus() == smsmailType) {
                msgSendTask = generateMsgTaskService.generateEnchashmentSuccessMsg(mobilePhone,
                        systemType);
            }
            // 取现拒绝
            if (EmailSendType.MSG_ENCHASHMENTREFUSE.getStatus() == smsmailType) {
                msgSendTask = generateMsgTaskService.generateEnchashmentRefuseMsg(mobilePhone,
                        systemType);
            }
            /* 提现失败提醒 */
            if (EmailSendType.MSG_ENCHASHMENTERROR.getStatus() == smsmailType) {
                msgSendTask =
                        generateMsgTaskService.generateEnchashmentErrorMsg(mobilePhone, systemType);
            }

            if (EmailSendType.MSG_SETTLEMENT.getStatus() == smsmailType) {
                String settlementNo = (String) kmMsg.getMsgData().get("settlementNo");
                MessagelLogger.info("结算通知:结算编号【" + settlementNo + "】");
                if (StringUtils.isBlank(settlementNo)) {
                    MessagelLogger.error("结算通知短信发送失败,结算编号不能为空!");
                    throw new RuntimeException("结算通知短信发送失败,结算编号不能为空!");
                }
                msgSendTask = generateMsgTaskService.generatesettlementMsg(mobilePhone,
                        settlementNo, systemType);
            }

            // 资质重审
            if (EmailSendType.MSG_APTITUDE_APPLY.getStatus() == smsmailType) {
                String companyName = (String) kmMsg.getMsgData().get("companyName");
                MessagelLogger.info("资质重审商家名为：" + companyName + "");
                if (StringUtils.isBlank(companyName)) {
                    MessagelLogger.error("商家名称不能为空");
                    throw new RuntimeException("商家名称不能为空!");
                }
                msgSendTask = generateMsgTaskService.generateAptitudeAppMsg(mobilePhone,
                        companyName, systemType);
            }

            // 活动通知
            if (EmailSendType.MSG_PROMOTION.getStatus() == smsmailType) {
                String Count = (String) kmMsg.getMsgData().get("Count");
                MessagelLogger.info("优惠券百分比：" + Count + "");
                if (StringUtils.isBlank(Count)) {
                    MessagelLogger.error("优惠券百分比不能为空");
                    throw new RuntimeException("优惠券百分比不能为空!");
                }
                String promotionName = (String) kmMsg.getMsgData().get("promotionName");
                MessagelLogger.info("促销活动名：" + promotionName + "");
                if (StringUtils.isBlank(promotionName)) {
                    MessagelLogger.error("促销活动名不能为空");
                    throw new RuntimeException("促销活动名不能为空!");
                }
                msgSendTask = generateMsgTaskService.generatePromotionCouponMsg(mobilePhone, Count,
                        promotionName, systemType);
            }

            // 预售尾款支付通知
            if (EmailSendType.MSG_PRESELL_FINALPAY.getStatus() == smsmailType) {
                String orderPayDate = (String) kmMsg.getMsgData().get("orderPayDate");// 订单支付时间
                String productSkucode = (String) kmMsg.getMsgData().get("productSkucode");// 商品skucode
                String finalpayEndTime = (String) kmMsg.getMsgData().get("finalpayEndTime");// 尾款支付结束时间
                if (StringUtils.isBlank(orderPayDate)) {
                    MessagelLogger.error("订单支付时间不能为空");
                    throw new RuntimeException("订单支付时间不能为空!");
                } else if (StringUtils.isBlank(productSkucode)) {
                    MessagelLogger.error("商品skucode不能为空");
                    throw new RuntimeException("商品skucode不能为空!");
                } else if (StringUtils.isBlank(finalpayEndTime)) {
                    MessagelLogger.error("尾款支付结束时间不能为空");
                    throw new RuntimeException("尾款支付结束时间不能为空!");
                }
                msgSendTask = generateMsgTaskService.generatePresellFinalpayMsg(mobilePhone,
                        orderPayDate, productSkucode, finalpayEndTime, systemType);
            }


            // 优惠劵到期
            if (EmailSendType.MSGCOUPONEXPIRING.getStatus() == smsmailType) {
                msgSendTask =
                        generateMsgTaskService.generateCouponExpiringMsg(mobilePhone, systemType);
            }
            // 促销短信
            if (EmailSendType.MSGSPREAD.getStatus() == smsmailType) {
                Map<String, Object> map = new HashMap<String, Object>();
                String msgContent = (String) kmMsg.getMsgData().get("msgContent");
                MessagelLogger.info("传入的促销短信信息为：msgContent为【" + msgContent + "】");
                if (null == uidList || uidList.size() <= 0) {
                    MessagelLogger.error("会员ID不能为空");
                    for (int i = 0; i < mobilePhones.size(); i++) {
                        map.put(mobilePhones.get(i), MessageConstants.SEND_BACK_STATUS_EXCEPTION);
                    }
                }
                if (null == msgContent) {
                    MessagelLogger.error("发送内容不能为空");
                    for (int i = 0; i < mobilePhones.size(); i++) {
                        map.put(mobilePhones.get(i), MessageConstants.SEND_BACK_STATUS_EXCEPTION);
                    }
                }
                msgSendTask = generateMsgTaskService.generateSpreadMsg(mobilePhone, msgContent,
                        msgSendType);
            }

            // 去掉语音验证
            /*
             * if (EmailSendType.MSG_VOICE_VERIFY_CODE.getStatus() == smsmailType) { String
             * accountSid = "53d81774b0dc07e130c8cc58746d703d"; String token =
             * "502fb4a90566b99bc8fcc77efdf75c81"; String appId =
             * "661b3f9dcfd7491e9c417052b5ab180b"; String mobileVerifyCode = (String)
             * kmMsg.getMsgData().get("mobileVerifyCode"); JsonReqClient jsonReqClient = new
             * JsonReqClient(); String re = jsonReqClient.voiceCode(accountSid, token, appId,
             * mobilePhone, mobileVerifyCode); } else {
             */

            handleMsgSendTaskService.handleMessage(msgSendTask);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
