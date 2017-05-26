package com.pltfm.app.dao.impl;

import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.example.SuppliersAvailableCategorysExample;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.SuppliersAvailableCategorysDAO;
import com.pltfm.app.vobject.Category;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Repository("suppliersAvailableCategorysDao")
public class SuppliersAvailableCategorysDAOImpl extends BaseDao implements SuppliersAvailableCategorysDAO {

    public int countByExample(SuppliersAvailableCategorysExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SuppliersAvailableCategorysExample example) throws SQLException {
        return sqlMapClient.delete("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long sacId) throws SQLException {
        SuppliersAvailableCategorys key = new SuppliersAvailableCategorys();
        key.setSacId(sacId);
        return sqlMapClient.delete("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(SuppliersAvailableCategorys record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_insert", record);
    }

    public void insertSelective(SuppliersAvailableCategorys record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(SuppliersAvailableCategorysExample example) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_selectByExample", example);
    }

    public SuppliersAvailableCategorys selectByPrimaryKey(Long sacId) throws SQLException {
        SuppliersAvailableCategorys key = new SuppliersAvailableCategorys();
        key.setSacId(sacId);
        return (SuppliersAvailableCategorys) sqlMapClient.queryForObject("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByExampleSelective(SuppliersAvailableCategorys record, SuppliersAvailableCategorysExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(SuppliersAvailableCategorys record, SuppliersAvailableCategorysExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(SuppliersAvailableCategorys record) throws SQLException {
        return sqlMapClient.update("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(SuppliersAvailableCategorys record) throws SQLException {
        return sqlMapClient.update("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_updateByPrimaryKey", record);
    }

    private static class UpdateByExampleParms extends SuppliersAvailableCategorysExample {
        private Object record;

        public UpdateByExampleParms(Object record, SuppliersAvailableCategorysExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public int deleteByPrimaryKey(BigDecimal sacId) throws SQLException {
        return 0;
    }

    @Override
    public SuppliersAvailableCategorys selectByPrimaryKey(BigDecimal sacId) throws SQLException {

        return null;
    }

    @Override
    public List<SuppliersAvailableCategorys> findSupplierCategories(SuppliersAvailableCategorys category)
            throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_AVAILABLE_CATEGORYS.categorys_selectBysupplierId", category);
    }

    @Override
    public List<SuppliersAvailableCategorys> findAllSupplierCategories() throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_AVAILABLE_CATEGORYS.categorys_selectByAllsupplier");
    }

    @Override
    public List<SuppliersAvailableCategorys> existsSupplierCategories(SuppliersAvailableCategorys category)
            throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_AVAILABLE_CATEGORYS.categorys_selectExistsBysupplierId", category);
    }

    @Override
    public int deleteSupplierCategory(SuppliersAvailableCategorys category) throws SQLException {
        return sqlMapClient.delete("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_deleteByPrimaryCretgorId", category);
    }

    @Override
    public SuppliersAvailableCategorys commissionRatioBySupplierIdAndCategoryId(SuppliersAvailableCategorys cate)
            throws SQLException {
        return (SuppliersAvailableCategorys) sqlMapClient.queryForObject(
                "SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_selectBysuppIdAndCategoryId", cate);
    }

    @Override
    public List<Category> applySuppliersCategories(Long supplierId) throws SQLException {
        List<Category> list = null;
        if (supplierId == 0) {
            list = sqlMapClient.queryForList("CATEGORY.applySuppliersCategoriesAll");
        } else {
            list = sqlMapClient.queryForList("CATEGORY.applySuppliersCategories", supplierId);
        }
        return list;
    }

}