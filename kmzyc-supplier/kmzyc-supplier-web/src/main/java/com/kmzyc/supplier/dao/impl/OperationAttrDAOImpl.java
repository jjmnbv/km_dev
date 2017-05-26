package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.dao.OperationAttrDAO;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.OperationAttrExample;

@Repository("operationAttrDAO")
public class OperationAttrDAOImpl extends BaseDAO implements OperationAttrDAO {

    public OperationAttr selectByPrimaryKey(Long operationAttrId) throws SQLException {
        OperationAttr key = new OperationAttr();
        key.setOperationAttrId(operationAttrId);
        return (OperationAttr) sqlMapClient.queryForObject("OPERATION_ATTR.selectByPrimaryKey", key);
    }

    @Override
    public List selectByExample(OperationAttrExample example) throws SQLException {
        return sqlMapClient.queryForList("OPERATION_ATTR.selectByExample", example);
    }
}