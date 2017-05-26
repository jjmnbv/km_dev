package com.kmzyc.promotion.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.promotion.app.dao.BaseProductDao;
import com.kmzyc.promotion.app.service.CompositionService;
import com.kmzyc.promotion.app.util.Constants;
import com.kmzyc.promotion.app.vobject.BaseProduct;
import com.kmzyc.promotion.app.vobject.Composition;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.promotion.sys.util.StringUtil;

/**
 * 套餐service
 * 
 * @author xlg
 * 
 */
@Service("compositionService")
public class CompositionServiceImpl implements CompositionService {
    private Logger logger = LoggerFactory.getLogger(CompositionServiceImpl.class);
    @Resource
    private BaseProductDao baseProductDao;

    /**
     * 查询套餐
     * 
     * @param suitId
     * @return
     * @throws ServiceException
     */
    @Override
    public Composition getComposition(Long suitId, Integer amount) throws ServiceException {
        try {
            List<Map<String, String>> rsList = baseProductDao.queryAbleComposition(suitId, amount);
            Composition ccp = null;
            if (null != rsList && !rsList.isEmpty()) {
                ccp = new Composition();
                ccp.setCid(suitId);
                ccp.setIsOutOfStock(false);
                List<BaseProduct> productList = new ArrayList<BaseProduct>();
                BigDecimal suitPrice = BigDecimal.ZERO, splitPrice = BigDecimal.ZERO,
                        pvValue = BigDecimal.ZERO, costIncomeMoney = BigDecimal.ZERO;// 套餐价格、分开价格,PV,或以金额
                String suitName = null;// 套餐名称
                Integer status = null, minStock = null;// 套餐状态，最小库存
                for (Map<String, String> rs : rsList) {
                    // 数量
                    Integer productCount = 0;
                    if (null != rs.get("PRODUCTCOUNT")) {
                        productCount = Integer.parseInt(rs.get("PRODUCTCOUNT"));
                    }
                    // 库存数量
                    Integer stockCount = 0;
                    if (null != rs.get("STOCKCOUNT")) {
                        stockCount = Integer.parseInt(rs.get("STOCKCOUNT"));
                    }
                    if (null == minStock
                            || (productCount != 0 && (minStock > (stockCount / productCount)))) {
                        minStock = stockCount / productCount;
                        ccp.setStockCount(minStock);
                    }

                    if (null == status) {
                        status = Integer.parseInt(rs.get("STATUS"));// 套餐状态
                        if (3 != status) {
                            ccp.setIsOutOfStock(true);
                        }
                    }
                    if (null == suitName) {
                        suitName = rs.get("SUITNAME");// 套餐名称
                        ccp.setName(suitName);
                    }
                    if (0 == BigDecimal.ZERO.compareTo(suitPrice)) {
                        suitPrice = new BigDecimal(rs.get("SUITPRICE")).setScale(2);// 套餐价格
                        ccp.setPrice(suitPrice);
                        ccp.setFinalPrice(suitPrice);
                    }
                    BigDecimal productPrice = new BigDecimal(rs.get("PRODUCTPRICE"));// 产品价格
                    String[] shopInfo = rs.get("SHOPINFO").split(",");// 商家信息格式类型,名称
                    if (null == shopInfo || 2 != shopInfo.length) {
                        throw new ServiceException(
                                com.kmzyc.promotion.app.util.Constants.DATAEXCEPTION, "套餐数据错误",
                                null);
                    }

                    String imgInfo = rs.get("IMGINFO");// 图片信息
                    Integer supplierType = 1;
                    if (!StringUtil.isEmpty(shopInfo[0])) {
                        supplierType = Integer.parseInt(shopInfo[0]);
                    }
                    String shopName = null;
                    if (!StringUtil.isEmpty(shopInfo[1])) {
                        shopName = shopInfo[1];
                    }
                    if (null == stockCount || stockCount < 1) {
                        throw new ServiceException(Constants.UNDERSTOCK, "商品库存不足", null);
                    }
                    pvValue = new BigDecimal(rs.get("PVALUE"));
                    costIncomeMoney = new BigDecimal(rs.get("COSTINCOMERATIO"));
                    BaseProduct bp = new BaseProduct();
                    bp.setSkuAttrValue(rs.get("CATEGORYATTRVALUE"));
                    bp.setBrandName(rs.get("BRANDNAME"));// 品牌
                    bp.setBatchNo(rs.get("BATCHNO"));// 批次号
                    bp.setCredits(Integer.parseInt(rs.get("CREDITS")));// 批次号
                    bp.setSellerId(Long.parseLong(rs.get("SELLERID")));// 商家ID
                    bp.setSupplierCode(rs.get("SELLERID"));// 商家ID
                    bp.setProductSkuId(Long.parseLong(rs.get("SKUID")));// skuId
                    bp.setProductID(Long.parseLong(rs.get("PRODUCTID")));// productId
                    bp.setBrandId(Long.parseLong(rs.get("BRANDID")));// brandId
                    bp.setCategoryId(Long.parseLong(rs.get("CATEGORYID")));// categoryId
                    bp.setTitle(rs.get("PRODUCTTITLE"));// 标题
                    bp.setName(rs.get("PRODUCTNAME"));// 名称
                    bp.setPvalue(new BigDecimal(rs.get("PVALUE")));
                    bp.setCostIncomeRatio(new BigDecimal(rs.get("COSTINCOMERATIO")));
                    bp.setErpProCode(rs.get("ERPPROCODE"));
                    bp.setErpSysCode(rs.get("ERPSYSCODE"));
                    bp.setProductNo(rs.get("PRODUCTNO"));// productNo
                    bp.setProductSkuCode(rs.get("PRODUCTSKUCODE"));// PRODUCTSKUCODE
                    bp.setUnitWeight(new BigDecimal(rs.get("UNITWEIGHT")));// UNITWEIGHT
                    if (null != imgInfo && imgInfo.indexOf(',') > 0) {
                        String[] imgs = imgInfo.split(",");
                        bp.setImagePath(imgs[0]);
                        bp.setImagePath7(imgs[1]);
                    }
                    splitPrice =
                            splitPrice.add(productPrice.multiply(new BigDecimal(productCount)));
                    bp.setStockCount(stockCount);
                    bp.setFinalPrice(productPrice);
                    bp.setPrice(productPrice);
                    bp.setAmount(productCount * amount);
                    bp.setSupplierType(supplierType);
                    bp.setSupplierName(shopName);
                    bp.setPvalue(
                            productPrice.divide(suitPrice, BigDecimal.ROUND_UP).multiply(pvValue));
                    bp.setCostIncomeMoney(productPrice.divide(suitPrice).multiply(costIncomeMoney));
                    bp.setCostIncomeRatio(new BigDecimal(rs.get("COSTINCOMERATIO")));
                    bp.setErpProCode(rs.get("ERPPROCODE"));
                    bp.setErpSysCode(rs.get("ERPSYSCODE"));
                    bp.setJxcCode(rs.get("JXCCODE"));
                    ccp.setPvalue(pvValue);// 产品PV
                    ccp.setCostIncomeRatio(new BigDecimal(rs.get("COSTINCOMERATIO")));// 获利百分比
                    ccp.setCostIncomeMoney(costIncomeMoney);// 获利金额
                    productList.add(bp);
                }
                ccp.setProductsPriceSum(splitPrice);
                ccp.setAmount(amount);
                if (BigDecimal.ZERO.compareTo(splitPrice) != 0) {
                    for (BaseProduct bp : productList) {
                        bp.setPvalue(bp.getFinalPrice().setScale(4)
                                .divide(splitPrice, BigDecimal.ROUND_HALF_DOWN)
                                .multiply(ccp.getPvalue()));
                        bp.setCostIncomeMoney(bp.getFinalPrice().setScale(4)
                                .divide(splitPrice, BigDecimal.ROUND_HALF_DOWN)
                                .multiply(ccp.getCostIncomeMoney()));
                    }
                }
                ccp.setIsChecked(true);
                ccp.setProductList(productList);
            }
            return ccp;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 批量查询套餐
     * 
     * @param suitIds套餐ID ，数量，数量可为空
     * @return
     * @throws ServiceException
     */
    @Override
    public List<Composition> queryBacthComposition(Map<Long, Integer> suitIds)
            throws ServiceException {
        try {
            List<Map<String, String>> rsList = baseProductDao.queryBatchAbleComposition(suitIds);
            List<Composition> rsMap = null;
            if (null != rsList && !rsList.isEmpty()) {
                rsMap = new ArrayList<Composition>();
                List<BaseProduct> productList = null;
                Composition ccp = null;
                Long suitId = null;
                BigDecimal suitPrice = BigDecimal.ZERO, splitPrice = BigDecimal.ZERO,
                        pvValue = BigDecimal.ZERO, costIncomeMoney = BigDecimal.ZERO;// 套餐价格、分开价格,PV,或以金额
                String suitName = null;// 套餐名称
                Integer status = null, minStock = null, amount = 1;// 套餐状态，最小库存、购买数量
                for (Map<String, String> rs : rsList) {
                    Long tempId = Long.parseLong(rs.get("SUITID"));
                    if (null == suitId || 0 != tempId.compareTo(suitId)) {
                        if (null != suitId) {
                            ccp.setProductsPriceSum(splitPrice);
                            if (BigDecimal.ZERO.compareTo(splitPrice) != 0) {
                                for (BaseProduct bp : productList) {
                                    bp.setPvalue(bp.getFinalPrice().setScale(4)
                                            .divide(splitPrice, BigDecimal.ROUND_HALF_DOWN)
                                            .multiply(ccp.getPvalue()));
                                    bp.setCostIncomeMoney(bp.getFinalPrice().setScale(4)
                                            .divide(splitPrice, BigDecimal.ROUND_HALF_DOWN)
                                            .multiply(ccp.getCostIncomeMoney()));
                                }
                            }
                            ccp.setProductList(productList);
                            rsMap.add(ccp);
                        }
                        // 初始化新套餐变化
                        suitName = null;
                        suitPrice = BigDecimal.ZERO;
                        splitPrice = BigDecimal.ZERO;
                        pvValue = new BigDecimal(rs.get("PVALUE"));
                        costIncomeMoney = new BigDecimal(rs.get("COSTINCOMERATIO"));
                        status = null;
                        minStock = null;
                        suitId = tempId;
                        amount = suitIds.get(suitId);
                        if (null == amount || 0 == amount) {
                            amount = 1;
                        }
                        productList = new ArrayList<BaseProduct>();
                        ccp = new Composition();
                        ccp.setCid(suitId);
                        ccp.setIsOutOfStock(false);
                    }

                    // 数量
                    Integer productCount = 0;
                    if (null != rs.get("PRODUCTCOUNT")) {
                        productCount = Integer.parseInt(rs.get("PRODUCTCOUNT"));
                    }
                    // 库存数量
                    Integer stockCount = 0;
                    if (null != rs.get("STOCKCOUNT")) {
                        stockCount = Integer.parseInt(rs.get("STOCKCOUNT"));
                    }

                    if (null == minStock
                            || (productCount != 0 && (minStock > (stockCount / productCount)))) {
                        minStock = stockCount / productCount;
                        ccp.setStockCount(minStock);
                    }

                    if (null == status) {
                        status = Integer.parseInt(rs.get("STATUS"));// 套餐状态
                        if (3 != status) {
                            ccp.setIsOutOfStock(true);
                        }
                    }
                    if (null == suitName) {
                        suitName = rs.get("SUITNAME");// 套餐名称
                        ccp.setName(suitName);
                    }
                    if (0 == BigDecimal.ZERO.compareTo(suitPrice)) {
                        suitPrice = new BigDecimal(rs.get("SUITPRICE")).setScale(2);// 套餐价格
                        ccp.setPrice(suitPrice);
                        ccp.setFinalPrice(suitPrice);
                    }
                    BigDecimal productPrice = new BigDecimal(rs.get("PRODUCTPRICE"));// 产品价格
                    String[] shopInfo = rs.get("SHOPINFO").split(",");// 商家信息格式类型,名称
                    if (null == shopInfo || 2 != shopInfo.length || null == stockCount
                            || stockCount < 1) {
                        ccp.setStockCount(0);// 产品无库存
                        logger.info("套餐" + suitId + "产品信息错误或无库存！");
                    }
                    String imgInfo = rs.get("IMGINFO");// 图片信息
                    Integer supplierType = 1;
                    if (!StringUtil.isEmpty(shopInfo[0])) {
                        supplierType = Integer.parseInt(shopInfo[0]);
                    }
                    String shopName = null;
                    if (!StringUtil.isEmpty(shopInfo[1])) {
                        shopName = shopInfo[1];
                    }
                    BaseProduct bp = new BaseProduct();
                    bp.setSkuAttrValue(rs.get("CATEGORYATTRVALUE"));
                    bp.setBrandName(rs.get("BRANDNAME"));// 品牌
                    bp.setBatchNo(rs.get("BATCHNO"));// 批次号
                    bp.setCredits(Integer.parseInt(rs.get("CREDITS")));// 批次号
                    bp.setSellerId(Long.parseLong(rs.get("SELLERID")));// 商家ID
                    bp.setSupplierCode(rs.get("SELLERID"));// 商家ID
                    bp.setProductSkuId(Long.parseLong(rs.get("SKUID")));// skuId
                    bp.setProductID(Long.parseLong(rs.get("PRODUCTID")));// productId
                    bp.setBrandId(Long.parseLong(rs.get("BRANDID")));// brandId
                    bp.setCategoryId(Long.parseLong(rs.get("CATEGORYID")));// categoryId
                    bp.setTitle(rs.get("PRODUCTTITLE"));// 标题
                    bp.setName(rs.get("PRODUCTNAME"));// 名称
                    bp.setProductNo(rs.get("PRODUCTNO"));// productNo
                    bp.setProductSkuCode(rs.get("PRODUCTSKUCODE"));// PRODUCTSKUCODE
                    bp.setUnitWeight(new BigDecimal(rs.get("UNITWEIGHT")));// UNITWEIGHT
                    if (null != imgInfo && imgInfo.indexOf(',') > 0) {
                        String[] imgs = imgInfo.split(",");
                        bp.setImagePath(imgs[0]);
                        bp.setImagePath7(imgs[1]);
                    }
                    splitPrice =
                            splitPrice.add(productPrice.multiply(new BigDecimal(productCount)));
                    bp.setStockCount(stockCount);
                    bp.setFinalPrice(productPrice);
                    bp.setPrice(productPrice);
                    bp.setAmount(productCount * amount);
                    bp.setSupplierType(supplierType);
                    bp.setSupplierName(shopName);
                    bp.setPvalue(
                            productPrice.divide(suitPrice, BigDecimal.ROUND_UP).multiply(pvValue));
                    bp.setCostIncomeMoney(productPrice.divide(suitPrice, BigDecimal.ROUND_UP)
                            .multiply(costIncomeMoney));
                    bp.setCostIncomeRatio(new BigDecimal(rs.get("COSTINCOMERATIO")));
                    bp.setErpProCode(rs.get("ERPPROCODE"));
                    bp.setErpSysCode(rs.get("ERPSYSCODE"));
                    bp.setJxcCode(rs.get("JXCCODE"));
                    productList.add(bp);
                    ccp.setAmount(amount);
                    ccp.setPvalue(pvValue);// 产品PV
                    ccp.setCostIncomeRatio(new BigDecimal(rs.get("COSTINCOMERATIO")));// 获利百分比
                    ccp.setCostIncomeMoney(costIncomeMoney);// 获利金额
                }
                ccp.setProductsPriceSum(splitPrice);
                if (BigDecimal.ZERO.compareTo(splitPrice) != 0) {
                    for (BaseProduct bp : productList) {
                        bp.setPvalue(bp.getFinalPrice().setScale(4)
                                .divide(splitPrice, BigDecimal.ROUND_HALF_DOWN)
                                .multiply(ccp.getPvalue()));
                        bp.setCostIncomeMoney(bp.getFinalPrice().setScale(4)
                                .divide(splitPrice, BigDecimal.ROUND_HALF_DOWN)
                                .multiply(ccp.getCostIncomeMoney()));
                    }
                }
                ccp.setProductList(productList);
                rsMap.add(ccp);
            }
            return rsMap;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
