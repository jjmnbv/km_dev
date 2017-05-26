package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.ProductDraftRemoteService;
import com.kmzyc.supplier.dao.ProductImageDAO;
import com.kmzyc.supplier.service.ProductImageService;
import com.kmzyc.supplier.util.FileUploadUtils;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.app.vobject.ProductImageExample;


/**
 * 产品图片业务实现
 *
 */
@Service("productImageService")
public class ProductImageServiceImpl implements ProductImageService {
	
	private Logger logger = LoggerFactory.getLogger(ProductImageServiceImpl.class);
	
	@Resource
	private ProductImageDAO productImageDao;

	@Resource
	private ProductDraftRemoteService productDraftRemoteService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public boolean updateProductImageBatch(List<ProductImageDraft> list){
		try{
			return productDraftRemoteService.updateProductImageDraftBatch(list);
		}catch(Exception e){
			logger.error("批量更新图片失败。",e);
			return false;
		}
	}

	@Override
	public short findMaxSortNoBySkuId(Long productSkuId) throws ServiceException {
        try {
            Integer max = productImageDao.queryMaxSortNo(productSkuId);
			return max == null ? 0 : max.shortValue();
		} catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public Long saveImage(ProductImage image) throws ServiceException {
        try {
            return productImageDao.insertImage(image);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public void modifyImageDefault(Long productSkuId, Long imageId)
			throws ServiceException {
		try {
			ProductImage record = new ProductImage();
			ProductImageExample exa = new ProductImageExample();
			record.setIsDefault("1");
			exa.createCriteria().andSkuIdEqualTo(productSkuId);
			productImageDao.updateByExampleSelective(record, exa);
			exa =  new ProductImageExample();
			record.setIsDefault("0");
			exa.createCriteria().andImageIdEqualTo(imageId);
			productImageDao.updateByExampleSelective(record, exa);
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public void deleteImageWithFileByImageIds(List<Long> imageIds) throws ServiceException {
        try {
            List<ProductImage> list = productImageDao.queryByImageIds(imageIds);
            list.forEach(FileUploadUtils::deleteProductImageFile);
            productImageDao.deleteImageBatch(imageIds);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public void updateImageBatch(List<ProductImage> list) throws ServiceException {
        try {
            int result = productImageDao.updateImageBatch(list);
            if(result == 0) {
                throw new Exception("修改失败！");
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
