package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.ProductImageDAO;
import com.pltfm.app.dao.ProductSkuDAO;
import com.pltfm.app.service.ProductDictService;
import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductSkuAttrService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.vobject.CouponProduct;
import com.pltfm.app.vobject.ProductAndSku;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuExample;
import com.pltfm.app.vobject.ProductStock;

import redis.clients.jedis.JedisCluster;

/**
 * 产品SKU业务逻辑类
 *
 * @author humy
 * @since 2013-7-9
 */
@Service("productSkuService")
public class ProductSkuServiceImpl implements ProductSkuService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ProductSkuDAO productSkuDao;

    @Resource
    private ProductService productService;

    @Resource
    private ProductImageService productImageService;

    @Resource
    private ProductImageDAO productImageDao;

    @Resource
    private ProductSkuAttrService productSkuAttrService;

    @Resource
    private JedisCluster jedisCluster;

    @Resource
    private PromotionRemoteService promotionRemoteService;

    @Resource
    private ProductDictService productDictService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void addProductSku(ProductSku productSku) throws ServiceException {
        try {
            productSkuDao.insert(productSku);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Long, ProductAndSku> findProduct(String productSkuCode) throws ServiceException {
        try {
            return productSkuDao.findProduct(productSkuCode);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductSku findProductSkuByCode(String skuCode) throws ServiceException {
        if (StringUtils.isBlank(skuCode)) {
            return null;
        }
        ProductSkuExample pse = new ProductSkuExample();
        pse.createCriteria().andProductSkuCodeEqualTo(skuCode);

        try {
            List<ProductSku> skuList = productSkuDao.selectByExample(pse);
            if (skuList.isEmpty()) {
                return null;
            }

            return skuList.get(0);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage checkSkuRelation(Long skuId) throws ServiceException {
        ProductSku sku = this.queryProductSkuList(skuId);
        List<ProductSku> list = new ArrayList<ProductSku>();
        list.add(sku);

        ResultMessage rm = new ResultMessage();
        rm.setIsSuccess(true);

        // 检查库存
        rm = productService.checkStockForDelProduct(list, rm);
        if (!rm.getIsSuccess() && !StringUtils.isEmpty(rm.getMessage())) return rm;

        // 检查产品关联
        rm = productService.checkProductRelationForDelProduct(list, rm);
        if (!rm.getIsSuccess() && !StringUtils.isEmpty(rm.getMessage())) return rm;

        // 检查优惠券
        rm = productService.checkCouponForDelProduct(list, rm);
        if (!rm.getIsSuccess() && !StringUtils.isEmpty(rm.getMessage())) return rm;
        return null;
    }

    @Override
    public List<ProductSku> queryProductSkuList(ProductSku productSku) throws ServiceException {
        ProductSkuExample example = new ProductSkuExample(); // 组装where查询条件的对象
        example.createCriteria().andProductIdEqualTo(productSku.getProductId());
        example.setOrderByClause(" product_sku_code ");

        try {
            return productSkuDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductSku queryProductSkuList(Long productSkuId) throws ServiceException {
        try {
            return productSkuDao.selectByPrimaryKey(productSkuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteProductSku(Long productSkuId) throws ServiceException {
        try {
            List<ProductImage> imageList = productImageService.findAllBySkuId(productSkuId);
            if (CollectionUtils.isEmpty(imageList)) {
                String ap_path = ConfigurationUtil.getString("pictureUploadPath");
                productImageDao.deleteImagesBySkuId(productSkuId);
                for (ProductImage pi : imageList) {
                    if (pi.getImgPath() != null && !pi.getImgPath().isEmpty()) {
                        FileUploadUtils.deleteFile(ap_path + pi.getImgPath());
                    }
                    if (pi.getImgPath1() != null && !pi.getImgPath1().isEmpty()) {
                        FileUploadUtils.deleteFile(ap_path + pi.getImgPath1());
                    }
                    if (pi.getImgPath2() != null && !pi.getImgPath2().isEmpty()) {
                        FileUploadUtils.deleteFile(ap_path + pi.getImgPath2());
                    }
                    if (pi.getImgPath3() != null && !pi.getImgPath3().isEmpty()) {
                        FileUploadUtils.deleteFile(ap_path + pi.getImgPath3());
                    }
                    if (pi.getImgPath4() != null && !pi.getImgPath4().isEmpty()) {
                        FileUploadUtils.deleteFile(ap_path + pi.getImgPath4());
                    }
                    if (pi.getImgPath5() != null && !pi.getImgPath5().isEmpty()) {
                        FileUploadUtils.deleteFile(ap_path + pi.getImgPath5());
                    }
                    if (pi.getImgPath6() != null && !pi.getImgPath6().isEmpty()) {
                        FileUploadUtils.deleteFile(ap_path + pi.getImgPath6());
                    }
                    if (pi.getImgPath7() != null && !pi.getImgPath7().isEmpty()) {
                        FileUploadUtils.deleteFile(ap_path + pi.getImgPath7());
                    }
                    if (pi.getImgPath8() != null && !pi.getImgPath8().isEmpty()) {
                        FileUploadUtils.deleteFile(ap_path + pi.getImgPath8());
                    }
                    if (pi.getImgPath9() != null && !pi.getImgPath9().isEmpty()) {
                        FileUploadUtils.deleteFile(ap_path + pi.getImgPath9());
                    }
                    if (pi.getImgPath10() != null && !pi.getImgPath10().isEmpty()) {
                        FileUploadUtils.deleteFile(ap_path + pi.getImgPath10());
                    }
                }
            }
            productSkuAttrService.deleteProductSkuAttr(productSkuId);
            productSkuDao.deleteByPrimaryKey(productSkuId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

    }

    @Override
    public String findMaxSkuCodeByCateCode(String cateCode) throws ServiceException {
        try {
            return productSkuDao.findMaxSkuCodeByCateCode(cateCode);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateUnitWeight(List<ProductSku> skuList) throws ServiceException {
        try {
            productSkuDao.updateUnitWeightByPrimaryKey(skuList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Long> selectSkuIdsByProductIdList(List<Long> productIdList) throws ServiceException {
        try {
            return productSkuDao.selectSkuIdsByProductIdList(productIdList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> selectAllSkuCodeList() throws ServiceException {
        ProductSkuExample example = new ProductSkuExample();
        example.createCriteria().andStatusEqualTo("1");

        try {
            List<ProductSku> productSkuList = productSkuDao.selectByExample(example);
            List<String> skuCodeList = productSkuList.stream()
                    .map(ProductSku::getProductSkuCode)
                    .collect(Collectors.toList());

            return skuCodeList;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateCachePrice(List<Long> skuIdList) throws ServiceException {
        try {
            skuIdList.forEach(promotionRemoteService::updateProductPromotionCache);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Long> selectSkuIdListByCategoryIdList(List<Long> categoryIdList) throws ServiceException {
        try {
            return productSkuDao.selectSkuIdByManyCategory(categoryIdList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Long, ProductSku> getSkuIdAndCodeMap(List<Long> skuIdList) throws ServiceException {
        try {
            return productSkuDao.getSkuIdAndCodeMap(skuIdList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductSku> findProductSkuBySkuCodes(List<String> productSkuCodeList) throws ServiceException {
        try {
            if (productSkuCodeList != null && !productSkuCodeList.isEmpty())
                return productSkuDao.findProductSkuBySkuCodes(productSkuCodeList);
            else
                return null;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductSku findProductSkuBySkuId(Long skuId) throws ServiceException {
        try {
            return productSkuDao.selectByPrimaryKey(skuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateProductSku(ProductSku productSku) throws ServiceException {
        try {
            return productSkuDao.updateByPrimaryKeySelective(productSku);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductSku> findBySkuIds(List<Long> productSkuIds) throws ServiceException {
        ProductSkuExample example = new ProductSkuExample();
        example.createCriteria().andProductSkuIdIn(productSkuIds);
        try {
            return productSkuDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateStock(List<ProductStock> stockList) throws ServiceException {
        try {
            productSkuDao.updateStock(stockList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductSku> findSameSkuBarCodeProductSku(Map<String, Object> map) throws ServiceException {
        try {
            return productSkuDao.findSameSkuBarCodeProductSku(map);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateProductSkuCache(List<Long> productSkuIds) throws ServiceException {
        logger.info("更新sku信息缓存开始，skuid:{}.", productSkuIds);
        try {
            List<Map<String, String>> skuDetailsMap = getProductSkuDetailsMap(productSkuIds);
            if (CollectionUtils.isEmpty(skuDetailsMap)) {
                logger.error("更新sku信息缓存失败，没有找到sku信息，skuid:{}.", productSkuIds);
                throw new ServiceException("请输入有效的skuid。");
            }
            updateSkuCache(skuDetailsMap);

            logger.info("更新sku信息缓存成功，skuid:{}.", productSkuIds);
        } catch (Exception e) {
            logger.error("更新sku信息缓存失败，skuid:{},错误信息：{}", new Object[]{productSkuIds, e.getMessage()});
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateAllProductSkuCache() throws ServiceException {
        logger.info("更新所有商品sku详细信息的缓存开始.");
        try {
            int count = productSkuDao.countByExample(new ProductSkuExample());
            if (count <= 0) {
                logger.error("获取sku详细详细时，获取所有sku总数失败，count={}", new Object[]{count});
                throw new ServiceException("获取sku详细详细时，获取所有sku总数失败，count=" + count);
            }

            int skip = 0;
            while (skip < count) {
                logger.info("更新所有商品sku详细信息的缓存进行中，从第{}条开始，每页1000条.", skip);
                List<Map<String, String>> skuDetailsMap = productSkuDao.getProductSkuDetailsMap(null, skip, 1000);
                trimMap(skuDetailsMap);
                updateSkuCache(skuDetailsMap);
                skip += 1000;
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        logger.info("更新所有商品sku详细信息的缓存结束.");
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private void updateSkuCache(List<Map<String, String>> skuDetailsMap) throws ServiceException {
        try {
            if (CollectionUtils.isEmpty(skuDetailsMap)) {
                logger.error("更新sku信息缓存失败，没有找到sku信息，skuDetailsMap:{}.", skuDetailsMap);
                throw new ServiceException("更新sku信息缓存失败，没有找到sku信息.");
            }
            logger.info("更新sku信息缓存开始，本次将更新{}条.", skuDetailsMap.size());

            for (Map<String, String> detailsMap : skuDetailsMap) {
                String productSkuId = detailsMap.get("productSkuId");
                //插入缓存
                jedisCluster.hset("sku_details", productSkuId, JSON.toJSONString(detailsMap));
            }
            logger.info("更新sku信息缓存成功.");
        } catch (Exception e) {
            logger.error("更新sku信息缓存失败,错误信息：{}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Map<String, String>> getProductSkuDetailsMap(List<Long> productSkuIds) throws ServiceException {
        try {
            List<Map<String, String>> productSkuDetailsMap = productSkuDao.getProductSkuDetailsMap(productSkuIds, 0, 0);
            trimMap(productSkuDetailsMap);
            return productSkuDetailsMap;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 清空map中为空的value
     *
     * @param productSkuDetailsMap
     */
    private void trimMap(List<Map<String, String>> productSkuDetailsMap) {
        List<String> needRemoveKeyList = null;

        if (CollectionUtils.isNotEmpty(productSkuDetailsMap)) {
            for (Map<String, String> skuDetailsMap : productSkuDetailsMap) {
                needRemoveKeyList = new ArrayList();
                for (Map.Entry<String, String> entry : skuDetailsMap.entrySet()) {
                    if (StringUtils.isEmpty(entry.getValue())) {
                        needRemoveKeyList.add(entry.getKey());
                    }
                }

                if (CollectionUtils.isNotEmpty(needRemoveKeyList)) {
                    needRemoveKeyList.forEach(skuDetailsMap::remove);
                }
            }
        }
    }

    @Override
    public List<CouponProduct> selectCouponProductBySkuId(String skuCode) throws ServiceException {
        try {
            return productSkuDao.selectCouponProductBySkuId(skuCode);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BigDecimal getSupportPvProportion() throws ServiceException {
        return productDictService.getSupportPvProportion();
    }

    @Override
    public Double getSkuMaxPvValue(BigDecimal pvProportion, Double price, Double costPrice) throws ServiceException {
        double maxPvValue = new BigDecimal(price)
                .subtract(new BigDecimal(costPrice))
                .multiply(pvProportion)
                .divide(new BigDecimal(1), 1, BigDecimal.ROUND_HALF_UP)
                .doubleValue();

        return maxPvValue >= 1 ? maxPvValue : 1d;
    }

    @Override
    public Double getSkuMaxPvValue(Double price, Double costPrice) throws ServiceException {
        BigDecimal pvProportion = getSupportPvProportion();
        if (pvProportion == null) {
            return 1d;
        }
        return getSkuMaxPvValue(pvProportion, price, costPrice);
    }
}
