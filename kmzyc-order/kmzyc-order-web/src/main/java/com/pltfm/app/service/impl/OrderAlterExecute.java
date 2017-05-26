package com.pltfm.app.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.OrderAlterDAO;
import com.pltfm.app.util.SysConstants;

/**
 * 
 * @author lvxingxing date:2013-8-5 explain:退换货单结转的定时任务实现类，
 *@Service("orderExecuteTask")
 */

public class OrderAlterExecute extends QuartzJobBean {
  private Logger log = Logger.getLogger(OrderAlterExecute.class);
  private OrderAlterDAO orderAlterDAO;

  public OrderAlterDAO getOrderAlterDAO() {
    return orderAlterDAO;
  }

  public void setOrderAlterDAO(OrderAlterDAO orderAlterDAO) {
    this.orderAlterDAO = orderAlterDAO;
  }

  /**
	 */
  private void executeOrderAlter() throws SQLException, ParseException, IOException,
      ServiceException {
    orderAlterDAO.autoSure(SysConstants.ORDER_AUTO_SURE_TIME);
  }

  /**
   * 定时任务执行入口
   */
  @Override
  protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
    log.info("自动确认退换货单开始！");
    try {
      executeOrderAlter();
    } catch (ServiceException e) {
      log.error("自动确认退货单Service异常", e);
    } catch (SQLException e) {
      log.error("自动确认退货单SQL异常", e);
    } catch (ParseException e) {
      log.error("自动确认退货单解析异常", e);
    } catch (IOException e) {
      log.error("自动确认退货单IO流异常", e);
    }
    log.info("自动确认退换货单结束！");
  }

}
