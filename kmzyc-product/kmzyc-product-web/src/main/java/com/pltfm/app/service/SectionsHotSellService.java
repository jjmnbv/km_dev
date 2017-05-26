package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;

public interface SectionsHotSellService {

    void injectHotSellB2BProduct() throws ServiceException;
	
	void injectHotSellZYCProduct() throws ServiceException;
}
