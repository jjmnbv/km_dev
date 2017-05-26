package com.pltfm.app.dao;

import com.pltfm.app.vobject.AppraiseProp;
import com.pltfm.app.vobject.AppraisePropExample;

import java.sql.SQLException;
import java.util.List;

public interface AppraisePropDAO {

	int countByExample(AppraisePropExample example) throws SQLException;

	int deleteByExample(AppraisePropExample example) throws SQLException;

	int deleteByPrimaryKey(Long appraisePropId) throws SQLException;

	void deleteByExampleBatch(Long[] delIds) throws SQLException;

	Long insert(AppraiseProp record) throws SQLException;

	void insertSelective(AppraiseProp record) throws SQLException;

	List selectByExample(AppraisePropExample example) throws SQLException;

	List selectByExample(AppraisePropExample example, int skip, int max) throws SQLException;

	List selectByCategoryId(Long cateId) throws SQLException;

	AppraiseProp selectByPrimaryKey(Long appraisePropId) throws SQLException;

	int updateByExampleSelective(AppraiseProp record, AppraisePropExample example) throws SQLException;

	int updateByExample(AppraiseProp record, AppraisePropExample example) throws SQLException;

	int updateByPrimaryKeySelective(AppraiseProp record) throws SQLException;

	int updateByPrimaryKey(AppraiseProp record) throws SQLException;
}