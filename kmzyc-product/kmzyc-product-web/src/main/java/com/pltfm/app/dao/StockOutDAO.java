package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.StockOut;
import com.pltfm.app.vobject.StockOutAftersaleReturn;
import com.pltfm.app.vobject.StockOutAndDetail;
import com.pltfm.app.vobject.StockOutDetail;
import com.pltfm.app.vobject.StockOutExample;
import com.pltfm.app.vobject.ViewProductSku;
import com.kmzyc.commons.exception.ServiceException;

/**
 * 出库单DAO层接口
 * 
 * @author luoyi
 * @createDate 2013/08/15
 * 
 */
public interface StockOutDAO {

	/**
	 * 查询出库单记录数
	 * 
	 * @param example
	 *            : 查询条件
	 * @return 返回受影响条数
	 * @throws SQLException
	 */
	int countByExample(StockOutExample example) throws SQLException;

	/**
	 * 根据条件删除出库单
	 * 
	 * @param example
	 *            :条件
	 * @return
	 * @throws SQLException
	 */
	int deleteByExample(StockOutExample example) throws SQLException;

	/**
	 * 根据stockOutId删除出库单
	 * 
	 * @param stockOutId
	 * @return
	 * @throws SQLException
	 */
	int deleteByPrimaryKey(Long stockOutId) throws SQLException;

	/**
	 * 插入出库单 整体插入
	 * 
	 * @param record
	 * @throws SQLException
	 */
	void insert(StockOut record) throws SQLException;

	/**
	 * 插入出库单 部分插入(允许为null值)
	 * 
	 * @param record
	 * @throws SQLException
	 */
	void insertSelective(StockOut record) throws SQLException;

	/**
	 * 查询出库单集合
	 * 
	 * @param example
	 *            : 查询条件
	 * @return List<StockOutDetail> 返回集合
	 * @throws SQLException
	 */
	List<StockOut> selectByExample(StockOutExample example, int skip, int max) throws SQLException;

	/**
	 * 查询出库单集合(重构的方法)
	 * 
	 * @param example
	 * @return
	 * @throws SQLException
	 */
	List<StockOut> selectByExample(StockOutExample example) throws SQLException;

	/**
	 * 根据 stockOutId查询单个出库单
	 * 
	 * @param stockOutId
	 * @return StockOutDetail 返回一条单
	 * @throws SQLException
	 */
	StockOut selectByPrimaryKey(Long stockOutId) throws SQLException;

	/**
	 * 更新出库单 直接更新，不用stockOutId
	 * 
	 * @return 返回受影响数
	 */
	int updateByExampleSelective(StockOut record, StockOutExample example) throws SQLException;

	/**
	 * 更新出库单(传符合条件的出库单对象)
	 * 
	 * @param example
	 *            :条件, record 对象
	 * @return 返回受影响数
	 */
	int updateByExample(StockOut record, StockOutExample example) throws SQLException;

	/**
	 * 更新出库单(允许null值)
	 * 
	 * @return 返回受影响数
	 */
	int updateByPrimaryKeySelective(StockOut record) throws SQLException;

	/**
	 * 根据stockOutId更新出库单 where stockOutId = ?
	 * 
	 * @return 返回受影响数
	 */
	int updateByPrimaryKey(StockOut record) throws SQLException;

	/**
	 * 批量更新出库单
	 * 
	 * @param statementName
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 */
	int batchUpdateForStockOut(String statementName, List<StockOut> parameterList) throws SQLException;
	
	/**
	 * 批量删除出库单
	 * 
	 * @param statementName
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 */
	int batchDeleteForStockOut(String statementName, List<StockOut> parameterList) throws SQLException;
	
	/**
	 * 查询出库主单与明细列表
	 * @param stockOutList
	 * @return
	 * @throws SQLException
	 */
	List<StockOutAndDetail> selectStockOutDetailByStockOutId(List<StockOut> stockOutList) throws SQLException;
	
	/**
	 * 根据仓库ID，出库单状态查询是否存在此类出库单
	 * @param warehouseIdList
	 * @return
	 * @throws Exception
	 */
	Long checkStockOutByWarehouse(List<Long> warehouseIdList) throws SQLException;

    /**
     *
     * @param stockOutIdList
     * @return
     * @throws SQLException
     */
	List<StockOut> selectStockOutListByStockOutIds(List<Long> stockOutIdList) throws SQLException;
	
    /**
	 * 批量保存出库单(远程接口用)
	 */
	void batchInsertStockOut(String statementName, List<StockOutDetail> parameterList) throws ServiceException ;
	/**
	 * 插入出库单 部分插入(允许为null值)
	 * 
	 * @param record
	 * @throws SQLException
	 */
    Long insertStockOutSelective(StockOut record) throws SQLException ;
	
	/**
	 * 根据出库单集合中的退货单号,查找出库单和退换货组合信息
	 * @param stockOutReturnNos
	 * @return StockOutAftersaleReturn
	 * @throws SQLException 
	 */
	List<StockOutAftersaleReturn> selectStockOutAfterReturnListByReturnNo(List<StockOut> stockOutReturnNos) throws SQLException;
	
	/**
	 * 根据订单号获取已经生成过出库单的订单号集合
	 * @return
	 * @throws SQLException
	 */
	Map<String,String> getStockOutByOrderNo(List<String> orderNoList) throws SQLException;

    /**
     * 查找ViewProductSku集合总数
     *
     * @param viewProductSku
     * @param warehouseId
     * @return
     */
    int countViewProductSkuByExample(ViewProductSku viewProductSku, Long warehouseId) throws SQLException;

    /**
     * 查找ViewProductSku集合信息
     *
     * @param viewProductSku
     * @param skip
     * @param max
     * @param warehouseId
     * @return
     */
    List viewProductSkuByObjectList(ViewProductSku viewProductSku, int skip, int max, Long warehouseId) throws SQLException;
}
