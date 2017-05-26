package com.pltfm.app.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.ProductAttrDraftDAO;
import com.pltfm.app.enums.CategoryAttrInputType;
import com.pltfm.app.enums.ProductAttrType;
import com.pltfm.app.service.CategoryAttrValueService;
import com.pltfm.app.service.OperationAttrService;
import com.pltfm.app.service.ProductAttrDraftService;
import com.pltfm.app.service.ProductDraftService;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductAttrDraftExample;
import com.pltfm.app.vobject.ProductAttrDraftExample.Criteria;
import com.pltfm.app.vobject.ProductDraft;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tanyunxing
 */
@Service("productAttrDraftService")
public class ProductAttrDraftServiceImpl implements ProductAttrDraftService {

    protected Logger log = LoggerFactory.getLogger(ProductAttrDraftServiceImpl.class);

    @Resource
    private CategoryAttrValueService categoryAttrValueService;
    
    @Resource
    private OperationAttrService operationAttrService;
    
    @Resource
    private ProductDraftService productDraftService;

    @Resource
    private ProductAttrDraftDAO productAttrDraftDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void addProductAttrDraft(ProductAttrDraft productAttrDraft) throws ServiceException  {
        try {
            productAttrDraftDao.insert(productAttrDraft);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductAttrDraft> findByCondition(ProductAttrDraft productAttrDraft) throws ServiceException  {
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
        exa.setOrderByClause(" relate_attr_id,sortno ");

        try {
            return productAttrDraftDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductAttrDraft> findCategoryAttr(Long productId) throws ServiceException  {
        ProductAttrDraft pad = new ProductAttrDraft();
        pad.setProductId(productId);
        pad.setProductAttrType(ProductAttrType.CATEGORY.getType().shortValue());
        pad.setIsSku((short) 0);

        List<ProductAttrDraft> cateAttrList = findByCondition(pad);
        for (ProductAttrDraft at : cateAttrList) {
            String value = at.getProductAttrValue();
            if (at.getInputType().intValue() != CategoryAttrInputType.TEXT.getType().intValue()
                    && StringUtils.isNotEmpty(value)) {
                at.setValueName(categoryAttrValueService.getCategoryAttrValueByValueIds(value.split(",")));
            }
        }
        return cateAttrList;
    }

    @Override
    public List<ProductAttrDraft> findSkuAttr(Long productId) throws ServiceException  {
        ProductAttrDraft pad = new ProductAttrDraft();
        pad.setProductId(productId);
        pad.setProductAttrType(ProductAttrType.CATEGORY.getType().shortValue());
        pad.setIsSku((short) 1);

        List<ProductAttrDraft> skuAttrList = findByCondition(pad);
        for (ProductAttrDraft at : skuAttrList) {
            String value = at.getProductAttrValue();

            if (at.getInputType().intValue() != CategoryAttrInputType.TEXT.getType().intValue()
                    && StringUtils.isNotEmpty(value)) {
                String[] valueId = value.split(",");

                List<String> needQueryCategory = new ArrayList<String>();
                List<String> notNeedQueryCategory = new ArrayList<String>();
                for (String id : valueId) {
                    if (id.indexOf("@") < 0) {//格式可能为@aaa#bbb(aaa为新增的规格，bbb为此规格的下标)
                        needQueryCategory.add(id);
                    } else {
                        int index = id.lastIndexOf("#");
                        if (index > 0) {
                            notNeedQueryCategory.add(id.substring(1, index));
                        } else {
                            notNeedQueryCategory.add(id.substring(1));
                        }
                    }
                }

                String needQueryValue = "";
                if (CollectionUtils.isNotEmpty(needQueryCategory)) {
                    needQueryValue = categoryAttrValueService.getCategoryAttrValueByValueIds(
                            needQueryCategory.toArray(new String[needQueryCategory.size()]));
                }

                String notNeedQueryValue = StringUtils.join(notNeedQueryCategory, ",");
                at.setValueName(needQueryValue + (StringUtils.isEmpty(notNeedQueryValue) ? "" : ",") + notNeedQueryValue);
            }
        }
        return skuAttrList;
    }

    @Override
    public List<ProductAttrDraft> findDefinitionAttr(Long productId) throws ServiceException  {
        ProductAttrDraft pad = new ProductAttrDraft();
        pad.setProductId(productId);
        pad.setProductAttrType(ProductAttrType.DEFINITION.getType().shortValue());

        return findByCondition(pad);
    }

    @Override
    public List<ProductAttrDraft> findOperationAttr(Long productId) throws ServiceException  {
        ProductAttrDraft pad = new ProductAttrDraft();
        pad.setProductId(productId);
        pad.setProductAttrType(ProductAttrType.OPERATION.getType().shortValue());

        return findByCondition(pad);
    }

    @Override
    public List<ProductAttrDraft> findProductDraftAttrByProductId(Long productId) throws ServiceException  {
        ProductAttrDraft pad = new ProductAttrDraft();
        pad.setProductId(productId);
        List<ProductAttrDraft> productAttrList = findByCondition(pad);

        return productAttrList;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchInsertOfficial(List<ProductAttrDraft> list) throws ServiceException {
        try {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }

            int result = productAttrDraftDao.batchInsertOfficial(list);
            if (result == 0) {
                throw new ServiceException("产品草稿属性批量插入失败，未操作一条数据。");
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteDraftByProductId(Long productId) throws ServiceException  {
        ProductAttrDraftExample exa = new ProductAttrDraftExample();
        exa.createCriteria().andProductIdEqualTo(productId);

        try {
            productAttrDraftDao.deleteByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeCategoryAttrValue(List<ProductAttrDraft> productAttrList) throws ServiceException  {
        for (ProductAttrDraft productAttr : productAttrList) {
            //类目属性含基本属性和sku属性
            short productAttrType = productAttr.getProductAttrType().shortValue();
            if (productAttrType == ProductAttrType.CATEGORY.getType().shortValue()) {//类目属性
                int inputType = productAttr.getInputType().intValue();

                if (CategoryAttrInputType.TEXT.getType().intValue() != inputType) {//属性输入类型 不是文本
                    productAttr.setCategoryAttrValueList(
                            categoryAttrValueService.queryCategoryAttrValueList(productAttr.getRelateAttrId()));

                    if (CategoryAttrInputType.CHECKBOX.getType().intValue() == inputType) {//属性输入类型 是checkbox
                        List<Long> list = new ArrayList();
                        String productAttrValue = productAttr.getProductAttrValue();

                        if (StringUtils.isNotEmpty(productAttrValue)) {
                            String[] ids = productAttrValue.split(",");
                            for (String id : ids) {
                                if (!StringUtils.isNumeric(id)) {
                                    continue;
                                }

                                list.add(Long.valueOf(id));
                            }
                        }
                        productAttr.setCheckBoxIds(list);
                        productAttr.setOldChecks(StringUtils.join(list, ","));
                    }
                }
                //运营属性
            } else if (productAttrType == ProductAttrType.OPERATION.getType().shortValue()) {
                OperationAttr operationAttr = operationAttrService.queryOperationAttr(productAttr.getRelateAttrId());
                productAttr.setProductAttrName(operationAttr.getOperationAttrName());
            }
        }
    }

    @Override
    public void changeOperationAttrValue(Long productId, List<OperationAttr> attrList) throws ServiceException  {
        List<ProductAttrDraft> productAttrList = this.findOperationAttr(productId);
        for (ProductAttrDraft attrDraft : productAttrList) {
            boolean notHaveTheOperationAttr = true;
            for (OperationAttr operationAttr : attrList) {
                if (attrDraft.getRelateAttrId().equals(operationAttr.getOperationAttrId())) {
                    operationAttr.setIsSelect(true);
                    notHaveTheOperationAttr = false;
                    break;
                }
            }
            if (notHaveTheOperationAttr) {
                OperationAttr ot = operationAttrService.queryOperationAttr(attrDraft.getRelateAttrId());
                ot.setIsSelect(true);
                attrList.add(ot);
            }
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateCategoryAttrValue(ProductDraft product, List<ProductAttrDraft> attrList) throws ServiceException {
        try {
            if (CollectionUtils.isEmpty(attrList)) {
                return;
            }

            for (ProductAttrDraft productAttr : attrList) {
                if (productAttr.getProductAttrId() == null) {
                    continue;
                }

                String[] values = productAttr.getProductAttrValues();
                if (values != null && values.length > 0) {
                    if (values.length == 1) {
                        productAttr.setProductAttrValue(values[0]);
                    } else {
                        productAttr.setProductAttrValue(StringUtils.join(values, ","));
                    }
                } else {
                    productAttr.setProductAttrValue("");
                }

                productAttrDraftDao.updateByPrimaryKeySelective(productAttr);
            }
            productDraftService.updateObject(product);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchUpdateProductAttrDraft(List<ProductAttrDraft> productAttrDraftList) throws ServiceException  {
        try {
            productAttrDraftDao.batchUpdateByPrimaryKeySelective(productAttrDraftList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateDefinitionAttrValue(ProductDraft product, Long productId, List<ProductAttrDraft> attrDraftList)
            throws ServiceException {
        ProductAttrDraftExample exa = new ProductAttrDraftExample();
        exa.createCriteria().andProductIdEqualTo(productId)
                .andProductAttrTypeEqualTo(ProductAttrType.DEFINITION.getType().shortValue());
        try {
            productAttrDraftDao.deleteByExample(exa);

            if (CollectionUtils.isNotEmpty(attrDraftList)) {
                for (ProductAttrDraft productAttr : attrDraftList) {
                    if (productAttr != null && productAttr.getProductId() == null) {
                        productAttr.setProductId(productId);
                        productAttr.setProductAttrType(ProductAttrType.DEFINITION.getType().shortValue());
                        productAttrDraftDao.insert(productAttr);
                    }
                }
            }

            productDraftService.updateObject(product);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateOperationAttrValue(ProductDraft product, Long productId, Long[] operationAttrIds)
            throws ServiceException {
        ProductAttrDraftExample exa = new ProductAttrDraftExample();
        exa.createCriteria().andProductIdEqualTo(productId)
                .andProductAttrTypeEqualTo(ProductAttrType.OPERATION.getType().shortValue());
        try {
            productAttrDraftDao.deleteByExample(exa);

            if (ArrayUtils.isNotEmpty(operationAttrIds)) {
                List<ProductAttrDraft> list = new ArrayList<ProductAttrDraft>();
                for (Long operationAttrId : operationAttrIds) {
                    ProductAttrDraft productAttr = new ProductAttrDraft();
                    productAttr.setProductId(productId);
                    productAttr.setProductAttrType(ProductAttrType.OPERATION.getType().shortValue());
                    productAttr.setRelateAttrId(operationAttrId);
                    OperationAttr operationAttr = operationAttrService.queryOperationAttr(operationAttrId);
                    productAttr.setProductAttrName(operationAttr.getOperationAttrName());
                    productAttr.setProductAttrValue(operationAttr.getOperationAttrId().toString());
                    productAttr.setIsNav(operationAttr.getIsNav().shortValue());
                    list.add(productAttr);
                }

                batchInsertDraft(list);
            }

            productDraftService.updateObject(product);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void batchInsertDraft(List<ProductAttrDraft> list) throws ServiceException  {
        try {
            productAttrDraftDao.batchInsertDraft(list);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchInsertDraftFromOfficial(List<ProductAttr> list) throws ServiceException {
        try {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }

            int result = productAttrDraftDao.batchInsertDraftFromOfficial(list);
            if (result == 0) {
                throw new ServiceException("批量保存草稿数据（数据来源至正式表）失败。没有保存数据.");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

}
    
