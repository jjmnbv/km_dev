package com.pltfm.sys.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class CollectPageUtil {

	private static Logger logger = LoggerFactory.getLogger(CollectPageUtil.class);

    /**
     * 采集指定地址的数据
     */
    public String getWebContentUTF(URL url) {
        String temp;
        final StringBuffer sb = new StringBuffer();
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
            while ((temp = in.readLine()) != null) {
                sb.append(temp + System.getProperty("line.separator"));
            }
            in.close();
        } catch (final MalformedURLException me) {
        	logger.error("××××××××××  获取远程URL连接异常!!!  url=" + url.getPath());
        	logger.error(me.getMessage());
            return null;
        } catch (final IOException e) {
        	logger.error("××××××××××  远程文件读取异常!!!  url=" + url.getPath());
        	logger.error(e.getMessage());
            return null;
        } catch (Exception e) {
        	logger.error("××××××××××  网络连接异常!!!  url=" + url.getPath());
        	logger.error(e.getMessage());
            return null;
        }
        return sb.toString();
    }


    /**
     * 采集指定地址的数据
     */
    public String getWebContentGB2312(URL url) {
        String temp;
        final StringBuffer sb = new StringBuffer();
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "gb2312"));
            while ((temp = in.readLine()) != null) {
                sb.append(temp + System.getProperty("line.separator"));
            }
            in.close();
        } catch (final MalformedURLException me) {
        	logger.error("××××××××××  获取远程URL连接异常!!!  url=" + url.getPath());
        	logger.error(me.getMessage());
            return null;
        } catch (final IOException e) {
        	logger.error("××××××××××  远程文件读取异常!!!  url=" + url.getPath());
        	logger.error(e.getMessage());
            return null;
        } catch (Exception e) {
        	logger.error("××××××××××  网络连接异常!!!  url=" + url.getPath());
        	logger.error(e.getMessage());
            return null;
        }
        return sb.toString();
    }
}
