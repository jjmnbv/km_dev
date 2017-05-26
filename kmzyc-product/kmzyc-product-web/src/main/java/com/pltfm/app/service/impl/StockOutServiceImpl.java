package com.pltfm.app.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.StockOutDAO;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.enums.StockOutTypeStatus;
import com.pltfm.app.service.DistributionDetailInfoService;
import com.pltfm.app.service.ProductStockService;
import com.pltfm.app.service.StockOutDetailService;
import com.pltfm.app.service.StockOutService;
import com.pltfm.app.util.BillCodeUtils;
import com.pltfm.app.util.BillPrefix;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionInfo;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.StockOut;
import com.pltfm.app.vobject.StockOutAftersaleReturn;
import com.pltfm.app.vobject.StockOutAndDetail;
import com.pltfm.app.vobject.StockOutDetail;
import com.pltfm.app.vobject.StockOutExample;
import com.pltfm.app.vobject.StockOutExample.Criteria;
import com.pltfm.app.vobject.ViewProductSku;
import com.pltfm.sys.util.DatetimeUtil;
import com.kmzyc.commons.page.Page;
import com.kmzyc.product.remote.service.StockRemoteService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口：出库单Service
 * 
 * @author luoyi
 * @createDate 2013/08/15
 */
@Service(value = "stockOutService")
public class StockOutServiceImpl implements StockOutService {

	Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private StockOutDAO stockOutDao;
	
	@Resource
	private ProductStockService productStockService;
	
	@Resource
	private StockOutDetailService stockOutDetailService;
	
	@Resource
	private DistributionDetailInfoService distributionDetailInfoService;
	
	@Resource
	private StockRemoteService stockRemoteService;
	
