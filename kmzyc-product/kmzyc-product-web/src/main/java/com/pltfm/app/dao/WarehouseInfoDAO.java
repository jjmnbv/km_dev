package com.pltfm.app.dao;

import com.pltfm.app.vobject.WarehouseInfo;
import com.pltfm.app.vobject.WarehouseInfoExample;

import java.sql.SQLException;
import java.util.List;

public interface WarehouseInfoDAO {

    int countByExample(WarehouseInfoExample example) throws SQLException;

    int deleteByExample(WarehouseInfoExample example) throws SQLException;

    int deleteByPrimaryKey(Long nWarehouseId) throws SQLException;

    Long insert(WarehouseInfo record) throws SQLException;

    void insertSelective(WarehouseInfo record) throws SQLException;

    List selectByExample(WarehouseInfoExample example, int skip, int max) throws SQLException;

    List selectByExample(WarehouseInfoExample example) throws SQLException;

    WarehouseInfo selectByPrimaryKey(Long nWarehouseId) throws SQLException;

    int updateByExampleSelective(WarehouseInfo record, WarehouseInfoExample example) throws SQLException;

    int updateByExample(WarehouseInfo record, WarehouseInfoExample example) throws SQLException;

    int updateByPrimaryKeySelective(WarehouseInfo record) throws SQLException;

    int updateByPrimaryKey(WarehouseInfo record) throws SQLException;

    /**
     * 批量更新
     *
     * @param statementName
     * @param parameterList
     * @return
     * @throws SQLException
     */
    int batchUpdateForWarehouseInfo(String statementName, List<Object> parameterList) throws SQLException;

    int checkWarehouseInfoByName(String name) throws SQLException;

    List<WarehouseInfo> getWarehouseInfoByList(List<Object> warehouseInfoList) throws SQLException;

    List<Long> checkWarehouseNameByModify(String warehouseName) throws SQLException;

}