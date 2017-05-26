package com.kmzyc.promotion.app.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过javascript使其字符串表达式变为java计算表达式
 * 
 * @author zhl
 * @since 2013-08-13
 * 
 */
@SuppressWarnings("unchecked")
public class JavaScriptExpress {
    protected static Logger logger = LoggerFactory.getLogger(JavaScriptExpress.class);
    private static ScriptEngineManager factory = new ScriptEngineManager();
    private static ScriptEngine engine = factory.getEngineByName("JavaScript");
    private static Map<String, String> paramsMap;

    public static Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public static void setParamsMap(Map<String, String> paramsMap) {
        JavaScriptExpress.paramsMap = paramsMap;
    }

    /**
     * 将字符串表达式转化成计算表达式
     * 
     * @param paramsMap 参数集合
     * @param option 字符串表达式
     * @return 返回数字类型或者布尔类型
     * @throws ScriptException
     */
    public static <T> T getMathValue(String option) throws ScriptException {
        try {
            // for循环遍历Map中key的set
            Set<String> outSet = paramsMap.keySet();
            for (Iterator<String> its = outSet.iterator(); its.hasNext();) {
                String key = its.next();
                option = option.replaceAll(key, paramsMap.get(key));
            }
            // 通过表达式计算
            Object o = engine.eval(option);
            return (T) o;
        } catch (ScriptException e) {
            logger.error("将字符串表达式转化成计算表达式异常：", e);
            throw e;
        }
    }
}
