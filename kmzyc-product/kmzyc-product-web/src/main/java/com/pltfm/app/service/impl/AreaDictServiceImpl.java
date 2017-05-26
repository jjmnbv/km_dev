package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pltfm.app.dao.AreaDictDAO;
import com.pltfm.app.service.AreaDictService;
import com.pltfm.app.vobject.AreaDict;
import com.pltfm.app.vobject.AreaDictExample;

/**
 * 地区业务层
 *
 * @author tanyunxing
 */
@Service("areaDictService")
public class AreaDictServiceImpl implements AreaDictService {

    @Resource
    private AreaDictDAO areaDictDao;

    @Override
    public List<AreaDict> findAllProvince() throws ServiceException {
        AreaDictExample exa = new AreaDictExample();
        exa.createCriteria().andAreatypeEqualTo(1);
        exa.setOrderByClause(" area_id ");

        try {
            return areaDictDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

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
    public AreaDict findSingleById(Integer id) throws ServiceException {
        try {
            return areaDictDao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public AreaDict findProvinceBySupplierId(Integer id) throws ServiceException {
        AreaDict area = findSingleById(id);
        AreaDictExample example = new AreaDictExample();
        example.createCriteria().andAreaIdEqualTo(area.getSupperareaId());
        try {
            List<AreaDict> areaList = areaDictDao.selectByExample(example);
            if (areaList.isEmpty()) return null;
            return areaList.get(0);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}