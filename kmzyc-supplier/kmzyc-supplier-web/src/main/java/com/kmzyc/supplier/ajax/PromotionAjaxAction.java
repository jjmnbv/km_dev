package com.kmzyc.supplier.ajax;

import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;
import com.kmzyc.supplier.action.SupplierBaseAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Controller("promotionAjaxAction")
@Scope("prototype")
public class PromotionAjaxAction extends SupplierBaseAction {

    private static Logger log = LoggerFactory.getLogger(PromotionAjaxAction.class);

    private List<Long> promotionIds;

    private String promotionId;

    private String rtnMessage;

    @Resource
    private PromotionRemoteService promotionRemoteService;

    private String productId;

    public String deletePromotion() {
        boolean haveDone = false;
        Map jsonMap = new HashMap();
        try {
            promotionRemoteService.deletePromotions(promotionId);
            haveDone = true;
        } catch (Exception e) {
            log.error("deletePromotion:::", e);
            haveDone = false;
        }
        String msg = null;
        if (haveDone) {
            msg = "删除成功";
        } else {
            msg = "删除失败";
        }

        jsonMap.put("flag", haveDone);
        jsonMap.put("msg", msg);
        writeJson(jsonMap);
        return null;
    }

    public String deletePromotionProduct() {
        boolean haveDone = false;
        Map jsonMap = new HashMap();
        Integer result = 1;
        try {
            PromotionProduct p = new PromotionProduct();
            p.setPromotionId(Long.valueOf(promotionId));
            p.setPromotionProductIds(productId);
            result = promotionRemoteService.deletePromotionProducts(p);
        } catch (Exception e) {
            log.error("deletePromotionProduct:::", e);
            result = 1;
        }
        String msg = null;
        if (result == 0) {
            msg = "删除成功";
        } else {
            msg = "删除失败";
        }

        jsonMap.put("flag", result);
        jsonMap.put("msg", msg);
        writeJson(jsonMap);
        return null;
    }

    public String copyPromotion() {
        boolean haveDone = false;
        Map jsonMap = new HashMap();
        try {
            Integer resultCode = promotionRemoteService.copyPromotion(Long.valueOf(promotionId)); //返回0成功；1失败
            haveDone = resultCode == 0;
        } catch (Exception e) {
            log.error("复制活动发生异常:::", e);
            haveDone = false;
        }

        String msg = null;
        if (haveDone) {
            msg = "复制成功";
        } else {
            msg = "复制失败";
        }

        jsonMap.put("flag", haveDone);
        jsonMap.put("msg", msg);
        writeJson(jsonMap);
        return null;
    }

    public List<Long> getPromotionIds() {
        return promotionIds;
    }

    public void setPromotionIds(List<Long> promotionIds) {
        this.promotionIds = promotionIds;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
