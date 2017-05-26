package com.kmzyc.promotion.app.service;

import java.util.List;

/**
 * 产品CMS上架服务
 * 
 * @author xkj
 * 
 */
public interface CmsProductUpShelfService {

	/**
	 * 产品上架
	 * 
	 * @param productIdList
	 * @throws Exception
	 */
	public void productUpShelfByCms(List<Integer> productIdList) throws Exception;
}
