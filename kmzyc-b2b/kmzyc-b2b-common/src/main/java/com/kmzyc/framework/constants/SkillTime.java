package com.kmzyc.framework.constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 秒杀各个时间点: songmiao Date: 15-9-18 Time: 上午 11：27
 */
public class SkillTime {
  // private static Logger logger = Logger.getLogger(SkillTime.class);
  private static Logger logger = LoggerFactory.getLogger(SkillTime.class);
  public static Date nowDate = new Date();
  /**
   * 早间场
   */
  public static final String MORNING = "08:00:00";
  /**
   * 早间场对应窗口名
   */
  public static final String MORNING_WINDOW = "app_seckill_tab1";
  /**
   * 午休场
   */
  public static final String NOONBREAK = "12:00:00";

  /**
   * 午休场对应窗口名
   */
  public static final String NOONBREAK_WINDOW = "app_seckill_tab2";

  /**
   * 下午场
   */
  public static final String AFTERNOON = "16:00:00";
  /**
   * 下午场对应窗口名
   */
  public static final String AFTERNOON_WINDOW = "app_seckill_tab3";

  /**
   * 晚间场
   */
  public static final String NIGHT = "20:00:00";
  /**
   * 晚间场对应窗口名
   */
  public static final String NIGHT_WINDOW = "app_seckill_tab4";

  /**
   * 午夜场
   */
  public static final String MIDNIGHT = "24:00:00";
  /**
   * 午夜场对应窗口名
   */
  public static final String MIDNIGHT_WINDOW = "app_seckill_tab5";

  /**
   * 早间场场次状态
   */
  public static String morningStatus;
  /**
   * 午休场场次状态
   */
  public static String noonbreakStatus;
  /**
   * 下午场场次状态
   */
  public static String afternoonStatus;
  /**
   * 晚间场场次状态
   */
  public static String nightStatus;
  /**
   * 午夜场场次状态
   */
  public static String midnightStatus;

  /**
   * 早间场场次当天状态
   */
  public static String morningTodayStatus;
  /**
   * 午休场场次当天状态
   */
  public static String noonbreakTodayStatus;
  /**
   * 下午场场次当天状态
   */
  public static String afternoonTodayStatus;
  /**
   * 晚间场场次当天状态
   */
  public static String nightTodayStatus;
  /**
   * 午夜场场次当天状态
   */
  public static String midnightTodayStatus;

  /**
   * 根据当前时间给场次状态赋值 0:正在进行 1：等待开始 2：已经结束
   */
  public static void initSkillStatus() {
    Date now = new Date();
    DateFormat dft = DateFormat.getTimeInstance();
    String nowStr = dft.format(now);
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    try {
      now = sdf.parse(nowStr);

      if (now.after(sdf.parse(MORNING)) && now.before(sdf.parse(NOONBREAK))) {
        morningStatus = "0";
        noonbreakStatus = "1";
        afternoonStatus = "1";
        nightStatus = "1";
        midnightStatus = "1";
        morningTodayStatus = "0";
        noonbreakTodayStatus = "1";
        afternoonTodayStatus = "1";
        nightTodayStatus = "1";
        midnightTodayStatus = "2";
      } else if (now.after(sdf.parse(NOONBREAK)) && now.before(sdf.parse(AFTERNOON))) {
        morningStatus = "1";
        noonbreakStatus = "0";
        afternoonStatus = "1";
        nightStatus = "1";
        midnightStatus = "1";
        morningTodayStatus = "2";
        noonbreakTodayStatus = "0";
        afternoonTodayStatus = "1";
        nightTodayStatus = "1";
        midnightTodayStatus = "2";
      } else if (now.after(sdf.parse(AFTERNOON)) && now.before(sdf.parse(NIGHT))) {
        morningStatus = "1";
        noonbreakStatus = "1";
        afternoonStatus = "0";
        nightStatus = "1";
        midnightStatus = "1";
        morningTodayStatus = "2";
        noonbreakTodayStatus = "2";
        afternoonTodayStatus = "0";
        nightTodayStatus = "1";
        midnightTodayStatus = "2";
      } else if (now.after(sdf.parse(NIGHT)) && now.before(sdf.parse(MIDNIGHT))) {
        morningStatus = "1";
        noonbreakStatus = "1";
        afternoonStatus = "1";
        nightStatus = "0";
        midnightStatus = "1";
        morningTodayStatus = "2";
        noonbreakTodayStatus = "2";
        afternoonTodayStatus = "2";
        nightTodayStatus = "0";
        midnightTodayStatus = "2";
      } else {
        morningStatus = "1";
        noonbreakStatus = "1";
        afternoonStatus = "1";
        nightStatus = "1";
        midnightStatus = "0";
        morningTodayStatus = "1";
        noonbreakTodayStatus = "1";
        afternoonTodayStatus = "1";
        nightTodayStatus = "1";
        midnightTodayStatus = "0";
      }

    } catch (ParseException e) {
      logger.error("获取当前秒杀场次报错",e);
    }
  }

