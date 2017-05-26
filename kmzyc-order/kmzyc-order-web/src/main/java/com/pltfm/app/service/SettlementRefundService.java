package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.SettlementRefund;
import com.pltfm.app.entities.SettlementRefundCriteria;
import com.pltfm.app.entities.SettlementRefundExample;

@SuppressWarnings("unchecked")
public interface SettlementRefundService {

  /**
   * 查询退款明细记录数
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  int countByExample(SettlementRefundExample example) throws SQLException;

  /**
   * 删除退款明细
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  int deleteByExample(SettlementRefundExample example) throws SQLException;

  /**
   * 新增退款明细
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  Long insert(SettlementRefund record) throws SQLException;

  /**
   * 查询退款明细
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  List selectByExample(SettlementRefundExample example) throws SQLException;

  /**
   * 修改
   * 
   * @param record
   * @param example
   * @return
   * @throws SQLException
   */
  int updateByExample(SettlementRefund record, SettlementRefundExample example) throws SQLException;

  /**
   * 退款数据导出
   * 
   * @param sno
   * @param filePath
   * @param settlementRefundCriteria
   * @return
   * @throws SQLException
   * @throws ServiceException
   */
  public String refundExport(String sno, String filePath,
      SettlementRefundCriteria settlementRefundCriteria) throws SQLException, ServiceException;

  /**
   * 获取退款综合映射
   * 
   * @param settlementRefundCriteria
   * @return
   * @throws ServiceException
   */
  public Map refundSum(SettlementRefundCriteria settlementRefundCriteria) throws ServiceException;
}
