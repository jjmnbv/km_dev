package com.kmzyc.product.remote.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.SuppliersRemoteService;
import com.kmzyc.supplier.model.SupplierFare;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.pltfm.app.dao.CategoryDAO;
import com.pltfm.app.dao.ProductDao;
import com.pltfm.app.dao.SupplierFareDAO;
import com.pltfm.app.dao.SuppliersAvailableCategorysDAO;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.Product;

/**
 * 供应商相关接口
 * 
 * @author xgh
 * 
 */
@Service("suppliersRemoteService")
public class SuppliersRemoteServiceImpl implements SuppliersRemoteService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private SuppliersAvailableCategorysDAO suppliersAvailableCategorysDao;

	@Resource
	private CategoryDAO categoryDao;

	@Resource
	private ProductDao productDao;

	@Resource
	private SupplierFareDAO supplierFareDao;

	@Override
    public BigDecimal getCommissionRatio(Long productSkuId) throws ServiceException {
		BigDecimal commissionRatio = null;
		SuppliersAvailableCategorys suAvCa = new SuppliersAvailableCategorys();
		if (null == productSkuId) {
			return null;
		}
		try {
			Product product = productDao.selectBySkuId(productSkuId);// 根据skuId查询main信息
            if (product == null) {
                return null;
            }

			Category category = categoryDao.findCategoryId(product.getCategoryId());// 根据三级类目推出一级类目id
			if (null == category) {
				return null;
			}
			suAvCa.setCategoryId(category.getCategoryId());// 一级类目id
			suAvCa.setSupplierId(Long.parseLong(product.getShopCode()));// 供应商id
			// 根据供应商id,和类目id查询比例信息
			SuppliersAvailableCategorys newCate = suppliersAvailableCategorysDao.commissionRatioBySupplierIdAndCategoryId(suAvCa);
			if (newCate != null) {
				commissionRatio = newCate.getCommissionRatio();// 获的佣金比例
			}
		} catch (Exception e) {
			log.error("查询供应商佣金比例接口出现异常！" + e.getMessage(), e);
            throw new ServiceException(e);
		}
		return commissionRatio;
	}

	@Override
    public BigDecimal getSupplierFareInfo(Long supplierId, BigDecimal orderAmount) throws ServiceException {
		SupplierFare newSupplierFare = null;
		if (null == orderAmount || null == supplierId) {
			return null;
		}
		try {
			newSupplierFare = supplierFareDao.selectBySupplierId(supplierId);
			if (newSupplierFare == null || newSupplierFare.getFreePrice() == null) {
				return null;
			}
			if (orderAmount.compareTo(newSupplierFare.getFreePrice()) == 1
					|| orderAmount.compareTo(newSupplierFare.getFreePrice()) == 0) {
				return BigDecimal.valueOf(0);
			} else {
				return newSupplierFare.getFirstHeavyFreight();
			}
		} catch (Exception e) {
			log.error("根据供应商id查询运费接口出现异常！" + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	@Override
    public List<SupplierFare> queryFreeFareList(List<Long> sid) throws ServiceException {
		List<SupplierFare> fareList = null;
		if (null != sid && !sid.isEmpty()) {
			try {
				fareList = supplierFareDao.selectSupplierFareInfoList(sid);
			} catch (Exception e) {
				log.error("根据商家ID查询免邮运费集合出现异常！" + e.getMessage(), e);
                throw new ServiceException(e);
			}
		}
		return fareList;
	}
}
