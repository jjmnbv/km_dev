package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.entities.HurlProduct;
import com.pltfm.app.entities.HurlProductCriteria;
import com.pltfm.app.entities.HurlProductExample;

@SuppressWarnings("unchecked")
public interface HurlProductDAO {

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  int countByExample(HurlProductExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  int deleteByExample(HurlProductExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  int deleteByPrimaryKey(Long settlementHurlId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  Long insert(HurlProduct record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  Long insertSelective(HurlProduct record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  List selectByExample(HurlProductExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  HurlProduct selectByPrimaryKey(Long settlementHurlId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  int updateByExampleSelective(HurlProduct record, HurlProductExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  int updateByExample(HurlProduct record, HurlProductExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  int updateByPrimaryKeySelective(HurlProduct record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_PRODUCT
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  int updateByPrimaryKey(HurlProduct record) throws SQLException;

  /**
   * 取妥投汇总金额数
   * 
   * @param hurlProductCriteria
   * @return
   * @throws SQLException
   */
  Map selectHurlSum(HurlProductCriteria hurlProductCriteria) throws SQLException;

  /**
   * 导出妥投商品明细 信息数据查询
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  List<Map<String, Object>> queryExportHurlInfo(Map<String, String> map) throws SQLException;
}
