package com.pltfm.app.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.DiffAdj;
import com.pltfm.app.entities.ExportInfo;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderPayInfo;
import com.pltfm.app.entities.SellerSettlement;
import com.pltfm.app.vobject.OrderMainVo;

@SuppressWarnings("unchecked")
public interface OrderQryService {

  /**
   * 查询订单列表
   */
  public List listOrder(Map<String, Object> map) throws ServiceException;

  /**
   * 订单列表计数
   */
  public Integer listCount(Map<String, Object> map) throws ServiceException;

  /**
   * 按订单号查询
   */
  public OrderMain getOrderByCode(String orderCode) throws ServiceException;

  /**
   * 按订单号查询已付尾款金额
   */
  public BigDecimal getOrderMoneyByCode(String orderCode) throws ServiceException;

  /**
   * 按订单号查询已付定金金额
   */
  public BigDecimal getOrderPaidDepositByCode(String orderCode) throws ServiceException;

  /**
   * 按客户号查询
   */
  public List getOrderByAccount(String accountCode) throws ServiceException;

  /**
   * 查询sku出货量
   */
  public Map countSKU(Date begin, Date end, List sKU) throws ServiceException;

  /**
   * 按客户账户查询时间范围内的消费情况
   * 
   * @param startDate
   * @param endDate
   * @param account
   * @param status
   * @return
   * @throws ServiceException
   */
  public BigDecimal getPersonalConsume(Map map) throws ServiceException;

  public OrderMain getRootOrderByCode(String orderCode) throws ServiceException;

  public BigDecimal listCountMoney(Map<String, Object> paramMap) throws ServiceException;

  public Integer queryAnalysisAccount(Map<String, String> map) throws ServiceException;

  public List<Map<String, Object>> queryAnalysisReport(Map<String, String> map)
      throws ServiceException;

  public String analysisReportExport(Map<String, String> map, Integer userId)
      throws ServiceException;

  /**
   * 分页查询客户采购数据
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<Map<String, Object>> queryCustomerPurchaseAnalysisByPage(Map<String, String> map)
      throws ServiceException;

  /**
   * 查询客户采购数据条数
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Integer countCustomerPurchaseAnalysis(Map<String, String> map) throws ServiceException;

  /**
   * 导出客户采购数据
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public String queryCustomerPurchaseAnalysis(Map<String, String> map, Integer uid)
      throws ServiceException;

  /**
   * 自营商城订单财务版
   * 
   * @param map
   * @param uid
   * @return
   * @throws ServiceException
   */
  public String ExportFinanceOrderReport(Map<String, String> map, Integer uid)
      throws ServiceException;

  /**
   * 商家订单财务版
   * 
   * @param map
   * @param uid
   * @return
   * @throws ServiceException
   */
  public String exportmerchantsOrderReport(List<Map<String, String>> mapList, Integer uid)
      throws ServiceException;

  public BigDecimal listCountActual(Map<String, Object> paramMap) throws ServiceException;

  /**
   * 模糊查询订单
   */
  public List listFuzzyOrder(Map<String, Object> map) throws ServiceException;

  /**
   * 计数模糊查询订单
   */
  public Integer listCountFuzzyOrder(Map<String, Object> map) throws ServiceException;

  /**
   * 模糊查询订单总金额
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  public BigDecimal listCountFuzzyOrderMoney(Map<String, Object> paramMap) throws ServiceException;

  /**
   * 模糊查询订单总返利金额
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  public BigDecimal listCountFuzzyOrderRebateMoney(Map<String, Object> paramMap)
      throws ServiceException;

  /**
   * 模糊查询订单实际金额
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  public BigDecimal listCountFuzzyOrderActual(Map<String, Object> paramMap) throws ServiceException;

  /**
   * 查询所属商家
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Map listCommercial(Map<String, Object> map) throws ServiceException;

  public Map listOrderData(Map<String, Object> map) throws ServiceException;

  /**
   * 批量根据订单号查询出库信息
   * 
   * @param orderCodes
   * @return
   * @throws ServiceException
   */
  public List queryCarryStockOutVOByOrderCodes(List<String> orderCodes) throws ServiceException;

