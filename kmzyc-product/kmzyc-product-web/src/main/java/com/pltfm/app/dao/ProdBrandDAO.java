package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProdBrandExample;

import java.sql.SQLException;
import java.util.List;

public interface ProdBrandDAO {

    int countByExample(ProdBrandExample example) throws SQLException;

    int deleteByExample(ProdBrandExample example) throws SQLException;

    int deleteByPrimaryKey(Long brandId) throws SQLException;

    int deleteByPrimaryKeyBatch(String[] brandIds) throws SQLException;

    Long insert(ProdBrand record) throws SQLException;

    Long insertSelective(ProdBrand record) throws SQLException;

    List selectByExample(ProdBrandExample example, int skip, int max) throws SQLException;

    List<ProdBrand> selectByExample(ProdBrandExample example) throws SQLException;

    ProdBrand selectByPrimaryKey(Long brandId) throws SQLException;

    int updateByExampleSelective(ProdBrand record, ProdBrandExample example) throws SQLException;

    int updateByExample(ProdBrand record, ProdBrandExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProdBrand record) throws SQLException;

    int updateByPrimaryKey(ProdBrand record) throws SQLException;

    String checkProductCounts(Long brandId) throws SQLException;

    boolean checkProdBrandIsFromSupplier(Long brandId) throws SQLException;

    int updateProdBrandDraft(ProdBrand record) throws SQLException;
}