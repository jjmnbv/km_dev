package com.pltfm.app.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctEnchashment;

public interface BnesAcctEnchashmentService {
    /**
     * 分页查询余额取现
     * 
     * @param bnesAcctEnchashment
     * @return
     * @throws SQLException
     */
    Page queryBnesAcctEnchashmentListByPage(BnesAcctEnchashment bnesAcctEnchashment, Page page)
            throws Exception;

    /**
     * 查询取现信息详情
     * 
     * @param record
     * @return
     * @throws Exception
     */
    BnesAcctEnchashment selectByPrimaryKeyEnchashment(int BnesAcctEnchashmentId) throws Exception;

    /**
     * 关联供应商查询取现信息
     * 
     * @param record
     * @return
     * @throws Exception
     */
    BnesAcctEnchashment selectByPrimaryKey(int bnesAcctEnchashmentId) throws Exception;

    /**
     * 关联微商查询取现信息
     * 
     * @param record
     * @return
     * @throws Exception
     */
    BnesAcctEnchashment selectByPrimaryKeyVS(int bnesAcctEnchashmentId) throws Exception;


    /**
     * 修改提现信息
     * 
     * @param record
     * @return
     * @throws Exception
     */
    int updateByPrimaryKeySelective(BnesAcctEnchashment record) throws Exception;

    /**
     * 执行提现操作先关处理
     * 
     * @param record
     * @return
     */
    void executeEnchashment(BnesAcctEnchashment record, AccountInfo accountInfo) throws Exception;

    /**
     * 拒绝提现操作,回滚冻结资金等信息
     * 
     * @param record
     * @param accountInfo
     * @throws Exception
     */
    void rejectEnchashment(BnesAcctEnchashment record, AccountInfo accountInfo) throws Exception;

    /**
     * 获取查询到的提现记录的总金额
     * 
     * @param record
     * @return
     * @throws Exception
     */
    BigDecimal queryEnchashmentTotalAmount(BnesAcctEnchashment record) throws Exception;

    /**
     * 获取查询到的提现记录的总金额（审核状态固定为审核通过和提现完成）
     * 
     * @param record
     * @return
     * @throws Exception
     */
    BigDecimal queryEnchashmentTotalAmountForFinish(BnesAcctEnchashment record) throws Exception;


    /**
     * 根据登录ID判断是否存在未审核完成的余额提现记录数
     * 
     * @param record
     * @return
     * @throws Exception
     */
    boolean checkIsHaveUnfinashedEnchashment(BnesAcctEnchashment record) throws Exception;


    /**
     * 查询导出康美云店余额提现的数据
     * 
     * @param bnesAcctEnchashment
     * @return
     */
    public List<BnesAcctEnchashment> queryExportDatas(BnesAcctEnchashment bnesAcctEnchashment)
            throws ServiceException;
}
