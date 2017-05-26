package com.kmzyc.b2b.shopcart.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kmzyc.b2b.shopcart.vo.NormalCartProduct;
import com.kmzyc.b2b.shopcart.vo.ShopCart;
import com.kmzyc.b2b.shopcart.vo.ShopCartProduct;
import com.kmzyc.b2b.util.CookieUtil;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.promotion.app.vobject.OrderVo;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.enums.CouponProductType;

/**
 * 购物车操作类，不更新缓存
 * 
 * @author xlg
 * 
 */
public abstract class ShopCartUtil {
    /**
     * 购物车memcached前缀
     */
    public final static String SHOP_CAR_MEMCACHED_KEY =
            ConfigurationUtil.getString("memcached_shopcart_key");
    /**
     * 购物车redis前缀
     */
    public final static String SHOP_CAR_REDIS_KEY =
            ConfigurationUtil.getString("redis_shopcart_key");

    /**
     * 购物车redis前缀
     */
    public final static String SHOP_CART_NEW_REDIS_KEY = "r_b2b_cart_";
    /**
     * 购物车渠道
     */
    public final static String SHOP_CAR_CHANNEL = ConfigurationUtil.getString("CHANNEL");
    /**
     * memcache过期时间1天,单位秒
     */
    public final static Long MEMCACHED_SHOPCAR_TIME =
            1000L * Integer.parseInt(ConfigurationUtil.getString("memCached_shopcart_time"));
    /**
     * redis过期时间100天,单位秒
     */
    public final static Integer SHOP_CAR_REDIS_EXPIRETIME =
            Integer.valueOf(ConfigurationUtil.getString("redis_memCached_shopcart_expire"));

    /*  *//**
           * 创建新的购物车
           *//*
             * public static ShopCar createNewShopCar(String userId, Boolean isLogin) { ShopCar
             * shopcar = new ShopCar(); shopcar.setUserID(userId); shopcar.setUserIsLogin(isLogin);
             * return shopcar; }
             */

    /*  *//**
           * 获取购物车ID和登录状态
           * 
           * @param request
           * @param response
           * @return
           *//*
             * public static Map<String, Object> getShopCarInfo(HttpServletRequest request,
             * HttpServletResponse response) { Map<String, Object> shopCarInfo = new HashMap<String,
             * Object>(); HttpSession session = request.getSession(); Object sessionUserId =
             * session.getAttribute(ThirdConstant.TMALL_USER_ID); String isTmall =
             * request.getParameter("isTmall"); boolean isLogin = false; if
             * (StringUtil.isEmpty(isTmall)) { sessionUserId =
             * session.getAttribute(Constants.SESSION_USER_ID); isLogin = null != sessionUserId; }
             * else { sessionUserId = session.getAttribute(ThirdConstant.TMALL_USER_ID); isLogin =
             * true; } String userId = null; if (isLogin) { userId = sessionUserId.toString(); }
             * else { userId = getTempUserId(request, response); } shopCarInfo.put("shopCarId",
             * userId); shopCarInfo.put("isLogin", isLogin); return shopCarInfo; }
             */

    /*  *//**
           * 合并购物车
           * 
           * @param shopCar1
           * @param shopCar2
           * @return
           *//*
             * public ShopCar mergeShopCar(ShopCar shopCar1, ShopCar shopCar2) throws
             * ServiceException { if (isNotEmpty(shopCar2)) { if (null == shopCar1 ||
             * !isNotEmpty(shopCar1)) { shopCar1 = shopCar2; } else {
             * shopCar1.mergeShopCar(shopCar2); } shopCar2.clear(); } return shopCar1; }
             */

    /**
     * 获取游客ID
     * 
     * @param request
     * @param response
     * @return
     */
    public static String getTempUserId(HttpServletRequest request, HttpServletResponse response) {
        String id = CookieUtil.getCookieValue(request, Constants.COOKIE_SHOPCART_TEMPID);
        if (StringUtil.isEmpty(id)) {
            id = request.getSession().getId();
            CookieUtil.createCookie(response, Constants.COOKIE_SHOPCART_TEMPID, id,
                    60 * 60 * 24 * 2);
        }
        return id;
    }


    /**
     * 判断购物车非空
     * 
     * @param shopCar
     * @return
     */
    // public static boolean isNotEmpty(ShopCar shopCar) {
    // return !isEmpty(shopCar);
    // }

    /*  *//**
           * 判断购物车非空
           * 
           * @param shopCar
           * @return
           *//*
             * public static boolean isEmpty(ShopCar shopCar) { return null == shopCar || null ==
             * shopCar.getSellerShopCar() || shopCar.getSellerShopCar().isEmpty(); }
             */

