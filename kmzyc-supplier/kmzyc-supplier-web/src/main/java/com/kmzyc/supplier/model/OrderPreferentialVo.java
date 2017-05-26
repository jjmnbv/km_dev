package com.kmzyc.supplier.model;

import com.pltfm.app.vobject.OrderItemVo;

import java.io.Serializable;

/**
 * 该实体主要用来显示单品层级的促销优惠活动
 *
 * @author KM
 */
public class OrderPreferentialVo extends OrderItemVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 促销活动名称
     */
    private String promotionName;
    /**
     * 促销类型字符串显示,单品层级目前只能参与打折或者特价
     */
    private String promotionTypeStr;

    /**
     * 促销活动id
     */
    private long promotionId;
    /**
     * 促销价格,如果是打折,这里字段值显示的是折扣数,如果是特价,则直接是特价价格
     */
    private double promotionPrice;

    /**
     * 促销类型
     */
    // private int promotionType;
    /**
     * 促销商品流水id
     */
    private long promotionProductId;

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionTypeStr() {
        return promotionTypeStr;
    }

    public void setPromotionTypeStr(String promotionTypeStr) {
        this.promotionTypeStr = promotionTypeStr;
    }

    public long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(long promotionId) {
        this.promotionId = promotionId;
    }

    public double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    // public int getPromotionType() {
    // return promotionType;
    // }
    //
    // public void setPromotionType(int promotionType) {
    // this.promotionType = promotionType;
    // }

    public long getPromotionProductId() {
        return promotionProductId;
    }

    public void setPromotionProductId(long promotionProductId) {
        this.promotionProductId = promotionProductId;
    }


}
