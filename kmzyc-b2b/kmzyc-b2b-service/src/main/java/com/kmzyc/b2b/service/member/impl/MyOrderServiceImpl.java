package com.kmzyc.b2b.service.member.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.LoginDao;
import com.kmzyc.b2b.dao.OrderAlterDao;
import com.kmzyc.b2b.dao.OrderOperateStatementDao;
import com.kmzyc.b2b.dao.ProductSkuDao;
import com.kmzyc.b2b.dao.member.MyOrderDao;
import com.kmzyc.b2b.dao.member.OrderSyncDAO;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.CouponAndPromotion;
import com.kmzyc.b2b.model.OrderAlter;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.OrderOperateStatement;
import com.kmzyc.b2b.model.OrderPreferential;
import com.kmzyc.b2b.model.OrderSync;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.model.QueryResult;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.EraInfoService;
import com.kmzyc.b2b.service.ProductStockService;
import com.kmzyc.b2b.service.QryOrderOnLineService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.util.SerializeUtil;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.b2b.vo.ExeOrderData;
import com.kmzyc.framework.constants.OrderDictionaryEnums;
import com.kmzyc.framework.exception.DaoException;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.framework.util.OrderUserSource;
import com.kmzyc.order.remote.OrderChangeStatusRemoteService;
import com.kmzyc.order.remote.OrderItemQryRemoteService;
import com.kmzyc.order.remote.OrderSyncRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.util.OrderDictionaryEnum;

import oracle.sql.TIMESTAMP;
import redis.clients.jedis.JedisCluster;


@SuppressWarnings("unchecked")
@Service
public class MyOrderServiceImpl implements MyOrderService {

    // private static Logger logger = Logger.getLogger(MyOrderServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(MyOrderServiceImpl.class);

    @Resource(name = "myOrderDaoImpl")
    private MyOrderDao myOrderDao;

    @Resource(name = "productSkuDaoImpl")
    private ProductSkuDao productSkuDao;

    @Resource(name = "orderAlterDaoImpl")
    private OrderAlterDao orderAlterDao;

    @Resource(name = "orderOperateStatementDao")
    private OrderOperateStatementDao orderOperateStatementDao;
    @Resource(name = "eraInfoServiceImpl")
    private EraInfoService eraInfoService;


    @Resource(name = "securityCentreServiceImpl")
    private SecurityCentreService securityCentreService;

    @Resource(name = "orderSyncDAOImpl")
    private OrderSyncDAO orderSyncDAO;
    @Resource(name = "productStockServiceImpl")
    private ProductStockService productStockService;

    @Resource(name = "loginDaoImp")
    private LoginDao loginDao;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    @Resource
    private OrderChangeStatusRemoteService orderChangeStatusRemoteService;
    @Resource
    private OrderItemQryRemoteService orderItemQryRemoteService;

    @Resource
    private OrderSyncRemoteService orderSyncRemoteService;

    @Resource(name = "qryOrderOnLineService")
    private QryOrderOnLineService qry;

    /**
     * 根据订单号查询根订单
     */
    @Override
    public String findRootOrder(String orderCode) throws SQLException {

        return myOrderDao.findRootOrderCode(orderCode);
    }

    /**
     * 根据订单编号获取系统操作流水
     */
    @Override
    public List<OrderOperateStatement> findOrderById(String No) throws SQLException {

        List<OrderOperateStatement> listorder;
        try {
            listorder = orderOperateStatementDao.findByOrderNo(No);
        } catch (DaoException e) {
            logger.error("获取系统操作流水失败" + e.getMessage(), e);
            return null;
        }
        return listorder;
    }

