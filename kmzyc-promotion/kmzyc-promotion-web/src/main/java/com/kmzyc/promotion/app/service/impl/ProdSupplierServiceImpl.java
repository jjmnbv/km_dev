package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.ProdSupplierDAO;
import com.kmzyc.promotion.app.service.ProdSupplierService;
import com.kmzyc.promotion.app.vobject.ProdSupplier;
import com.kmzyc.promotion.app.vobject.ProdSupplierExample;
import com.kmzyc.promotion.app.vobject.ProdSupplierExample.Criteria;

/**
 * 供应商业务实现接口
 * 
 * @author tanyunxing
 * 
 */
@Component("prodSupplierService")
@SuppressWarnings("unchecked")
public class ProdSupplierServiceImpl implements ProdSupplierService {

    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(ProdSupplierServiceImpl.class);

    @Resource
    private ProdSupplierDAO prodSupplierDao;

    @Override
    public ProdSupplier findById(Long id) throws Exception {

        try {
            return prodSupplierDao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            logger.error("调用方法prodSupplierDao.selectByPrimaryKey异常：", e);
            throw e;
        }

    }

    @Override
    public int deleteObject(Long id) throws Exception {

        int i = 0;
        try {
            i = prodSupplierDao.deleteByPrimaryKey(id);
        } catch (SQLException e) {
            logger.error("deleteObject方法异常：", e);
            throw e;
        }

        return i;

    }

    @Override
    public int updateObject(ProdSupplier prod) throws Exception {

        int i = 0;
        try {
            i = prodSupplierDao.updateByPrimaryKey(prod);
        } catch (SQLException e) {
            logger.error("调用prodSupplierDao.updateByPrimaryKey方法异常：", e);
            throw e;
        }

        return i;

    }

    @Override
    public void searchPage(ProdSupplier proSuppliser, Page page) throws Exception {

        ProdSupplierExample example = new ProdSupplierExample();
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(proSuppliser.getSupplierName())) {
            String prodSupplierName = new StringBuilder("%").append(proSuppliser.getSupplierName())
                    .append("%").toString();
            criteria.andSupplierNameLike(prodSupplierName);
        }

        if (StringUtils.isNotBlank(proSuppliser.getAddress())) {
            String prodSupplierAddress =
                    new StringBuilder("%").append(proSuppliser.getAddress()).append("%").toString();
            criteria.andAddressLike(prodSupplierAddress);
        }

        if (StringUtils.isNotBlank(proSuppliser.getCreditworth())) {
            String prodSupplierCreateWorth = new StringBuilder("%")
                    .append(proSuppliser.getCreditworth()).append("%").toString();
            criteria.andCreditworthLike(prodSupplierCreateWorth);
        }
        if (StringUtils.isNotBlank(proSuppliser.getDeliveryWay())) {
            String prodSupplierDeliverWay = new StringBuilder("%")
                    .append(proSuppliser.getDeliveryWay()).append("%").toString();
            criteria.andDeliveryWayLike(prodSupplierDeliverWay);

        }

        example.setOrderByClause("SUPPLIER_ID   DESC");

        try {

            List<ProdSupplier> list = prodSupplierDao.selectByExample(example, page);

            int count = prodSupplierDao.countByExample(example);

            page.setDataList(list);
            page.setRecordCount(count);

        } catch (Exception e) {
            logger.error("searchPage方法异常：", e);

        }

    }

    @Override
    public void addSupplier(ProdSupplier prodSupplier) throws Exception {

        prodSupplierDao.insert(prodSupplier);

    }

    @Override
    public List<ProdSupplier> selectProdSuppliersByNameNotId(String name, Long id)
            throws Exception {

        // System.out.println("你好");
        return prodSupplierDao.selectListByNameNotId(name, id);

    }

}
