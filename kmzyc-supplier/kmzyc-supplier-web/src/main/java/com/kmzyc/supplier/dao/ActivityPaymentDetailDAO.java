package com.kmzyc.supplier.dao;

import com.pltfm.app.vobject.ActivityPaymentDetail;
import com.pltfm.app.vobject.ActivityPaymentDetailExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 功能：付款DAO
 *
 * @author Zhoujiwei
 * @since 2016/3/28 14:06
 */
public interface ActivityPaymentDetailDAO {

    /**
     * 新增首次付款明细
     *
     * @param activityPaymentDetail
     * @return
     * @throws SQLException
     */
    Long insertSelective(ActivityPaymentDetail activityPaymentDetail) throws SQLException;

    /**
     * 批量新增首次付款明细
     *
     * @param list
     * @return
     * @throws SQLException
     */
    void batchInsertSelective(List<ActivityPaymentDetail> list) throws SQLException;

    /**
     * 根据付款id获取付款详细
     *
     * @param example 付款id
     * @return
     * @throws SQLException
     */
    List<ActivityPaymentDetail> getPaymentDetails(ActivityPaymentDetailExample example) throws SQLException;
}
