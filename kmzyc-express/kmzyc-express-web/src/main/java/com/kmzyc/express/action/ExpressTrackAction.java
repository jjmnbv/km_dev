package com.kmzyc.express.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.express.service.ExpressTrackService;
import com.kmzyc.express.util.Page;

@Controller("expressTrackAction")
@Scope("prototype")
public class ExpressTrackAction extends BaseAction {

  /**
   * UID
   */
  private static final long serialVersionUID = 6150733160889288744L;

  private Logger log = Logger.getLogger(ExpressTrackAction.class);

  @Resource
  private ExpressTrackService expressTrackService;

  private Map<String, String> map = new HashMap<String, String>();

  // 分页对象
  private Page page;

  /**
   * 跟踪详情列表
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
      int count = expressTrackService.queryExpressTrackCount(map);
      // page赋值
      page.setRecordCount(count);
      page.setPageSize(Integer.MAX_VALUE);
      // 分页查询参数
      map.put("startRow", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      map.put("endRow", (getPage().getPageNo() * getPage().getPageSize()) + "");
      // 设置datalist
      page.setDataList(expressTrackService.queryExpressTrackListByPage(map));
    } catch (Exception ex) {
      log.error(ex);
    }
    return "PageList";
  }

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }


}
