package com.pltfm.app.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.OrderCancelDAO;
import com.pltfm.app.entities.OrderCancelReason;
import com.pltfm.app.service.OrderCancelService;

@Service("orderCancelService")
@Scope("singleton")
public class OrderCancelServiceImpl implements OrderCancelService {

  @Resource
  private OrderCancelDAO orderCancelDAO;

  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  @Override
  public int saveOrderCancelReason(OrderCancelReason reason) throws ServiceException {
    int result = 0;
    try {
      result = orderCancelDAO.saveOrderCancelReason(reason);
    } catch (SQLException e) {
      result = 1;
      e.printStackTrace();
    }
    return result;
  }

}
