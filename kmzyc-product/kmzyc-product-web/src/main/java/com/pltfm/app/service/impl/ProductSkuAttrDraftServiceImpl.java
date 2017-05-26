package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pltfm.app.vobject.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.ProductSkuAttrDraftDAO;
import com.pltfm.app.service.ProductSkuAttrDraftService;
import com.kmzyc.commons.exception.ServiceException;

@Repository("productSkuAttrDraftService")
public class ProductSkuAttrDraftServiceImpl implements ProductSkuAttrDraftService {

	@Resource
	private ProductSkuAttrDraftDAO productSkuAttrDraftDao;
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public void addProductSkuAttrDraft(ProductSkuAttrDraft productSkuAttrDraft) throws ServiceException {
        try {
            productSkuAttrDraftDao.insert(productSkuAttrDraft);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public List<ProductSkuAttrDraft> findByProductId(Long productId) throws ServiceException {
        try {
            return productSkuAttrDraftDao.findByProductId(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public void batchInsertOfficical(List<ProductSkuAttrDraft> list) throws ServiceException {
		try {
			if(list!=null&&list.size()>0){
				int result = productSkuAttrDraftDao.batchInsertOfficial(list);
				if(result == 0){
					throw new ServiceException("");
				}
			}
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public void batchDeleteDraft(List<ProductSkuAttrDraft> list) throws ServiceException {
		try {
			if(list!=null&&list.size()>0){
				int result = productSkuAttrDraftDao.batchDeleteDraft(list);
				if(result == 0){
					throw new ServiceException("");
				}
			}
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public void deleteProductSkuAttr(Long productSkuId) throws ServiceException {
		ProductSkuAttrDraftExample example = new ProductSkuAttrDraftExample(); // 组装where查询条件的对象
		example.createCriteria().andProductSkuIdEqualTo(productSkuId);

        try {
            productSkuAttrDraftDao.deleteByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public void deleteProductSkuAttrByProductId(Long productId) throws ServiceException {
        try {
            productSkuAttrDraftDao.deleteProductSkuAttrByProductId(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public void batchInsertDraft(List<ProductSkuAttr> list) throws ServiceException {
		try {
			if (list != null && list.size() > 0) {
				int result = productSkuAttrDraftDao.batchInsertDraftFromOfficial(list);
				if (result == 0) {
					throw new ServiceException("");
				}
			}
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<ProductSkuAttrDraft> findSkuAttrDraftByProductId(Long productId) throws ServiceException {
        try {
            return productSkuAttrDraftDao.findSkuAttrDraftByProductId(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public List<ViewSkuAttr> findSkuDraftInfoByProductSkuId(Long productSkuId) throws ServiceException {
        try {
            return productSkuAttrDraftDao.findSkuDraftInfoByProductSkuId(productSkuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public boolean queryByAttrValueId(Long attrValueId) throws ServiceException {
		ProductSkuAttrDraftExample example = new ProductSkuAttrDraftExample();
		example.createCriteria().andCategoryAttrValueIdEqualTo(attrValueId);

        try {
            return productSkuAttrDraftDao.countByExample(example)>0;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void batchUpdateSkuAttrStatus(List<ProductSkuDraft> productSkuDraftList) throws ServiceException {
		try {
            if (CollectionUtils.isNotEmpty(productSkuDraftList)) {
                List<ProductSkuAttrDraft> list = new ArrayList<ProductSkuAttrDraft>();
                ProductSkuAttrDraft productSkuAttrDraft = null;
                for (ProductSkuDraft p : productSkuDraftList) {
                    productSkuAttrDraft = new ProductSkuAttrDraft();
                    productSkuAttrDraft.setProductSkuId(p.getProductSkuId());
                    productSkuAttrDraft.setStatus(p.getStatus());
                    list.add(productSkuAttrDraft);
                }

                productSkuAttrDraftDao.batchUpdateSkuAttrStatus(list);
            }
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Map<Long, List<ProductSkuAttrDraft>> findAndChangeSkuNewAttr(Long productId) throws ServiceException {
        try {
            List<ProductSkuAttrDraft> list = productSkuAttrDraftDao.findSkuNewAttr(productId);
            Map<Long, List<ProductSkuAttrDraft>> map = new LinkedHashMap();

            for(ProductSkuAttrDraft skuAttrDraft : list){
                if(!map.containsKey(skuAttrDraft.getCategoryAttrId())){
                    map.put(skuAttrDraft.getCategoryAttrId(), new ArrayList());
                    map.get(skuAttrDraft.getCategoryAttrId()).add(skuAttrDraft);
                }else{
                    map.get(skuAttrDraft.getCategoryAttrId()).add(skuAttrDraft);
                }
            }

            return map;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
