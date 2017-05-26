package com.kmzyc.supplier.service;


import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ProdBrand;

/**
 * 产品品牌业务接口
 * @author tanyunxing
 *
 */
public interface ProdBrandService {

	/**
	 * 获取所有有效的品牌
	 * @return
	 * @throws ServiceException
	 */
	List<ProdBrand> findAllValidBrand() throws ServiceException;

    /**
     * 查询列表
     * @param prodBrand
     * @param page
     * @return
     * @throws ServiceException
     */
	Page searchPage(ProdBrand prodBrand, Page page) throws ServiceException;
}