    /*  *//**
           * 批量删除
           * 
           * @param skuSellerIds
           * @param suitSellerIds
           * @param skuIds
           * @param suitIds
           *//*
             * public static boolean batchRemoveProduct(ShopCar shopcar, String skuSellerIds, String
             * skuIds, String suitSellerIds, String suitIds) { boolean reCala = false; String[]
             * sellerIds = null; if (!StringUtil.isEmpty(skuSellerIds)) { sellerIds =
             * skuSellerIds.split(","); if (null != sellerIds && 0 != sellerIds.length &&
             * !StringUtil.isEmpty(skuIds)) { reCala = true; String[] skus = skuIds.split(","); for
             * (int i = 0, len = sellerIds.length; i < len; i++) {
             * shopcar.removeProduct(Long.parseLong(sellerIds[i]), Long.parseLong(skus[i])); } } }
             * 
             * if (!StringUtil.isEmpty(suitSellerIds)) { sellerIds = suitSellerIds.split(","); if
             * (null != sellerIds && 0 != sellerIds.length && !StringUtil.isEmpty(suitIds)) { reCala
             * = true; String[] suits = suitIds.split(","); for (int i = 0, len = sellerIds.length;
             * i < len; i++) { shopcar.removeComposition(Long.parseLong(sellerIds[i]),
             * Long.parseLong(suits[i])); } } }
             * 
             * if (reCala) { shopcar.calcShopCarProductCount(); } return reCala; }
             */

    /**
     * 批量从购物车删除商品
     *//*
       * public static boolean batchRemove(ShopCar shopCar, JSONObject jo) throws ServiceException {
       * if (null != jo) { Set sellerKeys = jo.keySet(); if (null != sellerKeys &&
       * !sellerKeys.isEmpty()) { List<Long> removeId = new ArrayList<Long>(); Map<Long, Map<Long,
       * SellerShopProduct>> sspMapSet = shopCar.getSellerShopCar(); for (Iterator sellerKeysIt =
       * sellerKeys.iterator(); sellerKeysIt.hasNext();) { Long sellerId =
       * Long.parseLong(String.valueOf(sellerKeysIt.next())); Long sellerKey = sellerId; if
       * (!sspMapSet.containsKey(sellerKey)) { sellerKey = ShopCar.SELF_AND_PROXY_KEY; } JSONObject
       * sellerJo = jo.getJSONObject(sellerId.toString()); Map<Long, SellerShopProduct> sspMap =
       * sspMapSet.get(sellerKey); if (null == sspMap || sspMap.isEmpty() ||
       * !sspMap.containsKey(sellerId) || null == sellerJo) { continue; } SellerShopProduct ssp =
       * sspMap.get(sellerId); JSONArray skuJa = sellerJo.getJSONArray("skus"); Map<Long,
       * CarProduct> cpMap = ssp.getCarProducts(); if (null != skuJa && !skuJa.isEmpty() && null !=
       * cpMap && !cpMap.isEmpty()) { removeId.clear(); for (Object obj : skuJa) {
       * removeId.add(Long.parseLong(obj.toString())); } cpMap.keySet().removeAll(removeId); }
       * JSONArray suitJa = sellerJo.getJSONArray("suits"); Map<Long, CompositionCarProduct> ccpMap
       * = ssp.getCompositionCarProducts(); if (null != ccpMap && !ccpMap.isEmpty() && null !=
       * suitJa && !suitJa.isEmpty()) { removeId.clear(); for (Object obj : suitJa) {
       * removeId.add(Long.parseLong(obj.toString())); } ccpMap.keySet().removeAll(removeId); } if
       * ((null == cpMap || cpMap.isEmpty()) && (null == ccpMap || ccpMap.isEmpty())) {
       * sspMap.remove(sellerId); if (null == sspMap || sspMap.isEmpty()) {
       * sspMapSet.remove(sellerKey); } } } shopCar.calcShopCarProductCount(); return true; } }
       * return false; }
       */

    /**
     * 移除选中
     * 
     * @param shopCar
     */
    /*
     * public static void removeChecked(ShopCar shopCar) { shopCar.removeCheckedProduct(); }
     */

    /**
     * 移除商家选中的产品
     * 
     * @param shopCar
     */
    /*
     * public static void removeSellerChecked(ShopCar shopCar, Long sellerId) {
     * shopCar.removeSellerChecked(sellerId); }
     */

