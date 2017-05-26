package com.pltfm.app.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.ProductRelation;
import com.pltfm.app.vobject.ProductRelationAndDetail;
import com.pltfm.app.vobject.ProductRelationDetailExample;
import com.pltfm.app.vobject.ProductRelationExample;
import com.kmzyc.commons.page.Page;

public interface ProductRelationDAO {

    int countByExample(ProductRelationExample example) throws SQLException;

    int deleteByExample(ProductRelationExample example) throws SQLException;

    int deleteByPrimaryKey(Long relationId) throws SQLException;

    void insert(ProductRelation record) throws SQLException;

    void insertSelective(ProductRelation record) throws SQLException;

    List selectByExample(ProductRelationExample example, Page page) throws SQLException;

    ProductRelation selectByPrimaryKey(Long relationId) throws SQLException;

    List selectByExample(ProductRelationExample example) throws SQLException;

    int updateByExampleSelective(ProductRelation record, ProductRelationExample example) throws SQLException;

    int updateByExample(ProductRelation record, ProductRelationExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductRelation record) throws SQLException;

    int updateByPrimaryKey(ProductRelation record) throws SQLException;

    /**
     * 插入主关联主表，所获得的插入的主键
     *
     * @param record
     * @return
     * @throws SQLException
     */
    Long insertProductRelation(ProductRelation record) throws SQLException;

    /**
     * 批量删除产品关联组合 主单中的信息
     *
     * @param detailIdList
     * @return
     * @throws SQLException
     */
    int batchDelProductRelation(List<Long> detailIdList) throws SQLException;

    /**
     * 根据SKUID 查询产品关联表、明细表
     *
     * @param skuId
     * @return
     * @throws SQLException
     */
    List<ProductRelationAndDetail> selectProductRelationAndDetailBySkuId(Long skuId) throws SQLException;

    /**
     * 根据relationId 更改套餐的状态为-1（删除）
     *
     * @param relations
     * @return
     * @throws SQLException
     */
    int batchUpateProductRelationStatus(List<Long> relations) throws SQLException;

    /**
     * 根据realationID 更新创建时间
     *
     * @param productRelation
     * @return
     * @throws SQLException
     */
    int updateProductRelation(ProductRelation productRelation) throws SQLException;

    /**
     * 根据被关联的skuId 查询出 关联主表 ，子表信息
     *
     * @param skuId
     * @return
     * @throws SQLException
     */
    List<ProductRelationAndDetail> selectProductRelationAndDetailsByRelaitonSkuId(Long skuId) throws SQLException;

    /**
     * 套餐列表
     *
     * @param example
     * @return List
     * @throws SQLException
     */
    List selectByExampleTaoCan(ProductRelationExample example, Page page) throws SQLException;

    /**
     * 套餐列表统计
     *
     * @param example
     * @return int
     */
    int countByExampleTaoCan(ProductRelationExample example) throws SQLException;

    /**
     * 根据套餐ID查询产品ID集合（用于调用产品上下架接口）
     *
     * @param relationId
     * @return
     * @throws SQLException
     */
    List<Integer> selectProductIdByRelationId(List<Long> relationId) throws SQLException;

    /**
     * 组方列表
     */
    List selectByExampleZF(ProductRelationExample example, Page page) throws SQLException;

    /**
     * 组方列表统计
     */
    int countByExampleZF(ProductRelationExample example) throws SQLException;

    /**
     * 根据skuid获取人气组合
     *
     * @param skuId
     * @return
     * @throws SQLException
     */
    List<ProductRelation> getRecommendListBySkuId(Long skuId) throws SQLException;

    /**
     * 根据skuId获取优惠套餐组合
     *
     * @param skuId
     * @return
     * @throws SQLException
     */
    List<ProductRelation> getPackageListBySkuId(Long skuId) throws SQLException;

    int updateProductRelationStatus(Map<String, String> condition) throws SQLException;

    int batchUpdateRelationList(List<ProductRelation> list) throws SQLException;
}