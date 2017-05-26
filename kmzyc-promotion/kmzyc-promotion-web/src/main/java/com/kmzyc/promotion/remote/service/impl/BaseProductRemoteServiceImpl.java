package com.kmzyc.promotion.remote.service.impl;

import java.math.BigDecimal;
import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.km.framework.page.Pagination;
import com.kmzyc.promotion.app.dao.BaseProductDao;
import com.kmzyc.promotion.app.dao.CouponDAO;
import com.kmzyc.promotion.app.dao.PromotionInfoDao;
import com.kmzyc.promotion.app.dao.PromotionProductDAO;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.service.CompositionService;
import com.kmzyc.promotion.app.service.ProductSkuService;
import com.kmzyc.promotion.app.service.PromotionInfoService;
import com.kmzyc.promotion.app.service.PromotionRuleDataService;
import com.kmzyc.promotion.app.vobject.BaseProduct;
import com.kmzyc.promotion.app.vobject.Composition;
import com.kmzyc.promotion.app.vobject.PriceInfo;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.optimization.vo.Promotion;
import com.kmzyc.promotion.remote.service.BaseProductRemoteService;
import com.kmzyc.promotion.util.PromotionCacheUtil;
import com.kmzyc.promotion.util.UserChannelContextHolder;

@Service("baseProductRemoteService")
public class BaseProductRemoteServiceImpl implements BaseProductRemoteService {
    private Logger logger = LoggerFactory.getLogger(BaseProductRemoteServiceImpl.class);
    @Resource
    private PromotionProductDAO promotionProductDAO;
    @Resource
    private BaseProductDao baseProductDao;
    @Resource
    private PromotionInfoDao promotionInfoDao;
    @Resource
    private PromotionInfoService promotionInfoService;
    @Resource
    private CompositionService compositionService;
    @Resource
    private PromotionRuleDataService promotionRuleDataService;
    @Resource
    private CouponDAO coupondao;

    /**
     * 获取加价购商品价格
     * 
     * @param promotionId活动ID
     * @return
     * @throws Exception
     */
    @Override
    public List<BaseProduct> getAddPriceCarProductInfoList(Long promotionId) throws Exception {
        return baseProductDao.getAddPriceCarProductInfoList(promotionId);
    }

    /**
     * 参加某活动的商品
     * 
     * @param page
     * @param promotion
     * @return
     * @throws Exception
     */
    @Override
    public Pagination getAppActivityProductList(Pagination page, PromotionInfo promotion)
            throws Exception {
        return baseProductDao.getPromotionProduct(page, promotion);
    }

    /**
     * 获取活动产品列表
     * 
     * @param page
     * @param promotion
     * @return
     * @throws Exception
     */
    @Override
    public Pagination getAppPromotionProductList(Pagination page, PromotionInfo promotion)
            throws Exception {
        return baseProductDao.getPromotionProduct(page, promotion);
    }

    /**
     * 获取赠品
     * 
     * @param promotionId活动ID
     * @return
     * @throws Exception
     */
    @Override
    public List<BaseProduct> getGiftProduct(Long promotionId) throws Exception {
        return baseProductDao.getGiftProduct(promotionId);
    }

    /**
     * 获取特价产品价格和限购信息
     * 
     * @param skuId产品skuId
     * @param promotionId活动ID
     * @return
     * @throws Exception
     */
    @Override
    public PromotionProduct getPromotionProductPrice(Long skuId, Long promotionId)
            throws Exception {
        try {
            return promotionProductDAO.getPromotionProductPrice(skuId, promotionId);
        } catch (Exception e) {
            logger.error("获取特价产品价格和限购信息发生异常", e);
            throw new Exception(e);
        }
    }

    @Override
    public JSONObject getcalcPrice(Long uid, JSONObject jo) throws Exception {
        if (null == jo) {
            return null;
        }

        return calcPrice4B2B(uid, jo);
    }

