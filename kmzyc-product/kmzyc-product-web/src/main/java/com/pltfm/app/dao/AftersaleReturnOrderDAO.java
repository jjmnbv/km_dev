package com.pltfm.app.dao;

import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.app.vobject.AftersaleReturnOrderExample;
import com.pltfm.app.vobject.DistributionInfo;
import com.pltfm.app.vobject.StockOut;

import java.sql.SQLException;
import java.util.List;

public interface AftersaleReturnOrderDAO {

    int countByExample(AftersaleReturnOrderExample example) throws SQLException;

    int deleteByExample(AftersaleReturnOrderExample example) throws SQLException;

    int deleteByPrimaryKey(Long returnId) throws SQLException;

    void insert(AftersaleReturnOrder record) throws SQLException;

    void insertSelective(AftersaleReturnOrder record) throws SQLException;

    List selectByExample(AftersaleReturnOrderExample example) throws SQLException;

    List selectByExample(AftersaleReturnOrderExample example, int skip, int max) throws SQLException;

    AftersaleReturnOrder selectByPrimaryKey(Long returnId) throws SQLException;

    int updateByExampleSelective(AftersaleReturnOrder record, AftersaleReturnOrderExample example) throws SQLException;

    int updateByExample(AftersaleReturnOrder record, AftersaleReturnOrderExample example) throws SQLException;

    int updateByPrimaryKeySelective(AftersaleReturnOrder record) throws SQLException;

    int updateByPrimaryKey(AftersaleReturnOrder record) throws SQLException;

}