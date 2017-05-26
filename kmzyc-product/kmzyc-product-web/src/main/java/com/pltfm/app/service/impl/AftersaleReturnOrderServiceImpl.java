package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.order.remote.OrderAlterChangeStatusRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.AftersaleReturnOrderDAO;
import com.pltfm.app.enums.ReturnOrExchangeHandleResult;
import com.pltfm.app.enums.ReturnOrExchangeStatus;
import com.pltfm.app.enums.StockOutDayEndStatus;
import com.pltfm.app.enums.StockOutStatus;
import com.pltfm.app.enums.StockOutTypeStatus;
import com.pltfm.app.service.AftersaleReturnOrderService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ProductStockService;
import com.pltfm.app.service.StockOutDetailService;
import com.pltfm.app.util.BillCodeUtils;
import com.pltfm.app.util.BillPrefix;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.app.vobject.AftersaleReturnOrderExample;
import com.pltfm.app.vobject.AftersaleReturnOrderExample.Criteria;
import com.pltfm.app.vobject.StockOut;
import com.pltfm.app.vobject.StockOutDetail;
import com.pltfm.sys.model.SysUser;

/**
 * 
 * @author tanyunxing
 * 
 */
@Service("aftersaleReturnOrderService")
public class AftersaleReturnOrderServiceImpl implements AftersaleReturnOrderService {

    private Logger logger = LoggerFactory.getLogger(AftersaleReturnOrderServiceImpl.class);

	@Resource
	private AftersaleReturnOrderDAO aftersaleReturnOrderDao;

	@Resource
	private StockOutDetailService stockOutDetailService;
	
	@Resource
	private ProductService productService;
	
	@Resource
	private ProductSkuService productSkuService;
	
	@Resource
	private ProductStockService productStockService;

	@Resource
	private OrderAlterChangeStatusRemoteService orderAlterChangeStatusRemoteService;

	@Override
	public void searchPage(Page page, AftersaleReturnOrder order) throws ServiceException {
		int pageIndex = page.getPageNo();
		if (pageIndex == 0)
			pageIndex = 1;
		int pageSize = page.getPageSize();
		int skip = (pageIndex - 1) * pageSize;
		int max = pageSize;

		AftersaleReturnOrderExample exm = new AftersaleReturnOrderExample();
		Criteria c = exm.createCriteria();
		if (StringUtils.isNotEmpty(order.getStatus())) {
			c.andStatusEqualTo(order.getStatus());
		}
		if (StringUtils.isNotEmpty(order.getHandleResult())) {
			c.andHandleResultEqualTo(order.getHandleResult());
		}
		if (StringUtils.isNotEmpty(order.getOrderCode())) {
			c.andOrderCodeEqualTo(order.getOrderCode());
		}
		if(StringUtils.isNotEmpty(order.getProductNo())){
			String productNo = "%" + order.getProductNo() + "%";
			c.andProductNoLike(productNo);
		}
		if(StringUtils.isNotEmpty(order.getProductSku())){
			String productSKU = "%" + order.getProductSku() + "%";
			c.andProductSkuLike(productSKU);
		}
		exm.setOrderByClause(" CREATE_TIME desc");

        try {
            int count = aftersaleReturnOrderDao.countByExample(exm);
            List<AftersaleReturnOrder> list = aftersaleReturnOrderDao.selectByExample(exm, skip, max);
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            logger.error("分页列表异常", e);
            throw new ServiceException(e);
        }

    }

