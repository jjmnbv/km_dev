package com.pltfm.app.service.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.zkconfig.ConfigurationUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.ProductCertificateDraftDAO;
import com.pltfm.app.service.ProductCertificateDraftService;
import com.pltfm.app.service.ProductCertificateService;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.vobject.ProductCertificate;
import com.pltfm.app.vobject.ProductCertificateDraft;
import com.pltfm.app.vobject.ProductCertificateDraftExample;
import com.kmzyc.commons.exception.ServiceException;

@Service("productCertificateDraftService")
public class ProductCertificateDraftServiceImpl implements ProductCertificateDraftService {

    @Resource
    private ProductCertificateDraftDAO productCertificateDraftdao;

    @Resource
    private ProductCertificateService productCertificateService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchInsertDraft(List<ProductCertificateDraft> list) throws ServiceException {
        try {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }

            int result = productCertificateDraftdao.batchInsertDraft(list);
            if (result == 0) {
                throw new ServiceException("ProductCertificateDraft批量向草稿表插入数据为空。");
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductCertificateDraft> findByProductId(Long productId) throws ServiceException  {
        ProductCertificateDraftExample exa = new ProductCertificateDraftExample();
        exa.createCriteria().andProductIdEqualTo(productId);
        exa.setOrderByClause(" FILE_TYPE ");

        try {
            return productCertificateDraftdao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByCertificateFiles(List<ProductCertificateDraft> list) throws ServiceException  {
        if (list != null && list.size() > 0) {
            String filePath = ConfigurationUtil.getString("certificateFilePath");
            for (ProductCertificateDraft pcd : list) {
                FileUploadUtils.deleteFile(filePath + pcd.getFilePath());
            }
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteByProductIdWithNotFile(Long productId) throws ServiceException  {
        ProductCertificateDraftExample exa = new ProductCertificateDraftExample();
        exa.createCriteria().andProductIdEqualTo(productId);

        try {
            productCertificateDraftdao.deleteByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductCertificateDraft> findByPscIds(List<Long> pscIds) throws ServiceException  {
        ProductCertificateDraftExample exa = new ProductCertificateDraftExample();
        exa.createCriteria().andPscIdIn(pscIds);

        try {
            return productCertificateDraftdao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteByPscIdsWithNotFile(List<Long> pscIds) throws ServiceException  {
        ProductCertificateDraftExample exa = new ProductCertificateDraftExample();
        exa.createCriteria().andPscIdIn(pscIds);

        try {
            productCertificateDraftdao.deleteByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchInsertOfficialFromDraft(List<ProductCertificateDraft> list) throws ServiceException {
        try {
            if (list != null && list.size() > 0) {
                int result = productCertificateDraftdao.batchInsertOfficialFromDraft(list);
                if (result == 0) {
                    throw new ServiceException("ProductCertificateDraft批量向正式表插入数据将草稿中的数据copy至正式表中，为空。");
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateOfficialData(Long productId, List<ProductCertificateDraft> list) throws ServiceException {
        try {
            List<ProductCertificate> officialList = productCertificateService.findByProductId(productId);
            List<ProductCertificateDraft> toInsertList = new ArrayList<ProductCertificateDraft>();
            List<ProductCertificate> toDeleteList = new ArrayList<ProductCertificate>();
            boolean flag = true;
            //比较是否有要删除的资质文件
            for (ProductCertificate pc : officialList) {
                flag = true;
                for (ProductCertificateDraft pcd : list) {
                    if (pc.getPscId().equals(pcd.getPscId())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    toDeleteList.add(pc);
                }
            }
            //比较是否有要添加的资质文件
            for (ProductCertificateDraft pcd : list) {
                flag = true;
                for (ProductCertificate pc : officialList) {
                    if (pcd.getPscId().equals(pc.getPscId())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    toInsertList.add(pcd);
                }
            }

            if (CollectionUtils.isNotEmpty(toDeleteList)) {
                productCertificateService.deleteProductCertificate(toDeleteList);
            }
            if (CollectionUtils.isNotEmpty(toInsertList)) {
                productCertificateDraftdao.batchInsertOfficialFromDraft(toInsertList);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void insertDraftFromOfficialByProductId(Long productId) throws ServiceException  {
        try {
            productCertificateDraftdao.insertDraftFromOfficialByProductId(productId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String findPathByProductId(Long productId) throws ServiceException  {
        ProductCertificateDraftExample exa = new ProductCertificateDraftExample();
        exa.createCriteria().andProductIdEqualTo(productId);
        exa.setOrderByClause(" FILE_TYPE ");

        try {
            List<ProductCertificateDraft> list = productCertificateDraftdao.selectByExample(exa);
            String path = null;
            if (CollectionUtils.isNotEmpty(list)) {
                ProductCertificateDraft pcd = list.get(0);
                int index = pcd.getFilePath().lastIndexOf(File.separatorChar);
                if (index > 0) {
                    path = pcd.getFilePath().substring(0, index);
                }
            }

            if (StringUtils.isEmpty(path)) {
                path = FileUploadUtils.createCertificateSavePath()
                        .substring(ConfigurationUtil.getString("certificateFilePath").length());
            }

            if (!path.endsWith(String.valueOf(File.separatorChar))) {
                path = path + File.separatorChar;
            }
            return path;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
