package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.RefundRequestDao;
import com.pltfm.app.service.RefundRequestService;
import com.pltfm.app.vobject.RefundRequest;
import com.pltfm.sys.util.ErrorCode;

@Service("refundRequestService")
public class RefundRequestServiceImpl implements RefundRequestService {
  @Resource(name = "refundRequestDao")
  private RefundRequestDao refundRequestDao;

  /**
   * 新增退款请求
   * 
   * @return
   * @throws ServiceException
   */
  @Override
  public boolean addRefundRequest(RefundRequest rr) throws ServiceException {
    try {
      return refundRequestDao.addRefundRequest(rr);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "新增退款请求发生异常", e);
    }
  }
  
  /**
   * 预售--新增退款请求
   * 
   * @return
   * @throws ServiceException
   */
  @Override
  public boolean addRefundRequestForYS(RefundRequest rr) throws ServiceException {
    try {
      return refundRequestDao.addRefundRequestForYS(rr);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "预售--新增退款请求发生异常", e);
    }
  }
  
  

  /**
   * 修改退款请求信息
   * 
   * @param rr
   * @return
   * @throws ServiceException
   */
  @Override
  public boolean updateFinishRefundRequest(RefundRequest rr) throws ServiceException {
    try {
      return refundRequestDao.updateFinishRefundRequest(rr);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "修改退款请求信息发生异常", e);
    }
  }

  /**
   * 批量更新退款请求提交时间
   * 
   * @param rr
   * @return
   * @throws SQLException
   */
  @Override
public boolean updateRequestDateRefundRequest(List<Long> rrids, String refundNo, String uName)
      throws ServiceException {
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("rrids", rrids);
      params.put("refundNo", refundNo);
      params.put("uid", uName);
      return refundRequestDao.updateRequestDateRefundRequest(params);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "修改退款请求信息发生异常", e);
    }
  }

  /**
   * 查询退款请求
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
  public List<RefundRequest> queryRefundRequest(Map<String, String> map) throws ServiceException {
    try {
      return refundRequestDao.queryRefundRequest(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "查询退款请求发生异常", e);
    }
  }

  /**
   * 查询退款请求个数
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
  public Integer queryRefundRequestCount(Map<String, String> map) throws ServiceException {
    try {
      return refundRequestDao.queryRefundRequestCount(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "查询退款请求个数发生异常", e);
    }
  }
  
  /**
   * 预售查询退款请求个数
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
  public Integer queryRefundRequestCountYS(Map<String, Object> map) throws ServiceException {
    try {
      return refundRequestDao.queryRefundRequestCountYS(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "查询退款请求个数发生异常", e);
    }
  }
  

  /**
   * 根据退款批次号和支付流水号查询待处理退款请求
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
public List<RefundRequest> queryBatchReadyRefundRequest(List<String> outBatchNos, String refundNo)
      throws ServiceException {
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("outBatchNos", outBatchNos);
      params.put("refundNo", refundNo);
      return refundRequestDao.queryBatchReadyRefundRequest(params);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "根据退款批次号和支付流水号查询待处理退款请求", e);
    }
  }

  /**
   * 根据退款请求ID查询
   * 
   * @param rrids
   * @return
   * @throws ServiceException
   */
  @Override
public List<RefundRequest> queryBatchReadyRefundRequestByRID(List<Long> rrids)
      throws ServiceException {
    try {
      return refundRequestDao.queryBatchReadyRefundRequestByRID(rrids);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "根据请求ID查询退款请求发生异常", e);
    }
  }
}
