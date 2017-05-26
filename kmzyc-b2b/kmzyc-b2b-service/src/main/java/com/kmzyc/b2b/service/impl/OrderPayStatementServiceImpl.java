package com.kmzyc.b2b.service.impl;

import com.kmzyc.b2b.dao.OrderPayStatementDAO;
import com.kmzyc.b2b.model.OrderPayStatement;
import com.kmzyc.b2b.service.OrderPayStatementService;
import com.kmzyc.framework.exception.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Service
@SuppressWarnings("unchecked")
public class OrderPayStatementServiceImpl implements OrderPayStatementService {
    // private static Logger log = LoggerFactory.getLogger(OrderPayStatementServiceImpl.class);
    private static Logger log = LoggerFactory.getLogger(OrderPayStatementServiceImpl.class);
    @Resource(name = "orderPayStatementDAOImpl")
    private OrderPayStatementDAO orderPayStatementDAO;

    @Override
    public List<OrderPayStatement> getByOrderCode(Map<String, String> map) throws ServiceException {

        try {
            return orderPayStatementDAO.queryByOrderCode(map);
        } catch (SQLException e) {
            throw new ServiceException(0, "根据订单号：" + map + "查询订单优惠券信息失败", e);
        }
    }

    @Override
    public OrderPayStatement findrefundOrderPayStatement(String orderCode) throws ServiceException {

        try {
            return orderPayStatementDAO.findrefundOrderPayStatement(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(0, "根据订单号：" + orderCode + "查询订单支付信息失败", e);
        }
    }

    /**
     * 根据第三方流水号查询订单信息
     * 
     * @param batchNo
     * @return
     * @throws SQLException
     */
    public Map findOrderInfoByOuterStatementNo(String batchNo) throws ServiceException {

        try {
            return orderPayStatementDAO.findOrderInfoByOuterStatementNo(batchNo);
        } catch (SQLException e) {
            throw new ServiceException(0, "根据第三方流水号" + batchNo + "查询订单信息失败", e);
        }
    }

    @Override
    public void insertOrUpdateOrderPayStatement(String orderCode, String platformCode)
            throws ServiceException {

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            log.info("==订单支付回调:insertOrUpdateOrderPayStatement方法开始==" + df.format(new Date()));
            System.out.println("订单支付回调:insertOrUpdateOrderPayStatement方法开始=="
                    + df.format(new Date()));
            List<OrderPayStatement> opsList;
            OrderPayStatement ops;
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("orderCode", orderCode);
            paramMap.put("paymentWay", "4");
            log.info("==订单支付回调:queryByOrderCode开始==" + df.format(new Date()));
            System.out.println("==订单支付回调:queryByOrderCode开始==" + df.format(new Date()));
            opsList = orderPayStatementDAO.queryByOrderCode(paramMap);// 查询第三方支付流水
            log.info("==订单支付回调:queryByOrderCode结束==" + df.format(new Date()));
            System.out.println("==订单支付回调:queryByOrderCode结束==" + df.format(new Date()));
            if (null != opsList && opsList.size() == 1) {// 第三方支付流水应该只有一条，且为准备支付流水
                ops = opsList.get(0);// 第三方准备支付流水应该只有一条,
                if (null != ops && !ops.getPlatformCode().equals(platformCode)
                        && ops.getState().intValue() == 4) {// 如果交易流水中记录的支付平台代码，与实际回调的支付平台代码不一致，则需要修改
                    // 1.修改准备支付流水的支付平台代码
                    ops.setPlatformCode(platformCode);
                    String platformName;
                    switch (platformCode) {
                        case "1":
                            platformName = "易宝";
                            break;
                        case "2":
                            platformName = "快汇宝";
                            break;
                        case "3":
                            platformName = "支付宝";
                            break;
                        case "4":
                            platformName = "财付通";
                            break;
                        case "5":
                            platformName = "微信";
                            break;
                        case "6":
                            platformName = "时代";
                            break;
                        case "7":
                            platformName = "康美通";
                            break;
                        default:
                            platformName = "";

                    }
                    ops.setPlatformName(platformName);
                    // --更新
                    // 2.新增一条支付完成的支付流水
                    log.info("==订单支付回调:updateByPrimaryKey开始==" + df.format(new Date()));
                    System.out.println("==订单支付回调:updateByPrimaryKey开始==" + df.format(new Date()));
                    orderPayStatementDAO.updateByPrimaryKey(ops);
                    log.info("==订单支付回调:updateByPrimaryKey结束==" + df.format(new Date()));
                    System.out.println("==订单支付回调:updateByPrimaryKey结束==" + df.format(new Date()));
                    // ops.setState(1L);
                    // --新增 (不需要在这里添加，回调会调用远程orderRemotePay()方法,里面会加入支付完成流水)
                    // orderPayStatementDAO.insert(ops);
                }
            }
            log.info("==订单支付回调:insertOrUpdateOrderPayStatement方法结束==" + df.format(new Date()));
            System.out.println("订单支付回调:insertOrUpdateOrderPayStatement方法结束=="
                    + df.format(new Date()));
        } catch (SQLException e) {
            throw new ServiceException(0, "orderCode=" + orderCode + ";platformCode="
                    + platformCode + "更新和插入支付流水失败", e);
        }


    }

    @Override
    public OrderPayStatement findrefundOrderPayStatementForYs(Map<String, String> map)
            throws ServiceException {

        try {
            return orderPayStatementDAO.findrefundOrderPayStatementForYs(map);
        } catch (SQLException e) {
            throw new ServiceException(0, "根据订单号：" + map.get("orderCode") + "查询订单支付信息失败", e);
        }
    }
}
