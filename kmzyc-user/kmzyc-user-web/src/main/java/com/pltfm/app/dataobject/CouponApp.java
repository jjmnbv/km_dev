package com.pltfm.app.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券表
 * 
 * @author Administrator
 *
 */
public class CouponApp implements Serializable {
    // 优惠券ID
    private Long couponId;
    // 优惠券发放类别ID 1:手工发放类型 2：注册发放类型 3：订单满多少发放类型
    private Long couponGivetypeId;
    // 优惠券名称
    private String couponName;
    // 优惠券描述
    private String couponDescribe;
    // 优惠券抵扣金额
    private BigDecimal couponMoney;
    // 有效开始时间
    private Date starttime;
    // 有效结束时间
    private Date endtime;
    // 创建时间
    private Date createtime;
    // 优惠劵状态 1:未发放 2 已发放3未使用4已使用5已过期
    private Long status;
    // 客户等级ID
    private String customLeveid;
    // 客户注册开始时间
    private Date customRegStart;
    // 会员注册结束时间
    private Date customRegEnd;
    // 优惠券最低消费金额
    private BigDecimal payLeastMoney;
    // 客户ID 1:不能赠送 2 可以赠送
    private String customId;
    // 是否能赠送
    private String isGrant;
    // 优惠券有效天数
    private BigDecimal couponValidDay;
    // 单独用于注册类型的优惠券，是否启用，1:不启用 2:启用
    private String isValide;
    // 店铺标识，多个用逗号隔开
    private String shopCode;
    // 商家类别1：自营 2：入驻 3：代销
    private Short supplierType;
    // 发放状态1：未开始2：已完成3：进行中4：暂停5：截止
    private Short isStatus;
    // 时间范围限制类别1：固定时间2：固定天数
    private Short timeType;
    // 范围限制方式1：白名单2：黑名单
    private Short rangType;

    private Long notStatus;
    // ------------ for page
    int skip;
    int max;


    public Long getNotStatus() {
        return notStatus;
    }

    public void setNotStatus(Long notStatus) {
        this.notStatus = notStatus;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    /*
     * 删除渠道 public String getChannel() { return channel; }
     * 
     * public void setChannel(String channel) { this.channel = channel; }
     */

    public Short getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Short supplierType) {
        this.supplierType = supplierType;
    }

    public Short getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Short isStatus) {
        this.isStatus = isStatus;
    }

    public Short getTimeType() {
        return timeType;
    }

    public void setTimeType(Short timeType) {
        this.timeType = timeType;
    }

    public Short getRangType() {
        return rangType;
    }

    public void setRangType(Short rangType) {
        this.rangType = rangType;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getCouponGivetypeId() {
        return couponGivetypeId;
    }

    public void setCouponGivetypeId(Long couponGivetypeId) {
        this.couponGivetypeId = couponGivetypeId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponDescribe() {
        return couponDescribe;
    }

    public void setCouponDescribe(String couponDescribe) {
        this.couponDescribe = couponDescribe;
    }

    public BigDecimal getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(BigDecimal couponMoney) {
        this.couponMoney = couponMoney;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getCustomLeveid() {
        return customLeveid;
    }

    public void setCustomLeveid(String customLeveid) {
        this.customLeveid = customLeveid;
    }

    public Date getCustomRegStart() {
        return customRegStart;
    }

    public void setCustomRegStart(Date customRegStart) {
        this.customRegStart = customRegStart;
    }

    public Date getCustomRegEnd() {
        return customRegEnd;
    }

    public void setCustomRegEnd(Date customRegEnd) {
        this.customRegEnd = customRegEnd;
    }

    public BigDecimal getPayLeastMoney() {
        return payLeastMoney;
    }

    public void setPayLeastMoney(BigDecimal payLeastMoney) {
        this.payLeastMoney = payLeastMoney;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getIsGrant() {
        return isGrant;
    }

    public void setIsGrant(String isGrant) {
        this.isGrant = isGrant;
    }

    public BigDecimal getCouponValidDay() {
        return couponValidDay;
    }

    public void setCouponValidDay(BigDecimal couponValidDay) {
        this.couponValidDay = couponValidDay;
    }

    public String getIsValide() {
        return isValide;
    }

    public void setIsValide(String isValide) {
        this.isValide = isValide;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

}