    /**
     * 产品计算价格
     * 
     * @param uid
     * @param jo
     * @return
     * @throws Exception
     */
    private JSONObject calcPrice4B2B(Long uid, JSONObject jo) throws Exception {
        JSONObject rs = null;
        Set<String> keySet = jo.keySet();
        if (null != keySet && !keySet.isEmpty()) {
            rs = new JSONObject();
            BigDecimal productPrice = BigDecimal.ZERO;// 总金额
            BigDecimal productPvalue = BigDecimal.ZERO;// 总PV值

            Iterator<String> keyIt = keySet.iterator();
            Map<Long, BaseProduct> amountMap = new LinkedHashMap<Long, BaseProduct>();// 根据skuid存储产品数量
            List<Long> sellerIds = new ArrayList<Long>();
            while (keyIt.hasNext()) {
                sellerIds.add(Long.parseLong(keyIt.next()));
            }
            Map<Long, List<PromotionInfo>> promotionMap =
                    promotionInfoService.queryAblePromotions(sellerIds);// 查询商家所有的活动
            // 商家价格
            BigDecimal sellerPrice = BigDecimal.ZERO, serllerTotalWeight = BigDecimal.ZERO,
                    serllerCouponMonery = BigDecimal.ZERO, sellerAdditionalMoney = BigDecimal.ZERO,
                    serllerReductionMoney = BigDecimal.ZERO, pvalue = BigDecimal.ZERO;
            Integer sellerTotalCount = 0;
            // 减免前的总金额,总重量,加价购金额,送券金额,满减金额
            BigDecimal totalOrgMoney = BigDecimal.ZERO, totalWeight = BigDecimal.ZERO,
                    additionalMoney = BigDecimal.ZERO, couponMoney = BigDecimal.ZERO,
                    reductionMoney = BigDecimal.ZERO;
            // 总数量
            Integer totalCount = 0;
            Long id = 0l;
            Integer amount = 0;
            keyIt = keySet.iterator();
            Map<Long, BaseProduct> sellerProductMap = new HashMap<Long, BaseProduct>();// 商家产品
            while (keyIt.hasNext()) {
                Boolean isChecked = Boolean.FALSE;
                String key = keyIt.next();
                JSONObject innerJo = jo.getJSONObject(key);
                // 商家活动,代销的没有单独的活动
                List<PromotionInfo> sellerPromotion =
                        promotionMap == null ? null : promotionMap.get(Long.parseLong(key));
                sellerPrice = BigDecimal.ZERO;
                serllerTotalWeight = BigDecimal.ZERO;
                serllerCouponMonery = BigDecimal.ZERO;
                pvalue = BigDecimal.ZERO;
                sellerTotalCount = 0;
                sellerAdditionalMoney = BigDecimal.ZERO;
                serllerReductionMoney = BigDecimal.ZERO;
                JSONObject inner = new JSONObject();
                // 设置产品数量Map
                Map<Long, Integer> queryMap = new HashMap<Long, Integer>();
                // 设置产品是否选中Map
                Map<Long, Boolean> dataMap = new HashMap<Long, Boolean>();
                // 处理套餐商品价格
                JSONArray comArray = innerJo.getJSONArray("comArray");
                if (null != comArray && !comArray.isEmpty()) {
                    queryMap.clear();
                    dataMap.clear();
                    for (Object temp : comArray) {
                        JSONObject comJo = (JSONObject) temp;
                        id = comJo.getLong("comId");
                        dataMap.put(id, comJo.getBoolean("isChecked"));
                        queryMap.put(id, comJo.getInteger("amount"));
                    }
                    // 批量查询套餐
                    List<Composition> comMap = compositionService.queryBacthComposition(queryMap);
                    if (null != comMap && !comMap.isEmpty()) {
                        for (int i = 0; i < comMap.size(); i++) {
                            Composition comp = comMap.get(i);
                            comp.setAmount(queryMap.get(comp.getCid()));
                            isChecked = dataMap.get(comp.getCid());
                            comp.setIsChecked(isChecked);
                            try {
                                // 设置套餐最终价格信息，上下架状态，库存数量，限购信息，购物车使用，当活动参数不为空设置活动信息
                                execComposition(comp, sellerPromotion, uid, amountMap);
                            } catch (Exception e) {
                                comp.setIsOutOfStock(true);
                                logger.error("设置套餐最终价格信息发生异常", e);
                            }
                            if (isChecked) {
                                sellerProductMap.put(comp.getCid(), comp.getMainCarProduct());
                                totalCount += comp.calcAmount();
                                totalOrgMoney = totalOrgMoney.add(comp.getFinalPrice()
                                        .multiply(new BigDecimal(comp.getAmount())));
                                productPrice = totalOrgMoney;
                                totalWeight = totalWeight.add(comp.getWeigth());
                                sellerTotalCount += comp.calcAmount();// 商家总数量
                                sellerPrice = sellerPrice.add(comp.getFinalPrice()
                                        .multiply(new BigDecimal(comp.getAmount())));// 商家总价格
                                pvalue = pvalue.add(comp.getPvalue()
                                        .multiply(new BigDecimal(comp.getAmount()))); // pv值
                                productPvalue = productPvalue.add(comp.getPvalue()
                                        .multiply(new BigDecimal(comp.getAmount())));
                                serllerTotalWeight = serllerTotalWeight.add(comp.getWeigth());// 商家总重量
                            }
                        }
                    }
                    inner.put("comArray", comMap);
                }
                // 处理普通商品
                JSONArray cpArray = innerJo.getJSONArray("cpArray");
                if (null != cpArray && !cpArray.isEmpty()) {
                    queryMap.clear();
                    dataMap.clear();
                    for (Object temp : cpArray) {
                        JSONObject cpJo = (JSONObject) temp;
                        id = cpJo.getLong("skuId");
                        amount = cpJo.getInteger("amount");
                        if (null == amount || amount < 1) {
                            amount = 1;
                        }
                        queryMap.put(id, amount);
                        dataMap.put(id, cpJo.getBoolean("isChecked"));
                    }
                    // 批量获取产品价格信息
                    List<BaseProduct> bpMap = baseProductDao
                            .queryBatchProductPriceInfo(new ArrayList<Long>(queryMap.keySet()));
                    amount = 1;
                    for (int i = 0; i < bpMap.size(); i++) {
                        BaseProduct bp = bpMap.get(i);
                        bp.setAmount(queryMap.get(bp.getProductSkuId()));
                        isChecked = dataMap.get(bp.getProductSkuId());
                        bp.setIsChecked(isChecked);
                        if (3 != bp.getProductStatus() || 1 != bp.getSkuStatus()) {
                            bp.setIsOutOfStock(true);// 产品无库存
                        }
                        try {
                            execBaseProduct(bp, sellerPromotion, uid);
                            if (isChecked) {
                                setProductAmount(amountMap, bp, true);
                            }
                        } catch (Exception e) {
                            bp.setIsOutOfStock(true);
                            logger.error("设置产品最终价格信息发生异常", e);
                        }
                        if (isChecked) {
                            sellerProductMap.put(bp.getProductSkuId(), bp);
                            amount = queryMap.get(bp.getProductSkuId());
                            totalCount += amount;
                            totalOrgMoney = totalOrgMoney
                                    .add(bp.getFinalPrice().multiply(new BigDecimal(amount)));
                            sellerTotalCount += amount;// 商家总数量
                            sellerPrice = sellerPrice
                                    .add(bp.getFinalPrice().multiply(new BigDecimal(amount)));// 商家总价格
                            pvalue = pvalue.add(bp.getPvalue().multiply(new BigDecimal(amount)));
                            serllerTotalWeight = serllerTotalWeight
                                    .add(bp.getUnitWeight().multiply(new BigDecimal(amount)));// 商家总重量
                            productPrice = totalOrgMoney; // 总金额
                            productPvalue = productPvalue
                                    .add(bp.getPvalue().multiply(new BigDecimal(amount))); // 总pv值
                            totalWeight = totalWeight
                                    .add(bp.getUnitWeight().multiply(new BigDecimal(amount)));
                        }
                    }
                    inner.put("cpArray", bpMap);
                }
                // 处理活动产品价格
                Map<Long, PromotionRuleData> ruleMap = null;
                JSONArray pmArray = innerJo.getJSONArray("pmArray");
                if (null != pmArray && !pmArray.isEmpty()) {
                    ruleMap = new HashMap<Long, PromotionRuleData>();
                    for (Object temp : pmArray) {
                        JSONObject bpJo = (JSONObject) temp;
                        PromotionRuleData prd = new PromotionRuleData();
                        id = bpJo.getLong("pid");
                        prd.setPromotionId(id);
                        prd.setEntityId(bpJo.getLong("skuId"));
                        prd.setMaxMeetData(bpJo.getBigDecimal("maxMeetData"));
                        prd.setMeetData(bpJo.getBigDecimal("meetData"));
                        prd.setPromotionRuleDataId(bpJo.getLong("rid"));
                        ruleMap.put(id, prd);
                    }
                }
                // 执行商家级活动:参数1:参加的的活动 2产品的数量 3规则
                Map<String, Object> sellerProm =
                        execSellerPromotion(sellerPromotion, sellerProductMap, ruleMap);
                sellerProductMap.clear();
                if (sellerProm != null && !sellerProm.isEmpty()) {
                    Object coupon = sellerProm.get("coupon");
                    Object reduction = sellerProm.get("reduction");
                    Object addPriceMonery = sellerProm.get("additionalMoney");
                    if (null != coupon) {
                        couponMoney = couponMoney.add(new BigDecimal(coupon.toString()));
                        serllerCouponMonery =
                                serllerCouponMonery.add(new BigDecimal(coupon.toString()));
                    }
                    if (null != reduction) {
                        reductionMoney = reductionMoney.add(new BigDecimal(reduction.toString()));
                        serllerReductionMoney =
                                serllerReductionMoney.add(new BigDecimal(reduction.toString()));
                    }
                    if (null != additionalMoney) {
                        additionalMoney =
                                additionalMoney.add(new BigDecimal(addPriceMonery.toString()));
                        sellerAdditionalMoney = sellerAdditionalMoney
                                .add(new BigDecimal(addPriceMonery.toString()));
                    }

                    inner.put("giftMap", sellerProm.get("giftMap"));
                    inner.put("meetProm", sellerProm.get("meetProm"));
                    inner.put("missProm", sellerProm.get("missProm"));
                }
                inner.put("serllerCouponMonery", serllerCouponMonery);
                inner.put("sellerAdditionalMoney", sellerAdditionalMoney);
                inner.put("serllerReductionMoney", serllerReductionMoney);
                inner.put("sellerTotalCount", sellerTotalCount);
                inner.put("serllerTotalWeight", serllerTotalWeight);
                inner.put("sellerPrice", sellerPrice);
                inner.put("pvalue", pvalue);
                rs.put(key, inner);
            }
            rs.put("couponMoney", couponMoney);
            rs.put("additionalMoney", additionalMoney);
            rs.put("reductionMoney", reductionMoney);
            rs.put("totalCount", totalCount);
            rs.put("totalWeight", totalWeight);
            rs.put("productPvalue", productPvalue);
            rs.put("finalPrice", productPrice);
            rs.put("amountMap", amountMap);

        }
        return rs;

    }