    /**
     * 购物车排序 自营 >代销>入驻
     */
    /*
     * public static void sortShopCarBySellerId(ShopCar shopCar) { Map<Long, Map<Long,
     * SellerShopProduct>> sspMapSet = shopCar.getSellerShopCar(); if (null != sspMapSet &&
     * !sspMapSet.isEmpty()) { try { Long selfKey = ShopCar.SELF_AND_PROXY_KEY; Map<Long, Map<Long,
     * SellerShopProduct>> newSspMapSet = new LinkedHashMap<Long, Map<Long, SellerShopProduct>>();
     * Map<Long, SellerShopProduct> selfAndProxyMap = sspMapSet.get(selfKey); Map<Long,
     * SellerShopProduct> selfMap = new LinkedHashMap<Long, SellerShopProduct>(); Map<Long,
     * SellerShopProduct> proxyMap = new LinkedHashMap<Long, SellerShopProduct>(); if (null !=
     * selfAndProxyMap && !selfAndProxyMap.isEmpty()) { for (Iterator<SellerShopProduct> sspIt =
     * selfAndProxyMap.values().iterator(); sspIt .hasNext();) { SellerShopProduct ssp =
     * sspIt.next(); Long sellerId = ssp.getSellerId(); short supplierType =
     * ssp.getSellerInfo().getSupplierType(); if (SupplierType.SELLER_TYPE_SELF_SUPPORT.getIndex()
     * == supplierType) { selfMap.put(sellerId, ssp); } else if
     * (SupplierType.SELLER_TYPE_SALE_PROXY.getIndex() == supplierType) { proxyMap.put(sellerId,
     * ssp); } } selfMap.putAll(proxyMap); newSspMapSet.put(selfKey, selfMap); }
     * sspMapSet.remove(selfKey); newSspMapSet.putAll(sspMapSet);
     * shopCar.setSellerShopCar(newSspMapSet); proxyMap = null; selfMap = null; selfAndProxyMap =
     * null; sspMapSet = null; } catch (Exception e) { e.printStackTrace(); } } }
     */

    /*  *//**
           * 获取套餐
           * 
           * @param shopCar
           * @param sellerId
           * @param skuId
           * @return
           *//*
             * public static CompositionCarProduct getComposition(ShopCar shopCar, Long sellerId,
             * Long suitId) { CompositionCarProduct ccp = null; if (null != shopCar) { Map<Long,
             * Map<Long, SellerShopProduct>> sspMapSet = shopCar.getSellerShopCar(); if (null !=
             * sspMapSet && !sspMapSet.isEmpty()) { SellerShopProduct ssp = null; if
             * (sspMapSet.containsKey(sellerId)) { ssp = sspMapSet.get(sellerId).get(sellerId); }
             * else if (sspMapSet.containsKey(ShopCar.SELF_AND_PROXY_KEY)) { ssp =
             * sspMapSet.get(ShopCar.SELF_AND_PROXY_KEY).get(sellerId); } if (null != ssp &&
             * ssp.getCompositionCarProducts() != null && ssp.getCompositionCarProducts().size() >
             * 0) { ccp = ssp.getCompositionCarProducts().get(suitId); } } } return ccp; }
             */

    /*  *//**
           * 产品Map转换成List
           * 
           * @param carProducts
           * @return
           *//*
             * public static List<CarProduct> convertProductMapToList(Map<Long, CarProduct>
             * carProducts) { List<CarProduct> list = null; if (null != carProducts &&
             * !carProducts.isEmpty()) { list = new ArrayList<CarProduct>(); for
             * (Iterator<CarProduct> iterator = carProducts.values().iterator();
             * iterator.hasNext();) { CarProduct cp = iterator.next(); list.add(cp); } } return
             * list; }
             */

    /*  *//**
           * 套餐Map转换成List
           * 
           * @param Compositions
           * @return
           *//*
             * public static List<CompositionCarProduct> convertCompositionMapToList( Map<Long,
             * CompositionCarProduct> Compositions) { List<CompositionCarProduct> list = null; if
             * (null != Compositions && !Compositions.isEmpty()) { list = new
             * ArrayList<CompositionCarProduct>(); for (Iterator<CompositionCarProduct> iterator =
             * Compositions.values().iterator(); iterator .hasNext();) { CompositionCarProduct ccp =
             * iterator.next(); list.add(ccp); } } return list; }
             */

