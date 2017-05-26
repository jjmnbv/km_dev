package com.kmzyc.supplier.dao;


import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.example.SuppliersCertificateExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 供应商资质文件DAO
 *
 * @author luoyi
 * @createDate 2013/12/25
 */
public interface SuppliersCertificateDAO {

    /**
     * 根据条件查询所有列
     */
    int countByExample(SuppliersCertificateExample example) throws SQLException;

    /**
     * 根据条件删除
     */
    int deleteByExample(SuppliersCertificateExample example) throws SQLException;

    /**
     * 根据主键删除
     */
    int deleteByPrimaryKey(Long scId) throws SQLException;

    /**
     * 插入资质证书(要所有列)
     */
    void insert(SuppliersCertificate record) throws SQLException;

    /**
     * 插入资质证书(部分列)
     */
    void insertSelective(SuppliersCertificate record) throws SQLException;

    /**
     * 根据条件查询
     */
    List selectByExample(SuppliersCertificateExample example) throws SQLException;

    /**
     * 根据主键查询
     */
    SuppliersCertificate selectByPrimaryKey(Long scId) throws SQLException;

    /**
     * 更新资质文件(部分列更新)
     */
    int updateByExampleSelective(SuppliersCertificate record, SuppliersCertificateExample example) throws SQLException;

    /**
     * 根据条件更新资质文件(全部列更新)
     */
    int updateByExample(SuppliersCertificate record, SuppliersCertificateExample example) throws SQLException;

    /**
     * 根据主键更新(部分列)
     */
    int updateByPrimaryKeySelective(SuppliersCertificate record) throws SQLException;

    /**
     * 根据主键更新(全部列)
     */
    int updateByPrimaryKey(SuppliersCertificate record) throws SQLException;

    /**
     * 根据供应商id查询图片数量
     *
     * @param record
     * @return
     * @throws SQLException
     */
    int countBySupplierId(SuppliersCertificate record) throws SQLException;

    /**
     * 重审资质列表
     *
     * @param suppliersCertificate
     * @return
     * @throws SQLException
     * @author gongyan
     */
    List selectCertificateInfoList(SuppliersCertificate suppliersCertificate) throws SQLException;
}