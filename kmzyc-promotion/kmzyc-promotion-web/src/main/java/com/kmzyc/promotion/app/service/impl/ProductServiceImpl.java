package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.cms.remote.service.DetailPublishService;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.bean.ResultMessage;
import com.kmzyc.promotion.app.dao.ProductDao;
import com.kmzyc.promotion.app.enums.CategoryAttrInputType;
import com.kmzyc.promotion.app.enums.ProductStatus;
import com.kmzyc.promotion.app.enums.ProductType;
import com.kmzyc.promotion.app.fobject.ProductResultMap;
import com.kmzyc.promotion.app.jms.SearchMessageSender;
import com.kmzyc.promotion.app.service.CategoryAttrValueService;
import com.kmzyc.promotion.app.service.CouponProductService;
import com.kmzyc.promotion.app.service.ProductAttrService;
import com.kmzyc.promotion.app.service.ProductRelationService;
import com.kmzyc.promotion.app.service.ProductService;
import com.kmzyc.promotion.app.service.ProductSkuAttrService;
import com.kmzyc.promotion.app.service.ProductSkuService;
import com.kmzyc.promotion.app.threadHandler.ProductPriceHandler;
import com.kmzyc.promotion.app.vobject.CategoryAttrValue;
import com.kmzyc.promotion.app.vobject.CouponProduct;
import com.kmzyc.promotion.app.vobject.Product;
import com.kmzyc.promotion.app.vobject.ProductAttr;
import com.kmzyc.promotion.app.vobject.ProductExample;
import com.kmzyc.promotion.app.vobject.ProductExample.Criteria;
import com.kmzyc.promotion.app.vobject.ProductRelationAndDetail;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.ProductSkuAttr;
import com.kmzyc.promotion.exception.ServiceException;

@Service("productServiceImpl")
@SuppressWarnings("unchecked")
public class ProductServiceImpl implements ProductService {

    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Resource
    private DetailPublishService detailPublishService;

    @Resource
    ProductDao productDao;

    @Resource
    private SearchMessageSender sendMessageSender;

    @Resource
    private TaskExecutor taskExecutor;

    @Resource(name = "productAttrService")
    private ProductAttrService productAttrService;

    @Resource(name = "productSkuService")
    private ProductSkuService productSkuService;

    @Resource(name = "productSkuAttrService")
    private ProductSkuAttrService productSkuAttrService;

    @Resource
    private ProductRelationService productRelationService;

    @Resource
    private CouponProductService couponProductService;

    @Override
    public List<Product> getProducts(Product product) {
        return productDao.getProducts(product);
    }

    /**
     * 类目属性值业务接口
     */
    @Resource(name = "categoryAttrValueService")
    private CategoryAttrValueService categoryAttrValueService;

