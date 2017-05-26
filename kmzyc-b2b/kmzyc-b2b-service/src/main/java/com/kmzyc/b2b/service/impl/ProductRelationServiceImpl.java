package com.kmzyc.b2b.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.ProductRelationDao;
import com.kmzyc.b2b.dao.WxScanProductDao;
import com.kmzyc.b2b.model.ProductRelation;
import com.kmzyc.b2b.model.ProductRelationDetailAll;
import com.kmzyc.b2b.service.CarProductService;
import com.kmzyc.b2b.service.ProductRelationService;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.util.PromotionCacheUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

/**
 * 获取套餐信息
 * 
 * @author wangkai
 */
@SuppressWarnings("unchecked")
@Service("productRelationService")
public class ProductRelationServiceImpl implements ProductRelationService {
    private static Logger logger = LoggerFactory.getLogger(ProductRelationServiceImpl.class);
    @Resource(name = "productRelationDao")
    private ProductRelationDao productRelationDao;
    @Resource(name = "carProductService")
    private CarProductService carProductService;
    @Resource
    private MemCachedClient memCachedClient;
    @Resource(name = "wxScanProductDao")
    private WxScanProductDao wxScanProdctDao;
    @Resource
    private PromotionCacheUtil promotionCacheUtil;

    /**
     * 获取套餐信息产品信息算最大购买数量
     * 
     * @param id 套餐ID
     * @author wangkai
     */
    @Override
    public Map findProductRelationCount(Long id) throws Exception {
        List numList = new ArrayList();
        Map map = new HashMap();
        ProductRelation productRelation = productRelationDao.findByProductRelationId(id);
        List<ProductRelationDetailAll> prdList;
        if (productRelation.getPrdList() != null) {
            prdList = productRelation.getPrdList();
        } else {
            logger.info("套餐产品为空");
            return map;
        }
        ProductRelationDetailAll prd;
        CarProduct carp = new CarProduct();
        Double number;
        int num;

        if (prdList.size() > 0) {
            for (ProductRelationDetailAll aPrdList : prdList) {
                prd = aPrdList;
                carp.setProductSkuId(prd.getRelationSkuId());
                carp = carProductService.updateCarProduct(carp);
                number = carp.getStockCount().doubleValue() / prd.getProductCount().doubleValue();
                num = number.intValue();
                numList.add(num);
                logger.info("SkuId:" + prd.getRelationSkuId() + " ，每份：" + prd.getProductCount()
                        + " ，库存数量：" + carp.getStockCount() + " ,最多" + num + "份");
            }
            Collections.sort(numList);
            logger.info("可购买份数为:" + numList.get(0) + "份");
            map.put("count", numList.get(0));
            map.put("stauts", productRelation.getStatus());
        }
        return map;
    }

    /**
     * 获取套餐信息
     * 
     * @param id 套餐ID
     * @author wangkai
     */
    @Override
    public ProductRelation findProductRelationById(Long id) throws Exception {

        logger.info("查询套餐id为:" + id);
        return productRelationDao.findById(id);
    }

    /**
     * 查询主SKUID的所有组合产品
     * 
     * @param skuId
     * @return
     * @throws ServiceException
     */
    @Override
    public List<ProductRelation> queryProductRelationBySkuId(Long skuId) throws ServiceException {

        try {
            String key = ConfigurationUtil.getString("b2b_product_combine_key") + skuId;
            List<ProductRelation> prList = (List<ProductRelation>) memCachedClient.get(key);
            if (null == prList) {
                prList = productRelationDao.queryProductRelationBySkuId(skuId);
                memCachedClient.set(key, prList, new Date(5 * 60 * 1000));
            }
            return prList;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 排序组合
     * 
     * @param skuId
     * @return
     * @throws ServiceException
     */
    @Override
    public List<ProductRelation> querySortCombine(Long skuId, String openId)
            throws ServiceException {
        try {
            String key = ConfigurationUtil.getString("b2b_product_combine_key")
                    + (null == openId ? "" : openId) + skuId;
            List<ProductRelation> prList = (List<ProductRelation>) memCachedClient.get(key);
            if (null == prList) {
                prList = queryProductRelationBySkuId(skuId);
                List<Long> skuIds =
                        null != openId ? wxScanProdctDao.queryWXScanProductSku(openId) : null;
                if (null != prList && !prList.isEmpty()) {
                    Map<Long, Double> rateMap = new HashMap<>();
                    if (null != openId) {
                        double count;
                        for (ProductRelation pr : prList) {
                            count = 0;
                            for (ProductRelationDetailAll prda : pr.getPrdList()) {
                                count += skuIds.indexOf(prda.getRelationSkuId()) >= 0
                                        && !skuId.equals(prda.getRelationSkuId()) ? 1 : 0;
                            }
                            rateMap.put(pr.getRelationId(), count / (pr.getPrdList().size() - 1));
                        }
                    }
                    ProductRelation temp;
                    if (!rateMap.isEmpty()) {
                        for (int i = 0, len = prList.size(); i < len; i++) {
                            for (int j = len - 1; j > i; j--) {
                                if (rateMap.get(prList.get(j).getRelationId()) > rateMap
                                        .get(prList.get(j - 1).getRelationId())
                                        || (rateMap.get(prList.get(j).getRelationId())
                                                .equals(rateMap
                                                        .get(prList.get(j - 1).getRelationId()))
                                                && prList.get(j).getCreateDate().after(
                                                        prList.get(j - 1).getCreateDate()))) {
                                    temp = prList.get(j);
                                    prList.set(j, prList.get(j - 1));
                                    prList.set(j - 1, temp);
                                }
                            }
                        }
                    } else {
                        for (int i = 0, len = prList.size(); i < len; i++) {
                            for (int j = len - 1; j > i; j--) {
                                if (prList.get(j).getCreateDate()
                                        .after(prList.get(j - 1).getCreateDate())) {
                                    temp = prList.get(j);
                                    prList.set(j, prList.get(j - 1));
                                    prList.set(j - 1, temp);
                                }
                            }
                        }
                    }
                    memCachedClient.set(key, prList, new Date(5 * 60 * 1000));
                }
            }
            if (null != prList && !prList.isEmpty()) {
                Set<String> skuIds = new HashSet<>();
                for (ProductRelation pr : prList) {
                    for (ProductRelationDetailAll prd : pr.getPrdList()) {
                        skuIds.add(prd.getRelationSkuId().toString());
                    }
                }
                Map<String, ProductPromotion> ppMap =
                        promotionCacheUtil.getProductPromtoionInfoCahce(skuIds);
                if (null != ppMap) {
                    ProductPromotion pp;
                    for (ProductRelation pr : prList) {
                        for (ProductRelationDetailAll prd : pr.getPrdList()) {
                            if (null != (pp = ppMap.get(prd.getRelationSkuId().toString()))) {
                                prd.setRelationSkuPrice(
                                        pp.calculateFinalPrice(prd.getRelationSkuPrice()));
                                if (null != pp.getAvailableQuantity()) {
                                    prd.setStock(pp.getAvailableQuantity());
                                }
                                if (pp.getAvailableQuantity() != null && pp.getSellUpType() != null
                                        && pp.getAvailableQuantity() <= 0
                                        && pp.getSellUpType() == 1) {
                                    prd.setStock(0);
                                }
                            }
                        }
                    }
                }
            }
            return prList;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
