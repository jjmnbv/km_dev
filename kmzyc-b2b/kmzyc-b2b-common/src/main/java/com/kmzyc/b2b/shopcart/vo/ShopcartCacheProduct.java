package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;


public class ShopcartCacheProduct extends JSONObject
        implements
            Serializable,
            Comparable<ShopcartCacheProduct> {
    private static final long serialVersionUID = -4647805937380778102L;

    Long time;

    /**
     * 
     * @param id 商品id
     * @param amount 商品数量
     * @param activityChannel 来源渠道
     */
    public ShopcartCacheProduct(String id,int amount, Date time, boolean check,
            String activityChannel) {
        this.setId(id);
        //this.setRuleid(ruleid);
        if (amount <= 0) {
            amount = 1;
        }
        this.setAmount(amount);
        this.setCheck(check);
        setTime(time.getTime());
        this.setActivityChannel(activityChannel);
    }

    public ShopcartCacheProduct() {
        super();
    }


    /***/
    public boolean isCheck() {
        return super.getBooleanValue("check");
    }

    /***/
    public void setCheck(boolean check) {
        super.put("check", check);
    }

/*    *//***//*
    public String getRuleid() {
        return super.getString("ruleid");
    }

    *//***//*
    public void setRuleid(String ruleid) {
        super.put("ruleid", ruleid);
    }*/

    /***/
    public String getId() {
        return super.getString("id");
    }

    /***/
    public void setId(String id) {
        super.put("id", id);
    }

    public void setAmount(int amount) {
        super.put("amount", amount);
    }

    public int getAmount() {
        int amount = super.getIntValue("amount");
        return amount;
    }

    /***/
    public String toCacheString() {
        this.remove("id");
        return this.toJSONString();
    }


    public Long getTime() {
        return super.getLong("time");
    }

    public void setTime(Long time) {
        super.put("time", time);
    }

    @Override
    public int compareTo(ShopcartCacheProduct o) {
        return this.getTime().compareTo(o.getTime());
    }

    public String getActivityChannel() {
        return super.getString("activityChannel");
    }

    public void setActivityChannel(String activityChannel) {
        super.put("activityChannel", activityChannel);
    }

}
