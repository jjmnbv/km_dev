package com.kmzyc.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO流工具类
 * 
 * @author Liaotiansheng
 * 
 */
public class IOTools {
  static Logger logger= LoggerFactory.getLogger(IOTools.class);
  public static void closeIO(Closeable stream) {
    if (null != stream) {
      try {
        stream.close();
      } catch (IOException e) {
        logger.error(e.getMessage(),e);
      }
    }
  }

}
