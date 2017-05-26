package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.HurlProduct;
import com.pltfm.app.entities.HurlProductCriteria;
import com.pltfm.app.entities.HurlProductExample;

@SuppressWarnings("unchecked")
public interface HurlProductService {
  /**
   * 计算妥投商品明细记录数
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  int countByExample(HurlProductExample example) throws SQLException;

  /**
   * 删除妥投商品明细
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  int deleteByExample(HurlProductExample example) throws SQLException;

  /**
   * 添加妥投商品明细
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  Long insert(HurlProduct record) throws SQLException;

  /**
   * 查询妥投商品明细
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  List selectByExample(HurlProductExample example) throws SQLException;

  /**
   * 修改妥投商品明细
   * 
   * @param record
   * @param example
   * @return
   * @throws SQLException
   */
  int updateByExample(HurlProduct record, HurlProductExample example) throws SQLException;

  /**
   * 妥投商品导出
   * 
   * @param sno
   * @param filePath
   * @param hurlProductCriteria
   * @return
   * @throws SQLException
   * @throws ServiceException
   */
  public String hurlExport(String sno, String filePath, HurlProductCriteria hurlProductCriteria)
      throws SQLException, ServiceException;

  /**
   * 获取妥投汇总金额数
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Map selectHurlSum(HurlProductCriteria hurlProductCriteria) throws ServiceException;
}
