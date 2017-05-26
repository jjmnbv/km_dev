package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.entities.ExportInfo;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderMainExample;
import com.pltfm.app.entities.OrderMainExt;
import com.pltfm.app.entities.OrderPayInfo;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.OrderMainVo;
import com.pltfm.app.vobject.OrderPayInformForPressellVo;

@Repository("orderMainDAO")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class OrderMainDAOImpl extends BaseDAO implements OrderMainDAO {
  /**
   * 添加
   */
  @Override
  public Long insert(OrderMain record) throws SQLException {
    Object newKey = sqlMapClient.insert("KMORDER_ORDER_MAIN.ibatorgenerated_insert", record);
    OrderMainExt ome = record.getOrderMainext();
    if (null == ome) {
      ome = new OrderMainExt();
    }
    ome.setOrderCode(record.getOrderCode());
    insertOrderMainExt(ome);
    return (Long) newKey;
  }

  /**
   * 添加
   */
  @Override
  public Long insertSelective(OrderMain record) throws SQLException {
    Object newKey =
        sqlMapClient.insert("KMORDER_ORDER_MAIN.ibatorgenerated_insertSelective", record);
    OrderMainExt ome = record.getOrderMainext();
    if (null == ome) {
      ome = new OrderMainExt();
    }
    ome.setOrderCode(record.getOrderCode());
    insertOrderMainExt(ome);
    return (Long) newKey;
  }

  /**
   * 结转使用的查询
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  @Override
  public List selectByOrderExecute(Map map) throws SQLException {
    List list =
        sqlMapClient.queryForList("KMORDER_ORDER_MAIN.ibatorgenerated_selectByOrderExecute", map);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_MAIN
   * 
   * @ibatorgenerated Tue Nov 05 20:06:33 CST 2013
   */
  @Override
  public List selectByExample(OrderMainExample example) throws SQLException {
    List list =
        sqlMapClient.queryForList("KMORDER_ORDER_MAIN.ibatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_MAIN
   * 
   * @ibatorgenerated Tue Nov 05 20:06:33 CST 2013
   */
  @Override
  public OrderMain selectByPrimaryKey(Long orderId) throws SQLException {
    OrderMain key = new OrderMain();
    key.setOrderId(orderId);
    OrderMain record = (OrderMain) sqlMapClient
        .queryForObject("KMORDER_ORDER_MAIN.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 更新
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  @Override
  public int updateByPrimaryKeySelective(OrderMain record) throws SQLException {
    int rows = sqlMapClient.update("KMORDER_ORDER_MAIN.ibatorgenerated_updateByPrimaryKeySelective",
        record);
    return rows;
  }

  /**
   * 更新
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  @Override
  public int updateByPrimaryKey(OrderMain record) throws SQLException {
    int rows = sqlMapClient.update("KMORDER_ORDER_MAIN.ibatorgenerated_updateByPrimaryKey", record);
    try {
      if (record.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Order_Done
          .getKey()) {
        sqlMapClient.update("KMORDER_ORDER_MAIN.SQL_UPDATE_PARENT_FINISH_DATE", record);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return rows;
  }

  /**
   * 更新
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  @Override
  public int updateByOrderCodeSelective(OrderMain record) throws SQLException {
    int rows = sqlMapClient.update("KMORDER_ORDER_MAIN.ibatorgenerated_updateByOrderCodeSelective",
        record);
    return rows;
  }

  @Override
  public List selectByMap(Map<String, Object> map) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectByMap", map);
  }

  @Override
  public BigDecimal countMoneyByMap(Map<String, Object> map) throws SQLException {
    return (BigDecimal) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.countMoneyByMap", map);
  }

  @Override
  public BigDecimal countActualByMap(Map<String, Object> map) throws SQLException {
    return (BigDecimal) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.countActualByMap", map);
  }

  @Override
  public Integer countByMap(Map<String, Object> map) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.countByMap", map);
  }

  @Override
  public void changeOrderStatus(Map map) throws SQLException {
    sqlMapClient.update("KMORDER_ORDER_MAIN.changeOrderStatus", map);
  }

  @Override
  public void updateList(List<OrderMain> records) throws SQLException {
    super.batchUpdateData("KMORDER_ORDER_MAIN.ibatorgenerated_updateByPrimaryKeySelective",
        records);
  }

  /**
   * 批量更新订单状态
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  @Override
  public int batchUpdateOrderStatus(Map<String, Object> params) throws SQLException {
    return sqlMapClient.update("KMORDER_ORDER_MAIN.SQL_BATCH_UPDATE_ORDER_STATUS", params);
  }

  @Override
  public OrderMainVo selectByOrderCode(String orderCode) throws SQLException {
    return (OrderMainVo) sqlMapClient
        .queryForObject("KMORDER_ORDER_MAIN.ibatorgenerated_selectByOrderCode", orderCode);
  }

  @Override
  public BigDecimal selectOrderMoneyByOrderCode(String orderCode) throws SQLException {
    return (BigDecimal) sqlMapClient.queryForObject(
        "KMORDER_ORDER_MAIN.ibatorgenerated_selectOrderMoneyByOrderCode", orderCode);
  }

  @Override
  public BigDecimal selectOrderPaidDepositByOrderCode(String orderCode) throws SQLException {
    return (BigDecimal) sqlMapClient.queryForObject(
        "KMORDER_ORDER_MAIN.ibatorgenerated_selectOrderPaidDepositByOrderCode", orderCode);
  }

  @Override
  public List<OrderMainVo> getOrdersByCodes(Map<String, Object> mapOders) throws SQLException {

    List<OrderMainVo> records = sqlMapClient
        .queryForList("KMORDER_ORDER_MAIN.queryOrdersByCodes", mapOders);
    return records;
  }


  @Override
  public List<OrderMainVo> getOrdersByCodesASCSort(Map<String, Object> mapOders) throws SQLException {

    List<OrderMainVo> records = sqlMapClient
        .queryForList("KMORDER_ORDER_MAIN.queryOrdersByCodesASCSort", mapOders);
    return records;
  }
  
  @Override
  public int updateByOrderCode(OrderMain om) throws SQLException {
    int rows =
        sqlMapClient.update("KMORDER_ORDER_MAIN.ibatorgenerated_updateByOrderCodeSelective", om);
    return rows;
  }

  @Override
  public Map countSKU(Map map) throws SQLException {
    return sqlMapClient.queryForMap("KMORDER_ORDER_MAIN.countSKU", map, "skuCode", "skuCount");
  }

  @Override
  public BigDecimal getPersonalConsume(Map map) throws SQLException {
    return (BigDecimal) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.getPersonalConsume", map);
  }

  @Override
  public List<OrderMainVo> selectRootAndLeafsByOrderCode(String orderCode) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectRootAndLeafsByOrderCode", orderCode);
  }
  
  @Override
  public List<OrderMainVo> selectRootAndLeafsByOrderCodeASCSort(String orderCode) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectRootAndLeafsByOrderCodeASCSort", orderCode);
  }

  @Override
  public OrderMain selectRootOrderByCode(String orderCode) throws SQLException {
    return (OrderMain) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.selectRootOrderByCode",
        orderCode);
  }

  @Override
  public Integer queryAnalysisAccount(Map<String, String> map) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("KMREPORT.SQL_QUERY_ANALYSIS_CCOUNT", map);
  }

  @Override
  public List<Map<String, Object>> queryAnalysisReport(Map<String, String> map)
      throws SQLException {
    return sqlMapClient
        .queryForList("KMREPORT.SQL_QUERY_ANALYSIS_REPORT", map);
  }

  @Override
  public List<Map<String, Object>> analysisReportExport(Map<String, String> map)
      throws SQLException {
    return sqlMapClient
        .queryForList("KMREPORT.SQL_ANALYSIS_REPORT_EXPORT", map);
  }

  @Override
  public Boolean isLastOrder(Map map) throws SQLException {
    Integer rs = (Integer) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.isLastOrder", map);
    return rs == 0;
  }

  /**
   * 分页查询客户采购数据
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
public List<Map<String, Object>> queryCustomerPurchaseAnalysisByPage(Map<String, String> map)
      throws SQLException {
    return sqlMapClient
        .queryForList("KMREPORT.SQL_QUERY_CUSTOMER_PURCHASE_ANALYSIS", map);
  }

  /**
   * 查询客户采购数据条数
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
  public Integer countCustomerPurchaseAnalysis(Map<String, String> map) throws SQLException {
    return (Integer) sqlMapClient
        .queryForObject("KMREPORT.SQL_QUERY_CUSTOMER_PURCHASE_ANALYSIS_COUNT", map);
  }

  /**
   * 查询客户采购数据
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
  public List<Map<String, Object>> queryCustomerPurchaseAnalysis(Map<String, String> map)
      throws SQLException {
    return sqlMapClient
        .queryForList("KMREPORT.SQL_CUSTOMER_PURCHASE_ANALYSIS_EXPORT", map);
  }

  /**
   * 查询自营商城订单财务版数据
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
  public List<List<Map<String, Object>>> queryFinanceOrderReportData(Map<String, String> map)
      throws SQLException {
    List<List<Map<String, Object>>> list = new ArrayList<List<Map<String, Object>>>();
    list.add(sqlMapClient.queryForList("KMREPORT.SQL_QUERY_FINANCE_ORDER_REPORT_DATA", map));
    list.add(sqlMapClient.queryForList("KMREPORT.SQL_QUERY_FINANCE_ORDER_REPORT_REFUND_DATA", map));
    return list;
  }

  /**
   * 查询商家订单财务版数据
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
  public List<List<Map<String, Object>>> queryMerchantsOrderReportData(Map<String, String> map)
      throws SQLException {
    List<List<Map<String, Object>>> list = new ArrayList<List<Map<String, Object>>>();
    list.add(sqlMapClient.queryForList("KMREPORT.SQL_QUERY_MERCHANTS_ORDER_REPORT_DATA", map));
    list.add(sqlMapClient.queryForList("KMREPORT.SQL_QUERY_FINANCE_ORDER_REPORT_REFUND_DATA", map));
    return list;
  }

  /**
  * 
  */
  @Override
  public List<Map<String, Object>> queryDiffAdjReportData(Map<String, Object> map)
      throws SQLException {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    list = sqlMapClient.queryForList("KMREPORT.queryExportTzInfo", map);
    return list;
  }

  @Override
  public void changeOrderInfo(Map<String, String> map) throws SQLException {
    sqlMapClient.update("KMORDER_ORDER_MAIN.changeOrderInfoSelective", map);
  }

  @Override
  public int updateCheckFLagByOrderCode(Map<String, Object> map) throws SQLException {
    int rows = sqlMapClient.update("KMORDER_ORDER_MAIN.changeCheckFlag", map);
    return rows;
  }

  @Override
  public Integer countFuzzyOrderByMap(Map<String, Object> map) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.countFuzzyOrderByMap", map);
  }

  @Override
  public Integer countOutFuzzyOrderByMap(Map<String, Object> map) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.countOutFuzzyOrderByMap", map);
  }

  @Override
  public List<OrderMainVo> selectFuzzyOrderByMap(Map<String, Object> map) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectFuzzyOrderByMap", map);
  }

  @Override
  public List<OrderMainVo> selectOutFuzzyOrderByMap(Map<String, Object> map) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectOutFuzzyOrderByMap", map);
  }

  @Override
  public BigDecimal countOutFuzzyOrderActualByMap(Map<String, Object> paramMap)
      throws SQLException {
    return (BigDecimal) sqlMapClient
        .queryForObject("KMORDER_ORDER_MAIN.countOutFuzzyOrderActualByMap", paramMap);
  }

  @Override
  public BigDecimal countFuzzyOrderActualByMap(Map<String, Object> paramMap) throws SQLException {
    return (BigDecimal) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.countFuzzyOrderActualByMap",
        paramMap);
  }

  @Override
  public BigDecimal countFuzzyOrderMoneyByMap(Map<String, Object> paramMap) throws SQLException {
    return (BigDecimal) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.countFuzzyOrderMoneyByMap",
        paramMap);
  }

  @Override
  public BigDecimal countOutFuzzyOrderMoneyByMap(Map<String, Object> paramMap) throws SQLException {
    return (BigDecimal) sqlMapClient
        .queryForObject("KMORDER_ORDER_MAIN.countOutFuzzyOrderMoneyByMap", paramMap);
  }

  @Override
  public BigDecimal countOutFuzzyOrderRebateMoneyByMap(Map<String, Object> paramMap)
      throws SQLException {
    return (BigDecimal) sqlMapClient
        .queryForObject("KMORDER_ORDER_MAIN.countOutFuzzyOrderRebateMoneyByMap", paramMap);
  }

  @Override
  public List selectCommercialByMap(Map<String, Object> map) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectCommercialByMap", map);
  }

  @Override
  public List selectOrderDataByMap(Map<String, Object> map) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectOrderDataByMap", map);
  }

  @Override
  public BigDecimal countFuzzyOrderMoneyByMap2(Map<String, Object> paramMap) throws SQLException {
    return (BigDecimal) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.countFuzzyOrderMoneyByMap2",
        paramMap);
  }

  @Override
  public BigDecimal countFuzzyOrderActualByMap2(Map<String, Object> paramMap) throws SQLException {
    return (BigDecimal) sqlMapClient
        .queryForObject("KMORDER_ORDER_MAIN.countFuzzyOrderActualByMap2", paramMap);
  }

  /**
   * 批量根据订单号查询订单信息
   * 
   * @param orderCodes
   * @return
   * @throws SQLException
   */
  @Override
