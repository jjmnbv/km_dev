package com.pltfm.app.vobject;

import com.alibaba.fastjson.JSONObject;
import com.pltfm.app.util.PromotionInfoUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PromotionInfo implements Serializable,Comparable<PromotionInfo>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7447611749962972728L;

	//ID
	private Long promotionId;
	
	//标题
	private String title;
	
	//名称
	private String promotionName;
	
	
	//满足条件数值
	private BigDecimal meetData;
	
	//优惠数值
	private BigDecimal privilegeData;
	
	//活动类型
	private Integer promotionType;
	
	//优先级
	private Integer priority;
	
	private String cmsUrlPath;
	/**
	 * {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌',5:'指定商家'}
	 */
	private Integer productFilterType;
	private String productFilterSql;
	
	
	
	private Integer status;
	private String channel;
//	private Integer nature;
	private Integer shopSort;
	
	private Long supplierId;

	private Long ruleId;
    
    private Integer promotionPrizeNum;
    
    private BigDecimal promotionData;
    private String promotionNote;
	
	//规则数据（满足条件数值和优惠数值）
	private List<PromotionRuleData> ruleDatas;
	
	public List<PromotionRuleData> getRuleDatas() {
		return ruleDatas;
	}

	public void setRuleDatas(List<PromotionRuleData> ruleDatas) {
		this.ruleDatas = ruleDatas;
	}



	// 互斥活动ID
	private String mutualIds;
	public String getMutualIds() {
		if(mutualIds==null){
			mutualIds="";
		}
		return mutualIds;
	}
	public void setMutualIds(String mutualIds) {
		this.mutualIds = mutualIds;
	}
	/**
	 * ID
	 * @return
	 */
	public Long getPromotionId() {
		return promotionId;
	}
	 
	public String getProductNameView()
	{
		return null;
	}
	 
	public BigDecimal getLastAllMoney()
	{
		return null;
	}
	/**
	 * ID
	 * @return
	 */
	public void setPromotionInfoId(Long promotionId) {
		this.promotionId = promotionId;
	}
	/**
	 * 标题（广告语）
	 * @return
	 */
	
	public String getTitle() {
		return title;
	}
	/**
	 * 标题（广告语）
	 * @return
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
	/**
	 * 规则满足数值
	 * @return
	 */
	// 
	public BigDecimal getMeetData() {
		if(ruleDatas == null){
			return null;
		}
		if(ruleDatas.isEmpty()){
			return null;
		}
		return ruleDatas.get(0).getMeetData();
	}
	/**
	 * 规则满足数值
	 * @return
	 */
	public void setMeetData(BigDecimal meetData) {
		this.meetData = meetData;
	}
	/**
	 * 规则优惠数值
	 * @return
	 */
	
	public BigDecimal getPrivilegeData() {
		return privilegeData;
	}
	/**
	 * 规则优惠数值
	 * @return
	 */
	public void setPrivilegeData(BigDecimal privilegeData) {
		this.privilegeData = privilegeData;
	}
	/**
	 * 活动类型10特价；8打折；6满额减免；4满额送券
	 * @return
	 */
	
	public Integer getPromotionType() {
		return promotionType;
	}
	/**
	 * 活动类型10特价；8打折；6满额减免；4满额送券
	 * @return
	 */
	public void setPromotionType(Integer promotionType) {
		this.promotionType = promotionType;
	}
	/**
	 * 活动优先级
	 * @return
	 */
	 
	public Integer getPriority() {
		return priority;
	}
	/**
	 * 活动优先级
	 * @return
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	//用于查询
	private Date startTime;
	private Date endTime;
	private Long productSkuId;
	private Long productId;
	
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
	 
	public Long getProductSkuId() {
		return productSkuId;
	}
	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}
	 
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public JSONObject toJsonObject(){
		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		json.put("promotionName",this.promotionName);
		json.put("endTime",sdf.format(this.endTime));
		json.put("startTime",sdf.format(this.startTime));
		json.put("title",this.title);
		json.put("promotionType",this.promotionType);
		json.put("promotionTypeName",PromotionInfoUtil.getPromotionTypeMap().get(this.promotionType));
		json.put("promotionId",this.promotionId);
		json.put("privilegeData", privilegeData);
		json.put("meetData", meetData);
		json.put("productFilterType", productFilterType);
		String ruleKey = "ruleName";
		String ruleValue="";
		switch (this.promotionType) {
		case 10:
			ruleValue +="特价价格:"+promotionData.doubleValue();
			break;
		case 8:
			ruleValue +="折扣:打"+promotionData.doubleValue()+"折";
			break;
		case 6:
			ruleValue +="满减:满"+meetData.doubleValue()+"元，立减"+privilegeData.doubleValue()+"元";
			break;
		case 4:
			ruleValue +="满送:满"+meetData.doubleValue()+"元，立送id为"+privilegeData.longValue()+"的优惠券券";
			break;
			
		default:
			break;
		}
		json.put(ruleKey, ruleValue);
		return json;
	}
	public String toString(){
		return this.toJsonObject().toJSONString();
	}
	
	 
	public String getCmsUrlPath() {
		return cmsUrlPath;
	}
	public void setCmsUrlPath(String cmsUrlPath) {
		this.cmsUrlPath = cmsUrlPath;
	}
	/**
	 * {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌',5:'指定商家'}
	 * @return
	 */
	public Integer getProductFilterType() {
		return productFilterType;
	}
	/**
	 * {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌',5:'指定商家'}
	 * @param productFilterType
	 */
	public void setProductFilterType(Integer productFilterType) {
		this.productFilterType = productFilterType;
	}

	public String getProductFilterSql() {
		return productFilterSql;
	}

	public void setProductFilterSql(String productFilterSql) {
		this.productFilterSql = productFilterSql;
	}
	
	
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	/**活动种类 1是普通活动；2是特色活动用于满赠和加价购*/
//	public Integer getNature() {
//		return nature;
//	}
	/**活动种类 1是普通活动；2是特色活动用于满赠和加价购*/
