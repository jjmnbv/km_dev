package com.pltfm.app.dao;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionInfo;
import com.pltfm.app.vobject.DistributionInfoExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 配送单DAO层接口
 *
 * @author luoyi
 * @since 2013/08/20
 */
public interface DistributionInfoDAO {

    /**
     * 根据查询条件，返回查询到的配送单总记录数
     *
     * @param example :查询条件
     * @return 记录数
     * @throws SQLException
     */
    int countByExample(DistributionInfoExample example) throws SQLException;

    /**
     * 根据查询条件，返回删除的总记录数
     *
     * @param example
     * @return
     * @throws SQLException
     */
    int deleteByExample(DistributionInfoExample example) throws SQLException;

    /**
     * 根据配送单ID，返回删除的记录数
     *
     * @param distributionId (配送单ID)
     * @return
     * @throws SQLException
     */
    int deleteByPrimaryKey(Long distributionId) throws SQLException;

    /**
     * 插入整个配送单实体
     *
     * @param record
     * @throws SQLException
     */
    void insert(DistributionInfo record) throws SQLException;

    /**
     * 插入配送单不为null字段的实体
     *
     * @param record
     * @throws SQLException
     */
    void insertSelective(DistributionInfo record) throws SQLException;

    /**
     * 分页查询配送单，返回配送单集合
     *
     * @param example 查询条件
     * @param skip        下一页
     * @param max 最大记录数
     * @return 配送单集合
     * @throws SQLException
     */
    List<DistributionInfo> selectByExample(DistributionInfoExample example, int skip, int max) throws SQLException;

    /**
     * 查询符合条件配送单集合(不分页)
     *
     * @param example
     * @return
     * @throws SQLException
     */
    List<DistributionInfo> selectByExample(DistributionInfoExample example) throws SQLException;

    /**
     * 根据distributionId查询配送单
     *
     * @param distributionId
     * @return
     * @throws SQLException
     */
    DistributionInfo selectByPrimaryKey(Long distributionId) throws SQLException;

    /**
     * 更新配送单为null字段(部分更新)
     *
     * @param record
     * @param example
     * @return
     * @throws SQLException
     */
    int updateByExampleSelective(DistributionInfo record, DistributionInfoExample example) throws SQLException;

    /**
     * 更新整个配送单
     *
     * @param record
     * @param example
     * @return
     * @throws SQLException
     */
    int updateByExample(DistributionInfo record, DistributionInfoExample example) throws SQLException;

    /**
     * 根据distributionId更新非null字段配送单
     *
     * @param record
     * @return
     * @throws SQLException
     */
    int updateByPrimaryKeySelective(DistributionInfo record) throws SQLException;

    /**
     * 根据distributionId更新所有字段配送单
     *
     * @param record
     * @return
     * @throws SQLException
     */
    int updateByPrimaryKey(DistributionInfo record) throws SQLException;

    /**
     * 批量删除配送单
     *
     * @param statementName
     * @param parameterList
     * @return
     * @throws SQLException
     */
    int batchDeleteForDistribution(String statementName, List<DistributionInfo> parameterList) throws SQLException;

    /**
     * 批量审核配送单
     *
     * @param statementName
     * @param parameterList
     * @return
     * @throws SQLException
     */
    int batchUpdateForDistributionInfo(String statementName, List<DistributionInfo> parameterList) throws SQLException;

    /**
     * 插入配送单 部分插入(允许为null值)
     *
     * @param record
     * @throws ServiceException
     */
    Long insertDistributionSelective(DistributionInfo record) throws SQLException;

    /**
     * 批量保存配送单(远程接口用)
     */
    void batchInsertDistribution(String statementName, List<DistributionDetailInfo> parameterList) throws SQLException;
}