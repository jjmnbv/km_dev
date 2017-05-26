package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.pltfm.app.dao.BaseDao;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.SaleRankDAO;
import com.pltfm.app.vobject.SaleRank;
import com.pltfm.app.vobject.SaleRankExample;

@Component("saleRankDao")
public class SaleRankDAOImpl extends BaseDao implements SaleRankDAO {

    public int countByExample(SaleRankExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SALE_RANK.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SaleRankExample example) throws SQLException {
        int rows = sqlMapClient.delete("SALE_RANK.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long saleRankId) throws SQLException {
        SaleRank key = new SaleRank();
        key.setSaleRankId(saleRankId);
        int rows = sqlMapClient.delete("SALE_RANK.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(SaleRank record) throws SQLException {
        sqlMapClient.insert("SALE_RANK.ibatorgenerated_insert", record);
    }

    public void insertSelective(SaleRank record) throws SQLException {
        sqlMapClient.insert("SALE_RANK.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(SaleRankExample example) throws SQLException {
        List list = sqlMapClient.queryForList("SALE_RANK.ibatorgenerated_selectByExample", example);
        return list;
    }

    public SaleRank selectByPrimaryKey(Long saleRankId) throws SQLException {
        SaleRank key = new SaleRank();
        key.setSaleRankId(saleRankId);
        SaleRank record = (SaleRank) sqlMapClient.queryForObject("SALE_RANK.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(SaleRank record, SaleRankExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SALE_RANK.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(SaleRank record, SaleRankExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SALE_RANK.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(SaleRank record) throws SQLException {
        int rows = sqlMapClient.update("SALE_RANK.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(SaleRank record) throws SQLException {
        int rows = sqlMapClient.update("SALE_RANK.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends SaleRankExample {
        private Object record;

        public UpdateByExampleParms(Object record, SaleRankExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}