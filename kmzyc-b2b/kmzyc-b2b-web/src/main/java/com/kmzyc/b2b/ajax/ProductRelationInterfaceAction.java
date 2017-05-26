package com.kmzyc.b2b.ajax;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.ProductRelation;
import com.kmzyc.b2b.service.ProductRelationService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;

@Controller("productRelationInterfaceAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class ProductRelationInterfaceAction extends BaseAction {

  // private static Logger logger = Logger.getLogger(ProductRelationInterfaceAction.class);
  private static Logger logger = LoggerFactory.getLogger(ProductRelationInterfaceAction.class);
  @Resource(name = "productRelationService")
  private ProductRelationService productRelationService;
  public Long relationId;
  // 返回至页面的对象
  @SuppressWarnings("rawtypes")
  private ReturnResult returnResult;

  public String findProductRelationCount() {
    logger.info("查套餐最大购买数");
    HttpServletRequest request = ServletActionContext.getRequest();
    String jsoncallback = request.getParameter("callback");
    JSONObject json = new JSONObject();
    JSONArray jsonArr = new JSONArray();
    JSONObject jsonObj = new JSONObject();
    try {
      Map map = productRelationService.findProductRelationCount(relationId);
      if (map != null) {
        logger.info("查套餐最大购买数：" + map.get("count") + ",套餐状态:" + map.get("stauts"));
        json.put("code", InterfaceResultCode.SUCCESS);
        jsonObj.put("count", map.get("count"));
        jsonObj.put("stauts", map.get("stauts"));
        jsonArr.add(jsonObj);
        json.put("returnObject", jsonArr);
        json.put("message", "成功");
      } else {
        json.put("code", InterfaceResultCode.FAILED);
        json.put("returnObject", null);
        json.put("message", "失败");
      }
    } catch (Exception e) {
      json.put("code", InterfaceResultCode.FAILED);
      json.put("returnObject", null);
      json.put("message", "查询套餐信息出错");
      logger.error("查询套餐信息出错：" + e.getMessage(), e);
    }
    try {
      getResponse().getWriter().print(jsoncallback + "(" + json + ")");
    } catch (IOException e) {
      logger.error("查询套餐信息getResponse返回出错：" + e.getMessage(), e);
    }
    return null;
  }

  public String findProductRelation() {
    logger.info("查套餐金额和状态接口");
    HttpServletRequest request = ServletActionContext.getRequest();
    String jsoncallback = request.getParameter("callback");
    JSONObject json = new JSONObject();
    JSONArray jsonArr = new JSONArray();
    JSONObject jsonObj = new JSONObject();
    try {
      ProductRelation productRelation = productRelationService.findProductRelationById(relationId);
      if (productRelation != null) {
        logger.info("查套餐金额:" + productRelation.getTotalRelationPrice() + ",套餐状态:"
            + productRelation.getStatus());
        json.put("code", InterfaceResultCode.SUCCESS);
        jsonObj.put("Price", productRelation.getTotalRelationPrice());
        jsonObj.put("stauts", productRelation.getStatus());
        jsonArr.add(jsonObj);
        json.put("returnObject", jsonArr);
        json.put("message", "成功");
      } else {
        json.put("code", InterfaceResultCode.FAILED);
        json.put("returnObject", null);
        json.put("message", "失败");
      }

    } catch (Exception e) {
      logger.error("查询套餐信息出错：" + e.getMessage(), e);
    }
    try {
      getResponse().getWriter().print(jsoncallback + "(" + json + ")");
    } catch (IOException e) {
      logger.error("查询套餐信息getResponse返回出错：" + e.getMessage(), e);
    }
    return null;
  }

  public ProductRelationService getProductRelationService() {
    return productRelationService;
  }

  public void setProductRelationService(ProductRelationService productRelationService) {
    this.productRelationService = productRelationService;
  }

  public Long getRelationId() {
    return relationId;
  }

  public void setRelationId(Long relationId) {
    this.relationId = relationId;
  }

  @SuppressWarnings("rawtypes")
  public ReturnResult getReturnResult() {
    return returnResult;
  }

  @SuppressWarnings("rawtypes")
  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }

}
