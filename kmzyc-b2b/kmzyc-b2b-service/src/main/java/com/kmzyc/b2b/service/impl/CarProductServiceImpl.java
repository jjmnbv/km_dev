package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.CarProductDao;
import com.kmzyc.b2b.dao.ViewSkuAttrDao;
import com.kmzyc.b2b.service.CarProductService;
import com.kmzyc.b2b.service.OrderMainService;
import com.kmzyc.b2b.service.PromotionInfoService;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.app.util.ListUtils;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.enums.ProductType;
import com.pltfm.app.vobject.ViewSkuAttr;

@Service("carProductService")
@SuppressWarnings("unchecked")
public class CarProductServiceImpl implements CarProductService {
    @Resource
    private CarProductDao carProductDao;
    @Resource
    private ViewSkuAttrDao viewSkuAttrDao;
    @Resource(name = "orderMainServiceImpl")
    private OrderMainService orderMainService;
    @Resource
    private PromotionInfoService promotionInfoService;
    // private static Logger logger = Logger.getLogger(CarProductServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(CarProductServiceImpl.class);

    /**
     * 只更新商品上下架库存信息
     * 
     * @param carProduct
     * @return
     */
    @Override
    public CarProduct updateCarProduct(CarProduct carProduct) throws ServiceException {
        Long productSkuId = carProduct.getProductSkuId();
        CarProduct dbCarProduct = getCarProduct(productSkuId);
        carProduct.setStockCount(dbCarProduct.getStockCount());
        carProduct.setName(dbCarProduct.getName());
        carProduct.setIsOutOfStock(dbCarProduct.getIsOutOfStock());
        return carProduct;
    }

    /**
     * 设置产品的sku名称
     * 
     * @param carProduct
     * @return
     */
    @Override
    public CarProduct setCarProductSkuAttrValue(CarProduct carProduct) {

        List<ViewSkuAttr> list = viewSkuAttrDao.findBySkuId(carProduct.getProductSkuId());
        if (ListUtils.isNotEmpty(list)) {
            StringBuilder name = new StringBuilder();
            for (ViewSkuAttr vs : list) {
                name.append(vs.getCategoryAttrValue()).append(" ");
            }
            carProduct.setSkuAttrValue(name.toString());
        }
        return carProduct;
    }

    /**
     * 查询商品
     * 
     * @param productSkuId
     * @return
     * @throws ServiceException
     */
    @Override
    public CarProduct getCarProduct(Long productSkuId) throws ServiceException {

        CarProduct carProduct = carProductDao.findBySkuId(productSkuId);
        if (carProduct == null) {
            throw new ServiceException(Constants.DATAEXCEPTION, "编号为" + productSkuId + "的商品数据异常");
        }
        setCarProductSkuAttrValue(carProduct);
        return carProduct;
    }

 /*   *//**
     * 新建可用商品，须检查上下架、库存
     * 
     * @param skuId
     * @param amount
     * @return
     * @throws ServiceException -1002数据异常、-1001库存不足、-1000已下架、 -1007 不能购买
     *//*
    public CarProduct createAbleCarProduct(Long skuId, Integer amount, String loginType)
            throws ServiceException {

        Map<String, String> rs = null;
        try {
            rs = carProductDao.queryAbleCarProduct(skuId, amount, ShopCartUtil.SHOP_CAR_CHANNEL);
        } catch (Exception e) {
            throw new ServiceException(Constants.DATAEXCEPTION, "查询可用商品发生异常", e);
        }
        if (null == rs || rs.isEmpty()) {
            throw new ServiceException(Constants.DATAEXCEPTION, "不存在商品" + skuId, null);
        }
        Long outstock = Long.parseLong(rs.get("OUTSTOCK"));// 超出库存
        Integer supplierType = Integer.parseInt(rs.get("SUPPLIERTYPE"));// 商家类型
        if (1 == outstock.intValue()) {
            throw new ServiceException(Constants.UNDERSTOCK, "商品库存不足", null);
        }
        if (4 == supplierType && !Constants.LOGINTYPE.equals(loginType)) {
            throw new ServiceException(Constants.UNENBUY, "只有时代会员才能购买康美中药城产品", null);
        }
        CarProduct cp = new CarProduct();
        cp.setSellerId(Long.parseLong(rs.get("SELLERID")));
        cp.setProductSkuId(skuId);
        cp.setAmount(amount);
        cp.setProductType(Integer.parseInt(rs.get("PRODUCTTYPE")));
        cp.setSupplierType(supplierType);
        cp.setSupplierName(rs.get("SHOPNAME"));
        return cp;

    }*/

