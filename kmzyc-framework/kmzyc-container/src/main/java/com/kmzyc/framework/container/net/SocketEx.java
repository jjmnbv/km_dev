package com.kmzyc.framework.container.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SocketEx，扩展自{@link java.net.Socket}.
 * <p>
 * {@link #request(SocketRequest)}方法具有重连和发送功能:
 * 该方法首先不断地尝试连接服务器（独立线程），
 * 一但成功，便调用{@link SocketRequest#request(SocketEx)}方法.
 *
 * @author weishanyao
 * @see {@link SocketRequest#request(SocketEx)}方法.
 */
public class SocketEx extends Socket {
    final private static Log log = LogFactory.getLog(SocketEx.class);
    final private static Map<String, java.net.Socket> sockets = new ConcurrentHashMap<String, java.net.Socket>();

    private java.net.Socket socket;
    private transient boolean connected;
    private String host;
    private int port;

    public SocketEx() {
    }

    public SocketEx(Proxy proxy) {
        super(proxy);
    }

    public SocketEx(SocketImpl impl) throws SocketException {
        super(impl);
    }

    public SocketEx(String host, int port) throws UnknownHostException, IOException {
        try {
            connect(host, port);
        } catch (Exception e) {
            request(null);
        }
    }

    public SocketEx(InetAddress address, int port) throws IOException {
        this(address.getHostAddress(), port);
    }

    public SocketEx(String host, int port, InetAddress localAddr, int localPort) throws IOException {
        try {
            connect(host, port, localAddr, localPort);
        } catch (Exception e) {
            request(null);
        }
    }

    public SocketEx(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {
        this(address.getHostAddress(), port, localAddr, localPort);
    }

    // --

    @Override
    public void connect(SocketAddress endpoint) throws IOException {
        try {
            if (null == socket) {
                super.connect(endpoint);
                host = getInetAddress().getHostAddress();
                port = getPort();
                sockets.put(getIP(host) + ":" + port, socket = this);
            } else socket.connect(endpoint);
        } catch (Exception e) {
            request(null);
        }
    }

    @Override
    public void connect(SocketAddress endpoint, int timeout) throws IOException {
        try {
            if (null == socket) {
                super.connect(endpoint, timeout);
                host = getInetAddress().getHostAddress();
                port = getPort();
                sockets.put(getIP(host) + ":" + port, socket = this);
            } else socket.connect(endpoint, timeout);
        } catch (Exception e) {
            request(null);
        }
    }

    @Override
    public void bind(SocketAddress bindpoint) throws IOException {
        if (null == socket) super.bind(bindpoint);
        else socket.bind(bindpoint);
    }

    @Override
    public InetAddress getInetAddress() {
        if (null == socket) return super.getInetAddress();
        else return socket.getInetAddress();
    }

    @Override
    public InetAddress getLocalAddress() {
        if (null == socket) return super.getLocalAddress();
        else return socket.getLocalAddress();
    }

    @Override
    public int getPort() {
        if (null == socket) return super.getPort();
        else return socket.getPort();
    }

    @Override
    public int getLocalPort() {
        if (null == socket) return super.getLocalPort();
        else return socket.getLocalPort();
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        if (null == socket) return super.getRemoteSocketAddress();
        else return socket.getRemoteSocketAddress();
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        if (null == socket) return super.getLocalSocketAddress();
        else return socket.getLocalSocketAddress();
    }

    @Override
    public SocketChannel getChannel() {
        if (null == socket) return super.getChannel();
        else return socket.getChannel();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (null == socket) return super.getInputStream();
        else return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        if (null == socket) return super.getOutputStream();
        else return socket.getOutputStream();
    }

    @Override
    public void setTcpNoDelay(boolean on) throws SocketException {
        if (null == socket) super.setTcpNoDelay(on);
        else socket.setTcpNoDelay(on);
    }

    @Override
    public boolean getTcpNoDelay() throws SocketException {
        if (null == socket) return super.getTcpNoDelay();
        else return socket.getTcpNoDelay();
    }

    @Override
    public void setSoLinger(boolean on, int linger) throws SocketException {
        if (null == socket) super.setSoLinger(on, linger);
        else socket.setSoLinger(on, linger);
    }

    @Override
    public int getSoLinger() throws SocketException {
        if (null == socket) return super.getSoLinger();
        else return socket.getSoLinger();
    }

    @Override
    public void sendUrgentData(int data) throws IOException {
        if (null == socket) super.sendUrgentData(data);
        else socket.sendUrgentData(data);
    }

    @Override
    public void setOOBInline(boolean on) throws SocketException {
        if (null == socket) super.setOOBInline(on);
        else socket.setOOBInline(on);
    }

    @Override
    public boolean getOOBInline() throws SocketException {
        if (null == socket) return super.getOOBInline();
        else return socket.getOOBInline();
    }

    @Override
    public synchronized void setSoTimeout(int timeout) throws SocketException {
        if (null == socket) super.setSoTimeout(timeout);
        else socket.setSoTimeout(timeout);
    }

    @Override
    public synchronized int getSoTimeout() throws SocketException {
        if (null == socket) return super.getSoTimeout();
        else return socket.getSoTimeout();
    }

    @Override
    public synchronized void setSendBufferSize(int size) throws SocketException {
        if (null == socket) super.setSendBufferSize(size);
        else socket.setSendBufferSize(size);
    }

    @Override
    public synchronized int getSendBufferSize() throws SocketException {
        if (null == socket) return super.getSendBufferSize();
        else return socket.getSendBufferSize();
    }

    @Override
    public synchronized void setReceiveBufferSize(int size) throws SocketException {
        if (null == socket) super.setReceiveBufferSize(size);
        else socket.setReceiveBufferSize(size);
    }

    @Override
    public synchronized int getReceiveBufferSize() throws SocketException {
        if (null == socket) return super.getReceiveBufferSize();
        else return socket.getReceiveBufferSize();
    }

    @Override
    public void setKeepAlive(boolean on) throws SocketException {
        if (null == socket) super.setKeepAlive(on);
        else socket.setKeepAlive(on);
    }

    @Override
    public boolean getKeepAlive() throws SocketException {
        if (null == socket) return super.getKeepAlive();
        else return socket.getKeepAlive();
    }

    @Override
    public void setTrafficClass(int tc) throws SocketException {
        if (null == socket) super.setTrafficClass(tc);
        else socket.setTrafficClass(tc);
    }

    @Override
    public int getTrafficClass() throws SocketException {
        if (null == socket) return super.getTrafficClass();
        else return socket.getTrafficClass();
    }

    @Override
    public void setReuseAddress(boolean on) throws SocketException {
        if (null == socket) super.setReuseAddress(on);
        else socket.setReuseAddress(on);
    }

    @Override
    public boolean getReuseAddress() throws SocketException {
        if (null == socket) return super.getReuseAddress();
        else return socket.getReuseAddress();
    }

    @Override
    public synchronized void close() throws IOException {
        if (null == socket) super.close();
        else {
            try {
                socket.close();
            } catch (IOException e) {
                throw e;
            } finally {
                sockets.remove(getIP(host) + ":" + port);
                socket = null;
                try {
                    super.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    @Override
    public void shutdownInput() throws IOException {
        if (null == socket) super.shutdownInput();
        else socket.shutdownInput();
    }

    @Override
    public void shutdownOutput() throws IOException {
        if (null == socket) super.shutdownOutput();
        else socket.shutdownOutput();
    }

    @Override
    public String toString() {
        if (null == socket) return super.toString();
        else return socket.toString();
    }

    @Override
    public boolean isConnected() {
        if (null == socket) return super.isConnected();
        else return socket.isConnected();
    }

    @Override
    public boolean isBound() {
        if (null == socket) return super.isBound();
        else return socket.isBound();
    }

    @Override
    public boolean isClosed() {
        if (null == socket) return super.isClosed();
        else return socket.isClosed();
    }

    @Override
    public boolean isInputShutdown() {
        if (null == socket) return super.isInputShutdown();
        else return socket.isInputShutdown();
    }

    @Override
    public boolean isOutputShutdown() {
        if (null == socket) return super.isOutputShutdown();
        else return socket.isOutputShutdown();
    }

    @Override
    public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
        if (null == socket) super.setPerformancePreferences(connectionTime, latency, bandwidth);
        else socket.setPerformancePreferences(connectionTime, latency, bandwidth);
    }

    public synchronized void reconnect() throws IOException {
        log.info("Try to connect server(" + host + ":" + port + ") ...");
        if (null != socket) {
            InetAddress localAddr = socket.getLocalAddress();
            int localPort = socket.getLocalPort();
            try {
                socket.close();
            } catch (Exception e) {
            } finally {
                socket = null;
                String key = getIP(host) + ":" + port;
                sockets.remove(key);
                connect(host, port, localAddr, localPort);
            }
        } else {
            connect(null != host ? host : getInetAddress().getHostAddress(), 0 < port ? port : getPort(), getLocalAddress(), getLocalPort());
        }
        log.info("Connected to server(" + host + ":" + port + ") - O.K.");
    }

    /**
     * 持续地连接直至成功.
     * <p>
     * TODO: 间隔时间可以配置，暂时写死5秒.
     */
    public void request(final SocketRequest request) {
        if (connected && null != socket && socket.isConnected()) {
            if (null != request) {
                synchronized (socket) {
                    request.request(this);
                }
            }
            return;
        }
        connected = false;
        new Thread() {
            @Override
            public void run() {
                while (!connected) {
                    try {
                        reconnect();
                        connected = true;
                        if (null != request) {
                            synchronized (socket) {
                                try {
                                    request.request(SocketEx.this);
                                } catch (Throwable t) {
                                    log.error("Occur exception(s) on request server", t);
                                }
                            }
                        }
                    } catch (IOException e) {
                        log.error("Occur exception on connecting to server(" + host + ":" + port + ") - " + e.getMessage() + ", try it again ...");
                        connected = false;
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * 放弃重连尝试.
     */
    public synchronized void abandon() {
        connected = true;
    }


    private synchronized void connect(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        String key = getIP(host) + ":" + port;
        socket = sockets.get(key);
        if (null == socket) sockets.put(key, socket = new java.net.Socket(host, port));
        connected = true;
    }

    private synchronized void connect(String host, int port, InetAddress localAddr, int localPort) throws IOException {
        this.host = host;
        this.port = port;
        String key = getIP(host) + ":" + port;
        socket = sockets.get(key);
        if (null == socket) sockets.put(key, socket = new java.net.Socket(host, port, localAddr, localPort));
        connected = true;
    }

    private static String getIP(String ip) {
        if (null == ip || "127.0.0.1".equals(ip)) return "localhost";
        else return ip;
    }
}
