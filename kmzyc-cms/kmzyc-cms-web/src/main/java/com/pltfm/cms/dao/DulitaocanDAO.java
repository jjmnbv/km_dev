package com.pltfm.cms.dao;


import com.pltfm.app.vobject.ProductRelation;
import com.pltfm.cms.vobject.CmsProductRelation;

import java.sql.SQLException;
import java.util.List;

public interface DulitaocanDAO {
    public List selectRelationDetail(Long relationId) throws SQLException;

    /**
     * SKUID查询套餐
     */
    public List selectRelationAll(Long skuId) throws SQLException;

    public List selectSku(Long productSkuId) throws SQLException;

    /***
     * ID查询套餐主表
     * */
    public List selectRelation(ProductRelation r) throws SQLException;

    /***
     * 详细页ID查询套餐主表
     * */
    public List selectCmsRelation(CmsProductRelation r) throws SQLException;

    public List selectImg(Long productSkuId) throws SQLException;
}