package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.ProductDraftRemoteService;
import com.kmzyc.product.remote.service.ProductRemoteService;
import com.kmzyc.supplier.common.ProductSkuComparator;
import com.kmzyc.supplier.dao.ProductMainDao;
import com.kmzyc.supplier.service.CategoryAttrValueService;
import com.kmzyc.supplier.service.OperationAttrService;
import com.kmzyc.supplier.service.ProductAttrService;
import com.kmzyc.supplier.service.ProductMainService;
import com.kmzyc.supplier.service.ProductSkuAttrService;
import com.kmzyc.supplier.service.ProductSkuService;
import com.kmzyc.supplier.service.ProductStockService;
import com.kmzyc.supplier.service.ShopProductCategoryService;
import com.kmzyc.supplier.util.CodeUtils;
import com.kmzyc.supplier.util.SkuAttrValueIdUtils;
import com.kmzyc.supplier.vo.ProductMainVo;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.CategoryAttrInputType;
import com.pltfm.app.enums.ProductAttrType;
import com.pltfm.app.fobject.SkuCheckAttr;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuAttr;

@Service("productMainService")
public class ProductMainServiceImpl implements ProductMainService {

    @Resource
    private ProductMainDao productMainDao;

    @Resource
    private ProductAttrService productAttrService;

    @Resource
    private CategoryAttrValueService categoryAttrValueService;

    @Resource
    private OperationAttrService operationAttrService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductSkuAttrService productSkuAttrService;

    @Resource
    private ProductStockService productStockService;

    @Resource
    private ShopProductCategoryService shopProductCategoryService;

    @Resource
    private ProductRemoteService productRemoteService;

    @Resource
    private ProductDraftRemoteService productDraftRemoteService;

