package com.kmzyc.promotion.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.bean.ResultMessage;
import com.kmzyc.promotion.app.dao.PromotionProductDAO;
import com.kmzyc.promotion.app.dao.PromotionProductDataDAO;
import com.kmzyc.promotion.app.enums.IsSyncIndex;
import com.kmzyc.promotion.app.enums.PromotionProductStatus;
import com.kmzyc.promotion.app.enums.PromotionStatus;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.service.CategoryAttrValueService;
import com.kmzyc.promotion.app.service.ProductService;
import com.kmzyc.promotion.app.service.ProductSkuAttrService;
import com.kmzyc.promotion.app.service.ProductSkuService;
import com.kmzyc.promotion.app.service.PromotionInfoService;
import com.kmzyc.promotion.app.service.PromotionProductService;
import com.kmzyc.promotion.app.vobject.CategoryAttrValue;
import com.kmzyc.promotion.app.vobject.ProductAndSku;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.ProductSkuAttr;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.app.vobject.PromotionProductDataExample;
import com.kmzyc.promotion.app.vobject.PromotionProductExample;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.promotion.optimization.cache.PromotionProductCacheProcess;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;

@Service("promotionProductService")
public class PromotionProductServiceImpl implements PromotionProductService {

    private static final Logger log = LoggerFactory.getLogger(PromotionProductServiceImpl.class);

    @Resource
    private PromotionProductDAO promotionProductDAO;

    @Resource
    private PromotionProductDataDAO promotionProductDataDao;
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

    /**
     * 产品SKU业务接口
     */
    @Resource(name = "productSkuService")
    private ProductSkuService productSkuService;
    // 缓存相关
    @Resource
    private PromotionProductCacheProcess promotionProductCacheProcess;
    /**
     * 产品业务接口
     */
    @Resource
    private ProductService productService;

    /**
     * 查询查询活动产品列表
     * 
     * @param page 分页对象
     * @param promotion 活动基本信息
     * @return Page
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public Page queryPromotionProductList(Page pageParam, PromotionProduct promotionProduct)
            throws Exception {
        int pageNo = pageParam.getPageNo();// 当前查询第几页
        if (pageNo == 0)
            pageNo = 1;// 首次查询第一页
        int pageSize = pageParam.getPageSize(); // 每页显示记录数
        int skip = (pageNo - 1) * pageSize + 1;
        int max = (pageNo - 1) * pageSize + pageSize;
        Page page = null;
        try {
            promotionProduct.setSkip(skip);
            promotionProduct.setMax(max);
            page = promotionProductDAO.selectPageByVo(pageParam, promotionProduct);


            for (PromotionProduct promotionProd : (List<PromotionProduct>) page.getDataList()) {
                ProductSku productSku =
                        productSkuService.queryProductSkuList(promotionProd.getProductSkuId());
                if (productSku == null)
                    continue;
                promotionProd.setProductSkuCode(productSku.getProductSkuCode());
                promotionProd.setProductName(productService
                        .findProductById(productSku.getProductId()).getProductTitle());
                ProductSkuAttr productSkuAttr = new ProductSkuAttr();
                productSkuAttr.setProductSkuId(promotionProd.getProductSkuId());
                List<ProductSkuAttr> skuAttrList =
                        productSkuAttrService.queryProductSkuAttrList(productSkuAttr);
                for (ProductSkuAttr skuAttr : skuAttrList) {
                    CategoryAttrValue cav = categoryAttrValueService
                            .queryCategoryAttrValue(skuAttr.getCategoryAttrValueId());
                    if (cav == null)
                        continue;
                    skuAttr.setCategoryAttrValue(cav.getCategoryAttrValue());
                }
                promotionProd.setProductSkuAttrList(skuAttrList);
            }
        } catch (SQLException e) {
            log.error("查询查询活动产品列表", e);
            throw e;
        }
        return page;
    }

    /**
     * 增加活动产品
     * 
     * @param promotionProduct 活动产品对象
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void addPromotionProduct(PromotionProduct promotionProduct, Double price,
            PromotionInfo promotion) {
        Date now = new Date();
        try {
            Long promotionId = promotionProduct.getPromotionId();
            String[] productSkuIds = promotionProduct.getProductSkuIds().split(",");
            String inPruductSkuIds = "";
            if (promotion.getProductFilterType() == 2) {// 指定商品
                inPruductSkuIds = promotionProductDAO.selectInPruductSkuIds(promotionId);
            } else {
                inPruductSkuIds = promotionProductDAO.selectExceptionPruductSkuIds(promotionId);
            }
            inPruductSkuIds = "," + inPruductSkuIds + ",";

            for (String productSkuId : productSkuIds) {
                if (!inPruductSkuIds.contains("," + productSkuId + ",")) {
                    PromotionProduct record = new PromotionProduct();
                    record.setPromotionId(promotionId);
                    record.setProductSkuId(Long.parseLong(productSkuId));
                    record.setCreateTime(now);
                    record.setModifyTime(now);
                    if (promotionProduct.getCategory() != null) {
                        record.setCategory(promotionProduct.getCategory());
                    }
                    if (promotion.getStatus().equals(PromotionStatus.ISSUE.getValue())) {// 已审核通过并且未过期的活动
                        record.setStatus(PromotionProductStatus.NOT_ONLINE.getValue());
                    } else {
                        record.setStatus(PromotionProductStatus.ONLINE.getValue());
                    }
                    if (price != null) {
                        record.setPrice(new BigDecimal(price));
                    }
                    promotionProductDAO.insertSelective(record);
                }

            }
        } catch (Exception e) {
            log.error("增加活动产品异常：", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 删除活动产品
     * 
     * @param promotionProduct 活动产品对象
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePromotionProduct(PromotionProduct promotionProduct) throws Exception {
        String[] promotionProductIds = promotionProduct.getPromotionProductIds().split(",");
        for (String promotionProductId : promotionProductIds) {
            Long id = Long.valueOf(promotionProductId);
            promotionProduct = promotionProductDAO.selectByPrimaryKey(id);
            promotionProductDAO.deleteByPrimaryKey(id);
            // 删除附赠赠品
            PromotionProductDataExample ppde = new PromotionProductDataExample();
            ppde.createCriteria().andPromotionProductIdEqualTo(new BigDecimal(id));
            promotionProductDataDao.deleteByExample(ppde);
            // cache
            // productPriceCacheService.createProductPriceCache(promotionProduct.getProductSkuId());

        }
        // //通知MQ
        promotionInfoService.updateIsNotSyncIndex(promotionProduct.getPromotionId());

    }

    /**
     * 根据活动ID删除活动产品
     * 
     * @param promotionId 活动产品对象
     * @return
     * @throws Exception
     */
    @Override
    public void deletePromotionProduct(Long promotionId) throws SQLException {

        PromotionProductExample example = new PromotionProductExample();
        example.createCriteria().andPromotionIdEqualTo(promotionId).andCategoryIsNull();
        promotionProductDAO.deleteByExample(example);
        PromotionProductDataExample examplePPd = new PromotionProductDataExample();
        examplePPd.createCriteria().andPromotionIdEqualTo(new BigDecimal(promotionId));
        // 如果活动没有统一制定赠品， 删除所有附赠赠品
        PromotionInfo p = promotionInfoService.getPromotionById(promotionId);
        if (p.getPromotionData() == null) {
            promotionProductDataDao.deleteByExample(examplePPd);
        }
        // //通知MQ
        promotionInfoService.updateIsNotSyncIndex(promotionId);
        // cache
        // productPriceCacheService.notify(promotionId);

    }

