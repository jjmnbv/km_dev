package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.springframework.stereotype.Service;

import com.kmzyc.supplier.dao.AreaDictDAO;
import com.kmzyc.supplier.service.AreaDictService;
import com.pltfm.app.vobject.AreaDict;
import com.pltfm.app.vobject.AreaDictExample;

/**
 * 地区业务层
 */
@Service("areaDictService")
public class AreaDictServiceImpl implements AreaDictService {

    @Resource
    private AreaDictDAO areaDictDao;

    @Override
    public List<AreaDict> findCityByProvince(Integer provinceId) throws ServiceException {
        AreaDictExample exa = new AreaDictExample();
        exa.createCriteria().andSupperareaIdEqualTo(provinceId).andAreatypeEqualTo(2);
        exa.setOrderByClause(" area_id ");
        try {
            return areaDictDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AreaDict> findCountyByProvince(Integer cityId) throws ServiceException {
        AreaDictExample exa = new AreaDictExample();
        exa.createCriteria().andSupperareaIdEqualTo(cityId).andAreatypeEqualTo(3);
        exa.setOrderByClause(" area_id ");
        try {
            return areaDictDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public AreaDict findSingleById(Integer id) throws ServiceException {
        try {
            return areaDictDao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}