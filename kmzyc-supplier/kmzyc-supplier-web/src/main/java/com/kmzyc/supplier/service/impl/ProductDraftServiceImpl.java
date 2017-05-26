package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.ProductDraftRemoteService;
import com.kmzyc.supplier.common.ProductSkuDraftComparator;
import com.kmzyc.supplier.dao.ProductDraftDao;
import com.kmzyc.supplier.service.CategoryAttrService;
import com.kmzyc.supplier.service.CategoryAttrValueService;
import com.kmzyc.supplier.service.CategoryService;
import com.kmzyc.supplier.service.OperationAttrService;
import com.kmzyc.supplier.service.ProductAttrDraftService;
import com.kmzyc.supplier.service.ProductDraftService;
import com.kmzyc.supplier.service.ShopProductCategoryService;
import com.kmzyc.supplier.util.SkuAttrValueIdUtils;
import com.kmzyc.supplier.vo.ProductDraftVo;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.CategoryAttrInputType;
import com.pltfm.app.enums.ProductAttrType;
import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.fobject.SkuCheckAttr;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductAttrDraftExample;
import com.pltfm.app.vobject.ProductAttrDraftExample.Criteria;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductDraftExample;
import com.pltfm.app.vobject.ProductSkuDraft;


@Service("productDraftService")
public class ProductDraftServiceImpl implements ProductDraftService {

    private Logger logger = LoggerFactory.getLogger(ProductDraftServiceImpl.class);

    @Resource
    private CategoryAttrValueService categoryAttrValueService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private OperationAttrService operationAttrService;

    @Resource
    private ProductDraftDao productDraftDao;

    @Resource
    private ProductAttrDraftService productAttrDraftService;

    @Resource
    private CategoryAttrService categoryAttrService;

    @Resource
    ProductDraftRemoteService productDraftRemoteService;

    @Resource
    private ShopProductCategoryService shopProductCategoryService;

