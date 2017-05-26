package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ReserverApplyInfoDAO;
import com.pltfm.app.vobject.ReserverApplyInfo;

@Component(value = "reserverApplyInfoDAO")
public class ReserverApplyInfoDAOImpl implements ReserverApplyInfoDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  // 分页条件查询预备金审核列表
  @SuppressWarnings("unchecked")
  public List<ReserverApplyInfo> pageQueryApplyInfo(ReserverApplyInfo record) throws SQLException {
    return sqlMapClient.queryForList("RESERVER_APPLY_INFO.ibatorgenerated_selectByApplyInfo",
        record);
  }

  // 获取条件查询下总预备金审核列表总数
  public Integer pageQueryApplyInfoCount(ReserverApplyInfo record) throws SQLException {
    return (Integer) sqlMapClient
        .queryForObject("RESERVER_APPLY_INFO.ibatorgenerated_selectCountByApplyInfo", record);
  }

  // 根据申请记录id删除
  public int deleteByPrimaryKey(BigDecimal applyNotesId) throws SQLException {
    ReserverApplyInfo key = new ReserverApplyInfo();
    key.setApplyNotesId(applyNotesId);
    int rows = sqlMapClient.delete("RESERVER_APPLY_INFO.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  // 添加申请记录
  public void insertSelective(ReserverApplyInfo record) throws SQLException {
    sqlMapClient.insert("RESERVER_APPLY_INFO.ibatorgenerated_insertSelective", record);
  }

  // 根据申请记录id查询申请记录
  public List<ReserverApplyInfo> selectByPrimaryKey(ReserverApplyInfo record) throws SQLException {
    List<ReserverApplyInfo> reserverApplyInfo =
        sqlMapClient.queryForList("RESERVER_APPLY_INFO.ibatorgenerated_selectByPrimaryKey", record);
    return reserverApplyInfo;
  }

  // 根据申请记录id修改申请记录
  public int updateByPrimaryKey(ReserverApplyInfo record) throws SQLException {
    int rows =
        sqlMapClient.update("RESERVER_APPLY_INFO.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }
}
