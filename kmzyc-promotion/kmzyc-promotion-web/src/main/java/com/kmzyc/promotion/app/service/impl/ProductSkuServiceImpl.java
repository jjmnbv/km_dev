package com.kmzyc.promotion.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.ProductSkuDAO;
import com.kmzyc.promotion.app.service.CategoryAttrValueService;
import com.kmzyc.promotion.app.service.ProductPriceCacheService;
import com.kmzyc.promotion.app.service.ProductSkuAttrService;
import com.kmzyc.promotion.app.service.ProductSkuService;
import com.kmzyc.promotion.app.service.PromotionInfoService;
import com.kmzyc.promotion.app.vobject.CarryStockOutDetailVO;
import com.kmzyc.promotion.app.vobject.ProductAndSku;
import com.kmzyc.promotion.app.vobject.ProductPrice;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.ProductSkuAttr;
import com.kmzyc.promotion.app.vobject.ProductSkuExample;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.exception.ServiceException;

/**
 * 产品SKU业务逻辑类
 * 
 * @author humy
 * @since 2013-7-9
 */
@Service("productSkuService")
@SuppressWarnings("unchecked")
public class ProductSkuServiceImpl implements ProductSkuService {
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(ProductSkuServiceImpl.class);

    /**
     * 产品SKU数据库接口
     */
    @Resource(name = "productSkuDAO")
    private ProductSkuDAO productSkuDAO;

    @Resource
    private PromotionInfoService promotionInfoService;
    /**
     * 产品SKU属性业务接口
     */
    @Resource(name = "productSkuAttrService")
    private ProductSkuAttrService productSkuAttrService;

    /**
     * 产品类目属性值业务接口
     */
    @Resource(name = "categoryAttrValueService")
    private CategoryAttrValueService categoryAttrValueService;

    @Resource(name = "productPriceCacheService")
    private ProductPriceCacheService productPriceCacheService;

    /**
     * 根据SKU，查找商品信息
     */
    @Override
    public Map<Long, ProductAndSku> findProduct(String productSkuCode) {
        return productSkuDAO.findProduct(productSkuCode);
    }

    @Override
    public ProductSku findProductSkuByCode(String skuCode) throws Exception {
        if (StringUtils.isNotBlank(skuCode)) {
            ProductSkuExample pse = new ProductSkuExample();
            pse.createCriteria().andProductSkuCodeEqualTo(skuCode);
            List<ProductSku> skuList = productSkuDAO.selectByExample(pse);
            if (skuList.isEmpty())
                return null;
            return skuList.get(0);
        }
        return null;
    }

    @Override
    public void updatePrice(List<ProductSku> skuList) throws Exception {
        productSkuDAO.updateBatch(skuList);
    }

    /**
     * 查询产品SKU
     * 
     * @param productSku 产品SKU基本信息
     * @return List<ProductSku>
     * @throws Exception
     */
    @Override
    public List<ProductSku> queryProductSkuList(ProductSku productSku) throws Exception {
        ProductSkuExample example = new ProductSkuExample(); // 组装where查询条件的对象
        example.createCriteria().andProductIdEqualTo(productSku.getProductId());
        example.setOrderByClause(" product_sku_code ");
        return productSkuDAO.selectByExample(example);
    }

    /**
     * 根据主键查询产品SKU
     * 
     * @param productSkuId 产品SKU主键
     * @return ProductSku
     * @throws Exception
     */
    @Override
    public ProductSku queryProductSkuList(Long productSkuId) throws Exception {
        return productSkuDAO.selectByPrimaryKey(productSkuId);
    }