    /**
     * 设置套餐最终价格信息，上下架状态，库存数量，限购信息， 购物车使用 ，当活动参数不为空设置活动信息
     * 
     * @param comp
     * @param sellerPromotion
     * @param uid
     * @throws Exception
     */
    private void execComposition(Composition comp, List<PromotionInfo> sellerPromotion, Long uid,
            Map<Long, BaseProduct> amountMap) throws Exception {
        if (null == comp.getProductList() || comp.getProductList().isEmpty()) {
            throw new ServerException("套餐" + comp.getCid() + "没有产品");
        }
        if (comp.getIsChecked()) {
            for (BaseProduct bp : comp.getProductList()) {
                setProductAmount(amountMap, bp, false);
            }
        } else {
            for (BaseProduct bp : comp.getProductList()) {
                bp.setIsChecked(comp.getIsChecked());
            }
        }
    }

    /**
     * 设置产品最终价格信息，上下架状态，库存数量，限购信息， 购物车使用 ，当活动参数不为空设置活动信息
     * 
     * @param bp
     * @param sellerPromotion
     * @param uid
     * @throws Exception
     */
    private void execBaseProduct(BaseProduct bp, List<PromotionInfo> sellerPromotion, Long uid)
            throws Exception {
        if (sellerPromotion != null && !sellerPromotion.isEmpty()) {
            // 临时对象，避免覆盖cp对象
            BaseProduct temp = new BaseProduct();
            temp.setSellerId(bp.getSellerId());
            temp.setSupplierType(bp.getSupplierType());
            temp.setSupplierCode(bp.getSupplierCode());
            temp.setProductSkuId(bp.getProductSkuId());
            temp.setPrice(bp.getPrice());
            temp.setBrandId(bp.getBrandId());
            temp.setCategoryId(bp.getCategoryId());
            PromotionInfo outInfo = null;
            for (PromotionInfo p : sellerPromotion) {
                // 判断是否参加活动
                if (promotionInfoService.inPromotionInfoAndInitRule(temp, p)) {
                    bp.addPromotionInMap(p);
                    // 设置活动规则
                    p.getCarProductMap().put(bp.getProductSkuId().toString(), bp);
                    promotionInfoService.setPromotionInfoToBaseProduct(bp, p);
                    int type = p.getPromotionType().intValue();
                    // 处理打折特价商品
                    if ((PromotionTypeEnums.SALE.getValue() == type
                            || PromotionTypeEnums.DISCOUNT.getValue() == type)
                            && (null == outInfo || outInfo.compareTo(p) > 0)) {
                        if (temp.getMinBuy() > 0 || temp.getMaxBuy() > 0
                                || temp.getPromotionStock() > 0) {
                            outInfo = p;
                            bp.setMinBuy(temp.getMinBuy());
                            bp.setMaxBuy(temp.getMaxBuy());
                            bp.setPromotionStock(temp.getPromotionStock());
                            bp.setPromotionSell(temp.getPromotionSell());
                            bp.setPromoStartTime(p.getStartTime());
                            bp.setPromoEndTime(p.getEndTime());

                        }

                    }
                }
            }
            bp.initBaseProduct();// 设置活动优先级别
            if (null != uid && bp.getMaxBuy() > 0) {
                // 设置限购信息
                setPurchase(bp, uid);
            }
        }
    }

