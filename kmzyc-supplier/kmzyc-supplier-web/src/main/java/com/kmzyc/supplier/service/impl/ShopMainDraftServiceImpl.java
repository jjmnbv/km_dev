package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.ShopMainDraft;
import com.kmzyc.supplier.dao.ShopMainDraftDAO;
import com.kmzyc.supplier.model.example.ShopMainDraftExample;
import com.kmzyc.supplier.service.ShopMainDraftService;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("shopMainDraftService")
public class ShopMainDraftServiceImpl implements ShopMainDraftService {
	
	@Resource
	private ShopMainDraftDAO shopMainDraftDAO;

	@Override
	public Pagination searchPage(ShopMainDraft shopMainDraft, Pagination page) throws ServiceException {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("supplierId", shopMainDraft.getSupplierId());
		if(StringUtils.isNotBlank(shopMainDraft.getShopName())){
			condition.put("shopName", shopMainDraft.getShopName().trim());
		}
		if(StringUtils.isNotBlank(shopMainDraft.getShopTitle())){
			condition.put("shopTitle", shopMainDraft.getShopTitle().trim());
		}
		page.setObjCondition(condition);
        try {
            shopMainDraftDAO.findShopMainListByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public Long insertShopMainDraft(ShopMainDraft shopMainDraft) throws ServiceException {
        try {
            return shopMainDraftDAO.insertSelectiveWithOutClob(shopMainDraft);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public Long insertAndUpdateShopMainDraft(ShopMainDraft shopMainDraft) throws ServiceException {
        try {
            return shopMainDraftDAO.insertAndUpdateShopMainDraftWithOutClob(shopMainDraft);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public Integer deleteByShopId(Long shopId) throws ServiceException {
        try {
            return shopMainDraftDAO.deleteByShopId(shopId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public ShopMainDraft findByShopId(Long shopId) throws ServiceException {
        try {
            return shopMainDraftDAO.selectByPrimaryKeyWithoutBLOBS(shopId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public int updateById(ShopMainDraft shopMainDraft) throws ServiceException {
        try {
            return shopMainDraftDAO.updateByPrimaryKeyWithoutBLOBS(shopMainDraft);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public int findShopMainDraft(Long supplierId)
			throws ServiceException {
        try {
            return shopMainDraftDAO.findShopMainDraft(supplierId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public Integer selectRepeatName(Map conditionMap) throws ServiceException {
        try {
            String name = shopMainDraftDAO.selectRepeatName(conditionMap);
            if(StringUtils.isEmpty(name)){
                return 0;
            }
            return 1;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public ShopMainDraft findShopMainDraftByIdWithoutBLOBs(Long supplierId) throws ServiceException {
		ShopMainDraftExample exa = new ShopMainDraftExample();
		exa.createCriteria().andSupplierIdEqualTo(supplierId);
        try {
            List<ShopMainDraft> list = shopMainDraftDAO.selectByExampleWithoutBLOBs(exa);
            if (CollectionUtils.isNotEmpty(list)) {
                return list.get(0);
            }
            return null;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public Integer selectRepeatNameForUpdateShopMain(Map conditionMap) throws ServiceException {
        try {
            String name = shopMainDraftDAO.selectRepeatNameForUpdateShopMain(conditionMap);
            if(StringUtils.isEmpty(name)){
                return 0;
            }
            return 1;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}