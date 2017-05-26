package com.pltfm.app.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kmzyc.express.entities.ExpressTrack;
import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.ExpressTrackDAO;

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
}
