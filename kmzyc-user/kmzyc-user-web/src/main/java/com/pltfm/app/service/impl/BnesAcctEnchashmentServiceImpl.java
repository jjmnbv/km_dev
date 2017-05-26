package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.BnesAcctEnchashmentDAO;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.BnesAcctEnchashmentService;
import com.pltfm.app.service.BnesAcctTransListService;
import com.pltfm.app.service.BnesAcctTransactionService;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctEnchashment;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.sys.util.ErrorCode;

@Service(value = "bnesAcctEnchashmentService")
public class BnesAcctEnchashmentServiceImpl implements BnesAcctEnchashmentService {

    @Resource(name = "bnesAcctEnchashmentDAO")
    private BnesAcctEnchashmentDAO bnesAcctEnchashmentDAO;

    @Resource(name = "accountInfoService")
    private AccountInfoService accountInfoService;

    @Resource(name = "bnesAcctTransactionService")
    private BnesAcctTransactionService bnesAcctTransactionService;

    /** 余额变化记录 */
    @Resource(name = "bnesAcctTransListService")
    private BnesAcctTransListService bnesAcctTransListService;

    // 条件分页查询提现记录
    public Page queryBnesAcctEnchashmentListByPage(BnesAcctEnchashment bnesAcctEnchashment,
            Page page) throws Exception {
        // 查询取现记录数
        int count = bnesAcctEnchashmentDAO.queryBnesAcctEnchashmentListByCount(bnesAcctEnchashment);
        // page赋值
        page.setRecordCount(count);
        int max = page.getStartIndex() + page.getPageSize();
        bnesAcctEnchashment.setMinNum(page.getStartIndex());
        bnesAcctEnchashment.setMaxNum(max);
        page.setDataList(bnesAcctEnchashmentDAO
                .queryBnesAcctEnchashmentListByPage(bnesAcctEnchashment));
        return page;
    }

    // 查询取现信息详情
    @Override
    public BnesAcctEnchashment selectByPrimaryKeyEnchashment(int BnesAcctEnchashmentId)
            throws Exception {
        return bnesAcctEnchashmentDAO.selectByPrimaryKeyEnchashment(BnesAcctEnchashmentId);
    }

    // 关联供应商查询取现信息
    @Override
    public BnesAcctEnchashment selectByPrimaryKey(int bnesAcctEnchashmentId) throws Exception {
        return bnesAcctEnchashmentDAO.selectByPrimaryKey(bnesAcctEnchashmentId);
    }

    // 关联微商查询取现信息
    @Override
    public BnesAcctEnchashment selectByPrimaryKeyVS(int bnesAcctEnchashmentId) throws Exception {
        return bnesAcctEnchashmentDAO.selectByPrimaryKeyVS(bnesAcctEnchashmentId);
    }

    // 修改提现信息
    @Override
    public int updateByPrimaryKeySelective(BnesAcctEnchashment record) throws Exception {
        return bnesAcctEnchashmentDAO.updateByPrimaryKeySelective(record);
    }

