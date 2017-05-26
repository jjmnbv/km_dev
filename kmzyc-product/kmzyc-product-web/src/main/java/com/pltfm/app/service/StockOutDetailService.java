package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.StockOut;
import com.pltfm.app.vobject.StockOutDetail;
import com.kmzyc.commons.page.Page;

import java.util.List;

/**
 * 接口：出库明细单Service
 *
 * @author luoyi
 * @createDate 2013/08/16
 */
public interface StockOutDetailService {

    /**
     * 分页查找所有的出库明细单列表
     *
     * @param stockOutId ：查询：出库单ID
     */
    List<StockOutDetail> findStockOutDetailList(Page page, StockOutDetail stockOutDetail, Long stockOutId)
            throws ServiceException;

    /**
     * 处理出库单:
     *
     * @param stockOutDetails :出库明细单list
     * @param stockOut        :操作类型:0是添加,1是修改
     */
    StockOut getStockOutByDetail(List<StockOutDetail> stockOutDetails, StockOut stockOut) throws ServiceException;

    /**
     * 保存出库明细单(同时要保存出库单)
     * 要求:必须在同一个事务中
     *
     * @param stockOut
     * @param stockOutDetails
     * @return
     */
    ResultMessage saveStockOutDetail(StockOut stockOut, List<StockOutDetail> stockOutDetails) throws ServiceException;

    /**
     * 修改出库明细单(同时要保存出库单)
     * 要求:必须在同一个事务中
     *
     * @param stockOut
     * @param stockOutDetails
     * @return
     */
    ResultMessage updateStockOutDetail(StockOut stockOut, List<StockOutDetail> stockOutDetails) throws ServiceException;

    /**
     * 查找所有的出库单列表(非分页)
     *
     * @param stockOutId ：查询：出库单ID
     */
    List<StockOutDetail> findStockOutDetailList(Long stockOutId) throws ServiceException;

}