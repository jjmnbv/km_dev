package com.kmzyc.supplier.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.km.framework.page.Pagination;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.model.SupplierFare;
import com.kmzyc.supplier.service.SupplierFareService;

public class SupplierFareAction extends SupplierBaseAction{

	private static Logger logger = LoggerFactory.getLogger(SupplierFareAction.class);
	
	@Resource
	private SupplierFareService supplierFareService;
	
	private SupplierFare supplierFare;
	
	private Long fareId;
	
	/**
	 * 去供应商运费管理页面
	 * @return
	 */
	public String toSupplierFare(){
		try{
            Long supplierId = getSupplyId();
            int count=supplierFareService.countFareBySupplierId(supplierId);
		    if(count==0){
		        return SUCCESS;
		    }

			Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
			if(supplierFare==null) {
				supplierFare = new SupplierFare();
			}
			supplierFare.setSupplierId(supplierId);
			pagintion=supplierFareService.searchPage(supplierFare, page);
			return "listPage";
		}catch(Exception e){
			logger.error("运费列表查询错误："+e.getMessage());
			return ERROR;
		}
	}
	
	/**
	 * 去运费添加页面
	 * @return
	 */
	public String toAddFare(){
		return SUCCESS;
	}

	public String toUpdateFare(){
		try{
			supplierFare=supplierFareService.findByFareId(fareId);
		}catch(Exception e){
			logger.error("修改页面查询数据出现错误："+e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
	public SupplierFare getSupplierFare() {
		return supplierFare;
	}

	public void setSupplierFare(SupplierFare supplierFare) {
		this.supplierFare = supplierFare;
	}

	public Long getFareId() {
		return fareId;
	}

	public void setFareId(Long fareId) {
		this.fareId = fareId;
	}
}