    /**
     * 根据活动ID删除活动产品及所有关联项
     * 
     * @param promotionId 活动产品对象
     * @return
     * @throws Exception
     */
    @Override
    public void deletePromotionProductAll(Long promotionId) throws SQLException {

        PromotionProductExample example = new PromotionProductExample();
        example.createCriteria().andPromotionIdEqualTo(promotionId);
        promotionProductDAO.deleteByExample(example);
        PromotionProductDataExample examplePPd = new PromotionProductDataExample();
        examplePPd.createCriteria().andPromotionIdEqualTo(new BigDecimal(promotionId));
        // 删除所有附赠赠品
        promotionProductDataDao.deleteByExample(examplePPd);
        // //通知MQ
        promotionInfoService.updateIsNotSyncIndex(promotionId);
    }

    /**
     * 根据活动ID删除例外产品 add by songmiao 2015/11/26
     * 
     * @param promotionId 活动产品对象
     * @return
     * @throws Exception
     */
    @Override
    public void deletePromotionExceptionProduct(Long promotionId) throws SQLException {

        PromotionProductExample example = new PromotionProductExample();
        example.createCriteria().andPromotionIdEqualTo(promotionId).andCategoryEqualTo("-1");
        promotionProductDAO.deleteByExample(example);
        // //通知MQ
        promotionInfoService.updateIsNotSyncIndex(promotionId);
    }

    @Override
    public void updatePromotionProductPrice(PromotionProduct promotionProduct) {

        try {
            promotionProductDAO.updateByPrimaryKeySelective(promotionProduct);

            // promotionProduct =
            // promotionProductDAO.selectByPrimaryKey(promotionProduct.getPromotionProductId());
            // 通知MQ
            // promotionService.updateIsNotSyncIndex(promotionProduct.getPromotionId());
            // 更新cache
            // productPriceCacheService.createProductPriceCache(promotionProduct.getProductSkuId());
        } catch (Exception e) {
            log.error("根据活动ID删除例外产品", e);
            throw new ServiceException(e);
        }

    }

