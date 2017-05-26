package com.pltfm.app.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.entities.ExportInfo;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderMainExample;
import com.pltfm.app.entities.OrderMainExt;
import com.pltfm.app.entities.OrderPayInfo;
import com.pltfm.app.vobject.MerchantVo;
import com.pltfm.app.vobject.OrderMainVo;
import com.pltfm.app.vobject.OrderPayInformForPressellVo;

@SuppressWarnings("rawtypes")
public interface OrderMainDAO {

  /**
   * 添加
   */
  Long insert(OrderMain record) throws SQLException;

  /**
   * 添加
   */
  Long insertSelective(OrderMain record) throws SQLException;

  /**
   * 查询
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  List selectByExample(OrderMainExample example) throws SQLException;

  /**
   * 结转调用
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  List selectByOrderExecute(Map map) throws SQLException;

  /**
   * 根据订单ID查询
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  OrderMain selectByPrimaryKey(Long orderId) throws SQLException;

  /**
   * 以订单编码查询订单实体
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  OrderMainVo selectByOrderCode(String orderCode) throws SQLException;

  /**
   * 以订单编码查询订单已付尾款
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  BigDecimal selectOrderMoneyByOrderCode(String orderCode) throws SQLException;

  /**
   * 以订单编码查询订单已付定金
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  BigDecimal selectOrderPaidDepositByOrderCode(String orderCode) throws SQLException;

  /**
   * 更新
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKeySelective(OrderMain record) throws SQLException;

  /**
   * 更新
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByOrderCodeSelective(OrderMain record) throws SQLException;

  /**
   * 更新
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKey(OrderMain record) throws SQLException;

  /**
   * Map方式查询订单列表
   */
  List selectByMap(Map<String, Object> map) throws SQLException;

  BigDecimal countMoneyByMap(Map<String, Object> map) throws SQLException;

  /**
   * Map方式查询订单计数
   */
  Integer countByMap(Map<String, Object> map) throws SQLException;

  /**
   * 批量修改订单状态
   */
  void changeOrderStatus(Map map) throws SQLException;

  void updateList(List<OrderMain> records) throws SQLException;

  /**
   * 批量更新订单状态
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public int batchUpdateOrderStatus(Map<String, Object> params) throws SQLException;

  public int updateByOrderCode(OrderMain om) throws SQLException;

  /**
   * 查询sku出货量
   */
  public Map countSKU(Map map) throws SQLException;

  /**
   * 按客户账户查询时间范围内的消费情况
   * 
   * @param startDate
   * @param endDate
   * @param account
   * @param status
   * @return
   */
  BigDecimal getPersonalConsume(Map map) throws SQLException;

  List<OrderMainVo> selectRootAndLeafsByOrderCode(String orderCode) throws SQLException;

  OrderMain selectRootOrderByCode(String orderCode) throws SQLException;

  Integer queryAnalysisAccount(Map<String, String> map) throws SQLException;

  List<Map<String, Object>> queryAnalysisReport(Map<String, String> map) throws SQLException;

  List<Map<String, Object>> analysisReportExport(Map<String, String> map) throws SQLException;

  Boolean isLastOrder(Map nmap) throws SQLException;

  /**
   * 分页查询客户采购数据
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<Map<String, Object>> queryCustomerPurchaseAnalysisByPage(Map<String, String> map)
      throws SQLException;

  /**
   * 查询客户采购数据条数
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Integer countCustomerPurchaseAnalysis(Map<String, String> map) throws SQLException;

  /**
   * 查询客户采购数据
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<Map<String, Object>> queryCustomerPurchaseAnalysis(Map<String, String> map)
      throws SQLException;

  /**
   * 查询自营商城订单财务版数据
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<List<Map<String, Object>>> queryFinanceOrderReportData(Map<String, String> map)
      throws SQLException;

  /**
   * 查询商家订单财务版数据
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<List<Map<String, Object>>> queryMerchantsOrderReportData(Map<String, String> map)
      throws SQLException;

  public void changeOrderInfo(Map<String, String> map) throws SQLException;

  public BigDecimal countActualByMap(Map<String, Object> map) throws SQLException;

  // 修改审核标志位
  public int updateCheckFLagByOrderCode(Map<String, Object> map) throws SQLException;

  /**
   * 模糊计数查询订单
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public Integer countFuzzyOrderByMap(Map<String, Object> map) throws SQLException;

  /**
   * 模糊计数查询订单外部来源的·
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public Integer countOutFuzzyOrderByMap(Map<String, Object> map) throws SQLException;

  /**
   * 模糊查询订单
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public List<OrderMainVo> selectFuzzyOrderByMap(Map<String, Object> map) throws SQLException;

  /**
   * 模糊查询订单外部来源的·
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public List<OrderMainVo> selectOutFuzzyOrderByMap(Map<String, Object> map) throws SQLException;


  public BigDecimal countOutFuzzyOrderActualByMap(Map<String, Object> paramMap) throws SQLException;

  public BigDecimal countFuzzyOrderActualByMap(Map<String, Object> paramMap) throws SQLException;

  public BigDecimal countFuzzyOrderMoneyByMap(Map<String, Object> paramMap) throws SQLException;

  public BigDecimal countOutFuzzyOrderMoneyByMap(Map<String, Object> paramMap) throws SQLException;

  public BigDecimal countOutFuzzyOrderRebateMoneyByMap(Map<String, Object> paramMap)
      throws SQLException;

  public List<MerchantVo> selectCommercialByMap(Map<String, Object> map) throws SQLException;

  /**
   * Map方式查询订单列表
   */
  public List selectOrderDataByMap(Map<String, Object> map) throws SQLException;

