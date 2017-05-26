package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.StockRemoteService;
import com.kmzyc.supplier.dao.ProductStockDao;
import com.kmzyc.supplier.service.ProductStockService;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductStock;

/**
 * @author Administrator
 */
@Service("productStockService")
public class ProductStockServiceImpl implements ProductStockService {

    @Resource
    private ProductStockDao productStockDao;

    @Resource
    private StockRemoteService stockRemoteService;

    @Override
    public Pagination searchPage(ProductStock queryPara, Pagination page) throws ServiceException {
        Map<String, Object> condition = new HashMap<String, Object>();
        //设置按sku编码查询
        if (StringUtils.isNotBlank(queryPara.getSkuAttValue())) {
            condition.put("skuAttValue", queryPara.getSkuAttValue().trim());
        }
        //设置按产品编码查询
        if (StringUtils.isNotBlank(queryPara.getProductNo())) {
            condition.put("productNo", queryPara.getProductNo().trim());
        }
        //设置按产品名称查询
        if (null != queryPara.getProduct() && StringUtils.isNotBlank(queryPara.getProduct().getProductTitle())) {
            condition.put("productTitle", queryPara.getProduct().getProductTitle().trim());
        }
        //是否库存预警查询
        if (queryPara.getEndQuantity() != null) {
            condition.put("endQuantity", queryPara.getEndQuantity());
        }
        //按仓库Id查询,如果默认的值为-1,则是默认不按仓库查询
        // if(StringUtils.isNotBlank(String.valueOf(queryPara.getWarehouseId()))){
        // condition.put("warehouseId", queryPara.getWarehouseId());
        // }
        // 限定只查当前供应商的
        condition.put("shopCode", queryPara.getProduct().getShopCode());
        page.setObjCondition(condition);
        try {
            productStockDao.findProductStockListByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveProductStock(ProductStock para) throws ServiceException {
        try {
            productStockDao.insert(para);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateProductStock(ProductStock para) throws ServiceException {
        try {
            return productStockDao.update(para);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductStock queryByStockId(String stockId) throws ServiceException {
        try {
            return productStockDao.queryProductStockByStockId(stockId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int isExistSkuStock(String warehouseId, String skuValue) throws ServiceException {
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("warehouseId", warehouseId);
        condition.put("skuAttValue", skuValue);
        try {
            return productStockDao.queryIsExistSkuStockBySkuNo(condition);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage addStockForSupplier(Long supplierId, List<ProductSku> productSkuList) throws ServiceException {
        try {
            return stockRemoteService.addStockForSupplier(supplierId, productSkuList);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}