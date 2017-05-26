package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.page.Pagination;
import com.pltfm.app.vobject.ProductImageExample;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuExample;
import com.pltfm.app.vobject.ViewProductSku;
import com.pltfm.app.vobject.ViewProductSkuExample;

public interface ProductSkuDAO {

	 /**
	 * 分页查找发布产品SKU列表
	 * @param page
	 * @return
     * @throws SQLException 
	 */
	Pagination findProductSkuListByPage(Pagination page) throws SQLException;

	/**
	 * 分页查找发布产品SKU列表（运费）
	 * @param page
	 * @return
     * @throws SQLException 
	 */
	Pagination findProductSkuListForFreightByPage(Pagination page) throws SQLException;
	
	/**
	 * 查询所有可以添加库存的sku产品(其实就是状态为上架和已上架的)
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	Pagination findCanEnalbeStockProductSkuListByPage(Pagination page) throws SQLException;
	
	List selectByExample(ProductSkuExample example) throws SQLException;
	
	List selectByExampleForImages(ProductImageExample example) throws SQLException;
	
    List selectSKUAttrByExample(ViewProductSkuExample example) throws SQLException;
    
    ViewProductSku findSingleSku(Long productSkuId) throws SQLException;
    
    int updateProductSku(ProductSku productSku) throws SQLException;
    
    List<ProductSkuAttr> findSkuNewAttr(Long productId) throws SQLException;
    
    int updateProductSkuList(List<ProductSku> list) throws SQLException;
    
    int deleteProductSkuByIds(List<Long> productSkuIds) throws SQLException;
    
    String findMaxSkuCodeByCateCode(String cateCode) throws SQLException;
    
    void insertProductSku(ProductSku productSku) throws SQLException;
}