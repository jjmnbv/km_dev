package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tempuri.IMemberInfo;

import com.kmzyc.b2b.dao.LoginDao;
import com.kmzyc.b2b.model.OrderAlter;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.OrderPvSyncRemoteService;
import com.kmzyc.b2b.service.member.impl.MyOrderServiceImpl;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderItemQryRemoteService;
import com.kmzyc.order.remote.OrderSyncRemoteService;
import com.kmzyc.user.remote.service.UserGrowingService;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.util.OrderDictionaryEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

// import com.km.framework.common.util.RemoteTool;

@SuppressWarnings({"unchecked", "SpringJavaAutowiringInspection"})
@Service("orderPvSyncRemoteService")
public class OrderPvSyncRemoteServiceImpl implements OrderPvSyncRemoteService {

    @Resource(name = "myOrderServiceImpl")
    private MyOrderServiceImpl myOrderServiceImpl;
    @Resource(name = "loginServiceImp")
    private LoginService loginService;
    @Resource
    private OrderItemQryRemoteService orderItemQryRemoteService;
    @Resource
    private OrderSyncRemoteService orderSyncRemoteService;
    //客户系统的积分服务类
    @Resource(name = "userRemoteGrowingService")
    private UserGrowingService userRemoteGrowingService;

    @Resource(name = "loginDaoImp")
    private LoginDao loginDao;


    // private static Logger logger = Logger.getLogger(MyOrderServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(OrderPvSyncRemoteServiceImpl.class);


