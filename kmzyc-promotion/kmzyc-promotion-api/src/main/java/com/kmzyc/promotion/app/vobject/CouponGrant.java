package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CouponGrant implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long couponGrantId;

    private Long couponId;

    private Integer customId;

    /**优惠券状态*/
    private Long couponStatus;
    
    /**优惠券状态变更来源，从 什么状态 到 couponStatus*/
    private transient Long fromCouponStatus;
    
    private List<Coupon> coupon;
    
    private Date grantCreattime;
    
    private Long grantCreateman;
    
    private Date grantUpdatetime;
    
    private Long grantUpdateman;
    
    //---新增发放业务关联
    private Long grantType;
    
    private String grantRelatedCode;
    
    private String grantRemark;
    
    private Date grantEndtime;
    //激活状态
    private Long actStatus;
    private String activeCode;//ACTIVE_CODE激活码
    
    private String couponInfoNo; //不记名优惠券no
    
    private String couponName;  //为了记录流水而加上的
    
	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	//发放券对应的客户名称 
    private String customerName;
    //发送券对应的优惠券详情
    private Coupon couponAlreadyGrant;
    //有效开始时间
    private Date startTime;
    private Date endTime;
    private Long couponIssuingId;//COUPON_ISSUING_ID优惠劵发放设置ID
    //优惠券使用时间（新增2014.12.8）
    private Date useTime;
    private Date actTime;
    public Date getActTime() {
		return actTime;
	}

	public void setActTime(Date actTime) {
		this.actTime = actTime;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Long getCouponIssuingId() {
		return couponIssuingId;
	}

	public void setCouponIssuingId(Long couponIssuingId) {
		this.couponIssuingId = couponIssuingId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	//分页
    private int skip;
    private int max;
    
    
    public Long getActStatus() {
		return actStatus;
	}

	public void setActStatus(Long actStatus) {
		this.actStatus = actStatus;
	}
	public Long getCouponGrantId() {
		return couponGrantId;
	}

	public void setCouponGrantId(Long couponGrantId) {
		this.couponGrantId = couponGrantId;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Integer getCustomId() {
		return customId;
	}

	public void setCustomId(Integer customId) {
		this.customId = customId;
	}

	public Long getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(Long couponStatus) {
		this.couponStatus = couponStatus;
	}

	public List<Coupon> getCoupon() {
		return coupon;
	}

	public void setCoupon(List<Coupon> coupon) {
		this.coupon = coupon;
	}

	public Date getGrantCreattime() {
		return grantCreattime;
	}

	public void setGrantCreattime(Date grantCreattime) {
		this.grantCreattime = grantCreattime;
	}

	public Long getGrantCreateman() {
		return grantCreateman;
	}

	public void setGrantCreateman(Long grantCreateman) {
		this.grantCreateman = grantCreateman;
	}

	public Date getGrantUpdatetime() {
		return grantUpdatetime;
	}

	public void setGrantUpdatetime(Date grantUpdatetime) {
		this.grantUpdatetime = grantUpdatetime;
	}

	public Long getGrantUpdateman() {
		return grantUpdateman;
	}

	public void setGrantUpdateman(Long grantUpdateman) {
		this.grantUpdateman = grantUpdateman;
	}

	public Long getGrantType() {
		return grantType;
	}

	public void setGrantType(Long grantType) {
		this.grantType = grantType;
	}

	public String getGrantRelatedCode() {
		return grantRelatedCode;
	}

	public void setGrantRelatedCode(String grantRelatedCode) {
		this.grantRelatedCode = grantRelatedCode;
	}

	public String getGrantRemark() {
		return grantRemark;
	}

	public void setGrantRemark(String grantRemark) {
		this.grantRemark = grantRemark;
	}

	public Date getGrantEndtime() {
		return grantEndtime;
	}

	public void setGrantEndtime(Date grantEndtime) {
		this.grantEndtime = grantEndtime;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Coupon getCouponAlreadyGrant() {
		return couponAlreadyGrant;
	}

	public void setCouponAlreadyGrant(Coupon couponAlreadyGrant) {
		this.couponAlreadyGrant = couponAlreadyGrant;
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

	public String getCouponInfoNo() {
		return couponInfoNo;
	}

	public void setCouponInfoNo(String couponInfoNo) {
		this.couponInfoNo = couponInfoNo;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

    public Long getFromCouponStatus() {
        return fromCouponStatus;
    }

    public void setFromCouponStatus(Long fromCouponStatus) {
        this.fromCouponStatus = fromCouponStatus;
    }

}