package com.pltfm.app.dao;

import com.pltfm.app.vobject.SaleRank;
import com.pltfm.app.vobject.SaleRankExample;
import java.sql.SQLException;
import java.util.List;

public interface SaleRankDAO {

    int countByExample(SaleRankExample example) throws SQLException;

    int deleteByExample(SaleRankExample example) throws SQLException;

    int deleteByPrimaryKey(Long saleRankId) throws SQLException;

    void insert(SaleRank record) throws SQLException;

    void insertSelective(SaleRank record) throws SQLException;

    List selectByExample(SaleRankExample example) throws SQLException;

    SaleRank selectByPrimaryKey(Long saleRankId) throws SQLException;

    int updateByExampleSelective(SaleRank record, SaleRankExample example) throws SQLException;

    int updateByExample(SaleRank record, SaleRankExample example) throws SQLException;

    int updateByPrimaryKeySelective(SaleRank record) throws SQLException;

    int updateByPrimaryKey(SaleRank record) throws SQLException;
}