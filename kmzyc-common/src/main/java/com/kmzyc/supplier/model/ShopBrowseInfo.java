package com.kmzyc.supplier.model;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺浏览量
 * @author Administrator
 * 20150414
 */
public class ShopBrowseInfo implements Serializable{
	
	/**
	 * 供应商所对应的店铺id
	 */
	private String shopId;
	
	/**
	 * 分页返回的总记录数    由于需求变更,该字段作废0417 该有ngroups替代
	 */
	private Integer count;
	
	/**
	 * 一页显示多少数据量
	 */
	private Integer ps;
	
	/**
	 * 页码
	 */
	private Integer pn;
	
	
	/**
	 * 标题匹配查询
	 */
	private String titleForQuery;
	/**
	 * url地址匹配查询
	 */
	private String urlForQuery;
	
	/**
	 * 错误消息
	 */
	private String errorMsg;
	
	/**
	 * 查询时间类型
	 * 0 所有：起止时间为空（不限时间范围）；
	 * 1 当天：起止时间都自动选择当前日期；
	 * 2 最近七天：开始时间为当前日期减去6天，截止时间为当天；
	 * 3 当月：开始时间为当月1日，截止时间为当天；
	 * 4 上月：开始时间为上月1日，截止时间为上月最后一天； 
	 */
	private String timeTypeQuery;
	
	
	/**
	 * 搜索范围内的总点击量
	 */
	private String matches;
	
	/**
	 * 查询出来的总记录数
	 */
	private Integer ngroups;
	
	
	/**
	 * 这是jsonData 为分组数据准备的
	 */
	private String jsonDataForGroup;
	
	/**
	 * 店铺浏览量详细
	 */
	private List<ShopBrowseInfoDetail> entity;

	/**
	 * 供查询的日期
	 */
	private String startDate;
	private String endDate;
	
	
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPs() {
		return ps;
	}

	public void setPs(Integer ps) {
		this.ps = ps;
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}


	public String getTitleForQuery() {
		return titleForQuery;
	}

	public void setTitleForQuery(String titleForQuery) {
		this.titleForQuery = titleForQuery;
	}

	public String getUrlForQuery() {
		return urlForQuery;
	}

	public void setUrlForQuery(String urlForQuery) {
		this.urlForQuery = urlForQuery;
	}

	public String getTimeTypeQuery() {
		return timeTypeQuery;
	}

	public void setTimeTypeQuery(String timeTypeQuery) {
		this.timeTypeQuery = timeTypeQuery;
	}

	public List<ShopBrowseInfoDetail> getEntity() {
		return entity;
	}

	public void setEntity(List<ShopBrowseInfoDetail> entity) {
		this.entity = entity;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getMatches() {
		return matches;
	}

	public void setMatches(String matches) {
		this.matches = matches;
	}

	public Integer getNgroups() {
		return ngroups;
	}

	public void setNgroups(Integer ngroups) {
		this.ngroups = ngroups;
	}

	public String getJsonDataForGroup() {
		return jsonDataForGroup;
	}

	public void setJsonDataForGroup(String jsonDataForGroup) {
		this.jsonDataForGroup = jsonDataForGroup;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	

}
