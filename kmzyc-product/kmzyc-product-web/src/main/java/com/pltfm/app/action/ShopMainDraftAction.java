package com.pltfm.app.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.supplier.enums.ShopAuditStatus;
import com.kmzyc.supplier.enums.ShopMainStatus;
import com.kmzyc.supplier.model.ShopMainDraft;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.maps.SuppliersShopStatusMap;
import com.pltfm.app.service.ShopMainDraftService;
import com.kmzyc.commons.page.Page;

@Controller("shopMainDraftAction")
@Scope(value = "prototype")
public class ShopMainDraftAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ShopMainDraftAction.class);
	
	@Resource
	private ShopMainDraftService shopMainDraftService;
	private Page page;//分页
	private ShopMainDraft searchShopMainDraft;//查询条件
	private Long shopId;//店铺id
	private ShopMainDraft showShopMainDraft = new ShopMainDraft();//用于保存店铺信息
	private String ckType;//判断类型
	private String describe;//审核不通过原因
	private List<Long> shopIdList;
	private String auditStatus;
	private String type;
	
	public String showShopMainDraftList(){
		try{
			if(page == null) page =  new Page();
			if(searchShopMainDraft == null) searchShopMainDraft =  new ShopMainDraft();
			getRequest().setAttribute("suppliersStatusMap",SuppliersShopStatusMap.getMap());//审核状态集合
			shopMainDraftService.supplierShopDraftListShow(page, searchShopMainDraft);
		}catch(Exception e){
			logger.error("查询供应商店铺列表出现异常" + e.getMessage(), e);
		}
		return Action.SUCCESS;
	}
	
	public String toViewShopMainDraft(){
		try{
			showShopMainDraft=shopMainDraftService.supplierShopDraftView(shopId);
		}catch(Exception e){
			logger.error("供应商店铺显示出现异常" + e.getMessage(), e);
		}
		
		return type;
	}
	
	public String updateShoMainDraftAudit(){
		int result = 0;
		try{
			List<ShopMainDraft> smdList = new ArrayList<ShopMainDraft>();
			ShopMainDraft smd = null;
			for(Long sId : shopIdList){
				smd = new ShopMainDraft();
				
				smd.setShopId(sId);
				smd.setAuditStatus(auditStatus);
				if(auditStatus.equals(ShopAuditStatus.AUDIT.getStatus())){
					describe = "审核通过！店铺设置信息已生效！";
					smd.setStatus(ShopMainStatus.OPEN.getStatus());
				}else{
					describe = "审核未通过！原因：" + describe;
				}
				smd.setRemark(describe);
				
				smdList.add(smd);
			}
			result = shopMainDraftService.updateShopMainDraftBatch(smdList, auditStatus, shopIdList);
		}catch(Exception e){
			logger.error("供应商店铺审核显示出现异常" + e.getMessage(), e);
		}
		super.writeJson(result);
		return null;
	}
	
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public ShopMainDraft getSearchShopMainDraft() {
		return searchShopMainDraft;
	}
	public void setSearchShopMainDraft(ShopMainDraft searchShopMainDraft) {
		this.searchShopMainDraft = searchShopMainDraft;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public ShopMainDraft getShowShopMainDraft() {
		return showShopMainDraft;
	}
	public void setShowShopMainDraft(ShopMainDraft showShopMainDraft) {
		this.showShopMainDraft = showShopMainDraft;
	}
	public String getCkType() {
		return ckType;
	}
	public void setCkType(String ckType) {
		this.ckType = ckType;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public List<Long> getShopIdList() {
		return shopIdList;
	}

	public void setShopIdList(List<Long> shopIdList) {
		this.shopIdList = shopIdList;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
