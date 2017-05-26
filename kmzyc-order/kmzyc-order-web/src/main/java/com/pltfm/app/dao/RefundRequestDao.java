package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.RefundRequest;

public interface RefundRequestDao {
  /**
   * 新增退款请求
   * 
   * @return
   * @throws SQLException
   */
  public boolean addRefundRequest(RefundRequest rr) throws SQLException;

  /**
   *修改退完成款请求信息
   * 
   * @param rr
   * @return
   * @throws SQLException
   */
  public boolean updateFinishRefundRequest(RefundRequest rr) throws SQLException;

  /**
   * 批量更新退款请求提交时间
   * 
   * @param rr
   * @return
   * @throws SQLException
   */
  public boolean updateRequestDateRefundRequest(Map<String, Object> params) throws SQLException;

  /**
   * 查询退款请求
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public List<RefundRequest> queryRefundRequest(Map<String, String> map) throws SQLException;

  /**
   * 查询退款请求个数
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public Integer queryRefundRequestCount(Map<String, String> map) throws SQLException;

  /**
   * 根据退款批次号和支付流水号查询待处理退款请求
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<RefundRequest> queryBatchReadyRefundRequest(Map<String, Object> params)
      throws SQLException;

  /**
   * 根据退款请求ID查询
   * 
   * @param rrids
   * @return
   * @throws ServiceException
   */
  public List<RefundRequest> queryBatchReadyRefundRequestByRID(List<Long> rrids)
      throws SQLException;

  /**
   * 预售查询退款请求个数
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public Integer queryRefundRequestCountYS(Map<String, Object> map) throws SQLException;

  
  /**
   * 预售--新增退款请求
   * 
   * @return
   * @throws SQLException
   */
  boolean addRefundRequestForYS(RefundRequest rr) throws SQLException;
}
