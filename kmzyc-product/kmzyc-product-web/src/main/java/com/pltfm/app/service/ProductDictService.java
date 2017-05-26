package com.pltfm.app.service;


import com.kmzyc.commons.exception.ServiceException;

import java.math.BigDecimal;

public interface ProductDictService {

	
	/**
	 * 获取代销商的PV值比例
	 * @return
	 * @throws ServiceException
	 */
    BigDecimal getSupportPvProportion() throws ServiceException;
}
