package com.pltfm.app.remote.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.km.framework.mq.Sender;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.MessageConstants;
import com.kmzyc.framework.constants.MessageTypeEnum;
import com.kmzyc.order.remote.OrderCallBackRemoteService;
import com.pltfm.app.dao.OrderItemDAO;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dao.OrderOperateStatementDAO;
import com.pltfm.app.dao.OrderPayStatementDAO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.entities.OrderPayStatementExample;
import com.pltfm.app.entities.OrderPayStatementExample.Criteria;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.SysConstants;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.OrderMainVo;
import com.pltfm.sys.util.ErrorCode;

@SuppressWarnings("unchecked")
@Service("orderCallBackRemoteService")
public class OrderCallBackRemoteServiceImpl implements OrderCallBackRemoteService {

    private Logger log = Logger.getLogger(OrderCallBackRemoteServiceImpl.class);
    private static final long serialVersionUID = 3071588308647202569L;
    @Resource
    private OrderMainDAO orderMainDAO;
    @Resource
    private OrderItemDAO orderItemDAO;
    @Resource
    private OrderOperateStatementDAO orderOperateStatementDAO;
    @Resource
    private OrderPayStatementDAO orderPayStatementDAO;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource
    private Destination destination;

    @Resource(name = "logisticsMap")
    private Map<String, String> logisticsMap;



    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public String getLogisticNumber(LogisticAndDistributionInfoVO logisticAndDistributionInfoVO)
            throws ServiceException {

        // 初始化返回结果
        String result = null;
        // 初始化订单对象，用于通过订单号获取订单相关信息
        OrderMain orderMain = null;
        // 初始化订单装饰对象，用于从接口提取订单相关信息
        OrderMainVo orderMainVo = null;
        // 是否需要发邮件的标志位
        boolean needEmail = false;
        // 是否需要发短信的标志位
        boolean needMessage = false;
        // 邮件或短信发送是否成功的标志位
        boolean sendFlag = false;
        // 日志的描述信息
        String message = null;
        // 初始化邮件发送对象
        /* EmailSendGoodsVO emailVO = null; */
        // 初始化短信发送对象
        OrderMain messageVO = null;
        // 初始化用户对象，用于获取发送邮件或短信所需的个人信息
        UserInfoDO userInfoDO = null;
        // 修改订单信息的操作结果
        int updateMainIntResult = 0;
        // 修改订单明细信息的操作结果
        int updateItemIntResult = 0;
        // 初始化订单状态，用于订单中的订单状态赋值
        Long status = null;
        // 初始化订单操作类型
        int orderOperateType = 0;
        // 初始化订单状态，用于操作流水中订单状态赋值
        int orderStatus = 0;
        // 初始化当次订单操作流水对象
        OrderOperateStatement record = null;
        // 初始化前次订单操作流水对象
        OrderOperateStatement previousRecord = null;
        // 订单来源的渠道
        String orderChannel = "";
        // 初始化操作信息
        String operateInfo = null;
        // 短信邮件接口中的系统类型
        Integer systemType = Integer.MIN_VALUE;

        if (logisticAndDistributionInfoVO == null) {
            result = "logisticAndDistributionInfoVO is null !";
            return result;
        }
        // 从传入参数中得到物流单号
        String logisticsOrderNo = logisticAndDistributionInfoVO.getLogisticsOrderNo();
        // 从传入参数中得到物流公司代码
        String logisticsCode = logisticAndDistributionInfoVO.getLogisticsCode();
        // 从传入参数中得到物流公司名称
        String logisticsName = logisticAndDistributionInfoVO.getLogisticsName();
        // 从传入参数中得到订单号
        String orderCode = logisticAndDistributionInfoVO.getOrderCode();
        // 从传入参数中得到配送单单号
        Long distributionId = logisticAndDistributionInfoVO.getDistributionId();
        // 从传入参数中得到配送单-配送单明细的map
        Map<Long, List<DistributionDetailInfo>> distributionInfoMap =
                logisticAndDistributionInfoVO.getDistributionInfoMap();
        // 以传入的配送单为key得到该配送单下的所有配送单明细列表
        List<DistributionDetailInfo> distributionDetailList =
                distributionInfoMap.get(distributionId);
        // 如果供应商接口调用，配送单号是没有的
        if (distributionId == null) {
            result = "distributionId is null";
            return result;
        }

        if (logisticsOrderNo == null) {
            result = "logisticsOrderNo is null";
            return result;
        }
        if (logisticsCode == null) {
            result = "logisticsCode is null";
            return result;
        }
        // 遍历配送单明细列表
        if (distributionDetailList != null) {
            String batchNo = null;
            String skuCode = null;
            OrderItem orderItem = null;
            int loop = 0;
            for (DistributionDetailInfo detialInfo : distributionDetailList) {
                loop++;
                batchNo = detialInfo.getBatchNo();
                skuCode = detialInfo.getProductSkuValue();
                orderItem = new OrderItem();
                orderItem.setOrderCode(orderCode);
                orderItem.setCommoditySku(skuCode);
                orderItem.setCommodityBatchNumber(batchNo);
                try {
                    updateItemIntResult = orderItemDAO.updateBatchNoByOrderCode(orderItem);
                } catch (SQLException e) {
                    log.error("订单" + orderCode + "回调得到第三方物流运单号时修改订单明细中的商品：" + skuCode + " 批次号报错",
                            e);
                    updateItemIntResult = -1;
                    throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_ERROR,
                            "订单" + orderCode + "回调得到第三方物流运单号时修改订单明细中的商品：" + skuCode + " 批次号报错", e);
                }
            }
        } else {
            result = "distributionDetailList is null";
            return result;
        }
        // updateItemIntResult=-1说明前面修改批次号发生了异常；
        if (updateItemIntResult == -1) {
            result = "exception when updating batchNo on orderItem";
            return result;
        }
        // updateItemIntResult=0说明没有修改传入数据的批次号
        if (updateItemIntResult == 0) {
            result = "no availabe data when updating batchNo on orderItem";
            return result;
        }
        if (orderCode != null) {
            orderMain = new OrderMain();
            orderMain.setOrderCode(orderCode);
            try {
                orderMainVo = orderMainDAO.selectByOrderCode(orderCode);
                orderChannel = orderMainVo.getOrderChannel();
                systemType = SysConstants.sysChannelTosmsmailMap.get(orderChannel);
            } catch (SQLException e) {
                log.error("订单" + orderCode + "回调得到第三方物流运单号时查询订单报错", e);
                throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_ERROR,
                        "订单" + orderCode + "回调得到第三方物流运单号时查询订单报错", e);
            }
        } else {
            result = "orderCode is null";
            return result;
        }
        OrderMainVo orderMainVoo = null;
        try {
            orderMainVoo = orderMainDAO.selectByOrderCode(orderCode);
        } catch (SQLException e1) {
            log.error("根据订单号" + orderCode + ",获取订单异常", e1);
        }
        status = Long.valueOf(OrderDictionaryEnum.Order_Status.Ship_Done.getKey());
        orderMain.setLogisticsOrderNo(logisticsOrderNo);
        orderMain.setLogisticsCode(logisticsCode);
        boolean changeStatus = false;
        if (orderMainVoo != null && orderMainVoo.getOrderStatus()
                .equals(Long.valueOf(OrderDictionaryEnum.Order_Status.Order_Done.getKey()))) {
            orderMain.setOrderStatus(
                    Long.valueOf(OrderDictionaryEnum.Order_Status.Order_Done.getKey()));
        } else {
            changeStatus = true;
            orderMain.setOrderStatus(status);
        }
        orderMain.setLogisticsName(logisticsName);
        try {
            updateMainIntResult = orderMainDAO.updateByOrderCode(orderMain);
            /** begin同时修改推广效果订单状态creatBy lijianjun */
            /*
             * 删除微商业务 try { if (changeStatus) { SpreadEffect spereadEffect = new SpreadEffect();
             * spereadEffect.setStatus((short) SpreadEffectStatus.ALREADY_DISTRI.getKey()); //
             * 6代表已配送 spereadEffect.setOrderCode(orderCode);
             * spreadEffectDAO.updateSpreadOrder(spereadEffect); } } catch (SQLException e) {
             * log.error("修改推广效果订单状态发生异常", e); }
             */
            /** end同时修改推广效果订单状态creatBy lijianjun */
        } catch (SQLException e) {
            log.error("订单" + orderCode + "回调得到第三方物流运单号时修改订单报错", e);
            throw new ServiceException(21001, "订单" + orderCode + "回调得到第三方物流运单号时修改订单报错", e);
        }
        if (updateMainIntResult == 1 && updateItemIntResult > 0) {
            if (logisticAndDistributionInfoVO.isIncludeMedicineFlag()) {
                operateInfo = "您的订单 " + orderCode + " 中的商品已经从 广东康美健康大药房（普宁长春店）发出。请在收到商品后点击确认收货。";
            } else {
                operateInfo = "您的订单" + orderCode + "中的商品已经发货(" + logisticsMap.get(logisticsCode)
                        + "：" + logisticsOrderNo + "),请在收到商品后确认收货";
            }
            orderStatus = OrderDictionaryEnum.Order_Status.Ship_Done.getKey();
            orderOperateType = OrderDictionaryEnum.OrderOperateType.Ship.getKey();
            record = new OrderOperateStatement();
            record.setNowOperateDate(new Date());
            record.setNowOperateType(new Long(orderOperateType));
            record.setNowOperator(SysConstants.SYS);
            record.setNowOrderStatus(new Long(orderStatus));
            record.setOperateInfo(operateInfo);
            record.setOrderCode(orderCode);
            record.setNowOrderSum(orderMainVoo.getOriginalOrderSum());

            try {
                previousRecord = orderOperateStatementDAO.selectLatestByPrimaryKey(orderCode);
                if (previousRecord != null) {
                    record.setPreviousOperateDate(previousRecord.getNowOperateDate());
                    record.setPreviousOperateType(previousRecord.getNowOperateType());
                    record.setPreviousOperator(previousRecord.getNowOperator());
                    record.setPreviousOrderStatus(previousRecord.getNowOrderStatus());
                    record.setPreviousOrderSum(previousRecord.getNowOrderSum());
                    record.setNowOrderSum(previousRecord.getNowOrderSum());
                    if (previousRecord.getNowOrderStatus()
                            .intValue() != OrderDictionaryEnum.Order_Status.Ship_Done.getKey()) {
                        needMessage = true;
                    }
                }
                orderOperateStatementDAO.insert(record);
            } catch (SQLException e) {
                log.error("订单" + orderCode + "回调得到第三方物流运单号时生成操作流水异常", e);
                throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_ERROR,
                        "订单" + orderCode + "回调得到第三方物流运单号时生成操作流水异常", e);
            }

            try {
                // 根据loginId查询loginAccount
                userInfoDO = orderMainDAO.queryUserInfo(orderMainVo.getCustomerId().longValue());

                if (needMessage) {
                    messageVO = new OrderMain();
                    messageVO.setCustomerId(new BigDecimal(userInfoDO.getLoginId()));
                    messageVO.setCustomerMobile(userInfoDO.getMobile());
                    messageVO.setOrderCode(orderCode);
                    messageVO.setLogisticsName(logisticsName);
                    messageVO.setLogisticsOrderNo(logisticsOrderNo);
                    sendFlag = this.sendMsgByOrderShipped(messageVO, systemType);
                    if (sendFlag) {
                        message = "订单" + orderCode + "发送短信至" + userInfoDO.getMobile() + "成功";
                    } else {
                        message = "订单" + orderCode + "发送短信至" + userInfoDO.getMobile() + "失败";
                    }
                    log.info(message);
                }
            } catch (Exception e) {
                log.error("订单" + orderCode + "回调得到第三方物流运单号时发邮件或是短信异常", e);
            }
        }
        result = "SUCCESS";
        return result;
    }

    @Override
    public int insertPayStatement(List<OrderPayStatement> list) throws ServiceException {
        int result = 0;
        OrderPayStatement orderPayStatement;
        List<OrderPayStatement> selectOrderPayStatement;
        Long insertResult;
        try {
            // ---------------------- 查询是否已存在该订单号的“准备”的支付流水 begin------------
            orderPayStatement = list.get(0);
            OrderPayStatementExample example = new OrderPayStatementExample();
            Criteria criteria = example.createCriteria();
            criteria.andOrderCodeEqualTo(orderPayStatement.getOrderCode());
            criteria.andStateEqualTo(new Long(OrderDictionaryEnum.OrderPayState.Ready.getKey()));
            if (null != orderPayStatement.getYsFlage()
                    && !"".equals(orderPayStatement.getYsFlage().trim())) {// 如果
                criteria.addYsFlageEqualTo(orderPayStatement.getYsFlage());
            }
            selectOrderPayStatement = orderPayStatementDAO.selectByExample(example);
            // ---------------------- 查询是否已存在该订单号的“准备”的支付流水 end------------

            // 如果已存在准备的支付流水，则进行update
            if (selectOrderPayStatement != null && selectOrderPayStatement.size() > 0) {
                orderPayStatement
                        .setPayStatementNo(selectOrderPayStatement.get(0).getPayStatementNo());
                result = orderPayStatementDAO.updateByPrimaryKey(orderPayStatement);
            } else {
                insertResult = orderPayStatementDAO.insert(orderPayStatement);
                if (insertResult != null && insertResult.intValue() > 0) {
                    result = 1;
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_ERROR,
                    "银行/支付平台支付时录入支付流水信息异常", e);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public String getLogisticNumber4Supplier(
            LogisticAndDistributionInfoVO logisticAndDistributionInfoVO) throws ServiceException {
        // 初始化返回结果
        String result = null;
        // 初始化订单对象，用于通过订单号获取订单相关信息
        OrderMain orderMain = null;
        // 初始化订单装饰对象，用于从接口提取订单相关信息
        OrderMainVo orderMainVo = null;
        // 是否需要发邮件的标志位
        boolean needEmail = false;
        // 是否需要发短信的标志位
        boolean needMessage = false;
        // 邮件或短信发送是否成功的标志位
        boolean sendFlag = false;
        // 日志的描述信息
        String message = null;
        // 初始化邮件发送对象
        /* EmailSendGoodsVO emailVO = null; */
        // 初始化短信发送对象
        OrderMain messageVO = null;
        // 初始化用户对象，用于获取发送邮件或短信所需的个人信息
        UserInfoDO userInfoDO = null;
        // 初始化订单状态，用于订单中的订单状态赋值
        Long status = null;
        // 初始化订单操作类型
        int orderOperateType = 0;
        // 初始化订单状态，用于操作流水中订单状态赋值
        int orderStatus = 0;
        // 初始化当次订单操作流水对象
        OrderOperateStatement record = null;
        // 初始化前次订单操作流水对象
        OrderOperateStatement previousRecord = null;
        // 订单来源的渠道
        String orderChannel = "";
        // 初始化操作信息
        String operateInfo = null;
        // 短信邮件接口中的系统类型
        Integer systemType = Integer.MIN_VALUE;

        if (logisticAndDistributionInfoVO == null) {
            result = "logisticAndDistributionInfoVO is null !";
            return result;
        }
        // 从传入参数中得到物流单号
        String logisticsOrderNo = logisticAndDistributionInfoVO.getLogisticsOrderNo();
        // 从传入参数中得到物流公司代码
        String logisticsCode = logisticAndDistributionInfoVO.getLogisticsCode();
        // 从传入参数中得到物流公司名称
        String logisticsName = logisticAndDistributionInfoVO.getLogisticsName();
        // 从传入参数中得到订单号
        String orderCode = logisticAndDistributionInfoVO.getOrderCode();

        if (logisticsOrderNo == null) {
            result = "logisticsOrderNo is null";
            return result;
        }
        if (logisticsCode == null) {
            result = "logisticsCode is null";
            return result;
        }
        if (logisticsName == null) {
            result = "logisticsName is null";
            return result;
        }
        if (orderCode == null) {
            result = "orderCode is null";
            return result;
        } else {
            orderMain = new OrderMain();
            orderMain.setOrderCode(orderCode);
        }
        operateInfo = "您的订单" + orderCode + "中的商品已经发货(" + logisticsMap.get(logisticsCode) + "："
                + logisticsOrderNo + "),请在收到商品后确认收货";
        try {
            orderMainVo = orderMainDAO.selectByOrderCode(orderCode);
            orderChannel = orderMainVo.getOrderChannel();
            systemType = SysConstants.sysChannelTosmsmailMap.get(orderChannel);
        } catch (SQLException e) {
            log.error("订单" + orderCode + "供应商后台回调得到第三方物流运单号时查询订单报错", e);
            throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_ERROR,
                    "订单" + orderCode + "供应商后台回调得到第三方物流运单号时查询订单报错", e);
        }
        status = (long) OrderDictionaryEnum.Order_Status.Ship_Done.getKey();
        orderMain.setLogisticsOrderNo(logisticsOrderNo);
        orderMain.setLogisticsCode(logisticsCode);
        if (orderMainVo.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Order_Done
                .getKey()) {
            orderMain.setOrderStatus((long) OrderDictionaryEnum.Order_Status.Order_Done.getKey());
        } else {
            orderMain.setOrderStatus(status);
        }
        orderMain.setOrderStatus(status);
        orderMain.setLogisticsName(logisticsName);
        try {
            orderMainDAO.updateByOrderCode(orderMain);
            /*
             * 删除微商业务 try { if (changeStatus) { SpreadEffect spereadEffect = new SpreadEffect();
             * spereadEffect.setStatus((short) SpreadEffectStatus.ALREADY_DISTRI.getKey()); //
             * 6代表已配送 spereadEffect.setOrderCode(orderCode);
             * spreadEffectDAO.updateSpreadOrder(spereadEffect); } } catch (SQLException e) {
             * log.error("修改推广效果订单状态发生异常", e); }
             */
        } catch (SQLException e) {
            log.error("订单" + orderCode + "供应商后台回调得到第三方物流运单号时修改订单报错", e);
            throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_ERROR,
                    "订单" + orderCode + "供应商后台回调得到第三方物流运单号时修改订单报错", e);
        }
        orderStatus = OrderDictionaryEnum.Order_Status.Ship_Done.getKey();
        orderOperateType = OrderDictionaryEnum.OrderOperateType.Ship.getKey();
        record = new OrderOperateStatement();
        record.setNowOperateDate(new Date());
        record.setNowOperateType((long) orderOperateType);
        record.setNowOperator(null == logisticAndDistributionInfoVO.getOperator() ? SysConstants.SYS
                : logisticAndDistributionInfoVO.getOperator());
        record.setNowOrderStatus((long) orderStatus);
        record.setOperateInfo(operateInfo);
        record.setOrderCode(orderCode);
        try {
            previousRecord = orderOperateStatementDAO.selectLatestByPrimaryKey(orderCode);
            if (previousRecord != null) {
                record.setPreviousOperateDate(previousRecord.getNowOperateDate());
                record.setPreviousOperateType(previousRecord.getNowOperateType());
                record.setPreviousOperator(previousRecord.getNowOperator());
                record.setPreviousOrderStatus(previousRecord.getNowOrderStatus());
                record.setPreviousOrderSum(previousRecord.getNowOrderSum());
                record.setNowOrderSum(previousRecord.getNowOrderSum());
                // 物流单号可以修改一次 20161201 mod
                // if (previousRecord.getNowOrderStatus().intValue() !=
                // OrderDictionaryEnum.Order_Status.Ship_Done
                // .getKey()) {
                needMessage = true;
                // }
            }
            orderOperateStatementDAO.insert(record);
        } catch (SQLException e) {
            log.error("订单" + orderCode + "供应商后台回调得到第三方物流运单号时生成操作流水异常", e);
            throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_ERROR,
                    "订单" + orderCode + "供应商后台回调得到第三方物流运单号时生成操作流水异常", e);
        }
        try {
            // 根据loginId查询loginAccount
            userInfoDO = orderMainDAO.queryUserInfo(orderMainVo.getCustomerId().longValue());
            /*
             * 删除邮件业务 if (needEmail) { emailVO = new EmailSendGoodsVO();
             * emailVO.setCompany(logisticsName); emailVO.setDeliveryNo(logisticsOrderNo);
             * emailVO.setEmail(userInfoDO.getEmail());
             * emailVO.setLoginId(orderMainVo.getCustomerId().longValue());
             * emailVO.setLoginName(orderMainVo.getCustomerAccount());
             * emailVO.setOrderCode(orderCode); emailVO.setSendDate(new Date()); sendFlag =
             * emailSubscriptionRemoteService.emailSender(emailVO, systemType); if (sendFlag) {
             * message = "订单" + orderCode + "发送邮件至" + userInfoDO.getEmail() + "成功"; } else { message
             * = "订单" + orderCode + "发送邮件至" + userInfoDO.getEmail() + "失败"; } log.info(message); }
             */
            if (needMessage) {
                messageVO = new OrderMain();
                messageVO.setCustomerId(new BigDecimal(userInfoDO.getLoginId()));
                messageVO.setCustomerMobile(userInfoDO.getMobile());
                messageVO.setOrderCode(orderCode);
                messageVO.setLogisticsName(logisticsName);
                messageVO.setLogisticsOrderNo(logisticsOrderNo);
                messageVO.setLogisticsCode(logisticsCode);
                sendFlag = this.sendMsgByOrderShipped(messageVO, systemType);
                if (sendFlag) {
                    message = "订单" + orderCode + "发送短信至" + userInfoDO.getMobile() + "成功";
                } else {
                    message = "订单" + orderCode + "发送短信至" + userInfoDO.getMobile() + "失败";
                }
                log.info(message);
            }
        } catch (Exception e) {
            log.error("订单" + orderCode + "供应商后台回调得到第三方物流运单号时发邮件或是短信异常", e);
        }
        result = "SUCCESS";
        return result;
    }

    /**
     * 获取订单物流信息修改次数
     * 
     * @param orderCode
     * @return
     * @throws ServiceException
     */
    @Override
    public Integer queryUpdateLogisticCount(String orderCode) throws ServiceException {
        if (null == orderCode) {
            throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_ERROR, "订单号为空");
        }
        try {
            return orderOperateStatementDAO.queryUpdateLogisticCount(orderCode);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_ERROR, e.getMessage());
        }
    }

    /**
     * 已发货短信提醒
     *
     * @param orderMain
     * @return
     */
    private boolean sendMsgByOrderShipped(OrderMain orderMain, Integer systemType) {
        List<Long> uidList = new ArrayList<Long>();
        uidList.add(Long.parseLong(orderMain.getCustomerId().toString()));
        List<String> mobilePhones = new ArrayList<String>();
        mobilePhones.add(orderMain.getCustomerMobile());
        KmMsg kmMsg = new KmMsg("6000", true);// 报文编号为1000,参数2为是否回复
        kmMsg.getMsgData().put("kmMsgType", MessageConstants.KMMSG_MOBIL);
        kmMsg.getMsgData().put("smsmailType", EmailSendType.MSGDELIVERY.getStatus());
        kmMsg.getMsgData().put("systemType", MessageConstants.KMMSG_SYSTEM_TYPE_B2B);
        kmMsg.getMsgData().put("mobilePhones", mobilePhones);
        kmMsg.getMsgData().put("msgSendType", MessageConstants.EM_SEND_TYPE_IMM);
        kmMsg.getMsgData().put("uidList", uidList);
        kmMsg.getMsgData().put("orderCode", orderMain.getOrderCode());
        kmMsg.getMsgData().put("logisticsName", logisticsMap.get(orderMain.getLogisticsCode()));
        kmMsg.getMsgData().put("logisticsOrderNo", orderMain.getLogisticsOrderNo().toString());
        kmMsg.getMsgData().put("modelType", MessageTypeEnum.SHIPPEDORDER.getStatus());
        kmMsg.setReply(true);
        try {
            Sender.send(kmMsg, destination, jmsTemplate);
        } catch (Exception e) {
            log.error("sendMsgByOrderShipped" + e.getMessage(), e);
        }
        return true;
    }
}
