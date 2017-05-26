package com.pltfm.app.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.entities.OrderPreferential;
import com.pltfm.app.vobject.PaymentVO;

@SuppressWarnings("unchecked")
public interface OrderPayStatementService {

  /**
   * 记录订单支付流水
   * 
   * @param paymentWay 支付方式
   * @param state 支付状态
   * @param account 客户账号
   * @param orderCode 订单号
   * @param orderMoney 付款金额
   * @param createDate 生成日期
   * @param endDate 支付完成日期
   * @param outsidePayStatementNo 第三方支付流水号
   * @param flag 付款/退款标识
   * @param preferentialNo 优惠券号
   * @param bankName 银行名称
   * @param platFormName 支付平台名称
   * @param bankCode 银行代码
   * @param platFormCode 支付平台代码
   * @throws ServiceException
   */
  public void save(Long paymentWay, Long state, String account, String orderCode,
      BigDecimal orderMoney, Date createDate, Date endDate, String outsidePayStatementNo,
      Long flag, BigDecimal preferentialNo, String bankName, String platFormName, String bankCode,
      String platFormCode) throws ServiceException;

  /**
   * 订单支付接口
   * 
   * @param operator 操作人
   * @param paymentWay 支付方式
   * @param account 客户账号id
   * @param orderCode 订单号
   * @param orderMoney 付款金额
   * @param outsidePayStatementNo 第三方支付流水号
   * @param flag 付款/退款标识
   * @param preferentialNo 优惠券编号
   * @param bankName 支付银行名称
   * @param platFormName 第三方支付平台名称
   * @param bankCode 支付银行代码
   * @param platFormCode 支付平台代码
   * @return
   * @throws ServiceException
   */
  public int pay(String operator, Long paymentWay, String account, String orderCode,
      BigDecimal orderMoney, String outsidePayStatementNo, Long flag, BigDecimal preferentialNo,
      BigDecimal preferentialGrantId, Long status, Long state, String platFormCode,
      String platFormName) throws ServiceException;

  /**
   * 获取各种支付方式的支付总额
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public BigDecimal getOrderPay(Map map) throws ServiceException;

  public BigDecimal getNotPay(String orderCode) throws ServiceException;

  /**
   * 获取订单实付金额（余额支付+在线支付）
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public BigDecimal getOrderRealPay(Map map) throws ServiceException;

  /**
   * 批量处理支付
   * 
   * @param payList
   * @return 1为成功； -2为订单支付、操作流水/支付流水记录、锁库存、修改订单状态（如果满足条件）成功 但是会员积分和等级操作失败
   * @throws ServiceException
   */
  public int batchPay(PaymentVO paymentVO) throws ServiceException;

  /**
   * 批量保存支付流水记录
   * 
   * @param paymentVOList
   * @return 1为成功，-1为失败
   * @throws ServiceException
   */
  public int savePayStatement(List<OrderPayStatement> paymentVOList) throws ServiceException;

  public BigDecimal getPayPreferentialNoByOrderCode(String orderCode) throws ServiceException;

  // public List<BigDecimal> getPreferentialNoByOrderCode(String
  // orderCode,Long type) throws ServiceException;

  public Boolean checkFinish(String orderCode, BigDecimal orderMoney, BigDecimal amountPayable,
      Long flag) throws ServiceException;

  public List<OrderPreferential> getPreferentialByOrderCode(String orderCode, Long type)
      throws ServiceException;

  public List<OrderPayStatement> getPayPreferentialByOrderCode(String orderCode)
      throws ServiceException;

  public List<Map<String, Object>> querySaleReport(Map<String, String> map) throws ServiceException;

  public Integer querySaleReportCount(Map<String, String> map) throws ServiceException;

  public String saleReportExportExcel(Map<String, String> map, Integer userID)
      throws ServiceException;

  /**
   * 导出文件
   * 
   * @param sheetName
   * @param lable
   * @param data
   * @return
   * @throws ServiceException
   */
  public String exportFile(int uid, String sheetName, List<String> lable, List<String> key,
      List<Map<String, Object>> data) throws ServiceException;

  /**
   * 查询优惠券的状态
   */
  public Long queryCouponGrantByCounponGrantId(Long couponGrantId) throws ServiceException;

  /**
   * 获取订单支付信息
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public Map<String, BigDecimal> getOrderPayInfo(String orderCode) throws ServiceException;

  /**
   * 获取某订单康美通历史支付总额
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public BigDecimal getOrderPayByOrderCode(String orderCode) throws ServiceException;

  /**
   * 锁库存操作
   * 
   * @param paymentVO
   * @return
   * @throws ServiceException
   */
  public int lockStock(PaymentVO paymentVO) throws ServiceException;

  /**
   * 是否允许补单
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Boolean checkIsAdditional(Map map) throws ServiceException;

int pay(String operator, Long paymentWay, String account, String orderCode, BigDecimal orderMoney,
        String outsidePayStatementNo, Long flag, BigDecimal preferentialNo,
        BigDecimal preferentialGrantId, Long status, Long state, String platFormCode,
        String platFormName, String ysFlage) throws ServiceException;
}
