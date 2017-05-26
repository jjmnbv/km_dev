package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.remote.ExpressSubscriptionRemoteService;
import com.pltfm.app.dao.InvoiceDAO;
import com.pltfm.app.dao.InvoiceItemDAO;
import com.pltfm.app.dao.OrderItemDAO;
import com.pltfm.app.dao.OrderOperateStatementDAO;
import com.pltfm.app.dao.OrderPayStatementDAO;
import com.pltfm.app.dao.OrderPreferentialDAO;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.InvoiceItemExample;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderItemExample;
import com.pltfm.app.entities.OrderOperateStatementExample;
import com.pltfm.app.entities.OrderPayStatementExample;
import com.pltfm.app.entities.OrderPreferentialExample;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.sys.util.ErrorCode;

@Service("orderItemQryService")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class OrderItemQryServiceImpl extends BaseService implements OrderItemQryService {
  private static Logger logger = Logger.getLogger(OrderItemQryServiceImpl.class);

  @Resource
  private OrderItemDAO orderItemDAO;
  @Resource
  private OrderOperateStatementDAO orderOperateStatementDAO;
  @Resource
  private OrderPayStatementDAO orderPayStatementDAO;
  @Resource
  private OrderPreferentialDAO orderPreferentialDAO;
  @Resource
  private InvoiceItemDAO invoiceItemDAO;
  @Resource
  private InvoiceDAO invoiceDAO;
  
  @Resource
private ExpressSubscriptionRemoteService expressSubscriptionRemoteService;

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List listOrderItems(String orderCode) throws ServiceException {
    try {
      OrderItemExample example = new OrderItemExample();
      example.setOrderByClause("ORDER_ITEM_ID ASC");
      OrderItemExample.Criteria criteria = example.createCriteria();
      criteria.andOrderCodeEqualTo(orderCode);
      return orderItemDAO.selectByExample(example);
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_PRODUCT_DETAIL_ERROR, "查询订单商品时发生异常："
          + e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List listOrderItemsOut(String orderCode) throws ServiceException {
    try {
      return orderItemDAO.selectOutOrderByExample(orderCode);
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_PRODUCT_DETAIL_ERROR, "查询订单商品时发生异常："
          + e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List listOrderOperates(String orderCode) throws ServiceException {
    try {
      OrderOperateStatementExample example = new OrderOperateStatementExample();
      example.setOrderByClause("STATEMENT_Id ASC");
      OrderOperateStatementExample.Criteria criteria = example.createCriteria();
      criteria.andOrderCodeEqualTo(orderCode);
      return orderOperateStatementDAO.selectByExample(example);
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_OPERATE_DETAIL_ERROR, "查询订单操作时发生异常："
          + e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List listOrderPays(String orderCode) throws ServiceException {
    try {
      OrderPayStatementExample example = new OrderPayStatementExample();
      example.setOrderByClause("PAY_STATEMENT_NO ASC");
      OrderPayStatementExample.Criteria criteria = example.createCriteria();
      criteria.andOrderCodeEqualTo(orderCode);
      return orderPayStatementDAO.selectByExample(example);
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_PAY_DETAIL_ERROR, "查询订单支付时发生异常："
          + e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List listOrderPreferentials(String orderCode, Long type) throws ServiceException {
    try {
      OrderPreferentialExample example = new OrderPreferentialExample();
      example.setOrderByClause("ORDER_PREFERENTIAL_Id ASC");
      OrderPreferentialExample.Criteria criteria = example.createCriteria();
      criteria.andOrderCodeEqualTo(orderCode);
      if (null != type) {
        criteria.andOrderPreferentialTypeEqualTo(type);
      }
      return orderPreferentialDAO.selectByExample(example);
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_PREFERENTIAL_DETAIL_ERROR, "查询订单优惠时发生异常："
          + e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List listOrderInvoiceItems(Long InvoiceId) throws ServiceException {
    try {
      if (null != InvoiceId) {
        InvoiceItemExample example = new InvoiceItemExample();
        example.setOrderByClause("Invoice_Item_Id ASC");
        InvoiceItemExample.Criteria criteria = example.createCriteria();
        criteria.andInvoiceIdEqualTo(InvoiceId);
        return invoiceItemDAO.selectByExample(example);
      } else {
        return null;
      }
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_INVOICE_DETAIL_ERROR, "查询订单发票明细时发生异常："
          + e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public Invoice getInvoiceById(Long invoiceId) throws ServiceException {
    try {
      return invoiceDAO.selectByPrimaryKey(invoiceId);
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_INVOICE_DETAIL_ERROR, "发票流水号" + invoiceId
          + "查询订单发票时发生异常：" + e.getMessage());
    }
  }

  @Override
  public OrderItem getOrderItemById(Long orderItemId) throws ServiceException {
    try {
      return orderItemDAO.selectByPrimaryKey(orderItemId);
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_DETAIL_ERROR, "查询订单项" + orderItemId
          + "时发生异常：" + e.getMessage());
    }
  }

  @Override
  public List listOrderTree(String orderCode) throws ServiceException {
    try {
      return orderItemDAO.selectOrderTree(orderCode);
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "查询订单" + orderCode + "时发生异常："
          + e.getMessage());
    }
  }

  @Override
  public Integer queryGoodsCount(Map<String, String> map) throws ServiceException {
    try {
      return orderItemDAO.queryGoodsCount(map);
    } catch (SQLException e) {
      logger.error("查询商品销售分析表总数异常", e);
      throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR, "查询商品销售分析表总数异常："
          + e.getMessage());
    }
  }

  @Override
  public List<Map<String, Object>> queryGoodsReport(Map<String, String> map)
      throws ServiceException {
    try {
      return orderItemDAO.queryGoodsReport(map);
    } catch (SQLException e) {
      logger.error("查询商品销售分析表数据异常", e);
      throw new ServiceException(0, "查询商品销售分析表数据异常");
    }
  }

  @Override
  public String goodsReportExportExcel(Map<String, String> map, Integer userId)
      throws ServiceException {
    List<String> lable = new ArrayList<String>();
    lable.add("商品SKU");    
    lable.add("商品名称");
    lable.add("供应商");
    lable.add("品牌名");
    lable.add("一级类目");
    lable.add("二级类目");
    lable.add("三级类目");
    lable.add("销售类型");
    lable.add("下单账号");
    lable.add("订单号");
    lable.add("下单时间");
    lable.add("订单来源");
    lable.add("下单类型");
    lable.add("订单类型");
    lable.add("订单金额");
    lable.add("商品实收金额");
    lable.add("支付方式");
    lable.add("商品售价");
    lable.add("成本单价");
    lable.add("商品数量");
    lable.add("成本小计");
    lable.add("佣金比例");
    lable.add("实收佣金");
    lable.add("是否活动");
    lable.add("退货数");
    lable.add("退货金额");   
    System.out.println("----new--report---");
    List<String> key = new ArrayList<String>();
    key.add("COMMODITY_SKU");
    key.add("PROCUCT_NAME");
    key.add("SUPPLIER_NAME");
    key.add("BRAND_NAME");
    key.add("C1");
    key.add("C2");
    key.add("C3");
    key.add("SUPPLIER_TYPE_STR");
    key.add("CUSTOMER_ACCOUNT");
    key.add("ORDER_CODE");
    key.add("CREATE_DATE_STR");
    key.add("ORDER_SOURCE_STR");
    key.add("ORDER_PURCHASER_TYPE_STR");
    key.add("ORDER_TYPE_STR");
    key.add("AMOUNT_PAYABLE");
    key.add("COMMODITY_INCOMING_SUM");
    key.add("PAYMENT_WAY_STR");
    key.add("COMMODITY_CALLED_PRICE");
    key.add("COMMODITY_COST_PRICE");
    key.add("COMMODITY_NUMBER");
    key.add("COST_SUM");
    key.add("COMMISSION_RATE");
    key.add("COMMISSION_MONEY");
    key.add("ORDER_PREFERENTIAL_CODE");
    key.add("ALTER_NUM");
    key.add("ALTER_MONEY_SUM");
    
    // return exportFile(userId.intValue(), "商品销售分析表", lable, key, result);
    return exportFile2007(map, userId.intValue(), "商品销售分析表", lable, key);
  }

  @Override
  protected int getRows(Map map) throws ServiceException {
    try {
      return orderItemDAO.queryGoodsCount(map);
    } catch (SQLException e) {
      throw new ServiceException(0, "");
    }
  };

  @Override
  protected List<Map<String, Object>> getData(Map map) throws ServiceException {
    try {
      return orderItemDAO.queryGoodsReport(map);
    } catch (SQLException e) {
      throw new ServiceException(0, "");
    }
  }

  @Override
  public Boolean isSuit(String orderCode) throws ServiceException {
    try {
      return orderItemDAO.queryIsSuit(orderCode);
    } catch (SQLException e) {
      logger.error("发生错误", e);
      throw new ServiceException(0, "");
    }
  }

  @Override
  public Long selectOverplusNum(Long itemId) throws ServiceException {
    try {
      return orderItemDAO.selectOverplusNum(itemId);
    } catch (SQLException e) {
      logger.error("发生错误", e);
      throw new ServiceException(0, "");
    }
  }

  @Override
  public List<OrderItem> getOrderItemPvByOrderCodes(List<String> orderCodes)
      throws ServiceException {
    try {
      return orderItemDAO.queryOrderItemByOrderCodes(orderCodes);
    } catch (SQLException e) {
      logger.error("发生错误", e);
      throw new ServiceException(0, "");
    }
  }

  @Override
  public List<OrderItem> getOrderItemBySingleOrderCodes(String orderCode) throws ServiceException {
    try {
      return orderItemDAO.queryOrderItemBySingleOrderCodes(orderCode);
    } catch (SQLException e) {
      logger.error("发生错误", e);
      throw new ServiceException(0, "");
    }
  }

  @Override
  public ExpressSubscription queryOrderItemLogisticsInfo(String logisticsCode,
      String logisticsOrderNo) throws ServiceException {
    ExpressSubscription expressSubscription = null;
    try {
      if (StringUtils.isNotEmpty(logisticsCode) && StringUtils.isNotEmpty(logisticsOrderNo)) {
        expressSubscription = expressSubscriptionRemoteService.queryOrderExpressInfo(logisticsCode, logisticsOrderNo);
      }
    } catch (Exception ex) {
      logger.error("请求物流跟踪系统异常!", ex);
      throw new ServiceException(ErrorCode.INNER_SINGLE_LOGISTICS_ERROR, ex.getMessage());
    }
    return expressSubscription;
  }

    @Override
    public Map<String, Object> listGoodsMoney(Map<String, String> map) throws ServiceException {
        // TODO Auto-generated method stub
        try {
            Map<String, Object> moneyMap = new HashMap<String, Object>();
            moneyMap = orderItemDAO.listGoodsMoney(map);
            return moneyMap;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表总金额时发生异常："
                    + e.getMessage());
        }
    }
}
