package com.pltfm.crowdsourcing.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.crowdsourcing.model.QrcodeApplyRecord;
import com.pltfm.crowdsourcing.dao.QrcodeApplyRecordDao;

@Component("qrcodeApplyRecordDao")
public class QrcodeApplyRecordDaoImpl implements QrcodeApplyRecordDao {

  @Resource
  private SqlMapClient sqlMapClient;

  @Override
  public Long insert(QrcodeApplyRecord record) throws SQLException {
    return (Long) sqlMapClient.insert("CrowdQrcodeApplyRecord.ibatorgenerated_insert", record);
  }

  @Override
  public int updateByPrimaryKey(QrcodeApplyRecord record) throws SQLException {
    return 0;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public List<QrcodeApplyRecord> codeManageList(QrcodeApplyRecord record) throws SQLException {
    List list = sqlMapClient.queryForList("CrowdQrcodeApplyRecord.codeManageList", record);
    return list == null ? null : (List<QrcodeApplyRecord>) list;
  }

  @Override
  public int countManageList(QrcodeApplyRecord record) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("CrowdQrcodeApplyRecord.countManageList", record);
  }

  @Override
  public Map<String, Object> selectBagmanIdByInsCode(String insCode) throws SQLException {
    return (Map<String, Object>) sqlMapClient
        .queryForObject("CrowdQrcodeApplyRecord.selectBagmanIdByInsCode", insCode);
  }

}
