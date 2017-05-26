package com.pltfm.app.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.StockOutDayEndStatus;
import com.pltfm.app.enums.StockOutStatus;
import com.pltfm.app.enums.StockOutTypeStatus;
import com.pltfm.app.service.StockOutDetailService;
import com.pltfm.app.service.StockOutService;
import com.pltfm.app.util.BillCodeUtils;
import com.pltfm.app.util.BillPrefix;
import com.pltfm.app.vobject.StockOut;
import com.pltfm.app.vobject.StockOutDetail;
import com.kmzyc.commons.page.Page;

/**
 * 出库明细单业务处理action
 * 
 * @author luoyi
 * @createDate 2013/08/16
 * 
 */
@Controller("stockOutDetailAction")
@Scope(value = "prototype")
public class StockOutDetailAction extends BaseAction {

	private static final long serialVersionUID = -1238938486112888474L;

	@Resource
	private StockOutService stockOutService;
	@Resource
	private StockOutDetailService stockOutDetailService;

	private Page page = new Page();
	private StockOutDetail stockOutDetail = new StockOutDetail();// 出库明细单
	private StockOut stockOut = new StockOut();// 出库单
	private List<StockOutDetail> stockOutDetailList;
	private Long stockOutId; // 出库单ID
	private String rtnMessage; // 显示信息
	private String remark;// 备注
	private Long warehouseId;// 仓库id
	private Integer userId;// 经手人
	private String optionType;// 操作类型

	// 出库单查询条件
	private StockOut queryStockOut = new StockOut();
	// 结束时间
	private Date endDate;
	private int pageNum = 0; // 页数

