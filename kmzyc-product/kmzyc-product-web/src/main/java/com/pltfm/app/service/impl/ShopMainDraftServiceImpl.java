package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.supplier.enums.ShopAuditStatus;
import com.kmzyc.supplier.model.ShopMainDraft;
import com.kmzyc.supplier.model.example.ShopMainDraftExample;
import com.kmzyc.supplier.model.example.ShopMainDraftExample.Criteria;
import com.pltfm.app.dao.ShopMainDraftDAO;
import com.pltfm.app.service.ShopMainDraftService;
import com.pltfm.app.service.SupplierShopService;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

@Service("shopMainDraftService")
public class ShopMainDraftServiceImpl implements ShopMainDraftService {
	
	@Resource
	private ShopMainDraftDAO shopMainDraftDao;
	
	@Resource
	private SupplierShopService supplierShopService;

	@Override
	public List<ShopMainDraft> supplierShopDraftListShow(Page page, ShopMainDraft shopDraft) throws ServiceException {
		int pageIndex = page.getPageNo();
		if(pageIndex == 0) pageIndex = 1;
		int pageSize = page.getPageSize();
		int skip = (pageIndex-1) * pageSize;
		int max = pageSize;
		
		ShopMainDraftExample example=new ShopMainDraftExample();
		Criteria criteria = example.createCriteria();
		criteria.andAuditStatusEqualTo(ShopAuditStatus.APPLY.getStatus());
		if(StringUtils.isNotBlank(shopDraft.getShopTitle())){//店铺标题
			criteria.andShopTitleLike("%" + shopDraft.getShopTitle().trim() + "%");
		}
		if(StringUtils.isNotBlank(shopDraft.getShopName())){//店铺名称
			criteria.andShopNameLike("%" + shopDraft.getShopName().trim() + "%");
		}
		if(StringUtils.isNotBlank(shopDraft.getStatus())){//店铺状态
			example.setStatus(shopDraft.getStatus());
		}
		if(StringUtils.isNotBlank(shopDraft.getCorporateName())){//商户名称
			example.setCorporateName(shopDraft.getCorporateName().trim());
		}
		example.setOrderByClause("SHOP_ID DESC");
        
        try {
            int count= shopMainDraftDao.countByExample(example);
            List<ShopMainDraft> showList= shopMainDraftDao.selectByExampleShopDraftList(example, skip, max);
            page.setDataList(showList);
            page.setRecordCount(count);
            return showList;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public ShopMainDraft supplierShopDraftView(Long shopId) throws ServiceException {
        try {
            return shopMainDraftDao.selectByPrimaryKey(shopId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public int updateShopMainDraftBatch(List<ShopMainDraft> list,String auditStatus,List<Long> shopIdList)
            throws ServiceException {
		int result = 0;
		List<ShopMainDraft> draftList = null;
		try {
			shopMainDraftDao.updateBatch(list);
			ShopMainDraftExample example=new ShopMainDraftExample();
			Criteria criteria = example.createCriteria();
			criteria.andShopIdIn(shopIdList);
			draftList = shopMainDraftDao.selectByExampleForOfficial(example);
			if(ShopAuditStatus.AUDIT.getStatus().equals(auditStatus)){
				for(ShopMainDraft smd :draftList){
					if(supplierShopService.supplierShopView(smd.getShopId()) != null){
						shopMainDraftDao.updateOfficialFromDraft(smd);
					}else{
						shopMainDraftDao.insertOfficialFromDraft(smd);
					}
				}
				shopMainDraftDao.deleteDraftList(shopIdList);
			}else{
				for(ShopMainDraft shopMainDraft :list){
					if(supplierShopService.supplierShopView(shopMainDraft.getShopId()) != null){
						shopMainDraftDao.updateShopMainRemarkByShopId(shopMainDraft);
					}
				}
			}
			result = 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		return result;
	}

}
