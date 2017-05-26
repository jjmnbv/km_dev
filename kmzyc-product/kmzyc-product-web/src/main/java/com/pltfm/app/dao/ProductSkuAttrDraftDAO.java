package com.pltfm.app.dao;

import com.pltfm.app.vobject.*;

import java.sql.SQLException;
import java.util.List;

public interface ProductSkuAttrDraftDAO {

    int countByExample(ProductSkuAttrDraftExample example) throws SQLException;

    int deleteByExample(ProductSkuAttrDraftExample example) throws SQLException;

    int deleteByPrimaryKey(Long productSkuAttrId) throws SQLException;

    void insert(ProductSkuAttrDraft record) throws SQLException;

    void insertSelective(ProductSkuAttrDraft record) throws SQLException;

    List selectByExample(ProductSkuAttrDraftExample example) throws SQLException;

    ProductSkuAttrDraft selectByPrimaryKey(Long productSkuAttrId) throws SQLException;

    int updateByExampleSelective(ProductSkuAttrDraft record, ProductSkuAttrDraftExample example) throws SQLException;

    int updateByExample(ProductSkuAttrDraft record, ProductSkuAttrDraftExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductSkuAttrDraft record) throws SQLException;

    int updateByPrimaryKey(ProductSkuAttrDraft record) throws SQLException;
    
    int batchInsertOfficial(List<ProductSkuAttrDraft> list) throws SQLException;
    
    List<ProductSkuAttrDraft> findByProductId(Long productId) throws SQLException;
    
    int batchDeleteDraft(List<ProductSkuAttrDraft> list) throws SQLException;
    
    int batchInsertDraftFromOfficial(List<ProductSkuAttr> list) throws SQLException;
    
    /**
	 * 根据产品Id获取相关SKU规格（草稿）
	 * @param productId
	 * @return
	 * @throws Exception
	 */
    List<ProductSkuAttrDraft> findSkuAttrDraftByProductId(Long productId) throws SQLException;
    
    /**
	 * 根据SkuId获取SKU属性（草稿）
	 * @param productSkuId
	 * @return
	 * @throws Exception
	 */
    List<ViewSkuAttr> findSkuDraftInfoByProductSkuId(Long productSkuId) throws SQLException;
    
    /**
     * 查询SKU新加的属性值
     * @param productId
     * @return
     * @throws SQLException
     */
    List<ProductSkuAttrDraft> findSkuNewAttr(Long productId) throws SQLException;

    /**
     * 根据productId删除SKU属性
     *
     * @param productId 产品id
     */
    int deleteProductSkuAttrByProductId(Long productId) throws SQLException;

    /**
     * 批量根据SKU更新SKU状态
     *
     * @param list
     * @throws Exception
     */
    void batchUpdateSkuAttrStatus(List<ProductSkuAttrDraft> list) throws SQLException;
}