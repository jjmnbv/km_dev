package com.kmzyc.supplier.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.km.framework.page.Pagination;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductStock;

/**
 * 
 * @author Administrator
 *
 */
public interface ProductStockService {
	
	/**
	 * 分页查询商品库存列表
	 * @param queryPara
	 * @param page
	 * @return
	 */
	Pagination searchPage(ProductStock queryPara,Pagination page) throws ServiceException;
	
	
	/**
	 * 按库存Id查询库存实体
	 * @return
	 * @throws ServiceException
	 */
	ProductStock queryByStockId(String stockId) throws ServiceException;
	
	/**
	 * 添加库存记录
	 * @param para
	 * @throws ServiceException
	 */
	void saveProductStock(ProductStock para) throws ServiceException;
	
	
	/**
	 * 更新库存记录
	 * @param para
	 * @throws ServiceException
	 */
	int updateProductStock(ProductStock para) throws ServiceException;
	
	
	/**
	 * 检查指定的sku在某个仓库中是否有库存
	 * @param warehouseId
	 * @param skuValue
	 * @return
	 * @throws ServiceException
	 */
	int isExistSkuStock(String warehouseId,String skuValue) throws ServiceException;
	
	/**
	 * 为供应商增加库存
	 * @param supplierId
	 * @param productSkuList
	 * @return
	 * @throws ServiceException
	 */
	ResultMessage addStockForSupplier(Long supplierId, List<ProductSku> productSkuList) throws ServiceException;

}