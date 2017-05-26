package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.dao.PromotionInfoDao;
import com.kmzyc.b2b.dao.PromotionRuleDataDao;
import com.kmzyc.b2b.model.CmsPromotionTask;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.b2b.model.PromotionRuleData;
import com.kmzyc.b2b.service.CarProductService;
import com.kmzyc.b2b.service.ProductSkuService;
import com.kmzyc.b2b.service.PromotionInfoService;
import com.kmzyc.b2b.service.PromotionProductService;
import com.kmzyc.b2b.util.PromotionInfoUtil;
import com.kmzyc.b2b.util.SupplierType;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.optimization.vo.PresellProductVO;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.optimization.vo.Promotion;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;
import com.kmzyc.promotion.util.PresellCacheUtil;
import com.kmzyc.promotion.util.PromotionCacheUtil;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

// import com.km.framework.common.util.RemoteTool;

@Service("promotionInfoService")
@SuppressWarnings("unchecked")
public class PromotionInfoServiceImpl implements PromotionInfoService {
    private static Logger logger = LoggerFactory.getLogger(PromotionInfoServiceImpl.class);
    @Resource
    private PromotionInfoDao promotionInfoDao;

    @Resource
    private CarProductService carProductService;
    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;

    @Resource
    private PromotionProductService promotionProductService;

    @Resource
    private PromotionRuleDataDao promotionRuleDataDao;

    @Resource
    private PromotionCacheUtil promotionCacheUtil;
    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private PresellCacheUtil presellCacheUtil;

    /**
     * 明细页面获取价格
     * 
     * @throws Exception
     */
    @Override
    public JSONObject getPromotionInfoByProductToJson(ProductSku sku) throws Exception {
        Long skuId = sku.getProductSkuId();
        //
        // try {
        // productSkuQuantityDao.addBrowseQuantity(skuId);
        // } catch (Exception e) {
        // logger.error("",e);
        // }
        // 获取库存、上架下架、价格
        Map<String, Object> newSku = productSkuService.querySkuInfoForDetailPage(skuId.toString());
        JSONObject json = new JSONObject();
        json.put("status", "n");
        Integer productStockSum = 0;
        BigDecimal price;
        if (newSku == null) {
            return json;
        } else {
            price = (BigDecimal) newSku.get("PRICE");
            if (newSku.get("PRODUCT_SKU_CODE") != null) {
                sku.setProductSkuCode(newSku.get("PRODUCT_SKU_CODE").toString());
            }
            // {PRICE=50, SKUID=10465, PRODUCTSTOCKSUM=1023, STATUS=3, SKUSTATUS=1}
            if (!StringUtil.isEmpty(newSku.get("PRODUCTSTOCKSUM"))) {
                productStockSum = ((BigDecimal) newSku.get("PRODUCTSTOCKSUM")).intValue();
            }

            String status = (String) newSku.get("STATUS");
            String skuStatus = (String) newSku.get("SKUSTATUS");
            if (status.equals("3") && skuStatus.equals("1")) {
                json.put("status", "y");
            } else {
                json.put("PROMOTION_PRICE", price);
                return json;
            }
            json.put("stock", productStockSum);
        }
        try {
            ProductPromotion pp = promotionCacheUtil.getProductPromtoionInfoCahce(skuId.toString());
            if (pp != null) {
                JSONObject purchaseInfo = new JSONObject();
                purchaseInfo.put("minBuy", pp.getMin());
                purchaseInfo.put("maxBuy", pp.getMax());
                purchaseInfo.put("purchaseNum", pp.getAvailableQuantity());
                purchaseInfo.put("promotionStock", pp.getAvailableQuantity());
                purchaseInfo.put("promotionSell", 0);
                purchaseInfo.put("sellUpType", pp.getSellUpType());
                json.put(PURCHASE_INFO, purchaseInfo);
                json.put(PROMOTION_PRICE, pp.calculateFinalPrice(price));
                // 卖光后后设置 1停止销售直到活动结束；2恢复原价
                if (pp.getAvailableQuantity() != null && pp.getSellUpType() != null
                        && pp.getAvailableQuantity() <= 0 && pp.getSellUpType() == 1) {
                    json.put("status", "y");
                    json.put("promotionStock", 0);
                    json.put("purchaseNum", 0);
                    json.put("stock", 0);
                }
                List<Promotion> list = pp.getAllPromtotions();
                if (list != null) {
                    json.put(PROMOTION_INFO, this.buildNewPromotionJson(pp));
                }
            } else {
                json.put(PROMOTION_PRICE, price);
            }
        } catch (Exception e) {
            json.put(PROMOTION_PRICE, price);
            logger.error("", e);
        }
        JSONObject presellProdouct = null;
        try {
            // 获取预售商品信息 add by zhuyanling 201160720
            PresellProductVO presellProduct = presellCacheUtil.getPresellCache(skuId);
            if (presellProduct != null) {
                presellProdouct = new JSONObject();
                presellProdouct.put("availableQuantity", presellProduct.getAvailableQuantity());
                presellProdouct.put("prsellStock", presellProduct.getPrsellStock());
            }
        } catch (Exception e) {
            logger.error("查询预售商品基本失败，skuId：{}", e, skuId);
        } finally {
            json.put(PRESELL_PRODUCT_INFO, presellProdouct);
        }
        System.out.println("===" + json.toJSONString());
        return json;
    }

