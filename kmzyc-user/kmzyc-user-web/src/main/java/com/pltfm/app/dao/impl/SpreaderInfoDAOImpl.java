package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.SpreaderInfoDAO;
import com.pltfm.app.vobject.SpreaderInfo;

@Component(value = "spreaderInfoDAO")
public class SpreaderInfoDAOImpl implements SpreaderInfoDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  public BigDecimal querySeqVsnumber() throws SQLException {
    return (BigDecimal) sqlMapClient.queryForObject("SPREADER_INFO.querySeqVsnumber");
  }

  // 删除微商信息
  public int deleteByPrimaryKey(BigDecimal sid) throws SQLException {
    SpreaderInfo key = new SpreaderInfo();
    key.setSid(sid);
    int rows = sqlMapClient.delete("SPREADER_INFO.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  // 添加微商信息
  public BigDecimal insert(SpreaderInfo record) throws SQLException {
    return (BigDecimal) sqlMapClient.insert("SPREADER_INFO.ibatorgenerated_insert", record);
  }

  // 查询微商信息
  public SpreaderInfo selectByPrimaryKey(SpreaderInfo record) throws SQLException {
    return (SpreaderInfo) sqlMapClient
        .queryForObject("SPREADER_INFO.ibatorgenerated_selectByPrimaryKey", record);
  }

  // 修改微商信息
  public int updateByPrimaryKeySelective(SpreaderInfo record) throws SQLException {
    int rows =
        sqlMapClient.update("SPREADER_INFO.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

}
