package com.kmzyc.supplier.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductAttrDraft;

public interface ProductAttrDraftService {

	/**
	 * 查询产品属性
	 * @param productAttr 产品属性基本信息
	 * @return List<ProductAttr>
	 * @throws ServiceException 异常
	 */
	List<ProductAttrDraft> queryProductAttrDraft(ProductAttrDraft productAttr) throws ServiceException;
}
