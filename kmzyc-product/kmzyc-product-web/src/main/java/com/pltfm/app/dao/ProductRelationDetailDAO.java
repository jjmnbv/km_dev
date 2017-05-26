package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.ProductRelationDetail;
import com.pltfm.app.vobject.ProductRelationDetailExample;
import com.kmzyc.commons.page.Page;

public interface ProductRelationDetailDAO {

    int countByExample(ProductRelationDetailExample example) throws SQLException;

    int deleteByExample(ProductRelationDetailExample example) throws SQLException;

    int deleteByPrimaryKey(Long relationDetailId) throws SQLException;

    void insert(ProductRelationDetail record) throws SQLException;

    void insertSelective(ProductRelationDetail record) throws SQLException;

    List selectByExample(ProductRelationDetailExample example) throws SQLException;

    List selectByExample(ProductRelationDetailExample example, Page page) throws SQLException;

    ProductRelationDetail selectByPrimaryKey(Long relationDetailId) throws SQLException;

    int updateByExampleSelective(ProductRelationDetail record, ProductRelationDetailExample example) throws SQLException;

    int updateByExample(ProductRelationDetail record, ProductRelationDetailExample example) throws SQLException;

    void updateByPrimaryKeySelective(List<ProductRelationDetail> record) throws SQLException;

    int updateByPrimaryKeySelective(ProductRelationDetail record) throws SQLException;

    int updateByPrimaryKey(ProductRelationDetail record) throws SQLException;

    /**
     * 批量保存产品关联 字段的记录
     *
     * @param details
     * @return
     * @throws SQLException
     */
    void batchSaveProductRelationDetail(List<ProductRelationDetail> details) throws SQLException;

    /**
     * 根据产品关联的主键id 批量删除对应的字段信息
     *
     * @param relationId
     * @return
     * @throws SQLException
     */
    int batchDelProductRelationDetailByRelationId(List<Long> relationId) throws SQLException;

    /**
     * 根据关联子表的主键，删除关联字表的记录
     *
     * @param relationDetailId
     * @return
     * @throws SQLException
     */
    int batchDelProductRelationDetailByRelationDetailId(List<Long> relationDetailId) throws SQLException;

}