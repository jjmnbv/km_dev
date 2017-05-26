package com.kmzyc.b2b.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.service.CmsPromotionTaskService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.km.framework.page.Pagination;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class CmsPromotionTaskInterfaceAction extends BaseAction {
  // private static Logger logger = Logger.getLogger(CmsPromotionTaskInterfaceAction.class);
  private static Logger logger = LoggerFactory.getLogger(CmsPromotionTaskInterfaceAction.class);

  @Resource(name = "cmsPromotionTaskService")
  private CmsPromotionTaskService cmsPromotionTaskService;

  // 返回至页面的对象
  private ReturnResult returnResult;

  /**
   * 根据促销类型加载活动（参数1：促销类型;参数2：页数）
   * 
   * @return
   */
  public String queryPromotionListByType() {
    try {
      Integer promotionTypeId =
          Integer.valueOf(ServletActionContext.getRequest().getParameter("promotionTypeId"));
      Integer pageNo = Integer.valueOf(ServletActionContext.getRequest().getParameter("pageNo"));
      this.setPage(pageNo);
      Pagination page = this.getPagination(5, 10);

      Map<String, Object> condition = new HashMap<String, Object>();
      condition.put("promotionTypeId", promotionTypeId);
      page.setObjCondition(condition);
      page = cmsPromotionTaskService.queryForPage(page);
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", page);
    } catch (Exception e) {
      logger.error("查询促销类型下的活动列表失败：" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * 查询过期的活动
   */
  public String queryExpirePromotion() {
    try {
      logger.info("查询过期的活动");
      List list = cmsPromotionTaskService.queryExpirePromotion();
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", list);
    } catch (Exception e) {
      logger.error("查询过期的活动失败：" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  public ReturnResult getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }
}
