package com.pltfm.app.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ActionException;
import com.pltfm.app.service.ExpressLogService;

/**
 * @author hekai
 * @since JDK1.6
 * @history 2015-12-8 快递信息同步日志ACTION
 */
@Controller("expressLogAction")
@Scope("prototype")
public class ExpressLogAction extends BaseAction {

  /**
   * UID
   */
  private static final long serialVersionUID = 1432296410720642079L;

  @Resource
  private ExpressLogService expressLogService;

  private Map<String, String> map = new HashMap<String, String>();

  /**
   * 日志列表
   * 
   * @return
   * @throws ActionException
   */
  public String pageList() throws ActionException {

    try {
      // 查询取现记录数
      int count = expressLogService.queryExpressLogCount(map);
      // 分页查询参数
      map.put("startRow", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      map.put("endRow", (getPage().getPageNo() * getPage().getPageSize()) + "");
      // page赋值
      getPage().setRecordCount(count);
      getPage().setDataList(expressLogService.queryExpressLogListByPage(map));
    } catch (Exception ex) {
      throw new ActionException("物流同步日志异常：" + ex.getMessage());
    }
    return "PageList";
  }

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }



}
