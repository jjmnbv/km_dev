package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.dao.ShopMainDAO;
import com.kmzyc.supplier.model.example.ShopMainExample;
import com.kmzyc.supplier.service.ShopMainService;
import com.pltfm.app.bean.ResultMessage;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("shopMainService")
public class ShopMainServiceImpl implements ShopMainService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ShopMainDAO shopMainDAO;

    @Override
    public Pagination searchPage(ShopMain shopMain, Pagination page) throws ServiceException {
        Map<String, Object> condition = new HashMap();
        condition.put("supplierId", shopMain.getSupplierId());
        if (StringUtils.isNotBlank(shopMain.getShopName())) {
            condition.put("shopName", shopMain.getShopName().trim());
        }
        if (StringUtils.isNotBlank(shopMain.getShopTitle())) {
            condition.put("shopTitle", shopMain.getShopTitle().trim());
        }
        page.setObjCondition(condition);
        try {
            shopMainDAO.findShopMainListByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int deleteShopMainById(Long shopId) throws ServiceException {
        try {
            return shopMainDAO.deleteByPrimaryKey(shopId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ShopMain findShopMainById(Long shopId) throws ServiceException {
        try {
            return shopMainDAO.selectByPrimaryKey(shopId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findShopMain(Long supplierId) throws ServiceException {
        try {
            return shopMainDAO.findShopMain(supplierId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateShopMain(ShopMain shopMain) throws ServiceException {
        try {
            return shopMainDAO.updateByPrimaryKeySelective(shopMain);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateStatus(List<Long> shopIdList, String status) throws ServiceException {
        try {
            return shopMainDAO.updateStatus(shopIdList, status);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateAuditStatus(List<Long> shopIdList, String auditStatus) throws ServiceException {
        try {
            return shopMainDAO.updateAuditStatus(shopIdList, auditStatus);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ShopMain findShopMainBySupplierId(Long supplierId) throws ServiceException {
        ShopMainExample exa = new ShopMainExample();
        exa.createCriteria().andSupplierIdEqualTo(supplierId);
        try {
            List<ShopMain> list = shopMainDAO.selectByExampleWithBLOBs(exa);
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
            return null;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ShopMain findShopMainByIdWithoutBLOBs(Long supplierId) throws ServiceException {
        ShopMainExample exa = new ShopMainExample();
        exa.createCriteria().andSupplierIdEqualTo(supplierId);
        try {
            List<ShopMain> list = shopMainDAO.selectByExampleWithoutBLOBs(exa);
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
            return null;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage publishShopMainHomePage(List<Long> shopIdList) throws ServiceException {

        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage batchUpdateShopMainUrl(List<ShopMain> shopMainList) throws ServiceException {
        ResultMessage result = new ResultMessage();
        result.setMessage("店铺主页地址更新成功!");
        result.setIsSuccess(true);
        try {
            int count = shopMainDAO.batchUpdateShopMain(shopMainList);
            if (count < 1) {
                log.error("店铺主页地址更新失败!");
                result.setMessage("店铺主页地址更新失败!");
                result.setIsSuccess(false);
                return result;
            }
        } catch (Exception e) {
            log.error("店铺主页地址更新失败!", e);
            result.setMessage("店铺主页地址更新失败!");
            result.setIsSuccess(false);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage deleteShopMainFilePath(ShopMain shopMain) throws ServiceException {
        ResultMessage result = new ResultMessage();
        result.setIsSuccess(true);
        try {
            int count = shopMainDAO.deleteShopMainFilePath(shopMain);
            if (count < 1) {
                log.error("店铺文件路径删除失败!");
                result.setIsSuccess(false);
                result.setMessage("店铺文件路径删除失败!");
                return result;
            }
        } catch (Exception e) {
            log.error("店铺文件路径删除失败!", e);
            result.setIsSuccess(false);
            result.setMessage("店铺文件路径删除失败!");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public List<ShopMain> findAllShopMainBySupplierId(Long supplierId) throws ServiceException {
        if (supplierId == null) {
            throw new ServiceException("没有找到供应商Id信息。");
        }

        ShopMainExample exa = new ShopMainExample();
        exa.createCriteria().andSupplierIdEqualTo(supplierId);
        try {
            return shopMainDAO.selectByExampleWithoutBLOBs(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String previewHomePage(Long shopId) throws ServiceException {
        return null;
    }

    @Override
    public boolean updateShopMainDescribe(ShopMain shopMain) throws ServiceException {
        try {
            int count = shopMainDAO.updateShopMainDescibe(shopMain);
            return count >= 1;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
