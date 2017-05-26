package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.kmzyc.supplier.model.User;
import com.kmzyc.supplier.model.example.MerchantInfoExample;
import com.pltfm.app.vobject.SupplierForExport;
import com.kmzyc.commons.page.Page;

public interface SupplierAuditDAO {

    int countByExample(MerchantInfoOrSuppliers example) throws SQLException;

    int deleteByExample(MerchantInfo example) throws SQLException;

    int deleteByPrimaryKey(Long merchantId) throws SQLException;

    void insert(MerchantInfo record) throws SQLException;

    Long insertSelective(MerchantInfo record) throws SQLException;

    List selectByExample(MerchantInfoOrSuppliers record, Page page) throws SQLException;

    MerchantInfo selectByPrimaryKey(Long merchantId) throws SQLException;

    int updateByExampleSelective(MerchantInfo record, MerchantInfoExample example) throws SQLException;

    int updateByExample(MerchantInfo record, MerchantInfoExample example) throws SQLException;

    int updateByPrimaryKeySelective(MerchantInfo record) throws SQLException;

    int updateByPrimaryKey(MerchantInfo record) throws SQLException;

    User selectUserByUserName(String userName) throws SQLException;

    MerchantInfo selectByUserLoginId(MerchantInfo merchant) throws SQLException;

    List<MerchantInfoOrSuppliers> selectPageSupplierIdAndMerchantName(MerchantInfoOrSuppliers record) throws SQLException;

    Integer selectCountSupplierIdAndMerchantName(MerchantInfoOrSuppliers record) throws SQLException;

    /**
     * 查询供应商和商户的信息产品选择供应商用
     *
     * @return
     * @throws SQLException
     */
    List<MerchantInfoOrSuppliers> getSupplierByCloseStatus(MerchantInfoOrSuppliers record) throws SQLException;

    /**
     * 根据公司名称查询
     *
     * @return
     */
    List<MerchantInfo> selectByCompanyName1(MerchantInfo merchant) throws SQLException;

    List<SupplierForExport> getSupplierListForExcel(MerchantInfoOrSuppliers record) throws SQLException;

    /**
     * 查询商户分页信息列表
     *
     * @param mer 商户信息
     * @return List<Category> 商户信息列表
     * @throws Exception 异常
     */
    List<MerchantInfoOrSuppliers> queryMerList(MerchantInfoOrSuppliers mer, Page page) throws SQLException;

    /**
     * 查询商户分页信息总条数
     *
     * @param mer 商户信息
     * @return
     * @throws SQLException
     */
    int countItemMer(MerchantInfoOrSuppliers mer) throws SQLException;

    /**
     * 查询商户分页信息列表，用于活动中心模块待邀请的商家信息
     *
     * @param mer 商户信息
     * @return List<Category> 商户信息列表
     * @throws Exception 异常
     */
    List<MerchantInfoOrSuppliers> querySuppliersForInviteList(MerchantInfoOrSuppliers mer, Long activityId,
                                                              Page page) throws SQLException;

    /**
     * 查询商户分页信息总条数，用于活动中心模块待邀请的商家信息
     *
     * @param mer 商户信息
     * @return
     * @throws SQLException
     */
    int countSuppliersForInvite(MerchantInfoOrSuppliers mer, Long activityId) throws SQLException;

}