    /**
     * 创建更新缓存时需要查询产品列表
     * 
     * @throws Exception
     */
    @Override
    public Pagination queryProductListByPromotionInfo(Pagination page, PromotionInfo info)
            throws Exception {

        pageInitData(page);
        page = promotionProductDAO.getPromotionProduct(page, info);
        return page;
    }

    /**
     * 创建更新缓存时需要查询产品列表
     * 
     * @throws Exception
     */
    @Override
    public Pagination queryProductAndSkuListByPromotionInfo(Pagination page, PromotionInfo info)
            throws Exception {

        // {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌',5:'指定商家'}
        pageInitData(page);
        page = promotionProductDAO.getPromotionProductAndSku(page, info);
        return page;
    }

    private void pageInitData(Pagination page) {
        page.setStartindex((page.getPage() - 1) * page.getNumperpage() + 1);
        page.setEndindex(page.getPage() * page.getNumperpage());
    }

    @Override
    public ResultMessage updatePromotionProductForXianGou(PromotionProduct promotionProduct) {
        ResultMessage rmsg = new ResultMessage();
        rmsg.setMark(0);
        rmsg.setIsSuccess(true);
        try {
            PromotionProduct promotionProductTmp = promotionProductDAO
                    .selectByPrimaryKey(promotionProduct.getPromotionProductId());
            if (promotionProduct.getMinBuy() != null && promotionProductTmp.getMaxBuy() != null) {
                if (promotionProduct.getMinBuy().intValue() >= promotionProductTmp.getMaxBuy()
                        .intValue()) {
                    rmsg.setIsSuccess(false);
                    rmsg.setMark(1);
                    rmsg.setMessage("最小购买数必须小于最大购买数!");
                    return rmsg;
                }
            }
            if (promotionProduct.getMaxBuy() != null && promotionProductTmp.getMinBuy() != null) {
                if (promotionProductTmp.getMinBuy().intValue() >= promotionProduct.getMaxBuy()
                        .intValue()) {
                    rmsg.setIsSuccess(false);
                    rmsg.setMark(2);
                    rmsg.setMessage("最小购买数必须小于最大购买数!");
                    return rmsg;
                }
            }

            int count = promotionProductDAO.updateByPrimaryKeySelective(promotionProduct);
            if (count < 1) {
                rmsg.setIsSuccess(false);
                rmsg.setMark(3);
                return rmsg;
            }

        } catch (Exception e) {
            log.error("updatePromotionProductForXianGou异常：", e);
            rmsg.setMark(3);
            rmsg.setIsSuccess(false);
        }
        return rmsg;
    }

    @Override
    public List<ProductSku> queryProductsByPromotionInfo(PromotionInfo promotion)
            throws SQLException {
        // 活动商品筛选类型:1:'全场',2:'指定商品',3:'产品类目',4:'品牌'

        int selectProductType = promotion.getProductFilterType().intValue();
        int shopSort = promotion.getShopSort().intValue();
        if (selectProductType == 1 && shopSort == 1) {
            return null;
        }
        Map<String, String> conditionMap = new HashMap<String, String>();
        String sql = "PROMOTION_PRODUCT.getProductInfoNoPage";
        String promotionSelectSql =
                promotion.getProductFilterSql() == null ? "" : promotion.getProductFilterSql();
        int start = 0, end = promotionSelectSql.length();
        if (promotionSelectSql.startsWith(",")) {
            start = start + 1;
        }
        if (promotionSelectSql.endsWith(",")) {
            end = end - 1;
        }
        conditionMap.put("promotionId", promotion.getPromotionId().toString());
        if (shopSort == 2) {
            conditionMap.put("shopCodes", promotion.getSupplierId().toString());
        } else if (shopSort == 3) {
            // 康美自营代销
            conditionMap.put("shopCodes", "1");
            sql = "PROMOTION_PRODUCT.getProductInfoANoPage";
        } else {
            conditionMap.put("shopCodes", "");
        }
        // {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌'}
        switch (selectProductType) {
            case 1:
                break;
            case 2:
                sql = "PROMOTION_PRODUCT.getProductSkuIdBySkuNoPage";
                break;
            case 3:
                conditionMap.put("categoryIds", promotionSelectSql.substring(start, end));
                break;
            case 4:
                conditionMap.put("brandIds", promotionSelectSql.substring(start, end));
                break;
            default:
                break;
        }

        Pagination page = new Pagination();
        page.setObjCondition(conditionMap);
        // 过滤掉正在进行预售的商品，modify by zhuyanling 20160718
        List<ProductSku> list = promotionProductDAO.getPromotionProductsByInfo(sql, page);
        return list;
    }

