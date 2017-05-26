package com.kmzyc.supplier.model;

import java.io.Serializable;

/**
 * 店铺浏览量具体的信息,调用接口返回的result json数组,用于封装
 * @author Administrator
 * 20150414
 */
public class ShopBrowseInfoDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 浏览量
	 */
	private Integer count;
	
	/**
	 * url地址
	 */
	private String url;
	
	/**
	 * url地址所对应的标题
	 */
	private String title;
	
	
	/**
	 * 分组统计日期
	 */
	private String groupDate;
	/**
	 * 分组统计总量
	 */
	private Integer groupTotalCount;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGroupDate() {
		return groupDate;
	}

	public void setGroupDate(String groupDate) {
		this.groupDate = groupDate;
	}

	public Integer getGroupTotalCount() {
		return groupTotalCount;
	}

	public void setGroupTotalCount(Integer groupTotalCount) {
		this.groupTotalCount = groupTotalCount;
	}

}
