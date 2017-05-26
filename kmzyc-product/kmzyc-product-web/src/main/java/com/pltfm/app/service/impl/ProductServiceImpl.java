package com.pltfm.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.cms.remote.service.DetailPublishService;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.FilterFieldDAO;
import com.pltfm.app.dao.ProductDao;
import com.pltfm.app.enums.CategoryAttrInputType;
import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.enums.MsgOperation;
import com.pltfm.app.enums.OperationAttrs;
import com.pltfm.app.enums.ProductAttrType;
import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.enums.ProductType;
import com.pltfm.app.fobject.ProductResultMap;
import com.pltfm.app.jms.SearchMessageSender;
import com.pltfm.app.service.CategoryAttrService;
import com.pltfm.app.service.CategoryAttrValueService;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.service.CmsProductUpShelfService;
import com.pltfm.app.service.OperationAttrService;
import com.pltfm.app.service.ProdBrandService;
import com.pltfm.app.service.ProductAttrService;
import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.service.ProductRelationService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductSkuAttrService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ProductStockService;
import com.pltfm.app.service.SectionsDetailService;
import com.pltfm.app.threadHandler.ProductPriceCache;
import com.pltfm.app.threadHandler.ProductPriceHandler;
import com.pltfm.app.util.CodeUtils;
import com.pltfm.app.util.KeywordFilter;
import com.pltfm.app.util.ProductListType;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.CouponProduct;
import com.pltfm.app.vobject.FilterField;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductExample;
import com.pltfm.app.vobject.ProductExample.Criteria;
import com.pltfm.app.vobject.ProductRelationAndDetail;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuForExport;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.SectionsDetail;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    ProductDao productDao;

    @Resource
    private SearchMessageSender sendMessageSender;

    @Resource
    private TaskExecutor taskExecutor;

    @Resource
    private CmsProductUpShelfService cmsProductUpShelfService;

    @Resource
    private ProductAttrService productAttrService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductSkuAttrService productSkuAttrService;

    @Resource
    private ProductStockService productStockService;

    @Resource
    private ProdBrandService prodBrandService;

    @Resource
    private ProductRelationService productRelationService;

    @Resource
    private ProductImageService productImageService;

    @Resource
    private SectionsDetailService sectionsDetailService;

    @Resource
    private FilterFieldDAO filterFieldDao;

    @Resource
    private DetailPublishService detailPublishService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private CategoryAttrService categoryAttrService;

    @Resource
    private CategoryAttrValueService categoryAttrValueService;

    @Resource
    private OperationAttrService operationAttrService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long insertProduct(Product product, List<String> skuCheckedId) throws ServiceException {
        Long productId = null;
        try {
            // 获取该产品的类目
            Category cate = categoryService.showCategory(product.getCategoryId());
            if (StringUtils.isBlank(cate.getCategoryCode())) {
                throw new ServiceException("产品类目编号为空!");
            }

            // 保存产品基本信息
            productId = productDao.insertProduct(product);
            Product productTmp = productDao.selectByPrimaryKey(productId);

            List<List<ProductSkuAttr>> allProductSkuAttrList = new ArrayList<>();

            // 保存产品类目属性
            insertCategoryAttr(productId, product.getCategoryAttrList(), allProductSkuAttrList);

            // 生成SKU
            insertProductSku(productId, productTmp.getProductNo(), skuCheckedId);

            // 产品自定义属性
            insertDefinitionAttr(productId, product.getProductAttrList());

            // 产品运营属性
            insertOperationAttr(productId, product.getOperationAttrIds());
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        return productId;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private void insertProductSku(Long productId, String productNo, List<String> skuCheckedId)
            throws ServiceException {
        try {
            // 获取该产品下现存在的最大编号
            String sourceCode = productSkuService.findMaxSkuCodeByCateCode(productNo);
            // 如果没有值返回，则从01开始
            sourceCode = sourceCode == null ? productNo + "00" : sourceCode;

            if (skuCheckedId != null && skuCheckedId.size() > 0) {
                ProductSku productSku = null;
                for (String str : skuCheckedId) {
                    productSku = new ProductSku();
                    productSku.setProductId(productId);
                    sourceCode = CodeUtils.getNewCode(sourceCode);
                    productSku.setProductSkuCode(sourceCode);
                    productSku.setStatus("1");
                    productSkuService.addProductSku(productSku);
                    String[] values = str.split(",");
                    for (String value : values) {
                        String[] tempStr = value.split(":");
                        ProductSkuAttr skuAttr = new ProductSkuAttr();
                        skuAttr.setCategoryAttrId(Long.valueOf(tempStr[0]));
                        skuAttr.setCategoryAttrName(tempStr[1]);
                        skuAttr.setCategoryAttrValueId(Long.valueOf(tempStr[2]));
                        skuAttr.setStatus("1");
                        skuAttr.setProductSkuId(productSku.getProductSkuId());
                        productSkuAttrService.addProductSkuAttr(skuAttr);
                    }
                }
            } else {
                ProductSku productSku = new ProductSku();
                productSku.setProductId(productId);
                sourceCode = CodeUtils.getNewCode(sourceCode);
                productSku.setProductSkuCode(sourceCode);
                productSku.setStatus("1");
                productSkuService.addProductSku(productSku);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        logger.info(" productId:{} end insert sku属性开始", productId);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private void insertCategoryAttr(Long productId, List<CategoryAttr> categoryAttrList, List<List<ProductSkuAttr>> skuAttrList)
            throws ServiceException {
        try {
            if (categoryAttrList != null && categoryAttrList.size() > 0) {
                for (CategoryAttr categoryAttr : categoryAttrList) {
                    ProductAttr productAttr = new ProductAttr();
                    productAttr.setProductId(productId);
                    productAttr.setProductAttrType(ProductAttrType.CATEGORY.getType());
                    productAttr.setRelateAttrId(categoryAttr.getCategoryAttrId());
                    productAttr.setIsNav(categoryAttr.getIsNav());
                    productAttr.setInputType(categoryAttr.getInputType());
                    productAttr.setIsReq(categoryAttr.getIsReq());
                    productAttr.setIsSku(categoryAttr.getIsSku());
                    productAttr.setProductAttrName(categoryAttr.getCategoryAttrName());
                    String[] values = categoryAttr.getCategoryAttrValues();
                    if (values != null && values.length > 0) {
                        boolean isSku = false;
                        long i = 0;
                        List<ProductSkuAttr> productSkuAttrList = null;
                        if (categoryAttr.getIsSku() == 1) {
                            isSku = true;
                            productSkuAttrList = new ArrayList();
                        }
                        StringBuffer sb = new StringBuffer();
                        for (String value : values) {
                            sb.append(",").append(value);
                            if (isSku) {
                                ProductSkuAttr tmpProductSkuAttr = new ProductSkuAttr();
                                tmpProductSkuAttr.setIsNewAttrValue(1);
                                tmpProductSkuAttr.setProductSkuId(++i);
                                tmpProductSkuAttr.setCategoryAttrName(categoryAttr.getCategoryAttrName());
                                tmpProductSkuAttr.setCategoryAttrId(categoryAttr.getCategoryAttrId());
                                tmpProductSkuAttr.setCategoryAttrValueId(Long.parseLong(value));
                                productSkuAttrList.add(tmpProductSkuAttr);
                            }
                        }
                        if (productSkuAttrList != null && productSkuAttrList.size() > 0) {
                            skuAttrList.add(productSkuAttrList);
                        }
                        productAttr.setProductAttrValue(sb.substring(1).toString());
                        productAttrService.addProductAttr(productAttr);
                    } else {
                        productAttrService.addProductAttr(productAttr);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        logger.info(" productId:{} end insert 类目属性结束", productId);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private void insertDefinitionAttr(Long productId, List<ProductAttr> productAttrList) throws ServiceException {
        try {
            if (productAttrList != null && productAttrList.size() > 0) {
                for (ProductAttr productAttr : productAttrList) {
                    productAttr.setProductId(productId);
                    productAttr.setProductAttrType(ProductAttrType.DEFINITION.getType());
                    productAttrService.addProductAttr(productAttr);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        logger.info(" productId:{} end insert 产品自定义属性结束", productId);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private void insertOperationAttr(Long productId, Long[] operationAttrIds) throws ServiceException {
        try {
            if (operationAttrIds != null && operationAttrIds.length > 0) {
                for (Long operationAttrId : operationAttrIds) {
                    ProductAttr productAttr = new ProductAttr();
                    productAttr.setProductId(productId);
                    productAttr.setProductAttrType(ProductAttrType.OPERATION.getType());
                    productAttr.setRelateAttrId(operationAttrId);
                    OperationAttr operationAttr = operationAttrService.queryOperationAttr(operationAttrId);
                    productAttr.setProductAttrName(operationAttr.getOperationAttrName());
                    productAttr.setProductAttrValue(operationAttr.getOperationAttrId().toString());
                    productAttr.setIsNav(operationAttr.getIsNav());
                    productAttrService.addProductAttr(productAttr);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        logger.info(" productId:{} end insert 运营属性结束", productId);
    }

    @Override
    public void searchPage(Page page, Product product, String type) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProductExample pe = new ProductExample();
        Criteria criteria = pe.createCriteria();
        if (StringUtils.isNotBlank(product.getName()))
            criteria.andProcuctNameLike("%" + product.getName().trim() + "%");
        if (StringUtils.isNotBlank(product.getKeyword()))
            criteria.andKeywordLike("%" + product.getKeyword().trim() + "%");
        if (StringUtils.isNotBlank(product.getProductNo()))
            criteria.andProductNoLike("%" + product.getProductNo().trim() + "%");
        if (product.getCategoryId() != null && product.getCategoryId() != 0) // 产品所属类目
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        if (type != null && "sections".equals(type)) {
            criteria.andStatusEqualTo(ProductStatus.UP.getStatus());
        } else if (StringUtils.isNotBlank(product.getStatus())) { // 产品状态
            criteria.andStatusEqualTo(product.getStatus());
        }
        if (product.getBrandId() != null && product.getBrandId() != 0) {
            criteria.andBrandIdEqualTo(product.getBrandId());
        }
        if (type != null && type.indexOf("price") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(), ProductStatus.SYSDOWN.getStatus());
        }
        if (type != null && type.indexOf("weight") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(), ProductStatus.SYSDOWN.getStatus());
        }
        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("ARCHIVE_TIME DESC");
        } else {
            pe.setOrderByClause("PRODUCT_ID DESC");
        }

        try {
            int count = productDao.countByExample(pe);
            List<Product> productList = productDao.selectByExample(pe, skip, max);
            page.setDataList(productList);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void searchSkuPage(Page page, Product product, String type, Integer loginUserId)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProductExample pe = new ProductExample();
        Criteria criteria = pe.createCriteria();
        pe.setUserId(String.valueOf(loginUserId));
        if (StringUtils.isNotBlank(product.getName()))
            criteria.andProcuctNameLike("%" + product.getName().trim() + "%");
        if (StringUtils.isNotBlank(product.getKeyword()))
            criteria.andKeywordLike("%" + product.getKeyword().trim() + "%");
        if (StringUtils.isNotBlank(product.getProductNo()))
            criteria.andProductNoLike("%" + product.getProductNo().trim() + "%");
        if (product.getCategoryId() != null && product.getCategoryId() != 0) // 产品所属类目
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        if (type != null && "sections".equals(type)) {
            criteria.andStatusEqualTo(ProductStatus.UP.getStatus());
        } else if (StringUtils.isNotBlank(product.getStatus())) {// 产品状态
            criteria.andStatusEqualTo(product.getStatus());
        }

        if (product.getBrandId() != null && product.getBrandId() != 0) // 品牌
            criteria.andBrandIdEqualTo(product.getBrandId());
        if (type != null && type.indexOf("price") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(), ProductStatus.SYSDOWN.getStatus());
        }
        if (type != null && type.indexOf("weight") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(), ProductStatus.SYSDOWN.getStatus());
        }
        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("ARCHIVE_TIME DESC");
        } else {
            pe.setOrderByClause("PRODUCT_ID DESC");
        }

        try {
            int count = productDao.countBySkuExample(pe);
            List<ProductResultMap> productList = productDao.selectBySkuExample(pe, skip, max);
            page.setDataList(productList);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Product findProductById(Long id) throws ServiceException {
        try {
            return productDao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean updateProductById(Product product) throws ServiceException {
        try {
            int count = productDao.updateByPrimaryKey(product);
            return count > 0;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage upShelf(List<Product> productList) throws ServiceException {
        ResultMessage resultMsg = new ResultMessage();
        resultMsg.setIsSuccess(Boolean.TRUE);
        resultMsg.setMessage("上架成功");
        try {
            for (Product obj : productList) {
                if (!checkProduct(obj, resultMsg)) {
                    return resultMsg;
                }
            }

            int count = productDao.batchUpdateForProduct("productmapper.product_upShelfByIdList", productList);
            if (count < 0) {
                resultMsg.setIsSuccess(Boolean.FALSE);
                resultMsg.setMessage("批量更新失败，上架失败!");
            }
        } catch (Exception e) {
            logger.error(" productList: {} upShelf方法出错: {}", new Object[]{productList, e});
            throw new ServiceException(e);
        }
        return resultMsg;
    }

    private boolean checkUpShelf(Product product, ResultMessage resultMsg) {
        if (!ProductStatus.AUDIT.getStatus().equals(product.getStatus())
                && !ProductStatus.DOWN.getStatus().equals(product.getStatus())
                && !ProductStatus.SYSDOWN.getStatus().equals(product.getStatus())
                && !ProductStatus.ILLEGAL_DOWN.getStatus().equals(product.getStatus())
                && !ProductStatus.UP.getStatus().equals(product.getStatus())) {

            // 上架状态允许再次上架
            resultMsg.setIsSuccess(Boolean.FALSE);
            resultMsg.setMessage("产品：" + product.getName() + "的现有状态不符合上架要求，上架失败!");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private boolean checkProduct(Product product, ResultMessage resultMsg) throws ServiceException {
        try {
            // 获取上架产品列表
            Product productTmp = productDao.selectProductAndSkuByProductId(product);
            if (productTmp == null) {
                resultMsg.setIsSuccess(Boolean.FALSE);
                resultMsg.setMessage("此产品：[" + product.getId() + "]不存在，上架失败!");
                return Boolean.FALSE;
            }

            // 1.判断Product是否符合上架状态
            if (!checkUpShelf(productTmp, resultMsg)) {
                return Boolean.FALSE;
            }

            // 2.一个产品最少拥有一个SKU
            List<ProductSku> skuList = productTmp.getProductSkus();
            if (CollectionUtils.isEmpty(skuList)) {
                resultMsg.setIsSuccess(Boolean.FALSE);
                resultMsg.setMessage("产品：" + productTmp.getName() + "无有效SKU，上架失败!");
                return Boolean.FALSE;
            }

            // 3.检查上架产品所属品牌的有效性，无效不允许上架
            if (!checkBrand(productTmp, resultMsg)) {
                return Boolean.FALSE;
            }

            // 4.检查SKU及价格
            if (!checkSku(productTmp, skuList, resultMsg)) {
                return Boolean.FALSE;
            }
            resultMsg.setIsSuccess(Boolean.TRUE);
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 检查上架产品所属品牌的有效性，无效不允许上架
     *
     * @param product
     * @param resultMsg
     * @return
     * @throws ServiceException
     */
    private boolean checkBrand(Product product, ResultMessage resultMsg) throws ServiceException {
        if (null == product.getBrandId()) {
            resultMsg.setIsSuccess(Boolean.FALSE);
            resultMsg.setMessage("产品：" + product.getName() + "无品牌参数,不能上架!");
            return Boolean.FALSE;
        }

        try {
            ProdBrand prodBrand = prodBrandService.findProdBrandById(product.getBrandId());
            if (prodBrand == null || !IsValidStatus.VALID.getStatus().equals(prodBrand.getIsValid())) {
                resultMsg.setIsSuccess(Boolean.FALSE);
                resultMsg.setMessage("产品：" + product.getName() + "所属品牌已失效,不能上架!");
                return Boolean.FALSE;
            }
            resultMsg.setIsSuccess(Boolean.TRUE);
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 检查SKU及价格
     *
     * @param product   产品
     * @param skuList   sku列表
     * @param resultMsg 返回
     * @return
     * @throws ServiceException
     */
    private boolean checkSku(Product product, List<ProductSku> skuList, ResultMessage resultMsg)
            throws ServiceException {
        // 1.检查SKU的市场价、重量值、销售价、pv
        if (!checkSkuWeightAndPriceAndPv(skuList, resultMsg)) {
            return Boolean.FALSE;
        }

        // 2.这里还得检查图片
        for (ProductSku sku : skuList) {
            if (CollectionUtils.isEmpty(sku.getProductSkuImages())) {
                resultMsg.setIsSuccess(Boolean.FALSE);
                resultMsg.setMessage("产品：" + product.getName() + "的SKU：" + sku.getProductSkuCode() + "未上传商品图片，上架失败!");
                return Boolean.FALSE;
            }
        }

        // 同时满足产品支持到货通知，无需检测库存，直接返回TRUE
        // if(checkOperationAttr(product))return resultMsg;

        resultMsg.setIsSuccess(Boolean.TRUE);
        return Boolean.TRUE;
    }

    private boolean checkOperationAttr(Product product) throws ServiceException {
        ProductAttr productAttr = new ProductAttr();
        productAttr.setProductId(product.getId());
        productAttr.setProductAttrType(ProductAttrType.OPERATION.getType());
        productAttr.setRelateAttrId(OperationAttrs.IS_NOTICE.getStatus());
        try {
            List<ProductAttr> productAttrList = productAttrService.queryProductAttr(productAttr);
            // 该产品不支持到货通知
            if (productAttrList == null || productAttrList.isEmpty())
                return false;
        } catch (Exception e) {
            logger.error("检查运营属性失败，", e);
            return false;
        }
        return true;
    }

    /**
     * 检查SKU的市场价、重量值是否合法
     *
     * @param skuList       sku列表
     * @param resultMessage 返回的消息
     * @return
     */
    private boolean checkSkuWeightAndPriceAndPv(List<ProductSku> skuList, ResultMessage resultMessage) {
        for (ProductSku sku : skuList) {
            if (sku.getMarkPrice() == null || sku.getMarkPrice().doubleValue() <= 0d) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("SKU编码为：" + sku.getProductSkuCode() + " 的商品尚未录入合法的市场价!");
                return Boolean.FALSE;
            } else if (sku.getPrice() == null || sku.getPrice().doubleValue() <= 0d) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("SKU编码为：" + sku.getProductSkuCode() + " 的商品尚未录入合法的销售价格!");
                return Boolean.FALSE;
            } else if (sku.getUnitWeight() == null || sku.getUnitWeight().doubleValue() <= 0d) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("SKU编码为：" + sku.getProductSkuCode() + " 的商品尚未录入合法的单位重量!");
                return Boolean.FALSE;
            } else if (sku.getPvValue() == null || sku.getPvValue().doubleValue() < 1d) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("SKU编码为：" + sku.getProductSkuCode() + " 商品尚未录入合法的PV值!");
                return Boolean.FALSE;
            }
        }
        resultMessage.setIsSuccess(Boolean.TRUE);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean downShelf(List<Product> productList) throws ServiceException {
        try {
            int count = productDao.batchUpdateForProduct("productmapper.product_downShelfByIdList", productList);
            return count > 0;
        } catch (SQLException e) {
            logger.error(" productList: {} downShelf方法出错: {}", new Object[]{productList, e});
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CategoryAttr> getCategoryAttrList(Long categoryId) throws ServiceException {
        Category categoryParam = new Category();
        categoryParam.setCategoryId(categoryId);
        List<Category> categoryList = categoryService.queryCategoryParentList(categoryParam);
        List<Long> categoryIds = categoryList.stream().map(Category::getCategoryId).collect(Collectors.toList());
        List<CategoryAttr> categoryAttrList = null;
        if (categoryIds.size() > 0) {
            CategoryAttr categoryAttrParam = new CategoryAttr();
            categoryAttrParam.setCategoryIds(categoryIds);
            categoryAttrList = categoryAttrService.queryCategoryAttrList(categoryAttrParam);
        }
        return categoryAttrList;
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
    public void setProductAttrValue(List<ProductAttr> productAttrList) throws ServiceException {
        try {
            for (ProductAttr productAttr : productAttrList) {
                if (productAttr.getProductAttrType() == 1) {
                    Integer inputType = productAttr.getInputType();
                    String productAttrValue = productAttr.getProductAttrValue();

                    if (CategoryAttrInputType.RADIO.getType().compareTo(inputType) == 0
                            || CategoryAttrInputType.SELECT.getType().compareTo(inputType) == 0) {
                        if (productAttrValue == null) {
                            continue;
                        }

                        CategoryAttrValue categoryAttrValue = categoryAttrValueService
                                .queryCategoryAttrValue(Long.parseLong(productAttrValue));
                        if (categoryAttrValue != null) {
                            productAttr.setProductAttrValue(categoryAttrValue.getCategoryAttrValue());
                        }
                    } else if (CategoryAttrInputType.CHECKBOX.getType().compareTo(inputType) == 0) {
                        if (productAttrValue == null) {
                            continue;
                        }

                        String[] ids = productAttrValue.split(",");
                        StringBuilder sb = new StringBuilder();
                        for (String id : ids) {
                            sb.append("，");
                            if (id.indexOf("@") >= 0) {
                                int index = id.lastIndexOf("#");
                                if (index > 0) {
                                    sb.append(id.substring(1, index));
                                } else {
                                    sb.append(id.substring(1));
                                }
                            } else {
                                CategoryAttrValue categoryAttrValue = categoryAttrValueService.queryCategoryAttrValue(Long.parseLong(id));
                                if (categoryAttrValue != null) {
                                    sb.append(categoryAttrValue.getCategoryAttrValue());
                                }
                            }
                        }
                        if (StringUtils.isNotEmpty(sb.toString())) {
                            productAttr.setProductAttrValue(sb.substring(1).toString());
                        }
                    }
                } else if (productAttr.getProductAttrType() == 3) {
                    OperationAttr operationAttr = operationAttrService.queryOperationAttr(productAttr.getRelateAttrId());
                    productAttr.setProductAttrName(operationAttr.getOperationAttrName());
                }
            }
        } catch (Exception e) {
            logger.error("设置产品属性失败：", e.getMessage());
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
                        productAttr.setCategoryAttrValueList(
                                categoryAttrValueService.queryCategoryAttrValueList(productAttr.getRelateAttrId()));
                    } else if (CategoryAttrInputType.CHECKBOX.getType().compareTo(productAttr.getInputType()) == 0) {
                        productAttr.setCategoryAttrValueList(
                                categoryAttrValueService.queryCategoryAttrValueList(productAttr.getRelateAttrId()));
                        List<Long> list = new ArrayList<Long>();
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
    public void setOperationAttrValue(Long productId, List<OperationAttr> operationAttrList)
            throws ServiceException {
        ProductAttr productAttr = new ProductAttr();
        productAttr.setProductId(productId);
        productAttr.setProductAttrType(3);
        try {

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
    public List<ProductAttr> getProductAttrList(Long productId) throws ServiceException {
        ProductAttr productAttr = new ProductAttr();
        productAttr.setProductId(productId);
        try {
            return productAttrService.queryProductAttr(productAttr);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateCategoryAttrValue(List<ProductAttr> productAttrList) throws ServiceException {
        if (CollectionUtils.isNotEmpty(productAttrList)) {
            for (ProductAttr productAttr : productAttrList) {
                String[] values = productAttr.getProductAttrValues();
                try {
                    if (values != null && values.length > 0) {
                        if (values.length == 1) {
                            productAttr.setProductAttrValue(values[0]);
                            productAttrService.updateProductAttr(productAttr);
                        } else {
                            StringBuffer sb = new StringBuffer();
                            for (String value : values) {
                                sb.append(",").append(value);
                            }
                            productAttr.setProductAttrValue(sb.substring(1).toString());
                            productAttrService.updateProductAttr(productAttr);
                        }
                    } else {
                        productAttr.setProductAttrValue("");
                        productAttrService.updateProductAttr(productAttr);
                    }
                } catch (Exception e) {
                    throw new ServiceException(e);
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateSkuAttrValue(Long productId, String productNo, List<ProductAttr> productAttrList,
                                   List<String> skuCheckedId, List<String> oldSkuCheckedId, String toDeleteSkuIds)
            throws ServiceException {
        try {
            StringBuffer sb = null;
            for (ProductAttr attr : productAttrList) {
                sb = new StringBuffer();
                String[] values = attr.getProductAttrValues();
                if (values != null && values.length > 0) {
                    for (String value : values) {
                        sb.append(",").append(value);
                    }
                }
                String str = sb != null && sb.length() > 0 ? sb.substring(1).toString() : sb.toString();
                attr.setProductAttrValue(str);
                productAttrService.updateProductAttr(attr);
            }

            if (!StringUtils.isEmpty(toDeleteSkuIds)) {
                String[] str = toDeleteSkuIds.split(",");
                for (String s : str) {
                    productSkuService.deleteProductSku(Long.valueOf(s));
                }
            }
            if ((skuCheckedId != null && skuCheckedId.size() > 0)
                    || (oldSkuCheckedId == null && skuCheckedId == null)) {
                this.insertProductSku(productId, productNo, skuCheckedId);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateDefinitionAttrValue(Long productId, List<ProductAttr> productAttrList)
            throws ServiceException {
        ProductAttr attr = new ProductAttr();
        attr.setProductId(productId);
        attr.setProductAttrType(ProductAttrType.DEFINITION.getType());
        try {
            productAttrService.deleteProductAttr(attr);
            if (productAttrList != null && productAttrList.size() > 0) {
                for (ProductAttr productAttr : productAttrList) {
                    productAttr.setProductId(productId);
                    productAttr.setProductAttrType(ProductAttrType.DEFINITION.getType());
                    productAttrService.addProductAttr(productAttr);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateOperationAttrValue(Long productId, Long[] operationAttrIds) throws ServiceException {
        ProductAttr attr = new ProductAttr();
        attr.setProductId(productId);
        attr.setProductAttrType(ProductAttrType.OPERATION.getType());
        try {
            productAttrService.deleteProductAttr(attr);
            if (operationAttrIds != null && operationAttrIds.length > 0) {
                for (Long operationAttrId : operationAttrIds) {
                    ProductAttr productAttr = new ProductAttr();
                    productAttr.setProductId(productId);
                    productAttr.setProductAttrType(ProductAttrType.OPERATION.getType());
                    productAttr.setRelateAttrId(operationAttrId);
                    OperationAttr operationAttr = operationAttrService.queryOperationAttr(operationAttrId);
                    productAttr.setProductAttrName(operationAttr.getOperationAttrName());
                    productAttr.setProductAttrValue(operationAttr.getOperationAttrId().toString());
                    productAttr.setIsNav(operationAttr.getIsNav());
                    productAttrService.addProductAttr(productAttr);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean auditProductById(Long productId) throws ServiceException {
        if (productId == null)
            return false;
        Product product = new Product();
        product.setId(productId);
        product.setStatus(ProductStatus.AUDIT.getStatus());

        try {
            int count = productDao.updateByPrimaryKey(product);
            return count >= 1;
        } catch (Exception e) {
            logger.error("产品审核失败，", e);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage deleteProductById(Long productId) throws ServiceException {
        ResultMessage message = new ResultMessage();
        message.setIsSuccess(true);
        message.setMessage("产品删除成功!");

        Product product = new Product();
        product.setId(productId);
        try {
            List<Product> productList = productDao.selectSkuByProductId(product);
            if (CollectionUtils.isEmpty(productList)) {
                message.setIsSuccess(false);
                message.setMessage("产品未找到，数据错误!");
                return message;
            }

            product = productList.get(0);
            List<ProductSku> skuList = product.getProductSkus();
            // 检查SKU数量
            message = checkSkuForDelProduct(skuList, message);
            // SKU数量大于1个，则无法删除产品
            if (!message.getIsSuccess())
                return message;

            // 检查SKU库存
            message = checkStockForDelProduct(skuList, message);
            // 有库存则无法删除
            if (!message.getIsSuccess())
                return message;

            // 检查SKU是否被产品关联引用
            message = checkProductRelationForDelProduct(skuList, message);
            if (!message.getIsSuccess())
                return message;

            // 检查产品是否被栏目引用
            message = checkSectionsDetailForDelProduct(product, message);
            if (!message.getIsSuccess())
                return message;

            // 检查SKU是否被优惠券产品表COUPON_PRODUCT表引用
            message = checkCouponForDelProduct(skuList, message);
            if (!message.getIsSuccess())
                return message;

            // 删除产品属性
            ProductAttr productAttr = new ProductAttr();
            productAttr.setProductId(productId);
            productAttrService.deleteProductAttr(productAttr);

            // 删除此产品的最后一个PRODUCT_SKU,PRODUCT_SKU_ATTR
            productSkuService.deleteProductSku(skuList.get(0).getProductSkuId());

            // 删除产品
            long count = productDao.deleteProductById(productId);
            if (count < 1) {
                throw new ServiceException("产品删除失败!");
            }

            // 删除图片（因为要同时删除服务器图片文件，因此放到最后，等产品相关数据全部删除完）
            message = productImageService.deleteImagesBySkuId(skuList.get(0), message);
            if (!message.getIsSuccess())
                return message;
        } catch (Exception e) {
            logger.error("系统错误,产品删除失败!", e);
            throw new ServiceException(e);
        }

        return message;
    }

    /**
     * SKU只有唯一一个返回true
     *
     * @param skuList
     * @param message
     * @return
     * @throws ServiceException
     */
    private ResultMessage checkSkuForDelProduct(List<ProductSku> skuList, ResultMessage message)
            throws ServiceException {
        if (skuList != null && skuList.size() > 1) {
            message.setIsSuccess(false);
            message.setMessage("产品SKU数量大于1,无法直接删除!");
            return message;
        }
        return message;
    }

    @Override
    public ResultMessage checkStockForDelProduct(List<ProductSku> skuList, ResultMessage message)
            throws ServiceException {
        ProductSku productSku = skuList.get(0);

        try {
            List<ProductStock> stockList = productStockService.selectStockListBySkuId(productSku.getProductSkuId());
            if (stockList != null && !stockList.isEmpty()) {
                message.setIsSuccess(false);
                message.setMessage("产品SKU：" + productSku.getProductSkuCode() + " 有库存记录，无法删除!");
                return message;
            }
            return message;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage checkProductRelationForDelProduct(List<ProductSku> skuList, ResultMessage message)
            throws ServiceException {
        ProductSku productSku = skuList.get(0);

        try {
            List<ProductRelationAndDetail> prdList = productRelationService
                    .selectProductRelationAndDetailBySkuId(productSku.getProductSkuId());
            if (CollectionUtils.isNotEmpty(prdList)) {
                message.setIsSuccess(false);
                message.setMessage("产品SKU：" + productSku.getProductSkuCode() + " 有套餐引用，无法删除!");
                return message;
            }
            return message;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 检查产品是否与栏目明细关联
     *
     * @param product
     * @param message
     * @return
     * @throws ServiceException
     */
    private ResultMessage checkSectionsDetailForDelProduct(Product product, ResultMessage message)
            throws ServiceException {
        List<SectionsDetail> sDetailList = sectionsDetailService.selectSectionsDetailByProductId(product.getId());
        if (CollectionUtils.isNotEmpty(sDetailList)) {
            message.setIsSuccess(false);
            message.setMessage("产品：" + product.getName() + " 有栏目引用，无法删除!");
            return message;
        }
        return message;
    }

    /**
     * 优惠券产品表COUPON_PRODUCT表中不包含sku返回true
     *
     * @param skuList
     * @return
     * @throws ServiceException
     */
    @Override
    public ResultMessage checkCouponForDelProduct(List<ProductSku> skuList, ResultMessage message)
            throws ServiceException {
        String skuCode = skuList.get(0).getProductSkuCode();
        List<CouponProduct> cpList = productSkuService.selectCouponProductBySkuId(skuCode);
        if (CollectionUtils.isNotEmpty(cpList)) {
            message.setIsSuccess(false);
            message.setMessage("产品SKU：" + skuCode + " 有优惠券引用，无法删除!");
            return message;
        }
        return message;
    }

    @Override
    public List<ProductSku> getProductSkuAttrList(Long productId) throws ServiceException {
        ProductSku productSku = new ProductSku();
        productSku.setProductId(productId);
        try {
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
            return productSkuList;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean submitTheAudit(Long productId) throws ServiceException {
        Product product = new Product();
        product.setId(productId);

        try {
            List<Product> productList = productDao.selectSkuByProductId(product);
            if (productList != null && productList.size() > 0) {
                product = productList.get(0);
                product.setStatus(ProductStatus.UNAUDIT.getStatus());// 待审核
                int count = productDao.updateByPrimaryKey(product);
                if (count < 1)
                    return false;
            }
        } catch (SQLException e) {
            logger.error("产品提交审核失败，", e);
            return false;
        }
        return true;
    }

    @Override
    public List getCanUpShelfPro(Page pageParam, Product product) {
        ProductExample prodExam = new ProductExample();
        List canProd = null;

        try {
            // 查询上架状态为2 4 5 的产品。
            List statuList = new ArrayList();
            statuList.add(0, ProductStatus.AUDIT.getStatus());
            statuList.add(1, ProductStatus.DOWN.getStatus());
            statuList.add(2, ProductStatus.SYSDOWN.getStatus());
            if (product == null) {
                prodExam.createCriteria().andStatusIn(statuList);
            } else if (!StringUtils.isEmpty(product.getProductNo()) || !StringUtils.isEmpty(product.getName())
                    || !StringUtils.isEmpty(product.getKeyword()) || product.getBrandId() != 0
                    || product.getCategoryId() != 0) {
                // 产品编码
                String productNo = StringUtils.isEmpty(product.getProductNo()) ? "%%"
                        : "%" + product.getProductNo() + "%";
                // 产品名称
                String productName = StringUtils.isEmpty(product.getName()) ? "%%" : "%" + product.getName() + "%";
                // 类目ID
                Long categoryId = product.getCategoryId() == null || product.getCategoryId() == 0 ? null
                        : product.getCategoryId();
                // 关键字
                String keywords = StringUtils.isEmpty(product.getKeyword()) ? "%%"
                        : "%" + product.getKeyword() + "%";
                // 品牌
                Long brandId = product.getBrandId() == null || product.getBrandId() == 0 ? null
                        : product.getBrandId();
                if (categoryId != null) {
                    prodExam.createCriteria().andCategoryIdEqualTo(categoryId).andStatusIn(statuList)
                            .andProductNoLike(productNo).andProductNameLike(productName);
                }
                if (brandId != null) {
                    prodExam.createCriteria().andBrandIdEqualTo(brandId).andStatusIn(statuList)
                            .andProductNoLike(productNo).andProductNameLike(productName);
                }
                if (!keywords.equals("%%")) {
                    prodExam.createCriteria().andKeywordLike(keywords).andStatusIn(statuList)
                            .andProductNoLike(productNo).andProductNameLike(productName);
                } else {
                    prodExam.createCriteria().andStatusIn(statuList).andProductNoLike(productNo)
                            .andProductNameLike(productName);
                }
            } else {
                prodExam.createCriteria().andStatusIn(statuList);
            }
            int pageIndex = pageParam.getPageNo();
            if (pageIndex == 0)
                pageIndex = 1;
            int pageSize = pageParam.getPageSize();
            int skip = (pageIndex - 1) * pageSize;
            int max = pageSize;

            canProd = productDao.selectByExample(prodExam, skip, max);
            // 总页数
            List list = productDao.selectCountShelf(prodExam);
            Integer recs = Integer.parseInt(list.get(0).toString());
            pageParam.setRecordCount(recs);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return canProd;
    }

    @Override
    public ResultMessage checkProductName(String productName) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setIsSuccess(true);

        try {
            int count = productDao.selectProductByName(productName);
            if (count > 0) {
                resultMessage.setIsSuccess(false);
                resultMessage.setMessage("产品名称：" + productName + "重名,请重新输入!");
            }
            return resultMessage;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkIntro(String productIntroduction) throws ServiceException {
        try {
            // 查询审核关键字
            List<FilterField> filterList = filterFieldDao.selectAllFilter();
            KeywordFilter keyFilter = new KeywordFilter();

            // 循环关键字
            String[] keyWords = new String[filterList.size()];
            for (int j = 0; j < filterList.size(); j++) {
                // 审核内容
                keyWords[j] = filterList.get(j).getFieldName();
            }
            keyFilter.addKeywords(keyWords);
            keyFilter.setMatchType(2);
            // 如果不通过
            return !keyFilter.isContentKeyWords(productIntroduction);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage batchAuditProduct(List<Long> productIdList) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setIsSuccess(true);
        try {
            int count = productDao.batchAuditProduct(productIdList);
            if (count < 1) {
                throw new ServiceException("批量审核失败!");
            }
        } catch (Exception e) {
            logger.error("系统错误,批量审核失败!", e);
            throw new ServiceException(e);
        }

        return resultMessage;
    }

    @Override
    public void changeProductInfoNotify(List<Long> ids, String opType) throws ServiceException {
        if (ids == null || ids.isEmpty() || StringUtils.isBlank(opType)) {
            throw new ServiceException("参数为空!");
        }
        logger.info("产品信息变更通知MQ，操作类型：{}，ids：{}", new Object[]{opType, ids});
        sendMessageSender.sendMessage("2000", ids, opType);
    }

    @Override
    public Map<Long, ProductAttr> checkOperationAttr(List<Long> productIdList) throws ServiceException {
        try {
            return productDao.checkOperationAttr(productIdList);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void executeUpdateCachePrice(List<Long> skuIdList) throws ServiceException {
        try {
            taskExecutor.execute(new ProductPriceHandler(skuIdList));
        } catch (TaskRejectedException e) {
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
            }
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCachePrice(List<Long> skuIdList) throws ServiceException {
        try {
            taskExecutor.execute(new ProductPriceCache(skuIdList));
        } catch (TaskRejectedException e) {
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
            }
            throw new ServiceException(e);
        }
    }

    @Override
    public Product queryProductByProductNo(String productNo) throws ServiceException {
        try {
            return productDao.queryProductByProductNo(productNo);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String previewProductInfoPage(String productId) throws ServiceException {
        try {
            return detailPublishService.viewDetailPublish(Integer.valueOf(productId));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void searchPage(Page page, Product product, String type, Integer loginUserId)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProductExample pe = new ProductExample();
        Criteria criteria = pe.createCriteria();
        // 当前用户id，作为通过渠道过滤的条件
        pe.setUserId(String.valueOf(loginUserId));
        if (StringUtils.isNotBlank(product.getProductTitle())) {
            criteria.andProductTitleLike("%" + product.getProductTitle().trim() + "%");
        }
        if (StringUtils.isNotBlank(product.getKeyword())) {
            criteria.andKeywordLike("%" + product.getKeyword().trim() + "%");
        }
        if (StringUtils.isNotBlank(product.getProductNo())) {
            criteria.andProductNoLike("%" + product.getProductNo().trim() + "%");
        }
        if (product.getCategoryId() != null && product.getCategoryId() != 0) { // 产品所属类目
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        }
        if (product.getMCategoryId() != null && product.getCategoryId() == null) {
            pe.setmCategoryId(product.getMCategoryId());
        }
        if (product.getBCategoryId() != null && product.getMCategoryId() == null) {
            pe.setbCategoryId(product.getBCategoryId());
        }
        // 品牌模糊查询
        if (StringUtils.isNotBlank(product.getSearchBrandName())) {
            pe.setSearchBrandName(product.getSearchBrandName());
        }
        // 品牌
        if (product.getBrandId() != null && product.getBrandId() != 0) {
            criteria.andBrandIdEqualTo(product.getBrandId());
        }
        // 类目名称模糊查询
        if (StringUtils.isNotBlank(product.getSearchCategoryName())) {
            pe.setSearchCategoryName(product.getSearchCategoryName());
        }
        if (StringUtils.isNotBlank(product.getMCategoryName())) {
            pe.setMCategoryName(product.getMCategoryName());
        }
        if (StringUtils.isNotBlank(product.getBCategoryName())) {
            pe.setBCategoryName(product.getBCategoryName());
        }
        if (StringUtils.isNotBlank(type)) {
            if (ProductListType.SECTIONS.equals(type)) {
                criteria.andStatusEqualTo(ProductStatus.UP.getStatus());
            } else if (StringUtils.isNotBlank(product.getStatus())) {
                // 产品状态
                criteria.andStatusEqualTo(product.getStatus());
            }

            if (ProductListType.RECHECK.equals(type)) {
                // 产品信息复核 不显示康美自营的产品 bug[2810] bug[2875]只显示入驻供应商的产品
                criteria.andShopCodeIsSupplierType();
                criteria.andStatusEqualTo(ProductStatus.UP.getStatus());
            } else if (ProductListType.ILLEGAL.equals(type)) {
                criteria.andStatusEqualTo(ProductStatus.ILLEGAL_DOWN.getStatus());
            } else if (type.contains(ProductListType.PRICE)) {
                criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(), ProductStatus.SYSDOWN.getStatus());
            } else if (type.contains(ProductListType.WEIGHT)) {
                criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(), ProductStatus.SYSDOWN.getStatus());
            }
        } else if (StringUtils.isNotBlank(product.getStatus())) {
            // 产品状态
            criteria.andStatusEqualTo(product.getStatus());
        }
        if (StringUtils.isNotBlank(product.getSupplierNameForSearch())) {
            if ("康美自营".equals(product.getSupplierNameForSearch())) {
                // 若是选择康美自营
                criteria.andShopCodeEqualTo("221");
            } else {
                // 所属商家模糊查询
                pe.setSupplierNameForSearch(product.getSupplierNameForSearch());
            }
        }
        // 产品类型过滤
        if (product.getProductType() != null) {
            criteria.andProductTypeEqualTo(product.getProductType());
        }
        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("ARCHIVE_TIME DESC");
        } else {
            pe.setOrderByClause("PRODUCT_ID DESC");
        }

        try {
            int count = productDao.countByExample(pe);
            List<Product> productList = productDao.selectByExample(pe, skip, max);
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
    public void searchPageByUserIdForPrice(Page page, Product product, String type, Integer loginUserId)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProductExample pe = new ProductExample();
        Criteria criteria = pe.createCriteria();
        // 当前用户id，作为通过渠道过滤的条件
        pe.setUserId(String.valueOf(loginUserId));
        if (StringUtils.isNotBlank(product.getProductTitle())) {
            criteria.andProductTitleLike("%" + product.getProductTitle().trim() + "%");
        }
        if (StringUtils.isNotBlank(product.getKeyword())) {
            criteria.andKeywordLike("%" + product.getKeyword().trim() + "%");
        }
        if (StringUtils.isNotBlank(product.getProductNo())) {
            criteria.andProductNoLike("%" + product.getProductNo().trim() + "%");
        }
        // 产品所属类目
        if (product.getCategoryId() != null && product.getCategoryId() != 0l) {
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        }
        if (product.getMCategoryId() != null && product.getCategoryId() == null) {
            pe.setmCategoryId(product.getMCategoryId());
        }
        if (product.getBCategoryId() != null && product.getMCategoryId() == null) {
            pe.setbCategoryId(product.getBCategoryId());
        }
        // 类目名称模糊查询
        if (StringUtils.isNotBlank(product.getSearchCategoryName())) {
            pe.setSearchCategoryName(product.getSearchCategoryName());
        }
        if (StringUtils.isNotBlank(product.getMCategoryName())) {
            pe.setMCategoryName(product.getMCategoryName());
        }
        if (StringUtils.isNotBlank(product.getBCategoryName())) {
            pe.setBCategoryName(product.getBCategoryName());
        }
        // 品牌模糊查询
        if (StringUtils.isNotBlank(product.getSearchBrandName())) {
            pe.setSearchBrandName(product.getSearchBrandName());
        }
        // 品牌
        if (product.getBrandId() != null && product.getBrandId() != 0l) {
            criteria.andBrandIdEqualTo(product.getBrandId());
        }
        if (StringUtils.isNotBlank(type)) {
            if (ProductListType.SECTIONS.equals(type)) {
                criteria.andStatusEqualTo(ProductStatus.UP.getStatus());
            } else if (StringUtils.isNotBlank(product.getStatus())) {
                // 产品状态
                criteria.andStatusEqualTo(product.getStatus());
            }
            if (type.contains(ProductListType.PRICE)) {
                criteria.andStatusIn(new ArrayList<String>(
                        Arrays.asList(new String[]{ProductStatus.DELETE.getStatus(), ProductStatus.AUDIT.getStatus(),
                                ProductStatus.UP.getStatus(), ProductStatus.DOWN.getStatus(),
                                ProductStatus.ILLEGAL_DOWN.getStatus(), ProductStatus.SYSDOWN.getStatus()})));
            } else if (type.contains(ProductListType.WEIGHT)) {
                criteria.andStatusIn(new ArrayList<String>(
                        Arrays.asList(new String[]{ProductStatus.DELETE.getStatus(), ProductStatus.AUDIT.getStatus(),
                                ProductStatus.UP.getStatus(), ProductStatus.DOWN.getStatus(),
                                ProductStatus.ILLEGAL_DOWN.getStatus(), ProductStatus.SYSDOWN.getStatus()})));
            }
        } else if (StringUtils.isNotBlank(product.getStatus())) {
            // 产品状态
            criteria.andStatusEqualTo(product.getStatus());
        }
        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("ARCHIVE_TIME DESC");
        } else {
            pe.setOrderByClause("PRODUCT_ID DESC");
        }

        try {
            int count = productDao.countByExample(pe);
            List<Product> productList = productDao.selectByExampleForPrice(pe, skip, max);
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
    public List<ProductSkuForExport> exportProductInfo(Product product, String type) throws ServiceException {
        ProductExample example = new ProductExample();
        if (StringUtils.isNotBlank(product.getProductTitle()))
            example.setProductTitle(product.getProductTitle().trim());
        if (StringUtils.isNotBlank(product.getKeyword()))
            example.setKeyWord(product.getKeyword().trim());
        if (StringUtils.isNotBlank(product.getProductNo()))
            example.setProductNo(product.getProductNo().trim());
        if (product.getCategoryId() != null && product.getCategoryId() != 0) // 产品所属类目
            example.setCategoryId(product.getCategoryId());
        if (product.getMCategoryId() != null && product.getCategoryId() == null)
            example.setmCategoryId(product.getMCategoryId());
        if (product.getBCategoryId() != null && product.getMCategoryId() == null)
            example.setbCategoryId(product.getBCategoryId());
        // 品牌模糊查询
        if (StringUtils.isNotBlank(product.getSearchBrandName())) {
            example.setSearchBrandName(product.getSearchBrandName());
        }
        if (product.getBrandId() != null && product.getBrandId() != 0) {
            example.setBrandId(product.getBrandId());
        }
        // 类目名称模糊查询
        if (StringUtils.isNotBlank(product.getSearchCategoryName()))
            example.setSearchCategoryName(product.getSearchCategoryName());
        if (StringUtils.isNotBlank(product.getMCategoryName()))
            example.setMCategoryName(product.getMCategoryName());
        if (StringUtils.isNotBlank(product.getBCategoryName()))
            example.setBCategoryName(product.getBCategoryName());
        if (StringUtils.isNotBlank(product.getSupplierNameForSearch())) {
            example.setSupplierNameForSearch(product.getSupplierNameForSearch());
        }
        if (product.getProductType() != null) {
            example.setProductType(product.getProductType());
        }

        if (StringUtils.isNotBlank(type)) {
            if (ProductListType.RECHECK.equals(type)) {
                example.setStatus(ProductStatus.UP.getStatus());
                example.setIsNotSupply("true");
            } else if (ProductListType.ILLEGAL.equals(type)) {
                example.setStatus(ProductStatus.ILLEGAL_DOWN.getStatus());
            }
        }
        if (StringUtils.isNotBlank(product.getStatus())) {
            example.setStatus(product.getStatus());
        }


        try {
            return productDao.selectByExampleForExport(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void exportExcelForProductSku(List<ProductSkuForExport> exportList) throws ServiceException {
        OutputStream ops = null;
        // 设置excel工作表的将要显示的列标题

        String[] title = {"品牌", "商品名称", "商品标题", "sku描述",
                "产品类型", "商品编码", "商品SKU编码", "SKU状态",
                "成本价", "市场价", "销售单价", "PV值",
                "上架/下架", "一级类目", "二级类目", "三级类目",
                "商家", "商家类型", "上架时间", "下架时间",
                "批注", "访问链接"};
        try {
            // 导出不存在，自动创建目录
            String exportExcelPath = ConfigurationUtil.getString("exportExcelPath");
            File dir = new File(exportExcelPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filePath = exportExcelPath + File.separator + "productinfo.xls";
            // 创建Excel工作薄
            WritableWorkbook wwb;
            ops = new FileOutputStream(filePath);
            wwb = Workbook.createWorkbook(ops);
            // 添加第一个工作表并设置第一个Sheet的名字
            WritableSheet sheet = wwb.createSheet("商品信息表", 0);

            Label label;
            WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            wcf.setBackground(Colour.YELLOW2);
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            // 将列标题循环添加到Label中
            for (int i = 0; i < title.length; i++) {
                label = new Label(i, 0, title[i], wcf);
                sheet.addCell(label);
            }
            wcf = new WritableCellFormat();
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            for (int i = 0; i < exportList.size(); i++) {
                ProductSkuForExport export = exportList.get(i);
                sheet.addCell(new Label(0, i + 1, export.getBrandName(), wcf));
                sheet.addCell(new Label(1, i + 1, export.getProductName(), wcf));
                sheet.addCell(new Label(2, i + 1, export.getProductTitle(), wcf));
                sheet.addCell(new Label(3, i + 1, export.getProductSkuDesc(), wcf));
                sheet.addCell(new Label(4, i + 1, export.getProductType(), wcf));
                sheet.addCell(new Label(5, i + 1, export.getProductNo().toString(), wcf));
                sheet.addCell(new Label(6, i + 1, export.getProductSkuCode().toString(), wcf));
                sheet.addCell(new Label(7, i + 1, export.getProductSkuStatus(), wcf));
                sheet.addCell(new Label(8, i + 1, export.getCostPrice() != null ? export.getCostPrice().toString() : "暂无", wcf));
                sheet.addCell(new Label(9, i + 1, export.getMarkPrice() != null ? export.getMarkPrice().toString() : "暂无", wcf));
                sheet.addCell(new Label(10, i + 1, export.getPrice() != null ? export.getPrice().toString() : "暂无", wcf));
                sheet.addCell(new Label(11, i + 1, export.getPvValue(), wcf));
                sheet.addCell(new Label(12, i + 1, export.getStatus(), wcf));
                sheet.addCell(new Label(13, i + 1, export.getFirstCategoryName(), wcf));
                sheet.addCell(new Label(14, i + 1, export.getSecondCategoryName(), wcf));
                sheet.addCell(new Label(15, i + 1, export.getThirdCategoryName(), wcf));
                sheet.addCell(new Label(16, i + 1, export.getSupplierName(), wcf));
                sheet.addCell(new Label(17, i + 1, export.getSupplierType(), wcf));
                sheet.addCell(new Label(18, i + 1, export.getUpTime() != null ? dateFormat.format(export.getUpTime()) : "", wcf));
                sheet.addCell(new Label(19, i + 1, export.getArchiveTime() != null ? dateFormat.format(export.getArchiveTime()) : "", wcf));
                sheet.addCell(new Label(20, i + 1, export.getPostil(), wcf));
                sheet.addCell(new Label(21, i + 1,
                        "http://www.kmb2b.com/products/" + export.getProductSkuId() + ".shtml",
                        wcf));
            }

            wwb.write();
            wwb.close();
        } catch (Exception e) {
            throw new ServiceException(e);
        } finally {
            try {
                if (ops != null) {
                    ops.flush();
                    ops.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void searchCertificateProductPageByUserId(Page page, Product product, String type, Integer loginUserId)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProductExample pe = new ProductExample();
        Criteria criteria = pe.createCriteria();
        // 当前用户id，作为通过渠道过滤的条件
        pe.setUserId(String.valueOf(loginUserId));
        if (StringUtils.isNotBlank(product.getProductTitle())) {
            criteria.andProductTitleLike("%" + product.getProductTitle().trim() + "%");
        }
        if (StringUtils.isNotBlank(product.getKeyword())) {
            criteria.andKeywordLike("%" + product.getKeyword().trim() + "%");
        }
        if (StringUtils.isNotBlank(product.getProductNo())) {
            criteria.andProductNoLike("%" + product.getProductNo().trim() + "%");
        }
        if (product.getCategoryId() != null && product.getCategoryId() != 0) // 产品所属类目
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        if (product.getMCategoryId() != null && product.getCategoryId() == null)
            pe.setmCategoryId(product.getMCategoryId());
        if (product.getBCategoryId() != null && product.getMCategoryId() == null)
            pe.setbCategoryId(product.getBCategoryId());
        // 品牌模糊查询
        if (StringUtils.isNotBlank(product.getSearchBrandName()))
            pe.setSearchBrandName(product.getSearchBrandName());
        // 类目名称模糊查询
        if (StringUtils.isNotBlank(product.getSearchCategoryName()))
            pe.setSearchCategoryName(product.getSearchCategoryName());
        if (StringUtils.isNotBlank(product.getMCategoryName()))
            pe.setMCategoryName(product.getMCategoryName());
        if (StringUtils.isNotBlank(product.getBCategoryName()))
            pe.setBCategoryName(product.getBCategoryName());
        if (product.getBrandId() != null && product.getBrandId() != 0) // 品牌
            criteria.andBrandIdEqualTo(product.getBrandId());
        if ("OTC".equals(type)) {
            criteria.andProductTypeEqualTo(ProductType.OTC.getKey());
        }
        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("ARCHIVE_TIME DESC");
        } else {
            pe.setOrderByClause("PRODUCT_ID DESC");
        }

        try {
            int count = productDao.countByExample(pe);
            List<Product> productList = productDao.selectForCertificateByExample(pe, skip, max);
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
    public List<Integer> getProductIdByBrandId(Long brandId) throws ServiceException {
        try {
            return productDao.getProductIdByBrandId(brandId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage upShelfForSupplier(List<Long> supplierIds) throws ServiceException {
        ResultMessage message = new ResultMessage();
        try {
            List<Product> productList = productDao.getProductIdListForSuppliers(supplierIds);
            // 通知上架产品给搜索引擎
            List<Integer> cms_ids = productList.stream()
                    .map(p -> Integer.valueOf(p.getId().toString()))
                    .collect(Collectors.toList());

            // CMS上架接口
            cmsProductUpShelfService.productUpShelfByCms(cms_ids);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        message.setIsSuccess(true);
        return message;
    }

    @Override
    public ResultMessage downShelfForSupplier(List<Long> supplierIds) throws ServiceException {
        ResultMessage result = new ResultMessage();
        try {
            List<Product> productList = productDao.getProductIdListForSuppliers(supplierIds);
            List<Product> list = new ArrayList<Product>();
            Product prodTmp = null;
            Date archiveTime = new Date();
            for (Product p : productList) {
                prodTmp = new Product();
                prodTmp.setId(p.getId());
                prodTmp.setStatus(ProductStatus.DOWN.getStatus());
                prodTmp.setArchiveTime(archiveTime);
                list.add(prodTmp);
            }
            if (!downShelf(list)) {
                result.setMessage("下架指定供应商ID失败！");
                return result;
            }

            // 通知下架产品给搜索引擎
            List<Long> deleteProductIdList = list.stream().map(Product::getId).collect(Collectors.toList());
            List<Long> deleteSkuIdList = productSkuService.selectSkuIdsByProductIdList(deleteProductIdList);
            changeProductInfoNotify(deleteSkuIdList, MsgOperation.DELETE.getType());
            productSkuService.updateProductSkuCache(deleteSkuIdList);
        } catch (Exception e) {
            logger.error("通知下架产品给搜索引擎失败。", e);
            result.setMessage("通知下架产品给搜索引擎失败！");
            return result;
        }

        result.setIsSuccess(true);
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage illegalUp(Product product) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        try {

            Long id = Long.valueOf(product.getProductId());
            product.setId(id);
            if (!checkProduct(product, resultMessage)) {
                return resultMessage;
            }

            int i = productDao.updateIllegalProductUpShelfById(id);
            if (i < 0) {
                resultMessage.setMessage("违规产品重新上架失败!");
                resultMessage.setIsSuccess(Boolean.FALSE);
                return resultMessage;
            }
        } catch (SQLException e) {
            logger.error("违规产品重新上架失败,产品id为{},错误信息为：{}", new Object[]{product.getProductId(), e});
            throw new ServiceException(e);
        } catch (Exception e) {
            logger.error("检查产品能否上架失败。");
            throw new ServiceException(e);
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
        resultMessage.setMessage("违规产品重新上架成功!");
        return resultMessage;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage illegalDown(Product product) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        try {
            int i = productDao.updateIllegalProductDownShelfById(product);
            if (i < 0) {
                resultMessage.setMessage("违规产品下架失败!");
                return resultMessage;
            }
        } catch (SQLException e) {
            logger.error("违规产品下架失败,产品id为{},原因为：{}, 错误信息为：{}",
                    new Object[]{product.getProductId(), product.getReasons(), e});
            throw new ServiceException(e);
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
        resultMessage.setMessage("违规产品下架成功!");
        return resultMessage;
    }

    @Override
    public void searchPageByUserIdForPV(Page page, Product product, String type, Integer loginUserId)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProductExample pe = new ProductExample();
        Criteria criteria = pe.createCriteria();
        // 当前用户id，作为通过渠道过滤的条件
        pe.setUserId(String.valueOf(loginUserId));
        if (StringUtils.isNotBlank(product.getProductTitle())) {
            criteria.andProductTitleLike("%" + product.getProductTitle().trim() + "%");
        }
        if (StringUtils.isNotBlank(product.getKeyword())) {
            criteria.andKeywordLike("%" + product.getKeyword().trim() + "%");
        }
        if (StringUtils.isNotBlank(product.getProductNo())) {
            criteria.andProductNoLike("%" + product.getProductNo().trim() + "%");
        }
        // 产品所属类目
        if (product.getCategoryId() != null && product.getCategoryId() != 0l) {
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        }
        if (product.getMCategoryId() != null && product.getCategoryId() == null) {
            pe.setmCategoryId(product.getMCategoryId());
        }
        if (product.getBCategoryId() != null && product.getMCategoryId() == null) {
            pe.setbCategoryId(product.getBCategoryId());
        }
        // 类目名称模糊查询
        if (StringUtils.isNotBlank(product.getSearchCategoryName())) {
            pe.setSearchCategoryName(product.getSearchCategoryName());
        }
        if (StringUtils.isNotBlank(product.getMCategoryName())) {
            pe.setMCategoryName(product.getMCategoryName());
        }
        if (StringUtils.isNotBlank(product.getBCategoryName())) {
            pe.setBCategoryName(product.getBCategoryName());
        }
        // 品牌模糊查询
        if (StringUtils.isNotBlank(product.getSearchBrandName())) {
            pe.setSearchBrandName(product.getSearchBrandName());
        }
        // 品牌
        if (product.getBrandId() != null && product.getBrandId() != 0l) {
            criteria.andBrandIdEqualTo(product.getBrandId());
        }
        if (StringUtils.isNotBlank(type)) {
            if (ProductListType.SECTIONS.equals(type)) {
                criteria.andStatusEqualTo(ProductStatus.UP.getStatus());
            } else if (StringUtils.isNotBlank(product.getStatus())) {
                // 产品状态
                criteria.andStatusEqualTo(product.getStatus());
            }

            if (type.contains(ProductListType.PRICE)) {
                criteria.andStatusIn(new ArrayList<String>(
                        Arrays.asList(new String[]{ProductStatus.DELETE.getStatus(), ProductStatus.AUDIT.getStatus(),
                                ProductStatus.UP.getStatus(), ProductStatus.DOWN.getStatus(),
                                ProductStatus.ILLEGAL_DOWN.getStatus(), ProductStatus.SYSDOWN.getStatus()})));
            } else if (type.contains(ProductListType.WEIGHT)) {
                criteria.andStatusIn(new ArrayList<String>(
                        Arrays.asList(new String[]{ProductStatus.DELETE.getStatus(), ProductStatus.AUDIT.getStatus(),
                                ProductStatus.UP.getStatus(), ProductStatus.DOWN.getStatus(),
                                ProductStatus.ILLEGAL_DOWN.getStatus(), ProductStatus.SYSDOWN.getStatus()})));
            }
        } else if (StringUtils.isNotBlank(product.getStatus())) {
            // 产品状态
            criteria.andStatusEqualTo(product.getStatus());
        }

        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("ARCHIVE_TIME DESC");
        } else {
            pe.setOrderByClause("PRODUCT_ID DESC");
        }

        try {
            int count = productDao.countByExampleForPV(pe);
            List<Product> productList = productDao.selectByExampleForPV(pe, skip, max);
            productList.stream().filter(pd -> StringUtils.isNotBlank(pd.getProductTitle())).forEach(pd -> {
                pd.setProductTitle(pd.getProductTitle().replaceAll(" ", "&nbsp;"));
            });
            page.setDataList(productList);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
