package com.kmzyc.promotion.presell.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.kmzyc.promotion.app.action.BaseAction;
import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellCriteria;
import com.kmzyc.promotion.presell.service.PresellManagerService;


/**
 * 预售管理类，主要负责预售活动的查询，新增以及信息编辑
 * 
 * @author xuyongsheng
 * @date 2016-07-05
 * @version 0.1
 */
@SuppressWarnings("unchecked")
@Controller("presellManagerAction")
@Scope(value = "prototype")
public class PresellManagerAction extends BaseAction {

  private static final long serialVersionUID = -1523335279076907512L;

  private Logger logger = LoggerFactory.getLogger(PresellProductAuditAction.class);

  @Resource
  private PresellManagerService presellManagerService;

  private PromotionPresell promotionPresell;

  private PromotionPresellCriteria promotionPresellCriteria;

  /**
   * 查询预售活动信息列表（所有）
   * 
   * @return 预售活动信息列表页面
   */
  public String queryPresellManagerList() {
    page = this.getPage();
    String flag = this.getRequest().getParameter("flag");
    if ("0".equals(flag)) {
      promotionPresellCriteria = new PromotionPresellCriteria();
      page.setPageNo(0);
      page.setPageSize(10);
    }
    /*
     * 分页
     */
    int pageIndex = page.getPageNo();
    if (pageIndex == 0) pageIndex = 1;
    int pageSize = page.getPageSize();
    int startIndex = (pageIndex - 1) * pageSize;
    int endIndex = pageSize * pageIndex;
    promotionPresellCriteria.setStartIndex(startIndex < 0 ? 0 : startIndex);
    promotionPresellCriteria.setEndIndex(endIndex < 0 ? 10 : endIndex);
    try {
      Integer count = presellManagerService.queryPresellManagerCount(promotionPresellCriteria);
      page.setRecordCount(count);
      if (count > 0) {
        List<PromotionPresell> promotionPresellList =
            presellManagerService.queryPresellManagerList(promotionPresellCriteria);
        this.getRequest().setAttribute("dataList", promotionPresellList);
        this.getRequest().setAttribute("detailList",
            presellManagerService.queryProductDetailList(promotionPresellList));
      }

    } catch (Exception e) {
      logger.error("查询预售信息失败", e);
    }
    return "success";

  }


  /**
   * 查询预售活动信息详情
   * 
   * @param 预售活动id presellId
   * 
   * @return 预售活动管理详情页面
   */
  public String getPresellManagerDetail() {
    try {
      promotionPresellCriteria = new PromotionPresellCriteria();
      String presellId = (String) this.getRequest().getParameter("presellId");
      if (presellId != null) {
        promotionPresellCriteria.setPresellId(Long.valueOf(presellId));
      }
      List<PromotionPresell> promotionPresellList =
          presellManagerService.queryPresellManagerList(promotionPresellCriteria);
      this.getRequest().setAttribute("info", promotionPresellList.get(0));
      this.getRequest().setAttribute("detailList",
          presellManagerService.queryProductDetailList(promotionPresellList));
    } catch (Exception e) {
      logger.error("查询预售详情信息失败", e);
    }
    return "success";
  }



  /**
   * 提交审核
   * 
   * @param 预售活动id presellId
   * @throws Exception
   */
  public void submitPresellApp() {
    try {
      promotionPresellCriteria = new PromotionPresellCriteria();
      String presellId = (String) this.getRequest().getParameter("presellId");
      promotionPresellCriteria.setPresellId(Long.valueOf(presellId));// 活动id
      promotionPresellCriteria.setPresellStatus(1);// 预售活动状态(提审1)

      presellManagerService.submitPresellApp(promotionPresellCriteria);
    } catch (Exception e) {
      logger.error("预售提交审核失败", e);
      writeJson("failed");
    }
    writeJson("success");
  }

  /**
   * 终止活动
   * 
   * @param 预售活动id presellId
   * @throws Exception
   */
  public void stopPresell() {
    try {
      promotionPresellCriteria = new PromotionPresellCriteria();
      String presellId = (String) this.getRequest().getParameter("presellId");
      promotionPresellCriteria.setPresellId(Long.valueOf(presellId));// 活动id
      promotionPresellCriteria.setPresellStatus(3);// 预售活动状态（终止3）

      presellManagerService.stopPresell(promotionPresellCriteria);
    } catch (Exception e) {
      logger.error("预售终止活动失败", e);
      writeJson("failed");
    }
    writeJson("success");
  }

  /**
   * 删除活动
   * 
   * @param 预售活动id presellId
   * @throws Exception
   */
  public void deletePresellInfo() {
    try {
      String presellId = this.getRequest().getParameter("presellId");
      Long intPersellId = Long.parseLong(presellId);
      presellManagerService.deletePresellInfo(intPersellId);
    } catch (Exception e) {
      logger.error("预售删除活动失败", e);
      writeJson("failed");
    }
    writeJson("success");
  }


  /**
   * 撤销审批
   * 
   * @param 预售活动id presellId
   * @throws Exception
   */
  public void cancelPresellApply() {
    try {
      String presellId = this.getRequest().getParameter("presellId");
      Long intPersellId = Long.parseLong(presellId);
      presellManagerService.cancelPresellApply(intPersellId);
    } catch (Exception e) {
      logger.error("预售撤销审批失败", e);
      writeJson("failed");
    }
    writeJson("success");
  }



  /**
   * 转换为json
   * 
   * @param Object 需要处理的对象
   * 
   */
  public void writeJson(Object obj) {
    String json = JSON.toJSONString(obj);
    strWriteJson(json);
  }

  public PromotionPresellCriteria getPromotionPresellCriteria() {
    return promotionPresellCriteria;
  }

  public void setPromotionPresellCriteria(PromotionPresellCriteria promotionPresellCriteria) {
    this.promotionPresellCriteria = promotionPresellCriteria;
  }


  public PromotionPresell getPromotionPresell() {
    return promotionPresell;
  }

  public void setPromotionPresell(PromotionPresell promotionPresell) {
    this.promotionPresell = promotionPresell;
  }
}