    private List<JSONObject> buildNewPromotionJson(ProductPromotion pp) {

        List<Promotion> list = pp.getAllPromtotions();
        List<JSONObject> promotionListJson = new LinkedList<>();
        if (list != null) {
            for (Promotion p : list) {
                JSONObject promotionJson = new JSONObject();
                promotionJson.put("promotionTypeName", p.getTypeName());
                promotionJson.put("promotionName", p.getName());
                promotionJson.put("promotionId", p.getId());
                promotionJson.put("promotionType", p.getType());
                if (p.getType() == PromotionTypeEnums.GANT.getValue()) {
                    promotionJson.put("gantList", pp.getAffixProductList());
                }

                promotionJson.put("promotionData",
                        p.getData() == null ? BigDecimal.ZERO : ProductPromotion.toBigDecimal(p.getData()));

                promotionListJson.add(promotionJson);
            }
        }


        return promotionListJson;
    }


    /**
     * 明细页面获取价格 for app
     * 
     * @throws Exception
     */
    @Override
    public JSONObject getPromotionInfoByProductToJsonForApp(ProductSku sku) throws Exception {
        Long skuId = sku.getProductSkuId();
        //
        // try {
        // productSkuQuantityDao.addBrowseQuantity(skuId);
        // } catch (Exception e) {
        // logger.error("",e);
        // }
        // 获取库存、上架下架、价格
        Map<String, Object> newSku = productSkuService.querySkuInfoForDetailPage(skuId.toString());
        JSONObject json = new JSONObject();
        json.put("status", "n");
        Integer productStockSum = 0;
        BigDecimal price;
        if (newSku == null) {
            return json;
        } else {
            price = (BigDecimal) newSku.get("PRICE");
            if (newSku.get("PRODUCT_SKU_CODE") != null) {
                sku.setProductSkuCode(newSku.get("PRODUCT_SKU_CODE").toString());
            }
            // {PRICE=50, SKUID=10465, PRODUCTSTOCKSUM=1023, STATUS=3, SKUSTATUS=1}
            if (!StringUtil.isEmpty(newSku.get("PRODUCTSTOCKSUM"))) {
                productStockSum = ((BigDecimal) newSku.get("PRODUCTSTOCKSUM")).intValue();
            }

            String status = (String) newSku.get("STATUS");
            String skuStatus = (String) newSku.get("SKUSTATUS");
            if (status.equals("3") && skuStatus.equals("1")) {
                json.put("status", "y");
            } else {
                json.put(PROMOTION_PRICE, price);
                return json;
            }
            json.put("stock", productStockSum);
        }
        try {
            ProductPromotion pp = promotionCacheUtil.getProductPromtoionInfoCahce(skuId.toString());
            if (pp != null) {
                JSONObject purchaseInfo = new JSONObject();
                purchaseInfo.put("minBuy", pp.getMin());
                purchaseInfo.put("maxBuy", pp.getMax());
                purchaseInfo.put("purchaseNum", pp.getAvailableQuantity());
                purchaseInfo.put("promotionStock", pp.getAvailableQuantity());
                purchaseInfo.put("promotionSell", 0);
                purchaseInfo.put("sellUpType", pp.getSellUpType());
                json.put(PURCHASE_INFO, purchaseInfo);
                json.put(PROMOTION_PRICE, pp.calculateFinalPrice(price));
                // 卖光后后设置 1停止销售直到活动结束；2恢复原价
                if (pp.getAvailableQuantity() != null && pp.getSellUpType() != null
                        && pp.getAvailableQuantity() <= 0 && pp.getSellUpType() == 1) {
                    json.put("status", "y");
                    json.put("promotionStock", 0);
                    json.put("purchaseNum", 0);
                    json.put("stock", 0);
                }
                List<Promotion> list = pp.getAllPromtotions();
                if (list != null) {
                    json.put(PROMOTION_INFO, this.buildNewPromotionJsonForApp(pp));
                }
            } else {
                json.put(PROMOTION_PRICE, price);
            }

            // 计算APP可省金额
            if (pp != null && pp.getPricePromotion() != null) {


                if (pp.getPricePromotion().getType() == PromotionTypeEnums.DISCOUNT.getValue()) {
                    // pc打折
                    price =
                            pp.getPricePromotion().getData() == null ? BigDecimal.ZERO : price
                                    .multiply(pp.getPricePromotion().getData());
                } else {
                    // pc特价
                    price =
                            pp.getPricePromotion().getData() == null ? BigDecimal.ZERO : pp
                                    .getPricePromotion().getData();
                }

            }
            BigDecimal reducePrice = price.subtract(json.getBigDecimal(PROMOTION_PRICE));
            reducePrice =
                    reducePrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : reducePrice;
            json.put(REDUCE_PRICE, ProductPromotion.toBigDecimal(reducePrice));

        } catch (Exception e) {
            json.put(PROMOTION_PRICE, price);
            logger.error("", e);
        }
        return json;
    }

