package com.kmzyc.promotion.optimization.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.constant.PromotionConstant;
import com.kmzyc.promotion.util.StringUtil;
import com.kmzyc.promotion.util.UserChannelContextHolder;


public class ProductPromotion implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 6428757520485536200L;
    Map<String, Promotion> orderPromtotions;// 订单级
    List<Promotion> sortedOrderPromtotions;// 订单级
    Promotion pricePromotion;// 特价打折

    Promotion pricePromotionApp;// app 特价

    Promotion affixPromoiton;// 固定层级附赠活动
    List<PromotionProductData> affixProductList;// 附赠的商品
    private Integer min;
    private Integer max;
    private BigDecimal data;
    /** 活动可用量 */
    private Integer availableQuantity;
    private Integer sellUpType;
    private String[] labelArray;
    /** 所有参与活动 */
    List<Promotion> allPromtotions;

    /** 本地缓存过期时间 */
    private Date expireTime;

    // 商家ID
    private Long sellerId;
    // 促销类型
    private int type;

    /** 添加订单级 */
    public boolean addOrderPromotion(Promotion orderPromotion) {
        if (orderPromtotions == null) {
            orderPromtotions = new HashMap<String, Promotion>();
            sortedOrderPromtotions = new ArrayList<Promotion>();
        }
        orderPromtotions.put(orderPromotion.getId() + "", orderPromotion);
        sortedOrderPromtotions.add(orderPromotion);
        return true;
    }

    /**
     * 根据渠道号返回单品级活动(pc/wap 特价打折)
     * 
     * @return
     */
    public Promotion getPricePromotionForChannel() {

        String userchannel = UserChannelContextHolder.getUserChannel();

        if (PromotionConstant.CHANNEL_APP.equals(userchannel)) {
            if (pricePromotionApp != null) {
                return pricePromotionApp;
            } else {
                return pricePromotion;
            }
        } else {
            return pricePromotion;
        }
    }

    /**
     * 单品级活动(pc/wap 特价打折)
     * 
     * @return
     */
    public Promotion getPricePromotion() {
        return pricePromotion;
    }

    /**
     * 单品级活动(pc/wap 特价打折)
     * 
     * @param pricePromotion
     */
    public void setPricePromotion(Promotion pricePromotion) {
        this.pricePromotion = pricePromotion;
    }


    /**
     * 单品级活动(app 特价)
     * 
     * @return
     */
    public Promotion getPricePromotionApp() {
        return pricePromotionApp;
    }

    /**
     * 单品级活动(app 特价)
     * 
     * @return
     */
    public void setPricePromotionApp(Promotion pricePromotionApp) {
        this.pricePromotionApp = pricePromotionApp;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    /** 活动可用量 初始为活动库存 */
    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    /** 活动可用量 初始为活动库存 */
    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    /** 附赠活动 */
    public Promotion getAffixPromoiton() {
        return affixPromoiton;
    }

    /** 附赠活动 */
    public void setAffixPromoiton(Promotion affixPromoiton) {
        this.affixPromoiton = affixPromoiton;
    }

    /** 附赠商品 */
    public List<PromotionProductData> getAffixProductList() {
        return affixProductList;
    }

    /** 附赠商品 */
    public void setAffixProductList(List<PromotionProductData> affixProductList) {
        this.affixProductList = affixProductList;
    }

    /** 附赠商品 */
    public void addAffixProductList(List<PromotionProductData> affixProductList) {
        if (this.affixProductList == null) {
            this.affixProductList = new ArrayList<PromotionProductData>();
        }
        if (null != affixProductList && affixProductList.size() > 0) {
            this.affixProductList.addAll(affixProductList);
        }
    }

    /**
     * 卖光后后设置 1停止销售直到活动结束；2恢复原价
     */
    public Integer getSellUpType() {
        return sellUpType;
    }

    /**
     * 卖光后后设置 1停止销售直到活动结束；2恢复原价
     */
    public void setSellUpType(Integer sellUpType) {
        this.sellUpType = sellUpType;
    }


    /** 订单级活动 */
    public Map<String, Promotion> getOrderPromtotions() {
        return orderPromtotions;
    }

    /** 获取默认订单级第一个活动 */
    public Promotion getDefaultOrderPromtotion() {
        if (StringUtil.isEmpty(sortedOrderPromtotions)) {
            return null;
        }
        return sortedOrderPromtotions.iterator().next();
    }


    /**
     * 根据渠道号 获取商品最终定价。如果渠道号为app且有app特价活动则返回app特价，否则返回b2b单品级活动
     * 
     * @param price
     * @return
     */
    public BigDecimal calculateFinalPrice(BigDecimal price) {

        if (price == null) {
            return null;
        }

        String channel = UserChannelContextHolder.getUserChannel();

        BigDecimal finalPrice = null;
        if (!StringUtil.isEmpty(channel) && PromotionConstant.CHANNEL_APP.equals(channel)) {
            if (pricePromotionApp != null && pricePromotionApp
                    .getType() == PromotionTypeEnums.SALE_APP.getValue().intValue()) {
                finalPrice = this.getData();
            }

        }


        if (finalPrice == null) {
            if (pricePromotion == null) {
                return price;
            } else if (pricePromotion.getType() == PromotionTypeEnums.SALE.getValue().intValue()) {
                finalPrice = this.getData();
            } else if (pricePromotion.getType() == PromotionTypeEnums.DISCOUNT.getValue()
                    .intValue()) {
                finalPrice = price.multiply(pricePromotion.getData());
            }
        }

        if (finalPrice == null) {
            finalPrice = price;
        }
        finalPrice = toBigDecimal(finalPrice);

        return finalPrice;

    }


    /**
     * 格式化保留两位小数
     * 
     * @param number
     * @return
     */
    public static BigDecimal toBigDecimal(BigDecimal number) {
        if (number == null)
            return null;
        BigDecimal af3 = number.setScale(3, BigDecimal.ROUND_HALF_UP);
        BigDecimal af2 = number.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (af3.compareTo(af2) > 0) {
            return af2.add(BigDecimal.valueOf(0.01));
        }
        return af2;
    }

    /** 排序的订单级活动 */
    public List<Promotion> getSortedOrderPromtotions() {
        return sortedOrderPromtotions;
    }

    /** 排序的订单级活动 */
    public void setSortedOrderPromtotions(List<Promotion> sortedOrderPromtotions) {
        this.sortedOrderPromtotions = sortedOrderPromtotions;
    }

    /** 促销标签 */
    public String[] getLabelArray() {
        return labelArray;
    }

    /** 促销标签 */
    public void setLabelArray(String[] labelArray) {
        this.labelArray = labelArray;
    }

    /** 所有参与活动 */
    public List<Promotion> getAllPromtotions() {
        return allPromtotions;
    }

    /** 所有参与活动 */
    public void setAllPromtotions(List<Promotion> allPromtotions) {
        this.allPromtotions = allPromtotions;
    }

    /** 特价价格或者折扣 */
    public BigDecimal getData() {
        return data;
    }

    /** 特价价格或者折扣 */
    public void setData(BigDecimal data) {
        this.data = data;
    }

    /** 本地缓存过期时间 */
    public Date getExpireTime() {
        return expireTime;
    }

    /** 本地缓存过期时间 */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
