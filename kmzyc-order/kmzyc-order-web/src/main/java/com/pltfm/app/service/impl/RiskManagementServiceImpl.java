package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dao.OrderRiskScoreDao;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.entities.OrderRiskCondition;
import com.pltfm.app.entities.OrderRiskScore;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderRiskSettingService;
import com.pltfm.app.service.RiskManagementService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.OrderRiskKey;
import com.pltfm.app.util.OrderRiskManagementUtils;
import com.pltfm.app.util.SysConstants;
import com.pltfm.sys.util.ErrorCode;

import redis.clients.jedis.JedisCluster;


@Service(value = "riskManagementService")
@Scope("singleton")
public class RiskManagementServiceImpl implements RiskManagementService {
    private static final Logger logger = Logger.getLogger(RiskManagementServiceImpl.class);

    @Resource
    private JmsTemplate jmsTemplate;
    @Resource
    private javax.jms.Destination orderRiskManagementQueue;
    @Resource
    private OrderMainDAO orderMainDAO;
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;
    @Resource
    private OrderRiskSettingService orderRiskSettingService;
    @Resource
    private OrderOperateStatementService orderOperateStatementService;
    @Resource
    private OrderRiskScoreDao orderRiskScoreDao;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final BigDecimal HUNDRED = new BigDecimal(100);
    public static final ExecutorService executors = Executors.newFixedThreadPool(50);// 线程池

    /**
     * 新增订单风控判断任务
     * 
     * @param commerceId 商家ID
     * @param orderCode 订单
     * @throws ServiceException
     */
    @Override
    public boolean addRiskJudgeTask(String commerceId, String commerceName, String orderCode)
            throws ServiceException {
        boolean success = false;
        try {
            OrderRiskManagementUtils.send(
                    (null == commerceId ? "" : commerceId) + ","
                            + (null == commerceName ? "" : commerceName) + "," + orderCode,
                    orderRiskManagementQueue, jmsTemplate);
        } catch (Exception e) {
            logger.error("新增风控判断任务，订单" + orderCode, e);
        }
        return success;
    }

