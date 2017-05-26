package com.kmzyc.promotion.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.promotion.app.dao.ProductCertificateDAO;
import com.kmzyc.promotion.app.service.ProductCertificateService;
import com.kmzyc.promotion.app.util.FileUploadUtils;
import com.kmzyc.promotion.app.vobject.ProductCertificate;
import com.kmzyc.promotion.app.vobject.ProductCertificateExample;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Service("productCertificateService")
@SuppressWarnings("unchecked")
public class ProductCertificateServiceImpl implements ProductCertificateService {

    @Resource
    private ProductCertificateDAO productCertificateDao;

    @Override
    public List<ProductCertificate> findByProductId(Long productId) throws Exception {
        ProductCertificateExample exa = new ProductCertificateExample();
        exa.createCriteria().andProductIdEqualTo(productId);
        exa.setOrderByClause(" FILE_TYPE ");

        return productCertificateDao.selectByExample(exa);
    }

    @Override
    public void deleteProductCertificate(List<ProductCertificate> list) throws ServiceException {
        try {
            List<Long> pscIds = new ArrayList<Long>();
            for (ProductCertificate pc : list) {
                pscIds.add(pc.getPscId());
            }
            ProductCertificateExample exa = new ProductCertificateExample();
            exa.createCriteria().andPscIdIn(pscIds);
            productCertificateDao.deleteByExample(exa);

            for (ProductCertificate pc : list) {
                FileUploadUtils.deleteFile(ConfigurationUtil.getString("certificateFileViewPath")
                        + pc.getFilePath());
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}
