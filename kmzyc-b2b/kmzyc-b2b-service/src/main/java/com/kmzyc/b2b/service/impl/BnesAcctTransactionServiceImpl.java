package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.BnesAcctTransactionDAO;
import com.kmzyc.b2b.model.BnesAcctTransaction;
import com.kmzyc.b2b.service.BnesAcctTransactionService;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.page.Pagination;

@Service
public class BnesAcctTransactionServiceImpl implements BnesAcctTransactionService {
    @Resource(name = "bnesAcctTransactionDAOImpl")
    private BnesAcctTransactionDAO bnesAcctTransactionDAO;

    public List<BnesAcctTransaction> getBnesAcctTransactionById(long id) throws ServiceException {

        try {
            return this.bnesAcctTransactionDAO.getBnesAcctTransactionById(id);
        } catch (SQLException e) {
            throw new ServiceException(0, "获取充值单信息失败", e);
        }
    }

    /**
     * 分页查询账户余额明细信息
     * 
     * @param page
     * @return
     * @throws SQLException
     */
    public Pagination findAcctBalanceByUserId(Pagination page) throws SQLException {
        // 设置要连接的数据源为客户系统

        page =
                bnesAcctTransactionDAO.findByPage(
                        "BnesAcctTransaction.searchBnesAcctTransPageByUserId",
                        "BnesAcctTransaction.searchCountBnesAcctTransPageByUserId", page);
        return page;
    }

    @Override
    public List<BnesAcctTransaction> queryTransaction(Map<String, String> map)
            throws ServiceException {

        try {
            return this.bnesAcctTransactionDAO.queryTransaction(map);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询账户充值单错误", e);
        }
    }

    @Override
    public BnesAcctTransaction queryTransationExist(Map<String, String> map)
            throws ServiceException {

        try {
            return this.bnesAcctTransactionDAO.queryTransationExist(map);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询用户充值单错误", e);
        }
    }

    @Override
    public BnesAcctTransaction findByAccountNumber(String orderCode) throws ServiceException {

        try {
            return this.bnesAcctTransactionDAO.findByAccountNumber(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询充值单错误", e);
        }
    }

    public boolean isFirstRecharge(int accountId) throws ServiceException {

        try {
            int count = this.bnesAcctTransactionDAO.isFirstRecharge(accountId);
            return count <= 0;
        } catch (SQLException e) {
            throw new ServiceException(0, "查询充值流水错误", e);
        }
    }
}
