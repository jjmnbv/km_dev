package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductImageDAO;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageExample;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("productImageDao")
public class ProductImageDAOImpl extends BaseDao implements ProductImageDAO {

	public int countByExample(ProductImageExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_IMAGE.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	public int deleteByExample(ProductImageExample example) throws SQLException {
		return sqlMapClient.delete("PRODUCT_IMAGE.ibatorgenerated_deleteByExample", example);
	}

	public int deleteByPrimaryKey(Long imageId) throws SQLException {
		ProductImage key = new ProductImage();
		key.setImageId(imageId);
		return sqlMapClient.delete("PRODUCT_IMAGE.ibatorgenerated_deleteByPrimaryKey", key);
	}

	public Long insert(ProductImage record) throws SQLException {
        return (Long) sqlMapClient.insert("PRODUCT_IMAGE.ibatorgenerated_insert", record);
	}

	public void insertSelective(ProductImage record) throws SQLException {
		sqlMapClient.insert("PRODUCT_IMAGE.ibatorgenerated_insertSelective", record);
	}

	public List selectByExample(ProductImageExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_IMAGE.ibatorgenerated_selectByExample", example);
	}

	public ProductImage selectByPrimaryKey(Long imageId) throws SQLException {
		ProductImage key = new ProductImage();
		key.setImageId(imageId);
		ProductImage record = (ProductImage) sqlMapClient.queryForObject("PRODUCT_IMAGE.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	public int updateByExampleSelective(ProductImage record, ProductImageExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		return sqlMapClient.update("PRODUCT_IMAGE.ibatorgenerated_updateByExampleSelective", parms);
	}

	public int updateByExample(ProductImage record, ProductImageExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		return sqlMapClient.update("PRODUCT_IMAGE.ibatorgenerated_updateByExample", parms);
	}

	public int updateByPrimaryKeySelective(ProductImage record) throws SQLException {
		return sqlMapClient.update("PRODUCT_IMAGE.ibatorgenerated_updateByPrimaryKeySelective", record);
	}

	public int updateByPrimaryKey(ProductImage record) throws SQLException {
		return sqlMapClient.update("PRODUCT_IMAGE.ibatorgenerated_updateByPrimaryKey", record);
	}

	private static class UpdateByExampleParms extends ProductImageExample {
		private Object record;

		public UpdateByExampleParms(Object record, ProductImageExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

	@Override
	public int findMaxSortNoByProductSkuId(Long productSkuId) throws SQLException {
        Integer result =  (Integer)  sqlMapClient.queryForObject("PRODUCT_IMAGE.queryMaxSortNo", productSkuId);
        return result == null ? 0 : result.intValue();
	}

	@Override
	public void updateProductImage(List<ProductImage> list) throws SQLException {
		super.batchUpdateData("PRODUCT_IMAGE.ibatorgenerated_updateByPrimaryKeySelective", list);
	}

	@Override
	public int findCountsByProductSkuId(Long productSkuId) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("PRODUCT_IMAGE.queryImageCountsBySkuId", productSkuId);
	}

	@Override
	public int deleteImagesBySkuId(Long skuId) throws SQLException {
		return sqlMapClient.delete("PRODUCT_IMAGE.deleteImagesBySkuId", skuId);
	}

}