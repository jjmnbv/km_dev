package com.kmzyc.supplier.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.km.framework.page.Pagination;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.app.vobject.DistributionInfo;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.StockOut;

public interface AftersaleReturnOrderService {

    /**
     * 分页列表
     *
     * @param page
     * @param condition
     * @throws Exception
     */
    Pagination searchPage(Pagination page, Map<String, Object> condition) throws ServiceException;

    /**
     * 根据主键获取退货单
     *
     * @param orderCode
     * @return
     * @throws ServiceException
     */
    OrderAlter findByPrimaryKey(String orderCode) throws ServiceException;

    /**
     * 确认到货等一系列操作
     *
     * @param newOrder
     * @param userId
     * @param userName
     * @param bol
     * @return
     */
    int updateObjectSer(AftersaleReturnOrder newOrder, Integer userId, String userName, boolean bol)
            throws ServiceException;

    /**
     * 出库单批量审核:审核功能
     *
     * @return
     * @throws ServiceException
     */
    ResultMessage checkedStockOut(Long[] sIds, Integer loginUserId, String loginUserName)
            throws ServiceException;

    /**
     * 配送单审核:完成配送单审核功能
     *
     * @return
     * @throws ServiceException
     */
    ResultMessage checkedDistributionInfo(Long[] pIds) throws ServiceException;

    /**
     * 退换货操作流水
     *
     * @param alterCode
     * @return
     * @throws ServiceException
     */
    List listOrderAlterOperates(String alterCode) throws ServiceException;

    /**
     * 退换货审核
     *
     * @param operator
     * @param alterCode
     * @param type
     * @param returnFare
     * @param returnMoney
     * @param preferentialAmount
     * @param comment
     * @return
     * @throws ServiceException
     */
    int checkOrderAlter(String operator, String alterCode, Short type, BigDecimal fareSubsidy,
                        BigDecimal returnMoney, BigDecimal returnFare, BigDecimal returnSum,
                        BigDecimal preferentialAmount, String comment) throws ServiceException;

    /**
     * 确认退款或者返回原件
     *
     * @param sysOperate
     * @param oa
     * @return
     * @throws ServiceException
     */
    int changeAlterStatus(String sysOperate, OrderAlter oa) throws ServiceException;

    AftersaleReturnOrder selectByOrderCode(String orderCode) throws ServiceException;

    AftersaleReturnOrder findAfterSaleReturnOrderInfo(String orderCode, String handleResult, long returnId)
            throws ServiceException;

    OrderItem getOrderById(Long itemId) throws ServiceException;

    List getPhotoList(String photo) throws ServiceException;

    int setLogisticAndDistributionInfo(LogisticAndDistributionInfoVO logisticAndDistributionInfoVO)
            throws ServiceException;

    /**
     * 退换货订单导出
     *
     * @param map
     * @return
     * @throws ServiceException
     */
    String exportReturnOrder(Map map) throws ServiceException;


    /**
     * 取得某订单下累计的退货返运费值
     *
     * @param orderCode
     * @return
     * @throws ServiceException
     */
    BigDecimal getOrderTotalReturnFare(String orderCode) throws ServiceException;


}
