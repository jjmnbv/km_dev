package com.kmzyc.product.remote.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.ProductDraftRemoteService;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.ProductDao;
import com.pltfm.app.enums.DraftType;
import com.pltfm.app.enums.MsgOperation;
import com.pltfm.app.fobject.SkuCheckAttr;
import com.pltfm.app.service.CmsProductUpShelfService;
import com.pltfm.app.service.ProductAttrDraftService;
import com.pltfm.app.service.ProductDraftService;
import com.pltfm.app.service.ProductImageDraftService;
import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductSkuDraftService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ProductStockService;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuDraft;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Service("productDraftRemoteService")
public class ProductDraftRemoteServiceImpl implements ProductDraftRemoteService {

    private Logger logger = LoggerFactory.getLogger(ProductDraftRemoteServiceImpl.class);

    @Resource
    private ProductDraftService productDraftService;

    @Resource
    private ProductSkuDraftService productSkuDraftService;

    @Resource
    private ProductAttrDraftService productAttrDraftService;

    @Resource
    private CmsProductUpShelfService cmsProductUpShelfService;

    @Resource
    private ProductService productService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductImageDraftService productImageDraftService;

    @Resource
    private ProductImageService productImageService;

    @Resource
    private ProductStockService productStockService;

    @Resource
    private ProductDao productDao;

    @Override
    public Long insertProduct(ProductDraft product, List<SkuCheckAttr> skuCheckAttrs) throws ServiceException {
        Long productId = -1L;
        try {
            productId = productDraftService.insertProduct(product, skuCheckAttrs);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1L;
        }

        return productId;
    }

