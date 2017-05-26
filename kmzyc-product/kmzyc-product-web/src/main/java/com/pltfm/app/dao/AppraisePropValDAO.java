package com.pltfm.app.dao;

import com.pltfm.app.vobject.AppraisePropVal;
import com.pltfm.app.vobject.AppraisePropValExample;

import java.sql.SQLException;
import java.util.List;

public interface AppraisePropValDAO {

    int countByExample(AppraisePropValExample example) throws SQLException;

    int deleteByExample(AppraisePropValExample example) throws SQLException;

    /**
     * 根据属性Id删除属性值
     *
     * @param propIds
     * @throws SQLException
     */
    void deleteByExampleBatch(Long[] propIds) throws SQLException;

    int deleteByPrimaryKey(Long appraisePropValId) throws SQLException;

    void insert(AppraisePropVal record) throws SQLException;
    
    void insertBatch(List<AppraisePropVal> records) throws SQLException;

    void insertSelective(AppraisePropVal record) throws SQLException;

    List selectByExample(AppraisePropValExample example) throws SQLException;

    AppraisePropVal selectByPrimaryKey(Long appraisePropValId) throws SQLException;

    int updateByExampleSelective(AppraisePropVal record, AppraisePropValExample example) throws SQLException;

    int updateByExample(AppraisePropVal record, AppraisePropValExample example) throws SQLException;

    int updateByPrimaryKeySelective(AppraisePropVal record) throws SQLException;
    
    int updateByPrimaryKeySelectiveBatch(List<AppraisePropVal> records) throws SQLException;

    int updateByPrimaryKey(AppraisePropVal record) throws SQLException;
}