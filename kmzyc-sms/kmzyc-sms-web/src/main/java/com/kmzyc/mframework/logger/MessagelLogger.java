package com.kmzyc.mframework.logger;

import org.apache.log4j.Logger;

/**
 * Description:短信日志类
 * User: lishiming
 * Date: 14-3-18 下午2:56
 * Since: 1.0
 */
public class MessagelLogger {

    private static Logger logger = Logger.getLogger(MessagelLogger.class);

    public static void info(String message){
        logger.info(message);
    }

    public static void error(String message){
        logger.error(message);
    }

    public static void error(String message, Throwable t){
        logger.error(message, t);
    }

    public static void debug(String message){
        logger.debug(message);
    }
}
