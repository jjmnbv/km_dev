package com.kmzyc.supplier.dao;

import com.pltfm.app.vobject.ProductImageDraft;
import java.sql.SQLException;
import java.util.List;

public interface ProductImageDraftDAO {
	
	ProductImageDraft selectByPrimaryKey(Long imageId) throws SQLException;
	
	List<ProductImageDraft> findByImageIds(List<Long> imageIds) throws SQLException;
    
	void deletePicBatch(List<Long> imageIds) throws SQLException;
	
	List<ProductImageDraft> findImagesByProductId(Long productId) throws SQLException;
    
}