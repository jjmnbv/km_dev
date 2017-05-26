package com.pltfm.app.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.QueryResult;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderMainExt;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.vobject.RefundRequest;

@SuppressWarnings("unchecked")
public interface OrderOperateStatementService {

  /**
   * 批量处理取消支付宝订单
   * 
   * @param rrList 回调实体集合
   * @param refundNo 退款批次号
   * @return
   * @throws ServiceException
   */
  public void batchRefundZFB(List<RefundRequest> rrList, String returnObject)
      throws ServiceException;

  /**
   * 记录订单操作流水
   * 
   * @return
   * @throws SQLException
   * @throws ServiceException
   */
  public Boolean InsertOperate(OrderOperateStatement oost) throws ServiceException;

  /**
   * 记录订单流水
   * 
   * @param orderCode
   * @param orderItemId
   * @param status
   * @param operator
   * @param date
   * @param type
   * @param orderSum
   * @param info
   * @throws ServiceException
   */
  public int save(String orderCode, Long orderItemId, Long status, String operator, Date date,
      Long type, BigDecimal orderSum, String info) throws ServiceException;

  /**
   * 修改订单状态
   */
  public int changeOrderStatus(String operator, String orderCode, Long status, BigDecimal no)
      throws ServiceException;

  /**
   * 删除、恢复订单
   */
  public int changeOrderDisabled(String operator, String orderCode, Long disabled)
      throws ServiceException;

  /**
   * 批量修改订单状态 map中包含(orderIds订单号列表)或(查询条件且checkAll==true)、(status订单状态)
   */
  public int changeOrderStatus(Map map) throws ServiceException;

  /**
   * 更新订单
   * 
   * @param om
   * @throws ServiceException
   */
  public int updateOrderMain(OrderMain om) throws ServiceException;

  /**
   * 批量插入操作流水
   * 
   * @param list
   * @param operator
   * @throws ServiceException
   */
  public void insertByList(List<OrderMain> list, String operator, String info, Long orderStatus,
      Long operatType) throws ServiceException;

  public int changeOrderAssessStatus(String orderCode, Long asssessStatus) throws ServiceException;

  public int updateOrderMainSelective(OrderMain om) throws ServiceException;

  public OrderOperateStatement newOrderOperate(OrderOperateStatement oldRecord, String orderCode,
      Long orderItemId, Long status, String operator, Date date, Long type, BigDecimal orderSum,
      String info);

  public int additional(String orderCode) throws ServiceException;

  public int handle(String orderCode, Short state, String account, Date date)
      throws ServiceException;

  public int changeOrderInfo(Map<String, String> map) throws ServiceException;

  public int medicCheck(String orderCode, Long checkFlag, String account, Date date)
      throws ServiceException;

  /**
   * 修改运费
   * 
   * @param orderCode 订单
   * @param account 操作人
   * @param newFare 邮费
   * @return
   * @throws ServiceException
   */
  public boolean updateFare(String orderCode, String account, BigDecimal newFare)
      throws ServiceException;

  /**
   * 查询待确认收货的订单编号
   * 
   * @return
   * @throws ServiceException
   */
  public List<String> queryUnconfirmReceiptOrderCode() throws ServiceException;

  /**
   * 订单自动确认
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public boolean OrderAutoSure(String orderCode) throws ServiceException;

  /**
   * 延迟收货
   * 
   * @param operator
   * @param ome
   * @return
   * @throws ServiceException
   */
  public boolean delayReceipt(String operator, OrderMainExt ome) throws ServiceException;

  /**
   * 取消前补单
   * 
   * @return
   */
  public boolean additionalBeforeCancle(String orderCode, Long status,QueryResult qr) throws ServiceException;

public int renewExceptionOrder(String operator, String orderCode) throws ServiceException;

  public int saveExceptionOrder(String operator, OrderMain orderMain) throws ServiceException;

  /**
   * 修改风控状态
   * 
   * @param om
   * @param ops
   * @return
   * @throws ServiceException
   */
  public boolean orderRiskCheckUpdateOrder(String orderCode, Long status, String estimateContent,
      OrderOperateStatement ops) throws ServiceException;

  /**
   * 订单号
   * 
   * @param orderCode
   */
  public void pushOrderUserSource(String orderCode);

  /**
   * 生成异常订单是添加黑名单
   * 
   * @param orderCode
   * @param operator
   * @throws Exception
   */
  void addBlackList(String orderCode, String operator) throws Exception;
 
  /**
   * 检查订单是否第三方支付成功
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public boolean noPayOrderCheck(String orderCode) throws ServiceException;
}
