package com.pltfm.app.dao;

import com.pltfm.app.vobject.AreaDict;
import com.pltfm.app.vobject.AreaDictExample;

import java.sql.SQLException;
import java.util.List;

public interface AreaDictDAO {

    int countByExample(AreaDictExample example) throws SQLException;

    int deleteByExample(AreaDictExample example) throws SQLException;

    int deleteByPrimaryKey(Integer areaId) throws SQLException;

    void insert(AreaDict record) throws SQLException;

    void insertSelective(AreaDict record) throws SQLException;

    List selectByExample(AreaDictExample example) throws SQLException;

    AreaDict selectByPrimaryKey(Integer areaId) throws SQLException;

    int updateByExampleSelective(AreaDict record, AreaDictExample example) throws SQLException;

    int updateByExample(AreaDict record, AreaDictExample example) throws SQLException;

    int updateByPrimaryKeySelective(AreaDict record) throws SQLException;

    int updateByPrimaryKey(AreaDict record) throws SQLException;
}