package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kmzyc.supplier.model.SuppliersWarehouse;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.ProductStockDAO;
import com.pltfm.app.enums.StockLogBillType;
import com.pltfm.app.enums.StockLogOpType;
import com.pltfm.app.enums.StockOutTypeStatus;
import com.pltfm.app.service.ProductStockLogService;
import com.pltfm.app.service.ProductStockService;
import com.pltfm.app.service.SupplierAuditService;
import com.pltfm.app.vobject.CarryStockOutDetailVO;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.ProductStockExample;
import com.pltfm.app.vobject.ProductStockExample.Criteria;
import com.pltfm.app.vobject.ProductStockPurchase;
import com.pltfm.app.vobject.StockOutAndDetail;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

@Component("productStockService")
public class ProductStockServiceImpl implements ProductStockService {

    private Logger log = LoggerFactory.getLogger(ProductStockServiceImpl.class);

    @Resource
    private ProductStockDAO productStockDao;

    @Resource
    private ProductStockLogService productStockLogService;

    @Resource
    private SupplierAuditService supplierAuditService;

    @Override
    public void searchPage(Page page, ProductStock stock) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProductStockExample ps = new ProductStockExample();
        Criteria criteria = ps.createCriteria();
        if (stock.getProduct() != null && StringUtils.isNotBlank(stock.getProduct().getProductTitle()))
            ps.setProduct(stock.getProduct());
        if (StringUtils.isNotBlank(stock.getProductNo()))
            ps.setProductNo(stock.getProductNo());
        if (stock.getWarehouseId() != null && stock.getWarehouseId() != 0l) // 所属仓库
            criteria.andWarehouseIdEqualTo(stock.getWarehouseId());
        if (StringUtils.isNotBlank(stock.getSkuAttValue())) // SKU_ID
            criteria.andSkuAttValueLike("%" + stock.getSkuAttValue().trim() + "%");
        if (stock.getBeginQuantity() != null) {
            criteria.andStockQualityGreaterThanOrEqualTo(stock.getBeginQuantity());
        }
        if (stock.getEndQuantity() != null) {
            criteria.andStockQualityLessThan(stock.getEndQuantity());
        }
        ps.setOrderByClause("STOCK_ID DESC");

