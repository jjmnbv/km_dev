package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.springframework.stereotype.Service;

import com.pltfm.app.dao.ViewSkuAttrDAO;
import com.pltfm.app.service.ViewSkuAttrService;
import com.pltfm.app.vobject.ViewSkuAttr;
import com.pltfm.app.vobject.ViewSkuAttrExample;
import com.pltfm.app.vobject.ViewSkuAttrExample.Criteria;

@Service("viewSkuAttrService")
public class ViewSkuAttrServiceImpl implements ViewSkuAttrService {

	@Resource
	private ViewSkuAttrDAO viewSkuAttrDao;

	@Override
	public List<ViewSkuAttr> findBySkuId(Long skuId) throws ServiceException {
		ViewSkuAttrExample ex = new ViewSkuAttrExample();
		ex.createCriteria().andProductSkuIdEqualTo(skuId);

        try {
            return viewSkuAttrDao.selectByExample(ex);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public Long findSkuIdByAttrAndValue(Long productId, List<Long> valueId) throws ServiceException {
		ViewSkuAttrExample ex = new ViewSkuAttrExample();
		Criteria c = null;
		for (int i = 0; i < valueId.size(); i++) {
			c = ex.createCriteria();
			c.andProductIdEqualTo(productId).andCategoryAttrValueIdEqualTo(valueId.get(i));
            if (i > 0) {
                ex.or(c);
            }
        }
        
        try {
            return viewSkuAttrDao.selectByAttrIdAndValueId(ex);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public List<ViewSkuAttr> findProductAttrAndValueByProductId(Long productId) throws ServiceException {
        try {
            return viewSkuAttrDao.findProductAttrAndValueByProductId(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}