    /**
     * 执行订单风控
     * 
     * @param value
     * @throws ServiceException
     */
    @Override
    public void execRiskJudge(final String value) throws ServiceException {
        executors.execute(new Runnable() {
            @Override
            public void run() {
                String[] values = null;
                if (null != value && null != (values = value.split(",")) && values.length > 2) {
                    try {
                        orderRiskScoreDao.emptyOrderRiskScore(values[2]);
                    } catch (Exception e) {
                        logger.error("将订单之前风控判断得分置空发生异常" + e.getMessage(), e);
                    }
                }
                try {
                    coreExecute(value);
                } catch (ServiceException e) {
                    logger.error("执行订单风控发生异常" + e.getMessage(), e);
                }
            }
        });
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    private void coreExecute(String value) throws ServiceException {
        String commerceId = null, commerceName = null, orderCode = null;
        int time = 0;// 次数，重复三次
        try {
            Map<String, OrderRiskCondition> oscMap = null;
            String[] values = null;
            if (null != value && null != (values = value.split(",")) && values.length > 2
                    && null != (oscMap = orderRiskSettingService.queryOrderRiskCondition())
                    && !oscMap.isEmpty()) {
                commerceId = values[0];
                commerceName = values[1];
                orderCode = values[2];
                if (values.length == 4) {
                    time = Integer.parseInt(values[3]);
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("commerceId", commerceId);
                params.put("commerceName", commerceName);
                params.put("orderCode", orderCode);
                Map<String, Object> resultCondition =
                        orderMainDAO.queryOrderRiskResultCondition(params);
                if(null == resultCondition){
                    throw new ServiceException("风控失败:订单"+orderCode+"风控条件为空");
                }
                String payDate = (String) resultCondition.get("payDate");
                if (null != payDate && payDate.length() > 4) {
                    resultCondition.put("payDate", dateFormat.parse(payDate));
                }
                String customerAccount = (String) resultCondition.get("customerAccount");
                String consigneeMobile = (String) resultCondition.get("consigneeMobile");

                List<OrderRiskScore> list = new ArrayList<OrderRiskScore>();
                OrderRiskScore ors = null;
                boolean SUCCESS = false, commerce = false, customer = false, mobile = false;;// 是否通过、商家、帐号、手机
                StringBuffer extValue = null;
                OrderRiskCondition orc = oscMap.get(OrderRiskKey.ORDER_RISK_THRESHOLD);
                if ((commerce =
                        jedisCluster.sismember(OrderRiskKey.ORDER_RISK_WHITE_LIST + '_'
                                + OrderRiskKey.OrderRiskRosterTypeEnum.ROSTER_TYPE_COMMERCE
                                        .getCode(),
                                commerceName))
                        | (customer =
                                jedisCluster.sismember(
                                        OrderRiskKey.ORDER_RISK_WHITE_LIST + '_'
                                                + OrderRiskKey.OrderRiskRosterTypeEnum.ROSTER_TYPE_CUSTOMER_ACCOUNT
                                                        .getCode(),
                                        customerAccount))
                        | (mobile = jedisCluster.sismember(OrderRiskKey.ORDER_RISK_WHITE_LIST + '_'
                                + OrderRiskKey.OrderRiskRosterTypeEnum.ROSTER_TYPE_CONSIGNEE_MOBILE
                                        .getCode(),
                                consigneeMobile))) {
                    // 白名单
                    extValue = new StringBuffer();
                    ors = new OrderRiskScore();
                    ors.setOrderCode(orderCode);
                    if (commerce) {
                        extValue.append("商家").append(commerceName).append("为白名单;");
                    }
                    if (customer) {
                        extValue.append("下单账号").append(customerAccount).append("为白名单;");
                    }
                    if (mobile) {
                        extValue.append("收货手机").append(consigneeMobile).append("为白名单;");
                    }
                    ors.setLabel(extValue.toString());
                    ors.setLatitude(
                            OrderRiskKey.OrderRiskLatitudeEnum.ORDERRISKLATITUDE_ROSTER.getCode());
                    ors.setCheckValue(BigDecimal.ZERO);
                    ors.setScore(0);
                    list.add(ors);
                    SUCCESS = true;
                } else if ((commerce =
                        jedisCluster.sismember(OrderRiskKey.ORDER_RISK_BLACK_LIST + '_'
                                + OrderRiskKey.OrderRiskRosterTypeEnum.ROSTER_TYPE_COMMERCE
                                        .getCode(),
                                commerceName))
                        | (customer =
                                jedisCluster.sismember(
                                        OrderRiskKey.ORDER_RISK_BLACK_LIST + '_'
                                                + OrderRiskKey.OrderRiskRosterTypeEnum.ROSTER_TYPE_CUSTOMER_ACCOUNT
                                                        .getCode(),
                                        customerAccount))
                        | (mobile = jedisCluster.sismember(OrderRiskKey.ORDER_RISK_BLACK_LIST + '_'
                                + OrderRiskKey.OrderRiskRosterTypeEnum.ROSTER_TYPE_CONSIGNEE_MOBILE
                                        .getCode(),
                                consigneeMobile))) {
                    // 黑名单
                    extValue = new StringBuffer();
                    ors = new OrderRiskScore();
                    ors.setOrderCode(orderCode);
                    if (commerce) {
                        extValue.append("商家").append(commerceName).append("为黑名单;");
                    }
                    if (customer) {
                        extValue.append("下单账号").append(customerAccount).append("为黑名单;");
                    }
                    if (mobile) {
                        extValue.append("收货手机").append(consigneeMobile).append("为黑名单;");
                    }
                    ors.setLabel(extValue.toString());
                    ors.setLatitude(
                            OrderRiskKey.OrderRiskLatitudeEnum.ORDERRISKLATITUDE_ROSTER.getCode());
                    ors.setCheckValue(BigDecimal.ZERO);
                    ors.setScore(1);
                    list.add(ors);
                    SUCCESS = false;
                } else {
                    // 风控
                    ors = execPayRateCheck(orderCode, resultCondition,
                            oscMap.get(OrderRiskKey.ORDER_RISK_ORDER_PAY_RATE));
                    list.add(ors);

                    ors = execOrderMoneyCheck(orderCode, resultCondition,
                            oscMap.get(OrderRiskKey.ORDER_RISK_ORDER_MONEY_MAX));
                    list.add(ors);

                    // 针对入驻商家订单进行pv风控处理
                    if (resultCondition.get("supplierType") != null) {
                        BigDecimal supplierType = (BigDecimal) resultCondition.get("supplierType");
                        if (supplierType.intValue() == 2) {// 入驻商家订单
                            ors = execOrderPvRateCheck(orderCode, resultCondition,
                                    oscMap.get(OrderRiskKey.ORDER_RISK_ORDER_PV_RATE));
                            list.add(ors);
                        }
                    }

                    ors = execsameConsigneeMobileCheck(orderCode, resultCondition,
                            oscMap.get(OrderRiskKey.ORDER_RISK_SAME_CONSIGNEE_MOBILE));
                    list.add(ors);

                    ors = execsameCustomerAccountCheck(orderCode, resultCondition,
                            oscMap.get(OrderRiskKey.ORDER_RISK_SAME_CUSTOMER_ACCOUNT));
                    list.add(ors);

                    ors = execSCMRecentTtimeCheck(orderCode, resultCondition,
                            oscMap.get(OrderRiskKey.ORDER_RISK_SCM_RECENT_TIME));
                    list.add(ors);

                    ors = execCommerceOrderCountCheck(orderCode, resultCondition,
                            oscMap.get(OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_COUNT));
                    list.add(ors);

                    ors = execCommerceOrderMoneyCheck(orderCode, resultCondition,
                            oscMap.get(OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_MONEY));
                    list.add(ors);

                    int score = 0;
                    for (OrderRiskScore temp : list) {
                        score += temp.getScore();
                    }
                    SUCCESS = score < orc.getCondition().intValue();
                }
                ors = list.get(0);
                ors.setLabel(orc.getCondition() + "!@" + ors.getLabel());
                orderRiskScoreDao.batchInsert(list);

                long status = SUCCESS ? OrderDictionaryEnum.Order_Status.Risk_Pass.getKey()
                        : OrderDictionaryEnum.Order_Status.Risk_Appraise.getKey();
                BigDecimal orderMoney = (BigDecimal) resultCondition.get("orderMoney");
                OrderOperateStatement ops = new OrderOperateStatement();
                ops.setOrderCode(orderCode);
                ops.setNowOperateDate(new Date());
                ops.setNowOperateType(
                        (long) OrderDictionaryEnum.OrderOperateType.ORDERRISK_JUDGE.getKey());
                ops.setNowOperator(SysConstants.SYS);
                ops.setNowOrderStatus(status);
                ops.setNowOrderSum(orderMoney);
                ops.setPreviousOrderSum(orderMoney);
                ops.setPreviousOperateType(
                        (long) OrderDictionaryEnum.OrderOperateType.LockStock.getKey());
                ops.setPreviousOrderStatus(
                        (long) OrderDictionaryEnum.Order_Status.Stock_Lock.getKey());
                ops.setPreviousOperator(customerAccount);
                // ops.setOperateInfo("执行订单风控，结果为" + (SUCCESS ? "风控通过" : "待审核"));
                ops.setOperateInfo(SUCCESS ? "订单已通过自动风控判断" : "订单自动风控判断不通过，转入人工风险评估环节");
                if (!orderOperateStatementService.orderRiskCheckUpdateOrder(orderCode, status, null,
                        ops)) {
                    throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                            "执行订单风控修改过订单发生异常失败");
                }
            } else {
                throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                        "执行订单风控发生异常，值" + value + "，风控条件" + oscMap);
            }
        } catch (Exception e) {
            time++;
            if (null != commerceName && null != orderCode && time < 3) {
                addRiskJudgeTask(commerceId, commerceName, orderCode + "," + time);// 重复推送
            }
            logger.error("执行订单风控发生异常:" + value, e);
        }
    }