    /**
     * 分页 查找数据
     * 
     * @param page
     * @param prod
     */
    @Override
    public void searchPage(Page page, Product product, String type) throws Exception {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;
        ProductExample pe = new ProductExample();
        Criteria criteria = pe.createCriteria();

        if (StringUtils.isNotBlank(product.getName()))
            criteria.andProcuctNameLike(
                    new StringBuffer("%").append(product.getName().trim()).append("%").toString());
        if (StringUtils.isNotBlank(product.getKeyword()))
            criteria.andKeywordLike(new StringBuffer("%").append(product.getKeyword().trim())
                    .append("%").toString());
        if (StringUtils.isNotBlank(product.getProductNo()))
            criteria.andProductNoLike(new StringBuffer("%").append(product.getProductNo().trim())
                    .append("%").toString());

        if (product.getCategoryId() != null && product.getCategoryId() != 0) // 产品所属类目
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        if (type != null && "sections".equals(type)) {
            criteria.andStatusEqualTo(ProductStatus.UP.getStatus());
        } else {
            if (StringUtils.isNotBlank(product.getStatus())) // 产品状态
                criteria.andStatusEqualTo(product.getStatus());
        }

        if (product.getBrandId() != null && product.getBrandId() != 0) // 品牌
            criteria.andBrandIdEqualTo(product.getBrandId());

        if (type != null && type.indexOf("price") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(),
                    ProductStatus.SYSDOWN.getStatus());
        }
        if (type != null && type.indexOf("weight") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(),
                    ProductStatus.SYSDOWN.getStatus());
        }
        // 添加了显示中药材网的价格
        if (type != null && type.indexOf("zycPrice") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(),
                    ProductStatus.SYSDOWN.getStatus());
        }
        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("ARCHIVE_TIME DESC");
        } else {
            pe.setOrderByClause("PRODUCT_ID DESC");
        }
        int count = productDao.countByExample(pe);
        List<Product> productList = productDao.selectByExample(pe, skip, max);
        page.setDataList(productList);
        page.setRecordCount(count);
    }

    @Override
    public void searchSkuPage(Page page, Product product, String type, Integer loginUserId)
            throws Exception {
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
            criteria.andProcuctNameLike(
                    new StringBuffer("%").append(product.getName().trim()).append("%").toString());
        if (StringUtils.isNotBlank(product.getKeyword()))
            criteria.andKeywordLike(new StringBuffer("%").append(product.getKeyword().trim())
                    .append("%").toString());
        if (StringUtils.isNotBlank(product.getProductNo()))
            criteria.andProductNoLike(new StringBuffer("%").append(product.getProductNo().trim())
                    .append("%").toString());

        if (product.getCategoryId() != null && product.getCategoryId() != 0) // 产品所属类目
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        if (type != null && "sections".equals(type)) {
            criteria.andStatusEqualTo(ProductStatus.UP.getStatus());
        } else {
            if (StringUtils.isNotBlank(product.getStatus())) // 产品状态
                criteria.andStatusEqualTo(product.getStatus());
        }

        if (product.getBrandId() != null && product.getBrandId() != 0) // 品牌
            criteria.andBrandIdEqualTo(product.getBrandId());

        if (type != null && type.indexOf("price") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(),
                    ProductStatus.SYSDOWN.getStatus());
        }
        if (type != null && type.indexOf("weight") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(),
                    ProductStatus.SYSDOWN.getStatus());
        }
        // 添加了显示中药材网的价格
        if (type != null && type.indexOf("zycPrice") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(),
                    ProductStatus.SYSDOWN.getStatus());
        }
        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("ARCHIVE_TIME DESC");
        } else {
            pe.setOrderByClause("PRODUCT_ID DESC");
        }
        int count = productDao.countBySkuExample(pe);
        List<ProductResultMap> productList = productDao.selectBySkuExample(pe, skip, max);

        page.setDataList(productList);
        page.setRecordCount(count);
    }

    /**
     * 根据编号查找产品
     * 
     * @param id
     * @throws ServiceException
     */
    @Override
    public Product findProductById(Long id) throws Exception {
        return productDao.selectByPrimaryKey(id);
    }

    // 符合产品上架的状态（AUDIT("2","已审核,待上架"),DOWN("4","已下架"),SYSDOWN("5","系统下架")）
    @Override
    public ResultMessage checkUpShelf(Product product, ResultMessage resultMsg) {
        // 符合产品上架的状态（AUDIT("2","已审核,待上架"),DOWN("4","已下架"),SYSDOWN("5","系统下架")）
        if (!ProductStatus.AUDIT.getStatus().equals(product.getStatus())
                && !ProductStatus.DOWN.getStatus().equals(product.getStatus())
                && !ProductStatus.SYSDOWN.getStatus().equals(product.getStatus())) {
            resultMsg.setIsSuccess(false);
            resultMsg.setMessage("产品：" + product.getName() + "的现有状态不符合上架要求，上架失败!");
            return resultMsg;
        }
        return resultMsg;
    }

    @Override
    public void searchPageByB2B(Page page, Product product) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;

        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProductExample pe = new ProductExample();
        Criteria criteria = pe.createCriteria();

        if (StringUtils.isNotBlank(product.getName()))
            criteria.andProcuctNameLike(
                    new StringBuffer("%").append(product.getName()).append("%").toString());
        if (StringUtils.isNotBlank(product.getKeyword()))
            criteria.andKeywordLike(
                    new StringBuffer("%").append(product.getKeyword()).append("%").toString());
        if (StringUtils.isNotBlank(product.getProductNo()))
            criteria.andProductNoLike(
                    new StringBuffer("%").append(product.getProductNo()).append("%").toString());

        if (product.getCategoryId() != null && product.getCategoryId() != 0) // 产品所属类目
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        if (StringUtils.isNotBlank(product.getStatus())) // 产品状态
            criteria.andStatusEqualTo(product.getStatus());
        if (product.getBrandId() != null && product.getBrandId() != 0) // 品牌
            criteria.andBrandIdEqualTo(product.getBrandId());

        pe.setOrderByClause("PRODUCT_ID DESC");

        try {
            int count = productDao.countByExample(pe);
            List<Product> productList = productDao.selectByExample(pe, skip, max);

            page.setDataList(productList);
            page.setRecordCount(count);
        } catch (SQLException e) {
            logger.error("productId:{} searchPageByB2B方法出错", product.getId(), e);
            throw new ServiceException(e);
        }
    }

    /**
     * 设置产品属性值
     * 
     * @param productAttrList 产品属性基本信息列表
     * @return
     * @throws ServiceException 异常
     */
    @Override
    public void setProductAttrValue(List<ProductAttr> productAttrList) throws Exception {
        for (ProductAttr productAttr : productAttrList) {
            if (productAttr.getProductAttrType() == 1) {
                if (CategoryAttrInputType.RADIO.getType().compareTo(productAttr.getInputType()) == 0
                        || CategoryAttrInputType.SELECT.getType()
                                .compareTo(productAttr.getInputType()) == 0) {
                    if (productAttr.getProductAttrValue() == null)
                        continue;
                    productAttr.setProductAttrValue(categoryAttrValueService
                            .queryCategoryAttrValue(
                                    Long.parseLong(productAttr.getProductAttrValue()))
                            .getCategoryAttrValue());
                } else if (CategoryAttrInputType.CHECKBOX.getType()
                        .compareTo(productAttr.getInputType()) == 0) {
                    if (productAttr.getProductAttrValue() == null)
                        continue;
                    String[] ids = productAttr.getProductAttrValue().split(",");
                    if (ids.length > 1) {
                        StringBuilder sb = new StringBuilder();
                        for (String id : ids) {
                            sb.append("，")
                                    .append(categoryAttrValueService
                                            .queryCategoryAttrValue(Long.parseLong(id))
                                            .getCategoryAttrValue());
                        }
                        productAttr.setProductAttrValue(sb.substring(1).toString());
                    } else {
                        CategoryAttrValue categoryAttrValue = categoryAttrValueService
                                .queryCategoryAttrValue(Long.parseLong(ids[0]));
                        if (categoryAttrValue != null)
                            productAttr
                                    .setProductAttrValue(categoryAttrValue.getCategoryAttrValue());
                        else
                            productAttr.setProductAttrValue("");
                    }
                }
            }
        }
    }

    /**
     * 设置类目属性值
     * 
     * @param productAttrList 产品属性基本信息列表
     * @return
     * @throws ServiceException 异常
     */
    @Override
    public void setCategoryAttrValue(List<ProductAttr> productAttrList) throws Exception {
        for (ProductAttr productAttr : productAttrList) {
            if (productAttr.getProductAttrType() == 1) {
                if (CategoryAttrInputType.RADIO.getType().compareTo(productAttr.getInputType()) == 0
                        || CategoryAttrInputType.SELECT.getType()
                                .compareTo(productAttr.getInputType()) == 0) {
                    productAttr.setCategoryAttrValueList(categoryAttrValueService
                            .queryCategoryAttrValueList(productAttr.getRelateAttrId()));
                } else if (CategoryAttrInputType.CHECKBOX.getType()
                        .compareTo(productAttr.getInputType()) == 0) {
                    productAttr.setCategoryAttrValueList(categoryAttrValueService
                            .queryCategoryAttrValueList(productAttr.getRelateAttrId()));
                    List<Long> list = new ArrayList<Long>();
                    String oldChecks = "";
                    if (!StringUtils.isEmpty(productAttr.getProductAttrValue())) {
                        String[] ids = productAttr.getProductAttrValue().split(",");
                        for (String id : ids) {
                            list.add(Long.parseLong(id));
                            oldChecks += id + ",";
                        }
                    }
                    productAttr.setCheckBoxIds(list);
                    if (oldChecks.length() > 0) {
                        oldChecks = oldChecks.substring(0, oldChecks.length() - 1);
                    }
                    productAttr.setOldChecks(oldChecks);
                }
            }
        }
    }

    /**
     * 取产品属性列表
     * 
     * @param productId 产品ID
     * @return List<ProductAttr> 产品属性列表
     * @throws ServiceException 异常
     */
    @Override
    public List<ProductAttr> getProductAttrList(Long productId) throws Exception {
        ProductAttr productAttr = new ProductAttr();
        productAttr.setProductId(productId);
        List<ProductAttr> productAttrList = productAttrService.queryProductAttr(productAttr);
        return productAttrList;
    }

    /**
     * 更新产品类目属性值
     * 
     * @param productId 产品Id
     * @param productAttrList 类目属性基本信息列表
     * @return
     * @throws ServiceException 异常
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public void updateCategoryAttrValue(List<ProductAttr> productAttrList) throws ServiceException {
        if (productAttrList != null && productAttrList.size() > 0) {
            for (ProductAttr productAttr : productAttrList) {
                String[] values = productAttr.getProductAttrValues();
                // boolean isSku = false;
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

    /**
     * 产品关联表、明细表中不包含sku返回true
     * 
     * @param skuList
     * @return
     * @throws Exception
     */
    @Override
    public ResultMessage checkProductRelationForDelProduct(List<ProductSku> skuList,
            ResultMessage rmsg) throws Exception {
        ProductSku productSku = skuList.get(0);
        List<ProductRelationAndDetail> prdList = productRelationService
                .selectProductRelationAndDetailBySkuId(productSku.getProductSkuId());
        if (prdList != null && !prdList.isEmpty()) {
            rmsg.setIsSuccess(false);
            rmsg.setMessage("产品SKU：" + productSku.getProductSkuCode() + " 有套餐引用，无法删除!");
            return rmsg;
        }
        return rmsg;
    }

    /**
     * 优惠券产品表COUPON_PRODUCT表中不包含sku返回true
     * 
     * @param skuList
     * @return
     * @throws Exception
     */
    @Override
    public ResultMessage checkCouponForDelProduct(List<ProductSku> skuList, ResultMessage rmsg)
            throws Exception {
        String skuCode = skuList.get(0).getProductSkuCode();
        List<CouponProduct> cpList = couponProductService.selectCouponProductBySkuId(skuCode);
        if (cpList != null && !cpList.isEmpty()) {
            rmsg.setIsSuccess(false);
            rmsg.setMessage("产品SKU：" + skuCode + " 有优惠券引用，无法删除!");
            return rmsg;
        }
        return rmsg;
    }

    /**
     * 取产品SKU属性列表
     * 
     * @param productId 产品ID
     * @return List<CategoryAttr> 类目属性列表
     * @throws ServiceException 异常
     */
    @Override
    public List<ProductSku> getProductSkuAttrList(Long productId) throws Exception {
        ProductSku productSku = new ProductSku();
        productSku.setProductId(productId);
        List<ProductSku> productSkuList = productSkuService.queryProductSkuList(productSku);
        for (ProductSku sku : productSkuList) {
            ProductSkuAttr productSkuAttr = new ProductSkuAttr();
            productSkuAttr.setProductSkuId(sku.getProductSkuId());
            List<ProductSkuAttr> skuAttrList =
                    productSkuAttrService.queryProductSkuAttrList(productSkuAttr);
            for (ProductSkuAttr skuAttr : skuAttrList) {
                CategoryAttrValue value = categoryAttrValueService
                        .queryCategoryAttrValue(skuAttr.getCategoryAttrValueId());
                if (value != null) {
                    skuAttr.setCategoryAttrValue(value.getCategoryAttrValue());
                }
            }
            sku.setProductSkuAttrList(skuAttrList);

        }
        return productSkuList;
    }

    // @Override
    @Override
    public boolean submitTheAudit(Long productId) throws Exception {
        Product product = new Product();
        product.setId(productId);

        try {
            List<Product> productList = productDao.selectSkuByProductId(product);
            if (productList != null && productList.size() > 0) {
                product = productList.get(0);
                // List<ProductSku> skuList = product.getProductSkus();
                // if(skuList==null || skuList.isEmpty()) return false;
                product.setStatus(ProductStatus.UNAUDIT.getStatus());// 待审核
                int count = productDao.updateByPrimaryKey(product);
                if (count < 1)
                    return false;
            }
        } catch (SQLException e) {
            logger.error("submitTheAudit方法出错", e);
            return false;
        }
        return true;
    }

    @Override
    public void upShelfByOneProduct(Product product) throws Exception {
        if (product != null) {
            try {
                productDao.updateShelfStatus(product);
            } catch (SQLException e) {
                throw new Exception(e);
            }
        }
    }

    @Override
    public ResultMessage checkProductName(String productName) throws Exception {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setIsSuccess(true);
        int count = productDao.selectProductByName(productName);
        if (count > 0) {
            resultMessage.setIsSuccess(false);
            resultMessage.setMessage(
                    new StringBuilder("产品名称：").append(productName).append("重名,请重新输入!").toString());
        }

        return resultMessage;
    }

    @Override
    public Map<Long, ProductAttr> checkOperationAttr(List<Long> productIdList) throws Exception {
        try {
            return productDao.checkOperationAttr(productIdList);
        } catch (Exception e) {
            logger.error("checkOperationAttr方法异常：", e);
            return null;
        }
    }

    public SearchMessageSender getSendMessageSender() {
        return sendMessageSender;
    }

    public void setSendMessageSender(SearchMessageSender sendMessageSender) {
        this.sendMessageSender = sendMessageSender;
    }

    @Override
    public void ExecuteUpdateCachePrice(List<Long> skuIdList) throws Exception {
        try {
            taskExecutor.execute(new ProductPriceHandler(skuIdList));
        } catch (TaskRejectedException e) {
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                logger.error("ExecuteUpdateCachePrice方法异常：", e);
            }
        }
    }

    @Override
    public Product queryProductByProductNo(String productNo) throws SQLException {

        return productDao.queryProductByProductNo(productNo);
    }

    @Override
    public String previewProductInfoPage(String productId) throws Exception {
        String pageUrl = null;
        try {
            // DetailPublishService detailPublishService =
            // (DetailPublishService) RemoteTool.getRemote("04", "detailPublishService");
            pageUrl = detailPublishService.viewDetailPublish(Integer.valueOf(productId));
        } catch (Exception e) {
            logger.error("previewProductInfoPage方法异常：", e);
            throw new Exception(e);
        }
        return pageUrl;
    }

    /**
     * 分页 查找数据,并根据当前操作用户渠道进行过滤
     * 
     * @param page
     * @param prod
     */
    @Override
    public void searchPageByUserId(Page page, Product product, String type, Integer loginUserId)
            throws Exception {

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
        if (StringUtils.isNotBlank(product.getProductTitle()))
            criteria.andProductTitleLike(new StringBuffer("%")
                    .append(product.getProductTitle().trim()).append("%").toString());
        if (StringUtils.isNotBlank(product.getKeyword()))
            criteria.andKeywordLike(new StringBuffer("%").append(product.getKeyword().trim())
                    .append("%").toString());
        if (StringUtils.isNotBlank(product.getProductNo()))
            criteria.andProductNoLike(new StringBuffer("%").append(product.getProductNo().trim())
                    .append("%").toString());

        if (product.getCategoryId() != null && product.getCategoryId() != 0) // 产品所属类目
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        if (product.getmCategoryId() != null && product.getCategoryId() == null)
            pe.setmCategoryId(product.getmCategoryId());
        if (product.getbCategoryId() != null && product.getmCategoryId() == null)
            pe.setbCategoryId(product.getbCategoryId());

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

        if (type != null && "sections".equals(type)) {
            criteria.andStatusEqualTo(ProductStatus.UP.getStatus());
        } else {
            if (StringUtils.isNotBlank(product.getStatus())) // 产品状态
                criteria.andStatusEqualTo(product.getStatus());
        }

        if (product.getBrandId() != null && product.getBrandId() != 0) // 品牌
            criteria.andBrandIdEqualTo(product.getBrandId());

        if (type != null && type.indexOf("price") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(),
                    ProductStatus.SYSDOWN.getStatus());
        }
        if (type != null && type.indexOf("weight") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(),
                    ProductStatus.SYSDOWN.getStatus());
        }
        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("ARCHIVE_TIME DESC");
        } else {
            pe.setOrderByClause("PRODUCT_ID DESC");
        }
        int count = productDao.countByExample(pe);
        List<Product> productList = productDao.selectByExample(pe, skip, max);

        for (Product pd : productList) {
            if (StringUtils.isNotBlank(pd.getProductTitle())) {
                pd.setProductTitle(pd.getProductTitle().replaceAll(" ", "&nbsp;"));
            }
        }
        page.setDataList(productList);
        page.setRecordCount(count);

    }

    @Override
    public void searchPageByUserIdForPrice(Page page, Product product, String type,
            Integer loginUserId) throws Exception {

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
        if (StringUtils.isNotBlank(product.getProductTitle()))
            criteria.andProductTitleLike(new StringBuffer("%")
                    .append(product.getProductTitle().trim()).append("%").toString());
        if (StringUtils.isNotBlank(product.getKeyword()))
            criteria.andKeywordLike(new StringBuffer("%").append(product.getKeyword().trim())
                    .append("%").toString());
        if (StringUtils.isNotBlank(product.getProductNo()))
            criteria.andProductNoLike(new StringBuffer("%").append(product.getProductNo().trim())
                    .append("%").toString());

        if (product.getCategoryId() != null && product.getCategoryId() != 0) // 产品所属类目
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        if (product.getmCategoryId() != null && product.getCategoryId() == null)
            pe.setmCategoryId(product.getmCategoryId());
        if (product.getbCategoryId() != null && product.getmCategoryId() == null)
            pe.setbCategoryId(product.getbCategoryId());

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

        if (type != null && "sections".equals(type)) {
            criteria.andStatusEqualTo(ProductStatus.UP.getStatus());
        } else {
            if (StringUtils.isNotBlank(product.getStatus())) // 产品状态
                criteria.andStatusEqualTo(product.getStatus());
        }

        if (product.getBrandId() != null && product.getBrandId() != 0) // 品牌
            criteria.andBrandIdEqualTo(product.getBrandId());

        if (type != null && type.indexOf("price") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(),
                    ProductStatus.SYSDOWN.getStatus());
        }
        if (type != null && type.indexOf("weight") >= 0) {
            criteria.andStatusBetween(ProductStatus.AUDIT.getStatus(),
                    ProductStatus.SYSDOWN.getStatus());
        }
        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("ARCHIVE_TIME DESC");
        } else {
            pe.setOrderByClause("PRODUCT_ID DESC");
        }
        int count = productDao.countByExample(pe);
        List<Product> productList = productDao.selectByExampleForPrice(pe, skip, max);

        for (Product pd : productList) {
            if (StringUtils.isNotBlank(pd.getProductTitle())) {
                pd.setProductTitle(pd.getProductTitle().replaceAll(" ", "&nbsp;"));
            }
        }
        page.setDataList(productList);
        page.setRecordCount(count);

    }

    @Override
    public void searchCertificateProductPageByUserId(Page page, Product product, String type,
            Integer loginUserId) throws Exception {
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
        if (StringUtils.isNotBlank(product.getProductTitle()))
            criteria.andProductTitleLike(new StringBuffer("%")
                    .append(product.getProductTitle().trim()).append("%").toString());
        if (StringUtils.isNotBlank(product.getKeyword()))
            criteria.andKeywordLike(new StringBuffer("%").append(product.getKeyword().trim())
                    .append("%").toString());
        if (StringUtils.isNotBlank(product.getProductNo()))
            criteria.andProductNoLike(new StringBuffer("%").append(product.getProductNo().trim())
                    .append("%").toString());

        if (product.getCategoryId() != null && product.getCategoryId() != 0) // 产品所属类目
            criteria.andCategoryIdEqualTo(product.getCategoryId());
        if (product.getmCategoryId() != null && product.getCategoryId() == null)
            pe.setmCategoryId(product.getmCategoryId());
        if (product.getbCategoryId() != null && product.getmCategoryId() == null)
            pe.setbCategoryId(product.getbCategoryId());

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

        if ("MDSIN".equals(type)) {
            criteria.andProductTypeEqualTo(ProductType.MDSIN.getKey());
        }

        if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("UP_TIME DESC");
        } else if (ProductStatus.DOWN.getStatus().equals(product.getStatus())) {
            pe.setOrderByClause("ARCHIVE_TIME DESC");
        } else {
            pe.setOrderByClause("PRODUCT_ID DESC");
        }

        int count = productDao.countByExample(pe);
        List<Product> productList = productDao.selectForCertificateByExample(pe, skip, max);

        for (Product pd : productList) {
            if (StringUtils.isNotBlank(pd.getProductTitle())) {
                pd.setProductTitle(pd.getProductTitle().replaceAll(" ", "&nbsp;"));
            }
        }

        page.setDataList(productList);
        page.setRecordCount(count);
    }

    @Override
    public List<Integer> getProductIdByBrandId(Long brandId) throws Exception {
        return productDao.getProductIdByBrandId(brandId);
    }

    @Override
    public List<Map> queryForList(Collection<?> skuIds) throws Exception {
        // TODO Auto-generated method stub
        return productDao.getProducts(skuIds);
    }
}
