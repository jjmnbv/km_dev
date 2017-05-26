package com.pltfm.app.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ViewProductDetailInfoDAO;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ViewProductDetailInfoExample;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

/**
 * 产品详情DAO接口
 *
 * @author cjm
 * @since 2013-9-23
 */
@Component(value = "viewProductDetailInfoDAO")
public class ViewProductDetailInfoDAOImpl implements ViewProductDetailInfoDAO {

    /**
     * 数据库操作类
     */
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /**
     * 根据产品详情条件查询产品详情信息
     *
     * @param example 产品详情条件类
     * @throws SQLException 异常
     * @return 返回值
     */
    public List selectByExample(ViewProductDetailInfoExample example) throws SQLException {
        List list = sqlMapClient.queryForList("VIEW_PRODUCT_DETAIL_INFO.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * 通过产品主键查询产品sku属性信息
     *
     * @param productId 产品主键
     * @return sku属性信息
     * @throws SQLException 异常信息
     */
    public List<ProductAttr> queryProductAttrByProductId(Long productId) throws SQLException {
        List list = sqlMapClient.queryForList("VIEW_PRODUCT_DETAIL_INFO.iabatorgenerated_queryProductAttrByProductId", productId);
        return list;
    }

    /**
     * 通过草稿产品主键查询产品sku属性信息
     *
     * @param productId 产品主键
     * @return sku属性信息
     * @throws SQLException 异常信息
     */
    @Override
    public List<ProductAttr> queryProductAttrDraftByProductId(Long productId)
            throws SQLException {
        List list = sqlMapClient.queryForList("VIEW_PRODUCT_DETAIL_INFO.iabatorgenerated_queryProductAttrDraftByProductId", productId);
        return list;
    }

    /**
     * 通过产品sku查询产品属性值信息
     *
     * @param attrValue sku属性值
     * @throws SQLException 异常信息
     */
    public List<CategoryAttrValue> findByValueId(String attrValue) throws SQLException {
        List list = sqlMapClient.queryForList("VIEW_PRODUCT_DETAIL_INFO.iabatorgenerated_findByValueId", attrValue);
        return list;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table VIEW_PRODUCT_DETAIL_INFO
     * 产品详情条件类
     *
     * @abatorgenerated Mon Sep 23 11:19:42 CST 2013
     */
    private static class UpdateByExampleParms extends ViewProductDetailInfoExample {
        private Object record;

        public UpdateByExampleParms(Object record, ViewProductDetailInfoExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }


    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }


    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

}
