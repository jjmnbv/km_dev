package com.kmzyc.framework.socket;
//package com.km.framework.socket;
//
//
//
//import java.lang.reflect.Method;
//import java.util.Map;
//
//import org.jboss.netty.channel.ChannelHandler;
//import org.jboss.netty.channel.ChannelHandlerContext;
//import org.slf4j.Logger;import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//
//import com.km.framework.drools.RulesEngine;
//import com.km.framework.mq.bean.KmMsg;
//import com.km.framework.socket.bean.SocketMsg;
//import com.km.framework.socket.bean.SocketMsg.MsgType;
//
///**
// * 服务端接收消息处理器
// * @author zhengxin
// */
//@ChannelHandler.Sharable
//public class SocketReceiveHandler extends ChannelInboundHandlerAdapter{
//
//	static Logger logger = LoggerFactory.getLogger(SocketReceiveHandler.class);
//
//	public BeanFactory beanFactory;
//
//	public SocketReceiveHandler(BeanFactory beanFactory){
//		this.beanFactory = beanFactory;
//	}
//
//	/**
//	 * Socket通道接收消息事件
//	 * @param ctx ChannelHandler上下文
//	 * @param msg 消息实体
//	 */
//	@Override
//	public void channelRead(ChannelHandlerContext ctx, Object msg)
//			throws Exception {
//		if(msg instanceof SocketMsg){
//			SocketMsg socketMsg = (SocketMsg)msg;
//			switch (socketMsg.getMsgType()) {
//			case REQUEST:
//				request(socketMsg);
//				break;
//			case RESPONSE:
//				response(socketMsg);
//				break;
//			}
//		} else if (msg instanceof KmMsg){
//			RulesEngine.insert(msg);
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	private void request(SocketMsg socketMsg) {
//		Map<String, Object> responseData = null;
//		String exceptionMsg = null;
//		boolean isSendResponse = !socketMsg.isAsyn() ||
//				socketMsg.getCallbackClassName() != null;
//		String invokeClass = socketMsg.getInvokeClassName();
//		String invokeMethodName = socketMsg.getInvokeMethodName();
//		try {
//			//从spring上下文中获取调用的bean
//			BizProcesser bizProcess = (BizProcesser) beanFactory.getBean(invokeClass);
//			//调用方法进行业务处理
//			Method invokeMethod = bizProcess.getClass().getMethod(invokeMethodName,
//					String.class, Map.class);
//			Object returnObj = invokeMethod.invoke(bizProcess,
//					socketMsg.getSendSysCode(), socketMsg.getMsgData());
//
//			if(isSendResponse){
//				responseData = (Map<String, Object>) returnObj;
//			}
//		} catch (ClassCastException e) {
//			if(e.getMessage().indexOf("BizProcesser") > -1){
//				exceptionMsg = "请求业务处理失败, 类型转换异常.bean:" + invokeClass +
//						"未实现BizProcesser接口";
//			}else{
//				exceptionMsg = "请求业务处理失败, 类型转换异常.bean:" + invokeClass +
//						" method:" + invokeMethodName + " 返参类型不是Map<String, Object>";
//			}
//			logger.error(exceptionMsg, e);
//		} catch (BeansException e) {
//			exceptionMsg = "请求业务处理失败, 访问不到bean:" + invokeClass;
//			logger.error(exceptionMsg, e);
//		} catch (NoSuchMethodException e) {
//			exceptionMsg = "请求业务处理失败, 找不到方法.bean:" + invokeClass +
//					" method:" + invokeMethodName;
//			logger.error(exceptionMsg, e);
//		} catch (Exception e) {
//			exceptionMsg = "请求业务处理失败, 发送模块系统编号:" + socketMsg.getSendSysCode() +
//					", 参数:" + socketMsg.getMsgData();
//			logger.error(exceptionMsg, e);
//		}
//		if(isSendResponse){
//			//发送响应
//			try {
//				//组装响应实体
//				socketMsg.setMsgType(MsgType.RESPONSE);
//				socketMsg.setSendAddress(SocketServerInitListener.SERVER_ADDRESS);
//				socketMsg.setDestSysCode(socketMsg.getSendSysCode());
//				socketMsg.setMsgData(responseData);
//				//设置异常信息
//				socketMsg.setExceptionMsg(exceptionMsg);
//				//发送
//				SocketUtil.writeMsg(SocketServerInitListener.getWork(), socketMsg,
//						SocketRemoteTool.KMIB_HOST, SocketRemoteTool.KMIB_PORT,
//						SocketRemoteTool.connectTimeOut * 1000, socketMsg.isAsyn());
//			} catch (Exception e) {
//				logger.error("响应业务处理失败, kmibHost:" + SocketRemoteTool.KMIB_HOST +
//						", kmibPost:" + SocketRemoteTool.KMIB_PORT, e);
//			}
//		}
//	}
//
//	private void response(SocketMsg socketMsg){
//		if(!socketMsg.isAsyn()){//同步
//			if(!socketMsg.isReadTimeout()){
//				//唤醒
//				synchronized (SocketRemoteTool.class) {
//					SocketRemoteTool.setResponse(socketMsg);
//					SocketRemoteTool.class.notify();
//				}
//			}else {
//				logger.warn("超时的响应信息, SocketAddress：" + SocketServerInitListener.SERVER_ADDRESS
//						+ " readTimeout:" + socketMsg.getReadTimeout()
//						+ " responseData:" + socketMsg.getMsgData());
//			}
//		}else{//异步
//			String callClass = socketMsg.getCallbackClassName();
//			String callMethod = socketMsg.getCallbackMethodName();
//			try {
//				//从spring上下文中获取调用的bean
//				CallbackProcesser callback = (CallbackProcesser) beanFactory.getBean(
//						callClass);
//				//调用回复方法处理消息
//				Method invokeMethod = callback.getClass().getMethod(
//						callMethod, String.class, Map.class);
//				invokeMethod.invoke(callback, socketMsg.getSendSysCode(),
//						socketMsg.getMsgData());
//			} catch (ClassCastException e) {
//				logger.error("处理回复消息失败, 类型转换异常.bean:" + callClass +
//						"未实现CallbackProcesser接口", e);
//			} catch (BeansException e) {
//				logger.error("处理回复消息失败, 访问不到bean:" + callClass, e);
//			} catch (NoSuchMethodException e) {
//				logger.error("处理回复消息失败, 找不到方法. bean:" + callClass +
//						" method:" + callMethod, e);
//			} catch (Exception e) {
//				logger.error("处理回复消息失败, 发送模块系统编号:" + socketMsg.getSendSysCode() +
//						", 参数:" + socketMsg.getMsgData(), e);
//			}
//		}
//	}
//
//	@Override
//	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
//			throws Exception {
//		logger.error("Socket异常", cause);
//		ctx.close();
//	}
//}
