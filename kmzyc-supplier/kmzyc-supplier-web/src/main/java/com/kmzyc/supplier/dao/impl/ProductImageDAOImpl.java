package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.dao.ProductImageDAO;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageExample;

/**
 * 产品图片DAO实现
 * 
 * 
 */
@Repository("productImageDao")
public class ProductImageDAOImpl extends BaseDAO implements ProductImageDAO {

	public ProductImage selectByPrimaryKey(Long imageId) throws SQLException {
		ProductImage key = new ProductImage();
		key.setImageId(imageId);
        return (ProductImage) sqlMapClient.queryForObject("PRODUCT_IMAGE.ibatorgenerated_selectByPrimaryKey", key);
	}

	@Override
	public Integer queryMaxSortNo(Long productSkuId) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("PRODUCT_IMAGE.queryMaxSortNo", productSkuId);
	}

	@Override
	public Long insertImage(ProductImage image) throws SQLException {
		return (Long) sqlMapClient.insert("PRODUCT_IMAGE.ibatorgenerated_insert", image);
	}

	@Override
	public int updateByExampleSelective(ProductImage record,
			ProductImageExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_IMAGE.ibatorgenerated_updateByExampleSelective", parms);
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
	public int updateImageBatch(List<ProductImage> list) throws SQLException {
		return super.batchUpdateData("PRODUCT_IMAGE.ibatorgenerated_updateByPrimaryKeySelective", list);
	}

	@Override
	public List<ProductImage> queryByImageIds(List<Long> imageIds) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_IMAGE.queryByImageIds", imageIds);
	}

	@Override
	public int deleteImageBatch(List<Long> list) throws SQLException {
		return super.batchDeleteByPrimaryKeyData("PRODUCT_IMAGE.ibatorgenerated_deleteByPrimaryKey", list);
	}

}