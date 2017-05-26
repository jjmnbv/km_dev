package com.kmzyc.supplier.action;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.page.Pagination;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.enums.ShopAuditStatus;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.ShopMainDraft;
import com.kmzyc.supplier.service.ShopMainDraftService;
import com.kmzyc.supplier.service.ShopMainService;

@Scope("prototype")
@Controller("shopMainAction")
public class ShopMainAction extends SupplierBaseAction {

	private static Logger logger = LoggerFactory.getLogger(ShopMainAction.class);
	
	@Resource
	private ShopMainService shopMainService;
	
	@Resource
	private ShopMainDraftService shopMainDraftService;
	
	private ShopMainDraft shopMainDraft;
	
	private ShopMain shopMain;
	
	private String shopId;
	
	private Long userId;
	
	private Long supplierId;
	
	private String reType;
	
	public String toAddOrUpdateShopMain(){
		setShopMainServiceTypeMap();
		setShopMainTypeMap();

		try{
            supplierId = getSupplyId();
            shopMainDraft = shopMainDraftService.findShopMainDraftByIdWithoutBLOBs(supplierId);
			shopMain = shopMainService.findShopMainByIdWithoutBLOBs(supplierId);
			if (shopMainDraft == null && shopMain == null) {
				return "add";
			}

			//店铺草稿在审核通过之前，都将跳转至修改草稿信息页面
			if (shopMainDraft != null && shopMain == null && (shopMainDraft.getAuditStatus().equals(ShopAuditStatus.EDIT.getStatus())
					|| shopMainDraft.getAuditStatus().equals(ShopAuditStatus.APPLY.getStatus())
					|| shopMainDraft.getAuditStatus().equals(ShopAuditStatus.NOTPASS.getStatus()))) {
				return "update";
			}
			if (shopMainDraft != null && shopMain != null && (shopMainDraft.getAuditStatus().equals(ShopAuditStatus.EDIT.getStatus())
					|| shopMainDraft.getAuditStatus().equals(ShopAuditStatus.APPLY.getStatus()))) {
				return "update";
			}
		} catch(Exception e){
			logger.error("店铺信息查询错误："+e.getMessage(), e);
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String shopMainList(){
		try{
			Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
			if (shopMain == null) {
				shopMain = new ShopMain();
			}
			shopMain.setSupplierId(getSupplyId());
			pagintion = shopMainService.searchPage(shopMain, page);
		} catch(Exception e){
			logger.error("店铺列表查询错误："+e.getMessage(), e);
			return ERROR;
		}
		setShopMainStatusMap();
		setShopAuditStatusMap();
		return SUCCESS;
	}
	
	public String toAddShopMain(){
		setShopMainServiceTypeMap();
		setShopMainTypeMap();
		return SUCCESS;
	}
	
	public String toEditShopMain(){
		try{
			shopMain = shopMainService.findShopMainById(Long.valueOf(shopId));
		} catch(Exception e){
			logger.error("toEditShopMain:" + e.getMessage(), e);
			return ERROR;
		}
		setShopMainServiceTypeMap();
		setShopMainTypeMap();
		return SUCCESS;
	}
	
	public String toViewShopMain(){
		try {
			shopMain = shopMainService.findShopMainById(Long.valueOf(shopId));
		} catch (Exception e) {
			logger.error("toViewShopMain:" + e.getMessage(), e);
			return ERROR;
		}
		setShopMainServiceTypeMap();
		setShopMainTypeMap();
		return SUCCESS;
	}
	
	
	/**
	 * 供应商去申请店铺
	 * @return
	 */
	public String goAddShop(){
		setShopMainServiceTypeMap();
		setShopMainTypeMap();
		return SUCCESS;
	}
	
	/**
	 * 店铺商家介绍
	 * @return
	 */
	public String toShopMainDescribe(){
		try {
			shopMain = shopMainService.findShopMainBySupplierId(getSupplyId());
		} catch (Exception e) {
			logger.error("toShopMainDescribe:" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public ShopMain getShopMain() {
		return shopMain;
	}

	public void setShopMain(ShopMain shopMain) {
		this.shopMain = shopMain;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getReType() {
		return reType;
	}

	public void setReType(String reType) {
		this.reType = reType;
	}

	public ShopMainDraft getShopMainDraft() {
		return shopMainDraft;
	}

	public void setShopMainDraft(ShopMainDraft shopMainDraft) {
		this.shopMainDraft = shopMainDraft;
	}

}