package com.pltfm.sys.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

public class CollectPageUtil {

  Logger log = Logger.getLogger(this.getClass());

  /**
   * 采集指定地址的数据
   * 
   * @param url
   * @return
   */
  public String getWebContentUTF(URL url) {
    String temp;
    final StringBuffer sb = new StringBuffer();
    try {
      final BufferedReader in =
          new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
      while ((temp = in.readLine()) != null) {
        sb.append(temp + System.getProperty("line.separator"));
      }
      in.close();
    } catch (final MalformedURLException me) {
      log.error("××××××××××  获取远程URL连接异常!!!  url=" + url.getPath());
      log.error("获取远程URL连接异常", me);
      return null;
    } catch (final IOException e) {
      log.error("××××××××××  远程文件读取异常!!!  url=" + url.getPath());
      log.error("远程文件读取异常", e);
      return null;
    } catch (Exception e) {
      log.error("××××××××××  网络连接异常!!!  url=" + url.getPath());
      log.error("网络连接异常", e);
      return null;
    }
    return sb.toString();
  }

  /**
   * 采集指定地址的数据
   * 
   * @param url
   * @return
   */
  public String getWebContentGB2312(URL url) {
    String temp;
    final StringBuffer sb = new StringBuffer();
    try {
      final BufferedReader in =
          new BufferedReader(new InputStreamReader(url.openStream(), "gb2312"));
      while ((temp = in.readLine()) != null) {
        sb.append(temp + System.getProperty("line.separator"));
      }
      in.close();
    } catch (final MalformedURLException me) {
      log.error("××××××××××  获取远程URL连接异常!!!  url=" + url.getPath());
      log.error("获取远程URL连接异常", me);
      return null;
    } catch (final IOException e) {
      log.error("××××××××××  远程文件读取异常!!!  url=" + url.getPath());
      log.error("远程文件读取异常", e);
      return null;
    } catch (Exception e) {
      log.error("××××××××××  网络连接异常!!!  url=" + url.getPath());
      log.error("网络连接异常异常", e);
      return null;
    }
    return sb.toString();
  }
}
