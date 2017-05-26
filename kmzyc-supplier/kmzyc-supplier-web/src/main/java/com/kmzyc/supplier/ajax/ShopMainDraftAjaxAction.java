package com.kmzyc.supplier.ajax;

import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.ShopMainDraft;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.enums.ShopAuditStatus;
import com.kmzyc.supplier.service.ShopMainDraftService;
import com.kmzyc.supplier.util.ObjectPropertyCopyUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

@Controller("shopMainDraftAjaxAction")
@Scope("prototype")
public class ShopMainDraftAjaxAction extends SupplierBaseAction {
	
	private static Logger logger = LoggerFactory.getLogger(ShopMainDraftAjaxAction.class);

	@Resource
	private ShopMainDraftService shopMainDraftService;
	
	private ShopMain shopMain;
	
	private ShopMainDraft shopMainDraft;
	
	private String shopId;
	
	private String[] shopIds;
	
	private String oldName;
	
	public String deleteShopMainDraft(){
		Map<String, Object> jsonMap = new HashMap();
		try{
			int count = shopMainDraftService.deleteByShopId(Long.valueOf(shopId));
			boolean haveDone = count > 0;
			jsonMap.put("flag", haveDone);
			jsonMap.put("msg", haveDone ? "店铺删除成功!" : "店铺删除失败!");
		} catch(Exception e){
			logger.error("删除店铺错误："+e.getMessage(), e);
			jsonMap.put("flag", false);
			jsonMap.put("msg", "店铺删除失败!");
		}

		writeJson(jsonMap);
		return null;
	}
	
	public String deleteMsg(){
		try{
			shopMainDraft = new ShopMainDraft();
			shopMainDraft.setShopId(Long.valueOf(shopId));
			shopMainDraft.setRemark("");//消息置空
			shopMainDraftService.updateById(shopMainDraft);
		} catch(Exception e){
			logger.error("店铺修改错误："+e.getMessage(), e);
		}

		return null;
	}
	
	public String updateShopMainDraft(){
		Map<String, Object> jsonMap = new HashMap();
		Map<String,Object> conditionMap = new HashMap();
		conditionMap.put("shopName", shopMainDraft.getShopName());
		conditionMap.put("oldName", oldName);
		conditionMap.put("shopId", shopMainDraft.getShopId());

		try{
			if(shopMainDraftService.selectRepeatNameForUpdateShopMain(conditionMap) > 0){
				jsonMap.put("flag", false);
				jsonMap.put("msg", "【"+shopMainDraft.getShopName()+"】该店铺名已存在，请重命名!");
				writeJson(jsonMap);
				return null;
			}

			shopMainDraft.setApplyUser(super.getLoginUserId());
			shopMainDraft.setApplyTime(new Date());
			shopMainDraft.setSupplierId(super.getSupplyId());
			shopMainDraft.setRemark("审核中，审核时间大概需要1~3个工作日。请耐心等候。");
			shopMainDraft.setAuditStatus(ShopAuditStatus.APPLY.getStatus());//修改后变为“申请审核”状态
			int count = shopMainDraftService.updateById(shopMainDraft);
			boolean haveDone = count > 0;
			jsonMap.put("flag", haveDone);
			jsonMap.put("msg", haveDone ? "店铺修改成功!" : "店铺修改失败!");
			jsonMap.put("shopId", shopMainDraft.getShopId());
		} catch(Exception e){
			logger.error("店铺修改错误："+e.getMessage(), e);
			jsonMap.put("flag", false);
			jsonMap.put("msg", "店铺修改失败!");
		}

		writeJson(jsonMap);
		return null;
	}
	
