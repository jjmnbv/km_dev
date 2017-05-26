package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.pltfm.app.dao.ViewProductSkuDAO;
import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.service.ViewProductSkuService;
import com.pltfm.app.vobject.ViewProductSku;
import com.pltfm.app.vobject.ViewProductSkuExample;
import com.pltfm.app.vobject.ViewProductSkuExample.Criteria;
import com.kmzyc.commons.page.Page;

@Service("viewProductSkuService")
public class ViewProductSkuServiceImpl implements ViewProductSkuService {

	@Resource
	private ViewProductSkuDAO viewProductSkuDao;

	@Override
	public void searchPageByUserId(Page page, ViewProductSku viewProductSku, String type, Integer loginUserId)
            throws ServiceException {
		int pageIndex = page.getPageNo();
		if (pageIndex == 0)
			pageIndex = 1;
		int pageSize = page.getPageSize();
		int skip = (pageIndex - 1) * pageSize;
		int max = pageSize;

		ViewProductSkuExample example = new ViewProductSkuExample();
		example.setUserId(String.valueOf(loginUserId));
		Criteria c = example.createCriteria();
        if (StringUtils.isNotBlank(viewProductSku.getProductTitle())) {
            c.andProductTitleLike("%"+viewProductSku.getProductTitle()+"%");
        }
        if (StringUtils.isNotBlank(viewProductSku.getProductSkuCode())) {
            c.andProductSkuCodeLike("%"+viewProductSku.getProductSkuCode()+"%");
        }
		// 其他地方校验新增
        if (viewProductSku.getCategoryId() != null && viewProductSku.getCategoryId().intValue() != 0) {
            c.andCategoryIdEqualTo(viewProductSku.getCategoryId());
        }
        //产品状态(2-5)=AUDIT("2","已审核,待上架"),SYSDOWN("5","系统下架");
		if(!"ALL".equals(type)){
			c.andStatusBetween(ProductStatus.AUDIT.getStatus(), ProductStatus.SYSDOWN.getStatus());
		}
		if (StringUtils.isNotBlank(viewProductSku.getStatus())) {
			c.andStatusEqualTo(viewProductSku.getStatus());
		}
		if (StringUtils.isNotBlank(viewProductSku.getProductNo())) {
			c.andProductNoLike("%"+viewProductSku.getProductNo()+"%");
		}
		example.setOrderByClause(" PRODUCT_ID DESC, PRODUCT_SKU_ID desc ");

		try {
			int count = viewProductSkuDao.countByExampleByUser(example);
			List<ViewProductSku> list = viewProductSkuDao.selectByExampleByUser(example, skip, max);
			for(ViewProductSku pd : list){
				pd.setProcuctName(pd.getProcuctName().replaceAll(" ","&nbsp;"));
				pd.setProductTitle(pd.getProductTitle().replaceAll(" ","&nbsp;"));
			}
			page.setDataList(list);
			page.setRecordCount(count);
		} catch (SQLException e) {
            throw new ServiceException(e);
		}
	}

	@Override
	public ViewProductSku findByProductSkuId(Long productSkuId) throws ServiceException {
		ViewProductSkuExample example = new ViewProductSkuExample();
		example.createCriteria().andProductSkuIdEqualTo(productSkuId);

        try {
            return (ViewProductSku) viewProductSkuDao.selectByExample(example).get(0);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public List<ViewProductSku> findByProductId(Long productId) throws ServiceException {
		ViewProductSkuExample example = new ViewProductSkuExample();
		example.createCriteria().andProductIdEqualTo(productId).andSkuStatusEqualTo("1");

        try {
            return viewProductSkuDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public List<ViewProductSku> findProductAndSkuAttrByProductId(Long productId) throws ServiceException {
		ViewProductSkuExample example = new ViewProductSkuExample();
		example.createCriteria().andProductIdEqualTo(productId);

        try {
            return viewProductSkuDao.selectSKUAttrByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public ViewProductSku findByProductSkuCode(String productSkuCode) throws ServiceException {
		ViewProductSkuExample example = new ViewProductSkuExample();
		example.createCriteria().andProductSkuCodeEqualTo(productSkuCode);

        try {
            return (ViewProductSku) viewProductSkuDao.selectByExample(example).get(0);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
	@Override
	public void searchPageForFreightByUserId(Page page, ViewProductSku viewProductSku, Integer loginUserId)
			throws ServiceException {
		int pageIndex = page.getPageNo();
		if (pageIndex == 0)
			pageIndex = 1;
		int pageSize = page.getPageSize();
		int skip = (pageIndex - 1) * pageSize;
		int max = pageSize;

		ViewProductSkuExample example = new ViewProductSkuExample();
		example.setUserId(String.valueOf(loginUserId));
		Criteria c = example.createCriteria();
		if (StringUtils.isNotBlank(viewProductSku.getProductTitle())) {
			c.andProductTitleLike("%"+viewProductSku.getProductTitle()+"%");
		}
		if (StringUtils.isNotBlank(viewProductSku.getProductSkuCode())) {
			c.andProductSkuCodeLike("%"+viewProductSku.getProductSkuCode()+"%");
		}
		if (StringUtils.isNotBlank(viewProductSku.getStatus())) {
			c.andStatusEqualTo(viewProductSku.getStatus());
		}
		if (StringUtils.isNotBlank(viewProductSku.getProductNo())) {
			c.andProductNoLike("%"+viewProductSku.getProductNo()+"%");
		}
		example.setOrderByClause(" PRODUCT_ID DESC, PRODUCT_SKU_ID desc ");

		try {
			int count = viewProductSkuDao.countByExampleByUser(example);
			List<ViewProductSku> list = viewProductSkuDao.selectByExampleForFreightByUser(example, skip, max);
			for(ViewProductSku pd : list){
				pd.setProcuctName(pd.getProcuctName().replaceAll(" ","&nbsp;"));
				pd.setProductTitle(pd.getProductTitle().replaceAll(" ","&nbsp;"));
			}
			page.setDataList(list);
			page.setRecordCount(count);
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}

}