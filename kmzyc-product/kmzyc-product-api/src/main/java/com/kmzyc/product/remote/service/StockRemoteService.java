package com.kmzyc.product.remote.service;

import java.util.List;
import java.util.Map;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.app.vobject.CarryStockOutVO;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductStock;
import com.kmzyc.commons.exception.ServiceException;

public interface StockRemoteService {

    /**
     * 根据skuID和warehouseId(仓库ID)的集合查找库存数量
     *
     * @param paramMap <String, List<Long>>：String为skuCode,List<Long> 为skuCode对应的warehouseId集合
     *
     * @return Map<String, Map<Long, Long>>：String为skuCode,Map<Long, Long>为skuCode对应的warehouseId的库存数量
     */
    Map<String, Map<Long, Long>> findStockBySkuIdAndWarehouseId(Map<String, List<Long>> paramMap)
            throws ServiceException;

    /**
     * 批量锁库存
     *
     * @param lockMap (Map<SKUCode,<WareHouseId,lockCount>>)
     * @return
     * @throws ServiceException
     */
    Map<String, Object> batchLockStock(Map<String, Map<Long, Long>> lockMap, String orderCode, String webSite)
            throws ServiceException;

    /**
     * 批量解锁库存
     *
     * @param lockMap
     * @return
     * @throws ServiceException
     */
    Map<String, Object> batchUnLockStock(Map<String, Map<Long, Long>> lockMap, String webSite)
            throws ServiceException;

    /**
     * 根据仓库ID、SKUCODE锁定库存数量lockCount
     *
     * @param warehouseId
     * @param skuCode
     * @return
     * @throws ServiceException
     */
    boolean lockStock(Long warehouseId, String skuCode, Long lockCount) throws ServiceException;

    /**
     * 根据仓库ID、SKUCODE解除锁定库存数量unlockCount
     *
     * @param warehouseId
     * @param skuCode
     * @param unlockCount
     * @return
     * @throws ServiceException
     */
    boolean unLockStock(Long warehouseId, String skuCode, Long unlockCount) throws ServiceException;

    /**
     * 订单结转出库接口
     *
     * @param carryStockOutList
     */
    boolean insertCarryStockOut(List<CarryStockOutVO> carryStockOutList) throws ServiceException;

    /**
     * 生成退货单
     *
     * @param returnOrder 退货单对象
     * @throws ServiceException
     */
    void insertReturnOrder(AftersaleReturnOrder returnOrder) throws ServiceException;

    /**
     * 删除退货单（当前端订单触发取消对货单时执行）
     *
     * @param orderCode
     * @throws ServiceException
     */
    void deleteReturnOrder(String orderCode) throws ServiceException;

    /**
     * 入驻供应商订单结转
     *
     * @param carryStockOutList
     * @return
     * @throws ServiceException
     */
    boolean carryStockOutForSupplier(List<CarryStockOutVO> carryStockOutList) throws ServiceException;

    /**
     * 为供应商增加库存
     *
     * @param supplierId
     * @param productSkuList
     * @return
     * @throws ServiceException
     */
    ResultMessage addStockForSupplier(Long supplierId, List<ProductSku> productSkuList)
            throws ServiceException;

    /**
     * 批量锁库存 砍价项目
     *
     * @param list ProductStock描述
     *             <note>
     *             skuAttValue ：skuCode
     *             lockCount   ：库存数
     *             </note>
     * @return 成功与失败：map.get("isSuccess")
     * 失败信息：map.get("errorMessage")
     * 成功信息：map.get("data")  返回类型是map<skuCode, 仓库Id>
     */
    Map<String, Object> batchLockStock4KJ(List<ProductStock> list);

    /**
     * 批量解锁库存 砍价项目
     *
     * @param list ProductStock描述
     *             <note>
     *             skuAttValue ：skuCode
     *             warehouseId ：仓库id
     *             unlockCount ：解库存数
     *             </note>
     * @return 成功与失败：map.get("isSuccess")
     * 失败信息：map.get("errorMessage")
     */
    Map<String, Object> batchUnLockStock4KJ(List<ProductStock> list);
}