public List<OrderMain> queryOrderByOrderCodes(List<String> orderCodes) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.SQL_QUERY_ORDER_MAIN_BY_ORDER_CODES",
        orderCodes);
  }

  /**
   * 跟进订单号查询该订单用户ID和购买次数
   * 
   * @param orderCode
   * @return userId_buyNum
   * @throws SQLException
   */
  @Override
  public String queryUserBuyNumByOrderCode(String orderCode) throws SQLException {
    return (String) sqlMapClient
        .queryForObject("KMORDER_ORDER_MAIN.SQL_QUERY_USER_BUY_NUM_BY_ORDERCODE", orderCode);
  }

  @Override
  public void changeOrderFee(Map map) throws SQLException {
    sqlMapClient.update("KMORDER_ORDER_MAIN.changeOrderFee", map);
  }

  /**
   * 添加订单扩展信息
   * 
   * @param ome
   * @return
   * @throws SQLException
   */
  private Long insertOrderMainExt(OrderMainExt ome) throws SQLException {
    return (Long) sqlMapClient.insert("KMORDER_ORDER_MAIN.SQL_INSERT_ORDER_MAIN_EXT_INFO", ome);
  }

  /**
   * 修改订单扩展信息
   * 
   * @param ome
   * @return
   * @throws SQLException
   */
  @Override
  public int UpdateOrderMainExt(OrderMainExt ome) throws SQLException {
    return sqlMapClient.update("KMORDER_ORDER_MAIN.SQL_UPDATE_ORDER_MAIN_EXT_INFO", ome);
  }

  /**
   * 批量根据订单号查询订单信息结转用
   * 
   * @param orderCodes
   * @return
   * @throws SQLException
   */
  @Override
  public List<OrderMain> queryOrderByOrderCodesForExcute(List<String> orderCodes)
      throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.SQL_QUERY_ORDER_BY_ORDER_CODES_FOR_EXCUTE",
        orderCodes);
  }

  /**
   * 查询用户信息
   * 
   * @return
   * @throws SQLException
   */
  @Override
  public UserInfoDO queryUserInfo(Long uid) throws SQLException {
    return (UserInfoDO) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.SQL_QUERY_USER_INFO", uid);
  }

  @Override
  public OrderMain getParentCode(Map tempMap) throws SQLException {
    return (OrderMain) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.getParentCode", tempMap);

  }

  /**
   * 查询时代销售数据
   * 
   * @return
   * @throws SQLException
   */
  @Override
  public Map<String, BigDecimal> queryTimesSaleInfo(Map<String, String> params)
      throws SQLException {
    return (Map<String, BigDecimal>) sqlMapClient
        .queryForObject("KMREPORT.SQL_QUERY_TIMES_SALE_INFO", params);
  }

  /**
   * 按省份统计时代订单
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  @Override
  public List<Map<String, String>> queryTimesOrderStatisticsByProvince(Map<String, String> params)
      throws SQLException {
    return sqlMapClient
        .queryForList("KMREPORT.SQL_QUERY_TIMES_ORDER_STA_BY_PROVINCE", params);
  }

  /**
   * 获取销量前*产品
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  @Override
  public List<Map<String, String>> queryRankProductBySaleVolume(Map<String, String> params)
      throws SQLException {
    return sqlMapClient
        .queryForList("KMREPORT.SQL_QUERY_RANK_PRODUCT_BY_SALE_VOLUME", params);
  }

  /**
   * 获取销量前*产品
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  @Override
  public List<Map<String, String>> queryRankProductBySaleAmount(Map<String, String> params)
      throws SQLException {
    return sqlMapClient
        .queryForList("KMREPORT.SQL_QUERY_RANK_PRODUCT_BY_SALE_AMOUNT", params);
  }

  /**
   * 获取商家信息
   * 
   * @return
   * @throws SQLException
   */
  @Override
