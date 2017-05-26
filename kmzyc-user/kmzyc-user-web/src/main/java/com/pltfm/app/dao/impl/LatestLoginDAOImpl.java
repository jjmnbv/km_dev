package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.LatestLoginDAO;
import com.pltfm.app.vobject.LatestLogin;
import com.pltfm.app.vobject.LatestLoginExample;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 最近登录信息处理类
 * 
 * @author cjm
 * @since 2013-7-24
 */
@Component(value = "latestLoginDAO")
public class LatestLoginDAOImpl implements LatestLoginDAO {
  /**
   * 数据库操作对象
   */
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 添加最近登录信息
   * 
   * @param record 最近登录实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insert(LatestLogin record) throws SQLException {
    Object newKey = sqlMapClient.insert("LATEST_LOGIN.abatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * 修改最近登录信息
   * 
   * @param record 最近登录实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKey(LatestLogin record) throws SQLException {
    int rows = sqlMapClient.update("LATEST_LOGIN.abatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * 动态修改最近登录信息
   * 
   * @param record 最近登录实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKeySelective(LatestLogin record) throws SQLException {
    int rows =
        sqlMapClient.update("LATEST_LOGIN.abatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * 按最近登录信息条件查询
   * 
   * @param example 最近登录条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List selectByExample(LatestLoginExample example) throws SQLException {
    List list = sqlMapClient.queryForList("LATEST_LOGIN.abatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * 根据最近登录主键查询单条手机随机码信息
   * 
   * @param nId 最近登录主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public LatestLogin selectByPrimaryKey(Integer nId) throws SQLException {
    LatestLogin key = new LatestLogin();
    key.setN_Id(nId);
    LatestLogin record = (LatestLogin) sqlMapClient
        .queryForObject("LATEST_LOGIN.abatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 按最近登录信息条件进行删除
   * 
   * @param example 最近登录条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByExample(LatestLoginExample example) throws SQLException {
    int rows = sqlMapClient.delete("LATEST_LOGIN.abatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * 根据最近登录主键删除手机随机码信息
   * 
   * @param nCommercialTenantId 最近登录主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByPrimaryKey(Integer nId) throws SQLException {
    LatestLogin key = new LatestLogin();
    key.setN_Id(nId);
    int rows = sqlMapClient.delete("LATEST_LOGIN.abatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 按最近登录信息条件查询总条数
   * 
   * @param example 最近登录条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int countByExample(LatestLoginExample example) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("LATEST_LOGIN.abatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * 动态按最近登录信息条件进行修改
   * 
   * @param record 最近登录实体
   * @param example 最近登录条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByExampleSelective(LatestLogin record, LatestLoginExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("LATEST_LOGIN.abatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * 按最近登录信息条件进行修改
   * 
   * @param record 最近登录实体
   * @param example 最近登录条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByExample(LatestLogin record, LatestLoginExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("LATEST_LOGIN.abatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * 按条件查询最近登录信息总数量
   * 
   * @param vo 最近登录信息类
   * @return 返回值
   */
  @Override
  public int selectCountByVo(LatestLogin vo) throws SQLException {
    List list = sqlMapClient.queryForList("LATEST_LOGIN.getLatestLoginCount", vo);
    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();

    return recs;
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 邮箱验证信息类
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public List selectPageByVo(LatestLogin vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("LATEST_LOGIN.searchPageByVo", vo);
    return pageList;
  }

  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * LATEST_LOGIN 修改条件参数类
   * 
   * @abatorgenerated Wed Jul 24 10:23:01 CST 2013
   */
  private static class UpdateByExampleParms extends LatestLoginExample {
    private Object record;

    public UpdateByExampleParms(Object record, LatestLoginExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }


}
