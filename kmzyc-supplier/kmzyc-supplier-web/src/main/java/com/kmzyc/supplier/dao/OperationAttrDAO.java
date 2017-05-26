package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.OperationAttrExample;

public interface OperationAttrDAO {
	
    OperationAttr selectByPrimaryKey(Long operationAttrId) throws SQLException;
    
    List selectByExample(OperationAttrExample example) throws SQLException;
    
}