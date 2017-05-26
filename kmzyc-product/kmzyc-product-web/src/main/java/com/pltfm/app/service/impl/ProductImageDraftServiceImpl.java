package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.zkconfig.ConfigurationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.ProductImageDraftDAO;
import com.pltfm.app.service.ProductDraftService;
import com.pltfm.app.service.ProductImageDraftService;
import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.app.vobject.ProductImageDraftExample;
import com.kmzyc.commons.exception.ServiceException;

/**
 * @author tanyunxing
 */
@Service("productImageDraftService")
public class ProductImageDraftServiceImpl implements ProductImageDraftService {

    @Resource
    private ProductImageDraftDAO productImageDraftDao;

    @Resource
    private ProductImageService productImageService;

    private final String AP_PATH = ConfigurationUtil.getString("pictureUploadPath");

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long saveImage(ProductImageDraft image) throws ServiceException {
        try {
            return productImageDraftDao.insert(image);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteImageById(Long imageId) throws ServiceException  {
        try {
            productImageDraftDao.deleteByPrimaryKey(imageId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void modifyImageDefault(Long productSkuId, Long imageId) throws ServiceException  {
        ProductImageDraft record = new ProductImageDraft();
        ProductImageDraftExample exa = new ProductImageDraftExample();

        try {
            record.setIsDefault("1");
            exa.createCriteria().andSkuIdEqualTo(productSkuId);
            productImageDraftDao.updateByExampleSelective(record, exa);

            exa = new ProductImageDraftExample();
            record.setIsDefault("0");
            exa.createCriteria().andImageIdEqualTo(imageId);
            productImageDraftDao.updateByExampleSelective(record, exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductImageDraft> findAllBySkuId(Long productSkuId) throws ServiceException  {
        ProductImageDraftExample exa = new ProductImageDraftExample();
        exa.createCriteria().andSkuIdEqualTo(productSkuId);
        exa.setOrderByClause(" is_default,sortno ");

        try {
            return productImageDraftDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductImageDraft> findAllImage() throws ServiceException  {
        ProductImageDraftExample exa = new ProductImageDraftExample();
        exa.createCriteria().andImgPath8IsNull();
        exa.setOrderByClause(" sku_id,is_default,sortno ");

        try {
            return productImageDraftDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductImage> findAllIntoOfficialBySkuId(Long productSkuId) throws ServiceException  {
        ProductImageDraftExample exa = new ProductImageDraftExample();
        exa.createCriteria().andSkuIdEqualTo(productSkuId);
        exa.setOrderByClause(" is_default,sortno ");

        try {
            return productImageDraftDao.selectByExampleIntoOfficial(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findMaxSortNoBySkuId(Long productSkuId) throws ServiceException  {
        try {
            return productImageDraftDao.findMaxSortNoByProductSkuId(productSkuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean updateProductImageBatch(List<ProductImageDraft> list) throws ServiceException {
        try {
            productImageDraftDao.updateProductImage(list);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public boolean isLimitImage(Long productSkuId) throws ServiceException  {
        try {
            return productImageDraftDao.findCountsByProductSkuId(productSkuId) >= 10;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductImageDraft> findByProductId(Long productId) throws ServiceException  {
        ProductImageDraftExample exa = new ProductImageDraftExample();
        exa.createCriteria().andProductIdEqualTo(productId);

        try {
            return productImageDraftDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchInsertOfficial(List<ProductImageDraft> list) throws ServiceException {
        try {
            if (list != null && list.size() > 0) {
                productImageDraftDao.batchInsertOfficial(list);
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchDeleteDraft(List<ProductImageDraft> list, boolean needRemoveFile) throws ServiceException {
        try {
            if (list != null && list.size() > 0) {
                int result = productImageDraftDao.batchDeleteDraft(list);
                if (result == 0) {
                    throw new ServiceException("批量删除产品图片草稿表数据及是否删除图片文件物理数据失败，为空。");
                }

                if (needRemoveFile) {
                    list.forEach(this::deleteProductImageFile);
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchDeleteDraft(List<ProductImageDraft> list) throws ServiceException {
        try {
            if (list != null && list.size() > 0) {
                int result = productImageDraftDao.batchDeleteDraft(list);
                if (result == 0) {
                    throw new ServiceException("批量删除产品图片草稿表,为空.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchDeleteDraftWithImagePath(List<ProductImageDraft> list) throws ServiceException  {
        try {
            if (list != null && list.size() > 0) {
                list.forEach(this::deleteProductImageFile);
                int result = productImageDraftDao.batchDeleteDraft(list);
                if (result == 0) {
                    throw new ServiceException("批量删除产品图片并将图片文件一起删除, 为空。");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void insertDraftFromOfficialByProductId(Long productId) throws ServiceException {
        try {
            List<ProductImageDraft> list = productImageDraftDao.findOfficialToDraftByProductId(productId);
            if (list != null && list.size() > 0) {
                int result = productImageDraftDao.batchInsertDraftFromOfficial(list);
                if (result == 0) {
                    throw new ServiceException("从正式表中将数据复制到草稿表中, 为空，result=0.");
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public List<ProductImageDraft> updateImageOfficial(Long productId, List<ProductImageDraft> list)
            throws ServiceException {
        List<ProductImageDraft> toDeleteList = new ArrayList();

        try {
            List<ProductImageDraft> officialList = productImageDraftDao.findOfficialToDraftByProductId(productId);
            List<ProductImageDraft> toInsertList = new ArrayList<ProductImageDraft>();

            if (list != null && list.size() > 0) {
                if (officialList != null && officialList.size() > 0) {
                    for (ProductImageDraft pid : officialList) {
                        boolean flag = true;
                        for (ProductImageDraft pd : list) {
                            if (pid.getImageId().equals(pd.getImageId())) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            toDeleteList.add(pid);
                        }
                        productImageService.deleteImageById(pid.getImageId());
                    }
                }
                toInsertList.addAll(list);

                if (toInsertList.size() > 0) {
                    batchInsertOfficial(toInsertList);
                }
            } else {
                for (ProductImageDraft pid : officialList) {
                    toDeleteList.add(pid);
                    productImageService.deleteImageById(pid.getImageId());
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return toDeleteList;
    }

    private void deleteProductImageFile(ProductImageDraft pi) {

        if (pi.getImgPath() != null && !pi.getImgPath().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath());
        }
        if (pi.getImgPath1() != null && !pi.getImgPath1().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath1());
        }
        if (pi.getImgPath2() != null && !pi.getImgPath2().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath2());
        }
        if (pi.getImgPath3() != null && !pi.getImgPath3().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath3());
        }
        if (pi.getImgPath4() != null && !pi.getImgPath4().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath4());
        }
        if (pi.getImgPath5() != null && !pi.getImgPath5().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath5());
        }
        if (pi.getImgPath6() != null && !pi.getImgPath6().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath6());
        }
        if (pi.getImgPath7() != null && !pi.getImgPath7().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath7());
        }
        if (pi.getImgPath8() != null && !pi.getImgPath8().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath8());
        }
        if (pi.getImgPath9() != null && !pi.getImgPath9().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath9());
        }
        if (pi.getImgPath10() != null && !pi.getImgPath10().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath10());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateImageById(ProductImageDraft image) throws ServiceException  {
        try {
            productImageDraftDao.updateByPrimaryKeySelective(image);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }


}
