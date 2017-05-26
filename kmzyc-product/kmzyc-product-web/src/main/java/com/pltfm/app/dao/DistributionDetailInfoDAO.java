package com.pltfm.app.dao;

import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionDetailInfoExample;
import com.pltfm.app.vobject.DistributionInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * 配送明细单DAO层接口
 *
 * @author luoyi
 * @since 2013/08/20
 */
public interface DistributionDetailInfoDAO {

    /**
     * 根据查询条件，返回查询到的配送明细单总记录数
     *
     * @param example :查询条件
     * @return 记录数
     * @throws SQLException
     */
    int countByExample(DistributionDetailInfoExample example) throws SQLException;

    /**
     * 根据查询条件，返回删除的总记录数
     *
     * @param example
     * @return
     * @throws SQLException
     */
    int deleteByExample(DistributionDetailInfoExample example) throws SQLException;

    /**
     * 根据配送明细单ID，返回删除的记录数
     *
     * @param distributionId (配送明细单ID)
     * @return
     * @throws SQLException
     */
    int deleteByPrimaryKey(Long distributionId) throws SQLException;

    /**
     * 插入整个配送明细单实体
     *
     * @param record
     * @throws SQLException
     */
    void insert(DistributionDetailInfo record) throws SQLException;

    /**
     * 插入配送明细单不为null字段的实体
     *
     * @param record
     * @throws SQLException
     */
    void insertSelective(DistributionDetailInfo record) throws SQLException;

    /**
     * 分页查询配送明细单，返回配送明细单集合
     *
     * @param example   查询条件
     * @param skip      下一页
     * @param max       最大记录数
     * @return 配送明细单集合
     * @throws SQLException
     */
    List<DistributionDetailInfo> selectByExample(DistributionDetailInfoExample example, int skip, int max) throws SQLException;

    /**
     * 查询符合条件配送明细单集合(不分页)
     *
     * @param example
     * @return
     * @throws SQLException
     */
    List<DistributionDetailInfo> selectByExample(DistributionDetailInfoExample example) throws SQLException;

    /**
     * 根据detailId查询配送明细单
     *
     * @param distributionId
     * @return
     * @throws SQLException
     */
    DistributionDetailInfo selectByPrimaryKey(Long distributionId) throws SQLException;

    /**
     * 更新配送明细单为null字段(部分更新)
     *
     * @param record
     * @param example
     * @return
     * @throws SQLException
     */
    int updateByExampleSelective(DistributionDetailInfo record, DistributionDetailInfoExample example) throws SQLException;

    /**
     * 更新整个配送明细单
     *
     * @param record
     * @param example
     * @return
     * @throws SQLException
     */
    int updateByExample(DistributionDetailInfo record, DistributionDetailInfoExample example) throws SQLException;

    /**
     * 根据detailId更新非null字段配送明细单
     *
     * @param record
     * @return
     * @throws SQLException
     */
    int updateByPrimaryKeySelective(DistributionDetailInfo record) throws SQLException;

    /**
     * 根据detailId更新所有字段配送明细单
     *
     * @param record
     * @return
     * @throws SQLException
     */
    int updateByPrimaryKey(DistributionDetailInfo record) throws SQLException;

    /**
     * 批量保存配送明细单
     *
     * @param parameterList
     * @return
     */
    int batchSaveForDistributionDetail(List<DistributionDetailInfo> parameterList) throws SQLException;

    /**
     * 批量删除配送明细单
     *
     * @param parameterList
     * @return
     */
    int batchDeleteDistributionDetail(List<DistributionDetailInfo> parameterList) throws SQLException;

    /**
     * 批量修改配送明细单
     *
     * @param parameterList
     * @return
     */
    int batchUpdateDistributionDetail(List<DistributionDetailInfo> parameterList) throws SQLException;

    /**
     * 批量删除配送单
     *
     * @param statementName
     * @param parameterList
     * @return
     * @throws SQLException
     */
    int batchDeleteDistributionInfo(String statementName, List<DistributionInfo> parameterList) throws SQLException;
}