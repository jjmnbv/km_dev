package com.kmzyc.b2b.service.member.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.member.AccountDao;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.OrderPayStatement;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.member.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    // private static Logger logger = Logger.getLogger(AccountServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource(name = "accountDaoImpl")
    private AccountDao accountDao;

    /**
     * 根据用户id查询对应的账户信息
     * 
     * @param userId
     * @return
     * @throws SQLException
     */
    @Override
    public AccountInfo findAccountByUserId(long userId) throws SQLException {
        logger.info("查询用户对应的账户信息,userId:" + userId);
        // 使用客户数据源

        return (AccountInfo) accountDao.findById("AccountInfo.findByUserId", userId);
    }

    /**
     * 计算一段时间内已支付客户消费的总金额
     * 
     * @param userId
     * @param beginDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    @Override
    public BigDecimal countConsumptionSum(long userId, Date beginDate, Date endDate)
            throws SQLException {
        logger.info("计算一段时间内客户消费的总金额,userId:" + userId + ";beginDate:" + beginDate + ";endDate:"
                + endDate);
        // 使用订单数据源

        // 总支付金额
        BigDecimal amountPayableSum =
                accountDao.countAmountPayable(userId, beginDate, endDate);
        // 总退货返回金额
        BigDecimal returnSum =
                accountDao.countReturnAmount(userId, beginDate, endDate);
        // 总消费金额
        BigDecimal consumptionSum = amountPayableSum.subtract(returnSum);
        return consumptionSum;
    }

    /**
     * 计算一段时间内已完成客户消费的总金额
     * 
     * @param userId
     * @param beginDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    @Override
    public BigDecimal countCompleteOrder(long userId, Date beginDate, Date endDate)
            throws SQLException {
        logger.info("计算一段时间内客户已完成订单的总金额,userId:" + userId + ";beginDate:" + beginDate + ";endDate:"
                + endDate);
        // 使用订单数据源

        // 总支付金额
        BigDecimal amountPayableSum =
                accountDao.countAmountCompleteable(userId, beginDate, endDate);
        // 总退货返回金额
        BigDecimal returnSum =
                accountDao.countReturnAmount(userId, beginDate, endDate);
        // 总消费金额
        BigDecimal consumptionSum = amountPayableSum.subtract(returnSum);
        logger.info("用户" + userId + "退换货金额为：" + returnSum);
        return consumptionSum;
    }

    /**
     * 查询用户的消费明细
     * 
     * @param page
     * @return
     * @throws SQLException
     */
    @Override
    @SuppressWarnings("unchecked")
    public Pagination findConsumptionDetailByPage(Pagination page) throws SQLException {
        Map<String, Object> conditon = (Map<String, Object>) page.getObjCondition();
        logger.info("开始查询用户的消费明细,userId:" + conditon.get("userId") + ",startDate:"
                + conditon.get("startDate") + ",endDate:" + conditon.get("endDate"));
        // 使用订单数据源

        return accountDao.findByPage("OrderMain.findConsumptionDetail",
                "OrderMain.countConsumptionDetailByPage", page);
    }

    /**
     * 查询用户的消费明细
     * 
     * @param paramsMap
     * @return
     * @throws SQLException
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<OrderPayStatement> findConsumptionDetailByOrderCode(Map<String, Object> paramsMap)
            throws SQLException {
        // logger.info("根据订单号开始查询用户的消费明细:");
        // 使用订单数据源

        return accountDao.findByProperty("OrderMain.findConsumptionDetailByOrderCode", paramsMap);
    }

    /**
     * 个人中心我的信息接口整合查询
     */
    @Override
    public User findHomeLoadById(Map<String, Object> paramMap) throws SQLException {
        return (User) accountDao.findById("AccountInfo.findHomeLoadById", paramMap);
    }

}