	/**
	 * 出库明细单列表:查询出库明细单
	 * 
	 * @return SUCCESS
	 * @throws Exception
	 */
	public String findStockOutDetais() {
		try {
			setCheckedId(stockOutId); // 高亮显示
			if (pageNum == 1) {
				page.setPageNo(1);
			}
			if (null != optionType && "editStockDetail".equals(optionType)) {// 如果是修改
				stockOutDetailList = stockOutDetailService.findStockOutDetailList(stockOutId);
				setWarehouseForStatusMap();// 可用仓库信息
			} else {
				// 出库明细单集合
				stockOutDetailList = stockOutDetailService.findStockOutDetailList(page, stockOutDetail,stockOutId);
				setWarehouseMap();// 所有仓库信息
			}
			stockOut = stockOutService.findStockOut(stockOutId);
			// 审核状态和仓库信息
			setStockOutStatusMap();// 状态信息
			setStockOutTypeMap();// 出库单类型
			// 设置默认仓库选中项
			this.setWarehouseId(stockOut.getWarehouseId());
			// 设置经手人选中项
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 出库单审核:到出库明细单列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toCheckStockOutDetail() throws Exception {
		setCheckedId(stockOutId); // 高亮显示
		findStockOutDetais();
		return SUCCESS;
	}

	/**
	 * 出库单列表:到添加出库单页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddStockOutDetail() throws Exception {
		setWarehouseForStatusMap();// 可用仓库信息
		setStockOutStatusMap();// 状态信息
		setSysHandlerMap();// 经手人列表
		stockOut.setUserId(super.getLoginUserId());
		stockOut.setUserName(super.getLoginUserName());
		return SUCCESS;
	}

	/**
	 * 出库单列表:添加出库单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addStockOutDetail() throws Exception {
		// 出库单处理:根据出库明细单汇总数据
		
		List<StockOutDetail> stockOutDetailTmp = new ArrayList<StockOutDetail>();
		for(StockOutDetail sod : stockOutDetailList){
			if(sod==null) continue;
			if(StringUtils.isNotBlank(sod.getStr_productId()))
				sod.setProductId(Long.valueOf(sod.getStr_productId().trim()));
			if(StringUtils.isNotBlank(sod.getStr_skuId()))
				sod.setProductSkuId(Long.valueOf(sod.getStr_skuId().trim()));
			if(StringUtils.isNotBlank(sod.getStr_quantity()))
				sod.setQuantity(Integer.valueOf(sod.getStr_quantity().trim()));
			if(StringUtils.isNotBlank(sod.getStr_stockOutPrice()))
				sod.setPrice(new BigDecimal(sod.getStr_stockOutPrice().trim()));
			stockOutDetailTmp.add(sod);
		}

		stockOut.setCreateUser(getLoginUserId());// 创建人
		stockOut.setCreateUserName(getLoginUserName());// 创建人姓名
		stockOut.setType(StockOutTypeStatus.OTHER.getStatus()); // 手工出库计划
		stockOut.setCreateDate(new Date());// 创建日期
		stockOut.setStatus(Short.valueOf(StockOutStatus.UNAUDIT.getStatus()));// 出库单状态:未审核
		stockOut.setDayEndStatus(Short.valueOf(StockOutDayEndStatus.UNREPORT.getStatus()));// 日结状态
		stockOut.setStockOutNo(BillCodeUtils.getBillCode(BillPrefix.STOCKOUT_PREFIX));// 单号

		// 保存出库单和出库明细单
		ResultMessage resultMessage = null;
		try {
			resultMessage = stockOutDetailService.saveStockOutDetail(stockOut, stockOutDetailTmp);
			rtnMessage = resultMessage.getMessage();
		} catch (Exception e) {
			rtnMessage = "出库单添加失败!";
			e.printStackTrace();
			return ERROR;
		}
		setWarehouseForStatusMap();// 可用仓库信息
		setStockOutStatusMap();// 状态信息
		stockOutId = stockOut.getStockOutId();// 设置出库单ID，供查询细目单用
		return findStockOutDetais();
	}

	/**
	 * 出库单列表:到出库单修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toEditStockOutDetail() throws Exception {
		// 根据id查询到
		findStockOutDetais();
		setSysHandlerMap();// 经手人列表
		return SUCCESS;

	}

	/**
	 * 出库单修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editStockOutDetail() throws Exception {
		setWarehouseStatusMap();// 可用仓库信息
		setStockOutStatusMap();// 状态信息
		List<StockOutDetail> stockOutDetailTmp = new ArrayList<StockOutDetail>();
		for(StockOutDetail sod : stockOutDetailList){
			if(sod==null) continue;
			if(StringUtils.isNotBlank(sod.getStr_productId()))
				sod.setProductId(Long.valueOf(sod.getStr_productId().trim()));
			if(StringUtils.isNotBlank(sod.getStr_skuId()))
				sod.setProductSkuId(Long.valueOf(sod.getStr_skuId().trim()));
			if(StringUtils.isNotBlank(sod.getStr_quantity()))
				sod.setQuantity(Integer.valueOf(sod.getStr_quantity().trim()));
			if(StringUtils.isNotBlank(sod.getStr_stockOutPrice()))
				sod.setPrice(new BigDecimal(sod.getStr_stockOutPrice().trim()));
			stockOutDetailTmp.add(sod);
		}
		stockOut.setModifiyDate(new Date());// 修改日期
		stockOut.setModifierId(super.getLoginUserId());// 修改人
		stockOut.setModifierName(super.getLoginUserName());// 修改人姓名

		// 调用修改方法
		// 保存出库单和出库单详情
		ResultMessage resultMessage = stockOutDetailService
				.updateStockOutDetail(stockOut, stockOutDetailTmp);
		rtnMessage = resultMessage.getMessage();
		stockOutId = stockOut.getStockOutId();// 设置出库单ID，供查询细目单用
		return findStockOutDetais();
	}

	/*
	 * 以下为 set和get方法 ,业务处理方法不要写到以下界面
	 */

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public StockOutDetail getStockOutDetail() {
		return stockOutDetail;
	}

	public void setStockOutDetail(StockOutDetail stockOutDetail) {
		this.stockOutDetail = stockOutDetail;
	}

	public List<StockOutDetail> getStockOutDetailList() {
		return stockOutDetailList;
	}

	public void setStockOutDetailList(List<StockOutDetail> stockOutDetailList) {
		this.stockOutDetailList = stockOutDetailList;
	}

	public StockOutDetailService getStockOutDetailService() {
		return stockOutDetailService;
	}

	public void setStockOutDetailService(
			StockOutDetailService stockOutDetailService) {
		this.stockOutDetailService = stockOutDetailService;
	}

	public Long getStockOutId() {
		return stockOutId;
	}

	public void setStockOutId(Long stockOutId) {
		this.stockOutId = stockOutId;
	}

	public StockOut getStockOut() {
		return stockOut;
	}

	public void setStockOut(StockOut stockOut) {
		this.stockOut = stockOut;
	}

	public String getRtnMessage() {
		return rtnMessage;
	}

	public void setRtnMessage(String rtnMessage) {
		this.rtnMessage = rtnMessage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public StockOut getQueryStockOut() {
		return queryStockOut;
	}

	public void setQueryStockOut(StockOut queryStockOut) {
		this.queryStockOut = queryStockOut;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
