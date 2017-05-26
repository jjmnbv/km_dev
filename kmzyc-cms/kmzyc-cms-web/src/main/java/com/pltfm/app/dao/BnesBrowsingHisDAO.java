package com.pltfm.app.dao;

import com.pltfm.app.vobject.BnesBrowsingHis;
import com.pltfm.app.vobject.BnesBrowsingHisExample;
import com.pltfm.app.vobject.KeyWords;
import com.pltfm.shiro.vobject.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface BnesBrowsingHisDAO {
    /**
     * 根据店铺Id查询供应商用户名
     *
     * @param shopId 店铺Id
     */
    String selectBySupplierName(String shopId) throws SQLException;

    /**
     * 获取敏感词
     */
    List<KeyWords> findKeyWords() throws SQLException;

    /**
     * 根据SKUCode查询总点击量达到10000
     */
    String count(String skuCode) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds
     * to the database table BNES_BROWSING_HIS
     *
     * @abatorgenerated Thu Oct 24 16:39:19 CST 2013
     */
    void insert(BnesBrowsingHis record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds
     * to the database table BNES_BROWSING_HIS
     *
     * @abatorgenerated Thu Oct 24 16:39:19 CST 2013
     */
    int updateByPrimaryKey(BnesBrowsingHis record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds
     * to the database table BNES_BROWSING_HIS
     *
     * @abatorgenerated Thu Oct 24 16:39:19 CST 2013
     */
    int updateByPrimaryKeySelective(BnesBrowsingHis record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds
     * to the database table BNES_BROWSING_HIS
     *
     * @abatorgenerated Thu Oct 24 16:39:19 CST 2013
     */
    List selectByExample(BnesBrowsingHisExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds
     * to the database table BNES_BROWSING_HIS
     *
     * @abatorgenerated Thu Oct 24 16:39:19 CST 2013
     */
    BnesBrowsingHis selectByPrimaryKey(BigDecimal browsingHisId) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds
     * to the database table BNES_BROWSING_HIS
     *
     * @abatorgenerated Thu Oct 24 16:39:19 CST 2013
     */
    int deleteByExample(BnesBrowsingHisExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds
     * to the database table BNES_BROWSING_HIS
     *
     * @abatorgenerated Thu Oct 24 16:39:19 CST 2013
     */
    int deleteByPrimaryKey(BigDecimal browsingHisId) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds
     * to the database table BNES_BROWSING_HIS
     *
     * @abatorgenerated Thu Oct 24 16:39:19 CST 2013
     */
    int countByExample(BnesBrowsingHisExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds
     * to the database table BNES_BROWSING_HIS
     *
     * @abatorgenerated Thu Oct 24 16:39:19 CST 2013
     */
    int updateByExampleSelective(BnesBrowsingHis record, BnesBrowsingHisExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds
     * to the database table BNES_BROWSING_HIS
     *
     * @abatorgenerated Thu Oct 24 16:39:19 CST 2013
     */
    int updateByExample(BnesBrowsingHis record, BnesBrowsingHisExample example) throws SQLException;

    /**
     * 查询供应商账户和密码是否一致
     */
    public User queryUser(User user) throws SQLException;
}