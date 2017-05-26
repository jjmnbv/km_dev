package com.pltfm.app.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BillDate {

  public Map<String, Object> getDate(int payType, Date startDate) {
    Calendar calendar = Calendar.getInstance();
    if (startDate == null) {
      calendar.setTime(new Date());
    } else {
      calendar.setTime(startDate);
    }
    Map<String, Object> map = new HashMap<String, Object>();
    // 当前账单name
    String billName = "";
    // 上一期账单name
    String oldBillName = "";
    Date beginDate = null;
    Date endDate = null;
    Date repayDate = null;
    // 获取时间
    int mouth = calendar.get(Calendar.MONTH) + 1;
    int year = calendar.get(Calendar.YEAR);
    // 根据结算周期设置账单属性
    if (payType == 1) {
      if (mouth == 1) {
        billName = (year - 1) + "" + 12;
        oldBillName = (year - 1) + "" + 11;
        // 开始时间
        calendar.set(year - 1, 11, 1, 0, 0, 0);
        beginDate = calendar.getTime();
        // 结束时间
        calendar.set(year, 0, 1, 0, 0, 0);
        endDate = calendar.getTime();
        // 还款时间
        calendar.set(year, 0, 15, 0, 0, 0);
        repayDate = calendar.getTime();
      } else if (mouth == 2) {
        billName = year + "0" + (mouth - 1);
        oldBillName = (year - 1) + "" + 12;
        // 开始时间
        calendar.set(year, mouth - 2, 1, 0, 0, 0);
        beginDate = calendar.getTime();
        // 结束时间
        calendar.set(year, mouth - 1, 1, 0, 0, 0);
        endDate = calendar.getTime();
        // 还款时间
        calendar.set(year, mouth - 1, 15, 0, 0, 0);
        repayDate = calendar.getTime();
      } else {
        if (mouth == 12) {
          oldBillName = year + "" + (mouth - 2);
          billName = year + "" + (mouth - 1);
        } else if (mouth == 11) {
          oldBillName = year + "0" + (mouth - 2);
          billName = year + "" + (mouth - 1);
        } else {
          billName = year + "0" + (mouth - 1);
          oldBillName = year + "0" + (mouth - 2);
        }
        // 开始时间
        calendar.set(year, mouth - 2, 1, 0, 0, 0);
        beginDate = calendar.getTime();
        // 结束时间
        calendar.set(year, mouth - 1, 1, 0, 0, 0);
        endDate = calendar.getTime();
        // 还款时间
        calendar.set(year, mouth - 1, 15, 0, 0, 0);
        repayDate = calendar.getTime();
      }
    } else if (payType == 2 && (mouth == 1 || mouth == 4 || mouth == 7 || mouth == 10)) {
      // 季度结算
      int qTpye = 0;
      if (mouth == 1) {
        qTpye = 4;
        billName = (year - 1) + "Q" + qTpye;
        oldBillName = (year - 1) + "Q" + (qTpye - 1);
      } else if (mouth == 4) {
        qTpye = 1;
        billName = year + "Q" + qTpye;
        oldBillName = (year - 1) + "Q" + 4;
      } else if (mouth == 7) {
        qTpye = 2;
        billName = year + "Q" + qTpye;
        oldBillName = year + "Q" + (qTpye - 1);
      } else if (mouth == 10) {
        qTpye = 3;
        billName = year + "Q" + qTpye;
        oldBillName = year + "Q" + (qTpye - 1);
      }
      if (mouth == 1) {
        // 开始时间
        calendar.set(year - 1, 9, 1, 00, 00, 00);  
        beginDate = calendar.getTime();
        // 结束时间
//        calendar.set(year - 1, 12, 1, 00, 00, 00);   --This code passes a constant month value outside the expected range of 0..11 to a method.
        calendar.set(year, 0, 1, 00, 00, 00);
        endDate = calendar.getTime();
        // 还款时间
//        calendar.set(year - 1, 12, 15, 00, 00, 00);
        calendar.set(year, 0, 15, 00, 00, 00);
        repayDate = calendar.getTime();
      } else {
        // 开始时间
        calendar.set(year, mouth - 4, 1, 00, 00, 00);
        beginDate = calendar.getTime();
        // 结束时间
        calendar.set(year, mouth - 1, 1, 00, 00, 00);
        endDate = calendar.getTime();
        // 还款时间
        calendar.set(year, mouth - 1, 15, 00, 00, 00);
        repayDate = calendar.getTime();
      }
    } else if (payType == 3 && (mouth == 7 || mouth == 1)) {
      int qTpye = 0;
      if (mouth == 7) {
        qTpye = 1;
        billName = year + "H" + qTpye;
        oldBillName = (year - 1) + "H" + 2;
        // 开始时间
        calendar.set(year, mouth - 7, 1, 00, 00, 00);
        beginDate = calendar.getTime();
        // 结束时间
        calendar.set(year, mouth - 1, 1, 00, 00, 00);
        endDate = calendar.getTime();
        // 还款时间
        calendar.set(year, mouth - 1, 15, 00, 00, 00);
        repayDate = calendar.getTime();
      } else if (mouth == 1) {
        qTpye = 2;
        billName = (year - 1) + "H" + qTpye;
        oldBillName = (year - 1) + "H" + 1;
        // 开始时间
        calendar.set(year - 1, 6, 1, 00, 00, 00);
        beginDate = calendar.getTime();
        // 结束时间
        calendar.set(year, 0, 1, 00, 00, 00);
        endDate = calendar.getTime();
        // 还款时间
        calendar.set(year, 0, 15, 00, 00, 00);
        repayDate = calendar.getTime();
      }
    } else if (payType == 4 && mouth == 1) {
      billName = (year - 1) + "YR";
      oldBillName = (year - 2) + "YR";
      // 开始时间
      calendar.set(year - 1, 0, 1, 00, 00, 00);
      beginDate = calendar.getTime();
      // 结束时间
      calendar.set(year, 0, 1, 00, 00, 00);
      endDate = calendar.getTime();
      // 还款时间
      calendar.set(year, 0, 15, 00, 00, 00);
      repayDate = calendar.getTime();
    }
    map.put("billName", billName);
    map.put("oldBillName", oldBillName);
    map.put("beginDate", beginDate);
    map.put("endDate", endDate);
    map.put("repayDate", repayDate);
    return map;
  }


}
