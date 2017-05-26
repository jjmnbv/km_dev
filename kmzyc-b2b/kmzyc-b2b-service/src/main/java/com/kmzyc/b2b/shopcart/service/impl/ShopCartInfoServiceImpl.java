package com.kmzyc.b2b.shopcart.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.api.internal.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kmzyc.b2b.dao.OrderItemDao;
import com.kmzyc.b2b.service.OrderMainService;
import com.kmzyc.b2b.service.ShopCartInfoService;
import com.kmzyc.b2b.shopcart.dao.ShopCartInfoDao;
import com.kmzyc.b2b.shopcart.util.ShopCartItemCache;
import com.kmzyc.b2b.shopcart.util.ShopCartProcess;
import com.kmzyc.b2b.shopcart.util.ShopUtil;
import com.kmzyc.b2b.shopcart.util.ShopcartConstants;
import com.kmzyc.b2b.shopcart.vo.CartProduct;
import com.kmzyc.b2b.shopcart.vo.CompositionCartProduct;
import com.kmzyc.b2b.shopcart.vo.Gift;
import com.kmzyc.b2b.shopcart.vo.NormalCartProduct;
import com.kmzyc.b2b.shopcart.vo.SellerShop;
import com.kmzyc.b2b.shopcart.vo.SellerShopList;
import com.kmzyc.b2b.shopcart.vo.ShopCart;
import com.kmzyc.b2b.shopcart.vo.ShopCartItem;
import com.kmzyc.b2b.shopcart.vo.ShopCartProduct;
import com.kmzyc.b2b.shopcart.vo.ShopCartProductReminder;
import com.kmzyc.b2b.shopcart.vo.ShopCartUserInfo;
import com.kmzyc.b2b.shopcart.vo.ShopcartCacheProduct;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.optimization.vo.Promotion;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;
import com.kmzyc.promotion.util.PromotionCacheUtil;
import com.kmzyc.supplier.model.SupplierFare;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
@Service("shopCartInfoService")
public class ShopCartInfoServiceImpl implements ShopCartInfoService {
    private static Logger logger = LoggerFactory.getLogger(ShopCartInfoServiceImpl.class);

    @Resource
    private ShopCartInfoDao shopcartInfoDao;
    @Resource
    private OrderItemDao orderItemDao;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;
    @Resource
    private PromotionCacheUtil promotionCacheUtil;

    @Override
    public ShopCart generateSettlement(ShopCartUserInfo user) {
        String uid = user.getUid();
        // 从缓存获取用户购物车商品信息
        Map<String, String> allProductsCacheStringMap =
                jedisCluster.hgetAll(ShopcartConstants.SHOPCART_PRODUCT + uid);
        if (StringUtil.isEmpty(allProductsCacheStringMap)) {
            return null;
        }
        String rkey = ShopcartConstants.SHOPCART_ITEM + uid;
        Map<String, String> itemCacheMap = jedisCluster.hgetAll(rkey);
        ShopCartItemCache shopCartItemCache = new ShopCartItemCache(itemCacheMap);
        Set<String> normalProductSkaIdSet = Sets.newHashSet();// 用于批量查询数据库
        Set<String> compositionIdSet = Sets.newHashSet();// 用于批量查询数据库
        Map<String, ShopcartCacheProduct> allProductsCacheMap = Maps.newHashMap();
        for (Map.Entry<String, String> entry : allProductsCacheStringMap.entrySet()) {
            String idKey = entry.getKey();
            String val = entry.getValue();
            ShopcartCacheProduct product = JSONObject.parseObject(val, ShopcartCacheProduct.class);
            allProductsCacheMap.put(idKey, product);
            String id = idKey.substring(2, idKey.length());
            if (idKey.startsWith("c_")) {
                compositionIdSet.add(id);
            } else {
                normalProductSkaIdSet.add(id);
            }
        }
        ShopCart shopcart = buildShopCartByProducts(user, allProductsCacheMap, shopCartItemCache,
                normalProductSkaIdSet, compositionIdSet, null);
        // save
        Map<String, String> map = shopCartItemCache.getSaveCacheMap();

        if (shopCartItemCache.isNeedSave()) {
            jedisCluster.del(rkey);
            if (map != null && !map.isEmpty()) {
                jedisCluster.hmset(rkey, map);
                int seconds = user.isLogin() ? ShopcartConstants.MEMBER_SHOPCART_EXPIRE_TIME
                        : ShopcartConstants.NON_MEMBER_SHOPCART_EXPIRE_TIME;
                jedisCluster.expire(rkey, seconds); // 设置item过期时间
            }
        } else {
            if (map == null || map.isEmpty()) {
                jedisCluster.del(rkey);
            }
        }
        return shopcart;


    }

    @Override
    public ShopCart generateSettlement(ShopCartUserInfo user, ShopcartCacheProduct cacheProduct) {

        Map<String, ShopcartCacheProduct> settlementProductMap = Maps.newHashMap();
        String key = "n_" + cacheProduct.getId();
        settlementProductMap.put(key, cacheProduct);
        Set<String> normalProuctSkuIdSet = Sets.newHashSet();// 用于批量查询数据库
        normalProuctSkuIdSet.add(cacheProduct.getId());
        ShopCartItemCache shopCartItemCache = new ShopCartItemCache(null);
        return buildShopCartByProducts(user, settlementProductMap, shopCartItemCache,
                normalProuctSkuIdSet, null, null);
    }

    @Override
    public ShopCart generateSettlementForPressell(ShopCartUserInfo user,
            ShopcartCacheProduct cacheProduct, String buyType) {

        Map<String, ShopcartCacheProduct> settlementProductMap = Maps.newHashMap();
        String key = "n_" + cacheProduct.getId();
        settlementProductMap.put(key, cacheProduct);
        Set<String> normalProuctSkuIdSet = Sets.newHashSet();// 用于批量查询数据库
        normalProuctSkuIdSet.add(cacheProduct.getId());
        ShopCartItemCache shopCartItemCache = new ShopCartItemCache(null);
        return buildShopCartByProducts(user, settlementProductMap, shopCartItemCache,
                normalProuctSkuIdSet, null, buyType);
    }

    @Override
    public ShopCart generateSettlement(ShopCartUserInfo user, String[] productIdArray) {
        if (productIdArray == null || productIdArray.length == 0) {
            return null;
        }

        Map<String, ShopcartCacheProduct> allProuctsCacheMap = Maps.newHashMap();
        String uid = user.getUid();
        String shopCartKey = ShopcartConstants.SHOPCART_PRODUCT + uid;


        List<String> productInfoList = jedisCluster.hmget(shopCartKey, productIdArray);
        int i = 0;
        Set<String> normalProuctSkuIdSet = Sets.newHashSet();
        Set<String> compostionIdSet = Sets.newHashSet();
        for (String id : productIdArray) {
            if (StringUtil.isEmpty(id)) {
                continue;
            }
            String val = productInfoList.get(i);
            ShopcartCacheProduct product = JSONObject.parseObject(val, ShopcartCacheProduct.class);
            if (product == null) {
                return null;
                // continue;
            }
            allProuctsCacheMap.put(id, product);
            if (id.startsWith("c_")) {
                compostionIdSet.add(id.substring(2));
            } else if (id.startsWith("n_")) {
                normalProuctSkuIdSet.add(id.substring(2));
            }
            i++;
        }
        if (compostionIdSet.isEmpty() && normalProuctSkuIdSet.isEmpty()) {
            return null;
        }
        Map<String, String> itemCacheMap =
                jedisCluster.hgetAll(ShopcartConstants.SHOPCART_ITEM + uid);
        ShopCartItemCache shopCartItemCache = new ShopCartItemCache(itemCacheMap);

        if (StringUtil.isEmpty(allProuctsCacheMap)) {
            return null;
        }
        return buildShopCartByProducts(user, allProuctsCacheMap, shopCartItemCache,
                normalProuctSkuIdSet, compostionIdSet, null);
    }