  public BigDecimal countFuzzyOrderMoneyByMap2(Map<String, Object> paramMap) throws SQLException;

  public BigDecimal countFuzzyOrderActualByMap2(Map<String, Object> paramMap) throws SQLException;

  /**
   * 批量根据订单号查询订单信息
   * 
   * @param orderCodes
   * @return
   * @throws SQLException
   */
  public List<OrderMain> queryOrderByOrderCodes(List<String> orderCodes) throws SQLException;

  /**
   * 跟进订单号查询该订单用户购买次数
   * 
   * @param orderCode
   * @return userId_buyNum
   * @throws SQLException
   */
  public String queryUserBuyNumByOrderCode(String orderCode) throws SQLException;

  /**
   * 修改运费
   * 
   * @param map
   * @throws SQLException
   */
  public void changeOrderFee(Map map) throws SQLException;

  /**
   * 修改订单扩展信息
   * 
   * @param ome
   * @return
   * @throws SQLException
   */
  public int UpdateOrderMainExt(OrderMainExt ome) throws SQLException;

  /**
   * 批量根据订单号查询订单信息结转用
   * 
   * @param orderCodes
   * @return
   * @throws SQLException
   */
  public List<OrderMain> queryOrderByOrderCodesForExcute(List<String> orderCodes)
      throws SQLException;

  /**
   * 查询用户信息
   * 
   * @return
   * @throws SQLException
   */
  public UserInfoDO queryUserInfo(Long uid) throws SQLException;

  public OrderMain getParentCode(Map tempMap) throws SQLException;

  /**
   * 查询时代销售数据
   * 
   * @return
   * @throws SQLException
   */
  public Map<String, BigDecimal> queryTimesSaleInfo(Map<String, String> params) throws SQLException;

  /**
   * 按省份统计时代订单
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<Map<String, String>> queryTimesOrderStatisticsByProvince(Map<String, String> params)
      throws SQLException;

  /**
   * 获取销量前*产品
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<Map<String, String>> queryRankProductBySaleVolume(Map<String, String> params)
      throws SQLException;

  /**
   * 获取销量前*产品
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<Map<String, String>> queryRankProductBySaleAmount(Map<String, String> params)
      throws SQLException;

  /**
   * 获取商家信息
   * 
   * @return
   * @throws SQLException
   */
  public Map<String, String> queryCommerceInfo(String commerceId) throws SQLException;

  /**
   * 差异调整导出查询
   * 
   * @param map
   * @return
   */
  public List<Map<String, Object>> queryDiffAdjReportData(Map<String, Object> map)
      throws SQLException;

  /**
   * 
   * 查询供应商所有订单的订单信息
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  List<OrderMain> querySellerOrderExportData(Map<String, Object> params) throws SQLException;

  /**
   * 根据订单编号集查询订单列表信息
   * 
   * @param mapOders
   * @return
   * @throws SQLException
   */
  List<OrderMainVo> getOrdersByCodes(Map<String, Object> mapOders) throws SQLException;

  public Map<String, Object> countFuzzyByMap(Map<String, Object> paramMap) throws SQLException;

  /**
   * 根据账号查询账户信息表里里是否存在这条数据
   * 
   * @param account 账户
   * @return
   * @throws SQLException
   */
  public Boolean checkAccount(String account) throws SQLException;

  /**
   * 根据登陆Id查询账号
   * 
   * @param nloginId
   * @return
   * @throws SQLException
   */
  public String getAccount(Long nloginId) throws SQLException;

  /**
   * 查询skuId总数
   * 
   * @return
   * @throws SQLException
   */
  public Long getSkuIdTotal() throws SQLException;

  /**
   * 获取skuId集合
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public List<String> getSkuIdList(Map<String, Object> map) throws SQLException;

  /**
   * 获得各个skuId对应的销量
   * 
   * @param skucodeList
   * @return
   * @throws SQLException
   */
  public Map<String, String> skuIdAndCount(List<String> skucodeList) throws SQLException;

