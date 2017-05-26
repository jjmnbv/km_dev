package com.kmzyc.b2b.ajax;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.BrowsingHis;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.model.ProductStockInterface;
import com.kmzyc.b2b.service.BrowsingHisService;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.ProductStockService;
import com.kmzyc.b2b.service.PromotionInfoService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.util.PromotionCacheUtil;
import com.kmzyc.util.StringUtil;

/**
 * cms促销活动接口
 * 
 * @author hewenfeng
 * 
 */
@Controller("promotionInfoAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class PromotionInfoAction extends BaseAction {
    private static final long serialVersionUID = -3806088059509679619L;
    @Resource
    private PromotionInfoService promotionInfoService;
    @Resource
    private ProductPriceService productPriceService;
    @Resource(name = "productStockServiceImpl")
    private ProductStockService productStockService;
    // 返回至页面的对象
    private ReturnResult returnResult;
    @Resource(name = "browsingHisServiceImpl")
    private BrowsingHisService browsingHisService;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 详细页获取产品信息
     * 
     * @return
     */
    public String getProductInfoForDetailPage() {
        try {
            HttpServletRequest request = this.getRequest();
            String productSkuIdStr = request.getParameter("productSkuId");
            if (StringUtil.isEmpty(productSkuIdStr)) {
                returnResult =
                        new ReturnResult(InterfaceResultCode.FAILED, "失败", "参数productSkuId不能为空");
            } else {
                ProductSku sku = new ProductSku();
                sku.setProductSkuId(Long.valueOf(productSkuIdStr));
                JSONObject json = promotionInfoService.getPromotionInfoByProductToJson(sku);
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", json);
                // executeAddBrowsingHis(sku);
            }
        } catch (Exception e) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", e.getMessage());
        }
        return SUCCESS;
    }

    private void executeAddBrowsingHis(ProductSku sku) {
        final Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
        final String skuCode = sku.getProductSkuCode();
        if (userId == null || StringUtil.isEmpty(skuCode)) {
            return;
        }
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                BrowsingHis browsingHis = new BrowsingHis();
                browsingHis.setContentCode(skuCode);
                browsingHis.setLoginId(userId.intValue());
                // 浏览记录类型1:商品 2:商户
                browsingHis.setBrowsingType(1);
                try {
                    browsingHisService.addBrowsingHis(browsingHis);
                } catch (Exception e) {
                    LOG.error("添加浏览记录异常,userId:" + userId + ",skuCode:" + skuCode, e);
                }
            }
        });
    }

    /**
     * 批量查询产品促销价格
     * 
     * @return
     */
    public String queryBatchPromotionPrice() {
        try {
            HttpServletRequest request = this.getRequest();
            String skuIds = request.getParameter("skuIds");
            if (StringUtil.isEmpty(skuIds)) {
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", "参数skuIds不能为空");
            } else {
                List<ProductSku> pslist = new ArrayList<ProductSku>();
                ProductSku sku = null;
                for (String skuId : skuIds.split(",")) {
                    sku = new ProductSku();
                    sku.setProductSkuId(Long.valueOf(skuId));
                    pslist.add(sku);
                }
                pslist = productPriceService.getPriceBatch(pslist);
                sku = pslist.get(0);
                StringBuilder sb = new StringBuilder("[").append("{'skuId':")
                        .append(sku.getProductSkuId()).append(",'price':")
                        .append(sku.getFinalPrice()).append('}').append(',');
                for (int i = 1, len = pslist.size(); i < len; i++) {
                    sku = pslist.get(i);
                    sb.append("{'skuId':").append(sku.getProductSkuId()).append(",'price':")
                            .append(sku.getFinalPrice()).append('}').append(',');
                }
                sb.append("]");
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功",
                        JSONArray.parseArray(sb.toString()));
            }
        } catch (Exception e) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", e.getMessage());
        }
        return SUCCESS;
    }

    /**
     * 批量查询产品促销价格，根据传入的活动时间
     * 
     * @return
     */
    public String queryBatchPromotionPriceByDate() {
        try {
            HttpServletRequest request = this.getRequest();
            String skuIds = request.getParameter("skuIds");
            String promotionDateStr = request.getParameter("promotionData");
            Date promotionDate = null;
            if (promotionDateStr == null || "".equals(promotionDateStr)) {
                promotionDate = new Date();
            } else {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                promotionDate = dateFormat.parse(promotionDateStr);
            }
            if (StringUtil.isEmpty(skuIds)) {
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", "参数skuIds不能为空");
            } else {
                List<ProductSku> pslist;
                ProductSku sku;
                pslist = productPriceService.getPriceBatchByDate(skuIds, promotionDate);
                sku = pslist.get(0);
                StringBuilder sb =
                        new StringBuilder("[").append("{'skuId':").append(sku.getProductSkuId())
                                .append(",'price':").append(sku.getPrice()).append('}').append(',');
                for (int i = 1, len = pslist.size(); i < len; i++) {
                    sku = pslist.get(i);
                    sb.append("{'skuId':").append(sku.getProductSkuId()).append(",'price':")
                            .append(sku.getPrice()).append('}').append(',');
                }
                sb.append("]");
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功",
                        JSONArray.parseArray(sb.toString()));
            }
        } catch (Exception e) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", e.getMessage());
        }
        return SUCCESS;
    }

    public String getPromotionByProduct() {
        try {
            String productSkuIdStr = this.getRequest().getParameter("productSkuId");
            String productIdStr = this.getRequest().getParameter("productId");
            if (productSkuIdStr == null || productSkuIdStr.isEmpty()) {
                returnResult =
                        new ReturnResult(InterfaceResultCode.FAILED, "失败", "参数productSkuId不能为空");
                return SUCCESS;
            }
            Long productSkuId = Long.valueOf(productSkuIdStr);
            Long productId = null;
            if (productIdStr != null && !productIdStr.isEmpty()) {
                productId = Long.valueOf(productIdStr);
            }
            ProductSku sku = new ProductSku();
            sku.setProductId(productId);
            sku.setProductSkuId(productSkuId);
            JSONObject json = promotionInfoService.getPromotionInfoByProductToJson(sku);
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", json);
        } catch (ServiceException e) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", e.getErrorString());
        } catch (Exception e) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", e.getMessage());
        }
        return SUCCESS;
    }

    @Resource
    private PromotionCacheUtil promotionCacheUtil;

    /**
     * 查询库存
     * 
     * @return
     */
    public String queryStock() {

        try {
            String skuIdsStr = getRequest().getParameter("skuIds");
            if (null != skuIdsStr && skuIdsStr.length() > 0) {
                List<Long> skuIds = new ArrayList<Long>();
                // List<ProductSku> pslist = new ArrayList<ProductSku>();

                for (String skuId : skuIdsStr.split(",")) {
                    Long sid = Long.parseLong(skuId);
                    skuIds.add(sid);

                    // pslist.add(sku);
                }
                List<ProductStockInterface> stockList = productStockService.queryBatchStock(skuIds);
                // pslist = productPriceService.getPriceBatch(pslist);
                if (null == stockList || stockList.isEmpty()) {
                    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "0", stockList);
                } else {
                    ProductStockInterface psi = stockList.get(0);
                    StringBuilder sb = new StringBuilder();
                    sb.append('[');
                    // ProductPromotion pp =
                    // promotionCacheUtil.getProductPromtoionInfoCahce(psi.getSkuId().toString());
                    //
                    // sb.append("{'skuId':" + psi.getSkuId() + ",'sum':" + psi.getSum() + ",
                    // 'status':"
                    // + psi.getStatus() + ",'promotionStock':"
                    // + (pp == null ? 0 : pp.getAvailableQuantity())
                    // + ",'promotionSell':0,'promoBuyNum':0,'minBuy':" + (pp == null ? 0 :
                    // pp.getMin())
                    // + ",'maxBuy':" + (pp == null ? 0 : pp.getMax()) + "}");
                    for (int i = 0, len = stockList.size(); i < len; i++) {
                        psi = stockList.get(i);
                        if (psi == null) {
                            continue;
                        }
                        // ProductPromotion ppi =
                        // promotionCacheUtil.getProductPromtoionInfoCahce(psi.getSkuId().toString());
                        if (i != 0) {
                            sb.append(",");
                        }
                        ProductPromotion pp = promotionCacheUtil
                                .getProductPromtoionInfoCahce(psi.getSkuId().toString());
                        int promotionStock = -1;
                        int min = -1;
                        int max = -1;
                        if (pp != null) {
                            if (pp.getAvailableQuantity() != null) {
                                promotionStock = pp.getAvailableQuantity().intValue();
                            }
                            if (pp.getMin() != null) {
                                min = pp.getMin().intValue();
                            }
                            if (pp.getMax() != null) {
                                min = pp.getMax().intValue();
                            }
                        }
                        // pslist = productPriceService.getPriceBatch(pslist);
                        sb.append("{'skuId':" + psi.getSkuId() + ",'sum':" + psi.getSum()
                                + ", 'status':" + psi.getStatus()
                                + ",'promotionStock':0,'promotionSell':0,'promoBuyNum':"
                                + promotionStock + ",'minBuy':" + min + ",'maxBuy':" + max + "}");
                    }
                    sb.append(']');
                    returnResult =
                            new ReturnResult(InterfaceResultCode.SUCCESS, "0", sb.toString());
                }
            }
        } catch (Exception e) {
            LOG.error("", e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", e.getMessage());
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
