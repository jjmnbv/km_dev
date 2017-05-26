package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductAttrDraftDAO;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductAttrDraftExample;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author tanyunxing
 *
 */
@Repository("productAttrDraftDao")
public class ProductAttrDraftDAOImpl extends BaseDao<ProductAttrDraft> implements ProductAttrDraftDAO {

    public int countByExample(ProductAttrDraftExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("PRODUCT_ATTR_DRAFT.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ProductAttrDraftExample example) throws SQLException {
        int rows = sqlMapClient.delete("PRODUCT_ATTR_DRAFT.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long productAttrId) throws SQLException {
        ProductAttrDraft key = new ProductAttrDraft();
        key.setProductAttrId(productAttrId);
        int rows = sqlMapClient.delete("PRODUCT_ATTR_DRAFT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ProductAttrDraft record) throws SQLException {
        sqlMapClient.insert("PRODUCT_ATTR_DRAFT.ibatorgenerated_insert", record);
    }

    public void insertSelective(ProductAttrDraft record) throws SQLException {
        sqlMapClient.insert("PRODUCT_ATTR_DRAFT.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProductAttrDraftExample example) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_ATTR_DRAFT.ibatorgenerated_selectByExample", example);
        return list;
    }

    public ProductAttrDraft selectByPrimaryKey(Long productAttrId) throws SQLException {
        ProductAttrDraft key = new ProductAttrDraft();
        key.setProductAttrId(productAttrId);
        ProductAttrDraft record = (ProductAttrDraft) sqlMapClient.queryForObject("PRODUCT_ATTR_DRAFT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(ProductAttrDraft record, ProductAttrDraftExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_ATTR_DRAFT.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(ProductAttrDraft record, ProductAttrDraftExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_ATTR_DRAFT.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(ProductAttrDraft record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_ATTR_DRAFT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProductAttrDraft record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_ATTR_DRAFT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends ProductAttrDraftExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductAttrDraftExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public int batchInsertOfficial(List<ProductAttrDraft> list) throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_ATTR_DRAFT.insertProductAttrOfficial");
	}

	@Override
	public void batchInsertDraft(List<ProductAttrDraft> list) throws SQLException {
        batchInsertData(list, "PRODUCT_ATTR_DRAFT.ibatorgenerated_insert");
	}

	@Override
	public int batchInsertDraftFromOfficial(List<ProductAttr> list) throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_ATTR_DRAFT.insertDraftFromOfficial");
	}

    @Override
    public void batchUpdateByPrimaryKeySelective(List<ProductAttrDraft> list) throws SQLException {
        batchUpdate(list, "PRODUCT_ATTR_DRAFT.ibatorgenerated_updateByPrimaryKeySelective");
    }
}