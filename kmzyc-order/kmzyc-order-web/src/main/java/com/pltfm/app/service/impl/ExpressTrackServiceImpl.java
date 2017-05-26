package com.pltfm.app.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.util.ErrorCode;
import com.pltfm.app.dao.ExpressTrackDAO;
import com.pltfm.app.service.ExpressTrackService;

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
}
