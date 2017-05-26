package com.kmzyc.supplier.service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.SuppliersInfo;

import java.sql.SQLException;

/**
 * 供应商信息Service
 *
 * @author luoyi
 * @createDate 2014/01/11
 */

public interface SupplierInfoService {

    /**
     * merchantId　供应商基本信息id
     *
     * @return
     * @throws ServiceException
     */
    SuppliersInfo findSuppliersInfo(long merchantId) throws ServiceException;

    /**
     * 根据供应商id查询供应商信息
     *
     * @param supplierId
     * @return
     * @throws SQLException
     */
    SuppliersInfo selectByPrimaryKey(Long supplierId) throws ServiceException;

    /**
     * 根据供应商id更新供应商店铺浏览量状态
     *
     * @param supplierId
     * @param status
     * @return
     * @throws SQLException
     */
    boolean updateShopBrowseStatus(Long supplierId, Short status) throws ServiceException;

}