package com.pltfm.app.dao;

import com.kmzyc.commons.page.Page;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.example.SuppliersInfoExample;
import com.pltfm.app.vobject.SupplierCategoryName;
import com.pltfm.app.vobject.SupplierWarehouseName;

import java.sql.SQLException;
import java.util.List;

public interface SuppliersInfoDAO {

    List<SupplierCategoryName> getSupplierCategoryName(Integer levelType) throws SQLException;

    void saveSupplierCategoryName(List<SupplierCategoryName> firstCategoryNameList) throws SQLException;

    List<SupplierWarehouseName> getSuppliersWarehouseName() throws SQLException;

    void saveSuppliersWarehouseName(List<SupplierWarehouseName> supplierWarehouseNameList) throws SQLException;

    int countByExample(SuppliersInfoExample example) throws SQLException;

    int deleteByExample(SuppliersInfoExample example) throws SQLException;

    int deleteByPrimaryKey(Long supplierId) throws SQLException;

    void insert(SuppliersInfo record) throws SQLException;

    void insertSelective(SuppliersInfo record) throws SQLException;

    List selectByExample(SuppliersInfoExample example,Page page) throws SQLException;

    SuppliersInfo selectByPrimaryKey(Long supplierId) throws SQLException;

    int updateByExampleSelective(SuppliersInfo record, SuppliersInfoExample example) throws SQLException;

    int updateByExample(SuppliersInfo record, SuppliersInfoExample example) throws SQLException;

    int updateByPrimaryKeySelective(SuppliersInfo record) throws SQLException;

    int updateByPrimaryKey(SuppliersInfo record) throws SQLException;

    SuppliersInfo selectByMerchantId(Long merId) throws SQLException;

    /**
     * 根据商户id查询供应商数据
     * 
     * @param supplier
     * @return
     * @throws SQLException
     */
    SuppliersInfo selectByMerchantId(SuppliersInfo supplier) throws SQLException;

    /**
     * 根据产品id获取供应商类型
     * @param productId
     * @return
     * @throws SQLException
     */
    Integer getSupplierTypeByProductDraft(Long productId) throws SQLException;

    /**
     * 根据产品id获取供应商类型
     * @param productId
     * @return
     * @throws SQLException
     */
    Integer getSupplierTypeByProduct(Long productId) throws SQLException;


}