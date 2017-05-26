package com.pltfm.app.service.impl;

import com.pltfm.app.dao.ProductOperateLogDAO;
import com.pltfm.app.service.ProductOperateLogService;
import com.pltfm.app.vobject.ProductOperateLog;
import com.kmzyc.commons.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/10 16:33
 */
@Service("productOperateLogService")
public class ProductOperateLogServiceImpl implements ProductOperateLogService {

    @Resource
    private ProductOperateLogDAO productOperateLogDao;

    private Logger logger = LoggerFactory.getLogger(ProductOperateLogServiceImpl.class);

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long saveProductOperateLog(ProductOperateLog log) throws ServiceException {
        try {
            return productOperateLogDao.saveProductOperateLog(log);
        } catch (SQLException e) {
            logger.error("保存操作日志[{}]失败:[{}]", new Object[]{log, e});
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchSaveProductOperateLog(List<ProductOperateLog> list) throws ServiceException {
        try {
            productOperateLogDao.batchSaveProductOperateLog(list);
        } catch (SQLException e) {
            logger.error("保存操作日志[{}]失败:[{}]", new Object[]{list, e});
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductOperateLog> getProductOperateLogList(Long productId) throws ServiceException {
        try {
            return productOperateLogDao.getProductOperateLogList(productId);
        } catch (SQLException e) {
            logger.error("根据商品id[{}]查询日志操作失败:[{}]", new Object[]{productId, e});
            throw new ServiceException(e);
        }
    }

}