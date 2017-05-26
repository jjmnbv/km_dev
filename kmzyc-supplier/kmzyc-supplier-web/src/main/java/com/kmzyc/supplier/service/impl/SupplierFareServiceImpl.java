package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.ProductRemoteService;
import com.kmzyc.supplier.dao.SupplierFareDAO;
import com.kmzyc.supplier.model.SupplierFare;
import com.kmzyc.supplier.service.SupplierFareService;

import redis.clients.jedis.JedisCluster;

@Service("supplierFareService")
public class SupplierFareServiceImpl implements SupplierFareService {

    private static Logger logger = LoggerFactory.getLogger(SupplierFareServiceImpl.class);

    @Resource
    private SupplierFareDAO supplierFareDAO;

    @Resource
    private JedisCluster jedisCluster;

    @Resource
    private ProductRemoteService productRemoteService;

    @Override
    public Pagination searchPage(SupplierFare supplierFare, Pagination page) throws ServiceException {
        Map<String, Object> condition = new HashMap();
        condition.put("supplierId", supplierFare.getSupplierId());
        if (StringUtils.isNotBlank(supplierFare.getName())) {
            condition.put("name", supplierFare.getName().trim());
        }
        if (StringUtils.isNotBlank(supplierFare.getSite())) {
            condition.put("site", supplierFare.getSite().trim());
        }
        page.setObjCondition(condition);
        try {
            supplierFareDAO.findFareListByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    public int countFareBySupplierId(Long supplierId) throws ServiceException {
        try {
            return supplierFareDAO.countFareBySupplierId(supplierId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
    public Long insertFare(SupplierFare supplierFare) throws ServiceException {
        Long count = null;
        try {
            count = supplierFareDAO.insertSelective(supplierFare);
            String keyString = "b2b.com.kmzyc.supplier.fare.info." + supplierFare.getSupplierId();
            jedisCluster.del(keyString);
            if (count == null) {
                return count;
            }

            //发布供应商产品页面
            List<Long> supplierIdList = new ArrayList<Long>();
            supplierIdList.add(supplierFare.getSupplierId());
            productRemoteService.upShelfForSupplier(supplierIdList);
        } catch (Exception e) {
            logger.error("添加商家id={}运费缓存失败:{}", new Object[]{supplierFare.getSupplierId(), e});
            throw new ServiceException(e);
        }
        return count;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
    public int updateFare(SupplierFare supplierFare) throws ServiceException {
        int count = 0;
        try {
            count = supplierFareDAO.updateByPrimaryKeySelective(supplierFare);
            String keyString = "b2b.com.kmzyc.supplier.fare.info." + supplierFare.getSupplierId();
            jedisCluster.del(keyString);
            if (count < 1) {
                return count;
            }

            //发布供应商产品页面
            List<Long> supplierIdList = new ArrayList<Long>();
            supplierIdList.add(supplierFare.getSupplierId());
            productRemoteService.upShelfForSupplier(supplierIdList);
        } catch (Exception e) {
            logger.error("修改商家id={},运费缓存失败:{}", new Object[]{supplierFare.getSupplierId(), e});
            throw new ServiceException(e);
        }
        return count;
    }

    @Override
    public SupplierFare findByFareId(Long fareId) throws ServiceException {
        try {
            return supplierFareDAO.selectByPrimaryKey(fareId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
    public int deleteFare(Long fareId) throws ServiceException {
        try {
            return supplierFareDAO.deleteByPrimaryKey(fareId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
