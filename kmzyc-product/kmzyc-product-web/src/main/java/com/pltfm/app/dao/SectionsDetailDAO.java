package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.ProductHotSellInfoCache;
import com.pltfm.app.vobject.SectionsDetail;
import com.pltfm.app.vobject.SectionsDetailExample;

public interface SectionsDetailDAO {

    int countByExample(SectionsDetailExample example) throws SQLException;

    int deleteByExample(SectionsDetailExample example) throws SQLException;

    int deleteBySectionsId(Long sectionsId) throws SQLException;

    int deleteByPrimaryKey(Long sectionsDetailId) throws SQLException;

    void insert(SectionsDetail record) throws SQLException;

    void insertSelective(SectionsDetail record) throws SQLException;

    List selectByExample(SectionsDetailExample example, int skip, int max) throws SQLException;

    int updateByExampleSelective(SectionsDetail record, SectionsDetailExample example) throws SQLException;

    int updateByExample(SectionsDetail record, SectionsDetailExample example) throws SQLException;

    int updateByPrimaryKeySelective(SectionsDetail record) throws SQLException;

    int updateByPrimaryKey(SectionsDetail record) throws SQLException;

    SectionsDetail selectByPrimaryKey(Long sectionsDetailId) throws SQLException;

    List selectByExample(SectionsDetail detail, int skip, int max) throws SQLException;

    int batchInsertSectionsDetail(List<SectionsDetail> sectionsDetailList) throws SQLException;

    int batchDeleteSectionsDetail(List<Long> sectionsDetailIds) throws SQLException;

    int batchUpdateSectionsDetail(List<SectionsDetail> sectionsDetailList) throws SQLException;

    /**
     * 获取热销栏目的商品(中药材)
     *
     * @return
     * @throws SQLException
     */
    List<ProductHotSellInfoCache> findHotSellZYCProducts() throws SQLException;

    /**
     * 获取热销栏目的商品
     *
     * @return
     * @throws SQLException
     */
    List<ProductHotSellInfoCache> findHotSellB2BProducts() throws SQLException;

    /**
     * 根据产品ID查询栏目明细列表
     *
     * @param productId
     * @return
     * @throws SQLException
     */
    List<SectionsDetail> selectSectionsDetailByProductId(Long productId) throws SQLException;

    List<ProductHotSellInfoCache> findHotSellProducts(String saleType) throws SQLException;

}