package com.kmzyc.express.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kmzyc.express.dao.BaseDAO;
import com.kmzyc.express.dao.ExpressTrackDAO;
import com.kmzyc.express.entities.ExpressTrack;
@SuppressWarnings("unchecked")
@Component(value = "expressTrackDAO")
public class ExpressTrackDAOImpl extends BaseDAO<ExpressTrack> implements ExpressTrackDAO {

  @Override
  public int queryExpressTrackCount(Map<String, String> paramMap) throws Exception {
    return (Integer) sqlMapClient.queryForObject("KMORDER_EXPRESS_TRACK.queryExpressTrackCount",
        paramMap);
  }

  @Override
  public List queryExpressTrackList(Map<String, String> paramMap) throws Exception {
    return sqlMapClient.queryForList("KMORDER_EXPRESS_TRACK.queryExpressTrackList", paramMap);
  }

  @Override
  public Integer deleteExpressTrackBySubId(BigDecimal subId) throws Exception {
    return (Integer) sqlMapClient.insert("KMORDER_EXPRESS_TRACK.deleteExpressTrackBySubId", subId);
  }

  @Override
  public BigDecimal insertExpressTrack(ExpressTrack record) throws Exception {
    return (BigDecimal) sqlMapClient.insert("KMORDER_EXPRESS_TRACK.insertExpressTrack", record);
  }

  @Override
  public Integer batchInsertExpressTrack(List recordList) throws Exception {
    return super.batchInsertByDataNt(recordList, "KMORDER_EXPRESS_TRACK.insertExpressTrack");
  }
}
