package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.kmzyc.zkconfig.ConfigurationUtil;

import org.springframework.stereotype.Service;

import com.pltfm.app.dao.ProductCertificateDAO;
import com.pltfm.app.service.ProductCertificateService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.vobject.ProductCertificate;
import com.pltfm.app.vobject.ProductCertificateDraftExample;
import com.pltfm.app.vobject.ProductCertificateExample;
import com.kmzyc.commons.exception.ServiceException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("productCertificateService")
public class ProductCertificateServiceImpl implements ProductCertificateService {

	@Resource
	private ProductCertificateDAO productCertificateDao;

	@Override
	public List<ProductCertificate> findByProductId(Long productId) throws ServiceException  {
		ProductCertificateExample exa = new ProductCertificateExample();
		exa.createCriteria().andProductIdEqualTo(productId);
		exa.setOrderByClause(" FILE_TYPE ");

        try {
            return productCertificateDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void deleteProductCertificate(List<ProductCertificate> list) throws ServiceException {
        List<Long> pscIds = list.stream().map(ProductCertificate::getPscId)
                .collect(Collectors.toList());
        ProductCertificateExample exa = new ProductCertificateExample();
        exa.createCriteria().andPscIdIn(pscIds);

		try {
			productCertificateDao.deleteByExample(exa);
			for (ProductCertificate pc : list) {
				FileUploadUtils.deleteFile(ConfigurationUtil.getString("certificateFileViewPath") + pc.getFilePath());
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteByProductIdWithNotFile(Long productId) throws ServiceException  {
        ProductCertificateExample exa = new ProductCertificateExample();
        exa.createCriteria().andProductIdEqualTo(productId);

        try {
            productCertificateDao.deleteByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
