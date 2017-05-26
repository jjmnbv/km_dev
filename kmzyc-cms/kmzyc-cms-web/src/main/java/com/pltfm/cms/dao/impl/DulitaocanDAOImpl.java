package com.pltfm.cms.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.vobject.ProductRelation;
import com.pltfm.cms.dao.DulitaocanDAO;
import com.pltfm.cms.vobject.CmsProductRelation;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component(value = "dulitaocanDAO")
public class DulitaocanDAOImpl implements DulitaocanDAO {
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    public List selectRelationDetail(Long relationId) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_DULITAOCAN.ibatorgenerated_selectRelationDetail", relationId);
        return list;
    }

    public List selectSku(Long productSkuId) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_DULITAOCAN.ibatorgenerated_selectSku", productSkuId);
        return list;
    }

    public List selectImg(Long productSkuId) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_DULITAOCAN.ibatorgenerated_selectByImg", productSkuId);
        return list;
    }

    /***
     * 详细页ID查询套餐主表
     * */
    public List selectCmsRelation(CmsProductRelation r) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_DULITAOCAN.ibatorgenerated_selectCmsRelation", r);
        return list;
    }

    /***
     * ID查询套餐主表
     * */
    public List selectRelation(ProductRelation r) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_DULITAOCAN.ibatorgenerated_selectRelation", r);
        return list;
    }

    /**
     * SKUID查询套餐
     */
    public List selectRelationAll(Long skuId) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_DULITAOCAN.ibatorgenerated_selectRelationDetailAll", skuId);
        return list;
    }


    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }


}