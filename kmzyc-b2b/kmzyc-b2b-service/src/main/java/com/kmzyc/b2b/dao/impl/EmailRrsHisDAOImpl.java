package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.EmailRrsHisDAO;
import com.kmzyc.b2b.model.EmailRrsHis;
import com.kmzyc.b2b.model.EmailRrsHisExample;

@Repository("emailRrsHisDAO")
public class EmailRrsHisDAOImpl implements EmailRrsHisDAO {
    /**
     * EMAIL_RRS_HIS
     */
    @Resource
    private SqlMapClient sqlMapClient;


    /**
     * EMAIL_RRS_HIS deleteByExample
     */
    public int deleteByExample(EmailRrsHisExample example) throws SQLException {

        int rows = sqlMapClient.delete("EMAIL_RRS_HIS.deleteByExample", example);
        return rows;
    }

    /**
     * EMAIL_RRS_HIS deleteByPrimaryKey
     */
    public int deleteByPrimaryKey(Long emailRrsHisId) throws SQLException {
        EmailRrsHis _key = new EmailRrsHis();
        _key.setEmailRrsHisId(emailRrsHisId);
        int rows = sqlMapClient.delete("EMAIL_RRS_HIS.deleteByPrimaryKey", _key);
        return rows;
    }

    /**
     * EMAIL_RRS_HIS insert
     */
    public void insert(EmailRrsHis record) throws SQLException {
        sqlMapClient.insert("EMAIL_RRS_HIS.insert", record);
    }

    /**
     * EMAIL_RRS_HIS insertSelective
     */
    public void insertSelective(EmailRrsHis record) throws SQLException {
        sqlMapClient.insert("EMAIL_RRS_HIS.insertSelective", record);
    }

    /**
     * EMAIL_RRS_HIS selectByExample
     */
    @SuppressWarnings("rawtypes")
    public List selectByExample(EmailRrsHisExample example) throws SQLException {
        List list = sqlMapClient.queryForList("EMAIL_RRS_HIS.selectByExample", example);
        return list;
    }

    /**
     * EMAIL_RRS_HIS selectByPrimaryKey
     */
    public EmailRrsHis selectByPrimaryKey(Long emailRrsHisId) throws SQLException {
        EmailRrsHis _key = new EmailRrsHis();
        _key.setEmailRrsHisId(emailRrsHisId);
        EmailRrsHis record =
                (EmailRrsHis) sqlMapClient.queryForObject("EMAIL_RRS_HIS.selectByPrimaryKey", _key);
        return record;
    }

    /**
     * EMAIL_RRS_HIS updateByPrimaryKeySelective
     */
    public int updateByPrimaryKeySelective(EmailRrsHis record) throws SQLException {
        int rows = sqlMapClient.update("EMAIL_RRS_HIS.updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * EMAIL_RRS_HIS updateByPrimaryKey
     */
    public int updateByPrimaryKey(EmailRrsHis record) throws SQLException {
        int rows = sqlMapClient.update("EMAIL_RRS_HIS.updateByPrimaryKey", record);
        return rows;
    }

    /**
     * EMAIL_RRS_HIS
     */
    protected static class UpdateByExampleParms extends EmailRrsHisExample {
        private Object record;

        public UpdateByExampleParms(Object record, EmailRrsHisExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
