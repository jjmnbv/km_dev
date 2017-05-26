package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kmzyc.cms.remote.service.DetailPublishService;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.ProductDao;
import com.pltfm.app.dao.ProductDraftDAO;
import com.pltfm.app.enums.DraftType;
import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.enums.OperationAttrs;
import com.pltfm.app.enums.ProductAttrType;
import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.enums.ProductType;
import com.pltfm.app.fobject.SkuCheckAttr;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.service.OperationAttrService;
import com.pltfm.app.service.ProductAttrDraftService;
import com.pltfm.app.service.ProductAttrService;
import com.pltfm.app.service.ProductCertificateDraftService;
import com.pltfm.app.service.ProductCertificateService;
import com.pltfm.app.service.ProductDraftService;
import com.pltfm.app.service.ProductImageDraftService;
import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductSkuAttrDraftService;
import com.pltfm.app.service.ProductSkuAttrService;
import com.pltfm.app.service.ProductSkuDraftService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ProductStockService;
import com.pltfm.app.service.ShopProductCategoryService;
import com.pltfm.app.util.CodeUtils;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductCertificateDraft;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductDraftExample;
import com.pltfm.app.vobject.ProductDraftExample.Criteria;
import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuAttrDraft;
import com.pltfm.app.vobject.ProductSkuDraft;
import com.pltfm.app.vobject.ProductStock;

