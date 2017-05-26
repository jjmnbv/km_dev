package com.kmzyc.b2b.app;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.BrowsingHis;
import com.kmzyc.b2b.model.ProdAppraise;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.model.Productmain;
import com.kmzyc.b2b.service.BrowsingHisService;
import com.kmzyc.b2b.service.CarProductService;
import com.kmzyc.b2b.service.OrderAssessDetailService;
import com.kmzyc.b2b.service.OrderAssessService;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.ProductSkuQuantityService;
import com.kmzyc.b2b.service.ProductSkuService;
import com.kmzyc.b2b.service.ShopInfoService;
import com.kmzyc.b2b.service.SupplierNewsService;
import com.kmzyc.b2b.service.member.ConsultService;
import com.kmzyc.b2b.service.member.MyFavoriteService;
import com.kmzyc.b2b.util.SupplierType;
import com.kmzyc.b2b.vo.AppraisePoint;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.b2b.vo.CommercialTenantBasicInfo;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.product.remote.service.ProductSkuRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.maps.ApprovalTypeMap;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.Consult;
import com.pltfm.app.vobject.ViewSkuAttr;
import com.whalin.MemCached.MemCachedClient;

/**
 * 商品详情
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings({"unchecked", "BigDecimalMethodWithoutRoundingCalled"})
@Scope("prototype")
@Controller("appProductDetailAction")
public class AppProductDetailAction extends AppBaseAction {
    private static final long serialVersionUID = 3443290927330717006L;

    // private static final Logger logger = Logger.getLogger(AppProductDetailAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppProductDetailAction.class);

    private String productImgServerUrl = ConfigurationUtil.getString("PRODUCT_IMG_PATH");

    // private static final String productHtml = ConfigurationUtil.getString("CMS_PAGE_PATH");
    // private static final String USER_IMG_PATH = ConfigurationUtil.getString("USER_IMG_PATH");

    @Resource(name = "productPriceService")
    private ProductPriceService productPriceService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private OrderAssessService orderAssessService;

    @Resource
    private ConsultService consultService;

    @Resource
    private SupplierNewsService supplierNewService;

    @Resource
    private OrderAssessDetailService orderAssessDetailService;

    @Resource
    private MyFavoriteService myFavoriteService;

    @Resource
    private ShopInfoService shopInfoService;

    @Resource
    private CarProductService carProductService;

    @Resource
    private ProductSkuQuantityService productSkuQuantityService;

    @Resource(name = "browsingHisServiceImpl")
    private BrowsingHisService browsingHisService;

    @Resource
    protected MemCachedClient memCachedClient;
    private ReturnResult<Map<String, Object>> returnResult;

    @Resource
    private ProductSkuRemoteService productSkuRemoteService;

    // 每页多少条
    private int pageNum;
    // 第几页
    private int pageNo;

    // 商品skuid
    private String productSkuId;
    // 商品类型（0：套餐、1：组合）
    private String productRelationType;

    private String code = "200";

    private String message = "";

    private String pointSort;

    private String timeSort;
    private Long uid;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;


    private void setStartParam() {
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        pageNum = jsonParams.getIntValue("pageNum");
        pageNo = jsonParams.getIntValue("pageNo");
        productSkuId = getJsonStr(jsonParams, "productSkuId");
        productRelationType = getJsonStr(jsonParams, "productRelationType");
        pointSort = getJsonStr(jsonParams, "pointSort");
        timeSort = getJsonStr(jsonParams, "timeSort");
        if (StringUtils.isNotBlank(super.getUserid())) {
            uid = Long.parseLong(getUserid());
        }
    }

    /**
     * 根据商品SKUID查询商品组图
     * 
     * @return
     */
    public void getProdImag() throws Exception {
        try {
            this.setStartParam();
            Map<String, Object> map = new HashMap<String, Object>();
            // 商品组图
            List<ProductImage> produtImageList =
                    productSkuService.findAllBySkuId(Long.parseLong(productSkuId));
            List imagList = new ArrayList();
            for (ProductImage prodImag : produtImageList) {
                prodImag.setImgPath(productImgServerUrl + prodImag.getImgPath());
                prodImag.setImgPath1(productImgServerUrl + prodImag.getImgPath1());
                prodImag.setImgPath2(productImgServerUrl + prodImag.getImgPath2());
                prodImag.setImgPath3(productImgServerUrl + prodImag.getImgPath3());
                prodImag.setImgPath4(productImgServerUrl + prodImag.getImgPath4());
                prodImag.setImgPath5(productImgServerUrl + prodImag.getImgPath5());
                prodImag.setImgPath6(productImgServerUrl + prodImag.getImgPath6());
                prodImag.setImgPath7(productImgServerUrl + prodImag.getImgPath7());
                imagList.add(prodImag);
            }
            map.put("produtImageList", imagList);
            setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                    "根据SKUID查询商品组图成功", map));
        } catch (Exception e) {
            logger.error("查询商品SKUID组图出错", e);
            setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                    "根据SKUID查询商品组图出错", null));
        }
        printJsonString(returnResult);
    }

    /**
     * 获取商品说明 请求结果，成功则返回商品的说明信息，包括商品名称、 商品品牌、包装规格、药品属性、剂型、使用方法 、生产厂商、生产产地、上架时间；失败则返回具体的描述信息
     */
    public void getGoodsSpec() {
        try {
            this.setStartParam();
            StringBuilder categoryAttrValueBuffer = new StringBuilder();
            // 查询SKU信息
            // ProductSkuRemoteService par =
            // (ProductSkuRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_PRODUCT,
            // "productSkuService");
            Map<String, Object> map = new HashMap<String, Object>();
            ProductSku productSku = productSkuService.findProductSkuById(Long.parseLong
                    (productSkuId));
            Productmain pm = productSkuService.findProductSupplyType(productSku.getProductId());
            // 查询对应的sku
            List<ProductSku> productSkuList =
                    productSkuService.findSkuListByProductId(pm.getProductId());
            map.put("productName", pm.getProcuctName());
            map.put("brandName", pm.getBrandName());
            map.put("approvalType",
                    pm.getApprovalType() == null ? "" : ApprovalTypeMap.getValue(pm
                            .getApprovalType().toString()));
            map.put("productSkuCode", productSku.getProductSkuCode());
            // 通过用户id和产品skucode查询是否收藏
            boolean isFavorite = false;
            if (uid == null) {
                map.put("isFavorite", isFavorite);
            } else {
                isFavorite = myFavoriteService.isSavedFavorite(uid, productSku.getProductSkuCode());
                // true:false
                map.put("isFavorite", isFavorite);
            }
            map.put("pValue", productSku.getPvalue());
            map.put("productNo", pm.getProductNo());
            map.put("upTime", formatDate(pm.getUpTime(), "yyyy-MM-dd HH:mm:ss"));
            map.put("approvalNo", pm.getApprovalNo());
            // 取得商品介绍
            String prodUrl[] = this.getProductIntro(pm.getIntroduce());
            map.put("productIntro", prodUrl);
            // 自定义属性和基本属性
            List<ViewSkuAttr> viewDIYSkuAttr =
                    productSkuService.findAttrAndValueByProductId(productSku.getProductId());
            List diySkuAttr = new ArrayList();
            if (CollectionUtils.isNotEmpty(viewDIYSkuAttr)) {
                for (ViewSkuAttr skuAttr : viewDIYSkuAttr) {
                    if (skuAttr == null)
                        continue;
                    Map<String, Object> diyAtt = new HashMap<String, Object>();
                    diyAtt.put("categoryAttrName", skuAttr.getCategoryAttrName());
                    diyAtt.put("categoryAttrValue", skuAttr.getCategoryAttrValue());
                    diySkuAttr.add(diyAtt);
                }
            }
            map.put("DIYSkuAttr", diySkuAttr);
            // 取得SKU属性
            List<CategoryAttr> prodSkuAttr =
                    productSkuRemoteService.findSkuAttrByProductId(productSku.getProductId());
            if (CollectionUtils.isNotEmpty(prodSkuAttr)) {
                List skuAttrValue = new ArrayList();
                for (CategoryAttr categoryAttr : prodSkuAttr) {
                    if (categoryAttr == null)
                        continue;
                    Map<String, Object> catgoryAttrM = new HashMap<String, Object>();
                    catgoryAttrM.put("categoryAttrName", categoryAttr.getCategoryAttrName());
                    catgoryAttrM.put("productSkuId", productSku.getProductSkuId());
                    for (CategoryAttrValue skuAttr : categoryAttr.getCategoryAttrValueList()) {
                        if (skuAttr == null)
                            continue;
                        Map<String, Object> skuAttrM = new HashMap<String, Object>();
                        skuAttrM.put("categoryAttrName", categoryAttr.getCategoryAttrName());
                        skuAttrM.put("categoryAttrValue", skuAttr.getCategoryAttrValue());
                        skuAttrM.put("categoryAttrId", skuAttr.getCategoryAttrId());
                        skuAttrM.put("categoryAttrValueId", skuAttr.getCategoryAttrValueId());
                        skuAttrValue.add(skuAttrM);
                    }
                    skuAttrValue.add(catgoryAttrM);
                }
                // 一个产品对应的sku值
                map.put("myCategoryAttr", skuAttrValue);
            }
            List myCategoryAttr = new ArrayList();
            for (ProductSku prodSku : productSkuList) {
                List<ViewSkuAttr> viewSkuAttr =
                        productSkuRemoteService.findBySkuId(prodSku.getProductSkuId());
                if (CollectionUtils.isEmpty(viewSkuAttr))
                    continue;
                for (ViewSkuAttr viewSkuAttr1 : viewSkuAttr) {
                    if (viewSkuAttr1 == null)
                        continue;
                    Map<String, Object> mySku = new HashMap<String, Object>();
                    mySku.put("myCategoryAttrValue", viewSkuAttr1.getCategoryAttrValue());
                    mySku.put("myCategoryAttrValueId", viewSkuAttr1.getCategoryAttrValueId());
                    if (prodSku.getProductSkuId().toString().equals(productSkuId)) {
                        categoryAttrValueBuffer.append(" ").append(
                                viewSkuAttr1.getCategoryAttrValue());
                    }
                    mySku.put("myCategoryAttrId", viewSkuAttr1.getCategoryAttrId());
                    mySku.put("myCategoryAttrName", viewSkuAttr1.getCategoryAttrName());
                    mySku.put("mySkuId", prodSku.getProductSkuId());
                    myCategoryAttr.add(mySku);
                }
            }
            map.put("prodSkuValue_in", myCategoryAttr);
            map.put("productTitle", pm.getProductTitle() + categoryAttrValueBuffer);
            map.put("productSubTitle", pm.getProductSubtitle());
            map.put("marketPrice", productSku.getMarketPrice());
            // map.put("price", productSku.getPrice());
            Map<String, BigDecimal> priceSkuMap = new HashMap<String, BigDecimal>();
            priceSkuMap.put(productSkuId, productSku.getPrice() == null ? new BigDecimal("0.00")
                    : productSku.getPrice());
            Map<String, BigDecimal> pricePromotionMap =
                    productPriceService.getPromotionPricetBySkuIds(priceSkuMap);
            map.put("price", pricePromotionMap.get(productSkuId));

            // 查询商品对应的评价
            List<AppraisePoint> appraisePointList =
                    orderAssessService.findAppraisPointBySkuId(Integer.parseInt(productSkuId));
            if (CollectionUtils.isNotEmpty(appraisePointList)) {
                BigDecimal avg = null;
                map.put("appraiseCountPerson", appraisePointList.get(0).getCountPersonBySkuId());
                if (0 == appraisePointList.get(0).getCountPersonBySkuId()) {
                    map.put("appraiseCountPoint", 0);
                } else if ((avg =
                        new BigDecimal(appraisePointList.get(0).getTotlePoint())
                                .divide(new BigDecimal(appraisePointList.get(0)
                                        .getCountPersonBySkuId()), 2)).compareTo(new BigDecimal(5)) > 0) {
                    map.put("appraiseCountPoint", 5);
                } else {
                    map.put("appraiseCountPoint", avg.setScale(2));
                }
            }
            // 获取商品的剩余库存
            CarProduct carp = new CarProduct();
            carp.setProductSkuId(Long.parseLong(productSkuId));
            carp = carProductService.updateCarProduct(carp);
            map.put("stockNumber", carp.getStockCount());
            // 获取当前商品的出库仓
            String wareHouseName =
                    productSkuService.findProductWareHouseBySkuId(Long.parseLong(productSkuId));
            map.put("wareHouseName", wareHouseName);
            // 获取供应商电话
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("supplierId", pm.getShopCode());
            CommercialTenantBasicInfo com =
                    supplierNewService
                            .queryCommercialBySupplierId(Long.parseLong(pm.getShopCode()));
            if (null != com && !StringUtil.isEmpty(com.getFixedPhone())) {
                map.put("supplyTel", com.getFixedPhone());
            } else {
                map.put("supplyTel", "400-6600-518");
            }
            // 商品的上下架状态
            if (null != pm.getStatus() && "3".equals(pm.getStatus())) {
                map.put("upStatus", true);
            } else {
                map.put("upStatus", false);
            }

            // 获取当前商品的链接
            String productUrl =
                    ConfigurationUtil.getString("CMS_PAGE_PATH") + productSkuId + ".html";
            map.put("productUrl", productUrl);

            /** 获取店铺信息 start **/
            Map<String, Object> resultMap =
                    orderAssessDetailService.queryShopScore(Long.valueOf(pm.getShopCode()));
            map.put("prodDescPoint", resultMap.get("assess_Type_one"));// 宝贝描述相符评分
            map.put("sendSpeedPoint", resultMap.get("assess_Type_two"));// 卖家发货速度评分
            map.put("dispSpeedPoint", resultMap.get("assess_Type_three"));// 物流配送速度评分
            map.put("servicePoint", resultMap.get("assess_Type_four"));// 售前售后服务评分
            map.put("score", resultMap.get("avergScore"));// 综合评分
            /** 获取店铺信息 end **/


            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("skuId", productSkuId);
            paramMap = shopInfoService.querySimpleShopInfoByMap(paramMap);


            map.put("shopId", paramMap != null ? paramMap.get("SHOP_ID") : "");
            map.put("shopName", paramMap != null ? paramMap.get("SHOP_NAME") : "");

            map.put("isCollect",
                    paramMap != null ? myFavoriteService.isSavedFavoriteShop(Long.valueOf(1),
                            paramMap.get("SHOP_ID").toString()) : "");
            map.put("collectCount",
                    paramMap != null ? myFavoriteService.findFavoriteShopUserCount(paramMap.get(
                            "SHOP_ID").toString()) : "");


            // 该sku的单品运费 以及店铺运费 20151008 add mlq begin
            map.put("freightForProduct", productSku.getFreight());

            // 20151111 add 新塞入单品是否免邮 标识字段
            map.put("freeStatus", StringUtils.isBlank(productSku.getFreeStatus()) ? "0"
                    : productSku.getFreeStatus());

            // 标识该产品是自营还是商家入驻的 自营的shop_code标识为221,如果是该字段为true,反之则为false;
            boolean isKmSelfFlag =
                    String.valueOf(Constants.SELF_SELLER_ID).equals(pm.getShopCode())
                            || String.valueOf(SupplierType.SELLER_TYPE_SALE_PROXY.getIndex())
                                    .equals(pm.getIsKmSelf()) ? true : false;
            map.put("isKmSelf", isKmSelfFlag);

            // 首重运费
            BigDecimal firstHeavyFreight = BigDecimal.ZERO;
            // 满多少包邮
            BigDecimal freeFreight = BigDecimal.ZERO;

            // 该商品所属的店铺运费
            if (!isKmSelfFlag) {
                // 查询店铺运费

                Map<Object, Object> freightInfoMap =
                        shopInfoService.queryShopFareBySupplierId(Long.valueOf(pm.getShopCode()));
                if (freightInfoMap != null && !freightInfoMap.isEmpty()) {
                    firstHeavyFreight =
                            freightInfoMap.get("FIRST_HEAVY_FREIGHT") == null ? firstHeavyFreight
                                    : (BigDecimal) freightInfoMap.get("FIRST_HEAVY_FREIGHT");
                    freeFreight =
                            freightInfoMap.get("FREE_PRICE") == null ? freeFreight
                                    : (BigDecimal) freightInfoMap.get("FREE_PRICE");
                }
            }

            map.put("firstHeavyFreight", firstHeavyFreight);
            map.put("freeFreight", freeFreight);
            // 该sku的单品运费 以及店铺运费 20151008 add end

            // 商品图片
            List<ProductImage> produtImageList =
                    productSkuService.findAllIsDefaultByProductId(pm.getProductId());
            // ProductImage prodImagTmp = null;
            // List imagList = new ArrayList();
            // for (ProductImage prodImag : produtImageList) {
            // prodImagTmp = new ProductImage();
            // prodImagTmp.setImgPath4(productImgServerUrl + prodImag.getImgPath4());
            // prodImagTmp.setImgPath5(productImgServerUrl + prodImag.getImgPath5());
            // prodImagTmp.setImgPath6(productImgServerUrl + prodImag.getImgPath6());
            // imagList.add(prodImagTmp);
            // }
            // map.put("produtImageList", produtImageList);

            List skuFinalPriceList = new ArrayList();
            Map skuFinalPriceMap = null;
            /*
             * Map<String, BigDecimal> skuIdPriceSkuMap = new HashMap<String, BigDecimal>();
             * skuIdPriceSkuMap.put(productSkuId, productSku.getPrice()==null?new
             * BigDecimal("0.00"):productSku.getPrice()); Map<String, BigDecimal>
             * skuIdPricePromotionMap =
             * productPriceService.getPromotionPricetBySkuIds(skuIdPriceSkuMap); map.put("price",
             * skuIdPricePromotionMap.get(productSkuId));
             */
            // 价格
            Map<String, BigDecimal> skuIdPriceSkuMap = new HashMap<String, BigDecimal>();
            ProductSku productSkuPrice = null;
            for (ProductImage img : produtImageList) {
                ProductSku sku = new ProductSku();
                sku.setProductSkuId(img.getSkuId());
                // 商品最终价格
                // promotionInfoService.setProductPricePromotionInfoByDB(productSku, new Date());
                productSkuPrice = productSkuService.findProductSkuById(img.getSkuId());
                skuFinalPriceMap = new HashMap();
                skuFinalPriceMap.put("skuId", img.getSkuId());
                skuIdPriceSkuMap.put(img.getSkuId().toString(),
                        productSkuPrice.getPrice() == null ? new BigDecimal("0.00")
                                : productSkuPrice.getPrice());
                Map<String, BigDecimal> skuIdPricePromotionMap =
                        productPriceService.getPromotionPricetBySkuIds(skuIdPriceSkuMap);
                skuFinalPriceMap.put("finalPrice",
                        skuIdPricePromotionMap.get(img.getSkuId().toString()));
                // skuFinalPriceMap.put("finalPrice", productSku.getFinalPrice());
                skuFinalPriceMap.put("img4", productImgServerUrl + img.getImgPath4());
                skuFinalPriceMap.put("img5", productImgServerUrl + img.getImgPath5());
                skuFinalPriceMap.put("img6", productImgServerUrl + img.getImgPath6());
                skuFinalPriceList.add(skuFinalPriceMap);
            }

            map.put("produtImageList", skuFinalPriceList);

            setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                    "根据SKUID查询商品属性成功", map));

        } catch (Exception e) {
            logger.error("根据SKUID查询商品属性失败", e);
            setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                    "根据SKUID查询商品属性失败", null));
        }

        try {
            if (StringUtils.isNotBlank(super.getUserid())) {
                productSkuQuantityService.addBrowseQuantity(Long.valueOf(productSkuId));// 纯属临时行为，增加一条sku的浏览记录

                // 20151009 add begin 添加用户浏览记录 放入到商品详情接口里面
                // executeAddBrowsingHis(productSku);
            }
        } catch (Exception e) {
            logger.error("根据SKUID插入浏览记录失败", e);
        }

        printJsonString(returnResult);
    }

    /**
     * 
     * 获取商品绑定的套餐 、组合
     */
    public void getGoodsSeriesInfo() {
        Map<String, Object> rroductRelation = new HashMap<String, Object>();
        List returnProductRelationlist = new ArrayList();// 返回套餐组合头List
        List returnProductRelationDetaillist = null;// 套餐组合明细List
        Map<String, Object> prMap = null;// 套餐组合头map
        Map<String, Object> mapPrd = null;// 套餐组合明细map
        String relationSkuId = null; // 明细商品id（套餐、组合）
        String productCount = null;// 明细数量数量（套餐）
        String relationSkuPrice = null;// 明细商品价格（套餐、明细）
        String skuImage = null;// 明细图片链接（套餐、明细）
        String productName = null;// 商品名称（套餐、明细）
        String relationId = null;// 头id（套餐、明细）
        String totalRelationPrice = null;// 头价格（套餐）
        String relationProductSumPrice = null;// // 头单品合计价格（套餐）
        String relationName = null;// 头名（套餐、明细）
        String relationIdKey = null;// 标志位
        boolean isFalse = true;
        Map<String, BigDecimal> skuIdPriceSkuMap = new HashMap<String, BigDecimal>();// sku表价格map
        Map<String, BigDecimal> skuIdPricePromotionMap = new HashMap<String, BigDecimal>();// 促销返回价格map
        try {
            this.setStartParam();
            Map<String, Object> typeAndSkuIdMap = new HashMap<String, Object>();
            if (Strings.isNullOrEmpty(productSkuId)) {
                code = "0";
                isFalse = false;
                message = "传入参数错误,查询失败!";
            } else {
                typeAndSkuIdMap.put("productSkuId", productSkuId);
            }
            if (productRelationType.isEmpty()) {
                code = "0";
                message = "传入参数错误,查询失败!";
                isFalse = false;
            } else {
                typeAndSkuIdMap.put("productRelationType", productRelationType);
            }
            if (isFalse) {
                List<Map> productRelationlist =
                        productSkuService.findProductRelation(typeAndSkuIdMap, productRelationType);
                // 套餐
                if (CollectionUtils.isEmpty(productRelationlist)) {
                    if (productRelationType.toString().equals("0")) {
                        message = "此商品不存在套餐！";
                    } else {
                        message = "此商品不存在组合！";
                    }
                } else {
                    // 组合价格从促销系统拿
                    if (productRelationType.equals("1")) {
                        for (Map productRelationMap : productRelationlist) {
                            if (!(skuIdPriceSkuMap.containsKey(productRelationMap.get(
                                    "RELATION_SKU_ID").toString()) || productRelationMap
                                    .get("RELATION_SKU_ID") == null)) {// 判断是否有重复key，是否为空
                                skuIdPriceSkuMap
                                        .put(productRelationMap.get("RELATION_SKU_ID").toString(),
                                                new BigDecimal(productRelationMap
                                                        .get("RELATION_SKU_PRICE") == null ? "0.00"
                                                        : productRelationMap.get(
                                                                "RELATION_SKU_PRICE").toString()));
                            }
                        }
                        skuIdPricePromotionMap =
                                productPriceService.getPromotionPricetBySkuIds(skuIdPriceSkuMap);
                    }
                    for (Map productRelationMap : productRelationlist) {
                        relationSkuId =
                                productRelationMap.get("RELATION_SKU_ID") == null ? ""
                                        : productRelationMap.get("RELATION_SKU_ID").toString();
                        if (productRelationType.equals("0")) {// 套餐
                            relationSkuPrice =
                                    productRelationMap.get("RELATION_SKU_PRICE") == null ? "0.00"
                                            : productRelationMap.get("RELATION_SKU_PRICE")
                                                    .toString();
                        } else {// 组合
                            relationSkuPrice = skuIdPricePromotionMap.get(relationSkuId).toString();
                        }
                        skuImage =
                                productRelationMap.get("IMG_PATH") == null ? ""
                                        : productImgServerUrl
                                                + productRelationMap.get("IMG_PATH").toString();
                        productName =
                                productRelationMap.get("PROCUCT_NAME") == null ? ""
                                        : productRelationMap.get("PROCUCT_NAME").toString();
                        // 组合不存在商品数量和套餐价格
                        if (productRelationType.equals("0")) {
                            productCount =
                                    productRelationMap.get("PRODUCT_COUNT") == null ? ""
                                            : productRelationMap.get("PRODUCT_COUNT").toString();

                            totalRelationPrice =
                                    productRelationMap.get("TOTAL_RELATION_PRICE") == null ? ""
                                            : productRelationMap.get("TOTAL_RELATION_PRICE")
                                                    .toString();
                        }
                        relationId =
                                productRelationMap.get("RELATION_ID") == null ? ""
                                        : productRelationMap.get("RELATION_ID").toString();
                        relationProductSumPrice =
                                productRelationMap.get("SUM_RELATION_SKU_PRICE") == null ? ""
                                        : productRelationMap.get("SUM_RELATION_SKU_PRICE")
                                                .toString();

                        relationName =
                                productRelationMap.get("RELATION_NAME") == null ? ""
                                        : productRelationMap.get("RELATION_NAME").toString();
                        if (!productRelationMap.get("RELATION_ID").toString()
                                .equals((relationIdKey))) {
                            // 明细
                            mapPrd = new HashMap<String, Object>();
                            returnProductRelationDetaillist = new ArrayList();
                            mapPrd.put("relationSkuPrice", relationSkuPrice);
                            mapPrd.put("relationSkuId", relationSkuId);
                            // 组合不存在商品数量
                            if (productRelationType.equals("0")) {
                                mapPrd.put("productCount", productCount);
                            }
                            mapPrd.put("skuImage", skuImage);
                            mapPrd.put("productName", productName);
                            returnProductRelationDetaillist.add(mapPrd);
                            // 头
                            prMap = new HashMap<String, Object>();
                            prMap.put("relationId", relationId);
                            prMap.put("relationName", relationName);
                            if (productRelationType.equals("0")) {// 套餐
                                prMap.put("relationProductSumPrice", relationProductSumPrice);
                            } else {// 组合头的价格通过skuId从促销返回map中取
                                prMap.put("relationProductSumPrice",
                                        skuIdPricePromotionMap.get(productSkuId).toString());
                            }
                            // 组合不存在套餐价格
                            if (productRelationType.equals("0")) {
                                prMap.put("totalRelationPrice", totalRelationPrice);
                            }
                            prMap.put("productRelationDetails", returnProductRelationDetaillist);
                            returnProductRelationlist.add(prMap);

                        } else {
                            // 明细
                            mapPrd = new HashMap<String, Object>();
                            mapPrd.put("relationSkuPrice", relationSkuPrice);
                            mapPrd.put("relationSkuId", relationSkuId);
                            // 组合不存在商品数量
                            if (productRelationType.equals("0")) {
                                mapPrd.put("productCount", productCount);
                            }
                            mapPrd.put("skuImage", skuImage);
                            mapPrd.put("productName", productName);
                            returnProductRelationDetaillist.add(mapPrd);
                        }
                        relationIdKey =
                                productRelationMap.get("RELATION_ID") == null ? null
                                        : productRelationMap.get("RELATION_ID").toString();
                    }
                    if (productRelationType.equals("0")) {
                        message = "套餐查询成功！";
                    } else {
                        message = "组合查询成功！";
                    }
                }
            }
            rroductRelation.put("productRelations", returnProductRelationlist);
            rroductRelation.put("code", code);
            rroductRelation.put("message", message);
            // printJsonString(rroductRelation);
        } catch (Exception e) {
            logger.error("根据SKUID查询搭售失败", e);
            rroductRelation.put("productRelations", returnProductRelationlist);
            rroductRelation.put("code", "0");
            rroductRelation.put("message", "查询搭售异常");

        }
        printJsonString(rroductRelation);

    }

    /**
     * 根据商品SKUID查询商品评价
     * 
     * @return
     */
    public void getProdAppraiseById() {
        try {
            this.setStartParam();
            Map<String, Object> mapCondition = new HashMap<String, Object>();
            Pagination page = this.getPagination(5, pageNum);
            if (productSkuId != null) {
                mapCondition.put("skuId", productSkuId);
            }
            if (timeSort != null) {
                mapCondition.put("timeSort", timeSort);
            }
            if (pointSort != null) {
                mapCondition.put("pointSort", pointSort);
            }
            mapCondition.put("pageNum", pageNum);
            mapCondition.put("pageNo", pageNo);
            page.setObjCondition(mapCondition);
            // 查询
            page = orderAssessService.findAppraisListByPage(page);
            List<ProdAppraise> appraiseList = page.getRecordList();
            Map<String, Object> map = new HashMap<String, Object>();
            List list = new ArrayList();
            if (null != appraiseList && !appraiseList.isEmpty()) {
                for (ProdAppraise appraise : appraiseList) {
                    Map<String, Object> mapVo = new HashMap<String, Object>();
                    mapVo.put("point", appraise.getPoint());
                    mapVo.put("custName", appraise.getCustName());
                    mapVo.put("custImg",
                            ConfigurationUtil.getString("USER_IMG_PATH") + appraise.getCustId()
                                    + "_mid.jpg");
                    mapVo.put("appraiseContent", appraise.getAppraiseContent());
                    mapVo.put("appraiseDate", appraise.getAppraiseDate());
                    list.add(mapVo);
                }
            }
            map.put("totalrecords", page.getTotalRecords());
            map.put("assessList", list);
            map.put("assessPoint",
                    orderAssessService.queryAssessPoint(Long.parseLong(productSkuId)));
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "根据商品SKUID查询商品评价成功", map);
        } catch (Exception e) {
            logger.error("根据商品SKUID查询商品评价出错", e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "根据商品SKUID查询商品评价出错", null);
        }
        printJsonString(returnResult);
    }

    /**
     * 根据商品SKUID查询商品咨询
     * 
     * @return
     */
    public void getProdConsultId() throws Exception {
        try {
            this.setStartParam();
            Map<String, Object> mapCondition = new HashMap<String, Object>();
            Map<String, Object> map = new HashMap<String, Object>();
            Pagination page = this.getPagination(5, pageNum);
            page.setStartindex((pageNo - 1) * pageNum + 1);
            page.setEndindex((pageNum * pageNo));
            if (productSkuId != null) {
                mapCondition.put("productSkuid", productSkuId);
            }
            // map.put("consultContent", consultContent);
            page.setObjCondition(mapCondition);
            page = consultService.findConsultListByPage(page);
            List<Consult> consultList = page.getRecordList();

            List list = new ArrayList();
            map.put("totalRecords", page.getTotalRecords());
            for (Consult consult : consultList) {
                Map<String, Object> mapVo = new HashMap<String, Object>();
                mapVo.put("consultContent", consult.getConsultContent());
                mapVo.put("consultDate", consult.getConsultDate());
                mapVo.put("reply", consult.getReplyContent());
                mapVo.put("customNickName", consult.getCustNickname());
                list.add(mapVo);
            }
            map.put("consultList", list);
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "根据商品SKUID查询商品咨询成功", map);
        } catch (Exception e) {
            logger.error("根据商品SKUID查询商品咨询失败", e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "根据商品SKUID查询商品咨询出错", null);
        }
        printJsonString(returnResult);
    }

    /**
     * 将一串HTML代码转化为只过滤出图片的数组
     * 
     * @param productIntor
     * @return
     */
    private String[] getProductIntro(String productIntor) {
        String html[] = null;
        if (productIntor != null && !productIntor.isEmpty()) {
            Document doc = Jsoup.parse(productIntor);
            Elements el = doc.select("img");
            if (el.size() > 0) {
                html = new String[el.size()];
                for (int i = 0; i < el.size(); i++) {
                    html[i] = el.get(i).attr("src");
                }
            } else {
                html = new String[0];
            }
        }
        return html;
    }

    public String formatDate(java.util.Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                logger.error("",ex);
            }
        }
        return result;
    }



    /**
     * 
     * 20151009 add begin 添加用户浏览记录 放入到商品详情接口里面
     * 
     * @param sku
     */
    @SuppressWarnings("unused")
    private void executeAddBrowsingHis(ProductSku sku) {
        final String userId = super.getUserid();
        final String skuCode = sku.getProductSkuCode();
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (userId == null || StringUtil.isEmpty(skuCode)) {
                    return;
                }
                BrowsingHis browsingHis = new BrowsingHis();
                browsingHis.setContentCode(skuCode);
                browsingHis.setLoginId(Integer.valueOf(userId));
                // 浏览记录类型1:商品 2:商户
                browsingHis.setBrowsingType(1);
                try {
                    browsingHisService.addBrowsingHis(browsingHis);
                } catch (Exception e) {
                    logger.error("添加浏览记录异常,userId:" + userId + ",skuCode:" + skuCode, e);
                }
            }
        });
    }

    public ReturnResult<Map<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

    public ProductSkuService getproductSkuService() {
        return productSkuService;
    }

    public void setproductSkuService(ProductSkuService productSkuService) {
        this.productSkuService = productSkuService;
    }

}
