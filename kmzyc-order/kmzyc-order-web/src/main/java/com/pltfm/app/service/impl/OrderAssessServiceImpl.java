package com.pltfm.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.OrderAssessDetailDAO;
import com.pltfm.app.dao.OrderAssessInfoDAO;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dao.OrderOperateStatementDAO;
import com.pltfm.app.entities.OrderAssessDetail;
import com.pltfm.app.entities.OrderAssessInfo;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.service.OrderAssessService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.sys.util.ErrorCode;

@Service("orderAssessService")
@SuppressWarnings("unchecked")
public class OrderAssessServiceImpl implements OrderAssessService {

  // log4j实例化
  private static final Logger log = Logger.getLogger(OrderAssessServiceImpl.class);

  @Resource
  private OrderAssessInfoDAO orderAssessDAO;
  @Resource
  private OrderAssessDetailDAO orderAssessDetailDAO;
  @Resource
  private OrderAssessInfoDAO orderAssessInfoDAO;
  @Resource
  private OrderMainDAO orderMainDAO;
  @Resource
  OrderOperateStatementDAO orderOperateStatementDAO;

  @Override
  public int listCount(Map map) throws ServiceException {
    try {
      return orderAssessDAO.countByMap(map);
    } catch (Exception e) {
      log.error(e);
      throw new ServiceException(ErrorCode.INNER_ORDER_EVALUATE_ERROR, "查询评价列表计数时发生异常："
          + e.getMessage());
    }
  }

  @Override
  public List listOrder(Map map) throws ServiceException {

    try {
      return orderAssessDAO.selectByMap(map);
    } catch (Exception e) {
      log.error(e);
      throw new ServiceException(ErrorCode.INNER_ORDER_EVALUATE_ERROR, "查询评价列表时发生异常："
          + e.getMessage());
    }
  }