@Service("productDraftService")
public class ProductDraftServiceImpl implements ProductDraftService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ProductAttrDraftService productAttrDraftService;

    @Resource
    private OperationAttrService operationAttrService;

    @Resource
    private ProductSkuDraftService productSkuDraftService;

    @Resource
    private ProductSkuAttrDraftService productSkuAttrDraftService;

    @Resource
    private ProductImageDraftService productImageDraftService;

    @Resource
    protected CategoryService categoryService;

    @Resource
    private ProductService productService;

    @Resource
    private ProductStockService productStockService;

    @Resource
    private ProductAttrService productAttrService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductImageService productImageService;

    @Resource
    private ProductSkuAttrService productSkuAttrService;

    @Resource
    private ProductCertificateDraftService productCertificateDraftService;
    
    @Resource
    private ProductCertificateService productCertificateService;
    
    @Resource
    private ShopProductCategoryService shopProductCategoryService;

    @Resource
    private DetailPublishService detailPublishService;

    @Resource
    private ProductDao productDao;

    @Resource
    private ProductDraftDAO productDraftDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long insertProduct(ProductDraft productDraft, List<SkuCheckAttr> skuCheckAttrs)
            throws ServiceException {
        Long productId = null;
        log.info("开始保存产品。");
        try {

            // 保存产品基本信息
            productId = productDraftDao.insert(productDraft);
            List<List<ProductSkuAttrDraft>> allProductSkuAttrList = new ArrayList<>();

            // 保存产品类目属性
            insertCategoryAttr(productDraft, productDraft.getCategoryAttrList(), allProductSkuAttrList);

            // 生成SKU
            insertProductSku(productDraft, null, skuCheckAttrs);

            // 产品自定义属性
            insertDefinitionAttr(productDraft, productDraft.getProductAttrDraftList());

            // 产品运营属性
            insertOperationAttr(productDraft, productDraft.getOperationAttrIds());

            List<ProductCertificateDraft> certificateList = productDraft.getCerfificateList();
            if (CollectionUtils.isNotEmpty(certificateList)) {
                for (ProductCertificateDraft pcd : certificateList) {
                    pcd.setProductId(productId);
                }
                productCertificateDraftService.batchInsertDraft(certificateList);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        return productId;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private void insertCategoryAttr(ProductDraft productDraft, List<CategoryAttr> categoryAttrList,
            List<List<ProductSkuAttrDraft>> skuAttrDraftList) throws ServiceException {
        Long productId = productDraft.getProductId();
        log.info("开始保存产品id={}的类目属性.", productId);

        try {
            if (categoryAttrList != null && categoryAttrList.size() > 0) {
                for (CategoryAttr categoryAttr : categoryAttrList) {
                    ProductAttrDraft productAttrDraft = new ProductAttrDraft();
                    productAttrDraft.setProductId(productId);
                    productAttrDraft.setProductAttrType(ProductAttrType.CATEGORY.getType().shortValue());
                    productAttrDraft.setRelateAttrId(categoryAttr.getCategoryAttrId());
                    productAttrDraft.setIsNav(categoryAttr.getIsNav().shortValue());
                    productAttrDraft.setInputType(categoryAttr.getInputType().shortValue());
                    productAttrDraft.setIsReq(categoryAttr.getIsReq().shortValue());
                    productAttrDraft.setIsSku(categoryAttr.getIsSku().shortValue());
                    productAttrDraft.setProductAttrName(categoryAttr.getCategoryAttrName());
                    productAttrDraft.setOpType(productDraft.getOpType());
                    String[] values = categoryAttr.getCategoryAttrValues();
                    if (values != null && values.length > 0) {
                        boolean isSku = false;
                        long i = 0;
                        List<ProductSkuAttrDraft> productSkuAttrDraftList = null;
                        if (categoryAttr.getIsSku() == 1) {
                            isSku = true;
                            productSkuAttrDraftList = new ArrayList<ProductSkuAttrDraft>();
                        }
                        StringBuffer sb = new StringBuffer();
                        for (String value : values) {
                            sb.append(",").append(value);
                            if (isSku) {
                                ProductSkuAttrDraft tempSkuAttrDraft = new ProductSkuAttrDraft();
                                tempSkuAttrDraft.setIsNewAttrValue(1);
                                tempSkuAttrDraft.setProductSkuId(++i);
                                tempSkuAttrDraft.setCategoryAttrName(categoryAttr.getCategoryAttrName());
                                tempSkuAttrDraft.setCategoryAttrId(categoryAttr.getCategoryAttrId());

                                if (value.indexOf("@") < 0) {
                                    tempSkuAttrDraft.setCategoryAttrValueId(Long.valueOf(value));
                                } else {
                                    tempSkuAttrDraft.setNewAttr(value.substring(1));
                                }
                                productSkuAttrDraftList.add(tempSkuAttrDraft);
                            }
                        }
                        if (productSkuAttrDraftList != null && productSkuAttrDraftList.size() > 0) {
                            skuAttrDraftList.add(productSkuAttrDraftList);
                        }
                        productAttrDraft.setProductAttrValue(sb.substring(1).toString());
                        productAttrDraftService.addProductAttrDraft(productAttrDraft);
                    } else {
                        productAttrDraftService.addProductAttrDraft(productAttrDraft);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private void insertProductSku(ProductDraft productDraft, String productNo, List<SkuCheckAttr> skuCheckAttrs)
            throws ServiceException {
        Long productId = productDraft.getProductId();
        log.info("开始保存产品id={}的sku.", productId);

        try {
            // 合作方收益金额
            Double costIncomeRatio = 0.00;
            if (skuCheckAttrs != null && skuCheckAttrs.size() > 0) {
                ProductSkuDraft productSkuDraft = null;
                for (SkuCheckAttr str : skuCheckAttrs) {
                    productSkuDraft = new ProductSkuDraft();
                    productSkuDraft.setProductId(productId);
                    productSkuDraft.setStatus(str.getStatus());
                    productSkuDraft.setOpType(productDraft.getOpType());
                    if (str.getMarkPrice() != null) {
                        productSkuDraft.setMarkPrice(str.getMarkPrice());
                    }
                    if (str.getPrice() != null) {
                        productSkuDraft.setPrice(str.getPrice());
                    }
                    if (str.getUnitWeight() != null) {
                        productSkuDraft.setUnitWeight(str.getUnitWeight());
                    }
                    if (str.getPvValue() != null) {
                        productSkuDraft.setPvValue(str.getPvValue());
                    }
                    if (StringUtils.isNotEmpty(str.getSellerSkuCode())) {
                        productSkuDraft.setSellerSkuCode(str.getSellerSkuCode());
                    }
                    if (str.getStock() != null) {
                        productSkuDraft.setStock(str.getStock());
                    }
                    productSkuDraft.setCostIncomeRatio(costIncomeRatio);
                    productSkuDraftService.addProductSkuDraft(productSkuDraft);
                    String[] values = str.getSkuCheckedId().split(",");
                    for (String value : values) {
                        String[] tempStr = value.split(":");
                        ProductSkuAttrDraft skuAttrDraft = new ProductSkuAttrDraft();
                        skuAttrDraft.setCategoryAttrId(Long.valueOf(tempStr[0]));
                        skuAttrDraft.setCategoryAttrName(tempStr[1]);
                        // skuAttrDraft.setCategoryAttrValueId(Long.valueOf(tempStr[2]));
                        if (tempStr[2].indexOf("@") < 0) {
                            skuAttrDraft.setCategoryAttrValueId(Long.valueOf(tempStr[2]));
                        } else {
                            // 新增规格格式：@新增规格value#新增规格下标
                            int index = tempStr[2].lastIndexOf("#");
                            if (index > 0) {
                                skuAttrDraft.setNewAttr(tempStr[2].substring(1, index));
                                String idIndex = tempStr[2].substring(index + 1);
                                skuAttrDraft.setCategoryAttrValueId(Long.parseLong(idIndex));
                            } else {
                                skuAttrDraft.setNewAttr(tempStr[2].substring(1));
                            }
                        }
                        skuAttrDraft.setStatus(str.getStatus());
                        skuAttrDraft.setProductSkuId(productSkuDraft.getProductSkuId());
                        productSkuAttrDraftService.addProductSkuAttrDraft(skuAttrDraft);
                    }
                }
            } else {
                ProductSkuDraft productSkuDraft = new ProductSkuDraft();
                productSkuDraft.setProductId(productDraft.getProductId());
                if (productDraft.getPrice() != null) {
                    productSkuDraft.setPrice(productDraft.getPrice().doubleValue());
                }
                if (productDraft.getMarketPrice() != null) {
                    productSkuDraft.setMarkPrice(productDraft.getMarketPrice().doubleValue());
                }
                if (productDraft.getUnitWeight() != null) {
                    productSkuDraft.setUnitWeight(productDraft.getUnitWeight().doubleValue());
                }
                if (productDraft.getStock() != null) {
                    productSkuDraft.setStock(productDraft.getStock());
                }
                if (StringUtils.isNotBlank(productDraft.getSellerSkuCode())) {
                    productSkuDraft.setSellerSkuCode(productDraft.getSellerSkuCode());
                }
                if (productDraft.getPvValue() != null) {
                    productSkuDraft.setPvValue(productDraft.getPvValue().doubleValue());
                }
                productSkuDraft.setStatus(IsValidStatus.VALID.getStatus());
                productSkuDraft.setOpType(productDraft.getOpType());
                productSkuDraft.setCostIncomeRatio(costIncomeRatio);
                productSkuDraftService.addProductSkuDraft(productSkuDraft);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private void insertDefinitionAttr(ProductDraft productDraft, List<ProductAttrDraft> productAttrList)
            throws ServiceException  {
        Long productId = productDraft.getProductId();
        log.info("开始保存产品id={}的自定义属性.", productId);

        try {
            if (productAttrList != null && productAttrList.size() > 0) {
                for (ProductAttrDraft productAttrDraft : productAttrList) {
                    productAttrDraft.setProductId(productId);
                    productAttrDraft.setOpType(productDraft.getOpType());
                    productAttrDraft.setProductAttrType(ProductAttrType.DEFINITION.getType().shortValue());
                    productAttrDraftService.addProductAttrDraft(productAttrDraft);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private void insertOperationAttr(ProductDraft productDraft, Long[] operationAttrIds) throws ServiceException {
        Long productId = productDraft.getProductId();
        log.info("开始保存产品id={}的运营属性.", productId);

        try {
            if (operationAttrIds != null && operationAttrIds.length > 0) {
                for (Long operationAttrId : operationAttrIds) {
                    ProductAttrDraft productAttrDraft = new ProductAttrDraft();
                    productAttrDraft.setProductId(productId);
                    productAttrDraft.setProductAttrType(ProductAttrType.OPERATION.getType().shortValue());
                    productAttrDraft.setRelateAttrId(operationAttrId);
                    OperationAttr operationAttr = operationAttrService.queryOperationAttr(operationAttrId);
                    productAttrDraft.setProductAttrName(operationAttr.getOperationAttrName());
                    productAttrDraft.setProductAttrValue(operationAttr.getOperationAttrId() .toString());
                    productAttrDraft.setIsNav(operationAttr.getIsNav().shortValue());
                    productAttrDraft.setOpType(productDraft.getOpType());
                    productAttrDraftService.addProductAttrDraft(productAttrDraft);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void searchPage(Page page, ProductDraft product, String type) throws ServiceException  {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProductDraftExample example = new ProductDraftExample();
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(product.getProductTitle()))
            criteria.andProductTitleLike("%" + product.getProductTitle().trim() + "%");
        if (StringUtils.isNotBlank(product.getKeyword()))
            criteria.andKeywordLike("%" + product.getKeyword().trim() + "%");
        if (StringUtils.isNotBlank(product.getProductNo()))
            criteria.andProductNoLike("%" + product.getProductNo().trim() + "%");
        if (product.getCategoryId() != null && product.getCategoryId() != 0) // 产品所属类目
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        if (product.getMCategoryId() != null && product.getCategoryId() == null)
            example.setmCategoryId(product.getMCategoryId());
        if (product.getBCategoryId() != null && product.getMCategoryId() == null)
            example.setbCategoryId(product.getBCategoryId());
        if (StringUtils.isNotBlank(product.getStatus())) // 产品状态
            criteria.andStatusEqualTo(product.getStatus());
        if (product.getBrandId() != null && product.getBrandId() != 0)// 品牌
            criteria.andBrandIdEqualTo(product.getBrandId());
        if (CollectionUtils.isNotEmpty(product.getOpTypes())) {
            criteria.andOpTypeIn(product.getOpTypes());
        }
        if (StringUtils.isNotEmpty(product.getPriceStatus())) {
            criteria.andPriceStatusEqualTo(product.getPriceStatus());
        }
        if (StringUtils.isNotEmpty(product.getImageStatus())) {
            criteria.andImageStatusEqualTo(product.getImageStatus());
        }
        // 品牌模糊查询
        if (StringUtils.isNotBlank(product.getSearchBrandName()))
            example.setSearchBrandName(product.getSearchBrandName());
        // 所属商家模糊查询
        if (StringUtils.isNotBlank(product.getSupplierNameForSearch())) {
            // 若是选择康美自营
            if ("康美自营".equals(product.getSupplierNameForSearch())) {
                criteria.andShopCodeEqualTo("221");
            } else {
                example.setSupplierNameForSearch(product.getSupplierNameForSearch());
            }
        }
        // 审核时需要查询的商家类型
        Integer supplyType = product.getSupplyType();
        if (supplyType != null && supplyType != 0) {
            criteria.andSupplyType(supplyType);
        }
        // 审核时 提交审核时间查询
        if (StringUtils.isNotBlank(product.getBeforeToCheckTime())) {
            criteria.andToCheckTimeGreaterThan(Timestamp.valueOf(product.getBeforeToCheckTime()));
        }
        if (StringUtils.isNotBlank(product.getAfterToCheckTime())) {
            criteria.andToCheckTimeLessThan(Timestamp.valueOf(product.getAfterToCheckTime()));
        }
        // 产品类型过滤
        if (product.getProductType() != null
                && StringUtils.isNotBlank(product.getProductType().toString())) {
            criteria.andProductTypeEqualTo(product.getProductType());
        }
        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            example.setOrderByClause(" UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            example.setOrderByClause(" ARCHIVE_TIME ");
        } else if ("audit".equals(type)) {// 审核列表已提交审核时间降序
            example.setOrderByClause(" TO_CHECK_TIME DESC ");
        } else {
            example.setOrderByClause(" PRODUCT_ID DESC ");
        }

        try {
            int count = productDraftDao.countByExampleByUser(example);
            List<ProductDraft> productList = productDraftDao.selectByExampleByUser(example, skip, max);
            productList.stream().filter(pd -> StringUtils.isNotBlank(pd.getProductTitle())).forEach(pd -> {
                pd.setProductTitle(pd.getProductTitle().replaceAll(" ", "&nbsp;"));
            });
            page.setDataList(productList);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void searchPageByUserForAudit(Page page, ProductDraft product, String type) throws ServiceException  {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProductDraftExample pe = new ProductDraftExample();
        Criteria criteria = pe.createCriteria();
        if (StringUtils.isNotBlank(product.getProductTitle()))
            criteria.andProductTitleLike("%" + product.getProductTitle().trim() + "%");
        if (StringUtils.isNotBlank(product.getKeyword()))
            criteria.andKeywordLike("%" + product.getKeyword().trim() + "%");
        if (StringUtils.isNotBlank(product.getProductNo()))
            criteria.andProductNoLike("%" + product.getProductNo().trim() + "%");
        if (product.getCategoryId() != null && product.getCategoryId() != 0) // 产品所属类目
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        if (product.getMCategoryId() != null && product.getCategoryId() == null)
            pe.setmCategoryId(product.getMCategoryId());
        if (product.getBCategoryId() != null && product.getMCategoryId() == null)
            pe.setbCategoryId(product.getBCategoryId());
        if (StringUtils.isNotBlank(product.getStatus())) // 产品状态
            criteria.andStatusEqualTo(product.getStatus());
        if (product.getBrandId() != null && product.getBrandId() != 0)// 品牌
            criteria.andBrandIdEqualTo(product.getBrandId());
        if (CollectionUtils.isNotEmpty(product.getOpTypes())) {
            criteria.andOpTypeIn(product.getOpTypes());
        }
        if (StringUtils.isNotEmpty(product.getPriceStatus())) {
            criteria.andPriceStatusEqualTo(product.getPriceStatus());
        }
        if (StringUtils.isNotEmpty(product.getImageStatus())) {
            criteria.andImageStatusEqualTo(product.getImageStatus());
        }
        // 品牌模糊查询
        if (StringUtils.isNotBlank(product.getSearchBrandName()))
            pe.setSearchBrandName(product.getSearchBrandName());
        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause(" UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause(" ARCHIVE_TIME ");
        } else {
            pe.setOrderByClause(" PRODUCT_ID DESC ");
        }
        pe.setUserId(product.getUserId().toString());

        try {
            int count = productDraftDao.countByExampleByUser(pe);
            List<ProductDraft> productList = productDraftDao.selectByExampleByUserForAudit(pe, skip, max);
            productList.stream().filter(pd -> StringUtils.isNotBlank(pd.getProductTitle())).forEach(pd -> {
                pd.setProductTitle(pd.getProductTitle().replaceAll(" ", "&nbsp;"));
            });
            page.setDataList(productList);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductDraft findSingleProductAndSkuAndAttrValues(ProductDraft productDraft) throws ServiceException  {
        try {
            return productDraftDao.findSingleProduct(productDraft);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateObject(ProductDraft product) throws ServiceException {
        try {
            productDraftDao.updateByPrimaryKeySelective(product);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductDraft findByProductId(Long productId) throws ServiceException  {
        try {
            return productDraftDao.selectByPrimaryKey(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductDraft findByProductIdWithOutClob(Long productId) throws ServiceException  {
        ProductDraftExample exa = new ProductDraftExample();
        exa.createCriteria().andProductIdEqualTo(productId);

        try {
            List<ProductDraft> list = productDraftDao.selectByExampleWithoutBLOBs(exa);
            if (list != null && list.size() > 0) {
                return list.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    // 检查运营属性
    private boolean checkOperationAttr(ProductDraft productDraft) throws ServiceException  {
        ProductAttrDraft productAttrDraft = new ProductAttrDraft();
        productAttrDraft.setProductId(productDraft.getProductId());
        productAttrDraft.setProductAttrType(Short.valueOf(ProductAttrType.OPERATION.getType().toString()));
        productAttrDraft.setRelateAttrId(OperationAttrs.IS_NOTICE.getStatus());

        try {
            List<ProductAttrDraft> productAttrDraftList = productAttrDraftService.findByCondition(productAttrDraft);
            // 该产品不支持到货通知
            if (productAttrDraftList == null || productAttrDraftList.isEmpty())
                return false;
        } catch (Exception e) {
            log.error("查询运营属性出错：" + e);
            return false;
        }
        return true;
    }

    // 检查SKU的市场价、重量值、Pv
    private boolean checkSkuWeightAndPriceAndPv(List<ProductSkuDraft> skuDraftList,ResultMessage resultMessage) {
        for (ProductSkuDraft skuDraft : skuDraftList) {
            if (skuDraft.getMarkPrice() == null || skuDraft.getMarkPrice().doubleValue() <= 0d) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("商品尚未录入合法的市场价!");
                return Boolean.FALSE;
            } else if (skuDraft.getPrice() == null || skuDraft.getPrice().doubleValue() <= 0d) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("商品尚未录入合法的价格!");
                return Boolean.FALSE;
            } else if (skuDraft.getUnitWeight() == null || skuDraft.getUnitWeight().doubleValue() <= 0d) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("商品尚未录入合法的单位重量!");
                return Boolean.FALSE;
            } else if (skuDraft.getPvValue() == null || skuDraft.getPvValue().doubleValue() < 1d) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("商品尚未录入合法的PV值，最少为1!");
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 检查SKU及价格
     * @param productDraft
     * @param skuDraftList
     * @param resultMsg
     * @throws ServiceException 
     */
    private boolean checkSku(ProductDraft productDraft, List<ProductSkuDraft> skuDraftList, ResultMessage resultMsg)
            throws ServiceException  {
        String productName = productDraft.getProductName();
        if (!checkSkuWeightAndPriceAndPv(skuDraftList, resultMsg)) {
            return Boolean.FALSE;
        }

        // 这里还得检查图片
        for (ProductSkuDraft skuDraft : skuDraftList) {
            List<ProductImageDraft> imageList = skuDraft.getProductSkuImages();
            if (CollectionUtils.isEmpty(imageList)) {
                resultMsg.setIsSuccess(false);
                resultMsg.setMessage("产品：" + productName + " 未上传商品图片，上架失败!");
                return Boolean.FALSE;
            }
        }

        // 同时满足产品支持到货通知，无需检测库存，直接返回TRUE
        /*if (checkOperationAttr(productDraft)) {
            resultMsg.setIsSuccess(Boolean.TRUE);
            return Boolean.TRUE;
        }*/

        // 暂时不检查库存
        // checkProductStockBySku(productDraft,skuIdsMap,resultMsg);
        resultMsg.setIsSuccess(Boolean.TRUE);
        return Boolean.TRUE;
    }

    // 检查上架产品所属品牌的有效性，无效不允许上架
    private boolean checkBrand(ProductDraft productDraft, ResultMessage resultMsg) throws ServiceException  {
        if (null == productDraft.getBrandId()) {
            resultMsg.setIsSuccess(false);
            resultMsg.setMessage("产品：" + productDraft.getProductName() + "无品牌参数,不能上架!");
            return Boolean.FALSE;
        }
        ProdBrand prodBrand = productDraft.getProdBrand();
        if (prodBrand == null || !IsValidStatus.VALID.getStatus().equals(prodBrand.getIsValid())) {
            resultMsg.setIsSuccess(false);
            resultMsg.setMessage("产品：" + productDraft.getProductName() + "所属品牌,已失效,不能上架!");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 检查上架状态
     * <note>
     *  符合产品上架的状态（AUDIT("2","已审核,待上架"),DOWN("4","已下架"),SYSDOWN("5","系统下架")）
     * </note>
     * @param productDraft
     * @param resultMsg
     */
    public boolean checkUpShelf(ProductDraft productDraft, ResultMessage resultMsg) {
        // 符合产品上架的状态（AUDIT("2","已审核,待上架"),DOWN("4","已下架"),SYSDOWN("5","系统下架")）
        if (!ProductStatus.AUDIT.getStatus().equals(productDraft.getStatus())
                && !ProductStatus.DOWN.getStatus().equals(productDraft.getStatus())
                && !ProductStatus.SYSDOWN.getStatus().equals(productDraft.getStatus())) {
            resultMsg.setIsSuccess(false);
            resultMsg.setMessage("产品：" + productDraft.getProductName() + "的现有状态不符合上架要求，上架失败!");
            return Boolean.FALSE;
        }

        // 可能有商品价格未通过审核
        if (!ProductStatus.AUDIT.getStatus().equals(productDraft.getPriceStatus())) {
            resultMsg.setIsSuccess(false);
            resultMsg.setMessage("产品：" + productDraft.getProductName() + "的价格未通过审核，上架失败!");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 检查产品
     *
     * @param productDraftId
     * @param resultMsg
     * @throws ServiceException 
     */
    private boolean checkProduct(Long productDraftId, ResultMessage resultMsg) throws ServiceException  {
        try {
            // 获取上架产品列表
            List<ProductDraft> productDraftList = productDraftDao.selectSkuByProductDraftId(productDraftId);

            // 产品已经包装成商品(KEY:SKU)，才可以上架销售
            if (productDraftList != null && productDraftList.size() != 0) {
                ProductDraft productDraft = productDraftList.get(0);

                // 符合产品上架的状态
                if (!checkUpShelf(productDraft, resultMsg)) {
                    return Boolean.FALSE;
                }

                String productName = productDraft.getProductName();

                // 一个产品最少拥有一个SKU
                List<ProductSkuDraft> skuDraftList = productDraft.getProductSkuDrafts();
                if (CollectionUtils.isEmpty(skuDraftList)) {
                    resultMsg.setIsSuccess(false);
                    resultMsg.setMessage("产品："+productName+"无有效SKU，上架失败!");
                    return Boolean.FALSE;
                }

                //品牌
                if (!checkBrand(productDraft, resultMsg)) {
                    return Boolean.FALSE;
                }

                //SKU检测
                if (!checkSku(productDraft, skuDraftList, resultMsg)) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage checkUpShelf(List<Long> productIds) throws ServiceException {
        ResultMessage result = new ResultMessage();
        result.setIsSuccess(true);
        try {
            for (Long prodDraftId : productIds) {
                if (!checkProduct(prodDraftId, result)) {
                    return result;
                }
            }
        } catch (Exception e) {
            log.error("草稿产品上架检查出错：" + e);
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor = ServiceException.class)
    public ResultMessage upShelf(List<Long> productIds, Long loginId) throws ServiceException {
        ResultMessage rm = null;
        List<String> productSkuNoList = new ArrayList<String>();
        List<ProductImageDraft> toDeleteList = null;
        Date nowDate = null;
        try {
            rm = checkUpShelf(productIds);
            if (rm.getIsSuccess()) {
                boolean haveProductNo = false;
                boolean haveFileData = false;
                for (Long productId : productIds) {
                    haveProductNo = false;
                    haveFileData = false;
                    nowDate = new Date();
                    // 向产品正式表插入数据
                    ProductDraft pd = productDraftDao.selectByPrimaryKey(productId);
                    pd.setModifUser(loginId);
                    pd.setModifTime(nowDate);
                    pd.setUpTime(nowDate);
                    pd.setStatus(ProductStatus.UP.getStatus());

                    String productNo = "";
                    int opType = pd.getOpType().intValue();
                    if (opType == DraftType.UPDATE.getStatus().intValue()) {
                        productDraftDao.updateProductOfficialByDraft(pd);
                        productNo = pd.getProductNo();
                        ProductAttr productAttr = new ProductAttr();
                        productAttr.setProductId(productId);
                        productAttrService.deleteProductAttr(productAttr);
                        haveProductNo = true;
                        haveFileData = true;
                    } else {
                        productNo = productDraftDao.insertProductOfficial(pd);
                    }

                    // 向产品属性正式表插入数据
                    List<ProductAttrDraft> productAttrList = productAttrDraftService.findProductDraftAttrByProductId(pd.getProductId());
                    productAttrDraftService.batchInsertOfficial(productAttrList);

                    // 向产品SKU正式表插入数据
                    List<ProductSkuDraft> productSkuList = productSkuDraftService.findProductSkuByProductId(productId);

                    String productSkuNo = "";
                    if (haveProductNo) {
                        String sourceCode = productSkuService.findMaxSkuCodeByCateCode(productNo);
                        productSkuNo = sourceCode == null ? productNo + "00" : sourceCode;
                    } else {
                        productSkuNo = productNo + "00";
                    }

                    List<ProductSkuDraft> updateSkuList = new ArrayList<ProductSkuDraft>();
                    for (ProductSkuDraft psd : productSkuList) {
                        if (psd.getProductSkuCode() != null) {
                            updateSkuList.add(psd);
                            productSkuAttrService.deleteProductSkuAttr(psd.getProductSkuId());
                        } else {
                            productSkuNo = CodeUtils.getNewCode(productSkuNo);
                            psd.setProductSkuCode(productSkuNo);
                            productSkuNoList.add(productSkuNo);// 新增的SKU_NO
                        }
                    }

                    if (opType == DraftType.UPDATE.getStatus().intValue() && updateSkuList.size() > 0) {
                        productSkuList.removeAll(updateSkuList);
                        productSkuDraftService.batchUpdateOfficialFromDraft(updateSkuList, loginId);
                    }

                    if (productSkuList.size() > 0) {
                        productSkuDraftService.batchInsertOfficial(productSkuList, loginId, productId);
                    }

                    // 向产品SKU属性正式表插入数据
                    List<ProductSkuAttrDraft> productSkuAttrList = productSkuAttrDraftService.findByProductId(productId);
                    productSkuAttrDraftService.batchInsertOfficical(productSkuAttrList);

                    // 向产品图片正式表插入数据
                    List<ProductImageDraft> productImageList = productImageDraftService.findByProductId(productId);

                    // 产品资质文件
                    List<ProductCertificateDraft> productCertificateList = productCertificateDraftService.findByProductId(productId);

                    for (ProductImageDraft pid : productImageList) {
                        pid.setProductNo(productNo);
                    }

                    if (haveFileData) {
                        toDeleteList = productImageDraftService.updateImageOfficial(productId, productImageList);
                        productCertificateDraftService.updateOfficialData(productId, productCertificateList);
                    } else {
                        productImageDraftService.batchInsertOfficial(productImageList);
                        productCertificateDraftService.batchInsertOfficialFromDraft(productCertificateList);
                    }

                    // 清除草稿表操作
                    // 删除产品图片草稿表数据
                    productImageDraftService.batchDeleteDraft(productImageList);
                    // 删除产品资质文件草稿表数据
                    productCertificateDraftService.deleteByProductIdWithNotFile(productId);
                    // 删除产品SKU属性草稿表数据
                    productSkuAttrDraftService.batchDeleteDraft(productSkuAttrList);
                    // 删除产品SKU草稿表数据
                    productSkuDraftService.deleteDraftByProductId(productId);
                    // 删除产品属性草稿表数据
                    productAttrDraftService.deleteDraftByProductId(productId);
                    // 删除产品草稿表数据
                    productDraftDao.deleteByPrimaryKey(productId);
                }
                // 用于库存增加
                rm.setObject(productSkuNoList);
            }

            if (CollectionUtils.isNotEmpty(toDeleteList)) {
                toDeleteList.forEach(ProductImageDraft::deleteProductImageFile);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return rm;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteProductDraftByProductId(Long productId, String isExistProduct) throws ServiceException {
        try {
            // 获取产品图片数据
            List<ProductImageDraft> productImageList = productImageDraftService.findByProductId(productId);
            // 已经存在正式表
            boolean isExists = "1".equals(isExistProduct);
            // 删除产品图片数据及图片文件
            productImageDraftService.batchDeleteDraft(productImageList, !isExists);

            if (!isExists) {
                // 删除产品的资质文件
                List<ProductCertificateDraft> productCertificateDraftList =
                        productCertificateDraftService.findByProductId(productId);
                productCertificateDraftService.deleteByCertificateFiles(productCertificateDraftList);
            }
            // 删除产品的资质文件数据
            productCertificateDraftService.deleteByProductIdWithNotFile(productId);
            // 删除产品SKU属性草稿表数据
            productSkuAttrDraftService.deleteProductSkuAttrByProductId(productId);
            // 删除产品SKU草稿表数据
            productSkuDraftService.deleteDraftByProductId(productId);
            // 删除产品属性草稿表数据
            productAttrDraftService.deleteDraftByProductId(productId);

            // 删除其余店内分类的关联记录
            if ("0".equals(isExistProduct)) {
                // 是新增类型的并且还是供应商的产品,则将店内分类和产品的关系做删除
                ProductDraft draft = findByProductIdWithOutClob(productId);
                if (draft != null && draft.getShopCode() != null
                        && !"221".equals(draft.getShopCode())) {
                    shopProductCategoryService.deleteByProductId(productId);
                }
            }

            // 删除产品草稿表数据
            productDraftDao.deleteByPrimaryKey(productId);
        } catch (Exception e) {
            log.error("删除草稿产品失败，" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateSkuAttrValue(ProductDraft productDraft, String productNo,
            List<ProductAttrDraft> productAttrList, List<SkuCheckAttr> skuCheckAttrs,
            List<String> oldSkuCheckedId, String toDeleteSkuIds) throws ServiceException {
        try {
            Boolean notUpdateSkuStatus = true;

            // 1.修改已有的sku列表的状态并同时修改对应的sku属性的状态
            if (CollectionUtils.isNotEmpty(productDraft.getProductSkuDrafts())) {
                productSkuDraftService.batchUpdateProductSkuDraft(productDraft.getProductSkuDrafts());
                notUpdateSkuStatus = false;
            }

            // 2.批量更新sku属性
            for (ProductAttrDraft attr : productAttrList) {
                if (attr.getProductAttrId() == null) {
                    continue;
                }

                // sku属性中所选中的checkbox值
                String[] values = attr.getProductAttrValues();
                if (ArrayUtils.isNotEmpty(values)) {
                    attr.setProductAttrValue(StringUtils.join(values, ","));
                }
            }
            productAttrDraftService.batchUpdateProductAttrDraft(productAttrList);

            // 3.删除需要删除的sku数据
            if (!StringUtils.isEmpty(toDeleteSkuIds)) {
                String[] str = toDeleteSkuIds.split(",");
                for (String s : str) {
                    productSkuDraftService.deleteProductSku(Long.valueOf(s));
                }
            }

            // 4.如果有新增的sku列表或目前没有sku选中（此情况默认构造一条）
            if (CollectionUtils.isNotEmpty(skuCheckAttrs) || (CollectionUtils.isEmpty(oldSkuCheckedId)
                            && CollectionUtils.isEmpty(skuCheckAttrs) && notUpdateSkuStatus)) {
                insertProductSku(productDraft, productNo, skuCheckAttrs);
            }

            // 5.修改草稿产品
            productDraftDao.updateByPrimaryKeySelective(productDraft);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void insertFromProduct(ProductDraft product, List<SkuCheckAttr> skuCheckAttrs) throws ServiceException {
        try {
            productDraftDao.updateByPrimaryKeySelective(product);

            // 删除产品属性草稿表数据
            productAttrDraftService.deleteDraftByProductId(product.getProductId());
            // 向产品属性表插入数据
            List<ProductAttrDraft> productAttrList = product.getProductAttrDraftList();

            // 加入为空判断
            if (productAttrList != null && productAttrList.size() > 0) {
                productAttrList = changeInnerValue(product, productAttrList);
                productAttrDraftService.batchInsertDraft(productAttrList);
            }
            // 插入运营属性
            insertOperationAttr(product, product.getOperationAttrIds());
            // 更新SKU的状态
            List<ProductSkuDraft> skuDraftList = product.getProductSkuDrafts();
            if (CollectionUtils.isNotEmpty(skuDraftList)) {
                productSkuDraftService.batchUpdateProductSkuDraft(skuDraftList);
            }
            // 插入SKU以及SKU属性
            if (CollectionUtils.isNotEmpty(skuCheckAttrs)) {
                insertProductSku(product, product.getProductNo(), skuCheckAttrs);
            }

            if (product.getCerfificateList() != null && product.getCerfificateList().size() > 0) {
                List<Long> toDeleteIds = product.getCerfificateList().stream()
                        .filter(pcd -> pcd.getPscId() != null)
                        .map(ProductCertificateDraft::getPscId)
                        .collect(Collectors.toList());

                if (toDeleteIds.size() > 0) {
                    productCertificateDraftService.deleteByPscIdsWithNotFile(toDeleteIds);
                }
                productCertificateDraftService.batchInsertDraft(product.getCerfificateList());
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 改变产品属性集合里面的值
     * 
     * @param product
     * @param productAttrList
     * @return
     */
    private List<ProductAttrDraft> changeInnerValue(ProductDraft product, List<ProductAttrDraft> productAttrList) {
        List<ProductAttrDraft> result = new ArrayList<ProductAttrDraft>();
        // 避免productAttrList重存在空对象；再之后的批量插入中会出错；--自定义属性中删除属性引起的；
        for (ProductAttrDraft pad : productAttrList) {
            if (pad == null) {
                continue;
            }

            if (pad.getIsSku() != null && pad.getIsSku().equals((short) 1)) {
                String[] str = pad.getProductAttrValues();
                if (str != null && str.length > 0) {
                    if (str.length > 1) {
                        pad.setProductAttrValue(StringUtils.join(str, ","));
                    } else {
                        pad.setProductAttrValue(str[0]);
                    }
                }
            }
            if (pad.getProductId() == null) {
                pad.setProductId(product.getProductId());
                pad.setOpType(product.getOpType());
                pad.setProductAttrType(ProductAttrType.DEFINITION.getType().shortValue());
            }
            result.add(pad);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void insertProductDraftWithOutSeq(ProductDraft productDraft) throws ServiceException  {
        try {
            productDraftDao.insertWithOutSeq(productDraft);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage releaseProductPrice(Long productId, Long loginId) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        try {
            List<ProductSkuDraft> productSkuList = productSkuDraftService.findProductSkuByProductId(productId);
            if (!checkSkuWeightAndPriceAndPv(productSkuList, resultMessage)) {
                return resultMessage;
            }
            productSkuDraftService.batchUpdatePriceOnlyOfficialFromDraft(productSkuList, loginId);

            // 删除产品属性草稿表数据
            productAttrDraftService.deleteDraftByProductId(productId);
            // 删除产品SKU属性草稿表数据
            List<ProductSkuAttrDraft> productSkuAttrList = productSkuAttrDraftService.findByProductId(productId);
            productSkuAttrDraftService.batchDeleteDraft(productSkuAttrList);
            // 删除产品SKU草稿表数据
            productSkuDraftService.deleteDraftByProductId(productId);
            // 删除产品草稿表数据
            productDraftDao.deleteByPrimaryKey(productId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        resultMessage.setIsSuccess(Boolean.TRUE);
        return resultMessage;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void copyOfficialDataToDraft(Long productId, String isExistProduct) throws ServiceException {
        try {

            if ("1".equals(isExistProduct) || "2".equals(isExistProduct)) {
                this.deleteProductDraftByProductId(productId, isExistProduct);
            }
            // 复制产品主表
            Product product = productService.findProductById(productId);
            product.setModifTime(new Date());
            productDraftDao.insertDraftFromOfficial(product);

            // 复制产品属性表
            ProductAttr productAttr = new ProductAttr();
            productAttr.setProductId(productId);
            List<ProductAttr> productAttrList = productAttrService.queryProductAttr(productAttr);
            productAttrDraftService.batchInsertDraftFromOfficial(productAttrList);

            // 更新正式表中的库存信息
            List<ProductStock> stockList = productStockService.findByProductId(productId);
            productSkuService.updateStock(stockList);

            // 复制产品SKU
            ProductSku productSku = new ProductSku();
            productSku.setProductId(productId);
            List<ProductSku> productSkuList = productSkuService.queryProductSkuList(productSku);
            productSkuDraftService.batchInsertDraftFromOfficial(productSkuList);

            // 复制产品SKU属性表
            ProductSkuAttr attr = null;
            for (ProductSku sku : productSkuList) {
                attr = new ProductSkuAttr();
                attr.setProductSkuId(sku.getProductSkuId());

                List<ProductSkuAttr> skuAttrList =
                        productSkuAttrService.queryProductSkuAttrList(attr);
                productSkuAttrDraftService.batchInsertDraft(skuAttrList);
            }

            // 复制产品图片表
            productImageDraftService.insertDraftFromOfficialByProductId(productId);

            if (!ProductType.NOTDRUG.getKey().equals(product.getProductType())) {
                productCertificateDraftService.insertDraftFromOfficialByProductId(productId);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,  rollbackFor = ServiceException.class)
    public void batchUpdateObject(List<ProductDraft> list) throws ServiceException {
        try {
            if (CollectionUtils.isNotEmpty(list)) {
                productDraftDao.batchUpdateProductDraft(list);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String previewProductDraftInfoPage(String productId) throws ServiceException  {
        try {
            return detailPublishService.viewProductDraftPublish(Integer.valueOf(productId));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateProductCertificateFiles(ProductDraft product, List<ProductCertificateDraft> toAddList, List<Long> toDeleteIds)
            throws ServiceException {
        try {
            if (toDeleteIds != null && toDeleteIds.size() > 0) {
                List<ProductCertificateDraft> toDeleteList = productCertificateDraftService.findByPscIds(toDeleteIds);
                productCertificateDraftService.deleteByPscIdsWithNotFile(toDeleteIds);

                // 如果是产品是新增状态需要删除文件
                if (DraftType.ADD.getStatus().equals(product.getOpType())) {
                    productCertificateDraftService.deleteByCertificateFiles(toDeleteList);
                }
            }

            if (CollectionUtils.isNotEmpty(toAddList)) {
                productCertificateDraftService.batchInsertDraft(toAddList);
            }
            productDraftDao.updateByPrimaryKeySelective(product);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage batchDeleteProductDraftByPId(List<Long> productIdList) throws ServiceException  {
        ResultMessage message = new ResultMessage();
        message.setIsSuccess(true);
        try {
            for (Long productId : productIdList) {
                ProductDraft pd = findByProductIdWithOutClob(productId);
                String isExistProduct = "1";
                if (pd != null && DraftType.ADD.getStatus().equals(pd.getOpType())) {
                    isExistProduct = "0";
                }

                deleteProductDraftByProductId(productId, isExistProduct);
            }
        } catch (Exception e) {
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            log.error("批量删除草稿产品失败，" + e.getMessage(), e);
            message.setIsSuccess(false);
        }
        return message;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchSubmitDraftTheAudit(List<ProductDraft> list) throws ServiceException {
        try {
            if (list != null && list.size() > 0) {
                int result = productDraftDao.batchSubmitDraftTheAudit(list);
                if (result == 0) {
                    throw new ServiceException("批量提交审核出错!");
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage batchReleaseProductPrice(List<Long> productIds, Long loginId) throws ServiceException  {
        ResultMessage result = null;
        for (Long productId : productIds) {
            result = releaseProductPrice(productId, loginId);
            if (!result.getIsSuccess()) {
                return result;
            }
        }

        result.setIsSuccess(Boolean.TRUE);
        result.setMessage("发布成功！");
        return result;
    }

}