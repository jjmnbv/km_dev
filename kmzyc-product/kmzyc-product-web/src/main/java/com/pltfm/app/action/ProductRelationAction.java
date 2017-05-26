package com.pltfm.app.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.kmzyc.supplier.maps.SuppliersTypeMap;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.pltfm.app.enums.ProductRelationStatus;
import com.pltfm.app.service.CmsProductUpShelfService;
import com.pltfm.app.service.ProductMainTiedService;
import com.pltfm.app.service.ProductRelationDetailService;
import com.pltfm.app.service.ProductRelationService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.util.PurchaseUtils;
import com.pltfm.app.vobject.ProductRelation;
import com.pltfm.app.vobject.ProductRelationDetail;
import com.pltfm.app.vobject.ProductRelationDetailView;
import com.pltfm.app.vobject.ProductmainTied;
import com.kmzyc.commons.page.Page;

@Controller("productRelationAction")
@Scope(value = "prototype")
public class ProductRelationAction extends BaseAction {
	// page 对象
	private Page page = new Page();
	// 查询条件传递进来的
	private ProductmainTied productTied = new ProductmainTied();
	@Resource
	private ProductMainTiedService productMainTiedService;
	// 关联产品的集合
	private List<ProductRelationDetail> list;
	// 主产品信息
	private ProductRelation productRelation;
	// 产品关联 主单服务类
	@Resource
	private ProductRelationService productRelationService;
	// 产品关联 子单服务类
	@Resource
	private ProductRelationDetailService productRelationDetailService;
	// 提示信息
	private String rtnMessage;
	// 主产品的套餐
	private List<ProductRelation> productRelationList;
	// 根据主单的主键删除关联套餐
	private List<Long> productRelationId;
	// 根据子单的 主键删除关联产品
	private List<Long> relationDetailId;
	// 搭售子单信息
	private ProductRelationDetail productRelationDetail;
	// 查询某一关联中的信息
	private Map<ProductRelationDetail, ProductmainTied> viewMap;
	// 关联表 主键id
	private Long relationId;
	// 添加其他关联产品信息 save
	private List<ProductRelationDetail> list1;
	// 查询某个关联 已经关联的产品集合
	private List<ProductRelationDetail> relationEdDetails;
	// 主产品 skuId
	private String mainSkuId;
	// 操作类型 up 上架 down 下架
	private String operateType;
	@Resource
	private ProductSkuService productSkuService;
	// 主产品的价格
	Double mainSkuPrice;
	// 主产品的价格
	Double totalPrice;
	// 关联类型
	Short relationType;
	// 套餐状态
	private Short packageStatus;
	// 跳转页数
	private int pageNum = 0;
	// 调用cms 的接口刷新页面
	@Resource
	private CmsProductUpShelfService cmsProductUpShelfService;
	private Short editable; // 可编辑
	// 套餐渠道
	private String channel;
	// 套餐渠道
	private String productName;
	// 产品关联状态
	private String status;
	private String isRelation;
	private String[] productRelationSku1;
	private String[] productRelationPrice1;
	private String[] productRelationType1;
	private String[] relationID1;
	private List<Long> skuIds;// 保存skuid
	private List<BigDecimal> prices;// 保存商品单价
	private List<Short> proCount;// 保存商品数量
	private ProductRelation selectProductRelation;// 查询条件
	private ProductRelation productRelationUpdate;// 修改使用
	private String oldRIdCorrelationSIds;// 删除使用
	private int supplierTyes;// 标识是自营代销和入驻
	private Long detailId;
	private int updateType;
	private List<Long> relationDetailIdList;// 套餐子表修改id
	private List<Long> skuIdsAdd;// 保存skuid
	private List<BigDecimal> pricesAdd;// 保存商品单价
	private List<Short> proCountAdd;// 保存商品数量
	private int proCounts = 0;// 商品总数量
	private BigDecimal totalPirc = new BigDecimal(0.0);// 总价格
	private String viewType;// 根据此标识判断查看页面显示信息
	private int selectSupplierType;
	private MerchantInfoOrSuppliers selectSuppliersInfo = new MerchantInfoOrSuppliers();// 供应商查询的条件
	private Long bCategoryId;// 一级类目
	private Long mCategoryId;// 二级类目

