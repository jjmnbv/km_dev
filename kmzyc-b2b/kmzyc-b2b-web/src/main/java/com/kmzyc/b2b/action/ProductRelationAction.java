package com.kmzyc.b2b.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.service.ProductRelationService;
import com.kmzyc.framework.constants.Constants;

@Controller("productRelationAction")
@Scope("prototype")
public class ProductRelationAction extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  // private static Logger logger = Logger.getLogger(ProductRelationAction.class);
  private static Logger logger = LoggerFactory.getLogger(ProductRelationAction.class);
  @Resource(name = "productRelationService")
  private ProductRelationService productRelationService;

  /**
   * 进入组合页面
   * 
   * @return
   */
  public String gotoCombinePage() {
    try {
      HttpServletRequest request = getRequest();
      String openId = (String) getSession().getAttribute(Constants.SESSION_WX_OPENID);
      String skuId = request.getParameter("productSkuId");
      if (null != skuId) {
        request.setAttribute("relationList",
            productRelationService.querySortCombine(Long.parseLong(skuId), openId));
      }
    } catch (Exception e) {
      logger.error("",e);
    }
    return SUCCESS;
  }
}
