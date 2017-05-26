package com.pltfm.app.remote.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.model.RefundResult;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.RefundMentRemoteService;
import com.pltfm.app.service.OrderAlterOperateStatementService;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.RefundMentService;
import com.pltfm.app.service.RefundRequestService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.RefundRequest;

@Service("refundMentRemoteService")
@Scope("singleton")
public class RefundMentRemoteServiceImpl implements RefundMentRemoteService {

  @Resource
  RefundMentService refundMentService;
  @Resource
  private RefundRequestService refundRequestService;
  @Resource
  private OrderOperateStatementService orderOperateStatementService;
  @Resource
  private OrderAlterOperateStatementService orderAlterOperateStatementService;

  @Override
  public void refundMentDone(RefundResult refundResult) throws ServiceException {
    refundMentService.refundMentDone(refundResult);
  }

  /**
   * 退款回调
   * 
   * @param refundResult退款结果
   * @param paltCode支付平台
   * @throws ServiceException
   */
  @Override
public void refundCallBack(RefundResult rs, Integer paltCode) throws ServiceException {
    if (null == rs || null == rs.getRefundBatchNo() || null == paltCode) {
      return;
    }
    String extParam = rs.getExtParams();
    if (null == extParam || 0 == extParam.length()) {
      return;
    }
    if (paltCode == OrderDictionaryEnum.PlatformCode.alipay.getKey()) {
      String refundNo = rs.getRefundBatchNo();
      List<String> outBatchNos = new ArrayList<String>();
      for (String temp : extParam.split("#")) {
        if (null == temp) {
          continue;
        }
        String[] rd = temp.split("\\^");
        if (null != rd && 3 == rd.length && "SUCCESS".equalsIgnoreCase(rd[2])) {
          outBatchNos.add(rd[0]);
        }
      }
      List<RefundRequest> rrList =
          refundRequestService.queryBatchReadyRefundRequest(outBatchNos, refundNo);
      if (null == rrList || rrList.isEmpty()) {
        return;
      }
      List<RefundRequest> cancelList = new ArrayList<RefundRequest>();
      List<RefundRequest> alterlList = new ArrayList<RefundRequest>();
      for (RefundRequest rr : rrList) {
        if (rr.getRefundType() == 1) {
          cancelList.add(rr);
        } else {
          alterlList.add(rr);
        }
      }
      if (!cancelList.isEmpty()) {
        orderOperateStatementService.batchRefundZFB(cancelList, refundNo);
      }
      if (!alterlList.isEmpty()) {
        orderAlterOperateStatementService.batchAlterRefund(alterlList, refundNo);
      }
    }
  }
}
