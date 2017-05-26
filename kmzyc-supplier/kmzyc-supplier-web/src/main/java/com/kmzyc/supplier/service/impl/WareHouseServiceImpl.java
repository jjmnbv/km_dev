package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.springframework.stereotype.Service;

import com.kmzyc.supplier.dao.WareHouseDao;
import com.kmzyc.supplier.service.WareHouseService;
import com.pltfm.app.vobject.WarehouseInfo;

@Service("wareHouseService")
public class WareHouseServiceImpl implements WareHouseService{
	
	@Resource
	private WareHouseDao wareHouseDao;
	
	@Override
	public List<WarehouseInfo> queryAllEnableWarehouse() throws ServiceException {
        try {
            return wareHouseDao.queryAllEnableWarehouse();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public List<WarehouseInfo> queryWarehouseBySuppliersId(String suppliersId) throws ServiceException {
        try {
            return wareHouseDao.queryWarehouseBySuppliersId(suppliersId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}