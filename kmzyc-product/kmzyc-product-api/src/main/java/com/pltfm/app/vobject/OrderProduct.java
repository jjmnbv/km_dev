package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author hewenfeng
 *
 */
public class OrderProduct  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3941681152813137797L;
	/** 产品SKU ID */
	private Long productSkuID;
	/** 产品ID */
	private Long productID;
	/** 产品购买数量 */
	private int amount;
	/** 原始单价 */
	private BigDecimal costPrice;
	
	/**交易单价*/
	private BigDecimal dealPrice;
	
	/**最后单价 */
	private BigDecimal lastPrice;
	/**
	 * 所有活动（特价打折满减满送）
	 */
	private Map<Long,PromotionInfo> promotionInfoMap;
	
	/**
	 * 如果是套装明细，必须存放套装Id 
	 */
	private Long suitId;
	
	/**
	 * 排他
	 */
	private Boolean exclueAll;
	
	/**
	 * 产品code
	 */
	private String productCode;
	/**
	 * 商品sku code
	 */
	private String productSkuCode;
	
	/**
	 * 
	 */
	private String mutualIds;
	
	public String getMutualIds() {
		return mutualIds;
	}
	public void setMutualIds(String mutualIds) {
		this.mutualIds = mutualIds;
	}
	/**
	 * 如果是套装明细，必须存放套装Id 
	 */
	public Long getSuitId() {
		return suitId;
	}
	/**
	 * 如果是套装明细，必须存放套装Id 
	 */
	public void setSuitId(Long suitId) {
		this.suitId = suitId;
	}

	public Long getProductSkuID() {
		return productSkuID;
	}

	public void setProductSkuID(Long productSkuID) {
		this.productSkuID = productSkuID;
	}

	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
		this.productID = productID;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	/** 原始单价 */
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	/** 原始单价 */
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	/**交易单价*/
	public BigDecimal getDealPrice() {
		return dealPrice;
	}
	/**交易单价*/
	public void setDealPrice(BigDecimal dealPrice) {
		this.dealPrice = dealPrice;
	}
	/**最后单价 */
	public BigDecimal getLastPrice() {
		return lastPrice;
	}
	/**最后单价 */
	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}
	/**
	 * 所有活动（特价打折满减满送）
	 */
	public Map<Long, PromotionInfo> getPromotionInfoMap() {
		if(promotionInfoMap==null){
			promotionInfoMap = new ConcurrentHashMap<Long, PromotionInfo>();
		}
		return promotionInfoMap;
	}
	/**
	 * 所有活动（特价打折满减满送）
	 */
	public void setPromotionInfoMap(Map<Long, PromotionInfo> promotionInfoMap) {
		this.promotionInfoMap = promotionInfoMap;
	}
	/**
	 * 按类型移除
	 * @param type
	 */
	public void removePromotionInMap(Integer type){
		promotionInfoMap = this.getPromotionInfoMap();
		Set<Long> keySet = promotionInfoMap.keySet();
		Iterator<Long> iterator = keySet.iterator();
		List<Long> removeIds = new ArrayList<Long>();
		while(iterator.hasNext()){
			Long key = iterator.next();
			PromotionInfo info = promotionInfoMap.get(key);
			if(info.getPromotionType().compareTo(type)==0){
				removeIds.add(info.getPromotionId());
			}
		}
		keySet.removeAll(removeIds);
		this.setPromotionInfoMap(promotionInfoMap);
		
	}
	/**
	 * 按互斥id移除
	 * @param type
	 */
	public void removePromotionInMap(String mutualIds) {
		// TODO Auto-generated method stub
		if(mutualIds==null||mutualIds.isEmpty()){
			return;
		}
		String[] ids = mutualIds.split(",");
		if(ids==null||ids.length==0){
			return;
		}
		promotionInfoMap = this.getPromotionInfoMap();
		for(String id:ids){
			if(id!=null&&!id.isEmpty())
			promotionInfoMap.remove(Long.valueOf(id));
		}
		this.setPromotionInfoMap(promotionInfoMap);
	}
	public Boolean getExclueAll() {
		return exclueAll;
	}
	public void setExclueAll(Boolean exclueAll) {
		this.exclueAll = exclueAll;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductSkuCode() {
		return productSkuCode;
	}
	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}
	
}
