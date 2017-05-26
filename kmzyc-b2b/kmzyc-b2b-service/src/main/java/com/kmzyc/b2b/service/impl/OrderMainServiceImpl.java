package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.OrderItemDao;
import com.kmzyc.b2b.dao.OrderMainDAO;
import com.kmzyc.b2b.dao.member.MyOrderDao;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.service.OrderMainService;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.constant.PromotionConstant;
import com.kmzyc.promotion.util.PromotionCacheUtil;
import com.kmzyc.supplier.model.SupplierFare;

@Service
public class OrderMainServiceImpl implements OrderMainService {

    @Resource(name = "orderMainDAOImpl")
    private OrderMainDAO orderMainDao;
    @Resource(name = "orderItemDaoImpl")
    private OrderItemDao orderItemDao;
    @Resource(name = "myOrderDaoImpl")
    private MyOrderDao myOrderDao;
    @Resource
    private PromotionCacheUtil promotionCacheUtil;
    // private static Logger log = Logger.getLogger(OrderMainServiceImpl.class);
    private static Logger log = LoggerFactory.getLogger(OrderMainServiceImpl.class);

    @Override
    public List<OrderMain> findByOrderCode(Map<String, String> map) throws ServiceException {

        try {
            return this.orderMainDao.findByOrderCode(map);
        } catch (Exception e) {
            throw new ServiceException(0, "查询订单失败:订单号" + map.get("orderCode"), e);

        }
    }

    @Override
    public List<OrderMain> findPaySuccessLaterStages(Map<String, String> map) throws
            ServiceException {

        try {
            return this.orderMainDao.findPaySuccessLaterStages(map);
        } catch (Exception e) {
            throw new ServiceException(0, "查询订单失败:订单号" + map.get("orderCode"), e);

        }
    }

