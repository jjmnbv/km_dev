package com.kmzyc.framework.mq.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息数据对象
 *
 * @author Administrator
 */
public class KmMsg implements Serializable {
    private static final long serialVersionUID = 3384749225462814216L;

    /**
     * 消息唯一标识，默认为系统生成
     */
    private String msgID;
    /**
     * 消息编号
     */
    private String msgCode;
    /**
     * 是否需要确认
     */
    private boolean isReply;
    /**
     * 发送的时间，utc时间
     */
    private long sendTime;
    /** 需要重发的次数 0为不需要重发 */
//	private int isNeedReSendnum;
    /**
     * 发送次数
     */
    private int sendCount = 0;
    /**
     * 消息发送体
     */
    private Map<String, Object> msgData = new HashMap<String, Object>();

    public KmMsg() {
        msgID = UUIDHexGenerator.getInstance().generate();
    }


    public KmMsg(String msgCode, boolean isReply) {
        super();
        this.msgCode = msgCode;
        this.isReply = isReply;
        //this.isNeedReSendnum = isNeedReSendnum;
        this.msgID = UUIDHexGenerator.getInstance().generate();
        this.sendTime = System.currentTimeMillis();
        this.sendCount = 0;
    }


    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }


    public Map<String, Object> getMsgData() {
        return msgData;
    }

    public void setMsgData(Map<String, Object> msgData) {
        this.msgData = msgData;
    }


//	public void putMsgData(String key, Object value) {
//		msgData.put(key, value);
//	}
//
//	public Object getMsgData(String key) {
//		return msgData.get(key);
//	}
//
//	public Map<String, Object> getMsgProperties() {
//		return msgData;
//	}
//
//	public void setMsgProperties(Map<String, Object> msgProperties) {
//		this.msgData = msgProperties;
//	}

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

//	public int getIsNeedReSendnum() {
//		return isNeedReSendnum;
//	}
//
//	public void setIsNeedReSendnum(int isNeedReSendnum) {
//		this.isNeedReSendnum = isNeedReSendnum;
//	}

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }


    public boolean isReply() {
        return isReply;
    }


    public void setReply(boolean isReply) {
        this.isReply = isReply;
    }

}
