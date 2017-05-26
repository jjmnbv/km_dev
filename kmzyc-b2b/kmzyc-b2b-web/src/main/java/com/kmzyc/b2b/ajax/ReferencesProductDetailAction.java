package com.kmzyc.b2b.ajax;

import java.math.BigDecimal;
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
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.ProductSkuService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.ShopCategorys;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Controller("referencesProductDetailAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class ReferencesProductDetailAction extends BaseAction {

    // private static Logger logger = Logger.getLogger(ReferencesProductDetailAction.class);
    private static Logger logger = LoggerFactory.getLogger(ReferencesProductDetailAction.class);
    // private String productImgServerUrl = ConfigurationUtil.getString("PRODUCT_IMG_PATH");

    // private String productUrl = ConfigurationUtil.getString("CMS_PAGE_PATH");

    private String productSkuCode;

    // 供应商ID
    private Long supplierId;

    // 商品分类
    private Integer productCate;

    private String searchKey;

    private BigDecimal priceStart;
    private BigDecimal priceEnd;
    private Long showNumber;
    // 是否取推介分类
    private String recommed;

    // 排序方式 ： 1: 销量从高到低 2.收藏数从高到低
    private int rankType;

    @Resource(name = "productPriceService")
    private ProductPriceService productPriceService;

    @SuppressWarnings("unchecked")
    private ReturnResult returnResult;

    @Resource(name = "productSkuServiceImpl")
    private ProductSkuService productSkuServiceImpl;

    /**
     * 商品的全部类目以及商品的特别类目的获取
     */
    public String getShopCate() {
        List<ShopCategorys> shopCate = null;
        try {

            if(StringUtil.isEmpty(supplierId)){
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败:商家编号不能为空", null);
                return SUCCESS;
            }

            Map<String, Object> conditionMap = new HashMap<String, Object>();
            conditionMap.put("shopId", supplierId);
            if (null != recommed && !StringUtils.isEmpty(recommed)) {
                conditionMap.put("recommde", recommed);
            } else {
                conditionMap.put("recommde", null);
                recommed = null;
            }
            shopCate = productSkuServiceImpl.findCategorysByShopId(conditionMap, recommed);
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", shopCate);
        } catch (Exception e) {
            logger.error("根据shopId,查询商品类目失败：" + e.getMessage(), e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
        }
        return SUCCESS;
    }

    /**
     * CMS商品排行接口 , 如果捞不到数据则按照商品的上架时间。
     * 
     * @return
     */
    public String getProductRankByCondition() {
        try {
            Map<String, Object> conditionMap = new HashMap<String, Object>();
            List<ProductSku> skuList = new ArrayList<ProductSku>();
            if (null != productCate && productCate.intValue() == 0) { // 所有分类
                conditionMap.put("haveCategory", null);
            } else {
                conditionMap.put("haveCategory", productCate);
            }
            if (null != searchKey && !searchKey.isEmpty()) {
                conditionMap.put("searchKey", searchKey);
            } else {
                conditionMap.put("searchKey", null);
            }
            if (null == priceStart) {
                conditionMap.put("priceStart", null);
            }
            if (null == priceEnd) {
                conditionMap.put("priceEnd", null);
            }
            conditionMap.put("supplierId", supplierId);
            conditionMap.put("priceStart", priceStart);
            conditionMap.put("priceEnd", priceEnd);
            conditionMap.put("rankType", rankType);
            conditionMap.put("showNumber", showNumber);
            conditionMap.put("channle", ConfigurationUtil.getString("CHANNEL") + "%");
            if (rankType == 1) { // 销量排行
                skuList = productSkuServiceImpl.findProductRankByCondition(conditionMap);
                logger.info("根据：销量排序，结果集的大小为：" + skuList.size());
            } else if (rankType == 2) {// 收藏排行
                skuList = productSkuServiceImpl.findProductFavRankByCondition(conditionMap);
                logger.info("根据 ：收藏排序，结果集的大小为：" + skuList.size());
            } else {
                logger.error("rankType传参为" + rankType + "传参错误,查询失败");
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
            }
            if (skuList.size() == 0) { // 没有收藏和销售的信息，按照商品的上架的降序
                conditionMap.put("rankType", 3);
                skuList = productSkuServiceImpl.findProductUpTimeRankByCondition(conditionMap);
                logger.info("由于收藏或销量为0，从而根据 上架时间排序，结果集的大小为：" + skuList.size());
            }
            List<ProductSku> skuPrice = productPriceService.getPriceBatch(skuList);
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", skuPrice);

        } catch (Exception e) {
            logger.error("根据条件查询CMS商品排行接口失败" + e.getMessage(), e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
        }
        return SUCCESS;
    }

    public String getProductDetail() {
        try {

            List<ProductSku> skuList =
                    productSkuServiceImpl.findProductDetailByCode(productSkuCode);
            List<ProductSku> skuPrice = productPriceService.getPriceBatch(skuList);
            for (ProductSku sku : skuPrice) {
                sku.setUrl(ConfigurationUtil.getString("CMS_PAGE_PATH") + sku.getProductSkuId()
                        + ".html");
                for (com.kmzyc.b2b.model.ProductSkuAttr attr : sku.getProductSkuAttrList()) {
                    String CategoryAttrValue = "";
                    if (null != attr.getCategoryAttrValue()) {
                        CategoryAttrValue = attr.getCategoryAttrValue().getCategoryAttrValue();
                    }
                    sku.setCategoryAttrName(attr.getCategoryAttrName() + ":" + CategoryAttrValue);
                }
                if (null != sku.getProductImage()) {
                    String productImgServerUrl = ConfigurationUtil.getString("PRODUCT_IMG_PATH");
                    sku.getProductImage().setImgPath5(
                            productImgServerUrl + sku.getProductImage().getImgPath5());
                    sku.getProductImage().setImgPath7(
                            productImgServerUrl + sku.getProductImage().getImgPath7());
                }

            }
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", skuPrice);
            return SUCCESS;
        } catch (Exception e) {
            logger.error("查询商品详情出错", e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
        }
        return SUCCESS;
    }

    public String getProductUrl() {
        return ConfigurationUtil.getString("CMS_PAGE_PATH");
    }

    public ReturnResult getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult returnResult) {
        this.returnResult = returnResult;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public String getProductImgServerUrl() {
        return ConfigurationUtil.getString("PRODUCT_IMG_PATH");
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getProductCate() {
        return productCate;
    }

    public void setProductCate(Integer productCate) {
        this.productCate = productCate;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public BigDecimal getPriceStart() {
        return priceStart;
    }

    public void setPriceStart(BigDecimal priceStart) {
        this.priceStart = priceStart;
    }

    public BigDecimal getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(BigDecimal priceEnd) {
        this.priceEnd = priceEnd;
    }

    public int getRankType() {
        return rankType;
    }

    public void setRankType(int rankType) {
        this.rankType = rankType;
    }

    public ProductPriceService getProductPriceService() {
        return productPriceService;
    }

    public void setProductPriceService(ProductPriceService productPriceService) {
        this.productPriceService = productPriceService;
    }

    public ProductSkuService getProductSkuServiceImpl() {
        return productSkuServiceImpl;
    }

    public void setProductSkuServiceImpl(ProductSkuService productSkuServiceImpl) {
        this.productSkuServiceImpl = productSkuServiceImpl;
    }

    public Long getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(Long showNumber) {
        this.showNumber = showNumber;
    }

    public String getRecommed() {
        return recommed;
    }

    public void setRecommed(String recommed) {
        this.recommed = recommed;
    }
}
