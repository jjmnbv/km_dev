package com.kmzyc.promotion.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.promotion.app.dao.BaseDao;
import com.kmzyc.promotion.app.dao.PromotionRuleDataDAO;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.app.vobject.PromotionRuleDataExample;

@Repository("promotionRuleDataDAO")
@SuppressWarnings({"unchecked", "unused"})
public class PromotionRuleDataDAOImpl extends BaseDao<PromotionRuleData>
        implements PromotionRuleDataDAO {

    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public int deleteByExample(PromotionRuleDataExample example) throws SQLException {

        int rows =
                sqlMapClient.delete("PROMOTION_RULE_DATA.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    @Override
    public void insertSelective(PromotionRuleData record) throws SQLException {

        sqlMapClient.insert("PROMOTION_RULE_DATA.ibatorgenerated_insertSelective", record);
    }

    @Override
    public List selectByExample(PromotionRuleDataExample example) throws SQLException {
        List list = sqlMapClient.queryForList("PROMOTION_RULE_DATA.ibatorgenerated_selectByExample",
                example);
        return list;
    }

    private static class UpdateByExampleParms extends PromotionRuleDataExample {
        private final Object record;

        public UpdateByExampleParms(Object record, PromotionRuleDataExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    /**
     * 根据活动ID获取活动规则，Date可为空
     * 
     * @param promotionInfoId
     * @return
     * @throws SQLException
     */
    @Override
    public List<PromotionRuleData> selectPromotionRuleList(Long promotionInfoId) {
        List<PromotionRuleData> prdList = null;
        try {

            prdList = sqlMapClient.queryForList("PROMOTION_RULE_DATA.findByPromotionRuleDataInfo",
                    promotionInfoId);
        } catch (Exception e) {
            logger.error("获取产品是否参加活动异常", e);
        }
        return prdList;
    }

    /**
     * 批量新增规则数据
     * 
     * @param promotionRuleDataList
     * @throws SQLException
     */
    @Override
    public void batchInsertData(List<PromotionRuleData> promotionRuleDataList) throws SQLException {

        super.batchInsertData(promotionRuleDataList,
                "PROMOTION_RULE_DATA.ibatorgenerated_insertSelective");
    }

    /**
     * 批量查询规则数据
     * 
     * @param pids
     * @return
     * @throws SQLException
     */
    @Override
    public List<PromotionRuleData> queryBatchPromotionRuleData(List<Long> pids)
            throws SQLException {

        return sqlMapClient.queryForList("PROMOTION_RULE_DATA.SQL_QUERY_BATCH_PROMOTION_RULE_DATA",
                pids);
    }



    @Override
    public List<com.alibaba.fastjson.JSONObject> selectPromotionRuleAndEntity(
            Map<String, String> map, String sql) throws SQLException {

        return sqlMapClient.queryForList(sql, map);
    }
}
