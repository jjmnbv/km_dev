package com.pltfm.app.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.express.util.Page;
import com.pltfm.app.service.ExpressLogService;

@Controller("expressCompanyAction")
@Scope("prototype")
public class ExpressCompanyAction extends BaseAction {

  /**
   *UID
   */
  private static final long serialVersionUID = -684678163445153874L;

  private Logger log = Logger.getLogger(ExpressCompanyAction.class);

  @Resource
  private ExpressLogService expressLogService;

  private Map<String, String> map = new HashMap<String, String>();

  // 分页对象
  private Page page;


  /**
   * 物流订阅列表
   * 
   * @return
   * @throws ActionException
   */
  public String pageList() throws ActionException {
    if (page == null) {
      page = new Page();
    }
    try {
      // 查询取现记录数
      int count = expressLogService.queryExpressLogCount(map);
      // page赋值
      page.setRecordCount(count);
      // 分页查询参数
      map.put("startRow", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      map.put("endRow", (getPage().getPageNo() * getPage().getPageSize()) + "");
      // 设置datalist
      page.setDataList(expressLogService.queryExpressLogListByPage(map));
    } catch (Exception ex) {
      throw new ActionException("物流信息订阅异常：" + ex.getMessage());
    }
    return "PageList";
  }
}