    /**
     * 订单在线支付金额占总金额比重少于**
     * 
     * @param resultCondition
     * @param payRate
     * @return
     */
    private OrderRiskScore execPayRateCheck(String orderCode, Map<String, Object> resultCondition,
            OrderRiskCondition payRate) throws ServiceException {
        OrderRiskScore ors = new OrderRiskScore();
        ors.setOrderCode(orderCode);
        ors.setLabel("订单在线支付金额占总金额比重少于" + payRate.getCondition() + "%的；");
        ors.setLatitude(OrderRiskKey.OrderRiskLatitudeEnum.ORDERRISKLATITUDE_ORDER.getCode());
        BigDecimal orderMoney = (BigDecimal) resultCondition.get("orderMoney");
        BigDecimal thdpay = (BigDecimal) resultCondition.get("thdpay");
        BigDecimal rate = BigDecimal.ZERO;
        if (BigDecimal.ZERO.compareTo(orderMoney) != 0) {
            rate = thdpay.setScale(4).divide(orderMoney, BigDecimal.ROUND_HALF_DOWN)
                    .multiply(HUNDRED);
        }
        ors.setCheckValue(rate);
        ors.setExtValue("%（" + thdpay + "/" + orderMoney + "）");
        // 订单金额为0 不参与计算
        ors.setScore((BigDecimal.ZERO.compareTo(orderMoney) != 0
                && rate.compareTo(payRate.getCondition()) < 0) ? payRate.getScore() : 0);
        return ors;
    }

