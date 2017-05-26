package com.pltfm.app.servlet;

import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.pltfm.app.entities.OrderDictionary;
import com.pltfm.app.maps.OrderStatusMap;
import com.pltfm.app.service.OrderDictionaryService;
import com.pltfm.app.util.SysConstants;

/**
 * 
 * 
 * 类型解释：Spring启动完成后执行初始化操作 类型表述：预读某些实体的Key-Value，放入map，方便以后使用
 * 
 * @author zengming
 * @version 1.0
 * 
 */
@Component("initialServlet")
public class InitialServlet extends HttpServlet {
  private static final long serialVersionUID = -6305330686097972704L;
  private static final Logger log = Logger.getLogger(InitialServlet.class);
  @Resource
  private OrderDictionaryService orderDictionaryService;

  @SuppressWarnings("unused")
  @Override
  public void init() throws ServletException {
    // InputStream inputstream = null;
    //		
    // String config = getInitParameter("config");
    // URL resourceurl = this.getClass().getResource("/");
    // String resourcepath = resourceurl.getPath();
    //		
    // try
    // {
    // inputstream = new FileInputStream(resourcepath + config);
    // this.orderDictionaryService =null;
    // WebApplicationContext context =
    // WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
    // context.getBean("orderDictionaryService");
    // }
    // catch (FileNotFoundException e)
    // {
    // log.error(e);
    // }
    // XMLInterpreter xmlInterpreter = new XMLInterpreter();
    // xmlInterpreter.loadFromXML(inputstream);
      
    //因配置文件已放到远程zookeeper中，故将代码注释调
    /*ResourceBundle rb = ResourceBundle.getBundle(SysConstants.PROPERTIES_PATH);
    for (Object key : rb.keySet()) {
      log.info(key + "=" + rb.getString(key.toString()));
    }*/
      
    // System.out.println(SysConstants.PATH);
    // HashMap<String,Long> map = OrderStatusMap.getMap();
    // for(String key : map.keySet()){
    // System.out.println(""+key+" : "+map.get(key));
    // }
    List<OrderDictionary> allDictionaryList = null;
    String dictionaryType = null;
    String dictionaryCode = null;
    String dictionaryValue = null;
    Long dictionaryKey = null;
    try {
      allDictionaryList = orderDictionaryService.getAll();
      for (OrderDictionary dictionary : allDictionaryList) {
        dictionaryType = dictionary.getOrderDictionaryType();
        dictionaryKey = dictionary.getOrderDictionaryKey();
        dictionaryCode = dictionary.getOrderDictionaryCode();
        dictionaryValue = dictionary.getOrderDictionaryValue();
        if (dictionaryType != null && dictionaryType.equalsIgnoreCase(SysConstants.ORDER_STATUS)) {
          if (dictionaryCode != null && dictionaryCode.equalsIgnoreCase(SysConstants.NOT_PAY)) {
            OrderStatusMap.Order_Status.Not_Pay.setCode(dictionaryCode);
            OrderStatusMap.Order_Status.Not_Pay.setIndex(0);
            OrderStatusMap.Order_Status.Not_Pay.setCode(dictionaryCode);
            OrderStatusMap.Order_Status.Not_Pay.setValue(dictionaryValue);;
          }
        }
      }
    } catch (Exception e) {
      log.error("发生异常", e);
      log.error(e);
    }
  }
}
