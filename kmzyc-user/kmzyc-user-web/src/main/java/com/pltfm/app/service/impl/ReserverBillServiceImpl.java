package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.BnesAcctTransactionDAO;
import com.pltfm.app.dao.ReserverBillDAO;
import com.pltfm.app.dao.ReserverInfoDAO;
import com.pltfm.app.service.ReserverBillService;
import com.pltfm.app.util.BillDate;
import com.pltfm.app.util.Constants;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.ReserverBill;
import com.pltfm.app.vobject.ReserverInfo;

@Component(value = "reserverBillService")
@Scope(value = "prototype")
public class ReserverBillServiceImpl implements ReserverBillService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "reserverBillDAO")
    private ReserverBillDAO reserverBillDAO;
    @Resource(name = "reserverInfoDAO")
    private ReserverInfoDAO reserverInfoDAO;
    @Resource(name = "bnesAcctTransactionDAO")
    private BnesAcctTransactionDAO bnesAcctTransactionDAO;
    // 账单实体
    private ReserverBill reserverBill;
    // 流水实体
    private BnesAcctTransactionQuery bnesAcctTransactionQuery;

    // 分页获取账单
    public Page queryReserverBillAll(Page page, ReserverBill bill) throws Exception {
        // 获取账单总数
        Integer count = reserverBillDAO.queryReserverBillCount(bill);
        page.setRecordCount(count);
        // 设置索引
        int max = page.getStartIndex() + page.getPageSize();
        bill.setMaxNum(max);
        bill.setMinNum(page.getStartIndex());
        page.setDataList(reserverBillDAO.queryReserverBillAll(bill));
        return page;
    }

    // 删除账单
    @Override
    public int deleteByPrimaryKey(BigDecimal billId) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    // 添加账单 传结束时间
    @Override
    public void insert(Map<String, Object> mapPra) throws Exception {
        List<ReserverInfo> reserverInfoList = new ArrayList<ReserverInfo>();
        if (mapPra == null) {
            logger.info("定时生成账单开始");
            // 获取所有的预备金账户
            reserverInfoList = reserverInfoDAO.selectAllReserverInfo();
            for (ReserverInfo reserverInfo : reserverInfoList) {
                // 获取结算周期
                short payType = reserverInfo.getPayType();
                BillDate BillDate = new BillDate();
                // 获取相关账单查询条件
                Map<String, Object> map = BillDate.getDate(payType, null);
                // 获取上期账单
                ReserverBill rb = new ReserverBill();
                rb.setReserveId(reserverInfo.getReserveId());
                rb.setBillName(map.get("oldBillName").toString());
                if (StringUtils.isBlank(map.get("billName").toString())) {
                    continue;
                }
                rb = reserverBillDAO.selectByPrimaryKey(rb);

                // 获取上期未还金额(应还-已还)
                BigDecimal lastPriodRepay = new BigDecimal(0);
                BigDecimal lastPriodHavepay = new BigDecimal(0);

                // 不是第一期账单 生成上一期账单 或者上期已还为0 迭代
                if (rb == null && reserverInfo.getOpenDate().before((Date) map.get("beginDate"))) {
                    Map<String, Object> map1 = BillDate
                            .getDate(payType, (Date) map.get("beginDate"));
                    Date beginDateOld = (Date) map1.get("beginDate");
                    Map<String, Object> mapPraOld = new HashMap<String, Object>();
                    mapPraOld.put("startTime", beginDateOld);
                    mapPraOld.put("endTime", map.get("endDate"));
                    mapPraOld.put("accountLogin", reserverInfo.getAccountLogin());
                    insert(mapPraOld);
                    continue;
                }

                // 在还款日第二日1点对账单进行定时处理操作
                Calendar calendar = Calendar.getInstance();
                calendar.setTime((Date) map.get("beginDate"));
                // 获取时间
                int mouth = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                // 设置处理初始日期
                calendar.set(year, mouth - 1, 16);
                Date dateBegin = calendar.getTime();

                // 获取该用户状态成功流水
                if (bnesAcctTransactionQuery == null) {
                    bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
                }
                bnesAcctTransactionQuery.setAccountId(reserverInfo.getAccountId().intValue());
                bnesAcctTransactionQuery.setCreateDate((Date) map.get("beginDate"));
                bnesAcctTransactionQuery.setEndDate((Date) map.get("endDate"));
                bnesAcctTransactionQuery
                        .setStatus(Constants.TRANSACTIONSTATUSSUCCESS);
                // 设置交易类型为预备金相关交易11---预备金支付订单;12--预备金在线还款;
                // 13---预备金订单退款;14--预备金取消订单;15--预备金调整
                List statusList = Arrays.asList(Constants.TRANSACTION_TYPE_RESERVER_PAYBILL,
                        Constants.TRANSACTION_TYPE_RESERVER_RETURN,
                        Constants.TRANSACTION_TYPE_RESERVER_CANCELBILL);
                bnesAcctTransactionQuery.setArrayList(StringUtils.join(statusList, ","));
                bnesAcctTransactionQuery.setType(null);
                // 获取账单
                BigDecimal amoutBill = bnesAcctTransactionDAO
                        .queryAmountAccount(bnesAcctTransactionQuery);
                if (amoutBill == null) {
                    amoutBill = new BigDecimal(0);
                }
                // 获取异常调整
                bnesAcctTransactionQuery.setArrayList(null);
                bnesAcctTransactionQuery
                        .setType(Constants.TRANSACTION_TYPE_RESERVER_RESERVERCHANGE);
                BigDecimal amoutMissPay = bnesAcctTransactionDAO
                        .queryAmountAccount(bnesAcctTransactionQuery);
                if (amoutMissPay == null) {
                    amoutMissPay = new BigDecimal(0);
                }
                // 获取提前还款
                bnesAcctTransactionQuery.setCreateDate(dateBegin);
                bnesAcctTransactionQuery.setType(Constants.TRANSACTION_TYPE_RESERVER_ONLINEREPAY);
                BigDecimal amoutRepayed = bnesAcctTransactionDAO
                        .queryAmountAccount(bnesAcctTransactionQuery);
                if (amoutRepayed == null) {
                    amoutRepayed = new BigDecimal(0);
                }
                // 上期账单存在更新上期账单
                if (rb != null) {
                    // 判断上期账单已还=0 更新
                    if (rb.getCurrentRepayed().compareTo(BigDecimal.ZERO) == 0) {
                        // 获取上期已还(账单开始日到16号)
                        bnesAcctTransactionQuery.setCreateDate((Date) map.get("beginDate"));
                        bnesAcctTransactionQuery.setEndDate(dateBegin);
                        BigDecimal amoutRepayedBefor = bnesAcctTransactionDAO
                                .queryAmountAccount(bnesAcctTransactionQuery);
                        if (amoutRepayedBefor == null) {
                            amoutRepayedBefor = new BigDecimal(0);
                        }
                        // 更新上一期账单
                        if (amoutRepayedBefor.compareTo(rb.getRepay()) == -1) {
                            // 已还《应还
                            rb.setPayOff(Short.parseShort(Constants.RESERVERBILLNO));
                            rb.setOverduePay(
                                    Short.parseShort(Constants.RESERVERBILLOVERDUEPAYTRUE));
                        } else {
                            rb.setPayOff(Short.parseShort(Constants.RESERVERBILLTRUE));
                            rb.setOverduePay(Short.parseShort(Constants.RESERVERBILLOVERDUEPAYNO));
                        }
                        rb.setCurrentRepayed(amoutRepayedBefor);
                        reserverBillDAO.updateByPrimaryKeySelective(rb);
                        logger.info("更新上一期账单");
                    }
                    lastPriodRepay = rb.getRepay();
                    lastPriodHavepay = rb.getCurrentRepayed();

                }
                // 账单和流水的正负相反
                amoutBill = amoutBill.multiply(new BigDecimal(-1));
                amoutMissPay = amoutMissPay.multiply(new BigDecimal(-1));
                // 本期应还获取 (上期应还-上期已还+本期账单+异常调整-提前还款)
                BigDecimal repay = lastPriodRepay.subtract(lastPriodHavepay).add(amoutBill)
                        .add(amoutMissPay).subtract(amoutRepayed);
                if (repay == null) {
                    repay = new BigDecimal(0);
                }
                reserverBill = new ReserverBill();
                // 检查是否存在账单就更新
                reserverBill.setReserveId(reserverInfo.getReserveId());
                reserverBill.setBillName(map.get("billName").toString());
                reserverBill = reserverBillDAO.selectByPrimaryKey(reserverBill);
                if (reserverBill == null) {
                    reserverBill = new ReserverBill();
                    // 设置异常调整金额默认为0
                    reserverBill.setMissPay(amoutMissPay);
                    // 设置预备金id
                    reserverBill.setReserveId(reserverInfo.getReserveId());
                    // 设置上期应还金额
                    reserverBill.setLastPriodRepay(lastPriodRepay);
                    // 设置上期还款金额
                    reserverBill.setLastPriodHavepay(lastPriodHavepay);
                    // 设置本期应还
                    reserverBill.setRepay(repay);
                    // 设置本期账单
                    reserverBill.setBill(amoutBill);
                    // 设置本期已还为0
                    reserverBill.setCurrentRepayed(new BigDecimal(0));
                    // 设置本期提前还款
                    reserverBill.setAdvanceRepayed(amoutRepayed);
                    // 账单默认未还清
                    reserverBill.setPayOff(Short.parseShort(Constants.RESERVERBILLNO));
                    // 账单默认非逾期还款
                    reserverBill
                            .setOverduePay(Short.parseShort(Constants.RESERVERBILLOVERDUEPAYNO));
                    // 设置登陆id
                    reserverBill.setLoginId(reserverInfo.getLoginId());
                    // 设置账单名称
                    reserverBill.setBillName(map.get("billName").toString());
                    // 开始时间设置beginDate
                    reserverBill.setStartDate((Date) map.get("beginDate"));
                    // 结算时间
                    reserverBill.setSettlementDate((Date) map.get("endDate"));
                    // 还款时间
                    reserverBill.setRepayDate((Date) map.get("repayDate"));
                    // 生成账单
                    reserverBillDAO.insert(reserverBill);
                    reserverBill = new ReserverBill();
                    logger.info("账单生成");
                }
            }
            logger.info("自动账单生成结束");
        } else {
            logger.info("手动生成账单开始");
            String praLoginName = (String) mapPra.get("accountLogin");
            if (StringUtils.isBlank(praLoginName)) {
                // 获取所有的预备金账户
                reserverInfoList = reserverInfoDAO.selectAllReserverInfo();
            } else {
                // 根据用户名查询预备金
                reserverInfoList = reserverInfoDAO.selectReserverInfoByAccountLogin(praLoginName);
            }
            // 对传入时间进行分月处理
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) mapPra.get("startTime"));
            int d1 = calendar.get(Calendar.DAY_OF_MONTH);
            int m1 = calendar.get(Calendar.MONTH) + 1;
            int y1 = calendar.get(Calendar.YEAR);
            // 设置结束时间
            calendar.setTime((Date) mapPra.get("endTime"));
            int m2 = calendar.get(Calendar.MONTH) + 1;
            int y2 = calendar.get(Calendar.YEAR);
            List<Date> dateList = new ArrayList<Date>();
            int j = 0;
            if (d1 == 1) {
                j = m1;
            } else {
                j = m1 + 1;
            }
            int m = (m2 - j) + 12 * (y2 - y1);
            for (int i = 1; i <= m; i++) {
                if (j > 12) {
                    y1 = y1 + 1;
                    j = 1;
                }
                calendar.set(y1, j, 1);
                j++;
                dateList.add(calendar.getTime());
            }

            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < dateList.size(); i++) {
                for (ReserverInfo reserverInfo : reserverInfoList) {
                    // 获取结算周期
                    short payType = reserverInfo.getPayType();
                    BillDate billDate = new BillDate();
                    // 获取相关账单查询条件
                    map = billDate.getDate(payType, dateList.get(i));
                    Date beginDate = (Date) map.get("beginDate");
                    Date endDate = (Date) map.get("endDate");
                    // 传入页面选择时间
                    Date startTime = (Date) mapPra.get("startTime");
                    Date endTime = (Date) mapPra.get("endTime");
                    // 如果各用户生成账单时间不在输入时间内 则跳过
                    if (endDate == null || beginDate == null ||
                            endDate.getTime() < reserverInfo.getOpenDate().getTime() ||
                            beginDate.getTime() < startTime.getTime() ||
                            endDate.getTime() > endTime.getTime()) {
                        continue;
                    }
                    // 获取上期账单
                    ReserverBill rb = new ReserverBill();
                    rb.setBillName((String) map.get("oldBillName"));
                    rb.setReserveId(reserverInfo.getReserveId());
                    rb = reserverBillDAO.selectByPrimaryKey(rb);
                    // 获取上期未还金额(应还-已还)
                    BigDecimal lastPriodRepay = new BigDecimal(0);
                    BigDecimal lastPriodHavepay = new BigDecimal(0);
                    // 上期账单为空且不是第一期账单 生成上一期账单
                    if (rb == null && reserverInfo.getOpenDate().before(beginDate)) {
                        map = billDate.getDate(payType, beginDate);
                        Date beginDateOld = (Date) map.get("beginDate");
                        Map<String, Object> mapPraOld = new HashMap<String, Object>();
                        mapPraOld.put("startTime", beginDateOld);
                        mapPraOld.put("endTime", endTime);
                        mapPraOld.put("accountLogin", reserverInfo.getAccountLogin());
                        this.insert(mapPraOld);
                        break;
                    }
                    // 在上期还款日第二日1点对账单进行定时处理操作 （提前还款）
                    calendar.setTime((Date) map.get("beginDate"));
                    // 获取时间
                    int mouth = calendar.get(Calendar.MONTH) + 1;
                    int year = calendar.get(Calendar.YEAR);
                    // 设置处理初始日期
                    calendar.set(year, mouth - 1, 16);
                    Date dateBegin = calendar.getTime();

                    // 本期已还时间段(本期账单结束到该月15号)
                    calendar.setTime((Date) map.get("endDate"));
                    // 获取时间
                    int mouth1 = calendar.get(Calendar.MONTH) + 1;
                    int year1 = calendar.get(Calendar.YEAR);
                    // 设置处理初始日期
                    calendar.set(year1, mouth1 - 1, 16);
                    Date dateEnd = calendar.getTime();

                    // 获取该账单时间内状态成功流水
                    if (bnesAcctTransactionQuery == null) {
                        bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
                    }
                    bnesAcctTransactionQuery.setAccountId(reserverInfo.getAccountId().intValue());
                    bnesAcctTransactionQuery.setCreateDate(beginDate);
                    bnesAcctTransactionQuery.setEndDate(endDate);
                    bnesAcctTransactionQuery
                            .setStatus(Integer.valueOf(Constants.TRANSACTIONSTATUSSUCCESS));
                    // 设置交易类型为预备金相关交易11---预备金支付订单;12--预备金在线还款;
                    // 13---预备金订单退款;14--预备金取消订单;15--预备金调整
                    List statusList = Arrays.asList(Constants.TRANSACTION_TYPE_RESERVER_PAYBILL,
                            Constants.TRANSACTION_TYPE_RESERVER_RETURN,
                            Constants.TRANSACTION_TYPE_RESERVER_CANCELBILL);
                    bnesAcctTransactionQuery.setArrayList(StringUtils.join(statusList, ","));
                    bnesAcctTransactionQuery.setType(null);
                    // 获取账单
                    BigDecimal amoutBill = bnesAcctTransactionDAO
                            .queryAmountAccount(bnesAcctTransactionQuery);
                    if (amoutBill == null) {
                        amoutBill = new BigDecimal(0);
                    }
                    // 获取异常调整
                    bnesAcctTransactionQuery.setArrayList(null);
                    bnesAcctTransactionQuery
                            .setType(Constants.TRANSACTION_TYPE_RESERVER_RESERVERCHANGE);
                    BigDecimal amoutMissPay = bnesAcctTransactionDAO
                            .queryAmountAccount(bnesAcctTransactionQuery);
                    if (amoutMissPay == null) {
                        amoutMissPay = new BigDecimal(0);
                    }
                    // 获取提前还款
                    bnesAcctTransactionQuery.setCreateDate(dateBegin);
                    bnesAcctTransactionQuery
                            .setType(Constants.TRANSACTION_TYPE_RESERVER_ONLINEREPAY);
                    BigDecimal amoutRepayed = bnesAcctTransactionDAO
                            .queryAmountAccount(bnesAcctTransactionQuery);
                    if (amoutRepayed == null) {
                        amoutRepayed = new BigDecimal(0);
                    }
                    // 获取本期已还
                    BigDecimal currentRepayed = new BigDecimal(0);
                    if (dateEnd.before(endTime) || dateEnd.equals(endTime)) {
                        bnesAcctTransactionQuery.setCreateDate(endDate);
                        bnesAcctTransactionQuery.setEndDate(dateEnd);
                        currentRepayed = bnesAcctTransactionDAO
                                .queryAmountAccount(bnesAcctTransactionQuery);
                        if (currentRepayed == null) {
                            currentRepayed = new BigDecimal(0);
                        }
                    }

                    // 账单和流水的正负相反
                    amoutBill = amoutBill.multiply(new BigDecimal(-1));
                    amoutMissPay = amoutMissPay.multiply(new BigDecimal(-1));

                    // 上期账单存在更新上期账单
                    if (rb != null) {
                        // 判断上期账单已还=0 更新
                        if (rb.getCurrentRepayed().compareTo(BigDecimal.ZERO) == 0) {
                            // 获取上期已还(账单开始日到16号)
                            bnesAcctTransactionQuery.setCreateDate((Date) map.get("beginDate"));
                            bnesAcctTransactionQuery.setEndDate(dateBegin);
                            BigDecimal amoutRepayedBefor = bnesAcctTransactionDAO
                                    .queryAmountAccount(bnesAcctTransactionQuery);
                            if (amoutRepayedBefor == null) {
                                amoutRepayedBefor = new BigDecimal(0);
                            }
                            // 更新上一期账单
                            if (amoutRepayedBefor.compareTo(rb.getRepay()) == -1) {
                                // 已还《应还
                                rb.setPayOff(Short.parseShort(Constants.RESERVERBILLNO));
                                rb.setOverduePay(
                                        Short.parseShort(Constants.RESERVERBILLOVERDUEPAYTRUE));
                            } else {
                                rb.setPayOff(Short.parseShort(Constants.RESERVERBILLTRUE));
                                rb.setOverduePay(
                                        Short.parseShort(Constants.RESERVERBILLOVERDUEPAYNO));
                            }
                            rb.setCurrentRepayed(amoutRepayedBefor);
                            reserverBillDAO.updateByPrimaryKeySelective(rb);
                            logger.info("更新上一期账单");
                        }
                        lastPriodRepay = rb.getRepay();
                        lastPriodHavepay = rb.getCurrentRepayed();
                    }

                    // 本期应还获取 (上期应还-上期已还+本期账单+异常调整-提前还款)
                    BigDecimal repay = lastPriodRepay.subtract(lastPriodHavepay).add(amoutBill)
                            .add(amoutMissPay).subtract(amoutRepayed);
                    if (repay == null) {
                        repay = new BigDecimal(0);
                    }
                    // 检查是否存在账单就更新
                    reserverBill = new ReserverBill();
                    reserverBill.setReserveId(reserverInfo.getReserveId());
                    reserverBill.setBillName(map.get("billName").toString());
                    reserverBill = reserverBillDAO.selectByPrimaryKey(reserverBill);
                    if (reserverBill != null) {
                        if (dateEnd.before(endTime) || dateEnd.equals(endTime)) {
                            if (currentRepayed.compareTo(repay) == -1) {
                                // 已还《应还
                                reserverBill.setPayOff(Short.parseShort(Constants.RESERVERBILLNO));
                                reserverBill.setOverduePay(
                                        Short.parseShort(Constants.RESERVERBILLOVERDUEPAYTRUE));
                            } else {
                                reserverBill
                                        .setPayOff(Short.parseShort(Constants.RESERVERBILLTRUE));
                                reserverBill.setOverduePay(
                                        Short.parseShort(Constants.RESERVERBILLOVERDUEPAYNO));
                            }
                            // 设置本期已还
                            reserverBill.setCurrentRepayed(currentRepayed);
                        }
                        // 设置上期应还金额
                        reserverBill.setLastPriodRepay(lastPriodRepay);
                        // 设置上期还款金额
                        reserverBill.setLastPriodHavepay(lastPriodHavepay);
                        // 设置异常调整金额
                        reserverBill.setMissPay(amoutMissPay);
                        // 设置本期应还
                        reserverBill.setRepay(repay);
                        // 设置本期账单
                        reserverBill.setBill(amoutBill);
                        // 设置提前还款
                        reserverBill.setAdvanceRepayed(amoutRepayed);
                        // 更新账单
                        reserverBillDAO.updateByPrimaryKeySelective(reserverBill);
                        logger.info("账单更新");
                    } else {
                        reserverBill = new ReserverBill();
                        // 设置异常调整金额默认为0
                        reserverBill.setMissPay(amoutMissPay);
                        // 设置预备金id
                        reserverBill.setReserveId(reserverInfo.getReserveId());
                        // 设置上期应还金额
                        reserverBill.setLastPriodRepay(lastPriodRepay);
                        // 设置上期还款金额
                        reserverBill.setLastPriodHavepay(lastPriodHavepay);
                        // 设置本期应还
                        reserverBill.setRepay(repay);
                        // 设置本期账单
                        reserverBill.setBill(amoutBill);
                        // 设置本期已还
                        reserverBill.setCurrentRepayed(currentRepayed);
                        // 设置提前还款
                        reserverBill.setAdvanceRepayed(amoutRepayed);
                        // 账单默认未还清
                        reserverBill.setPayOff(Short.parseShort(Constants.RESERVERBILLNO));
                        // 账单默认非逾期还款
                        reserverBill.setOverduePay(
                                Short.parseShort(Constants.RESERVERBILLOVERDUEPAYNO));
                        // 设置登陆id
                        reserverBill.setLoginId(reserverInfo.getLoginId());
                        // 设置账单名称
                        reserverBill.setBillName(map.get("billName").toString());
                        // 开始时间设置beginDate
                        reserverBill.setStartDate((Date) map.get("beginDate"));
                        // 结算时间
                        reserverBill.setSettlementDate((Date) map.get("endDate"));
                        // 还款时间
                        reserverBill.setRepayDate((Date) map.get("repayDate"));
                        // 生成账单
                        reserverBillDAO.insert(reserverBill);
                        logger.info("账单生成成功");
                    }
                }
            }
            logger.info("手动生成账单结束");
        }
    }

    // 查询单个账单
    @Override
    public ReserverBill selectByPrimaryKey(ReserverBill reserverBill) throws Exception {
        return reserverBillDAO.selectByPrimaryKey(reserverBill);
    }

    // 修改账单
    @Override
    public int updateByPrimaryKeySelective(ReserverBill record) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }


}