    /**
     * 设置已购信息
     * 
     * @param bp
     * @param salePromo
     * @param discountPromo
     */
    private void setPurchase(BaseProduct bp, Long uid) {
        if (null != bp.getPromoStartTime() && bp.getMaxBuy() > 0) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userId", uid);
            params.put("skuId", bp.getProductSkuId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (null != bp.getPromoStartTime()) {
                params.put("startDate", sdf.format(bp.getPromoStartTime()));
            }
            if (null != bp.getPromoEndTime()) {
                params.put("endDate", sdf.format(bp.getPromoEndTime()));
            }
            try {
                Integer buyNum = baseProductDao.querySumUserBuySkuNum(params);
                bp.setPromoBuyNum(bp.getMaxBuy() - buyNum);
            } catch (Exception e) {
                bp.setPromoBuyNum(0);
                logger.error("查询活动期间用户购买某产品数量发生异常", e);
            }
        }
    }

    /**
     * 设置购买数量
     * 
     * @param newMap
     * @param bp
     * @param isAddRestriction
     */
    private void setProductAmount(Map<Long, BaseProduct> newMap, BaseProduct bp,
            boolean isAddRestriction) {
        Long mainSkuId = bp.getProductSkuId();
        BaseProduct newBp = newMap.get(mainSkuId);
        Integer amount = bp.getAmount();
        if (newBp == null) {
            newBp = copyProductAcmout(bp);
            newBp.setRestrictionAmount(0);
        } else {
            newBp.setAmount(newBp.getAmount() + amount);
        }
        if (isAddRestriction) {
            newBp.setRestrictionAmount(newBp.getRestrictionAmount() + amount);
            bp.setRestrictionAmount(newBp.getRestrictionAmount());
        }
        newMap.put(mainSkuId, newBp);
    }