public Map<String, String> queryCommerceInfo(String commerceId) throws SQLException {
    List<Map<String, String>> list = sqlMapClient
        .queryForList("KMORDER_ORDER_MAIN.SQL_QUERY_COMMERCE_INFO", Long.parseLong(commerceId));
    return (null == list || list.isEmpty()) ? null : list.get(0);
  }

  /**
   * 
   * 查询供应商所有订单的订单信息
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  @Override
  public List<OrderMain> querySellerOrderExportData(Map<String, Object> params)
      throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.SQL_QUERY_ORDER_MAIN_EXPORT_INFO", params);
  }

  @Override
  public Map<String, Object> countFuzzyByMap(Map<String, Object> paramMap) throws SQLException {
    return (Map<String, Object>) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.countFuzzyByMap",
        paramMap);
  }

  @Override
  public Boolean checkAccount(String account) throws SQLException {
    Long result = null;
    result = (Long) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.checkAccount", account);
    return result != null ? true : false;
  }

  @Override
  public String getAccount(Long nloginId) throws SQLException {
    String account = "";
    account = (String) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.getAccount", nloginId);
    return account;
  }

  @Override
  public List<OrderMain> querySyncOrderInfo2Base(List<String> lstOrderCode) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.queryOrderInfoSync2Base", lstOrderCode);
  }

  @Override
  public Long getSkuIdTotal() throws SQLException {
    Long total = 0L;
    total = (Long) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.getSkuIdTotal");
    return total;
  }

  @Override
  public List<String> getSkuIdList(Map<String, Object> map) throws SQLException {
    List<String> skucodeList = new ArrayList<String>();
    skucodeList = sqlMapClient.queryForList("KMORDER_ORDER_MAIN.getSkuIdList", map);
    return skucodeList;
  }

  @Override
  public Map<String, String> skuIdAndCount(List<String> skucodeList) throws SQLException {
    Map<String, String> map = new HashMap<String, String>();
    map = sqlMapClient.queryForMap("KMORDER_ORDER_MAIN.skuIdAndCount",
        skucodeList, "skuId", "skuCount");
    return map;
  }

  @Override
  public Long copyToOrdermain(String orderCode) throws SQLException {
    Long re = (Long) sqlMapClient.insert("KMORDER_ORDER_MAIN.copyToOrdermain", orderCode);
    return re;
  }

  @Override
  public Long copyToOrderitem(String orderCode) throws SQLException {
    Long re = (Long) sqlMapClient.insert("KMORDER_ORDER_MAIN.copyToOrderitem", orderCode);
    return re;
  }

  @Override
  public Long copyToOrderpaystatment(String orderCode) throws SQLException {
    Long re = (Long) sqlMapClient.insert("KMORDER_ORDER_MAIN.copyToOrderpaystatment", orderCode);
    return re;
  }

  @Override
  public Long copyToOrderOperate(String orderCode) throws SQLException {
    Long re1 = (Long) sqlMapClient.insert("KMORDER_ORDER_MAIN.copyToOrderOperate1", orderCode);
    Long re2 = (Long) sqlMapClient.insert("KMORDER_ORDER_MAIN.copyToOrderOperate2", orderCode);
    return re1 + re2;
  }

  @Override
  public OrderMain getOrderMainInfoForKJ(String orderCode) throws SQLException {
    OrderMain om = null;
    om = (OrderMain) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.getOrderInfoForKJ", orderCode);
    return om;
  }

  /**
   * 查询订单风控结果条件
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  @Override
  public Map<String, Object> queryOrderRiskResultCondition(Map<String, String> params)
      throws SQLException {
    return (Map<String, Object>) sqlMapClient
        .queryForObject("KMORDER_ORDER_MAIN.SQL_QUERY_ORDER_RISK_RESULT_CONDITION", params);
  }

  /**
   * 风控修改订单状态
   * 
   * @param OrderCode
   * @param status
   * @return
   * @throws SQLException
   */
  @Override
