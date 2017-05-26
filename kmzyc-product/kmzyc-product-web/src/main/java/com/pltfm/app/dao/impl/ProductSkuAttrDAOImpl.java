package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductSkuAttrDAO;
import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuAttrExample;
@Repository("productSkuAttrDao")
public class ProductSkuAttrDAOImpl extends BaseDao<ProductSkuAttr> implements ProductSkuAttrDAO {

    public int countByExample(ProductSkuAttrExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("PRODUCT_SKU_ATTR.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ProductSkuAttrExample example) throws SQLException {
        int rows = sqlMapClient.delete("PRODUCT_SKU_ATTR.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long productSkuAttrId) throws SQLException {
        ProductSkuAttr key = new ProductSkuAttr();
        key.setProductSkuAttrId(productSkuAttrId);
        int rows = sqlMapClient.delete("PRODUCT_SKU_ATTR.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ProductSkuAttr record) throws SQLException {
        sqlMapClient.insert("PRODUCT_SKU_ATTR.ibatorgenerated_insert", record);
    }

    public void insertSelective(ProductSkuAttr record) throws SQLException {
        sqlMapClient.insert("PRODUCT_SKU_ATTR.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProductSkuAttrExample example) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCT_SKU_ATTR.ibatorgenerated_selectByExample", example);
        return list;
    }

    public ProductSkuAttr selectByPrimaryKey(Long productSkuAttrId) throws SQLException {
        ProductSkuAttr key = new ProductSkuAttr();
        key.setProductSkuAttrId(productSkuAttrId);
        ProductSkuAttr record = (ProductSkuAttr) sqlMapClient.queryForObject("PRODUCT_SKU_ATTR.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(ProductSkuAttr record, ProductSkuAttrExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_SKU_ATTR.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(ProductSkuAttr record, ProductSkuAttrExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("PRODUCT_SKU_ATTR.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(ProductSkuAttr record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_SKU_ATTR.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProductSkuAttr record) throws SQLException {
        int rows = sqlMapClient.update("PRODUCT_SKU_ATTR.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends ProductSkuAttrExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductSkuAttrExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	public String querySkuAttrValueBySkuId(Long productSkuId) throws SQLException {
		return (String) sqlMapClient.queryForObject("PRODUCT_SKU_ATTR.querySkuAttrValueBySkuId", productSkuId);
	}

	public List<ProductSkuAttr> findSkuNewAttr(Long productId) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_SKU_ATTR.findSkuNewAttr", productId);
	}
	
	public int deleteProductSkuAttrByProductId(Long productId) throws SQLException {
        int rows = sqlMapClient.delete("PRODUCT_SKU_ATTR.deleteProductSkuAttrByProductId", productId);
        return rows;
    }
}