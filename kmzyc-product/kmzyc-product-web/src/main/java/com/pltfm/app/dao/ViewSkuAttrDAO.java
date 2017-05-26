package com.pltfm.app.dao;

import com.pltfm.app.vobject.ViewSkuAttr;
import com.pltfm.app.vobject.ViewSkuAttrExample;
import java.sql.SQLException;
import java.util.List;

public interface ViewSkuAttrDAO {

    int countByExample(ViewSkuAttrExample example) throws SQLException;

    int deleteByExample(ViewSkuAttrExample example) throws SQLException;

    void insert(ViewSkuAttr record) throws SQLException;

    void insertSelective(ViewSkuAttr record) throws SQLException;

    List selectByExample(ViewSkuAttrExample example) throws SQLException;
    
    Long selectByAttrIdAndValueId(ViewSkuAttrExample example) throws SQLException;

    int updateByExampleSelective(ViewSkuAttr record, ViewSkuAttrExample example) throws SQLException;

    int updateByExample(ViewSkuAttr record, ViewSkuAttrExample example) throws SQLException;
    
    List<ViewSkuAttr> findProductAttrAndValueByProductId(Long productId) throws SQLException;
    
}