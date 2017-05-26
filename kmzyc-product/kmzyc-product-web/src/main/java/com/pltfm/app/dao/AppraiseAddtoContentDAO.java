package com.pltfm.app.dao;

import com.pltfm.app.vobject.AppraiseAddtoContent;
import com.pltfm.app.vobject.AppraiseAddtoContentExample;
import java.sql.SQLException;
import java.util.List;

public interface AppraiseAddtoContentDAO {

    int countByExample(AppraiseAddtoContentExample example) throws SQLException;

    int deleteByExample(AppraiseAddtoContentExample example) throws SQLException;

    int deleteByPrimaryKey(Long addContentId) throws SQLException;

    void insert(AppraiseAddtoContent record) throws SQLException;

    void insertSelective(AppraiseAddtoContent record) throws SQLException;

    List selectByExample(AppraiseAddtoContentExample example) throws SQLException;

    AppraiseAddtoContent selectByPrimaryKey(Long addContentId) throws SQLException;

    int updateByExampleSelective(AppraiseAddtoContent record, AppraiseAddtoContentExample example) throws SQLException;

    int updateByExample(AppraiseAddtoContent record, AppraiseAddtoContentExample example) throws SQLException;

    int updateByPrimaryKeySelective(AppraiseAddtoContent record) throws SQLException;

    int updateByPrimaryKey(AppraiseAddtoContent record) throws SQLException;
    
    List<AppraiseAddtoContent> findValidData(AppraiseAddtoContent record,int skip,int max) throws SQLException;
    
    int findValidDataCount(AppraiseAddtoContent record) throws SQLException;
    
    void deleteByPrimaryKeyBatch(Long[] addContentIds) throws SQLException;
    
    void updateAddContentStatusPassByPrimaryKeyBatch(Long[] addContentIds) throws SQLException;
    
    void updateAddContentStatusUnPassByPrimaryKeyBatch(Long[] addContentIds) throws SQLException;
}