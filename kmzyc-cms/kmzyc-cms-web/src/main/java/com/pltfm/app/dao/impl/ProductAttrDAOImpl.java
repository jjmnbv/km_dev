package com.pltfm.app.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ProductAttrDAO;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductAttrExample;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component(value = "productAttrDAO")
public class ProductAttrDAOImpl implements ProductAttrDAO {
    /**
     * 数据库操作类
     */
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table PRODUCT_ATTR
     *
     * @abatorgenerated Thu Oct 24 09:41:48 CST 2013
     */
    public void insert(ProductAttr record) throws SQLException {
        sqlMapClient.insert("PRODUCT_ATTR.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table PRODUCT_ATTR
     *
     * @abatorgenerated Thu Oct 24 09:41:48 CST 2013
     */
    public int updateByPrimaryKey(ProductAttr record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_ATTR.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table PRODUCT_ATTR
     *
     * @abatorgenerated Thu Oct 24 09:41:48 CST 2013
     */
    public int updateByPrimaryKeySelective(ProductAttr record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_ATTR.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table PRODUCT_ATTR
     *
     * @abatorgenerated Thu Oct 24 09:41:48 CST 2013
     */
    public List selectByExample(ProductAttrExample example) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_ATTR.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table PRODUCT_ATTR
     *
     * @abatorgenerated Thu Oct 24 09:41:48 CST 2013
     */
    public ProductAttr selectByPrimaryKey(BigDecimal productAttrId) throws SQLException {
        ProductAttr key = new ProductAttr();
        key.setProductAttrId(productAttrId);
        ProductAttr record = (ProductAttr) sqlMapClient.queryForObject("PRODUCT_ATTR.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table PRODUCT_ATTR
     *
     * @abatorgenerated Thu Oct 24 09:41:48 CST 2013
     */
    public int deleteByExample(ProductAttrExample example) throws SQLException {
        int rows = sqlMapClient.delete("PRODUCT_ATTR.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table PRODUCT_ATTR
     *
     * @abatorgenerated Thu Oct 24 09:41:48 CST 2013
     */
    public int deleteByPrimaryKey(BigDecimal productAttrId) throws SQLException {
        ProductAttr key = new ProductAttr();
        key.setProductAttrId(productAttrId);
        int rows = sqlMapClient.delete("PRODUCT_ATTR.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table PRODUCT_ATTR
     *
     * @abatorgenerated Thu Oct 24 09:41:48 CST 2013
     */
    public int countByExample(ProductAttrExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_ATTR.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table PRODUCT_ATTR
     *
     * @abatorgenerated Thu Oct 24 09:41:48 CST 2013
     */
    public int updateByExampleSelective(ProductAttr record, ProductAttrExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_ATTR.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table PRODUCT_ATTR
     *
     * @abatorgenerated Thu Oct 24 09:41:48 CST 2013
     */
    public int updateByExample(ProductAttr record, ProductAttrExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_ATTR.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * 通过sku实体查询该产品是否拥有到货通知属性
     */
    public Integer selectSkuValue(ProductAttr record) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("PRODUCT_ATTR.abatorgenerated_selectProductAttrSkuInfo", record);
    }

    /**
     * 通过sku实体查询该产品草稿是否拥有到货通知属性
     */
    public Integer selectSkuValueDraft(ProductAttr record) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("PRODUCT_ATTR.abatorgenerated_selectProductDraftAttrSkuInfo", record);
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table PRODUCT_ATTR
     *
     * @abatorgenerated Thu Oct 24 09:41:48 CST 2013
     */
    private static class UpdateByExampleParms extends ProductAttrExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductAttrExample example) {
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
