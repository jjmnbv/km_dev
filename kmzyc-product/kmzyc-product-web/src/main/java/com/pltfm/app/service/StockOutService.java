package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.StockOut;
import com.pltfm.app.vobject.StockOutAftersaleReturn;
import com.pltfm.app.vobject.StockOutAndDetail;
import com.pltfm.app.vobject.ViewProductSku;
import com.kmzyc.commons.page.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 接口：出库单Service
 * 
 * @author luoyi
 * @createDate 2013/08/15
 */
public interface StockOutService {

	/**
	 * 分页查找所有的出库单列表
	 * 
	 * @param page
	 *            ：查询：创建结束日期
	 */
	List<StockOut> findStockOutList(Page page, StockOut stockOut, Date endCreateDate) throws ServiceException;

	/**
	 * 根据出库单ID，查找出库单信息
	 * @param stockOutId
	 * @throws ServiceException
	 */
	StockOut findStockOut(Long stockOutId) throws ServiceException;
	
	/**
	 * 根据出库单号，查找出库单信息
	 * @param stockOutNO
	 * @return StockOut
	 * @throws ServiceException
	 */
	StockOut findStockOutByNo(String stockOutNO) throws ServiceException;
	
	/**
	 * 订单出库审核
	 * @param stockOutList
	 * @return
	 * @throws ServiceException
	 */
	ResultMessage auditStockOutForOrder(List<StockOut> stockOutList) throws ServiceException ;
	
	/**
	 * 换货出库审核
	 * @param stockOutList
	 * @return
	 * @throws ServiceException
	 */
	ResultMessage auditStockOutForExchange(List<StockOut> stockOutList) throws ServiceException ;
	
	/**
	 * 其它出库审核
	 * @param stockOutList
	 * @return
	 * @throws ServiceException
	 */
	ResultMessage auditStockOutForOther(List<StockOut> stockOutList) throws ServiceException ;
	
	/**
	 * 删除出库单
	 * @param stockOutList 类似[2,5,8,]这样的字符串
	 * 要求根据传入进来的进行转换为stockOutId= [2, 5, 8]
	 */
	ResultMessage deleteStockOutList(List<StockOut> stockOutList) throws ServiceException;
	
	/**
	 * 查询出库主单与明细列表
	 * @param stockOutList
	 * @return
	 * @throws ServiceException
	 */
	List<StockOutAndDetail> selectStockOutDetailByStockOutId(List<StockOut> stockOutList) throws ServiceException;
	
	/**
	 * 根据仓库ID，出库单状态查询是否存在此类出库单
	 * @param warehouseIdList
	 * @return
	 * @throws ServiceException
	 */
	ResultMessage checkStockOutByWarehouse(List<Long> warehouseIdList) throws ServiceException;
	
	ResultMessage selectStockOutListByStockOutIds(List<Long> stockOutIdList) throws ServiceException;
	
	/**
	 * 批量修改出库单状态
	 * @param stockOutList
	 * @return
	 * @throws ServiceException
	 */
	void batchUpdateStatusForStockOut(List<StockOut> stockOutList)  throws ServiceException ;
	
	/**
	 * 换货单出库审核不通过，修改状态并释放库存
	 * @param stockOutList
	 * @throws ServiceException
	 */
	void orderAfterByExchange_notThroughAudit(List<StockOut> stockOutList,String webSite) throws ServiceException;
	
	/**
	 * 根据退换货单号,查找出库单和退换货信息
	 * @param stockOutReturnNoList
	 * @return StockOutAftersaleReturn
	 * @throws ServiceException 
	 * @throws ServiceException
	 */
	List<StockOutAftersaleReturn> selectStockOutListByReturnNo(List<StockOut> stockOutReturnNoList) throws ServiceException;
	
	/**
	 * 根据订单号获取已经生成过出库单的订单号集合
	 * @param orderNoList
	 * @return
	 * @throws ServiceException
	 */
	Map<String,String> getStockOutByOrderNo(List<String> orderNoList) throws ServiceException;

    void searchBySkuPage(Page page, ViewProductSku viewProductSku, Long warehouseId) throws ServiceException;
}
