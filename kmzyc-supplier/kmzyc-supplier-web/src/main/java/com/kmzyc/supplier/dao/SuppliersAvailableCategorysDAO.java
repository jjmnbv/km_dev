package com.kmzyc.supplier.dao;


import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.example.SuppliersAvailableCategorysExample;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 供应商可用类目DAO
 *
 * @createDate 2013/12/25
 * @author luoyi
 */
public interface SuppliersAvailableCategorysDAO {

    int countByExample(SuppliersAvailableCategorysExample example) throws SQLException;

    int deleteByExample(SuppliersAvailableCategorysExample example) throws SQLException;

    int deleteByPrimaryKey(Long sacId) throws SQLException;

    void insert(SuppliersAvailableCategorys record) throws SQLException;

    void insertSelective(SuppliersAvailableCategorys record) throws SQLException;

    List selectByExample(SuppliersAvailableCategorysExample example) throws SQLException;

    SuppliersAvailableCategorys selectByPrimaryKey(Long sacId) throws SQLException;

    int updateByExampleSelective(SuppliersAvailableCategorys record, SuppliersAvailableCategorysExample example)
            throws SQLException;

    int updateByExample(SuppliersAvailableCategorys record, SuppliersAvailableCategorysExample example) throws SQLException;

    int updateByPrimaryKeySelective(SuppliersAvailableCategorys record) throws SQLException;

    int updateByPrimaryKey(SuppliersAvailableCategorys record) throws SQLException;

    /**
     * 查找所有的供应商范围信息
     *
     * @param supplierId
     * @return
     * @throws SQLException
     */
    List<SuppliersAvailableCategorys> findSuppliersAvailableCategorysBySUpplierId(Long supplierId) throws SQLException;

    /**
     * 根据供应商id查询供应商类目
     *
     * @return
     * @throws SQLException
     */
    List<SuppliersAvailableCategorys> findSupplierCategorys(SuppliersAvailableCategorys cate) throws SQLException;

    /**
     * 根据供应商id查询供应商类目
     *
     * @return
     * @throws SQLException
     */
    List<SuppliersAvailableCategorys> findSupplierIdCategorys(SuppliersAvailableCategorys cate) throws SQLException;


    /**
     * 根据供应商id删除记录
     *
     * @return
     * @throws SQLException
     */
    void delSupplierIdCategorys(Long supplierId) throws SQLException;

    /**
     * 根据供应商id和类目id添加记录
     *
     * @return
     * @throws SQLException
     */
    void insertSupplierIdCategorys(Map map) throws SQLException;

    /**
     * 获取当前供应商一级类目权限
     *
     * @param supplierId    供应商id
     * @return
     * @throws SQLException
     */
    List<String> findSupplierCategory(Long supplierId) throws SQLException;

}