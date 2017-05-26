package com.kmzyc.supplier.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.b2b.service.OrderPvSyncRemoteService;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.remote.ExpressSubscriptionRemoteService;
import com.kmzyc.order.remote.FareRemoteService;
import com.kmzyc.order.remote.OrderAssessRemoteService;
import com.kmzyc.order.remote.OrderCallBackRemoteService;
import com.kmzyc.order.remote.OrderChangeStatusRemoteService;
import com.kmzyc.order.remote.OrderExecuteRemoteService;
import com.kmzyc.order.remote.OrderQryRemoteService;
import com.kmzyc.order.remote.OrderSyncRemoteService;
import com.kmzyc.supplier.dao.OrderMainDAO;
import com.kmzyc.supplier.dao.ProdAppraiseDao;
import com.kmzyc.supplier.model.OrderItems;
import com.kmzyc.supplier.model.OrderMain;
import com.kmzyc.supplier.model.ProdAppraiseAddContent;
import com.kmzyc.supplier.service.OrderService;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.InvoiceItemExample;
import com.pltfm.app.entities.OrderCarry;
import com.pltfm.app.entities.OrderItemExample;
import com.pltfm.app.entities.OrderOperateStatementExample;
import com.pltfm.app.enums.ProductChannel;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.InvoiceVo;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.OrderMainVo;
import com.pltfm.app.vobject.OrderOperateStatementVo;
import com.pltfm.app.vobject.OrderPreferentialEVO;

