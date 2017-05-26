package com.kmzyc.framework.socket;
//package com.km.framework.socket;
//
//import java.util.Map;
//
//import org.slf4j.Logger;import org.slf4j.LoggerFactory;
//
//import com.km.framework.mq.bean.KmMsg;
//import com.km.framework.socket.bean.SocketMsg;
//import com.km.framework.socket.bean.SocketMsg.MsgType;
//import com.km.framework.socket.exception.SocketRemoteException;
//
///**
// * SocketEx 远程调用工具类
// * @author zhengxin
// * @version 1.0
// */
//public final class SocketRemoteTool {
//
//	static Logger logger = LoggerFactory.getLogger(SocketRemoteTool.class);
//
//	/** 响应的消息实体*/
//	private static SocketMsg response;
//
//	/** 默认连接超时时间*/
//	static int connectTimeOut = 15;
//
//	/** 默认获取数据超时时间*/
//	static int readTimeout = 15;
//
//	/** kmib IP*/
//	static final String KMIB_HOST;
//
//	/** kmib port*/
//	static final int KMIB_PORT;
//
//	/**
//	 * @exception  IllegalArgumentException 当配置文件kmibAddress参数异常时
//	 */
//	static{
//		KMIB_HOST = SocketUtil.getHostByConfKey("kmibSocketAddress");
//		KMIB_PORT = SocketUtil.getPortByConfKey("kmibSocketAddress");
//	}
//
//	/**
//	 * 调用远端方法
//	 * @param destSysCode 目的系统模块编号（01订单管理系统，02产品管理系统，03客户管理系统，04信息管理系统
//	 * 05 B2C系统，06 B2B系统，08中药材商城， 09短信邮件模块）
//	 * @param invokeFullName 远程调用的名称 格式为:类名.方法名
//	 * @param msg 发送的参数
//	 * @exception NullPointerException 当发送的消息实体为空时
//	 * @exception SocketRemoteException 调用远端方法失败时
//	 * @return Map<String, Object> 响应的参数
//	 */
//	public static Map<String, Object> getRemote(String destSysCode, String invokeFullName, final Map<String, Object> msg){
//		return getRemote(destSysCode, invokeFullName, msg, connectTimeOut, readTimeout);
//	}
//
//	/**
//	 * 调用远端方法
//	 * @param destSysCode 目的系统模块编号（01订单管理系统，02产品管理系统，03客户管理系统，04信息管理系统
//	 * 05 B2C系统，06 B2B系统，08中药材商城， 09短信邮件模块）
//	 * @param invokeFullName 远程调用的名称 格式为:类名.方法名
//	 * @param msg 发送的参数
//	 * @param connectTimeOut 连接超时时间
//	 * @param readTimeout 获取数据超时时间
//	 * @exception NullPointerException 当发送的消息实体为空时
//	 * @exception SocketRemoteException 调用远端方法失败时
//	 * @return Map<String, Object> 响应的参数
//	 */
//	public static synchronized Map<String, Object> getRemote(String destSysCode, String invokeFullName,
//			final Map<String, Object> msg, int connectTimeOut, int readTimeout){
//		if(invokeFullName == null || msg == null || destSysCode == null || destSysCode.length() < 1)
//			throw new NullPointerException("destSysCode、invokeFullName与msg不能为空");
//		if(invokeFullName.indexOf(".") == -1)
//			throw new IllegalArgumentException("invokeFullName:"+invokeFullName+" 格式应为 类名.方法名");
//
//		try {
//			//封装参数
//			SocketMsg request = new SocketMsg(MsgType.REQUEST, SocketServerInitListener.SERVER_ADDRESS,
//					destSysCode, invokeFullName, msg, readTimeout);
//			long start = System.currentTimeMillis();
//			request.setSendTime(start);
//			//发送消息给KMIB
//			SocketUtil.writeMsg(SocketServerInitListener.getWork(), request, KMIB_HOST, KMIB_PORT,
//					connectTimeOut * 1000, false);
//
//			//等待服务端接收到KMIB响应
//			synchronized (SocketRemoteTool.class) {
//				response = null;
//				SocketRemoteTool.class.wait(readTimeout * 1000);
//			}
//			if((System.currentTimeMillis() - start) > (readTimeout * 1000))
//				logger.warn("调用远端方法超时 destSysCode:" + destSysCode + " invokeMethodName:" + invokeFullName
//						+ " readTimeOut:" + readTimeout + " msgData:" + msg);
//		} catch (Exception e) {
//			throw new SocketRemoteException("调用远端方法失败", e);
//		}
//		//如果远端执行方法失败则抛出运行时异常
//		if(response != null){
//			if(response.getExceptionMsg() != null)
//				throw new SocketRemoteException(response.getExceptionMsg());
//			else
//				return response.getMsgData();
//		}
//		return null;
//	}
//
//	/**
//	 * 服务端接收到响应时调用
//	 * @param response 响应的消息实体
//	 * @see SocketServerInitListener
//	 */
//	static void setResponse(SocketMsg response){
//		SocketRemoteTool.response = response;
//	}
//
//	/**
//	 * 异步调用远端方法
//	 * @param destSysCode 目的系统模块编号（01订单管理系统，02产品管理系统，03客户管理系统，04信息管理系统
//	 * 05 B2C系统，06 B2B系统，08中药材商城， 09短信邮件模块）
//	 * @param invokeFullName 远程调用的名称 格式为:类名.方法名
//	 * @param msg 发送的参数
//	 * @exception NullPointerException 当发送的消息实体为空时
//	 * @exception SocketRemoteException 调用远端方法失败时
//	 */
//	public static void send(String destSysCode, String invokeFullName, final Map<String, Object> msg){
//		send(destSysCode, invokeFullName, msg, null);
//	}
//
//	/**
//	 * 异步调用远端方法
//	 * @param destSysCode 目的系统模块编号（01订单管理系统，02产品管理系统，03客户管理系统，04信息管理系统
//	 * 05 B2C系统，06 B2B系统，08中药材商城， 09短信邮件模块）
//	 * @param invokeFullName 远程调用的名称 格式为:类名.方法名
//	 * @param msg 发送的参数
//	 * @param callbackFullName 回调方法名称 格式为：类名.方法名
//	 * @exception NullPointerException 当发送的消息实体为空时
//	 * @exception SocketRemoteException 调用远端方法失败时
//	 */
//	public static void send(String destSysCode, String invokeFullName, final Map<String, Object> msg,
//			String callbackFullName){
//		if(invokeFullName == null || msg == null || destSysCode == null || destSysCode.length() < 1)
//			throw new NullPointerException("destSysCode、invokeFullName与msg不能为空");
//		if(invokeFullName.indexOf(".") == -1)
//			throw new IllegalArgumentException("invokeFullName:" + invokeFullName + " 格式应为 类名.方法名");
//		if(callbackFullName != null && callbackFullName.indexOf(".") == -1)
//			throw new IllegalArgumentException("callbackFullName:" + callbackFullName + " 格式应为 类名.方法名");
//
//		try {
//			//封装参数
//			SocketMsg request = new SocketMsg(MsgType.REQUEST, SocketServerInitListener.SERVER_ADDRESS,
//					destSysCode, invokeFullName, msg, callbackFullName);
//			//发送消息给KMIB
//			SocketUtil.writeMsg(SocketServerInitListener.getWork(), request, KMIB_HOST, KMIB_PORT,
//					connectTimeOut * 1000, true);
//		} catch (Exception e) {
//			throw new SocketRemoteException("发送消息失败", e);
//		}
//	}
//
//	/**
//	 * 发送消息
//	 * @param msg 消息实体
//	 * @exception NullPointerException 当发送的消息实体为空时
//	 * @exception SocketRemoteException 发送消息失败时
//	 */
//	public static void send(KmMsg msg){
//		if(msg == null || msg.getMsgCode() == null || msg.getMsgData() == null)
//			throw new NullPointerException("msg、msgCode与msgData不能为空");
//		try {
//			//发送消息给KMIB
//			SocketUtil.writeMsg(SocketServerInitListener.getWork(), msg, KMIB_HOST, KMIB_PORT,
//					connectTimeOut * 1000, true);
//		} catch (Exception e) {
//			throw new SocketRemoteException("发送消息失败", e);
//		}
//	}
//}
