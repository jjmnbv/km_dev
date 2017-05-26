package com.kmzyc.b2b.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.action.BaseAction;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.OrderAssessDetailService;
import com.kmzyc.b2b.service.OrderAssessService;
import com.kmzyc.b2b.service.member.ProdAppraiseService;
import com.kmzyc.b2b.vo.AppraisePoint;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;

/**
 * 提供给CMS的关于产品打分的接口
 * 
 * @author Administrator
 */
@SuppressWarnings({"serial", "unchecked"})
@Controller
@Scope("prototype")
public class ProductPointInterfaceAction extends BaseAction {
  // private static Logger logger = Logger.getLogger(ProductPointInterfaceAction.class);
  private static Logger logger = LoggerFactory.getLogger(ProductPointInterfaceAction.class);
  @Resource(name = "orderAssessService")
  private OrderAssessService orderAssessService;

  private ReturnResult returnResult;

  @Resource(name = "loginServiceImp")
  private LoginService loginService;

  @Resource(name = "prodAppraiseServiceImpl")
  private ProdAppraiseService prodAppraiseService;

  @Resource(name = "orderAssessDetailService")
  private OrderAssessDetailService orderAssessDetailService;



  // 前台条件查询： 如果是0：降序 1 ：升序
  private String timeSort;

  private String pointSort;

  // 回复评价信息
  private String replyContent;

  // 被回复的评价ID
  private String appraiseId;

  // 被回复人ID
  private String appraiseManId;

  // 被回复人昵称
  private String appraiseManNickName;

  // 传过来的skuId
  private Integer skuId;

  // 传过来的xing
  private Short star;

  // 分页查看
  private String pageIndex;

  // 店铺Id
  private Long supplierId;

  public String getAssessPointBySku() {
    try {
      if (null != skuId) {
        returnResult =
            new ReturnResult(InterfaceResultCode.SUCCESS, "成功",
                orderAssessService.queryAssessPoint(skuId.longValue()));
      }
    } catch (Exception e) {
      logger.error("根据SKUID查询商品评分信息出错：" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * 根据条件查询商品的评价
   * 
   * @return
   */
  public String findAssessByCondition() {

    try {
      Map<String, Object> map = new HashMap<String, Object>();
      if ("0".equals(timeSort)) {
        map.put("timeSort", "desc");
      } else if ("1".equals(timeSort)) {
        map.put("timeSort", "asc");
      }
      if ("0".equals(pointSort)) {
        map.put("pointSort", "desc");
      } else if ("1".equals(pointSort)) {
        map.put("pointSort", "asc");
      }
      if (skuId != null) {
        map.put("skuId", skuId);
      }
      map.put("pageNum", 10);
      map.put("pageNo", null == pageIndex ? 1 : Integer.parseInt(pageIndex));
      Pagination page = this.getPagination(5, 10);
      page.setObjCondition(map);
      Pagination appraisePoint = orderAssessService.findAppraisListByPage(page);
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", appraisePoint);
    } catch (Exception e) {
      logger.error("根据SKUID查询商品评价总分出错：" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * 打分回复保存
   * 
   * @return
   */
  public String saveAppraiseReply() throws Exception {
    // 判断当前用户是否登陆
    HttpServletRequest request = ServletActionContext.getRequest();
    boolean isLogin = this.isLogin(request);
    // 如果登陆了
    if (isLogin) {
      // 取得用户
      User user = (User) loginService.verifyLogin(request);

      try {
        prodAppraiseService.saveAppraiseReply(user, replyContent, appraiseId, appraiseManId,
            appraiseManNickName);
      } catch (Exception e) {
        logger.error("保存回复失败：" + e.getMessage(), e);
        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "保存失败", null);
      }
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
    } else {
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  public String showSupplierPoint() throws Exception {
    if (null != supplierId) {
      try {
        returnResult =
            new ReturnResult(InterfaceResultCode.SUCCESS, "查询我的店铺的成功",
                orderAssessDetailService.queryShopScore(supplierId));
      } catch (Exception e) {
        logger.error("查询店铺Id为：" + supplierId + "的店铺分数查询出错" + e.getMessage(), e);
        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "查询出错", null);
      }
    } else {
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "查询出错", null);
    }
    return SUCCESS;
  }

  // 判断当前用户是否登陆

  private boolean isLogin(HttpServletRequest request) {
    boolean flag = false;
    User user = (User) loginService.verifyLogin(request);
    if (user != null) {
      flag = true;
    }
    return flag;
  }

  public ReturnResult getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }

  public String getReplyContent() {
    return replyContent;
  }

  public void setReplyContent(String replyContent) {
    this.replyContent = replyContent;
  }

  public String getAppraiseId() {
    return appraiseId;
  }

  public void setAppraiseId(String appraiseId) {
    this.appraiseId = appraiseId;
  }

  public String getAppraiseManId() {
    return appraiseManId;
  }

  public void setAppraiseManId(String appraiseManId) {
    this.appraiseManId = appraiseManId;
  }

  public String getAppraiseManNickName() {
    return appraiseManNickName;
  }

  public void setAppraiseManNickName(String appraiseManNickName) {
    this.appraiseManNickName = appraiseManNickName;
  }

  public Integer getSkuId() {
    return skuId;
  }

  public void setSkuId(Integer skuId) {
    this.skuId = skuId;
  }

  public Short getStar() {
    return star;
  }

  public void setStar(Short star) {
    this.star = star;
  }

  public String getPointSort() {
    return pointSort;
  }

  public void setPointSort(String pointSort) {
    this.pointSort = pointSort;
  }

  public String getTimeSort() {
    return timeSort;
  }

  public void setTimeSort(String timeSort) {
    this.timeSort = timeSort;
  }

  public String getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(String pageIndex) {
    this.pageIndex = pageIndex;
  }

  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  public void sas() {
    String ss =
        "[{\"averageTotlePoint\":0,\"averageTotleStar\":0,\"conutPersonByStar\":0,\"countPersonBySkuId\":0,\"personAccountByStar\":0,\"skuId\":11,\"starId\":1,\"totlePoint\":0,\"totlePointByStar\":0},{\"averageTotlePoint\":0,\"averageTotleStar\":0,\"conutPersonByStar\":0,\"countPersonBySkuId\":0,\"personAccountByStar\":0,\"skuId\":11,\"starId\":2,\"totlePoint\":0,\"totlePointByStar\":0},{\"averageTotlePoint\":0,\"averageTotleStar\":0,\"conutPersonByStar\":0,\"countPersonBySkuId\":0,\"personAccountByStar\":0,\"skuId\":11,\"starId\":3,\"totlePoint\":0,\"totlePointByStar\":0},{\"averageTotlePoint\":0,\"averageTotleStar\":0,\"conutPersonByStar\":0,\"countPersonBySkuId\":0,\"personAccountByStar\":0,\"skuId\":11,\"starId\":4,\"totlePoint\":0,\"totlePointByStar\":0},{\"averageTotlePoint\":0,\"averageTotleStar\":0,\"conutPersonByStar\":0,\"countPersonBySkuId\":0,\"personAccountByStar\":0,\"skuId\":11,\"starId\":5,\"totlePoint\":0,\"totlePointByStar\":0}]";
    List<AppraisePoint> list = JSONObject.parseObject(ss, List.class);
    System.out.println(list);
  }
}
