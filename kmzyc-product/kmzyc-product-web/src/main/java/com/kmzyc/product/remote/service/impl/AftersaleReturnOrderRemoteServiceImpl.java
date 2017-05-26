package com.kmzyc.product.remote.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderAlterCallBackRemoteService;
import com.kmzyc.order.remote.OrderAlterChangeStatusRemoteService;
import com.kmzyc.product.remote.service.AftersaleReturnOrderRemoteService;
import com.pltfm.app.dao.AftersaleReturnOrderDAO;
import com.pltfm.app.dao.ProductStockDAO;
import com.pltfm.app.enums.ReturnOrExchangeHandleResult;
import com.pltfm.app.enums.ReturnOrExchangeStatus;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ProductStockService;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.ProductStock;


@Service("aftersaleReturnOrderRemoteService")
public class AftersaleReturnOrderRemoteServiceImpl implements AftersaleReturnOrderRemoteService {

    Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private AftersaleReturnOrderDAO aftersaleReturnOrderDao;

    @Resource
    private ProductService productService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductStockService productStockService;

    @Resource
    private ProductStockDAO productStockDao;

    @Resource
    private OrderAlterCallBackRemoteService orderAlterCallBackRemoteService;

    @Resource
    private OrderAlterChangeStatusRemoteService orderAlterChangeStatusRemoteService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateObject(AftersaleReturnOrder record, Integer userId, String userName,
                            boolean changeReturnOrder) throws ServiceException {
        try {
            int result = 1;
            //商品已收到
            if (ReturnOrExchangeStatus.ARRIVALOFGOODS.getKey().equals(record.getStatus())) {
                result = orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(userName, record.getOrderCode(), Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.Pickup.getKey()), null);
            }
            //拒绝退货
            if (ReturnOrExchangeHandleResult.UNPASS.getKey().equals(record.getHandleResult())) {
                result = orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(userName, record.getOrderCode(), Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.F_BackShop.getKey()), record.getDetectResult());
            }
            //同意退货
            if (ReturnOrExchangeHandleResult.PASS.getKey().equals(record.getHandleResult())) {
                //换货出库
                if (record.getOrderType().equals((short) OrderAlterDictionaryEnum.AlterTypes.EXchange.getKey())) {
                    Long productSkuId = productSkuService.findProductSkuByCode(record.getProductSku()).getProductSkuId();
                    Long warehouseId = productStockService.findProductSkuStockCountBySkuId(productSkuId);
                    if (warehouseId != null) {
                        //锁库存
                        Map<String, Map<Long, Long>> lockMap = new HashMap<String, Map<Long, Long>>();
                        Map<Long, Long> innerMap = new HashMap<Long, Long>();
                        innerMap.put(warehouseId, record.getProductCounts());
                        lockMap.put(record.getProductSku(), innerMap);
                        Map<String, Object> resultMap = productStockService.batchLockStockForAfter(lockMap);

                        if (resultMap != null && resultMap.size() > 0) {
                            for (String key : resultMap.keySet()) {
                                if ("fail".equalsIgnoreCase(key)) {
                                    if (changeReturnOrder) {
                                        record.setHandleResult(ReturnOrExchangeHandleResult.UNPASS.getKey());
                                        record.setDetectResult("该商品库存不足");
                                        aftersaleReturnOrderDao.updateByPrimaryKeySelective(record);
                                        result = orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(userName, record.getOrderCode(), Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn.getKey()), "该商品库存不足");
                                        if (result != 1) {
                                            throw new ServiceException("调用远程接口失败！");
                                        }
                                        return 1;
                                    } else {
                                        return 0;
                                    }
                                }
                            }
                        }

                        ProductStock stock = new ProductStock();
                        stock.setWarehouseId(warehouseId);
                        stock.setSkuAttributeId(productSkuId);
                        stock.setStockQuality(record.getProductCounts());
                        List<ProductStock> stockList = new ArrayList<ProductStock>();
                        stockList.add(stock);
                        List<ProductStock> stockListTmp = productStockDao.checkStockOutForOrder(stockList);
                        if (stockListTmp != null && !stockListTmp.isEmpty()) {
                            logger.error("不符合出库条件!");
                            for (ProductStock stockTmp : stockListTmp) {
                                logger.error(new StringBuilder("不符合出库条件!").append("WarehouseId=").append(stockTmp.getWarehouseId())
                                        .append("SkuAttValue=").append(stockTmp.getSkuAttValue()));
                            }
                        }
                        int count = productStockDao.batchUpdateForStock("PRODUCT_STOCK.decreaseProductStockForOrder2", stockList);
                        if (count < 1) {
                            logger.error("退换货减库存失败!");
                            throw new ServiceException("退换货减库存失败!");
                        }
                        result = orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(userName, record.getOrderCode(), Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.F_Stockout.getKey()), null);
                    } else {
                        result = orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(userName, record.getOrderCode(), Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn.getKey()), null);
                    }
                } else {
                    result = orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(userName, record.getOrderCode(), Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.F_Backpay.getKey()), null);
                }
            }
            if (result != 1) {
                throw new ServiceException("调用远程接口失败！");
            }
            aftersaleReturnOrderDao.updateByPrimaryKeySelective(record);

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return 1;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int setLogisticAndDistributionInfo(LogisticAndDistributionInfoVO logisticAndDistributionInfoVOInfo) throws ServiceException {
        int count = 1;
        try {
            LogisticAndDistributionInfoVO logisticAndDistributionInfoVO = new LogisticAndDistributionInfoVO();
            logisticAndDistributionInfoVO.setOrderCode(logisticAndDistributionInfoVOInfo.getOrderCode());
            logisticAndDistributionInfoVO.setLogisticsCode(logisticAndDistributionInfoVOInfo.getLogisticsCode());
            logisticAndDistributionInfoVO.setLogisticsName(logisticAndDistributionInfoVOInfo.getLogisticsName());
            logisticAndDistributionInfoVO.setLogisticsOrderNo(logisticAndDistributionInfoVOInfo.getLogisticsOrderNo());
            //logisticAndDistributionInfoVO.setIncludeMedicineFlag(false);
            //distributionInfoMap = new HashMap<Long,List<DistributionDetailInfo>>();
            //distributionInfoMap.put(distributionInfo.getDistributionId(), distributionDetailInfoList);
            //logisticAndDistributionInfoVO.setDistributionInfoMap(distributionInfoMap);

            if (StringUtils.isNotBlank(logisticAndDistributionInfoVOInfo.getLogisticsOrderNo())) {
                logger.info("换货单物流单号推送准备开始!");
                String result = orderAlterCallBackRemoteService.getLogisticNumber(logisticAndDistributionInfoVO);
                logger.info("换货单物流单号推送完成!返回结果=" + result);
                //orderRemoteService.getLogisticNumber(logisticAndDistributionInfoVO,Short.valueOf("3"));
            }
        } catch (Exception e) {
            count = 0;
            e.printStackTrace();
            throw new ServiceException("配送失败!");
        }
        return count;
    }
}
