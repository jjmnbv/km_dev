package com.pltfm.app.dao;

import com.pltfm.app.vobject.AppraiseReply;
import com.pltfm.app.vobject.AppraiseReplyExample;

import java.sql.SQLException;
import java.util.List;

public interface AppraiseReplyDAO {

    int countByExample(AppraiseReplyExample example) throws SQLException;

    int deleteByExample(AppraiseReplyExample example) throws SQLException;

    int deleteByPrimaryKey(Long apprReplyId) throws SQLException;

    void insert(AppraiseReply record) throws SQLException;

    void insertSelective(AppraiseReply record) throws SQLException;

    List selectByExample(AppraiseReplyExample example) throws SQLException;

    List selectByExample(AppraiseReplyExample example, int skip, int max) throws SQLException;

    AppraiseReply selectByPrimaryKey(Long apprReplyId) throws SQLException;

    int updateByExampleSelective(AppraiseReply record, AppraiseReplyExample example) throws SQLException;

    int updateByExample(AppraiseReply record, AppraiseReplyExample example) throws SQLException;

    int updateByPrimaryKeySelective(AppraiseReply record) throws SQLException;

    int updateByPrimaryKey(AppraiseReply record) throws SQLException;

    void deleteByPrimaryKeyBatch(Long[] apprReplyIds) throws SQLException;

    void updateReplyStatusPassByPrimaryKeyBatch(Long[] apprReplyIds) throws SQLException;

    void updateReplyStatusUnPassByPrimaryKeyBatch(Long[] apprReplyIds) throws SQLException;

}