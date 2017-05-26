package com.kmzyc.supplier.service;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.SupplierFare;

public interface SupplierFareService {

    /**
     * 分页查询数据
     *
     * @param supplierFare
     * @param page
     * @return
     * @throws ServiceException
     */
    Pagination searchPage(SupplierFare supplierFare, Pagination page) throws ServiceException;

    /**
     * 添加运费信息
     *
     * @param supplierFare
     * @return
     * @throws ServiceException
     */
    Long insertFare(SupplierFare supplierFare) throws ServiceException;

    /**
     * 修改运费信息
     *
     * @param supplierFare
     * @return
     * @throws ServiceException
     */
    int updateFare(SupplierFare supplierFare) throws ServiceException;

    /**
     * 根据站点和供应商id查询运费数量
     *
     * @param supplierId
     * @return
     * @throws ServiceException
     */
    int countFareBySupplierId(Long supplierId) throws ServiceException;

    /**
     * 根据运费表主键id查询信息
     *
     * @param fareId
     * @return
     * @throws ServiceException
     */
    SupplierFare findByFareId(Long fareId) throws ServiceException;

    /**
     *
     * @param fareId
     * @return
     * @throws ServiceException
     */
    int deleteFare(Long fareId) throws ServiceException;
}