   /* *//*
     * 新建可用套餐，须检查上下架、库存
     * 
     * @param suitId
     * @param amount
     * @return
     * @throws code ：-1002数据异常、-1001库存不足、-1000已下架、 -1007 不能购买
     *//*
    @SuppressWarnings("unused")
   public CompositionCarProduct createAbleComposition(Long suitId, Integer amount, String loginType)
            throws ServiceException {

        try {
            List<Map<String, String>> rsList =
                    carProductDao.queryAbleComposition(suitId, ShopCartUtil.SHOP_CAR_CHANNEL);
            CompositionCarProduct ccp = null;
            if (null != rsList && !rsList.isEmpty()) {
                ccp = new CompositionCarProduct();
                ccp.setId(suitId);
                ccp.setIsOutOfStock(false);
                List<CarProduct> productList = new ArrayList<CarProduct>();
                BigDecimal suitPrice = BigDecimal.ZERO, splitPrice = BigDecimal.ZERO;// 套餐价格、分开价格
                String suitName = null;// 套餐名称
                Integer status = null, minStock = null;// 套餐状态，最小库存
                for (Map<String, String> rs : rsList) {
                    Long skuStatus = Long.parseLong(rs.get("SKUSTATUS"));// sku状态
                    Long productStatus = Long.parseLong(rs.get("PRODUCTSTATUS"));// 产品状态
                    Integer productCount = Integer.parseInt(rs.get("PRODUCTCOUNT"));// 数量
                    Integer stockCount = Integer.parseInt(rs.get("STOCKCOUNT"));// 库存数量
                    if (null == minStock || minStock > (stockCount / productCount)) {
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
                        suitPrice = new BigDecimal(rs.get("SUITPRICE"));// 套餐价格
                        ccp.setPrice(suitPrice);
                        ccp.setFinalPrice(suitPrice);
                    }
                    BigDecimal productPrice = new BigDecimal(rs.get("PRODUCTPRICE"));// 产品价格
                    String[] shopInfo = rs.get("SHOPINFO").split(",");// 商家信息格式类型,名称
                    if (null == shopInfo || 2 != shopInfo.length) {
                        throw new ServiceException(Constants.DATAEXCEPTION, "套餐数据错误", null);
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
                    if (4 == supplierType && !Constants.LOGINTYPE.equals(loginType)) {
                        throw new ServiceException(Constants.UNENBUY, "只有时代会员才能购买康美中药城套餐", null);
                    }
                    CarProduct cp = new CarProduct();
                    cp.setSkuAttrValue(rs.get("CATEGORYATTRVALUE"));
                    cp.setSellerId(Long.parseLong(rs.get("SELLERID")));// 商家ID
                    cp.setSupplierCode(rs.get("SELLERID"));// 商家ID
                    cp.setProductSkuId(Long.parseLong(rs.get("SKUID")));// skuId
                    cp.setProductID(Long.parseLong(rs.get("PRODUCTID")));// productId
                    cp.setBrandId(Long.parseLong(rs.get("BRANDID")));// brandId
                    cp.setCategoryId(Long.parseLong(rs.get("CATEGORYID")));// categoryId
                    cp.setChannel(ShopCartUtil.SHOP_CAR_CHANNEL);// channel
                    cp.setTitle(rs.get("PRODUCTTITLE"));// 标题
                    cp.setName(rs.get("PRODUCTNAME"));// 名称
                    cp.setProductNo(rs.get("PRODUCTNO"));// productNo
                    cp.setProductSkuCode(rs.get("PRODUCTSKUCODE"));// PRODUCTSKUCODE
                    cp.setUnitWeight(new BigDecimal(rs.get("UNITWEIGHT")));// UNITWEIGHT
                    if (null != imgInfo && imgInfo.indexOf(',') > 0) {
                        String[] imgs = imgInfo.split(",");
                        cp.setImagePath(imgs[0]);
                        cp.setImagePath7(imgs[1]);
                    }
                    splitPrice =
                            splitPrice.add(productPrice.multiply(new BigDecimal(productCount)));
                    cp.setStockCount(stockCount);
                    cp.setFinalPrice(productPrice);
                    cp.setPrice(productPrice);
                    cp.setAmount(productCount * amount);
                    cp.setSupplierType(supplierType);
                    cp.setSupplierName(shopName);
                    productList.add(cp);
                }
                ccp.setProductsPriceSum(splitPrice);
                ccp.setAmount(amount);
                ccp.setIsChecked(true);
                ccp.setProductList(productList);
            }
            return ccp;
        } catch (SQLException e) {
            throw new ServiceException(Constants.DATAEXCEPTION, "查询可用套餐发生异常", e);
        }
    }*/

