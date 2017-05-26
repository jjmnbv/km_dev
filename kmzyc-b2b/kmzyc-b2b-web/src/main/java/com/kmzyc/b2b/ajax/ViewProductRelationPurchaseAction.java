package com.kmzyc.b2b.ajax;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.ProductsOrderBySale;
import com.kmzyc.b2b.model.ViewProductRelationPurchase;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.ViewProductRelationPurchaseService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.util.StringUtil;
import com.whalin.MemCached.MemCachedClient;

@SuppressWarnings("serial")
@Controller("viewProductRelationPurchaseAction")
@Scope("prototype")
public class ViewProductRelationPurchaseAction extends BaseAction {
  // private static Logger logger = Logger.getLogger(ViewProductRelationPurchaseAction.class);
  private static Logger logger = LoggerFactory.getLogger(ViewProductRelationPurchaseAction.class);
  @SuppressWarnings("unchecked")
  private ReturnResult returnResult;
  @Resource
  ViewProductRelationPurchaseService viewProductRelationPurchaseService;
  @Resource
  ProductPriceService productPriceService;

  @Resource(name = "memCachedClient")
  private MemCachedClient memCachedClient;

  /**
   * 获取购买行为商品
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public String queryViewProductRelationPurchase() {
    String skuID = getRequest().getParameter("skuID");
    String sizeStr = getRequest().getParameter("size");
    try {
      if (!StringUtil.isEmpty(skuID)) {
        int size = 5;
        if (!StringUtil.isEmpty(sizeStr)) {
          size = Integer.parseInt(sizeStr);
        }
        String memcacheKey = "queryViewProductRelationPurchase" + "_" + skuID + "_" + size;
        Object objtemp = memCachedClient.get(memcacheKey);
        if (null != objtemp) {// 如果缓存里有值，直接返回
          returnResult = (ReturnResult) objtemp;
          return SUCCESS;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        if (skuID.indexOf(',') > 0) {
          List list = new ArrayList();
          for (Object obj : skuID.split(",")) {
            list.add(obj);
          }
          params.put("skuIds", list);
        } else {
          params.put("skuId", skuID);
        }
        params.put("relationType", 3);
        params.put("endindex", size);
        List<ViewProductRelationPurchase> list =
            viewProductRelationPurchaseService.findRelationProduct(params);
        productPriceService.getPriceBatch(list, true);
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", list);
        savaOrderInfoCached(memcacheKey, returnResult);
      } else {
        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
      }
    } catch (Exception e) {
      logger.error("++++++++++++++获取购买行为商品发生错误" + e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * 获取浏览行为商品
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public String getRelevanceSKU() {
    String skuID = getRequest().getParameter("skuID");
    String sizeStr = getRequest().getParameter("size");
    try {
      int size = 5;
      if (!StringUtil.isEmpty(sizeStr)) {
        size = Integer.parseInt(sizeStr);
      }
      String memcacheKey = "getRelevanceSKU" + "_" + skuID + "_" + size;

      Object objtemp = memCachedClient.get(memcacheKey);
      if (null != objtemp) {// 如果缓存里有值，直接返回
        returnResult = (ReturnResult) objtemp;
        return SUCCESS;
      }
      Map<String, Object> params = new HashMap<String, Object>();
      if (skuID.indexOf(',') > 0) {
        List list = new ArrayList();
        for (Object obj : skuID.split(",")) {
          list.add(obj);
        }
        params.put("skuIds", list);
      } else {
        params.put("skuId", skuID);
      }
      params.put("relationType", 2);
      params.put("endindex", size);
      List<ViewProductRelationPurchase> list =
          viewProductRelationPurchaseService.findRelationProduct(params);
      productPriceService.getPriceBatch(list);
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", list);
      savaOrderInfoCached(memcacheKey, returnResult);
    } catch (Exception e) {
      logger.error("++++++++++++++获取浏览行为商品发生错误" + e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * 查询商品按销量排序
   * 
   * @return
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public String queryViewProductBySalequantity() {
    String skuID = getRequest().getParameter("skuID");
    String sizeStr = getRequest().getParameter("size");
    try {
      if (!StringUtil.isEmpty(skuID)) {
        int size = 5;
        if (!StringUtil.isEmpty(sizeStr)) {
          size = Integer.parseInt(sizeStr);
        }
        String memcacheKey = "queryViewProductBySalequantity" + "_" + skuID + "_" + size;
        Object obj = memCachedClient.get(memcacheKey);
        if (null != obj) {// 如果缓存里有值，直接返回
          returnResult = (ReturnResult) obj;
          return SUCCESS;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("skuId", skuID);
        // params.put("relationType", 3);
        params.put("endindex", size);
        List<ProductsOrderBySale> list =
            viewProductRelationPurchaseService.findProductOrderBySalequantity(params);

        if (null == list || list.isEmpty()) {
          logger.info("queryViewProductBySalequantity 查询排序商品数据为空！");
          returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", "查询排序商品数据为空！");
        }
        // productPriceService.getPriceBatch(list, true);
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", list);
        savaOrderInfoCached(memcacheKey, returnResult);
      } else {
        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
      }
    } catch (Exception e) {
      logger.error("++++++++++++++获取商品排序结果失败！" + e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }

    return SUCCESS;
  }

  public static Date expDate = new Date(360 * 60 * 1000);// 缓存6小时

  /**
   * 将信息保存到memCached中，供查询时使用
   */
  public void savaOrderInfoCached(String key, ReturnResult returnResult) {
    memCachedClient.set(key, returnResult, expDate);
  }

  @SuppressWarnings("unchecked")
  public ReturnResult getReturnResult() {
    return returnResult;
  }

  @SuppressWarnings("unchecked")
  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }
}