    /**
     * 商家活动去重排序
     * 
     * @param list
     * @return
     */
    private List<PromotionInfo> unipPromotionList(List<PromotionInfo> list) {
        List<PromotionInfo> rsList = null;
        if (list != null && !list.isEmpty()) {
            Map<Long, PromotionInfo> rsMap = new HashMap<Long, PromotionInfo>();
            for (PromotionInfo info : list) {
                Integer type = info.getPromotionType();
                if (0 == PromotionTypeEnums.SALE.getValue().compareTo(type)
                        || 0 == PromotionTypeEnums.DISCOUNT.getValue().compareTo(type)) {
                    continue;
                }
                rsMap.put(info.getPromotionId(), info);
            }
            if (null != rsMap && !rsMap.isEmpty()) {
                rsList = new ArrayList<PromotionInfo>();
                for (Iterator<PromotionInfo> pit = rsMap.values().iterator(); pit.hasNext();) {
                    rsList.add(pit.next());
                }
                Collections.sort(rsList);
            }
        }
        return rsList;
    }

    /**
     * 获取优惠券价格
     * 
     * @param couponId
     * @return
     */
    private BigDecimal getCouponMoney(Long couponId) {
        BigDecimal rs = BigDecimal.ZERO;
        try {
            rs = coupondao.getcouponMoneyByCouponId(couponId);
        } catch (Exception e) {
            logger.error("获取优惠券价格发生异常", e);
        }
        return rs;
    }

