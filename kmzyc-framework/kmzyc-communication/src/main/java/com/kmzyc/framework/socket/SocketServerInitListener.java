package com.kmzyc.framework.socket;
//package com.km.framework.socket;
//
//import com.km.framework.common.util.GetProperties;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import java.lang.Thread.UncaughtExceptionHandler;
//
///**
// * SocketEx 服务端启动监听器
// *
// * 需要在web.xml中配置:
// * <listener>
// *  <listener-class>com.km.framework.socket.SocketServerInitListener</listener-class>
// * </listener>
// * @see ServletContextListener
// * @see BizProcesser
// * @author zhengxin
// * @version 1.0
// */
//public class SocketServerInitListener implements ServletContextListener {
//
//	static Logger logger = LoggerFactory.getLogger(SocketServerInitListener.class);
//
//	private static EventLoopGroup boss = new NioEventLoopGroup();
//
//	private static EventLoopGroup work = new NioEventLoopGroup();
//
//	private ChannelFuture future;
//
//	static final String SERVER_ADDRESS;
//
//	static final String SERVER_HOST;
//
//	static final int SERVER_PORT;
//
//	/**
//	 * @exception  IllegalArgumentException 当配置文件serverAddress参数异常时
//	 */
//	static{
//		SERVER_ADDRESS = GetProperties.getConfig("serverSocketAddress");
//		SERVER_HOST = SocketUtil.getHostByConfKey("serverSocketAddress");
//		SERVER_PORT = SocketUtil.getPortByConfKey("serverSocketAddress");
//	}
//
//	@Override
//	public void contextDestroyed(ServletContextEvent event) {
//		try {
//			future.channel().close().sync();
//			logger.info("关闭Socket服务成功, address:" + SERVER_ADDRESS);
//		} catch (InterruptedException e) {
//			logger.error("关闭Socket服务异常, address:" + SERVER_ADDRESS, e);
//		}finally{
//			Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
//				@Override
//				public void uncaughtException(Thread t, Throwable e) {
//				}
//			});
//			try{
//				boss.shutdownGracefully();
//			}catch(Exception e){}
//			try{
//				work.shutdownGracefully();
//			}catch(Exception e){}
//		}
//	}
//	@Override
//	public void contextInitialized(ServletContextEvent event) {
//		BeanFactory beanFactory = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
//		if(beanFactory == null){
//			logger.error("获取Spring Web上下文对象失败");
//			return;
//		}
//		ServerBootstrap b = new ServerBootstrap();
//		//注册Socket服务端
//		future = SocketUtil.configureServerBootstrap(b, boss, work, SERVER_HOST, SERVER_PORT,
//				SocketUtil.getObjectChannelInitializer(
//						new SocketReceiveHandler(beanFactory))).bind();
//		future.addListener(new ChannelFutureListener(){
//			@Override
//			public void operationComplete(ChannelFuture future)
//					throws Exception {
//				if(future.isSuccess())
//					logger.info("Socket服务端注册成功, address:" + SERVER_ADDRESS);
//				else
//					logger.info("Socket服务端注册失败, address:" + SERVER_ADDRESS);
//			}
//		});
//
//	}
//	public static EventLoopGroup getBoss() {
//		return boss;
//	}
//	public static EventLoopGroup getWork() {
//		return work;
//	}
//}
