package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.ProductRemoteService;
import com.kmzyc.supplier.dao.ProductSkuDAO;
import com.kmzyc.supplier.service.ProductSkuService;
import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageExample;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuExample;
import com.pltfm.app.vobject.ProductSkuExample.Criteria;
import com.pltfm.app.vobject.ViewProductSku;
import com.pltfm.app.vobject.ViewProductSkuExample;;

/**
 * 产品SKU业务逻辑类
 */
@Service("productSkuService")
public class ProductSkuServiceImpl implements ProductSkuService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ProductSkuDAO productSkuDAO;

    @Resource
    private ProductRemoteService productRemoteService;

    @Override
    public List<ProductSku> queryProductSkuList(ProductSku productSku) throws ServiceException {
        try {
            ProductSkuExample example = new ProductSkuExample();
            Criteria c = example.createCriteria();
            c.andProductIdEqualTo(productSku.getProductId());
            if (StringUtils.isNotEmpty(productSku.getStatus())) {
                c.andStatusEqualTo(productSku.getStatus());
            }
            example.setOrderByClause(" product_sku_code ");
            return productSkuDAO.selectByExample(example);
        } catch (SQLException e) {
            logger.error("查询产品SKU列表失败:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Pagination searchPage(Long supplierId, ViewProductSku viewProductSku, Pagination page)
            throws ServiceException {
        Map<String, Object> condition = new HashMap();
        condition.put("supplierId", supplierId.toString());
        if (StringUtils.isNotBlank(viewProductSku.getProcuctName())) {
            condition.put("productName", viewProductSku.getProcuctName().trim());
        }
        if (StringUtils.isNotBlank(viewProductSku.getProductNo())) {
            condition.put("productNo", viewProductSku.getProductNo().trim());
        }
        if (StringUtils.isNotBlank(viewProductSku.getProductSkuCode())) {
            condition.put("productSkuCode", viewProductSku.getProductSkuCode().trim());
        }
        if (StringUtils.isNotEmpty(viewProductSku.getSkuStatus())) {
            condition.put("skuStatus", viewProductSku.getSkuStatus().trim());
        }
        if (viewProductSku.getCategoryId() != null) {
            condition.put("categoryId", viewProductSku.getCategoryId());
        }
        if (viewProductSku.getMCategoryId() != null) {
            condition.put("mCategoryId", viewProductSku.getMCategoryId());
        }
        if (viewProductSku.getBCategoryId() != null) {
            condition.put("bCategoryId", viewProductSku.getBCategoryId());
        }
        condition.put("status", viewProductSku.getStatus());
        page.setObjCondition(condition);
        try {
            productSkuDAO.findProductSkuListByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    public Pagination searchEnableStockByPage(Long supplierId, ViewProductSku viewProductSku, Pagination page)
            throws ServiceException {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("supplierId", supplierId.toString());
        if (StringUtils.isNotBlank(viewProductSku.getProductTitle())) {
            condition.put("productTitle", viewProductSku.getProductTitle().trim());
        }
        if (StringUtils.isNotBlank(viewProductSku.getProductSkuCode())) {
            condition.put("productSkuCode", viewProductSku.getProductSkuCode().trim());
        }
        page.setObjCondition(condition);
        try {
            productSkuDAO.findCanEnalbeStockProductSkuListByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    public List<ProductImage> findAllBySkuId(Long productSkuId) throws ServiceException {
        ProductImageExample exa = new ProductImageExample();
        exa.createCriteria().andSkuIdEqualTo(productSkuId);
        exa.setOrderByClause(" is_default,sortno ");
        try {
            return productSkuDAO.selectByExampleForImages(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ViewProductSku> findProductAndSkuAttrByProductId(Long productId) throws ServiceException {
        ViewProductSkuExample example = new ViewProductSkuExample();
        example.createCriteria().andProductIdEqualTo(productId).andSkuStatusEqualTo("1");
        try {
            return productSkuDAO.selectSKUAttrByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductSku> findIsValidProductSkusByProductId(Long productId) throws ServiceException {
        ProductSkuExample exa = new ProductSkuExample();
        exa.createCriteria().andProductIdEqualTo(productId).andStatusEqualTo(IsValidStatus.VALID.getStatus());
        try {
            return productSkuDAO.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Pagination searchForFreightPage(Long supplierId, ViewProductSku viewProductSku, Pagination page)
            throws ServiceException {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("supplierId", supplierId.toString());
        if (StringUtils.isNotBlank(viewProductSku.getProcuctName())) {
            condition.put("productName", viewProductSku.getProcuctName().trim());
        }
        if (StringUtils.isNotBlank(viewProductSku.getProductTitle())) {
            condition.put("productTitle", viewProductSku.getProductTitle().trim());
        }
        if (StringUtils.isNotBlank(viewProductSku.getProductNo())) {
            condition.put("productNo", viewProductSku.getProductNo().trim());
        }
        if (StringUtils.isNotBlank(viewProductSku.getProductSkuCode())) {
            condition.put("productSkuCode", viewProductSku.getProductSkuCode().trim());
        }
        if (StringUtils.isNotEmpty(viewProductSku.getSkuStatus())) {
            condition.put("skuStatus", viewProductSku.getSkuStatus().trim());
        }
        condition.put("status", viewProductSku.getStatus());
        page.setObjCondition(condition);
        try {
            productSkuDAO.findProductSkuListForFreightByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    public ViewProductSku findSingleSkuAndAttr(Long productSkuId) throws ServiceException {
        try {
            return productSkuDAO.findSingleSku(productSkuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateProductSku(ProductSku productSku) throws ServiceException {
        try {
            return productSkuDAO.updateProductSku(productSku);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void upShelfForSku(ProductSku productSku) throws ServiceException {
        List<ProductSku> skuList = new ArrayList<>();
        skuList.add(productSku);
        try {
            productRemoteService.upShelfForSku(skuList);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Long, Set<String>> findAndChangeSkuNewAttr(Long productId) throws ServiceException {
        try {
            List<ProductSkuAttr> list = productSkuDAO.findSkuNewAttr(productId);
            //兼容历史遗留重复数据,更改使用Set
            Map<Long, Set<String>> map = new LinkedHashMap<Long, Set<String>>();
            for (ProductSkuAttr skuAttr : list) {
                if (!map.containsKey(skuAttr.getCategoryAttrId())) {
                    map.put(skuAttr.getCategoryAttrId(), new LinkedHashSet<>());
                }
                map.get(skuAttr.getCategoryAttrId()).add(skuAttr.getNewAttr());
            }
            return map;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateProductSkuList(List<ProductSku> list) throws ServiceException {
        int result = 0;
        try {
            result = productSkuDAO.updateProductSkuList(list);
            if (result == 0) throw new ServiceException("");
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int deleteProductSkuByIds(List<Long> productSkuIds) throws ServiceException {
        int result = 0;
        try {
            result = productSkuDAO.deleteProductSkuByIds(productSkuIds);
            if (result == 0) throw new ServiceException("");
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public String findMaxSkuCodeByCateCode(String cateCode) throws ServiceException {
        try {
            return productSkuDAO.findMaxSkuCodeByCateCode(cateCode);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void insertProductSku(ProductSku productSku) throws ServiceException {
        try {
            productSkuDAO.insertProductSku(productSku);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