    /**
     * 根据查询条件进行分页查询
     */
    @Override
    public Pagination findOrderMainByPage(Pagination page) throws SQLException {
        try {
            Map<String, Object> conditon = (Map<String, Object>) page.getObjCondition();
            logger.debug("开始查询用户的订单列表,userId:" + conditon.get("userId") + ",keyword:"
                    + conditon.get("keyword") + ",orderStatus:" + conditon.get("orderStatus"));
            long startTime = System.currentTimeMillis();
            // 使用订单数据源

            page = myOrderDao.findByPage("OrderMain.SQL_QUERY_MY_ORDER_LIST",
                    "OrderMain.SQL_QUERY_MY_ORDER_COUNT", page);
            List<OrderMain> data = page.getRecordList();
            if (null != data && !data.isEmpty()) {
                List<String> pCodes = new ArrayList<>();
                List<String> splitCodes = new ArrayList<>();
                for (OrderMain om : data) {
                    pCodes.add(om.getOrderCode());
                    if (om.getOrderStatus() == OrderDictionaryEnum.Order_Status.Split_Done
                            .getKey()) { // 已拆分
                        splitCodes.add(om.getOrderCode());
                    }
                }
                int idx;
                List<OrderMain> childOrder;
                // 二级
                if (splitCodes.size() > 0
                        && null != (childOrder = myOrderDao.queryChildOrderByOrderCode(splitCodes))
                        && !childOrder.isEmpty()) {
                    splitCodes.clear();
                    Iterator<OrderMain> it2 = childOrder.iterator();
                    while (it2.hasNext()) {
                        OrderMain com = it2.next();
                        if (com.getOrderStatus() == OrderDictionaryEnum.Order_Status.Split_Done
                                .getKey()) { // 已拆分
                            splitCodes.add(com.getOrderCode());
                        }
                        idx = pCodes.indexOf(com.getParentOrderCode());
                        data.add(++idx, com);
                        pCodes.add(idx, com.getOrderCode());
                        it2.remove();
                    }
                    // 三级
                    if (splitCodes.size() > 0
                            && null != (childOrder =
                                    myOrderDao.queryChildOrderByOrderCode(splitCodes))
                            && !childOrder.isEmpty()) {
                        splitCodes.clear();
                        for (OrderMain com : childOrder) {
                            idx = pCodes.indexOf(com.getParentOrderCode());
                            data.add(++idx, com);
                        }
                    }
                    page.setRecordList(data);
                }
            }
            logger.debug("查询用户的订单列表结束，耗时" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return page;
    }

    /**
     * 全部订单wap
     */
    @Override
    public Pagination wapFindOrderMainByPage(Pagination page) throws SQLException {
        Map<String, Object> conditon = (Map<String, Object>) page.getObjCondition();
        logger.debug("开始查询用户的订单列表,userId:" + conditon.get("userId") + ",keyword:"
                + conditon.get("keyword") + ",orderStatus:" + conditon.get("orderStatus"));
        long startTime = System.currentTimeMillis();
        // 使用订单数据源

        page = myOrderDao.findByPage("OrderMain.SQL_QUERY_MY_ORDER_LIST",
                "OrderMain.SQL_QUERY_MY_ORDER_COUNT", page);
        logger.debug("查询用户的订单列表结束，耗时" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        return page;
    }

    /**
     * 删除订单
     */
    @Override
    public void deleteOrderMain(String userAccount, String orderMainCode) throws Exception {
        try {
            logger.debug("开始调用订单系统订单删除接口,参数userAccount:" + userAccount + ",orderMainCode:"
                    + orderMainCode);
            // OrderChangeStatusRemoteService orderChangeStatusRemoteService =
            // (OrderChangeStatusRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_ORDER,
            // "orderChangeStatusService");
            orderChangeStatusRemoteService.changeOrderDisabled(userAccount, orderMainCode,
                    (long) (OrderDictionaryEnums.OrderDisabled.Delete.getKey()));
            logger.debug("调用订单系统订单删除接口结束");
        } catch (Exception e) {
            logger.error("调用订单系统的订单删除接口异常：" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 取消订单，分两种情况： 1.付款前； 2.付款后送货前，以及送货失败；
     */
    @Override
    public void cancelOrderMain(String userAccount, String orderMainCode, long orderMainStatus)
            throws Exception {

        try {

            String key = "cancelOrderMain" + orderMainCode;
            if (null == jedisCluster.get(key)) {
                jedisCluster.setex(key, 600, String.valueOf(orderMainStatus));

                OrderMain om = myOrderDao.queryOrderMainByOrderCode(orderMainCode);
                if (null != om && null != om.getCustomerId()) {
                    QueryResult qr = null;
                    if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Not_Pay
                            .getKey()) {
                        qr = qry.queryByOrder(orderMainCode);
                    }
                    orderChangeStatusRemoteService.changeOrderStatus(userAccount, orderMainCode,
                            (long) (OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()), null,
                            qr);
                    if (om.getOrderStatus() == OrderDictionaryEnum.Order_Status.Not_Pay.getKey()
                            && !productStockService.updateProductOrderQuantityCache(
                                    om.getCustomerId().longValue(), false, orderMainCode)) {
                        logger.error("订单" + orderMainCode + "增加活动库存失败");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("调用订单系统的取消订单接口异常：" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 确认订单收货
     */
    @Override
    public int sureOrderMain(String userId, String orderMainCode, long orderMainStatus)
            throws Exception {
        int result;
        try {
            // OrderChangeStatusRemoteService orderChangeStatusRemoteService =
            // (OrderChangeStatusRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_ORDER,
            // "orderChangeStatusService");
            logger.info("开始调用订单系统确认完成,参数userId:" + userId + ",orderMainCode:" + orderMainCode
                    + ",orderMainStatus:" + orderMainStatus);
            result = orderChangeStatusRemoteService.changeOrderStatus(String.valueOf(userId),
                    orderMainCode, (long) (OrderDictionaryEnum.Order_Status.Order_Done.getKey()),
                    null, null);
            // /以下为直销同步PV
            OrderMain om = this.findOrderByOrderCode(orderMainCode);
            if (om.getOrderType() == OrderDictionaryEnum.Order_Type.Normal.getKey()
                    || om.getOrderType() == OrderDictionaryEnum.Order_Type.YsOrder.getKey()) {// 普通订单和预售订单

                List<com.pltfm.app.entities.OrderItem> orderItemList =
                        orderItemQryRemoteService.getOrderItemBySingleOrderCode(orderMainCode);
                com.pltfm.app.entities.OrderItem orderItem;
                float pvCount = 0f;
                for (com.pltfm.app.entities.OrderItem anOrderItemList : orderItemList) {
                    orderItem = anOrderItemList;
                    pvCount = orderItem.getCommodityPv() + pvCount;
                }
                if (pvCount > 0) {// PV 大于0的时候才去同步**有PV的
                    Long loginId = om.getCustomerId().longValue();
                    User user = loginDao.getUserByLoginId(loginId);

                    if (user.getCustomerTypeId() == 2) { //
                        logger.info("添加同步信息的直销会员账号是 ：" + user.getLoginAccount());

                        com.pltfm.app.entities.OrderSync orderSync =
                                new com.pltfm.app.entities.OrderSync();
                        orderSync.setOrderCode(om.getOrderCode());
                        /*
                         * PV同步状态 未同步
                         */
                        short SYNC_STATUS = 2;
                        orderSync.setSyncFlag(SYNC_STATUS);// 0：成功，-1：异常，-2重复提交,1未同步（新加的）
                        orderSync.setOutCode(user.getLoginAccount());
                        orderSync.setSyncDate(new Date());
                        String orderRe = orderSyncRemoteService.updateOrderSync(orderSync);
                        logger.info("* 成功返回1 失败返回0订单同步启记录返回结果 ：" + orderRe);
                        /*
                         * 取消在确认收货时同步 coderList.add(orderMainCode); Map re = syncPv(coderList); //
                         * 0：成功，-1：异常，-2重复提交 logger.info("0：成功，-1：异常，-2重复提交同步返回结果：" +
                         * re.toString());
                         */
                    }
                }
            }
            logger.info("调用订单系统确认完成接口结束!");
        } catch (Exception e) {
            logger.error("调用订单系统确认完成订单接口异常：" + e.getMessage(), e);
            throw e;
        }
        return result;
    }

    /**
     * 根据订单id查询订单信息
     */
    @Override
    public OrderMain findOrderMainById(Integer orderMainId) throws SQLException {
        // 使用订单数据源

        OrderMain orderMain =
                (OrderMain) myOrderDao.findById("OrderMain.findByOrderMainId", orderMainId);
        List<OrderItem> orderItemList = orderMain.getOrderItemList();
        // 使用产品数据源

        for (OrderItem orderItem : orderItemList) {
            // 获取订单里商品的默认图片信息（主图）
            ProductImage defaultProductImage = myOrderDao.findDefaultProductImageBySkuCode(
                    "ProductImage.findDefaultImageBySkuCode", orderItem.getCommoditySku());
            orderItem.setDefaultProductImage(defaultProductImage);
            if (defaultProductImage != null) {
                orderItem.setProductSkuId(defaultProductImage.getSkuId());
            }
        }
        return orderMain;
    }

    /**
     * 根据订单code查询订单实体
     */
    @Override
    public OrderMain findOrderByOrderCode(String orderCode) throws SQLException {
        // 使用订单数据源

        /*
         * List<OrderItem> orderItemList = ordermain.getOrderItemList(); for (int j = 0; j <
         * orderItemList.size(); j++) { OrderItem orderItem = orderItemList.get(j); // 使用产品数据源 //
         * 获取订单里商品的默认图片信息（主图） ProductImage defaultProductImage =
         * myOrderDao.findDefaultProductImageBySkuCode( "ProductImage.findDefaultImageBySkuCode",
         * orderItem.getCommoditySku()); ProductSku productsku =
         * productSkuDao.findProductBySkuCode(orderItem.getCommoditySku());
         * orderItem.setProductSkuId(defaultProductImage.getSkuId());
         * orderItem.setDefaultProductImage(defaultProductImage); }
         */
        return (OrderMain) myOrderDao.findById("OrderMain.findByOrderCode2", orderCode);
    }

    /**
     * 查询当前订单是不是主订单
     */
    @Override
    public BigDecimal findIsMainOrder(String orderCode) throws SQLException {
        // 使用订单数据源

        return (BigDecimal) myOrderDao.findById("OrderMain.findIsMainOrder", orderCode);
    }

    /**
     * 计算会员特定状态的订单总数
     */
    @Override
    public Integer countOrders(Long userId, List<Integer> orderStatusList) throws SQLException {
        String statusStr = StringUtils.join(orderStatusList, ",");
        logger.info("计算会员特定状态的订单总数,参数userId:" + userId + "；orderStatusStr:" + statusStr);
        // 使用订单数据源

        Map<String, Object> para = new HashMap<>();
        para.put("userId", userId);
        para.put("orderStatus", statusStr);
        String channel = ConfigurationUtil.getString("CHANNEL");
        para.put("channel", channel);
        return (Integer) myOrderDao.findById("OrderMain.countByUserId", para);
    }

    /**
     * 计算会员特定状态的待评价总数
     */
    @Override
    public Integer countWaitAssess(Long userId, List<Integer> orderStatusList,
            List<Integer> orderAssessList) throws SQLException {
        String orderAssessStatus = StringUtils.join(orderAssessList, ",");
        logger.info("计算会员特定状态的订单总数,参数userId:" + userId + "；orderAssessStatus:" + orderAssessStatus);
        // 使用订单数据源

        Map<String, Object> para = new HashMap<>();
        para.put("userId", userId);
        para.put("orderStatus", "6");
        para.put("assessStatus", orderAssessStatus);
        String channel = ConfigurationUtil.getString("CHANNEL");
        para.put("channel", channel);
        return (Integer) myOrderDao.findById("OrderMain.countByUserId", para);
    }

    @Override
    public List<OrderItem> findAppraiseByOrdercode(String orderCode) throws Exception {

        List<OrderItem> orderItemList;
        orderItemList = myOrderDao.findByProperty("OrderMain.findAppriaseBycode", orderCode);
        for (OrderItem orderItem : orderItemList) {
            // 使用产品数据源

            // 获取订单里商品的默认图片信息（主图）
            ProductImage defaultProductImage = myOrderDao.findDefaultProductImageBySkuCode(
                    "ProductImage.findDefaultImageBySkuCode", orderItem.getCommoditySku());
            try {
                ProductSku productsku =
                        productSkuDao.findProductBySkuCode(orderItem.getCommoditySku());
                orderItem.setProductSkuId(productsku.getProductSkuId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw e;
            }
            orderItem.setDefaultProductImage(defaultProductImage);
        }
        return orderItemList;
    }

    /**
     * 根据主单parentOrderCode,查找已拆分的子订单集合
     *
     * @param parentOrderCode 父单的订单CODE luoyi
     */
    @Override
    public List<OrderMain> findOrderListByParentCode(String parentOrderCode) {
        // 使用产品数据源

        try {
            return myOrderDao.findByProperty("OrderMain.findSonListByParendOrderCode",
                    parentOrderCode);
        } catch (SQLException e) {
            logger.error("查询子订单出错" + e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    /**
     * 根据主单parentOrderCode,查找已拆分的子订单集合
     *
     * @param parentOrderCode 父单的订单CODE luoyi
     */
    @Override
    public List<OrderMain> wapFindOrderListByParentCode(String parentOrderCode) {
        // 使用产品数据源

        try {
            List<OrderMain> om = myOrderDao
                    .findByProperty("OrderMain.wapFindSonListByParendOrderCode", parentOrderCode);
            OrderMain orderMain;
            List<OrderMain> omList = new ArrayList();
            for (OrderMain om1 : om) {
                /* orderMain=new OrderMain(); */
                orderMain = findOrderByOrderCode(om1.getOrderCode());
                omList.add(orderMain);
            }

            //
            return omList;
        } catch (SQLException e) {
            logger.error("查询子订单出错" + e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    /**
     * 根据订单code,查询类型为4=满额送券的优惠券信息
     *
     * @param orderCode 订单CODE luoyi 2013/11/28
     */
    @Override
    public List<CouponAndPromotion> findCouponByOrderCode(String orderCode) {
        // 使用产品数据源

        try {
            return myOrderDao.findByProperty("OrderMain.findCouponInfoByOrderCode", orderCode);
        } catch (SQLException e) {
            logger.error("查询订单所获得的优惠券信息出错" + e.getMessage());
            return null;
        }

    }

    /**
     * 根据订单code,查询类型为4=满额送券的优惠券信息
     *
     * @param orderCode 订单CODE wangkai 2014/04/24
     */
    @Override
    public List<CouponAndPromotion> findCouponAndPromotionByOrderCode(String orderCode) {
        // 使用产品数据源
        try {

            return myOrderDao.findByProperty("OrderMain.findCouponInfoByOrderCode", orderCode);
        } catch (SQLException e) {
            logger.error("查询订单所获得的优惠券信息出错" + e.getMessage());
            return null;
        }

    }

    /**
     * 根据优惠券ID查优惠券产品表
     */
    @Override
    public int findCouponById(int CouponId) {
        try {

            Pagination pagination = new Pagination();
            pagination.setObjCondition(CouponId);
            return myOrderDao.findByCount("OrderMain.findByCouponCount", pagination);
        } catch (SQLException e) {
            logger.error("根据优惠券ID查优惠券产品表出错" + e.getMessage());
            return 0;
        }
    }

    /**
     * 查订单对应商品的退货信息 （用来计算同步Pv）
     *
     * @return OrderAlter
     */
    @Override
    public OrderAlter findOrderAlterPv(Map map) {
        OrderAlter orderAlter;
        try {

            orderAlter = orderAlterDao.findOrderAlterPv(map);
        } catch (SQLException e) {
            logger.error("查退货信息异常：" + e.getMessage(), e);
            return null;
        }
        return orderAlter;
    }

    @Override
    public List<OrderSync> selectOrderSyncByOrderCode(String orderCode) {
        List<OrderSync> list;
        try {

            list = orderSyncDAO.selectOrderSyncByOrderCode(orderCode);
        } catch (SQLException e) {
            logger.error("查PV同步数据异常：" + e.getMessage(), e);
            return null;
        }
        return list;
    }

    @Override
    public List<OrderMain> reconciliation(Map map) {
        List<OrderMain> list;
        try {

            list = myOrderDao.findByProperty("OrderMain.OrderReconciliation", map);
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    @Override
    public List<OrderPreferential> orderPreferentialList(Map map) throws Exception {
        try {
            return myOrderDao.orderPreferentialList(map);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 根据订状态统计我的订单数量
     */
    @Override
    public Map<String, BigDecimal> queryMyOrderStatusCount(Map<String, Object> map)
            throws ServiceException {
        try {

            return myOrderDao.queryMyOrderStatusCount(map);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 根据订状态统计我的订单数量
     */
    @Override
    public Map<String, BigDecimal> queryMyOrderAddappraiseAndAppraiseItemCount(
            Map<String, Object> map) throws ServiceException {
        try {

            return myOrderDao.queryMyOrderAppraiseCount(map);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * OMS查询结转订单
     */
    @Override
    public String queryExeOrder(Map<String, String> params) throws ServiceException {
        StringBuilder sb = new StringBuilder();
        try {
            String orderCode = params.get("tid");
            String starttime = params.get("starttime");
            String endtime = params.get("endtime");

            int count = 0, page = 0, pagesize = 1;
            if ((null == orderCode || orderCode.length() == 0) && null != starttime
                    && null != endtime) {
                page = Integer.parseInt(params.get("page"));// 页数
                pagesize = Integer.parseInt(params.get("pagesize"));// 单页内数据量
                params.put("beginIndex", String.valueOf((page - 1) * pagesize + 1));
                params.put("endIndex", String.valueOf(page * pagesize));
                count = myOrderDao.queryExeOrderCount(params);
            } else if (null != orderCode && orderCode.length() > 0) {
                params.put("orderCode", orderCode);
            } else {
                throw new ServiceException(0, "OMS查询结转订单查询参数错误");
            }
            List<ExeOrderData> result = myOrderDao.queryExeOrder(params);
            if (null != result) {
                for (ExeOrderData eod : result) {
                    sb.append(eod);
                }
                if ("trades.sold.increment.get".equals(params.get("method"))) {
                    return "<total_results>" + count + "</total_results>" + "<total_page>"
                            + (count / pagesize + (count % pagesize == 0 ? 0 : 1)) + "</total_page>"
                            + "<current_page>" + page + "</current_page>" + "<order_list>"
                            + sb.toString() + "</order_list>";
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return sb.toString();
    }

    /**
     * 51fanli查询订单
     */
    /*
     * @Override public List<OrderMain> fandOrderByFanli(Map map) throws ServiceException {
     * 
     * List<OrderMain> list = null; try { list =
     * myOrderDao.findByProperty("OrderMain.findOrder_fanli", map); } catch (SQLException e) {
     * logger.error("51返利查询订单失败" + e.getMessage()); return list; } return list; }
     */
    @Override
    public void orderUserSourceType(String cpsValue, com.pltfm.app.entities.OrderMain orderMain,
            List<com.pltfm.app.entities.OrderItem> oilist) {

        try {

            Map loginId = new HashMap();
            loginId.put("loginId", orderMain.getCustomerId());
            jedisCluster.setex(("orderItem_" + orderMain.getOrderCode()).getBytes(), 86400,
                    SerializeUtil.serialize(oilist));
            if (cpsValue != null && !"".equals(cpsValue)) {
                jedisCluster.setex("orderUserSource_" + orderMain.getOrderCode(), 86400, cpsValue);
                // cps
            } else {
                /*
                 * if (orderMain.getCustomerAccount().endsWith("_51fanli")) { // 51fanli
                 * jedis.setex("orderUserSource_" + orderMain.getOrderCode(), 86400,
                 * OrderUserSource.FANLI.getType()); } else {
                 */
                EraInfo era = eraInfoService.selectEranInfoById(loginId);
                if (era != null) {
                    jedisCluster.setex("orderUserSource_" + orderMain.getOrderCode(), 86400,
                            OrderUserSource.ERA.getType());
                } else {
                    /*
                     * Spreader spreader = loginDao.querySpreaderByLoginId(orderMain.getCustomerId()
                     * .longValue()); if (spreader != null) { jedis.set("orderUserSource_" +
                     * orderMain.getOrderCode(), OrderUserSource.YUNSHANG.getType()); } else {
                     */
                    jedisCluster.setex("orderUserSource_" + orderMain.getOrderCode(), 86400,
                            OrderUserSource.KM.getType());
                    // }
                }
                // }
            }
        } catch (Exception e) {
            jedisCluster.setex("orderUserSource_" + orderMain.getOrderCode(), 86400,
                    OrderUserSource.KM.getType());
        }
    }

    /*    *//*
             * @param skuId SKU编码
             * 
             * @param orderNo 订单号
             * 
             * @param orderQuantity 订单数量
             * 
             * @param payTime 支付时间
             *//*
               * @Override public void pushOrderUserSource(String orderCode) {
               * 
               * try {
               * 
               * List<com.pltfm.app.entities.OrderItem> oList =
               * (List<com.pltfm.app.entities.OrderItem>) SerializeUtil
               * .unserialize(jedisCluster.get(("orderItem_" + orderCode).getBytes())); JSONObject
               * jsonMain = new JSONObject(); JSONArray jsonArray = new JSONArray();
               * jsonMain.put("orderCode", orderCode);// 订单编号 for (com.pltfm.app.entities.OrderItem
               * oi : oList) { JSONObject jsonItem = new JSONObject(); jsonItem.put("skuId",
               * oi.getSkuId());// SkuID jsonItem.put("commodityNumber", oi.getCommodityNumber());//
               * 商品数量 jsonArray.add(jsonItem); } jsonMain.put("oList", jsonArray);
               * jsonMain.put("userSource", jedisCluster.get("orderUserSource_" + orderCode));// 来源
               * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               * jsonMain.put("payTime", sdf.format(new Date()));// 支付时间 logger.info("推送信息为：" +
               * jsonMain.toString()); send(jsonMain.toString(), destination, jmsTemplate);
               * 
               * } catch (Exception e) { logger.error("订单用户来源推送出错：" + e.getMessage()); } }
               */

    public static void send(final String value, Destination destination, JmsTemplate jmsTemplate) {
        jmsTemplate.send(destination, (Session session) -> {
            ObjectMessage objectMessage;
            try {
                objectMessage = session.createObjectMessage(value);
            } catch (Exception e) {
                logger.error("发送MQ消息发生异常" + e.getMessage());
                throw new JMSException("发送MQ消息发生异常," + e);
            }
            return objectMessage;
        });
    }

    /**
     * 允许启用支付密码 1:符合 -1：手机不符 -2: 订单符合 0不符合
     */
    @Override
    public int enablePayPWD(Long uid) throws ServiceException {
        int enable = 0;

        try {

            String key = ConfigurationUtil.getString("B2B_ENABLE_PAY_PWD") + uid;
            if (!jedisCluster.exists(key)) {

                Map<String, String> map = myOrderDao.queryEnablePayPWDCondition(uid);
                // 如果已有支付密码直接跳过验证
                AccountInfo accountInfo = securityCentreService.accountByUserId(uid);
                String mobile, avaOrder = null;
                if (null != map && null != (avaOrder = map.get("avaOrder"))
                        && null != (mobile = map.get("mobile")) && mobile.length() == 11
                        && Integer.parseInt(avaOrder) > 0
                        || accountInfo != null
                                && !StringUtil.isEmpty(accountInfo.getPaymentpwd())) {
                    jedisCluster.set(key, avaOrder);
                    enable = 1;
                } else if (null != map && (null == (avaOrder = map.get("avaOrder"))
                        || Integer.parseInt(avaOrder) < 1)) {
                    enable = -2;
                } else if (null != map
                        && (null == (mobile = map.get("mobile")) || mobile.length() != 11)) {
                    enable = -1;
                }
            } else {
                enable = 1;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return enable;
    }

    /**
     * 根据订单编号获取系统操作流水
     */
    @Override
    public List<OrderOperateStatement> findOperateByNo(String No) throws SQLException {

        List<OrderOperateStatement> listorder;
        try {
            listorder = orderOperateStatementDao.findByOrderNoForView(No);
        } catch (DaoException e) {
            logger.error("获取系统操作流水失败" + e.getMessage(), e);
            return null;
        }
        return listorder;
    }

    /**
     * 根据skuId查找产品的默认图片信息（主图）
     */
    @Override
    public ProductImage findDefaultProductImageBySkuCode(String sqlId, String skuCode)
            throws SQLException {
        return myOrderDao.findDefaultProductImageBySkuCode(sqlId, skuCode);
    }

    @Override
    public BigDecimal sumPvByCustomerId(long customerId) {
        String CUSTOMER_PV_KEY = "customer.pv.key";
        BigDecimal sumPv = new BigDecimal(0);
        try {
            if (jedisCluster.exists(CUSTOMER_PV_KEY + "_" + customerId)) {
                sumPv = new BigDecimal(jedisCluster.get(CUSTOMER_PV_KEY + "_" + customerId));
            } else {
                sumPv = myOrderDao.sumPvByCustomerId(customerId);
                jedisCluster.set(CUSTOMER_PV_KEY + "_" + customerId,
                        sumPv == null ? "0" : sumPv.toString());
                jedisCluster.expire(CUSTOMER_PV_KEY + "_" + customerId, 60 * 30);
            }

        } catch (SQLException e) {
            logger.error("获取系统操作流水失败" + e.getMessage(), e);
        }
        return sumPv == null ? new BigDecimal(0) : sumPv;
    }


    /**
     * 根据订单编码获得对应各金额
     */

    @Override
    public String[] findReserveByOrderMoney(String orederCode, Integer orderType)
            throws SQLException {

        List<Map<String, Object>> list = myOrderDao.findReserveByOrderMoney(orederCode);
        String[] moneys = new String[2];
        if (orderType == 7) {
            for (Map<String, Object> map : list) {
                moneys[0] = "￥" + map.get("DEPOSITSUM");
                moneys[1] = "￥" + map.get("AMOUNTPAYABLE");
            }
        } else {
            moneys[0] = "";
            moneys[1] = "";
        }
        return moneys;
    }

    /**
     * 根据订单号查询是否支付定金和尾款
     */

    @Override
    public String[] findDepositTailByOrderCode(String orederCode, Integer orderType)
            throws SQLException {

        // String state = myOrderDao.findDepositTailByOrderCode(orederCode);
        List<Map<String, Object>> list = myOrderDao.findReserveByOrderMoney(orederCode);
        String[] moneys = new String[2];
        if (orderType == 7) {
            for (Map<String, Object> map : list) {
                moneys[0] = "￥" + map.get("DEPOSITSUM");
                moneys[1] = "￥" + map.get("AMOUNTPAYABLE");
            }
        }
        // if ("no".equals(state)) {
        // moneys[0] = moneys[0] + "(未支付)";
        // moneys[1] = moneys[1] + "(未支付)";
        // } else if ("deposit".equals(state)) {
        // moneys[0] = moneys[0] + "(已支付)";
        // moneys[1] = moneys[1] + "(未支付)";
        // } else if ("final".equals(state)) {
        // moneys[0] = moneys[0] + "(已支付)";
        // moneys[1] = moneys[1] + "(已支付)";
        // } else {
        // moneys[0] = moneys[0] + "(未支付)";
        // moneys[1] = moneys[1] + "(未支付)";
        // }
        return moneys;
    }

    /**
     * 根据订单号查询是否支付定金和尾款状态
     */

    @Override
    public String findDepositTailStateByOrderCode(String orederCode) throws SQLException {
        return myOrderDao.findDepositTailByOrderCode(orederCode);
    }

    /**
     * 根据订单编码获得对应支付时间
     */

    @Override
    public String findReserveByOrderCode(String orederCode, Long orderStatus) throws SQLException {

        List<Map<String, Object>> list = myOrderDao.findReserveByOrderCode(orederCode);
        String reserveTime = null;
        String reservePrefixTime = null;
        boolean isFinalPayment = false;

        for (Map<String, Object> map : list) {
            if (orderStatus == 1) {
                reserveTime = map.get("DEPOSITENDTIME") + "";
            } else if (orderStatus == 23) {
                reserveTime = map.get("FINALPAYSTARTTIME") + "";
                if (reserveTime.charAt(0) != '-') {
                    isFinalPayment = true;
                    reserveTime = map.get("FINALPAYENDTIME") + "";
                }
            }
        }

        if (reserveTime == null) {
            reserveTime = "预售订单有误";
            return reserveTime;
        } else {
            if (orderStatus == 1) {
                if (reserveTime.charAt(0) == '-') {
                    reserveTime = reserveTime.substring(1, reserveTime.length());
                    reservePrefixTime = "距离定金支付倒计时:";

                } else {
                    reserveTime = "定金支付已截止";
                    return reserveTime;
                }
            } else {
                if (isFinalPayment) {
                    if (reserveTime.charAt(0) == '-') {
                        reserveTime = reserveTime.substring(1, reserveTime.length());
                        reservePrefixTime = "距离尾款支付倒计时:";
                    } else {
                        reserveTime = "尾款支付已截止";
                        return reserveTime;
                    }
                } else {
                    if (reserveTime.charAt(0) == '-') {
                        reserveTime = reserveTime.substring(1, reserveTime.length());
                        reservePrefixTime = "距离开始支付尾款倒计时:";
                    } else {
                        if (Long.parseLong(reserveTime) == 0) {
                            reserveTime = findPayEndTimeByOrderCode(orederCode);
                            return reserveTime;
                        }
                    }
                }

            }

            long ms = Long.parseLong(reserveTime);
            int ss = 1000;
            int mi = ss * 60;
            int hh = mi * 60;
            int dd = hh * 24;
            long day = ms / dd;
            long hour = (ms - day * dd) / hh;
            long minute = (ms - day * dd - hour * hh) / mi;
            long second = (ms - day * dd - hour * hh - minute * mi) / ss;
            String strDay = day < 10 ? "0" + day : "" + day; // 天
            String strHour = hour < 10 ? "0" + hour : "" + hour;// 小时
            String strMinute = minute < 10 ? "0" + minute : "" + minute;// 分钟
            String strSecond = second < 10 ? "0" + second : "" + second;// 秒
            if (orderStatus == 1) {
                reserveTime = reservePrefixTime + strHour + "时" + strMinute + "分" + strSecond + "秒";
            } else {
                reserveTime = reservePrefixTime + strDay + "天" + strHour + "时" + strMinute + "分"
                        + strSecond + "秒";
            }

        }
        return reserveTime;
    }


    /**
     * 根据订单编码获得对应支付时间
     */

    @Override
    public String[] findReserveByOrderState(String orederCode) throws SQLException {

        String[] states = new String[7];
        List<Map<String, Object>> list = myOrderDao.findReserveByOrderCode(orederCode);
        for (Map<String, Object> map : list) {
            if ((map.get("DEPOSITENDTIME") + "").charAt(0) == '-') {
                states[0] = "yes";
            } else {
                states[0] = "no";
            }
            if ((map.get("FINALPAYSTARTTIME") + "").charAt(0) == '-') {
                states[1] = "yes";
            } else {
                states[1] = "no";
            }
            if ((map.get("FINALPAYENDTIME") + "").charAt(0) == '-') {
                states[2] = "yes";
            } else {
                states[2] = "no";
            }
            if ((map.get("DELIVERYENDTIME") + "").charAt(0) == '-') {
                states[3] = "yes";
            } else {
                if (Long.parseLong(map.get("DELIVERYENDTIME").toString()) < 864000000) {
                    states[3] = "no";
                } else {
                    states[3] = "timeout";
                }
            }
            if (map.get("FINISHDATE") == null) {
                states[4] = "void";
            } else {
                if ((map.get("FINISHDATE").toString()).charAt(0) == '-') {
                    states[4] = "no";
                } else {
                    if (Long.parseLong(map.get("FINISHDATE").toString()) < 1296000000) {
                        states[4] = "yes";
                    } else {
                        states[4] = "no";
                    }
                }
            }
            if (map.get("JUDGESALESRETURN") == null) { // 退换货判断
                states[5] = "yes";
            } else {
                states[5] = "no";
            }

            if (map.get("JUDGECOMPENSATE") == null) { // 赔付判断
                states[6] = "yes";
            } else {
                states[6] = "no";
            }
        }
        return states;
    }

    /**
     * 根据订单号获得尾款支付截止时间
     */

    @Override
    public String findPayEndTimeByOrderCode(String orederCode) throws SQLException {

        List<Map<String, Object>> list = myOrderDao.findReserveByOrderCode(orederCode);
        String reserveTime = list.get(0).get("FINALPAYENDTIME") + "";
        String reservePrefixTime;
        if (reserveTime.charAt(0) == '-') {
            reserveTime = reserveTime.substring(1, reserveTime.length());
            reservePrefixTime = "距离尾款支付倒计时:";
        } else {
            reserveTime = "尾款支付已截止";
            return reserveTime;
        }
        long ms = Long.parseLong(reserveTime);
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;
        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        String strDay = day < 10 ? "0" + day : "" + day; // 天
        String strHour = hour < 10 ? "0" + hour : "" + hour;// 小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;// 分钟
        String strSecond = second < 10 ? "0" + second : "" + second;// 秒
        reserveTime = reservePrefixTime + strDay + "天" + strHour + "时" + strMinute + "分" + strSecond
                + "秒";
        return reserveTime;
    }

    @Override
    public String findReserveByOrderPhone(String orederCode) throws SQLException {
        return myOrderDao.findReserveByOrderPhone(orederCode);
    }

    @Override
    public Map<String, Object> findReserveByOrderCode(String orederCode) throws SQLException {

        List<Map<String, Object>> list = myOrderDao.findReserveByOrderCode(orederCode);

        return list.get(0);
    }

    /**
     * 根据订单编码获得所有流水状态
     */
    @Override
    public List<Map<String, Object>> findStateByOrderCode(String orederCode) throws SQLException {

        List<Map<String, Object>> list = myOrderDao.findStateByOrderCode(orederCode);
        for (Map<String, Object> map : list) {
            TIMESTAMP timestamp = (TIMESTAMP) map.get("NOWOPERATEDATE");
            Timestamp times = timestamp.timestampValue();
            map.put("NOWOPERATEDATE", new Date(times.getTime()));
        }
        return list;
    }

    /**
     * 根据订单编码判断是否可以支付定金
     */
    @Override
    public String judgeDepositTimeByOrderMoney(String orederCode) throws SQLException {
        return myOrderDao.judgeDepositTimeByOrderMoney(orederCode).charAt(0) == '-' ? "yes" : "no";
    }

    /**
     * 根据订单号查询预售活动状态
     */
    @Override
    public Integer findPresellStatusByOrderCode(String orderCode) throws SQLException {

        return myOrderDao.findPresellStatusByOrderCode(orderCode);
    }
}
