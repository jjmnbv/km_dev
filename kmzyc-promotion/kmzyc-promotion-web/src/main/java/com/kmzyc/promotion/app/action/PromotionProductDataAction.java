package com.kmzyc.promotion.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kmzyc.promotion.app.bean.ResultMessage;
import com.kmzyc.promotion.app.service.PromotionProductDataService;
import com.kmzyc.promotion.app.service.PromotionProductService;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;

/**
 * 
 * @author songmiao 2015/11/18
 */
public class PromotionProductDataAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    @Resource
    private PromotionProductService promotionProductService;
    @Resource
    private PromotionProductDataService promotionProductDataService;
    private PromotionProductData promotionProductData;
    private String productSkuIds;
    // 日志记录
    protected Logger logger = LoggerFactory.getLogger(PromotionProductDataAction.class);

    public void addPromotionProductData() {
        JSONObject json = new JSONObject();
        try {
            PromotionProduct promotionProduct = promotionProductService
                    .queryPromotionProductById(promotionProductData.getPromotionProductId());
            List<PromotionProductData> list = promotionProductDataService
                    .queryListBypromotionProductId(promotionProductData.getPromotionProductId());
            List<PromotionProductData> resultList = Lists.newArrayList();
            // 去重操作
            for (PromotionProductData ppd : list) {
                String skuId = ppd.getProductSkuId().toString();
                if (productSkuIds.indexOf(skuId) >= 0) {

                    productSkuIds = productSkuIds.replace(skuId, "");
                }
            }
            String[] productSkuIdList = productSkuIds.split(",");
            for (String pId : productSkuIdList) {
                if (pId != null && !pId.equals("")) {
                    PromotionProductData p = new PromotionProductData();
                    p.setPrarentSkuId(promotionProductData.getPrarentSkuId());
                    p.setPromotionId(promotionProductData.getPromotionId());
                    p.setPromotionProductId(promotionProductData.getPromotionProductId());
                    p.setProductSkuId(Long.valueOf(pId));
                    p.setNum(1l);// 赠品初始数量默认为1
                    if (promotionProduct.getStatus() != null) {
                        p.setStatus(Long.valueOf(promotionProduct.getStatus()));
                    }
                    resultList.add(p);

                }
            }
            if (resultList.size() > 0) {
                promotionProductDataService.addPromotionProductDataList(resultList);
            }
            json.put("code", 0);
        } catch (Exception e) {
            json.put("code", -1);
        }
        this.strWriteJson(json.toJSONString());
    }

    /**
     * 修改附赠商品数量
     * 
     * @return
     */
    public String updatePromotionProductDataNum() {
        PrintWriter out = null;
        Map<String, Object> jsonMap = Maps.newHashMap();
        ResultMessage rmsg = new ResultMessage();

        // 获取流对象
        try {
            out = super.getPrintWriter();
        } catch (IOException e) {
            logger.error("updatePromotionProductDataNum方法异常：", e);
            return null;
        }

        try {
            rmsg = promotionProductDataService.updatePromotionProductData(promotionProductData);
            jsonMap.put("result", rmsg.getIsSuccess());
            jsonMap.put("msg", rmsg.getMessage());
            jsonMap.put("code", 0);
            out.print(JSON.toJSONString(jsonMap));
        } catch (Exception e) {
            logger.error("updatePromotionProductDataNum方法异常：", e);
            jsonMap.put("result", false);
            jsonMap.put("msg", "修改失败!");
            out.print(JSON.toJSONString(jsonMap));
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

        return null;
    }

    /**
     * 修改附赠商品数量
     * 
     * @return
     */
    public String deletePromotionProductData() {
        PrintWriter out = null;
        Map<String, Object> jsonMap = Maps.newHashMap();
        ResultMessage rmsg = new ResultMessage();

        // 获取流对象
        try {
            out = super.getPrintWriter();
        } catch (IOException e) {
            logger.error("deletePromotionProductData方法异常：", e);
            return null;
        }

        try {
            rmsg = promotionProductDataService.deletePromotionProductData(promotionProductData);
            jsonMap.put("result", rmsg.getIsSuccess());
            jsonMap.put("msg", rmsg.getMessage());
            jsonMap.put("code", 0);
            out.print(JSON.toJSONString(jsonMap));
        } catch (Exception e) {
            logger.error("deletePromotionProductData方法异常：", e);
            jsonMap.put("result", false);
            jsonMap.put("msg", "修改失败!");
            out.print(JSON.toJSONString(jsonMap));
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

        return null;
    }

    public PromotionProductData getPromotionProductData() {
        return promotionProductData;
    }

    public void setPromotionProductData(PromotionProductData promotionProductData) {
        this.promotionProductData = promotionProductData;
    }

    public String getProductSkuIds() {
        return productSkuIds;
    }

    public void setProductSkuIds(String productSkuIds) {
        this.productSkuIds = productSkuIds;
    }


}
