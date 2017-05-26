package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductDraftExample;

public interface ProductDraftDAO {

    int countByExample(ProductDraftExample example) throws SQLException;

    int deleteByExample(ProductDraftExample example) throws SQLException;

    int deleteByPrimaryKey(Long productId) throws SQLException;

    Long insert(ProductDraft record) throws SQLException;

    void insertWithOutSeq(ProductDraft record) throws SQLException;

    void insertSelective(ProductDraft record) throws SQLException;

    List selectByExampleWithoutBLOBs(ProductDraftExample example) throws SQLException;

    List selectByExample(ProductDraftExample example, int skip, int max) throws SQLException;

    ProductDraft selectByPrimaryKey(Long productId) throws SQLException;

    ProductDraft findSingleProduct(ProductDraft productDraft) throws SQLException;

    int updateByExampleSelective(ProductDraft record, ProductDraftExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductDraft record) throws SQLException;

    /**
     * 将草稿表中的数据移到正式表中
     *
     * @param productDraft
     * @return
     * @throws SQLException
     */
    String insertProductOfficial(ProductDraft productDraft) throws SQLException;

    /**
     * 更新正式表中的数据
     *
     * @param productDraft
     * @throws SQLException
     */
    void updateProductOfficialByDraft(ProductDraft productDraft) throws SQLException;

    /**
     * 批量保存数据（对象为正式表数据）
     *
     * @param obj
     * @return
     * @throws SQLException
     */
    void insertDraftFromOfficial(Product obj) throws SQLException;

    /**
     * 批量更新数据
     *
     * @param list
     * @return
     * @throws Exception
     */
    void batchUpdateProductDraft(List<ProductDraft> list) throws Exception;

    /**
     * 检查上架添加时获取产品列表
     *
     * @param productDraftId
     * @return
     * @throws SQLException
     */
    List<ProductDraft> selectSkuByProductDraftId(Long productDraftId) throws SQLException;

    List<ProductDraft> selectByExampleByUser(ProductDraftExample pe, int skip, int max) throws SQLException;

    int countByExampleByUser(ProductDraftExample pe) throws SQLException;

    /**
     * 批量提交审核
     *
     * @param list
     * @return
     * @throws Exception
     */
    int batchSubmitDraftTheAudit(List<ProductDraft> list) throws SQLException;

    List selectByExampleByUserForAudit(ProductDraftExample example, int skip, int max) throws SQLException;

}