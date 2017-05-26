package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.dao.ProductDraftSkuDAO;
import com.pltfm.app.vobject.ProductImageDraftExample;
import com.pltfm.app.vobject.ProductSkuAttrDraft;
import com.pltfm.app.vobject.ProductSkuAttrExample;
import com.pltfm.app.vobject.ProductSkuDraft;
import com.pltfm.app.vobject.ProductSkuDraftExample;
import com.pltfm.app.vobject.ProductSkuExample;

@Repository("productDraftSkuDAO")
public class ProductDraftSkuDAOImpl extends BaseDAO implements ProductDraftSkuDAO {

    public List selectByExample(ProductSkuExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU_DRAFT.ibatorgenerated_selectByExample", example);
    }

	@Override
	public Pagination findProductSkuListByPage(Pagination page) throws SQLException {
		return findPaginationByPage(sqlMapClient, "PRODUCT_SKU_DRAFT.selectProductSkuListByMerchantId",
				"PRODUCT_SKU_DRAFT.selectCountForProductSkuByMerchantId", page);
	}
	public List selectByExample(ProductSkuAttrExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_DRAFT_SKU_ATTR.ibatorgenerated_selectByExample", example);
    }

	@Override
	public ProductSkuDraft findSingleSkuAndAttrValue(Long productSkuId) throws SQLException {
		return (ProductSkuDraft) sqlMapClient.queryForObject("PRODUCT_SKU_DRAFT.findSingleSkuAndAttrValue", productSkuId);
	}
	
	@Override
    public List selectByExample(ProductImageDraftExample example) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_IMAGE_DRAFT.ibatorgenerated_selectByExample", example);
    }
	
	public List selectByExampleForImages(ProductImageDraftExample example) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_IMAGE_DRAFT.ibatorgenerated_selectByExample", example);
	}

	@Override
	public List selectByExample(ProductSkuDraftExample example) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_SKU_DRAFT.ibatorgenerated_selectByExample2", example);
	}

	@Override
	public List<ProductSkuAttrDraft> findSkuNewAttr(Long productId) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_SKU_DRAFT.findSkuNewAttr", productId);
	}

}