/**
 * @author tanyunxing
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private ProdAppraiseDao prodAppraiseDao;

    @Resource
    private OrderMainDAO orderMainDao;

    @Resource
    private OrderExecuteRemoteService orderExecuteRemoteService;

    @Resource
    private OrderQryRemoteService orderQryRemoteService;

    @Resource
    private ExpressSubscriptionRemoteService expressSubscriptionRemoteService;

    @Resource
    private OrderCallBackRemoteService orderCallBackRemoteService;

    @Resource
    private OrderChangeStatusRemoteService orderChangeStatusRemoteService;

    @Resource
    private OrderPvSyncRemoteService orderPvSyncRemoteService;

    @Resource
    private OrderSyncRemoteService orderSyncRemoteService;

    @Resource
    private OrderAssessRemoteService orderAssessRemoteService;

    @Resource
    private FareRemoteService fareRemoteService;

    //风控通过
    private final static Integer RISK_PASS = 22;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public List<OrderMain> findOrderList(Map<String, Object> condition) throws ServiceException {
        try {
            List<OrderMain> data = orderMainDao.selectByMap(condition);
            List newData = new LinkedList();
            if (data.size() == 1) {
                // 主子订单
                OrderMain om = data.get(0);
                // 查询该订单下的子订单,包括自身主订单
                List<OrderMainVo> leafs = orderMainDao.selectRootAndLeafsByOrderCode(om.getOrderCode());
                newData.addAll(leafs);
            } else {
                // 根订单集
                Map<String, OrderMain> rootMap = new HashMap<String, OrderMain>();
                for (OrderMain om : data) {
                    // 根订单
                    OrderMain root;
                    // 根订单存在
                    if (null != om.getRootOrderCode()) {
                        root = rootMap.get(om.getRootOrderCode());
                        if (null == root) {// 根订单不在map中
                            root = orderMainDao.selectByOrderCode(om.getRootOrderCode());
                            newData.add(root);
                            rootMap.put(root.getOrderCode(), root);
                        }
                        // 添加在根订单后面,由于linkedList的特殊性,怎么放的顺序输出就是什么顺序
                        newData.add(newData.indexOf(root) + 1, om);
                    } else {
                        // 添加在最后面
                        newData.add(om);
                    }
                }
            }
            return newData;
        } catch (Exception e) {
            logger.error("findOrderList方法获取订单列表时发生异常", e);
            throw new ServiceException(e);
        }

    }

    @Override
    public Integer findOrderListCount(Map<String, Object> condition) throws ServiceException {
        try {
            return orderMainDao.countByMap(condition);
        } catch (Exception e) {
            logger.error("findOrderListCount方法获取订单列表时发生异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OrderCarry> carryOver(Date beginDate, Date endDate, Long commerceId, String orderCode) throws ServiceException {
        Map qureyMap = new HashMap();
        qureyMap.put("StartDate", beginDate);// 结转时间
        qureyMap.put("endDate", endDate);// 结转时间
        qureyMap.put("commerceId", commerceId);
        List<Integer> orderStatus = new ArrayList<Integer>();
        orderStatus.add(RISK_PASS);
        orderStatus.add(OrderDictionaryEnum.Order_Status.Splited_Not_Settle.getKey());
        /*orderStatus.add(OrderDictionaryEnum.Order_Status.Merge_Not_Settle.getKey());*/
        orderStatus.add(OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey());
        qureyMap.put("orderStatus", orderStatus);

        // 新增单个订单结转注入
        if (StringUtils.isNotEmpty(orderCode)) {
            qureyMap.put("execOrderCode", orderCode);
        }
        try {
            return orderExecuteRemoteService.orderExecute(qureyMap);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OrderCarry> carryOverList(Map<String, Object> condition) throws ServiceException {
        try {
            return orderQryRemoteService.getOrderExecutList(condition);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer carryOverListCount(Map<String, Object> condition) throws ServiceException {
        try {
            return orderQryRemoteService.getOrderExecutCount(condition);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public List<OrderItems> findOrderItemByOrderCode(String orderCode) throws ServiceException {
        try {
            OrderItemExample example = new OrderItemExample();
            example.setOrderByClause("ORDER_ITEM_ID ASC");
            OrderItemExample.Criteria criteria = example.createCriteria();
            criteria.andOrderCodeEqualTo(orderCode);
            return orderMainDao.selectOrderItemByExample(example);
        } catch (Exception e) {
            logger.error("findOrderItemByOrderCode方法查询订单商品发生异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public List<OrderOperateStatementVo> findOrderOperateStatementByOrderCode(String orderCode) throws ServiceException {
        try {
            OrderOperateStatementExample example = new OrderOperateStatementExample();
            example.setOrderByClause("STATEMENT_Id ASC");
            OrderOperateStatementExample.Criteria criteria = example.createCriteria();
            criteria.andOrderCodeEqualTo(orderCode);
            return orderMainDao.selectOrderOpStatementByExample(example);
        } catch (Exception e) {
            logger.error("findOrderOperateStatementByOrderCode方法查询订单操作流水发生异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OrderPreferentialEVO> findOrderPreferentialByOrderCode(String orderCode) throws ServiceException {
        try {
            return orderMainDao.queryOrderLevelPreferential(orderCode);
        } catch (Exception e) {
            logger.error("findOrderPreferentialByOrderCode方法查询订单优惠操作发生异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public OrderMainVo findOrderByOrderCode(String orderCode) throws ServiceException {
        try {
            return orderMainDao.selectByOrderCode(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BigDecimal getOrderPay(Map<String, Object> map) throws ServiceException {
        try {
            return orderMainDao.getOrderPay(map);
        } catch (Exception e) {
            logger.error("getOrderPay方法查询订单支付方式发生异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Invoice findInvoiceById(Long id) throws ServiceException {
        try {
            return orderMainDao.selectByPrimaryKeyForInvoice(id);
        } catch (Exception e) {
            logger.error("findInvoiceById方法查询订单发票信息发生异常,发票id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Boolean checkIsAdditional(Map<String, Object> map) throws ServiceException {
        try {
            return orderMainDao.checkIsAdditional(map);
        } catch (Exception e) {
            logger.error("checkIsAdditional方法查询是否需要补单发生异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<InvoiceVo> findInvoiceItemsById(Long id) throws ServiceException {
        try {
            if (null == id) {
                return null;
            }

            InvoiceItemExample example = new InvoiceItemExample();
            example.setOrderByClause("Invoice_Item_Id ASC");
            InvoiceItemExample.Criteria criteria = example.createCriteria();
            criteria.andInvoiceIdEqualTo(id);
            return orderMainDao.selectInvoiceItemByExample(example);
        } catch (Exception e) {
            logger.error("findInvoiceItemsById方法查询订单发票明细发生异常,发票id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public int editOrderRemark(Map<String, String> map) throws ServiceException {
        try {
            return orderChangeStatusRemoteService.changeOrderInfo(map);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String sendLogisticsInfo(LogisticAndDistributionInfoVO logisticAndDistributionInfoVO) throws ServiceException {
        try {
            ExpressSubscription expressSubscription = new ExpressSubscription();
            expressSubscription.setLogisticsCode(logisticAndDistributionInfoVO.getLogisticsCode());
            expressSubscription.setLogisticsNo(logisticAndDistributionInfoVO.getLogisticsOrderNo());
            expressSubscription.setOrderCode(logisticAndDistributionInfoVO.getOrderCode());
            expressSubscription.setChannel(ProductChannel.B2B.getStatus());
            String result = expressSubscriptionRemoteService.sucribeOrderExpressInfo(expressSubscription);
            logger.info("订单号={},订阅物流信息完成!返回结果={}。", new Object[]{logisticAndDistributionInfoVO.getOrderCode(),result});
            return orderCallBackRemoteService.getLogisticNumber4Supplier(logisticAndDistributionInfoVO);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map sycOrderList(List<String> orderMainCodeList) throws ServiceException {
        try {
            return orderPvSyncRemoteService.orderPvSync(orderMainCodeList);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map findSyncOrderList(Map<String, Object> conditionMap) throws ServiceException {
        try {
            return orderSyncRemoteService.getOrderSyncList(conditionMap);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, Object> queryAssessByOrderCode(String orderCode) throws ServiceException {
        try {
            return orderAssessRemoteService.queryAssessByOrderCode(orderCode);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateFare(String orderCode, String userId, BigDecimal newFare) throws ServiceException {
        try {
            return fareRemoteService.changeLogisticsFee(orderCode, userId, newFare);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProdAppraiseAddContent> queryAppraiseByOrderCode(String orderCode) throws ServiceException {
        try {
            return prodAppraiseDao.queryByOrderCode(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ExpressSubscription queryExpressPathInfo(String expressName, String expressNo) throws ServiceException {
        try {
            return expressSubscriptionRemoteService.queryOrderExpressInfo(expressName, expressNo);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer queryUpdateLogisticCount(String orderCode) throws ServiceException {
        try {
            return orderMainDao.queryUpdateLogisticCount(orderCode);
        } catch (Exception e) {
            logger.error("checkIsAdditional方法查询物流信息修改次数发生异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String exportOrder(Map map) throws ServiceException {
        try {
            return orderQryRemoteService.exportSellerOrders(map);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String getFinallyPayEndTime(String orderCode) throws ServiceException {
        try {
            return orderMainDao.getFinallyPayEndTime(orderCode);
        } catch (SQLException e) {
            logger.error("获取订单[{}]的预售活动尾款支付截止时间失败，错误信息{}.", new Object[]{orderCode, e});
            throw new ServiceException(e);
        }
    }
}
