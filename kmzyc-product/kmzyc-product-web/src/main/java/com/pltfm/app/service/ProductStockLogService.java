package com.pltfm.app.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.ProductStockLog;
import com.kmzyc.commons.page.Page;

/**
 * 产品库存操作日志业务接口
 *
 * @author Administrator
 */
public interface ProductStockLogService {

    /**
     * 根据 ProductStockLog 实体 的条件 ，分页page 进行查询
     *
     * @param prodStockLog
     * @param page
     * @throws ServiceException
     */
    void selectList(ProductStockLog prodStockLog, Page page) throws ServiceException;

    /**
     * 执行日志记录线程
     *
     * @param productId
     * @param productNo
     * @param skuId
     * @param skuCode
     * @param stockId
     * @param beforeQuantity
     * @param endQuantity
     * @param changeQuantity
     * @param opType
     * @param billDetailId
     * @param billNo
     * @param billType
     * @param userId
     * @param userName
     * @param createDate
     * @param remark
     * @throws ServiceException
     */
    void executeAddStockLog(final Long productId, final String productName, final String productNo,
                            final Long skuId, final String skuCode, final Long stockId, final Long warehouseId,
                            final Long beforeQuantity, final Long endQuantity, final Long changeQuantity,
                            final Short opType, final Long billDetailId, final String billNo,
                            final Short billType, final Integer userId, final String userName,
                            final Date createDate, final String remark) throws ServiceException;

    void executeBatchAddStockLog(List<ProductStock> stockList, Map<Long, ProductStock> oldStockMap, Short opType
            , Short billType, Integer userId, String userName, Date createDate, String remark) throws ServiceException;

    /**
     * 添加产品库存操作日志记录
     *
     * @param productStockLog
     * @throws ServiceException
     */
    void addStockLog(ProductStockLog productStockLog) throws ServiceException;

    void batchAddStockLog(List<ProductStockLog> productStockLogList) throws ServiceException;
}