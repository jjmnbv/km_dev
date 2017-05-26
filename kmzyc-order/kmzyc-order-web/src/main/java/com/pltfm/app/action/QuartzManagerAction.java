package com.pltfm.app.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Controller;

import com.pltfm.app.entities.QuartzInfo;
import com.pltfm.app.service.QuartzInfoService;

@Controller("quartzManagerAction")
public class QuartzManagerAction extends BaseAction {

  private static final long serialVersionUID = 1L;
  @Resource
  private QuartzInfoService quartzInfoService;
  @Resource
  private StdScheduler scheduler;

  @Override
  /**
   * 定时任务列表
   */
  public String execute() throws Exception {
    HttpServletRequest request = this.getHttpServletRequest();
    Map<String, String> params = new HashMap<String, String>();
    params.put("order", "order");
    params.put("jobName", request.getParameter("jobName"));
    params.put("jobGroup", request.getParameter("jobGroup"));
    params.put("isVolatile", request.getParameter("isVolatile"));
    params.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
    params.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
    getPage().setDataList(quartzInfoService.queryTaskByPage(params));
    getPage().setRecordCount(quartzInfoService.queryTaskCount(params));
    request.setAttribute("paramsMap", params);
    return SUCCESS;
  }

  /**
   * 更新订单任务
   * 
   * @return
   * @throws Exception
   */
  public String updateTask() throws Exception {
    HttpServletRequest request = this.getHttpServletRequest();
    QuartzInfo params = new QuartzInfo();
    params.setTaskId(Long.parseLong(request.getParameter("taskid")));
    params.setSysCode("order");
    params.setTriggerName(request.getParameter("triggerName"));
    params.setTriggerGroup(request.getParameter("triggerGroup"));
    params.setCronExpression(request.getParameter("cronExpression"));
    params.setJobName(request.getParameter("jobName"));
    params.setJobGroup(request.getParameter("jobGroup"));
    params.setJobClass(request.getParameter("jobClass"));
    params.setIsVolatile(request.getParameter("isVolatile"));
    params.setJobDescription(request.getParameter("description"));
    quartzInfoService.updateTask(params);
    return null;
  }

  /**
   * 新增定时任务
   * 
   * @return
   * @throws Exception
   */
  public String addTask() throws Exception {
    HttpServletRequest request = this.getHttpServletRequest();
    QuartzInfo params = new QuartzInfo();
    params.setSysCode(request.getParameter("sysCode"));
    params.setTriggerName(request.getParameter("triggerName"));
    params.setTriggerGroup(request.getParameter("triggerGroup"));
    params.setCronExpression(request.getParameter("cronExpression"));
    params.setJobName(request.getParameter("jobName"));
    params.setJobGroup(request.getParameter("jobGroup"));
    params.setJobClass(request.getParameter("jobClass"));
    params.setIsVolatile(request.getParameter("isVolatile"));
    params.setJobDescription(request.getParameter("description"));
    quartzInfoService.addTask(params);
    return null;
  }

  /**
   * 删除定时任务
   * 
   * @return
   * @throws Exception
   */
  public String deleteTask() throws Exception {
    HttpServletRequest request = this.getHttpServletRequest();
    String taskId = request.getParameter("taskId");
    if (null != taskId && taskId.length() > 0) {
      quartzInfoService.deleteTask(new Long(taskId));
    }
    return null;
  }

  /**
   * 手动触发刷新job
   * 
   * @return
   * @throws Exception
   */
  public String refleshTask() throws Exception {
    HttpServletRequest request = this.getHttpServletRequest();
    String taskId = request.getParameter("taskId");
    if (null != taskId && taskId.length() > 0) {
      QuartzInfo params = new QuartzInfo();
      params.setTaskId(Long.parseLong(taskId));
      params.setSysCode("order");
      quartzInfoService.refleshTask(scheduler, params);
    }
    return null;
  }

  /**
   * 打印job
   * 
   * @return
   * @throws Exception
   */
  public String printJob() throws Exception {
    String[] tgns = scheduler.getTriggerGroupNames();
    if (null != tgns) {
      System.out.println("++++++++++++++++++触发器列表+++++++++");
      for (String groupName : tgns) {
        String[] tgs = scheduler.getTriggerNames(groupName);
        if (null != tgs) {
          for (String tg : tgs) {
            Trigger trigger = scheduler.getTrigger(groupName, tg);
            System.out.println(groupName + ">" + tg + "|" + trigger.getJobGroup() + ">"
                + trigger.getJobName() + ":" + trigger.getStartTime() + "~" + trigger.getEndTime()
                + "|" + trigger.getPreviousFireTime() + ":" + trigger.getNextFireTime().toString());
          }
        }
      }
    }
    return null;
  }
}
