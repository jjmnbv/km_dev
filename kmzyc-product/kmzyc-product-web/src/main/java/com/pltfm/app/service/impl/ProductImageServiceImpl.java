package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.zkconfig.ConfigurationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.ProductImageDAO;
import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageExample;
import com.pltfm.app.vobject.ProductSku;
import com.kmzyc.commons.exception.ServiceException;


/**
 * 产品图片业务实现
 * @author tanyunxing
 *
 */
@Service("productImageService")
public class ProductImageServiceImpl implements ProductImageService {

	@Resource
	private ProductImageDAO productImageDao;

	@Override
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public Long saveImage(ProductImage image) throws ServiceException  {
		try {
			return productImageDao.insert(image);
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public void deleteImageById(Long imageId) throws ServiceException  {
		try {
			productImageDao.deleteByPrimaryKey(imageId);
		} catch (Exception e) {
            throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public void modifyImageDefault(Long productSkuId, Long imageId) throws ServiceException {
        ProductImage record = new ProductImage();
        ProductImageExample exa = new ProductImageExample();
        record.setIsDefault("0");
        exa.createCriteria().andImageIdEqualTo(imageId);

		try {
			productImageDao.updateByExampleSelective(record, exa);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<ProductImage> findAllBySkuId(Long productSkuId) throws ServiceException  {
		ProductImageExample exa = new ProductImageExample();
		exa.createCriteria().andSkuIdEqualTo(productSkuId);
		exa.setOrderByClause(" is_default,sortno ");

        try {
            return productImageDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
	@Override
	public List<ProductImage> findAllImage() throws ServiceException  {
		ProductImageExample exa = new ProductImageExample();
		exa.createCriteria().andImgPath8IsNull();
		exa.setOrderByClause(" sku_id,is_default,sortno ");

        try {
            return productImageDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public int findMaxSortNoBySkuId(Long productSkuId) throws ServiceException  {
        try {
            return productImageDao.findMaxSortNoByProductSkuId(productSkuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public boolean updateProductImageBatch(List<ProductImage> list) throws ServiceException {
		try {
			productImageDao.updateProductImage(list);
		} catch (SQLException e) {
            throw new ServiceException(e);
		}
		return true;
	}

	@Override
	public boolean isLimitImage(Long productSkuId) throws ServiceException  {
        try {
            return productImageDao.findCountsByProductSkuId(productSkuId)>=10;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
    public ResultMessage deleteImagesBySkuId(ProductSku productSku,ResultMessage message) throws ServiceException  {
		try {
			List<ProductImage> imageList = productSku.getProductSkuImages();
			if(imageList!=null && !imageList.isEmpty()){
				String ap_path = ConfigurationUtil.getString("pictureUploadPath");
				for(ProductImage pi : imageList){
					if (pi.getImgPath() != null && !pi.getImgPath().isEmpty()) {
						FileUploadUtils.deleteFile(ap_path + pi.getImgPath());
					}
					if (pi.getImgPath1() != null && !pi.getImgPath1().isEmpty()) {
						FileUploadUtils.deleteFile(ap_path + pi.getImgPath1());
					}
					if (pi.getImgPath2() != null && !pi.getImgPath2().isEmpty()) {
						FileUploadUtils.deleteFile(ap_path + pi.getImgPath2());
					}
					if (pi.getImgPath3() != null && !pi.getImgPath3().isEmpty()) {
						FileUploadUtils.deleteFile(ap_path + pi.getImgPath3());
					}
					if (pi.getImgPath4() != null && !pi.getImgPath4().isEmpty()) {
						FileUploadUtils.deleteFile(ap_path + pi.getImgPath4());
					}
					if (pi.getImgPath5() != null && !pi.getImgPath5().isEmpty()) {
						FileUploadUtils.deleteFile(ap_path + pi.getImgPath5());
					}
					if (pi.getImgPath6() != null && !pi.getImgPath6().isEmpty()) {
						FileUploadUtils.deleteFile(ap_path + pi.getImgPath6());
					}
					if (pi.getImgPath7() != null && !pi.getImgPath7().isEmpty()) {
						FileUploadUtils.deleteFile(ap_path + pi.getImgPath7());
					}
					if (pi.getImgPath8() != null && !pi.getImgPath8().isEmpty()) {
						FileUploadUtils.deleteFile(ap_path + pi.getImgPath8());
					}
					if (pi.getImgPath9() != null && !pi.getImgPath9().isEmpty()) {
						FileUploadUtils.deleteFile(ap_path + pi.getImgPath9());
					}
					if (pi.getImgPath10() != null && !pi.getImgPath10().isEmpty()) {
						FileUploadUtils.deleteFile(ap_path + pi.getImgPath10());
					}
				}
				
				int count = productImageDao.deleteImagesBySkuId(productSku.getProductSkuId());
				if(count<1){
					message.setIsSuccess(false);
					message.setMessage("删除产品图片失败!");
					return message;
				}
			}
		} catch (SQLException e) {
			message.setIsSuccess(false);
			message.setMessage("系统错误，删除产品图片失败!");
			return message;
		}
		
		return message;
	}

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
    public void updateImageById(ProductImage image) throws ServiceException  {
        try {
            productImageDao.updateByPrimaryKeySelective(image);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
}
