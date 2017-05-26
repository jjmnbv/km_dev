package com.kmzyc.express.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * 商品价格redis缓存操作工具
 * 
 * @author xlg
 * 
 */
public class RedisCacheUtil {
  private static Logger logger = LoggerFactory.getLogger(RedisCacheUtil.class);

  /**
   * 序列化对象
   */
  public static byte[] serialize(Object obj) throws IOException {
    ObjectOutputStream oos = null;
    ByteArrayOutputStream baos = null;
    try {
      baos = new ByteArrayOutputStream();
      oos = new ObjectOutputStream(baos);
      oos.writeObject(obj);
      byte[] bytes = baos.toByteArray();
      return bytes;
    } finally {
      if (oos != null) oos.close();
      if (baos != null) baos.close();
    }
  }

  /**
   * 反序列化对象
   */
  public static Object unserialize(byte[] bytes) throws IOException, ClassNotFoundException {
    if (bytes == null || 0 == bytes.length) return null;
    ByteArrayInputStream bais = null;
    ObjectInputStream ois = null;
    try {
      bais = new ByteArrayInputStream(bytes);
      ois = new ObjectInputStream(bais);
      return ois.readObject();
    } catch (Exception e) {
      logger.error("", e);
      return null;
    } finally {
      if (ois != null) ois.close();
      if (bais != null) bais.close();
    }
  }
}
