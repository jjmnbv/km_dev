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
import com.kmzyc.supplier.dao.ProductImageDraftDAO;
import com.kmzyc.supplier.service.ProductImageDraftService;
import com.kmzyc.supplier.util.FileUploadUtils;
import com.pltfm.app.vobject.ProductImageDraft;


@Service("productImageDraftService")
public class ProductImageDraftServiceImpl implements ProductImageDraftService {

    private Logger logger = LoggerFactory.getLogger(ProductImageDraftServiceImpl.class);

    @Resource
    private ProductImageDraftDAO productImageDraftDao;

    @Resource
    private ProductDraftRemoteService productDraftRemoteService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
    public Long saveImage(ProductImageDraft image) throws ServiceException {
        try {
            Long count = productDraftRemoteService.saveImage(image);
            if (count.intValue() == -1) throw new Exception("保存图片失败!");
            return count;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
    public void deleteImageById(Long imageId) throws ServiceException {
        try {
            int count = productDraftRemoteService.deleteImageById(imageId);
            if (count == -1) {
                throw new ServiceException("图片删除失败!");
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
    public void modifyImageDefault(Long productSkuId, Long imageId) throws ServiceException {
        try {
            int count = productDraftRemoteService.modifyImageDefault(productSkuId, imageId);
            if (count == -1) throw new ServiceException("修改默认图片失败!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int findMaxSortNoBySkuId(Long productSkuId) throws ServiceException {
        try {
            int count = productDraftRemoteService.findMaxSortNoBySkuId(productSkuId);
            if (count == -1) throw new ServiceException("获取图片排序失败!");
            return count;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isLimitImage(Long productSkuId) throws ServiceException {
        try {
            return productDraftRemoteService.isLimitImage(productSkuId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductImageDraft> findByProductId(Long productId) throws ServiceException {
        try {
            return productImageDraftDao.findImagesByProductId(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
    public void deleteImageBatchById(List<Long> imageIds) throws ServiceException {
        try {
            List<ProductImageDraft> list = productImageDraftDao.findByImageIds(imageIds);
            list.forEach(FileUploadUtils::deleteProductImageDraftFile);
            productImageDraftDao.deletePicBatch(imageIds);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
