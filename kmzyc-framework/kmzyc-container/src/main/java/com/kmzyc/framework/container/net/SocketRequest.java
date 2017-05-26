package com.kmzyc.framework.container.net;


/**
 * Socket连接回调.
 * <p>
 * {@link SocketEx#request(SocketRequest)}方法传入此对象，
 * 该方法首先不断地尝试连接服务器（独立线程），
 * 一但成功，便调用{@link #request(SocketEx)}方法.
 *
 * @author weishanyao
 * @see {@link SocketEx#request(SocketRequest)}方法.
 */
public interface SocketRequest {
    void request(SocketEx socket);
}
