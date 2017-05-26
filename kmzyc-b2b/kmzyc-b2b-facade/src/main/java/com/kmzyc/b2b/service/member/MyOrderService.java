package com.kmzyc.b2b.service.member;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.CouponAndPromotion;
import com.kmzyc.b2b.model.OrderAlter;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.OrderOperateStatement;
import com.kmzyc.b2b.model.OrderPreferential;
import com.kmzyc.b2b.model.OrderSync;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.page.Pagination;

public interface MyOrderService {
  /**
   * 根据订单号查询根订单
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  /**
   * 查询当前订单是不是主订单
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public BigDecimal findIsMainOrder(String orderCode) throws SQLException;

  public String findRootOrder(String orderCode) throws SQLException;

  public List<OrderOperateStatement> findOrderById(String No) throws SQLException;

  /**
   * 根据查询条件进行分页查询
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public Pagination findOrderMainByPage(Pagination page) throws SQLException;

  /**
   * WAP查全部订单
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public Pagination wapFindOrderMainByPage(Pagination page) throws SQLException;

  /**
   * 删除订单
   * 
   * @param userAccount
   * @param orderMainCode
   * @throws Exception
   */
  public void deleteOrderMain(String userAccount, String orderMainCode) throws Exception;

  /**
   * 取消订单，分两种情况： 1.付款前； 2.付款后送货前，以及送货失败；
   * 
   * @param userAccount
   * @param orderMainCode
   * @param orderMainStatus
   * @throws Exception
   */
  public void cancelOrderMain(String userAccount, String orderMainCode, long orderMainStatus)
      throws Exception;

  /**
   * 根据订单id查询订单信息
   * 
   * @param orderMainId
   * @return
   * @throws SQLException
   */
  public OrderMain findOrderMainById(Integer orderMainId) throws SQLException;

  /**
   * 根据订单code查询订单实体
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public OrderMain findOrderByOrderCode(String orderCode) throws SQLException;

  /**
   * 计算会员特定状态的订单总数
   * 
   * @param userId
   * @return
   * @throws SQLException
   */
  public Integer countOrders(Long userId, List<Integer> orderStatusList) throws SQLException;

  /**
   * 确认完成订单
   * 
   * @param userId
   * @param orderMainCode
   * @param orderMainStatus
   * @throws Exception
   */
  public int sureOrderMain(String userId, String orderMainCode, long orderMainStatus)
      throws Exception;

  /**
   * 计算会员特定状态的待评价总数
   * 
   */
  public Integer countWaitAssess(Long userId, List<Integer> orderStatusList,
      List<Integer> orderAssessList) throws SQLException;

  public List<OrderItem> findAppraiseByOrdercode(String orderCode) throws Exception;

  /**
   * 根据主单parentOrderCode,查找已拆分的子订单集合
   * 
   * @param parentOrderCode 父单的订单CODE luoyi
   */
  public List<OrderMain> findOrderListByParentCode(String parentOrderCode);

  /**
   * 根据主单parentOrderCode,查找已拆分的子订单集合WAP
   * 
   * @param parentOrderCode 父单的订单CODE luoyi
   */
  public List<OrderMain> wapFindOrderListByParentCode(String parentOrderCode);

  /**
   * 根据订单code,查询类型为4=满额送券的优惠券信息
   * 
   * @param orderCode 订单CODE luoyi 2013/11/28
   */
  public List<CouponAndPromotion> findCouponByOrderCode(String orderCode);

  public int findCouponById(int CouponId);

  /**
   * 根据订单code,查询类型为4=满额送券的优惠券信息
   * 
   * @param orderCode 订单CODE wangkai 2014/04/24
   */
  public List<CouponAndPromotion> findCouponAndPromotionByOrderCode(String orderCode);

  /**
   * 查订单对应商品的退货信息 （用来计算同步Pv）
   * 
   * @param map
   * @return OrderAlter
   */
  public OrderAlter findOrderAlterPv(Map map);

  /**
   * 查同步成功的 同步数据
   * 
   * @param orderCode
   * @return
   */
  public List<OrderSync> selectOrderSyncByOrderCode(String orderCode);

  /**
   * 根据订单号或时代会员编号查订单信息
   * 
   * @param map
   * @return
   * @throws Exception
   */
  public List<OrderMain> reconciliation(Map map) throws Exception;