    @Override
    public Pagination searchPage(ProductDraft product, String shopCategoryId, Pagination page) throws ServiceException {
        Map<String, Object> condition = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(product.getProductName())) {
            condition.put("name", product.getProductName().trim());
        }
        if (StringUtils.isNotBlank(product.getProductTitle())) {
            condition.put("productTitle", product.getProductTitle().trim());
        }
        if (StringUtils.isNotBlank(product.getProductNo())) {
            condition.put("productNo", product.getProductNo().trim());
        }
        if (StringUtils.isNotBlank(product.getKeyword())) {
            condition.put("keyWord", product.getKeyword().trim());
        }
        if (StringUtils.isNotBlank(product.getChannel())) {
            condition.put("channel", product.getChannel().trim());
        }
        if (product.getOpTypes() != null && !product.getOpTypes().isEmpty()) {
            condition.put("opTypes", product.getOpTypes());
        }
        if (StringUtils.isNotEmpty(product.getSearchBrandName())) {
            condition.put("searchBrandName", product.getSearchBrandName().trim());
        }
        if (StringUtils.isNotBlank(product.getShopCode())) {
            condition.put("supplierId", product.getShopCode().trim());
        }
        if (StringUtils.isNotBlank(product.getStatus())) {
            //当查询审核不通过时，采用or查询产品信息状态和产品价格状态
            if (ProductStatus.AUDITUNPASS.getStatus().equals(product.getStatus())) {
                condition.put("auditUnPassStatus", product.getStatus());
            } else if (ProductStatus.UNAUDIT.getStatus().equals(product.getStatus())) {
                //查询审核中时，增加条件：产品信息状态和产品价格状态其中之一非审核不通过
                condition.put("unAuditStatus", ProductStatus.AUDITUNPASS.getStatus());
                condition.put("auditUnPassStatus", product.getStatus());
            } else {
                condition.put("status", product.getStatus());
            }
        }
        if (StringUtils.isNotBlank(product.getPriceStatus())) {
            condition.put("priceStatus", product.getPriceStatus());
        }
        // 产品所属类目
        // 小类
        if (product.getCategoryId() != null && product.getCategoryId() != 0)
            condition.put("categoryId", product.getCategoryId());
        // 中类
        if (product.getMCategoryId() != null && product.getCategoryId() == null)
            condition.put("mCategoryId", product.getMCategoryId());
        // 大类
        if (product.getBCategoryId() != null && product.getMCategoryId() == null)
            condition.put("bCategoryId", product.getBCategoryId());
        if (StringUtils.isNotBlank(shopCategoryId)) {
            condition.put("shopCategoryId", shopCategoryId);
        }
        page.setObjCondition(condition);
        try {
            productDraftDao.findProductMainListByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    public int updateObject(ProductDraft pd, String shopCategoryIds) throws ServiceException {
        try {
            int result = productDraftRemoteService.updateObject(pd);
            //如果只是提交审核,则不删除店内分类
            if ("submitAudit".equals(shopCategoryIds)) {
                return result;
            }
            // 若是店内分类为空,则说明要清空掉之前的
            Long productId = pd.getProductId();
            if (StringUtils.isBlank(shopCategoryIds)) {
                shopProductCategoryService.deleteByProductId(productId);
                return result;
            }
            // 更新店内分类
            shopProductCategoryService.updateByProductId(productId, StringUtils.split(shopCategoryIds, ","));
            return result;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductDraft findByProductIdWithOutClob(Long productId) throws ServiceException {
        try {
            ProductDraftExample exa = new ProductDraftExample();
            exa.createCriteria().andProductIdEqualTo(productId);
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

    @Override
    public void deleteProductDraft(ProductDraft pd) throws ServiceException {
        try {
            productDraftRemoteService.deleteProductDraftByProductId(pd.getProductId());
            // 删除店内分类类目和产品的关联关系
            shopProductCategoryService.deleteByProductId(pd.getProductId());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductDraftVo findProductDraftVoByProductId(Long productId) throws ServiceException {
        try {
            return productDraftDao.findProductMainVoByProductId(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductAttrDraft> getProductAttrDraftList(Long id) throws ServiceException {
        ProductAttrDraft productAttr = new ProductAttrDraft();
        productAttr.setProductId(id);
        List<ProductAttrDraft> productAttrList = productAttrDraftService.queryProductAttrDraft(productAttr);
        if (CollectionUtils.isNotEmpty(productAttrList)) {
            setProductAttrValue(productAttrList);
        }
        return productAttrList;
    }

    @Override
    public void setProductAttrValue(List<ProductAttrDraft> productAttrList) throws ServiceException {
        for (ProductAttrDraft productDraftAttr : productAttrList) {
            //商品类目属性
            Short productAttrType = productDraftAttr.getProductAttrType();

            if (productAttrType.intValue() == ProductAttrType.CATEGORY.getType().intValue()) {
                //属性值
                String productAttrValue = productDraftAttr.getProductAttrValue();
                if (StringUtils.isEmpty(productAttrValue)) {
                    continue;
                }

                //商品属性值输入类型  单选框", "多选框","下拉框"
                Short inputType = productDraftAttr.getInputType();
                //单选框","下拉框"
                if (CategoryAttrInputType.RADIO.getType().intValue() == inputType.intValue()
                        || CategoryAttrInputType.SELECT.getType().intValue() == inputType.intValue()) {
                    //设置单选框和下拉框的值
                    CategoryAttrValue categoryAttrValue = categoryAttrValueService.queryCategoryAttrValue(Long.parseLong(productAttrValue));
                    if (categoryAttrValue != null) {
                        productDraftAttr.setProductAttrValue(categoryAttrValue.getCategoryAttrValue());
                    }
                // "多选框"
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

                    productDraftAttr.setProductAttrValue(StringUtils.join(categoryAttrValueList, ","));
                }
            } else if (productAttrType.intValue() == ProductAttrType.OPERATION.getType().intValue()) {
                OperationAttr operationAttr = operationAttrService.queryOperationAttr(productDraftAttr.getRelateAttrId());
                if (operationAttr != null) {
                    productDraftAttr.setProductAttrName(operationAttr.getOperationAttrName());
                }
            }
        }
    }

    @Override
    public List<ProductAttrDraft> getProductSkuAttrList(Long productId) throws ServiceException {
        ProductAttrDraft pad = new ProductAttrDraft();
        pad.setProductId(productId);
        pad.setProductAttrType(ProductAttrType.CATEGORY.getType().shortValue());
        pad.setIsSku((short) 1);

        List<ProductAttrDraft> skuAttrList = this.findByCondition(pad);
        for (ProductAttrDraft at : skuAttrList) {
            if (at.getInputType().intValue() == CategoryAttrInputType.TEXT.getType().intValue()) {
                at.setValueName(at.getProductAttrValue());
            } else {
                String value = at.getProductAttrValue();
                if (StringUtils.isNotEmpty(value)) {
                    String[] valueId = value.split(",");
                    valueId = SkuAttrValueIdUtils.sort(valueId);
                    StringBuilder valueName = new StringBuilder();
                    for (String id : valueId) {
                        valueName.append(",");
                        if (id.indexOf("@") < 0) {
                            CategoryAttrValue categoryAttrValue =
                                    categoryAttrValueService.queryCategoryAttrValue(Long.valueOf(id));
                            if (categoryAttrValue != null) {
                                valueName.append(categoryAttrValue.getCategoryAttrValue());
                            }
                        } else {
                            int index = id.lastIndexOf("#");
                            if (index > 0) {
                                valueName.append(id.substring(1, index));
                            } else {
                                valueName.append(id.substring(1));
                            }
                        }
                    }
                    at.setValueName(valueName.substring(1));
                }
            }
        }
        return skuAttrList;
    }

    @Override
    public List<ProductAttrDraft> findByCondition(ProductAttrDraft productAttrDraft) throws ServiceException {
        ProductAttrDraftExample exa = new ProductAttrDraftExample();
        Criteria c = exa.createCriteria();
        if (productAttrDraft.getProductId() != null) {
            c.andProductIdEqualTo(productAttrDraft.getProductId());
        }
        if (productAttrDraft.getInputType() != null) {
            c.andInputTypeEqualTo(productAttrDraft.getInputType());
        }
        if (productAttrDraft.getIsNav() != null) {
            c.andIsNavEqualTo(productAttrDraft.getIsNav());
        }
        if (productAttrDraft.getIsReq() != null) {
            c.andIsReqEqualTo(productAttrDraft.getIsReq());
        }
        if (productAttrDraft.getIsSku() != null) {
            c.andIsSkuEqualTo(productAttrDraft.getIsSku());
        }
        if (productAttrDraft.getOpType() != null) {
            c.andOpTypeEqualTo(productAttrDraft.getOpType());
        }
        if (productAttrDraft.getProductAttrType() != null) {
            c.andProductAttrTypeEqualTo(productAttrDraft.getProductAttrType());
        }
        if (productAttrDraft.getRelateAttrId() != null) {
            c.andRelateAttrIdEqualTo(productAttrDraft.getRelateAttrId());
        }

        exa.setOrderByClause(" relate_attr_id,PRODUCT_ATTR_ID ");
        try {
            return productDraftDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductDraft findSingleProductAndSkusAndAttrValues(ProductDraft productDraft) throws ServiceException {
        try {
            ProductDraft result = productDraftDao.findSingleProduct(productDraft);
            if (result != null) {
                List<ProductSkuDraft> skuList = result.getProductSkuDrafts();
                //设置sku列表
                Collections.sort(skuList, new ProductSkuDraftComparator());
                result.setProductSkuDrafts(skuList);
            } else {
                return new ProductDraft();
            }
            return result;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductAttrDraft> findDefinitionAttr(Long productId) throws ServiceException {
        try {
            ProductAttrDraft pad = new ProductAttrDraft();
            pad.setProductId(productId);
            pad.setProductAttrType(ProductAttrType.DEFINITION.getType().shortValue());
            List<ProductAttrDraft> definitionAttrList = this.findByCondition(pad);
            for (ProductAttrDraft at : definitionAttrList) {
                at.setValueName(at.getProductAttrValue());
            }
            return definitionAttrList;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductAttrDraft> findOperationAttr(Long productId) throws ServiceException {
        try {
            ProductAttrDraft pad = new ProductAttrDraft();
            pad.setProductId(productId);
            pad.setProductAttrType(ProductAttrType.OPERATION.getType().shortValue());

            List<ProductAttrDraft> operationAttrList = this.findByCondition(pad);
            for (ProductAttrDraft at : operationAttrList) {
                at.setValueName(at.getProductAttrName());
            }
            return operationAttrList;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OperationAttr> getOperationAttrList() throws ServiceException {
        try {
            return operationAttrService.queryOperationAttrList();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CategoryAttr> getCategoryAttrList(Long categoryId) throws ServiceException {
        try {
            Category categoryParam = new Category();
            categoryParam.setCategoryId(categoryId);
            List<Category> categoryList = categoryService.queryCategoryParentList(categoryParam);
            List<Long> categoryIds = categoryList.stream().map(Category::getCategoryId).collect(Collectors.toList());
            List<CategoryAttr> categoryAttrList = null;
            if (CollectionUtils.isNotEmpty(categoryIds)) {
                CategoryAttr categoryAttrParam = new CategoryAttr();
                categoryAttrParam.setCategoryIds(categoryIds);
                categoryAttrList = categoryAttrService.queryCategoryAttrList(categoryAttrParam);
            }
            return categoryAttrList;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductAttrDraft> getProductAttrList(Long productId) throws ServiceException {
        try {
            ProductAttrDraft productAttr = new ProductAttrDraft();
            productAttr.setProductId(productId);
            return productAttrDraftService.queryProductAttrDraft(productAttr);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void setCategoryAttrValue(List<ProductAttrDraft> productAttrList) throws ServiceException {
        try {
            for (ProductAttrDraft productAttr : productAttrList) {
                //商品属性类型
                Short productAttrType = productAttr.getProductAttrType();
                //商品类目属性id
                Long categoryAttrId = productAttr.getRelateAttrId();

                if (productAttrType.intValue() == ProductAttrType.CATEGORY.getType().intValue()) {
                    Short inputType = productAttr.getInputType();

                    if (CategoryAttrInputType.RADIO.getType().intValue() == inputType.intValue()
                            || CategoryAttrInputType.SELECT.getType().intValue() == inputType.intValue()) {
                        productAttr.setCategoryAttrValueList(categoryAttrValueService.queryCategoryAttrValueList(categoryAttrId));
                    } else if (CategoryAttrInputType.CHECKBOX.getType().intValue() == inputType.intValue()) {
                        productAttr.setCategoryAttrValueList(categoryAttrValueService.queryCategoryAttrValueList(categoryAttrId));

                        String productAttrValue = productAttr.getProductAttrValue();

                        if (StringUtils.isNotEmpty(productAttrValue)) {
                            String[] ids = productAttrValue.split(",");
                            List<Long> list = Arrays.stream(ids).filter(id -> id.indexOf("@") < 0)
                                    .map(id -> Long.parseLong(id)).collect(Collectors.toList());
                            productAttr.setCheckBoxIds(list);
                            productAttr.setOldChecks(StringUtils.join(list, ","));
                        }

                    }
                } else if (productAttrType.intValue() == ProductAttrType.OPERATION.getType().intValue()) {
                    OperationAttr operationAttr = operationAttrService.queryOperationAttr(categoryAttrId);
                    productAttr.setProductAttrName(operationAttr.getOperationAttrName());
                }
            }
        } catch (Exception e) {
            logger.error("设置类目属性的checkbox和checkId失败：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void setOperationAttrValue(Long productId, List<OperationAttr> operationAttrList) throws ServiceException {
        ProductAttrDraft productAttr = new ProductAttrDraft();
        productAttr.setProductId(productId);
        productAttr.setProductAttrType(ProductAttrType.OPERATION.getType().shortValue());

        try {
            List<ProductAttrDraft> productAttrList = productAttrDraftService.queryProductAttrDraft(productAttr);
            for (ProductAttrDraft productAttrTemp : productAttrList) {
                boolean notHaveTheOperation = true;
                for (OperationAttr operation : operationAttrList) {
                    if (productAttrTemp.getRelateAttrId().equals(operation.getOperationAttrId())) {
                        operation.setIsSelect(true);
                        notHaveTheOperation = false;
                        break;
                    }
                }
                if (notHaveTheOperation) {
                    OperationAttr ot = operationAttrService.queryOperationAttr(productAttrTemp.getRelateAttrId());
                    ot.setIsSelect(true);
                    operationAttrList.add(ot);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage upShelf(Long[] productIds, long longValue, Long supplierId) throws ServiceException {
        try {
            return productDraftRemoteService.upshelf(Lists.newArrayList(productIds), longValue, supplierId);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
    public void updateOperationAttrValue(ProductDraft product, Long loginId, Long[] operationAttrIds)
            throws ServiceException {
        try {
            productDraftRemoteService.productDraftUpdate(5, loginId, product, null, null, null);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
    public void updateCategoryAttrValue(ProductDraft product, Integer dataType) throws ServiceException {
        try {
            productDraftRemoteService.productDraftUpdate(dataType, null, product, null, null, null);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
    public void updateDefinitionAttrValue(ProductDraft product, Integer dataType) throws ServiceException {
        try {
            productDraftRemoteService.productDraftUpdate(dataType, null, product, null, null, null);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
    public int updateSkuAttrValue(ProductDraft product, List<SkuCheckAttr> skuCheckAttrs, List<String> oldSkuCheckedId,
                                  String toDeleteSkuIds, Integer dataType) throws ServiceException {
        try {
            return productDraftRemoteService.productDraftUpdate(dataType, null,
                    product, skuCheckAttrs, oldSkuCheckedId, toDeleteSkuIds);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
    public Long insertProduct(ProductDraft product, List<SkuCheckAttr> skuCheckAttrs, String shopCategoryIds)
            throws ServiceException {
        Long productId = null;
        try {
            productId = productDraftRemoteService.insertProduct(product, skuCheckAttrs);

            // 添加店内分类
            if (StringUtils.isBlank(shopCategoryIds)) {
                return productId;
            }
            String[] idArray = StringUtils.split(shopCategoryIds, ",");
            shopProductCategoryService.insert(productId, idArray);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
        return productId;

    }

    @Override
    public int deleteProductDraftByProductId(Long productId) throws ServiceException {
        try {
            return productDraftRemoteService.deleteProductDraftByProductId(productId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage releaseProductPrice(Long productId) throws ServiceException {
        try {
            return productDraftRemoteService.releaseProductPrice(productId, Long.valueOf(0));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String previewProductDraftInfoPage(String productId) throws ServiceException {
        try {
            return productDraftRemoteService.previewProductDraftInfoPage(productId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
    public int productDraftModify(Integer dataType, Long loginId, ProductDraft productDraft, List<SkuCheckAttr> skuCheckAttrs,
                                  List<String> oldSkuCheckedId, String toDeleteSkuIds, String shopCategoryIds)
            throws ServiceException {
        try {
            productDraft.setOpType(null);
            int result = productDraftRemoteService.productDraftUpdate(dataType, loginId,
                    productDraft, skuCheckAttrs, oldSkuCheckedId, toDeleteSkuIds);
            // 若是店内分类为空,则说明要清空掉之前的
            Long productId = productDraft.getProductId();
            if (StringUtils.isBlank(shopCategoryIds)) {
                shopProductCategoryService.deleteByProductId(productId);
                return result;
            }
            // 更新店内分类
            shopProductCategoryService.updateByProductId(productId, StringUtils.split(shopCategoryIds, ","));
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
}
