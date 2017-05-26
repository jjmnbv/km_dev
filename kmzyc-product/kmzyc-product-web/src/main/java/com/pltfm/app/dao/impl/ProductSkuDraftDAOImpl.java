package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductSkuDraftDAO;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuDraft;
import com.pltfm.app.vobject.ProductSkuDraftExample;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("productSkuDraftDao")
public class ProductSkuDraftDAOImpl extends BaseDao<ProductSkuDraft> implements ProductSkuDraftDAO {

    public int countByExample(ProductSkuDraftExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("PRODUCT_SKU_DRAFT.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ProductSkuDraftExample example) throws SQLException {
        return sqlMapClient.delete("PRODUCT_SKU_DRAFT.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long productSkuId) throws SQLException {
        ProductSkuDraft key = new ProductSkuDraft();
        key.setProductSkuId(productSkuId);
        return sqlMapClient.delete("PRODUCT_SKU_DRAFT.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(ProductSkuDraft record) throws SQLException {
        sqlMapClient.insert("PRODUCT_SKU_DRAFT.ibatorgenerated_insert", record);
    }

    public void insertSelective(ProductSkuDraft record) throws SQLException {
        sqlMapClient.insert("PRODUCT_SKU_DRAFT.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProductSkuDraftExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU_DRAFT.ibatorgenerated_selectByExample", example);
    }
    
    public List selectByExampleIntoOfficial(ProductSkuDraftExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU_DRAFT.ibatorgenerated_selectByExample_Official", example);
    }

    public ProductSkuDraft selectByPrimaryKey(Long productSkuId) throws SQLException {
        ProductSkuDraft key = new ProductSkuDraft();
        key.setProductSkuId(productSkuId);
        return (ProductSkuDraft) sqlMapClient.queryForObject("PRODUCT_SKU_DRAFT.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByExampleSelective(ProductSkuDraft record, ProductSkuDraftExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_SKU_DRAFT.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(ProductSkuDraft record, ProductSkuDraftExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_SKU_DRAFT.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(ProductSkuDraft record) throws SQLException {
        return sqlMapClient.update("PRODUCT_SKU_DRAFT.ibatorgenerated_updateByPrimaryKeySelective", record);
    }
    
    public void batchUpdateByPrimaryKeySelective(List<ProductSkuDraft> list) throws SQLException {
        batchUpdate(list, "PRODUCT_SKU_DRAFT.ibatorgenerated_updateByPrimaryKeySelective");
    }

    public int updateByPrimaryKey(ProductSkuDraft record) throws SQLException {
        return sqlMapClient.update("PRODUCT_SKU_DRAFT.ibatorgenerated_updateByPrimaryKey", record);
    }
    
    private static class UpdateByExampleParms extends ProductSkuDraftExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductSkuDraftExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	public void updatePriceBatch(List<ProductSkuDraft> list) throws SQLException {
		batchUpdate(list, "PRODUCT_SKU_DRAFT.updateSkuPriceAndWeight");
	}

	public List selectSomeSKUS(ProductSkuDraft record) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_SKU_DRAFT.findSKUAndAttrValue", record);
	}
    
	public List<ProductSkuDraft> selectSomeSKUSByUser(ProductSkuDraft record) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_SKU_DRAFT.findSKUAndAttrValueByUser", record);
	}
    
	public int selectSomeSKUSCounts(ProductSkuDraft record) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("PRODUCT_SKU_DRAFT.findSKUAndAttrValueCounts", record);
	}
    
	public int selectSomeSKUSCountsByUser(ProductSkuDraft record) throws SQLException {
        return (Integer)sqlMapClient.queryForObject("PRODUCT_SKU_DRAFT.findSKUAndAttrValueCountsByUser", record);
	}

	public ProductSkuDraft findSingleSkuAndAttrValue(Long productSkuId) throws SQLException {
		return (ProductSkuDraft) sqlMapClient.queryForObject("PRODUCT_SKU_DRAFT.findSingleSkuAndAttrValue", productSkuId);
	}
	
	public ProductSkuDraft findSingleSkuAndAttrValueForIntroduce(Long productSkuId) throws SQLException {
		return (ProductSkuDraft) sqlMapClient.queryForObject("PRODUCT_SKU_DRAFT.findSingleSkuAndAttrValueForIntroduce", productSkuId);
	}

	public int batchInsertOfficial(List<ProductSkuDraft> list) throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_SKU_DRAFT.insertProductSkuOfficial");
	}

	public int batchInsertDraftFromOfficialWithOutSeq(List list) throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_SKU_DRAFT.ibatorgenerated_insertFromOfficial_withOutSeq");
	}

	public int batchUpdateOfficialFromDraft(List<ProductSkuDraft> list) throws Exception {
		return super.batchUpdateNt(list,"PRODUCT_SKU_DRAFT.updateOfficialByPrimaryKey");
	}
	
	public int batchUpdatePriceOnlyOfficialFromDraft(List<ProductSkuDraft> list) throws SQLException {
		return super.batchUpdateNt(list,"PRODUCT_SKU_DRAFT.updatePriceOnlyOfficialByPrimaryKey");
	}

	public int batchInsertDraftWithOutSeq(List<ProductSkuDraft> list) throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_SKU_DRAFT.ibatorgenerated_insert_withOutSeq");
	}

	public int batchInsertDraftFromOfficial(List<ProductSku> list) throws SQLException {
		return super.batchInsertDataNt(list, "PRODUCT_SKU_DRAFT.insertDraftFromOfficial");
	}

	public boolean checkSkuPriceAndWeight(List<Long> productIds) throws SQLException {
		Integer i = (Integer)sqlMapClient.queryForObject("PRODUCT_SKU_DRAFT.checkSkuPriceAndWeight", productIds);
        return i!=null && i.intValue()>0;
	}

	public List<ProductSkuDraft> findSameSkuBarCodeProductSku(Map<String,Object> map) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_SKU_DRAFT.findSameSkuBarCodeProductSku", map);
	}

}