package com.kmzyc.order.remote;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.vobject.OrderAlterVo;

/**
 * 订单查询接口
 * 
 * @author chenwei
 * @date 2013.08.09
 */
@SuppressWarnings("unchecked")
public interface OrderAlterQryRemoteService extends Serializable {
  /**
   * 根据退换货批次号获取退换货操作流水
   * 
   * @param orderAlterId
   * @return
   * @throws ServiceException
   */
  public List listOrderAlterOperates(String alterCode) throws ServiceException;

  /**
   * 查询退换货，提供给咨询工具
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List queryOrderAlterListForConsultation(Map<String, Object> map) throws ServiceException;

  /**
   * 查询退换货列表
   */
  public List<OrderAlterVo> queryOrderAlterListByMap(Map<String, Object> map) throws ServiceException;

  /**
   * 查询退换货订单数量
   */
  public int countOrderAlterListByMap(Map<String, Object> map) throws ServiceException;

  /**
   * 查询退换货明细
   */
  public OrderAlter getOrderAlterByCode(String orderCode) throws ServiceException;

  /**
   * 查询退换货支付流水
   */
  public List listOrderAlterPays(String alterCode) throws ServiceException;

 /**
  * 
  * @param operatorType
  * @param operator
  * @param alterCode
  * @param type
  * @param fareSubsidy 补偿运费
  * @param returnMoney 商品退款金额
  * @param returnFare  退货返运费
  * @param returnSum   退款总金额
  * @param preferentialAmount
  * @param comment
  * @return
  * @throws ServiceException
  */
  public int checkOrderAlter(Integer operatorType, String operator, String alterCode, Short type,
     BigDecimal fareSubsidy,BigDecimal returnMoney,BigDecimal returnFare,BigDecimal returnSum, BigDecimal preferentialAmount, String comment)
      throws ServiceException;

  /**
   * 确认退款 或者还回原件
   */
  public int changeAlterStatus(Integer operatorType, String sysOperate, OrderAlter oa)
      throws ServiceException;

  /**
   * 供应商的商品详情查询
   */
  public OrderItem getOrderItemById(Long itemId) throws ServiceException;

  /**
   * 产品图片接口
   */
  public List getPhotoByBatchNo(String photoBatchNo) throws ServiceException;

  /**
   *判断订单是否包含活动商品
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public boolean isSuit(String orderCode) throws ServiceException;

  /**
   * 判断是否需要补单
   */
  public boolean checkIsAdditional(String orderAlterCode) throws ServiceException;
  
  /**
   * 取得某订单下累计的退货返运费值
   * @param orderCode  订单号
   * @return
   * @throws ServiceException
   */
  public BigDecimal selectReturnFare(String orderCode) throws ServiceException;
}