    private List<JSONObject> buildNewPromotionJsonForApp(ProductPromotion pp) {

        List<Promotion> list = pp.getAllPromtotions();
        List<JSONObject> promotionListJson = new LinkedList<>();
        if (list != null) {
            for (Promotion p : list) {
                JSONObject promotionJson = new JSONObject();
                if (p.getType() == PromotionTypeEnums.GANT.getValue()) {
                    if (pp.getAffixProductList() == null) {
                        promotionJson.put("promotionTypeName", p.getTypeName());
                        promotionJson.put("promotionName", p.getName());
                        promotionJson.put("promotionId", p.getId());
                        promotionJson.put("promotionType", p.getType());
                        promotionListJson.add(promotionJson);
                    } else {
                        for (PromotionProductData pd : pp.getAffixProductList()) {
                            promotionJson = new JSONObject();
                            promotionJson.put("promotionTypeName", p.getTypeName());
                            promotionJson.put("promotionName",
                                    pd.getProductTitle() + pd.getSkuAttrValue() + "*" + pd.getNum()
                                            + "件");
                            promotionJson.put("promotionId", p.getId());
                            promotionJson.put("promotionType", p.getType());
                            promotionListJson.add(promotionJson);
                        }
                    }
                } else {
                    promotionJson.put("promotionTypeName", p.getTypeName());
                    promotionJson.put("promotionName", p.getName());
                    promotionJson.put("promotionId", p.getId());
                    promotionJson.put("promotionType", p.getType());
                    promotionListJson.add(promotionJson);
                }


            }
        }


        return promotionListJson;
    }

/**
     * 查询指定时间配置渠道所有审核通过的活动
     * 
     * @param date 为空时 为所有
     * @return
     */
private List<PromotionInfo> getAllPromotionListByTime(Date date) {
        return this.getAllPromotionListByTime(date, ConfigurationUtil.getString("CHANNEL"));
    }

/**
     * 查询指定时间指定渠道所有审核通过的活动
     * 
     * @param date 为空时 为所有
     * @param channel 为空时 为所有
     * @return
     */
private List<PromotionInfo> getAllPromotionListByTime(Date date, String channel) {

        Map<String, Object> conditionMapNow = new HashMap<>();
        conditionMapNow.put("queryDate", date);
        conditionMapNow.put("channel", channel);
    return promotionInfoDao.getPromotionInfoList(conditionMapNow);
    }
/*
    *//**
     * 从数据库获取
     * 
     * @param <T>
     * @param t
     * @param date
     * @throws ServiceException
     */
    @Override
    public <T extends com.kmzyc.b2b.vo.BaseProduct> void setProductPricePromotionInfoByDB(T t,
            Date date) throws ServiceException {
        List<PromotionInfo> getAllPromotionListNow = this.getAllPromotionListByTime(date);
        setProductPricePromotionInfoByDB(t, getAllPromotionListNow);
    }

