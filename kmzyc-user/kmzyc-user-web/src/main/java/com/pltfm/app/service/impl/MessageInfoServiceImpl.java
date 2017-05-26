package com.pltfm.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.km.framework.mq.Sender;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.MessageConstants;
import com.pltfm.app.service.MessageInfoService;
import com.pltfm.app.util.Constants;

/**
 *
 * 
 * @author cjm
 * @since 2013-8-8
 */
@Component(value = "messageInfoService")
public class MessageInfoServiceImpl implements MessageInfoService {

    private static Logger logger = Logger.getLogger(MessageInfoServiceImpl.class);


    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name = "emailMsg")
    private Destination destination;

    /**
     * 推广短信发送
     */
    @Override
    public Map<String, Object> sendMsgBySpread(List<Long> uidList, List<String> mobilePhoneList,
            String msg, Integer sendType, Integer systemType) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (null == mobilePhoneList || mobilePhoneList.size() <= 0) {
            logger.error("手机号不能为空");
            for (int i = 0; i < uidList.size(); i++) {
                map.put(uidList.get(i).toString(), Constants.SEND_BACK_STATUS_EXCEPTION);
            }
            return map;
        }
        KmMsg kmMsg = new KmMsg("6000", true);// 报文编号为1000,参数2为是否回复
        kmMsg.getMsgData().put("kmMsgType", MessageConstants.KMMSG_MOBIL);
        kmMsg.getMsgData().put("smsmailType", EmailSendType.MSGSPREAD.getStatus());
        kmMsg.getMsgData().put("systemType", MessageConstants.KMMSG_SYSTEM_TYPE_B2B);
        kmMsg.getMsgData().put("mobilePhones", mobilePhoneList);
        kmMsg.getMsgData().put("msgSendType", MessageConstants.EM_SEND_TYPE_IMM);
        kmMsg.getMsgData().put("uidList", uidList);
        kmMsg.getMsgData().put("msgContent", msg);
        try {
            Sender.send(kmMsg, destination, jmsTemplate);
        } catch (Exception e) {
            logger.error("发送支付成功短信失败" + e.getMessage());
            for (int i = 0; i < mobilePhoneList.size(); i++) {
                map.put(mobilePhoneList.get(i), Constants.SEND_BACK_STATUS_EXCEPTION);
            }
        }
        for (int i = 0; i < mobilePhoneList.size(); i++) {
            map.put(mobilePhoneList.get(i), Constants.SEND_BACK_STATUS_SUCCESS);
        }
        return map;
    }

}
