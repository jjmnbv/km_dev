package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.LoginDao;
import com.kmzyc.b2b.dao.OrderItemDao;
import com.kmzyc.b2b.dao.ProductRelationDao;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.ProductRelation;
import com.kmzyc.b2b.model.SupplierOrderItem;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.OrderItemService;
import com.kmzyc.b2b.service.ProductSkuService;
import com.kmzyc.b2b.service.ProductStockService;
import com.kmzyc.b2b.shopcart.vo.CartProduct;
import com.kmzyc.b2b.shopcart.vo.Gift;
import com.kmzyc.b2b.shopcart.vo.NormalCartProduct;
import com.kmzyc.b2b.shopcart.vo.ShopCart;
import com.kmzyc.b2b.shopcart.vo.ShopCartItem;
import com.kmzyc.b2b.shopcart.vo.ShopCartProduct;
import com.kmzyc.b2b.vo.PayMoneyPresell;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.optimization.vo.Promotion;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;
import com.pltfm.app.entities.OrderItemExt;
import com.pltfm.app.entities.OrderMainExt;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.OrderPreferentialVO;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    // private static Logger log = Logger.getLogger(OrderItemServiceImpl.class);
    private static Logger log = LoggerFactory.getLogger(OrderItemServiceImpl.class);
    @Resource(name = "orderItemDaoImpl")
    private OrderItemDao orderItemDao;
    @Resource(name = "productRelationDao")
    private ProductRelationDao productRelationDao;
    @Resource(name = "productSkuServiceImpl")
    private ProductSkuService productSkuService;
    @Resource(name = "productStockServiceImpl")
    private ProductStockService productStockService;
    @Resource(name = "loginDaoImp")
    private LoginDao loginDao;
    private static final BigDecimal TIMES_CREDIT_RATIO = new BigDecimal(3);// 时代积分倍率

    // private static final String secretKey = ConfigurationUtil.getString("spreadRuleKey");

    @Override
    public OrderItem findById(Long orderItemId) throws ServiceException {

        try {
            return orderItemDao.findById(orderItemId);
        } catch (Exception e) {
            throw new ServiceException(0, "查询订单项失败:订单项号" + orderItemId, e);
        }
    }

    @Override
    public OrderItem findByIdForReturnShop(Long orderItemId) throws ServiceException {

        try {
            return orderItemDao.findByIdForReturnShop(orderItemId);
        } catch (Exception e) {
            throw new ServiceException(0, "查询订单项失败:订单项号" + orderItemId, e);
        }
    }

    /**
     * 查询订单总积分
     *
     * @author km-张文平
     */
    @Override
    public double getTotalCredits(String orderCode) throws ServiceException {
        try {
            return this.orderItemDao.getTotalCredits(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询订单总积分错误", e);
        }
    }

    @Override
    public List<OrderItem> findByOrderCode(String orderCode) throws ServiceException {

        try {
            return orderItemDao.findByOrderCode(orderCode);
        } catch (Exception e) {
            throw new ServiceException(0, "查询订单项失败:订单编码" + orderCode, e);
        }
    }

    @Override
    public Long selectOverplusNum(Long orderItemId) throws ServiceException {

        try {
            return orderItemDao.selectOverplusNum(orderItemId);
        } catch (Exception e) {
            throw new ServiceException(0, "查询商品未退换数量失败" + orderItemId, e);
        }
    }

    @Override
    public List<ProductRelation> querySuitIdByOrderCode(String orderCode) throws ServiceException {

        List<ProductRelation> result = new ArrayList<>();
        try {
            List<Long> suitIdList = orderItemDao.querySuitIdByOrderCode(orderCode);

            for (Long l : suitIdList) {
                ProductRelation productRelation = this.productRelationDao.findById(l);
                if (productRelation.getStatus() == 4L) {
                    result.add(productRelation);
                    return result;
                }
            }
        } catch (Exception e) {
            throw new ServiceException(0, "查询订单套餐ID失败" + orderCode, e);
        }
        return result;
    }

    @Override
    public List<OrderItem> findByOrderCodeWithoutGiftProduct(String orderCode) throws
            ServiceException {

        try {
            return this.orderItemDao.findByOrderCodeWithoutGiftProduct(orderCode);
        } catch (Exception e) {
            throw new ServiceException(0, "查询订单明细失败" + orderCode, e);
        }
    }

    @Override
    public List<String> queryErrProductAmountInPromotion(String orderCode) throws ServiceException {

        try {
            return this.orderItemDao.queryErrProductAmountInPromotion(orderCode);
        } catch (Exception e) {
            throw new ServiceException(0, "查询促销商品库存异常商品" + orderCode, e);
        }
    }

    @Override
    public SupplierOrderItem findSupplierOrderItem(Long orderItemId) throws ServiceException {

        try {
            List<SupplierOrderItem> sList = this.orderItemDao.findSupplierOrderItem(orderItemId);
            if (sList != null && sList.size() > 0) {
                return sList.get(0);
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new ServiceException(0, "查询促销商品库存异常商品" + orderItemId, e);
        }
    }

    /**
     * 查询产品明细库存
     */
    @Override
    public List<Long> queryOrderItemStock(String orderCode) throws ServiceException {

        try {
            return this.orderItemDao.queryOrderItemStock(orderCode);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 校验结算购物车1:changed;2:offshelf;3:outstock;4
     */
    @Override
    public Map<String, String> compareSett(ShopCart settShopCart, ShopCart shopCartCache,
                                           String outStockConfirm) throws ServiceException {
        try {
            Map<String, String> result = new HashMap<>();
            Map<String, ShopCartProduct> settScpMap, cacheScpMap;
            if (null == settShopCart || null == shopCartCache || 0 !=
                    settShopCart.getAdditionalMoney().compareTo(shopCartCache.getAdditionalMoney())
// 加价购
                    || 0 !=
                    settShopCart.getReductionMoney().compareTo(shopCartCache.getReductionMoney())
// 满减
                    || 0 !=
                    settShopCart.getCheckTotalMoney().compareTo(shopCartCache.getCheckTotalMoney())
// 活动后总计
                    || 0 != settShopCart.getUncalculateMoney()
                    .compareTo(shopCartCache.getUncalculateMoney())// 活动前总计
                    || settShopCart.getCheckTotalMoney().compareTo(BigDecimal.ZERO) <= 0 ||
                    null == (settScpMap = settShopCart.getProductMap()) ||
                    null == (cacheScpMap = shopCartCache.getProductMap()) ||
                    (null == shopCartCache.getFreight() ||
                            0 != shopCartCache.getFreight().compareTo(settShopCart.getFreight()))) {
                result.put("flag", "1");
            } else {
                Map<Long, Integer> stockMap = new HashMap<>();
                Integer stock, cacheStock;
                Long skuId;
                Map<Long, PromotionProductData> affixProductList = new HashMap<>();// 附赠集合
//                List<Long> affixSkuId = new ArrayList<Long>();// 附赠SKUID
                Map<Long, Integer> affixAmoutMap = new HashMap<>();// 附赠个数
                Map<String, Integer> giftStockMap = settShopCart.getGiftStockMap();
                Map<String, Integer> cachegiftStockMap = shopCartCache.getGiftStockMap();
                boolean hasAffix = false;
                for (Map.Entry<String, ShopCartProduct> entry : settScpMap.entrySet()) {
                    ShopCartProduct scp = entry.getValue();
                    ShopCartProduct scpCache = cacheScpMap.get(entry.getKey());
                    if (null == scp || null == scpCache || scp.getCheck() != scpCache.getCheck()) {
                        result.put("flag", "1");
                        break;
                    } else if (entry.getKey().contains("n_")) {
                        skuId = Long.parseLong(scp.getId());
                        stock = (stockMap.containsKey(skuId) ? stockMap.get(skuId) :
                                scp.getStockCount()) - scp.getAmount().intValue();
                        if (stock < 0) {
                            result.put("flag", "3");
                            result.put("msg", scp.getTitle());
                            break;
                        }
                        stockMap.put(skuId, stock);
                        if (null != scp.getProductPromotion() &&
                                null != scp.getProductPromotion().getAffixProductList() &&
                                !scp.getProductPromotion().getAffixProductList().isEmpty()) {
                            // affixProductList.addAll(scp.getProductPromotion().getAffixProductList());

                            for (PromotionProductData ppd : scp.getProductPromotion()
                                    .getAffixProductList()) {
                                affixProductList.put(ppd.getProductSkuId(), ppd);
                                ppd.setPrarentSkuId(skuId);
//                                affixSkuId.add(ppd.getProductSkuId());
                                affixAmoutMap.put(ppd.getProductSkuId(),
                                        scp.getAmount().intValue() * ppd.getNum().intValue() +
                                                (affixAmoutMap.containsKey(ppd.getProductSkuId()) ?
                                                        affixAmoutMap.get(ppd.getProductSkuId()) :
                                                        0));
                                if (!hasAffix && null != (stock = giftStockMap
                                        .get(ppd.getProductSkuId().toString())) && stock <
                                        (scp.getAmount().intValue() * ppd.getNum().intValue())) {
                                    hasAffix = true;
                                }
                            }
                            if (null == scpCache.getProductPromotion().getAffixProductList() ||
                                    scp.getProductPromotion().getAffixProductList().size() !=
                                            scpCache.getProductPromotion().getAffixProductList()
                                                    .size()) {
                                result.put("flag", "1");
                                break;
                            }
                        }
                    } else if (entry.getKey().contains("c_")) {
                        for (NormalCartProduct ncp : scp.getProductList()) {
                            stock = (stockMap.containsKey(ncp.getProductSkuId()) ?
                                    stockMap.get(ncp.getProductSkuId()) : ncp.getStockCount()) -
                                    (scp.getAmount().multiply(new BigDecimal(ncp.getAmount()))
                                            .intValue());
                            if (stock < 0) {
                                result.put("flag", "3");
                                result.put("msg", scp.getTitle());
                                break;
                            }
                            stockMap.put(ncp.getProductSkuId(), stock);
                        }
                    }
                }
                boolean needConfirm = "0".equals(outStockConfirm);
                if (result.isEmpty()) {
                    if ((null == giftStockMap && cachegiftStockMap != null) ||
                            ((null != giftStockMap && cachegiftStockMap == null))) {
                        result.put("flag", "1");
                        return result;
                    } else if (null != giftStockMap && null != cachegiftStockMap) {
                        for (Map.Entry<String, Integer> entry : giftStockMap.entrySet()) {
                            if (!cachegiftStockMap.containsKey(entry.getKey()) ||
                                    (cachegiftStockMap.get(entry.getKey()) == 0 &&
                                            !cachegiftStockMap.get(entry.getKey())
                                                    .equals(entry.getValue()))) {
                                result.put("flag", "1");
                                return result;
                            }
                        }
                        for (String key : cachegiftStockMap.keySet()) {
                            if (!giftStockMap.containsKey(key)) {
                                result.put("flag", "1");
                                return result;
                            }
                        }
                    }
                }
                if (result.isEmpty() && null != affixProductList && !affixProductList.isEmpty()) {
                    Map<String, Integer> affixStockMap = settShopCart.getGiftStockMap();

                    if (needConfirm && hasAffix &&
                            (null == affixStockMap || affixStockMap.isEmpty())) {
                        result.put("flag", "-2");
                        result.put("msg", "附赠商品库存不足");
                        return result;
                    } else if (needConfirm) {
                        Long jSkuId;
                        StringBuilder tips = new StringBuilder();
                        for (PromotionProductData ppd : affixProductList.values()) {
                            jSkuId = ppd.getProductSkuId();
                            stock = stockMap.containsKey(jSkuId) ? stockMap.get(jSkuId) :
                                    affixStockMap.get(jSkuId.toString());
                            if (!giftStockMap.containsKey(jSkuId.toString()) ||
                                    0 == giftStockMap.get(jSkuId.toString()) ||
                                    !affixAmoutMap.containsKey(jSkuId)) {
                                // mkw add 20151225 附赠商品无库存，生成订单时无提示
                                result.put("flag", "-2");
                                tips.append("附赠商品：").append(ppd.getProductTitle())
                                        .append(null == ppd.getSkuAttrValue() ? "" :
                                                "&nbsp;" + ppd.getSkuAttrValue())
                                        .append("&nbsp;库存不足<br/>");
                                // end
                                continue;
                            }
                            if (null == stock || stock < affixAmoutMap.get(jSkuId)) {
                                result.put("flag", "-2");
                                tips.append("附赠商品：").append(ppd.getProductTitle())
                                        .append(null == ppd.getSkuAttrValue() ? "" :
                                                ppd.getSkuAttrValue()).append("实际库存")
                                        .append((null == stock ? 0 : stock)).append("小于赠送数量")
                                        .append((affixAmoutMap.get(jSkuId))).append("<br/>");
                                affixAmoutMap.remove(jSkuId);
                                continue;
                            }
                            stock -= affixAmoutMap.containsKey(jSkuId) ? affixAmoutMap.get(jSkuId) :
                                    0;
                            // mkw del 20151225 附赠商品无库存，生成订单时无提示
                            // affixAmoutMap.remove(jSkuId);
                            // end
                            stockMap.put(jSkuId, stock);
                        }
                        if ("-2".equals(result.get("flag"))) {
                            result.put("msg", tips.toString());
                        }
                    }
                }
                if (result.isEmpty() && null != settShopCart.getShopCartItemMap() &&
                        !settShopCart.getShopCartItemMap().isEmpty()) {
                    String present, id;
                    for (ShopCartItem sci : settShopCart.getShopCartItemMap().values()) {
                        if (null != (present = sci.getDefaultPresents()) &&
                                null != sci.getRulePresents() &&
                                sci.getRulePresents().containsKey(present)) {
                            for (Gift g : sci.getRulePresents().get(present)) {
                                id = g.getId();
                                skuId = Long.parseLong(id);
                                stock = (stockMap.containsKey(skuId) ? stockMap.get(skuId) :
                                        (giftStockMap.containsKey(id) ? giftStockMap.get(id) : 0));
                                cacheStock = null != cachegiftStockMap ? cachegiftStockMap.get(id) :
                                        null;
                                if (needConfirm && (null == stock || stock < g.getAmount()) &&
                                        (null != cacheStock && cacheStock > g.getAmount())) {
                                    result.put("flag", "-2");
                                    result.put("msg", "赠品：" + g.getName() + "库存不足");
                                    break;
                                }
                                stockMap.put(skuId, stock - g.getAmount());
                            }
                        }
                    }
                    for (ShopCartItem sci : settShopCart.getShopCartItemMap().values()) {
                        if (null != sci.getGifts() && !sci.getGifts().isEmpty()) {
                            for (Gift g : sci.getGifts()) {
                                id = g.getId();
                                skuId = Long.parseLong(id);
                                stock = stockMap.containsKey(skuId) ? stockMap.get(skuId) :
                                        (giftStockMap.containsKey(id) ? giftStockMap.get(id) : 0);
                                if (null == stock || stock < g.getAmount()) {
                                    result.put("flag", "3");
                                    result.put("msg", "加价购：" + g.getName());
                                    break;
                                }
                                stockMap.put(skuId, stock - g.getAmount());
                            }
                        }
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 订单明细
     */
    @Override
    public void generateOrderItem(AccountInfo settAccountInfo,
                                  com.pltfm.app.entities.OrderMain orderMain, ShopCart settShopCart,
                                  List<com.pltfm.app.entities.OrderItem> oiList,
                                  List<OrderPreferentialVO> opList, BigDecimal[] moneyInfo,
                                  boolean[] identity, PayMoneyPresell payMoneyPresell) throws
            ServiceException {
        BigDecimal rate = moneyInfo[0], discount = moneyInfo[1], realMoney = moneyInfo[2], payableMoney = moneyInfo[3], depositPrice = payMoneyPresell
                .getDepositPrice(), finalPrice = payMoneyPresell.getFinalPrice();

        if (realMoney.compareTo(BigDecimal.ZERO) < 0) {
            realMoney = BigDecimal.ZERO;
        }
        if (payableMoney.compareTo(BigDecimal.ZERO) < 0) {
            payableMoney = BigDecimal.ZERO;
        }
        if (null == depositPrice || depositPrice.compareTo(BigDecimal.ZERO) < 0) {
            depositPrice = BigDecimal.ZERO;
        }
        if (null == finalPrice || finalPrice.compareTo(BigDecimal.ZERO) < 0) {
            finalPrice = BigDecimal.ZERO;
        }
        boolean isEnterSale = identity[0], isTimeLogin = identity[1], isTimesShop = identity[2];
        List<Long> skuIds = new ArrayList<>(), promotionSkuIds = new ArrayList<>();
        List<Long> couponIds = new ArrayList<>(), affixSkuId = new ArrayList<>();// 产品|套餐|加价购SKU/加价购|赠品SKU|附赠/附赠
        Map<String, ShopCartProduct> scpMap = settShopCart.getProductMap();
        Map<Long, List<PromotionProductData>> affixMap = new HashMap<>();
        Map<Long, BigDecimal> affixNumMap = new HashMap<>();// 附赠产品数量
        Map<Long, String> skuCodeMap = new HashMap<>();// 普通SKUCODE集合
        Long skuId, pSkuId;// SKUID 推广者ID 附赠产品SKUID 附赠数量
        List<PromotionProductData> temp;
        for (Map.Entry<String, ShopCartProduct> entry : scpMap.entrySet()) {
            ShopCartProduct scp = entry.getValue();
            if (entry.getKey().contains("n_")) {
                skuId = Long.parseLong(scp.getId());
                skuIds.add(skuId);
                skuCodeMap.put(skuId, scp.getProductSkuCode());
                if (null != scp.getProductPromotion() &&
                        null != (temp = scp.getProductPromotion().getAffixProductList()) &&
                        !temp.isEmpty()) {
                    affixMap.put(skuId, temp);
                    for (PromotionProductData ppd : temp) {
                        ppd.setPrarentSkuId(skuId);
                        pSkuId = ppd.getProductSkuId();
                        promotionSkuIds.add(pSkuId);
                        skuIds.add(pSkuId);
                        affixSkuId.add(pSkuId);
                        if (affixNumMap.containsKey(pSkuId)) {
                            affixNumMap.put(pSkuId,
                                    scp.getAmount().multiply(new BigDecimal(ppd.getNum()))
                                            .add(affixNumMap.get(pSkuId)));
                        } else {
                            affixNumMap.put(pSkuId,
                                    scp.getAmount().multiply(new BigDecimal(ppd.getNum())));
                        }
                    }
                }
            } else if (entry.getKey().contains("c_")) {
                for (NormalCartProduct ncp : scp.getProductList()) {
                    skuIds.add(ncp.getProductSkuId());
                }
            }
        }
        String present;
        if (null != settShopCart.getShopCartItemMap() &&
                !settShopCart.getShopCartItemMap().isEmpty()) {
            for (ShopCartItem sci : settShopCart.getShopCartItemMap().values()) {
                if (null != sci.getGifts() && !sci.getGifts().isEmpty()) {
                    for (Gift g : sci.getGifts()) {
                        skuIds.add(Long.parseLong(g.getId()));
                        promotionSkuIds.add(Long.parseLong(g.getId()));
                    }
                } else if (null != (present = sci.getDefaultPresents()) &&
                        null != sci.getRulePresents() &&
                        sci.getRulePresents().containsKey(present)) {
                    for (Gift g : sci.getRulePresents().get(present)) {
                        skuIds.add(Long.parseLong(g.getId()));
                        promotionSkuIds.add(Long.parseLong(g.getId()));
                    }
                } else if (null != sci.getCoupon()) {
                    couponIds.add(Long.parseLong(sci.getCoupon().getId()));
                }
            }
        }
        Map<Long, BigDecimal> wareMap = productStockService.queryWarehouseId(skuIds);
        Map<String, BigDecimal> comRatio = productSkuService.queryBatchComRatio(skuIds);

        String userBindType = null;

        Map<Long, CartProduct> promotionProducts = promotionSkuIds.isEmpty() ? null :
                productSkuService.queryPromotionProducts(promotionSkuIds);
        Map<String, Integer> affixStockMap = settShopCart.getGiftStockMap();

        OrderPreferentialVO op;
        Promotion pp;
        CartProduct cp;
        List<Long> promIds = new ArrayList<>();// 推广者
        com.pltfm.app.entities.OrderItem item = null;
        OrderItemExt oie;
        BigDecimal orderPvCredit = BigDecimal.ZERO, unitIncoming, incomingSum = BigDecimal.ZERO;
        int idx = 0, size = skuIds.size();
        Map<String, CartProduct> cpMap = settShopCart.getCartProductMap();
        BigDecimal suitPriceSum, suitPvCount, amount, orderCommission = BigDecimal.ZERO; // 套餐产品累加价格、套餐内pv累计、套餐内收益累计、购买数量、订单返利、返利比率
        Map<Long, Integer> stockMap = new HashMap<>();
        Integer stock;
        boolean isMedicInOrderItem = false;// 是否有药品
        for (Map.Entry<String, ShopCartProduct> entry : scpMap.entrySet()) {
            ShopCartProduct scp = entry.getValue();
            if (entry.getKey().contains("n_")) {
                cp = cpMap.get(entry.getKey());
                skuId = Long.parseLong(scp.getId());
                if (!wareMap.containsKey(skuId)) {
                    log.warn("仓库" + wareMap + "没有" + skuId);
                    continue;
                }
                item = new com.pltfm.app.entities.OrderItem();
                item.setWarehouseId(wareMap.get(skuId));
                item.setCommodityName(null == scp.getName() ?
                        (scp.getTitle().length() > 50 ? scp.getTitle().substring(0, 50) :
                                scp.getTitle()) : scp.getName());
                item.setCommodityCode(cp.getProductNo());
                item.setSkuId(skuId);
                item.setCommoditySku(scp.getProductSkuCode());
                item.setCommodityCalledPrice(cp.getPrice());
                item.setCommodityCostPrice(cp.getCostPrice());
                item.setProductSkuId(cp.getProductSkuId());
                item.setSkuBarCode(cp.getSkuBarCode());

                item.setCommodityNumber(Long.parseLong("" + scp.getAmount()));

                item.setCommodityCalledSum(cp.getPrice().multiply(scp.getAmount()));
                item.setCommodityDescription(cp.getProductDesc());
                item.setImageUrl(scp.getImagePath());
                item.setCommodityUnitWeight(cp.getUnitWeight());
                item.setCommodityType(1L);// 普通产品
                item.setCommodityTitle(scp.getTitle());
                item.setCommoditySkuDescription(
                        null != cp.getSkuAttrValue() && cp.getSkuAttrValue().length() > 66 ?
                                cp.getSkuAttrValue().substring(0, 66) : cp.getSkuAttrValue());
                item.setCommodityBrand(cp.getBrandName());
                item.setErpProCode(scp.getErpProCode());
                item.setErpSysCode(cp.getErpSysCode());
                item.setSupplierType(
                        null != cp.getSupplierType() ? cp.getSupplierType().longValue() : null);

                if (!isTimeLogin && null != cp.getCredits()) {
                    // if (scp.getPvalue() != null) {
                    // item.setCredits(cp.getCredits().longValue()
                    // + scp.getPvalue().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
                    // orderPvCredit = orderPvCredit.add(scp.getPvalue().multiply(scp.getAmount()));
                    // } else {
                    item.setCredits(cp.getCredits().longValue());
                    // }
                }
                // 遵循"四舍六入，逢五奇进偶舍"
                idx++;

                if (null != orderMain.getOrderType() && orderMain.getOrderType() == 7L) {
                    // 预售订单
                    item.setDeposit(depositPrice);
                    item.setCommodityUnitIncoming(depositPrice.add(finalPrice));
                    item.setPresellId(payMoneyPresell.getPresellId());

                    item.setCommodityUnitPrice(depositPrice.add(finalPrice));
                    item.setCommoditySum(depositPrice.add(finalPrice).multiply(scp.getAmount()));

                    item.setActualMoney(item.getCommodityUnitIncoming());
                } else {
                    if (idx == size) {
                        item.setCommodityUnitIncoming(realMoney.subtract(incomingSum)
                                .divide(scp.getAmount(), 5, BigDecimal.ROUND_HALF_UP));
                        if (item.getCommodityUnitIncoming().compareTo(BigDecimal.ZERO) < 0) {
                            item.setCommodityUnitIncoming(BigDecimal.ZERO);
                        }
                        item.setActualMoney(realMoney.subtract(incomingSum));
                    } else {
                        unitIncoming = scp.getFinalPrice().multiply(rate)
                                .setScale(2, BigDecimal.ROUND_HALF_EVEN).multiply(discount);
                        item.setCommodityUnitIncoming(unitIncoming);
                        if (item.getCommodityUnitIncoming().compareTo(BigDecimal.ZERO) < 0) {
                            item.setCommodityUnitIncoming(BigDecimal.ZERO);
                        }
                        item.setActualMoney(
                                item.getCommodityUnitIncoming().multiply(scp.getAmount()));
                    }

                    item.setCommodityUnitPrice(scp.getFinalPrice());
                    item.setCommoditySum(scp.getProductPriceTotal());
                }
                incomingSum = incomingSum.add(item.getActualMoney());

                if (scp.getPvalue() != null && scp.getPvalue().compareTo(BigDecimal.ZERO) > 0) {
                    item.setCommodityPv(scp.getPvalue().floatValue());
                } else {
                    item.setCommodityPv(0f);
                }

                if (isTimeLogin) {
                    item.setCostIncomeRatio(
                            cp.getCostIncomeRatio() != null ? cp.getCostIncomeRatio().floatValue() :
                                    null);
                    item.setCostIncomeMoney(isTimesShop ? item.getCommodityUnitIncoming() :
                            (null == item.getCommodityPv() ? BigDecimal.ZERO :
                                    new BigDecimal(item.getCommodityPv())
                                            .multiply(TIMES_CREDIT_RATIO)));
                } else {
                    item.setCostIncomeRatio(0f);
                    item.setCostIncomeMoney(new BigDecimal(0));
                }

                oie = new OrderItemExt();
                oie.setInvoicingCode(cp.getJxcCode());

                item.setOrderItemExt(oie);
                item.setSupplier(
                        null == cp.getSupplierCode() ? null : cp.getSupplierCode().toString());
                if (isEnterSale && BigDecimal.ZERO.compareTo(payableMoney) < 0 &&
                        null != comRatio && comRatio.containsKey(cp.getProductNo())) {
                    item.setCommissionRate(comRatio.get(cp.getProductNo()));

                    BigDecimal pvTotal = null == item.getCommodityPv() ? BigDecimal.ZERO :
                            new BigDecimal(item.getCommodityPv())
                                    .multiply(new BigDecimal(item.getCommodityNumber()));

                    if (null != orderMain.getOrderType() && orderMain.getOrderType() == 7L) {
                        // 应结货款=预售价*数量*(1-佣金比例)
                        BigDecimal settlementLoan = ((depositPrice.add(finalPrice))
                                .multiply(new BigDecimal(item.getCommodityNumber()))
                                .multiply(BigDecimal.ONE.subtract(comRatio.get(cp.getProductNo()))))
                                .subtract(pvTotal).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                        item.setSettlementLoan(settlementLoan);
                    } else {

                        //应结货款 =实付金额（总额+加价购-满减-优惠券） * 单品总价（金额*数量） *（1-佣金比例） / 余额支付金额 - PV总值（单品PV*购买数量）
                        BigDecimal settlementLoan = realMoney.multiply(scp.getProductPriceTotal())
                                .multiply(BigDecimal.ONE.subtract(comRatio.get(cp.getProductNo())))
                                .divide(payableMoney, 3).subtract(pvTotal)
                                .setScale(2, BigDecimal.ROUND_HALF_EVEN);
                        item.setSettlementLoan(settlementLoan);
                    }
                }
                if (null != cp.getProductType() &&
                        OrderDictionaryEnum.OrderItemExtAttrType.includeMedic.getKey() ==
                                cp.getProductType()) {
                    isMedicInOrderItem = true;
                    item.setExtAttrType(
                            (long) OrderDictionaryEnum.OrderItemExtAttrType.includeMedic.getKey());
                }
                stockMap.put(skuId,
                        (stockMap.containsKey(skuId) ? stockMap.get(skuId) : scp.getStockCount()) -
                                scp.getAmount().intValue());
                oiList.add(item);

                if (null != scp.getProductPromotion() &&
                        null != (pp = scp.getProductPromotion().getPricePromotionForChannel())) {
                    op = new OrderPreferentialVO();
                    op.setOrderPreferentialCode(String.valueOf(pp.getId()));
                    op.setOrderPreferentialSum((cp.getPrice().subtract(scp.getFinalPrice()))
                            .multiply(scp.getAmount()));
                    op.setOrderPreferentialType((long) pp.getType());
                    op.setPreferentialSKU(scp.getProductSkuCode());
                    opList.add(op);// 产品级活动

                }

            } else if (entry.getKey().contains("c_")) {
                suitPriceSum = BigDecimal.ZERO;
                suitPvCount = BigDecimal.ZERO;
                for (NormalCartProduct ncp : scp.getProductList()) {
                    suitPriceSum = suitPriceSum
                            .add(ncp.getFinalPrice().multiply(new BigDecimal(ncp.getAmount()))
                                    .multiply(scp.getAmount()));
                }
                for (int i = 0, len = scp.getProductList().size(); i < len; i++) {
                    NormalCartProduct ncp = scp.getProductList().get(i);
                    skuId = ncp.getProductSkuId();
                    if (!wareMap.containsKey(skuId)) {
                        log.warn("仓库" + wareMap + "没有" + skuId);
                        continue;
                    }
                    amount = new BigDecimal(ncp.getAmount()).multiply(scp.getAmount());
                    item = new com.pltfm.app.entities.OrderItem();
                    item.setWarehouseId(wareMap.get(skuId));
                    item.setCommodityName(ncp.getName() == null ?
                            (ncp.getTitle().length() > 50 ? ncp.getTitle().substring(0, 50) :
                                    ncp.getTitle()) : ncp.getName());
                    item.setCommodityCode(ncp.getProductNo());
                    item.setSkuId(skuId);
                    item.setCommoditySku(ncp.getProductSkuCode());

                    item.setSkuBarCode(ncp.getSkuBarCode());

                    item.setProductSkuId(ncp.getProductSkuId());
                    item.setCommodityCalledPrice(ncp.getPrice());
                    item.setCommodityCostPrice(ncp.getCostPrice());
                    item.setCommodityUnitPrice(
                            ncp.getFinalPrice().multiply(scp.getProductPriceTotal())
                                    .divide(suitPriceSum, 5, BigDecimal.ROUND_HALF_UP));
                    item.setCommodityNumber(amount.longValue());
                    item.setCommoditySum(ncp.getFinalPrice().multiply(amount));
                    item.setCommodityCalledSum(ncp.getPrice().multiply(amount));
                    item.setCommodityDescription(ncp.getProductDesc());
                    item.setImageUrl(ncp.getImagePath());
                    item.setSuitId(Long.parseLong(scp.getId()));
                    item.setCommodityUnitWeight(ncp.getUnitWeight());
                    item.setCommodityType(3L);// 套餐附属商品
                    item.setCommodityTitle(ncp.getTitle());
                    item.setCommoditySkuDescription(
                            null != ncp.getSkuAttrValue() && ncp.getSkuAttrValue().length() > 66 ?
                                    ncp.getSkuAttrValue().substring(0, 66) : ncp.getSkuAttrValue());
                    item.setSupplier(
                            null == ncp.getSellerId() ? null : ncp.getSellerId().toString());
                    item.setErpProCode(ncp.getErpProCode());
                    item.setErpSysCode(ncp.getErpSysCode());
                    idx++;
                    if (idx == size) {
                        item.setCommodityUnitIncoming(realMoney.subtract(incomingSum)
                                .divide(amount, 5, BigDecimal.ROUND_HALF_UP));
                        if (item.getCommodityUnitIncoming().compareTo(BigDecimal.ZERO) < 0) {
                            item.setCommodityUnitIncoming(BigDecimal.ZERO);
                        }
                        item.setActualMoney(realMoney.subtract(incomingSum));
                    } else {
                        item.setCommodityUnitIncoming(
                                item.getCommodityUnitPrice().multiply(rate).multiply(discount));
                        if (item.getCommodityUnitIncoming().compareTo(BigDecimal.ZERO) < 0) {
                            item.setCommodityUnitIncoming(BigDecimal.ZERO);
                        }
                        item.setActualMoney(item.getCommodityUnitIncoming().multiply(amount));
                    }
                    incomingSum = incomingSum.add(item.getActualMoney());

                    if (ncp.getPvalue() != null && BigDecimal.ZERO.compareTo(ncp.getPvalue()) < 0) {
                        if (i != len - 1) {
                            item.setCommodityPv(
                                    ncp.getPvalue().setScale(0, BigDecimal.ROUND_HALF_UP)
                                            .floatValue());
                            suitPvCount = suitPvCount
                                    .add(new BigDecimal(item.getCommodityPv()).multiply(amount));
                        } else {
                            item.setCommodityPv(
                                    scp.getPvalue().multiply(scp.getAmount()).subtract(suitPvCount)
                                            .divide(amount, BigDecimal.ROUND_HALF_UP)
                                            .setScale(0, BigDecimal.ROUND_HALF_UP).floatValue());
                        }
                    } else {
                        item.setCommodityPv(0f);
                    }

                    if (ncp.getCostIncomeRatio() != null) {
                        item.setCostIncomeRatio(ncp.getCostIncomeRatio().floatValue());
                    }
                    if (isTimesShop) {
                        item.setCostIncomeMoney(item.getCommodityUnitIncoming());
                    } else if (isTimeLogin) {
                        item.setCostIncomeMoney(null == item.getCommodityPv() ? BigDecimal.ZERO :
                                new BigDecimal(item.getCommodityPv()).multiply(TIMES_CREDIT_RATIO));
                    }
                    oie = new OrderItemExt();
                    oie.setInvoicingCode(ncp.getJxcCode());
                    item.setOrderItemExt(oie);

                    if (null != ncp.getSupplierType()) {
                        item.setSupplierType(ncp.getSupplierType().longValue());
                    }
                    item.setCommodityBrand(ncp.getBrandName());
                    if (!isTimeLogin && null != ncp.getCredits()) {

                        item.setCredits(ncp.getCredits().longValue());

                    }
                    if (isEnterSale && BigDecimal.ZERO.compareTo(payableMoney) < 0 &&
                            null != comRatio && comRatio.containsKey(ncp.getProductNo())) {
                        item.setCommissionRate(comRatio.get(ncp.getProductNo()));
                        BigDecimal pvTotal = null == item.getCommodityPv() ? BigDecimal.ZERO :
                                new BigDecimal(item.getCommodityPv())
                                        .multiply(new BigDecimal(item.getCommodityNumber()));
                        item.setSettlementLoan(item.getCommodityUnitIncoming().multiply(amount)
                                .multiply(BigDecimal.ONE.subtract(comRatio.get(ncp.getProductNo())))
                                .subtract(pvTotal).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                    }
                    if (null != ncp.getProductType() &&
                            OrderDictionaryEnum.OrderItemExtAttrType.includeMedic.getKey() ==
                                    ncp.getProductType()) {
                        item.setExtAttrType(2L);
                        isMedicInOrderItem = true;
                    }
                    stockMap.put(skuId, (stockMap.containsKey(skuId) ? stockMap.get(skuId) :
                            ncp.getStockCount()) - amount.intValue());
                    oiList.add(item);
                }
            }
        }
        Map<String, Integer> giftStockMap = settShopCart.getGiftStockMap();
        if (null != affixStockMap && !affixStockMap.isEmpty()) {
            for (Map.Entry<Long, List<PromotionProductData>> entry : affixMap.entrySet()) {
                List<PromotionProductData> affixList = entry.getValue();
                for (PromotionProductData ppd : affixList) {
                    skuId = ppd.getProductSkuId();
                    if (null == (cp = promotionProducts.get(skuId))) {
                        log.warn("附赠产品" + promotionProducts + "没有" + skuId);
                        continue;
                    }
                    if (!giftStockMap.containsKey(skuId.toString()) ||
                            0 == giftStockMap.get(skuId.toString())) {
                        continue;
                    }
                    if (!wareMap.containsKey(skuId)) {
                        log.warn("仓库" + wareMap + "没有" + skuId);
                        continue;
                    }
                    stock = stockMap.containsKey(skuId) ? stockMap.get(skuId) :
                            affixStockMap.get(skuId.toString());
                    amount = affixNumMap.get(skuId);
                    if (null == stock || stock == 0) {
                        continue;
                    }
                    if (null != amount) {
                        if (stock < amount.intValue()) {
                            log.info(settAccountInfo.getAccountLogin() + "结算时附赠产品" + skuId + "库存" +
                                    stock + "小于购买数量" + amount.toString() + "，实际赠送" + stock);
                            amount = new BigDecimal(stock);
                            stock = 0;
                        } else {
                            stock = stock - amount.intValue();
                        }
                        item = new com.pltfm.app.entities.OrderItem();
                        item.setWarehouseId(wareMap.get(skuId));
                        item.setCommodityName(cp.getName() == null ?
                                (cp.getTitle().length() > 50 ? cp.getTitle().substring(0, 50) :
                                        cp.getTitle()) : cp.getName());
                        item.setCommodityCode(cp.getProductNo());
                        item.setCommoditySku(cp.getProductSkuCode());
                        item.setSkuId(skuId);

                        item.setSkuBarCode(cp.getSkuBarCode());

                        item.setCommodityCalledPrice(cp.getPrice());
                        item.setCommodityCostPrice(cp.getCostPrice());
                        item.setCommodityUnitPrice(BigDecimal.ZERO);
                        item.setCommodityNumber(amount.longValue());
                        item.setCommoditySum(BigDecimal.ZERO);
                        item.setCommodityCalledSum(cp.getPrice().multiply(amount));
                        item.setCommodityDescription(cp.getProductDesc());
                        item.setErpProCode(cp.getErpProCode());
                        item.setErpSysCode(cp.getErpSysCode());
                        item.setProductSkuId(cp.getProductSkuId());
                        item.setImageUrl(cp.getImagePath());
                        item.setCommodityUnitWeight(cp.getUnitWeight());
                        item.setCommodityUnitIncoming(BigDecimal.ZERO);
                        item.setCommodityPv(0f);
                        item.setCostIncomeRatio(0f);
                        item.setCostIncomeMoney(BigDecimal.ZERO);
                        item.setCommodityBrand(cp.getBrandName());
                        item.setCredits(0L);
                        item.setCommissionRate(BigDecimal.ZERO);
                        item.setSettlementLoan(BigDecimal.ZERO);
                        item.setCommodityType(6L);// 附赠
                        item.setCommodityTitle(cp.getTitle() +
                                (null == cp.getSkuAttrValue() ? " " : cp.getSkuAttrValue()));
                        item.setCommoditySkuDescription(
                                null != cp.getSkuAttrValue() && cp.getSkuAttrValue().length() > 66 ?
                                        cp.getSkuAttrValue().substring(0, 66) :
                                        cp.getSkuAttrValue());
                        item.setSupplier(null == cp.getSupplierCode() ? null :
                                cp.getSupplierCode().toString());
                        item.setSupplierType(
                                null != cp.getSupplierType() ? cp.getSupplierType().longValue() :
                                        null);
                        oie = new OrderItemExt();
                        oie.setInvoicingCode(cp.getJxcCode());
                        item.setOrderItemExt(oie);
                        if (OrderDictionaryEnum.OrderItemExtAttrType.includeMedic.getKey() ==
                                cp.getProductType()) {
                            isMedicInOrderItem = true;
                            item.setExtAttrType(2L);
                        }
                        oiList.add(item);
                        stockMap.put(skuId, stock);
                        affixNumMap.remove(skuId);// 该附赠产品只生成一个订单明细
                    }
                    op = new OrderPreferentialVO();
                    op.setOrderPreferentialCode(ppd.getPromotionId().toString());
                    op.setOrderPreferentialType(PromotionTypeEnums.GANT.getValue().longValue());
                    op.setOrderPreferentialSum(item.getCommodityCalledSum());
                    op.setPreferentialSKU(skuCodeMap.get(ppd.getPrarentSkuId()));
                    opList.add(op);
                }
            }
        }
        if (null != settShopCart.getShopCartItemMap() &&
                !settShopCart.getShopCartItemMap().isEmpty()) {
            for (ShopCartItem sci : settShopCart.getShopCartItemMap().values()) {
                if (null != promotionProducts && null != sci.getGifts() &&
                        !sci.getGifts().isEmpty()) {
                    for (Gift g : sci.getGifts()) {
                        skuId = Long.parseLong(g.getId());
                        stock = giftStockMap.get(g.getId());
                        stock = null == stock ? 0 : stock;
                        if (null == (cp = promotionProducts.get(Long.parseLong(g.getId())))) {
                            log.warn("活动产品" + promotionProducts + "没有" + skuId);
                            continue;
                        }
                        if (stock - g.getAmount() < 0) {
                            continue;
                        }
                        giftStockMap.put(g.getId(), stock - g.getAmount());
                        if (!wareMap.containsKey(skuId)) {
                            log.warn("仓库" + wareMap + "没有" + skuId);
                            continue;
                        }
                        item = new com.pltfm.app.entities.OrderItem();
                        item.setWarehouseId(wareMap.get(skuId));
                        item.setCommodityName(cp.getName() == null ?
                                (cp.getTitle().length() > 50 ? cp.getTitle().substring(0, 50) :
                                        cp.getTitle()) : cp.getName());
                        item.setCommodityCode(cp.getProductNo());
                        item.setSkuId(skuId);

                        item.setSkuBarCode(cp.getSkuBarCode());

                        item.setCommoditySku(cp.getProductSkuCode());
                        item.setCommodityCalledPrice(cp.getPrice());
                        item.setCommodityCostPrice(cp.getCostPrice());
                        item.setCommodityUnitPrice(g.getPrice());
                        item.setCommodityNumber(Long.parseLong("" + g.getAmount()));
                        item.setCommoditySum(g.getPrice().multiply(new BigDecimal(g.getAmount())));
                        item.setCommodityCalledSum(
                                cp.getPrice().multiply(new BigDecimal(g.getAmount())));
                        item.setCommodityDescription(cp.getProductDesc());
                        item.setErpProCode(cp.getErpProCode());
                        item.setErpSysCode(cp.getErpSysCode());
                        item.setProductSkuId(cp.getProductSkuId());
                        item.setImageUrl(cp.getImagePath());
                        item.setCommodityUnitWeight(cp.getUnitWeight());
                        idx++;
                        if (idx == size) {
                            item.setCommodityUnitIncoming(realMoney.subtract(incomingSum)
                                    .divide(new BigDecimal(g.getAmount()), 5,
                                            BigDecimal.ROUND_HALF_UP));
                            if (item.getCommodityUnitIncoming().compareTo(BigDecimal.ZERO) < 0) {
                                item.setCommodityUnitIncoming(BigDecimal.ZERO);
                            }
                        } else {
                            item.setCommodityUnitIncoming(
                                    g.getPrice().multiply(rate).multiply(discount));
                            if (item.getCommodityUnitIncoming().compareTo(BigDecimal.ZERO) < 0) {
                                item.setCommodityUnitIncoming(BigDecimal.ZERO);
                            }
                        }
                        incomingSum = incomingSum.add(item.getCommodityUnitIncoming()
                                .multiply(new BigDecimal(g.getAmount())));

                        item.setCommodityPv(0f);
                        if (cp.getCostIncomeRatio() != null) {
                            item.setCostIncomeRatio(cp.getCostIncomeRatio().floatValue());
                        }
                        if (isTimesShop) {
                            item.setCostIncomeMoney(item.getCommodityUnitIncoming());
                        } else if (isTimeLogin) {
                            item.setCostIncomeMoney(
                                    null == item.getCommodityPv() ? BigDecimal.ZERO :
                                            new BigDecimal(item.getCommodityPv())
                                                    .multiply(TIMES_CREDIT_RATIO));
                        }

                        oie = new OrderItemExt();
                        oie.setInvoicingCode(cp.getJxcCode());

                        item.setOrderItemExt(oie);
                        item.setCommodityType(5L);// 加价购
                        item.setCommodityTitle(g.getName());
                        item.setCommoditySkuDescription(
                                null != cp.getSkuAttrValue() && cp.getSkuAttrValue().length() > 66 ?
                                        cp.getSkuAttrValue().substring(0, 66) :
                                        cp.getSkuAttrValue());
                        item.setSupplier(null == cp.getSupplierCode() ? null :
                                cp.getSupplierCode().toString());
                        if (null != cp.getSupplierType()) {
                            item.setSupplierType(cp.getSupplierType().longValue());
                        }
                        item.setCommodityBrand(cp.getBrandName());
                        if (!isTimeLogin && null != cp.getCredits()) {

                            item.setCredits(cp.getCredits().longValue());

                        }
                        if (isEnterSale && BigDecimal.ZERO.compareTo(payableMoney) < 0 &&
                                null != comRatio && comRatio.containsKey(cp.getProductNo())) {
                            item.setCommissionRate(comRatio.get(cp.getProductNo()));
                            item.setSettlementLoan(realMoney.multiply(item.getCommoditySum())
                                    .multiply(BigDecimal.ONE
                                            .subtract(comRatio.get(cp.getProductNo())))
                                    .divide(payableMoney, 3)
                                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                        }
                        if (null != cp.getProductType() &&
                                OrderDictionaryEnum.OrderItemExtAttrType.includeMedic.getKey() ==
                                        cp.getProductType()) {
                            isMedicInOrderItem = true;
                            item.setExtAttrType(2L);
                        }
                        if (stockMap.containsKey(skuId) && stockMap.get(skuId) < stock) {
                            stock = stockMap.get(skuId);
                        }
                        stockMap.put(skuId, stock - g.getAmount());
                        oiList.add(item);

                        op = new OrderPreferentialVO();
                        op.setOrderPreferentialCode(String.valueOf(sci.getPromtionInfo().getId()));
                        op.setOrderPreferentialType((long) sci.getPromtionInfo().getType());
                        op.setOrderPreferentialSum(item.getCommoditySum());
                        op.setPreferentialSKU(g.getId());
                        opList.add(op);
                    }
                } else if (null != promotionProducts &&
                        null != (present = sci.getDefaultPresents()) &&
                        null != sci.getRulePresents() &&
                        sci.getRulePresents().containsKey(present)) {
                    for (Gift g : sci.getRulePresents().get(present)) {
                        skuId = Long.parseLong(g.getId());
                        stock = giftStockMap.get(g.getId());
                        if (null == stock) {
                            stock = 0;
                        } else if (stockMap.containsKey(skuId) && stockMap.get(skuId) < stock) {
                            stock = stockMap.get(skuId);
                        }
                        if (null == (cp = promotionProducts.get(Long.parseLong(g.getId()))) ||
                                g.getAmount() > stock) {
                            log.warn("赠品" + skuId + "库存不足");
                            continue;
                        }
                        giftStockMap.put(g.getId(), stock - g.getAmount());
                        if (!wareMap.containsKey(skuId)) {
                            log.warn("仓库" + wareMap + "没有" + skuId);
                            continue;
                        }
                        item = new com.pltfm.app.entities.OrderItem();
                        item.setWarehouseId(wareMap.get(skuId));
                        item.setCommodityName(cp.getName() == null ?
                                (cp.getTitle().length() > 50 ? cp.getTitle().substring(0, 50) :
                                        cp.getTitle()) : cp.getName());
                        item.setCommodityCode(cp.getProductNo());
                        item.setCommoditySku(cp.getProductSkuCode());
                        item.setSkuId(skuId);

                        item.setSkuBarCode(cp.getSkuBarCode());

                        item.setCommodityCalledPrice(cp.getPrice());
                        item.setCommodityCostPrice(cp.getCostPrice());
                        item.setCommodityUnitPrice(BigDecimal.ZERO);
                        item.setCommodityNumber(Long.parseLong("" + g.getAmount()));
                        item.setCommoditySum(BigDecimal.ZERO);
                        item.setCommodityCalledSum(
                                cp.getPrice().multiply(new BigDecimal(g.getAmount())));
                        item.setCommodityDescription(cp.getProductDesc());
                        item.setErpProCode(cp.getErpProCode());
                        item.setErpSysCode(cp.getErpSysCode());
                        item.setProductSkuId(cp.getProductSkuId());
                        item.setImageUrl(cp.getImagePath());
                        item.setCommodityUnitWeight(cp.getUnitWeight());
                        item.setCommodityUnitIncoming(BigDecimal.ZERO);
                        item.setCommodityPv(0f);
                        item.setCostIncomeRatio(0f);
                        item.setCostIncomeMoney(BigDecimal.ZERO);
                        item.setCommodityBrand(cp.getBrandName());
                        item.setCredits(0L);
                        item.setCommissionRate(BigDecimal.ZERO);
                        item.setSettlementLoan(BigDecimal.ZERO);
                        item.setCommodityType(4L);// 赠品
                        item.setCommodityTitle(g.getName());
                        item.setCommoditySkuDescription(
                                null != cp.getSkuAttrValue() && cp.getSkuAttrValue().length() > 66 ?
                                        cp.getSkuAttrValue().substring(0, 66) :
                                        cp.getSkuAttrValue());
                        item.setSupplier(null == cp.getSupplierCode() ? null :
                                cp.getSupplierCode().toString());
                        item.setSupplierType(
                                null != cp.getSupplierType() ? cp.getSupplierType().longValue() :
                                        null);
                        oie = new OrderItemExt();
                        oie.setInvoicingCode(cp.getJxcCode());
                        item.setOrderItemExt(oie);
                        if (OrderDictionaryEnum.OrderItemExtAttrType.includeMedic.getKey() ==
                                cp.getProductType()) {
                            isMedicInOrderItem = true;
                            item.setExtAttrType(2L);
                        }
                        oiList.add(item);
                        op = new OrderPreferentialVO();
                        op.setOrderPreferentialCode(String.valueOf(sci.getPromtionInfo().getId()));
                        op.setOrderPreferentialType((long) sci.getPromtionInfo().getType());
                        op.setOrderPreferentialSum(item.getCommodityCalledSum());
                        op.setPreferentialSKU(g.getId());
                        opList.add(op);
                    }
                } else if (null != sci.getCoupon()) {
                    Integer couponAmount = sci.getCoupon().getAmount();
                    if (null == couponAmount) {
                        continue;
                    } else if (0 == couponAmount) {
                        couponAmount = 1;
                    }
                    for (int i = 0; i < couponAmount; i++) {
                        op = new OrderPreferentialVO();
                        op.setOrderPreferentialCode(String.valueOf(sci.getPromtionInfo().getId()));
                        op.setOrderPreferentialType((long) sci.getPromtionInfo().getType());
                        op.setCouponId(sci.getCoupon().getId());
                        opList.add(op);
                    }
                }
            }
        }
        List<ShopCartItem> mopl = settShopCart.getMeetOrderPromotionList();// 满减活动
        if (null != mopl && !mopl.isEmpty()) {
            for (ShopCartItem sci : mopl) {
                if (sci.getPromtionInfo().getType() != PromotionTypeEnums.REDUCTION.getValue()) {
                    continue;
                }
                op = new OrderPreferentialVO();
                op.setOrderPreferentialCode(String.valueOf(sci.getPromtionInfo().getId()));
                op.setOrderPreferentialType((long) sci.getPromtionInfo().getType());
                op.setOrderPreferentialSum(sci.getReductionMoney());
                opList.add(op);
            }
        }
        orderMain.setTotalCredit(orderPvCredit.setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
        // 药品默认审核不通过,medicFlag:1
        if (isMedicInOrderItem) {
            orderMain.setMedicFlag((long) OrderDictionaryEnum.OrderMainMedicFlag.yes.getKey());
            orderMain
                    .setCheckFlag((long) OrderDictionaryEnum.OrderMainMedicCheckFlag.fail.getKey());
        } else {
            orderMain
                    .setCheckFlag((long) OrderDictionaryEnum.OrderMainMedicCheckFlag.pass.getKey());
            orderMain.setMedicFlag((long) OrderDictionaryEnum.OrderMainMedicFlag.no.getKey());
        }
        try {
            OrderMainExt orderMainExt = orderMain.getOrderMainext();
            orderMainExt.setOrderOutSource(userBindType);
            orderMainExt.setOrderCommission(orderCommission);
            if (!promIds.isEmpty()) {

                User userOrganInfo = loginDao.selectPromOrganInfo(promIds);
                if (userOrganInfo != null) {
                    orderMainExt.setPromId(userOrganInfo.getPromId()); // 推广者
                    orderMainExt.setOrganCode(userOrganInfo.getOrganCode());// 设置用户组织机构
                    orderMainExt.setOrganDes(userOrganInfo.getOrganDes());// 设置用户组织机构描述
                } else {
                    orderMainExt.setPromId(promIds.get(0)); // 推广者
                    orderMainExt.setOrganCode(settAccountInfo.getOrganCode());
                    orderMainExt.setOrganDes(settAccountInfo.getOrganDes());
                }
            }
        } catch (Exception e) {
            log.error(orderMain.getCustomerId() + "结算设置组织机构发生异常", e);
        }
    }

    /**
     * 校验加密后的推广规则(用于校验)
     */
    /*private boolean checkSpreadParameter(String spreadRuleKey) {
        try {
            if (StringUtil.isEmpty(ConfigurationUtil.getString("spreadRuleKey"))) {
                return false;
            }
            String[] spreadRuleKeys = spreadRuleKey.split(",");
            if (null == spreadRuleKeys && spreadRuleKeys.length != 4) {
                return false;
            }

            String par =
                    "cpId=" + spreadRuleKeys[0] + ",spreadId=" + spreadRuleKeys[2] + ",secretKey="
                            + ConfigurationUtil.getString("spreadRuleKey");
            String secretPar = MD5.getMD5Str(par);
            if (secretPar.equals(spreadRuleKeys[3])) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }*/
    @Override
    public Integer queryUserBuyPromotionNumForPressell(Long userId, Long skuId,
                                                       String pressellId) throws SQLException {

        return this.orderItemDao.queryUserBuyPromotionNumForPressell(userId, skuId, pressellId);
    }
}
