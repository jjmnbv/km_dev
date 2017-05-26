package com.pltfm.sys.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CollectPageUtil {

  Logger log = LoggerFactory.getLogger(this.getClass());

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
      log.error(me.getMessage());
      return null;
    } catch (final IOException e) {
      log.error("××××××××××  远程文件读取异常!!!  url=" + url.getPath());
      log.error(e.getMessage());
      return null;
    } catch (Exception e) {
      log.error("××××××××××  网络连接异常!!!  url=" + url.getPath());
      log.error(e.getMessage());
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
      log.error(me.getMessage());
      return null;
    } catch (final IOException e) {
      log.error("××××××××××  远程文件读取异常!!!  url=" + url.getPath());
      log.error(e.getMessage());
      return null;
    } catch (Exception e) {
      log.error("××××××××××  网络连接异常!!!  url=" + url.getPath());
      log.error(e.getMessage());
      return null;
    }
    return sb.toString();
  }
}