    /*  *//**
           * 遍历操作产品
           * 
           * @param shopCar
           * @throws Exception
           *//*
             * public void eachProduct(ShopCar shopCar) throws Exception { Map<Long, Map<Long,
             * SellerShopProduct>> sspMapSet = shopCar.getSellerShopCar(); if (null != sspMapSet &&
             * !sspMapSet.isEmpty()) { for (Iterator<Map<Long, SellerShopProduct>> sspItSet =
             * sspMapSet.values().iterator(); sspItSet .hasNext();) { for
             * (Iterator<SellerShopProduct> sspIt = sspItSet.next().values().iterator(); sspIt
             * .hasNext();) { SellerShopProduct ssp = sspIt.next(); Map<Long, CarProduct> cpMap =
             * ssp.getCarProducts(); if (cpMap != null && !cpMap.isEmpty()) { for
             * (Iterator<CarProduct> cpIt = cpMap.values().iterator(); cpIt.hasNext();) { boolean
             * isSuccess = handleProduct(shopCar, cpIt.next()); if (!isSuccess) { throw new
             * Exception(""); } } } Map<Long, CompositionCarProduct> ccpMap =
             * ssp.getCompositionCarProducts(); if (ccpMap != null && !ccpMap.isEmpty()) { for
             * (Iterator<CompositionCarProduct> ccpIt = ccpMap.values().iterator(); ccpIt
             * .hasNext();) { boolean isSuccess = handleProduct(shopCar, ccpIt.next()); if
             * (!isSuccess) { throw new Exception(""); } } } } } } }
             */

    /*  *//**
           * 遍历操作礼品
           * 
           * @param shopCar
           *//*
             * public void eachGiftProduct(ShopCar shopCar) { Map<Long, Map<Long, CarProduct>>
             * sspMapSet = shopCar.getGiftProductMap(); if (null != sspMapSet &&
             * !sspMapSet.isEmpty()) { for (Iterator<Map<Long, CarProduct>> sspItSet =
             * sspMapSet.values().iterator(); sspItSet .hasNext();) {
             * 
             * } } }
             */

    /* *//** 处理单品 */
    /*
     * public boolean handleProduct(ShopCar shopCar, CarProduct cp) { return false; }
     * 
     *//** 处理套装 */

    /*
     * public boolean handleProduct(ShopCar shopCar, CompositionCarProduct ccp) { return false; }
     * 
     *//** 处理赠品加价购 *//*
                     * public boolean handleGiftProduct(ShopCar shopCar, CarProduct cp) { return
                     * false; }
                     * 
                     * public static List<CarProduct> toList(Map<Long, CarProduct> giftMap) {
                     * List<CarProduct> list = new ArrayList<CarProduct>(); Iterator<CarProduct> ite
                     * = giftMap.values().iterator(); while (ite.hasNext()) { list.add(ite.next());
                     * } return list; }
                     */

    public static List<OrderVo> getShopCarOrderList(ShopCart shopCart) {
        List<OrderVo> result = new ArrayList<OrderVo>();
        Map<String, ShopCartProduct> productMap = shopCart.getProductMap();
        OrderVo vo;
        BigDecimal pageageMoney = BigDecimal.ZERO;// 套餐金额
        for (Map.Entry<String, ShopCartProduct> entry : productMap.entrySet()) {
            ShopCartProduct carproduct = entry.getValue();
            if (entry.getKey().startsWith("n_")) {
                vo = new OrderVo();
                vo.setProductSkuCode(carproduct.getProductSkuCode());
                vo.setProductPrice(carproduct.getFinalPrice());
                vo.setProductNumber(Integer.parseInt(carproduct.getAmount().toString()));
                vo.setProductType(CouponProductType.SINGLE.getKey());
                vo.setProductTotlePrice(
                        carproduct.getAmount().multiply(carproduct.getFinalPrice()));
                result.add(vo);
            } else {// 套装
                pageageMoney = pageageMoney.add(carproduct.getProductPriceTotal());
                BigDecimal ccpOrgPrice = BigDecimal.ZERO;
                for (NormalCartProduct cp : carproduct.getProductList()) {
                    ccpOrgPrice = ccpOrgPrice
                            .add(cp.getFinalPrice().multiply(new BigDecimal(cp.getAmount())));
                }
                for (NormalCartProduct cp : carproduct.getProductList()) {
                    vo = new OrderVo();
                    vo.setRelationId(new BigDecimal(carproduct.getId()));
                    vo.setRelationPrice(
                            carproduct.getFinalPrice().multiply(carproduct.getAmount()));
                    vo.setProductSkuCode(cp.getProductSkuCode());
                    vo.setProductPrice(cp.getFinalPrice().multiply(carproduct.getFinalPrice())
                            .setScale(2, BigDecimal.ROUND_HALF_UP)
                            .divide(ccpOrgPrice, BigDecimal.ROUND_HALF_UP));
                    vo.setProductNumber(cp.getAmount());
                    vo.setProductType(CouponProductType.INPACKAGE.getKey());
                    vo.setProductTotlePrice(new BigDecimal(cp.getAmount())
                            .multiply(carproduct.getAmount()).multiply(vo.getProductPrice()));
                    result.add(vo);
                }
            }
        }
        return result;
    }
}
