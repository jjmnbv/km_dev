package com.pltfm.app.dao;

import com.pltfm.app.vobject.ViewProductSku;
import com.pltfm.app.vobject.ViewProductSkuExample;
import java.sql.SQLException;
import java.util.List;

public interface ViewProductSkuDAO {

    int countByExample(ViewProductSkuExample example) throws SQLException;
    
    int countByExampleByUser(ViewProductSkuExample example) throws SQLException;

    int deleteByExample(ViewProductSkuExample example) throws SQLException;

    void insert(ViewProductSku record) throws SQLException;

    void insertSelective(ViewProductSku record) throws SQLException;

    List selectByExample(ViewProductSkuExample example) throws SQLException;

    List selectByExample(ViewProductSkuExample example,int skip,int max) throws SQLException;
    
    List selectByExampleByUser(ViewProductSkuExample example,int skip,int max) throws SQLException;
    
    List selectByExampleForFreightByUser(ViewProductSkuExample example,int skip,int max) throws SQLException;

    int updateByExampleSelective(ViewProductSku record, ViewProductSkuExample example) throws SQLException;

    int updateByExample(ViewProductSku record, ViewProductSkuExample example) throws SQLException;

    List selectSKUAttrByExample(ViewProductSkuExample example) throws SQLException;
    
}