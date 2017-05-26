package com.kmzyc.b2b.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

/**
 * 集合帮助类
 * 
 * @author xlg
 * 
 */
@SuppressWarnings("unchecked")
public class CollectionUtils {

  /**
   * 深拷贝
   * 
   * @param <T>
   * @param src
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(byteOut);
    out.writeObject(src);
    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
    ObjectInputStream in = new ObjectInputStream(byteIn);
    List<T> dest = (List<T>) in.readObject();
    in.close();
    out.close();
    return dest;
  }

  /**
   * 深拷贝
   * 
   * @param <T>
   * @param src
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static Map deepCopy(Map src) throws IOException, ClassNotFoundException {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(byteOut);
    out.writeObject(src);
    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
    ObjectInputStream in = new ObjectInputStream(byteIn);
    Map rs = (Map) in.readObject();
    in.close();
    out.close();
    return rs;
  }

  /**
   * 深拷贝
   * 
   * @param <T>
   * @param src
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static <T extends Object> T deepCopyObj(T t) throws IOException, ClassNotFoundException {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(byteOut);
    out.writeObject(t);
    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
    ObjectInputStream in = new ObjectInputStream(byteIn);
    T rs = (T) in.readObject();
    in.close();
    out.close();
    return rs;
  }
}
