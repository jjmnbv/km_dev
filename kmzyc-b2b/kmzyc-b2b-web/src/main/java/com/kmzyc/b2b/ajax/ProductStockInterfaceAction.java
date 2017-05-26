package com.kmzyc.b2b.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.ProductStockInterface;
import com.kmzyc.b2b.service.OrderMainService;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.ProductStockService;
import com.kmzyc.b2b.service.PromotionInfoService;
import com.kmzyc.b2b.vo.CompositionCarProduct;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;

@SuppressWarnings({"serial", "unchecked"})
@Scope("prototype")
@Controller
public class ProductStockInterfaceAction extends BaseAction {
  // private static Logger logger = Logger.getLogger(ProductStockInterfaceAction.class);
  private static Logger logger = LoggerFactory.getLogger(ProductStockInterfaceAction.class);
  // 返回至页面的对象
  private ReturnResult returnResult;

  @Resource(name = "productStockServiceImpl")
  private ProductStockService productStockService;
  @Resource
  private ProductPriceService productPriceService;
  @Resource(name = "orderMainServiceImpl")
  private OrderMainService orderMainService;
  @Resource
  private PromotionInfoService promotionInfoService;

  private ProductStockInterface productStockInterface = new ProductStockInterface();

  // 获取库存
  // public String queryProductStockById() {
  // // 获取传入参数skuId判断处理
  // String sku = getRequest().getParameter("skuId");
  // if (StringUtils.isEmpty(sku)) {
  // logger.info("库存传入sku为空");
  // returnResult = new ReturnResult(InterfaceResultCode.FAILED, "skuId为空", null);
  // return SUCCESS;
  // }
  // Long skuId = Long.parseLong(sku);
  // Map dataMap = new HashMap();
  // try {
  // productStockInterface = productStockService.queryProductStockById(skuId);
  // if (productStockInterface == null) {
  // logger.info("无数据");
  // returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
  // return SUCCESS;
  // }
  // // 库存总数判断处理<0 设为0d
  // Double sum = productStockInterface.getSum();
  // if (sum != null) {
  // if (sum < 0) {
  // productStockInterface.setSum(0d);
  // }
  //
  // } else {
  // productStockInterface.setSum(0d);
  // }
  // dataMap.put("sum", productStockInterface.getSum());
  // dataMap.put("status", productStockInterface.getStatus());
  // /*** 获取限购信息 ***/
  // try {
  // CarProduct cp = new CarProduct();
  // cp.setProductSkuId(skuId);
  // productPriceService.setPromotionInfoPrice(cp);
  // if (cp.getMinBuy() > 0 || cp.getMaxBuy() > 0 || cp.getPromotionStock() > 0) {
  // int proType = cp.getRestrictionType();
  // Long pid = null;
  // if (0 == proType && null != cp.getSalePromotionInfo()) {
  // pid = cp.getSalePromotionInfo().getPromotionId();
  // } else if (1 == proType) {
  // pid = cp.getDiscountPromotionInfo().getPromotionId();
  // }
  // if (null != pid) {
  // PromotionProduct pp = promotionInfoService.getPromotionBySKU(skuId, pid);
  // if (null != pp) {
  // dataMap.put("minBuy", pp.getMinBuy());
  // dataMap.put("maxBuy", pp.getMaxBuy());
  // int purchaseNum = 0;
  // if (0 != (purchaseNum = pp.getPromotionStock())) {
  // purchaseNum -= pp.getPromotionSell();
  // purchaseNum = 1 > purchaseNum ? -1 : purchaseNum;
  // }
  // dataMap.put("promotionStock", pp.getPromotionStock());
  // dataMap.put("purchaseNum", purchaseNum);
  // dataMap.put("promotionSell", pp.getPromotionSell());
  // }
  // }
  // Object loginId = getSession().getAttribute(Constants.SESSION_USER_ID);
  // if (0 != cp.getMaxBuy() && null != loginId) {
  // Map map = new HashMap();
  // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  // map.put("userId", Long.parseLong(loginId.toString()));
  // map.put("skuId", cp.getProductSkuId());
  // if (null != cp.getPromoStartTime()) {
  // map.put("startDate", sdf.format(cp.getPromoStartTime()));
  // }
  // if (null != cp.getPromoEndTime()) {
  // map.put("endDate", sdf.format(cp.getPromoEndTime()));
  // }
  // BigDecimal buyNum = orderMainService.querySumUserBuySKUNum(map);
  // dataMap.put("buyNum", buyNum);
  // }
  // }
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // /*** 限购信息 **/
  // returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", dataMap);
  // } catch (Exception e) {
  // logger.error("获取库存sql异常" + e.getMessage(), e);
  // returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
  // }
  // return SUCCESS;
  // }

  /**
   * 获取套餐信息
   * 
   * @return
   * @throws Exception
   */
  public String getSuitInfo() throws Exception {
    String suitIds = getRequest().getParameter("suitIds");
    List<Map<String, Object>> rsList = null;
    if (!"undefined".equals(suitIds) && !StringUtils.isEmpty(suitIds)) {
      List<CompositionCarProduct> dataList = productStockService.querySuitInfo(suitIds);
      if (null != dataList && !dataList.isEmpty()) {
        rsList = new ArrayList<Map<String, Object>>();
        for (CompositionCarProduct ccp : dataList) {
          Map<String, Object> map = new HashMap<String, Object>();
          map.put("suitId", ccp.getId());
          map.put("stockCount", ccp.getStockCount());
          rsList.add(map);
        }
      }
    }
    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", rsList);
    return SUCCESS;
  }

  public ReturnResult getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }

  public ProductStockInterface getProductStockInterface() {
    return productStockInterface;
  }

  public void setProductStockInterface(ProductStockInterface productStockInterface) {
    this.productStockInterface = productStockInterface;
  }

}
