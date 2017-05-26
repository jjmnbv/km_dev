package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.vo.EraInfo;
import com.pltfm.app.dao.EraInfoDAO;


@Component
public class EraInfoDAOImpl implements EraInfoDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  // 分页查询时代列表
  @Override
@SuppressWarnings("unchecked")
  public List<EraInfo> selectEraInfoList(EraInfo eraInfo) throws SQLException {
    return sqlMapClient.queryForList("ERA_INFO.selectEraInfoList", eraInfo);
  }

  // 查询时代会员总数
  @Override
public int selectEraInfoCount(EraInfo eraInfo) throws SQLException {
    Integer count = (Integer) sqlMapClient.queryForObject("ERA_INFO.selectEraInfoCount", eraInfo);
    if (count == null) {
      count = 0;
    }
    return count;
  }

  // 删除
  @Override
public int deleteByPrimaryKey(Long eraInfoId) throws SQLException {
    EraInfo key = new EraInfo();
    key.setEraInfoId(new BigDecimal(eraInfoId));
    int rows = sqlMapClient.delete("ERA_INFO.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  // 添加
  @Override
public BigDecimal insert(EraInfo record) throws SQLException {
    BigDecimal ear_id = (BigDecimal) sqlMapClient.insert("ERA_INFO.ibatorgenerated_insert", record);
    return ear_id;
  }

  // 查询
  @Override
public EraInfo selectByPrimaryKey(Long eraInfoId) throws SQLException {
    EraInfo key = new EraInfo();
    key.setEraInfoId(new BigDecimal(eraInfoId));
    EraInfo record =
        (EraInfo) sqlMapClient.queryForObject("ERA_INFO.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  // 关联login_info 查询是否是时代用户
  @Override
public EraInfo selectOverLoginInfoByLoginId(String loginAccount) throws SQLException {
    return (EraInfo) sqlMapClient.queryForObject("ERA_INFO.select_byLoginIdInLoginInfo",
        loginAccount);
  }

  // 条件查询
  @Override
public EraInfo selectByPrimaryKeys(EraInfo record) throws SQLException {
    return (EraInfo) sqlMapClient.queryForObject("ERA_INFO.ibatorgenerated_selectByPrimaryKeys",
        record);
  }

  // 修改
  @Override
public int updateByPrimaryKeySelective(EraInfo record) throws SQLException {
    int rows = sqlMapClient.update("ERA_INFO.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * 修改直销登录信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  @Override
public int updateByLoginIdSelective(EraInfo record) throws SQLException {
    int rows = sqlMapClient.update("ERA_INFO.ibatorgenerated_updateByloginIdSelective", record);
    return rows;
  }

  @Override
  public EraInfo selectByEraNo(String eraNo) throws SQLException {
      EraInfo eraInfo = new EraInfo();
      eraInfo.setEraNo(eraNo);
      EraInfo record =
          (EraInfo) sqlMapClient.queryForObject("ERA_INFO.ibatorgenerated_selectByEraNo", eraInfo);
      return record;
  }

@Override
public BigDecimal insertorUpdateEraInfo(EraInfo record) throws SQLException {
    BigDecimal ear_id = (BigDecimal) sqlMapClient.insert("ERA_INFO.insertorUpdateEraInfo", record);
    return ear_id;
}
}
