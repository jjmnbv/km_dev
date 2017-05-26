package com.kmzyc.b2b.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.Information;
import com.kmzyc.b2b.service.FAQService;
import com.kmzyc.framework.constants.Constants;
import com.km.framework.page.Pagination;

@Controller("FAQAction")
@Scope("prototype")
public class FAQAction extends BaseAction {

  /**
   * 序列号
   */
  private static final long serialVersionUID = 1L;

  // private static Logger logger = Logger.getLogger(FAQAction.class);
  private static Logger logger = LoggerFactory.getLogger(FAQAction.class);
  @Resource(name = "FAQServiceImpl")
  private FAQService faqService;

  private String checkName;

  @SuppressWarnings("unchecked")
  public String queryFAQList() {
    Pagination page = this.getPagination(5, 15);
    // 页面接受查询条件
    Map<String, String> objconditon = (Map<String, String>) page.getObjCondition();
    Map<String, Object> newConditon = new HashMap<String, Object>();

    if (objconditon != null) {
      checkName = objconditon.get("keyWord");
      newConditon.put("keyWord", checkName);
    }
    newConditon.put("faqType", Constants.faq);
    page.setObjCondition(newConditon);
    try {
      this.pagintion = faqService.findFAQListByPage(page);
      if (checkName != null) {
        List<Information> faqList = this.pagintion.getRecordList();
        List<Information> tempList = new ArrayList<Information>();
        for (Iterator<Information> iterator = faqList.iterator(); iterator.hasNext();) {
          Information information = (Information) iterator.next();
          if (information.getName().indexOf(checkName) != -1) {
            String infoName =
                information.getName().replaceAll(checkName, "<a>" + checkName + "</a>");
            information.setName(infoName);
            tempList.add(information);
          }
        }
        this.pagintion.setRecordList(tempList);
      }

    } catch (Exception e) {
      logger.error("查询常见问题出错：" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

}
