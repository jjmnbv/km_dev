package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.HurlFare;
import com.pltfm.app.entities.HurlFareCriteria;
import com.pltfm.app.entities.HurlFareExample;

@SuppressWarnings("unchecked")
public interface HurlFareService {

  /**
   * 计算妥投订单运费明细数量
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  int countByExample(HurlFareExample example) throws SQLException;

  /**
   * 删除妥投订单运费明细
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  int deleteByExample(HurlFareExample example) throws SQLException;

  /**
   * 添加妥投订单运费明细
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  Long insert(HurlFare record) throws SQLException;

  /**
   * 查询
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  List selectByExample(HurlFareExample example) throws SQLException;

  /**
   * 修改
   * 
   * @param record
   * @param example
   * @return
   * @throws SQLException
   */
  int updateByExample(HurlFare record, HurlFareExample example) throws SQLException;

  /**
   * 妥投运费数据导出
   * 
   * @param sno
   * @param filePath
   * @param hurlFareCriteria
   * @param hurlIds 记录id拼接
   * @return
   * @throws SQLException
   * @throws ServiceException
   */
  public String hurlFareExport(String sno, String filePath, HurlFareCriteria hurlFareCriteria)
      throws SQLException, ServiceException;

  /**
   * 运费汇总数据映射
   * 
   * @param hurlFareCriteria
   * @return
   * @throws ServiceException
   */
  Map hurlFareSumResult(HurlFareCriteria hurlFareCriteria) throws ServiceException;
}
