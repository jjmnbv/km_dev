package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.DistributionInfoDAO;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionInfo;
import com.pltfm.app.vobject.DistributionInfoExample;

/**
 * 配送单DAO层实现类
 *
 * @author luoyi
 * @since 2013/08/20
 */
@Repository("distributionInfoDao")
public class DistributionInfoDAOImpl extends BaseDao implements DistributionInfoDAO {

    public int countByExample(DistributionInfoExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("DISTRIBUTION_INFO.countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(DistributionInfoExample example) throws SQLException {
        int rows = sqlMapClient.delete("DISTRIBUTION_INFO.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long distributionId) throws SQLException {
        DistributionInfo _key = new DistributionInfo();
        _key.setDistributionId(distributionId);
        int rows = sqlMapClient.delete("DISTRIBUTION_INFO.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(DistributionInfo record) throws SQLException {
        sqlMapClient.insert("DISTRIBUTION_INFO.insert", record);
    }

    public void insertSelective(DistributionInfo record) throws SQLException {
        sqlMapClient.insert("DISTRIBUTION_INFO.insertSelective", record);
    }

    public List<DistributionInfo> selectByExample(DistributionInfoExample example, int skip, int max) throws SQLException {
        List<DistributionInfo> list = sqlMapClient.queryForList(
                "DISTRIBUTION_INFO.selectByExample", example, skip, max);
        return list;
    }

    public List<DistributionInfo> selectByExample(DistributionInfoExample example) throws SQLException {
        List<DistributionInfo> list = sqlMapClient.queryForList("DISTRIBUTION_INFO.selectByExample", example);
        return list;
    }

    public DistributionInfo selectByPrimaryKey(Long distributionId) throws SQLException {
        DistributionInfo _key = new DistributionInfo();
        _key.setDistributionId(distributionId);
        DistributionInfo record = (DistributionInfo) sqlMapClient.queryForObject("DISTRIBUTION_INFO.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByExampleSelective(DistributionInfo record, DistributionInfoExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("DISTRIBUTION_INFO.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(DistributionInfo record, DistributionInfoExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("DISTRIBUTION_INFO.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(DistributionInfo record) throws SQLException {
        int rows = sqlMapClient.update("DISTRIBUTION_INFO.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(DistributionInfo record) throws SQLException {
        int rows = sqlMapClient.update("DISTRIBUTION_INFO.updateByPrimaryKey", record);
        return rows;
    }

    public int batchDeleteForDistribution(String statementName, List<DistributionInfo> parameterList)
            throws SQLException {
        return super.batchDeleteByDataPrimaryKey(parameterList, statementName);
    }

    public int batchUpdateForDistributionInfo(String statementName, List<DistributionInfo> parameterList)
            throws SQLException {
        return super.batchUpdateData(statementName, parameterList);
    }

    protected static class UpdateByExampleParms extends DistributionInfoExample {
        private static final long serialVersionUID = 2406012007423797488L;
        private Object record;

        public UpdateByExampleParms(Object record, DistributionInfoExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    public Long insertDistributionSelective(DistributionInfo record) throws SQLException {
        return (Long) sqlMapClient.insert("DISTRIBUTION_INFO.insertSelective", record);
    }

    public void batchInsertDistribution(String statementName, List<DistributionDetailInfo> parameterList)
            throws SQLException {
        super.batchInsertData(parameterList, statementName);
    }
}