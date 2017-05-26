package com.pltfm.app.dao;

import com.pltfm.app.vobject.Productmain;

import java.sql.SQLException;
import java.util.List;

public interface ProductmainDAO {
    /**
     * 跟据供应商查询产品
     */
    public List selectByExampleWithoutBLOBs(Productmain p) throws SQLException;
}