    private ShopCart buildShopCartByProducts(ShopCartUserInfo user,
            Map<String, ShopcartCacheProduct> allProuctsCacheMap,
            ShopCartItemCache shopCartItemCache, Set<String> normalProuctSkuIdSet,
            Set<String> compostionIdSet, String buyType) {
        // ShopCartTask tesk = new ShopCartTask(promotionCacheUtil, normalProuctSkuIdSet);
        // tesk.fork();
        // 批量的活动信息
        Map<String, ProductPromotion> promtoionmap = null;
        List<NormalCartProduct> normalProductList; // 数据库中普通产品信息
        List<CompositionCartProduct> compositionList;// 数据库中套装信息
        // 查数据库数据库中产品信息
        normalProductList = getProductList(
                normalProuctSkuIdSet.toArray(new String[normalProuctSkuIdSet.size()]));
        compositionList = getCompositionList(compostionIdSet);
        if (normalProductList.isEmpty() && compositionList.isEmpty()) {
            return null;
        }
        // promtoionmap = tesk.join();
        // 查询活动 生成订单时，预售活动不需要查询促销信息
        if (!Constants.PRESELL_BUY.equals(buyType)) {
            promtoionmap = promotionCacheUtil.getProductPromtoionInfoCahce(normalProuctSkuIdSet);
        }

        ShopCartProcess process = new ShopCartProcess(promtoionmap, shopCartItemCache);
        Map<String, ShopCartProductReminder> productErrorReminder = Maps.newHashMap();
        Map<String, CartProduct> cartProductMap = Maps.newHashMap();
        for (NormalCartProduct cartProduct : normalProductList) {
            Long skuId = cartProduct.getProductSkuId();
            String id = "n_" + skuId;
            cartProductMap.put(id, cartProduct);
            ShopcartCacheProduct json = allProuctsCacheMap.get(id); //
            if (!Constants.PRESELL_BUY.equals(buyType)) {
                ShopCartProductReminder reminder = commonVerificationProduct(cartProduct);
                if (reminder == null) {
                    reminder = verificationProduct(cartProduct, json,
                            promtoionmap == null ? null : promtoionmap.get(skuId.toString()), user);
                }
                if (reminder != null) {
                    json.setCheck(reminder.isNormal());
                    // promtoionmap.remove(skuId);
                    json.put("reminder", reminder);
                    productErrorReminder.put(id, reminder);
                }
            }
            ShopCartProduct product = new ShopCartProduct(json);
            process.processNormalProduct(cartProduct, product);
        }

        for (CompositionCartProduct cartProduct : compositionList) {
            Long cmId = cartProduct.getId();
            String id = "c_" + cmId;
            cartProductMap.put(id, cartProduct);
            ShopcartCacheProduct json = allProuctsCacheMap.get(id); //
            ShopCartProductReminder reminder = commonVerificationProduct(cartProduct);
            if (reminder == null) {
                reminder = verificationProduct(cartProduct, json,
                        promtoionmap == null ? null : promtoionmap.get(cmId.toString()), user);
            }
            if (reminder != null) {
                json.setCheck(reminder.isNormal());
                // mkw add 20150921 缺陷：1055 购物车：购物车中套餐下架无提示；
                json.put("reminder", reminder);
                productErrorReminder.put(id, reminder);
                // end
            }
            ShopCartProduct product = new ShopCartProduct(json);
            process.processCompositionCartProduct(cartProduct, product);
        }

        try {
            ShopCart shopcart = calculateTotal(process);
            shopcart.setCartProductMap(cartProductMap);
            if (!productErrorReminder.isEmpty()) {
                shopcart.setProductErrorReminder(productErrorReminder);
            }
            SellerShopList list = shopcart.getSellerShopList();
            list.getSellerShopSet().addAll(list.getSellerShopMap().values());
            return shopcart;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 校验是否可以购买
     */
    private ShopCartProductReminder commonVerificationProduct(CartProduct normalCartProduct) {
        try {
            if (normalCartProduct instanceof CompositionCartProduct) {
                if (normalCartProduct.getIsOutOfStock()) {
                    return buildShopCartProductReminder(Constants.DROPS, false, "该商品已下架,无法购买",
                            normalCartProduct.getProductSkuId());
                }
            } else if (3 != normalCartProduct.getProductStatus()
                    || 1 != normalCartProduct.getSkuStatus()) {
                return buildShopCartProductReminder(Constants.DROPS, false, "该商品已下架,无法购买",
                        normalCartProduct.getProductSkuId());
            }
            if (normalCartProduct.getStockCount() <= 0) {
                return buildShopCartProductReminder(Constants.NON_STOCK, false, "该商品无库存,无法购买",
                        normalCartProduct.getProductSkuId());
            }
            if (normalCartProduct.getProductType() != null
                    && 3 == normalCartProduct.getProductType()) {
                return buildShopCartProductReminder(Constants.PRESCRIPTION, false,
                        "该商品为处方药，请到我的药方进行购买", normalCartProduct.getProductSkuId());
            }
            if (normalCartProduct.getSupplierType() == null) {
                return buildShopCartProductReminder(Constants.DATAEXCEPTION, false, "该商品信息异常，无法购买",
                        normalCartProduct.getProductSkuId());
            }
            // 时代商品
            if (normalCartProduct.getSupplierType() == Constants.SUPPLIERTYPE) {

                return buildShopCartProductReminder(Constants.UNENBUY, false, "该商品为康美中药城产品,无法购买",
                        normalCartProduct.getProductSkuId());
                // end
            }
        } catch (Exception e) {
            logger.error("校验商品信息异常", e);
            return buildShopCartProductReminder(Constants.DATAEXCEPTION, false, "该商品信息异常，无法购买",
                    normalCartProduct.getProductSkuId());
        }
        return null;
    }

    /**
     * 购买量校验
     */
    private ShopCartProductReminder verificationProduct(CartProduct normalCartProduct,
            JSONObject jsonProduct, ProductPromotion productPromo, ShopCartUserInfo user) {
        int maxCount = normalCartProduct.getStockCount();
        int maxCode = Constants.UNDERSTOCK;
        // int minCode = 1;
        int amount = jsonProduct.getIntValue("amount");
        int maxCountApp = normalCartProduct.getStockCount();

        jsonProduct.put("maxCode", maxCode);
        jsonProduct.put("max", maxCount);
        jsonProduct.put("maxApp", maxCountApp);

        if (normalCartProduct.getStockCount() < amount) {
            return buildShopCartProductReminder(Constants.UNDERSTOCK, true, "该商品库存不足,请修改订购数量",
                    normalCartProduct.getProductSkuId());
        } else if (normalCartProduct instanceof CompositionCartProduct) {

        } else if (productPromo != null && productPromo.getAvailableQuantity() != null) {
            if (productPromo.getAvailableQuantity() <= 0
                    || productPromo.getAvailableQuantity() < amount) {
                return buildShopCartProductReminder(Constants.SELLSTOCK,
                        productPromo.getAvailableQuantity() != 0,
                        "该商品活动库存不足" + (productPromo.getAvailableQuantity() != 0 ? "" : ",无法购买"),
                        normalCartProduct.getProductSkuId());
            }

            if (maxCount > productPromo.getAvailableQuantity()) {
                maxCount = productPromo.getAvailableQuantity();
                maxCountApp = productPromo.getAvailableQuantity();

                jsonProduct.put("maxCode", Constants.SELLSTOCK);
                jsonProduct.put("max", maxCount);
                jsonProduct.put("maxApp", maxCountApp);
            }
        }
        if (productPromo != null) {
            if (!user.isLogin()) {
                return buildShopCartProductReminder(Constants.UNENJOYACTIVITY, false,
                        "登录用户才能购买限购商品", normalCartProduct.getProductSkuId());
            }
            if (productPromo.getMax() != null) {
                if (maxCount > productPromo.getMax()) {
                    maxCount = productPromo.getMax();
                    maxCountApp = productPromo.getMax();

                    jsonProduct.put("maxCode", Constants.OVERLIMIT);
                    jsonProduct.put("max", maxCount);
                    jsonProduct.put("maxApp", maxCountApp);
                }

                Promotion pricePromotion = productPromo.getPricePromotionForChannel();
                if (pricePromotion == null) {
                    return null;
                }

                Integer userPurchase = queryUserPurchase(Long.valueOf(user.getUid()),
                        pricePromotion.getId() + "", normalCartProduct.getProductSkuId());
                jsonProduct.put("userPurchase", userPurchase);

                if (maxCount > productPromo.getMax() - userPurchase) {
                    maxCount = productPromo.getMax() - userPurchase;

                    jsonProduct.put("maxCode", Constants.OVERLIMIT);
                    jsonProduct.put("max", maxCount);
                }
                if (maxCount < amount) {
                    if (productPromo.getMax() > userPurchase) {
                        return buildShopCartProductReminder(
                                Constants.OVERLIMIT, maxCount > 0, "该商品活动期间每个会员限购"
                                        + productPromo.getMax() + "件，您已经购买了" + userPurchase + "件",
                                normalCartProduct.getProductSkuId());
                    } else {
                        return buildShopCartProductReminder(Constants.OVERLIMIT, maxCount > 0,
                                "该商品活动期间每个会员限购" + productPromo.getMax() + "件，无法购买",
                                normalCartProduct.getProductSkuId());
                    }
                }
            }
            if (productPromo.getMin() != null) {

                jsonProduct.put("minCode", Constants.BELOWLIMIT);
                jsonProduct.put("min", productPromo.getMin());

                if (productPromo.getMin() > amount) {
                    return buildShopCartProductReminder(Constants.BELOWLIMIT, true,
                            "该商品活动期间" + productPromo.getMin() + "件起售",
                            normalCartProduct.getProductSkuId());
                }
            }
        }

        return null;
    }

    /***
     *
     * @param code 错误编号
     * @param normal 是否可以正常购买
     * @param message 错误提示
     * @return
     */
    private ShopCartProductReminder buildShopCartProductReminder(int code, boolean normal,
            String message, Long productSkuId) {
        ShopCartProductReminder reminder = new ShopCartProductReminder();
        reminder.setCode(code);
        reminder.setMessage(message);
        reminder.setNormal(normal);
        logger.info(reminder.getMessage() + "->productSkuId:" + productSkuId);
        return reminder;
    }


    private ShopCart calculateTotal(ShopCartProcess shopCartProcess) throws Exception {
        ShopCart shopcart = new ShopCart();
        ShopCartItemCache shopCartItemCache = shopCartProcess.getShopCartItemCache();
        shopcart.setSellerShopList(shopCartProcess.getSellerShopList());
        shopcart.setShopCartItemMap(shopCartItemCache.getShopCartItemMap());
        Map<String, ShopCartProduct> productsPageMap = shopCartProcess.getProuctsPageMap();
        shopcart.setProductMap(productsPageMap);
        Map<Long, SellerShop> sellerShopMap =
                shopCartProcess.getSellerShopList().getSellerShopMap();
        // 批量获取商家运费
        Map<Long, SupplierFare> supplierFareMap = getSupplierFareMap(sellerShopMap.keySet());
        shopcart.setSupplierFareMap(supplierFareMap);
        // boolean isFirstQuryEraInfo = true;// 第一次获取时代会员信息 防止重复查询
        Set<String> giftIds = Sets.newHashSet();
        for (SellerShop shop : sellerShopMap.values()) {// 遍历商家
            Long shopId = shop.getSellerId();
            SellerShop sellerShop = sellerShopMap.get(shopId);
            // EraInfo eraInfo = null;
            /*
             * if (sellerShop.getSupplierType() == 4 && isFirstQuryEraInfo) { // 获取时代会员信息 eraInfo =
             * eraInfoService.queryEraInfoByLoginId(Long.parseLong(uid)); isFirstQuryEraInfo =
             * false; if (eraInfo != null) { IMemberInfo imi = loginService.findWebservice(); String
             * result = imi.getPromotionInfo(); sdPromotionJson = JSONObject.parseObject(result);
             * sellerShop.setInfo(sdPromotionJson.getString("Info"));
             * sellerShop.setUrl(sdPromotionJson.getString("Url"));
             * sellerShop.setEraGradeName(eraInfo.getEraGradeName()); sellerShop.setEraGradeRate(
             * eraInfo.getEraGradeRate().multiply(new BigDecimal(100))); } }
             */
            Map<String, ShopCartItem> itemMap = shop.getShopCartItemMap();


            // 是否免商家运费 true:是 false:否
            boolean freeFlag = true;
            for (ShopCartItem shopCartItem : itemMap.values()) {// 遍历商家下面的item
                String itemKey = shopCartItem.getId();
                if (itemKey.endsWith("_0")) {
                    shopCartItem.addCheckTotalMoney(shopCartItem.getUncalculateMoney());
                } else {
                    Promotion promotion = shopCartItem.getPromtionInfo();
                    List<JSONObject> ruleDataList = promotion.getRuleDataAndPrizeEntityInfo();
                    if (null != ruleDataList && ruleDataList.size() > 0) {
                        shopCartItem =
                                importRuleGift(shopCartItem, promotion, ruleDataList, giftIds);
                    }
                    if (shopCartItem.getMeet()) {
                        shopcart.addMeetOrderPromotion(shopCartItem);
                    }
                }
                // 商家金额合计
                shopCartItemCache.add(shopCartItem, true);
                // sellerShop.addTotalProductPvalue(shopCartItem.getProductPvalue());
                sellerShop.addAllMoney(shopCartItem.getAllMoney());
                sellerShop.addUncalculateMoney(shopCartItem.getUncalculateMoney());
                sellerShop.addItionalMoney(shopCartItem.getAdditionalMoney());
                sellerShop.addReductionMoney(shopCartItem.getReductionMoney());
                sellerShop.addCheckTotalMoney(shopCartItem.getCheckTotalMoney());
                sellerShop.addCheckedProductCount(shopCartItem.getCheckedProductCount());
                sellerShop.addFreight(shopCartItem.getFreight());
                // sellerShop.addCheckProductId(shopCartItem.getc);
                // sellerShop.addWeight(shopCartItem.getWeight());

                // mkw add 20151008 如果商品免邮且只购买了一件商品，则不收任何运费（商品运费+商家运费）
                // mkw modif 20151020 商家所有商品免邮，则不收任何运费（商品运费+商家运费）

                for (String str : shopCartItem.getCarProducts()) {
                    // mkw 20151123 增加附赠商品库存校验
                    ShopCartProduct product = productsPageMap.get(str);
                    JSONObject json = product.getMap();

                    ProductPromotion productPromotion =
                            json.getObject("productPromotion", ProductPromotion.class);

                    if (null != productPromotion) {

                        if (null != productPromotion.getAffixPromoiton() && PromotionTypeEnums.GANT
                                .getValue() == productPromotion.getAffixPromoiton().getType()) {
                            List<PromotionProductData> data =
                                    productPromotion.getAffixProductList();
                            if (null != data) {
                                for (PromotionProductData promotionProductData : data) {
                                    giftIds.add(
                                            String.valueOf(promotionProductData.getProductSkuId()));
                                }
                            }
                        }
                    }
                    // end

                    if (freeFlag) {
                        // mkw modif 20151109 当是否免邮标志为空，则默认为不免邮
                        if (ShopcartConstants.S_FREE_STATUS_0.equals(json.getString("freeStatus"))
                                || StringUtils.isEmpty(json.getString("freeStatus"))) {
                            freeFlag = false;
                        }
                    }

                }
                // end
            }

            // 商家运费
            SupplierFare fare = supplierFareMap.get(shopId);
            // 购物车金额合计
            BigDecimal checkTotalMoney = sellerShop.getCheckTotalMoney();
            if (fare != null) {
                if (!freeFlag && fare.getFreePrice().compareTo(checkTotalMoney) > 0) {
                    sellerShop.addFreight(fare.getFirstHeavyFreight());
                }
                // mkw 20151023 增加商家免运费金额
                sellerShop.setFreePrice(fare.getFreePrice());
                // end
            }

            shopcart.addItionalMoney(sellerShop.getAdditionalMoney());
            shopcart.addReductionMoney(sellerShop.getReductionMoney());
            shopcart.addAllMoney(sellerShop.getAllMoney());
            shopcart.addFreight(sellerShop.getFreight());
            shopcart.addCheckTotalMoney(checkTotalMoney);
            shopcart.addUncalculateMoney(sellerShop.getUncalculateMoney());
            shopcart.addCheckedProductCount(sellerShop.getCheckedProductCount());
            // shopcart.addWeight(sellerShop.getWeight());
            shopcart.addTotalProductPvalue(sellerShop.getProductPvalue());
        }
        // 赠品加价购附赠商品库存
        List<String> giftIdList = new ArrayList<>();
        giftIdList.addAll(giftIds);
        if (!giftIdList.isEmpty()) {
            Map<String, Integer> giftStockMap = this.queryStockBatch(giftIdList);
            shopcart.setGiftStockMap(giftStockMap);
        }
        return shopcart;
    }

    private ShopCartItem importRuleGift(ShopCartItem shopCartItem, Promotion promotion,
            List<JSONObject> ruleDataList, Set<String> giftIds) {

        boolean isMeet = false;
        BigDecimal oldMeetData = shopCartItem.getMeetData();
        BigDecimal meetData = null;
        int type = promotion.getType();
        shopCartItem.setRulePresents(null);
        shopCartItem.setRuleGifts(null);
        // 默认赠品
        String defaultPresents = null;
        for (JSONObject currentRule : ruleDataList) {
            int meetDataType = currentRule.getIntValue("MEET_DATA_TYPE");
            BigDecimal thisMeetData = currentRule.getBigDecimal("MEET_DATA");
            BigDecimal checkData;
            if (meetDataType == 1) {// 金额
                checkData = shopCartItem.getUncalculateMoney();
            } else { // 数量
                checkData = BigDecimal.valueOf(shopCartItem.getCheckedProductCount());
            }
            if (thisMeetData.compareTo(checkData) > 0) {
                break;
            }
            meetData = thisMeetData;
            isMeet = true;
            int count = currentRule.getInteger("NUM1");
            BigDecimal prizeData = currentRule.getBigDecimal("PRIZE_DATA");
            shopCartItem.setGiftCount(count);
            Gift gift = buildGift(currentRule);
            if (type == PromotionTypeEnums.COUPON.getValue()) {// 送券
                shopCartItem.setCoupon(gift);
            } else if (type == PromotionTypeEnums.REDUCTION.getValue()) {// 满额减免
                shopCartItem.setReductionMoney(prizeData);
            } else if (type == PromotionTypeEnums.INCREASE.getValue()) {// 加价购
                shopCartItem.addRuleGift(gift);
                giftIds.add(gift.getId());
            } else if (type == PromotionTypeEnums.GIFT.getValue()) {// 赠品
                defaultPresents = meetData.intValue() + "";
                shopCartItem.addRulePresents(defaultPresents, gift);
                giftIds.add(gift.getId());
            }
        }
        shopCartItem.setMeetData(meetData);
        if (isMeet) {
            // 订单级满足的梯度有变化，手选的赠品和加价购需要重新选择
            boolean meetDataIsChange = oldMeetData == null || oldMeetData.compareTo(meetData) != 0;
            if (meetDataIsChange) {
                // shopCartItem.setMeetData(newMeetData);
                shopCartItem.setGifts(null);
                shopCartItem.setAdditionalMoney(BigDecimal.ZERO);
                shopCartItem.setCountChoosed(0);
                shopCartItem.setPresents(null);
            }
            // 修改默认赠品
            String presentskey = shopCartItem.getPresents();
            if (presentskey != null && shopCartItem.getRulePresents().containsKey(presentskey)) {
                shopCartItem.setDefaultPresents(presentskey);
            } else {
                shopCartItem.setDefaultPresents(defaultPresents);
            }

            // mkw add 20151015 统计已选赠品数量
            if (!StringUtil.isEmpty(shopCartItem.getDefaultPresents())) {
                List<Gift> giftList =
                        shopCartItem.getRulePresents().get(shopCartItem.getDefaultPresents());
                int countPresents = 0;
                for (Gift aGiftList : giftList) {
                    countPresents += aGiftList.getAmount();
                }
                shopCartItem.setCountPresents(countPresents);
            }
            // end
            shopCartItem.setMeet(true);
        } else {
            shopCartItem.setMeet(false);
            shopCartItem.setRuleGifts(null);
            shopCartItem.setRulePresents(null);
            shopCartItem.setGifts(null);
            shopCartItem.setPresents(null);
            shopCartItem.setDefaultPresents(null);
            shopCartItem.setMeetData(null);
            shopCartItem.setAdditionalMoney(BigDecimal.ZERO);
            shopCartItem.setReductionMoney(BigDecimal.ZERO);
            shopCartItem.setCountChoosed(0);
            shopCartItem.setCountPresents(0);
        }
        shopCartItem.setCheckTotalMoney(shopCartItem.getUncalculateMoney()
                .add(shopCartItem.getAdditionalMoney()).subtract(shopCartItem.getReductionMoney()));
        return shopCartItem;
    }

    // {"ENTITY_ID":10600,"IMG_PATH":"/upload/product/11080/10600/20150506110137610463_10600.jpg",
    // "MEET_DATA":200,"MEET_DATA_TYPE":1,"NUM1":1,
    // "PRIZE_DATA":"20","PROCUCT_NAME":"玛咖一号","PROMOTION_ID":25980,
    // "PROMOTION_RULE_DATA_ID":14927}
    private Gift buildGift(JSONObject currentRule) {
        String dataId = currentRule.getString("PROMOTION_RULE_DATA_ID");
        String id = currentRule.getString("ENTITY_ID");
        String name = currentRule.getString("PROCUCT_NAME");
        if (StringUtil.isEmpty(name)) {
            // "COUPON_MONEY":10,"COUPON_NAME":"测试专用"
            name = currentRule.getString("COUPON_NAME");
        } else if (currentRule.containsKey("SKUATTR")) {
            name = name + " " + currentRule.getString("SKUATTR");
        }
        String img = currentRule.getString("IMG_PATH");
        BigDecimal price;
        if (currentRule.containsKey("COUPON_MONEY")) {
            price = currentRule.getBigDecimal("COUPON_MONEY");
        } else {
            price = currentRule.getBigDecimal("PRIZE_DATA");
        }

        Integer maxAmount = currentRule.getInteger("NUM1");
        Integer meetData = currentRule.getInteger("MEET_DATA");
        Gift gift = new Gift();
        gift.setDataId(dataId);
        gift.setId(id);
        gift.setName(name);
        gift.setImg(img);
        gift.setPrice(price);
        gift.setAmount(maxAmount);
        gift.setMaxAmount(maxAmount);
        gift.setMeetData(meetData);
        return gift;
    }


    // public boolean saveSpreadRules(String userId, net.sf.json.JSONObject json) throws
    // ServiceException {
    // return false;
    //
    // }

    /**
     * 读取推广规则cookie
     */

    // public JSONObject readSpreadRules(String userId) throws ServiceException {
    // return null;
    // }
    private List<CompositionCartProduct> getCompositionList(Set<String> compositionIds) {
        if (StringUtil.isEmpty(compositionIds)) {
            return new ArrayList<>();
        }
        // Map<Long, CompositionCartProduct> compositionMap = new HashMap<Long,
        // CompositionCartProduct>();
        // Map<String, Object> params = new HashMap<String, Object>();
        // params.put("suitIds", compositionIds.toArray(new String[compositionIds.size()])); //
        // 套餐IDS
        List<CompositionCartProduct> rsMapList = new ArrayList<>();
        try {
            List<Map<String, String>> rsList = shopcartInfoDao
                    .getComsiotionList(compositionIds.toArray(new String[compositionIds.size()]));
            if (null != rsList && !rsList.isEmpty()) {
                List<NormalCartProduct> productList = null;
                CompositionCartProduct ccp = null;
                Long suitId = null;
                BigDecimal suitPrice = BigDecimal.ZERO, splitPrice = BigDecimal.ZERO,
                        costIncomeMoney = BigDecimal.ZERO;// 套餐价格、分开价格,或以金额
                String suitName = null;// 套餐名称
                Integer status = null, minStock = null; // 套餐状态，最小库存、购买数量
                for (Map<String, String> rs : rsList) {
                    Long tempId = Long.parseLong(rs.get("SUITID"));
                    if (null == suitId || 0 != tempId.compareTo(suitId)) {
                        // 初始化新套餐变化
                        suitName = null;
                        suitPrice = BigDecimal.ZERO;
                        splitPrice = BigDecimal.ZERO;
                        costIncomeMoney = new BigDecimal(rs.get("COSTINCOMERATIO"));
                        status = null;
                        minStock = null;
                        suitId = tempId;
                        productList = new ArrayList<>();
                        ccp = new CompositionCartProduct();
                        ccp.setId(suitId);
                        ccp.setIsOutOfStock(false);
                        rsMapList.add(ccp);
                    }

                    Integer productCount = Integer.parseInt(rs.get("PRODUCTCOUNT"));// 数量
                    if (ccp.getAmount() == 0) {
                        ccp.setAmount(productCount);
                    } else {
                        ccp.setAmount(ccp.getAmount() + productCount);
                    }
                    Integer stockCount = Integer.parseInt(rs.get("STOCKCOUNT"));// 库存数量
                    if (null == minStock || minStock > (stockCount / productCount)) {
                        minStock = stockCount / productCount;
                        ccp.setStockCount(minStock);
                    }
                    if (null == status) {
                        status = Integer.parseInt(rs.get("STATUS"));// 套餐状态
                        if (3 != status) {
                            ccp.setIsOutOfStock(true);
                        }
                    }
                    if (null == suitName) {
                        suitName = rs.get("SUITNAME");// 套餐名称
                        ccp.setName(suitName);
                    }
                    if (0 == BigDecimal.ZERO.compareTo(suitPrice)) {
                        suitPrice = new BigDecimal(rs.get("SUITPRICE")).setScale(2);// 套餐价格
                        ccp.setPrice(suitPrice);
                        ccp.setFinalPrice(suitPrice);
                    }
                    BigDecimal productPrice = new BigDecimal(rs.get("PRODUCTPRICE"));// 产品价格
                    String[] shopInfo = rs.get("SHOPINFO").split(",");// 商家信息格式类型,名称
                    if (null == shopInfo || 2 != shopInfo.length || stockCount < 1) {
                        ccp.setStockCount(0);// 产品无库存
                        logger.info("套餐" + suitId + "产品信息错误或无库存！");
                    }
                    String imgInfo = rs.get("IMGINFO");// 图片信息
                    Integer supplierType = 1;
                    if (!StringUtil.isEmpty(shopInfo[0])) {
                        supplierType = Integer.parseInt(shopInfo[0]);
                    }
                    String shopName = null;
                    if (!StringUtil.isEmpty(shopInfo[1])) {
                        shopName = shopInfo[1];
                    }
                    NormalCartProduct bp = new NormalCartProduct();
                    bp.setProductDesc(rs.get("PRODUCTDESC"));
                    bp.setSkuAttrValue(rs.get("CATEGORYATTRVALUE"));
                    bp.setBrandName(rs.get("BRANDNAME"));// 品牌
                    bp.setBatchNo(rs.get("BATCHNO"));// 批次号
                    bp.setCredits(Integer.parseInt(rs.get("CREDITS")));// 批次号
                    bp.setSellerId(Long.parseLong(rs.get("SELLERID")));// 商家ID
                    // bp.setSupplierCode(Long.valueOf(rs.get("SELLERID")));// 商家ID
                    bp.setProductSkuId(Long.parseLong(rs.get("SKUID")));// skuId
                    bp.setProductID(Long.parseLong(rs.get("PRODUCTID")));// productId
                    bp.setBrandId(Long.parseLong(rs.get("BRANDID")));// brandId
                    bp.setCategoryId(Long.parseLong(rs.get("CATEGORYID")));// categoryId
                    bp.setChannel("B2B");// channel
                    bp.setTitle(rs.get("PRODUCTTITLE"));// 标题
                    bp.setName(rs.get("PRODUCTNAME"));// 名称
                    bp.setProductNo(rs.get("PRODUCTNO"));// productNo
                    bp.setProductSkuCode(rs.get("PRODUCTSKUCODE"));// PRODUCTSKUCODE
                    bp.setUnitWeight(new BigDecimal(rs.get("UNITWEIGHT")));// UNITWEIGHT
                    if (null != imgInfo && imgInfo.indexOf(',') > 0) {
                        String[] imgs = imgInfo.split(",");
                        bp.setImagePath(imgs[0]);
                        bp.setImagePath7(imgs[1]);
                    }
                    BigDecimal count = new BigDecimal(productCount);
                    splitPrice = splitPrice.add(productPrice.multiply(count));
                    bp.setStockCount(stockCount);
                    bp.setFinalPrice(productPrice);
                    bp.setPrice(productPrice);
                    bp.setAmount(productCount);
                    bp.setSupplierType(supplierType);
                    bp.setSupplierName(shopName);

                    // mkw 20151020 add
                    bp.setShopName(shopName);
                    // end

                    bp.setSkuBarCode(rs.get("SKUBARCODE"));

                    bp.setPvalue(new BigDecimal(
                            StringUtil.isEmpty(rs.get("PVALUE")) ? "0" : rs.get("PVALUE")));

                    bp.setCostIncomeMoney(productPrice.divide(suitPrice, BigDecimal.ROUND_UP)
                            .multiply(costIncomeMoney));
                    bp.setCostIncomeRatio(new BigDecimal(rs.get("COSTINCOMERATIO")));
                    bp.setErpProCode(rs.get("ERPPROCODE"));
                    bp.setErpSysCode(rs.get("ERPSYSCODE"));
                    bp.setJxcCode(rs.get("JXCCODE"));
                    if (ccp.getUnitWeight() == null) {
                        ccp.setUnitWeight(BigDecimal.ZERO);
                    }
                    ccp.setUnitWeight(ccp.getUnitWeight().add(bp.getUnitWeight().multiply(count)));
                    productList.add(bp);
                    ccp.setSellerId(bp.getSellerId());
                    ccp.setShopName(bp.getShopName());
                    ccp.setSupplierType(bp.getSupplierType());


                    ccp.setPvalue((ccp.getPvalue() == null ? BigDecimal.ZERO : ccp.getPvalue())
                            .add(bp.getPvalue()));


                    ccp.setCostIncomeRatio(new BigDecimal(rs.get("COSTINCOMERATIO")));// 获利百分比
                    ccp.setCostIncomeMoney(costIncomeMoney);// 获利金额

                    // // mkw add 20150921 bug:1068 购物车：购物车中加入2个套餐后，购物商品均不显示；
                    ccp.setProductsPriceSum(splitPrice);

                    ccp.setProductList(productList);
                    // end
                }

            }
        } catch (Exception e) {
            logger.error("批量查询套餐异常", e);
        }

        return rsMapList;
    }

    private List<NormalCartProduct> getProductList(String[] productIds) {
        if (StringUtil.isEmpty(productIds)) {
            return new ArrayList<>();
        }
        try {
            return shopcartInfoDao.getProductList(productIds);
        } catch (Exception ex) {
            logger.error("批量商品异常", ex);
        }
        return new ArrayList<>();
    }

    /**
     * 批量添加普通商品，全部添加成功返回null，否则返回错误的商品和对应的错误编码
     */
    @Override
    public boolean addProduct(ShopCartUserInfo user, String activityChannel, int amount,
            String... skuIds) {
        String uid = user.getUid();
        if (StringUtil.isEmpty(uid)) {
            throw new ServiceException(Constants.DATAEXCEPTION, "uid为空");
        }
        if (skuIds.length > 30) {
            throw new ServiceException(Constants.DATAEXCEPTION, "最多只能添加30件商品");
        }
        try {
            String key = ShopcartConstants.SHOPCART_PRODUCT + uid;
            if (jedisCluster.hlen(key) > 60) {
                throw new ServiceException(Constants.DATAEXCEPTION, "最多只能添加60件商品");
            }
            List<NormalCartProduct> cpList = shopcartInfoDao.getProductList(skuIds);
            if (cpList.size() != skuIds.length) {
                logger.info("存在非法商品->skuids:" + Arrays.toString(skuIds));
                throw new ServiceException(Constants.NON_PRODUCT, "存在非法商品");
            }
            Map<String, JSONObject> mapProduct = new HashMap<>();
            Map<String, String> redisCacheMap = new HashMap<>();
            Set<String> skuIdSet = new HashSet<>();
            // List<String> strlist = new ArrayList<String>();
            // for (String string : skuIds) {
            // strlist.add(ShopcartConstants.B_SC_SKUHEAD_N_ + string);
            // }
            // List<String> valList = jedis.hmget(key, strlist.toArray(new String[strlist.size()]));

            // int i = 0;
            Date now = new Date();
            for (String skuId : skuIds) {// 批量设置叠加redis中的商品数量
                ShopcartCacheProduct cacheProduct;
                String field = ShopcartConstants.B_SC_SKUHEAD_N_ + skuId;
                // String val = valList.get(i);
                String val = jedisCluster.hget(key, field);
                if (StringUtil.isEmpty(val)) {// 新增
                    cacheProduct =
                            new ShopcartCacheProduct(skuId, amount, now, false, activityChannel);
                    // mapProduct.put(field, cacheProduct);
                } else {
                    cacheProduct = JSONObject.parseObject(val, ShopcartCacheProduct.class);
                    cacheProduct.setAmount(cacheProduct.getAmount() + amount);
                }

                cacheProduct.setActivityChannel(activityChannel);

                skuIdSet.add(skuId);
                // 加入需要保存的对象map
                mapProduct.put(field, cacheProduct);
            }
            // 获取活动信息
            Map<String, ProductPromotion> productPromotionMap =
                    promotionCacheUtil.getProductPromtoionInfoCahce(skuIdSet);
            // 批量校验商品合法
            for (CartProduct product : cpList) {
                ShopCartProductReminder reminder = commonVerificationProduct(product);
                if (reminder != null) {
                    logger.info("存在非法商品->productSkuId:" + product.getProductSkuId());
                    throw new ServiceException(reminder.getCode(),
                            StringUtil.isEmpty(reminder.getMessage()) ? "存在非法商品"
                                    : reminder.getMessage());
                }
                Long skuId = product.getProductSkuId();
                String field = ShopcartConstants.B_SC_SKUHEAD_N_ + skuId;
                JSONObject productJson = mapProduct.get(field);
                ProductPromotion productPromotion = productPromotionMap.get(skuId.toString());
                reminder = verificationProduct(product, productJson, productPromotion, user);
                // jsonProduct.getIntValue("amount");

                if (reminder != null) {
                    if (reminder.getCode() == Constants.UNENJOYACTIVITY) {
                        throw new ServiceException(reminder.getCode(), reminder.getMessage());
                    }
                    if (productJson.getIntValue("max") <= 0) {
                        throw new ServiceException(reminder.getCode(), reminder.getMessage());
                    }

                    if (productJson.getInteger("amount") > productJson.getInteger("max")) {
                        productJson.put("amount", productJson.get("max"));
                    }
                    /*
                     * throw new ServiceException(reminder.getCode(),
                     * StringUtil.isEmpty(reminder.getMessage()) ? "存在无法购买的商品" :
                     * reminder.getMessage());
                     */
                }
                // 验证通过加入需要保存的字符串map
                productJson.remove("minCode");
                productJson.remove("min");
                productJson.remove("maxCode");
                productJson.remove("max");
                redisCacheMap.put(field, productJson.toJSONString());
            }
            String result = jedisCluster.hmset(key, redisCacheMap);
            if (ShopcartConstants.B_SC_OK.equals(result)) {
                this.updateShopCartExpire(key, user.isLogin(), jedisCluster);
                return true;
            } else {
                return false;
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error("系统异常", e);
            throw new ServiceException(Constants.DATAEXCEPTION, "系统异常");
        }
    }

    /**
     * 添加套餐
     */
    @Override
    public boolean addComposition(ShopCartUserInfo user, int amount, String suitId) {

        Set<String> suitIdSet = new HashSet<>();
        suitIdSet.add(suitId);
        List<CompositionCartProduct> list = getCompositionList(suitIdSet);
        if (list.isEmpty()) {
            logger.info("存在非法商品->:suitId" + suitId);
            throw new ServiceException(Constants.NON_PRODUCT, "存在非法商品");
        }
        CompositionCartProduct product = list.get(0);
        ShopCartProductReminder reminder = commonVerificationProduct(product);
        // mkw add 20150924 增加校验信息
        if (reminder != null) {
            logger.info("存在无法购买的商品->suitId:" + suitId);
            throw new ServiceException(reminder.getCode(), StringUtil.isEmpty(reminder.getMessage())
                    ? "存在无法购买的商品" : reminder.getMessage());
        }
        // end
        JSONObject sjon;
        String uid = user.getUid();
        String key = ShopcartConstants.SHOPCART_PRODUCT + uid;
        String field = "c_" + suitId;
        String val = jedisCluster.hget(key, field);
        if (StringUtil.isEmpty(val)) {
            sjon = new JSONObject();
            sjon.put("amount", amount);
            sjon.put("time", new Date());

            // mkw modif 20150923 添加商品默认不选中 1081
            // sjon.put("check", true);
            sjon.put("check", false);
            // end
        } else {
            sjon = JSONObject.parseObject(val);
            sjon.put("amount", sjon.getInteger("amount") + amount);
        }

        long ret = jedisCluster.hset(key, field, sjon.toJSONString());
        boolean saveOk = ret == 0L || ret == 1L;
        if (saveOk) {
            this.updateShopCartExpire(key, user.isLogin(), jedisCluster);
            return true;
        } else {
            return false;
        }
        // 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
        // 如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。

    }

    private void updateShopCartExpire(String key, boolean isLogin, JedisCluster jedisCluster) {
        int seconds = isLogin ? ShopcartConstants.MEMBER_SHOPCART_EXPIRE_TIME
                : ShopcartConstants.NON_MEMBER_SHOPCART_EXPIRE_TIME;
        long res = jedisCluster.expire(key, seconds);
        if (res != 0L && res != 1L) {
            logger.error("ShopCartInfoServiceImpl.updateShopCartExpire() 设置购物车过期时间失败{}", key);
            // throw new ServiceException(Constants.DATAEXCEPTION, "设置购物车过期时间失败");
        }

    }

    /***
     * 删除购物车商品 普通商品n_开头，套装c_开头
     */
    @Override
    public boolean deleteProduct(String uid, String skuId) throws ServiceException {
        boolean rs = false;
        try {
            Long issucess = jedisCluster.hdel(ShopcartConstants.SHOPCART_PRODUCT + uid, skuId);
            // issucess 返回删除的行数
            rs = issucess.intValue() != 0;
        } catch (Exception ex) {
            logger.error("删除购物车产品异常->skuId:" + skuId, ex);
        }
        return rs;
    }

    /***
     * 批量删除购物车商品 普通商品n_开头，套装c_开头
     */
    @Override
    public boolean deleteProduct(String uid, String[] skuIds) throws ServiceException {

        try {
            Long ret = jedisCluster.hdel(ShopcartConstants.SHOPCART_PRODUCT + uid, skuIds);
            return ret > 0L;
        } catch (Exception ex) {
            logger.error("删除购物车产品异常->skuIds:" + Arrays.toString(skuIds), ex);
        }
        return false;
    }


    @Override
    public void updateCarProductInShopCar(String uid, Long skuId, Integer amount) {
        String field = "n_" + skuId.toString();
        updateProductInShopCar(uid, field, amount);
    }

    /**
     * 通用修改购物车商品数量
     */
    @Override
    public boolean updateProductInShopCar(String uid, String id, Integer amount) {
        try {
            String key = ShopcartConstants.SHOPCART_PRODUCT + uid;
            String val = jedisCluster.hget(key, id);

            if (StringUtil.isEmpty(val)) {
                logger.info("不存在的商品->uid:" + uid + " id:" + id);
                throw new ServiceException(Constants.NON_PRODUCT, "不存在的商品", null);
            }

            JSONObject sjon = JSONObject.parseObject(val);
            sjon.put("amount", amount);
            long r = jedisCluster.hset(key, id, sjon.toJSONString());

            return r == 0L || r == 1L;
        } catch (Exception ex) {
            logger.error("修改购物车异常->uid:" + uid + " id:" + id, ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public int countShopCartProductNum(String uid) {
        try {
            String key = ShopcartConstants.SHOPCART_PRODUCT + uid;

            Integer sum = 0;

            Map<String, String> map = jedisCluster.hgetAll(key);
            for (String s : map.values()) {
                JSONObject json = JSONObject.parseObject(s);

                sum += json.getInteger("amount");
            }

            return sum;

        } catch (Exception e) {
            logger.error("统计购物车商品数量异常->uid:" + uid, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateSuitInShopCarChecked(String uid, Long suitId, int amount) {
        String field = "c_" + suitId.toString();
        updateProductInShopCar(uid, field, amount);
    }


    /**
     * 只选一个商品 参数：用户ID ，产品套餐ID ，是否选中，类型(0代表产品，1代表套餐)
     */

    @Override
    public boolean checkOneProduct(String uid, String id) {
        try {
            // 更新产品选中
            String key = ShopcartConstants.SHOPCART_PRODUCT + uid;
            String val = jedisCluster.hget(key, id);
            if (!StringUtil.isEmpty(val)) {
                if (val.contains("true")) {
                    val = val.replace("true", "false");
                } else {
                    val = val.replace("false", "true");
                }
                long ret = jedisCluster.hset(key, id, val); // 设置选中
                return ret == 0L || ret == 1L;
            } else {
                logger.info("选中产品失败->" + "uid:" + uid + " id:" + id + " 不存在");
            }
        } catch (Exception ex) {
            logger.error("选中产品异常->" + "uid:" + uid + "id:" + id, ex);
        }
        return false;
    }

    /**
     * 全选所有产品
     */
    @Override
    public boolean checkAllProduct(String uid, boolean check) {

        try {
            String cpkey = ShopcartConstants.SHOPCART_PRODUCT + uid;
            Map<String, String> cpListMap = jedisCluster.hgetAll(cpkey);
            if (StringUtil.isEmpty(cpListMap)) {
                return false;
            }
            // 处理单品
            Map<String, String> cpListNewMap = Maps.newHashMap();
            String checkStr = check + "";
            for (Map.Entry<String, String> entry : cpListMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value.contains(checkStr)) {
                    continue;
                } else {
                    value = value.replace(check ? "false" : "true", check ? "true" : "false");
                }
                cpListNewMap.put(key, value);
            }
            if (!StringUtil.isEmpty(cpListNewMap)) {
                String ret = jedisCluster.hmset(cpkey, cpListNewMap);
                return ShopcartConstants.B_SC_OK.equals(ret);
            }
        } catch (Exception e) {
            logger.error("全选购物车保存发生异常->uid:" + uid, e);
        }

        return false;
    }

    @Override
    public boolean choosePromotionInfo(String uid, String productId, String promotionId) {
        // boolean isSuccess = false;
        try {
            String shopCartKey = ShopcartConstants.SHOPCART_PRODUCT + uid;
            String val = jedisCluster.hget(shopCartKey, productId);
            JSONObject json = JSONObject.parseObject(val);
            json.put("orderPromotionId", promotionId);
            long ret = jedisCluster.hset(shopCartKey, productId, json.toJSONString());
            return ret == 0L || ret == 1L;
        } catch (Exception e) {
            logger.error("设置商品参加的活动异常->" + "uid:" + uid + "productId:" + productId + "promotionId:"
                    + promotionId, e);
        }
        return false;
    }

    @Override
    public List<JSONObject> getItemGiftInfo(String uid, String itemId) {
        return null;
    }

    @Override
    public boolean chooseGift(String uid, String itemId, Map<Integer, Integer> map) {

        try {
            String rkey = ShopcartConstants.SHOPCART_ITEM + uid;
            String itemStr = jedisCluster.hget(rkey, itemId);
            ShopCartItem item = JSONObject.parseObject(itemStr, ShopCartItem.class);
            Map<String, Gift> giftMap = item.getRuleGifts();
            Set<Gift> newSet = Sets.newLinkedHashSet();
            int tocount = item.getGiftCount();
            int choosedCount = 0;
            BigDecimal additionalMoney = BigDecimal.ZERO;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                Integer giftDataId = entry.getKey();
                Integer amount = entry.getValue();
                Gift gift = giftMap.get(giftDataId.toString());
                List<String> giftIdList = new ArrayList<>();
                giftIdList.add(gift.getId());
                Map<String, Integer> stockMap = this.queryStockBatch(giftIdList);
                Integer stock = stockMap.get(gift.getId());
                if (stock == null) {
                    logger.info("选择了无库存的加价购商品->" + "uid:" + uid + "giftId:" + gift.getId());
                    throw new ServiceException(Constants.NON_STOCK, "选择了无库存的加价购商品");
                }
                if (stock.compareTo(amount) < 0) {
                    logger.info("选择的加价购商品数量超过了库存->" + "uid:" + uid + "giftId:" + gift.getId());
                    throw new ServiceException(Constants.UNDERSTOCK, "选择的加价购商品数量超过了库存");
                }
                if (amount > tocount) {
                    logger.info("超过可选数量->" + "uid:" + uid + "giftId:" + gift.getId());
                    throw new ServiceException(0, "超过可选数量");
                }
                // if (amount > gift.getMaxAmount()) {
                // throw new ServiceException(0, "超过可选数量");
                // }
                gift.setAmount(amount);
                tocount -= amount;
                choosedCount += amount;
                newSet.add(gift);
                if (gift.getPrice() != null && gift.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                    additionalMoney = additionalMoney
                            .add(gift.getPrice().multiply(BigDecimal.valueOf(amount)));
                }
            }
            item.setGifts(newSet);
            item.setCountChoosed(choosedCount);
            item.setAdditionalMoney(additionalMoney);
            String itemStrNew = JSONObject.toJSONString(item, ShopCartItemCache.filter,
                    SerializerFeature.DisableCircularReferenceDetect);
            long ret = jedisCluster.hset(rkey, itemId, itemStrNew);

            return ret == 0L || ret == 1L;

        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.info("选择加价购商品异常->" + "uid:" + uid + "itemId:" + itemId, e);
            throw new ServiceException(Constants.DATAEXCEPTION, "系统异常");
        }
    }

    @Override
    public boolean checkShopAllProduct(String uid, String[] selectIdArray, boolean checked) {

        try {

            String shopCartKey = ShopcartConstants.SHOPCART_PRODUCT + uid;
            List<String> list = jedisCluster.hmget(shopCartKey, selectIdArray);
            String checkedStr = checked + "";
            Map<String, String> newValueMap = Maps.newHashMap();
            for (int i = 0; i < list.size(); i++) {
                String checkValue = list.get(i);
                if (checkValue.contains(checkedStr)) {
                    continue;
                }
                checkValue =
                        checkValue.replace(checked ? "false" : "true", checked ? "true" : "false");
                newValueMap.put(selectIdArray[i], checkValue);
            }
            if (!newValueMap.isEmpty()) {
                String ret = jedisCluster.hmset(shopCartKey, newValueMap);

                return ShopcartConstants.B_SC_OK.equals(ret);

            }
        } catch (Exception e) {
            logger.info("选中购物车商品异常->" + "uid:" + uid + "selectIdArray:"
                    + Arrays.toString(selectIdArray));
        }
        return false;
    }

    /**
     * 查询活动活动期间购买量 优化读缓存 60秒过期
     */
    private Integer queryUserPurchase(Long userId, String promotionId, Long skuId) {

        try {


            return orderItemDao.queryUserBuyPromotionNum(userId, skuId, promotionId);
            // }
        } catch (SQLException e) {
            logger.error("查询会员活动购买数量异常->" + "userId:" + userId + "skuId:" + skuId + "promotionId:"
                    + promotionId, e);

        }
        return 0;
    }

    @Override
    public boolean deleteGift(String uid, String itemId, String giftId) {

        try {
            String rkey = ShopcartConstants.SHOPCART_ITEM + uid;
            String itemStr = jedisCluster.hget(rkey, itemId);
            ShopCartItem item = JSONObject.parseObject(itemStr, ShopCartItem.class);
            // Gift gift = new Gift();
            // gift.setId(giftId);
            // gift.setDataId(giftId);
            Set<Gift> giftSet = item.getGifts();
            if (giftSet == null) {
                logger.warn(
                        "没有已选的加价购商品->" + "uid:" + uid + "itemId:" + itemId + "giftId:" + giftId);
                return false;
            }
            Iterator<Gift> giftIterator = giftSet.iterator();
            BigDecimal money = BigDecimal.ZERO;
            int countChoosed = 0;
            item.setCountChoosed(0);
            item.setAdditionalMoney(money);
            Set<Gift> removeGiftSet = new HashSet<>();
            while (giftIterator.hasNext()) {
                Gift f = giftIterator.next();
                // if (f.getId().equals(giftId)) {
                if (f.getDataId().equals(giftId)) {
                    removeGiftSet.add(f);
                    continue;
                }
                item.addItionalMoney(f.getPrice().multiply(BigDecimal.valueOf(f.getAmount())));
                countChoosed += f.getAmount();
            }
            if (StringUtil.isEmpty(removeGiftSet)) {
                return true;
            }
            giftSet.removeAll(removeGiftSet);
            item.setCountChoosed(countChoosed);
            String itemStrNew = JSONObject.toJSONString(item);
            long ret = jedisCluster.hset(rkey, itemId, itemStrNew);

            return ret == 0L || ret == 1L;

        } catch (Exception e) {
            logger.error("删除加价购商品异常->" + "uid:" + uid + "itemId:" + itemId + "giftId:" + giftId);
        }
        return false;
    }

    @Resource(name = "orderMainServiceImpl")
    private OrderMainService orderMainService;

    @Override
    public Map<Long, SupplierFare> getSupplierFareMap(Set<Long> keySet) {
        Map<Long, SupplierFare> fareMap = Maps.newHashMap();
        // 入驻商家ids
        List<Long> ids = Lists.newArrayList();


        if (keySet != null && !keySet.isEmpty()) {
            SupplierFare temp;
            // 循环从cahce获取
            for (Long id : keySet) {
                temp = getSupplierFaceFromCache(jedisCluster.get(ConfigurationUtil
                        .getString("b2b.com.kmzyc.supplier.fare").concat(id.toString())));
                if (temp == null) {
                    ids.add(id); // cache中不存在，从db中查询
                } else {
                    fareMap.put(id, temp);
                }
            }
        }

        // 全部从缓存获取到
        if (ids.isEmpty()) {
            return fareMap;
        }

        try {
            if (ids.contains(ShopUtil.SELF_AND_PROXY_KEY)) {
                // 自营邮费查询
                SupplierFare selfFare = orderMainService.querySelfFare(ShopUtil.SHOP_CAR_CHANNEL);
                if (selfFare != null) {
                    selfFare.setSupplierId(ShopUtil.SELF_AND_PROXY_KEY);
                    setSupplierFaceToCache(selfFare, jedisCluster);
                    fareMap.put(ShopUtil.SELF_AND_PROXY_KEY, selfFare);
                    ids.remove(ShopUtil.SELF_AND_PROXY_KEY);
                }
            }

            // 说明 ids 只有一个元素
            if (ids.isEmpty()) {
                return fareMap;
            }

            List<SupplierFare> fareList = this.shopcartInfoDao.selectSupplierFareInfoList(ids);
            if (null != fareList && !fareList.isEmpty()) {
                for (SupplierFare sf : fareList) {
                    fareMap.put(sf.getSupplierId(), sf);
                    setSupplierFaceToCache(sf, jedisCluster);
                }
            }
        } catch (Exception e) {
            logger.error("获取商家运费异常", e);
        }

        return fareMap;
    }

    private SupplierFare getSupplierFaceFromCache(String supplierFare) {
        SupplierFare selfFare = null;
        if (!StringUtil.isEmpty(supplierFare)) {
            selfFare = JSONObject.parseObject(supplierFare, SupplierFare.class);
        }
        return selfFare;
    }

    private void setSupplierFaceToCache(SupplierFare supplierFare, JedisCluster jedisCluster) {
        // 存入cache, 设置过期时间 30天有效 后台修改有del
        jedisCluster.setex(
                ConfigurationUtil.getString("b2b.com.kmzyc.supplier.fare")
                        .concat(supplierFare.getSupplierId().toString()),
                ConfigurationUtil.getIntValue("b2b.common.supplier.fare.valid.time") * 60 * 2 * 24
                        * 30,
                JSONObject.toJSONString(supplierFare));
    }

    @Override
    public boolean choosePresents(String uid, String itemId, String presents) {

        try {
            String rkey = ShopcartConstants.SHOPCART_ITEM + uid;
            String itemStr = jedisCluster.hget(rkey, itemId);
            ShopCartItem item = JSONObject.parseObject(itemStr, ShopCartItem.class);
            if (item.getRulePresents().containsKey(presents)) {
                item.setPresents(presents);

                // mkw add 20151015 统计已选赠品
                Map<String, List<Gift>> rulePresensts = item.getRulePresents();
                if (!rulePresensts.isEmpty()) {
                    List<Gift> giftList = rulePresensts.get(presents);
                    int countPresents = 0;
                    for (Gift aGiftList : giftList) {
                        countPresents += aGiftList.getAmount();
                    }
                    item.setCountPresents(countPresents);
                }
                // end

                String itemStrNew = JSONObject.toJSONString(item);
                long ret = jedisCluster.hset(rkey, itemId, itemStrNew);

                return ret == 0L || ret == 1L;
            }
        } catch (Exception e) {
            logger.error("选择赠品异常->" + "presents:" + presents + "itemId:" + itemId + "uid:" + uid,
                    e);
        }
        return false;
    }

    @Override
    public Map<String, Integer> queryStockBatch(List<?> skuIds) {
        try {
            if (skuIds == null || skuIds.isEmpty()) {
                return Maps.newHashMap();
            }
            return shopcartInfoDao.queryStockBatch(skuIds);
        } catch (SQLException e) {
            logger.error("批量查询商品库存异常->" + "skuIds:" + skuIds, e);
            return Maps.newHashMap();
        }
    }

    @Override
    public boolean removeChecked(String uid) {

        try {
            String shopCartKey = ShopcartConstants.SHOPCART_PRODUCT + uid;
            Map<String, String> allProuctsCacheStringMap = jedisCluster.hgetAll(shopCartKey);
            if (allProuctsCacheStringMap == null || allProuctsCacheStringMap.isEmpty()) {
                return false;
            }
            List<String> removeKeyList = new ArrayList<>();
            for (Map.Entry<String, String> entry : allProuctsCacheStringMap.entrySet()) {
                String value = entry.getValue();
                if (value.contains(Boolean.TRUE.toString())) {
                    removeKeyList.add(entry.getKey());
                }
            }
            if (removeKeyList.isEmpty()) {
                return false;
            }
            long ret = jedisCluster.hdel(shopCartKey,
                    removeKeyList.toArray(new String[removeKeyList.size()]));
            return ret > 0L;
        } catch (Exception e) {
            logger.error("删除购物车所有选中商品异常->" + "uid:" + uid, e);
        }
        return false;
    }

    /**
     * 合并购物车 未叠加数量
     */
    @Override
    public boolean mergeShopCar(String userId, String tempId) {


        Map<String, String> prouctTemps =
                jedisCluster.hgetAll(ShopcartConstants.SHOPCART_PRODUCT + tempId);
        if (prouctTemps.isEmpty()) // 临时购物车为空
        {
            return true;
        }

        if (!prouctTemps.isEmpty()) {
            String res =
                    jedisCluster.hmset(ShopcartConstants.SHOPCART_PRODUCT + userId, prouctTemps); // 把临时购物车单品放入缓存

            if (res.equals(ShopcartConstants.B_SC_OK)) {
                jedisCluster.expire(ShopcartConstants.SHOPCART_PRODUCT + userId,
                        ShopcartConstants.MEMBER_SHOPCART_EXPIRE_TIME);

                // mkw add 20151016 合并加价购商品
                String rkey = ShopcartConstants.SHOPCART_ITEM + tempId;
                Map<String, String> prouctTemps11 = jedisCluster.hgetAll(rkey);
                if (!prouctTemps11.isEmpty()) {
                    jedisCluster.hmset(ShopcartConstants.SHOPCART_ITEM + userId, prouctTemps11);
                    jedisCluster.del(ShopcartConstants.SHOPCART_ITEM + tempId);
                }
                // end

                jedisCluster.del(ShopcartConstants.SHOPCART_PRODUCT + tempId);
                return true;
            } else {
                return false;
            }
        }

        return true;
    }
}