  /**
   * 砍价活动 --拷贝数据到订单表
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public Long copyToOrdermain(String orderCode) throws SQLException;

  /**
   * 砍价活动 --拷贝数据到订单明细表
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public Long copyToOrderitem(String orderCode) throws SQLException;

  /**
   * 砍价活动 --拷贝数据到订单支付流水表
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public Long copyToOrderpaystatment(String orderCode) throws SQLException;

  /**
   * 砍价活动 --生成订单操作流水数据
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public Long copyToOrderOperate(String orderCode) throws SQLException;

  /**
   * 查询砍价虚拟订单主表
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public OrderMain getOrderMainInfoForKJ(String orderCode) throws SQLException;

  /**
   * 查询订单风控结果条件
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public Map<String, Object> queryOrderRiskResultCondition(Map<String, String> params)
      throws SQLException;

  /**
   * 风控修改订单状态
   * 
   * @param OrderCode
   * @param status
   * @return
   * @throws SQLException
   */
  public boolean orderRiskCheckUpdateOrder(String OrderCode, Long status, String estimateContent)
      throws SQLException;

  /**
   * 取消订单
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public int cancelOrder(String orderCode) throws SQLException;

  /**
   * 查询需要同步的订单信息
   * 
   * @param lstOrderCode 需要同步的订单编码
   * @return
   * @throws SQLException
   */
  public List<OrderMain> querySyncOrderInfo2Base(List<String> lstOrderCode) throws SQLException;

  /**
   * 查询待发送支付尾款通知的订单
   * 
   * @return
   * @throws SQLException
   */
  public List<OrderPayInformForPressellVo> queryOrderPayInformForPressell() throws SQLException;

  /**
   * 批量修改订单扩展表，短信发送标识
   * 
   * @param vo
   * @return
   * @throws SQLException
   */
  public int updateOrderMainExtMessageSendFlag(OrderPayInformForPressellVo vo) throws SQLException;

  public int updateOrderMainStatus(String orderCode) throws SQLException;
  /**
   * 查询超过定金支付时间且未付款的订单号
   * TODO 描述这个方法的作用<br/> 
   *
   * @author KM 
   * @return
   * @throws SQLException
   */
  public List<String> queryCancleOrderForPressell() throws SQLException;

  public List<OrderMainVo> selectRootAndLeafsByOrderCodeASCSort(String orderCode) throws SQLException;

  public List<OrderMainVo> getOrdersByCodesASCSort(Map<String, Object> mapCodes) throws SQLException;
  
  /**
   * 查询用户类型
   * @param loginAccout
   * @return 1:普通用户；2：时代用户；3：准时代用户；4：游客
   * @throws SQLException
   */
  public Integer selectLoginType(String loginAccout) throws SQLException;
  
  /**
   * 收退款数据记录数
   * @param map
   * @return
   * @throws SQLException
   */
  public Integer orderPayInfoListCount(Map<String,Object> map) throws SQLException;
  
  /**
   * 查询收退款数据列表
   * @param map
   * @return
   * @throws SQLException
   */
  public List<OrderPayInfo> queryOrderPayInfoList(Map<String, Object> map) throws SQLException;
  
  /**
   * 统计收退款金额
   * @param map
   * @return
   * @throws SQLException
   */
  public Map<String,String> CountOrderPayMoney(Map<String, Object> map) throws SQLException;
  
  /**
   * 查询需要导出数据
   * @param map
   * @return
   * @throws SQLException
   */
  public List<Map<String,Object>> queryOrderPayInfoForExport(Map<String, Object> map) throws SQLException;
  
  /**
   * 添加数据导出报表信息记录
   * @param ei
   * @return
   * @throws SQLException
   */
  public Long insertExportInfo(ExportInfo ei) throws SQLException;
  
  /**
   * 添加数据导出报表信息记录
   * @param ei
   * @return
   * @throws SQLException
   */
  public int updateExportInfo(ExportInfo ei) throws SQLException;
  
  /**
   * 查询导出报表信息
   * @return
   * @throws SQLException
   */
  public List<ExportInfo> queryExportInfo(Map<String,String> map) throws SQLException;
  
  /**
   * 查询导出报表信息
   * @return
   * @throws SQLException
   */
  public Integer getExportInfoCount(Map<String,String> map) throws SQLException;
  
  
  /**
   * 查询商城订单财务版数据
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<List<Map<String, Object>>> queryShopOrderReportData(Map<String, String> map)
      throws SQLException;
 
 /** 
  * 查询入驻商家结算报表数据
  * 
  * @param map
  * @return
  * @throws ServiceException
  */
 public List<List<Map<String, Object>>> AsynMerchantsOrderReportData(Map<String, String> map)
     throws SQLException;
  
 /**
  * 查询入驻商家差异调整数据
  * 
  * @param map
  * @return
  */
 public List<Map<String, Object>> AsynDiffAdjReportData(Map<String, Object> map)
     throws SQLException;
 
 
 /**
  * 查询供应商和商户的信息
  *
  * @return
  * @throws SQLException
  */
 List<MerchantInfoOrSuppliers> getSupplierByCloseStatus(MerchantInfoOrSuppliers record) throws SQLException;
}
