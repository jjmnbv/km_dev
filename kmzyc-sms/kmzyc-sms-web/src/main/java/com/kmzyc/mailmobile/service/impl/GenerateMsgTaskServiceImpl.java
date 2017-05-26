package com.kmzyc.mailmobile.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.MessageConstants;
import com.kmzyc.framework.constants.MessageTypeEnum;
import com.kmzyc.mailmobile.model.MsgSendTask;
import com.kmzyc.mailmobile.service.GenerateMsgTaskService;
import com.kmzyc.mframework.constants.SystemEnums;
import com.kmzyc.mframework.logger.MessagelLogger;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Service("generateMsgTaskService")
public class GenerateMsgTaskServiceImpl implements GenerateMsgTaskService {

    private Logger logger = Logger.getLogger(GenerateMsgTaskServiceImpl.class);

    /**
     * 公共方法，产生短信发送任务对象
     * 
     * @param receiver
     * @param content
     * @param type
     * @param msgType
     * @param sendTime
     * @param isSuccess
     * @param sendCount
     * @param createTime
     * @param updateTime
     * @return
     */
    private MsgSendTask generateMsgSendTask(String receiver, String content, Integer type,
            Integer msgType, Date sendTime, Integer isSuccess, Integer sendCount, Date createTime,
            Date updateTime) {
        MsgSendTask msgSendTask = new MsgSendTask();
        msgSendTask.setReceiver(receiver);
        msgSendTask.setContent(content);
        msgSendTask.setType(type);
        msgSendTask.setMsgType(msgType);
        msgSendTask.setSendTime(sendTime);
        msgSendTask.setIsSuccess(isSuccess);
        msgSendTask.setSendCount(sendCount);
        if (createTime == null) {
            msgSendTask.setCreateTime(new Date());
        }
        if (updateTime == null) {
            msgSendTask.setUpdateTime(new Date());
        }
        return msgSendTask;

    }

