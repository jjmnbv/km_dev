package com.kmzyc.supplier.dao;

import com.pltfm.app.vobject.ActivitySupplierEntry;
import com.pltfm.app.vobject.ActivitySupplierEntryExample;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/18 14:41
 */
public interface ActivitySupplierDAO {

    /**
     * 根据参数查询供应商报名数据
     *
     * @param example 查询参数
     * @return
     */
    ActivitySupplierEntry selectByExample(ActivitySupplierEntryExample example) throws SQLException;

    /**
     * 修改商家报名数据
     *
     * @param activitySupplierEntry
     * @return
     * @throws SQLException
     */
    int updateByPrimaryKeySelective(ActivitySupplierEntry activitySupplierEntry) throws SQLException;

    /**
     * 获取商家报名时活动商家类型
     *
     * @param paramMap  supplierId商家id，activityId活动id
     * @return
     * @throws SQLException
     */
    Integer getActivitySupplierType(Map<String, Object> paramMap) throws SQLException;

    /**
     * 获取商家报名id
     *
     * @param paramMap  supplierId商家id，activityId活动id
     * @return
     * @throws SQLException
     */
    Long getSupplierEntryId(Map<String, Object> paramMap) throws SQLException;

    /**
     * 获取商家报名活动总费用
     *
     * @param paramMap  supplierId商家id，activityId活动id
     * @return
     * @throws SQLException
     */
    BigDecimal getActivityTotalPrice(Map<String, Object> paramMap) throws SQLException;

    /**
     * 商家报名时报名信息
     *
     * @param supplierEntry  商家报名信息
     * @return
     * @throws SQLException
     */
    Long saveSupplierEntry(ActivitySupplierEntry supplierEntry) throws SQLException;

    /**
     * 修改商家报名情况
     *
     * @param supplierEntry  商家报名信息
     * @return
     * @throws SQLException
     */
    void updateSupplierEntry(ActivitySupplierEntry supplierEntry) throws SQLException;

    /**
     * 获取活动商家报名状态的具体情况
     *
     * @param paramMap  supplierId商家id，activityId活动id
     * @return
     * @throws SQLException
     */
    Map<String, Long> getActivitySupplierEntryStatus(Map<String, Object> paramMap) throws SQLException;

    /**
     * 追加推广修改总费用
     *
     * @param supplierEntry 参数
     * @return
     * @throws SQLException
     */
    int appendPrice(ActivitySupplierEntry supplierEntry) throws SQLException;

    /**
     * 获取商家店铺评分
     *
     * @param supplier  供应商id
     * @return
     * @throws SQLException
     */
    Map<String, BigDecimal> getShopScore(Long supplier) throws SQLException;
}