    @Override
    public BigDecimal findNeedToPayMoney(String orderCode) throws ServiceException {

        try {
            return this.orderMainDao.findNeedToPayMoney(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询订单实付金额出错：订单号:" + orderCode, e);
        }
    }

    @Override
    public BigDecimal queryPayMoney(String orderCode) throws ServiceException {

        try {
            return this.orderMainDao.queryPayMoney(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询支付金额出错：订单号:" + orderCode, e);
        }
    }

    @Override
    public BigDecimal findPaiedMoney(String orderCode) throws ServiceException {

        try {
            return this.orderMainDao.findPaiedMoney(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询订单实付金额出错：订单号:" + orderCode, e);
        }
    }

    @Override
    public BigDecimal querySumUserBuySKUNum(Map map) throws ServiceException {

        try {
            return this.orderMainDao.querySumUserBuySKUNum(map);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询活动期间某用户购买指定产品数量", e);
        }
    }

    @Override
    public Integer queryUserBuyProductAccount(Map<String, Object> map) throws ServiceException {

        try {
            return this.orderMainDao.queryUserBuyProductAccount(map);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询活动期间某用户购买指定产品数量", e);
        }
    }

    /**
     * 查询订单中商品数量大于库存的商品
     */
    @Override
    public List<String> checkProductStockNumber(String orderCode) throws ServiceException {
        try {
            return this.orderItemDao.checkProductStockNumber(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询订单中商品数量大于库存的商品出错：订单号:" + orderCode, e);
        }
    }

    /**
     * 查询订单表中该抽奖号的订单数量
     */
    @Override
    public Integer byPrizeNumberCount(OrderMain orderMain) throws ServiceException {

        try {
            return orderMainDao.byPrizeNumberCount(orderMain);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询自营邮费信息
     */
    @Override
    public SupplierFare querySelfFare(String channel) throws ServiceException {

        try {
            return orderMainDao.querySelfFare(channel);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long getSalesVolume(String skuId) {

        try {
            Long saleVolume = orderMainDao.getSalesVolume(skuId);
            if (saleVolume == null) {
                saleVolume = 0L;
            }
            return saleVolume;
        } catch (SQLException e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 查询用户购买次数
     */
    @Override
    public Integer queryUserBuyNum(Long loginId) throws ServiceException {

        try {
            if (null == loginId) {
                return 0;
            }
            return orderMainDao.queryUserBuyNum(loginId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 支付前校验订单
     */
    @Override
    public String checkPayOrder(Long userId, String orderCode, BigDecimal payMoney) throws
            ServiceException {
        String result = null;
        try {

            OrderMain orderMain = null;
            Map<String, String> map = new HashMap<>();
            map.put("orderCode", orderCode);
            List<OrderMain> orderMainList = orderMainDao.findByOrderCode(map);
            if (null == orderMainList || orderMainList.isEmpty()) {
                result = "订单不存在";
            } else {
                orderMain = orderMainList.get(0);
            }
            BigDecimal needToPay;
            if (null != orderMain) {
                if (Objects.equals(orderMain.getOrderStatus(), Constants.ORDER_PAY_STATE_SUCCESS_2)) {
                    result = "订单已经支付完成！";
                } else if ((Calendar.getInstance().getTimeInMillis() -
                        orderMain.getCreateDate().getTime()) / 3600000d >
                        Constants.ORDER_DISABLED_TIME) {
                    result = "订单已经过期，请重新购买并支付！";
                } else if (null != (needToPay = orderMainDao.findNeedToPayMoney(orderCode)) &&
                        needToPay.compareTo(payMoney) != 0) {
                    result = "订单金额已发生变化，请重新刷新页面!";
                }
            }
            if (null == result) {
                List<Long> stocks = orderItemDao.queryOrderItemStock(orderCode);
                for (Long stock : stocks) {
                    if (stock < 0) {
                        result = "商品库存不足，请重新购买并支付！";
                        break;
                    }
                }
            }
            List<String> etitle;
            if (null == result) {
                etitle = orderItemDao.queryOrderItemExceptionStatusProduct(orderCode);
                if (null != etitle && !etitle.isEmpty()) {
                    result = "商品" + etitle.get(0) + "已经下架，请重新购买再支付！";
                }
            }
            if (null == result) {
                etitle = orderItemDao.queryOrderItemExceptionStatusSuits(orderCode);
                if (null != etitle && !etitle.isEmpty()) {
                    result = "套餐" + etitle.get(0) + "已经下架，请重新购买再支付！";
                }
            }
            if (null == result) {
                Map<Long, String> ppMap = orderItemDao.queryOrderProductPromotion(orderCode);
                if (null != ppMap && !ppMap.isEmpty()) {
                    Map<String, Integer> pprsMap;
                    String[] dvalue;
                    for (Map.Entry<Long, String> entry : ppMap.entrySet()) {
                        dvalue =entry.getValue().split(",");
                        pprsMap = promotionCacheUtil
                                .getProductRestrictionQuantity(dvalue[0], dvalue[1]);
                        if (pprsMap.containsKey(PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD) &&
                                pprsMap.get(PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD) <
                                        Integer.parseInt(dvalue[2])) {
                            result = "商品" + dvalue[3] + "活动库存不足，请重新购买再支付！";
                            break;
                        } else if (pprsMap.containsKey("max") && (pprsMap.get("max") < (orderItemDao
                                .queryUserBuyPromotionNum(userId, Long.parseLong(dvalue[0]),
                                        dvalue[1]) + Integer.parseInt(dvalue[2])))) {
                            // mkw 20151221 modfiy bug2949
                            // result = "商品" + dvalue[3] + "活动库存不足，请重新购买再支付！";
                            result = "商品 " + dvalue[3] + " 活动期间限购 " + pprsMap.get("max") +
                                    " 件,无法购买！";
                            // end
                            break;
                        }
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 支付定金前校验订单
     */
    @Override
    public String checkPayDeposit(Long userId, String orderCode, BigDecimal payMoney) throws
            ServiceException {
        String result = null;
        try {

            OrderMain orderMain = null;
            Map<String, String> map = new HashMap<>();
            map.put("orderCode", orderCode);
            List<OrderMain> orderMainList = orderMainDao.findByOrderCode(map);
            if (null == orderMainList || orderMainList.isEmpty()) {
                result = "订单不存在";
            } else {
                orderMain = orderMainList.get(0);
            }
            BigDecimal needToPay;
            // 校验预售活动是否已经结束
            if (null == result) {
                Integer presellStatus = myOrderDao.findPresellStatusByOrderCode(orderCode);
                if (presellStatus == 3) {
                    result = "该活动已经结束！";
                }
            }
            if (null == result) {
                // 获取当前时间与定金支付截止时间的差值
                Map<String, Object> timeMap = myOrderDao.findReserveByOrderCode(orderCode).get(0);
                BigDecimal end = (BigDecimal) timeMap.get("DEPOSITENDTIME");

                // 定金支付时间是否到期
                boolean isOverdue = end.longValue() > 0;

                if (null != orderMain) {
                    if (Objects
                            .equals(orderMain.getOrderStatus(), Constants.ORDER_PAY_STATE_SUCCESS_2)) {
                        result = "订单已经支付完成！";
                    } else if ((Calendar.getInstance().getTimeInMillis() -
                            orderMain.getCreateDate().getTime()) / 3600000d >
                            Constants.PRESELL_ORDER_DISABLED_TIME) {
                        result = "订单已经过期，请重新购买并支付！";
                    } else if (isOverdue) {
                        result = "定金支付时间已过，请重新购买并支付！";
                    } else if (null != (needToPay = orderMainDao.findNeedToPayDeposit(orderCode)) &&
                            needToPay.compareTo(payMoney) != 0) {
                        result = "订单金额已发生变化，请重新刷新页面!";
                    }
                }
            }
            List<String> etitle;
            if (null == result) {
                etitle = orderItemDao.queryOrderItemExceptionStatusProduct(orderCode);
                if (null != etitle && !etitle.isEmpty()) {
                    result = "商品" + etitle.get(0) + "已经下架，请重新购买再支付！";
                }
            }
            if (null == result) {
                etitle = orderItemDao.queryOrderItemExceptionStatusSuits(orderCode);
                if (null != etitle && !etitle.isEmpty()) {
                    result = "套餐" + etitle.get(0) + "已经下架，请重新购买再支付！";
                }
            }
            if (null == result) {
                Map<Long, String> ppMap = orderItemDao.queryOrderProductPromotion(orderCode);
                if (null != ppMap && !ppMap.isEmpty()) {
                    Map<String, Integer> pprsMap;
                    String[] dvalue;
                    for (String value: ppMap.values()) {
                        dvalue = value.split(",");
                        pprsMap = promotionCacheUtil
                                .getProductRestrictionQuantity(dvalue[0], dvalue[1]);
                        // 去掉库存判断
                        // if (pprsMap.containsKey(PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD)
                        // && pprsMap.get(PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD) < Integer
                        // .parseInt(dvalue[2])) {
                        // result = "商品" + dvalue[3] + "活动库存不足，请重新购买再支付！";
                        // break;
                        // } else
                        if (pprsMap.containsKey("max") && (pprsMap.get("max") < (orderItemDao
                                .queryUserBuyPromotionNum(userId, Long.parseLong(dvalue[0]),
                                        dvalue[1]) + Integer.parseInt(dvalue[2])))) {
                            // mkw 20151221 modfiy bug2949
                            // result = "商品" + dvalue[3] + "活动库存不足，请重新购买再支付！";
                            result = "商品 " + dvalue[3] + " 活动期间限购 " + pprsMap.get("max") +
                                    " 件,无法购买！";
                            // end
                            break;
                        }
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 支付尾款前校验订单
     */
    @Override
    public String checkPayRetainage(Long userId, String orderCode, BigDecimal payMoney) throws
            ServiceException {
        String result = null;
        try {

            OrderMain orderMain;
            Map<String, String> map = new HashMap<>();
            map.put("orderCode", orderCode);
            List<OrderMain> orderMainList = orderMainDao.findByOrderCode(map);
            if (null == orderMainList || orderMainList.isEmpty()) {
                result = "订单不存在";
            } else {
                orderMain = orderMainList.get(0);
                BigDecimal needToPay;
                BigDecimal orderMoney = orderMain.getDepositSum();// 预售定金
                needToPay = orderMain.getAmountPayable().subtract(orderMoney); // 尾款 = 订单应付金额 - 定金

                // 获取当前时间与尾款支付开始时间，截止时间的差值

                Map<String, Object> timeMap = myOrderDao.findReserveByOrderCode(orderCode).get(0);
                BigDecimal start = (BigDecimal) timeMap.get("FINALPAYSTARTTIME");
                BigDecimal end = (BigDecimal) timeMap.get("FINALPAYENDTIME");

                // 是否提前支付尾款
                boolean isAdvance = start.longValue() < 0;
                // 是否逾期支付尾款
                boolean isOverdue = end.longValue() > 0;

                if (Objects.equals(orderMain.getOrderStatus(), Constants.ORDER_PAY_STATE_SUCCESS_2)) {
                    result = "订单已经支付完成！";
                } else if (isOverdue) {
                    result = "已过尾款支付时间！";
                } else if (isAdvance) {
                    result = "未到尾款支付时间！";
                } else if (null != needToPay && needToPay.compareTo(payMoney) != 0) {
                    result = "订单金额已发生变化，请重新刷新页面!";
                }
                if (null == result) {
                    Map<Long, String> ppMap = orderItemDao.queryOrderProductPromotion(orderCode);
                    if (null != ppMap && !ppMap.isEmpty()) {
                        Map<String, Integer> pprsMap;
                        String[] dvalue;
                        for (String opid : ppMap.values()) {
                            dvalue = opid.split(",");
                            pprsMap = promotionCacheUtil
                                    .getProductRestrictionQuantity(dvalue[0], dvalue[1]);
                            if (pprsMap.containsKey("max") && (pprsMap.get("max") < (orderItemDao
                                    .queryUserBuyPromotionNum(userId, Long.parseLong(dvalue[0]),
                                            dvalue[1]) + Integer.parseInt(dvalue[2])))) {
                                // mkw 20151221 modfiy bug2949
                                // result = "商品" + dvalue[3] + "活动库存不足，请重新购买再支付！";
                                result = "商品 " + dvalue[3] + " 活动期间限购 " + pprsMap.get("max") +
                                        " 件,无法购买！";
                                // end
                                break;
                            }
                        }
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 校验订单、用户是否匹配
     */
    @Override
    public boolean checkOrderUser(Long userId, String orderCode, Integer flag) throws
            ServiceException {
        Map<String, Object> params = new HashMap<>();
        params.put("loginId", userId);
        params.put("orderCode", orderCode);
        Integer rs = null;
        try {
            if (2 == flag) {

                rs = orderMainDao.checkOrderUser(params);
            } else if (1 == flag) {
                return true;
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return (null != rs && rs > 0);
    }

    /**
     * 查询支付回调订单信息
     */
    @Override
    public Map<String, Object> queryOrderMainForPayCall(String orderCode) throws ServiceException {
        try {

            return orderMainDao.queryOrderMainForPayCall(orderCode);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    /**
     * 支付前校验订单
     */
    @Override
    public String appCheckPayOrder(Long userId, String orderCode) throws ServiceException {
        String result = null;
        try {

            OrderMain orderMain = null;
            Map<String, String> map = new HashMap<>();
            map.put("orderCode", orderCode);
            List<OrderMain> orderMainList = orderMainDao.findByOrderCode(map);
            if (null == orderMainList || orderMainList.isEmpty()) {
                result = "订单不存在";
            } else {
                orderMain = orderMainList.get(0);
            }
//            BigDecimal needToPay = null;
            if (null != orderMain) {
                if (Objects.equals(orderMain.getOrderStatus(), Constants.ORDER_PAY_STATE_SUCCESS_2)) {
                    result = "订单已经支付完成！";
                } else if ((Calendar.getInstance().getTimeInMillis() -
                        orderMain.getCreateDate().getTime()) / 3600000d >
                        Constants.ORDER_DISABLED_TIME) {
                    result = "订单已经过期，请重新购买并支付！";
                } /*
                   * else if (null != (needToPay = orderMainDao.findNeedToPayMoney(orderCode)) &&
                   * needToPay.compareTo(payMoney) != 0) { result = "订单金额已发生变化，请重新刷新页面!"; }
                   */
            }
            if (null == result) {
                List<Long> stocks = orderItemDao.queryOrderItemStock(orderCode);
                for (Long stock : stocks) {
                    if (stock < 0) {
                        result = "商品库存不足，请重新购买并支付！";
                        break;
                    }
                }
            }
            List<String> etitle;
            if (null == result) {
                etitle = orderItemDao.queryOrderItemExceptionStatusProduct(orderCode);
                if (null != etitle && !etitle.isEmpty()) {
                    result = "商品" + etitle.get(0) + "已经下架，请重新购买再支付！";
                }
            }
            if (null == result) {
                etitle = orderItemDao.queryOrderItemExceptionStatusSuits(orderCode);
                if (null != etitle && !etitle.isEmpty()) {
                    result = "套餐" + etitle.get(0) + "已经下架，请重新购买再支付！";
                }
            }
            if (null == result) {
                Map<Long, String> ppMap = orderItemDao.queryOrderProductPromotion(orderCode);
                if (null != ppMap && !ppMap.isEmpty()) {
                    Map<String, Integer> pprsMap;
                    String[] dvalue;
                    for (String opid : ppMap.values()) {
                        dvalue = opid.split(",");
                        pprsMap = promotionCacheUtil
                                .getProductRestrictionQuantity(dvalue[0], dvalue[1]);
                        if (pprsMap.containsKey(PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD) &&
                                pprsMap.get(PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD) <
                                        Integer.parseInt(dvalue[2])) {
                            result = "商品" + dvalue[3] + "活动库存不足，请重新购买再支付！";
                            break;
                        } else if (pprsMap.containsKey("max") && (pprsMap.get("max") < (orderItemDao
                                .queryUserBuyPromotionNum(userId, Long.parseLong(dvalue[0]),
                                        dvalue[1]) + Integer.parseInt(dvalue[2])))) {
                            // mkw 20151221 modfiy bug2949
                            // result = "商品" + dvalue[3] + "活动库存不足，请重新购买再支付！";
                            result = "商品 " + dvalue[3] + " 活动期间限购 " + pprsMap.get("max") +
                                    " 件,无法购买！";
                            // end
                            break;
                        }
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int queryYesterdayOrderCount() throws ServiceException {
        try {
            Map<String, String> params = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            StringBuilder dateBuilder = new StringBuilder();
            DecimalFormat df = new DecimalFormat("00");
            dateBuilder.append(cal.get(Calendar.YEAR));
            dateBuilder.append('-');
            dateBuilder.append(df.format(cal.get(Calendar.MONTH) + 1));
            dateBuilder.append('-');
            dateBuilder.append(df.format(cal.get(Calendar.DAY_OF_MONTH)));
            params.put("createDateBegin", dateBuilder.toString().concat(" 00:00:00"));
            params.put("createDateEnd", dateBuilder.toString().concat(" 23:59:59"));

            return orderMainDao.queryYesterdayOrderCount(params);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 查询预售支付回调订单信息
     */
    @Override
    public Map<String, Object> queryPayCallForYs(Map<String, String> mapc) throws ServiceException {
        try {

            return orderMainDao.queryPayCallForYs(mapc);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BigDecimal findNeedToPayDeposit(String orderCode) throws ServiceException {

        try {
            return this.orderMainDao.findNeedToPayDeposit(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询订单实付定金出错：订单号:" + orderCode, e);
        }
    }
}
