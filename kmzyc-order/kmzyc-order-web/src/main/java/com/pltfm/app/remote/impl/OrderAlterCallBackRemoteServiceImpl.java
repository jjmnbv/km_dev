package com.pltfm.app.remote.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderAlterCallBackRemoteService;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.service.OrderAlterOperateStatementService;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.sys.util.ErrorCode;

//import com.pltfm.app.util.LogisticsCompanyMap;

@SuppressWarnings("unchecked")
@Service("orderAlterCallBackRemoteService")
public class OrderAlterCallBackRemoteServiceImpl implements OrderAlterCallBackRemoteService {

  private static final long serialVersionUID = -2864075406970082443L;

  private static Logger logger = Logger.getLogger(OrderAlterCallBackRemoteServiceImpl.class);

  @Resource
  private OrderAlterOperateStatementService orderAlterOperateStatementService;

  // 修改退换货单状态
  @Resource(name = "logisticsMap")
  private Map<String,String> logisticsMap;

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public String getLogisticNumber(LogisticAndDistributionInfoVO logisticAndDistributionInfoVO)
      throws ServiceException {
    String logisticsOrderNo = logisticAndDistributionInfoVO.getLogisticsOrderNo();
    String logisticsCode = logisticAndDistributionInfoVO.getLogisticsCode();
    String logisticsName = logisticAndDistributionInfoVO.getLogisticsName();
    String orderAlterCode = logisticAndDistributionInfoVO.getOrderCode();
    Map DistributionInfoMap = logisticAndDistributionInfoVO.getDistributionInfoMap();
    Integer operatorType = logisticAndDistributionInfoVO.getOperatorType();

    String result = "SUCCESS";
    Long statusNow = null;
    Long statusPrevious;
    Long type = null;
    Date date = new Date();
    String operator;
    if (operatorType != null && operatorType.equals(1)) {
      operator = logisticAndDistributionInfoVO.getOperator();
    } else {
      operator = "product";
    }

    String operateInfo;
    if (logisticsOrderNo == null) {
      result = "logisticsOrderNo is null";
    }
    if (logisticsCode == null) {
      result = "logisticsCode is null";
    }
    if (orderAlterCode == null) {
      result = "orderAlterCode is null";
    }
    try {
      OrderAlter oa = orderAlterOperateStatementService.getOrderAlterByCode(orderAlterCode);
      statusPrevious = new Long(oa.getProposeStatus());
      oa.setLogisticsCode(logisticsCode);
      oa.setLogisticsOrderNo(logisticsOrderNo);
      oa.setLogisticsName(logisticsName);
      if (DistributionInfoMap != null) {
        List<DistributionDetailInfo> distributionDetailList =
            (List<DistributionDetailInfo>) DistributionInfoMap.get(logisticAndDistributionInfoVO
                .getDistributionId());
        String batchNo = distributionDetailList.get(0).getBatchNo();
        oa.setAlterBatchNumber(batchNo);
      }
      if (statusPrevious.intValue() == OrderAlterDictionaryEnum.Propose_Status.F_Stockout.getKey()) {
        statusNow = (long) OrderAlterDictionaryEnum.Propose_Status.Stockout.getKey();
        type = (long) OrderAlterDictionaryEnum.OrderAlterOperateType.Stockout.getKey();
      } else if (statusPrevious.intValue() == OrderAlterDictionaryEnum.Propose_Status.BackShop
          .getKey()) {
        statusNow = (long) OrderAlterDictionaryEnum.Propose_Status.BackShop.getKey();
        type = (long) OrderAlterDictionaryEnum.OrderAlterOperateType.BackShop.getKey();
      }
      if (logisticAndDistributionInfoVO.isIncludeMedicineFlag()) {
        operateInfo = "您的退换货单 " + orderAlterCode + " 中的商品已经从 广东康美之恋大药房（普宁长春店）发出。请在收到商品后点击确认收货。";
      } else {
        operateInfo =
            "您的退换货单" + orderAlterCode + "中的商品已经发货("
                + logisticsMap.get(logisticsCode) + "：" + logisticsOrderNo
                + "),请在收到商品后确认收货";
      }
      if(null == statusNow){
          logger.error("退换货单:"+orderAlterCode+"修改运单号失败,状态= "+statusPrevious.intValue()+"不能做此操作!");
          throw new ServiceException("退换货单:"+orderAlterCode+"修改运单号失败,状态= "+statusPrevious.intValue()+"不能做此操作!");
      }
      oa.setProposeStatus(statusNow.shortValue());
      orderAlterOperateStatementService.updateOrderAlter(oa);
      orderAlterOperateStatementService.save(orderAlterCode, null, statusNow, operator,
          date, type, null, operateInfo);
      /*
       * orderAlterOperateStatementService.save(orderAlterCode, oa.getOrderItemId(),
       * (long)oa.getProposeStatus(),"product", new Date(),
       * oa.getProposeStatus().intValue()==OrderAlterDictionaryEnum
       * .Propose_Status.F_Stockout.getKey()? (long)OrderAlterDictionaryEnum
       * .OrderAlterOperateType.Stockout.getKey(): (long)OrderAlterDictionaryEnum
       * .OrderAlterOperateType.BackShop.getKey(), oa.getRuturnSum(), "换货物流信息.");
       */
      // 如果当前流程节点的状态为已通过待发货的话，那么还需要修改为已发货待签收
    } catch (Exception e) {
      logger.error("回调退换货发生错误", e);
      throw new ServiceException(ErrorCode.INTER_RETURNS_ERROR, "回调退换货发生错误：" + e.getMessage());
    }
    return result;
  }
}