	/**
	 * 定制管理-产品关联-查询列表
	 * 
	 * @return
	 */
	public String skuProductQuery() {
		productTied.setBCategoryId(bCategoryId);
		productTied.setMCategoryId(mCategoryId);
		try {
			productMainTiedService.selectList(productTied, page, getLoginUserId());
			initCategoryList(productTied.getBCategoryId(), productTied.getMCategoryId(), productTied.getCategoryId());
			setProductStatusMap();
			setProductBrandMap();
		} catch (Exception e) {
			logger.error("定制管理-产品关联-查询列表失败:" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-产品关联-根据主产品skuId 查询出其所有的产品关联至产品关联列表
	 * 
	 * @return
	 */
	public String queryPackage() {
		try {
			if (pageNum != 0) {
				page.setPageNo(pageNum);
			}
			productRelationService.getProductRelationByMainSkuId(productRelation, page);
			getProductRelationType();
			getProductRelationValidStatus();
		} catch (Exception e) {
			logger.error("定制管理-产品关联-根据主产品skuId 查询出其所有的产品关联至产品关联列表失败:" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-产品关联-产品关联列表-删除关联
	 * 
	 * @return
	 */
	public String delPackage() {
		Long[] sIds = PurchaseUtils.strArrToIntArr(getRequest().getParameter("delPackageStr"));
		List<Long> delPackageList = new ArrayList<Long>();
		for (Long id : sIds) {
			delPackageList.add(id);
		}
		PrintWriter out = null;
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			productRelationService.batchDelProductRelationStatus(delPackageList);
			ProductmainTied prod = productMainTiedService.findObjectBySku(Long.valueOf(mainSkuId));
			List<Integer> prodIdList = new ArrayList<Integer>();
			prodIdList.add(prod.getProductId().intValue());
			// 调用cms 的页面进行刷新
			cmsProductUpShelfService.productUpShelfByCms(prodIdList);
			out = super.getPrintWriter();
			String msg = "关联删除成功";
			jsonMap.put("msg", msg);
			out.print(JSON.toJSONString(jsonMap));
		} catch (Exception e) {
			String msg = "关联删除失败";
			jsonMap.put("msg", msg);
			logger.error("定制管理-产品关联-产品关联列表-删除关联失败:" + e.getMessage(), e);
			return null;
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		return null;
	}

	/**
	 * 定制管理-产品关联-产品关联列表-新增产品关联-跳转至新增'产品关联'页面
	 * 
	 * @return
	 */
	public String relationQueryProduct() {

		try {
			if (productTied.getProductSkuId() != null) {
				mainSkuPrice = productSkuService.queryProductSkuList(productTied.getProductSkuId()).getPrice();
			}
			// 关联类型
			getProductRelationType();
		} catch (Exception e) {
			logger.error("定制管理-产品关联-产品关联列表-新增产品关联-跳转至新增'产品关联'页面失败:" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-产品关联-产品关联列表-新增组方关联-跳转至新增'组方关联'页面
	 * 
	 * @return
	 */
	public String relationQueryZFProduct() {
		try {
			if (productTied.getProductSkuId() != null) {
				mainSkuPrice = productSkuService.queryProductSkuList(productTied.getProductSkuId()).getPrice();
			}
			getCategoryList();
		} catch (Exception e) {
			logger.error("定制管理-产品关联-产品关联列表-新增组方关联-跳转至新增'组方关联'页面失败:" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-产品关联（组方关联）-产品关联（组方关联）列表-操作更新有效状态（组方共用 ——> 更新有效状态）
	 * 
	 * @return
	 */
	public String updateStatus() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			productRelationService.updateProductRelationStatus(relationId, status);
			// 调用cms 的页面进行刷新
			ProductmainTied prod = productMainTiedService.findObjectBySku(Long.valueOf(mainSkuId));
			List<Integer> prodIdList = new ArrayList<Integer>();
			prodIdList.add(prod.getProductId().intValue());
			cmsProductUpShelfService.productUpShelfByCms(prodIdList);
			String msg = "更改状态成功";
			jsonMap.put("msg", msg);
		} catch (Exception e) {
			String msg = "更改状态失败";
			jsonMap.put("msg", msg);
			logger.error("定制管理-产品关联（组方关联）-产品关联（组方关联）列表-操作更新有效状态（组方共用 ——> 更新有效状态）失败:" + e.getMessage(), e);
		}
        strWriteJson(JSON.toJSONString(jsonMap));
		return null;
	}

	/**
	 * 定制管理-产品关联-产品关联列表-可编辑状态下的 在一个套餐中查询其关联产品的信息
	 * 
	 * @return
	 */
	public String viewProductDeatil() {
		setCheckedId(productRelationDetail.getRelationId());// 高亮显示
		try {
			// 获得相对应的关联类型
			relationType = productRelationService.queryProductRelation(productRelationDetail.getRelationId()).getRelationType();
			productRelationDetailService.getProductRelationDetailProductSku(productRelationDetail.getRelationId(),
					page);
			setProductStatusMap();
		} catch (Exception e) {
			logger.error("定制管理-产品关联-产品关联列表-可编辑状态下的 在一个套餐中查询其关联产品的信息失败:" + e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-产品关联-关联列表-编辑产品-删除关联产品
	 * 
	 * @return
	 */
	public String delPackageDetail() {
		Long[] sIds = PurchaseUtils.strArrToIntArr(getRequest().getParameter("packageDetailStr"));
		List<Long> delPackageDetailList = new ArrayList<Long>();
		for (Long id : sIds) {
			delPackageDetailList.add(id);
		}
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			productRelationDetailService.batchDelProductRelationDetailByRelationDetailId(delPackageDetailList);
			String msg = "关联产品删除成功";
			jsonMap.put("msg", msg);
            strWriteJson(JSON.toJSONString(jsonMap));
		} catch (Exception e) {
			String msg = "关联产品删除失败";
			jsonMap.put("msg", msg);
            strWriteJson(JSON.toJSONString(jsonMap));
			logger.error("定制管理-产品关联-关联列表-编辑产品-删除关联产品失败" + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 定制管理-产品关联-产品关联列表-新增产品(新增组方产品)查询
	 * 
	 * @return
	 */
	public String addOtherTypeProductRelation() {
		try {
			if (productTied == null) {
                productTied = new ProductmainTied();
            }
			productTied.setUserId(String.valueOf(getLoginUserId()));
			if (relationId != null) {
				productTied.setRelationId(String.valueOf(relationId));
				relationEdDetails = productRelationDetailService.queryProductRelationDetailByRelationId(relationId);
				productMainTiedService.selectListNotMainSKU(productTied, page);
			} else {
				productMainTiedService.selectList(productTied, page);
			}
			// 初始化三级目录
			initCategoryList(productTied.getBCategoryId(), productTied.getMCategoryId(), productTied.getCategoryId());
			setProductStatusMap();
			getCategoryList();
			setProductBrandMap();
		} catch (Exception e) {
			logger.error("定制管理-产品关联-产品关联列表-新增产品(新增组方产品)查询失败:" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;

	}

	/**
	 * 定制管理-产品关联-产品关联列表-保存新添加信息
	 * 
	 * @return
	 */
	public String saveProductRelation() {
		String result = "";
		if ("0".equals(productRelation.getRelationType().toString())) {
			productRelation.setStatus(Short.valueOf(ProductRelationStatus.NEW.getStatus().toString()));// 如果为套餐
            // ，添加后状态为新增
			result = "package"; // 添加套餐成功
		} else {
			result = "success"; // 添加产品关联成功
		}
		productRelation.setCreateDate(new Date());
		try {
			productRelationService.saveProductRelationAndDetail(productRelation, list1);
			setRtnMessage("addsuccess");
		} catch (Exception e) {
			setRtnMessage("error");
			logger.error("定制管理-产品关联-产品关联列表-保存新添加信息失败:" + e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 定制管理-产品关联-产品关联列表-编辑后保存添加信息
	 * 
	 * @return
	 */
	public String saveOtherProductRelation() {
		try {
			productTied.setProductSkuId(Long.valueOf(mainSkuId.toString()));
			ProductRelation productRelation = new ProductRelation();
			productRelation.setRelationId(relationId);
			// 更新状态创建时间，以便定时任务重新更新可编辑状态
			productRelation.setCreateDate(new Date());
			productRelationService.savePRDetailAndUpdatePR(productRelation, list1);
			setRtnMessage("success");
		} catch (Exception e) {
			setRtnMessage("error");
			logger.error("定制管理-产品关联-产品关联列表-编辑后保存添加信息失败:" + e.getMessage(), e);
			return null;
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-产品关联-组方关联列表-保存新添加信息
	 * 
	 * @return
	 */
	public String saveProductZFRelation() {
		productRelation.setCreateDate(new Date());
		try {
			productRelationService.saveProductRelationAndDetail(productRelation, list1);
			setRtnMessage("addsuccess");
		} catch (Exception e) {
			setRtnMessage("error");
			logger.error("定制管理-产品关联-组方关联列表-保存新添加信息失败:" + e.getMessage(), e);
		}
		return "success";
	}

	/**
	 * 定制管理-套餐管理-查询列表列表
	 */
	public String queryPackageDetail() {
		try {
			if (selectProductRelation == null)
				selectProductRelation = new ProductRelation();
			if (pageNum != 0) {
				page.setPageNo(pageNum);
			}
			productRelationService.getProductPackageByMainSkuId(selectProductRelation, page);
			getProductRelationType();
			getProductRelationValidStatus();
			getProductRelationEdibleStatus();
		} catch (Exception e) {
			logger.error("定制管理-套餐管理-查询列表列表失败:" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-套餐管理（组方列表）-删除套餐（组方）信息（组方共用 ——> 批量、单套餐）
	 * 
	 * @return
	 */
	public String delRelationInfoTaoCan() {
		Long[] sIds = PurchaseUtils.strArrToIntArr(getRequest().getParameter("delPackageStr"));// 套餐（组方）id
		List<Long> delPackageList = new ArrayList<Long>();
		for (Long id : sIds) {
			delPackageList.add(id);
		}
		PrintWriter out = null;
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			productRelationService.delProductRelation(delPackageList);// 删除套餐（组方）以及套餐（组方）子表信息
			out = super.getPrintWriter();
			String msg = "删除成功";
			jsonMap.put("msg", msg);
			out.print(JSON.toJSONString(jsonMap));
		} catch (Exception e) {
			String msg = "删除失败";
			jsonMap.put("msg", msg);
			logger.error("定制管理-套餐管理（组方列表）-删除套餐（组方）信息失败:" + e.getMessage(), e);
			return null;
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		return null;
	}

	/**
	 * 定制管理-套餐管理-跳转添加套餐页面
	 * 
	 * @return
	 */
	public String packageAddProductTaoCan() {
		try {
			// 关联类型
			getProductRelationStatusMap();
			getProductRelationType();
			getCategoryList();
			setProductBrandMap();
		} catch (Exception e) {
			logger.error("定制管理-套餐管理-跳转添加套餐页面失败:" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-套餐管理-添加套餐-新增产品查询
	 * 
	 * @return
	 */
	public String addOtherTypeProductRelationTaoCan() {
		try {
			if (productTied == null) {
                productTied = new ProductmainTied();
            }

			productTied.setUserId(String.valueOf(getLoginUserId()));
			productTied.setMCategoryId(mCategoryId);
			productTied.setBCategoryId(bCategoryId);
			if (2 == supplierTyes) {// 入驻查询的商品
				productMainTiedService.selectListTaoCan(productTied, page);
			} else {// 自营入驻的商品
				productMainTiedService.selectListTaoCanZiying(productTied, page);
			}
			// 初始化三级目录
			initCategoryList(productTied.getBCategoryId(), productTied.getMCategoryId(), productTied.getCategoryId());
			setProductStatusMap();
			setProductBrandMap();
		} catch (Exception e) {
			logger.error("定制管理-套餐管理-添加套餐-新增产品查询失败:" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;

	}

	/**
	 * 定制管理-套餐管理-新增套餐
	 * 
	 * @return
	 */
	public String saveProductRelationTaoCan() {
		List<ProductRelationDetail> list2 = new ArrayList<ProductRelationDetail>();
		productRelation.setCreateDate(new Date());
		productRelation.setStatus(Short.valueOf(ProductRelationStatus.NEW.getStatus().toString()));// 设置套餐为新增状态
		try {
			productRelationService.saveTCPRDetailAndUpdatePR(productRelation, list2, prices, skuIds, proCount);
			setRtnMessage("addsuccess");
		} catch (Exception e) {
			setRtnMessage("error");
			logger.error("定制管理-套餐管理-新增套餐失败:" + e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-套餐管理-上下架套餐并调用cms系统发布更新页面
	 * 
	 * @return
	 */
	public String upOrDownShelf() {
		Long[] sIds = PurchaseUtils.strArrToIntArr(getRequest().getParameter("delPackageStr"));
		List<Long> operateList = new ArrayList<Long>();
		for (Long id : sIds) {
			operateList.add(id);
		}
		PrintWriter out = null;
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			if (operateType == null)
				throw new Exception("");
			out = super.getPrintWriter();
			String msg = productRelationService.upOrDownShelf(operateList, operateType);
			jsonMap.put("msg", msg);
			try {
				List<Integer> productIdList = productRelationService.selectProductIdByRelationId(operateList);
				cmsProductUpShelfService.productUpShelfByCms(productIdList);
			} catch (Exception e) {
				String msgCms = "CMS发布异常！";
				jsonMap.put("msg", msg);
				logger.error("定制管理-套餐管理-上下架套餐CMS发布失败:" + e.getMessage(), e);
			}
			out.print(JSON.toJSONString(jsonMap));
		} catch (Exception e) {
			String msgUp = "上架异常！";
			jsonMap.put("msg", msgUp);
			logger.error("定制管理-套餐管理-上下架套餐失败:" + e.getMessage(), e);
			return null;
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		return null;
	}

	/**
	 * 定制管理-套餐管理-套餐显示详情
	 * 
	 * @return
	 */
	public String viewProductPackageDeatilTaoCan() throws Exception {
		setCheckedId(productRelationDetail.getRelationId());// 高亮显示
		try {
			List<ProductRelationDetail> productRelationList = productRelationDetailService
					.productRelationDetailList(productRelationDetail.getRelationId());
			productRelation = productRelationService.selectProductRelationById(productRelationDetail.getRelationId());// 根据套餐id查询套餐信息
			List<ProductRelationDetailView> viewList = productRelationDetailService
					.getProductRelationDetailProductSku(productRelationDetail.getRelationId(), page);
			for (int i = 0; i < viewList.size(); i++) {
				proCounts = (viewList.get(i).getProductCount()) + proCounts;// 商品数量先加
				totalPirc = viewList.get(i).getPrice()
						.multiply(new BigDecimal(productRelationList.get(i).getProductCount())).add(totalPirc);
			}
			getProductRelationStatusMap();
		} catch (Exception e) {
			logger.error("定制管理-套餐管理-套餐显示详情失败:" + e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-套餐管理-修改保存套餐信息
	 * 
	 * @return
	 */
	public String updateProductRelation() throws Exception {

		try {
			if (updateType == 1) {// 新增状态下的修改套餐
				productRelationService.updateProductRelationSerAndDetail(productRelation, relationDetailIdList,
						proCount, skuIds, prices, skuIdsAdd, pricesAdd, proCountAdd);
			} else {
				productRelationService.updateProductRelationSer(productRelationUpdate);
			}
			setRtnMessage("successUpdate");
		} catch (Exception e) {
			setRtnMessage("error");
			logger.error("定制管理-套餐管理-修改保存套餐信息异常" + e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-套餐管理-新增或修改后查询列表
	 */
	public String packageManage() {
		try {
			if (StringUtils.isNotBlank(productTied.getRelationType())) {
				productMainTiedService.selectListByRelation(productTied, page, getLoginUserId());
			} else {
				productMainTiedService.selectList(productTied, page, getLoginUserId());
			}
			// 初始化三级目录
			initCategoryList(productTied.getBCategoryId(), productTied.getMCategoryId(), productTied.getCategoryId());
			getPackageMap();
			// ajax 查询
			setProductStatusMap();
			setProductBrandMap();
		} catch (Exception e) {
			logger.error("定制管理-套餐管理-新增或修改后查询列表失败" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-套餐管理-修改保存套餐(根据套餐子表id删除信息)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delRelationDetail() {
		int count = 0;
		try {
			count = productRelationDetailService.delRelationDetailById(detailId);
			if (count > 0) {
				getResponse().getWriter().print("1");
			} else {
				getResponse().getWriter().print("0");
			}
		} catch (Exception e) {
			logger.error("定制管理-套餐管理-修改保存套餐(根据套餐子表id删除信息)失败" + e.getMessage(), e);
			return null;
		}
		return null;
	}

	/**
	 * 定制管理-组方列表-获取组方产品列表
	 * 
	 * @return
	 */
	public String queryZFProductRelation() {
		// 获取状态
		getZFProductRelationValidStatus();
		try {
			if (selectProductRelation == null)
				selectProductRelation = new ProductRelation();
			if (pageNum == 0) {
				productRelationService.getAllZFProductRelation(selectProductRelation, page);
			} else {
				page.setPageNo(pageNum);
				productRelationService.getAllZFProductRelation(selectProductRelation, page);
			}
		} catch (Exception e) {
			logger.error("定制管理-组方列表-获取组方产品列表失败" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-组方列表-根据组方id查询组方详情
	 * 
	 * @return
	 */
	public String viewZFProductDeatil() {
		try {
			// 组合详情
			productRelation = productRelationService.queryProductRelation(productRelationDetail.getRelationId());
			// 副产品详情
			productRelationDetailService.getProductRelationDetailProductSku(productRelationDetail.getRelationId(),
					page);
			// 获取产品状态
			getProductRelationStatusMap();
		} catch (Exception e) {
			logger.error("定制管理-组方列表-根据组方id查询组方详情失败" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-组方列表-根据组方id修改查询组方详情
	 * 
	 * @return
	 */
	public String updateZFProductDeatil() {
		setCheckedId(productRelationDetail.getRelationId());// 高亮显示
		try {
			// 组合详情
			productRelation = productRelationService.queryProductRelation(productRelationDetail.getRelationId());
			// 获得相对应的关联类型
			relationType = productRelation.getRelationType();
			// 副产品详情
			productRelationDetailService.getProductRelationDetailProductSku(productRelationDetail.getRelationId(),
					page);
			// 获取产品状态
			getProductRelationStatusMap();
		} catch (Exception e) {
			logger.error("定制管理-组方列表-根据组方id修改查询组方详情失败" + e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 定制管理-组方列表-保存修改组方关联产品的信息
	 * 
	 * @return
	 */
	public String updateProductZFRelation() {
		productRelation.setCreateDate(new Date());
		try {
			productRelationService.updateZFProductRelationAndDetail(oldRIdCorrelationSIds, productRelation, list1);
			setRtnMessage("successUpdate");
		} catch (Exception e) {
			setRtnMessage("error");
			logger.error("定制管理-组方列表-保存修改组方关联产品的信息失败" + e.getMessage(), e);
		}
		return "success";
	}

	/**
	 * 跳转到添加其他产品的页面
	 * 
	 * @return
	 */
	public String addOtherProductRelation() {
		try {
			productMainTiedService.selectListNotMainSKU(productTied, page);
			// 初始化三级目录
			initCategoryList(productTied.getBCategoryId(), productTied.getMCategoryId(), productTied.getCategoryId());
			setProductStatusMap();
			getCategoryList();
			setProductBrandMap();
		} catch (Exception e) {
			logger.error("跳转到添加其他产品的页面失败" + e.getMessage(), e);
			return ERROR;
		}
		return "otherSuccess";
	}

	// 更新套餐的价格
	public String updatePrice() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			productRelationDetailService.updateProductRelationDetailPrice(productRelationDetail);
			// 调用cms系统刷新页面
			ProductmainTied prod = productMainTiedService.findObjectBySku(Long.valueOf(mainSkuId));
			List<Integer> prodIdList = new ArrayList<Integer>();
			prodIdList.add(prod.getProductId().intValue());
			// 调用cms 的页面进行刷新
			cmsProductUpShelfService.productUpShelfByCms(prodIdList);
			String msg = "更改价格成功";
			jsonMap.put("msg", msg);
		} catch (Exception e) {
			String msg = "更改价格失败";
			jsonMap.put("msg", msg);
			logger.error("更新套餐的价格失败" + e.getMessage(), e);
		}
        strWriteJson(JSON.toJSONString(jsonMap));
		return null;
	}

	/**
	 * 查询供应商信息
	 * 
	 * @return
	 */
	public String showSupplierInfo() {
		try {
			if (selectSupplierType == 1) {
				selectSuppliersInfo.setSupplierType(Short.parseShort("3"));
			} else {
				selectSuppliersInfo.setSupplierType(Short.parseShort("2"));
			}
			if (pageNum != 0) {
				page.setPageNo(pageNum);
			}
            supplierAuditService.showSupplierList(selectSuppliersInfo, page);
			getRequest().setAttribute("SuppliersTypeMap", SuppliersTypeMap.getMap());
		} catch (Exception e) {
			logger.error("查询供应商信息失败" + e.getMessage(), e);
		}
		return SUCCESS;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public ProductmainTied getProductTied() {
		return productTied;
	}

	public void setProductTied(ProductmainTied productTied) {
		this.productTied = productTied;
	}

	public List<ProductRelationDetail> getList() {
		return list;
	}

	public void setList(List<ProductRelationDetail> list) {
		this.list = list;
	}

	public ProductRelation getProductRelation() {
		return productRelation;
	}

	public void setProductRelation(ProductRelation productRelation) {
		this.productRelation = productRelation;
	}

	public String getRtnMessage() {
		return rtnMessage;
	}

	public void setRtnMessage(String rtnMessage) {
		this.rtnMessage = rtnMessage;
	}

	public List<ProductRelation> getProductRelationList() {
		return productRelationList;
	}

	public void setProductRelationList(List<ProductRelation> productRelationList) {
		this.productRelationList = productRelationList;
	}

	public List<Long> getProductRelationId() {
		return productRelationId;
	}

	public void setProductRelationId(List<Long> productRelationId) {
		this.productRelationId = productRelationId;
	}

	public ProductRelationDetail getProductRelationDetail() {
		return productRelationDetail;
	}

	public void setProductRelationDetail(ProductRelationDetail productRelationDetail) {
		this.productRelationDetail = productRelationDetail;
	}

	public Map<ProductRelationDetail, ProductmainTied> getViewMap() {
		return viewMap;
	}

	public void setViewMap(Map<ProductRelationDetail, ProductmainTied> viewMap) {
		this.viewMap = viewMap;
	}

	public List<Long> getRelationDetailId() {
		return relationDetailId;
	}

	public void setRelationDetailId(List<Long> relationDetailId) {
		this.relationDetailId = relationDetailId;
	}

	public String getMainSkuId() {
		return mainSkuId;
	}

	public void setMainSkuId(String mainSkuId) {
		this.mainSkuId = mainSkuId;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public List<ProductRelationDetail> getList1() {
		return list1;
	}

	public void setList1(List<ProductRelationDetail> list1) {
		this.list1 = list1;
	}

	public List<ProductRelationDetail> getRelationEdDetails() {
		return relationEdDetails;
	}

	public void setRelationEdDetails(List<ProductRelationDetail> relationEdDetails) {
		this.relationEdDetails = relationEdDetails;
	}

	public Double getMainSkuPrice() {
		return mainSkuPrice;
	}

	public void setMainSkuPrice(Double mainSkuPrice) {
		this.mainSkuPrice = mainSkuPrice;
	}

	public Short getRelationType() {
		return relationType;
	}

	public void setRelationType(Short relationType) {
		this.relationType = relationType;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public Short getEditable() {
		return editable;
	}

	public void setEditable(Short editable) {
		this.editable = editable;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Short getPackageStatus() {
		return packageStatus;
	}

	public void setPackageStatus(Short packageStatus) {
		this.packageStatus = packageStatus;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsRelation() {
		return isRelation;
	}

	public void setIsRelation(String isRelation) {
		this.isRelation = isRelation;
	}

	public ProductSkuService getProductSkuService() {
		return productSkuService;
	}

	public void setProductSkuService(ProductSkuService productSkuService) {
		this.productSkuService = productSkuService;
	}

	public String[] getProductRelationSku1() {
		return productRelationSku1;
	}

	public void setProductRelationSku1(String[] productRelationSku1) {
		this.productRelationSku1 = productRelationSku1;
	}

	public String[] getProductRelationType1() {
		return productRelationType1;
	}

	public void setProductRelationType1(String[] productRelationType1) {
		this.productRelationType1 = productRelationType1;
	}

	public String[] getRelationID1() {
		return relationID1;
	}

	public void setRelationID1(String[] relationID1) {
		this.relationID1 = relationID1;
	}

	public String[] getProductRelationPrice1() {
		return productRelationPrice1;
	}

	public void setProductRelationPrice1(String[] productRelationPrice1) {
		this.productRelationPrice1 = productRelationPrice1;
	}

	public ProductRelation getSelectProductRelation() {
		return selectProductRelation;
	}

	public void setSelectProductRelation(ProductRelation selectProductRelation) {
		this.selectProductRelation = selectProductRelation;
	}

	public MerchantInfoOrSuppliers getSelectSuppliersInfo() {
		return selectSuppliersInfo;
	}

	public void setSelectSuppliersInfo(MerchantInfoOrSuppliers selectSuppliersInfo) {
		this.selectSuppliersInfo = selectSuppliersInfo;
	}

	public int getSelectSupplierType() {
		return selectSupplierType;
	}

	public void setSelectSupplierType(int selectSupplierType) {
		this.selectSupplierType = selectSupplierType;
	}

	public List<BigDecimal> getPrices() {
		return prices;
	}

	public void setPrices(List<BigDecimal> prices) {
		this.prices = prices;
	}

	public List<Short> getProCount() {
		return proCount;
	}

	public void setProCount(List<Short> proCount) {
		this.proCount = proCount;
	}

	public List<Long> getSkuIds() {
		return skuIds;
	}

	public void setSkuIds(List<Long> skuIds) {
		this.skuIds = skuIds;
	}

	public int getProCounts() {
		return proCounts;
	}

	public void setProCounts(int proCounts) {
		this.proCounts = proCounts;
	}

	public BigDecimal getTotalPirc() {
		return totalPirc;
	}

	public void setTotalPirc(BigDecimal totalPirc) {
		this.totalPirc = totalPirc;
	}

	public ProductRelation getProductRelationUpdate() {
		return productRelationUpdate;
	}

	public void setProductRelationUpdate(ProductRelation productRelationUpdate) {
		this.productRelationUpdate = productRelationUpdate;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public int getSupplierTyes() {
		return supplierTyes;
	}

	public void setSupplierTyes(int supplierTyes) {
		this.supplierTyes = supplierTyes;
	}

	public int getUpdateType() {
		return updateType;
	}

	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	}

	public List<Long> getRelationDetailIdList() {
		return relationDetailIdList;
	}

	public void setRelationDetailIdList(List<Long> relationDetailIdList) {
		this.relationDetailIdList = relationDetailIdList;
	}

	public List<Long> getSkuIdsAdd() {
		return skuIdsAdd;
	}

	public void setSkuIdsAdd(List<Long> skuIdsAdd) {
		this.skuIdsAdd = skuIdsAdd;
	}

	public List<BigDecimal> getPricesAdd() {
		return pricesAdd;
	}

	public void setPricesAdd(List<BigDecimal> pricesAdd) {
		this.pricesAdd = pricesAdd;
	}

	public List<Short> getProCountAdd() {
		return proCountAdd;
	}

	public void setProCountAdd(List<Short> proCountAdd) {
		this.proCountAdd = proCountAdd;
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public String getOldRIdCorrelationSIds() {
		return oldRIdCorrelationSIds;
	}

	public void setOldRIdCorrelationSIds(String oldRIdCorrelationSIds) {
		this.oldRIdCorrelationSIds = oldRIdCorrelationSIds;
	}

	public Long getbCategoryId() {
		return bCategoryId;
	}

	public void setbCategoryId(Long bCategoryId) {
		this.bCategoryId = bCategoryId;
	}

	public Long getmCategoryId() {
		return mCategoryId;
	}

	public void setmCategoryId(Long mCategoryId) {
		this.mCategoryId = mCategoryId;
	}

}