public boolean orderRiskCheckUpdateOrder(String OrderCode, Long status, String estimateContent)
      throws SQLException {
    int result = 0;
    Map<String, String> params = new HashMap<String, String>();
    params.put("orderCode", OrderCode);
    if (OrderDictionaryEnum.Order_Status.Risk_Pass.getKey() == status) {
      params.put("estimateContent", estimateContent);
      result =
          sqlMapClient.update("KMORDER_ORDER_MAIN.SQL_ORDER_RISK_CHECK_UPDATE_ORDER_PASS", params);
    } else if (OrderDictionaryEnum.Order_Status.Risk_Appraise.getKey() == status) {
      result = sqlMapClient
          .update("KMORDER_ORDER_MAIN.SQL_ORDER_RISK_CHECK_UPDATE_ORDER_RISK_APPRAISE", OrderCode);
    } else if (OrderDictionaryEnum.Order_Status.Exception_Order.getKey() == status) {
      params.put("estimateContent", estimateContent);
      result = sqlMapClient.update("KMORDER_ORDER_MAIN.SQL_ORDER_RISK_CHECK_UPDATE_ORDER_UNPASS",
          params);
    }
    return 1 == result;
  }

  /**
   * 取消订单
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  @Override
public int cancelOrder(String orderCode) throws SQLException {
    return sqlMapClient.update("KMORDER_ORDER_MAIN.SQL_UPDATE_CANCEL_ORDER", orderCode);
  }

  @Override
  public List<OrderPayInformForPressellVo> queryOrderPayInformForPressell() throws SQLException {

    return this.sqlMapClient.queryForList("KMORDER_ORDER_MAIN.OrderPayInformForPressell");
  }

  @Override
  public int updateOrderMainExtMessageSendFlag(OrderPayInformForPressellVo vo) throws SQLException {

    return this.sqlMapClient.update("KMORDER_ORDER_MAIN.updateOrderMainExtMessageSendFlag", vo);
  }


  @Override
  public int updateOrderMainStatus(String orderCode) throws SQLException {
    // TODO Auto-generated method stub
    int rows = sqlMapClient.update("KMORDER_ORDER_MAIN.SQL_UPDATE_ORDER_MAIN_YS", orderCode);
    return rows;
  }
  
  @Override
  public List<String> queryCancleOrderForPressell() throws SQLException {
    return this.sqlMapClient.queryForList("KMORDER_ORDER_MAIN.CancelOrderForPressell");
  }

@Override
public Integer selectLoginType(String loginAccout) throws SQLException {
    if(null == loginAccout || "".equals(loginAccout)){
        throw new SQLException("查询账户类型时输入参数loginAccout为空");
    }
    Integer type = (Integer)sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.QUERY_LOGIN_INFO_TYPE", loginAccout);
    return type;
}

@Override
public Integer orderPayInfoListCount(Map<String, Object> map) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.orderPayInfoListCount", map);
}

@Override
public List<OrderPayInfo> queryOrderPayInfoList(Map<String, Object> map) throws SQLException {
    List <OrderPayInfo> payList = new ArrayList<OrderPayInfo>();
    payList = sqlMapClient.queryForList("KMORDER_ORDER_MAIN.queryOrderPayInfoList",map);
    return payList;
}

@Override
public List<Map<String,Object>> queryOrderPayInfoForExport(Map<String, Object> map) throws SQLException {
    List<Map<String,Object>> payList = new ArrayList<Map<String,Object>>();
    payList = sqlMapClient.queryForList("KMORDER_ORDER_MAIN.queryOrderPayInfoForExport",map);
    return payList;
}

@Override
public Long insertExportInfo(ExportInfo ei) throws SQLException {
    Object newKey = sqlMapClient.insert("KMREPORT.exportInfo_insert",ei);
    return (Long) newKey;
}

@Override
public int updateExportInfo(ExportInfo ei) throws SQLException {
    int row = sqlMapClient.update("KMREPORT.exportInfo_updateStatus",ei);
    if(row >1){
       throw new SQLException("修改导出报表信息超过1条,row ="+row+";ExportId="+ei.getExportId()); 
    }
    return row;
}

@Override
public List<ExportInfo> queryExportInfo(Map<String,String> map) throws SQLException {
    List<ExportInfo> exportInfos = new ArrayList<ExportInfo>();
    exportInfos = sqlMapClient.queryForList("KMREPORT.exportInfo_query", map);
    return exportInfos;
}

@Override
public Integer getExportInfoCount(Map<String, String> map) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("KMREPORT.exportInfo_queryCount", map);
}

@Override
public Map<String, String> CountOrderPayMoney(Map<String, Object> map) throws SQLException {
    Map<String,String> reMap = sqlMapClient.queryForMap("KMORDER_ORDER_MAIN.CountOrderPayMoney", map,"pay","return");
    return reMap;
}

@Override
public List<List<Map<String, Object>>> queryShopOrderReportData(Map<String, String> map)
                throws SQLException {
    List<List<Map<String, Object>>> list = new ArrayList<List<Map<String, Object>>>();
    list.add(sqlMapClient.queryForList("KMREPORT.SHOP_ORDER_REPORT_DATA", map));
    list.add(sqlMapClient.queryForList("KMREPORT.SHOP_ORDER_REPORT_REFUND_DATA", map));
    return list;
}

@Override
public List<List<Map<String, Object>>> AsynMerchantsOrderReportData(Map<String, String> map)
                throws SQLException {
    List<List<Map<String, Object>>> list = new ArrayList<List<Map<String, Object>>>();
    list.add(sqlMapClient.queryForList("KMREPORT.ASYN_MERCHANTS_ORDER_REPORT_DATA", map));
    list.add(sqlMapClient.queryForList("KMREPORT.ASYN_ORDER_REPORT_REFUND_DATA", map));
    return list;
}

@Override
public List<Map<String, Object>> AsynDiffAdjReportData(Map<String, Object> map)
                throws SQLException {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    list = sqlMapClient.queryForList("KMREPORT.ASYN_ExportTzInfo", map);
    return list;
}

@Override
public List<MerchantInfoOrSuppliers> getSupplierByCloseStatus(MerchantInfoOrSuppliers record)
                throws SQLException {
    return sqlMapClient.queryForList("KMREPORT.getSupplierByCloseStatus", record);
}

}
