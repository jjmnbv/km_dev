package com.pltfm.activity.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.commons.page.Page;
import com.pltfm.activity.service.ActivityPromotionEffectService;
import com.pltfm.app.action.BaseAction;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;

/**
 * 活动效果推广
 * 
 * @author wanwen
 *
 */
@Controller("activityPromotionEffectAction")
@Scope(value = "prototype")
public class ActivityPromotionEffectAction extends BaseAction {

	private static final long serialVersionUID = -8080398939458014483L;

	@Resource
	private ActivityPromotionEffectService promotionEffectService;

	private ActivityInfo activityParam;

	private ActivitySupplierEntry supplierEntryParam;

	private ActivitySku activitySkuParam;

	private OrderMain orderMainParam;

	private Page page = new Page();

	private String supplierEntryId;

	private String skuId;

	private String orderStatusForMenuQuery;

	private String orderCodeForSearch;

	private String orderStatus;

	private static final String RISK_PASS = "22"; // 风控通过

	private static final String STOCK_LOCK = "20"; // 已锁库存

	private String entryStartTime; // 报名开始时间

	private String entryEndTime; // 报名结束时间

	private String activityStartTime; // 活动开始时间

	private String activityEndTime; // 活动结束时间

	private Integer activityType; // 活动类型

	private String activityId;

	/**
	 * 分页获取活动推广效果列表
	 *
	 * @return
	 */
	public String findPromotionEffectList() {
		logger.debug("开始请求查询活动推广效果列表,参数{}", JSONObject.toJSONString(activityParam));
		if (activityParam == null) {
			activityParam = new ActivityInfo();
		}
		if (StringUtils.isNotBlank(entryStartTime) && StringUtils.isNotBlank(entryEndTime)) {
			activityParam.setEntryStartTime(Timestamp.valueOf(entryStartTime.trim()));
			activityParam.setEntryEndTime(Timestamp.valueOf(entryEndTime.trim()));
		}
		if (StringUtils.isNotBlank(activityStartTime) && StringUtils.isNotBlank(activityEndTime)) {
			activityParam.setActivityStartTime(Timestamp.valueOf(activityStartTime.trim()));
			activityParam.setActivityEndTime(Timestamp.valueOf(activityEndTime.trim()));
		}
		try {
			promotionEffectService.queryPromotionEffectListForPage(activityParam, page);
		} catch (Exception e) {
			logger.error("查询活动推广效果列表出错:" + e);
			return ERROR;
		}
		// 活动类型
		setActivityTypeMap();
		// 活动收费类型
		setActivityChargeTypeMap();

		return SUCCESS;
	}