    /**
     * 执行商家级活动
     * 
     * @param shopCar
     * @param productPromotionInfo
     * @return
     */
    private Map<String, Object> execSellerPromotion(List<PromotionInfo> promList,
            Map<Long, BaseProduct> sellerProductMap, Map<Long, PromotionRuleData> pMap) {
        if (null == promList || promList.isEmpty() || null == sellerProductMap
                || sellerProductMap.isEmpty()) {
            return null;
        }
        List<PromotionInfo> uniqList = unipPromotionList(promList);
        if (null == uniqList || uniqList.isEmpty()) {
            return null;
        }
        if (null == pMap) {
            pMap = new HashMap<Long, PromotionRuleData>();
        }
        List<Long> pids = new ArrayList<Long>();
        List<Long> jjgMz = new ArrayList<Long>();
        for (PromotionInfo info : uniqList) {
            pids.add(info.getPromotionId());
            if (PromotionTypeEnums.INCREASE.getValue().equals(info.getPromotionType())
                    || PromotionTypeEnums.GIFT.getValue().equals(info.getPromotionType())) {
                jjgMz.add(info.getPromotionId());
            }
        }
        Map<Long, List<PromotionProduct>> promotionProductMap =
                new HashMap<Long, List<PromotionProduct>>();
        // 不满足、满足的活动
        List<PromotionInfo> missProm = new ArrayList<PromotionInfo>(),
                meetProm = new ArrayList<PromotionInfo>();
        Map<String, Object> rsMap = new HashMap<String, Object>();
        // 批量查询规则数据
        Map<Long, List<PromotionRuleData>> prdMap =
                promotionRuleDataService.queryBatchPromotionRuleData(pids);
        BigDecimal reduction = BigDecimal.ZERO, coupon = BigDecimal.ZERO,
                additionalMoney = BigDecimal.ZERO;// 活动总减免，活动总送券、加价购

        Map<Long, List<PromotionProduct>> giftMap = null;
        try {
            giftMap = promotionProductDAO.queryBatchGiftProductPrice(null, jjgMz);
        } catch (Exception e) {
            logger.error("批量查询加价购、赠品价格发生异常", e);
        }
        if (null == giftMap) {
            giftMap = new HashMap<Long, List<PromotionProduct>>();
        }
        if (null == prdMap) {
            prdMap = new HashMap<Long, List<PromotionRuleData>>();
        }
        // 遍历活动，设置规则数据、活动产品
        PromotionInfo infoTemp = null;
        List<PromotionProduct> infoPPlist = null;// 当前活动的活动产品List
        for (PromotionInfo info : uniqList) {
            Long promotionInfoId = info.getPromotionId();
            infoPPlist = giftMap.get(promotionInfoId);
            // 活动下的商品原始总价
            BigDecimal onePromotiontotal = BigDecimal.ZERO;
            for (Iterator<BaseProduct> bpIt = sellerProductMap.values().iterator(); bpIt
                    .hasNext();) {
                BaseProduct bp = bpIt.next();
                if (bp.getExcludeAll()) {
                    continue;
                }
                // 商品不参加活动
                if (!promotionInfoService.inPromotionInfo(bp, promotionInfoId)
                        || bp.getSupplierType() == 4) {
                    bp.removePromotionInMap(info);
                    continue;
                }
                info.getCarProductMap().put(bp.getProductSkuId().toString(), bp);
                onePromotiontotal = onePromotiontotal
                        .add(bp.getFinalPrice().multiply(BigDecimal.valueOf(bp.getAmount())));
            }
            Integer type = info.getPromotionType();
            List<PromotionRuleData> prdList = prdMap.get(promotionInfoId);
            if (infoPPlist != null) {
                info.setGiftProductList(infoPPlist);
            }
            if (prdList != null) {
                info.setRuleDatas(prdList);
            }
            if (null == prdList || prdList.isEmpty() || info.getMeetData() == null) {
                continue;
            }
            BigDecimal priceData = BigDecimal.ZERO;
            Long entityId = 0L;
            // 判断是否选中加购价产品规则
            PromotionRuleData checkPrd = pMap.get(promotionInfoId);// 选中的活动产品
            for (PromotionRuleData prd : prdList) {
                if (onePromotiontotal.compareTo(prd.getMeetData()) >= 0) {
                    priceData = new BigDecimal(prd.getPrizeData());
                    entityId = prd.getEntityId();
                    if (0 == type.compareTo(PromotionTypeEnums.COUPON.getValue())) {
                        info.setPrivilegeData(new BigDecimal(entityId));
                        break;
                        // 加价购//满赠
                    } else if ((0 == type.compareTo(PromotionTypeEnums.INCREASE.getValue())
                            || 0 == type.compareTo(PromotionTypeEnums.GIFT.getValue()))
                            && null != checkPrd && checkPrd.getEntityId().equals(prd.getEntityId())
                            && prd.getPromotionRuleDataId()
                                    .equals(checkPrd.getPromotionRuleDataId())) {
                        checkPrd = prd;// 设置价格数据
                        info.setPrivilegeData(priceData);
                        break;
                    } else {
                        info.setPrivilegeData(priceData);
                        break;
                    }
                }
            }
            if (0 == type.compareTo(PromotionTypeEnums.GIFT.getValue())
                    && onePromotiontotal.compareTo(info.getMeetData()) < 0) {
                missProm.add(info);
                continue;
            }
            for (Iterator<BaseProduct> bpIt = sellerProductMap.values().iterator(); bpIt
                    .hasNext();) {
                BaseProduct bp = bpIt.next();
                if (!promotionInfoService.inPromotionInfo(bp, promotionInfoId)) {// 商品不参加活动
                    bp.removePromotionInMap(info);
                    continue;
                }
                if (info.getExcludeAll()) {
                    bp.setExcludeAll(true);
                } else {
                    bp.removePromotionInMap(info.getMutexPromotionId());// 移除互斥
                    bp.removePromotionInMap(type);// 移除同类型的
                }
            }
            if (onePromotiontotal.compareTo(info.getMeetData()) < 0) {
                missProm.add(info);
                continue;
            } else {
                meetProm.add(info);
            }
            // 满额减免
            if (0 == PromotionTypeEnums.REDUCTION.getValue().compareTo(type)) {
                if (null == infoTemp || infoTemp.compareTo(info) < 0) {
                    infoTemp = info;
                    reduction = reduction.add(priceData);
                }
                // 满额送卷
            } else if (0 == PromotionTypeEnums.COUPON.getValue().compareTo(type)) {
                coupon = coupon.add(getCouponMoney(entityId));
            } else {
                if (null == infoPPlist || infoPPlist.isEmpty()) {
                    continue;
                }
                boolean hasGift = false,
                        isGift = 0 == PromotionTypeEnums.GIFT.getValue().compareTo(type);// 是否有选中、满赠
                PromotionProduct pp = null;
                List<PromotionProduct> promotionProduct = new ArrayList<PromotionProduct>();
                BigDecimal maxMeetPrice = BigDecimal.ZERO;// 满赠活动中最大的满足条件
                BigDecimal conditionPrice = BigDecimal.ZERO;// 当条件满足最大限度，当本次发生变化时选最高
                if (isGift) {
                    for (int i = 0; i < infoPPlist.size(); i++) {
                        pp = infoPPlist.get(i);
                        if (pp.getMarketPrice().compareTo(onePromotiontotal) <= 0
                                && pp.getMarketPrice().compareTo(maxMeetPrice) > 0) {
                            maxMeetPrice = pp.getMarketPrice();
                        }
                    }
                    if (maxMeetPrice.compareTo(conditionPrice) >= 0) {
                        // 说明购买产品数量发生变化，此时应该赠送满足的最高阶梯，同时把最高阶梯值传给b2b，用于下次判断、
                        conditionPrice = maxMeetPrice;
                        if (null != checkPrd && null != checkPrd.getMeetData()
                                && null != checkPrd.getMaxMeetData()
                                && checkPrd.getMeetData().compareTo(checkPrd.getMaxMeetData()) <= 0
                                && checkPrd.getMaxMeetData().compareTo(maxMeetPrice) >= 0) {
                            conditionPrice = checkPrd.getMeetData();
                            if (conditionPrice.compareTo(maxMeetPrice) >= 0) {
                                conditionPrice = maxMeetPrice;
                            }
                        }
                    }
                }
                for (int i = 0; i < infoPPlist.size(); i++) {
                    pp = infoPPlist.get(i);
                    if (!hasGift && isGift && 0 != maxMeetPrice.compareTo(BigDecimal.ZERO)
                            && 0 == pp.getMarketPrice().compareTo(conditionPrice)
                            && (null == checkPrd
                                    || (null != checkPrd.getPromotionRuleDataId()
                                            && 0 == pp.getRuleDataId()
                                                    .compareTo(checkPrd.getPromotionRuleDataId()))
                                    || (0 == pp.getMarketPrice().compareTo(conditionPrice)))) {
                        // 满赠，选择符合条件最高规则的数据或已选中的满赠
                        hasGift = true;
                        pp.setMaxMeetData(maxMeetPrice); // 设置选中条件赠品最大值
                        pp.setIsChecked(hasGift);
                        // 加价购
                    } else if (null != checkPrd
                            && onePromotiontotal.compareTo(pp.getMarketPrice()) >= 0
                            && 0 == pp.getPromotionType()
                                    .compareTo(PromotionTypeEnums.INCREASE.getValue())
                            && null != checkPrd.getEntityId()
                            && 0 == checkPrd.getEntityId().compareTo(pp.getProductSkuId())
                            && null != checkPrd.getPromotionRuleDataId() && 0 == pp.getRuleDataId()
                                    .compareTo(checkPrd.getPromotionRuleDataId())) {
                        pp.setIsChecked(true);
                        additionalMoney = additionalMoney.add(pp.getFinalPrice());
                    } else {
                        pp.setIsChecked(false);
                    }
                    pp.setAmount(pp.getStockCount().compareTo(0) <= 0 ? 0 : 1);
                    promotionProduct.add(pp);
                }
                promotionProductMap.put(info.getPromotionId(), promotionProduct);
            }
        }
        rsMap.put("giftMap", promotionProductMap);// 活动产品
        rsMap.put("coupon", coupon);// 优惠券金额
        rsMap.put("reduction", reduction);// 满减金额
        rsMap.put("additionalMoney", additionalMoney);// 加价购
        rsMap.put("missProm", missProm);// 未满足的活动
        rsMap.put("meetProm", meetProm);// 满足的活动
        return rsMap;
    }

