package com.kmzyc.framework.socket.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * 消息实体
 * @author zhengxin
 */
public class SocketMsg implements Serializable{

	private static final long serialVersionUID = 1L;

	private boolean isAsyn;
	/** 消息类型 request response*/
	private MsgType msgType;
	/** 发送模块服务端监听地址 */
	private String sendAddress;

	/** 发送模块系统编号 */
	private String sendSysCode;

	/** 目的模块系统编号*/
	private String destSysCode;

	/** 发送的时间，utc时间 */
	private long sendTime;

	/** 调用的远程类名*/
	private String invokeClassName;
	/** 调用的远程方法名*/
	private String invokeMethodName;

	/** 消息发送体*/
	private Map<String, Object> msgData;

	/** 异常信息*/
	private String exceptionMsg;

	private int readTimeout;

	private String callbackClassName;

	private String callbackMethodName;

	public SocketMsg() {
		super();
	}

	public SocketMsg(MsgType msgType, String sendAddress, String destSysCode, String invokeFullName,
			Map<String, Object> msgData, int readTimeout) {
		super();
		this.isAsyn = false;
		this.msgType = msgType;
		this.sendAddress = sendAddress;
		this.destSysCode = destSysCode;
		if(invokeFullName != null){
			int mid = invokeFullName.indexOf(".");
			//类名首字母转小写
			StringBuilder buff = new StringBuilder(invokeFullName.substring(0, mid));
			buff.setCharAt(0, Character.toLowerCase(buff.charAt(0)));
			this.invokeClassName = buff.toString();
			this.invokeMethodName = invokeFullName.substring(mid + 1);
		}
		this.msgData = msgData;
		this.readTimeout = readTimeout;
	}

	public SocketMsg(MsgType msgType, String sendAddress, String destSysCode, String invokeFullName,
			Map<String, Object> msgData, String callbackFullName) {
		this(msgType, sendAddress, destSysCode, invokeFullName, msgData, -1);
		this.isAsyn = true;
		if(callbackFullName != null){
			int mid = callbackFullName.indexOf(".");
			//类名首字母转小写
			StringBuilder buff = new StringBuilder(callbackFullName.substring(0, mid));
			buff.setCharAt(0, Character.toLowerCase(buff.charAt(0)));
			this.callbackClassName = buff.toString();
			this.callbackMethodName = callbackFullName.substring(mid + 1);
		}
		this.sendTime = System.currentTimeMillis();
	}

	public boolean isReadTimeout(){
		return (System.currentTimeMillis() - this.sendTime)/1000 > this.readTimeout;
	}

	/** getter and setter*/

	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	public String getDestSysCode() {
		return destSysCode;
	}

	public void setDestSysCode(String destSysCode) {
		this.destSysCode = destSysCode;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public Map<String, Object> getMsgData() {
		return msgData;
	}

	public void setMsgData(Map<String, Object> msgData) {
		this.msgData = msgData;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getSendSysCode() {
		return sendSysCode;
	}

	public void setSendSysCode(String sendSysCode) {
		this.sendSysCode = sendSysCode;
	}

	public String getInvokeMethodName() {
		return invokeMethodName;
	}

	public String getInvokeClassName() {
		return invokeClassName;
	}

	public void setInvokeClassName(String invokeClassName) {
		this.invokeClassName = invokeClassName;
	}

	public void setInvokeMethodName(String invokeMethodName) {
		this.invokeMethodName = invokeMethodName;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public boolean isAsyn() {
		return isAsyn;
	}

	public void setAsyn(boolean isAsyn) {
		this.isAsyn = isAsyn;
	}

	public String getCallbackClassName() {
		return callbackClassName;
	}

	public void setCallbackClassName(String callbackClassName) {
		this.callbackClassName = callbackClassName;
	}

	public String getCallbackMethodName() {
		return callbackMethodName;
	}

	public void setCallbackMethodName(String callbackMethodName) {
		this.callbackMethodName = callbackMethodName;
	}

	public static enum MsgType{REQUEST, RESPONSE}

	@Override
	public String toString() {
		return "SocketMsg [isAsyn=" + isAsyn + ", msgType=" + msgType
				+ ", sendAddress=" + sendAddress + ", sendSysCode="
				+ sendSysCode + ", destSysCode=" + destSysCode + ", sendTime="
				+ sendTime + ", invokeClassName=" + invokeClassName
				+ ", invokeMethodName=" + invokeMethodName + ", msgData="
				+ msgData + ", exceptionMsg=" + exceptionMsg + ", readTimeout="
				+ readTimeout + ", callbackClassName=" + callbackClassName
				+ ", callbackMethodName=" + callbackMethodName + "]";
	}

}
