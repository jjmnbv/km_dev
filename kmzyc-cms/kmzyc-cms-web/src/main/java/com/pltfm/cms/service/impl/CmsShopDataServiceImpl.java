package com.pltfm.cms.service.impl;

import com.pltfm.cms.dao.CmsShopDataDAO;
import com.pltfm.cms.service.CmsShopDataService;
import com.pltfm.cms.vobject.CmsShopData;

import org.springframework.stereotype.Component;

import java.sql.SQLException;

import javax.annotation.Resource;

@Component("cmsShopDataService")
public class CmsShopDataServiceImpl implements CmsShopDataService {

    @Resource(name = "cmsShopDataDAO")
    private CmsShopDataDAO cmsShopDataDAO;

    @Override
    public Integer insertShopData(CmsShopData cmsShopData) throws SQLException {
        // TODO Auto-generated method stub
        return cmsShopDataDAO.insert(cmsShopData);
    }

    @Override
    public CmsShopData selectById(Long shopDataId) throws NumberFormatException, SQLException {
        return cmsShopDataDAO.selectByPrimaryKey(Integer.valueOf(shopDataId.toString()));
    }

    @Override
    public void update(CmsShopData cmsShopData) throws SQLException {
        cmsShopDataDAO.update(cmsShopData);
    }

}
