package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.RefundRequest;

public interface RefundRequestService {
  /**
   * 新增退款请求
   * 
   * @return
   * @throws ServiceException
   */
  public boolean addRefundRequest(RefundRequest rr) throws ServiceException;

  /**
   *修改退完成款请求信息
   * 
   * @param rr
   * @return
   * @throws SQLException
   */
  public boolean updateFinishRefundRequest(RefundRequest rr) throws ServiceException;

  /**
   * 批量更新退款请求提交时间
   * 
   * @param rr
   * @return
   * @throws SQLException
   */
  public boolean updateRequestDateRefundRequest(List<Long> rrids, String refundNo, String uName)
      throws ServiceException;

  /**
   * 查询退款请求
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<RefundRequest> queryRefundRequest(Map<String, String> map) throws ServiceException;

  /**
   * 查询退款请求个数
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Integer queryRefundRequestCount(Map<String, String> map) throws ServiceException;

  /**
   * 根据退款批次号和支付流水号查询待处理退款请求
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<RefundRequest> queryBatchReadyRefundRequest(List<String> outBatchNos, String refundNo)
      throws ServiceException;

  /**
   * 根据退款请求ID查询
   * 
   * @param rrids
   * @return
   * @throws ServiceException
   */
  public List<RefundRequest> queryBatchReadyRefundRequestByRID(List<Long> rrids)
      throws ServiceException;

  /**
   * 预售查询退款请求个数
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Integer queryRefundRequestCountYS(Map<String, Object> queryMap2) throws ServiceException;

  /**
   * 预售--新增退款请求
   * 
   * @return
   * @throws ServiceException
   */
  public boolean addRefundRequestForYS(RefundRequest rr) throws ServiceException;

}