    /**
     * 组装验证码发送对象
     */
    @Override
    public MsgSendTask generateMobileVerifyMsg(String mobilePhone, String mobileVerifyCode,
            String modeType, Integer systemType) {
        MessagelLogger.info("传入的参数为手机：【" + mobilePhone + "】,验证码为【" + mobileVerifyCode + "】，flag为【"
                + modeType + "】，系统类型为1：b2b，2：zyc【" + systemType + "】");
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());
        // 根据类型或许不同的模板组成完整的发送信息
        String completeSendMSg = ConfigurationUtil.getString(modeType + "_" + systemTitle);
        completeSendMSg = completeSendMSg.replace("mobileVerifyCode", mobileVerifyCode);// 替换验证码
        MsgSendTask msgSendTask =
                generateMsgSendTask(mobilePhone, completeSendMSg, MessageConstants.EM_SEND_TYPE_IMM,
                        EmailSendType.MSG_VERIFY_CODE.getStatus(), null, null, null, null, null);
        return msgSendTask;
    }

    /**
     * 组装注册发送对象
     */
    @Override
    public MsgSendTask generateRegisterMsg(String mobilePhone, String modeType,
            Integer systemType) {
        MessagelLogger.info("传入的参数为手机：【" + mobilePhone + "】，modeType为【" + modeType
                + "】，系统类型为1：b2b，2：zyc【" + systemType + "】");
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());
        String completeSendMSg = ConfigurationUtil.getString(modeType + "_" + systemTitle);
        MsgSendTask msgSendTask =
                generateMsgSendTask(mobilePhone, completeSendMSg, MessageConstants.EM_SEND_TYPE_IMM,
                        EmailSendType.MSG_REGISTER.getStatus(), null, null, null, null, null);
        return msgSendTask;
    }

    /**
     * 组装发货成功发送对象
     */
    @Override
    public MsgSendTask generateOrderShippedMsg(String mobilePhone, String orderCode,
            String logisticsName, String logisticsOrderNo, String modelType, Integer systemType) {
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());
        String completeSendMSg = ConfigurationUtil.getString(modelType + "_" + systemTitle);
        completeSendMSg = completeSendMSg.replace("orderCode", orderCode)
                .replace("logisticsOrderNo", logisticsOrderNo)
                .replace("logisticsName", logisticsName);
        MsgSendTask msgSendTask =
                generateMsgSendTask(mobilePhone, completeSendMSg, MessageConstants.EM_SEND_TYPE_IMM,
                        EmailSendType.MSGDELIVERY.getStatus(), null, null, null, null, null);
        return msgSendTask;
    }

    @Override
    public MsgSendTask generateReturnGoodsMsg(String mobilePhone, String companyAddress,
            String companyPhone, String contectUserName, String returnOrderCode, Integer systemType,
            Integer businessType, String businessPhone) {
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());
        String completeSendMSg = null;
        String business = null;
        if (businessType == 1) {
            logger.info("退换货商家类型" + "【" + businessType + "】康美中药城");
            completeSendMSg = ConfigurationUtil
                    .getString(MessageTypeEnum.RETURNGOOD.getStatus() + "_" + systemTitle);
            business = "康美中药城";
            completeSendMSg = completeSendMSg.replace("returnOrderCode", returnOrderCode)
                    .replace("businessType", business).replace("businessPhone", businessPhone);
        } else if (businessType == 2) {
            logger.info("退换货商家类型" + "【" + businessType + "】商家");
            completeSendMSg = ConfigurationUtil
                    .getString(MessageTypeEnum.RETURNGOOD.getStatus() + "_" + systemTitle);
            business = "商家";
            completeSendMSg = completeSendMSg.replace("returnOrderCode", returnOrderCode)
                    .replace("businessType", business).replace("businessPhone", businessPhone);
        } else if (businessType == 3) {
            logger.info("退换货商家类型" + "【" + businessType + "】商家,无联系方式");
            completeSendMSg = ConfigurationUtil.getString(
                    "business_" + MessageTypeEnum.RETURNGOOD.getStatus() + "_" + systemTitle);
            business = "商家";
            completeSendMSg = completeSendMSg.replace("returnOrderCode", returnOrderCode)
                    .replace("businessType", business);
        }

        MsgSendTask msgSendTask =
                generateMsgSendTask(mobilePhone, completeSendMSg, MessageConstants.EM_SEND_TYPE_IMM,
                        EmailSendType.MSGRETURNGOODS.getStatus(), null, null, null, null, null);
        return msgSendTask;
    }


    @Override
    public MsgSendTask generateEnchashmentSuccessMsg(String mobilePhones, Integer systemType) {
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());// 判断系统
        String completeSendMSg = ConfigurationUtil
                .getString(MessageTypeEnum.ENCHASHMENTSUCCESS.getStatus() + "_" + systemTitle);
        MsgSendTask msgSendTask = generateMsgSendTask(mobilePhones, completeSendMSg,
                MessageConstants.EM_SEND_TYPE_IMM, EmailSendType.MSG_ENCHASHMENTSUCCESS.getStatus(),
                new Date(), null, null, null, null);
        return msgSendTask;
    }

    @Override
    public MsgSendTask generateEnchashmentRefuseMsg(String mobilePhones, Integer systemType) {
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());// 判断系统
        String completeSendMSg = ConfigurationUtil
                .getString(MessageTypeEnum.MSG_ENCHASHMENTREFUSE.getStatus() + "_" + systemTitle);
        MsgSendTask msgSendTask = generateMsgSendTask(mobilePhones, completeSendMSg,
                MessageConstants.EM_SEND_TYPE_IMM, EmailSendType.MSG_ENCHASHMENTREFUSE.getStatus(),
                new Date(), null, null, null, null);
        return msgSendTask;
    }

    @Override
    public MsgSendTask generateEnchashmentErrorMsg(String mobilePhones, Integer systemType) {
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());// 判断系统
        String completeSendMSg = ConfigurationUtil
                .getString(MessageTypeEnum.MSG_ENCHASHMENTERROR.getStatus() + "_" + systemTitle);
        MsgSendTask msgSendTask = generateMsgSendTask(mobilePhones, completeSendMSg,
                MessageConstants.EM_SEND_TYPE_IMM, EmailSendType.MSG_ENCHASHMENTERROR.getStatus(),
                new Date(), null, null, null, null);
        return msgSendTask;
    }



    @Override
    public MsgSendTask generatesettlementMsg(String mobilePhones, String settlementNo,
            Integer systemType) {
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());// 判断系统
        String completeSendMSg = ConfigurationUtil
                .getString(MessageTypeEnum.MSG_SETTLEMENT.getStatus() + "_" + systemTitle);
        completeSendMSg = completeSendMSg.replace("settlementNo", settlementNo);
        MsgSendTask msgSendTask = generateMsgSendTask(mobilePhones, completeSendMSg,
                MessageConstants.EM_SEND_TYPE_IMM, EmailSendType.MSG_SETTLEMENT.getStatus(),
                new Date(), null, null, null, null);
        return msgSendTask;
    }


    @Override
    public MsgSendTask generateCouponExpiringMsg(String mobilePhones, Integer systemType) {
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());// 判断系统
        String completeSendMSg = ConfigurationUtil
                .getString(MessageTypeEnum.COUPONEXPIRING.getStatus() + "_" + systemTitle);
        MsgSendTask msgSendTask = generateMsgSendTask(mobilePhones, completeSendMSg,
                MessageConstants.EM_SEND_TYPE_IMM, EmailSendType.MSGCOUPONEXPIRING.getStatus(),
                new Date(), null, null, null, null);
        return msgSendTask;
    }

    @Override
    public MsgSendTask generateSpreadMsg(String mobilePhones, String msgContent, Integer timeType) {
        MsgSendTask msgSendTask = generateMsgSendTask(mobilePhones, msgContent, timeType,
                EmailSendType.MSGSPREAD.getStatus(), new Date(), null, null, null, null);
        return msgSendTask;
    }


    @Override
    public MsgSendTask generateAptitudeAppMsg(String mobilePhones, String companyName,
            Integer systemType) {
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());// 判断系统
        String completeSendMSg = ConfigurationUtil
                .getString(MessageTypeEnum.MSG_APTITUDE_APPLY.getStatus() + "_" + systemTitle);
        completeSendMSg = completeSendMSg.replace("companyName", companyName);
        MsgSendTask msgSendTask = generateMsgSendTask(mobilePhones, completeSendMSg,
                MessageConstants.EM_SEND_TYPE_IMM, EmailSendType.MSG_APTITUDE_APPLY.getStatus(),
                new Date(), null, null, null, null);
        return msgSendTask;
    }



    @Override
    public MsgSendTask generatePresellFinalpayMsg(String mobilePhones, String orderPayDate,
            String productSkucode, String finalpayEndTime, Integer systemType) {
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());// 判断系统
        String completeSendMSg = ConfigurationUtil
                .getString(MessageTypeEnum.MSG_PRESELL_FINALPAY.getStatus() + "_" + systemTitle);
        completeSendMSg = completeSendMSg.replace("orderPayDate", orderPayDate)
                .replace("productSkucode", productSkucode)
                .replace("finalpayEndTime", finalpayEndTime);
        MsgSendTask msgSendTask = generateMsgSendTask(mobilePhones, completeSendMSg,
                MessageConstants.EM_SEND_TYPE_IMM, EmailSendType.MSG_PRESELL_FINALPAY.getStatus(),
                new Date(), null, null, null, null);
        return msgSendTask;
    }

    @Override
    public MsgSendTask generateEnchashmentSuccessMsg(String mobilePhones, Integer systemType,
            String sendCount) {
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());// 判断系统
        String completeSendMSg = ConfigurationUtil
                .getString(MessageTypeEnum.MSG_SYSTEMSENDCOUNT.getStatus() + "_" + systemTitle);
        completeSendMSg = completeSendMSg.replace("sendCount", sendCount);
        MsgSendTask msgSendTask = generateMsgSendTask(mobilePhones, completeSendMSg,
                MessageConstants.EM_SEND_TYPE_IMM, EmailSendType.MSG_SYSTEMSENDCOUNT.getStatus(),
                new Date(), null, null, null, null);
        return msgSendTask;
    }

    @Override
    public MsgSendTask generatePromotionCouponMsg(String mobilePhones, String count,
            String promotionName, Integer systemType) {
        String systemTitle = SystemEnums.getKeyByIndex(systemType.intValue());// 判断系统
        String completeSendMSg = ConfigurationUtil
                .getString(MessageTypeEnum.MSG_PROMOTION.getStatus() + "_" + systemTitle);
        completeSendMSg =
                completeSendMSg.replace("Count", count).replace("promotionName", promotionName);
        MsgSendTask msgSendTask = generateMsgSendTask(mobilePhones, completeSendMSg,
                MessageConstants.EM_SEND_TYPE_IMM, EmailSendType.MSG_PROMOTION.getStatus(),
                new Date(), null, null, null, null);
        return msgSendTask;
    }


}