        try {
            int count = productStockDao.countByExampleByProductTitle(ps);
            List<ProductStock> productStockList = productStockDao.selectByExampleByProductTitle(ps, skip, max);

            productStockList.stream()
                    .filter(st -> StringUtils.isNotBlank(st.getProduct().getProductTitle()))
                    .forEach(st -> {
                        st.getProduct().setProductTitle(
                                st.getProduct().getProductTitle().replaceAll(" ", "&nbsp;"));
                    });
            page.setDataList(productStockList);
            page.setRecordCount(count);
        } catch (Exception e) {
            log.error(" productStockId:" + stock.getProductId() + " searchPage方法出错", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void searchPageForAlarm(Page page, ProductStock stock) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProductStockExample ps = new ProductStockExample();
        Criteria criteria = ps.createCriteria();
        if (stock.getProduct() != null && StringUtils.isNotBlank(stock.getProduct().getProductTitle()))
            ps.setProduct(stock.getProduct());
        if (StringUtils.isNotBlank(stock.getProductNo()))
            ps.setProductNo(stock.getProductNo());
        if (stock.getWarehouseId() != null && stock.getWarehouseId() != 0l) // 所属仓库
            criteria.andWarehouseIdEqualTo(stock.getWarehouseId());
        if (StringUtils.isNotBlank(stock.getSkuAttValue())) // SKU_ID
            criteria.andSkuAttValueLike("%" + stock.getSkuAttValue().trim() + "%");
        if (stock.getBeginQuantity() != null) {
            criteria.andStockQualityGreaterThanOrEqualTo(stock.getBeginQuantity());
        }
        if (stock.getEndQuantity() != null) {
            criteria.andStockQualityLessThan(stock.getEndQuantity());
        }
        ps.setOrderByClause("STOCK_ID DESC");

        try {
            int count = productStockDao.countByExampleForAlarm(ps);
            List<ProductStock> productStockList = productStockDao.selectByExampleForAlarm(ps, skip, max);
            productStockList.stream()
                    .filter(st -> StringUtils.isNotBlank(st.getProduct().getProductTitle()))
                    .forEach(st -> {
                        st.getProduct().setProductTitle(
                                st.getProduct().getProductTitle().replaceAll(" ", "&nbsp;"));
                    });
            page.setDataList(productStockList);
            page.setRecordCount(count);
        } catch (Exception e) {
            log.error(" productStockId:" + stock.getProductId() + " searchPage方法出错", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage insertProductStock(ProductStock stock, Integer userId, String userName)
            throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        Long count = 0l;
        ProductStockExample pse = new ProductStockExample();
        Criteria criteria = pse.createCriteria();
        try {
            if (stock.getWarehouseId() != null) {
                criteria.andWarehouseIdEqualTo(stock.getWarehouseId());
            }
            if (stock.getSkuAttributeId() != null)
                criteria.andSkuAttributeIdEqualTo(stock.getSkuAttributeId());

            int num = productStockDao.countByExample(pse);
            if (num > 0) {
                return resultMessage;
            }

            count = productStockDao.insertProductStock(stock);
            if (count > 0) {
                // 异步记录库存日志
                productStockLogService.executeAddStockLog(stock.getProductId(), stock.getProductName(),
                        stock.getProductNo(), stock.getSkuAttributeId(), stock.getSkuAttValue(), count,
                        stock.getWarehouseId(), 0l, stock.getStockQuality(), stock.getStockQuality(),
                        StockLogOpType.OP_STOCK.getType(), null, null, StockLogBillType.ADD_STOCK.getType(),
                        userId, userName, new Date(), StockLogBillType.ADD_STOCK.getTitle());
                resultMessage.setIsSuccess(true);
                return resultMessage;
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return resultMessage;
    }

    @Override
    public ProductStock findProductStockById(Long id) throws ServiceException {
        try {
            return productStockDao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean updateProductStockById(ProductStock stock, Integer userId, String userName)
            throws ServiceException {
        try {
            ProductStock stockTmp = productStockDao.selectByPrimaryKey(stock.getStockId());
            int count = productStockDao.updateByPrimaryKey2(stock);
            if (count > 0) {
                // 异步记录库存日志
                productStockLogService.executeAddStockLog(stock.getProductId(), stock.getProductName(),
                        stock.getProductNo(), stock.getSkuAttributeId(), stock.getSkuAttValue(),
                        stock.getStockId(), stock.getWarehouseId(), stockTmp.getStockQuality(),
                        stock.getStockQuality(), stock.getStockQuality() - stockTmp.getStockQuality(),
                        StockLogOpType.OP_STOCK.getType(), null, null, StockLogBillType.UPDATE_STOCK.getType(),
                        userId, userName, new Date(), StockLogBillType.UPDATE_STOCK.getTitle());
                return true;
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return false;
    }

    @Override
    public boolean checkProductStockBySkuCode(String skuCode, Long warehouseId) throws ServiceException {
        if (StringUtils.isBlank(skuCode) || warehouseId == null) {
            return false;
        }

        ProductStockExample pse = new ProductStockExample();
        pse.createCriteria().andSkuAttValueEqualTo(skuCode).andWarehouseIdEqualTo(warehouseId);

        try {
            List<ProductStock> stockList = productStockDao.selectByExample(pse);
            return stockList.isEmpty() || stockList.size() == 0;
        } catch (SQLException e) {
            log.error(" skuCode:" + skuCode + ",warehouseId:" + warehouseId + " checkProductStockBySkuCode方法出错", e);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public synchronized void increaseOrderQuantity(ProductStock stock) throws ServiceException {
        if (stock == null) {
            return;
        }

        // 库存ID与（仓库ID+SKU_ID)都可以唯一标识唯一一条库存记录
        if (stock.getStockId() == null || (stock.getSkuAttributeId() == null && stock.getWarehouseId() == null)) {
            return;
        }

        try {
            productStockDao.increaseOrderQuantity(stock);
        } catch (SQLException e) {
            log.error(" productStockId:" + stock.getProductId() + " increaseOrderQuantity方法出错", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Map<String, Object> batchIncreaseOrderQuantity(List<ProductStock> stockList, String webSite)
            throws ServiceException {
        Map<String, Object> returnMap = new HashMap();
        if (stockList == null || stockList.isEmpty()) {
            returnMap.put("fail", "param is not");
            return returnMap;
        }

        try {
            Map<Long, ProductStock> oldStockMap = productStockDao.getProductStockMap(stockList);
            for (ProductStock stock : stockList) {
                int count = productStockDao.batchIncreaseOrderQuantity(stock);
                if (count < 1) {
                    log.error("lockStock fail!" + "WarehouseId=" + stock.getWarehouseId()
                            + "SkuAttValue=" + stock.getSkuAttValue());
                    returnMap.put("fail", "lockStock fail!" + "WarehouseId=" + stock.getWarehouseId()
                            + "SkuAttValue=" + stock.getSkuAttValue());
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return returnMap;
                }
            }

            List<ProductStock> newStockList = productStockDao.getProductStockList(stockList);
            // 异步记录订购数量变化日志
            productStockLogService.executeBatchAddStockLog(newStockList, oldStockMap,
                    StockLogOpType.ADD_ORDER.getType(), StockLogBillType.STOCK_OUT.getType(), null, null,
                    new Date(), StockLogBillType.ORDER.getTitle());
            productStockDao.batchUpdatePromotionSell(stockList);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        returnMap.put("success", "success");
        log.info("batchIncreaseOrderQuantity方法返回结果success.");
        return returnMap;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public synchronized void decreaseOrderQuantity(ProductStock stock) throws ServiceException {
        if (stock == null) {
            return;
        }

        // 库存ID与（仓库ID+SKU_ID)都可以唯一标识唯一一条库存记录
        if (stock.getStockId() == null || (stock.getSkuAttributeId() == null && stock.getWarehouseId() == null)) {
            return;
        }

        try {
            productStockDao.decreaseOrderQuantity(stock);
        } catch (SQLException e) {
            log.error(" productStockId:" + stock.getProductId() + " decreaseOrderQuantity方法出错", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage batchIncreaseStockQuantity(List<ProductStock> stockList, Short stockOutType)
            throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setIsSuccess(true);
        resultMessage.setMessage("库存数量更新成功!");
        int count = 0;
        if (CollectionUtils.isEmpty(stockList)) {
            log.error("参数不合法!");
            resultMessage.setIsSuccess(false);
            resultMessage.setMessage("参数不合法!");
            return resultMessage;
        }

        try {
            // 订单出库 或 换货出库
            if (StockOutTypeStatus.ORDER.getStatus().shortValue() == stockOutType.shortValue()
                    || StockOutTypeStatus.EXCHANGE.getStatus().shortValue() == stockOutType.shortValue()) {
                List<ProductStock> stockListTmp = productStockDao.checkStockOutForOrder(stockList);
                if (stockListTmp != null && !stockListTmp.isEmpty()) {
                    if (CollectionUtils.isNotEmpty(stockListTmp)) {
                        stockListTmp.forEach(stock -> log.error("不符合出库条件:"
                                + "WarehouseId=" + stock.getWarehouseId()
                                + ",SkuAttValue=" + stock.getSkuAttValue()));
                    }
                }

                count = productStockDao.batchUpdateForStock("PRODUCT_STOCK.decreaseProductStockForOrder2", stockList);
            } else if (StockOutTypeStatus.OTHER.getStatus().shortValue() == stockOutType.shortValue()) {// 其它出库
                List<ProductStock> stockListTmp = productStockDao.checkStockOutForOther(stockList);
                if (stockListTmp != null && !stockListTmp.isEmpty()) {
                    if (CollectionUtils.isNotEmpty(stockListTmp)) {
                        stockListTmp.forEach(stock -> log.error("不符合出库条件:"
                                + "WarehouseId=" + stock.getWarehouseId()
                                + ",SkuAttValue=" + stock.getSkuAttValue()));
                    }
                }

                count = productStockDao.batchUpdateForStock("PRODUCT_STOCK.decreaseProductStock", stockList);
            }

            if (count < 1) {
                log.error("库存数量更新失败!");
                throw new ServiceException("库存数量更新失败!");
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        return resultMessage;
    }

    @Override
    public List<ProductStock> remoteSelectStockQuantity(List<ProductStock> stockList) throws ServiceException {
        try {
            return productStockDao.remoteSelectStockQuantity(stockList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkStockByWarehouseList(List<ProductStock> stockList) throws ServiceException {
        try {
            long count = productStockDao.checkStockByWarehouseList(stockList);
            return count <= 0;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer selectRemainQuantityBySkuId(Long skuId) throws ServiceException {
        try {
            return productStockDao.selectRemainQuantityBySkuId(skuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductStock> selectStockListBySkuId(Long skuId) throws ServiceException {
        try {
            return productStockDao.selectStockListBySkuId(skuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Long, ProductStock> selectProductStockListByStockList(List<ProductStock> stockList)
            throws ServiceException {
        try {
            return productStockDao.selectProductStockListByStockList(stockList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Map<String, Object> batchDecreaseOrderQuantity(List<ProductStock> stockList, String webSite)
            throws ServiceException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (stockList == null || stockList.isEmpty()) {
            returnMap.put("fail", "param is not");
            return returnMap;
        }

        try {
            List<ProductStock> stockListTmp = productStockDao.checkAvailableStockForUnLock(stockList);
            if (stockListTmp != null && !stockListTmp.isEmpty()) {
                returnMap.put("fail", stockListTmp);
                return returnMap;
            }

            int count = productStockDao.batchDecreaseOrderQuantity(stockList);
            if (count < 1) {
                log.error("unLockStock fail");
                returnMap.put("fail", "unLockStock fail");
                return returnMap;
            }
            // 更新数据库和缓存活动销量
            productStockDao.batchUpdatePromotionSellMin(stockList);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        returnMap.put("success", "success");
        return returnMap;
    }

    @Override
    public Long findProductSkuStockCountBySkuId(Long productSkuId) throws ServiceException {
        try {
            return productStockDao.findProductSkuStockCountBySkuId(productSkuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public List<ProductStock> findProductStocksByWarehouseIdSkuCode(List<CarryStockOutDetailVO> carryStockOutDetailVOs)
            throws ServiceException {
        try {
            return productStockDao.findProductStockListByWarehouseSkuCode(carryStockOutDetailVOs);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public synchronized ResultMessage addStockForSupplier(Long supplierId, List<ProductSku> productSkuList)
            throws ServiceException {
        ResultMessage message = new ResultMessage();
        message.setIsSuccess(true);

        try {
            List<SuppliersWarehouse> suppliersWarehouseList = supplierAuditService.selectBySupplierId(supplierId);
            Long warehouseId = suppliersWarehouseList.get(0).getWarehouseId();
            ProductStock stock = null;
            List<ProductStock> stockList = new ArrayList<ProductStock>();
            for (ProductSku sku : productSkuList) {
                stock = new ProductStock();
                stock.setWarehouseId(warehouseId);
                stock.setProductNo(sku.getProduct().getProductNo());
                stock.setSkuAttributeId(sku.getProductSkuId());
                stock.setSkuAttValue(sku.getProductSkuCode());
                stock.setProductId(sku.getProductId());
                stock.setStockQuality(sku.getStock());
                stockList.add(stock);
            }

            List<Long> isExistSkuIdList = productStockDao.findIsExistSkuStock(stockList);
            List<ProductStock> addStockList = new ArrayList<ProductStock>();
            List<ProductStock> updateStockList = new ArrayList<ProductStock>();
            for (ProductStock ps : stockList) {
                if (isExistSkuIdList.contains(ps.getSkuAttributeId())) {
                    updateStockList.add(ps);
                } else {
                    addStockList.add(ps);
                }
            }
            int addCount = -1;
            int updateCount = -1;
            if (CollectionUtils.isNotEmpty(addStockList)) {
                addCount = productStockDao.batchAddForStock(addStockList);
            }
            if (CollectionUtils.isNotEmpty(updateStockList)) {
                updateCount = productStockDao.updateSkuStockForSupplierBatch(updateStockList);
            }
            if (addCount == 0 || updateCount == 0) {
                throw new ServiceException("供应商ID为：" + supplierId + "的库存批量增加失败!");
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return message;
    }

    @Override
    public Map<Long, Long> findStockIdMapByWarehouseAndSkuId(List<StockOutAndDetail> detailList)
            throws ServiceException {
        Map<Long, Long> stockIdMap = new HashMap<Long, Long>();
        try {
            List<StockOutAndDetail> list = productStockDao.findStockIdMapByWarehouseAndSkuId(detailList);
            for (StockOutAndDetail obj : list) {
                stockIdMap.put(obj.getDetailId(), obj.getStockId());
            }

        } catch (SQLException e) {
            throw new ServiceException(e);
        }

        return stockIdMap;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Map<String, Object> batchLockStockForAfter(Map<String, Map<Long, Long>> lockMap) throws ServiceException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (lockMap == null || lockMap.isEmpty()) {
            log.error("批量锁库存fail,param is null");
            returnMap.put("fail", "param is null");
            return returnMap;
        }

        List<ProductStock> stockList = new ArrayList();
        for (Map.Entry<String, Map<Long, Long>> entry : lockMap.entrySet()) {
            String skuCode = entry.getKey();
            Map<Long, Long> paramMap = entry.getValue();
            stockList.addAll(paramMap.entrySet().stream().map(stockEntry -> {
                ProductStock stock = new ProductStock();
                stock.setSkuAttValue(skuCode);
                stock.setWarehouseId(stockEntry.getKey());
                stock.setLockCount(stockEntry.getValue());
                return stock;
            }).collect(Collectors.toList()));
        }

        try {
            returnMap = this.batchLockOrderQuantityForAfter(stockList);
        } catch (Exception e) {
            log.error("批量锁库存fail,system error" + e.getMessage(), e);
            throw new ServiceException(e);
        }

        return returnMap;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Map<String, Object> batchLockOrderQuantityForAfter(List<ProductStock> stockList) throws ServiceException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (stockList == null || stockList.isEmpty()) {
            returnMap.put("fail", "param is not");
            return returnMap;
        }

        try {
            List<ProductStock> stockListTmp = productStockDao.checkAvailableStockForLockForAfter(stockList);
            if (CollectionUtils.isNotEmpty(stockListTmp)) {
                returnMap.put("fail", stockListTmp);
                return returnMap;
            }

            for (ProductStock stock : stockList) {
                int count = productStockDao.batchIncreaseOrderQuantityForAfter(stock);
                if (count < 1) {
                    log.error("lockStock fail!" + "WarehouseId=" + stock.getWarehouseId()
                            + "SkuAttValue=" + stock.getSkuAttValue());
                    returnMap.put("fail", "lockStock fail!" + "WarehouseId=" + stock.getWarehouseId()
                            + "SkuAttValue=" + stock.getSkuAttValue());
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return returnMap;
                }
            }

        } catch (Exception e) {
            throw new ServiceException(e);
        }

        returnMap.put("success", "success");
        return returnMap;
    }

    @Override
    public List<ProductStock> findByProductId(Long productId) throws ServiceException {
        try {
            return productStockDao.findByProductId(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, Object> getWarehouseIdBySkuCode(List<ProductStock> list) throws ServiceException {
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException(100, "skuCode为空！");
        }

        try {
            //1.获取仓库
            Map<String, Object> warehouseMap = productStockDao.getWarehouseIdBySkuCode(list);
            if (MapUtils.isEmpty(warehouseMap)) {
                throw new ServiceException(100, "没有找到仓库号。");
            }

            //2.校验
            for (ProductStock stock : list) {
                String skuCode = stock.getSkuAttValue();
                if (warehouseMap.get(skuCode) == null) {
                    throw new ServiceException(200, new StringBuilder("skuCode为")
                            .append(skuCode)
                            .append("的商品没有找到仓库号。")
                            .toString());
                }
            }
            return warehouseMap;
        } catch (SQLException e) {
            log.error("根据skuCode列表获取对应的最大库存仓库id失败：", e);
            throw new ServiceException(400, "根据skuCode列表获取对应的最大库存仓库id失败.");
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Map<String, Object> batchLockStock4KJ(List<ProductStock> list) throws ServiceException {
        //1.校验数据
        checkProductStock(list, Boolean.FALSE);

        //2.根据skuCode获取对应的最大库存的仓库id
        Map<String, Object> warehouseMap = getWarehouseIdBySkuCode(list);

        //3.锁库存
        StringBuilder errorMessage = new StringBuilder("砍价商品锁库存失败,");
        try {
            for (ProductStock stock : list) {
                long warehouseId = Long.parseLong(warehouseMap.get(stock.getSkuAttValue()).toString());
                stock.setWarehouseId(warehouseId);
                int count = productStockDao.batchIncreaseOrderQuantity(stock);
                if (count != 1) {
                    errorMessage.append("仓库id[")
                            .append(warehouseId)
                            .append("],SkuCode[")
                            .append(stock.getSkuAttValue())
                            .append("],锁库存数[")
                            .append(stock.getLockCount())
                            .append("],影响行数[")
                            .append(count)
                            .append("].");
                    throw new ServiceException(300, errorMessage.toString());
                }
            }
        } catch (SQLException e) {
            log.error("砍价商品锁库存失败：", e);
            throw new ServiceException(400, "砍价项目锁库存失败，请稍后再试！");
        }

        //3.锁库存日志记录
        try {
            Map<Long, ProductStock> oldStockMap = productStockDao.getProductStockMap(list);
            List<ProductStock> newStockList = productStockDao.getProductStockList(list);
            // 异步记录订购数量变化日志
            productStockLogService.executeBatchAddStockLog(newStockList, oldStockMap,
                    StockLogOpType.ADD_ORDER.getType(), StockLogBillType.KJ_ORDER.getType(), null, null,
                    new Date(), StockLogBillType.ORDER.getTitle());
        } catch (Exception e) {
            log.error("锁库存日志出错：", e);
        }

        return warehouseMap;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchUnLockStock4KJ(List<ProductStock> list) throws ServiceException {
        try {
            //1.构造解锁条件
            checkProductStock(list, Boolean.TRUE);

            //2.校验解锁库存的sku是否符合条件
            List<ProductStock> problemProductStockList = productStockDao.checkAvailableStockForUnLock(list);
            if (CollectionUtils.isNotEmpty(problemProductStockList)) {
                throw new ServiceException(200, handleErrorMessage(problemProductStockList, list));
            }

            //3.解锁库存
            int count = productStockDao.batchDecreaseOrderQuantity(list);
            if (count != 1) {
                throw new ServiceException(300, "砍价商品解锁库存失败，影响行数为" + count + "，请稍后再试！");
            }
        } catch (SQLException e) {
            log.error("砍价商品解锁库存失败：", e);
            throw new ServiceException(400, "砍价商品解锁库存失败，数据操作失败，请稍后再试！");
        }
    }

    /**
     * 构造无法解锁的错误信息
     *
     * @param problemProductStockList 错误库存
     * @param sourceStockList         错误解锁数
     * @return
     */
    private String handleErrorMessage(List<ProductStock> problemProductStockList, List<ProductStock> sourceStockList) {
        StringBuilder errorMessage = new StringBuilder();
        //1.构造解订购数
        Map<String, Long> countMap = new HashMap<String, Long>();
        for (ProductStock productStock : sourceStockList) {
            countMap.put(productStock.getSkuAttValue(), productStock.getUnLockCount());
        }

        //2.校验并处理错误信息
        for (ProductStock stock : problemProductStockList) {
            String skuCode = stock.getSkuAttValue();
            if (StringUtils.isNotEmpty(skuCode)) {
                errorMessage.append("skuCode为[" + skuCode + "]在仓库[" + stock.getWarehouseId())
                        .append("]中的订购库存数[" + stock.getOrderQuality() + "]小于当前的解库存数[")
                        .append(countMap.get(skuCode) + "].\n");
            }
        }
        if (StringUtils.isNotBlank(errorMessage.toString())) {
            return errorMessage.insert(0, "解锁库存失败：").toString();
        }

        return "";
    }

    /**
     * 检查库存参数
     * <note>
     * 锁库存时检查   skuCode、锁订购数
     * 解锁库存时检查 skuCode、仓库号、解锁订购数
     * </note>
     *
     * @param list
     * @param wasUnlock true解锁/false锁
     * @return
     */
    private void checkProductStock(List<ProductStock> list, Boolean wasUnlock) {
        StringBuilder errorMessage = new StringBuilder();
        if (wasUnlock) {
            errorMessage.append("砍价商品解锁库存订购数失败,");
        } else {
            errorMessage.append("砍价商品锁库存订购数失败,");
        }

        boolean haveWrongParam = Boolean.FALSE;
        boolean needThrowException = Boolean.FALSE;

        for (ProductStock stock : list) {
            haveWrongParam = Boolean.FALSE;
            String skuCode = stock.getSkuAttValue();
            Long unlockCount = stock.getUnLockCount();
            Long lockCount = stock.getLockCount();
            Long warehouseId = stock.getWarehouseId();
            if (StringUtils.isEmpty(skuCode)) {
                haveWrongParam = Boolean.TRUE;
                needThrowException = Boolean.TRUE;
                errorMessage.append("sku编号为空,");
            }
            if (wasUnlock && warehouseId == null) {
                haveWrongParam = Boolean.TRUE;
                needThrowException = Boolean.TRUE;
                errorMessage.append("仓库Id为空,");
            }
            if (wasUnlock && unlockCount != null && unlockCount < 0) {
                haveWrongParam = Boolean.TRUE;
                needThrowException = Boolean.TRUE;
                errorMessage.append("锁库存订购数为负数,");
            }
            if (!wasUnlock && lockCount != null && lockCount < 0) {
                haveWrongParam = Boolean.TRUE;
                needThrowException = Boolean.TRUE;
                errorMessage.append("解锁库存订购数为负数,");
            }

            if (haveWrongParam) {
                errorMessage.append("SkuCode[" + skuCode);
                if (wasUnlock) {
                    errorMessage.append("],仓库id[" + warehouseId)
                            .append("],解锁库存订购数[" + unlockCount + "].");
                } else {
                    errorMessage.append("],锁库存订购数[" + lockCount + "].");
                }
            }
        }

        if (needThrowException) {
            throw new ServiceException(100, errorMessage.toString());
        }
    }
}
