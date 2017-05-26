package com.kmzyc.b2b.util;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

@SuppressWarnings("unchecked")
public class XMLHelper {
  /**
   * 只解析单层解析xml
   * 
   * @param xmlSource
   * @return
   * @throws Exception
   */
  public static Map<String, String> singleLevelAnalyze(String xmlSource) throws Exception {
    SortedMap<String, String> xmlMap = new TreeMap<String, String>();
    Document doc = DocumentHelper.parseText(xmlSource);
    List<Element> elements = doc.getRootElement().elements();
    if (null != elements && !elements.isEmpty()) {
      for (Element el : elements) {
        xmlMap.put(el.getName(), el.getText());
      }
    }
    return xmlMap;
  }

  /**
   * 只解析单层解析xml
   * 
   * @param xmlSource
   * @return
   * @throws Exception
   */
  public static Map<String, String> singleLevelAnalyze(List<String> strList) throws Exception {
    if (null == strList || strList.isEmpty()) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    for (String val : strList) {
      sb.append(val);
    }
    return singleLevelAnalyze(sb.toString());
  }
}
