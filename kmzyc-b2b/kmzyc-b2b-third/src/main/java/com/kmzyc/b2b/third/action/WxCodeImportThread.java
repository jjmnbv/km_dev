package com.kmzyc.b2b.third.action;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.b2b.third.dao.WxConsumeCodeRecordDAO;
import com.kmzyc.b2b.third.model.wxCard.QueryPara;
import com.kmzyc.b2b.third.util.BaseUtil;
import com.kmzyc.b2b.third.util.WxHttpUtil;

import net.sf.json.JSONObject;

/**
 * Description: User: lishiming Date: 15-1-27 下午5:29 Since: 1.0
 */
@Component("wxCodeImportThread")
@Scope("prototype")
public class WxCodeImportThread implements Runnable {

  private static Logger log = LoggerFactory.getLogger(WxCodeImportThread.class);

  private int index;
  private int begin;
  private int end;
  private String cardId;
  private String couponId;
  private String tableName;
  private static AtomicInteger importAmount = new AtomicInteger(0);

  @Resource(name = "wxConsumeCodeRecordDaoImpl")
  private WxConsumeCodeRecordDAO dao;

  // public WxCodeImportThread(int threadindex, int importNum,String
  // cardIdStr, String couponIdStr){
  // index = threadindex;
  // begin = importNum*(index); //index从0开始
  // end = importNum*(index+1) - 1;
  // cardId = cardIdStr;
  // couponId = couponIdStr;
  // }

  public void run() {
    log.info("开始运行第" + index + "个线程,导入表名：" + tableName + ",导入数据起始位置：" + begin + ",导入数据结束位置:" + end);
    List<QueryPara> list;
    int num = 100; // 每次导入100条
    int beginTemp = begin;

    int endTemp = begin + num;
    int times = 1;

    boolean flag = true;
    int count = 0;
    while (endTemp <= end) {
      try {
        log.info("开始第" + times + "次导入，为cardId=" + cardId + "导入从 " + beginTemp + " 到" + endTemp
            + " 的记录");
        list = dao.queryActiveCodeForImport(couponId, beginTemp, endTemp, tableName);
        if (list != null && list.size() > 0) {
          String sendUrl =
              "https://api.weixin.qq.com/card/code/deposit?access_token=" + BaseUtil.getToken();
          String data = "{'card_id': '" + cardId + "','code': ['";
          StringBuilder sb = new StringBuilder();
          for (QueryPara queryPara : list) {
            sb.append(queryPara.getCode() + "','");
          }
          data = data + sb.substring(0, sb.length() - 2) + "]}";
          JSONObject jsonData =
              WxHttpUtil.httpRequest(sendUrl, "POST", JSONObject.fromObject(data).toString());
          if (!"0".equals(jsonData.getString("errcode"))) {
            flag = false;
            log.info("为cardId=" + cardId + "导入从 " + beginTemp + " 到" + endTemp + " 的记录失败,微信返回数据："
                + jsonData);
          } else {
            count = count + list.size();
            int importAmountTemp = importAmount.addAndGet(list.size());
            log.info("为cardId=" + cardId + "导入从 " + beginTemp + " 到" + endTemp + " 的记录成功,第" + index
                + "个线程已导入" + count + "条,共已导入" + importAmountTemp);
          }
          // 每次累加100条
          beginTemp = endTemp;
          endTemp = endTemp + num;
          times++;
        } else {
          log.info("为cardId=" + cardId + "查询第 " + beginTemp + " 到" + endTemp + " 的记录失败");
        }

      } catch (Exception e) {
        log.error("执行异常", e);
        flag = false;
        break;
      }
    }
    log.info("第" + index + "个线程导入完成，共导入" + count + "条," + (flag ? "全部导入成功" : "中途有部分失败"));

  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getBegin() {
    return begin;
  }

  public void setBegin(int begin) {
    this.begin = begin;
  }

  public int getEnd() {
    return end;
  }

  public void setEnd(int end) {
    this.end = end;
  }

  public String getCardId() {
    return cardId;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public String getCouponId() {
    return couponId;
  }

  public void setCouponId(String couponId) {
    this.couponId = couponId;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
}
