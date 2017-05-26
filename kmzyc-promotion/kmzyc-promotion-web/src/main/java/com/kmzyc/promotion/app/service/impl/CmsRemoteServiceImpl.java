package com.kmzyc.promotion.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

import com.kmzyc.promotion.app.service.CmsProductUpShelfService;
import com.kmzyc.promotion.app.threadHandler.CmsProductUpShelfHandler;

@Service("cmsProductUpShelfService")
public class CmsRemoteServiceImpl implements CmsProductUpShelfService {
  // 日志记录
  private static final Logger logger = LoggerFactory.getLogger(CmsRemoteServiceImpl.class);

  // 执行器
  @Resource
  private TaskExecutor taskExecutor;

  @Override
  public void productUpShelfByCms(List<Integer> productIdList) throws Exception {
    try {
      taskExecutor.execute(new CmsProductUpShelfHandler(productIdList));
    } catch (TaskRejectedException e) {
      try {
        Thread.sleep(1000);
      } catch (Exception ex) {
        logger.error("productUpShelfByCms方法异常：", e);
      }
    }
  }
}
