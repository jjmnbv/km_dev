package com.pltfm.app.dao;

import com.pltfm.app.vobject.BnesAcctEnchashment;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface BnesAcctEnchashmentDAO {
    /**
     * 分页查询余额取现
     * 
     * @param bnesAcctEnchashment
     * @return
     * @throws SQLException
     */
    List<BnesAcctEnchashment> queryBnesAcctEnchashmentListByPage(
            BnesAcctEnchashment bnesAcctEnchashment) throws SQLException;

    /**
     * 条件查询余额提现记录总数
     * 
     * @param bnesAcctEnchashment
     * @return
     * @throws SQLException
     */
    int queryBnesAcctEnchashmentListByCount(BnesAcctEnchashment bnesAcctEnchashment)
            throws SQLException;

    /**
     * 删除取现信息
     * 
     * @param enchashmentId
     * @return
     * @throws SQLException
     */
    int deleteByPrimaryKey(BigDecimal enchashmentId) throws SQLException;

    /**
     * 添加取现信息
     * 
     * @param record
     * @throws SQLException
     */
    BigDecimal insertSelective(BnesAcctEnchashment record) throws SQLException;

    /**
     * 查询取现信息详情
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    BnesAcctEnchashment selectByPrimaryKeyEnchashment(int BnesAcctEnchashmentId)
            throws SQLException;


    /**
     * 关联供应商查询取现信息
     * 
     * @param enchashmentId
     * @return
     * @throws SQLException
     */
    BnesAcctEnchashment selectByPrimaryKey(int bnesAcctEnchashmentId) throws SQLException;

    /**
     * 关联微商查询取现信息
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    BnesAcctEnchashment selectByPrimaryKeyVS(int bnesAcctEnchashmentId) throws SQLException;

    /**
     * 修改取现信息
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    int updateByPrimaryKeySelective(BnesAcctEnchashment record) throws SQLException;

    /**
     * 查询提现记录的总金额
     * 
     * @param record
     * @return
     * @throws Exception
     */
    BigDecimal queryEnchashmentTotalAmount(BnesAcctEnchashment record) throws Exception;

    /**
     * 查询提现记录的总金额（审核状态固定为审核通过和提现完成）
     * 
     * @param record
     * @return
     * @throws Exception
     */
    BigDecimal queryEnchashmentTotalAmountForFinish(BnesAcctEnchashment record) throws Exception;


    /**
     * 根据登录ID查询未完成的余额提现记录数
     * 
     * @param record 查询参数
     * @return
     * @throws Exception
     */
    int queryUnfinashedEnchashmentCount(BnesAcctEnchashment record) throws Exception;

    /**
     * 查询康美云店余额提现导出数据
     * 
     * @param bnesAcctEnchashment
     * @return
     * @throws SQLException
     */
    public List<BnesAcctEnchashment> queryExportDatas(BnesAcctEnchashment bnesAcctEnchashment)
            throws SQLException;



}
