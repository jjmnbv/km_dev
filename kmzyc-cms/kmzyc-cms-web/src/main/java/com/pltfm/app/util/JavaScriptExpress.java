package com.pltfm.app.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 通过javascript使其字符串表达式变为java计算表达式
 *
 * @author zhl
 * @since 2013-08-13
 */
public class JavaScriptExpress {
    ScriptEngineManager factory = new ScriptEngineManager();
    ScriptEngine engine = factory.getEngineByName("JavaScript");

    /**
     * 将字符串表达式转化成计算表达式
     *
     * @param paramsMap 参数集合
     * @param option    字符串表达式
     * @return 返回double类型值
     */
    public Double getMathValue(Map<String, String> paramsMap, String option) {
        double d = 0;
        try {
            //for循环遍历Map中key的set
            Set<Map.Entry<String,String>> outSet = paramsMap.entrySet();
            for (Iterator<Map.Entry<String,String>> its = outSet.iterator(); its.hasNext(); ) {
                Map.Entry<String,String> entry=its.next();
                option = option.replaceAll(entry.getKey(), entry.getValue());
            }
            //通过表达式计算值
            Object o = engine.eval(option);
            d = Double.parseDouble(o.toString());
        } catch (ScriptException e) {
            return null;
        }
        return d;
    }

}
