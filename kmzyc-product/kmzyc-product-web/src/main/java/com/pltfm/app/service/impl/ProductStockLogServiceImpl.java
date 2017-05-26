package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.pltfm.app.dao.ProductStockLogDAO;
import com.pltfm.app.service.ProductStockLogService;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.ProductStockLog;
import com.pltfm.app.vobject.ProductStockLogExample;
import com.pltfm.app.vobject.ProductStockLogExample.Criteria;
import com.kmzyc.commons.page.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("productStockLogService")
public class ProductStockLogServiceImpl implements ProductStockLogService {

    @Resource
    private ProductStockLogDAO productStockLogDao;

    @Resource
    private TaskExecutor taskExecutor;

    @Override
    public void selectList(ProductStockLog productLog, Page page) throws ServiceException {
        ProductStockLogExample example = new ProductStockLogExample();
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(productLog.getProductName())) {
            criteria.andProductNameLike("%" + productLog.getProductName() + "%");
        }
        if (StringUtils.isNotBlank(productLog.getProductNo())) {
            criteria.andProductNoLike("%" + productLog.getProductNo() + "%");
        }
        if (StringUtils.isNotBlank(productLog.getProductSkuCode())) {
            criteria.andProductSkuCodeLike("%" + productLog.getProductSkuCode() + "%");
        }
        if (StringUtils.isNotBlank(productLog.getDetailNo())) {
            criteria.andDetailNoLike("%" + productLog.getDetailNo() + "%");
        }
        if (StringUtils.isNotBlank(productLog.getUserName())) {
            criteria.andUserNameLike("%" + productLog.getUserName() + "%");
        }
        example.setOrderByClause("create_date desc");

        try {
            List<ProductStockLog> list = productStockLogDao.selectByExample(example, page);
            page.setDataList(list);
            page.setRecordCount(productStockLogDao.countByExample(example));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void executeAddStockLog(final Long productId, final String productName, final String productNo,
                                   final Long skuId, final String skuCode, final Long stockId, final Long warehouseId,
                                   final Long beforeQuantity, final Long endQuantity, final Long changeQuantity,
                                   final Short opType, final Long billDetailId, final String billNo, final Short billType,
                                   final Integer userId, final String userName, final Date createDate,
                                   final String remark) throws ServiceException {
        try {
            ProductStockLog stockLog = new ProductStockLog();
            stockLog.setProductId(productId);
            stockLog.setProductName(productName);
            stockLog.setProductNo(productNo);
            stockLog.setProductSkuId(skuId);
            stockLog.setProductSkuCode(skuCode);
            stockLog.setStockId(stockId);
            stockLog.setWarehouseId(warehouseId);
            stockLog.setBeforeQuantity(beforeQuantity);
            stockLog.setEndQuantity(endQuantity);
            stockLog.setChangeQuantity(changeQuantity);
            stockLog.setOpType(opType);
            stockLog.setBillDetailId(billDetailId);
            stockLog.setDetailNo(billNo);
            stockLog.setBillType(billType);
            stockLog.setUserId(userId);
            stockLog.setUserName(userName);
            stockLog.setCreateDate(createDate);
            stockLog.setRemark(remark);
            taskExecutor.execute(() -> addStockLog(stockLog));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void executeBatchAddStockLog(List<ProductStock> stockList, Map<Long, ProductStock> oldStockMap,
                                        Short opType, Short billType, Integer userId, String userName,
                                        Date createDate, String remark) throws ServiceException {
        try {
            ProductStockLog stockLog;
            ProductStock tempProductStock;
            List<ProductStockLog> stockLogList = new ArrayList();

            for(ProductStock stock : stockList){
                stockLog = new ProductStockLog();
                tempProductStock = stock;
                if (oldStockMap == null) {
                    tempProductStock.setBeginQuantity(0l);
                    tempProductStock.setEndQuantity(stock.getStockQuality());
                } else {
                    ProductStock oldStock = oldStockMap.get(stock.getStockId());
                    if (oldStock == null) {
                        continue;
                    }
                    tempProductStock = oldStock;
                    tempProductStock.setEndQuantity(oldStock.getStockQuality() - stock.getStockQuality());
                }

                stockLog.setProductId(tempProductStock.getProductId());
                stockLog.setProductName(tempProductStock.getProductName());
                stockLog.setProductNo(tempProductStock.getProductNo());
                stockLog.setProductSkuId(tempProductStock.getSkuAttributeId());
                stockLog.setProductSkuCode(tempProductStock.getSkuAttValue());
                stockLog.setStockId(tempProductStock.getStockId());
                stockLog.setWarehouseId(tempProductStock.getWarehouseId());
                stockLog.setBeforeQuantity(tempProductStock.getStockQuality());
                stockLog.setChangeQuantity(stock.getStockQuality());
                stockLog.setEndQuantity(tempProductStock.getEndQuantity());
                stockLog.setOpType(opType);
                stockLog.setBillDetailId(stock.getBillDetailId());
                stockLog.setDetailNo(stock.getBillNo());
                stockLog.setBillType(billType);
                stockLog.setUserId(userId);
                stockLog.setUserName(userName);
                stockLog.setCreateDate(createDate);
                stockLog.setRemark(remark);
                stockLogList.add(stockLog);
            }

            taskExecutor.execute(() -> batchAddStockLog(stockLogList));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void addStockLog(ProductStockLog productStockLog) throws ServiceException {
        try {
            productStockLogDao.insert(productStockLog);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchAddStockLog(List<ProductStockLog> productStockLogList) throws ServiceException {
        try {
            productStockLogDao.batchAddStockLog(productStockLogList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}