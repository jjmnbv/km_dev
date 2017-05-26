package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductSkuAttrDraftDAO;
import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuAttrDraft;
import com.pltfm.app.vobject.ProductSkuAttrDraftExample;
import com.pltfm.app.vobject.ViewSkuAttr;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author tanyunxing
 *
 */
@Repository("productSkuAttrDraftDao")
public class ProductSkuAttrDraftDAOImpl extends BaseDao<ProductSkuAttrDraft> implements ProductSkuAttrDraftDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    public ProductSkuAttrDraftDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    public int countByExample(ProductSkuAttrDraftExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    public int deleteByExample(ProductSkuAttrDraftExample example) throws SQLException {
        int rows = sqlMapClient.delete("PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    public int deleteByPrimaryKey(Long productSkuAttrId) throws SQLException {
        ProductSkuAttrDraft key = new ProductSkuAttrDraft();
        key.setProductSkuAttrId(productSkuAttrId);
        int rows = sqlMapClient.delete("PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public int deleteProductSkuAttrByProductId(Long productId) throws SQLException {
        int rows = sqlMapClient.delete("PRODUCT_SKU_ATTR_DRAFT.deleteProductSkuAttrByProductId", productId);
        return rows;
    }

    @Override
    public void batchUpdateSkuAttrStatus(List<ProductSkuAttrDraft> list) throws SQLException {
        super.batchUpdate(list, "PRODUCT_SKU_ATTR_DRAFT.updateStatusByProductSkuId");
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    public void insert(ProductSkuAttrDraft record) throws SQLException {
        sqlMapClient.insert("PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    public void insertSelective(ProductSkuAttrDraft record) throws SQLException {
        sqlMapClient.insert("PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    public List selectByExample(ProductSkuAttrDraftExample example) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    public ProductSkuAttrDraft selectByPrimaryKey(Long productSkuAttrId) throws SQLException {
        ProductSkuAttrDraft key = new ProductSkuAttrDraft();
        key.setProductSkuAttrId(productSkuAttrId);
        ProductSkuAttrDraft record = (ProductSkuAttrDraft) sqlMapClient.queryForObject("PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    public int updateByExampleSelective(ProductSkuAttrDraft record, ProductSkuAttrDraftExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    public int updateByExample(ProductSkuAttrDraft record, ProductSkuAttrDraftExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    public int updateByPrimaryKeySelective(ProductSkuAttrDraft record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    public int updateByPrimaryKey(ProductSkuAttrDraft record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table PRODUCT_SKU_ATTR_DRAFT
     *
     * @ibatorgenerated Fri Nov 29 14:46:25 CST 2013
     */
    private static class UpdateByExampleParms extends ProductSkuAttrDraftExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductSkuAttrDraftExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public int batchInsertOfficial(List<ProductSkuAttrDraft> list)
			throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_SKU_ATTR_DRAFT.insertProductSkuAttrOfficial");
	}

	@Override
	public List<ProductSkuAttrDraft> findByProductId(Long productId)
			throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_SKU_ATTR_DRAFT.selectListByProductId", productId);
	}

	@Override
	public int batchDeleteDraft(List<ProductSkuAttrDraft> list)
			throws SQLException {
		return super.batchDeleteByDataPrimaryKeyNt(list, "PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_deleteByPrimaryKey");
	}

	@Override
	public int batchInsertDraftFromOfficial(List<ProductSkuAttr> list)
			throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_SKU_ATTR_DRAFT.ibatorgenerated_insert_withOutSeq");
	}

	@Override
	public List<ProductSkuAttrDraft> findSkuAttrDraftByProductId(Long productId)
			throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_SKU_ATTR_DRAFT.findSkuAttrDraftByProductId", productId);
	}

	@Override
	public List<ViewSkuAttr> findSkuDraftInfoByProductSkuId(Long productSkuId)
			throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_SKU_ATTR_DRAFT.findSkuDraftInfoByProductSkuId", productSkuId);
	}

	@Override
	public List<ProductSkuAttrDraft> findSkuNewAttr(Long productId)
			throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_SKU_ATTR_DRAFT.findSkuNewAttr", productId);
	}

}