  // 将当前年月日拼接在传入字符串之前，获取秒杀开始时间
  public static Date getSkillStartDateStr(String dateStr, String todayStatus) {
    Date date = null;
    if (dateStr == null) {
      return new Date();
    }
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
      Calendar c = Calendar.getInstance();
      if ("2".equals(todayStatus)) {
        // 取第二天的时间
        if (!dateStr.equals("24:00:00")) {
          c.add(Calendar.DAY_OF_MONTH, 1);
        }
      }
      String startTime = sdf.format(c.getTime()) + " " + dateStr;
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      date = dateFormat.parse(startTime);
    } catch (Exception e) {
      logger.error("将当前年月日拼接在传入字符串之前，获取秒杀开始时间失败：", e);
    }
    return date;
  }

  /**
   * 获取当前秒杀场次类型
   * 
   * @return
   */
  public static String getSkillsIndexType() {
    String resultString = "";
    Date now = new Date();
    DateFormat dft = DateFormat.getTimeInstance();
    String nowStr = dft.format(now);
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    try {
      now = sdf.parse(nowStr);

      if (now.after(sdf.parse(MORNING)) && now.before(sdf.parse(NOONBREAK))) {
        resultString = MORNING_WINDOW;
      } else if (now.after(sdf.parse(NOONBREAK)) && now.before(sdf.parse(AFTERNOON))) {
        resultString = NOONBREAK_WINDOW;
      } else if (now.after(sdf.parse(AFTERNOON)) && now.before(sdf.parse(NIGHT))) {
        resultString = AFTERNOON_WINDOW;
      } else if (now.after(sdf.parse(NIGHT)) && now.before(sdf.parse(MIDNIGHT))) {
        resultString = NIGHT_WINDOW;
      } else {
        resultString = MIDNIGHT_WINDOW;
      }

    } catch (ParseException e) {
      logger.error("获取当前秒杀场次报错",e);
    }
    return resultString;
  }

  /**
   * 获取当前秒杀场次类型对应的星期，1：星期一，2：星期二，3：星期三，4：星期四，5：星期五，6：星期六，7：星期日
   * 
   * @return
   */
  public static String getSkillsWeekType(String todayStatus) {
    Calendar cal = Calendar.getInstance();
    int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
    if (week <= 0) {
      week = 7;
    }
    // todayStatus:2,今天应对的场次已结束，应查询第二天相对应的场次
    if (todayStatus != null && todayStatus.equals("2")) {
      week += 1;
      if (week > 7) {
        // todayStatus:2,今天应对的场次已结束，应查询第二天相对应的场次，周日取下周一的秒杀商品
        week = 1;
      }
    }
    return String.valueOf(week);

  }

  /**
   * 获取当前秒杀场次开始时间点
   * 
   * @return
   */
  public static String getSkillsStartTime() {
    String resultString = "";
    Date now = new Date();
    DateFormat dft = DateFormat.getTimeInstance(DateFormat.MEDIUM,Locale.CHINA);
    String nowStr = dft.format(now);
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    try {
      now = sdf.parse(nowStr);

      if (now.after(sdf.parse(MORNING)) && now.before(sdf.parse(NOONBREAK))) {
        resultString = MORNING;
      } else if (now.after(sdf.parse(NOONBREAK)) && now.before(sdf.parse(AFTERNOON))) {
        resultString = NOONBREAK;
      } else if (now.after(sdf.parse(AFTERNOON)) && now.before(sdf.parse(NIGHT))) {
        resultString = AFTERNOON;
      } else if (now.after(sdf.parse(NIGHT)) && now.before(sdf.parse(MIDNIGHT))) {
        resultString = NIGHT;
      } else {
        resultString = MIDNIGHT;
      }

    } catch (ParseException e) {
      logger.error("获取当前秒杀场次开始时间点报错");
      e.printStackTrace();
    }
    return resultString;
  }

  public static void main(String[] args) {
    SkillTime.getSkillsStartTime();
  }


  /**
   * 获取当前秒杀场次结束时间点
   * 
   * @return
   */
  public static String getSkillsEndTime() {
    String resultString = "";
    Date now = new Date();
    DateFormat dft = DateFormat.getTimeInstance();
    String nowStr = dft.format(now);
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    try {
      now = sdf.parse(nowStr);

      if (now.after(sdf.parse(MORNING)) && now.before(sdf.parse(NOONBREAK))) {
        resultString = NOONBREAK;
      } else if (now.after(sdf.parse(NOONBREAK)) && now.before(sdf.parse(AFTERNOON))) {
        resultString = AFTERNOON;
      } else if (now.after(sdf.parse(AFTERNOON)) && now.before(sdf.parse(NIGHT))) {
        resultString = NIGHT;
      } else if (now.after(sdf.parse(NIGHT)) && now.before(sdf.parse(MIDNIGHT))) {
        resultString = MIDNIGHT;
      } else {
        resultString = MORNING;
      }

    } catch (ParseException e) {
      logger.error("获取当前秒杀场次结束时间点报错");
      e.printStackTrace();
    }
    return resultString;
  }
}
