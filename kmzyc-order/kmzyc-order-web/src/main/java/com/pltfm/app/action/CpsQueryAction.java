/*
 * 删除cps业务 package com.pltfm.app.action;
 * 
 * import java.util.HashMap; import java.util.Map;
 * 
 * import javax.annotation.Resource;
 * 
 * import org.apache.log4j.Logger; import org.springframework.context.annotation.Scope; import
 * org.springframework.stereotype.Controller;
 * 
 * import com.pltfm.app.service.CpsQueryService; import com.pltfm.app.util.DateTimeUtils; import
 * com.kmzyc.commons.exception.ActionException; import com.kmzyc.commons.exception.ServiceException;
 * 
 *//**
 * cps查询action
 *//*
@Controller("cpsQueryAction")
@Scope("prototype")
public class CpsQueryAction extends BaseAction {

  private static final long serialVersionUID = 1L;
  private Logger log = Logger.getLogger(CpsQueryAction.class);
  @Resource
  private CpsQueryService cpsService;

  private Map<String, String> map = new HashMap<String, String>();

  *//**
   * CPS跳转数据列表
   * 
   * @return
   * @throws ActionException
   *//*
  public String cpsTrackList() throws ActionException {
    try {
      if (null != map && !map.isEmpty()) {
        if ((null == map.get("dateStart") || "".equals(map.get("dateStart")))
            && (null == map.get("dateEnd") || "".equals(map.get("dateStart")))) {
          DateTimeUtils.genDateQuery(map.get("dateRange"), "dateStart", "dateEnd", map);
        }
        map.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
        map.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
        getPage().setRecordCount(cpsService.queryCPSTrackInfoCount(map));
        getPage().setDataList(cpsService.queryCPSTrackInfo(map));
      }
    } catch (ServiceException e) {
      log.error("CPS跳转数据列表发生异常", e);
    }
    return SUCCESS;
  }

  *//**
   * CPS订单标识信息列表
   * 
   * @return
   * @throws ActionException
   *//*
  public String cpsOrderFlagList() throws ActionException {
    try {
      if ((null == map.get("dateStart") || "".equals(map.get("dateStart")))
          && (null == map.get("dateEnd") || "".equals(map.get("dateStart")))) {
        DateTimeUtils.genDateQuery(map.get("dateRange"), "dateStart", "dateEnd", map);
      }
      map.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      map.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(cpsService.queryCPSOrderFlagCount(map));
      getPage().setDataList(cpsService.queryCPSOrderFlag(map));
    } catch (ServiceException e) {
      log.error("CPS订单标识信息列表发生异常", e);
    }
    return SUCCESS;
  }

  *//**
   * CPS请求明细列表
   * 
   * @return
   *//*
  public String cpsRequestList() throws ActionException {
    try {
      if ((null == map.get("dateStart") || "".equals(map.get("dateStart")))
          && (null == map.get("dateEnd") || "".equals(map.get("dateStart")))) {
        DateTimeUtils.genDateQuery(map.get("dateRange"), "dateStart", "dateEnd", map);
      }
      map.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      map.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(cpsService.queryCPSRequestInfoCount(map));
      getPage().setDataList(cpsService.queryCPSRequestInfo(map));
    } catch (ServiceException e) {
      log.error("CPS请求明细列表发生异常", e);
    }
    return SUCCESS;
  }

  *//**
   * CPS订单数据
   * 
   * @return
   *//*
  public String cpsOrderList() throws ActionException {
    try {
      if ((null == map.get("dateStart") || "".equals(map.get("dateStart")))
          && (null == map.get("dateEnd") || "".equals(map.get("dateStart")))) {
        DateTimeUtils.genDateQuery(map.get("dateRange"), "dateStart", "dateEnd", map);
      }
      map.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      map.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(cpsService.queryCPSOrderInfoCount(map));
      getPage().setDataList(cpsService.queryCPSOrderInfo(map));
    } catch (ServiceException e) {
      log.error("CPS请求明细列表发生异常", e);
    }
    return SUCCESS;
  }

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }
}
*/
