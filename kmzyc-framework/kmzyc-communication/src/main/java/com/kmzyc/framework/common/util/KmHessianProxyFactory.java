package com.kmzyc.framework.common.util;
import com.caucho.hessian.client.HessianProxyFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


public class KmHessianProxyFactory extends HessianProxyFactory {
	private long connectTimeOut = -1;

	@Override
	protected URLConnection openConnection(URL url) throws IOException {

		URLConnection conn = super.openConnection(url);
		if (this.connectTimeOut > 0) {
			conn.setConnectTimeout((int) this.connectTimeOut);
		}
		return conn; 
	}
	public long getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(long connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
}