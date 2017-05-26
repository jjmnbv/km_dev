package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.SectionsDetailDAO;
import com.pltfm.app.vobject.ProductHotSellInfoCache;
import com.pltfm.app.vobject.SectionsDetail;
import com.pltfm.app.vobject.SectionsDetailExample;

@Repository("SectionsDetailDao")
public class SectionsDetailDAOImpl extends BaseDao implements SectionsDetailDAO {

    @Override
    public int countByExample(SectionsDetailExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("SECTIONS_DETAIL.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    @Override
    public int deleteByExample(SectionsDetailExample example) throws SQLException {
        int rows = sqlMapClient.delete("SECTIONS_DETAIL.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer sectionsDetailId) throws SQLException {
        SectionsDetail key = new SectionsDetail();
        key.setSectionsDetailId(Long.valueOf(sectionsDetailId));
        return sqlMapClient.delete("SECTIONS_DETAIL.ibatorgenerated_deleteByPrimaryKey", key);
    }

    @Override
    public void insert(SectionsDetail record) throws SQLException {
        sqlMapClient.insert("SECTIONS_DETAIL.ibatorgenerated_insert", record);
    }

    @Override
    public void insertSelective(SectionsDetail record) throws SQLException {
        sqlMapClient.insert("SECTIONS_DETAIL.ibatorgenerated_insertSelective", record);
    }

    @Override
    public List selectByExample(SectionsDetailExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("SECTIONS_DETAIL.ibatorgenerated_selectByExample", example, skip, max);
    }

    public SectionsDetail selectByPrimaryKey(Integer sectionsDetailId) throws SQLException {
        SectionsDetail key = new SectionsDetail();
        key.setSectionsDetailId(Long.parseLong(sectionsDetailId.toString()));
        SectionsDetail record = (SectionsDetail) sqlMapClient.queryForObject("SECTIONS_DETAIL.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    @Override
    public int updateByExampleSelective(SectionsDetail record, SectionsDetailExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SECTIONS_DETAIL.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    @Override
    public int updateByExample(SectionsDetail record, SectionsDetailExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SECTIONS_DETAIL.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    @Override
    public int updateByPrimaryKeySelective(SectionsDetail record) throws SQLException {
        int rows = sqlMapClient.update("SECTIONS_DETAIL.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    @Override
    public int updateByPrimaryKey(SectionsDetail record) throws SQLException {
        int rows = sqlMapClient.update("SECTIONS_DETAIL.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    private static class UpdateByExampleParms extends SectionsDetailExample {
        private Object record;

        public UpdateByExampleParms(Object record, SectionsDetailExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public int deleteByPrimaryKey(Long sectionsDetailId) throws SQLException {
        SectionsDetail key = new SectionsDetail();
        key.setSectionsDetailId(sectionsDetailId);
        int rows = sqlMapClient.delete("SECTIONS_DETAIL.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    @Override
    public SectionsDetail selectByPrimaryKey(Long sectionsDetailId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int deleteBySectionsId(Long sectionsId) throws SQLException {
        int rows = sqlMapClient.delete("SECTIONS_DETAIL.ibatorgenerated_deleteBySectionsId", sectionsId);
        return rows;
    }

    @Override
    public List selectByExample(SectionsDetail detail, int skip, int max) throws SQLException {
        List list = this.getSqlMapClient().queryForList("SECTIONS_DETAIL.selectSectionsDetailBySectionsId", detail, skip, max);
        return list;
    }

    @Override
    public int batchInsertSectionsDetail(List<SectionsDetail> sectionsDetailList) throws SQLException {
        return super.batchInsertDataNt(sectionsDetailList, "SECTIONS_DETAIL.ibatorgenerated_insert");
    }

    @Override
    public int batchDeleteSectionsDetail(List<Long> sectionsDetailIds) throws SQLException {
        return super.batchDeleteByDataPrimaryKeyNt(sectionsDetailIds, "SECTIONS_DETAIL.batchDeleteByPrimaryKey");
    }

    @Override
    public int batchUpdateSectionsDetail(List<SectionsDetail> sectionsDetailList) throws SQLException {
        return super.batchUpdateNt(sectionsDetailList, "SECTIONS_DETAIL.updateSectionDetail");
    }

    @Override
    public List<ProductHotSellInfoCache> findHotSellZYCProducts() throws SQLException {
        return sqlMapClient.queryForList("SECTIONS_DETAIL.findHotSellZYCProducts");
    }

    @Override
    public List<ProductHotSellInfoCache> findHotSellProducts(String saleType) throws SQLException {
        return sqlMapClient.queryForList("SECTIONS_DETAIL.findHotSellProducts", saleType);
    }

    @Override
    public List<ProductHotSellInfoCache> findHotSellB2BProducts() throws SQLException {
        return sqlMapClient.queryForList("SECTIONS_DETAIL.findHotSellB2BProducts");
    }

    @Override
    public List<SectionsDetail> selectSectionsDetailByProductId(Long productId) throws SQLException {
        return sqlMapClient.queryForList("SECTIONS_DETAIL.selectSectionsDetailByProductId", productId);
    }
}