    /**
     * 复制产品对象 数量值
     * 
     * @param org
     * @return
     */
    private BaseProduct copyProductAcmout(BaseProduct org) {
        BaseProduct bp = new BaseProduct();
        bp.setProductSkuId(org.getProductSkuId());
        bp.setAmount(org.getAmount());
        bp.setFinalPrice(org.getFinalPrice());
        bp.setPrice(org.getPrice());
        bp.setTitle(org.getTitle());
        bp.setCostIncomeRatio(org.getCostIncomeRatio());
        bp.setErpProCode(org.getErpProCode());
        bp.setErpSysCode(org.getErpSysCode());
        bp.setSkuAttrValue(org.getSkuAttrValue());
        bp.setRestrictionAmount(org.getRestrictionAmount());
        bp.setPromotionInfoList(org.getPromotionInfoList());
        bp.setPromotionInfoMap(org.getPromotionInfoMap());
        bp.setMaxBuy(org.getMaxBuy());
        bp.setMinBuy(org.getMinBuy());
        bp.setName(org.getName());
        bp.setPromoBuyNum(org.getPromoBuyNum());
        bp.setPromotionSell(org.getPromotionSell());
        bp.setPromotionStock(org.getPromotionStock());
        bp.setStockCount(org.getStockCount());
        return bp;
    }