    /**
     * 查询产品SKU列表
     * 
     * @param page 分页对象
     * @param productSku 产品SKU基本信息
     * @return Page
     * @throws Exception
     */
    @Override
    public Page queryProductSkuList(Page pageParam, ProductSku productSku) throws Exception {
        int pageNo = pageParam.getPageNo();// 当前查询第几页
        if (pageNo == 0)
            pageNo = 1;// 首次查询第一页
        int pageSize = pageParam.getPageSize(); // 每页显示记录数
        int skip = (pageNo - 1) * pageSize + 1;
        int max = (pageNo - 1) * pageSize + pageSize;
        Page page = null;
        try {
            productSku.setSkip(skip);
            productSku.setMax(max);
            page = productSkuDAO.selectPageByVo(pageParam, productSku);
            // page.setPageNo(pageNo);// 当前查询第几页
            for (ProductSku sku : (List<ProductSku>) page.getDataList()) {
                ProductSkuAttr productSkuAttr = new ProductSkuAttr();
                productSkuAttr.setProductSkuId(sku.getProductSkuId());
                List<ProductSkuAttr> skuAttrList =
                        productSkuAttrService.queryProductSkuAttrList(productSkuAttr);
                for (ProductSkuAttr skuAttr : skuAttrList) {
                    if (categoryAttrValueService
                            .queryCategoryAttrValue(skuAttr.getCategoryAttrValueId()) != null) {
                        skuAttr.setCategoryAttrValue(categoryAttrValueService
                                .queryCategoryAttrValue(skuAttr.getCategoryAttrValueId())
                                .getCategoryAttrValue());
                    }
                }
                sku.setProductSkuAttrList(skuAttrList);

            }
        } catch (SQLException e) {
            logger.error("查询产品SKU列表异常", e);
            throw e;
        }
        return page;
    }

    @Override
    public String findMaxSkuCodeByCateCode(String cateCode) throws Exception {
        return productSkuDAO.findMaxSkuCodeByCateCode(cateCode);
    }

    @Override
    public void updateSkuByProductSkuIdBatch(List list) throws Exception {
        productSkuDAO.updateSkuByProductSkuIdBatch(list);
    }

    @Override
    public List<String> selectSkuCodeListByCategoryBrandBySkucode(ProductSku sku) throws Exception {
        return productSkuDAO.selectSkuCodeListByCategoryBrandBySkucode(sku);

    }

    @Override
    public List<String> selectSkuCodeListByCategory(ProductSku sku) throws Exception {

        return productSkuDAO.selectSkuCodeListByCategory(sku);

    }

    @Override
    public List<String> selectSamePriceStatus(ProductSku sku) throws Exception {

        ProductSku productSku = selectProductSkuBySkucode(sku);

        double money = productSku.getPrice();
        int myMoeny = (int) money;
        String price = String.valueOf(myMoeny);

        int length = price.length();
        int first = Integer.valueOf(price.substring(0, 1));

        double beginPrice = first;
        double lastPrice = first + 1;

        Map<String, Object> map = Maps.newHashMap();
        for (int i = 0; i < length - 1; i++) {

            beginPrice = beginPrice * 10;
            lastPrice = lastPrice * 10;
        }
        map.put("beginPrice", beginPrice);
        map.put("lastPrice", lastPrice);
        map.put("productSkuCode", sku.getProductSkuCode());

        List<String> list = productSkuDAO.selectSkuCodeListByPriceStatus(map);

        return list;
    }

    @Override
    public ProductSku selectProductSkuBySkucode(ProductSku sku) throws Exception {
        return productSkuDAO.selectProductSkuBySkucode(sku);
    }

    @Override
    public void updateUnitWeight(List<ProductSku> skuList) throws Exception {
        productSkuDAO.updateUnitWeightByPrimaryKey(skuList);
    }

    @Override
    public List<String> selectSkuCodeListByCategoryIdList(List<Long> categoryIdList)
            throws Exception {

        return productSkuDAO.selectSkuCodeByManyCategory(categoryIdList);

    }

    @Override
    public List<Long> selectSkuIdsByProductIdList(List<Long> productIdList) throws Exception {

        return productSkuDAO.selectSkuIdsByProductIdList(productIdList);
    }

    @Override
    public List<String> selectAllSkuCodeList() throws Exception {
        ProductSkuExample example = new ProductSkuExample();
        example.createCriteria().andStatusEqualTo(new String("1"));

        List<ProductSku> productSkusList = productSkuDAO.selectByExample(example);
        List<String> skuCodeList = new ArrayList<String>();

        for (ProductSku sku : productSkusList) {

            skuCodeList.add(sku.getProductSkuCode());

        }

        return skuCodeList;

    }

