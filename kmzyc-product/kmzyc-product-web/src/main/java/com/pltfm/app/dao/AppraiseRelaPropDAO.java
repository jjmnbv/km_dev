package com.pltfm.app.dao;

import com.pltfm.app.vobject.AppraiseRelaProp;
import com.pltfm.app.vobject.AppraiseRelaPropExample;

import java.sql.SQLException;
import java.util.List;

public interface AppraiseRelaPropDAO {

    int countByExample(AppraiseRelaPropExample example) throws SQLException;

    int deleteByExample(AppraiseRelaPropExample example) throws SQLException;
    
    int deleteBatchByCagetoryId(String[] cagetoryIds) throws SQLException;
    
    int deleteBatchByAppraisePropId(Long[] appraisePropIds) throws SQLException;

    int deleteByPrimaryKey(Long appraiseRelaPropId) throws SQLException;

    void insert(AppraiseRelaProp record) throws SQLException;
    
    void insertBatch(List<AppraiseRelaProp> records) throws SQLException;

    void insertSelective(AppraiseRelaProp record) throws SQLException;

    List selectByExample(AppraiseRelaPropExample example) throws SQLException;

    AppraiseRelaProp selectByPrimaryKey(Long appraiseRelaPropId) throws SQLException;

    int updateByExampleSelective(AppraiseRelaProp record, AppraiseRelaPropExample example) throws SQLException;

    int updateByExample(AppraiseRelaProp record, AppraiseRelaPropExample example) throws SQLException;

    int updateByPrimaryKeySelective(AppraiseRelaProp record) throws SQLException;

    int updateByPrimaryKey(AppraiseRelaProp record) throws SQLException;
}