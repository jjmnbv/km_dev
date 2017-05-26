package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.TiedSadeDAO;
import com.pltfm.app.service.ProductTiedService;
import com.pltfm.app.vobject.TiedSade;
import com.pltfm.app.vobject.TiedSadeExample;
import com.pltfm.app.vobject.TiedSadeExample.Criteria;
import com.kmzyc.commons.exception.ServiceException;

@Component("productTiedService")
public class ProductTiedServiceImpl implements ProductTiedService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private TiedSadeDAO tiedSadeDao;

    @Override
    public List<TiedSade> findListByExample(TiedSade tiedSade) throws ServiceException {
        TiedSadeExample example = new TiedSadeExample();
        Long mainSku = tiedSade.getMainSku();
        Criteria criteria = example.createCriteria();
        criteria.andMainSkuEqualTo(mainSku);
        criteria.andTiedSadeTypeNotEqualTo((short) 3);

        try {
            return tiedSadeDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public List<TiedSade> findConnectListByMainSku(TiedSade tiedSade) throws ServiceException {
        TiedSadeExample example = new TiedSadeExample();
        Long mainSku = tiedSade.getMainSku();
        Criteria criteria = example.createCriteria();
        criteria.andMainSkuEqualTo(mainSku);
        criteria.andTiedSadeTypeEqualTo((short) 3);

        try {
            return tiedSadeDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void delBatchByPrimaryKey(Long[] list) throws ServiceException {
        try {
            tiedSadeDao.delBatchByPrimaryKey(Lists.newArrayList(list));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updatePriceByKey(TiedSade tiedSade) throws ServiceException {
        try {
            tiedSadeDao.updateMatchPriceByKey(tiedSade);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int insertTiedSaleByBatch(List<TiedSade> list) throws ServiceException {
        try {
            return tiedSadeDao.insertTiedSaleByBatch(list);
        } catch (SQLException e) {
            logger.error("搭售添加失败", e);
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateTiedSaleType(TiedSade tiedSade) throws ServiceException {
        try {
            tiedSadeDao.updateTiedSaleType(tiedSade);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TiedSade> findConnectListByExample(TiedSade tiedSade) throws ServiceException {
        TiedSadeExample example = new TiedSadeExample();
        Long mainSku = tiedSade.getMainSku();
        Criteria criteria = example.createCriteria();
        criteria.andMainSkuEqualTo(mainSku);
        criteria.andTiedSadeTypeEqualTo((short) 3);

        try {
            return tiedSadeDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}