    @Override
    public List<ProductAndSku> queryProductByPromotionInfo(Pagination page,
            PromotionInfo promotion) {
        // {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌',5:'指定商家'}
        Integer selectProductType = promotion.getProductFilterType();
        Map<String, String> conditionMap = new HashMap<String, String>();
        // String sql = "PROMOTION_PRODUCT.getProductInfo";
        String sql = "promotion_optimization.getProductInfo";
        String promotionSelectSql =
                promotion.getProductFilterSql() == null ? "" : promotion.getProductFilterSql();
        int start = 0, end = promotionSelectSql.length();
        if (promotionSelectSql.startsWith(",")) {
            start = start + 1;
        }
        if (promotionSelectSql.endsWith(",")) {
            end = end - 1;
        }
        conditionMap.put("promotionId", promotion.getPromotionId().toString());
        int shopSort = promotion.getShopSort().intValue();
        if (shopSort == 2) {
            conditionMap.put("shopCodes", promotion.getSupplierId().toString());
        } else if (shopSort == 3) {
            // 康美自营代销
            conditionMap.put("shopCodes", "1");
            // sql = "PROMOTION_PRODUCT.getProductInfoA";
            sql = "promotion_optimization.getProductInfoKM";
        } else {
            conditionMap.put("shopCodes", "");
        }
        // {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌'}
        switch (selectProductType.intValue()) {
            case 1:
                break;
            case 2:
                // sql = "PROMOTION_PRODUCT.getProductSkuIdBySku";
                sql = "promotion_optimization.getPromotionProductSkuIdBySku";
                break;
            case 3:
                conditionMap.put("categoryIds", promotionSelectSql.substring(start, end));
                break;
            case 4:
                conditionMap.put("brandIds", promotionSelectSql.substring(start, end));
                break;
            default:
                break;
        }
        page.setObjCondition(conditionMap);

        List<ProductAndSku> map = promotionProductDAO.queryProductByPromotionInfo(sql, page);
        return map;
    }

    /**
     * add by songmiao 2015-8-5 已审核上线活动修改参加活动商品
     * 
     * @throws SQLException
     */
    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public int updataPromotionProductStatus(Long promotionProductId) throws SQLException {
        PromotionProduct pp = new PromotionProduct();
        int result = 0;
        try {
            // 缓存变更类型 1 是移除 2是添加
            int type = 1;
            pp = promotionProductDAO.selectByPrimaryKey(promotionProductId);
            PromotionInfo pi = promotionInfoService.getPromotionById(pp.getPromotionId());
            if (pp.getStatus().equals(PromotionProductStatus.ONLINE.getValue())) {
                pp.setStatus(PromotionProductStatus.EXPIRED.getValue());
            } else {
                type = 2;
                pp.setStatus(PromotionProductStatus.ONLINE.getValue());
            }
            if (pi.getPromotionType().equals(PromotionTypeEnums.GANT.getValue())
                    && pi.getPromotionData() == null) {
                List<PromotionProductData> listp =
                        promotionProductDataDao.queryListBypromotionProductId(promotionProductId);
                if (listp.size() <= 0) {
                    return 2;
                }
            }
            pp.setModifyTime(new Date());
            int count = promotionProductDAO.updateByPrimaryKeySelective(pp);
            promotionProductDataDao.updatePPdStatusByPPId(pp);
            if (count != 1)
                result = 1;
            // 商品状态变更后更新缓存,如活动为特价或打折或app特价活动的同步状态改为未同步
            boolean cResult = promotionProductCacheProcess.createOrUpdateProductPromotionCache(
                    pp.getProductSkuId(), pp.getPromotionId(), type);
            if (!cResult) {
                throw new ServiceException("更新已审核活动商品缓存报错");
            }
            if (pi.getPromotionType().equals(PromotionTypeEnums.SALE.getValue())
                    || pi.getPromotionType().equals(PromotionTypeEnums.DISCOUNT.getValue())
                    || pi.getPromotionType().equals(PromotionTypeEnums.SALE_APP.getValue())) {
                pi.setIsSycnIndex(IsSyncIndex.NOTSYNC.getKey());
                promotionInfoService.updatePromotion(pi);
            }
        } catch (ServiceException e) {
            log.error("更新已审核活动商品报错");
            throw e;
        }
        return result;
    }

    @Override
    public PromotionProduct queryPromotionProductById(Long promotionProductId) throws SQLException {
        try {
            return promotionProductDataDao.queryByPromotionProductId(promotionProductId);
        } catch (ServiceException e) {
            log.error("更新已审核活动商品报错", e);
            throw e;
        }
    }

}
