package com.kmzyc.b2b.ajax;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.cms.remote.service.DetailPublishService;
import com.kmzyc.framework.constants.InterfaceResultCode;

// import com.km.framework.common.util.RemoteTool;

/**
 * Description: User: lishiming Date: 14-7-7 上午10:28 Since: 1.0
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class CmsPagePublishAction extends BaseAction {
  // private static Logger logger = Logger.getLogger(CmsPagePublishAction.class);
  private static Logger logger = LoggerFactory.getLogger(CmsPagePublishAction.class);
  @Resource
  private DetailPublishService detailPublishService;

  private Integer pageId;

  private ReturnResult returnResult;

  public String pagePublish() {
    if (pageId == null) {
      logger.info("页面id不能为空");
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "页面id不能为空", null);
      return SUCCESS;
    }
    try {
      // DetailPublishService remoteAdvService =
      // (DetailPublishService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_CMS,
      // "detailPublishService");
      detailPublishService.pageIdParse(pageId);
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
      logger.info("页面发布成功，页面id:" + pageId);
    } catch (Exception e) {
      logger.error("页面发布失败，页面id:" + pageId, e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "页面发布失败失败", null);
    }
    return SUCCESS;
  }

  public Integer getPageId() {
    return pageId;
  }

  public void setPageId(Integer pageId) {
    this.pageId = pageId;
  }

  public ReturnResult getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }
}
