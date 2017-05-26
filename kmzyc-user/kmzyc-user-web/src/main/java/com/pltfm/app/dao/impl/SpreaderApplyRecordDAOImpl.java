package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.SpreaderApplyRecordDAO;
import com.pltfm.app.vobject.SpreaderApplyRecord;

@Component(value = "spreaderApplyRecordDAO")
public class SpreaderApplyRecordDAOImpl implements SpreaderApplyRecordDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  // 删除微商推广着记录
  public int deleteByPrimaryKey(BigDecimal aid) throws SQLException {
    SpreaderApplyRecord key = new SpreaderApplyRecord();
    key.setAid(aid);
    int rows = sqlMapClient.delete("SPREADER_APPLY_RECORD.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  // 添加微商推广着记录
  public BigDecimal insert(SpreaderApplyRecord record) throws SQLException {
    return (BigDecimal) sqlMapClient.insert("SPREADER_APPLY_RECORD.ibatorgenerated_insertSelective",
        record);
  }

  // 查询微商推广着记录
  public SpreaderApplyRecord selectByPrimaryKey(BigDecimal aid) throws SQLException {
    SpreaderApplyRecord key = new SpreaderApplyRecord();
    key.setAid(aid);
    SpreaderApplyRecord record = (SpreaderApplyRecord) sqlMapClient
        .queryForObject("SPREADER_APPLY_RECORD.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  // 修改微商推广着记录
  public int updateByPrimaryKeySelective(SpreaderApplyRecord record) throws SQLException {
    int rows = sqlMapClient
        .update("SPREADER_APPLY_RECORD.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

}
