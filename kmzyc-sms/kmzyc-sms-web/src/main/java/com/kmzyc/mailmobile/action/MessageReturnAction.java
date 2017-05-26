package com.kmzyc.mailmobile.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.kmzyc.mailmobile.model.MsgSendTask;
import com.kmzyc.mailmobile.service.MsgSendTaskService;
import com.kmzyc.mframework.logger.MessagelLogger;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;

@Controller(value = "messageReturnAction")
@Scope(value = "prototype")
public class MessageReturnAction extends BaseAction {

  private Logger logger = Logger.getLogger(MessageReturnAction.class);
  @Resource(name = "msgSendTaskService")
  private MsgSendTaskService msgSendTaskService;
  public String report, args, hb, yxParam;// jcxg,mdkj,hb,yuexin

  // (mdkj)Report ID，特服号，手机号，唯一标识，状态（0或DELIVRD为成功，其它均为失败），时间，
  // (jcxg)15622228062|DELIVRD|656040841365870592|74149|2015-10-19 12:51:02
  public void messageReturn() {
    MessagelLogger.info("jcxg返回消息编号report为:" + report);
    MessagelLogger.info("mdkj返回消息编号args为:" + args);
    MessagelLogger.info("hb返回消息编号hb为:" + hb);
    MessagelLogger.info("yxParam返回消息编号yxParam为:" + yxParam);

    /** 君诚信鸽 **/
    if (report != null) {
      if (report.indexOf(";") >= 0) {// 多条还是单条
        MessagelLogger.info("多条处理");
        String re[] = report.split(";");
        for (int i = 0; i < re.length; i++) {
          if (re[i].indexOf("|") >= 0) {
            String r[] = re[i].split("\\|");
            MessagelLogger.info("多条处理:共：" + re.length + ",第" + i + ",内容为：" + re[i]);
            Map map = new HashMap();
            map.put("sendReturn", r[2]);
            map.put("mobile", r[0]);
            try {
              MsgSendTask msgSendTask = msgSendTaskService.SelectMsgSendTask(map);
              if (msgSendTask != null) {
                msgSendTask.setSendSuccess(r[1]);
                msgSendTask.setReturnTime(new Date());
                msgSendTaskService.updateMsgSendTask(msgSendTask);
              }
            } catch (Exception e) {
              MessagelLogger.info("返回信息处理出错：" + e.getMessage());
            }
          }
        }
      } else {
        MessagelLogger.info("单条处理");
        String re[] = report.split("\\|");
        Map map = new HashMap();
        map.put("sendReturn", re[2]);
        map.put("mobile", re[0]);
        try {
          MsgSendTask msgSendTask = msgSendTaskService.SelectMsgSendTask(map);
          if (msgSendTask != null) {
            msgSendTask.setSendSuccess(re[1]);
            msgSendTask.setReturnTime(new Date());
            msgSendTaskService.updateMsgSendTask(msgSendTask);
          }
        } catch (Exception e) {
          MessagelLogger.info("返回信息处理出错：" + e.getMessage());
        }
      }
    }
    /** 漫道科技 **/
    if (args != null) {
      if (args.indexOf(";") >= 0) {// 多条还是单条
        String re[] = report.split(";");
        for (int i = 0; i < re.length; i++) {
          if (re[i].indexOf(",") >= 0) {
            MessagelLogger.info("多条处理");
            String r[] = re[i].split(",");
            Map map = new HashMap();
            map.put("sendReturn", r[2]);
            map.put("mobile", r[0]);
            try {
              MsgSendTask msgSendTask = msgSendTaskService.SelectMsgSendTask(map);
              if (msgSendTask != null) {
                msgSendTask.setSendSuccess(r[1]);
                msgSendTask.setReturnTime(new Date());
                msgSendTaskService.updateMsgSendTask(msgSendTask);
              }
            } catch (Exception e) {
              MessagelLogger.info("返回信息处理出错：" + e.getMessage());
            }
          }
        }
      } else {
        MessagelLogger.info("单条处理");
        String re[] = args.split(",");
        Map map = new HashMap();
        map.put("sendReturn", re[3]);
        map.put("mobile", re[2]);
        try {
          MsgSendTask msgSendTask = msgSendTaskService.SelectMsgSendTask(map);
          if (msgSendTask != null) {
            msgSendTask.setSendSuccess(re[1]);
            msgSendTask.setReturnTime(new Date());
            msgSendTaskService.updateMsgSendTask(msgSendTask);
          }
        } catch (Exception e) {
          MessagelLogger.info("返回信息处理出错：" + e.getMessage());
        }
      }
    }
    /** 昊博短信 **/
    if (hb != null) {
      MessagelLogger.info("单条处理");
      String re[] = hb.split("\\|");
      Map map = new HashMap();
      map.put("sendReturn", re[8]);
      map.put("mobile", re[4]);
      try {
        MsgSendTask msgSendTask = msgSendTaskService.SelectMsgSendTask(map);
        if (msgSendTask != null) {
          msgSendTask.setSendSuccess(re[10]);
          msgSendTask.setReturnTime(new Date());
          msgSendTaskService.updateMsgSendTask(msgSendTask);
        }
      } catch (Exception e) {
        MessagelLogger.info("返回信息处理出错：" + e.getMessage());
      }
    }

    /** 悦信短信 **/
    if (yxParam != null) {
      if (yxParam.indexOf(";") >= 0) {// 多条还是单条
        String re[] = yxParam.split(";");
        for (int i = 0; i < re.length; i++) {
          if (re[i].indexOf("\\|") >= 0) {
            MessagelLogger.info("多条处理");
            String r[] = re[i].split(",");
            Map map = new HashMap();
            map.put("sendReturn", r[3].split("\\$")[0]);

            map.put("mobile", r[1]);
            try {
              MsgSendTask msgSendTask = msgSendTaskService.SelectMsgSendTask(map);
              if (msgSendTask != null) {
                if ("0".equals(r[2])) {
                  msgSendTask.setSendSuccess("DELIVRD");
                } else {
                  msgSendTask.setSendSuccess(r[2]);
                }
                msgSendTask.setReturnTime(new Date());
                msgSendTaskService.updateMsgSendTask(msgSendTask);
              }
            } catch (Exception e) {
              MessagelLogger.info("返回信息处理出错：" + e.getMessage());
            }
          }
        }
      } else {
        MessagelLogger.info("单条处理");
        String re[] = yxParam.split("\\|");
        Map map = new HashMap();
        map.put("sendReturn", re[3].split("\\$")[0]);
        map.put("mobile", re[1]);
        try {
          MsgSendTask msgSendTask = msgSendTaskService.SelectMsgSendTask(map);
          if (msgSendTask != null) {
            msgSendTask.setSendSuccess(re[2]);
            msgSendTask.setReturnTime(new Date());
            msgSendTaskService.updateMsgSendTask(msgSendTask);
          }
        } catch (Exception e) {
          MessagelLogger.info("返回信息处理出错：" + e.getMessage());
        }
      }
    }

  }

  public String getReport() {
    return report;
  }

  public void setReport(String report) {
    this.report = report;
  }

  public String getArgs() {
    return args;
  }

  public void setArgs(String args) {
    this.args = args;
  }

  public String getHb() {
    return hb;
  }

  public void setHb(String hb) {
    this.hb = hb;
  }

  public String getYxParam() {
    return yxParam;
  }

  public void setYxParam(String yxParam) {
    this.yxParam = yxParam;
  }


}
