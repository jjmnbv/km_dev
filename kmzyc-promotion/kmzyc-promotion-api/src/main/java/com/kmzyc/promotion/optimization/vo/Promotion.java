package com.kmzyc.promotion.optimization.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.promotion.util.PromotionCacheUtil;


public class Promotion implements Serializable, Comparable<Promotion> {
    private static final long serialVersionUID = 2409647948266483065L;
    private long id;
    private String name;
    private int type;
    private Integer level;
    private Date startTime;
    private Date endTime;
    private BigDecimal data;// 特价价格或者折扣
    /** 活动可用量 */
    private Integer availableQuantity;
    private Integer sellUpType; // 活动库存卖光后的类型

    private BigDecimal gantFilterType; // 附赠是否统计指定赠品 （-1为统一，否则为 不指定）
    // 商家ID
    private Long sellerId;

    private transient List<JSONObject> ruleDataAndPrizeEntityInfo;


    public Promotion() {

    }


    public BigDecimal getGantFilterType() {
        return gantFilterType;
    }



    public void setGantFilterType(BigDecimal gantFilterType) {
        this.gantFilterType = gantFilterType;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 打折活动折扣 */
    public BigDecimal getData() {
        return data;
    }

    /** 打折活动折扣 */
    public void setData(BigDecimal data) {
        this.data = data;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 返回负数this优先级高 返回正数o的优先级高
     */
    public int compareTo(Promotion o) {
        int compare = this.level.compareTo(o.getLevel());
        if (compare == 0) {
            compare = this.getStartTime().compareTo(o.getStartTime());
        }
        if (compare == 0) {
            compare = Long.valueOf(this.id).compareTo(o.getId());
        }
        return compare * -1;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTypeName() {
        return PromotionCacheUtil.getPromotionTagTitle(type);
        // return PromotionTypeEnums.valueOf(type + "").getTitle();
    }

    /** 规则数据 */
    public List<JSONObject> getRuleDataAndPrizeEntityInfo() {
        return ruleDataAndPrizeEntityInfo;
    }

    /** 规则数据 */
    public void setRuleDataAndPrizeEntityInfo(List<JSONObject> ruleDataAndPrizeEntityInfo) {
        this.ruleDataAndPrizeEntityInfo = ruleDataAndPrizeEntityInfo;
    }


    public Integer getAvailableQuantity() {
        return availableQuantity;
    }


    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }


    public Integer getSellUpType() {
        return sellUpType;
    }


    public void setSellUpType(Integer sellUpType) {
        this.sellUpType = sellUpType;
    }


    public Long getSellerId() {
        return sellerId;
    }


    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }


}