    @Override
    public Map orderPvSync(List orderCodeList) {
        Map reMap = new HashMap();
        try {
            IMemberInfo im = loginService.findWebservice();
            com.pltfm.app.entities.OrderItem orderItem;
            for (int i = 0; i < orderCodeList.size(); i++) {
                try {
                    logger.error("PV同步orderCode：" + orderCodeList.get(i).toString());
                    List<com.pltfm.app.entities.OrderItem> orderItemList = orderItemQryRemoteService
                            .getOrderItemBySingleOrderCode(orderCodeList.get(i).toString());
                    JSONObject json = new JSONObject();
                    JSONArray jsonArr = new JSONArray();
                    JSONObject jsonObj = new JSONObject();
                    OrderMain om = myOrderServiceImpl
                            .findOrderByOrderCode(orderCodeList.get(i).toString());
                    jsonObj.put("orderID", om.getOrderCode());

                    jsonObj.put("ordertype", "53");


                    double pv = 0d;
                    double totalMoney = 0d;
                    double orderTotalMoney = 0.00d;
                    for (OrderItem anOrderItemList : orderItemList) {
                        orderItem = anOrderItemList;
                        Map map = new HashMap();
                        map.put("orderCode", om.getOrderCode());
                        map.put("orderItemId", orderItem.getOrderItemId());
                        OrderAlter orderAlter = myOrderServiceImpl.findOrderAlterPv(map);
                        Long alterNum = 0L;
                        if (orderAlter != null) {
                            alterNum = orderAlter.getAlterNum();
                        }
                        if (om.getOrderType() == OrderDictionaryEnum.Order_Type.Normal.getKey() ||
                                om.getOrderType() ==
                                        OrderDictionaryEnum.Order_Type.YsOrder.getKey()) {
                            System.out.println(
                                    orderItem.getCommoditySum().doubleValue() + "--------" +
                                            orderItem.getCostIncomeRatio());
                            // totalMoney=totalMoney+(orderItem.getCommoditySum().doubleValue()*orderItem.getCostIncomeRatio());
                            totalMoney = totalMoney +
                                    ((orderItem.getCostIncomeMoney() == null ? 0.00 :
                                            orderItem.getCostIncomeMoney().doubleValue()) *
                                            (orderItem.getCommodityNumber() - alterNum));// 如果有退货的
                            orderTotalMoney =
                                    orderItem.getCommoditySum().doubleValue() + orderTotalMoney;
                            // orderItem.getCostIncomeMoney();
                        }
                        pv = ((orderItem.getCommodityPv() == null ? 0.00 :
                                orderItem.getCommodityPv().doubleValue()) *
                                (orderItem.getCommodityNumber() - alterNum)) + pv;// 如果有退货的
                        json.put("orderItemID", orderItem.getOrderItemId());// 订单明细号,整数
                        json.put("commodityTitle", orderItem.getCommodityTitle());// 商品标题,字符串
                        json.put("commoditySKU", orderItem.getCommoditySku());// 商品SKU,字符串
                        json.put("commoditySKUDescription",
                                orderItem.getCommoditySkuDescription());// 商品SKU描述,字符串
                        json.put("commodityUnitIncoming",
                                orderItem.getCommodityUnitIncoming());// 商品单品实收金额,浮点数
                        json.put("commodityNumber",
                                orderItem.getCommodityNumber() - alterNum);// 商品数量,整数(减去退货数量)
                        json.put("commodityUnitPV", orderItem.getCommodityPv() == null ? 0.00 :
                                orderItem.getCommodityPv());// 商品单品可获PV,浮点数
                        json.put("commodityPartnerProfit", 0.00);// 商品单品合作方可获收益,浮点数
                        json.put("ProductID", orderItem.getErpProCode());// 商品ID
                        jsonArr.add(json);
                    }
                    jsonObj.put("detailList", jsonArr);// json格式的订单产品列表
                    jsonObj.put("orderTotalMoney", orderTotalMoney);// 实际金额
                    jsonObj.put("totalMoney", 0.00);// 获利金额
                    jsonObj.put("totalPv", pv);// PV
                    jsonObj.put("carryMoney", om.getFare());// 运费
                    jsonObj.put("consignee", om.getConsigneeName());// 收货人
                    jsonObj.put("ccpcCode", om.getProvince() + "," + om.getCity() + "," +
                            om.getArea());// 城市表编码或字符串，例："广东省,广州市,天河区"
                    jsonObj.put("conAddress", om.getConsigneeAddr());// 收货地址
                    jsonObj.put("conZipCode",
                            null != om.getZipcode() ? om.getZipcode() : " ");// 收货邮编
                    jsonObj.put("conTelphone", om.getConsigneeMobile());// 收货电话
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 时间 格式
                    jsonObj.put("paymentDate", sdf.format(om.getPayDate()));// paymentDate支付时间
                    jsonObj.put("completeDate", sdf.format(om.getFinishDate()));// / <param
                    // name="completeDate">订单完成时间</param>
                    Long loginId = om.getCustomerId().longValue();
                    User user = loginDao.getUserByLoginId(loginId);
                    jsonObj.put("number", user.getLoginAccount());// 会员编号
                    logger.info("PV同步测试:" + jsonObj.toString());

                    logger.info("PV同步测试:" + jsonObj.get("orderID").toString() + "," +
                            jsonObj.get("ordertype").toString() + "," +
                            jsonObj.get("number").toString() + "," + jsonObj.get("totalPv") + "," +
                            jsonObj.get("totalMoney") + "," + jsonObj.get("orderTotalMoney") + "," +
                            om.getFare().doubleValue() + "," + jsonObj.get("consignee").toString() +
                            "," + jsonObj.get("ccpcCode").toString() + "," +
                            jsonObj.get("conAddress").toString() + "," +
                            jsonObj.get("conZipCode").toString() + "," +
                            jsonObj.get("conTelphone").toString() + "," +
                            jsonObj.get("detailList").toString() +
                            jsonObj.get("paymentDate").toString() +
                            jsonObj.get("completeDate").toString());
                    String re;
                    if (0f < pv) {
                        // 0成功 -1 异常 -2重复提交！
                        re = im.memberOrderCreate(jsonObj.get("orderID").toString(),
                                jsonObj.get("ordertype").toString(),
                                jsonObj.get("number").toString(),
                                (Double) jsonObj.get("totalMoney"), (Double) jsonObj.get("totalPv"),
                                (Double) jsonObj.get("orderTotalMoney"), om.getFare().doubleValue(),
                                jsonObj.get("consignee").toString(),
                                jsonObj.get("ccpcCode").toString(),
                                jsonObj.get("conAddress").toString(),
                                jsonObj.get("conZipCode").toString(),
                                jsonObj.get("conTelphone").toString(),
                                jsonObj.get("detailList").toString(),
                                jsonObj.get("paymentDate").toString(),
                                jsonObj.get("completeDate").toString());
                        // //re="0";//测试使用
                        logger.info("同步orderCode：" + orderCodeList.get(i).toString() +
                                "pv大于0,*0成功 -1 异常 -2重复提交！同步结果 ：" + re);
                    } else {
                        re = "0";// pv为0时也是成功的
                        logger.info("同步orderCode：" + orderCodeList.get(i).toString() + "pv为0" + re);
                    }
                    reMap.put(om.getOrderCode(), re);// code:re

                    // 订单系统同步标识 0:失败 1:成功，2：未同步
                    if ("0".equals(re) || "-2".equals(re)) {
                        re = "1";
                    } else if ("-1".equals(re)) {
                        re = "0";
                    }
                    logger.info("同步orderCode：" + orderCodeList.get(i).toString() + ",最终re:" + re);

                    com.pltfm.app.entities.OrderSync orderSync = new com.pltfm.app.entities.OrderSync();
                    orderSync.setOrderCode(om.getOrderCode());// 同步订单
                    orderSync.setSyncFlag((short) Integer.parseInt(re));// 同步状态
                    orderSync.setOutCode(user.getLoginAccount());// 直销会员编号
                    orderSync.setSyncDate(new Date());// 同步时间
                    orderSync.setOrderProfit(new BigDecimal(totalMoney));// 获利金额
                    orderSync.setOrderPv(new BigDecimal(pv)); // 同步PV
                    String orderRe = orderSyncRemoteService.updateOrderSync(orderSync);
                    logger.info("* 成功返回1 失败返回0订单同步启记录返回结果 ：" + orderRe);

                    //START 更新用户积分
                    if("1".equals(re)){
                        try {
                            String loginAccout = om.getCustomerAccount();
                            BigDecimal thepv = new BigDecimal(pv);
                            Integer resu = userRemoteGrowingService
                                    .updateScoreInfoForTimeMember(1, loginAccout, thepv);
                            if (resu != 1) {
                                logger.error("订单:" + om.getOrderCode() + "修改用户pv积分出错！i =" + i);
                            }
                        } catch (Exception e) {
                            logger.error("订单:" + om.getOrderCode() + "更新用户pv积分失败!");
                        }
                    }
                    //END 更新用户积分
                    

                } catch (ServiceException se) {
                    logger.error("同步orderCode：" + orderCodeList.get(i).toString() +
                            "更新同步表失败,PV同步已忽略ServiceException异常：" + se.getMessage(), se);
                } catch (Exception e1) {
                    logger.error("同步orderCode：" + orderCodeList.get(i).toString() + ",PV同步已忽略异常：" +
                            e1.getMessage(), e1);
                }
            }
        } catch (Exception e) {
            logger.error("PV同步webService：" + e.getMessage(), e);
            return reMap;
        }
        return reMap;
    }


}