    @Override
    public int updateObject(ProductDraft product) throws ServiceException {
        try {
            productDraftService.updateObject(product);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    @Override
    public int batchUpdateObject(List<ProductDraft> list) throws ServiceException {
        try {
            productDraftService.batchUpdateObject(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    @Override
    public int copyOfficialDataToDraft(Long productId, String isExistProduct) throws ServiceException {
        try {
            productDraftService.copyOfficialDataToDraft(productId, isExistProduct);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    @Override
    public ResultMessage checkUpshelf(List<Long> productIds) throws ServiceException {
        ResultMessage rm = new ResultMessage();
        try {
            rm = productDraftService.checkUpShelf(productIds);
        } catch (Exception e) {
            logger.error(e.getMessage());
            rm.setIsSuccess(false);
            rm.setMessage("调用接口出错了！");
        }
        return rm;
    }

    @Override
    public ResultMessage upshelf(List<Long> productIds, Long loginId, Long supplierId)
            throws ServiceException {
        ResultMessage rm = new ResultMessage();
        try {
            rm = productDraftService.upShelf(productIds, loginId);
            if (!rm.getIsSuccess()) {
                return rm;
            }
        } catch (Exception e) {
            logger.error("upShelf ERROR:::", e);
            rm.setIsSuccess(false);
            rm.setMessage("调用接口出错了！");
            return rm;
        }

        try {
            // 新增上架时，需要为此商品新增库存记录
            List<String> productSkuCodeList = (List<String>) rm.getObject();
            if (CollectionUtils.isNotEmpty(productSkuCodeList)) {
                List<ProductSku> productSkuList = productSkuService.findProductSkuBySkuCodes(productSkuCodeList);
                if (CollectionUtils.isNotEmpty(productSkuList)) {
                    rm = productStockService.addStockForSupplier(supplierId, productSkuList);
                }
                if (!rm.getIsSuccess()) {
                    return rm;
                }
            }
        } catch (Exception e) {
            logger.error("新增上架时，需要为此商品新增库存记录失败:", e);
            rm.setIsSuccess(false);
            rm.setMessage("商品上架成功，商品库存增加失败，请记录上架商品并与客服人员联系!");
            return rm;
        }


        try {
            // CMS上架
            List<Integer> productIdIntList = productIds.stream().map(id -> Integer.valueOf(id.intValue())).collect(Collectors.toList());
            cmsProductUpShelfService.productUpShelfByCms(productIdIntList);
        } catch (Exception e1) {
            logger.error("CMS上架接口失败：", e1);
        }

        try {
            List<Long> skuIdList = productSkuService.selectSkuIdsByProductIdList(productIds);
            if (skuIdList != null && !skuIdList.isEmpty()) {
                productService.executeUpdateCachePrice(skuIdList);
                productService.changeProductInfoNotify(skuIdList, MsgOperation.ADD.getType());
                productSkuService.updateProductSkuCache(skuIdList);
            }
        } catch (Exception e2) {
            logger.error("搜索通知失败：", e2);
        }

        return rm;
    }

    @Override
    public int insertDraftFromProduct(ProductDraft product, List<SkuCheckAttr> skuCheckAttrs)
            throws ServiceException {
        try {
            productDraftService.insertFromProduct(product, skuCheckAttrs);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }

        return 0;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int productDraftUpdate(Integer dataType, Long loginId, ProductDraft productDraft,
            List<SkuCheckAttr> skuCheckAttrs, List<String> oldSkuCheckedId, String toDeleteSkuIds)
            throws ServiceException {
        productDraft.setModifUser(loginId);
        productDraft.setModifTime(new Date());
        try {
            productDraftService.updateObject(productDraft);
            productAttrDraftService.updateCategoryAttrValue(productDraft,productDraft.getProductAttrDraftList());
            if (!StringUtils.isEmpty(toDeleteSkuIds)) {
                toDeleteSkuIds = toDeleteSkuIds.substring(0, toDeleteSkuIds.length() - 1);
            }
            productDraftService.updateSkuAttrValue(productDraft, productDraft.getProductNo(),
                    productDraft.getProductAttrDraftList(), skuCheckAttrs, oldSkuCheckedId,toDeleteSkuIds);
            productAttrDraftService.updateDefinitionAttrValue(productDraft,
                    productDraft.getProductId(), productDraft.getProductAttrDraftList());
            productAttrDraftService.updateOperationAttrValue(productDraft,
                    productDraft.getProductId(), productDraft.getOperationAttrIds());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    @Override
    public int findSameProductFromDraft(Long productId) throws ServiceException {
        int result = -1;
        ProductDraft pd = productDraftService.findByProductIdWithOutClob(productId);
        if (pd == null) {
            result = 0;
        } else {
            if (DraftType.UPDATE.getStatus().equals(pd.getOpType())) {
                result = 1;
            } else {
                result = 2;
            }
        }
        return result;
    }

    @Override
    public ResultMessage releaseProductPrice(Long productId, Long loginId) throws ServiceException {
        ResultMessage resultMessage = null;
        try {
            resultMessage = productDraftService.releaseProductPrice(productId, loginId);
        } catch (Exception e) {
            logger.error("发布产品价格失败：", e);
            resultMessage = new ResultMessage();
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("发布产品价格失败.");
        }
        return resultMessage;
    }

    @Override
    public int updateSingleSkuPrice(Long productId, List<ProductSkuDraft> list) throws ServiceException {
        try {
            productSkuDraftService.updateSingleSkuPrice(productId, list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    @Override
    public int auditProductPrice(Long[] productIdChk, String auditStatus, String reasonText) throws ServiceException {
        try {
            productSkuDraftService.auditProductPrice(productIdChk, auditStatus, reasonText);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    @Override
    public int updateBatchByPrimaryKey(Long productId, List<ProductSkuDraft> list) throws ServiceException {
        try {
            productSkuDraftService.updateBatchByPrimaryKey(productId, list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    @Override
    public int deleteProductDraftByProductId(Long productId) throws ServiceException {
        try {
            ProductDraft pd = productDraftService.findByProductIdWithOutClob(productId);
            String isExistProduct = "1";
            if (pd != null && DraftType.ADD.getStatus().equals(pd.getOpType())) {
                isExistProduct = "0";
            }
            productDraftService.deleteProductDraftByProductId(productId, isExistProduct);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    @Override
    public boolean downShelf(List<Product> productList) throws ServiceException {
        try {
            int count = productDao.batchUpdateForProduct("productmapper.product_downShelfByIdList", productList);
            if (count < 1)
                return false;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }

        // 通知下架产品给搜索引擎
        List<Long> productIdList = productList.stream().map(Product::getId).collect(Collectors.toList());
        try {
            List<Long> delete_skuIdList = productSkuService.selectSkuIdsByProductIdList(productIdList);
            productService.changeProductInfoNotify(delete_skuIdList, MsgOperation.DELETE.getType());
            productSkuService.updateProductSkuCache(delete_skuIdList);
        } catch (Exception e2) {
            logger.error("调用搜索引擎MQ接口失败：" + e2.getMessage(), e2);
        }

        return true;
    }

    @Override
    public ResultMessage productUpShelf(List<Product> productList) throws ServiceException {
        ResultMessage resultMsg = productService.upShelf(productList);

        if (resultMsg.getIsSuccess()) {
            // 用于通知搜索引擎MQ消息的productId集合
            List<Long> ids = new ArrayList();
            // CMS通知产品ID集合
            List<Integer> cms_ids = new ArrayList();
            for (Product prod : productList) {
                ids.add(prod.getId());
                cms_ids.add(Integer.valueOf(prod.getId().toString()));
            }

            try {
                // CMS上架接口
                cmsProductUpShelfService.productUpShelfByCms(cms_ids);
                List<Long> skuIdList = productSkuService.selectSkuIdsByProductIdList(ids);
                if (skuIdList != null && !skuIdList.isEmpty()) {
                    productService.executeUpdateCachePrice(skuIdList);
                    productService.changeProductInfoNotify(skuIdList, MsgOperation.ADD.getType());
                    productSkuService.updateProductSkuCache(skuIdList);
                }
            } catch (Exception e) {
                logger.error("上架失败，", e);
            }
        }
        return resultMsg;
    }

    @Override
    public boolean isLimitImage(Long productSkuId) throws ServiceException {
        try {
            return productImageDraftService.isLimitImage(productSkuId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public int findMaxSortNoBySkuId(Long productSkuId) throws ServiceException {
        try {
            return productImageDraftService.findMaxSortNoBySkuId(productSkuId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
    }

    @Override
    public Long saveImage(ProductImageDraft image) throws ServiceException {
        try {
            return productImageDraftService.saveImage(image);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1l;
        }
    }

    @Override
    public int deleteImageById(Long imageId) throws ServiceException {
        try {
            productImageDraftService.deleteImageById(imageId);
        } catch (Exception e) {
            logger.error("删除图片ID为：" + imageId + "的图片失败" + e.getMessage());
            return -1;
        }
        return 0;
    }

    @Override
    public int modifyImageDefault(Long productSkuId, Long imageId) throws ServiceException {
        try {
            productImageDraftService.modifyImageDefault(productSkuId, imageId);
        } catch (Exception e) {
            logger.error("修改默认图片ID为：" + imageId + "的图片失败" + e.getMessage(), e);
            return -1;
        }
        return 0;
    }

    @Override
    public boolean updateProductImageDraftBatch(List<ProductImageDraft> list) throws ServiceException {
        try {
            return productImageDraftService.updateProductImageBatch(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateProductImageBatch(List<ProductImage> list) throws ServiceException {
        try {
            return productImageService.updateProductImageBatch(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public int updateProductSkuDraft(ProductSkuDraft productSkuDraft) throws ServiceException {
        return productSkuDraftService.updateSkuIntroduce(productSkuDraft);
    }

    @Override
    public String previewProductDraftInfoPage(String productId) throws ServiceException {
        return productDraftService.previewProductDraftInfoPage(productId);
    }

}