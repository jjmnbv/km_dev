package com.pltfm.app.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
  /**
   * 序列化
   * 
   * @param object
   * @return
   */
  public static byte[] serialize(Object object) {
    ObjectOutputStream oos = null;
    ByteArrayOutputStream baos = null;
    try {
      // 序列化
      baos = new ByteArrayOutputStream();
      oos = new ObjectOutputStream(baos);
      oos.writeObject(object);
      byte[] bytes = baos.toByteArray();
      return bytes;
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      if (baos != null) baos.close();
      if (oos != null) oos.close();
    } catch (Exception e) {

    }
    return null;
  }

  /**
   * 反序列化
   * 
   * @param bytes
   * @return
   */
  public static Object unserialize(byte[] bytes) {
    ByteArrayInputStream bais = null;
    ObjectInputStream ois = null;
    try {
      // 反序列化
      bais = new ByteArrayInputStream(bytes);
      ois = new ObjectInputStream(bais);
      return ois.readObject();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (bais != null) bais.close();
        if (ois != null) ois.close();
      } catch (Exception e) {

      }

    }
    return null;
  }
}
