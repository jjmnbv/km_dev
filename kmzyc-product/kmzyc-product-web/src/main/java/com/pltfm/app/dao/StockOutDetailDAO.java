package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.StockOutDetail;
import com.pltfm.app.vobject.StockOutDetailExample;
import com.kmzyc.commons.exception.ServiceException;

/**
 * 出库明细单DAO层接口
 *
 * @author luoyi
 * @createDate 2013/08/15
 */
public interface StockOutDetailDAO {

    /**
     * 查询出库明细单记录数
     *
     * @param example : 查询条件
     * @return 返回受影响条数
     * @throws SQLException
     */
    int countByExample(StockOutDetailExample example) throws SQLException;

    /**
     * 根据条件删除出库明细单
     *
     * @param example :条件
     * @return
     * @throws SQLException
     */
    int deleteByExample(StockOutDetailExample example) throws SQLException;

    /**
     * 根据detailId删除出库明细单
     *
     * @param detailId
     * @return
     * @throws SQLException
     */
    int deleteByPrimaryKey(Long detailId) throws SQLException;

    /**
     * 插入出库明细单 整体插入
     *
     * @param record
     * @throws SQLException
     */
    void insert(StockOutDetail record) throws SQLException;

    /**
     * 插入出库明细单 部分插入(允许为null值)
     *
     * @param record
     * @throws SQLException
     */
    void insertSelective(StockOutDetail record) throws SQLException;

    /**
     * 查询出库明细单集合
     *
     * @param example : 查询条件
     * @return List<StockOutDetail> 返回集合
     * @throws SQLException
     */
    List<StockOutDetail> selectByExample(StockOutDetailExample example, int skip, int max) throws SQLException;

    List<StockOutDetail> selectByExample(StockOutDetailExample example)  throws SQLException;

    /**
     * 根据 detailId查询单个出库明细单
     *
     * @param detailId
     * @return StockOutDetail 返回一条明细单
     * @throws SQLException
     */
    StockOutDetail selectByPrimaryKey(Long detailId) throws SQLException;

    /**
     * 更新出库明细单 直接更新，不用detailId
     */
    int updateByExampleSelective(StockOutDetail record, StockOutDetailExample example) throws SQLException;

    /**
     * 更新出库明细单(传整个出库明细单对象)
     */
    int updateByExample(StockOutDetail record, StockOutDetailExample example) throws SQLException;

    /**
     * 更新出库明细单(允许null值)
     */
    int updateByPrimaryKeySelective(StockOutDetail record) throws SQLException;

    /**
     * 根据detailId更新出库明细单 where detailId = ?
     */
    int updateByPrimaryKey(StockOutDetail record) throws SQLException;

    /**
     * 批量保存出库单
     *
     * @return
     */
    int batchInsertStockOut(List<StockOutDetail> parameterList) throws SQLException;

    /**
     * 批量删除出库单
     *
     * @return
     */
    int batchDeleteStockOutDetail(List<StockOutDetail> parameterList) throws SQLException;

    /**
     * 批量修改出库单
     */
    int batchUpdateStockOutDetail(List<StockOutDetail> parameterList) throws SQLException;
}
