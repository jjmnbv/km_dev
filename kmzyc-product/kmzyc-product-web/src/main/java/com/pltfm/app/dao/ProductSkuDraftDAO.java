package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuDraft;
import com.pltfm.app.vobject.ProductSkuDraftExample;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductSkuDraftDAO {

    int countByExample(ProductSkuDraftExample example) throws SQLException;

    int deleteByExample(ProductSkuDraftExample example) throws SQLException;

    int deleteByPrimaryKey(Long productSkuId) throws SQLException;

    void insert(ProductSkuDraft record) throws SQLException;

    void insertSelective(ProductSkuDraft record) throws SQLException;

    List selectByExample(ProductSkuDraftExample example) throws SQLException;

    List selectByExampleIntoOfficial(ProductSkuDraftExample example) throws SQLException;

    List selectSomeSKUS(ProductSkuDraft record) throws SQLException;

    int selectSomeSKUSCounts(ProductSkuDraft record) throws SQLException;

    ProductSkuDraft selectByPrimaryKey(Long productSkuId) throws SQLException;

    ProductSkuDraft findSingleSkuAndAttrValue(Long productSkuId) throws SQLException;

    ProductSkuDraft findSingleSkuAndAttrValueForIntroduce(Long productSkuId) throws SQLException;

    int updateByExampleSelective(ProductSkuDraft record, ProductSkuDraftExample example) throws SQLException;

    int updateByExample(ProductSkuDraft record, ProductSkuDraftExample example) throws SQLException;

    void updatePriceBatch(List<ProductSkuDraft> list) throws SQLException;

    int updateByPrimaryKeySelective(ProductSkuDraft record) throws SQLException;

    void batchUpdateByPrimaryKeySelective(List<ProductSkuDraft> list) throws SQLException;

    int updateByPrimaryKey(ProductSkuDraft record) throws SQLException;

    /**
     * 向正式表中插入数据
     *
     * @param list
     * @return
     * @throws SQLException
     */
    int batchInsertOfficial(List<ProductSkuDraft> list) throws SQLException;

    /**
     * 从正式表中获取数据，批量插入草稿表中，不生成序列（主要用于正式表更新时）
     *
     * @param list
     * @return
     * @throws SQLException
     */
    int batchInsertDraftFromOfficialWithOutSeq(List list) throws SQLException;

    /**
     * 往草稿表中插入数据，不生成序列（主要用于单独的价格更新时）
     *
     * @param list
     * @return
     * @throws SQLException
     */
    int batchInsertDraftWithOutSeq(List<ProductSkuDraft> list) throws SQLException;

    /**
     * 从草稿表中获取数据，批量更新正式表中的数据
     *
     * @param list
     * @return
     * @throws Exception
     */
    int batchUpdateOfficialFromDraft(List<ProductSkuDraft> list) throws Exception;

    /**
     * 批量保存（数据来源正式表）
     *
     * @return
     * @throws SQLException
     */
    int batchInsertDraftFromOfficial(List<ProductSku> list) throws SQLException;

    List<ProductSkuDraft> selectSomeSKUSByUser(ProductSkuDraft record) throws SQLException;

    int selectSomeSKUSCountsByUser(ProductSkuDraft record) throws SQLException;

    boolean checkSkuPriceAndWeight(List<Long> productIds) throws SQLException;

    int batchUpdatePriceOnlyOfficialFromDraft(List<ProductSkuDraft> list) throws SQLException;

    List<ProductSkuDraft> findSameSkuBarCodeProductSku(Map<String, Object> map) throws SQLException;
}