    @Resource
    private PromotionCacheUtil promotionCacheUtil;
    @Resource
    private ProductSkuService productSkuService;

    @Override
    public PriceInfo getCachePriceBySkuId(Long skuId) throws Exception {
        ProductPromotion pp = promotionCacheUtil.getProductPromtoionInfoCahce(skuId.toString());
        if (pp == null) {
            return null;
        }
        Promotion p = pp.getPricePromotion();
        if (p == null) {
            return null;
        }
        PriceInfo rs = new PriceInfo();
        BigDecimal finalPrice;
        if (p.getType() == PromotionTypeEnums.SALE.getIntValue()) {
            finalPrice = pp.getData();
        } else {
            ProductSku sku = productSkuService.findProductSkuBySkuId(skuId);
            if (sku == null) {
                return null;
            }
            BigDecimal price = new BigDecimal(sku.getPrice());
            finalPrice = pp.calculateFinalPrice(price);
        }
        rs.setFinalPrice(finalPrice);
        return rs;
    }

    @Override
    public PriceInfo getCachePriceBySkuId_New(Long skuId) throws Exception {
        ProductPromotion pp = promotionCacheUtil.getProductPromtoionInfoCahce(skuId.toString());
        if (pp == null) {
            return null;
        }
        Promotion p = pp.getPricePromotion();
        if (p == null) {
            return null;
        }
        PriceInfo rs = new PriceInfo();
        BigDecimal finalPrice;
        if (p.getType() == PromotionTypeEnums.SALE.getIntValue()) {
            finalPrice = pp.getData();
        } else {
            ProductSku sku = productSkuService.findProductSkuBySkuId(skuId);
            if (sku == null) {
                return null;
            }
            BigDecimal price = new BigDecimal(sku.getPrice());
            finalPrice = pp.calculateFinalPrice(price);
        }
        rs.setFinalPrice(finalPrice);
        UserChannelContextHolder.destroyUserChannel();
        return rs;
    }
}
