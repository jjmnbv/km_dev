package com.kmzyc.b2b.third.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.b2b.third.model.wechat.ArticleItem;
import com.kmzyc.b2b.third.model.wechat.ArticleMsg;
import com.kmzyc.b2b.third.model.wechat.TextMsg;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 消息的基类,包括处理将用户推送过来的消息转成我们自己想要的 对象
 * 
 * @author Administrator 2014-05-12
 * 
 */
public class WxMsgXmlUtil {

  private static final Logger log = LoggerFactory.getLogger(WxMsgXmlUtil.class);

  /**
   * 扩展xstream，使其支持CDATA块
   * 
   * @date 2013-05-19
   */
  private static XStream xstream = new XStream(new XppDriver() {
    public HierarchicalStreamWriter createWriter(Writer out) {
      return new PrettyPrintWriter(out) {
        // 对所有xml节点的转换都增加CDATA标记
        boolean cdata = true;

        @SuppressWarnings("unchecked")
        public void startNode(String name, Class clazz) {
          super.startNode(name, clazz);
        }

        protected void writeText(QuickWriter writer, String text) {
          if (cdata) {
            writer.write("<![CDATA[");
            writer.write(text);
            writer.write("]]>");
          } else {
            writer.write(text);
          }
        }
      };
    }
  });

  /**
   * 将推送过来的消息或者事件转换为map
   * 
   * @param request
   * @return
   */
  public static Map<String, String> parseXmlToMap(HttpServletRequest request) {
    Map<String, String> msgMap = new HashMap<String, String>();

    try {
      // 从request中获取到输入流
      InputStream is = request.getInputStream();

      log.info("WxMsgXmlUtil接收request中的输入流------------>" + is.toString());

      // 使用SAX Dom来解析xml
      SAXReader saxReader = new SAXReader();
      Document document = saxReader.read(is);

      // bufferreader方式解读 请勿注释
      // Document document = null;
      //
      // StringBuilder content = new StringBuilder();
      // BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      // String line = null;
      // while ((line = br.readLine()) != null) {
      // content.append(line + "\n");
      // }
      // br.close();
      // document = DocumentHelper.parseText(content.toString());



      // 得到根元素,在此为获得xml节点
      Element root = document.getRootElement();

      // 接收过来的消息格式较简单,并无复杂子节点
      List<Element> childElements = root.elements();

      log.info("WxMsgXmlUtil 获得输入流当中的所有xml节点---------->" + childElements.toString());

      // 循环子节点,并按照key,value的形式存入map
      for (Element element : childElements) {
        msgMap.put(element.getName(), element.getTextTrim());
      }

      // 关闭输入流
      is.close();
      is = null;
    } catch (IOException e) {
      log.error(e.getMessage(),e);
    } catch (DocumentException e) {
      log.error(e.getMessage(),e);
    }
    return msgMap;
  }


  /**
   * 将推送过来的微信组方扫码时间事件转换为map add for 20151009 微信组方数据格式传送变更
   * 
   * @param request
   * @return
   */
  public static Map<String, String> parseForWxGroup(HttpServletRequest request) {
    Map<String, String> msgMap = new HashMap<String, String>();

    try {

      String xmlPara = String.valueOf(request.getHeader("user_scan_product"));


      if (StringUtils.isBlank(xmlPara)) {
        return msgMap;
      }

      // 此处需要服务端 将数据用java.net.URLEncoder.encode(s)数据
      xmlPara = java.net.URLDecoder.decode(xmlPara, "UTF-8");


      // 解决乱码 尝试方案
      // xmlPara = new String(xmlPara.getBytes("ISO-8859-1"), "UTF-8");

      // xmlPara = new String(xmlPara.getBytes("utf-8"));
      // System.out.println("decode方式=" + java.net.URLDecoder.decode(xmlPara, "UTF-8"));
      // System.out.println("ISO-8859-1 转 GB2312="
      // + new String(xmlPara.getBytes("ISO-8859-1"), "GB2312"));
      // System.out.println("ISO-8859-1 转GBK=" + new String(xmlPara.getBytes("ISO-8859-1"), "GBK"));
      //
      // System.out.println("utf-8-1=" + new String(xmlPara.getBytes("utf-8"), "GBK"));
      // System.out.println("utf-8-2=" + new String(xmlPara.getBytes("utf-8"), "GB2312"));
      // System.out.println("ISO-8859-1 转 utf-8编码后的=" + xmlPara);



      // bufferreader方式解读
      Document document = DocumentHelper.parseText(xmlPara);

      // 得到根元素,在此为获得xml节点
      Element root = document.getRootElement();

      // 接收过来的消息格式较简单,并无复杂子节点
      List<Element> childElements = root.elements();

      log.info("WxMsgXmlUtil 获得输入流当中的所有xml节点---------->" + childElements.toString());

      // 循环子节点,并按照key,value的形式存入map
      for (Element element : childElements) {
        msgMap.put(element.getName(), element.getTextTrim());
      }

    } catch (DocumentException e) {
      log.error(e.getMessage(),e);
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage(),e);
    }
    return msgMap;
  }



  /**
   * 文本消息对象转换成xml
   * 
   * @param textMsg 文本消息对象
   * @return xml
   */
  public static String textMsgToXml(TextMsg text) {
    xstream.alias("xml", text.getClass());
    return xstream.toXML(text);
  }

  /**
   * 图文消息转换成xml
   * 
   * @param article
   * @return
   */
  public static String articlesMsgToXml(ArticleMsg article) {
    xstream.alias("xml", article.getClass());
    xstream.alias("item",ArticleItem.class);
    return xstream.toXML(article);
  }

}
