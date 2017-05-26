package com.pltfm.crowdsourcing.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.crowdsourcing.common.GlobalVariable;
import com.km.crowdsourcing.service.GlobalVariableService;
import com.kmzyc.promotion.app.vobject.Message;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.action.BaseAction;
import com.pltfm.app.vobject.SpreaderRPInfoCriteria;
import com.pltfm.sys.service.SpreaderInfoService;

import redis.clients.jedis.JedisCluster;

/**
 * 
 * @ClassName: GlobalVariableAction
 * @Description: 全局变量管理
 * @author yijiangbo
 * @date 2016年3月15日 上午11:31:17
 * @version 1.0
 */
@Controller("globalVariableAction")
@Scope(value = "prototype")
public class GlobalVariableAction extends BaseAction implements ModelDriven {

  private static final long serialVersionUID = 4248109542989699382L;

  private static Logger logger = LoggerFactory.getLogger(GlobalVariableAction.class);


  private GlobalVariable globalVariable = new GlobalVariable();


  @Resource
  private JedisCluster jedis;

  @Resource
  private GlobalVariableService globalVariableService;

  @Resource
  private SpreaderInfoService spreaderInfoService;


  /**
   * @Title: goGlobalVariable @Description: 跳转全局变量管理页 @return String @throws
   */
  public String goGlobalVariable() {
    try {
      globalVariable = globalVariableService.getGlobalVariable();
    } catch (Exception e) {
      logger.error("获取全局变量失败", e);
    }
    return SUCCESS;
  }


  /**
   * @Title: goCleanRPPage @Description: 跳转微商红包清理页面 @return String @throws
   */
  public String goCleanRPPage() {
    return SUCCESS;
  }

  /**
   * @Title: cleanMicroRP @Description: 518红包清理 @return String @throws
   */
  public String cleanMicroRP() {
    final String key = "CleanMicroBussinessRPJob-execute";
//   boolean islock = redisTemplate.tryLock(key);
    Long i = jedis.setnx(key, "1");
    if (i<=0) {
      logger.info("手动清理微商红包任务在进行中");
      return "doing";
    }
  
    // 校验当前时间是否晚于clean_rp_datetime（红包清除时间 2016-05-31 00：00：00）
   
    Date now = new Date();
    String activity_starttime_4 = ConfigurationUtil.getString("activity_starttime_4");
    String activity_endtime_4 = ConfigurationUtil.getString("activity_endtime_4");
    String clean_rp_datetime = ConfigurationUtil.getString("clean_rp_datetime");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date cleanRPDatetime = sdf.parse(clean_rp_datetime);
      if (now.before(cleanRPDatetime)) {// 如果当前时间早于设定的红包清理时间
        log.info("手动清除微商红包任务，未到设定时间！" + clean_rp_datetime);
        return "notyet";
      }

      SpreaderRPInfoCriteria criteria = new SpreaderRPInfoCriteria();
      criteria.setActiveEndDate(sdf.parse(activity_endtime_4));
      criteria.setActiveStartDate(sdf.parse(activity_starttime_4));
      criteria.setRpEndDate(sdf.parse(clean_rp_datetime));
      log.info("手动微商清理红包 start");
      spreaderInfoService.cleanMicroBussinessRP(criteria);
      log.info("手动微商清理红包 end");
    } catch (Exception e) {
      log.error("手动清理微商红包异常" + e.getMessage(), e);
      return ERROR;
    } 

    jedis.del(key);
    return SUCCESS;
  
  }



  /**
   * @Title: setGlobalVariable @Description: 设置全局变量管理页 @return String @throws
   */
  public void updateGlobalVariable() {
    Message message = new Message();
    try {
      globalVariableService.updateGlobalVariable(globalVariable);
      message.setCode(0);
      message.setModule("保存众包全局变量成功！");
    } catch (Exception e) {
      message.setCode(1);
      message.setModule("保存众包全局变量异常！");
      logger.error("保存众包全局变量失败", e);
    }
    JSONObject json = (JSONObject) JSONObject.toJSON(message);
    this.writeJson(json);
  }


  @Override
  public Object getModel() {
    // TODO Auto-generated method stub
    return null;
  }

  public GlobalVariable getGlobalVariable() {
    return globalVariable;
  }

  public void setGlobalVariable(GlobalVariable globalVariable) {
    this.globalVariable = globalVariable;
  }



}
