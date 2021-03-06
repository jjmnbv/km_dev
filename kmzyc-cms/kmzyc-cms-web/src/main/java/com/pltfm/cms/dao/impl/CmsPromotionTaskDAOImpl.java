package com.pltfm.cms.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.cms.dao.CmsPromotionTaskDAO;
import com.pltfm.cms.vobject.CmsPromotionTask;
import com.pltfm.cms.vobject.CmsPromotionTaskExample;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component("cmsPromotionTaskDAO")
public class CmsPromotionTaskDAOImpl implements CmsPromotionTaskDAO {
	private static Logger logger = LoggerFactory.getLogger(CmsPromotionTaskDAOImpl.class);

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;


    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PROMOTION_TASK
     *
     * @abatorgenerated Sat Oct 12 16:04:01 CST 2013
     */
    public int insert(CmsPromotionTask record) throws SQLException {
        int i = 0;
        try {
            i = 1;
            sqlMapClient.insert("CMS_PROMOTION_TASK.abatorgenerated_insert", record);
        } catch (Exception e) {
            logger.error("CmsPromotionTaskDAOImpl.insert异常：" + e.getMessage(), e);
        }
        return i;
    }

    public List selectByExample(CmsPromotionTaskExample example) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_PROMOTION_TASK.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PROMOTION_TASK
     *
     * @abatorgenerated Sat Oct 12 16:04:01 CST 2013
     */
    public int deleteByExample(CmsPromotionTaskExample example) throws SQLException {
        int rows = sqlMapClient.delete("CMS_PROMOTION_TASK.abatorgenerated_deleteByExample", example);
        return rows;
    }


    public int countByTask(CmsPromotionTask example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("CMS_PROMOTION_TASK.countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PROMOTION_TASK
     *
     * @abatorgenerated Sat Oct 12 16:04:01 CST 2013
     */
    public int updateByExampleSelective(CmsPromotionTask record, CmsPromotionTaskExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CMS_PROMOTION_TASK.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }


    public int updateByExample(CmsPromotionTask record) throws SQLException {
        int rows = sqlMapClient.update("CMS_PROMOTION_TASK.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table CMS_PROMOTION_TASK
     *
     * @abatorgenerated Sat Oct 12 16:04:01 CST 2013
     */
    private static class UpdateByExampleParms extends CmsPromotionTaskExample {
        private Object record;

        public UpdateByExampleParms(Object record, CmsPromotionTaskExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    /**
     * 条数
     *
     * @abatorgenerated
     */
    public int countByExample(CmsPromotionTask example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("CMS_PROMOTION_TASK.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * 分页查询
     */
    public List queryForPage(CmsPromotionTask record) throws SQLException {

        return sqlMapClient.queryForList("CMS_PROMOTION_TASK.abatorgenerated_pageBy", record);
    }


    /**
     * 过期活动
     */
    public List queryExpirePromotion() throws SQLException {

        return sqlMapClient.queryForList("CMS_PROMOTION_TASK.abatorgenerated_expirePromotion");
    }

    /**
     * 根据活动信息主键查询单条活动信息
     *
     * @param viewPromotionId 活动信息主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public CmsPromotionTask selectByPrimaryKey(Integer id, int siteId)
            throws SQLException {
        CmsPromotionTask key = new CmsPromotionTask();
        key.setId(id);
        key.setSiteId(siteId);
        CmsPromotionTask record = (CmsPromotionTask) sqlMapClient.queryForObject("CMS_PROMOTION_TASK.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    @Override
    public int deleteByPrimaryKey(int record, int siteId) throws SQLException {
        CmsPromotionTask key = new CmsPromotionTask();
        key.setId(record);
        key.setSiteId(siteId);
        int rows = sqlMapClient.delete("CMS_PROMOTION_TASK.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public int getSeqId() throws SQLException {
        int seq = (Integer) sqlMapClient.queryForObject("CMS_PROMOTION_TASK.abatorgenerated_getSeqId");
        return seq;
    }

    @Override
    public CmsPromotionTask selectByIdAndSiteId(CmsPromotionTask CMSP)
            throws SQLException {
        CmsPromotionTask record = (CmsPromotionTask) sqlMapClient.queryForObject("CMS_PROMOTION_TASK.abatorgenerated_selectByPrimaryKey", CMSP);
        return record;
    }


    @Override
    public CmsPromotionTask publishObj() throws SQLException {
        CmsPromotionTask record = (CmsPromotionTask) sqlMapClient.queryForObject("CMS_PROMOTION_TASK.abatorgenerated_publishData");
        return record;
    }

    @Override
    public CmsPromotionTask closeObj() throws SQLException {
        CmsPromotionTask record = (CmsPromotionTask) sqlMapClient.queryForObject("CMS_PROMOTION_TASK.abatorgenerated_closeData");
        return record;
    }

    @Override
    public List getAllPromtionTask(CmsPromotionTask promotion) throws SQLException {
        // TODO Auto-generated method stub
        return this.sqlMapClient.queryForList("CMS_PROMOTION_TASK.abatorgenerated_getAllPromotionTask", promotion);
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }


}