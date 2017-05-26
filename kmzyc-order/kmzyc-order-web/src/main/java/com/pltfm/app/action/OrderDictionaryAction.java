package com.pltfm.app.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.pltfm.app.entities.OrderDictionary;

@Controller("orderDictionaryAction")
public class OrderDictionaryAction extends BaseAction {

  private List<OrderDictionary> listOrderDictionary;
  private Logger log = Logger.getLogger(OrderDictionaryAction.class);
  /**
	 * 
	 */
  private static final long serialVersionUID = -7671639471145312388L;

  public String getDictionary() {
    try {
      // listOrderDictionary = orderDictionaryService.getAll();
    } catch (Exception e) {
      log.error("获取数据字典异常！", e);
      return ERROR;
    }
    return SUCCESS;
  }

  public List<OrderDictionary> getListOrderDictionary() {
    return listOrderDictionary;
  }

  public void setListOrderDictionary(List<OrderDictionary> listOrderDictionary) {
    this.listOrderDictionary = listOrderDictionary;
  }

}