    @Override
    public Pagination searchPage(Product product, String shopCategoryId, Pagination page) throws ServiceException {
        Map<String, Object> condition = new HashMap();
        if (StringUtils.isNotBlank(product.getName())) {
            condition.put("name", product.getName().trim());
        }
        if (StringUtils.isNotBlank(product.getProductTitle())) {
            condition.put("productTitle", product.getProductTitle().trim());
        }
        if (StringUtils.isNotBlank(product.getProductNo())) {
            condition.put("productNo", product.getProductNo().trim());
        }
        if (StringUtils.isNotEmpty(product.getSearchBrandName())) {
            condition.put("searchBrandName", product.getSearchBrandName().trim());
        }
        if (StringUtils.isNotBlank(shopCategoryId)) {
            condition.put("shopCategoryId", shopCategoryId);
        }
        condition.put("shopCode", product.getShopCode());
        condition.put("status", product.getStatus());
        page.setObjCondition(condition);
        try {
            productMainDao.findProductMainListByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    public Pagination queryRelationWithShopCategoryPage(Product product, Pagination page) throws ServiceException {
        Map<String, Object> condition = new HashMap();
        condition.put("shopCode", product.getShopCode());
        page.setObjCondition(condition);
        try {
            productMainDao.queryRelationWithShopCategoryByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

        return page;
    }

    @Override
    public Pagination queryUnRelationWithShopCategoryPage(Product product, Pagination page) throws ServiceException {
        Map<String, Object> condition = new HashMap();
        condition.put("shopCode", product.getShopCode());
        page.setObjCondition(condition);
        try {
            productMainDao.queryUnrelationWithShopCategoryByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    public ProductMainVo findProductMainVoByProductId(Long productId) throws ServiceException {
        try {
            return productMainDao.findProductMainVoByProductId(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductAttr> getProductAttrList(Long productId) throws ServiceException {
        ProductAttr productAttr = new ProductAttr();
        productAttr.setProductId(productId);
        return productAttrService.queryProductAttr(productAttr);
    }

    @Override
    public void setProductAttrValue(List<ProductAttr> productAttrList) throws ServiceException {
        for (ProductAttr productAttr : productAttrList) {
            //商品属性类型
            Integer productAttrType = productAttr.getProductAttrType();
            if (productAttrType.intValue() == ProductAttrType.CATEGORY.getType().intValue()) {
                //商品属性值
                String productAttrValue = productAttr.getProductAttrValue();
                if (StringUtils.isEmpty(productAttrValue)) {
                    continue;
                }

                //商品属性值输入类型  单选框", "多选框","下拉框"
                Integer inputType = productAttr.getInputType();
                if (CategoryAttrInputType.RADIO.getType().intValue() == inputType.intValue()
                        || CategoryAttrInputType.SELECT.getType().intValue() == inputType.intValue()) {
                    //设置单选框和下拉框的值
                    CategoryAttrValue categoryAttrValue = categoryAttrValueService.queryCategoryAttrValue(Long.parseLong(productAttrValue));
                    if (categoryAttrValue != null) {
                        productAttr.setProductAttrValue(categoryAttrValue.getCategoryAttrValue());
                    }
                } else if (CategoryAttrInputType.CHECKBOX.getType().intValue() == inputType.intValue()) {
                    String[] ids = productAttrValue.split(",");
                    ids = SkuAttrValueIdUtils.sort(ids);
                    //checkbox基本值
                    List<String> categoryAttrValueList = Arrays.stream(ids).filter(id -> id.indexOf("@") < 0).map(id -> {
                        CategoryAttrValue categoryAttrValue = categoryAttrValueService.queryCategoryAttrValue(Long.valueOf(id));
                        if (categoryAttrValue != null) {
                            return categoryAttrValue.getCategoryAttrValue();
                        } else {
                            return null;
                        }
                    }).collect(Collectors.toList());
                    //checkbox自定义值
                    categoryAttrValueList.addAll(Arrays.stream(ids).filter(id -> id.indexOf("@") >= 0).map(id -> {
                        int index = id.lastIndexOf("#");
                        if (index > 0) {
                            return id.substring(1, index);
                        } else {
                            return id.substring(1);
                        }
                    }).collect(Collectors.toList()));

                    productAttr.setProductAttrValue(StringUtils.join(categoryAttrValueList, ","));
                }
            } else if (productAttrType.intValue() == ProductAttrType.OPERATION.getType().intValue()) {
                OperationAttr operationAttr = operationAttrService.queryOperationAttr(productAttr.getRelateAttrId());
                if (operationAttr != null) {
                    productAttr.setProductAttrName(operationAttr.getOperationAttrName());
                }
            }
        }

    }

    @Override
    public List<ProductSku> getProductSkuAttrList(Long productId) throws ServiceException {
        try {
            ProductSku productSku = new ProductSku();
            productSku.setProductId(productId);
            List<ProductSku> productSkuList = productSkuService.queryProductSkuList(productSku);
            for (ProductSku sku : productSkuList) {
                ProductSkuAttr productSkuAttr = new ProductSkuAttr();
                productSkuAttr.setProductSkuId(sku.getProductSkuId());
                List<ProductSkuAttr> skuAttrList = productSkuAttrService.queryProductSkuAttrList(productSkuAttr);
                for (ProductSkuAttr skuAttr : skuAttrList) {
                    if (StringUtils.isNotEmpty(skuAttr.getNewAttr())) {
                        skuAttr.setCategoryAttrValue(skuAttr.getNewAttr());
                        continue;
                    }
                    CategoryAttrValue value = categoryAttrValueService.queryCategoryAttrValue(skuAttr.getCategoryAttrValueId());
                    if (value != null) {
                        skuAttr.setCategoryAttrValue(value.getCategoryAttrValue());
                    }
                }
                sku.setProductSkuAttrList(skuAttrList);
            }
            Collections.sort(productSkuList, new ProductSkuComparator());
            return productSkuList;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean productDownShelf(List<Product> productList) throws ServiceException {
        try {
            return productDraftRemoteService.downShelf(productList);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage productUpShelf(List<Product> productList) throws ServiceException {
        try {
            return productDraftRemoteService.productUpShelf(productList);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Product findProductById(Long id) throws ServiceException {
        try {
            return productMainDao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setCategoryAttrValue(List<ProductAttr> productAttrList) throws ServiceException {
        try {
            for (ProductAttr productAttr : productAttrList) {
                if (productAttr.getProductAttrType() == 1) {
                    if (CategoryAttrInputType.RADIO.getType().compareTo(productAttr.getInputType()) == 0
                            || CategoryAttrInputType.SELECT.getType().compareTo(productAttr.getInputType()) == 0) {
                        productAttr.setCategoryAttrValueList(categoryAttrValueService
                                .queryCategoryAttrValueList(productAttr.getRelateAttrId()));
                    } else if (CategoryAttrInputType.CHECKBOX.getType().compareTo(productAttr.getInputType()) == 0) {
                        productAttr.setCategoryAttrValueList(categoryAttrValueService
                                .queryCategoryAttrValueList(productAttr.getRelateAttrId()));
                        List<Long> list = new ArrayList();
                        if (StringUtils.isNotEmpty(productAttr.getProductAttrValue())) {
                            String[] ids = productAttr.getProductAttrValue().split(",");
                            for (String id : ids) {
                                if (id.indexOf("@") >= 0) {
                                    continue;
                                }

                                list.add(Long.parseLong(id));
                            }
                        }
                        productAttr.setCheckBoxIds(list);
                        productAttr.setOldChecks(StringUtils.join(list, ","));
                    }
                } else if (productAttr.getProductAttrType() == 3) {
                    OperationAttr operationAttr = operationAttrService.queryOperationAttr(productAttr.getRelateAttrId());
                    productAttr.setProductAttrName(operationAttr.getOperationAttrName());
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OperationAttr> getOperationAttrList() throws ServiceException {
        try {
            return operationAttrService.queryOperationAttrList();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setOperationAttrValue(Long productId, List<OperationAttr> operationAttrList) throws ServiceException {
        try {
            ProductAttr productAttr = new ProductAttr();
            productAttr.setProductId(productId);
            productAttr.setProductAttrType(3);
            List<ProductAttr> productAttrList = productAttrService.queryProductAttr(productAttr);

            for (ProductAttr attr : productAttrList) {
                boolean flag = true;
                for (OperationAttr operationAttr : operationAttrList) {
                    if (attr.getRelateAttrId().equals(operationAttr.getOperationAttrId())) {
                        operationAttr.setIsSelect(true);
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    OperationAttr ot = operationAttrService.queryOperationAttr(attr.getRelateAttrId());
                    ot.setIsSelect(true);
                    operationAttrList.add(ot);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String previewProductInfoPage(String productId) throws ServiceException {
        try {
            return productRemoteService.previewProductInfoPage(productId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int productUpdate(Long supplierId, Long loginId, Product product,
                             List<SkuCheckAttr> skuCheckAttrs, List<String> oldSkuCheckedId, String toDeleteSkuIds,
                             String shopCategoryIds) throws ServiceException {
        try {
            Long id = product.getId();

            // 更新产品主表
            productMainDao.updateObject(product);
            // 更新产品属性表
            productAttrService.updateCategoryAttrValue(product, product.getProductAttrList());
            // 更新SKU
            if (!StringUtils.isEmpty(toDeleteSkuIds)) {
                toDeleteSkuIds = toDeleteSkuIds.substring(0, toDeleteSkuIds.length() - 1);
            }
            updateSkuAttrValue(product, product.getProductNo(), skuCheckAttrs, oldSkuCheckedId, toDeleteSkuIds);

            productStockService.addStockForSupplier(supplierId, product.getProductSkus());

            productAttrService.updateDefinitionAttrValue(product, id, product.getProductAttrList());

            productAttrService.updateOperationAttrValue(product, id, product.getOperationAttrIds());

            // 更新店内分类
            if (StringUtils.isBlank(shopCategoryIds)) {
                shopProductCategoryService.deleteByProductId(id);
                return 0;
            }

            shopProductCategoryService.updateByProductId(id, StringUtils.split(shopCategoryIds, ","));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return 0;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private void updateSkuAttrValue(Product product, String productNo, List<SkuCheckAttr> skuCheckAttrs,
                                    List<String> oldSkuCheckedId, String toDeleteSkuIds) throws ServiceException {
        try {
            Boolean haveUpdateSku = true;
            if (CollectionUtils.isNotEmpty(product.getProductSkus())) {
                productSkuService.updateProductSkuList(product.getProductSkus());
                haveUpdateSku = false;
            }

            if (StringUtils.isNotEmpty(toDeleteSkuIds)) {
                List<Long> skuIdList = Lists.newArrayList(StringUtils.split(toDeleteSkuIds, ","))
                        .stream().map(skuId -> Long.valueOf(skuId))
                        .collect(Collectors.toList());
                productSkuService.deleteProductSkuByIds(skuIdList);
            }

            //有新增的sku或者没有老的sku也没新增sku同时还未修改过sku的时候会新增sku
            if (CollectionUtils.isNotEmpty(skuCheckAttrs)
                    || (CollectionUtils.isEmpty(oldSkuCheckedId) && CollectionUtils.isEmpty(skuCheckAttrs) && haveUpdateSku)) {
                insertProductSku(product, productNo, skuCheckAttrs);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private void insertProductSku(Product product, String productNo, List<SkuCheckAttr> skuCheckAttrs) throws ServiceException {
        try {
            if (skuCheckAttrs != null && skuCheckAttrs.size() > 0) {
                String sourceCode = productSkuService.findMaxSkuCodeByCateCode(productNo);
                String productSkuNo = sourceCode == null ? productNo + "00" : sourceCode;
                ProductSku productSku = null;
                ProductSkuAttr skuAttr = null;
                for (SkuCheckAttr str : skuCheckAttrs) {
                    productSku = new ProductSku();
                    productSkuNo = CodeUtils.getNewCode(productSkuNo);
                    productSku.setProduct(product);
                    productSku.setProductId(product.getId());
                    productSku.setProductSkuCode(productSkuNo);
                    productSku.setStatus(str.getStatus());
                    if (str.getMarkPrice() != null) {
                        productSku.setMarkPrice(str.getMarkPrice());
                    }
                    if (str.getPrice() != null) {
                        productSku.setPrice(str.getPrice());
                    }
                    if (str.getUnitWeight() != null) {
                        productSku.setUnitWeight(str.getUnitWeight());
                    }
                    if (str.getPvValue() != null) {
                        productSku.setPvValue(str.getPvValue());
                    }
                    if (StringUtils.isNotEmpty(str.getSellerSkuCode())) {
                        productSku.setSellerSkuCode(str.getSellerSkuCode());
                    }
                    if (str.getStock() != null) {
                        productSku.setStock(str.getStock());
                    }

                    productSkuService.insertProductSku(productSku);
                    String[] values = str.getSkuCheckedId().split(",");
                    for (String value : values) {
                        String[] tempStr = value.split(":");
                        skuAttr = new ProductSkuAttr();
                        skuAttr.setCategoryAttrId(Long.valueOf(tempStr[0]));
                        skuAttr.setCategoryAttrName(tempStr[1]);
                        if (tempStr[2].indexOf("@") < 0) {
                            skuAttr.setCategoryAttrValueId(Long.valueOf(tempStr[2]));
                        } else {
                            //新增规格格式：@新增规格value#新增规格下标
                            int index = tempStr[2].lastIndexOf("#");
                            if (index > 0) {
                                skuAttr.setNewAttr(tempStr[2].substring(1, index));
                                String idIndex = tempStr[2].substring(index + 1);
                                skuAttr.setCategoryAttrValueId(Long.parseLong(idIndex));
                            } else {
                                skuAttr.setNewAttr(tempStr[2].substring(1));
                            }
                        }
                        skuAttr.setStatus(str.getStatus());
                        skuAttr.setProductSkuId(productSku.getProductSkuId());
                        productSkuAttrService.insertProductSkuAttr(skuAttr);
                    }
                    product.getProductSkus().add(productSku);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductSku> findSingleProductAndSkusAndAttrValuesAndImages(Product product) throws ServiceException {
        try {
            return productMainDao.findSingleProductAndSkusAndAttrValues(product);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateProductBatch(List<Product> list) throws ServiceException {
        try {
            return productMainDao.updateBatch(list);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}