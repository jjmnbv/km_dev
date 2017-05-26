package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kmzyc.b2b.dao.Live800Dao;
import com.kmzyc.b2b.service.Live800Service;
import com.kmzyc.b2b.service.member.AddressService;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.order.remote.OrderAlterQryRemoteService;
import com.kmzyc.order.remote.OrderQryRemoteService;
import com.pltfm.app.vobject.OrderAlterVo;
import com.pltfm.app.vobject.OrderMainVo;

// import com.km.framework.common.util.RemoteTool;

@Service("live800Service")
public class Live800ServiceImpl implements Live800Service {

    // private static final Logger logger = Logger.getLogger(Live800ServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(Live800ServiceImpl.class);

    @Resource
    private Live800Dao live800Dao;

    @Resource(name = "addressServiceImpl")
    private AddressService addressService;

    @Resource
    private OrderQryRemoteService orderQryRemoteService;
    @Resource
    private OrderAlterQryRemoteService orderAlterQryRemoteService;

    /**
     * 查询用户信息
     */
    @Override
    public Map<String, Object> getCustomInfo(long userId) throws ServiceException {
        Map<String, Object> result = Maps.newHashMap();
        try {

            result.put("baseInfo", live800Dao.getCustomInfo(userId));
            // 地址信息从addressService
            result.put("goodsReceiptAdd", addressService.findByLoginId(((Long) userId).intValue()));
            /* live800Dao.getGoodsReceiptAddress(userId) */
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return result;
    }

    /**
     * orderCode 订单号， orderChannel 渠道， customerAccount 客户账号(下单账号)， commerceId 销售类型（null为自营，否则为第三方）,
     * consigneeName 收货人， orderStatusStr 订单状态 (1：已取消；1：未付款；
     * 2：已付款；3：已结转；4：已出库；5：已配送；6：已完成；12：送货失败；15：已结转未出库；16：已拆分；17：已合并 ；18：已拆分未结转；19：已合并未结转)
     * amountPayable 订单金额 createDate 下单日期
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getCustomOrderInfo(Map<String, Object> args) throws ServiceException {
        Map<String, Object> result = Maps.newHashMap();
        List<Map<String, Object>> orderList = Lists.newArrayList();
        try {
            // OrderQryRemoteService orderService =
            // (OrderQryRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_ORDER,
            // "orderQryService");
            Map<String, Object> queryResult =
                    orderQryRemoteService.queryOrderListForConsultation(args);
            if (null == queryResult || queryResult.isEmpty()) {
                return result;
            }
            List<OrderMainVo> orderMain = (List<OrderMainVo>) queryResult.get("orderMain");
            if (null != orderMain) {
                for (OrderMainVo vo : orderMain) {
                    Map<String, Object> info = Maps.newHashMap();
                    info.put("orderCode", vo.getOrderCode());
                    info.put("orderChannel", vo.getOrderChannel());
                    info.put("customerAccount", vo.getCustomerAccount());
                    info.put("commerceId", vo.getCommerceId());
                    info.put("consigneeName", vo.getConsigneeName());
                    info.put("consigneeMobile", vo.getConsigneeMobile());
                    info.put("orderStatusStr", vo.getOrderStatusStr());
                    info.put("amountPayable", vo.getAmountPayable());
                    info.put("createDate", vo.getCreateDate());
                    orderList.add(info);
                }
                result.put("orderList", orderList);
            }

            double orderMoney = MapUtils.getDoubleValue(queryResult, "orderMoney"); // 订单总金额
            result.put("orderMoney", orderMoney);

            double orderActual = MapUtils.getDoubleValue(queryResult, "orderActual"); // 订单实际金额
            result.put("orderActual", orderActual);
        } catch (Exception e) {
            logger.error("获取用户：" + MapUtils.getString(args, "customerAccount") + "的定单信息失败！", e);
        }
        return result;
    }

    /**
     * orderCode 订单号 orderAlterCode 退换货编号 commodityName 商品标题 alterTypeStr类型 proposeStatusStr 状态
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getCustomExchangeInfo(Map<String, Object> args)
            throws ServiceException {
        List<Map<String, Object>> result = Lists.newArrayList();
        try {
            // OrderAlterQryRemoteService orderService =
            // (OrderAlterQryRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_ORDER,
            // "orderAlterQryService");
            List<OrderAlterVo> orderList =
                    orderAlterQryRemoteService.queryOrderAlterListForConsultation(args);
            if (null != orderList) {
                for (OrderAlterVo vo : orderList) {
                    Map<String, Object> info = Maps.newHashMap();
                    info.put("orderCode", vo.getOrderCode());
                    info.put("orderAlterCode", vo.getOrderAlterCode());
                    info.put("commodityName", vo.getCommodityName());
                    info.put("alterTypeStr", vo.getAlterTypeStr());
                    info.put("proposeStatusStr", vo.getProposeStatusStr());
                    result.add(info);
                }
            }
        } catch (Exception e) {
            logger.error("获取用户：" + MapUtils.getString(args, "customerAccount") + "的退换货信息失败！", e);
        }
        return result;
    }

    @Override
    public long getUserId(String userName) throws ServiceException {
        try {

            return live800Dao.getUserId(userName);
        } catch (SQLException e) {
            logger.error("查询登陆id出现异常！ " + e.getMessage());
            throw new ServiceException(e);
        }
    }
}
