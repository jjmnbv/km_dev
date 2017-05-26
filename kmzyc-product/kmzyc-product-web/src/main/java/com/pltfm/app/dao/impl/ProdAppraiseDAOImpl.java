package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProdAppraiseDAO;
import com.pltfm.app.vobject.ProdAppraise;
import com.pltfm.app.vobject.ProdAppraiseExample;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 产品评价
 *
 * @author tanyunxing
 */
@Repository("prodAppraiseDao")
public class ProdAppraiseDAOImpl extends BaseDao<ProdAppraise> implements ProdAppraiseDAO {

    public int countByExample(ProdAppraiseExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "PROD_APPRAISE.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ProdAppraiseExample example) throws SQLException {
        return sqlMapClient.delete("PROD_APPRAISE.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long appraiseId) throws SQLException {
        ProdAppraise key = new ProdAppraise();
        key.setAppraiseId(appraiseId);
        return sqlMapClient.delete("PROD_APPRAISE.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(ProdAppraise record) throws SQLException {
        sqlMapClient.insert("PROD_APPRAISE.ibatorgenerated_insert", record);
    }

    public void insertSelective(ProdAppraise record) throws SQLException {
        sqlMapClient.insert("PROD_APPRAISE.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProdAppraiseExample example) throws SQLException {
        return sqlMapClient.queryForList("PROD_APPRAISE.ibatorgenerated_selectByExample", example);
    }

    public List selectByExample(ProdAppraiseExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("PROD_APPRAISE.ibatorgenerated_selectByExample", example, skip, max);
    }

    public ProdAppraise selectByPrimaryKey(Long appraiseId) throws SQLException {
        ProdAppraise key = new ProdAppraise();
        key.setAppraiseId(appraiseId);
        return (ProdAppraise) sqlMapClient.queryForObject("PROD_APPRAISE.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByExampleSelective(ProdAppraise record, ProdAppraiseExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PROD_APPRAISE.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(ProdAppraise record, ProdAppraiseExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PROD_APPRAISE.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(ProdAppraise record) throws SQLException {
        return sqlMapClient.update("PROD_APPRAISE.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(ProdAppraise record) throws SQLException {
        return sqlMapClient.update("PROD_APPRAISE.ibatorgenerated_updateByPrimaryKey", record);
    }

    private static class UpdateByExampleParms extends ProdAppraiseExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProdAppraiseExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public int deleteByPrimaryKeyBatch(Long[] appraiseIds) throws SQLException {
        return sqlMapClient.delete("PROD_APPRAISE.deleteByPrimaryKeyBatch", appraiseIds);
    }

    @Override
    public int insertDataForExcel(List<ProdAppraise> list) throws SQLException {
        return batchInsertDataNt(list, "PROD_APPRAISE.ibatorgenerated_insertForExcel");
    }

    @Override
    public List<String> findIsExistUserName(List<String> checkUsers) throws SQLException {
        return sqlMapClient.queryForList("PROD_APPRAISE.findIsExistUserName", checkUsers);
    }

    @Override
    public List<Map> queryProductAppraise(Map<String, Object> condition) throws SQLException {
        return sqlMapClient.queryForList("PROD_APPRAISE.queryProductAppraise", condition);
    }

    @Override
    public int countProductAppraise(Long skuId) throws SQLException {
        return ((Integer)sqlMapClient.queryForObject("PROD_APPRAISE.countProductAppraise", skuId)).intValue();
    }
}