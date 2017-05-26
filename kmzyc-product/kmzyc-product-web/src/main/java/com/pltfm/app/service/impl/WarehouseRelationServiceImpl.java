package com.pltfm.app.service.impl;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pltfm.app.dao.WarehouseRelationDAO;
import com.pltfm.app.service.WarehouseRelationService;
import com.pltfm.app.vobject.WarehouseRelation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Component("warehouseRelationService")
public class WarehouseRelationServiceImpl implements WarehouseRelationService {

	@Resource
	private WarehouseRelationDAO warehouseRelationDao;
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public Long insert(WarehouseRelation relation) throws ServiceException {
        try {
            return warehouseRelationDao.insert(relation);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int update(WarehouseRelation relation) throws ServiceException {
        try {
            return warehouseRelationDao.updateByPrimaryKeySelective(relation);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
