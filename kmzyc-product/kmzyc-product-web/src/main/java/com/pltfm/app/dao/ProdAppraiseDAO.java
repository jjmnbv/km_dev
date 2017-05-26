package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProdAppraise;
import com.pltfm.app.vobject.ProdAppraiseExample;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 产品评价DAO
 *
 * @author tanyunxing
 */
public interface ProdAppraiseDAO {

    int countByExample(ProdAppraiseExample example) throws SQLException;

    int deleteByExample(ProdAppraiseExample example) throws SQLException;

    int deleteByPrimaryKey(Long appraiseId) throws SQLException;

    /**
     * 批量删除
     *
     * @param appraiseIds
     * @return
     * @throws SQLException
     */
    int deleteByPrimaryKeyBatch(Long[] appraiseIds) throws SQLException;

    void insert(ProdAppraise record) throws SQLException;

    void insertSelective(ProdAppraise record) throws SQLException;

    List selectByExample(ProdAppraiseExample example) throws SQLException;

    List selectByExample(ProdAppraiseExample example, int skip, int max) throws SQLException;

    List<String> findIsExistUserName(List<String> checkUsers) throws SQLException;

    int insertDataForExcel(List<ProdAppraise> list) throws SQLException;

    ProdAppraise selectByPrimaryKey(Long appraiseId) throws SQLException;

    int updateByExampleSelective(ProdAppraise record, ProdAppraiseExample example) throws SQLException;

    int updateByExample(ProdAppraise record, ProdAppraiseExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProdAppraise record) throws SQLException;

    int updateByPrimaryKey(ProdAppraise record) throws SQLException;

    /**
     * 查找sku的评论  每页10条
     *
     * @param condition 查询参数
     *                  <note>
     *                  isTimeSort    true时间排序/false评分排序
     *                  sort          desc降序/asc升序
     *                  skuId         sku的id
     *                  pageNumber    评论页数
     *                  </note>
     * @return
     * @throws Exception
     */
    List<Map> queryProductAppraise(Map<String, Object> condition) throws SQLException;

    /**
     * 统计当前skuId下的评论条数
     *
     * @param skuId sku的id
     * @return
     * @throws Exception
     */
    int countProductAppraise(Long skuId) throws SQLException;
}