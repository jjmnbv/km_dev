package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;

import java.util.List;

/**
 * 产品CMS上架服务
 *
 * @author xkj
 */
public interface CmsProductUpShelfService {

    /**
     * 产品上架
     *
     * @param productIdList
     * @throws ServiceException
     */
    void productUpShelfByCms(List<Integer> productIdList) throws ServiceException;
}
