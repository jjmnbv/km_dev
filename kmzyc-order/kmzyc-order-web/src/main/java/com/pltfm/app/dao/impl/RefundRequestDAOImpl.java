package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.RefundRequestDao;
import com.pltfm.app.vobject.RefundRequest;

@Repository("refundRequestDao")
@SuppressWarnings("unchecked")
public class RefundRequestDAOImpl extends BaseDAO<RefundRequest> implements RefundRequestDao {
  /**
   * 新增退款请求
   * 
   * @return
   * @throws SQLException
   */
  @Override
  public boolean addRefundRequest(RefundRequest rr) throws SQLException {
    return sqlMapClient.insert("KMORDER_REFUND_REQUEST.SQL_INSERT_REFUND_REQUEST", rr) != null;
  }
  
  /**
   * 预售--新增退款请求
   * 
   * @return
   * @throws SQLException
   */
  @Override
  public boolean addRefundRequestForYS(RefundRequest rr) throws SQLException {
    return sqlMapClient.insert("KMORDER_REFUND_REQUEST.SQL_INSERT_REFUND_REQUEST_YS", rr) != null;
  }
  

  /**
   *修改退完成款请求信息
   * 
   * @param rr
   * @return
   * @throws SQLException
   */
  @Override
  public boolean updateFinishRefundRequest(RefundRequest rr) throws SQLException {
    return sqlMapClient.update("KMORDER_REFUND_REQUEST.SQL_UPDATE_FINISH_REFUND_REQUEST", rr) > 0;
  }

  /**
   * 批量更新退款请求提交时间
   * 
   * @param rr
   * @return
   * @throws SQLException
   */
  @Override
public boolean updateRequestDateRefundRequest(Map<String, Object> params) throws SQLException {
    return sqlMapClient.update("KMORDER_REFUND_REQUEST.SQL_UPDATE_REQUEST_DATE_REFUND_REQUEST",
        params) > 0;
  }

  /**
   * 查询退款请求
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  @Override
  public List<RefundRequest> queryRefundRequest(Map<String, String> map) throws SQLException {
    return sqlMapClient.queryForList(
        "KMORDER_REFUND_REQUEST.SQL_QUERY_REFUND_REQUEST", map);
  }
  
  /**
   * 预售查询指定退款请求个数
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  @Override
  public Integer queryRefundRequestCountYS(Map<String, Object> map) throws SQLException {
    return (Integer) sqlMapClient.queryForObject(
        "KMORDER_REFUND_REQUEST.SQL_QUERY_REFUND_REQUEST_COUNTYS", map);
  }

  /**
   * 查询退款请求个数
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  @Override
  public Integer queryRefundRequestCount(Map<String, String> map) throws SQLException {
    return (Integer) sqlMapClient.queryForObject(
        "KMORDER_REFUND_REQUEST.SQL_QUERY_REFUND_REQUEST_COUNT", map);
  }

  /**
   * 根据退款批次号和支付流水号查询待处理退款请求
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
  public List<RefundRequest> queryBatchReadyRefundRequest(Map<String, Object> params)
      throws SQLException {
    return sqlMapClient.queryForList(
        "KMORDER_REFUND_REQUEST.SQL_QUERY_BATCH_READY_REFUND_REQUEST", params);
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
      throws SQLException {
    return sqlMapClient.queryForList(
        "KMORDER_REFUND_REQUEST.SQL_QUERY_BATCH_READY_REFUND_REQUEST_BY_RID", rrids);
  }
}
