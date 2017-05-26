package com.kmzyc.supplier.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.RechargeDetails;
import com.kmzyc.supplier.model.SupplierAccountInfo;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.maps.CertificateTypeMap;
import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.supplier.service.AccountService;
import com.kmzyc.supplier.service.SupplierCategorysService;
import com.kmzyc.supplier.service.SupplierCertificateService;
import com.kmzyc.supplier.service.SupplierInfoService;

/**
 * 供应商信息查看action
 *
 * @author luoyi
 * @createDate 2014/01/08
 *
 */
@Controller("supplierInfoAction")
@Scope("prototype")
public class SupplierInfoAction extends SupplierBaseAction {

	private Logger logger = LoggerFactory.getLogger(SupplierInfoAction.class);

	@Resource
	private AccountService accountService;

	@Resource
	private SupplierCategorysService supplierCategorysService;

	@Resource
	private SupplierInfoService supplierInfoService;

	@Resource
	private SupplierCertificateService supplierCertificateService;

	private String deleteInfoId;

	private String deleteFileId;

	private AccountInfo accountInfo;

	private RechargeDetails rechargeDetails = new RechargeDetails();

	private SupplierAccountInfo supplierAccountInfo ;//供应商账户信息

	private MerchantInfo supplierBaseInfo ;//供应商基本信息

	private List<SuppliersAvailableCategorys> suppliserCategoryList;

	private SuppliersInfo suppliersInfo;//供应商信息

	private List<SuppliersCertificate> certificateList;

	/**
	 * 查询供应商基本信息
	 * @return
	 * @throws SQLException
	 */
	private String showBaseSupplierInfo() throws SQLException {
		// 登录的用户
		Long userId = getLoginUserId();
		try {
			//查询供应商级别和余额信息
			supplierAccountInfo = accountService.findSupplierAccountInfoByUserId(userId);
			//查询供应商公司信息
			supplierBaseInfo = accountService.findSupplierBaseInfoByUserId(userId);
			//获得供应商信息
			suppliersInfo = supplierInfoService.findSuppliersInfo(supplierBaseInfo.getMerchantId());
			//查询供应商供应范围
			suppliserCategoryList = supplierCategorysService.findSupplierCategoriesBySupplierId(suppliersInfo.getSupplierId());
		} catch (Exception e) {
			logger.error("查询供应商基本信息出错{}.", e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 查询
	 * @return
	 */
	public String showSupplierInfo(){
		try {
			// 登录的用户
			showBaseSupplierInfo();
			//旧资质信息列表
			Long supplierId = getSupplyId();
			certificateList = supplierCertificateService.findCertificateListBySupplierId(supplierId);
			//将证件类型枚举放到Request中
			getRequest().setAttribute("certificateTypeMap", CertificateTypeMap.getMap());
		} catch (Exception e) {
			logger.error("查看供应信息出错{}.", e);
			return ERROR;
		}
		return SUCCESS;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}

	public RechargeDetails getRechargeDetails() {
		return rechargeDetails;
	}

	public void setRechargeDetails(RechargeDetails rechargeDetails) {
		this.rechargeDetails = rechargeDetails;
	}

	public SupplierAccountInfo getSupplierAccountInfo() {
		return supplierAccountInfo;
	}

	public void setSupplierAccountInfo(SupplierAccountInfo supplierAccountInfo) {
		this.supplierAccountInfo = supplierAccountInfo;
	}

	public MerchantInfo getSupplierBaseInfo() {
		return supplierBaseInfo;
	}

	public void setSupplierBaseInfo(MerchantInfo supplierBaseInfo) {
		this.supplierBaseInfo = supplierBaseInfo;
	}

	public List<SuppliersAvailableCategorys> getSuppliserCategoryList() {
		return suppliserCategoryList;
	}

	public void setSuppliserCategoryList(
			List<SuppliersAvailableCategorys> suppliserCategoryList) {
		this.suppliserCategoryList = suppliserCategoryList;
	}

	public SuppliersInfo getSuppliersInfo() {
		return suppliersInfo;
	}

	public void setSuppliersInfo(SuppliersInfo suppliersInfo) {
		this.suppliersInfo = suppliersInfo;
	}

	public List<SuppliersCertificate> getCertificateList() {
		return certificateList;
	}

	public void setCertificateList(List<SuppliersCertificate> certificateList) {
		this.certificateList = certificateList;
	}

	public String getDeleteInfoId() {
		return deleteInfoId;
	}

	public void setDeleteInfoId(String deleteInfoId) {
		this.deleteInfoId = deleteInfoId;
	}

	public String getDeleteFileId() {
		return deleteFileId;
	}

	public void setDeleteFileId(String deleteFileId) {
		this.deleteFileId = deleteFileId;
	}

}
