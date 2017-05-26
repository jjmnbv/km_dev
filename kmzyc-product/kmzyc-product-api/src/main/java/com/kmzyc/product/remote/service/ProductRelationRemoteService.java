package com.kmzyc.product.remote.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductRelation;

public interface ProductRelationRemoteService {

	/**
	 * 根据skuId  查询出  关联类型 为套餐类型 的 关联信息
	 * @param skuId
	 * @return
	 * @throws ServiceException
	 */
	List<ProductRelation> queryProductAndDetailPackageList(Long skuId) throws ServiceException;

	/**
	 * 根据skuId  查询出  关联类型 为人气类型  的 关联信息
	 * @param skuId
	 * @return
	 * @throws ServiceException
	 */
	 List<ProductRelation>  queryProductAndDetailRecommendList(Long skuId) throws ServiceException ;
}