//	public void setNature(Integer nature) {
//		this.nature = nature;
//	}

	
	/**
	 * 降序排列 优先级高的排前面  返回负数this优先级高 </br>
	 * 1、商品作用域优先级高于订单优先级</br>
	 * 2、同一作用域 强制排他优先级最高</br>
	 * 3、类型优先级 特价>打折>满减>满送</br>
	 * 4、以上情况相同 优先级数字越大  优先级越高</br>
	 * 5、以上情况相同 活动开始时间越晚 优先级越高</br>
	 */
	@Override
	public int compareTo(PromotionInfo o) {
		// TODO Auto-generated method stub
		int compare = 0;
		if(getPromotionId().intValue()-o.getPromotionId().intValue()==0){
			return compare;
		}
		if(getMutualIds().equals("all")&&o.getMutualIds().equals("all")){
			compare =0;
		}else if(!this.mutualIds.equals("all")&&!o.getMutualIds().equals("all")){
			compare =0;
		}else{
			if(this.mutualIds.equals("all")){
				compare = 1;
			}else if(o.getMutualIds().equals("all")){
				compare = -1;
			}
		}
		if(compare==0){
			compare = this.promotionType-o.getPromotionType();
		}
		
		if(compare==0){
			compare = this.priority-o.getPriority();
		}
		if(compare==0){
			compare =this.startTime.compareTo(o.getStartTime());
		}
		return compare*(-1);
	}
	/**商家类别 ：1所有；2指定商家；3康美自营代销*/
	public Integer getShopSort() {
		return shopSort;
	}
	/**商家类别 ：1所有；2指定商家；3康美自营代销*/
	public void setShopSort(Integer shopSort) {
		this.shopSort = shopSort;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getPromotionPrizeNum() {
		return promotionPrizeNum;
	}

	public void setPromotionPrizeNum(Integer promotionPrizeNum) {
		this.promotionPrizeNum = promotionPrizeNum;
	}

	public BigDecimal getPromotionData() {
		return promotionData;
	}

	public void setPromotionData(BigDecimal promotionData) {
		this.promotionData = promotionData;
	}

	public String getPromotionNote() {
		return promotionNote;
	}

	public void setPromotionNote(String promotionNote) {
		this.promotionNote = promotionNote;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
}