    /**
     * 订单金额超过 * 元的；
     * 
     * @param resultCondition
     * @param payRate
     * @return
     */
    private OrderRiskScore execOrderMoneyCheck(String orderCode,
            Map<String, Object> resultCondition, OrderRiskCondition payRate)
            throws ServiceException {
        OrderRiskScore ors = new OrderRiskScore();
        ors.setOrderCode(orderCode);
        ors.setLabel("订单金额超过" + payRate.getCondition() + "的；");
        ors.setLatitude(OrderRiskKey.OrderRiskLatitudeEnum.ORDERRISKLATITUDE_ORDER.getCode());
        BigDecimal orderMoney = (BigDecimal) resultCondition.get("orderMoney");
        ors.setCheckValue(orderMoney);
        ors.setScore(orderMoney.compareTo(payRate.getCondition()) > 0 ? payRate.getScore() : 0);
        return ors;
    }

    /**
     * 入驻商家订单PV积分占订单实收减去佣金的金额比重高于 *%的；
     * 
     * @param resultCondition
     * @param orderCode
     * @return
     */
    private OrderRiskScore execOrderPvRateCheck(String orderCode,
            Map<String, Object> resultCondition, OrderRiskCondition orderRiskCondition)
            throws ServiceException {
        OrderRiskScore ors = new OrderRiskScore();
        ors.setOrderCode(orderCode);
        ors.setLabel("入驻商家订单PV积分占订单实收减去佣金的金额比重高于" + orderRiskCondition.getCondition() + "%的；");
        ors.setLatitude(OrderRiskKey.OrderRiskLatitudeEnum.ORDERRISKLATITUDE_ORDER.getCode());
        // 设置的比率
        BigDecimal rate = orderRiskCondition.getCondition();
        // 订单实收金额
        BigDecimal realMoney = (BigDecimal) resultCondition.get("realMoney");
        // 订单PV积分
        BigDecimal orderPv = (BigDecimal) resultCondition.get("orderPv");
        // 订单佣金总额
        BigDecimal commission = (BigDecimal) resultCondition.get("commission");

        // 计算订单PV积分占订单实收减去佣金的金额比重
        double pvRate = 0;
        if (orderPv != null && realMoney != null && commission != null) {
            double calNum = realMoney.doubleValue() - commission.doubleValue();
            if (calNum != 0) {
                pvRate = 100 * orderPv.doubleValue() / calNum;
            } else {
                pvRate = 100 * orderPv.doubleValue();
            }
        }

        ors.setCheckValue(new BigDecimal(pvRate).setScale(2, BigDecimal.ROUND_HALF_UP));
        ors.setExtValue("%（" + orderPv + "/（" + realMoney + "-" + commission + "））");
        ors.setScore(pvRate - rate.doubleValue() > 0 ? orderRiskCondition.getScore() : 0);
        return ors;
    }

