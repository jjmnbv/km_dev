package com.kmzyc.supplier.dao;

import com.km.framework.page.Pagination;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;

import java.sql.SQLException;

/**
 * 功能：活动DAO
 *
 * @author Zhoujiwei
 * @since 2016/3/18 14:41
 */
public interface ActivityDAO {

    /**
     * 获取当前活动是否已经活动终止
     *
     * @param activityId    活动id
     * @throws SQLException
     */
    boolean isStop(Long activityId) throws SQLException;

    /**
     * 分页查询活动列表
     *
     * @param page      查询参数
     * @param listSql   列表查询sql
     * @param countSql  统计查询sql
     * @throws SQLException
     */
    void getActivityList(Pagination page, String listSql, String countSql) throws SQLException;

    /**
     * 获取活动信息
     *
     * @param activityId    活动id
     * @return
     * @throws SQLException
     */
    ActivityInfo getActivityById(Long activityId) throws SQLException;
    
    /**
     * 查询充值流水
     * @param accountTransactionId
     * @return
     * @throws Exception
     */
    String bnesAcctTransactionById(Integer accountTransactionId) throws SQLException;

}