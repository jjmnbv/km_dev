package com.pltfm.app.dao.impl;


import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.TiedSadeDAO;
import com.pltfm.app.vobject.TiedSade;
import com.pltfm.app.vobject.TiedSadeExample;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("tiedSadeDao")
public class TiedSadeDAOImpl extends BaseDao implements TiedSadeDAO {

    public int countByExample(TiedSadeExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("TIED_SADE.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(TiedSadeExample example) throws SQLException {
        int rows = sqlMapClient.delete("TIED_SADE.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long tiedSadeId) throws SQLException {
        TiedSade key = new TiedSade();
        key.setTiedSadeId(tiedSadeId);
        int rows = sqlMapClient.delete("TIED_SADE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(TiedSade record) throws SQLException {
        sqlMapClient.insert("TIED_SADE.ibatorgenerated_insert", record);
    }

    public void insertSelective(TiedSade record) throws SQLException {
        sqlMapClient.insert("TIED_SADE.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(TiedSadeExample example) throws SQLException {
        List list = sqlMapClient.queryForList("TIED_SADE.ibatorgenerated_selectByExample", example);
        return list;
    }

    public TiedSade selectByPrimaryKey(Long tiedSadeId) throws SQLException {
        TiedSade key = new TiedSade();
        key.setTiedSadeId(tiedSadeId);
        TiedSade record = (TiedSade) sqlMapClient.queryForObject("TIED_SADE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(TiedSade record, TiedSadeExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("TIED_SADE.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(TiedSade record, TiedSadeExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("TIED_SADE.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(TiedSade record) throws SQLException {
        int rows = sqlMapClient.update("TIED_SADE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    private static class UpdateByExampleParms extends TiedSadeExample {
        private Object record;

        public UpdateByExampleParms(Object record, TiedSadeExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    public int updateByPrimaryKey(TiedSade record) throws SQLException {
        int rows = sqlMapClient.update(
                "TIED_SADE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;


    }

    public void updateMatchPriceByKey(TiedSade record) throws SQLException {
        sqlMapClient.update("TIED_SADE.updateMatchPriceByKey", record);

    }

    public int insertTiedSaleByBatch(List<TiedSade> list) throws SQLException {
        return batchInsertDataNt(list, "TIED_SADE.ibatorgenerated_insertSelective");

    }

    public void delBatchByPrimaryKey(List<Long> list) throws SQLException {
        batchDeleteByDataPrimaryKey(list, "TIED_SADE.deleteByPrimary");

    }

    public void updateTiedSaleType(TiedSade tiedSade) throws Exception {
        sqlMapClient.update("TIED_SADE.updateTiedSaleType", tiedSade);

    }

}