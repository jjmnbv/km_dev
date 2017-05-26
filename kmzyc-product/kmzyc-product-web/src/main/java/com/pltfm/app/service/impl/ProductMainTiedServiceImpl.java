package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.ProductmainTiedDAO;
import org.springframework.stereotype.Service;

import com.pltfm.app.service.ProductMainTiedService;

import com.pltfm.app.vobject.ProductmainTied;

import com.kmzyc.commons.page.Page;


@Service("productMainTiedService")
public class ProductMainTiedServiceImpl implements ProductMainTiedService {

    @Resource
    private ProductmainTiedDAO productMainTiedDao;

    @Override
    public void selectList(ProductmainTied prod, Page page) throws ServiceException {

        try {
            List<ProductmainTied> list = productMainTiedDao.selectList(prod, page);
            page.setRecordCount(productMainTiedDao.countItem(prod));
            page.setDataList(list);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void selectListTaoCan(ProductmainTied prod, Page page) throws ServiceException  {
        try {
            List<ProductmainTied> list = productMainTiedDao.selectListTaoCan(prod, page);
            page.setDataList(list);
            page.setRecordCount(productMainTiedDao.countItemTaoCan(prod));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void selectListTaoCanZiying(ProductmainTied prod, Page page) throws ServiceException  {
        try {
            List<ProductmainTied> list = productMainTiedDao.selectListTaoCanZiYing(prod, page);
            page.setDataList(list);
            page.setRecordCount(productMainTiedDao.countItemTaoCanZiying(prod));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void selectList(ProductmainTied prod, Page page, Integer userId) throws ServiceException  {
        prod.setUserId(String.valueOf(userId));

        try {
            List<ProductmainTied> list = productMainTiedDao.selectListByUser(prod, page);
            page.setDataList(list);
            page.setRecordCount(productMainTiedDao.countItemByUser(prod));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void selectListByRelation(ProductmainTied prod, Page page, Integer userId) throws ServiceException  {
        prod.setUserId(String.valueOf(userId));

        try {
            List<ProductmainTied> list = productMainTiedDao.selectListByUserByRelation(prod, page);
            page.setDataList(list);
            page.setRecordCount(productMainTiedDao.countItemByUserByRelation(prod));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public ProductmainTied findObjectBySku(Long id) throws ServiceException  {
        try {
            return productMainTiedDao.selectExampleBySku(id);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void selectListNotMainSKU(ProductmainTied prod, Page page) throws ServiceException  {
        try {
            List<ProductmainTied> list = productMainTiedDao.selectListExcepMainsku(prod, page);
            page.setDataList(list);
            page.setRecordCount(productMainTiedDao.countItemExceptMainsku(prod));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countItemExceptMainsku(ProductmainTied prod) throws ServiceException  {
        try {
            return productMainTiedDao.countItemExceptMainsku(prod);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, ProductmainTied> getProductSkuMapBySkucode(List<String> list) throws ServiceException {
        try {
            return productMainTiedDao.getProductSkuMapBySkucode(list);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

}