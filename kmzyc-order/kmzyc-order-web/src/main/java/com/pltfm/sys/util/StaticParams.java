package com.pltfm.sys.util;

import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.pltfm.sys.bean.SysParamBean;
import com.pltfm.sys.model.SysParam;

@SuppressWarnings("unchecked")
public class StaticParams {
  private static Logger log = Logger.getLogger(StaticParams.class);
  private static Hashtable paramMap = new Hashtable(); // 参数表sys_param中的参数

  // private static Hashtable typeMap = new Hashtable(); //类型表gd_type中的参数

  public StaticParams() {}

  /*------------------------------------------  sys_param中的参数 -----------------------------------------------*/
  /**
   * @description: 第一次查询某个参数，将此参数的查询结果list放入系统静态变量paramMap的value，并将sysCd+","+paramGp作为key
   * @param sysCd ,paramGp
   */
  private static void loadParamList(String sysCd, String paramGp) {
    String paraStr = sysCd + "," + paramGp;
    List paramList = null;
    try {
      // System.out.println("--------------> 第一次查询此参数："+paraStr);
      SysParamBean bean = new SysParamBean();
      paramList = bean.getSysParamList(sysCd, paramGp);
    } catch (Exception e) {
      log.error(e);
      // System.out.println("[-FAIL->> 参数:" + sysCd + paramGp +
      // "列表获取失败!\n" + e.getMessage());
    }
    if (null != paramList) {
      paramMap.put(paraStr, paramList);
    }
  }

  /**
   * @description: 根据sysCd,paramGp查询系统静态变量paramMap，如果没有则进行此参数的首次加载
   * @param sysCd ,paramGp
   * @return List
   */
  public static List getParamListByCd(String sysCd, String paramGp) {
    String paraStr = sysCd + "," + paramGp;
    List paramList = null;
    // //System.out.println("--------------从系统静态变量paramMap获取参数："+paraStr);
    if (!paramMap.containsKey(paraStr)) {
      loadParamList(sysCd, paramGp);
    }
    paramList = (List) paramMap.get(paraStr);
    return paramList;
  }

  /**
   * @description: 根据sysCd,paramGp清空静态变量paramMap中的某个参数,并重新加载
   * @param sysCd ,paramGp
   * @return List
   */
  public static List removeParamListByCd(String sysCd, String paramGp) {
    String paraStr = sysCd + "," + paramGp;
    List paramList = null;
    // //System.out.println("--------------从系统静态变量paramMap清空参数："+paraStr);
    // 如果静态map中已存在此参数，将其清除
    if (paramMap.containsKey(paraStr)) paramMap.remove(paraStr);
    // 重新加载此参数
    loadParamList(sysCd, paramGp);

    paramList = (List) paramMap.get(paraStr);
    return paramList;
  }

  /**
   * @description: 根据sysCd,paramGp,paramValue得到参数名称paramName
   * @param sysCd ,paramGp
   * @return List
   */
  public static String getParamNameByCd(String sysCd, String paramGp, String paramValue) {
    String paraStr = sysCd + "," + paramGp;
    List paramList = null;
    String paramName = "";
    // //System.out.println("--------------从系统静态变量paramMap获取paramName："+paraStr+"  "+paramValue);
    if (!paramMap.containsKey(paraStr)) {
      loadParamList(sysCd, paramGp);
    }
    paramList = (List) paramMap.get(paraStr);
    if (paramList != null && paramList.size() > 0) {
      for (int i = 0; i < paramList.size(); i++) {
        SysParam param = (SysParam) paramList.get(i);
        if (param.getParamValue().equals(paramValue)) paramName = param.getParamName();
      }
    }
    return paramName;
  }

}
