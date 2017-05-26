package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.dao.ProductSkuDAO;
import com.pltfm.app.vobject.ProductImageExample;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuExample;
import com.pltfm.app.vobject.ViewProductSku;
import com.pltfm.app.vobject.ViewProductSkuExample;

@Repository("productSkuDAO")
public class ProductSkuDAOImpl extends BaseDAO implements ProductSkuDAO {

    public List selectByExample(ProductSkuExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU.ibatorgenerated_selectByExample", example);
    }

    @Override
    public Pagination findProductSkuListByPage(Pagination page) throws SQLException {
        return findPaginationByPage(sqlMapClient, "PRODUCT_SKU.selectProductSkuListByMerchantIdForPromotion",
                "PRODUCT_SKU.selectCountForProductSkuByMerchantIdForPromotion", page);
    }
	
	public List selectByExampleForImages(ProductImageExample example) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_IMAGE.ibatorgenerated_selectByExample", example);
	}
	
    public List selectSKUAttrByExample(ViewProductSkuExample example) throws SQLException{
    	return sqlMapClient.queryForList("PRODUCT_SKU.ibatorgenerated_selectSKUAttrByExample", example);
    }

	@Override
	public Pagination findCanEnalbeStockProductSkuListByPage(Pagination page) throws SQLException {
		return findPaginationByPage(sqlMapClient, "PRODUCT_SKU.selectCanEnableStockProductSkuListByMerchantId",
				"PRODUCT_SKU.selectCanEnableStockCountForProductSkuByMerchantId", page);
	}

	@Override
	public Pagination findProductSkuListForFreightByPage(Pagination page) throws SQLException {
		return findPaginationByPage(sqlMapClient, "PRODUCT_SKU.selectProductSkuListForFreightByMerchantId",
				"PRODUCT_SKU.selectCountForProductSkuByMerchantId", page);
	}

	@Override
	public ViewProductSku findSingleSku(Long productSkuId) throws SQLException {
		return (ViewProductSku) sqlMapClient.queryForObject("PRODUCT_SKU.findProductSkuAndName", productSkuId);
	}

	@Override
	public int updateProductSku(ProductSku productSku) throws SQLException {
		return sqlMapClient.update("PRODUCT_SKU.ibatorgenerated_updateByPrimaryKeySelective", productSku);
	}

	@Override
	public List<ProductSkuAttr> findSkuNewAttr(Long productId) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT_SKU.findSkuNewAttr", productId);
	}

	@Override
	public int updateProductSkuList(List<ProductSku> list) throws SQLException {
		return super.batchUpdateData("PRODUCT_SKU.ibatorgenerated_updateByPrimaryKeySelective", list);
	}

	@Override
	public int deleteProductSkuByIds(List<Long> productSkuIds) throws SQLException {
		return super.batchUpdateData("PRODUCT_SKU.deleteByPrimaryKey", productSkuIds);
	}

	@Override
	public String findMaxSkuCodeByCateCode(String cateCode) throws SQLException {
		Object obj = sqlMapClient.queryForObject("PRODUCT_SKU.findMaxSkuCodeByCateCode", cateCode);
		return obj == null ? null : obj.toString();
	}

	@Override
	public void insertProductSku(ProductSku productSku) throws SQLException {
		sqlMapClient.insert("PRODUCT_SKU.insertProductSku", productSku);
	}
}