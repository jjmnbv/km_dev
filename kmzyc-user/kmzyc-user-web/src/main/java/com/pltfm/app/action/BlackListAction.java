package com.pltfm.app.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;


@Component(value = "blackListAction")
@Scope(value = "prototype")
public class BlackListAction extends BaseAction {
  @Resource
  private JedisCluster jedis;
  private String blackListContent;
  private String Content;
  private static final Logger logger = LoggerFactory.getLogger(BlackListAction.class);


  public String saveBlackList() {
    try {
      String fileName = getRequest().getRealPath("/") + "blackList.txt";// 指定文件名
      File file = new File(fileName);
      FileOutputStream out = new FileOutputStream(file);// 建立输出流
      byte[] b = new byte[1024];
      Content = blackListContent;
      b = Content.getBytes();// String转化为byte[]
      out.write(b);// 写入文本内容
      out.flush();
      out.close();
      logger.info("保存成功黑名单为:" + Content);
      jedis.set("MSG_BLACKLIST", Content);
    } catch (Exception e) {
      logger.error("保存文件出错：" + e.getMessage());
      System.out.println(e.toString());
    } finally {
      return SUCCESS;
    }
  }

  public String findBlackList() {
    blackListContent = (String) jedis.get("MSG_BLACKLIST");
    if (null == blackListContent) {
      try {
        String filePath = getRequest().getRealPath("/") + "blackList.txt";// 指定文件名
        String encoding = "UTF-8";
        File file = new File(filePath);
        if (file.isFile() && file.exists()) { // 判断文件是否存在
          InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
          BufferedReader bufferedReader = new BufferedReader(read);
          String lineTxt = null;
          blackListContent = "";
          int i = 0;
          while ((lineTxt = bufferedReader.readLine()) != null) {
            i++;
            blackListContent = blackListContent + lineTxt;
          }
          read.close();
          logger.info("黑名单内容：" + blackListContent);
        } else {
          logger.error("找不到指定的文件");
        }
      } catch (Exception e) {
        logger.error("读取文件内容出错");
        e.printStackTrace();
      }
    }
    return SUCCESS;
  }



  public String getBlackListContent() {
    return blackListContent;
  }

  public void setBlackListContent(String blackListContent) {
    this.blackListContent = blackListContent;
  }

}
