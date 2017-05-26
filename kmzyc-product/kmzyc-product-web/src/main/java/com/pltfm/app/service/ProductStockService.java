package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.CarryStockOutDetailVO;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.ProductStockPurchase;
import com.pltfm.app.vobject.StockOutAndDetail;
import com.kmzyc.commons.page.Page;

import java.util.List;
import java.util.Map;

public interface ProductStockService {

    /**
     * 分页 查找数据
     *
     * @param page
     * @param prod
     */
    void searchPage(Page page, ProductStock stock) throws ServiceException;

    /**
     * 分页 查找数据(告警库存列表)
     *
     * @param page
     * @param prod
     */
    void searchPageForAlarm(Page page, ProductStock stock) throws ServiceException;

    /**
     * 新增库存
     *
     * @param stock
     * @return
     * @throws ServiceException
     */
    ResultMessage insertProductStock(ProductStock stock, Integer userId, String userName) throws ServiceException;

    /**
     * 根据编号查找产品
     *
     * @param id
     * @throws ServiceException
     */
    ProductStock findProductStockById(Long id) throws ServiceException;

    /**
     * 根据ID更新库存数据
     *
     * @param stock
     * @return
     * @throws ServiceException
     */
    boolean updateProductStockById(ProductStock stock, Integer userId, String userName) throws ServiceException;

    /**
     * 检查SKU是否合法
     *
     * @param skuCode
     * @param warehouseId
     * @return
     * @throws ServiceException
     */
    boolean checkProductStockBySkuCode(String skuCode, Long warehouseId) throws ServiceException;

    /**
     * 根据仓库ID、库存ID或SKU_ID增加订购总量
     *
     * @param stock
     * @throws ServiceException
     */
    void increaseOrderQuantity(ProductStock stock) throws ServiceException;

    /**
     * 根据仓库ID、库存ID或SKU_ID减少订购总量
     *
     * @param stock
     * @throws ServiceException
     */
    void decreaseOrderQuantity(ProductStock stock) throws ServiceException;

    /**
     * 批量减少库存数量
     *
     * @param stockList
     * @return
     * @throws ServiceException
     */
    ResultMessage batchIncreaseStockQuantity(List<ProductStock> stockList, Short stockOutType) throws ServiceException;

    /**
     * 订单系统远程调用查询sku库存数量
     *
     * @param stockList
     * @return
     * @throws ServiceException
     */
    List<ProductStock> remoteSelectStockQuantity(List<ProductStock> stockList) throws ServiceException;

    boolean checkStockByWarehouseList(List<ProductStock> stockList) throws ServiceException;

    /**
     * 批量锁库存
     *
     * @param stockList
     * @throws ServiceException
     */
    Map<String, Object> batchIncreaseOrderQuantity(List<ProductStock> stockList, String webSite)
            throws ServiceException;

    /**
     * 售后批量锁库存
     *
     * @param stockList
     * @throws ServiceException
     */
    Map<String, Object> batchLockOrderQuantityForAfter(List<ProductStock> stockList) throws ServiceException;

    /**
     * 批量解锁库存
     *
     * @param stockList
     * @throws ServiceException
     */
    Map<String, Object> batchDecreaseOrderQuantity(List<ProductStock> stockList, String webSite) throws ServiceException;

    /**
     * 根据skuId 查询出 库存表中 对于的数量 即stockQuanttiy- order_quality 数量
     *
     * @param skuId
     * @return
     * @throws ServiceException
     */
    Integer selectRemainQuantityBySkuId(Long skuId) throws ServiceException;

    /**
     * 根据skuId 查询出此sku的所有库存列表
     *
     * @param skuId
     * @return
     * @throws ServiceException
     */
    List<ProductStock> selectStockListBySkuId(Long skuId) throws ServiceException;

    Map<Long, ProductStock> selectProductStockListByStockList(List<ProductStock> stockList) throws ServiceException;

    /**
     * 查询是否有库存，如果有则返回仓库Id，否则返回0
     *
     * @param productSkuId
     * @return
     * @throws ServiceException
     */
    Long findProductSkuStockCountBySkuId(Long productSkuId) throws ServiceException;

    /**
     * 根据仓库ID和产品skuCode查找库存信息
     *
     * @return
     * @throws ServiceException
     */
    List<ProductStock> findProductStocksByWarehouseIdSkuCode(List<CarryStockOutDetailVO> carryStockOutDetailVOs);

    /**
     * 为供应商批量增加商品库存
     *
     * @param supplierId
     * @return
     * @throws ServiceException
     */
    ResultMessage addStockForSupplier(Long supplierId, List<ProductSku> productSkuList) throws ServiceException;

    Map<Long, Long> findStockIdMapByWarehouseAndSkuId(List<StockOutAndDetail> detailList) throws ServiceException;

    /**
     * 售后批量锁库存
     *
     * @param lockMap (Map<SKUCode,<WareHouseId,lockCount>>)
     * @return Map<String,Object>
     * @throws ServiceException
     */
    Map<String, Object> batchLockStockForAfter(Map<String, Map<Long, Long>> lockMap)  throws ServiceException;

    /**
     * 根据产品ID查询库存信息（SKU只取一条记录最大的记录）
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ProductStock> findByProductId(Long productId) throws ServiceException;

    /**
     * 根据skuCode列表获取对应的最大库存仓库id
     *
     * @param list skuCode列表
     * @return
     * @throws ServiceException
     */
    Map<String, Object> getWarehouseIdBySkuCode(List<ProductStock> list) throws ServiceException;

    /**
     * 批量锁库存 砍价项目
     *
     * @param list
     * @return
     */
    Map<String, Object> batchLockStock4KJ(List<ProductStock> list) throws ServiceException;

    /**
     * 批量解锁库存 砍价项目
     *
     * @param list
     * @return
     */
    void batchUnLockStock4KJ(List<ProductStock> list) throws ServiceException;
}
