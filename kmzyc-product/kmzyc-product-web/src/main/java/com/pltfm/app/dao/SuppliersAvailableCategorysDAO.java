package com.pltfm.app.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.example.SuppliersAvailableCategorysExample;
import com.pltfm.app.vobject.Category;

public interface SuppliersAvailableCategorysDAO {

    int countByExample(SuppliersAvailableCategorysExample example) throws SQLException;

    int deleteByExample(SuppliersAvailableCategorysExample example) throws SQLException;

    int deleteByPrimaryKey(BigDecimal sacId) throws SQLException;

    void insert(SuppliersAvailableCategorys record) throws SQLException;

    void insertSelective(SuppliersAvailableCategorys record) throws SQLException;

    List selectByExample(SuppliersAvailableCategorysExample example) throws SQLException;

    SuppliersAvailableCategorys selectByPrimaryKey(BigDecimal sacId) throws SQLException;

    int updateByExampleSelective(SuppliersAvailableCategorys record, SuppliersAvailableCategorysExample example)
            throws SQLException;

    int updateByExample(SuppliersAvailableCategorys record, SuppliersAvailableCategorysExample example)
            throws SQLException;

    int updateByPrimaryKeySelective(SuppliersAvailableCategorys record) throws SQLException;

    int updateByPrimaryKey(SuppliersAvailableCategorys record) throws SQLException;

    List<SuppliersAvailableCategorys> findSupplierCategories(SuppliersAvailableCategorys cate) throws SQLException;

    List<SuppliersAvailableCategorys> findAllSupplierCategories() throws SQLException;

    List<SuppliersAvailableCategorys> existsSupplierCategories(SuppliersAvailableCategorys cate) throws SQLException;

    int deleteSupplierCategory(SuppliersAvailableCategorys gorys) throws SQLException;

    /**
     * 根据供应商id,类目id查询佣金比例
     *
     * @param cate
     * @return
     * @throws SQLException
     */
    SuppliersAvailableCategorys commissionRatioBySupplierIdAndCategoryId(SuppliersAvailableCategorys cate) throws SQLException;

    /**
     * 申请供应商选择类目显示
     *
     * @param supplierId
     * @return
     * @throws SQLException
     */
    List<Category> applySuppliersCategories(Long supplierId) throws SQLException;

}