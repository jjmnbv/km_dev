package com.kmzyc.b2b.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.member.ProdAppraiseDao;
import com.kmzyc.b2b.model.CouponAndPromotion;
import com.kmzyc.b2b.model.OrderAlter;
import com.kmzyc.b2b.model.OrderAlterPhoto;
import com.kmzyc.b2b.model.OrderAssessDetail;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.OrderOperateStatement;
import com.kmzyc.b2b.model.OrderPayStatement;
import com.kmzyc.b2b.model.OrderPreferential;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.OrderAssessDetailService;
import com.kmzyc.b2b.service.OrderAssessService;
import com.kmzyc.b2b.service.OrderItemService;
import com.kmzyc.b2b.service.OrderMainService;
import com.kmzyc.b2b.service.OrderTrailService;
import com.kmzyc.b2b.service.ShopInfoService;
import com.kmzyc.b2b.service.member.AccountService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.service.member.ReturnGoodsTraceService;
import com.kmzyc.b2b.service.member.ReturnShopService;
import com.kmzyc.b2b.util.OrderStatusUtil;
import com.kmzyc.b2b.vo.OrderAssessPoint;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.TraceInfoVO;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.entities.ExpressTrack;
import com.kmzyc.express.remote.ExpressSubscriptionRemoteService;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.constants.OrderAssessPointMap;
import com.kmzyc.framework.sensitive.SensitiveType;
import com.kmzyc.framework.sensitive.SensitiveWordFilter;
import com.kmzyc.order.remote.OrderAssessRemoteService;
import com.kmzyc.product.remote.service.ProductAppraiseRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.entities.OrderAssessInfo;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.AppraiseAddtoContent;
import com.pltfm.app.vobject.ProdAppraise;

import redis.clients.jedis.JedisCluster;

// import com.km.framework.common.util.RemoteTool;

/**
 * 我的订单相关APP接口
 *
 * @author wangkai
 */
@SuppressWarnings("unchecked")
@Scope("prototype")
@Controller("appMyOrderAction")
public class AppMyOrderAction extends AppBaseAction {
    private static final long serialVersionUID = 3443290927330717006L;

    // private static final Logger logger = Logger.getLogger(AppMyOrderAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppMyOrderAction.class);
    // private static final String prodAppraisedone = ConfigurationUtil.getString("r_prodappraise");

    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;

    @Resource(name = "accountServiceImpl")
    private AccountService accountService;
    @Resource(name = "loginServiceImp")
    private LoginService loginService;
    @Resource(name = "orderAssessService")
    private OrderAssessService orderAssessService;
    @Resource(name = "sensitiveWordFilter")
    private SensitiveWordFilter sensitiveWordFilter;
    @Resource(name = "prodAppraiseDaoImpl")
    private ProdAppraiseDao prodAppraiseDao;
    @Resource(name = "returnShopServiceImpl")
    private ReturnShopService returnShopService;
    @Resource(name = "shopInfoService")
    private ShopInfoService shopInfoService;
    @Resource(name = "returnGoodsService")
    private ReturnGoodsTraceService returnGoodsTraceService;
    @Resource(name = "orderItemServiceImpl")
    private OrderItemService orderItemService;
    @Resource(name = "orderTrailService")
    private OrderTrailService orderTrailService;
    @Autowired
    private ExpressSubscriptionRemoteService expressSubscriptionRemoteService;
    @Resource
    private OrderMainService orderMainService;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    // private final String showPath = ConfigurationUtil.getString("RETURNSHOP_PHOTO_PATH");
    /**
     * 订单详细评价表
     */
    @Resource(name = "orderAssessDetailService")
    private OrderAssessDetailService orderAssessDetailService;

    @Resource
    private ProductAppraiseRemoteService productAppraiseRemoteService;

    @Resource
    private OrderAssessRemoteService orderAssessRemoteService;

    // 订单操作日志
    private List<OrderOperateStatement> listorder;

    private String uid;
    // 每页多少条
    private int pageNum;
    // 第几页
    private int pageNo;
    private int isParentOrder = 0;// 是否为父订单0＝否,1＝是
    private int orderStatus; // 订单状态
    private String orderCode; // 订单编码
    private String productName;// 产品名
    private String orderAlterCode;// 退换货编码


    /*
     * 亲们，现统一分页的参数，如下： 分页 入参pageNo:当前页码；pageNum:每页条数； 出参pageNo:当前页码；pageNum:每页条数；totalNum:总条数 /* 订单，
     * 产品评分， 评价， 订单评分， s追评， 评论ID orderMain, prodappraisePoint, contents, orderappraisePoint,
     * appendContents, appraiseId
     */

    private void setStartParam() {
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        uid = this.getUserid();
        pageNum = jsonParams.getIntValue("pageNum");
        pageNo = jsonParams.getIntValue("pageNo");
        orderStatus = jsonParams.getIntValue("orderStatus");
        orderCode = jsonParams.getString("orderCode");
        productName = jsonParams.getString("productName");
        orderAlterCode = jsonParams.getString("orderAlterCode");
    }

