package com.pltfm.cms.dao;

import com.pltfm.cms.vobject.CmsShopData;
import com.pltfm.cms.vobject.CmsShopDataExample;

import java.sql.SQLException;
import java.util.List;

public interface CmsShopDataDAO {

    Integer delete(Integer shopDataId) throws SQLException;

    public List selectList(List shopDataIds) throws SQLException;

    Integer insert(CmsShopData cmsShopData) throws SQLException;


    CmsShopData select(Integer shopDataId) throws SQLException;

    public List selectAll(CmsShopData cmsShopData) throws SQLException;

    Integer update(CmsShopData cmsShopData) throws SQLException;

    public CmsShopData select2(CmsShopData cmsShopData) throws SQLException;

    CmsShopData selectByPrimaryKey(Integer shopDataId) throws SQLException;

    List selectByExample(CmsShopDataExample example) throws SQLException;

    /***
     * 供应商发布调用
     * */
    public List selectAll3(CmsShopData cmsShopData) throws SQLException;
}