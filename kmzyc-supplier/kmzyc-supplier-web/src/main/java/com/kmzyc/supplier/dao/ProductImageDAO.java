package com.kmzyc.supplier.dao;

import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageExample;

import java.sql.SQLException;
import java.util.List;

public interface ProductImageDAO {
	
	ProductImage selectByPrimaryKey(Long imageId) throws SQLException;
	
	Integer queryMaxSortNo(Long productSkuId) throws SQLException;
	
	Long insertImage(ProductImage image) throws SQLException;
	
	int updateByExampleSelective(ProductImage record, ProductImageExample example) throws SQLException;
	
	int updateImageBatch(List<ProductImage> list) throws SQLException;
	
	List<ProductImage> queryByImageIds(List<Long> imageIds) throws SQLException;
	
	int deleteImageBatch(List<Long> list) throws SQLException;
	
}