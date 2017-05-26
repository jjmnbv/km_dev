package com.pltfm.app.dao;

import com.pltfm.app.vobject.FilterField;
import com.pltfm.app.vobject.FilterFieldExample;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * 回复审核实现dao
 */
public interface FilterFieldDAO {

    int countByExample(FilterFieldExample example) throws SQLException;

    int deleteByExample(FilterFieldExample example) throws SQLException;

    int deleteByPrimaryKey(BigDecimal fieldId) throws SQLException;

    void insert(FilterField record) throws SQLException;

    void insertSelective(FilterField record) throws SQLException;

    List selectByExample(FilterFieldExample example) throws SQLException;

    FilterField selectByPrimaryKey(BigDecimal fieldId) throws SQLException;

    int updateByExampleSelective(FilterField record, FilterFieldExample example) throws SQLException;

    int updateByExample(FilterField record, FilterFieldExample example) throws SQLException;

    int updateByPrimaryKeySelective(FilterField record) throws SQLException;

    int updateByPrimaryKey(FilterField record) throws SQLException;

    /**
     * 查询全部
     */
    List<FilterField> selectAllFilter() throws SQLException;
}