  /**
   * 根据订单号查优惠信息
   * 
   * @param map
   * @return
   * @throws Exception
   */
  public List<OrderPreferential> orderPreferentialList(Map map) throws Exception;

  /**
   * 根据订状态统计我的订单数量
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Map<String, BigDecimal> queryMyOrderStatusCount(Map<String, Object> map)
      throws ServiceException;

  /**
   * 根据订单号统计 订单
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Map<String, BigDecimal> queryMyOrderAddappraiseAndAppraiseItemCount(
      Map<String, Object> map) throws ServiceException;

  /**
   * OMS查询结转订单
   * 
   * @return
   * @throws ServiceException
   */
  public String queryExeOrder(Map<String, String> params) throws ServiceException;

  /**
   * 根据返利网传来的值查询订单
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
//  public List<OrderMain> fandOrderByFanli(Map map) throws ServiceException;

  /**
   * 订单用户来源类型
   * 
   * @param orderMain
   * @throws ServiceException
   */
  public void orderUserSourceType(String cpsvalue, com.pltfm.app.entities.OrderMain orderMain,
      List<com.pltfm.app.entities.OrderItem> oilist) throws ServiceException;

/*  *//**
   * 订单号
   * 
   * @param orderCode
   *//*
  public void pushOrderUserSource(String orderCode);*/

  /**
   * 允许启用支付密码
   * 
   * @return
   * @throws ServiceException
   */
  public int enablePayPWD(Long uid) throws ServiceException;

  /**
   * 找订单流程排除风控流程
   * 
   * @param No
   * @return
   * @throws SQLException
   */
  List<OrderOperateStatement> findOperateByNo(String No) throws SQLException;

  /**
   * 根据skuId查找产品的默认图片信息（主图）
   * 
   * @param sqlId
   * @param skuCode
   * @throws SQLException
   * @return
   */
  public ProductImage findDefaultProductImageBySkuCode(String sqlId, String skuCode)
      throws SQLException;

  /**
   * 统计时代会员pv值
   * 
   * @param customerId
   * @return
   */
  public BigDecimal sumPvByCustomerId(long customerId);
  
  /**
   * 根据订单编码获得对应支付时间
   * 
   * @param orederCode
   * @return
   */
  public String findReserveByOrderCode(String orederCode, Long orderStatus) throws SQLException;
  
  /**
   * 根据订单编码获得对应支付状态
   * 
   * @param orederCode
   * @return
   */
  public String[] findReserveByOrderState(String orederCode) throws SQLException;
  
  /**
   * 根据订单编码获得对应支付各金额
   * 
   * @param orederCode
   * @return
   */
  public String[] findReserveByOrderMoney(String orederCode, Integer orderType) throws SQLException;
  
  /**
   * 根据订单编码获得对应支付手机号
   * 
   * @param orederCode
   * @return
   */
  public String findReserveByOrderPhone(String orederCode) throws SQLException;
  
  /**
   * 根据订单编码获得所有流水状态
   * 
   * @param orederCode
   * @return
   */
  public List<Map<String, Object>> findStateByOrderCode(String orederCode) throws SQLException;
  
  /**
   * 根据订单编码获得对应支付时间,返回map
   * 
   * @param orederCode
   * @return
   * @throws SQLException 
   */
  Map<String, Object> findReserveByOrderCode(String orederCode) throws SQLException;
  /**
   * 根据订单号查询预售活动状态
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public Integer findPresellStatusByOrderCode(String orderCode) throws SQLException;
  
  /**
   * 根据订单号获得尾款支付截止时间
   * @param orederCode
   * @return
   * @throws SQLException
   */
  public String findPayEndTimeByOrderCode(String orederCode) throws SQLException;
  
  /**
   * 根据订单编码判断是否可以支付定金
   * 
   * @param orederCode
   * @return
   * @throws SQLException 
   */
  public String judgeDepositTimeByOrderMoney(String orederCode) throws SQLException;
  
  /**
   * 根据订单编码获得对应各金额
   * 
   * @param orederCode
   * @return
   * @throws SQLException 
   */
  
  public String[] findDepositTailByOrderCode(String orederCode, Integer orderType) throws SQLException;
  
  /**
   * 根据订单号查询是否支付定金和尾款状态
   * 
   * @param orederCode
   * @return
   * @throws SQLException 
   */
  public String findDepositTailStateByOrderCode(String orederCode) throws SQLException;
  
}
