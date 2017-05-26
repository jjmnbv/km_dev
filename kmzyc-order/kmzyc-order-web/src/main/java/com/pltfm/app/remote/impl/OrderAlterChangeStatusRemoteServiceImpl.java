package com.pltfm.app.remote.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderAlterChangeStatusRemoteService;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderAlterPhoto;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.service.OrderAlterOperateStatementService;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.OrderReturnService;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.SysConstants;
import com.pltfm.sys.util.ErrorCode;

@Service("orderAlterChangeStatusRemoteService")
@SuppressWarnings("unchecked")
public class OrderAlterChangeStatusRemoteServiceImpl
        implements
            OrderAlterChangeStatusRemoteService {

    private static final long serialVersionUID = 7601831739410155832L;

    private Logger log = Logger.getLogger(OrderAlterChangeStatusRemoteServiceImpl.class);

    @Resource
    private OrderQryService orderQryService;
    @Resource
    private OrderOperateStatementService orderOperateStatementService;
    @Resource
    private OrderAlterOperateStatementService orderAlterOperateStatementService;
    @Resource
    private OrderReturnService orderReturnService;

    @Override
    public int changeAlterStatus(String operator, OrderAlter oa) throws ServiceException {
        int result = 0;
        try {
            result = orderAlterOperateStatementService.changeAlterStatus(operator, oa);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_RETURNS_ERROR,
                    "退换货单更改状态发生错误：" + e.getMessage());
        }
        return result;
    }

    @Override
    public int alter(OrderAlter oa) throws ServiceException {
        int result = 0;
        try {
            if (oa.getAlterType().intValue() == OrderAlterDictionaryEnum.AlterTypes.Refund
                    .getKey()) {
                OrderMain order = orderQryService.getOrderByCode(oa.getOrderCode());
                if (order.getOrderStatus().intValue() < 3) {
                    log.error("订单此状态下不能确认完成！");
                    throw new ActionException();
                }
                if (order.getOrderStatus()
                        .intValue() != (OrderDictionaryEnum.Order_Status.Order_Done.getKey())) {
                    orderOperateStatementService.changeOrderStatus(SysConstants.SYS_KEY,
                            oa.getOrderCode(),
                            (long) OrderDictionaryEnum.Order_Status.Order_Done.getKey(), null);
                }
            } else if (oa.getAlterType().intValue() == OrderAlterDictionaryEnum.AlterTypes.Indemnity
                    .getKey()) {
                OrderMain order = orderQryService.getOrderByCode(oa.getOrderCode());
                if (!(order.getOrderStatus() == 2 || order.getOrderStatus() == 3
                        || order.getOrderStatus() == 4 || order.getOrderStatus() == 15
                        || order.getOrderStatus() == 20 || order.getOrderStatus() == 21
                        || order.getOrderStatus() == 22)) {
                    log.error("订单此状态下不能确认完成！");
                    throw new ActionException();
                }
            }
            result = orderReturnService.alter(oa);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_RETURNS_ERROR,
                    "退换货申请发生错误：" + e.getMessage());
        }
        return result;
    }

    @Override
    public Map compute(String orderCode, Long orderItemId, Long alterNum) throws ServiceException {
        Map map = null;
        try {
            map = orderReturnService.compute(orderCode, orderItemId, alterNum);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_RETURNS_ERROR,
                    "退换货计算发生错误：" + e.getMessage());
        }
        return map;
    }

    @Override
    public int savaPhoto(OrderAlterPhoto photo) throws ServiceException {
        int result = 0;
        try {
            result = orderReturnService.savaPhoto(photo);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_RETURNS_ERROR,
                    "退换货保存图片发生错误：" + e.getMessage());
        }
        return result;
    }

    @Override
    public int changeAlterStatusForProduct(String operator, String orderAlterCode, Long status,
            String comment) throws ServiceException {
        int result = 0;
        try {
            result = orderAlterOperateStatementService.changeAlterStatusForProduct(operator,
                    orderAlterCode, status, comment);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_RETURNS_ERROR,
                    "退换货更改状态发生错误：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String exportSellerAlterOrders(Map map) throws ServiceException {
        try {
            return orderReturnService.exportSellerAlterOrders(map);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR,
                    "导出商家退换货订单异常：" + e.getMessage());
        }
    }


}
