package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.OrderItemDao;
import com.kmzyc.b2b.dao.ProductStockDAO;
import com.kmzyc.b2b.model.ProductStockInterface;
import com.kmzyc.b2b.service.ProductStockService;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.b2b.vo.CompositionCarProduct;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.util.PromotionCacheUtil;
import com.kmzyc.util.StringUtil;

@Service
public class ProductStockServiceImpl implements ProductStockService {
    private static Logger logger= LoggerFactory.getLogger(ProductStockService.class);
    @Resource(name = "productStockDAOImpl")
    private ProductStockDAO productStockDao;
    @Resource
    private PromotionCacheUtil promotionCacheUtil;
    @Resource(name = "orderItemDaoImpl")
    private OrderItemDao orderItemDao;

    @Override
    public Double selectBySkuId(String skuId) {

        try {
            Double stockAmount = productStockDao.selectBySkuId(skuId);
            if (null == stockAmount) {
                return 0d;
            }
            return stockAmount;
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
            return 0d;
        }

    }

    @Override
    public Double selectByLongSkuId(Long skuId) {

        try {
            Double stockAmount = productStockDao.selectByLongSkuId(skuId);
            if (null == stockAmount) {
                return 0d;
            }
            return stockAmount;
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
            return 0d;
        }

    }

    /**
     * 根据SKUID查询库存
     * 
     * @param skuId
     * @return
     * @throws SQLException
     */
    @Override
    public ProductStockInterface queryProductStockById(Long skuId) throws ServiceException {
        try {

            return productStockDao.queryProductStockById(skuId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int queryRealStockBySkuId(Long skuId) throws SQLException {

        return (Integer) productStockDao.findById("ProductStock.queryRealStockBySkuId", skuId);
    }

    @Override
    public long queryWarehouseOfMaxproductAmount(String skuId) throws ServiceException {

        try {
            return productStockDao.queryWarehouseOfMaxproductAmount(skuId);
        } catch (SQLException e) {
            throw new ServiceException(0, "获取库存最大仓库ID失败", e);
        }
    }

    @Override
    public List<String> checkProductStockNumISOKInOrder(String orderCode) throws ServiceException {

        try {
            return productStockDao.checkProductStockNumISOKInOrder(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(0, "验证订单中商品库存是否足够失败", e);
        }
    }

    @Override
    public List<String> queryOrderProductIsOutOfShelf(String orderCode) throws ServiceException {

        try {
            return productStockDao.queryOrderProductIsOutOfShelf(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(0, "验证订单中商品是否下架失败", e);
        }
    }

    /**
     * 查询套餐信息
     * 
     * @param suitIds
     * @return
     * @throws ServiceException
     */
    @Override
    public List<CompositionCarProduct> querySuitInfo(String suitIds) throws ServiceException {

        try {
            if (StringUtil.isEmpty(suitIds)) {
                return null;
            }
            List<CompositionCarProduct> ccpList = null;
            List<Map<String, String>> list;
            if (!suitIds.contains(",")) {
                list = productStockDao.querySuitStockInfo(Long.parseLong(suitIds));
            } else {
                List<Long> ids = new ArrayList<>();
                for (String id : suitIds.split(",")) {
                    if (StringUtil.isEmpty(id)) {
                        continue;
                    }
                    ids.add(Long.parseLong(id));
                }
                list = productStockDao.querySuitStockInfoForBatch(ids);
            }
            if (null != list && !list.isEmpty()) {
                ccpList = new ArrayList<>();
                Long SuitId = null;
                CompositionCarProduct ccp = null;
                List<CarProduct> cpList = null;
                Integer minStock = null;
                for (Map<String, String> map : list) {
                    Long tempId = Long.parseLong(map.get("SUITID"));
                    if (null == SuitId || 0 != SuitId.compareTo(tempId)) {
                        if (null != SuitId) {
                            ccp.setProductList(cpList);
                            ccpList.add(ccp);
                        }
                        minStock = null;
                        SuitId = tempId;
                        ccp = new CompositionCarProduct();
                        cpList = new ArrayList<>();
                    }
                    ccp.setId(tempId);
                    CarProduct cp = new CarProduct();
                    int productCount = Integer.parseInt(map.get("PRODUCTCOUNT"));
                    int stockCount = Integer.parseInt(map.get("STOCKCOUNT"));
                    if (null == minStock || minStock > (stockCount / productCount)) {
                        minStock = stockCount / productCount;
                        ccp.setStockCount(minStock);
                    }
                    cp.setProductSkuId(Long.parseLong(map.get("SKUID")));
                    cp.setStockCount(stockCount);
                    cpList.add(cp);
                }
                ccp.setProductList(cpList);
                ccpList.add(ccp);
            }
            return ccpList;
        } catch (SQLException e) {
            throw new ServiceException(0, "查询套餐信息发生异常", e);
        }
    }

    /**
     * 批量查询库存
     * 
     * @param skuIds
     * @return
     * @throws ServiceException
     */
    @Override
    public List<ProductStockInterface> queryBatchStock(List<Long> skuIds) throws ServiceException {
        try {

            return productStockDao.queryBatchStock(skuIds);
        } catch (Exception e) {
            throw new ServiceException(0, "批量查询库存发生异常", e);
        }
    }

    /**
     * 查询库存数量最大的仓库ID
     * 
     * @param skuIds
     * @return
     * @throws ServiceException
     */
    @Override
    public Map<Long, BigDecimal> queryWarehouseId(List<Long> skuIds) throws ServiceException {
        try {

            return productStockDao.queryWarehouseId(skuIds);
        } catch (Exception e) {
            throw new ServiceException(0, "批量查询库存发生异常", e);
        }
    }

    /**
     * 更新订单活动库存
     * 
     * @param userId
     * @param orderCode
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean updateProductOrderQuantityCache(Long userId, boolean isReduce, String orderCode)
            throws ServiceException {
        try {

            Map<Long, String> ppMap = orderItemDao.queryOrderProductPromotion(orderCode);
            if (null != ppMap && !ppMap.isEmpty()) {
                String[] dvalue;
                for (String opid : ppMap.values()) {
                    dvalue = opid.split(",");
                    promotionCacheUtil.updateProductOrderQuantityCache(
                            dvalue[0],
                            dvalue[1],
                            isReduce ? (0 - Integer.parseInt(dvalue[2])) : Integer
                                    .parseInt(dvalue[2]));
                }
            }
            return true;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 查询sku库存
     * 
     * @param skuIds
     * @return
     * @throws ServiceException
     */
    @Override
    public Map<Long, Integer> querySkuStock(List<Long> skuIds) throws ServiceException {
        try {

            return productStockDao.querySkuStock(skuIds);
        } catch (Exception e) {
            throw new ServiceException(0, "查询sku库存发生异常", e);
        }
    }
}
