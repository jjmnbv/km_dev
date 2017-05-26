package com.kmzyc.supplier.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.remote.ExpressSubscriptionRemoteService;
import com.kmzyc.order.remote.OrderAlterCallBackRemoteService;
import com.kmzyc.order.remote.OrderAlterChangeStatusRemoteService;
import com.kmzyc.order.remote.OrderAlterQryRemoteService;
import com.kmzyc.product.remote.service.AftersaleReturnOrderRemoteService;
import com.kmzyc.supplier.dao.AftersaleReturnOrderDAO;
import com.kmzyc.supplier.service.AftersaleReturnOrderService;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.enums.ProductChannel;
import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.OrderAlterVo;

@Service("aftersaleReturnOrderService")
public class AftersaleReturnOrderServiceImpl implements AftersaleReturnOrderService {

    private static Logger logger = LoggerFactory.getLogger(AftersaleReturnOrderServiceImpl.class);

    @Resource
    private AftersaleReturnOrderDAO aftersaleReturnOrderDao;

    @Resource
    private AftersaleReturnOrderRemoteService aftersaleReturnOrderRemoteService;

    @Resource
    private ExpressSubscriptionRemoteService expressSubscriptionRemoteService;

    @Resource
    private OrderAlterQryRemoteService orderAlterQryRemoteService;

    @Resource
    private OrderAlterCallBackRemoteService orderAlterCallBackRemoteService;

    @Resource
    private OrderAlterChangeStatusRemoteService orderAlterChangeStatusRemoteService;

    private static final Integer operatorType = 1;

