package com.pltfm.app.remote.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderAssessRemoteService;
import com.pltfm.app.entities.OrderAssessInfo;
import com.pltfm.app.service.OrderAssessService;
import com.pltfm.sys.util.ErrorCode;

@Service("orderAssessRemoteService")
public class OrderAssessRemoteServiceImpl implements OrderAssessRemoteService {

  private static final long serialVersionUID = -5872691613664905924L;
  @Resource
  private OrderAssessService orderAssessService;

  @Override
  public int createAssessInfo(OrderAssessInfo orderAssessInfo, String[] assessType,
      String[] assessName, String[] state, String orderCode) throws ServiceException {
    int result = 0;
    try {
      orderAssessService
          .createAssessInfo(orderAssessInfo, assessType, assessName, state, orderCode);
      result = 1;
    } catch (Exception e) {
      result = 0;
      throw new ServiceException(ErrorCode.INTER_ORDER_EVALUATE_NEW_ERROR, "生成评价发生错误："
          + e.getMessage());
    }

    return result;
  }

  /**
   * 查询订单评价
   * 
   * @return
   * @throws ServiceException
   */
  @Override
public Map<String, Object> queryAssessByOrderCode(String orderCode) throws ServiceException {
    Map<String, Object> map = null;
    if (null != orderCode && !"".equals(orderCode)) {
      try {
        OrderAssessInfo assessInfo = orderAssessService.selectByOrderCode(orderCode);
        if (null != assessInfo) {
          map = new HashMap<String, Object>();
          map.put("assessInfo", assessInfo);
          map.put("assessDetailList", orderAssessService.selectDetailByAssessID(assessInfo
              .getAssessInfoId()));
        }
      } catch (Exception e) {
        throw new ServiceException(ErrorCode.INTER_ORDER_EVALUATE_NEW_ERROR, "接口获取评价发生错误："
            + e.getMessage());
      }
    }
    return map;
  }
}