    /**
     * 获取用户订单列表 5.13
     *
     * @return ok
     */
    public void getMyOrderList() {
        try {
            this.setStartParam();
            Map<String, Object> newConditon = new HashMap<String, Object>();
            newConditon.put("userId", uid);
            OrderStatusUtil.parseAppOrderStatus(orderStatus, newConditon);
            if (null != productName && !"".equals(productName)) {// 20150918 添加产品名 搜索
                newConditon.put("keyword", productName);
            }
            Pagination page = this.getPagination(5, pageNum);// pageNum每页多少条
            // pageNo第几页
            page.setStartindex((pageNo - 1) * pageNum + 1);
            page.setEndindex((pageNum * pageNo));
            // 添加渠道查询条件
            String channel = ConfigurationUtil.getString("CHANNEL");
            newConditon.put("channel", channel);
            page.setObjCondition(newConditon);
            this.pagintion = myOrderService.findOrderMainByPage(page);
            // 我的订单列表
            List<OrderMain> orderMainList = pagintion.getRecordList();
            Map<String, Object> mapVo = null;
            List orderMainL = new ArrayList();

            for (OrderMain ordermain : orderMainList) {
                mapVo = new HashMap<String, Object>();
                // 订单内容
                mapVo.put("orderCode", ordermain.getOrderCode());// 订单编号
                if (ordermain.getParentOrderCode() == null) {
                    if (ordermain.getOrderStatus().intValue() ==
                            OrderDictionaryEnum.Order_Status.Split_Done.getKey()) {
                        mapVo.put("isParentOrder", 0);// 父订单
                    } else {
                        mapVo.put("isParentOrder", 2);// 普通订单
                    }

                } else {
                    mapVo.put("isParentOrder", 1);// 子订单
                }

                /* PARENT_ORDER_CODE */
                mapVo.put("parentOrderCode", ordermain.getParentOrderCode());// 父订单编号
                mapVo.put("commerceId", ordermain.getCommerceId());// 店铺ID
                String path = "http://img.kmb2b.com/product/upload/productBrand/20140528151615_895.jpg";
                if (ordermain.getLogoPath() != null) {
                    mapVo.put("logoPath", ConfigurationUtil.getString("PRODUCT_IMG_PATH") +
                            ordermain.getLogoPath());// 店铺logo
                } else {
                    mapVo.put("logoPath", path);// 店铺 自营
                }
                mapVo.put("shopName", ordermain.getShopName());// 店铺名
                mapVo.put("orderStatus", ordermain.getOrderStatus());// 订单状态
                mapVo.put("orderType", ordermain.getOrderType());// 订单类型
                /*
                 * if (ordermain.getOrderStatus() ==
                 * OrderDictionaryEnum.Order_Status.Not_Pay.getKey()) {// 为未支付时查支付信息 Map<String,
                 * Object> Conditon = new HashMap<String, Object>(); // 解析并组装查询条件
                 * Conditon.put("orderCode", ordermain.getOrderCode());// 订单code // 支付信息
                 * List<OrderPayStatement> orderPayStatementList =
                 * accountService.findConsumptionDetailByOrderCode(Conditon); Map<String, Object>
                 * OrderPay = null; List OrderPayList = null; OrderPayStatement orderPayStatement =
                 * null; if (orderPayStatementList.size() > 0) { for (int i = 0; i <
                 * orderPayStatementList.size(); i++) { OrderPayList = new ArrayList(); OrderPay =
                 * new HashMap<String, Object>(); orderPayStatement = orderPayStatementList.get(i);
                 * OrderPay.put("paymentWay", orderPayStatement.getPaymentWay());// 支付方式APP
                 * OrderPay.put("orderMoney", orderPayStatement.getOrderMoney());// 支付金额APP
                 * OrderPayList.add(OrderPay); } } mapVo.put("orderPayInforList", OrderPayList);//
                 * 支付app }
                 */
                mapVo.put("orderMoney", ordermain.getAmountPayable());// 订单金额
                mapVo.put("orderTime", ordermain.getCreateDate());// 下订单时间
                String AssessStauts = jedisCluster
                        .get(ConfigurationUtil.getString("r_prodappraise") +
                                ordermain.getOrderCode());
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("orderCode", ordermain.getOrderCode());
                if (AssessStauts != null) {
                    mapVo.put("assessStauts", "2");// 已评价
                    Map<String, BigDecimal> result = myOrderService
                            .queryMyOrderAddappraiseAndAppraiseItemCount(params);
                    BigDecimal addCount = result.get("ADDAPPRAISECOUNT");
                    // Integer appaiseCount = (Integer) result.get("APPRAISECOUNT");
                    BigDecimal itemCount = result.get("ITEMCOUNT");
                    if (addCount.intValue() == itemCount.intValue()) {
                        mapVo.put("assessStauts", "3");// 已追评
                    }
                } else { // 如果评价未完成
                    mapVo.put("assessStauts", "1");// 未评价
                }

                // 订单商品内容
                List orderProList = new ArrayList();
                Map<String, Object> mapPro = null;
                String productImgServerUrl = ConfigurationUtil.getString("PRODUCT_IMG_PATH");
                for (OrderItem orderitem : ordermain.getOrderItemList()) {
                    mapPro = new HashMap<String, Object>();
                    mapPro.put("orderProdId",
                            orderitem.getDefaultProductImage().getSkuId());// skuId
                    if (orderitem.getDefaultProductImage() != null) {
                        mapPro.put("orderProdUrl", productImgServerUrl +
                                orderitem.getDefaultProductImage().getImgPath5());

                        mapPro.put("orderProdUrl2", productImgServerUrl +
                                orderitem.getDefaultProductImage().getImgPath6());
                    } // 图片url
                    mapPro.put("orderProName", orderitem.getCommodityName());// 商品名
                    mapPro.put("orderProPric", orderitem.getCommodityUnitPrice());// 商品价格
                    mapPro.put("orderProNum", orderitem.getCommodityNumber());// 商品数量
                    mapPro.put("orderItemId", orderitem.getOrderItemId());// 订单详情ID
                    orderProList.add(mapPro);

                }
                mapVo.put("mapPro", orderProList);
                orderMainL.add(mapVo);
            }
            Map<String, Object> orderList = new HashMap<String, Object>();
            orderList.put("orderList", orderMainL); // 订单集合
            orderList.put("totalNum", page.getTotalRecords()); // 订单总条数（除去根订单的）
            orderList.put("totalPage", page.getTotalpage());// 订单总页面数
            // mapVo.put("totalRecords", page.getTotalRecords());
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "查询用户订单列表成功",
                            orderList));
        } catch (Exception e) {
            logger.error("查询用户订单列表失败:", e);
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "失败", null));
        }
    }

    /**
     * 获取订单详情5.14
     *
     * @throws Exception OK
     */
    public void getMyOrderDetail() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            this.setStartParam();
            // 根据订单号来查订单 返回ordermain对象
            OrderMain orderMain = myOrderService.findOrderByOrderCode(orderCode);
            // 根据订单中的物流公司编号查出物流公司名称
            orderMain.setLogisticsName(ConfigurationUtil.getString(orderMain.getLogisticsCode()));
            // 判断该订单是否为当前用户的订单，如果不是则跳转到error页面
            if (orderMain.getCustomerId().compareTo(new BigDecimal(uid)) != 0) {
                logger.info("查询用户订单详情失败:订单和用户不对应");
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                "查询用户订单详情失败", null));
            }
            // 看此订单有没有获得优惠券的信息
            List<CouponAndPromotion> couponList = myOrderService
                    .findCouponAndPromotionByOrderCode(orderMain.getOrderCode());
            CouponAndPromotion coupon = null;
            List couponsList = new ArrayList();
            Map<String, Object> coupons = null;
            if (couponList.size() > 0) {
                for (int i = 0; i < couponList.size(); i++) {
                    coupons = new HashMap<String, Object>();
                    coupon = couponList.get(i);
                    coupons.put("couponMoney", coupon.getCouponMoney());// 优惠券金额APP
                    coupons.put("couponValidDay", coupon.getCouponValidDay());// 优惠券有效天数APP
                    couponsList.add(coupons);
                }
            }
            map.put("couponList", couponsList);// 优惠信息APP
            map.put("createDate", orderMain.getCreateDate());// 订单生成时间
            Map parame = new HashMap();
            parame.put("orderCode", orderMain.getOrderCode());
            List<OrderPreferential> preferentailList = myOrderService.orderPreferentialList(parame);
            List prList = new ArrayList();
            Map<String, Object> prMap = null;
            if (preferentailList.size() > 0) {
                for (OrderPreferential pr : preferentailList) {// 本订单享受的促销活动类型以及活动标题
                    prMap = new HashMap<String, Object>();
                    prMap.put("preferentialType", pr.getOrderPreferentialType());// 优惠类型
                    prMap.put("promotionName", pr.getPromotionName());// 活动名
                    prMap.put("orderItemId", pr.getOrderItemId());// 订单详情ID 有就是产品类的优惠 没有就是订单类的
                    prList.add(prMap);
                }
            }
            map.put("prList", prList);// 促销信息
            Map<String, Object> orderMap = new HashMap<String, Object>();
            Map shopMap = new HashMap();
            shopMap.put("commerceId", orderMain.getCommerceId());
            Map shopInfoMap = null;
            if (orderMain.getCommerceId() != null &&
                    null != (shopInfoMap = shopInfoService.querySimpleShopInfoByMap(shopMap))) {
                orderMap.put("shopId", orderMain.getCommerceId());
                orderMap.put("shopName", shopInfoMap.get("SHOP_NAME"));
            } else {
                orderMap.put("shopId", 221);
                orderMap.put("shopName", "康美自营");
            }
            String AssessStauts = jedisCluster
                    .get(ConfigurationUtil.getString("r_prodappraise") + orderMain.getOrderCode());
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderCode", orderMain.getOrderCode());
            if (AssessStauts != null) {
                orderMap.put("assessStauts", "2");// 已评价
                Map<String, BigDecimal> result = myOrderService
                        .queryMyOrderAddappraiseAndAppraiseItemCount(params);
                BigDecimal addCount = result.get("ADDAPPRAISECOUNT");
                // Integer appaiseCount = (Integer) result.get("APPRAISECOUNT");
                BigDecimal itemCount = result.get("ITEMCOUNT");
                if (addCount.intValue() == itemCount.intValue()) {
                    orderMap.put("assessStauts", "3");// 已追评
                }
            } else { // 如果评价未完成
                orderMap.put("assessStauts", "1");// 未评价
            }
            orderMap.put("distribution", 1);// 配送方式 1快递
            orderMap.put("orderDescription", orderMain.getOrderDescription());// 备注orderDescription
            orderMap.put("orderCode", orderMain.getOrderCode());// 订单编号
            orderMap.put("uname", orderMain.getConsigneeName());// 订单收货人姓名APP
            orderMap.put("mobile", orderMain.getConsigneeMobile());// 订单收货人手机号码APP
            orderMap.put("province", orderMain.getProvince());// 订单收货人省 APP
            orderMap.put("city", orderMain.getCity());// 订单收货人市 APP
            orderMap.put("area", orderMain.getArea());// 订单收货人区（县） APP
            orderMap.put("addr", orderMain.getConsigneeAddr());// 订单收货人地址APP
            orderMap.put("zipcode", orderMain.getZipcode());// 订单收货人邮编APP
            orderMap.put("payMethodValue", orderMain.getPayMethodValue());// 订单支付方式APP
            orderMap.put("fare", orderMain.getFare());// 运费APP
            orderMap.put("logisticsName", orderMain.getLogisticsName());// 物流公司APP
            orderMap.put("logisticsOrderNo", orderMain.getLogisticsOrderNo());// 运单编号APP
            orderMap.put("invoiceInfoType", orderMain.getInvoiceInfoType());// 发票信息
            // 类型APP
            orderMap.put("invoiceInfoTitle", orderMain.getInvoiceInfoTitle());// 发票信息
            // 抬头APP
            orderMap.put("invoiceInfoContent", orderMain.getInvoiceInfoContent());// 发票信息
            orderMap.put("orderDescription", orderMain.getOrderDescription());// 订单备注
            // 内容APP
            orderMap.put("orderStatus", orderMain.getOrderStatus());// 订单状态APP

            orderMap.put("orderType", orderMain.getOrderType());// 订单类型

            User user = loginService.queryUserByLoginId(uid);

            BigDecimal totalCredit = new BigDecimal(
                    orderMain.getTotalCredit() == null ? 0 : orderMain.getTotalCredit());
            if ((null != user && Constants.CUSTOMER_TYPE_SD_MEMBER.intValue() == user.getCustomerTypeId().intValue())
                    && (null == totalCredit || 0 == totalCredit.intValue() && null != orderMain.getOrderPv())) {
                totalCredit = orderMain.getOrderPv();
            }
            orderMap.put("totalCredit", totalCredit);// 订单提交获得的积分APP/PV
            orderMap.put("originalOrderSum", orderMain.getOriginalOrderSum());// 订单商品总金额APP
            orderMap.put("discountSum", orderMain.getDiscountSum());// 订单满减金额APP
            orderMap.put("amountPayable", orderMain.getAmountPayable());// 订单总金额APP
            orderMap.put("confirmDelivery", orderMain.getConfirmDelivery());// 送货前是否要确认
            // 0为不要其它为要
            orderMap.put("deliveryDateType", orderMain.getDeliveryDateType());// 工作日送货
            // 1工作日送货
            // 2休息日送货
            // 其他为都可以
            // orderMap.put("OrderItemList",orderMain.getOrderItemList());//商品信息
            // APP
            List<OrderItem> list = orderMain.getOrderItemList();
            List ItemList = new ArrayList();
            Map<String, Object> orderI = null;
            OrderItem orderItem = null;
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    orderI = new HashMap<String, Object>();
                    orderItem = list.get(i);
                    orderI.put("imageUrl", ConfigurationUtil.getString("PRODUCT_IMG_PATH") +
                            orderItem.getDefaultProductImage().getImgPath5());// 商品图
                    orderI.put("commodityName", orderItem.getCommodityName());// 商品名
                    orderI.put("commodityNumber", orderItem.getCommodityNumber());// //商品数量
                    orderI.put("productSkuId",
                            orderItem.getDefaultProductImage().getSkuId());// 商品SKUID
                    orderI.put("commoditySum", orderItem.getCommodityUnitPrice());// 商品单件原价20160317
                    orderI.put("commodityType", orderItem.getCommodityType());// 商品类型
                    orderI.put("pv", orderItem.getCommodityPv());// PV
                    orderI.put("appraiseId", orderItem.getAppraiseId());// 评分ID
                    orderI.put("orderItemId", orderItem.getOrderItemId());// 订单详情ID
                    ItemList.add(orderI);
                }
            }
            orderMap.put("ItemList", ItemList);// 商品信息 APP
            map.put("orderMain", orderMap); // 收货人信息 APP
            // map.put("orderMain", orderMain); //订单
            List sonList = new ArrayList();
            if (orderMain.getOrderStatus().intValue() ==
                    OrderDictionaryEnum.Order_Status.Split_Done.getKey()) {
                List<OrderMain> sonOrderMainList = myOrderService
                        .findOrderListByParentCode(orderMain.getOrderCode());
                // map.put("sonOrderMainList", sonOrderMainList);//子订单信息
                OrderMain sonOrde = null;
                Map<String, Object> sonOrder = null;
                if (sonOrderMainList.size() > 0) {
                    for (int i = 0; i < sonOrderMainList.size(); i++) {
                        sonOrder = new HashMap<String, Object>();
                        sonOrde = sonOrderMainList.get(i);
                        sonOrder.put("sonOrderCode", sonOrde.getOrderCode());// 子订单号
                        sonOrder.put("sonOrderStatus", sonOrde.getOrderStatus());// 子订单状态
                        sonList.add(sonOrder);
                    }
                }
            }
            map.put("sonList", sonList);// 子订单信息APP
            // 如果无父订单,(反之说明为拆分后的子单,不需要查询支付信息)
            if (StringUtils.isBlank(orderMain.getParentOrderCode())) {
                // sql查询条件对象
                Map<String, Object> newConditon = new HashMap<String, Object>();
                // 解析并组装查询条件
                newConditon.put("orderCode", orderMain.getOrderCode());// 订单code
                // 支付信息
                List<OrderPayStatement> orderPayStatementList = accountService
                        .findConsumptionDetailByOrderCode(newConditon);
                isParentOrder = 1;// 是主订单(父订单) 或普通订单
                Map<String, Object> OrderPay = null;
                OrderPayStatement orderPayStatement = null;
                List OrderPayList = new ArrayList();
                if (orderPayStatementList.size() > 0) {
                    for (int i = 0; i < orderPayStatementList.size(); i++) {
                        OrderPay = new HashMap<String, Object>();
                        orderPayStatement = orderPayStatementList.get(i);
                        OrderPay.put("paymentWay", orderPayStatement.getPaymentWay());// 支付方式APP
                        OrderPay.put("orderMoney", orderPayStatement.getOrderMoney());// 支付金额APP
                        OrderPayList.add(OrderPay);
                    }
                }
                map.put("orderPayInforList", OrderPayList);// 支付app
            }
            listorder = myOrderService.findOperateByNo(orderMain.getOrderCode());// 操作流水
            map.put("listorder", listorder);
            map.put("isParentOrder", isParentOrder);// 是主订单=1 子订单=0 APP
            BigDecimal addPrice = BigDecimal.ZERO;
            for (int i = 0; i < list.size(); i++) {
                orderItem = list.get(i);
                if (orderItem.getCommodityType() != null &&
                        orderItem.getCommodityType() == 5) { // 商品类型为5
                    // 加价购的价格总和
                    addPrice = addPrice.add(orderItem.getCommoditySum());
                }
            }
            map.put("addPrice", addPrice);
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "查询用户订单详情成功",
                            map));
        } catch (Exception e) {
            logger.error("查询用户订单详情失败:", e);
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "查询用户订单详情失败",
                            null));
        }
    }

    /**
     * 获取订单是否有效
     */
    public void getOrderStatus() {
        String payFlag = null;
        try {
            this.setStartParam();
            OrderMain orderMain = myOrderService.findOrderByOrderCode(orderCode);
            if (orderMain.getOrderStatus().intValue() ==
                    OrderDictionaryEnum.Order_Status.Not_Pay.getKey()) {
                Date date = orderMain.getCreateDate();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
                System.out.println(orderMain.getCreateDate() + "   " + calendar.getTime());
                Map map = new HashMap();
                Date nowdate = new Date();

                // 一次性活动，活动结束代码可以删除
                if (nowdate.after(DateTimeUtils.parseDate(
                        ConfigurationUtil.getString("APP_KMT_PAY_PRIVILEGE_STARTTIME"))) &&
                        nowdate.before(DateTimeUtils.parseDate(
                                ConfigurationUtil.getString("APP_KMT_PAY_PRIVILEGE_ENDTIME")))) {
                    String privilegeInfo = ConfigurationUtil
                            .getString("APP_KMT_PAY_PRIVILEGE_INFO");
                    map.put("privilegeInfo",
                            StringUtil.isEmpty(privilegeInfo) ? "" : privilegeInfo);
                }

                // 是否过期
                if (nowdate.after(calendar.getTime())) {
                    map.put("status", 0); // 过期了为0
                    myOrderService.cancelOrderMain(orderMain.getCustomerAccount(), orderCode,
                            orderStatus);
                    this.printJsonString(
                            new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                    "订单过期", map));
                } else {
                    payFlag = orderMainService.appCheckPayOrder(Long.parseLong(uid), orderCode);
                    if (payFlag == null) {
                        map.put("status", 1);// 没过期为1
                        BigDecimal money = orderMainService.findNeedToPayMoney(orderCode);
                        map.put("money", money);// 需支付金额APP
                        this.printJsonString(
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                        "订单没过期，可支付", map));
                    } else {
                        map.put("status", 2);// 订单不能支付
                        this.printJsonString(
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                        payFlag, map));
                    }
                }
            } else {
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                "订单不是未付款状态", null));
            }
        } catch (Exception e) {
            logger.error("订单有效期计算异常:", e);
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "订单有效期计算异常",
                            null));
        }
    }

    /**
     * 删除订单
     */
    public void deleteOrder() {
        this.setStartParam();
        try {
            this.setStartParam();
            OrderMain orderMain = myOrderService.findOrderByOrderCode(orderCode);
            if (orderMain.getOrderStatus().intValue() ==
                    OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()) {
                if (uid != null && !uid.equals("") && uid.equals(
                        myOrderService.findOrderByOrderCode(orderCode).getCustomerId()
                                .toString())) {
                    User user = loginService.queryUserByLoginId(uid);
                    myOrderService.deleteOrderMain(user.getLoginAccount(), orderCode);
                    // 0true 1 false
                    this.printJsonString(
                            new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                    "订单删除成功", null));

                } else {
                    logger.info("订单号和用户ID 不匹配");
                    this.printJsonString(
                            new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                    "订单号和用户ID 不匹配", null));
                }
            } else {
                logger.info("订单不是以取消状态");
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                "订单不是已取消状态", null));

            }
        } catch (Exception e) {
            logger.error("删除订单出错：" + e.getMessage(), e);
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "失败", null));
        }
    }

    /**
     * 确认收货
     *
     * @throws Exception ok
     */
    public void sureHaveProduct() {
        try {
            this.setStartParam();
            if (uid != null && !uid.equals("") && uid.equals(
                    myOrderService.findOrderByOrderCode(orderCode).getCustomerId().toString())) {

                // 如果此订单状态为5才执行
                if (myOrderService.findOrderByOrderCode(orderCode).getOrderStatus().intValue() ==
                        OrderDictionaryEnum.Order_Status.Ship_Done.getKey()) {
                    int result = myOrderService.sureOrderMain(uid, orderCode,
                            OrderDictionaryEnum.Order_Status.Order_Done.getKey());
                    result = 1;
                    if (result != 1) {
                        this.printJsonString(
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                        "失败", null));
                    } else {
                        this.printJsonString(
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                        "成功", null));
                    }
                } else {
                    this.printJsonString(
                            new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                    "确认收货时订单状态不为5", null));
                }
            } else {
                logger.info("订单号和用户ID 不匹配");
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                "订单号和用户ID 不匹配", null));
            }
        } catch (Exception e) {
            logger.error("确认订单出错：" + e.getMessage(), e);
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "确认订单出错",
                            null));
        }
    }

    /**
     * 保存评价
     *
     * @return OK
     */
    public void saveAssessContent() {
        try {
            setStartParam();
            User user = loginService.queryUserByLoginId(uid);
            if (user == null) {
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "找不到用户数据",
                                null));
                /*
                 * setReturnResult(new ReturnResult<Map<String, Object>>(
                 * InterfaceResultCode.FAILED, "找不到用户数据", null));
                 */
            }
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            OrderMain orderMain = myOrderService.findOrderByOrderCode(orderCode);
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
            String prodAppraisePoint = null;// 评价分数
            String appraiseContents = null;// 评价内容
            String appendContents = null;// 追评
            String appraiseId = null;// 评价ID
            JSONObject json = null;
            int appraiseIdFlag = 0;// 有几个有评论ID
            for (OrderItem oi : orderMain.getOrderItemList()) {
                json = jsonParams.getJSONObject(oi.getOrderItemId().toString());
                if (json.containsKey("appraiseId")) {
                    appraiseId = json.get("appraiseId").toString();
                }
                if (appraiseId != null && !"".equals(appraiseId)) { // 评论ID个数记录
                    Long.parseLong(appraiseId);// 如果转换异常就不是ID
                    appraiseIdFlag++;
                }
            }
            if (appraiseIdFlag > 0) {// 评论ID 数和 产品数相同为追评
                int appraiseSuccess = 0;// 追成功数量
                boolean falg = true;
                for (OrderItem oi : orderMain.getOrderItemList()) {
                    json = jsonParams.getJSONObject(oi.getOrderItemId().toString());
                    if (json.containsKey("appendContents")) {
                        appendContents = json.get("appendContents").toString();
                    }
                    if (appendContents != null && !"".equals(appendContents)) {// 追评内容
                        if (sensitiveWordFilter.doFilt(appendContents, SensitiveType.commit)) {
                            this.printJsonString(new ReturnResult<Map<String, Object>>(
                                    InterfaceResultCode.FAILED, "追加评价内容有敏感词！！", null));
                            falg = false;
                            break;
                        }
                        if (appendContents.length() > 300 || appendContents.length() < 6) {
                            this.printJsonString(new ReturnResult<Map<String, Object>>(
                                    InterfaceResultCode.FAILED, "内容长度不在6-300之间！！", null));
                            falg = false;
                            break;
                        }
                    }
                }
                if (falg) {
                    for (OrderItem oi : orderMain.getOrderItemList()) {
                        json = jsonParams.getJSONObject(oi.getOrderItemId().toString());
                        if (json.containsKey("appraiseId")) {// 有评论ID 的才是要追评的·
                            appraiseId = json.get("appraiseId").toString();
                            com.kmzyc.b2b.model.AppraiseAddtoContent appraiseAddToCondition = new com.kmzyc.b2b.model.AppraiseAddtoContent();
                            appraiseAddToCondition.setAppraiseId(Long.parseLong(appraiseId));
                            if (prodAppraiseDao.findAppendByOrderAndSku(appraiseAddToCondition) ==
                                    null) {
                                AppraiseAddtoContent appraiseAddToContent = new AppraiseAddtoContent();
                                appraiseAddToContent.setAppraiseId(Long.parseLong(appraiseId));
                                appraiseAddToContent
                                        .setAddContentDate(sim.parse(sim.format(new Date())));
                                appraiseAddToContent.setAddContent(appendContents);
                                if (appendContents.length() > 6 &&
                                        appendContents.length() < 300) {// 长度大于6小于300
                                    // ProductAppraiseRemoteService productAppraiseRemoteService =
                                    // (ProductAppraiseRemoteService) RemoteTool
                                    // .getRemote(Constants.REMOTE_SERVICE_PRODUCT,
                                    // "prodAppraiseService");
                                    productAppraiseRemoteService
                                            .saveAddtoContent(appraiseAddToContent);
                                    appraiseSuccess++;
                                } else {
                                    this.printJsonString(new ReturnResult<Map<String, Object>>(
                                            InterfaceResultCode.FAILED, "内容长度不在6-300之间！！", null));
                                }
                            }
                        }
                    }
                    if (appraiseSuccess != 0) {
                        this.printJsonString(
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                        "追评成功！！！成功条数为：" + appraiseSuccess, null));
                    } else {
                        this.printJsonString(
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                        "追评失败", null));
                    }
                }

            } else {// 首次评论
                String AssessStauts = jedisCluster
                        .get(ConfigurationUtil.getString("r_prodappraise") +
                                orderMain.getOrderCode());
                if (AssessStauts != null) {
                    logger.error("失败！！！订单" + orderMain.getOrderCode() + "已评论过了！");
                    this.printJsonString(
                            new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                    "订单！！！" + orderMain.getOrderCode() + "已评论过了！", null));
                } else {
                    // 如果评价未完成

                    // ProductAppraiseRemoteService productappraiseRemoteService =
                    // (ProductAppraiseRemoteService)
                    // RemoteTool.getRemote(Constants.REMOTE_SERVICE_PRODUCT,
                    // "prodAppraiseService");
                    String[] assessName = {"宝贝描述相符", "卖家发货速度", "物流配送速度", "售前售后服务"};
                    String[] assessType = {"Assess_Type_one", "Assess_Type_two", "Assess_Type_three", "Assess_Type_four"};
                    // OrderAssessRemoteService orderassessRemoteService =
                    // (OrderAssessRemoteService)
                    // RemoteTool
                    // .getRemote(Constants.REMOTE_SERVICE_ORDER, "orderAssessService");
                    OrderAssessInfo orderAssessInfo = new OrderAssessInfo();
                    orderAssessInfo.setOrderCode(orderMain.getOrderCode());
                    // 此处以防取不到用户的数据
                    orderAssessInfo.setGuestNum(user.getLoginAccount());
                    orderAssessInfo.setCreateDate(sim.parse(sim.format(new Date())));
                    String Assess_Type_one = jsonParams.get("Assess_Type_one").toString();
                    String Assess_Type_two = jsonParams.get("Assess_Type_two").toString();
                    String Assess_Type_three = jsonParams.get("Assess_Type_three").toString();
                    String Assess_Type_four = jsonParams.get("Assess_Type_four").toString();
                    boolean flg = true;
                    if (Integer.parseInt(Assess_Type_one) <= 0 ||
                            Integer.parseInt(Assess_Type_one) > 5) {
                        logger.info("Assess_Type_one评分分数只能在1-5之间！！！");
                        flg = false;
                        this.printJsonString(
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                        "评分分数只能在1-5之间", null));
                    }
                    if (Integer.parseInt(Assess_Type_two) <= 0 ||
                            Integer.parseInt(Assess_Type_two) > 5) {
                        logger.info("Assess_Type_two评分分数只能在1-5之间！！！");
                        flg = false;
                        this.printJsonString(
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                        "评分分数只能在1-5之间", null));
                    }
                    if (Integer.parseInt(Assess_Type_three) <= 0 ||
                            Integer.parseInt(Assess_Type_three) > 5) {
                        logger.info("Assess_Type_three评分分数只能在1-5之间！！！");
                        flg = false;
                        this.printJsonString(
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                        "评分分数只能在1-5之间", null));
                    }
                    if (Integer.parseInt(Assess_Type_four) <= 0 ||
                            Integer.parseInt(Assess_Type_four) > 5) {
                        logger.info("Assess_Type_four评分分数只能在1-5之间！！！");
                        flg = false;
                        this.printJsonString(
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                        "评分分数只能在1-5之间", null));

                    }
                    for (OrderItem oi : orderMain.getOrderItemList()) {
                        json = jsonParams.getJSONObject(oi.getOrderItemId().toString());
                        if (json.containsKey("prodAppraisePoint")) {
                            prodAppraisePoint = json.get("prodAppraisePoint").toString();
                        }
                        if (json.containsKey("appraiseContents")) {
                            appraiseContents = json.get("appraiseContents").toString();
                        }
                        if (prodAppraisePoint != null && !"".equals(prodAppraisePoint)) {// 分数验证
                            if (Integer.parseInt(prodAppraisePoint) <= 0 ||
                                    Integer.parseInt(prodAppraisePoint) > 5) {
                                logger.info("评分分数只能在1-5之间！！！");
                                this.printJsonString(new ReturnResult<Map<String, Object>>(
                                        InterfaceResultCode.FAILED, "评分分数只能在1-5之间！！", null));
                                flg = false;
                                break;
                            }
                        }
                        if (appraiseContents != null && !"".equals(appraiseContents)) {// 评价内容
                            if (sensitiveWordFilter
                                    .doFilt(appraiseContents, SensitiveType.commit)) {
                                this.printJsonString(new ReturnResult<Map<String, Object>>(
                                        InterfaceResultCode.FAILED, "评价内容有敏感词！！", null));
                                flg = false;
                                break;
                            }
                            if (appraiseContents.length() > 300 || appraiseContents.length() < 6) {
                                this.printJsonString(new ReturnResult<Map<String, Object>>(
                                        InterfaceResultCode.FAILED, "内容长度不在6-300之间！！！", null));
                                flg = false;
                                break;
                            }
                        }
                    }
                    if (flg) {// /评价数据校验通过
                        String[] orderPoint = {Assess_Type_one, Assess_Type_two, Assess_Type_three, Assess_Type_four};
                        OrderAssessDetail orderassessDetai1 = new OrderAssessDetail();
                        orderassessDetai1.setOrderCode(orderCode);
                        List<OrderAssessDetail> orderAssessDlist1 = orderAssessDetailService
                                .findOrderAssessDetailByCondition(orderassessDetai1);
                        if (orderAssessDlist1.size() == 0) {
                            // 订单评价 添加数据
                            int result = orderAssessRemoteService
                                    .createAssessInfo(orderAssessInfo, assessType, assessName,
                                            orderPoint, orderCode);
                            if (result == 1) {
                               /* // 订单评分后奖励积分
                                orderAssessService.getScoreByCondition("RU0010", 1,
                                        user.getLoginAccount(), new HashMap());*/
                            } else {
                                logger.info("订单评价 添加数据失败！！！");
                                this.printJsonString(new ReturnResult<Map<String, Object>>(
                                        InterfaceResultCode.FAILED, "订单评价 添加数据失败！！", null));
                                return;
                            }
                        }
                        com.pltfm.app.vobject.ProdAppraise prodappraise = new ProdAppraise();
                        for (OrderItem oi : orderMain.getOrderItemList()) {
                            json = jsonParams.getJSONObject(oi.getOrderItemId().toString());
                            if (json.containsKey("prodAppraisePoint")) {
                                prodAppraisePoint = json.get("prodAppraisePoint").toString();
                            }
                            if (json.containsKey("appraiseContents")) {
                                appraiseContents = json.get("appraiseContents").toString();
                            }
                            if (prodAppraisePoint != null && !"".equals(prodAppraisePoint)) {// 分数验证
                                if (Integer.parseInt(prodAppraisePoint) <= 0 ||
                                        Integer.parseInt(prodAppraisePoint) > 5) {
                                    logger.info("评分分数只能在1-5之间！！！");
                                    this.printJsonString(new ReturnResult<Map<String, Object>>(
                                            InterfaceResultCode.FAILED, "评分分数只能在1-5之间！！", null));
                                    return;
                                }
                            }
                            if (appraiseContents != null && !"".equals(appraiseContents)) {// 评价内容
                                if (sensitiveWordFilter
                                        .doFilt(appraiseContents, SensitiveType.commit)) {
                                    this.printJsonString(new ReturnResult<Map<String, Object>>(
                                            InterfaceResultCode.FAILED, "评价内容有敏感词！！", null));
                                    return;
                                }
                                if (appraiseContents.length() > 300 ||
                                        appraiseContents.length() < 6) {
                                    this.printJsonString(new ReturnResult<Map<String, Object>>(
                                            InterfaceResultCode.FAILED, "内容长度不在6-300之间！！！", null));
                                    return;
                                }
                            }

                            prodappraise.setOrderDetailId(oi.getOrderItemId());
                            prodappraise.setProductSkuId(oi.getDefaultProductImage().getSkuId());
                            prodappraise.setPoint(Short.valueOf(prodAppraisePoint));
                            prodappraise.setAppraiseDate(sim.parse(sim.format(new Date())));
                            prodappraise.setAppraiseContent(appraiseContents);
                            prodappraise.setCustId(user.getLoginId().intValue());
                            //prodappraise.setCustLevel(user.getLevelId().toString());
                            prodappraise.setCustName(user.getLoginAccount());
                            prodappraise.setSatisficing("没用的数据");
                            prodappraise.setProdBuyDate(orderMain.getCreateDate());
                            prodappraise.setProductName(oi.getCommodityName());
                            // 设置默认数据
                            prodappraise.setCheckStatus(Short.parseShort("0"));
                            prodappraise.setUserfulAmount( Long.valueOf(0));
                            prodappraise.setReplyAmount( Long.valueOf(0));
                            prodappraise.setAddtoContent("0");
                            prodappraise.setReplyContent("0");
                            // 商品评价 添加数据 判断
                            Map findMap = new HashMap();
                            findMap.put("orderDetailId", oi.getOrderItemId());
                            // 该订单详情评论条数
                            int reCount = prodAppraiseDao.findProdAppraiseByOrderDetailId(findMap);
                            if (reCount > 0) {
                                logger.error("失败！！！" + oi.getOrderItemId() + "已评论过了！");
                                this.printJsonString(new ReturnResult<Map<String, Object>>(
                                        InterfaceResultCode.FAILED,
                                        "失败！！！" + oi.getOrderItemId() + "已评论过了！", null));
                                return;
                            } else {
                                // 如果评价未完成
                                productAppraiseRemoteService.insertAppraise(prodappraise);
                               /* orderAssessService.getScoreByCondition("RU0007", 1,
                                        user.getLoginAccount(), new HashMap());*/
                            }
                            // 判断评价是否完全--------------------
                            OrderAssessDetail orderassessDetai = new OrderAssessDetail();
                            orderassessDetai.setOrderCode(orderCode);
                            List<OrderAssessDetail> orderAssessDlist = orderAssessDetailService
                                    .findOrderAssessDetailByCondition(orderassessDetai);
                            List<String> praiseId = new ArrayList<String>();
                            List<OrderItem> orderItemList = myOrderService
                                    .findAppraiseByOrdercode(orderCode);
                            for (int j = 0; j < orderItemList.size(); j++) {
                                if (orderItemList.get(j).getProdApraiseList() != null &&
                                        orderItemList.get(j).getProdApraiseList().getAppraiseId() !=
                                                0L) {
                                    praiseId.add(orderItemList.get(j).getProdApraiseList()
                                            .getAppraiseId().toString());
                                }
                            }
                            // 如果订单打分存在
                            if (orderAssessDlist.size() > 0) {
                                int count = praiseId.size();
                                // 如果订单评价完全，则写入缓存
                                if (count == orderItemList.size()) {
                                    jedisCluster.set(ConfigurationUtil.getString("r_prodappraise") +
                                            orderCode, Constants.PROD_APPRIASEDONE);
                                }
                            }
                            // -------------
                            this.printJsonString(new ReturnResult<Map<String, Object>>(
                                    InterfaceResultCode.SUCCESS, "订单评价提交成功！！！", null));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("异常失败！！！" + e.getMessage());
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "异常失败！！",
                            null));

        }
    }


    /**
     * no.4.10 手机客户端取消订单 OK
     */
    public void cancelOrder() {
        try {
            this.setStartParam();
            User user = loginService.queryUserByLoginId(uid);
            orderStatus = myOrderService.findOrderByOrderCode(orderCode).getOrderStatus()
                    .intValue();
            if (orderStatus != OrderDictionaryEnum.Order_Status.Not_Pay.getKey() &&
                    orderStatus != OrderDictionaryEnum.Order_Status.Pay_Done.getKey() &&
                    orderStatus != OrderDictionaryEnum.Order_Status.Stock_Lock.getKey() &&
                    orderStatus != OrderDictionaryEnum.Order_Status.Risk_Appraise.getKey() &&
                    orderStatus != OrderDictionaryEnum.Order_Status.Risk_Pass.getKey()) {
                logger.info("订单不是已付款，未付款，锁库，风控通过，风控不通过状态不能取消");
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                "订单不是已付款，未付款，锁库，风控通过，风控不通过状态不能取消！！！！", null));
                return;
            } else if (!myOrderService.findOrderByOrderCode(orderCode).getCustomerId().toString()
                    .equals(uid)) {
                logger.info("订单和用户不匹配！！！");
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                "订单和用户不匹配！！！！", null));
                return;
            } else {
                myOrderService.cancelOrderMain(user.getLoginAccount(), orderCode,
                        OrderAlterDictionaryEnum.Propose_Status.Cancel.getKey());
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                "取消订单成功！！！", null));
            }
        } catch (Exception e) {
            logger.error("手机客户端取消订单出错" + e.getMessage());
            this.printJsonString(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                    "异常：取消订单出错！！！", null));
        }

    }

    /**
     * 手机订单进度详情
     *
     * 20150919 wk
     */
    public void getOrderProgresInfo() {
        try {
            this.setStartParam();
            Map<String, Object> map = new HashMap<String, Object>();
            logger.info("app订单进度查询的订单编号：" + orderCode);
            OrderMain orderMain = myOrderService.findOrderByOrderCode(orderCode);
            if (orderMain.getCustomerId().compareTo(new BigDecimal(uid)) != 0) {
                logger.info("非当前用户查询退换货单详情，currentUserID：" + uid + ",退换货单id:" + orderAlterCode);
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                "非当前用户查询退换货单详情", null));
                return;
            }
            // 操作流水
            listorder = myOrderService.findOperateByNo(orderCode);
            String logisticsCode = orderMain.getLogisticsCode();
            logger.info("app订单进度查询的订单的快递公司编号：" + logisticsCode);
            String expressNo = orderMain.getLogisticsOrderNo() != null ?
                    orderMain.getLogisticsOrderNo().toString() : null;
            logger.info("app订单进度查询的订单的快递编号：" + expressNo);
            ExpressSubscription expressSubscription = null;
            if (null != expressNo && null != logisticsCode) {// 有物流信息时
                map.put("expressNo", expressNo);// 物流公司编号
                map.put("logisticsCode", ConfigurationUtil.getString(logisticsCode));// 快递单号
                // 根据查询的快递公司,为代码(如yunda); ---976186294981,
                expressSubscription = expressSubscriptionRemoteService
                        .queryOrderExpressInfo(logisticsCode, expressNo);
                if (null != expressSubscription &&
                        null != expressSubscription.getExpressTrackList()) {
                    List<ExpressTrack> trackList = expressSubscription.getExpressTrackList();
                    if (trackList != null && trackList.size() > 0) {
                        for (ExpressTrack track : trackList) {
                            track.setOper(orderMain.getLogisticsName());
                        }
                        // 重新
                        orderTrailService.mergeOrderAndExpress(listorder, expressSubscription);
                        listorder = new ArrayList();
                        OrderOperateStatement ops = null;
                        // SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        for (int i = 0; i < expressSubscription.getExpressTrackList().size(); i++) {
                            ExpressTrack expressTrack = expressSubscription.getExpressTrackList()
                                    .get(i);
                            ops = new OrderOperateStatement();
                            // String operaDateString = sf.format(expressTrack.getTrackDate());
                            ops.setNowOperateDate(expressTrack.getTrackDate());
                            ops.setOperateInfo(expressTrack.getTrackMsg());
                            ops.setNowOperator(expressTrack.getOper());
                            listorder.add(ops);
                        }
                        Collections.sort(listorder, new Comparator<OrderOperateStatement>() {
                            @Override
                            public int compare(OrderOperateStatement o1, OrderOperateStatement o2) {
                                return o2.getNowOperateDate().compareTo(o1.getNowOperateDate());
                            }
                        });
                        map.put("listorder", listorder);// 订单流水
                    }
                } else {
                    map.put("listorder", listorder);// 订单流水
                }

            } else {
                map.put("listorder", listorder);// 订单流水
            }
            map.put("orderStatus", orderMain.getOrderStatus());// 订单状态
            map.put("Extmsg", "1");// 原来标识手机端是否要查询快递信息的 现默认为1（不要）
            this.printJsonString(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                    "订单进度查询成功！！！", map));

        } catch (Exception e) {
            logger.error("app订单进度查询异常" + e.getMessage(), e);
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "订单进度查询异常！！！",
                            null));
        }
    }

    /**
     * 订单申请退换货列表 ok 20150919 wk
     */
    public void getorderApplyList() {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        try {
            this.setStartParam();
            Map<String, Object> newConditon = new HashMap<String, Object>();
            newConditon.put("userId", uid);
            OrderStatusUtil.parseOrderStatus(orderStatus, newConditon);
            if (null != productName && !"".equals(productName)) {// 20150919添加产品名 搜索
                newConditon.put("keyword", productName);
            }
            Pagination page = this.getPagination(5, pageNum);// pageNum每页多少条
            // pageNo第几页
            page.setStartindex((pageNo - 1) * pageNum + 1);
            page.setEndindex((pageNum * pageNo));
            // 设置查询条件
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, -Constants.ORDER_APPLY_TIME);
            newConditon.put("overDate", calendar.getTime()); // 过期时间
            // 添加渠道查询条件
            String channel = ConfigurationUtil.getString("CHANNEL");
            newConditon.put("channel", channel);
            page.setObjCondition(newConditon);
            this.pagintion = returnShopService.findReturnOrderMainByPage(page);
            List<OrderMain> orderMainList = pagintion.getRecordList();
            Map<String, Object> map = null;
            List orderList = new ArrayList();
            List oiList = new ArrayList();
            for (OrderMain om : orderMainList) {
                map = new HashMap<String, Object>();
                if (om.getCommerceId() == null) {
                    map.put("salesType", 1);// 1为自营
                } else {
                    map.put("salesType", 0);// 0为第三方
                }
                map.put("orderCode", om.getOrderCode());
                map.put("orderTime", om.getCreateDate());// 下订单时间
                map.put("finishDate", om.getFinishDate());// 完成时间
                map.put("orderStatus", om.getOrderStatus());// 订单状态
                for (OrderItem oi : om.getOrderItemList()) {
                    Map<String, Object> oiMap = new HashMap<String, Object>();
                    oiMap.put("productSkuId", oi.getDefaultProductImage().getSkuId());// SKUId
                    oiMap.put("orderProdUrl", ConfigurationUtil.getString("PRODUCT_IMG_PATH") +
                            oi.getDefaultProductImage().getImgPath5());
                    oiMap.put("orderItemId", oi.getOrderItemId());// orderItemId
                    oiMap.put("commodityName", oi.getCommodityName());// 商品名
                    oiMap.put("orderProNum", oi.getCommodityNumber());// 商品数量
                    oiMap.put("pv", oi.getCommodityPv());// pv
                    oiMap.put("isReturning", oi.getIsReturning());// 是否已经申请
                    oiList.add(oiMap);
                }
                map.put("oiList", printOut(oiList));
                orderList.add(map);
            }
            pageMap.put("orderList", orderList); // 订单集合
            pageMap.put("totalNum", page.getTotalRecords()); // 订单总条数（除去根订单的）
            pageMap.put("totalPage", page.getTotalpage());// 订单总页面数
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "订单申请列表成功",
                            pageMap));
        } catch (SQLException e) {
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "订单申请列表失败",
                            null));
        }
    }

    /**
     * 订单申请退换货记录列表 20150919 wk
     */
    public void getReturnShopList() {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        try {
            this.setStartParam();
            Map<String, Object> newConditon = new HashMap<String, Object>();
            newConditon.put("userId", uid);
            OrderStatusUtil.parseOrderStatus(orderStatus, newConditon);


            if (null != productName && !"".equals(productName)) {// 20150919添加产品名 搜索
                newConditon.put("keyword", productName);
            }

            Pagination page = this.getPagination(5, pageNum);// pageNum每页多少条
            // pageNo第几页
            page.setStartindex((pageNo - 1) * pageNum + 1);
            page.setEndindex((pageNum * pageNo));
            // 添加渠道查询条件
            String channel = ConfigurationUtil.getString("CHANNEL");
            newConditon.put("channel", channel);
            page.setObjCondition(newConditon);

            pagintion = returnShopService.findReturnShopByPage(page);
            List<OrderAlter> orderAlterList = pagintion.getRecordList();
            List oaList = new ArrayList();
            Map<String, Object> oaMap = null;
            for (OrderAlter oa : orderAlterList) {
                oaMap = new HashMap<String, Object>();
                oaMap.put("orderCoder", oa.getOrderCode());// 订单编号
                oaMap.put("orderAlterCode", oa.getOrderAlterCode());// 订单退换货编号
                oaMap.put("productSkuId", oa.getOrderItem().getProductSkuId());// 订单退换产品SKUID`
                oaMap.put("orderProdUrl", ConfigurationUtil.getString("PRODUCT_IMG_PATH") +
                        oa.getOrderItem().getDefaultProductImage().getImgPath5());// 订单退换产品图
                oaMap.put("commodityName", oa.getOrderItem().getCommodityName());// 商品名
                oaMap.put("alterNum", oa.getAlterNum());// 商品名数量
                oaMap.put("createDate", oa.getCreateDate());// 创建时间
                oaMap.put("proposeStatus", oa.getProposeStatus());// 审核状态
                oaMap.put("alterType", oa.getAlterType());// 退换货状态
                oaList.add(oaMap);
            }
            pageMap.put("orderList", oaList); // 订单集合
            pageMap.put("totalNum", page.getTotalRecords()); // 订单总条数（除去根订单的）
            pageMap.put("totalPage", page.getTotalpage());// 订单总页面数
            this.printJsonString(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                    "订单退换货记录列表成功", pageMap));
        } catch (Exception e) {
            logger.info("订单退换货记录列表失败" + e.getMessage());
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "订单退换货记录列表失败",
                            null));
        }

    }

    /**
     * 订单申请退换货记录详情 20150919 wk
     */
    public void getOrderApplyDetail() {
        try {
            this.setStartParam();
            List<TraceInfoVO> listVo = null;
            OrderAlter orderAlter = null;
            List<OrderAlterPhoto> photoList = null;
            orderAlter = returnShopService.findByCode(orderAlterCode);
            OrderMain om = myOrderService.findOrderByOrderCode(orderAlter.getOrderCode());
            OrderItem orderItem = orderItemService
                    .findByIdForReturnShop(orderAlter.getOrderItemId());
            if (om.getCustomerId().compareTo(new BigDecimal(uid)) != 0) {
                logger.info("非当前用户查询退换货单详情，currentUserID：" + uid + ",退换货单id:" + orderAlterCode);
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                "非当前用户查询退换货单详情", null));
                return;
            }
            photoList = returnShopService.getPhotoByBatchNo(orderAlter.getPhotoBatchNo());
            DateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 物流跟踪信息
            listVo = returnGoodsTraceService.getTraceInfo(orderAlterCode);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("alterCode", orderAlter.getOrderAlterCode());
            map.put("proposeStatus", orderAlter.getProposeStatus());
            map.put("alterType", orderAlter.getAlterType());
            map.put("time", formatStr.format(listVo.get(0).getDate()));
            map.put("info", listVo.get(0).getInfo());
            map.put("evidence", orderAlter.getEvidence());
            map.put("alterComment", orderAlter.getAlterComment());
            List list = new ArrayList();
            Map<String, Object> tiMap = null;
            for (TraceInfoVO ti : listVo) {
                tiMap = new HashMap<String, Object>();
                tiMap.put("time", formatStr.format(ti.getDate()));
                /* tiMap.put("info", ti.getInfo()); */
                tiMap.put("status", ti.getStatus());
                list.add(tiMap);
            }
            map.put("traceInfoList", list);
            map.put("productSkuId", orderItem.getProductSkuId());
            map.put("productName", orderItem.getCommodityName());
            List pList = new ArrayList();
            Map<String, Object> pMap = null;
            for (OrderAlterPhoto oap : photoList) {
                pMap = new HashMap<String, Object>();
                pMap.put("photoUrl",
                        ConfigurationUtil.getString("RETURNSHOP_PHOTO_PATH") + oap.getUrl());
                pList.add(pMap);
            }
            map.put("photo", pList);
            map.put("ruturnMoney", orderAlter.getRuturnMoney());
            map.put("fareSubsidy", orderAlter.getFareSubsidy());
            map.put("ruturnSum", orderAlter.getRuturnSum());
            map.put("customerLogisticsName", orderAlter.getCustomerLogisticsName());
            map.put("customerLogisticsNo", orderAlter.getCustomerLogisticsNo());
            map.put("addr", orderAlter.getProvince() + orderAlter.getCity() + orderAlter.getArea() +
                    orderAlter.getAddress());
            map.put("logisticsName", orderAlter.getLogisticsName());
            map.put("logisticsOrderNo", orderAlter.getLogisticsOrderNo());
            map.put("name", orderAlter.getName());
            map.put("zipcode", orderAlter.getZipcode());
            map.put("phone", orderAlter.getPhone());
            map.put("shopAddr", null);
            map.put("shopPeople", null);
            map.put("shopZipcode", null);
            map.put("shopPhone", null);
            this.printJsonString(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                    "订单退换货记录详情成功", map));

        } catch (Exception e) {
            logger.info("订单退换货记录详情失败" + e.getMessage());
            this.printJsonString(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                    "订单退换货记录详情失败", null));
        }
    }

    /**
     * 退换货流水
     */
    public void getReturnTracing() {
        try {
            this.setStartParam();
            List<TraceInfoVO> listVo = null;
            List list = new ArrayList();
            Map<String, Object> tiMap = null;
            Map<String, Object> map = new HashMap<String, Object>();
            OrderAlter orderAlter = returnShopService.findByCode(orderAlterCode);
            OrderMain om = myOrderService.findOrderByOrderCode(orderAlter.getOrderCode());
            if (om.getCustomerId().compareTo(new BigDecimal(uid)) != 0) {
                logger.info("非当前用户查询退换货单详情，currentUserID：" + uid + ",退换货单id:" + orderAlterCode);
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                "非当前用户查询退换货单详情", null));
                return;
            }
            listVo = returnGoodsTraceService.getTraceInfo(orderAlterCode);
            DateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (TraceInfoVO ti : listVo) {
                tiMap = new HashMap<String, Object>();
                tiMap.put("time", formatStr.format(ti.getDate()));
                tiMap.put("info", ti.getInfo());
                tiMap.put("nowOperator", ti.getOperator());
                list.add(tiMap);
            }
            map.put("traceInfoList", list);
            map.put("alterComment", orderAlter.getAlterComment());// 备注
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "订单退换货跟踪成功",
                            map));

        } catch (Exception e) {
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "订单退换货跟踪失败",
                            null));
        }
    }

    /**
     * 查看订单编号对应的订单评价打分
     */
    public void getAssess() {
        try {
            this.setStartParam();
            int orderHavePoint = 0;
            int prodHavePoint = 0;
            int assessStatus = 0;
            // 此处为List集合 实际上是一个订单主体。
            Map<String, Object> map = new HashMap<String, Object>();
            OrderMain orderMain = myOrderService.findOrderByOrderCode(orderCode);
            if (orderMain.getCustomerId().compareTo(new BigDecimal(uid)) != 0) {
                logger.info("查询用户订单详情失败:订单和用户不对应");
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                "查询用户订单评价出错误，订单和用户不对应", null));
            }
            // 判断是否有订单的打分
            OrderAssessDetail orderassessDetai = new OrderAssessDetail();
            orderassessDetai.setOrderCode(orderCode);
            List<OrderAssessDetail> orderAssessDlist = orderAssessDetailService
                    .findOrderAssessDetailByCondition(orderassessDetai);
            List<OrderItem> orderItemList = myOrderService.findAppraiseByOrdercode(orderCode);
            super.getRequest().setAttribute("orderAssessMap", OrderAssessPointMap.getMap());
            map.put("orderCode", orderCode);// 订单编号
            if (orderAssessDlist.size() > 0) {

                map.put("assessStatus", assessStatus);// 评价状态 为0已评价 1为未评价
                for (OrderAssessDetail orderAssessDetail : orderAssessDlist) {
                    if (orderAssessDetail.getAssessType().equals(OrderAssessPoint.getOne())) {
                        map.put("Assess_Type_one", orderAssessDetail.getAssessScore());
                    } else if (orderAssessDetail.getAssessType()
                            .equals(OrderAssessPoint.getTwo())) {
                        map.put("Assess_Type_two", orderAssessDetail.getAssessScore());
                    } else if (orderAssessDetail.getAssessType()
                            .equals(OrderAssessPoint.getThree())) {
                        map.put("Assess_Type_three", orderAssessDetail.getAssessScore());
                    } else if (orderAssessDetail.getAssessType()
                            .equals(OrderAssessPoint.getFour())) {
                        map.put("Assess_Type_four", orderAssessDetail.getAssessScore());
                    }
                }
                orderHavePoint = 1;
            } else {
                assessStatus = 1;
                map.put("assessStatus", assessStatus);// 评价状态 为0已评价 1为未评价
                this.printJsonString(
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                "订单评价信息查询异常 没有评价信息", null));
            }
            Map<String, Object> orderI = null;
            OrderItem orderItem = null;
            List<String> praiseId = new ArrayList<String>();
            List ItemList = new ArrayList();
            for (int j = 0; j < orderItemList.size(); j++) {
                orderI = new HashMap<String, Object>();
                orderItem = orderItemList.get(j);
                orderI.put("imageUrl", ConfigurationUtil.getString("PRODUCT_IMG_PATH") +
                        orderItem.getDefaultProductImage().getImgPath5());// 商品图
                orderI.put("commodityName", orderItem.getCommodityName());// 商品名
                orderI.put("commodityNumber", orderItem.getCommodityNumber());// //商品数量
                orderI.put("productSkuId", orderItem.getProductSkuId());// 商品SKUID
                orderI.put("commodityType", orderItem.getCommodityType());// 商品类型
                orderI.put("orderItemId", orderItem.getOrderItemId());// 订单详情ID
                if (orderItem.getProdApraiseList() != null) {
                    orderI.put("appraisePoint", orderItem.getProdApraiseList().getPoint());// 评分
                    orderI.put("appraiseId", orderItem.getProdApraiseList().getAppraiseId());// 评价ID
                    orderI.put("appraiseContent",
                            orderItem.getProdApraiseList().getAppraiseContent());// 评价内容
                    List<com.kmzyc.b2b.model.AppraiseAddtoContent> oiAppraiseAddList = orderItem
                            .getAppraiseAdd();
                    if (oiAppraiseAddList != null && oiAppraiseAddList.size() > 0) {
                        orderI.put("addtoContent", oiAppraiseAddList.get(0).getAddContent());// 追评内容
                    }
                }

                ItemList.add(orderI);
            }
            map.put("ItemList", ItemList);
            if (praiseId.size() > 0) {
                int count = orderAssessService.checkOrderAssessComplete(praiseId);
                if (count == orderItemList.size() && orderHavePoint == 1) {
                    prodHavePoint = 1;
                }
            }
            map.put("addAssessStatus", prodHavePoint);// 追评状态 1已完成 0为未完成
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "订单评价信息查询成功",
                            map));
        } catch (Exception e) {
            this.printJsonString(
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "订单评价信息查询异常",
                            null));
        }
    }

    public List<OrderOperateStatement> getListorder() {
        return listorder;
    }

    public void setListorder(List<OrderOperateStatement> listorder) {
        this.listorder = listorder;
    }

    public String printOut(Object obj) {
        JSONSerializer serializer = new JSONSerializer();
        serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
        serializer.write(obj);
        String jsonString = serializer.toString();
        return jsonString;
    }
}