    @Override
    public void updateCachePrice(List<Long> skuIdList) {
        try {
            List<PromotionInfo> infoList =
                    promotionInfoService.getExpiryPromotionListByTime(new Date());
            for (Long skuId : skuIdList) {
                productPriceCacheService.createProductPriceCache(skuId, infoList);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Long> selectSkuIdListByCategoryIdList(List<Long> categoryIdList) throws Exception {
        return productSkuDAO.selectSkuIdByManyCategory(categoryIdList);
    }

    @Override
    public Map<Long, ProductSku> getSkuIdAndCodeMap(List<Long> skuIdList) throws Exception {
        return productSkuDAO.getSkuIdAndCodeMap(skuIdList);
    }

    /**
     * 根据SKUCODE的集合，查找所有的ProductSku信息
     * 
     * @param productSkuCodeList
     * @return create by luoyi 2014/04/06
     */
    @Override
    public List<ProductAndSku> findProductSkuListBySkuCodes(
            List<CarryStockOutDetailVO> productSkuCodeList) {
        List<ProductAndSku> list = productSkuDAO.findProductsBySkuCodeList(productSkuCodeList);
        return list;
    }

    @Override
    public List<ProductSku> findProductSkuBySkuCodes(List<String> productSkuCodeList)
            throws Exception {
        if (productSkuCodeList != null && !productSkuCodeList.isEmpty())
            return productSkuDAO.findProductSkuBySkuCodes(productSkuCodeList);
        else
            return null;
    }

    @Override
    public ProductSku findProductSkuBySkuId(Long skuId) throws Exception {
        return productSkuDAO.selectByPrimaryKey(skuId);
    }

    /**
     * 批量查询产品价格
     * 
     * @param params
     * @return
     * @throws SQLException
     */
    @Override
    public Map<Long, BigDecimal> queryBatchPrice(List<Long> skuIds) throws ServiceException {
        try {
            List<ProductPrice> ppList = productSkuDAO.queryBatchPrice(skuIds);
            if (null != ppList && !ppList.isEmpty()) {
                Map<Long, BigDecimal> rsMap = new HashMap<Long, BigDecimal>();
                for (ProductPrice pp : ppList) {
                    rsMap.put(pp.getSkuId(), pp.getFinalPrice());
                }
                return rsMap;
            }
        } catch (Exception e) {
            logger.error("批量查询产品价格异常", e);
        }
        return null;
    }

    @Override
    public Page queryPresellProductSkuList(Page pageParam, ProductSku productSku) throws Exception {
        int pageNo = pageParam.getPageNo();// 当前查询第几页
        if (pageNo == 0)
            pageNo = 1;// 首次查询第一页
        int pageSize = pageParam.getPageSize(); // 每页显示记录数
        int skip = (pageNo - 1) * pageSize + 1;
        int max = (pageNo - 1) * pageSize + pageSize;
        Page page = null;
        try {
            productSku.setSkip(skip);
            productSku.setMax(max);
            page = productSkuDAO.selecPresellProductByVo(pageParam, productSku);
            // page.setPageNo(pageNo);// 当前查询第几页
            for (ProductSku sku : (List<ProductSku>) page.getDataList()) {
                ProductSkuAttr productSkuAttr = new ProductSkuAttr();
                productSkuAttr.setProductSkuId(sku.getProductSkuId());
                List<ProductSkuAttr> skuAttrList =
                        productSkuAttrService.queryProductSkuAttrList(productSkuAttr);
                for (ProductSkuAttr skuAttr : skuAttrList) {
                    if (categoryAttrValueService
                            .queryCategoryAttrValue(skuAttr.getCategoryAttrValueId()) != null) {
                        skuAttr.setCategoryAttrValue(categoryAttrValueService
                                .queryCategoryAttrValue(skuAttr.getCategoryAttrValueId())
                                .getCategoryAttrValue());
                    }
                }
                sku.setProductSkuAttrList(skuAttrList);
            }
        } catch (SQLException e) {
            logger.error("查询产品SKU列表异常", e);
            throw e;
        }
        return page;

    }
}
