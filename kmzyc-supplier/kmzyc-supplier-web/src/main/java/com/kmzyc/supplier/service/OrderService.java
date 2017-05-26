package com.kmzyc.supplier.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.supplier.model.OrderItems;
import com.kmzyc.supplier.model.OrderMain;
import com.kmzyc.supplier.model.ProdAppraiseAddContent;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.OrderCarry;
import com.pltfm.app.vobject.InvoiceVo;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.OrderMainVo;
import com.pltfm.app.vobject.OrderOperateStatementVo;
import com.pltfm.app.vobject.OrderPreferentialEVO;

public interface OrderService {

    /**
     * 用于各个订单列表查询,比如所有订单,已完成,待结转等等订单分页查询总数
     *
     * <note>
     *     此前的这种列表查询是采用订单接口调用,现在修改为自己写sql实现查询
     * </note>
     *
     * @param condition   分页对象
     *                       封存条件对象 订单状态: 采用的是订单系统中的枚举 OrderDictionaryEnum.Order_Status -1：已取消
     *                     Cancel_Done ; -2：取消审核中 Cancel_Checking; 1：未付款 Not_Pay; 2：已付款 Pay_Done; 3：已结转
     *                     Settle_Done; 4：已出库 Stock_Done; 5：已配送 Ship_Done; 6：已完成 Order_Done; 12：送货失败 Ship_Fail ;
     *                     15：已结转未出库 Settle_Not_Stock; 16：已拆分 Split_Done; 17：已合并 Merge_Done; 18：已拆分未结转
     *                     Splited_Not_Settle; 19：已合并未结转 Merge_Not_Settle
     * @return 分页对象, 已封装好list
     */
    List<OrderMain> findOrderList(Map<String, Object> condition) throws ServiceException;

    /**
     * 记录数
     *
     * @param condition
     * @return
     * @throws ServiceException
     */
    Integer findOrderListCount(Map<String, Object> condition) throws ServiceException;

    /**
     * 单个订单
     *
     * @param orderCode
     * @return
     * @throws ServiceException
     */
    OrderMainVo findOrderByOrderCode(String orderCode) throws ServiceException;

    /**
     * 获取子订单
     *
     * @param orderCode
     * @return
     * @throws ServiceException
     */
    List<OrderItems> findOrderItemByOrderCode(String orderCode) throws ServiceException;

    /**
     * 操作流水
     *
     * @param orderCode
     * @return
     * @throws ServiceException
     */
    List<OrderOperateStatementVo> findOrderOperateStatementByOrderCode(String orderCode) throws ServiceException;

    /**
     * 优惠信息
     *
     * @param orderCode
     * @return
     * @throws ServiceException
     */
    List<OrderPreferentialEVO> findOrderPreferentialByOrderCode(String orderCode) throws ServiceException;

    /**
     * 发票明细
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    List<InvoiceVo> findInvoiceItemsById(Long id) throws ServiceException;

    /**
     * 发票基本信息
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    Invoice findInvoiceById(Long id) throws ServiceException;

    /**
     * 是否需要补单
     *
     * @param map
     * @return
     * @throws ServiceException
     */
    Boolean checkIsAdditional(Map<String, Object> map) throws ServiceException;

    /**
     * 获取订单支付信息
     * @param map
     * @return
     * @throws ServiceException
     */
    BigDecimal getOrderPay(Map<String, Object> map) throws ServiceException;

    /**
     * 订单手动结转
     *
     * @return
     * @throws ServiceException
     */
    List<OrderCarry> carryOver(Date beginDate, Date endDate, Long commerceId, String orderCode) throws ServiceException;

    /**
     * 历史结转列表
     *
     * @param condition
     * @return
     * @throws ServiceException
     */
    List<OrderCarry> carryOverList(Map<String, Object> condition) throws ServiceException;

    Integer carryOverListCount(Map<String, Object> condition) throws ServiceException;

    /**
     * 修改订单备注信息
     *
     * @param map
     * @return
     * @throws ServiceException
     */
    int editOrderRemark(Map<String, String> map) throws ServiceException;

    /**
     * 推送配送单号给订单
     *
     * @param logisticAndDistributionInfoVO
     * @return
     * @throws ServiceException
     */
    String sendLogisticsInfo(LogisticAndDistributionInfoVO logisticAndDistributionInfoVO) throws ServiceException;

    /**
     * 根据订单号查找评价信息
     *
     * @param orderCode
     * @return
     * @throws ServiceException
     */
    List<ProdAppraiseAddContent> queryAppraiseByOrderCode(String orderCode) throws ServiceException;

    /**
     * 同步B2B 康美中药城订单
     *
     * @param orderMainCodeList
     * @return
     * @throws ServiceException
     */
    Map sycOrderList(List<String> orderMainCodeList) throws ServiceException;

    /**
     * 获取B2B 康美中药城订单
     *
     * @param conditionMap
     * @return
     * @throws ServiceException
     */
    Map findSyncOrderList(Map<String, Object> conditionMap) throws ServiceException;

    /**
     * 查看订单评价信息
     * @param orderCode
     * @return
     * @throws ServiceException
     */
    Map<String, Object> queryAssessByOrderCode(String orderCode) throws ServiceException;

    /**
     * 修改运费
     *
     * @param orderCode
     * @param userId
     * @param newFare
     * @return
     * @throws ServiceException
     */
    boolean updateFare(String orderCode, String userId, BigDecimal newFare) throws ServiceException;

    /**
     * 查询快递信息
     *
     * @param expressName
     * @param expressNo
     * @return
     * @throws ServiceException
     */
    ExpressSubscription queryExpressPathInfo(String expressName, String expressNo) throws ServiceException;

    /**
     * 商家仅限修改一次订单物流信息
     *
     * @param orderCode 订单号
     * @return 返回若是<2,则说明可以修改 1次是填写物流信息，2次表示填写一次、修改一次。
     * @throws ServiceException
     */
    Integer queryUpdateLogisticCount(String orderCode) throws ServiceException;

    /**
     * 订单导出功能
     *
     * @param map
     * @return
     * @throws ServiceException
     */
    String exportOrder(Map map) throws ServiceException;

    /**
     * 获取预售活动尾款支付截止时间
     *
     * @param orderCode 订单号
     * @return
     * @throws ServiceException
     */
    String getFinallyPayEndTime(String orderCode) throws ServiceException;
}