	public String toEditOfficialData(){
		Map<String, Object> jsonMap = new HashMap();
		if (shopMain == null) {
			jsonMap.put("flag", false);
			jsonMap.put("msg", "店铺信息提交审核失败，请稍后再试或联系客服人员！");
			writeJson(jsonMap);
			return null;
		}

		Map<String,Object> conditionMap = new HashMap();
		conditionMap.put("shopName", shopMain.getShopName());
		conditionMap.put("shopId", shopMain.getShopId());

		try {
			if(shopMainDraftService.selectRepeatNameForUpdateShopMain(conditionMap) > 0){
				jsonMap.put("flag", false);
				jsonMap.put("msg", "【" + shopMain.getShopName() + "】该店铺名已存在，请重命名！");
				writeJson(jsonMap);
				return null;
			}
			
			ShopMainDraft shopMainDraft = shopMainDraftService.findByShopId(Long.valueOf(shopId));
			shopMain.setRemark("审核中，审核时间大概需要1~3个工作日。请耐心等候。");
			shopMain.setApplyUser(super.getLoginUserId());
			shopMain.setApplyTime(new Date());
			shopMain.setSupplierId(super.getSupplyId());
			shopMain.setAuditStatus(ShopAuditStatus.APPLY.getStatus());//修改后变为“申请审核”状态
			if(shopMainDraft == null){
				shopMainDraft = new ShopMainDraft();
				ObjectPropertyCopyUtil.propertyCopy(shopMain, shopMainDraft);
				shopMainDraftService.insertAndUpdateShopMainDraft(shopMainDraft);
			}else{
				ObjectPropertyCopyUtil.propertyCopy(shopMain, shopMainDraft);
				shopMainDraftService.updateById(shopMainDraft);
			}
			jsonMap.put("flag", true);
			jsonMap.put("msg", "提交审核成功！");
		} catch (Exception e) {
			logger.error("店铺修改错误："+e.getMessage(), e);
			jsonMap.put("flag", false);
			jsonMap.put("msg", "系统发生错误，请稍后再试或联系客服人员！");
		}

		writeJson(jsonMap);
		return null;
	}
	
	public String applyShopMainDraft(){
		Map<String, Object> jsonMap = new HashMap();
		ShopMainDraft shopDraft = new ShopMainDraft();
		shopDraft.setShopId(Long.valueOf(shopId));
		shopDraft.setAuditStatus(ShopAuditStatus.APPLY.getStatus());

		try{
			int count = shopMainDraftService.updateById(shopDraft);
			boolean haveDone = count > 0;
			jsonMap.put("flag", haveDone);
			jsonMap.put("msg", haveDone ? "店铺申请成功!" : "店铺申请失败!");
		} catch(Exception e){
			logger.error("店铺申请错误："+e.getMessage(), e);
			jsonMap.put("flag", false);
			jsonMap.put("msg", "店铺申请失败!");
		}

		writeJson(jsonMap);
		return null;
	}
	
	public String cancelShopMainDraft(){
		Map<String, Object> jsonMap = new HashMap();
		ShopMainDraft shopDraft = new ShopMainDraft();
		shopDraft.setShopId(Long.valueOf(shopId));
		shopDraft.setAuditStatus(ShopAuditStatus.EDIT.getStatus());

		try{
			int count = shopMainDraftService.updateById(shopDraft);
			boolean haveDone = count > 0;
			jsonMap.put("flag", haveDone);
			jsonMap.put("msg", haveDone ? "店铺撤回申请成功!" : "店铺撤回申请失败!");
		} catch(Exception e){
			logger.error("店铺撤回申请错误："+e.getMessage(), e);
			jsonMap.put("flag", false);
			jsonMap.put("msg", "店铺撤回申请失败!");
		}

		writeJson(jsonMap);
		return null;
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

	public String[] getShopIds() {
        String[] temp = shopIds;
		return temp;
	}

	public void setShopIds(String[] shopIds) {
		this.shopIds = shopIds;
	}

	public ShopMainDraft getShopMainDraft() {
		return shopMainDraft;
	}

	public void setShopMainDraft(ShopMainDraft shopMainDraft) {
		this.shopMainDraft = shopMainDraft;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	
}
