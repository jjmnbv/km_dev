package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.ShopProductCategoryDAO;
import com.pltfm.app.service.ShopProductCategoryService;
import com.pltfm.app.vobject.ShopProductCategory;
import com.pltfm.app.vobject.ShopProductCategoryExample;

@Service("shopProductCategoryService")
public class ShopProductCategoryServiceImpl implements ShopProductCategoryService {
	
	@Resource
	private ShopProductCategoryDAO shopProductCategoryDao;

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void insert(ShopProductCategory para) throws ServiceException {
        try {
            shopProductCategoryDao.insertSelective(para);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteByProductId(long productId) throws ServiceException {
		ShopProductCategoryExample example =new ShopProductCategoryExample();
		example.createCriteria().andProductIdEqualTo(productId);

        try {
            shopProductCategoryDao.deleteByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}