	@Override
	public AftersaleReturnOrder findByPrimaryKey(Long returnId, String handleResult) throws ServiceException {
        try {
            if ("2".equals(handleResult)) {
                AftersaleReturnOrder record = new AftersaleReturnOrder();
                record.setReturnId(returnId);
                record.setHandleResult("3");
                aftersaleReturnOrderDao.updateByPrimaryKeySelective(record);
            }
            return aftersaleReturnOrderDao.selectByPrimaryKey(returnId);
        } catch (SQLException e) {
            logger.error("根据主键获取退货单异常", e);
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int updateObject(AftersaleReturnOrder record, SysUser user,boolean changeReturnOrder)
			throws ServiceException {
		try {
			int result = 1;
			String operatorName = ConfigurationUtil.getString("orderOperatorName");
			//商品已收到
			if(ReturnOrExchangeStatus.ARRIVALOFGOODS.getKey().equals(record.getStatus())){
				result =orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(operatorName,record.getOrderCode(),Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.Pickup.getKey()),null);
			}
			//拒绝退货
			if(ReturnOrExchangeHandleResult.UNPASS.getKey().equals(record.getHandleResult())){
				result = orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(operatorName,record.getOrderCode(),Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.F_BackShop.getKey()),record.getDetectResult());
			}
			//同意退货
			if (ReturnOrExchangeHandleResult.PASS.getKey().equals(record.getHandleResult())) {
				//换货出库
				if (record.getOrderType().equals((short) OrderAlterDictionaryEnum.AlterTypes.EXchange.getKey())) {
					BigDecimal productId = BigDecimal.valueOf(productService.queryProductByProductNo(record.getProductNo()).getProductId());
					BigDecimal productSkuId = BigDecimal.valueOf(productSkuService.findProductSkuByCode(record.getProductSku()).getProductSkuId());
					Long warehouseId = productStockService.findProductSkuStockCountBySkuId(productSkuId.longValue());
					if(warehouseId != null){
						//锁库存
						Map<String,Map<Long,Long>> lockMap = new HashMap<String,Map<Long,Long>>();
						Map<Long,Long> innerMap = new HashMap<Long,Long>();
						innerMap.put(warehouseId, record.getProductCounts());
						lockMap.put(record.getProductSku(), innerMap);
						Map<String, Object> resultMap = productStockService.batchLockStockForAfter(lockMap);
						
						if(resultMap != null && resultMap.size() > 0){
						    for(String key : resultMap.keySet()){
						    	if("fail".equalsIgnoreCase(key)){
						    		if(changeReturnOrder){
						    			record.setHandleResult(ReturnOrExchangeHandleResult.UNPASS.getKey());
							    		record.setDetectResult("该商品库存不足");
							    		aftersaleReturnOrderDao.updateByPrimaryKeySelective(record);
							    		result = orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(operatorName,record.getOrderCode(),Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn.getKey()),"该商品库存不足");
							    		if(result != 1){
											throw new ServiceException("调用远程接口失败！");
										}
							    		return 1;
						    		}else{
						    			return 0;
						    		}
						    	}
						    }
					    }
						
						List<StockOutDetail> listOut = new ArrayList<StockOutDetail>();
						StockOut stockOut = new StockOut();
						StockOutDetail sod = new StockOutDetail();
	
						stockOut.setCreateUser(user.getUserId());// 创建人
						stockOut.setCreateUserName(user.getUserName());//创建人姓名
						stockOut.setType(StockOutTypeStatus.EXCHANGE.getStatus()); // 手工出库计划
						stockOut.setCreateDate(new Date());// 创建日期
						stockOut.setStatus(Short.valueOf(StockOutStatus.UNAUDIT.getStatus()));// 出库单状态:未审核
						stockOut.setDayEndStatus(Short.valueOf(StockOutDayEndStatus.UNREPORT.getStatus()));// 日结状态
						stockOut.setStockOutNo(BillCodeUtils.getBillCode(BillPrefix.STOCKOUT_PREFIX));// 单号
						stockOut.setTotalQuantity(record.getProductCounts().intValue());
						stockOut.setWarehouseId(warehouseId);
						stockOut.setBillNo(record.getOrderCode());
						stockOut.setTotalSum(record.getTotalPrice());
						
						sod.setProductId(productId.longValue());
						sod.setProductNo(record.getProductNo());
						sod.setProductName(record.getProductName());
						sod.setProductSkuId(productSkuId.longValue());
						sod.setProductSkuValue(record.getProductSku());
						sod.setQuantity(record.getProductCounts().intValue());
						sod.setPrice(record.getUnitPrice());
						sod.setSum(record.getTotalPrice());
						sod.setBillDetailId(record.getOrderdetailId());
						sod.setBillType(StockOutTypeStatus.EXCHANGE.getStatus());
						
						listOut.add(sod);
						stockOutDetailService.saveStockOutDetail(stockOut, listOut);
						result = orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(operatorName,record.getOrderCode(),Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.F_Stockout.getKey()),null);
					}else{
						result = orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(operatorName,record.getOrderCode(),Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn.getKey()),null);
					}
				}else{
					result = orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(operatorName,record.getOrderCode(),Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.F_Backpay.getKey()),null);
				}
			}
			if(result != 1){
				throw new ServiceException("调用远程接口失败！");
			}
			aftersaleReturnOrderDao.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return 1;
	}

}
