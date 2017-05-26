package com.pltfm.sys.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.zkconfig.ConfigurationUtil;



public final class WithdrawalsUtil {
  private static final Logger logger = LoggerFactory.getLogger(WithdrawalsUtil.class);


  public static boolean getWithdrawalsTime() {
    boolean flag = false;
    try {
      SimpleDateFormat format = new SimpleDateFormat("dd HH:mm:ss");
      Date time = format.parse(format.format(new Date()));
      String start_date = ConfigurationUtil.getString("withdrawals_start_date");
      String end_date = ConfigurationUtil.getString("withdrawals_end_date");
      if (start_date == null) {
        start_date = "15 00:00:00";
      }
      if (end_date == null) {
        end_date = "20 23:59:59";
      }
      Date forTimeDate = format.parse(start_date);
      Date stroldDate = format.parse(end_date);
      if (time.after(forTimeDate) && time.before(stroldDate)) {
        flag = true;
      }
    } catch (Exception e) {
      logger.error("获取提现时间失败", e);
    }
    return flag;
  }

}