    @Override
    public Pagination searchPage(Pagination page, Map<String, Object> condition) throws ServiceException {
        try {
            List<OrderAlterVo> recordList = orderAlterQryRemoteService.queryOrderAlterListByMap(condition);
            int count = orderAlterQryRemoteService.countOrderAlterListByMap(condition);
            page.setRecordList(recordList);
            page.setTotalRecords(count);
            return page;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public OrderAlter findByPrimaryKey(String orderCode) throws ServiceException {
        try {
            return orderAlterQryRemoteService.getOrderAlterByCode(orderCode);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List listOrderAlterOperates(String alterCode) throws ServiceException {
        try {
            return orderAlterQryRemoteService.listOrderAlterOperates(alterCode);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage checkedDistributionInfo(Long[] pIds) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        /*try {
            // resultMessage=aftersaleReturnOrderRemoteService.checkedDistributionInfo(pIds);
        } catch (Exception e) {
            logger.error("审核配送单出现异常:", e);
            throw new ServiceException(0, "", e);
        }*/
        return resultMessage;
    }

    @Override
    public ResultMessage checkedStockOut(Long[] sIds, Integer loginUserId, String loginUserName) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        try {
            // resultMessage=aftersaleReturnOrderRemoteService.checkedStockOut(sIds,loginUserId,loginUserName);
        } catch (Exception e) {
            logger.error("出库单批量审核:审核功能出现异常:", e);
            throw new ServiceException(e);
        }
        return resultMessage;
    }

    @Override
    public int updateObjectSer(AftersaleReturnOrder newOrder, Integer userId, String userName, boolean bol)
            throws ServiceException {
        int resultCount = -1;
        try {
            resultCount = aftersaleReturnOrderRemoteService.updateObject(newOrder, userId, userName, bol);
        } catch (Exception e) {
            logger.error("操作出现异常:", e);
            throw new ServiceException(e);
        }
        return resultCount;
    }

    @Override
    public int checkOrderAlter(String operator, String alterCode, Short type, BigDecimal fareSubsidy,
                               BigDecimal returnMoney, BigDecimal returnFare, BigDecimal returnSum,
                               BigDecimal preferentialAmount, String comment) throws ServiceException {
        int count = 0;
        try {
            count = orderAlterQryRemoteService.checkOrderAlter(operatorType, operator, alterCode, type,
                    fareSubsidy, returnMoney, returnFare, returnSum, preferentialAmount, comment);
        } catch (Exception e) {
            logger.error("退换货审核出现异常:", e);
            throw new ServiceException(e);
        }
        return count;
    }

    @Override
    public int changeAlterStatus(String sysOperate, OrderAlter oa) throws ServiceException {

        // 如果是返回原件则需要 订阅物流信息
        if (StringUtils.isNotBlank(oa.getLogisticsCode()) && StringUtils.isNotBlank(oa.getLogisticsOrderNo())) {
            subscribeExpressInfo(oa.getLogisticsCode(), oa.getLogisticsOrderNo(), oa.getOrderCode());
        }

        try {
            return orderAlterQryRemoteService.changeAlterStatus(operatorType, sysOperate, oa);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public AftersaleReturnOrder selectByOrderCode(String orderCode) throws ServiceException {
        AftersaleReturnOrder aftersaleReturnOrder = null;
        try {
            aftersaleReturnOrder = aftersaleReturnOrderDao.selectByOrderCode(orderCode);
        } catch (Exception e) {
            logger.error("根据退换货单号查询产品系统数据出现异常:", e);
            throw new ServiceException(e);
        }
        return aftersaleReturnOrder;
    }

    @Override
    public AftersaleReturnOrder findAfterSaleReturnOrderInfo(String orderCode, String handleResult, long returnId)
            throws ServiceException {
        try {
            if ("2".equals(handleResult)) {
                AftersaleReturnOrder record = new AftersaleReturnOrder();
                record.setReturnId(returnId);
                record.setHandleResult("3");
                aftersaleReturnOrderDao.updateByPrimaryKeySelective(record);
            }
            return aftersaleReturnOrderDao.selectByOrderCode(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public OrderItem getOrderById(Long itemId) throws ServiceException {
        try {
            return orderAlterQryRemoteService.getOrderItemById(itemId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List getPhotoList(String photo) throws ServiceException {
        try {
            return orderAlterQryRemoteService.getPhotoByBatchNo(photo);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int setLogisticAndDistributionInfo(LogisticAndDistributionInfoVO logisticAndDistributionInfoVO)
            throws ServiceException {
        int resultCount = 0;
        try {
            // 加入订阅物流的推送
            subscribeExpressInfo(logisticAndDistributionInfoVO.getLogisticsCode(),
                    logisticAndDistributionInfoVO.getLogisticsOrderNo(),
                    logisticAndDistributionInfoVO.getOrderAlterCode());

            logger.info("换货单物流单号推送准备开始!");
            String result = orderAlterCallBackRemoteService.getLogisticNumber(logisticAndDistributionInfoVO);
            if (result.equals("SUCCESS")) {
                resultCount = 1;
            }
            logger.info("换货单物流单号推送完成!返回结果=" + result);
        } catch (Exception e) {
            logger.error("操作出现异常:", e);
            throw new ServiceException(e);
        }
        return resultCount;
    }

    @Override
    public String exportReturnOrder(Map map) throws ServiceException {
        try {
            return orderAlterChangeStatusRemoteService.exportSellerAlterOrders(map);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    protected void subscribeExpressInfo(String logisticCode, String logisticOrderNo, String orderCode)
            throws ServiceException {
        try {
            // 加入订阅物流的推送
            ExpressSubscription expressSubscription = new ExpressSubscription();
            expressSubscription.setLogisticsCode(logisticCode);
            expressSubscription.setLogisticsNo(logisticOrderNo);
            expressSubscription.setOrderCode(orderCode);
            expressSubscription.setChannel(ProductChannel.B2B.getStatus());
            String result = expressSubscriptionRemoteService.sucribeOrderExpressInfo(expressSubscription);
            logger.info("退换货订单号orderCode=" + orderCode + ",订阅物流信息完成!返回结果=" + result);
        } catch (Exception e) {
            logger.error("退换货订单号orderCode=" + orderCode + ",订阅物流信息失败：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public BigDecimal getOrderTotalReturnFare(String orderCode) throws ServiceException {
        try {
            return orderAlterQryRemoteService.selectReturnFare(orderCode);
        } catch (Exception e) {
            logger.error("getOrderTotalReturnFare发生异常,具体信息为:", e);
        }
        return BigDecimal.ZERO;
    }

}
