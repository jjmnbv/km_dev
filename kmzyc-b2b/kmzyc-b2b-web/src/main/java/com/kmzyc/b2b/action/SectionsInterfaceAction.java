package com.kmzyc.b2b.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.SectionsService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class SectionsInterfaceAction extends BaseAction {
  private static Logger logger = LoggerFactory.getLogger(SectionsInterfaceAction.class);

  @Resource(name = "sectionsServiceImpl")
  private SectionsService sectionsService;

  @Resource(name = "productPriceService")
  private ProductPriceService productPriceService;

  // 返回至页面的对象
  private ReturnResult returnResult;

  /**
   * 查询热销栏目下的商品信息
   * 
   * @return
   */
  public String loadHotSaleProducts() {
    try {
      List<ProductSku> productSkuList =
          sectionsService.queryProductSkuBySections(Constants.SECTION_INDENTIFICATION_REXIAO);
      // 获取商品的真实价格及促销信息
      productPriceService.getPriceBatch(productSkuList);
      // 返回至页面的对象
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", productSkuList);
    } catch (SQLException e) {
      logger.error("查询热销栏目下的商品信息出错：" + e.getMessage(), e);
      // 返回至页面的对象
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    } catch (Exception e) {
      logger.error("查询热销栏目下的商品信息出错：" + e.getMessage(), e);
      // 返回至页面的对象
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
