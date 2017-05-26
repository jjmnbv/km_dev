package com.pltfm.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.commons.exception.ActionException;
import com.pltfm.app.service.OrderSyncService;

@Controller("orderSyncAction")
@Scope("prototype")
public class OrderSyncAction extends BaseAction {

  private static final long serialVersionUID = 1L;

  private Logger log = Logger.getLogger(OrderSyncAction.class);

  private Map<String, String> map = new HashMap<String, String>();// 查询条件map

  private Long syncId;

  private String syncIds;

  @Resource
  private OrderSyncService orderSyncService;

  /**
   * 到订单列表查询页(按状态)
   * 
   * @return
   * @throws ActionException
   */
  public String showOrderSyncList() throws ActionException {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.putAll(map);
    try {
      paramMap.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      paramMap.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(orderSyncService.listCount(paramMap));
      getPage().setDataList(orderSyncService.listOrder(paramMap));

    } catch (Exception e) {
      log.info(e.getMessage());
    }
    return "success";
  }

  /**
   * 订单同步
   */
  public void syncOrder() {
    JSONObject json = new JSONObject();
    boolean rs = false;
    try {
      if (syncId == 0) {
        rs = orderSyncService.syncFailOrders();// 同步所有失败订单
      } else if (syncId == -1 && syncIds != null) {
        rs = orderSyncService.syncOrderList(syncIds.split(","));// 批量同步订单
      } else {
        String[] idsStrings = {syncId.toString()};
        rs = orderSyncService.syncOrderList(idsStrings);// 同步单个订单
      }
    } catch (Exception e) {
      log.error(e);
    }
    json.put("success", rs);
    json.put("title", rs ? "订单同步成功" : "订单同步失败");
    this.strWriteJson(json.toJSONString());
  }

  /**
   * 导出同步数据
   * 
   * @return
   */
  public String exportSync() {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.putAll(map);
    try {
      getHttpServletRequest().setAttribute("dataList", orderSyncService.listOrder(paramMap));
    } catch (Exception e) {
      log.error(e);
    }
    return SUCCESS;
  }

  /**
   * 将字符串返回成Json字符串
   * 
   * @param strJson
   */
  @Override
protected void strWriteJson(String strJson) {
    ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
    PrintWriter pw = null;
    try {
      pw = ServletActionContext.getResponse().getWriter();
      pw.write(strJson);
      pw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (pw != null) {
        pw.close();
      }
    }
  }

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  public Long getSyncId() {
    return syncId;
  }

  public void setSyncId(Long syncId) {
    this.syncId = syncId;
  }

  public String getSyncIds() {
    return syncIds;
  }

  public void setSyncIds(String syncIds) {
    this.syncIds = syncIds;
  }

}
