package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductImageDraftDAO;
import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.app.vobject.ProductImageDraftExample;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("productImageDraftDao")
public class ProductImageDraftDAOImpl extends BaseDao<ProductImageDraft> implements ProductImageDraftDAO {

    public int countByExample(ProductImageDraftExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("PRODUCT_IMAGE_DRAFT.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ProductImageDraftExample example) throws SQLException {
        int rows = sqlMapClient.delete("PRODUCT_IMAGE_DRAFT.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long imageId) throws SQLException {
        ProductImageDraft key = new ProductImageDraft();
        key.setImageId(imageId);
        int rows = sqlMapClient.delete("PRODUCT_IMAGE_DRAFT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ProductImageDraft record) throws SQLException {
        return (Long) sqlMapClient.insert("PRODUCT_IMAGE_DRAFT.ibatorgenerated_insert", record);
    }

    public void insertSelective(ProductImageDraft record) throws SQLException {
        sqlMapClient.insert("PRODUCT_IMAGE_DRAFT.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProductImageDraftExample example) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_IMAGE_DRAFT.ibatorgenerated_selectByExample", example);
        return list;
    }
    
    public List selectByExampleIntoOfficial(ProductImageDraftExample example) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_IMAGE_DRAFT.ibatorgenerated_selectByExample_Official", example);
        return list;
    }

    public ProductImageDraft selectByPrimaryKey(Long imageId) throws SQLException {
        ProductImageDraft key = new ProductImageDraft();
        key.setImageId(imageId);
        ProductImageDraft record = (ProductImageDraft) sqlMapClient.queryForObject("PRODUCT_IMAGE_DRAFT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(ProductImageDraft record, ProductImageDraftExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_IMAGE_DRAFT.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(ProductImageDraft record, ProductImageDraftExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_IMAGE_DRAFT.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(ProductImageDraft record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_IMAGE_DRAFT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProductImageDraft record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_IMAGE_DRAFT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends ProductImageDraftExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductImageDraftExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public int findMaxSortNoByProductSkuId(Long productSkuId) throws SQLException {
        Integer result =  (Integer) sqlMapClient.queryForObject("PRODUCT_IMAGE_DRAFT.queryMaxSortNo", productSkuId);
        return result == null ? 0 : result.intValue();
	}

	@Override
	public void updateProductImage(List<ProductImageDraft> list) throws SQLException {
		super.batchUpdateData("PRODUCT_IMAGE_DRAFT.ibatorgenerated_updateByPrimaryKeySelective", list);
	}

	@Override
	public int findCountsByProductSkuId(Long productSkuId) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("PRODUCT_IMAGE_DRAFT.queryImageCountsBySkuId", productSkuId);
	}

	@Override
	public int batchInsertOfficial(List<ProductImageDraft> list) throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_IMAGE_DRAFT.insertProductImageOfficial");
	}

	@Override
	public int batchDeleteDraft(List<ProductImageDraft> list) throws SQLException {
		return super.batchDeleteByDataPrimaryKeyNt(list, "PRODUCT_IMAGE_DRAFT.ibatorgenerated_deleteByPrimaryKey");
	}

	@Override
	public List<ProductImageDraft> findOfficialToDraftByProductId(Long productId) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_IMAGE_DRAFT.findOfficialByProductId", productId);
	}

	@Override
	public int batchInsertDraftFromOfficial(List<ProductImageDraft> list) throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_IMAGE_DRAFT.ibatorgenerated_insert_withOutSeq");
	}
}