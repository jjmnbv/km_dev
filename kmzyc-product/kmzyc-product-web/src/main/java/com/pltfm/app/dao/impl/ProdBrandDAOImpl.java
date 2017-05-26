package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProdBrandDAO;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProdBrandExample;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 产品品牌DAO实现类
 *
 * @author tanyunxing
 */
@Repository("prodBrandDao")
public class ProdBrandDAOImpl extends BaseDao implements ProdBrandDAO {

    public int countByExample(ProdBrandExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PROD_BRAND.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ProdBrandExample example) throws SQLException {
        int rows = sqlMapClient.delete("PROD_BRAND.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long brandId) throws SQLException {
        ProdBrand key = new ProdBrand();
        key.setBrandId(brandId);
        int rows = sqlMapClient.delete("PROD_BRAND.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ProdBrand record) throws SQLException {
        return (Long) sqlMapClient.insert("PROD_BRAND.ibatorgenerated_insert", record);
    }

    public Long insertSelective(ProdBrand record) throws SQLException {
        return (Long) sqlMapClient.insert("PROD_BRAND.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProdBrandExample example, int skip, int max) throws SQLException {
        List list = this.getSqlMapClient().queryForList("PROD_BRAND.ibatorgenerated_selectByExample", example, skip, max);
        return list;
    }

    public ProdBrand selectByPrimaryKey(Long brandId) throws SQLException {
        ProdBrand key = new ProdBrand();
        key.setBrandId(brandId);
        ProdBrand record = (ProdBrand) sqlMapClient.queryForObject("PROD_BRAND.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(ProdBrand record, ProdBrandExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PROD_BRAND.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(ProdBrand record, ProdBrandExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PROD_BRAND.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(ProdBrand record) throws SQLException {
        int rows = sqlMapClient.update("PROD_BRAND.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProdBrand record) throws SQLException {
        int rows = sqlMapClient.update("PROD_BRAND.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    public List<ProdBrand> selectByExample(ProdBrandExample example) throws SQLException {
        return this.getSqlMapClient().queryForList("PROD_BRAND.ibatorgenerated_selectByExample", example);
    }

    private static class UpdateByExampleParms extends ProdBrandExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProdBrandExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public int deleteByPrimaryKeyBatch(String[] brandIds) throws SQLException {
        return sqlMapClient.delete("PROD_BRAND.deleteByPrimaryKeyBatch", brandIds);
    }

    @Override
    public String checkProductCounts(Long brandId) throws SQLException {
        return (String) sqlMapClient.queryForObject("PROD_BRAND.checkProductByBrandId", brandId);
    }

    @Override
    public boolean checkProdBrandIsFromSupplier(Long brandId) throws SQLException {
        return Boolean.valueOf((String) sqlMapClient.queryForObject("PROD_BRAND.checkProdBrandIsFromSupplier", brandId));
    }

    @Override
    public int updateProdBrandDraft(ProdBrand record) throws SQLException {
        int i = sqlMapClient.update("PROD_BRAND.updateProdBrandDraft", record);
        return i;
    }

}