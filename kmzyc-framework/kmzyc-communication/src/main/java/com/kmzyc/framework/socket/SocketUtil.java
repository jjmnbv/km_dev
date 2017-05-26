package com.kmzyc.framework.socket;
//package com.km.framework.socket;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandler;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.serialization.ClassResolvers;
//import io.netty.handler.codec.serialization.ObjectDecoder;
//import io.netty.handler.codec.serialization.ObjectEncoder;
//import io.netty.handler.codec.string.StringDecoder;
//import io.netty.handler.codec.string.StringEncoder;
//
//import java.net.InetSocketAddress;
//
//import com.km.framework.common.util.GetProperties;
//
///**
// * Socket工具类
// * 使用netty4.X API
// * @author Administrator
// */
//public final class SocketUtil {
//
//	/**
//	 * 获取处理对象的解析器
//	 * @param handlers 业务解析器
//	 * @return
//	 */
//	public static ChannelHandler getObjectChannelInitializer(final ChannelHandler... handlers){
//		return new ChannelInitializer<SocketChannel>() {
//			@Override
//			protected void initChannel(SocketChannel ch) throws Exception {
//				ChannelPipeline pipeline = ch.pipeline();
//				//允许一次解析100M大小的对象，默认为1M
//				pipeline.addLast(new ObjectEncoder(),
//						new ObjectDecoder(104857600, ClassResolvers.cacheDisabled(null)));
//				if(handlers != null && handlers.length > 0)
//					pipeline.addLast(handlers);
//			}
//		};
//	}
//
//	/**
//	 * 获取处理字符串的解析器
//	 * @param handlers 业务解析器
//	 * @return
//	 */
//	public static ChannelHandler getStringChannelInitializer(final ChannelHandler... handlers){
//		return new ChannelInitializer<SocketChannel>() {
//			@Override
//			protected void initChannel(SocketChannel ch) throws Exception {
//				ChannelPipeline pipeline = ch.pipeline();
//				pipeline.addLast(new StringEncoder(),new StringDecoder());
//				if(handlers != null && handlers.length > 0)
//					pipeline.addLast(handlers);
//			}
//		};
//	}
//
//	/**
//	 * 连接服务端，输出消息对象，立即关闭连接(同步)
//	 * @param msg 消息对象
//	 * @param host 服务端IP
//	 * @param port 服务端PORT
//	 * @param connectTimeOut 连接超时时间
//	 * @throws InterruptedException
//	 */
//	public static void writeMsg(Object msg, String host, int port,
//			int connectTimeOut) throws InterruptedException {
//		EventLoopGroup g = new NioEventLoopGroup(1);
//		try {
//			writeMsg(g, msg, host, port, connectTimeOut, false);
//		} finally {
//			g.shutdownGracefully();
//		}
//	}
//
//	/**
//	 * 连接服务端，输出消息对象，立即关闭连接
//	 * @param group 线程组
//	 * @param msg 消息对象
//	 * @param host 服务端IP
//	 * @param port 服务端PORT
//	 * @param connectTimeOut 连接超时时间
//	 * @param isAsyn 是否异步发送
//	 * @throws InterruptedException
//	 */
//	public static void writeMsg(EventLoopGroup group, final Object msg,
//			final String host, final int port,int connectTimeOut,
//			boolean isAsyn) throws InterruptedException {
//		ChannelFuture f = configureBootstrap(new Bootstrap(),
//				group, host, port, connectTimeOut,
//				getObjectChannelInitializer()).connect();
//		if(isAsyn){//异步
//			f.addListener(new ChannelFutureListener(){
//				@Override
//				public void operationComplete(ChannelFuture future)
//						throws Exception {
//					if(future.isSuccess()){
//						Channel ch = future.channel();
//						//发送消息
//						ch.writeAndFlush(msg).sync();
//						//关闭通道
//						ch.close().sync();
//					}else
//						throw new RuntimeException(
//								"连接失败, host:" + host +
//								" port:" + port, future.cause());
//				}
//			});
//		}else{//同步
//			Channel ch = f.sync().channel();
//			ch.writeAndFlush(msg).sync();
//			ch.close().sync();
//		}
//	}
//
//	public static void writeMsg(EventLoopGroup group, final Object msg, String host, int port,
//			boolean isAsyn) throws InterruptedException {
//		 writeMsg(group, msg, host, port, -1, isAsyn);
//	}
//
//	/**
//	 * 连接服务端，输出消息对象，立即关闭连接(同步)
//	 * @param msg 消息对象
//	 * @param host 服务端IP
//	 * @param port 服务端PORT
//	 * @throws InterruptedException
//	 */
//	public static void writeMsg(Object msg, String host, int port)
//			throws InterruptedException {
//		writeMsg(msg, host, port, -1);
//	}
//
//	/**
//	 * 配置Bootstrap
//	 * @param b
//	 * @param g
//	 * @param host
//	 * @param port
//	 * @param channelInitializer
//	 * @return
//	 */
//	public static Bootstrap configureBootstrap(Bootstrap b, EventLoopGroup g,
//			String host, int port, ChannelHandler channelInitializer) {
//       return configureBootstrap(b, g, host, port, -1, channelInitializer);
//    }
//
//	/**
//	 * 配置Bootstrap
//	 * @param b
//	 * @param g
//	 * @param host
//	 * @param port
//	 * @param connectTimeOut
//	 * @param readTimeout
//	 * @param channelInitializer
//	 * @return
//	 */
//	public static Bootstrap configureBootstrap(Bootstrap b, EventLoopGroup g,
//			String host, int port,
//			int connectTimeOut, ChannelHandler channelInitializer) {
//        b.group(g)
//         .channel(NioSocketChannel.class)
//         .remoteAddress(host, port)
//         .handler(channelInitializer);
//        if(connectTimeOut > 0)
//        	b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeOut);
//        return b;
//    }
//
//	/**
//	 * 配置ServerBootstrap
//	 * @param b
//	 * @param boss
//	 * @param work
//	 * @param host
//	 * @param port
//	 * @param channelInitializer
//	 * @return
//	 */
//	public static ServerBootstrap configureServerBootstrap(ServerBootstrap b,
//			EventLoopGroup boss, EventLoopGroup work,
//			String host, int port, ChannelHandler channelInitializer) {
//        b.group(boss, work)
//         .channel(NioServerSocketChannel.class)
//         .localAddress(new InetSocketAddress(host, port))
//         .childHandler(channelInitializer);
//        return b;
//    }
//
//	public static String getHostByConfKey(String configKey){
//		String address = GetProperties.getConfig(configKey);
//		int mid = address.indexOf(":");
//		String host = null;
//		if(mid != -1){
//			host = address.substring(0, mid);
//		}else {
//			throw new IllegalArgumentException("配置文件" + configKey + "参数异常, 格式应为 IP:PORT");
//		}
//		return host;
//	}
//
//	public static String getHostByAddress(String address){
//		int mid = address.indexOf(":");
//		if(mid != -1){
//			return address.substring(0, mid);
//		}else {
//			throw new IllegalArgumentException("address:" + address + "参数异常, 格式应为 IP:PORT");
//		}
//	}
//
//	public static int getPortByConfKey(String configKey){
//		String address = GetProperties.getConfig(configKey);
//		int mid = address.indexOf(":");
//		if(mid != -1){
//			return Integer.parseInt(address.substring(mid + 1));
//		}else {
//			throw new IllegalArgumentException("配置文件" + configKey + "参数异常, 格式应为 IP:PORT");
//		}
//	}
//
//	public static int getPortByAddress(String address){
//		int mid = address.indexOf(":");
//		if(mid != -1){
//			return Integer.parseInt(address.substring(mid + 1));
//		}else {
//			throw new IllegalArgumentException("address:" + address + "参数异常, 格式应为 IP:PORT");
//		}
//	}
//}
