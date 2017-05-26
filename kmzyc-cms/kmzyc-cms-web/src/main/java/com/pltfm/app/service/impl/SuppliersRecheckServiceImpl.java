/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.app.service.impl date:
 * 2016年9月14日下午5:39:23 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.SuppliersRecheckDAO;
import com.pltfm.app.service.SuppliersRecheckService;
import com.pltfm.cms.vobject.SuppliersRecheck;

/**
 * 供应商资质重审信息 Service实现 <br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 下午5:39:23
 * @version
 * @see
 */
@Service(value = "suppliersRecheckService")
public class SuppliersRecheckServiceImpl implements SuppliersRecheckService {

    @Resource
    private SuppliersRecheckDAO suppliersRecheckDAO;

    @Override
    public List<SuppliersRecheck> findListBySupplierId(Long supplierId) throws ServiceException {
        try {
            return suppliersRecheckDAO.findListBySupplierId(supplierId);
        } catch (SQLException e) {
            throw new ServiceException(e.getErrorCode(), e.getMessage(), e);
        }
    }
}
