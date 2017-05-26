package com.kmzyc.framework.log.aop;
//package com.km.framework.log.aop;
//
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.struts2.ServletActionContext;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.jasig.cas.client.util.AssertionHolder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.km.framework.socket.SocketRemoteTool;
//
///**
// * aop记录日志 环绕通知实现类
// * @author zheng xin
// * @version 1.0
// */
//@Component
//public class AopLogging{
//
//	private static Logger log = LoggerFactory.getLogger(AopLogging.class);
//
//	/**
//	 * Aop 环绕通知
//	 * @param pjp
//	 * @return 业务方法返回值
//	 * @throws Throwable 义务异常
//	 */
//	public Object around(ProceedingJoinPoint pjp) throws Throwable {
//		Object returnObj = null;
//		long start = System.currentTimeMillis();
//		try {
//			//调用被aop拦截方法
//			returnObj = pjp.proceed();
//			//处理完毕后发送日志
//			sendLog(pjp, start);
//		} catch (Exception e) {
//			//调用拦截方法异常则先发送日志再将异常抛出
//			sendLog(pjp, start);
//			throw e;
//		}
//		return returnObj;
//	}
//
//	/**
//	 * 发送异步日志
//	 * @param pjp
//	 * @param start
//	 */
//	private void sendLog(ProceedingJoinPoint pjp, long start) {
//		String userName = null;
//		Map<String, Object> sendData = new HashMap<String, Object>();
//		try {
//			//封装参数
//			sendData.put("startTime", new Long(start));
//			sendData.put("endTime", new Long(System.currentTimeMillis()));
//
//			String className = pjp.getTarget().getClass().getSimpleName();
//			String methodName = pjp.getSignature().getName();
//			String key = className+ "." + methodName;
//			String[] conf = LogConfig.getConfigByKey(key);
//			//如果在aopLog.properties文件中找不到 这个 类名.方法名 的配置则返回
//			if(conf == null){
//				return;
//			}
//			//获取用户名
//			if(LogConfig.isCasUse()){//使用CAS
//				userName = AssertionHolder.getAssertion().getPrincipal().getName();
//			}else{//从session中获取
//				Object user = ServletActionContext.getContext().getSession()
//						.get(LogConfig.getUserSessionKey());
//				if(user != null){
//					Method method = user.getClass().getMethod(LogConfig.getUserNameByMethod());
//					userName = (method == null? "" : (String) method.invoke(user));
//				}
//			}
//			sendData.put("userName", userName);
//			sendData.put("className", className);
//			sendData.put("methodName", methodName);
//			sendData.put("modelName", conf[0]);
//			sendData.put("ip", getIP(ServletActionContext.getRequest()));
//			sendData.put("operationType", conf[1]);
//			sendData.put("content", conf[2]);
//			//发送数据给日志系统处理 10为日志系统编号
//			SocketRemoteTool.send("10", "RemoteLogProcesser.process", sendData);
//		} catch (Exception e) {
//			log.error("发送异步日志消息异常", e);
//		}
//	}
//
//	/**
//	 * 获取客户端IP
//	 * @param request
//	 * @return
//	 */
//	private String getIP(HttpServletRequest request) {
//		String ip = request.getHeader("x-forwarded-for");
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getRemoteAddr();
//		}
//		return ip;
//	}
//
//}
