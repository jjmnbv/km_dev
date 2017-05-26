package com.kmzyc.b2b.shopcart.util;

import java.math.BigDecimal;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.kmzyc.b2b.shopcart.vo.CartProduct;
import com.kmzyc.b2b.shopcart.vo.CompositionCartProduct;
import com.kmzyc.b2b.shopcart.vo.NormalCartProduct;
import com.kmzyc.b2b.shopcart.vo.SellerShop;
import com.kmzyc.b2b.shopcart.vo.SellerShopList;
import com.kmzyc.b2b.shopcart.vo.ShopCartItem;
import com.kmzyc.b2b.shopcart.vo.ShopCartProduct;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.optimization.vo.Promotion;
import com.kmzyc.util.StringUtil;

public class ShopCartProcess {

    // 缓存中商品数量 添加时间 选中状态
    private Map<String, ProductPromotion> promtoionMap;
    // Map<String, String> itemCacheMap;
    // Map<String, ShopCartItem> itemPromotionMap;
    private Map<String, ShopCartProduct> prouctsPageMap;
    private ShopCartItemCache shopCartItemCache;
    private SellerShopList sellerShopList;


    public ShopCartProcess(Map<String, ProductPromotion> promtoionMap,
                           ShopCartItemCache shopCartItemCache) {
        // this.compositions = compositions;
        this.promtoionMap = promtoionMap;
        prouctsPageMap = Maps.newHashMap();
        sellerShopList = new SellerShopList();
        // itemPromotionMap = new HashMap<String, ShopCartItem>();
        this.shopCartItemCache = shopCartItemCache;
    }

    public void processCompositionCartProduct(CompositionCartProduct compostionProduct,
                                              ShopCartProduct compositionCartProduct) {
        Long ccpId = compostionProduct.getId();
        String ccpIdkey = "c_" + ccpId.toString();
        SellerShop shop = generateShop(compostionProduct);
        JSONObject json = compositionCartProduct.getMap(); // 获取缓存中套餐数量
        json.put("pid", ccpIdkey);
        BigDecimal amount = json.getBigDecimal("amount");
        BigDecimal productPriceTotal = compostionProduct.getPrice().multiply(amount);
        json.put("productPriceTotal", productPriceTotal);
        json.put("name", compostionProduct.getName());
        json.put("finalPrice", compostionProduct.getFinalPrice());
        json.put("id", ccpId);
        json.put("title", compostionProduct.getName());
        json.put("erpProCode", compostionProduct.getErpProCode());
        json.put("productList", compostionProduct.getProductList());
        json.put("stockCount", compostionProduct.getStockCount());// 产品库存
        json.put("pvalue", compostionProduct.getPvalue());
        ShopCartItem shopCartItem = generateItem(compostionProduct, json);
        shopCartItem.addProduct(compositionCartProduct);
        shop.addShopCartItem(shopCartItem);
        sellerShopList.add(shop);
        shopCartItem.addAllMoney(productPriceTotal);
        // shop.addAllMoney(productPriceTotal);
        boolean check = json.getBooleanValue("check");
        if (compositionCartProduct.getProductReminder() != null &&
                !compositionCartProduct.getProductReminder().isNormal()) {

        } else {
            shop.addCheckProductId(ccpIdkey, check);
        }
        if (check) {
            shopCartItem.addUncalculateMoney(productPriceTotal);
            shopCartItem.addCheckedProductCount(amount.intValue() * compostionProduct.getAmount());
            shopCartItem.addWeight(compostionProduct.getUnitWeight().multiply(amount));
            shopCartItem.addTotalProductPvalue(compostionProduct.getPvalue().multiply(amount));
            // 商家统计PV
            shop.addTotalProductPvalue(compostionProduct.getPvalue().multiply(amount));

        }
        prouctsPageMap.put(ccpIdkey, compositionCartProduct);
    }