    /**
     * 批量校验产品，并返回成功的商品ID
     * 
     * @param skuids
     * @param amounts
     * @return
     * @throws ServiceException
     */
    public List<Long> batchCheckProducts(List<Long> skuids, List<Integer> amounts)
            throws ServiceException {
        List<Long> rsList = null;
        Map<Long, Integer> paramsList = null;
        if (null != skuids && !skuids.isEmpty() && skuids.size() == amounts.size()) {
            paramsList = new HashMap<Long, Integer>();
            for (int i = 0, len = skuids.size(); i < len; i++) {
                paramsList.put(skuids.get(i), amounts.get(i));
            }
            try {
                rsList = carProductDao.queryAbleProductSkuForBatch(paramsList);
            } catch (SQLException e) {
                throw new ServiceException(0, "批量校验产品，并返回成功的商品ID发生异常", e);
            }
        }
        return rsList;
    }

    /**
     * 是否是常规产品
     * 
     * @return
     * @throws ServiceException
     */
    public void isNormalProduct(int type, Long id) throws ServiceException {
        boolean isNormal = true;
        try {
            if (type == Constants.PRODUCT_FLAG) {
                isNormal = ProductType.RX.getKey().intValue() != carProductDao.queryProductType(id);
            } else if (type == Constants.SUIT_FLAG) {
                Map<Long, Integer> rsMap = carProductDao.queryProductTypeByComId(id);
                if (null != rsMap && !rsMap.isEmpty()) {
                    for (Iterator<Integer> cpIt = rsMap.values().iterator(); cpIt.hasNext();) {
                        if (0 == ProductType.RX.getKey().compareTo(cpIt.next())) {
                            isNormal = false;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // throw new ServiceException(Constants.DATAEXCEPTION, "是否是常规产品发生异常", e);
            throw new ServiceException(Constants.DATAEXCEPTION, "系统繁忙,请稍等片刻再重新刷新页面.", e);
        }
        if (!isNormal) {
            throw new ServiceException(Constants.PRESCRIPTION, "存在处方药，请到我的处方药进行购买");
        }
    }

    /**
     * 根据skuid批量检查是否是常规产品
     * 
     * @return
     * @throws ServiceException
     */
    public void isNormalProduct(List<Long> skuIds) throws ServiceException {
        boolean isNormal = true;
        try {
            Map<Long, Integer> rsMap = carProductDao.queryBatchProductType(skuIds);
            if (null != rsMap && !rsMap.isEmpty()) {
                for (Iterator<Integer> cpIt = rsMap.values().iterator(); cpIt.hasNext();) {
                    if (0 == ProductType.RX.getKey().compareTo(cpIt.next())) {
                        isNormal = false;
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(Constants.DATAEXCEPTION, "根据skuid批量检查是否是常规产品发生异常", e);
        }
        if (!isNormal) {
            throw new ServiceException(Constants.PRESCRIPTION, "存在处方药，请到我的处方药进行购买");
        }
    }

    /**
     * 根据供应商ID查询商家名称
     * 
     * @param sellerId
     * @return
     * @throws SQLException
     */
    @Override
    public String queryCorporateNameBySupplierId(Long sellerId) throws ServiceException {
        try {
            return carProductDao.queryCorporateNameBySupplierId(sellerId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 批量查询产品最大库存
     * 
     * @param skuIds
     * @return
     * @throws ServiceException
     */
    @Override
    public Map<Long, Integer> queryBatchMaxStock(List<Long> skuIds) throws ServiceException {
        Map<Long, Integer> rsMap = null;
        if (null != skuIds && !skuIds.isEmpty()) {
            try {
                rsMap = carProductDao.queryBatchMaxStock(skuIds);
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
        return rsMap;
    }

    /**
     * 验证是否在库存库存范围内
     * 
     * @param oiList
     * @return
     * @throws ServiceException
     */
    public boolean validInStock(List<OrderItem> oiList) throws ServiceException {
        boolean inStock = false;
        if (null != oiList && !oiList.isEmpty()) {
            Map<Long, Integer> amountMap = new HashMap<Long, Integer>();
            for (OrderItem oi : oiList) {
                Long skuId = oi.getProductSkuId();
                int amount = oi.getCommodityNumber().intValue();
                if (amountMap.containsKey(skuId)) {
                    amount += amountMap.get(skuId);
                }
                amountMap.put(skuId, amount);
            }
            List<Long> skuIds = new ArrayList<Long>(amountMap.keySet());
            Map<Long, Integer> rsMap = queryBatchMaxStock(skuIds);
            if (null != rsMap && !rsMap.isEmpty()) {
                inStock = true;
            }
            for (Long skuId : skuIds) {
                Integer amount = rsMap.get(skuId);
                if (null == amount || amount.compareTo(amountMap.get(skuId)) < 0) {
                    inStock = false;
                }
            }
        }
        return inStock;
    }

}