    @Override
    public <T extends com.kmzyc.b2b.vo.BaseProduct> void setProductPricePromotionInfoByDB(T t,
            List<PromotionInfo> getAllPromotionListNow) throws ServiceException {
        Long productSkuId = t.getProductSkuId();
        CarProduct cp = carProductService.getCarProduct(productSkuId);
        if (cp.getPrice() == null) {
            throw new ServiceException(0, "productSkuId:" + productSkuId + "获取原始价格失败！");
        }
        setProductPricePromotionInfoByDB(t, cp, getAllPromotionListNow);
    }

    // public <T extends BaseProduct> void setProductPricePromotionInfoByDB(T
    // t,ProductSku sku,Productmain pm,Date date)throws ServiceException{
    // List<PromotionInfo> getAllPromotionListNow =
    // this.getAllPromotionListByTime(date);
    // setProductPricePromotionInfoByDB(t,sku,pm,getAllPromotionListNow);
    // }

    private <T extends com.kmzyc.b2b.vo.BaseProduct> void setProductPricePromotionInfoByDB(T t,
                                                                                          CarProduct cp,
                                                                                          List<PromotionInfo> getAllPromotionListNow) throws ServiceException {
        // 将原价设置为最终价格
        t.setFinalPrice(cp.getPrice());
        if (getAllPromotionListNow == null || getAllPromotionListNow.isEmpty()) {
            return;
        }
        PromotionInfo outInfo = null;
        for (PromotionInfo info : getAllPromotionListNow) {
            if (inPromotionInfo(cp, info)) {
                try {
                    setPromotionInfoToBaseProduct(t, info);
                    int type = info.getPromotionType();
                    if (PromotionTypeEnums.SALE.getValue() == type
                            || PromotionTypeEnums.DISCOUNT.getValue() == type) {
                        if (null == outInfo || outInfo.compareTo(info) > 0) {
                            if (cp.getMinBuy() > 0 || cp.getMaxBuy() > 0
                                    || cp.getPromotionStock() > 0) {
                                outInfo = info;
                                t.setMinBuy(cp.getMinBuy());
                                t.setMaxBuy(cp.getMaxBuy());
                                t.setPromotionStock(cp.getPromotionStock());
                                t.setPromotionSell(cp.getPromotionSell());
                                t.setPromoStartTime(info.getStartTime());
                                t.setPromoEndTime(info.getEndTime());
                            }
                        }
                    }
                } catch (SQLException e) {
                    throw new ServiceException(e);
                }
            }
        }
        try {
            // 只针对特价活动才做处理
            if (null != t.getSalePromotionInfo()) {
                HashMap paramMap = new HashMap();
                com.kmzyc.b2b.model.PromotionProduct promotionProduct;
                paramMap.put("productSkuId", t.getProductSkuId());
                paramMap.put("promotionId", t.getSalePromotionInfo().getPromotionId());

                List<com.kmzyc.b2b.model.PromotionProduct> pList =
                        promotionProductService.getPromotionProductBySku(paramMap);
                if (null != pList && pList.size() > 0) {
                    promotionProduct = pList.get(0);
                    t.setPromotionProduct(promotionProduct);
                }
            }

        } catch (SQLException e) {
            logger.error("通过prodcutSkuId和promotionId查询产品异常", e);
        }
        t.initBaseProduct();
    }

    /**
     * 加载活动到商品中并设置规则数据
     * */
    private <T extends com.kmzyc.b2b.vo.BaseProduct> void setPromotionInfoToBaseProduct(T t,
            PromotionInfo info) throws SQLException {
        int type = info.getPromotionType();
        PromotionInfo tOrginfo;
        switch (type) {
            case 10:// 特价
                tOrginfo = t.getSalePromotionInfo();
                if (tOrginfo == null || tOrginfo.compareTo(info) > 0) {
                    t.setSalePromotionInfo(info);
                }
                return;
            case 8:// 打折
                tOrginfo = t.getDiscountPromotionInfo();
                if (tOrginfo == null || tOrginfo.compareTo(info) > 0) {
                    t.setDiscountPromotionInfo(info);
                }
                // return;
            default:
                if (t.getPromotionInfoList() == null)
                    t.setPromotionInfoList(new ArrayList<>());
                t.getPromotionInfoList().add(info);
                // break;
        }
        initRuleDatas(info);
        // t.initBaseProduct();
    }