    public void processNormalProduct(NormalCartProduct normalCartProduct,
                                     ShopCartProduct shopCartProduct) {
        Long skuId = normalCartProduct.getProductSkuId();
        String skuIdkey = "n_" + skuId.toString();
        JSONObject productInfoJson = shopCartProduct.getMap();
        productInfoJson.put("pid", skuIdkey);
        SellerShop shop = generateShop(normalCartProduct);
        ShopCartItem shopCartItem = generateItem(normalCartProduct, productInfoJson);
        shopCartItem.addProduct(shopCartProduct);
        // TODO 附赠
        shop.addShopCartItem(shopCartItem);
        sellerShopList.add(shop);
        // BigDecimal price = normalCartProduct.getPrice();
        BigDecimal finalPrice = productInfoJson.getBigDecimal("finalPrice");
        BigDecimal amount = productInfoJson.getBigDecimal("amount");
        BigDecimal productPriceTotal = finalPrice.multiply(amount);
        BigDecimal productPvalueTotal = BigDecimal.ZERO;
        if (normalCartProduct.getPvalue() != null) {
            productPvalueTotal = normalCartProduct.getPvalue().multiply(amount); // 时代pv小计总值
        }

        productInfoJson.put("productPriceTotal", productPriceTotal);
        productInfoJson.put("productPvalueTotal", productPvalueTotal);
        productInfoJson.put("pvalue", normalCartProduct.getPvalue());
        productInfoJson.put("erpProCode", normalCartProduct.getErpProCode());
        productInfoJson.put("id", skuId);

        // mkw modify 20150928 是否免邮
        String freeStatus = String.valueOf(normalCartProduct.getFreeStatus());
        if (StringUtil.isEmpty(freeStatus)) {
            freeStatus = ShopcartConstants.S_FREE_STATUS_0;
            normalCartProduct.setFreeStatus(Integer.valueOf(freeStatus));
        }
        if (ShopcartConstants.S_FREE_STATUS_1.equals(freeStatus)) {
            productInfoJson.put("freight", 0);
            normalCartProduct.setFreight(new BigDecimal(0));
        } else {
            productInfoJson.put("freight", normalCartProduct.getFreight());
        }
        productInfoJson.put("freeStatus", normalCartProduct.getFreeStatus());
        // end

        productInfoJson.put("finalPrice", finalPrice);
        productInfoJson.put("productSkuCode", normalCartProduct.getProductSkuCode());
        if (normalCartProduct.getSkuAttrValue() != null) { // 如果规格不能空
            productInfoJson.put("title",
                    normalCartProduct.getTitle() + " " + normalCartProduct.getSkuAttrValue());
        } else {
            productInfoJson.put("title", normalCartProduct.getTitle());
        }
        productInfoJson.put("stockCount", normalCartProduct.getStockCount());// 产品库存
        productInfoJson.put("imagePath", normalCartProduct.getImagePath());
        shopCartItem.addAllMoney(productPriceTotal);
        // shop.addAllMoney(productPriceTotal);
        boolean check = productInfoJson.getBooleanValue("check");
        if (shopCartProduct.getProductReminder() != null &&
                !shopCartProduct.getProductReminder().isNormal()) {

        } else {
            shop.addCheckProductId(skuIdkey, check);
        }
        if (check) {
            shopCartItem.addUncalculateMoney(productPriceTotal); // 选中小计金额 计算了活动
            shopCartItem.addCheckedProductCount(amount.intValue());
            shopCartItem.addWeight(normalCartProduct.getUnitWeight().multiply(amount));
            if (normalCartProduct.getFreight() != null) {
                shopCartItem.addFreight(normalCartProduct.getFreight().multiply(amount));
            }
            shopCartItem.addTotalProductPvalue(productPvalueTotal);
            // 商家统计PV
            shop.addTotalProductPvalue(productPvalueTotal);
        }
        prouctsPageMap.put(skuIdkey, shopCartProduct);
        // sellerShopList.add(shop);
    }

    private ShopCartItem generateItem(CartProduct ncp, JSONObject productInfoJson) {
        if (productInfoJson == null) {
            productInfoJson = new JSONObject();
        }
        BigDecimal price = ncp.getPrice();
        Promotion orderPromotion = null;
        Long sellerId;
        int type = ncp.getSupplierType();
        if (type == 3 || type == 1) {
            sellerId = 1L;
        } else {
            sellerId = ncp.getSellerId();
            // if (sellerId == 221L) {
            // sellerId = 1L;
            // }
        }
        BigDecimal finalPrice = price;
        String itemkey = sellerId + "_0";
        if (ncp instanceof CompositionCartProduct) {
        } else {
            Long id = ncp.getProductSkuId();
            String promotionId = productInfoJson.getString("orderPromotionId");
            if (!StringUtil.isEmpty(promtoionMap)) {
                ProductPromotion productPromotion = promtoionMap.get(id.toString());
                // System.out.println(id);
                if (productPromotion != null) {
                    finalPrice = productPromotion.calculateFinalPrice(price);
                    Map<String, Promotion> map = productPromotion.getOrderPromtotions();
                    if (promotionId != null && map != null) {// 已经选了活动
                        orderPromotion = map.get(promotionId);
                    }
                    if (orderPromotion == null && promotionId == null) {// 没有选活动
                        orderPromotion = productPromotion.getDefaultOrderPromtotion();
                    }
                    if (orderPromotion != null) {
                        itemkey = sellerId + "_" + orderPromotion.getId();
                    }
                    productInfoJson.put("productPromotion", productPromotion);
                }
            }

        }
        productInfoJson.put("finalPrice", finalPrice);
        ShopCartItem shopCartItem = shopCartItemCache.get(itemkey);
        if (shopCartItem == null) {
            shopCartItem = new ShopCartItem(itemkey);
            shopCartItem.setPromtionInfo(orderPromotion);
            shopCartItemCache.add(shopCartItem, false);
        } else {
            shopCartItem.setPromtionInfo(orderPromotion);
        }


        return shopCartItem;
    }

    private SellerShop generateShop(CartProduct normalCartProduct) {
        Long shopCode;
        int type = normalCartProduct.getSupplierType();
        String name;
        if (type == 3 || type == 1) {
            shopCode = 1L;
            name = "康美自营";
        } else {
            shopCode = normalCartProduct.getSellerId();
            // if (shopCode == 221L) {
            // shopCode = 1l;
            // name = "康美自营";
            // } else {
            name = normalCartProduct.getShopName();
            // }
        }
        SellerShop shop = sellerShopList.get(shopCode);
        if (shop == null) {
            shop = new SellerShop(); //
            shop.setSellerId(shopCode);
            shop.setShopName(name);
            shop.setSupplierType(type);
        }
        return shop;
    }


    public Map<String, ShopCartProduct> getProuctsPageMap() {
        return prouctsPageMap;
    }

    public SellerShopList getSellerShopList() {
        return sellerShopList;
    }

    public ShopCartItemCache getShopCartItemCache() {
        return shopCartItemCache;
    }


}
