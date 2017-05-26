package com.kmzyc.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对像序列化工具类
 * 
 * @author Administrator
 * 
 */
public class SerializeUtil {
  // private static Logger logger = Logger.getLogger(SerializeUtil.class);
  private static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

  public static byte[] serialize(Object object) {
    ObjectOutputStream oos = null;
    ByteArrayOutputStream bos = null;
    try {
      // 序列化
      bos = new ByteArrayOutputStream();
      oos = new ObjectOutputStream(bos);
      oos.writeObject(object);
      byte[] bytes = bos.toByteArray();
      return bytes;
    } catch (Exception e) {
      logger.error("系列化对象出现异常！", e);
    }
    return null;

  }

  public static Object unserialize(byte[] bytes) {

    ByteArrayInputStream bais = null;
    try {
      // 反序列化
      bais = new ByteArrayInputStream(bytes);
      ObjectInputStream ois = new ObjectInputStream(bais);
      return ois.readObject();

    } catch (Exception e) {
      logger.error("反系列化对象出现异常！", e);
    }
    return null;
  }

}
