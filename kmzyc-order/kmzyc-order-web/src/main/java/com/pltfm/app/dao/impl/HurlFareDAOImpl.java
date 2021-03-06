package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.HurlFareDAO;
import com.pltfm.app.entities.HurlFare;
import com.pltfm.app.entities.HurlFareCriteria;
import com.pltfm.app.entities.HurlFareExample;

@SuppressWarnings("unchecked")
@Repository("hurlFareDAO")
public class HurlFareDAOImpl extends BaseDAO implements HurlFareDAO {

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public int countByExample(HurlFareExample example) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject("HURL_FARE.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public int deleteByExample(HurlFareExample example) throws SQLException {
    int rows = sqlMapClient.delete("HURL_FARE.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public int deleteByPrimaryKey(Long settlementFareId) throws SQLException {
    HurlFare key = new HurlFare();
    key.setSettlementFareId(settlementFareId);
    int rows = sqlMapClient.delete("HURL_FARE.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public Long insert(HurlFare record) throws SQLException {
    Object newKey = sqlMapClient.insert("HURL_FARE.ibatorgenerated_insert", record);
    return (Long) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public Long insertSelective(HurlFare record) throws SQLException {
    Object newKey = sqlMapClient.insert("HURL_FARE.ibatorgenerated_insertSelective", record);
    return (Long) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public List selectByExample(HurlFareExample example) throws SQLException {
    List list = sqlMapClient.queryForList("HURL_FARE.ibatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public HurlFare selectByPrimaryKey(Long settlementFareId) throws SQLException {
    HurlFare key = new HurlFare();
    key.setSettlementFareId(settlementFareId);
    HurlFare record =
        (HurlFare) sqlMapClient.queryForObject("HURL_FARE.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public int updateByExampleSelective(HurlFare record, HurlFareExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("HURL_FARE.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public int updateByExample(HurlFare record, HurlFareExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("HURL_FARE.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public int updateByPrimaryKeySelective(HurlFare record) throws SQLException {
    int rows = sqlMapClient.update("HURL_FARE.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public int updateByPrimaryKey(HurlFare record) throws SQLException {
    int rows = sqlMapClient.update("HURL_FARE.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * HURL_FARE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private static class UpdateByExampleParms extends HurlFareExample {
    private static final long serialVersionUID = 1L;
    private Object record;

    public UpdateByExampleParms(Object record, HurlFareExample example) {
      super(example);
      this.record = record;
    }

    @SuppressWarnings("unused")
    public Object getRecord() {
      return record;
    }
  }

  @Override
  public Map hurlFareSumResult(HurlFareCriteria hurlFareCriteria) throws SQLException {
    List list = sqlMapClient.queryForList("HURL_FARE.selectHurlFareSum", hurlFareCriteria);
    return CollectionUtils.isEmpty(list) ? null : (Map) list.get(0);
  }

  @Override
  public List<Map<String, Object>> queryExportHurlFare(Map<String, String> map) throws SQLException {
    List<Map<String, Object>> list =
        sqlMapClient.queryForList("HURL_FARE.queryExportHurlFare", map);
    return list;
  }
}