  /**
   * 跟进订单号查询该订单用户购买次数
   * 
   * @param orderCode
   * @return userId_buyNum
   * @throws ServiceException
   */
  public String queryUserBuyNumByOrderCode(String orderCode) throws ServiceException;

  /**
   * 导出时代订单数据
   * 
   * @param params
   * @return
   * @throws ServiceException
   */
  public Map<String, Object> queryTimesOrderForExport(Map<String, String> params)
      throws ServiceException;

  /**
   * 获取商家信息
   * 
   * @return
   * @throws ServiceException
   */
  public Map<String, String> queryCommerceInfo(String commerceId) throws ServiceException;

  /**
   * 导出结算单详情
   * 
   * @param mapList
   * @param uid
   * @return
   * @throws ServiceException
   */
  public String exportDetailOrder(String roleName, Integer uid, SellerSettlement sellerSettlement,
      List<DiffAdj> diffList) throws ServiceException;

  /**
   * 提供给远程调用 -- 导出结算单详情
   * 
   * @param roleName
   * @param uid
   * @return
   * @throws ServiceException
   */
  public String exportSettleOrder(String roleName, Integer uid) throws ServiceException;

  /**
   * 供应商平台提供的导出所有订单信息
   * 
   * @param params 导出查询参数
   * @return Sting 导出excel的路径
   * @throws ServiceException
   */
  public String exportSellerOrders(Map<String, Object> params) throws ServiceException;

  /**
   * 根据订单号集查询订单信息列表
   * 
   * @param orderCodes
   * @return
   * @throws ServiceException
   */
  public List<OrderMainVo> getOrdersByCodes(List<String> orderCodes) throws ServiceException;

  /**
   * 模糊查询订单总金额,订单实际金额
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  public Map<String, Object> listCountFuzzy(Map<String, Object> paramMap) throws ServiceException;

  /**
   * 同步已支付订单信息到总部系统
   * 
   * @param lstAlterCode
   * @return
   * @throws ServiceException
   */
 /* public int syncOrderInfo2Base(List<String> lstOrderCode) throws ServiceException;*/


  /**
   * 收退款数据记录数
   */
  public Integer orderPayInfoListCount(Map<String, Object> map) throws ServiceException;
  
  /**
   * 查询收退款数据列表
   */
  public List<OrderPayInfo> queryOrderPayInfoList(Map<String, Object> map) throws ServiceException;
  
  /**
   * 导出收退款数据
   * @param map
   * @return
   * @throws ServiceException
   */
  public String ExportOrderPayInfo(Map<String, Object> map,Integer userId) throws ServiceException;
  
  /**
   * 插入导出报表信息
   * @param map
   * @param userName
   * @return
   * @throws ServiceException
   */
  public String insertExportInfo(Map<String, String> map, String userName,String url)
                  throws ServiceException;
  
  /**
   * 商城订单财务版
   * 
   * @param map
   * @param uid
   * @return
   * @throws ServiceException
   */
  public String AsynExportFinanceOrderReport(Map<String, String> map, Integer uid,String writeUrl,String newKey)
      throws ServiceException;
  
  /**
   * 分页查询导出报表信息
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<ExportInfo> queryExportInfo(Map<String,String> map) throws ServiceException;  
  
  /**
   * 查询导出报表信息记录数
   */
  public Integer getExportInfoCount(Map<String, String> map) throws ServiceException;
  
  /**
   * 统计收退款金额
   * @param map
   * @return
   * @throws ServiceException
   */
  public Map<String,String> CountOrderPayMoney(Map<String, Object> map) throws ServiceException;
  
  
  /**
   * 异步导出入驻商家结算报表
   * 
   * @param map
   * @param uid
   * @return
   * @throws ServiceException
   */
  public String AsynExportmerchantsOrderReport(List<Map<String, String>> mapList, Integer uid,String writeUrl,String newKey)
      throws ServiceException;
  
  /**
   * 导出结算单
   * @param map
   * @param userId
   * @return
   * @throws ServiceException
   */
  public String exportFinacialAuditInfo(List<Map<String,Object>> map) throws ServiceException;
  
  /**
   * 查询商家信息
   * @return
   * @throws ServiceException
   */
  public Map<String, String> supplierIdAndMerchantNameMap(Map<String,String> map) throws ServiceException;
  
}
