package com.pltfm.app.service;

import com.pltfm.app.vobject.ProductOperateLog;
import com.pltfm.app.vobject.ProductPriceRecord;
import com.kmzyc.commons.exception.ServiceException;

import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/10 16:32
 */
public interface ProductOperateLogService {

    /**
     * 保存日志
     * @param log
     * @return
     */
    Long saveProductOperateLog(ProductOperateLog log) throws ServiceException;

    /**
     * 批量保存日志
     * @param list
     * @return
     */
    void batchSaveProductOperateLog(List<ProductOperateLog> list) throws ServiceException;

    /**
     * 根据商品id查询日志操作
     * @param productId
     * @return
     */
    List<ProductOperateLog> getProductOperateLogList(Long productId) throws ServiceException;


}
