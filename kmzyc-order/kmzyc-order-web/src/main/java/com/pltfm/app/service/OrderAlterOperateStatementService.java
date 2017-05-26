package com.pltfm.app.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderAlterDetail;
import com.pltfm.app.entities.OrderAlterOperateStatement;
import com.pltfm.app.vobject.Address;
import com.pltfm.app.vobject.OrderAlterOperateStatementSaveVo;
import com.pltfm.app.vobject.RefundRequest;

@SuppressWarnings("unchecked")
public interface OrderAlterOperateStatementService {
  /**
   * 记录退换货单操作流水
   * 
   * @return
   * @throws SQLException
   */
  public Boolean InsertOperate(OrderAlterOperateStatement oost) throws ServiceException;

  /**
   * 记录退换货单流水
   * 
   * @param orderId
   * @param orderItemId
   * @param status
   * @param operator
   * @param date
   * @param type
   * @param orderSum
   * @param info
   * @throws ServiceException
   */
  public void save(String orderAlterCode, Long orderItemId, Long status, String operator,
      Date date, Long type, BigDecimal orderSum, String info) throws ServiceException;

  public void save(OrderAlterOperateStatementSaveVo vo) throws ServiceException;

  /**
   * 
   * @param oa
   * @return
   * @throws ServiceException
   */
  public String saveOrderAlter(OrderAlter oa, BigDecimal customerId) throws ServiceException;

  /**
   * 
   * @param orderAlterId
   * @return
   * @throws ServiceException
   */
  public OrderAlter getOrderAlterByCode(String orderAlterCode) throws ServiceException;

  /**
   * 更改状态
   * 
   * @param operator
   * @param alterId
   * @param status
   * @param code
   * @throws ServiceException
   */
  public int changeAlterStatus(String operator, OrderAlter oa) throws ServiceException;

  /**
   * 根据退换货批次号获取退换货详情
   * 
   * @param orderAlterId
   * @return
   * @throws ServiceException
   */
  public List getOrderAlterDetailsByAlterCode(String orderAlterCode) throws ServiceException;

  public void updateOrderAlter(OrderAlter oa) throws ServiceException;

  public void saveOrderAlterDetail(OrderAlterDetail oad) throws ServiceException;

  public List listOrderAlterOperates(String alterCode) throws ServiceException;

  public List listOrderAlterPays(String alterCode) throws ServiceException;

  public Address getAddressById(BigDecimal addressId) throws ServiceException;

  public int changeAlterStatusForProduct(String operator, String orderAlterCode, Long status,
      String comment) throws ServiceException;

  public Integer selectFareAdditional(String orderAlterCode) throws ServiceException;

  public int additional(String alterCode) throws ServiceException;

  /**
   * 批量处理退换货
   * 
   * @param rrList 回调实体集合
   * @param refundNo 退款批次号
   * @throws ServiceException
   */
  public void batchAlterRefund(List<RefundRequest> rrList, String refundNo) throws ServiceException;

  /**
   * 查询待确认的退换货编号
   * 
   * @return
   * @throws ServiceException
   */
  public List<String> queryUnconfirmOrderAlterCode() throws ServiceException;

  /**
   * 自动确认退换货
   * 
   * @param orderAlterCode
   * @return
   * @throws ServiceException
   */
  public boolean OrderAlterAutoSure(String orderAlterCode) throws ServiceException;
  
  /**
   * 得到某订单下累计的退货返运费值
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public BigDecimal selectReturnFare(String orderCode) throws ServiceException;

  public Map<String, String> changeAlterStatusYS(String sYS, OrderAlter setOA) throws ServiceException;

  public void updateOrderMainStatus(String orderCode) throws ServiceException;;
}
