package com.kmzyc.framework.container.utils;

/**
 * 类型处理工具箱.
 * @author weishanyao
 */
public class TypeHelper {
    
    /**
     * 比较给定类型和对象是否匹配，用于形参和实参的匹配情形.
     * @param type 类型.
     * @param obj 对象. 
     * @return 如果对象匹配于类型, 则返回真, 否则返回假.
     */
    public static boolean checkAssignable(Class<?> type, Object obj) {
        if (null==obj || type.isInstance(obj)) return true;
        if (type.isPrimitive()) {
            if ((type==Integer.TYPE || type==Long.TYPE || type==Short.TYPE) && (obj instanceof Integer || obj instanceof Long || obj instanceof Short)) {
                return true; 
            }
            else if ((type==Double.TYPE || type==Float.TYPE) && (obj instanceof Double || obj instanceof Float)) return true; 
            else if (type==Boolean.TYPE && obj instanceof Boolean) return true;
            else if (type==Character.TYPE && obj instanceof Character) return true;
            else if (type==Byte.TYPE && obj instanceof Byte) return true;
            else return false;
        }
        return false;
    }    
}
