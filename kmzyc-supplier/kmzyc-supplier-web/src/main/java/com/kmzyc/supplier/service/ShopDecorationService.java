package com.kmzyc.supplier.service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.ShopMain;


public interface ShopDecorationService {

    /**
     * 查看模板
     * @param shopMain
     * @param dataType
     * @return
     * @throws ServiceException
     */
	String viewTemplet(ShopMain shopMain,String dataType) throws ServiceException;

    /**
     * 发布模板
     * @param shopMain
     * @param dataType
     * @return
     * @throws ServiceException
     */
	String publishTemplet(ShopMain shopMain,String dataType) throws ServiceException;
	
}