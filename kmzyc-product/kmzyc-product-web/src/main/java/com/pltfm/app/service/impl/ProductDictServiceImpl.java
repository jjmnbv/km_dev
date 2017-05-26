package com.pltfm.app.service.impl;

import com.pltfm.app.dao.ProductDictDAO;
import com.pltfm.app.service.ProductDictService;
import com.kmzyc.commons.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/10/31 10:39
 */
@Service("productDictService")
public class ProductDictServiceImpl implements ProductDictService {

    @Resource
    private ProductDictDAO productDictDao;

    @Override
    public BigDecimal getSupportPvProportion() throws ServiceException {
        try {
            return productDictDao.getSupportPvProportion();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