    // 提现操作
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void executeEnchashment(BnesAcctEnchashment record, AccountInfo accountInfo)
            throws Exception {

        // 获取账户可用余额
        BigDecimal amountAvlibal = accountInfo.getAmountAvlibal();
        // 审核通过时获取提现后余额(扣除冻结金额)
        BigDecimal afterFroen =
                accountInfo.getAmountFrozen().subtract(record.getEnchashmentAmount());
        // 修改账户冻结金额
        accountInfo.setAmountFrozen(afterFroen);
        accountInfo.setN_AccountAmount(accountInfo.getN_AccountAmount().subtract(
                record.getEnchashmentAmount()));
        // 交易流水
        String accountTransactionNo = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        BnesAcctTransactionQuery transactionVO = new BnesAcctTransactionQuery();
        transactionVO.setAccountId(accountInfo.getN_AccountId());
        transactionVO.setType(Constants.TRANSACTION_TYPE_ENCHASHMENT);
        transactionVO.setContent(accountInfo.getAccountLogin() + "商家取现:￥"
                + record.getEnchashmentAmount());
        transactionVO.setAmount(record.getEnchashmentAmount().negate());
        transactionVO.setStatus(Constants.TRANSACTIONSTATUSSUCCESS);
        transactionVO.setAccountNumber(accountTransactionNo);
        transactionVO.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
        transactionVO.setCreatedId(accountInfo.getN_LoginId());
        // 设置交易流水号
        record.setAccountTransactionId(accountTransactionNo);
        // 更新账户金额信息
        accountInfoService.updateAccountInfo(accountInfo);
        // 添加商家提现流水
        Integer transactionId =
                bnesAcctTransactionService.insertBnesAcctTransactionDO(transactionVO);
        // 更新提现记录信息
        this.updateByPrimaryKeySelective(record);

        // 账户交易变化记录
        BnesAcctTransListDO transListDO = new BnesAcctTransListDO();
        transListDO.setAccountId(Integer.valueOf(accountInfo.getN_AccountId()));
        transListDO.setAccountTransactionId(transactionId);
        transListDO.setBeforeAmount(amountAvlibal);
        transListDO.setAfterAmount(amountAvlibal);
        transListDO.setMoneyAmount(transactionVO.getAmount());
        transListDO.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
        transListDO.setCreatedId(transactionVO.getLoginId());
        transListDO.setModifieId(transactionVO.getLoginId());
        transListDO.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
        // 添加账户交易变化记录
        bnesAcctTransListService.insertBnesAcctTransListDO(transListDO);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void rejectEnchashment(BnesAcctEnchashment record, AccountInfo accountInfo)
            throws Exception {
        Date now = DateTimeUtils.getCalendarInstance().getTime();
        String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(now);
        // 审核拒绝时 解冻账户申请余额 增加可用余额
        BigDecimal before = accountInfo.getAmountAvlibal();
        BigDecimal afterFroen =
                accountInfo.getAmountFrozen().subtract(record.getEnchashmentAmount());
        BigDecimal after = accountInfo.getAmountAvlibal().add(record.getEnchashmentAmount());
        // 修改账户余额
        accountInfo.setAmountFrozen(afterFroen);
        accountInfo.setAmountAvlibal(after);
        accountInfoService.updateAccountInfo(accountInfo);
        // 添加解冻流水
        BnesAcctTransactionQuery bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
        bnesAcctTransactionQuery.setAccountId(accountInfo.getN_AccountId());
        bnesAcctTransactionQuery.setType(Constants.TRANSACTION_TYPE_THAW);
        bnesAcctTransactionQuery.setContent(accountInfo.getAccountLogin() + "余额解冻:￥"
                + record.getEnchashmentAmount().doubleValue());
        bnesAcctTransactionQuery.setAmount(record.getEnchashmentAmount());
        bnesAcctTransactionQuery.setStatus(Constants.TRANSACTIONSTATUSSUCCESS);
        bnesAcctTransactionQuery.setAccountNumber(number);
        bnesAcctTransactionQuery.setCreateDate(now);
        bnesAcctTransactionQuery.setCreatedId(accountInfo.getN_LoginId());
        // 插入账户交易流水
        Integer tid =
                bnesAcctTransactionService.insertBnesAcctTransactionDO(bnesAcctTransactionQuery);

        // 更新提现记录信息
        record.setAccountTransactionId(bnesAcctTransactionQuery.getAccountNumber());
        this.updateByPrimaryKeySelective(record);

        // 添加余额交易变化记录
        BnesAcctTransListDO bnesAcctTransListDO = new BnesAcctTransListDO();
        bnesAcctTransListDO.setAccountId(accountInfo.getN_AccountId());
        bnesAcctTransListDO.setAccountTransactionId(tid);
        bnesAcctTransListDO.setBeforeAmount(before);
        bnesAcctTransListDO.setAfterAmount(after);
        bnesAcctTransListDO.setMoneyAmount(bnesAcctTransactionQuery.getAmount());
        bnesAcctTransListDO.setCreateDate(now);
        bnesAcctTransListDO.setCreatedId(bnesAcctTransactionQuery.getLoginId());
        bnesAcctTransListDO.setModifieId(bnesAcctTransactionQuery.getLoginId());
        bnesAcctTransListDO.setModifyDate(now);
        bnesAcctTransListService.insertBnesAcctTransListDO(bnesAcctTransListDO);
    }


    @Override
    public BigDecimal queryEnchashmentTotalAmount(BnesAcctEnchashment record) throws Exception {
        BigDecimal totalAmount = bnesAcctEnchashmentDAO.queryEnchashmentTotalAmount(record);
        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }
        return totalAmount;
    }

    @Override
    public BigDecimal queryEnchashmentTotalAmountForFinish(BnesAcctEnchashment record)
            throws Exception {
        BigDecimal totalAmount =
                bnesAcctEnchashmentDAO.queryEnchashmentTotalAmountForFinish(record);
        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }
        return totalAmount;
    }

    @Override
    public boolean checkIsHaveUnfinashedEnchashment(BnesAcctEnchashment record) throws Exception {
        int count = bnesAcctEnchashmentDAO.queryUnfinashedEnchashmentCount(record);
        // 如果未审核完成的提现记录数大于0 则返回true,否则返回false
        return count > 0 ? true : false;
    }

    @Override
    public List<BnesAcctEnchashment> queryExportDatas(BnesAcctEnchashment bnesAcctEnchashment)
            throws ServiceException {
        try {
            return  bnesAcctEnchashmentDAO.queryExportDatas(bnesAcctEnchashment);
        } catch (SQLException e) {
            throw new ServiceException(ErrorCode.DB_ERROR, e.getMessage());
        }
    }


}
