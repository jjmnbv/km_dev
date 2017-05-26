package com.pltfm.app.vobject;

import java.util.Date;
import java.util.List;

/**
 * 对应表：prod_appraise
 * @author Administrator
 *
 */

public class ProdAppraise implements java.io.Serializable {

	private static final long serialVersionUID = 6211823410191342477L;

	private Long appraiseId;

	private Long orderDetailId;

	private Long productSkuId;

	private Date prodBuyDate;

	private String appraiseContent;

	private Short point;

	private String satisficing;

	private String propName1;

	private String propVal1;

	private Short propPoint1;

	private String propName2;

	private Short propPoint2;

	private String propVal2;

	private Long userfulAmount;

	private Long replyAmount;

	private Integer custId;

	private String custName;

	private String custLevel;

	private String custPicPath;

	private Date appraiseDate;

	private Short elite;

	private Short checkStatus;

	private Integer checkManId;

	private String checkMan;

	private String des;

	private String spa1;

	private String spa2;

	private String spa3;

	private String productName;
	
	//ProductSku对象
	private ProductSku productSku;

	//产品主标题
	private String productTitle;

    private String advantage;

    private String defect;

    private String addtoContent;

    private String replyContent;
	
	//方便查询的字段
	
	private Date startBuyDate;
	
	private Date endBuyDate;
	
	private Date startAppDate;
	
	private Date endAppDate;
	
	//回复表里的字段
	private Long apprReplyId;

	private Short replyStatus;

	 //追评表里的字段
	private Long addContentId;

    private Short checkStatus1;

    private String serchType;//用来组装查询状态

    private String serchType1;//用来组装查询状态

    // 关联的产品回复表
    private List<AppraiseReply> appReplyList;

    // 关联的产品追评表
    private List<AppraiseAddtoContent> appAddToContentList;

	public Long getAppraiseId() {
		return appraiseId;
	}

	public void setAppraiseId(Long appraiseId) {
		this.appraiseId = appraiseId;
	}

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Long getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	public Date getProdBuyDate() {
		return prodBuyDate;
	}

	public void setProdBuyDate(Date prodBuyDate) {
		this.prodBuyDate = prodBuyDate;
	}

	public String getAppraiseContent() {
		return appraiseContent;
	}

	public void setAppraiseContent(String appraiseContent) {
		this.appraiseContent = appraiseContent;
	}

	public Short getPoint() {
		return point;
	}

	public void setPoint(Short point) {
		this.point = point;
	}

	public String getSatisficing() {
		return satisficing;
	}

	public void setSatisficing(String satisficing) {
		this.satisficing = satisficing;
	}

	public String getPropName1() {
		return propName1;
	}

	public void setPropName1(String propName1) {
		this.propName1 = propName1;
	}

	public String getPropVal1() {
		return propVal1;
	}

	public void setPropVal1(String propVal1) {
		this.propVal1 = propVal1;
	}

	public Short getPropPoint1() {
		return propPoint1;
	}

	public void setPropPoint1(Short propPoint1) {
		this.propPoint1 = propPoint1;
	}

	public String getPropName2() {
		return propName2;
	}

	public void setPropName2(String propName2) {
		this.propName2 = propName2;
	}

	public Short getPropPoint2() {
		return propPoint2;
	}

	public void setPropPoint2(Short propPoint2) {
		this.propPoint2 = propPoint2;
	}

	public String getPropVal2() {
		return propVal2;
	}

	public void setPropVal2(String propVal2) {
		this.propVal2 = propVal2;
	}

	public Long getUserfulAmount() {
		return userfulAmount;
	}

	public void setUserfulAmount(Long userfulAmount) {
		this.userfulAmount = userfulAmount;
	}

	public Long getReplyAmount() {
		return replyAmount;
	}

	public void setReplyAmount(Long replyAmount) {
		this.replyAmount = replyAmount;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustLevel() {
		return custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}

	public String getCustPicPath() {
		return custPicPath;
	}

	public void setCustPicPath(String custPicPath) {
		this.custPicPath = custPicPath;
	}

	public Date getAppraiseDate() {
		return appraiseDate;
	}

	public void setAppraiseDate(Date appraiseDate) {
		this.appraiseDate = appraiseDate;
	}

	public Short getElite() {
		return elite;
	}

	public void setElite(Short elite) {
		this.elite = elite;
	}

	public Short getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getCheckManId() {
		return checkManId;
	}

	public void setCheckManId(Integer checkManId) {
		this.checkManId = checkManId;
	}

	public String getCheckMan() {
		return checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getSpa1() {
		return spa1;
	}

	public void setSpa1(String spa1) {
		this.spa1 = spa1;
	}

	public String getSpa2() {
		return spa2;
	}

	public void setSpa2(String spa2) {
		this.spa2 = spa2;
	}

	public String getSpa3() {
		return spa3;
	}

	public void setSpa3(String spa3) {
		this.spa3 = spa3;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getStartBuyDate() {
		return startBuyDate;
	}

	public void setStartBuyDate(Date startBuyDate) {
		this.startBuyDate = startBuyDate;
	}

	public Date getEndBuyDate() {
		return endBuyDate;
	}

	public void setEndBuyDate(Date endBuyDate) {
		this.endBuyDate = endBuyDate;
	}

	public Date getStartAppDate() {
		return startAppDate;
	}

	public void setStartAppDate(Date startAppDate) {
		this.startAppDate = startAppDate;
	}

	public Date getEndAppDate() {
		return endAppDate;
	}

	public void setEndAppDate(Date endAppDate) {
		this.endAppDate = endAppDate;
	}

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}

	public String getDefect() {
		return defect;
	}

	public void setDefect(String defect) {
		this.defect = defect;
	}

	public String getAddtoContent() {
		return addtoContent;
	}

	public void setAddtoContent(String addtoContent) {
		this.addtoContent = addtoContent;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

	public Long getApprReplyId() {
		return apprReplyId;
	}

	public void setApprReplyId(Long apprReplyId) {
		this.apprReplyId = apprReplyId;
	}

	public Short getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(Short replyStatus) {
		this.replyStatus = replyStatus;
	}

	public Long getAddContentId() {
		return addContentId;
	}

	public void setAddContentId(Long addContentId) {
		this.addContentId = addContentId;
	}

	public Short getCheckStatus1() {
		return checkStatus1;
	}

	public void setCheckStatus1(Short checkStatus1) {
		this.checkStatus1 = checkStatus1;
	}

	public String getSerchType() {
		return serchType;
	}

	public void setSerchType(String serchType) {
		this.serchType = serchType;
	}

	public String getSerchType1() {
		return serchType1;
	}

	public void setSerchType1(String serchType1) {
		this.serchType1 = serchType1;
	}

    public List<AppraiseReply> getAppReplyList() {
        return appReplyList;
    }

    public void setAppReplyList(List<AppraiseReply> appReplyList) {
        this.appReplyList = appReplyList;
    }

    public List<AppraiseAddtoContent> getAppAddToContentList() {
        return appAddToContentList;
    }

    public void setAppAddToContentList(List<AppraiseAddtoContent> appAddToContentList) {
        this.appAddToContentList = appAddToContentList;
    }
}