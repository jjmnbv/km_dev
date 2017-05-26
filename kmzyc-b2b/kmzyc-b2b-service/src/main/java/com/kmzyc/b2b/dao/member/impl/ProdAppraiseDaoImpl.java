package com.kmzyc.b2b.dao.member.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.member.ProdAppraiseDao;
import com.kmzyc.b2b.model.AppraiseAddtoContent;
import com.kmzyc.b2b.model.ProdAppraise;
import com.km.framework.persistence.impl.DaoImpl;

/**
 * Description:商品评价数据访问类 User: lishiming Date: 13-10-17 下午3:45 Since: 1.0
 */
@Component
public class ProdAppraiseDaoImpl extends DaoImpl implements ProdAppraiseDao {

    // static Logger logger = Logger.getLogger(ProdAppraiseDaoImpl.class);
    private static Logger logger = LoggerFactory.getLogger(ProdAppraiseDaoImpl.class);

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /**
     * 根据SKUID，查询商品的总评分人数
     */
    public int selectCountPersonBySkuId(ProdAppraise prodAppraise) throws SQLException {
        int totle = 0;
        if (sqlMapClient.queryForObject("ProdAppraise.selectCountBySkuId", prodAppraise) == null) {
            totle = 0;
        } else {
            totle =
                    (Integer) sqlMapClient.queryForObject("ProdAppraise.selectCountBySkuId",
                            prodAppraise);
        }

        return totle;
    }

    /**
     * 根据SKUID，查询商品的总评分
     */
    public int selectPointBySkuId(ProdAppraise prodAppraise) throws SQLException {
        int totle_point;
        if (sqlMapClient.queryForObject("ProdAppraise.selectPonitBySkuId", prodAppraise) != null) {
            totle_point =
                    (Integer) sqlMapClient.queryForObject("ProdAppraise.selectPonitBySkuId",
                            prodAppraise);
        } else {
            totle_point = 0;
        }
        return totle_point;
    }

    /**
     * 根据实体查询对应的List
     */
    public List<ProdAppraise> findProdAppList(ProdAppraise prodAppraise) throws SQLException {
        List<ProdAppraise> prodAppraiseList =
                sqlMapClient.queryForList("ProdAppraise.selectAppraiseList", prodAppraise);
        return prodAppraiseList;
    }

    /**
     * 验证orderdetail和skuid是否有对应的打分
     */
    public ProdAppraise findProdAppByOrderAndSku(ProdAppraise prodAppraise) throws SQLException {

        ProdAppraise prodAppraiseResult = new ProdAppraise();
        try {
            prodAppraiseResult =
                    (ProdAppraise) sqlMapClient.queryForObject(
                            "ProdAppraise.selectAppraiseByorder", prodAppraise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return prodAppraiseResult;
    }

    /**
     * 查询评论是否存在
     */
    public int findProdAppraiseByOrderDetailId(Map map) throws SQLException {

        int re = 0;
        try {
            re =
                    (Integer) sqlMapClient.queryForObject(
                            "ProdAppraise.findProdAppraiseByOrderDetailId", map);

        } catch (Exception e) {
            logger.error("查询评论是否存在ERROR", e);
        }
        return re;
    }

    /**
     * 验证是否有追平
     */
    public AppraiseAddtoContent findAppendByOrderAndSku(AppraiseAddtoContent appraiseAdd)
            throws SQLException {
        AppraiseAddtoContent appraiseResult =
                (AppraiseAddtoContent) sqlMapClient.queryForObject(
                        "APPRAISE_ADDTO_CONTENT.selectAppraiseAddByOrder", appraiseAdd);
        return appraiseResult;
    }

}
