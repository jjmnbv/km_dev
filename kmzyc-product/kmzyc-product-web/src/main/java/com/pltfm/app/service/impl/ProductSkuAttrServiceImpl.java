package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.ProductSkuAttrDAO;
import com.pltfm.app.service.ProductSkuAttrService;
import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuAttrExample;
import com.kmzyc.commons.exception.ServiceException;

@Service("productSkuAttrService")
public class ProductSkuAttrServiceImpl implements ProductSkuAttrService {

    @Resource
    private ProductSkuAttrDAO productSkuAttrDao;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void addProductSkuAttr(ProductSkuAttr productSkuAttr) throws ServiceException {
        try {
            productSkuAttrDao.insert(productSkuAttr);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public List<ProductSkuAttr> queryProductSkuAttrList(ProductSkuAttr productSkuAttr) throws ServiceException {
        ProductSkuAttrExample example = new ProductSkuAttrExample(); // 组装where查询条件的对象
        example.createCriteria().andProductSkuIdEqualTo(productSkuAttr.getProductSkuId());
        example.setOrderByClause(" CATEGORY_ATTR_ID ");

        try {
            return productSkuAttrDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteProductSkuAttr(Long productSkuId) throws ServiceException {
        ProductSkuAttrExample example = new ProductSkuAttrExample(); // 组装where查询条件的对象
        example.createCriteria().andProductSkuIdEqualTo(productSkuId);

        try {
            productSkuAttrDao.deleteByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean queryByAttrValueId(Long attrValueId) throws ServiceException {
        ProductSkuAttrExample example = new ProductSkuAttrExample();
        example.createCriteria().andCategoryAttrValueIdEqualTo(attrValueId.intValue());

        try {
            return productSkuAttrDao.countByExample(example) > 0;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String querySkuAttrValueBySkuId(Long productSkuId) throws ServiceException {
        try {
            return productSkuAttrDao.querySkuAttrValueBySkuId(productSkuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Long, List<ProductSkuAttr>> findAndChangeSkuNewAttr(Long productId) throws ServiceException {
        try {
            List<ProductSkuAttr> list = productSkuAttrDao.findSkuNewAttr(productId);
            Map<Long, List<ProductSkuAttr>> map = new LinkedHashMap();

            for (ProductSkuAttr productSkuAttr : list) {
                if (!map.containsKey(productSkuAttr.getCategoryAttrId())) {
                    map.put(productSkuAttr.getCategoryAttrId(), new ArrayList<ProductSkuAttr>());
                    map.get(productSkuAttr.getCategoryAttrId()).add(productSkuAttr);
                } else {
                    map.get(productSkuAttr.getCategoryAttrId()).add(productSkuAttr);
                }
            }
            return map;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteProductSkuAttrByProductId(Long productId) throws ServiceException {
        try {
            productSkuAttrDao.deleteProductSkuAttrByProductId(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}


