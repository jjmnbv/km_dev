package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.DistributionDetailInfoDAO;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionDetailInfoExample;
import com.pltfm.app.vobject.DistributionInfo;


/**
 * 配送明细单DAO层实现类
 *
 * @author luoyi
 * @since 2013/08/20
 */
@Repository("distributionDetailInfoDao")
public class DistributionDetailInfoDAOImpl extends BaseDao implements DistributionDetailInfoDAO {

    public int countByExample(DistributionDetailInfoExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "DISTRIBUTION_DETAIL_INFO.countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(DistributionDetailInfoExample example) throws SQLException {
        int rows = sqlMapClient.delete(
                "DISTRIBUTION_DETAIL_INFO.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long detailId) throws SQLException {
        DistributionDetailInfo _key = new DistributionDetailInfo();
        _key.setDetailId(detailId);
        int rows = sqlMapClient.delete(
                "DISTRIBUTION_DETAIL_INFO.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(DistributionDetailInfo record) throws SQLException {
        sqlMapClient.insert("DISTRIBUTION_DETAIL_INFO.insert", record);
    }

    public void insertSelective(DistributionDetailInfo record) throws SQLException {
        sqlMapClient.insert("DISTRIBUTION_DETAIL_INFO.insertSelective", record);
    }

    public List<DistributionDetailInfo> selectByExample(DistributionDetailInfoExample example, int skip, int max)
            throws SQLException {
        return sqlMapClient.queryForList("DISTRIBUTION_DETAIL_INFO.selectByExample", example, skip, max);
    }

    public List<DistributionDetailInfo> selectByExample(DistributionDetailInfoExample example) throws SQLException {
        return sqlMapClient.queryForList("DISTRIBUTION_DETAIL_INFO.selectByExample", example);
    }

    public DistributionDetailInfo selectByPrimaryKey(Long detailId) throws SQLException {
        DistributionDetailInfo _key = new DistributionDetailInfo();
        _key.setDetailId(detailId);
        DistributionDetailInfo record = (DistributionDetailInfo) sqlMapClient.queryForObject("DISTRIBUTION_DETAIL_INFO.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByExampleSelective(DistributionDetailInfo record, DistributionDetailInfoExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update(
                "DISTRIBUTION_DETAIL_INFO.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(DistributionDetailInfo record, DistributionDetailInfoExample example)
            throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("DISTRIBUTION_DETAIL_INFO.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(DistributionDetailInfo record) throws SQLException {
        int rows = sqlMapClient.update("DISTRIBUTION_DETAIL_INFO.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(DistributionDetailInfo record) throws SQLException {
        int rows = sqlMapClient.update("DISTRIBUTION_DETAIL_INFO.updateByPrimaryKey", record);
        return rows;
    }

    public int batchSaveForDistributionDetail(List<DistributionDetailInfo> parameterList) throws SQLException {
        return super.batchInsertDataNt(parameterList, "DISTRIBUTION_DETAIL_INFO.insertSelective");
    }

    public int batchDeleteDistributionDetail(List<DistributionDetailInfo> parameterList) throws SQLException {
        return super.batchDeleteByDataPrimaryKey(parameterList, "DISTRIBUTION_DETAIL_INFO.deleteByPrimaryKey");
    }

    public int batchUpdateDistributionDetail(List<DistributionDetailInfo> parameterList) throws SQLException {
        return super.batchUpdateData("DISTRIBUTION_DETAIL_INFO.updateByPrimaryKeySelective", parameterList);
    }

    public int batchDeleteDistributionInfo(String statementName, List<DistributionInfo> parameterList) throws SQLException {
        return super.batchDeleteByDataPrimaryKey(parameterList, statementName);
    }

    protected static class UpdateByExampleParms extends  DistributionDetailInfoExample {
        private static final long serialVersionUID = 8955340750896911623L;
        private Object record;

        public UpdateByExampleParms(Object record, DistributionDetailInfoExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

}