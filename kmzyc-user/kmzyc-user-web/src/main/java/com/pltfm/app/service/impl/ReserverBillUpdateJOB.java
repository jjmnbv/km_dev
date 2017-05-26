package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pltfm.app.dao.BnesAcctTransactionDAO;
import com.pltfm.app.dao.ReserverBillDAO;
import com.pltfm.app.service.ReserverBillService;
import com.pltfm.app.service.ReserverInfoService;
import com.pltfm.app.util.BillDate;
import com.pltfm.app.util.Constants;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.ReserverBill;
import com.pltfm.app.vobject.ReserverInfo;

@Component
public class ReserverBillUpdateJOB {
  Logger logger = LoggerFactory.getLogger(this.getClass());
  @Resource(name = "reserverBillDAO")
  private ReserverBillDAO reserverBillDAO;
  @Resource(name = "reserverInfoService")
  private ReserverInfoService reserverInfoService;
  @Resource(name = "reserverBillService")
  private ReserverBillService reserverBillService;
  @Resource(name = "bnesAcctTransactionDAO")
  private BnesAcctTransactionDAO bnesAcctTransactionDAO;
  // 账单实体
  private ReserverBill reserverBill;
  // 流水实体
  private BnesAcctTransactionQuery bnesAcctTransactionQuery;

  public void ReserverBillUpdate() throws Exception {
    logger.info("定时更新账单开始");
    try {
      // 获取所有的预备金用户
      List<ReserverInfo> reserverInfoList = reserverInfoService.selectAllReserverInfo();
      // 遍历所有用户
      for (ReserverInfo reserverInfo : reserverInfoList) {
        // 获取用户还款周期
        Short payType = reserverInfo.getPayType();
        // 账单日期处理类
        BillDate billDate = new BillDate();
        Map<String, Object> map = billDate.getDate(payType, null);
        // 获取需要更新的账单
        if (StringUtils.isBlank(map.get("billName").toString())) {
          continue;
        }
        reserverBill = new ReserverBill();
        // 设置查询参数
        reserverBill.setReserveId(reserverInfo.getReserveId());
        reserverBill.setBillName(map.get("billName").toString());
        reserverBill = reserverBillService.selectByPrimaryKey(reserverBill);
        // 在还款日第二日1点对账单进行定时处理操作
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date) map.get("endDate"));
        // 获取时间
        int mouth = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        // 设置处理初始日期
        calendar.set(year, mouth - 1, 1);
        Date dateBegin = calendar.getTime();
        // 设置处理结束日期
        calendar.set(year, mouth - 1, 16);
        Date dateEnd = calendar.getTime();
        // 获取该预备金用户还款状态成功流水(更新当前1号到15号的还款问题)
        if (bnesAcctTransactionQuery == null) {
          bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
        }
        bnesAcctTransactionQuery.setAccountId(reserverInfo.getAccountId().intValue());
        bnesAcctTransactionQuery.setCreateDate(dateBegin);
        bnesAcctTransactionQuery.setEndDate(dateEnd);
        bnesAcctTransactionQuery.setStatus(Constants.TRANSACTIONSTATUSSUCCESS);
        // 获取预备金在线还款流水12
        bnesAcctTransactionQuery.setType(Constants.TRANSACTION_TYPE_RESERVER_ONLINEREPAY);
        // 账单已还金额
        if (reserverBill == null) {
          continue;
        }
        BigDecimal amoutRepayed = new BigDecimal(0);
        List<BnesAcctTransactionQuery> transactionList =
            bnesAcctTransactionDAO.selectTransactionAll(bnesAcctTransactionQuery);
        for (BnesAcctTransactionQuery bnesAcctTransactionQuery : transactionList) {
          // 已还金额(本月1号到本月15号的还款金额)
          amoutRepayed = amoutRepayed.add(bnesAcctTransactionQuery.getAmount());
        }
        // 设置本期已还
        reserverBill.setCurrentRepayed(amoutRepayed);
        if (amoutRepayed.compareTo(reserverBill.getRepay()) == -1) {
          // 已还《应还
          reserverBill.setPayOff(Short.parseShort(Constants.RESERVERBILLNO));
          reserverBill.setOverduePay(Short.parseShort(Constants.RESERVERBILLOVERDUEPAYTRUE));
        } else {
          reserverBill.setPayOff(Short.parseShort(Constants.RESERVERBILLTRUE));
          reserverBill.setOverduePay(Short.parseShort(Constants.RESERVERBILLOVERDUEPAYNO));
        }
        // 更新账单
        reserverBillDAO.updateByPrimaryKeySelective(reserverBill);
      }
    } catch (Exception e) {
      logger.error("更新账单异常" + e.getMessage(), e);
    }

  }

}
