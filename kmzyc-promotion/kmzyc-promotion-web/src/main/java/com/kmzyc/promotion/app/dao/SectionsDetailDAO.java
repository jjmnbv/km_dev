package com.kmzyc.promotion.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.ProductHotSellInfoCache;
import com.kmzyc.promotion.app.vobject.SectionsDetail;
import com.kmzyc.promotion.app.vobject.SectionsDetailExample;

@SuppressWarnings("unchecked")
public interface SectionsDetailDAO {

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table SECTIONS_DETAIL
     * 
     * @ibatorgenerated Tue Jul 30 10:57:37 CST 2013
     */
    int countByExample(SectionsDetailExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table SECTIONS_DETAIL
     * 
     * @ibatorgenerated Tue Jul 30 10:57:37 CST 2013
     */
    int deleteByExample(SectionsDetailExample example) throws SQLException;

    public int deleteBySectionsId(Long sectionsId) throws SQLException;

    public int deleteByPrimaryKey(Long sectionsDetailId) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table SECTIONS_DETAIL
     * 
     * @ibatorgenerated Tue Jul 30 10:57:37 CST 2013
     */
    void insert(SectionsDetail record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table SECTIONS_DETAIL
     * 
     * @ibatorgenerated Tue Jul 30 10:57:37 CST 2013
     */
    void insertSelective(SectionsDetail record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table SECTIONS_DETAIL
     * 
     * @ibatorgenerated Tue Jul 30 10:57:37 CST 2013
     */
    List selectByExample(SectionsDetailExample example, int skip, int max) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table SECTIONS_DETAIL
     * 
     * @ibatorgenerated Tue Jul 30 10:57:37 CST 2013
     */
    int updateByExampleSelective(SectionsDetail record, SectionsDetailExample example)
            throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table SECTIONS_DETAIL
     * 
     * @ibatorgenerated Tue Jul 30 10:57:37 CST 2013
     */
    int updateByExample(SectionsDetail record, SectionsDetailExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table SECTIONS_DETAIL
     * 
     * @ibatorgenerated Tue Jul 30 10:57:37 CST 2013
     */
    int updateByPrimaryKeySelective(SectionsDetail record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table SECTIONS_DETAIL
     * 
     * @ibatorgenerated Tue Jul 30 10:57:37 CST 2013
     */
    int updateByPrimaryKey(SectionsDetail record) throws SQLException;

    /**
     * 获取栏目详情列表信息列表
     * 
     * @param page 分页对象
     * @param sections 栏目信息实体
     * @return 分页栏目列表
     * @throws SQLException SQL异常
     */
    public Page selectDetailPageByVo(Page page, SectionsDetail vo) throws SQLException;

    public SectionsDetail selectByPrimaryKey(Long sectionsDetailId) throws SQLException;

    public List selectByExample(SectionsDetail detail, int skip, int max) throws SQLException;

    public int batchInsertSectionsDetail(List<SectionsDetail> sectionsDetailList)
            throws SQLException;

    public int batchDeleteSectionsDetail(List<Long> sectionsDetailIds) throws SQLException;

    public int batchUpdateSectionsDetail(List<SectionsDetail> sectionsDetailList)
            throws SQLException;

    /**
     * 获取热销栏目的商品(中药材)
     * 
     * @return
     * @throws SQLException
     */
    public List<ProductHotSellInfoCache> findHotSellZYCProducts() throws SQLException;

    /**
     * 获取热销栏目的商品
     * 
     * @return
     * @throws SQLException
     */
    public List<ProductHotSellInfoCache> findHotSellB2BProducts() throws SQLException;

    /**
     * 根据产品ID查询栏目明细列表
     * 
     * @param productId
     * @return
     * @throws SQLException
     */
    public List<SectionsDetail> selectSectionsDetailByProductId(Long productId) throws SQLException;

}