	/**
	 * 分页获取活动商家销量统计
	 * 
	 * @return
	 */
	public String findActivitySupplierSalesList() {
		logger.debug("开始请求查询活动商家销量统计,参数{}", JSONObject.toJSONString(supplierEntryParam));
		if (supplierEntryParam == null) {
			supplierEntryParam = new ActivitySupplierEntry();
		}
		try {
			this.promotionEffectService.queryActivitySuppliersSales(page, supplierEntryParam);
		} catch (Exception e) {
			logger.error("查询活动商家销量统计出错:" + e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 分页查询促销活动商家推广商品销量
	 * 
	 * @return
	 */
	public String findPromotionActivitySupplierSales() {
		logger.debug("开始请求查询促销活动商家推广商品销量,参数{}", JSONObject.toJSONString(activitySkuParam));
		if (activitySkuParam == null) {
			activitySkuParam = new ActivitySku();
		}
		try {
			this.promotionEffectService.getSPAndTxtSupplierProductSales(page, activitySkuParam);
		} catch (Exception e) {
			logger.error("查询促销活动商家推广商品销量出错:" + e);
			return ERROR;
		}
		getRequest().setAttribute("activityType", activityType);
		return SUCCESS;
	}

	/**
	 * 分页查询图文活动商家推广商品销量
	 * 
	 * @return
	 */
	public String findTeletextActivitySupplierSales() {
		logger.debug("开始请求查询图文活动商家推广商品销量,参数{}", JSONObject.toJSONString(activitySkuParam));
		if (activitySkuParam == null) {
			activitySkuParam = new ActivitySku();
		}
		try {
			this.promotionEffectService.getSPAndTxtSupplierProductSales(page, activitySkuParam);
		} catch (Exception e) {
			logger.error("查询图文活动促销活动商家推广商品销量出错:" + e);
			return ERROR;
		}
		getRequest().setAttribute("activityType", activityType);
		return SUCCESS;
	}

	/**
	 * 分页获取渠道活动商家推广商品销量
	 * 
	 * @return
	 */
	public String findChannelSupplierProductSalesList() {
		logger.debug("开始请求查询渠道活动商家推广商品销量,参数{}", JSONObject.toJSONString(activitySkuParam));
		if (activitySkuParam == null) {
			activitySkuParam = new ActivitySku();
		}
		try {
			this.promotionEffectService.getChannelSupplierProductSales(page, activitySkuParam);
		} catch (Exception e) {
			logger.error("查询渠道活动商家推广商品销量出错:" + e);
			return ERROR;
		}
		getRequest().setAttribute("activityType", activityType);
		return SUCCESS;
	}

	/**
	 * 查询活动商家追加推广明细
	 * 
	 * @return
	 */
	public String findSupplierAppendProductList() {
		logger.debug("开始请求查询活动商家追加推广明细,参数{}", JSONObject.toJSONString(activitySkuParam));
		if (activitySkuParam == null) {
			activitySkuParam = new ActivitySku();
		}
		try {
			this.promotionEffectService.querySupplierAppendProductList(page, activitySkuParam);
		} catch (Exception e) {
			logger.error("查询活动商家追加推广明细出错:" + e);
			return ERROR;
		}
		getRequest().setAttribute("activityType", activityType);
		return SUCCESS;
	}

	/**
	 * 查询活动SKU销售明细
	 * 
	 * @return
	 */
	public String findActivitySkuSalesDetail() {
		logger.debug("开始请求查询活动SKU销售明细,参数{}", JSONObject.toJSONString(orderMainParam));
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(orderStatusForMenuQuery)) {
			String finalStatus = orderStatusForMenuQuery;
			List<String> statusList = new ArrayList<String>();
			if (RISK_PASS.equals(orderStatusForMenuQuery)) {
				statusList.add(orderStatusForMenuQuery);
				statusList.add(STOCK_LOCK);
				conditionMap.put("statusArr", statusList);
			} else {
				conditionMap.put("status", finalStatus);
			}
		}
		if (StringUtils.isNotEmpty(orderCodeForSearch))
			conditionMap.put("orderCode", orderCodeForSearch.trim());
		try {
			this.promotionEffectService.querySkuSalesDetail(supplierEntryId, skuId, page, conditionMap);
		} catch (Exception e) {
			logger.error("查询活动SKU销售明细出错:" + e);
			return ERROR;
		}
		setOrderStatusMapForOrderQuery();
		getRequest().setAttribute("activityId", activityId);
		getRequest().setAttribute("activityType", activityType);
		return SUCCESS;
	}

	public ActivityInfo getActivityParam() {
		return activityParam;
	}

	public void setActivityParam(ActivityInfo activityParam) {
		this.activityParam = activityParam;
	}

	@Override
    public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public ActivitySupplierEntry getSupplierEntryParam() {
		return supplierEntryParam;
	}

	public void setSupplierEntryParam(ActivitySupplierEntry supplierEntryParam) {
		this.supplierEntryParam = supplierEntryParam;
	}

	public ActivitySku getActivitySkuParam() {
		return activitySkuParam;
	}

	public void setActivitySkuParam(ActivitySku activitySkuParam) {
		this.activitySkuParam = activitySkuParam;
	}

	public String getSupplierEntryId() {
		return supplierEntryId;
	}

	public void setSupplierEntryId(String supplierEntryId) {
		this.supplierEntryId = supplierEntryId;
	}

	public OrderMain getOrderMainParam() {
		return orderMainParam;
	}

	public void setOrderMainParam(OrderMain orderMainParam) {
		this.orderMainParam = orderMainParam;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getOrderStatusForMenuQuery() {
		return orderStatusForMenuQuery;
	}

	public void setOrderStatusForMenuQuery(String orderStatusForMenuQuery) {
		this.orderStatusForMenuQuery = orderStatusForMenuQuery;
	}

	public String getOrderCodeForSearch() {
		return orderCodeForSearch;
	}

	public void setOrderCodeForSearch(String orderCodeForSearch) {
		this.orderCodeForSearch = orderCodeForSearch;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getEntryStartTime() {
		return entryStartTime;
	}

	public void setEntryStartTime(String entryStartTime) {
		this.entryStartTime = entryStartTime;
	}

	public String getEntryEndTime() {
		return entryEndTime;
	}

	public void setEntryEndTime(String entryEndTime) {
		this.entryEndTime = entryEndTime;
	}

	public String getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(String activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public String getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

}
