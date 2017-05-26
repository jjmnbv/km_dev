package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.ProductDraftRemoteService;
import com.kmzyc.supplier.dao.ProductDraftSkuDAO;
import com.kmzyc.supplier.service.ProductDraftSkuService;
import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageDraftExample;
import com.pltfm.app.vobject.ProductSkuAttrDraft;
import com.pltfm.app.vobject.ProductSkuDraft;
import com.pltfm.app.vobject.ProductSkuDraftExample;
import com.pltfm.app.vobject.ViewProductSku;

/**
 * 产品SKU业务逻辑类
 * 
 */
@Service("productDraftSkuService")
public class ProductDraftSkuServiceImpl implements ProductDraftSkuService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ProductDraftSkuDAO productDraftSkuDAO;

    @Resource
	private ProductDraftRemoteService productDraftRemoteService;

	@Override
	public Pagination searchPage(Long supplierId, ViewProductSku viewProductSku, Pagination page)
            throws ServiceException {
		Map<String, Object> condition = new HashMap();
		condition.put("supplierId", supplierId.toString());
		if(StringUtils.isNotBlank(viewProductSku.getProcuctName())){
			condition.put("productName", viewProductSku.getProcuctName().trim());
		}
		if(StringUtils.isNotBlank(viewProductSku.getProductNo())){
			condition.put("productNo", viewProductSku.getProductNo().trim());
		}
		if(StringUtils.isNotBlank(viewProductSku.getProductSkuCode())){
			condition.put("productSkuCode", viewProductSku.getProductSkuCode().trim());
		}
		if(StringUtils.isNotEmpty(viewProductSku.getSkuStatus())){
			condition.put("skuStatus", viewProductSku.getSkuStatus().trim());
		}
		
		condition.put("status", viewProductSku.getStatus());
		page.setObjCondition(condition);
        try {
            productDraftSkuDAO.findProductSkuListByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
	}

	@Override
	public ProductSkuDraft findSingleProductSku(Long productSkuId) throws ServiceException {
        try {
            return productDraftSkuDAO.findSingleSkuAndAttrValue(productSkuId);
        } catch (SQLException e) {
            logger.error("获取单个对象失败：", e);
            throw new ServiceException(e);
        }
    }

	@Override
	public List<ProductImage> findAllBySkuId(Long productSkuId) throws ServiceException {
		ProductImageDraftExample exa = new ProductImageDraftExample();
		exa.createCriteria().andSkuIdEqualTo(productSkuId);
		exa.setOrderByClause(" is_default,sortno ");
        try {
            return productDraftSkuDAO.selectByExampleForImages(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public boolean updatePriceAndWeight(Long productId, List<ProductSkuDraft> list) throws ServiceException {
        try {
            int count = productDraftRemoteService.updateBatchByPrimaryKey(productId, list);
            return count >= 0;
        } catch (Exception e) {
            throw new ServiceException(e);
        }

	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public boolean updateSingleSkuPrice(Long productId, List<ProductSkuDraft> list) throws ServiceException {
        try {
            int count = productDraftRemoteService.updateSingleSkuPrice(productId, list);
            return count >= 0;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public boolean updateSkuIntroduce(ProductSkuDraft productSkuDraft) throws ServiceException {
        try {
            int count = productDraftRemoteService.updateProductSkuDraft(productSkuDraft);
            return count > 0;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public List<ProductSkuDraft> findIsValidProductSkuByProductId(Long productId) throws ServiceException {
		ProductSkuDraftExample exa = new ProductSkuDraftExample();
		exa.createCriteria().andProductIdEqualTo(productId).andStatusEqualTo(IsValidStatus.VALID.getStatus());
        try {
            return productDraftSkuDAO.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public Map<Long, Set<String>> findAndChangeSkuNewAttr( Long productId) throws ServiceException {
        try {
            List<ProductSkuAttrDraft> list = productDraftSkuDAO.findSkuNewAttr(productId);
            //兼容历史遗留重复数据,更改使用Set
            Map<Long, Set<String>> map = new LinkedHashMap();
            for (ProductSkuAttrDraft skuAttr : list) {
                if (!map.containsKey(skuAttr.getCategoryAttrId())) {
                    map.put(skuAttr.getCategoryAttrId(), new LinkedHashSet());
                }
                map.get(skuAttr.getCategoryAttrId()).add(skuAttr.getNewAttr());
            }
            return map;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
}