    /**
     * 同一个收货手机前溯24小时（支付时间）内有效订单超过*个的；
     * 
     * @param resultCondition
     * @param payRate
     * @return
     */
    private OrderRiskScore execsameConsigneeMobileCheck(String orderCode,
            Map<String, Object> resultCondition, OrderRiskCondition payRate)
            throws ServiceException {
        OrderRiskScore ors = new OrderRiskScore();
        ors.setOrderCode(orderCode);
        ors.setLabel("同一个收货手机前溯24小时（支付时间）内有效订单超过" + payRate.getCondition() + "个的；");
        ors.setLatitude(OrderRiskKey.OrderRiskLatitudeEnum.ORDERRISKLATITUDE_CONSUMER.getCode());
        String sameConsigneeMobileInfo = (String) resultCondition.get("sameConsigneeMobileInfo");
        String[] values = sameConsigneeMobileInfo.split(",");
        BigDecimal orderCount = BigDecimal.ZERO;
        String consigneeMobile = (String) resultCondition.get("consigneeMobile");
        if (null != values && values.length > 0 && null != values[0] && values[0].length() > 0) {
            orderCount = new BigDecimal(values[0]);
        }
        orderCount = orderCount.add(BigDecimal.ONE);// 算上当前
        ors.setCheckValue(orderCount);
        Date payDate = (Date) resultCondition.get("payDate");
        Calendar cal = Calendar.getInstance();
        String extValue = "cm," + consigneeMobile;// 前台展示 和帐号区分
        if (null != payDate) {
            cal.setTime(payDate);
            cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 24);
            extValue += ',' + dateFormat.format(cal.getTime()) + ',' + dateFormat.format(payDate);
        }
        ors.setExtValue(extValue);
        ors.setScore(orderCount.compareTo(payRate.getCondition()) > 0 ? payRate.getScore() : 0);
        return ors;
    }

    /**
     * 同一个下单账号前溯24小时（支付时间）内有效订单超过*个的；
     * 
     * @param resultCondition
     * @param payRate
     * @return
     */
    private OrderRiskScore execsameCustomerAccountCheck(String orderCode,
            Map<String, Object> resultCondition, OrderRiskCondition payRate)
            throws ServiceException {
        OrderRiskScore ors = new OrderRiskScore();
        ors.setOrderCode(orderCode);
        ors.setLabel("同一个下单账号前溯24小时（支付时间）内有效订单超过 " + payRate.getCondition() + "个的；");
        ors.setLatitude(OrderRiskKey.OrderRiskLatitudeEnum.ORDERRISKLATITUDE_CONSUMER.getCode());
        String sameCustomerIdInfo = (String) resultCondition.get("sameCustomerIdInfo");
        String[] values = sameCustomerIdInfo.split(",");
        BigDecimal orderCount = BigDecimal.ZERO;
        String customerAccount = (String) resultCondition.get("customerAccount");
        if (null != values && values.length > 0 && null != values[0] && values[0].length() > 0) {
            orderCount = new BigDecimal(values[0]);
        }
        orderCount = orderCount.add(BigDecimal.ONE);// 算上当前
        ors.setCheckValue(orderCount);
        Date payDate = (Date) resultCondition.get("payDate");
        Calendar cal = Calendar.getInstance();
        String extValue = "cc," + customerAccount;// 前台展示 和手机号码区分
        cal.setTime(payDate);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 24);
        extValue += ',' + dateFormat.format(cal.getTime()) + ',' + dateFormat.format(payDate);
        ors.setExtValue(extValue);
        ors.setScore(orderCount.compareTo(payRate.getCondition()) > 0 ? payRate.getScore() : 0);
        return ors;
    }

    /**
     * 同一收货手机上一有效订单支付时间间隔小于 * 分钟的；
     * 
     * @param resultCondition
     * @param payRate
     * @return
     */
    private OrderRiskScore execSCMRecentTtimeCheck(String orderCode,
            Map<String, Object> resultCondition, OrderRiskCondition payRate)
            throws ServiceException {
        OrderRiskScore ors = new OrderRiskScore();
        ors.setOrderCode(orderCode);
        ors.setLabel("同一收货手机上一有效订单支付时间间隔小于" + payRate.getCondition() + "分钟的；");
        ors.setLatitude(OrderRiskKey.OrderRiskLatitudeEnum.ORDERRISKLATITUDE_CONSUMER.getCode());
        Date prevpayDate = null;
        Date payDate = (Date) resultCondition.get("payDate");
        String sameConsigneeMobileInfo = (String) resultCondition.get("sameConsigneeMobileInfo");
        String[] values = sameConsigneeMobileInfo.split(",");
        if (null != values && values.length > 1 && null != values[1] && values[1].length() > 0) {
            try {
                prevpayDate = dateFormat.parse(values[1]);
            } catch (Exception e) {
                throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                        "同一收货手机上一有效订单支付时间间隔小于 * 分钟的；时间转换发生异常", e);
            }
        }
        BigDecimal diff = BigDecimal.ZERO;
        if (null != prevpayDate) {
            diff = BigDecimal.valueOf((payDate.getTime() - prevpayDate.getTime()) / 1000);
        }
        ors.setCheckValue(diff);
        ors.setExtValue(values.length > 2 ? values[2] : null);
        ors.setScore((null != prevpayDate
                && diff.compareTo(payRate.getCondition().multiply(new BigDecimal(60))) < 0)
                        ? payRate.getScore() : 0);
        return ors;
    }

    /**
     * 同一商家当日有效订单数超过**且比上一自然日超过 * % 的；
     * 
     * @param resultCondition
     * @param payRate
     * @return
     */
    private OrderRiskScore execCommerceOrderCountCheck(String orderCode,
            Map<String, Object> resultCondition, OrderRiskCondition payRate)
            throws ServiceException {
        OrderRiskScore ors = new OrderRiskScore();
        ors.setOrderCode(orderCode);
        ors.setLabel(
                "同一商家当日有效订单数超过" + payRate.getMax() + "，且比上一自然日超过" + payRate.getCondition() + "%的；");
        ors.setLatitude(OrderRiskKey.OrderRiskLatitudeEnum.ORDERRISKLATITUDE_SUPPLIER.getCode());
        String commerceInfo = (String) resultCondition.get("commerceInfo");
        String[] values = null;
        BigDecimal yestCount = BigDecimal.ZERO;
        BigDecimal todayCount = BigDecimal.ZERO;
        BigDecimal rate = BigDecimal.ZERO;
        if (null != commerceInfo && null != (values = commerceInfo.split(","))) {
            if (null != values && values.length > 1) {
                yestCount = new BigDecimal(values[1]);
            }
            if (null != values && values.length > 3) {
                todayCount = new BigDecimal(values[3]);
            }
            rate = todayCount.setScale(4)
                    .divide((yestCount.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ONE
                            : yestCount), BigDecimal.ROUND_HALF_DOWN)
                    .subtract(BigDecimal.ONE).multiply(HUNDRED);

        }
        ors.setCheckValue(rate);
        ors.setExtValue("%（" + todayCount + " / " + yestCount + " - 100%）");
        ors.setScore((payRate.getMax().compareTo(BigDecimal.ZERO) > 0
                && todayCount.compareTo(payRate.getMax()) > 0
                && rate.compareTo(payRate.getCondition()) > 0) ? payRate.getScore() : 0);
        return ors;
    }

    /**
     * 同一商家当日有效订单金额超过**且比上一自然日超过 * % 的；
     * 
     * @param resultCondition
     * @param payRate
     * @return
     */
    private OrderRiskScore execCommerceOrderMoneyCheck(String orderCode,
            Map<String, Object> resultCondition, OrderRiskCondition payRate)
            throws ServiceException {
        OrderRiskScore ors = new OrderRiskScore();
        ors.setOrderCode(orderCode);
        ors.setLabel("同一商家当日有效订单金额超过" + payRate.getMax() + "，且比上一自然日超过" + payRate.getCondition()
                + "%的；");
        ors.setLatitude(OrderRiskKey.OrderRiskLatitudeEnum.ORDERRISKLATITUDE_SUPPLIER.getCode());
        String commerceInfo = (String) resultCondition.get("commerceInfo");
        String[] values = null;
        BigDecimal yestMoney = BigDecimal.ZERO;
        BigDecimal todayMoney = BigDecimal.ZERO;
        BigDecimal rate = BigDecimal.ZERO;
        if (null != commerceInfo && null != (values = commerceInfo.split(","))) {
            if (null != values && values.length > 2) {
                yestMoney = new BigDecimal(values[2]);
            }
            if (null != values && values.length > 4) {
                todayMoney = new BigDecimal(values[4]);
            }
            rate = todayMoney.setScale(4)
                    .divide((yestMoney.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ONE
                            : yestMoney), BigDecimal.ROUND_HALF_DOWN)
                    .subtract(BigDecimal.ONE).multiply(HUNDRED);
        }
        ors.setCheckValue(rate);
        ors.setExtValue("%（" + todayMoney + " / " + yestMoney + " - 100%）");
        ors.setScore((payRate.getMax().compareTo(BigDecimal.ZERO) > 0
                && todayMoney.compareTo(payRate.getMax()) > 0
                && rate.compareTo(payRate.getCondition()) > 0) ? payRate.getScore() : 0);
        return ors;
    }
}