	public List<StockOut> findStockOutList(Page page, StockOut stockOut, Date endDate)
            throws ServiceException {
		int pageIndex = page.getPageNo();
		if (pageIndex == 0)
			pageIndex = 1;
		int pageSize = page.getPageSize();
		int skip = (pageIndex - 1) * pageSize;
		// 每页记录数
		int max = pageSize;

		StockOutExample example = new StockOutExample();
		Criteria criteria = example.createCriteria();
		// 状态不为空
		if (null != stockOut.getStatus()) {
			criteria.andStatusEqualTo(stockOut.getStatus());
		}
		// 仓库不为空
		if (null != stockOut.getWarehouseId()) {
			criteria.andWarehouseIdEqualTo(stockOut.getWarehouseId());
		}
		// 类型不为空
		if (null != stockOut.getType()) {
			criteria.andTypeEqualTo(stockOut.getType());
		}
		// 采购单号不为空
		if (StringUtils.isNotBlank(stockOut.getStockOutNo())) {
			criteria.andStockOutNoLike(stockOut.getStockOutNo());
		}
		// 订单号不为空
		if (StringUtils.isNotBlank(stockOut.getBillNo())) {
			criteria.andBillNoLike(stockOut.getBillNo());
		}
		// 查询日期不为空
		if (null != stockOut.getCreateDate()) {
			if (null == endDate) {
				criteria.andCreateDateGreaterThanOrEqualTo(stockOut.getCreateDate());
			} else {
				// 结束日期要加上1天
				criteria.andCreateDateGreaterThanOrEqualTo(stockOut.getCreateDate());
				criteria.andCreateDateLessThan(DatetimeUtil.getDateByDay(endDate, 1));
			}
		} else if (null != endDate) {// 开始日期为空,结束日期不为空
			criteria.andCreateDateLessThanOrEqualTo(endDate);
		}
		// 设置按出库单ID排序
		example.setOrderByClause(" stock_Out_Id DESC");

        try {
            // 总记录数
            int count = stockOutDao.countByExample(example);
            List<StockOut> stockOutList = stockOutDao.selectByExample(example, skip, max);
            page.setDataList(stockOutList);// 设置数据
            page.setRecordCount(count);// 设置总记录数
            return stockOutList;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	public StockOut findStockOut(Long stockOutId) throws ServiceException {
        try {
            return stockOutDao.selectByPrimaryKey(stockOutId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	public StockOut findStockOutByNo(String stockOutNO) throws ServiceException {
		StockOutExample example = new StockOutExample();
		Criteria criteria = example.createCriteria();
		// 根据采购单进行精确查询
		criteria.andStockOutNoEqualTo(stockOutNO);

        try {
            List<StockOut> pList = stockOutDao.selectByExample(example);
            return pList == null ? null : pList.get(0);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public ResultMessage auditStockOutForOrder(List<StockOut> stockOutList) throws ServiceException {
		ResultMessage resultMessage = null;
		List<ProductStock> stockList = null;
		//出库前的对应库存数据
		Map<Long,ProductStock> oldStockMap = null;
		List<Long> skuIdList = new ArrayList<Long>();
		try{
			int count = stockOutDao.batchUpdateForStockOut("STOCK_OUT.checkedStockOutByList", stockOutList);
			if (count<1) {
				log.error("批量审核出库单失败!");
				throw new ServiceException("批量审核出库单失败!");
			}
				
			//出库明细单
			List<StockOutAndDetail> detailList =  selectStockOutDetailByStockOutId(stockOutList);
			Map<Long,Long> stockIdMap = productStockService.findStockIdMapByWarehouseAndSkuId(detailList);
			if(detailList==null || detailList.isEmpty()){
				log.error("出库单数据问题，审核出库单失败!");
				throw new ServiceException("出库单数据问题，审核出库单失败!");
			}
			
			stockList = new ArrayList<ProductStock>();
			
			ProductStock stock = null;
			for(StockOutAndDetail detail : detailList){
				if(AuditStatus.AUDIT.getStatus().equals(detail.getStatus().toString()))
					stock = new ProductStock();
					if(stockIdMap!=null){
						stock.setStockId(stockIdMap.get(detail.getDetailId()));
					}else{
						stock.setStockId(detail.getStockId());
					}
					stock.setStockQuality(Long.valueOf(detail.getQuantity()));
					stock.setWarehouseId(detail.getWarehouseId());
					stock.setSkuAttributeId(detail.getProductSkuId());
					stock.setSkuAttValue(detail.getProductSkuValue());
					//为了记录库存日志，非持久化，不影响正常业务
					stock.setBillDetailId(detail.getDetailId());
					stock.setBillNo(detail.getStockOutNo());
				
					stockList.add(stock);
					//MQ通知搜索
					skuIdList.add(stock.getSkuAttributeId());

			}
			
			//出库前的对应库存数据
			oldStockMap = productStockService.selectProductStockListByStockList(stockList);
			
			//减库存数量或者订购数量
			resultMessage =productStockService.batchIncreaseStockQuantity(stockList, StockOutTypeStatus.ORDER.getStatus());

			//减少库存数量操作
			if(!resultMessage.getIsSuccess()){//如果出现false
				log.error(resultMessage.getMessage());
				throw new ServiceException(resultMessage.getMessage());				
			}
		}catch(Exception e){
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
		
		resultMessage.setObject(stockList);
		resultMessage.setObject2(skuIdList);
		resultMessage.setMap(oldStockMap);
		return resultMessage;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public ResultMessage auditStockOutForExchange(List<StockOut> stockOutList) throws ServiceException {
		ResultMessage resultMessage = null;
		List<ProductStock> stockList = null;
		//出库前的对应库存数据
		Map<Long,ProductStock> oldStockMap = null;
		List<Long> skuIdList = new ArrayList<Long>();
		try{
			int count = stockOutDao.batchUpdateForStockOut(
					"STOCK_OUT.checkedStockOutByList", stockOutList);
			if (count<1) {
				throw new ServiceException("批量审核出库单失败!");
			}
				
			//出库明细单
			List<StockOutAndDetail> detailList =  selectStockOutDetailByStockOutId(stockOutList);
			Map<Long,Long> stockIdMap = productStockService.findStockIdMapByWarehouseAndSkuId(detailList);
			if(detailList==null || detailList.isEmpty()){
				throw new ServiceException("出库单数据问题，审核出库单失败!");
			}
			
			stockList = new ArrayList<ProductStock>();
			
			ProductStock stock = null;
			for(StockOutAndDetail detail : detailList){
				if(AuditStatus.AUDIT.getStatus().equals(detail.getStatus().toString()))
					stock = new ProductStock();
					if(stockIdMap!=null){
						stock.setStockId(stockIdMap.get(detail.getDetailId()));
					}else{
						stock.setStockId(detail.getStockId());
					}
					stock.setStockQuality(Long.valueOf(detail.getQuantity()));
					stock.setWarehouseId(detail.getWarehouseId());
					stock.setSkuAttributeId(detail.getProductSkuId());
					stock.setSkuAttValue(detail.getProductSkuValue());
					//为了记录库存日志，非持久化，不影响正常业务
					stock.setBillDetailId(detail.getDetailId());
					stock.setBillNo(detail.getStockOutNo());
				
					stockList.add(stock);
					//MQ通知搜索
					skuIdList.add(stock.getSkuAttributeId());

			}
			
			//出库前的对应库存数据
			oldStockMap = productStockService.selectProductStockListByStockList(stockList);
			
			//减库存数量或者订购数量
			resultMessage =productStockService.batchIncreaseStockQuantity(stockList, StockOutTypeStatus.EXCHANGE.getStatus());

			//减少库存数量操作
			if(!resultMessage.getIsSuccess()){//如果出现false
				throw new ServiceException("审核出库单:更新库存操作失败!" + resultMessage.getMessage());
			}
			//换货出库单生成配送单
			saveDistributionInfo(stockOutList);
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		resultMessage.setObject(stockList);
		resultMessage.setObject2(skuIdList);
		resultMessage.setMap(oldStockMap);
		
		return resultMessage;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	private void saveDistributionInfo(List<StockOut> stockOutList) throws ServiceException{
		try{
			//根据出库单,生成查找明细单
			List<StockOutDetail> sDetails = null;
			//获取到换货的配送地址和配送电话(将stockOutList转为stockoutAfterlist)
		 	List<StockOutAftersaleReturn> stockoutAfterlist = this.selectStockOutListByReturnNo(stockOutList);
			DistributionInfo distributionInfo = null;
			DistributionDetailInfo detail = null;
			List<DistributionDetailInfo> distributionDetailInfos = null;
		 	
			for (StockOutAftersaleReturn sOut : stockoutAfterlist) {
				//生成配送单数据
				distributionInfo = new DistributionInfo();
				distributionInfo.setWarehouseId(sOut.getWarehouseId());// 仓库ID
				distributionInfo.setTotalSum(sOut.getTotalSum());// 总金额
				distributionInfo.setTotalQuantity(sOut.getTotalQuantity());// 总数量
				String distributionNo = BillCodeUtils.getBillCode(BillPrefix.DISPATCHING_PREFIX);// 配送单号
				distributionInfo.setDistributionNo(distributionNo);// 设置生成配送单号
				distributionInfo.setUserName(sOut.getCustName());// 设置收货人
				distributionInfo.setTel(sOut.getLinkPhone());//联系人电话
				distributionInfo.setDeliveryAddress(sOut.getShipAddress());// 设置送货地址
				distributionInfo.setTel(sOut.getLinkPhone());// 设置送货电话
				distributionInfo.setLogisticsDate(new Date());// 设置配送日期
				distributionInfo.setBillNo(sOut.getStockOutNo());// 设置出库编号
				distributionInfo.setOrderNo(sOut.getBillNo());//退货单号
				// 配送细目单
				distributionDetailInfos = new ArrayList<DistributionDetailInfo>();
				detail = new DistributionDetailInfo();
				
//				sDetails = new ArrayList<StockOutDetail>();
				sDetails = stockOutDetailService.findStockOutDetailList(sOut.getStockOutId());
				if(sDetails==null || sDetails.isEmpty()){
					throw new ServiceException("数据错误，生成配送单失败!");
				}
				//根据出库明细单,生成配送明细单
				for (StockOutDetail stockOutDetail:sDetails) {
					detail.setProductId(stockOutDetail.getProductId());// 产品ID
					detail.setProductName(stockOutDetail.getProductName());// 产品名称
					detail.setProductNo(stockOutDetail.getProductNo());// 产品货号
					detail.setProductSkuId(stockOutDetail.getProductSkuId());// 产品skuId
					detail.setProductSkuValue(stockOutDetail.getProductSkuValue());// 产品skuCode
					detail.setQuantity(stockOutDetail.getQuantity());// 明细单数量
					detail.setPrice(stockOutDetail.getPrice());// 产品价格
					detail.setSum(stockOutDetail.getSum());// 金额
					distributionDetailInfos.add(detail);// 添加到集合
				}
				//进行配送单保存
				distributionDetailInfoService.saveDistributionDetailList(distributionDetailInfos, distributionInfo);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("配送单生成失败!", e);
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public ResultMessage auditStockOutForOther(List<StockOut> stockOutList) throws ServiceException {
		ResultMessage resultMessage = null;
		//出库前的对应库存数据
		List<ProductStock> stockList = new ArrayList();
		Map<Long,ProductStock> oldStockMap = null;
		List<Long> skuIdList = new ArrayList();

		try{
			int count = stockOutDao.batchUpdateForStockOut("STOCK_OUT.checkedStockOutByList", stockOutList);
			if (count<1) {
				throw new ServiceException("批量审核出库单失败!");
			}
				
			//出库明细单
			List<StockOutAndDetail> detailList =  selectStockOutDetailByStockOutId(stockOutList);
			Map<Long,Long> stockIdMap = productStockService.findStockIdMapByWarehouseAndSkuId(detailList);
			if(CollectionUtils.isEmpty(detailList)){
				throw new ServiceException("出库单数据问题，审核出库单失败!");
			}
			
			ProductStock stock = null;
			for(StockOutAndDetail detail : detailList){
				if(AuditStatus.AUDIT.getStatus().equals(detail.getStatus().toString())) {
                    stock = new ProductStock();
                    if (stockIdMap != null) {
                        stock.setStockId(stockIdMap.get(detail.getDetailId()));
                    } else {
                        stock.setStockId(detail.getStockId());
                    }
                    stock.setStockQuality(Long.valueOf(detail.getQuantity()));
                    stock.setWarehouseId(detail.getWarehouseId());
                    stock.setSkuAttributeId(detail.getProductSkuId());
                    stock.setSkuAttValue(detail.getProductSkuValue());
                    //为了记录库存日志，非持久化，不影响正常业务
                    stock.setBillDetailId(detail.getDetailId());
                    stock.setBillNo(detail.getStockOutNo());
                    stockList.add(stock);
                    //MQ通知搜索
                    skuIdList.add(stock.getSkuAttributeId());
                }
			}
			
			//出库前的对应库存数据
			oldStockMap = productStockService.selectProductStockListByStockList(stockList);
			
			//减库存数量或者订购数量
			resultMessage =productStockService.batchIncreaseStockQuantity(stockList, StockOutTypeStatus.OTHER.getStatus());

			//减少库存数量操作
			if(!resultMessage.getIsSuccess()){//如果出现false
				throw new ServiceException("审核出库单:更新库存操作失败!");				
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}

		resultMessage.setObject(stockList);
		resultMessage.setObject2(skuIdList);
		resultMessage.setMap(oldStockMap);
		return resultMessage;
	}

	@Transactional(readOnly=true,propagation=Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public ResultMessage deleteStockOutList(List<StockOut> stockOutList) throws ServiceException {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setIsSuccess(true);
		resultMessage.setMessage("批量删除出库单成功!");

		try {
            // 出库明细单批量删除
			int count = stockOutDao.batchDeleteForStockOut("STOCK_OUT.deleteStockOutDetailList", stockOutList);
			if (count < 1) {
                throw new ServiceException("批量删除出库单失败!");
            }
			
            //出库单批量删除
			count = stockOutDao.batchDeleteForStockOut("STOCK_OUT.deleteStockOutList", stockOutList);
			if (count < 1) {
                throw new ServiceException("批量删除出库单失败!");
            }
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("批量删除出库单失败!");
		}
		return resultMessage;
	}
	
	@Override
	public List<StockOutAndDetail> selectStockOutDetailByStockOutId(List<StockOut> stockOutList)
            throws ServiceException {
        try {
            return stockOutDao.selectStockOutDetailByStockOutId(stockOutList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public ResultMessage checkStockOutByWarehouse(List<Long> warehouseIdList) throws ServiceException {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setIsSuccess(true);
        try {
            Long count = stockOutDao.checkStockOutByWarehouse(warehouseIdList);
            if(count>0){
                resultMessage.setIsSuccess(false);
                resultMessage.setMessage("有部分仓库已被未处理完成的出库单引用，不能停用!");
            }
            return resultMessage;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public ResultMessage selectStockOutListByStockOutIds(List<Long> stockOutIdList) throws ServiceException {
		ResultMessage message = new ResultMessage();
		message.setIsSuccess(true);
		message.setMessage("批量审核出库单成功!");

        try {
            List<StockOut> stockOutList = stockOutDao.selectStockOutListByStockOutIds(stockOutIdList);
            if(CollectionUtils.isEmpty(stockOutList)){
                message.setIsSuccess(false);
                message.setMessage("出库单数据问题，批量审核出库单失败!");
                return message;
            }

            message.setObject(stockOutList);
            return message;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public void batchUpdateStatusForStockOut(List<StockOut> stockOutList) throws ServiceException {
		int count;
		try {
			count = stockOutDao.batchUpdateForStockOut("STOCK_OUT.updateStatusForExchangeStockout",stockOutList);
			if(count<1){
				throw new ServiceException("批量更新失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("系统错误,出库单批量更新失败!");
		}
	}
	
	@Override
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public synchronized void orderAfterByExchange_notThroughAudit(List<StockOut> stockOutList,String webSite)
            throws ServiceException {
		int count;
		try {
			count = stockOutDao.batchUpdateForStockOut("STOCK_OUT.updateStatusForExchangeStockout",stockOutList);
			if(count<1){
				throw new ServiceException("批量更新失败!");
			}
			
			//出库明细单
			List<StockOutAndDetail> detailList =  selectStockOutDetailByStockOutId(stockOutList);
			if(detailList==null || detailList.isEmpty()){
				throw new ServiceException("出库单数据问题!");
			}
			
			//解锁库存
			Map<String, Map<Long, Long>> upLockMap = new HashMap<String, Map<Long, Long>>();
			Map<Long, Long> paramMap = null;
			Long wareHouseId = null;
			Long stockQuality = null;
			for(StockOutAndDetail detail : detailList){
				String skuCode = detail.getProductSkuValue();
				if(upLockMap.get(skuCode)==null){
					paramMap = new HashMap<Long,Long>();
					paramMap.put(detail.getWarehouseId(), Long.valueOf(detail.getQuantity()));
					upLockMap.put(skuCode, paramMap);
				}else{
					paramMap = upLockMap.get(skuCode);
					wareHouseId = detail.getWarehouseId();
					stockQuality = Long.valueOf(detail.getQuantity());
					if (!paramMap.containsKey(wareHouseId)) {
						paramMap.put(wareHouseId, stockQuality);
					} else {// 否则要取出现有键值对中的value进行累加，然后替代当前的键值对
						stockQuality = paramMap.get(wareHouseId);
						stockQuality = detail.getQuantity()
								+ stockQuality;
						paramMap.remove(wareHouseId);
						paramMap.put(wareHouseId, stockQuality);
					}
				}
			}
			
			Map<String, Object> resultMap = stockRemoteService.batchUnLockStock(upLockMap,webSite);
			if(resultMap != null && resultMap.size() > 0){
			    for(String key : resultMap.keySet()){
			    	if("fail".equalsIgnoreCase(key)){
			    		throw new ServiceException("库存解锁失败!");
			    	}
			    }
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("系统错误,出库单批量更新失败!", e);
		}
	}
	
	public List<StockOutAftersaleReturn> selectStockOutListByReturnNo(List<StockOut> stockOutReturnNoList)
            throws ServiceException{
		try {
			return stockOutDao.selectStockOutAfterReturnListByReturnNo(stockOutReturnNoList);
		} catch (SQLException e) {
			throw new ServiceException("根据退换货单号,查找出库单和退换货信息失败!", e);
		}
	}

	public Map<String,String> getStockOutByOrderNo(List<String> orderNoList) throws ServiceException {
		try {
			return stockOutDao.getStockOutByOrderNo(orderNoList);
		} catch (SQLException e) {
			throw new ServiceException("根据订单号获取已经生成过出库单的订单号集合失败!", e);
		}
	}

    public void searchBySkuPage(Page page, ViewProductSku viewProductSku, Long warehouseId)
            throws ServiceException{
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;

        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        if(StringUtils.isNotBlank(viewProductSku.getProductTitle())){
            viewProductSku.setProductTitle(viewProductSku.getProductTitle().trim());
        }

        if(StringUtils.isNotBlank(viewProductSku.getProductSkuCode())){
            viewProductSku.setProductSkuCode(viewProductSku.getProductSkuCode().trim());
        }

        try {
            page.setRecordCount(stockOutDao.countViewProductSkuByExample(viewProductSku, warehouseId));
            page.setDataList(stockOutDao.viewProductSkuByObjectList(viewProductSku, skip, max, warehouseId));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
