package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.supplier.dao.ProductDraftDao;
import com.kmzyc.supplier.vo.ProductDraftVo;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductAttrDraftExample;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductDraftExample;

@Repository("ProductDraftDao")
public class ProductDraftDaoImpl extends BaseDAO implements ProductDraftDao {

	@Override
	public Pagination findProductMainListByPage(Pagination page) throws SQLException {
		return findPaginationByPage(sqlMapClient, "PRODUCT_DRAFT.selectProductDraftListByMerchantId",
				"PRODUCT_DRAFT.selectCountForProductDraftByMerchantId", page);
	}

	@Override
	public List selectByExampleWithoutBLOBs(ProductDraftExample example) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_DRAFT.ibatorgenerated_selectByExample", example);
	}

	@Override
	public ProductDraftVo findProductMainVoByProductId(Long productId) throws SQLException {
		return (ProductDraftVo) sqlMapClient.queryForObject("PRODUCT_DRAFT.findProductDraftVoByProductId", productId);
	}

	@Override
	public List<ProductAttrDraft> selectByExample(ProductAttrDraftExample exa) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_DRAFT_ATTR.ibatorgenerated_selectByExample", exa);
	}
	@Override
	public ProductDraft findSingleProduct(ProductDraft productDraft) throws SQLException {
        return (ProductDraft) sqlMapClient.queryForObject("PRODUCT_DRAFT.findSingleProductAndSkusAndAttrValues", productDraft);
	}

	@Override
	public ProductDraft selectByPrimaryKey(Long productId) throws SQLException {
		ProductDraft product = new ProductDraft();
		product.setProductId(productId);
        return (ProductDraft) sqlMapClient.queryForObject("PRODUCT_DRAFT.productDraft_selectByPrimaryKey2", product);
	}

}
