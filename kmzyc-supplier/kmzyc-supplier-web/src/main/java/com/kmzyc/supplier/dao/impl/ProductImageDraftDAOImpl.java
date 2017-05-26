package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.dao.ProductImageDraftDAO;
import com.pltfm.app.vobject.ProductImageDraft;


@Repository("productImageDraftDao")
public class ProductImageDraftDAOImpl extends BaseDAO<ProductImageDraft> implements ProductImageDraftDAO {
	
	public ProductImageDraft selectByPrimaryKey(Long imageId) throws SQLException {
        ProductImageDraft key = new ProductImageDraft();
        key.setImageId(imageId);
        return (ProductImageDraft) sqlMapClient.queryForObject("PRODUCT_IMAGE_DRAFT.ibatorgenerated_selectByPrimaryKey", key);
    }

	@Override
	public void deletePicBatch(List<Long> imageIds) throws SQLException {
		super.batchDeleteByPrimaryKeyData("PRODUCT_IMAGE_DRAFT.deleteImagesByImageId", imageIds);
	}

	@Override
	public List<ProductImageDraft> findByImageIds(List<Long> imageIds) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_IMAGE_DRAFT.queryByImageIds", imageIds);
	}

	@Override
	public List<ProductImageDraft> findImagesByProductId(Long productId) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_IMAGE_DRAFT.findImagesByProductId", productId);
	}
	
}