  @Override
  public Long save(OrderAssessInfo record) throws ServiceException {
    try {
      return orderAssessDAO.insert(record);
    } catch (Exception e) {
      log.error(e);
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_EVALUATE_ERROR, "保存评价列表时发生异常："
          + e.getMessage());
    }

  }

  @Override
  public void insertList(List<OrderAssessDetail> oadList) throws ServiceException {
    try {
      orderAssessDetailDAO.insertList(oadList);
    } catch (Exception e) {
      log.error(e);
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_EVALUATE_ERROR, "批量保存评价列表时发生异常："
          + e.getMessage());
    }

  }

  @Override
  public int bathInsertOrderAssess(List<Object> oaList) throws ServiceException {
    int result = 0;
    try {
      if (!CollectionUtils.isEmpty(oaList)) {
        List<OrderAssessDetail> detailList = (List<OrderAssessDetail>) oaList.get(1);
        orderAssessDetailDAO.bathinsertOrderAssess(detailList);
        List<OrderAssessInfo> infoList = (List<OrderAssessInfo>) oaList.get(0);
        orderAssessInfoDAO.bathInsertOrderAssessInfo(infoList);
      }
    } catch (Exception e) {
      log.error(e);
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_EVALUATE_ERROR, "批量保存评价列表时发生异常："
          + e.getMessage());
    }
    return result;

  }

  @Override
  public List<OrderAssessDetail> selectDetailByAssessID(Long assessInfoID) throws ServiceException {
    try {
      Map map = new HashMap();
      map.put("assessInfoId", assessInfoID);
      return orderAssessDetailDAO.selectListByAssessInfoID(map);
    } catch (Exception e) {
      log.error(e);
      throw new ServiceException(ErrorCode.INNER_ORDER_EVALUATE_ERROR, "通过评价ID查询评价明细时发生异常："
          + e.getMessage());
    }
  }

  @Override
  public OrderAssessInfo selectByOrderCode(String orderCode) throws ServiceException {

    try {
      return orderAssessDAO.selectByOrderCode(orderCode);
    } catch (Exception e) {
      log.error(e);
      throw new ServiceException(ErrorCode.INNER_ORDER_EVALUATE_ERROR, "通过orderCode" + orderCode
          + "查询订单评价时发生异常：" + e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public void delete(String orderCode) throws ServiceException {
    try {
      orderAssessDAO.deleteByOrderCode(orderCode);
      orderAssessDetailDAO.deleteByOrderCode(orderCode);
    } catch (Exception e) {
      log.error(e);
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_EVALUATE_ERROR, "删除订单评价"
          + orderCode + "时发生异常：" + e.getMessage());
    }

  }

  /**
   * 前端传入的String[] assessType,String[] assessName,String[] state 这3个数组的length必须一致
   */
  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public void createAssessInfo(OrderAssessInfo orderAssessInfo, String[] assessType,
      String[] assessName, String[] state, String orderCode) throws ServiceException {
    try {
      // 评价明细对象初始化
      OrderAssessDetail orderAssessDetail = null;
      // 订单对象初始化
      OrderMain om = null;
      // 通过订单编码得到订单对象，用于set评价信息中的相关字段
      om = orderMainDAO.selectByOrderCode(orderCode);
      // 修改订单状态为“已评价”
      // om.setOrderStatus(new
      // Long(OrderDictionaryEnum.Order_Status.Assess_Done.getKey()));
      om.setAssessStatus((long) OrderDictionaryEnum.Assess_Status.Assess.getKey());
      // 评价信息中总得分初始化
      Long mark = 0L;
      // 遍历传入的各个评价type的得分，计算得到该评价的总得分
      for (int i = 0; i < state.length; i++) {
        mark = mark + Long.parseLong(state[i]);
      }
      // 赋值评价总得分
      orderAssessInfo.setAssessMark(mark);
      // 赋值订单编码
      orderAssessInfo.setOrderCode(orderCode);
      // 赋值订单id
      orderAssessInfo.setOrderId(om.getOrderId());
      // 保存评价信息，返回值为评价信息id
      Long resalut = save(orderAssessInfo);
      // 组装评价明细
      List<OrderAssessDetail> oadList = new ArrayList<OrderAssessDetail>();
      Date dateNow = new Date();
      for (int i = 0; i < assessType.length; i++) {
        orderAssessDetail = new OrderAssessDetail();
        orderAssessDetail.setAssessInfoId(resalut);
        orderAssessDetail.setAssessScore(Integer.parseInt(state[i]));
        orderAssessDetail.setAssessType(assessType[i]);
        orderAssessDetail.setAssessName(assessName[i]);
        orderAssessDetail.setOrderCode(orderAssessInfo.getOrderCode());
        orderAssessDetail.setCreateDate(dateNow);
        oadList.add(orderAssessDetail);
      }
      // 往数据库插入明细
      insertList(oadList);

      // 更新订单状态
      orderMainDAO.updateByOrderCode(om);
      Date date = new Date();
      OrderOperateStatement ops = new OrderOperateStatement();
      ops.setPreviousOperateDate(date);
      ops.setPreviousOperateType((long) OrderDictionaryEnum.OrderOperateType.Finish.getKey());
      ops.setPreviousOperator(om.getCustomerAccount());
      ops.setPreviousOrderStatus(om.getOrderStatus());
      ops.setPreviousOrderSum(om.getAmountPayable());
      ops.setNowOperateDate(date);
      ops.setNowOperateType((long) OrderDictionaryEnum.OrderOperateType.Assess.getKey());
      ops.setNowOperator(om.getCustomerAccount());
      ops.setNowOrderStatus(om.getOrderStatus());
      ops.setNowOrderSum(om.getAmountPayable());
      ops.setOperateInfo("订单评价完成");
      ops.setOrderCode(orderCode);
      orderOperateStatementDAO.insert(ops);
    } catch (Exception e) {
      log.error("记录订单评价发生异常", e);
      log.error(e);
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_EVALUATE_ERROR, "记录订单评价"
          + orderCode + "时发生异常：" + e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public void deleteAssessInfo(String orderCode) throws ServiceException {
    try {
      orderAssessDAO.deleteByOrderCode(orderCode);
      orderAssessDetailDAO.deleteByOrderCode(orderCode);
      OrderMain orderMain = new OrderMain();
      orderMain.setOrderCode(orderCode);
      // orderMain.setOrderStatus(6L);
      orderMain.setAssessStatus(Long.valueOf(OrderDictionaryEnum.Assess_Status.None.getKey()));
      orderMainDAO.updateByOrderCode(orderMain);
    } catch (Exception e) {
      log.error(e);
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_EVALUATE_ERROR, "删除评价"
          + orderCode + "并更新订单状态时发生异常：" + e.getMessage());
    }
  }

}