    /**
     * 判断sku是否参加活动
     * 
     * @param cp
     * @param info
     * @return
     */
    private boolean inPromotionInfo(CarProduct cp, PromotionInfo info) {
        String promotionChannle = info.getChannel();
        String productChannle = cp.getChannel();
        if (productChannle == null) {
            return false;
        }
        if (!productChannle.contains(promotionChannle)) {
            return false;
        }
        Long productSellerId = cp.getSellerId();
        Long infoSellerId = info.getSellerId();
        // 商家类别 ：1所有；2指定入驻商家；3康美自营代销；4后期
        int shopSort = info.getShopSort();
        if (shopSort == 2) {
            boolean isIn = infoSellerId.compareTo(productSellerId) == 0;
            if (!isIn) {
                return false;
            }
        } else if (shopSort == 3) {// 3康美自营代销
            int productSupplierType = cp.getSupplierType();
            if (productSupplierType == SupplierType.SELLER_TYPE_ENTER_SALE.getIndex()) {
                return false;
            }
        }
        String filter = info.getProductFilterSql();
        if (filter == null) {
            filter = "";
        }
        filter = "," + filter + ",";
        switch (info.getProductFilterType()) {
            case 1:
                return true;
            case 2:
                // 获取价格
                com.kmzyc.b2b.model.PromotionProduct pp =
                        promotionInfoDao.getPromotionProduct(cp.getProductSkuId(),
                                info.getPromotionId());
                boolean isIn = null != pp;
                if (!isIn)
                    return false;
                Integer infoType = info.getPromotionType();
                Integer sale = PromotionTypeEnums.SALE.getValue();
                Integer discount = PromotionTypeEnums.DISCOUNT.getValue();
                if (infoType.compareTo(sale) == 0 || infoType.compareTo(discount) == 0) {
                    if (pp.getPrice() == null) {
                        info.setPrivilegeData(cp.getPrice());
                    } else {
                        info.setPrivilegeData(pp.getPrice());
                    }
                    // 取得最终价格
                    cp.setFinalPrice(pp.getPrice());
                    cp.setMinBuy(pp.getMinBuy());
                    cp.setMaxBuy(pp.getMaxBuy());
                    cp.setPromotionStock(pp.getPromotionStock());
                    cp.setPromotionSell(pp.getPromotionSell());
                    cp.setPromoStartTime(info.getStartTime());
                    cp.setPromoEndTime(info.getEndTime());
                }
                return true;
            case 3:

                if (filter.contains("," + cp.getCategoryId() + ",")) {
                    return true;
                }
                break;
            case 4:

                if (filter.contains("," + cp.getBrandId() + ",")) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public String getPromotionCmsUrlPath(Long promotionId) {
        String rootPath = ConfigurationUtil.getString("CMS_ROOT_PATH");
        if (rootPath.endsWith("/")) {
            rootPath = rootPath.substring(0, rootPath.length() - 1);
        }
        Object object =
                memCachedClient.get(ConfigurationUtil.getString("memCached_cmsPromotionStaticUrl")
                        + promotionId);
        if (object != null) {
            rootPath += object.toString();
        }
        return rootPath;
    }

    @Override
    public PromotionInfo getPromotionInfoById(Long promotionId) {

        PromotionInfo promotionInfo = promotionInfoDao.findById(promotionId);
        if (promotionInfo != null) {
            promotionInfo.setPromotionTypeName(PromotionInfoUtil.typeNameMap.get(promotionInfo
                    .getPromotionType()));
            promotionInfo.setCmsUrlPath(getPromotionCmsUrlPath(promotionId));
            // promotionInfo.set
        }
        return promotionInfo;
    }

    /**
     * 从CMS系统获取app首页发布活动列表
     * 
     * @return
     */
    @Override
    public List<com.kmzyc.b2b.model.CmsPromotionTask> getCmsPromotionTaskList() {

        try {
            return promotionInfoDao.findList("PromotionInfo.findCmsPromotion");
        } catch (SQLException e) {

            logger.warn("从CMS系统获取app首页发布活动列表失败", e);
        }
        return null;
    }

    @Override
    public void initRuleDatas(PromotionInfo info) {
        try {
            if (null != info ){
                if(info.getRuleDatas() != null && info.getRuleDatas().size() > 0) {
                    return;
                }
                List<PromotionRuleData> list =
                        promotionRuleDataDao.selectPromotionRuleList(info.getPromotionId());
                info.setRuleDatas(list);
            }
        } catch (SQLException e) {
            logger.error("设置活动规则失败", e);
        }

    }


}
