package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ConsultDAO;
import com.pltfm.app.vobject.Consult;
import com.pltfm.app.vobject.ConsultExample;
import com.pltfm.sys.model.SysModelUtil;
import com.kmzyc.commons.page.Page;

@Repository("consultDao")
public class ConsultDAOImpl extends BaseDao<Consult> implements ConsultDAO {

    public int countByExample(ConsultExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("CONSULT.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ConsultExample example) throws SQLException {
        int rows = sqlMapClient.delete("CONSULT.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(BigDecimal consultId) throws SQLException {
        Consult key = new Consult();
        key.setConsultId(consultId.longValue());
        int rows = sqlMapClient.delete("CONSULT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(Consult record) throws SQLException {
        sqlMapClient.insert("CONSULT.ibatorgenerated_insert", record);
    }

    public void insertSelective(Consult record) throws SQLException {
        sqlMapClient.insert("CONSULT.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ConsultExample example) throws SQLException {
        List list = sqlMapClient.queryForList("CONSULT.ibatorgenerated_selectByExample", example);
        return list;
    }

    public Consult selectByPrimaryKey(long consultId) throws SQLException {
        Consult key = new Consult();
        key.setConsultId(consultId);
        Consult record = (Consult) sqlMapClient.queryForObject("CONSULT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public Consult selectByConsultId(BigDecimal consultId) throws SQLException {
        Consult key = new Consult();
        key.setConsultId(consultId.longValue());
        Consult record = (Consult) sqlMapClient.queryForObject("CONSULT.ibatorgenerated_selectByConsultId", key);
        return record;
    }

    public int updateByExampleSelective(Consult record, ConsultExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CONSULT.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(Consult record, ConsultExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CONSULT.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(Consult record) throws SQLException {
        int rows = sqlMapClient.update("CONSULT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(Consult record) throws SQLException {
        int rows = sqlMapClient.update("CONSULT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    private static class UpdateByExampleParms extends ConsultExample {
        private Object record;

        public UpdateByExampleParms(Object record, ConsultExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public Page selectPageByVo(Page page, Consult vo) throws SQLException {
        //用List接收
        List list = sqlMapClient.queryForList("CONSULT.getConsultCount", vo);

        SysModelUtil countResult = (SysModelUtil) list.get(0);
        //总条数
        int recs = countResult.getTheCount().intValue();

        int pagecount = 1;
        //总页数
        if (recs > 1) {
            pagecount = (recs - 1) / page.getPageSize() + 1;
        }
        page.setRecordCount(recs);
        page.setPageCount(pagecount);
        List pageList = sqlMapClient.queryForList("CONSULT.searchPageByVo", vo);
        page.setDataList(pageList);
        return page;
    }

    @Override
    public List selectPageByVo(Consult vo) throws SQLException {
        //用List接收
        List pageList = sqlMapClient.queryForList("CONSULT.searchPageByVo", vo);
        return pageList;
    }

    @Override
    public List queryForList(Consult consult) throws SQLException {
        List list = sqlMapClient.queryForList("CONSULT.getConsultCount", consult);
        return list;
    }

    @Override
    public List queryForPage(Consult consult) throws SQLException {
        List list = sqlMapClient.queryForList("CONSULT.searchPageByVo", consult);
        return list;
    }

    @Override
    public Consult insertConsult(Consult consult) throws SQLException {
        Long i = ((Long) sqlMapClient.insert("CONSULT.ibatorgenerated_insertSelective", consult));
        return selectByConsultId(new BigDecimal(i));
    }

}