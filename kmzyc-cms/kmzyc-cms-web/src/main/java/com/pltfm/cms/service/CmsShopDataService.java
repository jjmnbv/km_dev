package com.pltfm.cms.service;

import com.pltfm.cms.vobject.CmsShopData;

import java.sql.SQLException;

public interface CmsShopDataService {

    public Integer insertShopData(CmsShopData cmsShopData) throws SQLException;

    public CmsShopData selectById(Long shopDataId) throws NumberFormatException, SQLException;

    public void update(CmsShopData cmsShopData) throws SQLException;

}
