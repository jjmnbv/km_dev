package com.kmzyc.express.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.dao.ExpressTrackDAO;
import com.kmzyc.express.entities.ExpressTrack;
import com.kmzyc.express.service.ExpressTrackService;
import com.kmzyc.express.util.ErrorCode;
@SuppressWarnings("unchecked")
@Service("expressTrackService")
@Scope("singleton")
public class ExpressTrackServiceImpl implements ExpressTrackService {
  private static Logger logger = Logger.getLogger(ExpressTrackServiceImpl.class);

  @Resource
  private ExpressTrackDAO expressTrackDAO;

  @Override
  public int queryExpressTrackCount(Map<String, String> paramMap) throws ServiceException {
    try {
      return expressTrackDAO.queryExpressTrackCount(paramMap);
    } catch (Exception e) {
      logger.error("查询订阅订单的物流跟踪明细数量异常：" + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_TRACK_ERR, "查询订阅订单的物流跟踪明细数量异常：" + e.getMessage());
    }
  }

  @Override
  public List queryExpressTrackListByPage(Map<String, String> paramMap) throws ServiceException {
    try {
      return expressTrackDAO.queryExpressTrackList(paramMap);
    } catch (Exception e) {
      logger.error("查询订阅订单的物流跟踪明细列表异常：" + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_TRACK_ERR, "查询订阅订单的物流跟踪明细列表异常：" + e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public BigDecimal insertExpressTrack(ExpressTrack record) throws ServiceException {
    try {
      return expressTrackDAO.insertExpressTrack(record);
    } catch (Exception e) {
      logger.error("新增订阅订单的物流跟踪明细异常：" + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_TRACK_ERR, "新增订阅订单的物流跟踪明细异常：" + e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public Integer deleteExpressTrackBySubId(BigDecimal subId) throws ServiceException {
    try {
      return expressTrackDAO.deleteExpressTrackBySubId(subId);
    } catch (Exception e) {
      logger.error("删除订阅订单的物流跟踪明细异常： subId=" + subId + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_TRACK_ERR, "删除订阅订单的物流跟踪明细异常：" + e.getMessage());
    }

  }

  @Override
  public Integer batchInsertExpressTrack(List recordList) throws ServiceException {
    try {
      return expressTrackDAO.batchInsertExpressTrack(recordList);
    } catch (Exception e) {
      logger.error("批量新增订阅订单的物流跟踪明细异常：" + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_TRACK_ERR, "批量新增订阅订单的物流跟踪明细异常：" + e.getMessage());
    }
  }



}
