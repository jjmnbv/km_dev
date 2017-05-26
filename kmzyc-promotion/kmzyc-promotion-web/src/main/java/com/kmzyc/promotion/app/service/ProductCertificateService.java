package com.kmzyc.promotion.app.service;

import java.util.List;

import com.kmzyc.promotion.app.vobject.ProductCertificate;

public interface ProductCertificateService {

	/**
	 * 根据产品Id获取资质文件
	 * 
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductCertificate> findByProductId(Long productId) throws Exception;

	/**
	 * 删除
	 * 
	 * @param list
	 * @throws Exception
	 */
	public void deleteProductCertificate(List<ProductCertificate> list) throws Exception;

}
