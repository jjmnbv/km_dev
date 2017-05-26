package com.kmzyc.product.remote.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.StockRemoteService;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.AftersaleReturnOrderDAO;
import com.pltfm.app.dao.DistributionInfoDAO;
import com.pltfm.app.dao.StockOutDAO;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.enums.MsgOperation;
import com.pltfm.app.enums.StockLogBillType;
import com.pltfm.app.enums.StockLogOpType;
import com.pltfm.app.enums.StockOutStatus;
import com.pltfm.app.enums.StockOutTypeStatus;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ProductStockLogService;
import com.pltfm.app.service.ProductStockService;
import com.pltfm.app.service.StockOutService;
import com.pltfm.app.service.WarehouseInfoService;
import com.pltfm.app.util.BillCodeUtils;
import com.pltfm.app.util.BillPrefix;
import com.pltfm.app.util.SystemCommonUser;
import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.app.vobject.AftersaleReturnOrderExample;
import com.pltfm.app.vobject.CarryStockOutDetailVO;
import com.pltfm.app.vobject.CarryStockOutVO;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionInfo;
import com.pltfm.app.vobject.ProductAndSku;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.StockOut;
import com.pltfm.app.vobject.StockOutDetail;
import com.pltfm.app.vobject.WarehouseInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service("stockRemoteService")
public class StockRemoteServiceImpl implements StockRemoteService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private StockOutService stockOutService;

    @Resource
    private WarehouseInfoService warehouseInfoService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductStockService productStockService;

    @Resource
    private ProductStockLogService productStockLogService;

    @Resource
    private ProductService productService;

    @Resource
    private AftersaleReturnOrderDAO aftersaleReturnOrderDao;

    @Resource
    private StockOutDAO stockOutDao;

    @Resource
    private DistributionInfoDAO distributionInfoDao;

    @Override
    public Map<String, Map<Long, Long>> findStockBySkuIdAndWarehouseId(Map<String, List<Long>> paramMap)
            throws ServiceException {
        if (MapUtils.isEmpty(paramMap)) {
            throw new ServiceException("参数不合法!");
        }

        List<ProductStock> stockList = new ArrayList();
        List<Long> warehouseIdList = null;
        Map<String, Map<Long, Long>> mapOutSide = new HashMap();

        try {
            for (Entry<String, List<Long>> entry: paramMap.entrySet()) {
                warehouseIdList = entry.getValue();
                if (CollectionUtils.isNotEmpty(warehouseIdList)) {
                    stockList.addAll(warehouseIdList.stream().map(warehouseId -> {
                        ProductStock productStock = new ProductStock();
                        productStock.setSkuAttValue(entry.getKey());
                        productStock.setWarehouseId(warehouseId);
                        return productStock;
                    }).collect(Collectors.toList()));
                }
            }

            stockList = productStockService.remoteSelectStockQuantity(stockList);
            Map<Long, Long> mapInner = null;
            String skuCode = null;
            Long wareHouseId = null;
            for (ProductStock stock : stockList) {
                skuCode = stock.getSkuAttValue();
                // 不包含的skuCode，新建map放置（仓库Id,库存）
                if (!mapOutSide.containsKey(skuCode)) {
                    mapInner = new HashMap();
                    mapInner.put(stock.getWarehouseId(), stock.getStockQuality());
                    mapOutSide.put(skuCode, mapInner);
                } else {// 包含的skuCode，则第一，判断该skuCode下的map中的仓库id作为key，是否已经包含
                    mapInner = mapOutSide.get(skuCode);
                    wareHouseId = stock.getWarehouseId();
                    // 第二如果该仓库id不被包含，则需要put一个新的键值对进去
                    if (!mapInner.containsKey(wareHouseId)) {
                        mapInner.put(wareHouseId, stock.getStockQuality());
                    } else {// 否则要取出现有键值对中的value进行累加，然后替代当前的键值对
                        mapInner.put(wareHouseId, mapInner.get(wareHouseId) + stock.getStockQuality());
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException("查询库存出错!", e);
        }
        log.info("mapOutSide.size()=======" + mapOutSide.size());
        log.info("mapOutSide.keySet()=======" + mapOutSide.keySet());
        return mapOutSide;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean insertCarryStockOut(List<CarryStockOutVO> list) throws ServiceException {
        // 如果传过来的为空集合,抛异常
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException("参数不合法!");
        }
        ResultMessage message;
        List<ResultMessage> messageList = new ArrayList();

        try {
            //过滤已经生成出库单订单
            List<String> orderNoList = list.stream()
                    .map(CarryStockOutVO::getBillNo)
                    .collect(Collectors.toList());
            //根据订单号获取已经生成过出库单的订单号集合
            Map<String, String> orderNoMap = stockOutService.getStockOutByOrderNo(orderNoList);
            List<CarryStockOutVO> trimList = list.stream()
                    .filter(outVo -> !orderNoMap.containsKey(outVo.getBillNo()))
                    .collect(Collectors.toList());

            //集合无数据说明订单都已经生成过出库单
            if (CollectionUtils.isEmpty(trimList)) {
                return true;
            }

            // 出库单集合
            List<Map<StockOut, List<StockOutDetail>>> stockOutMapList = createStockOutData(trimList);
            // 配送单集合
            List<Map<DistributionInfo, List<DistributionDetailInfo>>> distributionMapList = createDistributionData(trimList);

            for (int i = 0; i < stockOutMapList.size(); i++) {
                //1.出库单
                Map<StockOut, List<StockOutDetail>> stockOutMap = stockOutMapList.get(i);
                StockOut stockOut = new StockOut();// 出库单
                List<StockOutDetail> detailList = null;// 出库明细单
                for (Entry<StockOut, List<StockOutDetail>> stockOutEntry : stockOutMap.entrySet()) {
                    stockOut = stockOutEntry.getKey();
                    detailList = stockOutEntry.getValue();
                }
                // 1.1出库单保存
                List<StockOut> stockOutList = saveStockOut(stockOut, detailList);
                if (CollectionUtils.isEmpty(stockOutList)) {
                    continue;
                }

                // 1.2出库单批量审核
                message = autoCheckedStockOut(stockOutList);
                if (message == null) {
                    log.error("出库单批量审核失败.");
                    throw new ServiceException("出库单批量审核失败.");
                }
                if (!message.getIsSuccess()) {
                    log.error("出库单批量审核失败:" + message.getMessage());
                    throw new ServiceException("出库单批量审核失败:" + message.getMessage());
                }
                messageList.add(message);

                // 2.配送单的生成
                Map<DistributionInfo, List<DistributionDetailInfo>> distributionMap = distributionMapList.get(i);
                DistributionInfo distributionInfo = new DistributionInfo();// 配送单
                List<DistributionDetailInfo> distributionDetailList = null;// 配送单明细单
                for (Entry<DistributionInfo, List<DistributionDetailInfo>> entry : distributionMap.entrySet()) {
                    distributionInfo        = entry.getKey();
                    distributionDetailList  = entry.getValue();
                }
                saveDistribution(stockOut.getStockOutNo(), distributionInfo, distributionDetailList);
            }
        } catch (Exception e) {
            log.error("订单结转出库接口失败，", e);
            throw new ServiceException(e);
        }

        try {
            for (ResultMessage msg : messageList) {
                //异步记录日志
                productStockLogService.executeBatchAddStockLog((List<ProductStock>) msg.getObject(),
                        msg.getMap(), StockLogOpType.OUT.getType(), StockLogBillType.STOCK_OUT.getType(),
                        null, "SYSTEM", new Date(), StockLogBillType.STOCK_OUT.getTitle());

                //减少库存后通知搜索引擎
                productService.changeProductInfoNotify((List<Long>) msg.getObject2(), MsgOperation.UPDATE.getType());
            }
        } catch (Exception e) {
            log.error("库存日志记录出错：", e);
        }

        return true;
    }

    /**
     * 保存出库单及其明细
     * @param stockOut  出库单
     * @param detailList    出库明细单
     * @return
     * @throws ServiceException
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private List<StockOut> saveStockOut(StockOut stockOut, List<StockOutDetail> detailList) throws ServiceException {
        List<StockOut> stockOutList = null;
        try {
            // 1、出库单保存
            Long stockOutId = stockOutDao.insertStockOutSelective(stockOut);

            // 2、批量保存出库明细单
            detailList.forEach(stockOutDetail -> stockOutDetail.setStockOutId(stockOutId));
            stockOutDao.batchInsertStockOut("STOCK_OUT_DETAIL.insertSelective", detailList);

            // 3.自动构建审核出库单信息
            stockOutList = new ArrayList();
            stockOut = new StockOut();
            stockOut.setStockOutId(stockOutId);// 设置stockOutId
            stockOut.setAuditUser(SystemCommonUser.SYSTEMCOMMONUSER_ID);// 审核人:登录用户ID
            stockOut.setCheckUserName(SystemCommonUser.SYSTEMCOMMONUSER_NAME);// 审核人姓名
            stockOut.setAuditDate(new Date());// 审核日期
            stockOut.setStatus(Short.valueOf(AuditStatus.AUDIT.getStatus()));// 状态
            stockOut.setModifierId(SystemCommonUser.SYSTEMCOMMONUSER_ID);// 出库创建人
            stockOut.setModifierName(SystemCommonUser.SYSTEMCOMMONUSER_NAME);// 名称
            stockOut.setModifiyDate(new Date());// 出库日期
            stockOutList.add(stockOut);
        } catch (Exception e) {
            throw new ServiceException("订单接口保存数据出错!", e);
        }
        return stockOutList;
    }

    /**
     * 保存配送单信息
     * @param stockOutNo    出库单号
     * @param info          配送单信息
     * @param detailList    配送单详情
     * @throws ServiceException
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private void saveDistribution(String stockOutNo, DistributionInfo info, List<DistributionDetailInfo> detailList)
            throws ServiceException {
        try {
            info.setBillNo(stockOutNo);// 设置配送单的bill_No为出
            // 1、配送单的保存
            Long distributionInfoId = distributionInfoDao.insertDistributionSelective(info);
            // 2、设置配送单ID
            detailList.stream().forEach(detailInfo -> detailInfo.setDistributionId(distributionInfoId));
            ///3、批量保存配送明细单
            distributionInfoDao.batchInsertDistribution("DISTRIBUTION_DETAIL_INFO.insertSelective", detailList);
        } catch (Exception e) {
            log.error("保存配送单出错", e);
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage autoCheckedStockOut(List<StockOut> stockOutList) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setIsSuccess(true);
        try {
            // 审核
            resultMessage = stockOutService.auditStockOutForOrder(stockOutList);
        } catch (Exception e) {
            log.error("自动审核出库单失败,", e);
            resultMessage.setIsSuccess(false);
            resultMessage.setMessage("自动审核出库单失败!");
            return resultMessage;
        }

        return resultMessage;
    }

    /**
     * 根据订单传过来的CarryStockOutVo(list)处理数据 生成出库单相关数据
     *
     * @param list
     * @return
     */
    private List<Map<StockOut, List<StockOutDetail>>> createStockOutData(List<CarryStockOutVO> list)
            throws ServiceException {
        // 返回的数据集合，每一个订单编号对应一个
        List<Map<StockOut, List<StockOutDetail>>> stockOutList = new ArrayList();
        Map<StockOut, List<StockOutDetail>> stockOutMap = null;
        List<StockOutDetail> stockOutDetails = null;
        CarryStockOutVO stockOutVo = null;
        CarryStockOutDetailVO stockOutDetailVo = null;

        for (int i = 0; i < list.size(); i++) {
            stockOutVo = list.get(i);
            stockOutMap = new HashMap();
            long warehouseId = -1;// 仓库ID
            String billNo = stockOutVo.getBillNo();// 订单编号
            int totalQuantity = 0;// 总数量
            BigDecimal totalMoney = new BigDecimal(0);// 总金额
            stockOutDetails = new ArrayList<>();
            for (int j = 0; j < stockOutVo.getDetailList().size(); j++) {
                stockOutDetailVo = stockOutVo.getDetailList().get(j);
                // 出库细目单
                StockOutDetail detail = new StockOutDetail();
                // 仓库ID
                warehouseId = stockOutDetailVo.getWarehouseId().intValue();
                // 金额累加
                totalMoney = totalMoney.add(stockOutDetailVo.getUnitPrice()
                        .multiply(new BigDecimal(stockOutDetailVo.getCommodityNumber())));
                // 数量累加
                totalQuantity += stockOutDetailVo.getCommodityNumber();

                // 根据product_sku_code,获得产品信息
                ProductAndSku p = null;
                Map<Long, ProductAndSku> pMap = productSkuService.findProduct(stockOutDetailVo.getSkuCode());
                for (Entry<Long, ProductAndSku> entry : pMap.entrySet()) {
                    p = entry.getValue();
                }
                detail.setProductId(p.getProductId());// 产品ID
                detail.setProductName(p.getName());// 产品名称
                detail.setProductNo(p.getProductNo());// 产品货号
                detail.setProductSkuId(p.getProductSkuId());// 产品skuId
                detail.setProductSkuValue(stockOutDetailVo.getSkuCode());// 产品skuCode
                detail.setBillDetailId(stockOutDetailVo.getBillDetailID());// 设置业务单据明细ID
                detail.setBillType(StockOutTypeStatus.ORDER.getStatus());// 单据类型(此处为订单)
                detail.setQuantity(stockOutDetailVo.getCommodityNumber().intValue());// 明细单数量
                detail.setPrice(stockOutDetailVo.getUnitPrice());// 产品价格
                detail.setSum(stockOutDetailVo.getUnitPrice()
                        .multiply(new BigDecimal(stockOutDetailVo.getCommodityNumber())));// 金额
                stockOutDetails.add(detail);// 添加到集合
            }
            // 出库单
            StockOut stockOut = new StockOut();
            // 根据订单传过来的，处理出库单和出库明细单
            stockOut.setCreateUser(SystemCommonUser.SYSTEMCOMMONUSER_ID);// 经手人
            stockOut.setCreateUserName(SystemCommonUser.SYSTEMCOMMONUSER_NAME);// 经手人姓名
            stockOut.setType(StockOutTypeStatus.ORDER.getStatus()); // 出库类型
            stockOut.setCreateDate(new Date());// 创建日期
            stockOut.setStatus(Short.valueOf(StockOutStatus.UNAUDIT.getStatus()));// 出库单状态:未审核
            stockOut.setDayEndStatus(Short.valueOf(StockOutStatus.UNAUDIT.getStatus()));// 日结状态
            stockOut.setWarehouseId(warehouseId);// 设置仓库
            stockOut.setTotalSum(totalMoney);// 设置总金额
            stockOut.setTotalQuantity(totalQuantity);// 设置出库单数量
            stockOut.setBillNo(billNo);// 设置订单编号
            stockOut.setModifierId(SystemCommonUser.SYSTEMCOMMONUSER_ID);// 出库人ID
            stockOut.setModifierName(SystemCommonUser.SYSTEMCOMMONUSER_NAME);// 名称
            stockOut.setModifiyDate(new Date());// 出库日期
            stockOut.setStockOutNo(BillCodeUtils.getBillCode(BillPrefix.STOCKOUT_PREFIX));// 出库单号生成
            stockOutMap.put(stockOut, stockOutDetails);
            stockOutList.add(stockOutMap);
        }
        return stockOutList;
    }

    /**
     * 根据订单传过来的CarryStockOutVo(list)处理数据 生成配送单相关数据
     *
     * @param list
     * @return
     */
    private List<Map<DistributionInfo, List<DistributionDetailInfo>>> createDistributionData(List<CarryStockOutVO> list)
            throws ServiceException {
        // 返回的对象的集合
        List<Map<DistributionInfo, List<DistributionDetailInfo>>> result = new ArrayList();

        for (int i = 0; i < list.size(); i++) {
            // 每一个订单编号对应一个map集合
            Map<DistributionInfo, List<DistributionDetailInfo>> infoMap = new HashMap();
            // 配送细目单
            List<DistributionDetailInfo> detailList = new ArrayList();
            // 配送单
            DistributionInfo distributionInfo = new DistributionInfo();
            long warehouseId = -1;
            int totalQuantity = 0;// 总数量
            BigDecimal totalSum = new BigDecimal(0);// 总金额
            CarryStockOutVO stockOutVo = list.get(i);

            for (int j = 0; j < stockOutVo.getDetailList().size(); j++) {
                // 配送细目单
                DistributionDetailInfo detail = new DistributionDetailInfo();
                CarryStockOutDetailVO detailVo = stockOutVo.getDetailList().get(j);
                warehouseId = detailVo.getWarehouseId().intValue();// 仓库ID
                totalSum = totalSum.add(detailVo.getUnitPrice()
                        .multiply(new BigDecimal(detailVo.getCommodityNumber())));// 金额累加
                totalQuantity += detailVo.getCommodityNumber();// 数量累加

                ProductAndSku p = null;
                Map<Long, ProductAndSku> pMap = productSkuService.findProduct(detailVo.getSkuCode());
                for (Entry<Long, ProductAndSku> entry : pMap.entrySet()) {
                    p = entry.getValue();
                }

                detail.setBillDetailId(detailVo.getBillDetailID());// 设置业务ID
                detail.setProductId(p.getProductId());// 产品ID
                detail.setProductName(p.getName());// 产品名称
                detail.setProductNo(p.getProductNo());// 产品货号
                detail.setProductSkuId(p.getProductSkuId());// 产品skuId
                detail.setProductSkuValue(p.getProductSkuCode());// 产品skuCode
                detail.setQuantity(detailVo.getCommodityNumber().intValue());// 明细单数量
                detail.setPrice(detailVo.getUnitPrice());// 产品价格
                detail.setSum(detailVo.getUnitPrice()
                        .multiply(new BigDecimal(detailVo.getCommodityNumber())));// 金额
                detailList.add(detail);// 添加到集合
            }
            // 根据仓库ID,查找仓库信息
            WarehouseInfo warehouseInfo = warehouseInfoService.findWarehouseInfoById(warehouseId);
            distributionInfo.setWarehouseId(warehouseId);// 仓库ID
            distributionInfo.setWarehouseName(warehouseInfo.getWarehouseName());// 仓库名称
            distributionInfo.setTotalSum(totalSum);// 总金额
            distributionInfo.setTotalQuantity(totalQuantity);// 总数量
            distributionInfo.setDistributionNo(BillCodeUtils.getBillCode(BillPrefix.DISPATCHING_PREFIX));// 设置生成配送单号
            distributionInfo.setUserName(stockOutVo.getPurchaserName());// 设置收货人
            distributionInfo.setDeliveryAddress(stockOutVo.getPurchaserAddrr());// 设置送货地址
            distributionInfo.setTel(stockOutVo.getPurchaserTel());// 设置送货电话
            distributionInfo.setLogisticsDate(new Date());// 设置配送日期
            distributionInfo.setOrderNo(String.valueOf(stockOutVo.getBillNo()));// 设置订单编号
            infoMap.put(distributionInfo, detailList);
            result.add(infoMap);// 添加到要返回的集合中
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void insertReturnOrder(AftersaleReturnOrder returnOrder) throws ServiceException {
        if (returnOrder == null) {
            throw new ServiceException("订单参数不能为空");
        }

        try {
            aftersaleReturnOrderDao.insert(returnOrder);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean lockStock(Long warehouseId, String skuCode, Long lockCount) throws ServiceException {
        if (warehouseId == null || StringUtils.isBlank(skuCode) || lockCount == null) {
            return false;
        }

        ProductStock stock = new ProductStock();
        stock.setWarehouseId(warehouseId);
        stock.setSkuAttValue(skuCode);
        stock.setOrderQuality(lockCount);
        try {
            productStockService.increaseOrderQuantity(stock);
        } catch (Exception e) {
            log.error("根据仓库ID、SKUCODE锁定库存数量lockCount失败，", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean unLockStock(Long warehouseId, String skuCode, Long unlockCount) throws ServiceException {
        if (warehouseId == null || StringUtils.isBlank(skuCode) || unlockCount == null) {
            return false;
        }

        ProductStock stock = new ProductStock();
        stock.setWarehouseId(warehouseId);
        stock.setSkuAttValue(skuCode);
        stock.setOrderQuality(unlockCount);
        try {
            productStockService.decreaseOrderQuantity(stock);
        } catch (Exception e) {
            log.error("根据仓库ID、SKUCODE解除锁定库存数量lockCount失败，", e);
            return false;
        }

        return true;
    }

    @Override
    public Map<String, Object> batchLockStock(Map<String, Map<Long, Long>> lockMap, String orderCode, String webSite)
            throws ServiceException {
        log.info("进入锁库存方法batchLockStock");
        Map<String, Object> returnMap = new HashMap();
        if (MapUtils.isEmpty(lockMap)) {
            log.error("批量锁库存fail,param is null");
            returnMap.put("fail", "param is null");
            return returnMap;
        }

        List<ProductStock> stockList = new ArrayList();
        ProductStock stock = null;
        for (Entry<String, Map<Long, Long>> entries :  lockMap.entrySet()) {
            String skuCode = entries.getKey();
            Map<Long, Long> warehouseLockMap = entries.getValue();
            for (Entry<Long, Long> lockEntries : warehouseLockMap.entrySet()) {
                stock = new ProductStock();
                stock.setSkuAttValue(skuCode);
                stock.setWarehouseId(lockEntries.getKey());
                stock.setLockCount(lockEntries.getValue());
                stock.setBillNo(orderCode);
                stockList.add(stock);
            }
        }

        try {
            returnMap = productStockService.batchIncreaseOrderQuantity(stockList, webSite);
        } catch (Exception e) {
            log.error("批量锁库存fail,system error", e);
            returnMap.put("fail", "system error");
            return returnMap;
        }

        log.info("锁库存方法batchLockStock返回结果success:" + returnMap.get("success"));
        log.info("锁库存方法batchLockStock返回结果fail:" + returnMap.get("fail"));
        return returnMap;
    }

    @Override
    public Map<String, Object> batchUnLockStock(Map<String, Map<Long, Long>> unLockMap, String webSite)
            throws ServiceException {
        Map<String, Object> returnMap = new HashMap();
        if (MapUtils.isEmpty(unLockMap)) {
            log.error("批量解锁库存fail,param is null");
            returnMap.put("fail", "param is null");
            return returnMap;
        }

        List<ProductStock> stockList = new ArrayList();
        ProductStock stock = null;
        for (Entry<String, Map<Long, Long>> entries :  unLockMap.entrySet()) {
            String skuCode = entries.getKey();
            Map<Long, Long> warehouseUnLockMap = entries.getValue();
            for (Entry<Long, Long> unLockEntries : warehouseUnLockMap.entrySet()) {
                stock = new ProductStock();
                stock.setSkuAttValue(skuCode);
                stock.setWarehouseId(unLockEntries.getKey());
                stock.setUnLockCount(unLockEntries.getValue());
                stockList.add(stock);
            }
        }

        try {
            returnMap = productStockService.batchDecreaseOrderQuantity(stockList, webSite);
        } catch (Exception e) {
            log.error("批量解锁库存fail,system error", e);
            returnMap.put("fail", "system error");
            return returnMap;
        }
        return returnMap;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteReturnOrder(String orderCode) throws ServiceException {
        if (!StringUtils.isEmpty(orderCode)) {
            throw new ServiceException("请输入退货单号！");
        }

        AftersaleReturnOrderExample exa = new AftersaleReturnOrderExample();
        exa.createCriteria().andOrderCodeEqualTo(orderCode);
        try {
            aftersaleReturnOrderDao.deleteByExample(exa);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean carryStockOutForSupplier(List<CarryStockOutVO> carryStockOutList) throws ServiceException {
        //1.参数校验
        if (CollectionUtils.isEmpty(carryStockOutList)) {
            throw new ServiceException("参数不合法!");
        }

        ResultMessage message ;
        List<ResultMessage> messageList = new ArrayList();

        //出库前的对应库存数据
        Map<Long, ProductStock> oldStockMap = null;
        List<Long> skuIdList = new ArrayList();
        Map<String, Long> quantityMap = null;
        try {
            for (int i = 0; i < carryStockOutList.size(); i++) {
                List<CarryStockOutDetailVO> detailList = carryStockOutList.get(i).getDetailList();
                quantityMap = new HashMap();
                for (CarryStockOutDetailVO vo : detailList) {
                    String key = vo.getWarehouseId() + "_" + vo.getSkuCode();
                    if (quantityMap.containsKey(key)) {
                        quantityMap.put(key, quantityMap.get(key) + vo.getCommodityNumber());
                    } else {
                        quantityMap.put(key, vo.getCommodityNumber());
                    }
                }


                List<ProductStock> stockList = productStockService.findProductStocksByWarehouseIdSkuCode(detailList);
                //出库前的对应库存数据
                oldStockMap = productStockService.selectProductStockListByStockList(stockList);

                for (ProductStock ps : stockList) {
                    skuIdList.add(ps.getSkuAttributeId());
                    ps.setStockQuality(quantityMap.get(ps.getWarehouseId() + "_" + ps.getSkuAttValue()));
                }

                //减库存数量或者订购数量
                message = productStockService.batchIncreaseStockQuantity(stockList, StockOutTypeStatus.ORDER.getStatus());

                //减少库存数量操作
                if (!message.getIsSuccess()) {
                    log.error(message.getMessage());
                    throw new ServiceException(message.getMessage());
                }

                message.setObject(stockList);
                message.setObject2(skuIdList);
                message.setMap(oldStockMap);
                messageList.add(message);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        try {
            for (ResultMessage temp : messageList) {
                //异步记录日志
                productStockLogService.executeBatchAddStockLog((List<ProductStock>) temp.getObject(),
                        temp.getMap(), StockLogOpType.OUT.getType(),
                        StockLogBillType.STOCK_OUT.getType(), null, "SYSTEM", new Date(),
                        StockLogBillType.STOCK_OUT.getTitle());

                //减少库存后通知搜索引擎
                productService.changeProductInfoNotify((List<Long>) temp.getObject2(),
                        MsgOperation.UPDATE.getType());
            }
        } catch (Exception e) {
            log.error("库存日志记录出错：", e);
            throw new ServiceException(e);
        }

        return true;
    }

    @Override
    public ResultMessage addStockForSupplier(Long supplierId, List<ProductSku> productSkuList) throws ServiceException {
        return productStockService.addStockForSupplier(supplierId, productSkuList);
    }

    @Override
    public Map<String, Object> batchLockStock4KJ(List<ProductStock> list) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //1.校验
        if (CollectionUtils.isEmpty(list)) {
            resultMap.put("isSuccess", Boolean.FALSE);
            resultMap.put("errorMessage", "请求参数为空.");
            return resultMap;
        }
        log.info(new StringBuilder("砍价商品[").append(list).append("]锁库存开始.").toString());

        //2.锁库存
        try {
            resultMap.put("data", productStockService.batchLockStock4KJ(list));
            resultMap.put("isSuccess", Boolean.TRUE);
        } catch (ServiceException e) {
            resultMap.put("isSuccess", Boolean.FALSE);
            resultMap.put("errorMessage", e.getMessage());
        }
        log.info(new StringBuilder("砍价商品[").append(list).append("]锁库存结束.").toString());
        return resultMap;
    }

    @Override
    public Map<String, Object> batchUnLockStock4KJ(List<ProductStock> list) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //1.校验
        if (CollectionUtils.isEmpty(list)) {
            resultMap.put("isSuccess", Boolean.FALSE);
            resultMap.put("errorMessage", "请求参数为空.");
            return resultMap;
        }
        log.info(new StringBuilder("砍价商品解锁库存[").append(list).append("]解锁库存开始.").toString());

        //2.解锁库存
        try {
            productStockService.batchUnLockStock4KJ(list);
            resultMap.put("isSuccess", Boolean.TRUE);
        } catch (ServiceException e) {
            resultMap.put("isSuccess", Boolean.FALSE);
            resultMap.put("errorMessage", e.getMessage());
        }

        log.info(new StringBuilder("砍价商品解锁库存[").append(list).append("]解锁库存结束.").toString());
        return resultMap;
    }

}